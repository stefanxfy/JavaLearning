package com.stefan.DailyTest;

import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.*;
import org.apache.poi.hwpf.converter.FontReplacer.Triplet;
import org.apache.poi.hwpf.model.*;
import org.apache.poi.hwpf.usermodel.*;
import org.apache.poi.poifs.filesystem.Entry;
import org.apache.poi.util.POILogFactory;
import org.apache.poi.util.POILogger;
import org.w3c.dom.Node;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.poi.hwpf.converter.AbstractWordUtils.TWIPS_PER_INCH;

/**
 * Converts Word files (95-2007) into HTML files.
 * <p>
 * This implementation doesn't create images or links to them. This can be
 * changed by overriding {@link #processImage(Element, boolean, Picture)}
 * method.
 */
public class DocConverter {
	public static String EMPTY = "";

	public static final String ENCODING_UTF8 = "UTF-8";

	public static final String FORMAT_HTML = "html";

	private static final byte BEL_MARK = 7;

	private static final byte FIELD_BEGIN_MARK = 19;

	private static final byte FIELD_END_MARK = 21;

	private static final byte FIELD_SEPARATOR_MARK = 20;

	private static final Pattern PATTERN_HYPERLINK_EXTERNAL = Pattern
			.compile("^[ \\t\\r\\n]*HYPERLINK \"(.*)\".*$");

	private static final Pattern PATTERN_HYPERLINK_LOCAL = Pattern
			.compile("^[ \\t\\r\\n]*HYPERLINK \\\\l \"(.*)\"[ ](.*)$");

	private static final Pattern PATTERN_PAGEREF = Pattern
			.compile("^[ \\t\\r\\n]*PAGEREF ([^ ]*)[ \\t\\r\\n]*\\\\h.*$");

	private static final byte SPECCHAR_AUTONUMBERED_FOOTNOTE_REFERENCE = 2;

	private static final byte SPECCHAR_DRAWN_OBJECT = 8;

	private static final char UNICODECHAR_NO_BREAK_SPACE = '\u00a0';

	private static final char UNICODECHAR_NONBREAKING_HYPHEN = '\u2011';

	private static final char UNICODECHAR_ZERO_WIDTH_SPACE = '\u200b';

	private final Set<Bookmark> bookmarkStack = new LinkedHashSet<Bookmark>();

	private FontReplacer fontReplacer = new DefaultFontReplacer();

	private PicturesManager picturesManager;

	private String encoding = ENCODING_UTF8;

	private String format = FORMAT_HTML;

	/** 文档图片集 */
	PicturesTable picstab = null;

	/** added pageContainer */
	static Element pageContainer = null;

	private static class DeadFieldBoundaries {
		final int beginMark;
		final int endMark;
		final int separatorMark;

		public DeadFieldBoundaries(int beginMark, int separatorMark, int endMark) {
			this.beginMark = beginMark;
			this.separatorMark = separatorMark;
			this.endMark = endMark;
		}
	}

	private static final class Structure implements Comparable<Structure> {
		final int end;
		final int start;
		final Object structure;

		Structure(Bookmark bookmark) {
			this.start = bookmark.getStart();
			this.end = bookmark.getEnd();
			this.structure = bookmark;
		}

		Structure(DeadFieldBoundaries deadFieldBoundaries, int start, int end) {
			this.start = start;
			this.end = end;
			this.structure = deadFieldBoundaries;
		}

		Structure(Field field) {
			this.start = field.getFieldStartOffset();
			this.end = field.getFieldEndOffset();
			this.structure = field;
		}

		public int compareTo(Structure o) {
			return start < o.start ? -1 : start == o.start ? 0 : 1;
		}

		@Override
		public String toString() {
			return "Structure [" + start + "; " + end + "): "
					+ structure.toString();
		}
	}

	private static void addToStructures(List<Structure> structures,
			Structure structure) {
		for (Iterator<Structure> iterator = structures.iterator(); iterator
				.hasNext();) {
			Structure another = iterator.next();

			if (another.start <= structure.start
					&& another.end >= structure.start) {
				return;
			}

			if ((structure.start < another.start && another.start < structure.end)
					|| (structure.start < another.start && another.end <= structure.end)
					|| (structure.start <= another.start && another.end < structure.end)) {
				iterator.remove();
				continue;
			}
		}
		structures.add(structure);
	}

	private Triplet getCharacterRunTriplet(CharacterRun characterRun) {
		Triplet original = new Triplet();
		original.bold = characterRun.isBold();
		original.italic = characterRun.isItalic();
		original.fontName = characterRun.getFontName();
		Triplet updated = getFontReplacer().update(original);
		return updated;
	}

	public FontReplacer getFontReplacer() {
		return fontReplacer;
	}

	private int getNumberColumnsSpanned(int[] tableCellEdges,
			int currentEdgeIndex, TableCell tableCell) {
		int nextEdgeIndex = currentEdgeIndex;
		int colSpan = 0;
		int cellRightEdge = tableCell.getLeftEdge() + tableCell.getWidth();
		while (tableCellEdges[nextEdgeIndex] < cellRightEdge) {
			colSpan++;
			nextEdgeIndex++;
		}
		return colSpan;
	}

	private int getNumberRowsSpanned(Table table, final int[] tableCellEdges,
			int currentRowIndex, int currentColumnIndex, TableCell tableCell) {
		if (!tableCell.isFirstVerticallyMerged())
			return 1;

		final int numRows = table.numRows();

		int count = 1;
		for (int r1 = currentRowIndex + 1; r1 < numRows; r1++) {
			TableRow nextRow = table.getRow(r1);
			if (currentColumnIndex >= nextRow.numCells())
				break;

			// we need to skip row if he don't have cells at all
			boolean hasCells = false;
			int currentEdgeIndex = 0;
			for (int c = 0; c < nextRow.numCells(); c++) {
				TableCell nextTableCell = nextRow.getCell(c);
				if (!nextTableCell.isVerticallyMerged()
						|| nextTableCell.isFirstVerticallyMerged()) {
					int colSpan = getNumberColumnsSpanned(tableCellEdges,
							currentEdgeIndex, nextTableCell);
					currentEdgeIndex += colSpan;

					if (colSpan != 0) {
						hasCells = true;
						break;
					}
				} else {
					currentEdgeIndex += getNumberColumnsSpanned(tableCellEdges,
							currentEdgeIndex, nextTableCell);
				}
			}
			if (!hasCells)
				continue;

			TableCell nextCell = nextRow.getCell(currentColumnIndex);
			if (!nextCell.isVerticallyMerged()
					|| nextCell.isFirstVerticallyMerged())
				break;
			count++;
		}
		return count;
	}

	public PicturesManager getPicturesManager() {
		return picturesManager;
	}

	private boolean processCharacters(final HWPFDocument wordDocument,
			final int currentTableLevel, final Range range, final Element block) {
		if (range == null)
			return false;

		boolean haveAnyText = false;

		/*
		 * In text there can be fields, bookmarks, may be other structures (code
		 * below allows extension). Those structures can overlaps, so either we
		 * should process char-by-char (slow) or find a correct way to
		 * reconstruct the structure of range -- sergey
		 */
		List<Structure> structures = new LinkedList<Structure>();
		if (wordDocument instanceof HWPFDocument) {
			final HWPFDocument doc = (HWPFDocument) wordDocument;

			Map<Integer, List<Bookmark>> rangeBookmarks = doc.getBookmarks()
					.getBookmarksStartedBetween(range.getStartOffset(),
							range.getEndOffset());

			if (rangeBookmarks != null) {
				for (List<Bookmark> lists : rangeBookmarks.values()) {
					for (Bookmark bookmark : lists) {
						if (!bookmarkStack.contains(bookmark))
							addToStructures(structures, new Structure(bookmark));
					}
				}
			}

			int skipUntil = -1;
			for (int c = 0; c < range.numCharacterRuns(); c++) {
				CharacterRun characterRun = range.getCharacterRun(c);
				if (characterRun == null)
					throw new AssertionError();
				if (characterRun.getStartOffset() < skipUntil)
					continue;
				String text = characterRun.text();
				if (text == null || text.length() == 0
						|| text.charAt(0) != FIELD_BEGIN_MARK)
					continue;

				Field aliveField = ((HWPFDocument) wordDocument).getFields()
						.getFieldByStartOffset(FieldsDocumentPart.MAIN,
								characterRun.getStartOffset());
				if (aliveField != null) {
					addToStructures(structures, new Structure(aliveField));
				} else {
					int[] separatorEnd = tryDeadField_lookupFieldSeparatorEnd(
							wordDocument, range, c);
					if (separatorEnd != null) {
						addToStructures(structures, new Structure(
								new DeadFieldBoundaries(c, separatorEnd[0],
										separatorEnd[1]), characterRun
										.getStartOffset(), range
										.getCharacterRun(separatorEnd[1])
										.getEndOffset()));
						c = separatorEnd[1];
					}
				}
			}
		}

		structures = new ArrayList<Structure>(structures);
		Collections.sort(structures);

		int previous = range.getStartOffset();
		for (Structure structure : structures) {
			if (structure.start != previous) {
				Range subrange = new Range(previous, structure.start, range) {
					@Override
					public String toString() {
						return "BetweenStructuresSubrange " + super.toString();
					}
				};
				processCharacters(wordDocument, currentTableLevel, subrange, block);
			}

			if (structure.structure instanceof Bookmark) {
				// other bookmarks with same boundaries
				List<Bookmark> bookmarks = new LinkedList<Bookmark>();
				for (Bookmark bookmark : ((HWPFDocument) wordDocument)
						.getBookmarks().getBookmarksStartedBetween(
								structure.start, structure.start + 1).values()
						.iterator().next()) {
					if (bookmark.getStart() == structure.start
							&& bookmark.getEnd() == structure.end) {
						bookmarks.add(bookmark);
					}
				}

				bookmarkStack.addAll(bookmarks);
				try {
					int end = Math.min(range.getEndOffset(), structure.end);
					Range subrange = new Range(structure.start, end, range) {
						@Override
						public String toString() {
							return "BookmarksSubrange " + super.toString();
						}
					};

					processBookmarks(wordDocument, block, subrange,
							currentTableLevel, bookmarks);
				} finally {
					bookmarkStack.removeAll(bookmarks);
				}
			} else if (structure.structure instanceof Field) {
				Field field = (Field) structure.structure;
				processField((HWPFDocument) wordDocument, range,
						currentTableLevel, field, block);
			} else if (structure.structure instanceof DeadFieldBoundaries) {
				DeadFieldBoundaries boundaries = (DeadFieldBoundaries) structure.structure;
				processDeadField(wordDocument, block, range, currentTableLevel,
						boundaries.beginMark, boundaries.separatorMark,
						boundaries.endMark);
			} else {
				throw new UnsupportedOperationException("NYI: "
						+ structure.structure.getClass());
			}

			previous = Math.min(range.getEndOffset(), structure.end);
		}

		if (previous != range.getStartOffset()) {
			if (previous > range.getEndOffset()) {
				logger.log(POILogger.WARN, "Latest structure in ", range,
						" ended at #" + previous, " after range boundaries [",
						range.getStartOffset() + "; " + range.getEndOffset(),
						")");
				return true;
			}

			if (previous < range.getEndOffset()) {
				Range subrange = new Range(previous, range.getEndOffset(),
						range) {
					@Override
					public String toString() {
						return "AfterStructureSubrange " + super.toString();
					}
				};
				processCharacters(wordDocument, currentTableLevel, subrange,
						block);
			}
			return true;
		}

		for (int c = 0; c < range.numCharacterRuns(); c++) {
			CharacterRun characterRun = range.getCharacterRun(c);

			if (characterRun == null)
				throw new AssertionError();

			if (wordDocument instanceof HWPFDocument
					&& ((HWPFDocument) wordDocument).getPicturesTable()
							.hasPicture(characterRun)) {
				HWPFDocument newFormat = (HWPFDocument) wordDocument;
				Picture picture = newFormat.getPicturesTable().extractPicture(
						characterRun, true);

				processImage(block, characterRun.text().charAt(0) == 0x01,
						picture);
				continue;
			}

			String text = characterRun.text();
			if (text.getBytes().length == 0)
				continue;

			if (characterRun.isSpecialCharacter()) {
				if (text.charAt(0) == SPECCHAR_AUTONUMBERED_FOOTNOTE_REFERENCE
						&& (wordDocument instanceof HWPFDocument)) {
					HWPFDocument doc = (HWPFDocument) wordDocument;
					processNoteAnchor(doc, characterRun, block);
					continue;
				}
				if (text.charAt(0) == SPECCHAR_DRAWN_OBJECT
						&& (wordDocument instanceof HWPFDocument)) {
					HWPFDocument doc = (HWPFDocument) wordDocument;
					processDrawnObject(doc, characterRun, block);
					continue;
				}
				if (characterRun.isOle2()
						&& (wordDocument instanceof HWPFDocument)) {
					HWPFDocument doc = (HWPFDocument) wordDocument;
					processOle2(doc, characterRun, block);
					continue;
				}
			}

			if (text.getBytes()[0] == FIELD_BEGIN_MARK) {
				if (wordDocument instanceof HWPFDocument) {
					Field aliveField = ((HWPFDocument) wordDocument)
							.getFields().getFieldByStartOffset(
									FieldsDocumentPart.MAIN,
									characterRun.getStartOffset());
					if (aliveField != null) {
						processField(((HWPFDocument) wordDocument), range,
								currentTableLevel, aliveField, block);

						int continueAfter = aliveField.getFieldEndOffset();
						while (c < range.numCharacterRuns()
								&& range.getCharacterRun(c).getEndOffset() <= continueAfter)
							c++;

						if (c < range.numCharacterRuns())
							c--;

						continue;
					}
				}

				int skipTo = tryDeadField(wordDocument, range,
						currentTableLevel, c, block);

				if (skipTo != c) {
					c = skipTo;
					continue;
				}

				continue;
			}
			if (text.getBytes()[0] == FIELD_SEPARATOR_MARK) {
				// shall not appear without FIELD_BEGIN_MARK
				continue;
			}
			if (text.getBytes()[0] == FIELD_END_MARK) {
				// shall not appear without FIELD_BEGIN_MARK
				continue;
			}

			if (characterRun.isSpecialCharacter() || characterRun.isObj()
					|| characterRun.isOle2()) {
				continue;
			}

			if (text.endsWith("\r")
					|| (text.charAt(text.length() - 1) == BEL_MARK && currentTableLevel != Integer.MIN_VALUE))
				text = text.substring(0, text.length() - 1);

			{
				// line breaks
				StringBuilder stringBuilder = new StringBuilder();
				for (char charChar : text.toCharArray()) {
					if (charChar == 11) {
						if (stringBuilder.length() > 0) {
							outputCharacters(block, characterRun, stringBuilder
									.toString());
							stringBuilder.setLength(0);
						}
						processLineBreak(block, characterRun);
					} else if (charChar == 30) {
						// Non-breaking hyphens are stored as ASCII 30
						stringBuilder.append(UNICODECHAR_NONBREAKING_HYPHEN);
					} else if (charChar == 31) {
						// Non-required hyphens to zero-width space
						stringBuilder.append(UNICODECHAR_ZERO_WIDTH_SPACE);
					} else if (charChar >= 0x20 || charChar == 0x09
							|| charChar == 0x0A || charChar == 0x0D) {
						stringBuilder.append(charChar);
					}
				}
				if (stringBuilder.length() > 0) {
					outputCharacters(block, characterRun, stringBuilder
							.toString());
					stringBuilder.setLength(0);
				}
			}

			haveAnyText |= text.trim().length() != 0;
		}

		return haveAnyText;
	}

	private void processDeadField(HWPFDocument wordDocument,
			Element currentBlock, Range range, int currentTableLevel,
			int beginMark, int separatorMark, int endMark) {
		if (beginMark + 1 < separatorMark && separatorMark + 1 < endMark) {
			Range formulaRange = new Range(range.getCharacterRun(beginMark + 1)
					.getStartOffset(), range.getCharacterRun(separatorMark - 1)
					.getEndOffset(), range) {
				@Override
				public String toString() {
					return "Dead field formula subrange: " + super.toString();
				}
			};
			Range valueRange = new Range(range.getCharacterRun(
					separatorMark + 1).getStartOffset(), range.getCharacterRun(
					endMark - 1).getEndOffset(), range) {
				@Override
				public String toString() {
					return "Dead field value subrange: " + super.toString();
				}
			};
			String formula = formulaRange.text();
			final Matcher matcher = PATTERN_HYPERLINK_LOCAL.matcher(formula);
			if (matcher.matches()) {
				String localref = matcher.group(1);
				processPageref(wordDocument, currentBlock, valueRange,
						currentTableLevel, localref);
				return;
			}
		}

		StringBuilder debug = new StringBuilder("Unsupported field type: \n");
		for (int i = beginMark; i <= endMark; i++) {
			debug.append("\t");
			debug.append(range.getCharacterRun(i));
			debug.append("\n");
		}
		logger.log(POILogger.WARN, debug);

		Range deadFieldValueSubrage = new Range(range.getCharacterRun(
				separatorMark).getStartOffset() + 1, range.getCharacterRun(
				endMark).getStartOffset(), range) {
			@Override
			public String toString() {
				return "DeadFieldValueSubrange (" + super.toString() + ")";
			}
		};

		// just output field value
		if (separatorMark + 1 < endMark)
			processCharacters(wordDocument, currentTableLevel,
					deadFieldValueSubrage, currentBlock);

		return;
	}

	private void processDrawnObject(HWPFDocument doc,
			CharacterRun characterRun, Element block) {
		if (picturesManager == null)
			return;

		OfficeDrawing officeDrawing = doc.getOfficeDrawingsMain()
				.getOfficeDrawingAt(characterRun.getStartOffset());
		if (officeDrawing == null) {
			logger.log(POILogger.WARN, "Characters #" + characterRun
					+ " references missing drawn object");
			return;
		}

		byte[] pictureData = officeDrawing.getPictureData();
		if (pictureData == null)
			// usual shape?
			return;

		float width = (officeDrawing.getRectangleRight() - officeDrawing
				.getRectangleLeft())
				/ AbstractWordUtils.TWIPS_PER_INCH;
		float height = (officeDrawing.getRectangleBottom() - officeDrawing
				.getRectangleTop())
				/ AbstractWordUtils.TWIPS_PER_INCH;

		final PictureType type = PictureType.findMatchingType(pictureData);
		String path = picturesManager.savePicture(pictureData, type,
						"s" + characterRun.getStartOffset() + "." + type,
						width, height);

		processDrawnObject(doc, characterRun, officeDrawing, path, block);
	}

	private void processField(HWPFDocument wordDocument, Range parentRange,
			int currentTableLevel, Field field, Element currentBlock) {
		switch (field.getType()) {
		case 37: // page reference
		{
			final Range firstSubrange = field.firstSubrange(parentRange);
			if (firstSubrange != null) {
				String formula = firstSubrange.text();
				Matcher matcher = PATTERN_PAGEREF.matcher(formula);
				if (matcher.find()) {
					String pageref = matcher.group(1);
					processPageref(wordDocument, currentBlock, field
							.secondSubrange(parentRange), currentTableLevel,
							pageref);
					return;
				}
			}
			break;
		}
		case 58: // Embedded Object
		{
			if (!field.hasSeparator()) {
				logger.log(POILogger.WARN, parentRange + " contains " + field
						+ " with 'Embedded Object' but without separator mark");
				return;
			}

			CharacterRun separator = field
					.getMarkSeparatorCharacterRun(parentRange);

			if (separator.isOle2()) {
				// the only supported so far
				boolean processed = processOle2(wordDocument, separator,
						currentBlock);

				// if we didn't output OLE - output field value
				if (!processed) {
					processCharacters(wordDocument, currentTableLevel, field
							.secondSubrange(parentRange), currentBlock);
				}

				return;
			}

			break;
		}
		case 88: // hyperlink
		{
			final Range firstSubrange = field.firstSubrange(parentRange);
			if (firstSubrange != null) {
				String formula = firstSubrange.text();
				Matcher matcher = PATTERN_HYPERLINK_EXTERNAL.matcher(formula);
				if (matcher.matches()) {
					String hyperlink = matcher.group(1);
					processHyperlink(wordDocument, currentBlock, field
							.secondSubrange(parentRange), currentTableLevel,
							hyperlink);
					return;
				}
				matcher.usePattern(PATTERN_HYPERLINK_LOCAL);
				if (matcher.matches()) {
					String hyperlink = matcher.group(1);
					Range textRange = null;
					String text = matcher.group(2);
					if (text != null && text.isEmpty()) {
						textRange = new Range(firstSubrange.getStartOffset()
								+ matcher.start(2), firstSubrange
								.getStartOffset()
								+ matcher.end(2), firstSubrange) {
							@Override
							public String toString() {
								return "Local hyperlink text";
							}
						};
					}
					processPageref(wordDocument, currentBlock, textRange,
							currentTableLevel, hyperlink);
					return;
				}
			}
			break;
		}
		}

		logger.log(POILogger.WARN, parentRange + " contains " + field
				+ " with unsupported type or format");
		processCharacters(wordDocument, currentTableLevel, field
				.secondSubrange(parentRange), currentBlock);
	}

	private void processImage(Element currentBlock, boolean inlined,
			Picture picture) {
		if (picturesManager != null) {
			final int aspectRatioX = picture.getHorizontalScalingFactor();
			final int aspectRatioY = picture.getVerticalScalingFactor();

			final float imageWidth = aspectRatioX > 0 ? picture.getDxaGoal()
					* aspectRatioX / 1000 / AbstractWordUtils.TWIPS_PER_INCH
					: picture.getDxaGoal() / AbstractWordUtils.TWIPS_PER_INCH;
			final float imageHeight = aspectRatioY > 0 ? picture.getDyaGoal()
					* aspectRatioY / 1000 / AbstractWordUtils.TWIPS_PER_INCH
					: picture.getDyaGoal() / AbstractWordUtils.TWIPS_PER_INCH;

			String url = picturesManager.savePicture(picture.getContent(), picture
					.suggestPictureType(), picture.suggestFullFileName(),
					imageWidth, imageHeight);

			if (url != null && !url.isEmpty()) {
				processImage(currentBlock, inlined, picture, url);
				return;
			}
		}

		processImageWithoutPicturesManager(currentBlock, inlined, picture);
	}

	private void processNoteAnchor(HWPFDocument doc,
			CharacterRun characterRun, final Element block) {
		{
			Notes footnotes = doc.getFootnotes();
			int noteIndex = footnotes.getNoteIndexByAnchorPosition(characterRun
					.getStartOffset());
			if (noteIndex != -1) {
				Range footnoteRange = doc.getFootnoteRange();
				int rangeStartOffset = footnoteRange.getStartOffset();
				int noteTextStartOffset = footnotes
						.getNoteTextStartOffset(noteIndex);
				int noteTextEndOffset = footnotes
						.getNoteTextEndOffset(noteIndex);

				Range noteTextRange = new Range(rangeStartOffset
						+ noteTextStartOffset, rangeStartOffset
						+ noteTextEndOffset, doc);

				processFootnoteAutonumbered(doc, noteIndex, block,
						noteTextRange);
				return;
			}
		}
		{
			Notes endnotes = doc.getEndnotes();
			int noteIndex = endnotes.getNoteIndexByAnchorPosition(characterRun
					.getStartOffset());
			if (noteIndex != -1) {
				Range endnoteRange = doc.getEndnoteRange();
				int rangeStartOffset = endnoteRange.getStartOffset();
				int noteTextStartOffset = endnotes
						.getNoteTextStartOffset(noteIndex);
				int noteTextEndOffset = endnotes
						.getNoteTextEndOffset(noteIndex);

				Range noteTextRange = new Range(rangeStartOffset
						+ noteTextStartOffset, rangeStartOffset
						+ noteTextEndOffset, doc);

				processEndnoteAutonumbered(doc, noteIndex, block, noteTextRange);
				return;
			}
		}
	}

	private boolean processOle2(HWPFDocument doc, CharacterRun characterRun,
			Element block) {
		Entry entry = doc.getObjectsPool().getObjectById(
				"_" + characterRun.getPicOffset());
		if (entry == null) {
			logger.log(POILogger.WARN, "Referenced OLE2 object '", Integer
					.valueOf(characterRun.getPicOffset()),
					"' not found in ObjectPool");
			return false;
		}

		try {
			return processOle2(doc, block, entry);
		} catch (Exception exc) {
			logger.log(POILogger.WARN,
					"Unable to convert internal OLE2 object '", Integer
							.valueOf(characterRun.getPicOffset()), "': ", exc,
					exc);
			return false;
		}
	}

	private boolean processOle2(HWPFDocument wordDocument, Element block,
			Entry entry) throws Exception {
		return false;
	}

	public void setFontReplacer(FontReplacer fontReplacer) {
		this.fontReplacer = fontReplacer;
	}

	public void setPicturesManager(PicturesManager fileManager) {
		this.picturesManager = fileManager;
	}

	private int tryDeadField(HWPFDocument wordDocument, Range range,
			int currentTableLevel, int beginMark, Element currentBlock) {
		int[] separatorEnd = tryDeadField_lookupFieldSeparatorEnd(wordDocument,
				range, beginMark);
		if (separatorEnd == null)
			return beginMark;

		processDeadField(wordDocument, currentBlock, range, currentTableLevel,
				beginMark, separatorEnd[0], separatorEnd[1]);

		return separatorEnd[1];
	}

	private int[] tryDeadField_lookupFieldSeparatorEnd(
			HWPFDocument wordDocument, Range range, int beginMark) {
		int separatorMark = -1;
		int endMark = -1;
		for (int c = beginMark + 1; c < range.numCharacterRuns(); c++) {
			CharacterRun characterRun = range.getCharacterRun(c);

			String text = characterRun.text();
			if (text.getBytes().length == 0)
				continue;

			final byte firstByte = text.getBytes()[0];
			if (firstByte == FIELD_BEGIN_MARK) {
				int[] nested = tryDeadField_lookupFieldSeparatorEnd(
						wordDocument, range, c);
				if (nested != null) {
					c = nested[1];
				}
				continue;
			}

			if (firstByte == FIELD_SEPARATOR_MARK) {
				if (separatorMark != -1) {
					return null;
				}

				separatorMark = c;
				continue;
			}

			if (text.getBytes()[0] == FIELD_END_MARK) {
				if (endMark != -1) {
					return null;
				}

				endMark = c;
				break;
			}
		}

		if (separatorMark == -1 || endMark == -1) {
			return null;
		}

		return new int[] {separatorMark, endMark};
	}

	public static byte[] getFormatBytes(InputStream is,
			PicturesManager pm, String encoding, String format) throws Exception {
		if (is == null) {
			throw new IllegalArgumentException("InputStream is null. >>!quit");
		}

		try {
			DocConverter converter = new DocConverter(pm, encoding, format);
			converter.processDocument(is);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			converter.transform(baos);
			return baos.toByteArray();
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}

	public static void convert(String docPath, String outputPath,
			PicturesManager pm, String encoding, String format) throws Exception {
		FileInputStream fis = new FileInputStream(docPath);
		FileOutputStream fos = new FileOutputStream(outputPath);
		try {
			DocConverter converter = new DocConverter(pm, encoding, format);
			converter.processDocument(fis);
			converter.transform(fos);
		} finally {
			if (fos != null) {
				fos.close();
			}
			if (fis != null) {
				fis.close();
			}
		}
	}

	/**
     * 获取带有格式的文本
	 * @param is
     * @param pictureManager
     * @param encoding
     * @param format
     * @return
     * @throws Exception
	 */
	public static String getFormatContent(InputStream is,
										  PicturesManager pictureManager, String encoding, String format) throws Exception {
		return new String(getFormatBytes(is, pictureManager, encoding, format));
	}

	/**
	 * 获取纯文本，可用于统计字符数
	 * @param is
	 * @param pictureManager
	 * @param encoding
	 * @param format
	 * @param lineFeed 文本段落是否有换行
	 * @return
	 * @throws Exception
	 */
	public static String getFormatSingleContent(InputStream is,
												PicturesManager pictureManager, String encoding, String format, boolean lineFeed) throws Exception {
		return HtmlUtil.removeHtmlOfTxt(getFormatContent(is, pictureManager, encoding, format), lineFeed);
	}

	private void processDocument(InputStream is) throws IOException {
		final HWPFDocument wordDocument = new HWPFDocument(is);
		if (wordDocument.getPicturesTable().getAllPictures().size() > 0) {
			this.picstab = wordDocument.getPicturesTable();
		}
		try {
			final SummaryInformation summaryInformation = wordDocument.getSummaryInformation();

			if (summaryInformation != null) {
				processDocumentInformation(summaryInformation);
			}
		} catch (Exception exc) {
			logger.log(POILogger.WARN,
					"Unable to process document summary information:", exc, exc);
		}

		final Range docRange = wordDocument.getRange();

		if (docRange.numSections() == 1) {
			processSingleSection(wordDocument, docRange.getSection(0));
			afterProcess();
			return;
		}

		processDocumentPart(wordDocument, docRange);
		afterProcess();
	}

	private void transform(OutputStream os) throws Exception {
		DOMSource domSource = new DOMSource(htmlDocumentFacade.getDocument());
		StreamResult streamResult = new StreamResult(os);

		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer serializer = tf.newTransformer();
		serializer.setOutputProperty(OutputKeys.ENCODING, encoding);
		serializer.setOutputProperty(OutputKeys.INDENT, "yes");
		serializer.setOutputProperty(OutputKeys.METHOD, format);
		serializer.transform(domSource, streamResult);
	}

	/**
	 * Holds properties values, applied to current <tt>p</tt> element. Those
	 * properties shall not be doubled in children <tt>span</tt> elements.
	 */
	private static class BlockProperies {
		final String pFontName;
		final int pFontSize;

		public BlockProperies(String pFontName, int pFontSize) {
			this.pFontName = pFontName;
			this.pFontSize = pFontSize;
		}
	}

	private static final POILogger logger = POILogFactory
			.getLogger(DocConverter.class);

	private static String getSectionStyle(Section section) {
		float leftMargin = section.getMarginLeft() / TWIPS_PER_INCH;
		float rightMargin = section.getMarginRight() / TWIPS_PER_INCH;
		float topMargin = section.getMarginTop() / TWIPS_PER_INCH;
		float bottomMargin = section.getMarginBottom() / TWIPS_PER_INCH;

		String style = "margin: " + topMargin + "in " + rightMargin + "in "
				+ bottomMargin + "in " + leftMargin + "in;";

		if (section.getNumColumns() > 1) {
			style += "column-count: " + (section.getNumColumns()) + ";";
			if (section.isColumnsEvenlySpaced()) {
				float distance = section.getDistanceBetweenColumns()
						/ TWIPS_PER_INCH;
				style += "column-gap: " + distance + "in;";
			} else {
				style += "column-gap: 0.25in;";
			}
		}
		return style;
	}

	private final Stack<BlockProperies> blocksProperies = new Stack<BlockProperies>();

	private final HtmlDocumentFacade htmlDocumentFacade;

	private Element notes = null;

	public DocConverter(PicturesManager pm, String encoding,
			String format) throws ParserConfigurationException {
		this.htmlDocumentFacade = new HtmlDocumentFacade(DocumentBuilderFactory
				.newInstance().newDocumentBuilder().newDocument());

		String bodyStyle = "background-color:white";
		htmlDocumentFacade.addStyleClass(htmlDocumentFacade.getBody(), "body", bodyStyle);

		Document document = htmlDocumentFacade.getDocument();
		Element window = document.createElement("div");
		Element center = document.createElement("center");
		center.appendChild(window);
		StringBuilder windowStyle = new StringBuilder();
		windowStyle.append("width:800px;margin:0 auto;padding:14px;text-align:left;");
		windowStyle.append("border:1px solid #7F9DB9;border-radius:4px;");
		windowStyle.append("box-shadow:0 0 8px rgba(0, 0, 0, .5);");
		htmlDocumentFacade.addStyleClass(window, "window", windowStyle.toString());
		htmlDocumentFacade.getBody().appendChild(center);
		pageContainer = window;

		this.picturesManager = pm;
		this.encoding = encoding;
		this.format = format;
	}

	/**
	 * Special actions that need to be called after processing complete, like
	 * updating stylesheets or building document notes list. Usually they are
	 * called once, but it's okay to call them several times.
	 */
	private void afterProcess() {
		if (notes != null)
			htmlDocumentFacade.getBody().appendChild(notes);

		htmlDocumentFacade.updateStylesheet();
	}

	private void outputCharacters(Element pElement,
			CharacterRun characterRun, String text) {
		Element span = htmlDocumentFacade.getDocument().createElement("span");
		pElement.appendChild(span);

		StringBuilder style = new StringBuilder();
		BlockProperies blockProperies = this.blocksProperies.peek();
		Triplet triplet = getCharacterRunTriplet(characterRun);

		if (triplet.fontName != null && !triplet.fontName.isEmpty()
				&& !triplet.fontName.equals(blockProperies.pFontName)) {
			style.append("font-family:" + triplet.fontName + ";");
		}
		if (characterRun.getFontSize() / 2 != blockProperies.pFontSize) {
			style.append("font-size:" + characterRun.getFontSize() / 2 + "pt;");
		}
		if (triplet.bold) {
			style.append("font-weight:bold;");
		}
		if (triplet.italic) {
			style.append("font-style:italic;");
		}

		WordToHtmlUtils.addCharactersProperties(characterRun, style);
		if (style.length() != 0){
			//htmlDocumentFacade.addStyleClass(span, "s", style.toString());
			span.setAttribute("style", style.toString());
		}


		Text textNode = htmlDocumentFacade.createText(text);
		span.appendChild(textNode);
	}

	/**
	 * Wrap range into bookmark(s) and process it. All bookmarks have starts
	 * equal to range start and ends equal to range end. Usually it's only one
	 * bookmark.
	 */
	private void processBookmarks(HWPFDocument wordDocument,
			Element currentBlock, Range range, int currentTableLevel,
			List<Bookmark> rangeBookmarks) {
		Element parent = currentBlock;
		for (Bookmark bookmark : rangeBookmarks) {
			Element bookmarkElement = htmlDocumentFacade
					.createBookmark(bookmark.getName());
			parent.appendChild(bookmarkElement);
			parent = bookmarkElement;
		}

		if (range != null)
			processCharacters(wordDocument, currentTableLevel, range, parent);
	}

	private void processDocumentInformation(
			SummaryInformation summaryInformation) {
		String title = summaryInformation.getTitle();
		if (title != null && !title.isEmpty())
			htmlDocumentFacade.setTitle(title);

		String author = summaryInformation.getAuthor();
		if (author != null && !author.isEmpty())
			htmlDocumentFacade.addAuthor(title);

		String keyword = summaryInformation.getKeywords();
		if (keyword != null && !keyword.isEmpty())
			htmlDocumentFacade.addKeywords(keyword);

		String comments = summaryInformation.getComments();
		if (comments != null && !comments.isEmpty())
			htmlDocumentFacade.addDescription(comments);
	}

	// 分range处理
	public void processDocumentPart(HWPFDocument wordDocument, Range range) {
		for (int s = 0; s < range.numSections(); s++) {
			processSection(wordDocument, range.getSection(s), s);
		}
		afterProcess();
	}

	private void processDrawnObject(HWPFDocument doc,
			CharacterRun characterRun, OfficeDrawing officeDrawing,
			String path, Element block) {
		Element img = htmlDocumentFacade.createImage(path);
		block.appendChild(img);
	}

	private void processEndnoteAutonumbered(HWPFDocument wordDocument,
			int noteIndex, Element block, Range endnoteTextRange) {
		processNoteAutonumbered(wordDocument, "end", noteIndex, block,
				endnoteTextRange);
	}

	private void processFootnoteAutonumbered(HWPFDocument wordDocument,
			int noteIndex, Element block, Range footnoteTextRange) {
		processNoteAutonumbered(wordDocument, "foot", noteIndex, block,
				footnoteTextRange);
	}

	private void processHyperlink(HWPFDocument wordDocument,
			Element currentBlock, Range textRange, int currentTableLevel,
			String hyperlink) {
		Element basicLink = htmlDocumentFacade.createHyperlink(hyperlink);
		currentBlock.appendChild(basicLink);

		if (textRange != null)
			processCharacters(wordDocument, currentTableLevel, textRange,
					basicLink);
	}

	@SuppressWarnings("deprecation")
	private void processImage(Element currentBlock, boolean inlined,
			Picture picture, String imageSourcePath) {
		final int aspectRatioX = picture.getHorizontalScalingFactor();
		final int aspectRatioY = picture.getVerticalScalingFactor();

		StringBuilder style = new StringBuilder();

		final float imageWidth;
		final float imageHeight;

		final float cropTop;
		final float cropBottom;
		final float cropLeft;
		final float cropRight;

		if (aspectRatioX > 0) {
			imageWidth = picture.getDxaGoal() * aspectRatioX / 1000
					/ TWIPS_PER_INCH;
			cropRight = picture.getDxaCropRight() * aspectRatioX / 1000
					/ TWIPS_PER_INCH;
			cropLeft = picture.getDxaCropLeft() * aspectRatioX / 1000
					/ TWIPS_PER_INCH;
		} else {
			imageWidth = picture.getDxaGoal() / TWIPS_PER_INCH;
			cropRight = picture.getDxaCropRight() / TWIPS_PER_INCH;
			cropLeft = picture.getDxaCropLeft() / TWIPS_PER_INCH;
		}

		if (aspectRatioY > 0) {
			imageHeight = picture.getDyaGoal() * aspectRatioY / 1000
					/ TWIPS_PER_INCH;
			cropTop = picture.getDyaCropTop() * aspectRatioY / 1000
					/ TWIPS_PER_INCH;
			cropBottom = picture.getDyaCropBottom() * aspectRatioY / 1000
					/ TWIPS_PER_INCH;
		} else {
			imageHeight = picture.getDyaGoal() / TWIPS_PER_INCH;
			cropTop = picture.getDyaCropTop() / TWIPS_PER_INCH;
			cropBottom = picture.getDyaCropBottom() / TWIPS_PER_INCH;
		}

		Element root;
		if (cropTop != 0 || cropRight != 0 || cropBottom != 0 || cropLeft != 0) {
			float visibleWidth = Math.max(0, imageWidth - cropLeft - cropRight);
			float visibleHeight = Math.max(0, imageHeight - cropTop
					- cropBottom);

			root = htmlDocumentFacade.createBlock();
			/*htmlDocumentFacade.addStyleClass(root, "d",
					"vertical-align:text-bottom;width:" + visibleWidth
							+ "in;height:" + visibleHeight + "in;");
*/			root.setAttribute("style",
		"vertical-align:text-bottom;width:" + visibleWidth
		+ "in;height:" + visibleHeight + "in;");

			// complex
			Element inner = htmlDocumentFacade.createBlock();
			/*htmlDocumentFacade.addStyleClass(inner, "d",
					"position:relative;width:" + visibleWidth + "in;height:"
							+ visibleHeight + "in;overflow:hidden;");
			*/
			inner.setAttribute("style",
					"position:relative;width:" + visibleWidth + "in;height:"
							+ visibleHeight + "in;overflow:hidden;");

			root.appendChild(inner);

			Element image = htmlDocumentFacade.createImage(imageSourcePath);
			/*htmlDocumentFacade.addStyleClass(image, "i",
					"position:absolute;left:-" + cropLeft + ";top:-" + cropTop
							+ ";width:" + imageWidth + "in;height:"
							+ imageHeight + "in;");*/
			image.setAttribute("style",
					"position:absolute;left:-" + cropLeft + ";top:-" + cropTop
							+ ";width:" + imageWidth + "in;height:"
							+ imageHeight + "in;");
			inner.appendChild(image);

			style.append("overflow:hidden;");
		} else {
			root = htmlDocumentFacade.createImage(imageSourcePath);
			root.setAttribute("style", "width:" + imageWidth + "in;height:"
					+ imageHeight + "in;vertical-align:text-bottom;");
		}

		currentBlock.appendChild(root);
	}

	private void processImageWithoutPicturesManager(Element currentBlock,
			boolean inlined, Picture picture) {
		currentBlock.appendChild(htmlDocumentFacade.getDocument()
				.createComment(
						"Image link to '" + picture.suggestFullFileName()
								+ "' can be here"));
	}

	private void processLineBreak(Element block, CharacterRun characterRun) {
		block.appendChild(htmlDocumentFacade.createLineBreak());
	}

	private void processNoteAutonumbered(HWPFDocument doc, String type,
			int noteIndex, Element block, Range noteTextRange) {
		final String textIndex = String.valueOf(noteIndex + 1);
		final String textIndexClass = htmlDocumentFacade.getOrCreateCssClass(
				"a", "vertical-align:super;font-size:smaller;");
		final String forwardNoteLink = type + "note_" + textIndex;
		final String backwardNoteLink = type + "note_back_" + textIndex;

		Element anchor = htmlDocumentFacade.createHyperlink("#"
				+ forwardNoteLink);
		anchor.setAttribute("name", backwardNoteLink);
		anchor
				.setAttribute("class", textIndexClass + " " + type
						+ "noteanchor");
		anchor.setTextContent(textIndex);
		block.appendChild(anchor);

		if (notes == null) {
			notes = htmlDocumentFacade.createBlock();
			notes.setAttribute("class", "notes");
		}

		Element note = htmlDocumentFacade.createBlock();
		note.setAttribute("class", type + "note");
		notes.appendChild(note);

		Element bookmark = htmlDocumentFacade.createBookmark(forwardNoteLink);
		bookmark.setAttribute("href", "#" + backwardNoteLink);
		bookmark.setTextContent(textIndex);
		bookmark.setAttribute("class", textIndexClass + " " + type + "noteindex");
		note.appendChild(bookmark);
		note.appendChild(htmlDocumentFacade.createText(" "));

		Element span = htmlDocumentFacade.getDocument().createElement("span");
		span.setAttribute("class", type + "notetext");
		note.appendChild(span);

		this.blocksProperies.add(new BlockProperies("", -1));
		try {
			processCharacters(doc, Integer.MIN_VALUE, noteTextRange, span);
		} finally {
			this.blocksProperies.pop();
		}
	}

	private void processPageBreak(HWPFDocument wordDocument, Element flow) {
		flow.appendChild(htmlDocumentFacade.createLineBreak());
	}

	private void processPageref(HWPFDocument hwpfDocument,
			Element currentBlock, Range textRange, int currentTableLevel,
			String pageref) {
		Element basicLink = htmlDocumentFacade.createHyperlink("#" + pageref);
		currentBlock.appendChild(basicLink);

		if (textRange != null)
			processCharacters(hwpfDocument, currentTableLevel, textRange,
					basicLink);
	}

	private void processParagraph(HWPFDocument hwpfDocument,
			Element parentElement, int currentTableLevel, Paragraph paragraph,
			String bulletText) {
		final Element pElement = htmlDocumentFacade.createParagraph();
		parentElement.appendChild(pElement);
		if (itemSymbol) {
			Element span = htmlDocumentFacade.getDocument().createElement(
					"span");
			StringBuilder itemCss = new StringBuilder();
			itemCss.append("font-size:12.0pt;line-height:150%;font-family:Wingdings;");
			itemCss.append("mso-ascii-font-family:Wingdings;mso-hide:none;");
			itemCss.append("mso-ansi-language:EN-US;mso-fareast-language:ZH-CN;");
			itemCss.append("font-weight:normal;mso-bidi-font-weight:normal;font-style:normal;");
			itemCss.append("mso-bidi-font-style:normal;text-underline:windowtext none;");
			itemCss.append("text-decoration:none;background:transparent");
			//htmlDocumentFacade.addStyleClass(span, "itemSymbol", itemCss.toString());
			span.setAttribute("style",itemCss.toString());
			span.setTextContent("Ø");
			pElement.appendChild(span);
			itemSymbol = false;
		}

		StringBuilder style = new StringBuilder();
		WordToHtmlUtils.addParagraphProperties(paragraph, style);

		final int charRuns = paragraph.numCharacterRuns();
		if (charRuns == 0) {
			return;
		}

		{
			final String pFontName;
			final int pFontSize;
			final CharacterRun characterRun = paragraph.getCharacterRun(0);
			if ("".equals(paragraph.text().trim())) {
				pElement.setTextContent(String.valueOf(UNICODECHAR_NO_BREAK_SPACE));
			}
			if (characterRun != null) {
				Triplet triplet = getCharacterRunTriplet(characterRun);
				pFontSize = characterRun.getFontSize() / 2;
				pFontName = triplet.fontName;
				WordToHtmlUtils.addFontFamily(pFontName, style);
				WordToHtmlUtils.addFontSize(pFontSize, style);
			} else {
				pFontSize = -1;
				pFontName = EMPTY;
			}
			blocksProperies.push(new BlockProperies(pFontName, pFontSize));
		}
		try {
			if (bulletText != null && !bulletText.isEmpty()) {
				if (bulletText.endsWith("\t")) {
					/*
					 * We don't know how to handle all cases in HTML, but at
					 * least simplest case shall be handled
					 */
					final float defaultTab = TWIPS_PER_INCH / 2;
					float firstLinePosition = paragraph.getIndentFromLeft()
							+ paragraph.getFirstLineIndent() + 20; // char have

					float nextStop = (float) (Math.ceil(firstLinePosition
							/ defaultTab) * defaultTab);

					final float spanMinWidth = nextStop - firstLinePosition;

					Element span = htmlDocumentFacade.getDocument()
							.createElement("span");
					//htmlDocumentFacade.addStyleClass(span, "s","display: inline-block; text-indent: 0; min-width: "+ (spanMinWidth / TWIPS_PER_INCH) + "in;");
					span.setAttribute("style","display: inline-block; text-indent: 0; min-width: "+ (spanMinWidth / TWIPS_PER_INCH) + "in;");

					pElement.appendChild(span);

					Text textNode = htmlDocumentFacade.createText(bulletText
							.substring(0, bulletText.length() - 1)
							+ UNICODECHAR_ZERO_WIDTH_SPACE
							+ UNICODECHAR_NO_BREAK_SPACE);
					span.appendChild(textNode);
				} else {
					Text textNode = htmlDocumentFacade.createText(bulletText
							.substring(0, bulletText.length() - 1));
					pElement.appendChild(textNode);
				}
			}

			processCharacters(hwpfDocument, currentTableLevel, paragraph,
					pElement);
		} finally {
			blocksProperies.pop();
		}

		if (style.length() > 0){
			//htmlDocumentFacade.addStyleClass(pElement, "p", style.toString());
			pElement.setAttribute("style", style.toString());
		}


		compactChildNodesR(pElement, "span");
		return;
	}

	static void compactChildNodesR(Element parentElement, String childTagName) {
		NodeList childNodes = parentElement.getChildNodes();
		for (int i = 0; i < childNodes.getLength() - 1; i++) {
			Node child1 = childNodes.item(i);
			Node child2 = childNodes.item(i + 1);
			if (!canBeMerged(child1, child2, childTagName))
				continue;

			// merge
			while (child2.getChildNodes().getLength() > 0)
				child1.appendChild(child2.getFirstChild());
			child2.getParentNode().removeChild(child2);
			i--;
		}

		childNodes = parentElement.getChildNodes();
		for (int i = 0; i < childNodes.getLength() - 1; i++) {
			Node child = childNodes.item(i);
			if (child instanceof Element) {
				compactChildNodesR((Element) child, childTagName);
			}
		}
	}

	static boolean canBeMerged(Node node1, Node node2, String requiredTagName) {
		if (node1.getNodeType() != Node.ELEMENT_NODE
				|| node2.getNodeType() != Node.ELEMENT_NODE)
			return false;

		Element element1 = (Element) node1;
		Element element2 = (Element) node2;

		if (!equals(requiredTagName, element1.getTagName())
				|| !equals(requiredTagName, element2.getTagName()))
			return false;

		NamedNodeMap attributes1 = element1.getAttributes();
		NamedNodeMap attributes2 = element2.getAttributes();

		if (attributes1.getLength() != attributes2.getLength())
			return false;

		for (int i = 0; i < attributes1.getLength(); i++) {
			final Attr attr1 = (Attr) attributes1.item(i);
			final Attr attr2;
			String namespaceURI = attr1.getNamespaceURI();
			if (namespaceURI != null && !namespaceURI.isEmpty())
				attr2 = (Attr) attributes2.getNamedItemNS(attr1
						.getNamespaceURI(), attr1.getLocalName());
			else
				attr2 = (Attr) attributes2.getNamedItem(attr1.getName());

			if (attr2 == null
					|| !equals(attr1.getTextContent(), attr2.getTextContent()))
				return false;
		}

		return true;
	}

	private static boolean equals(String str1, String str2) {
		return str1 == null ? str2 == null : str1.equals(str2);
	}

	private void processSection(HWPFDocument wordDocument, Section section,
			int sectionCounter) {
		Element div = htmlDocumentFacade.createBlock();
		//htmlDocumentFacade.addStyleClass(div, "d", getSectionStyle(section));
		div.setAttribute("style", getSectionStyle(section));

		pageContainer.appendChild(div);

		processParagraphes(wordDocument, div, section, Integer.MIN_VALUE);
	}

	private void processSingleSection(HWPFDocument wordDocument,
			Section section) {
		processParagraphes(wordDocument, pageContainer, section,
				Integer.MIN_VALUE);
	}

	/** 解析table */
	private void processTable(HWPFDocument hwpfDocument, Element flow,
			Table table) {
		Element tableHeader = htmlDocumentFacade.createTableHeader();
		Element tableBody = htmlDocumentFacade.createTableBody();

		final int[] tableCellEdges = buildTableCellEdgesArray(table);
		final int tableRows = table.numRows();

		int maxColumns = Integer.MIN_VALUE;
		for (int r = 0; r < tableRows; r++) {
			maxColumns = Math.max(maxColumns, table.getRow(r).numCells());
		}

		for (int r = 0; r < tableRows; r++) {
			TableRow tableRow = table.getRow(r);

			Element tableRowElement = htmlDocumentFacade.createTableRow();
			StringBuilder tableRowStyle = new StringBuilder();

			WordToHtmlUtils.addTableRowProperties(tableRow, tableRowStyle);

			// index of current element in tableCellEdges[]
			int currentEdgeIndex = 0;
			final int rowCells = tableRow.numCells();
			for (int c = 0; c < rowCells; c++) {
				TableCell tableCell = tableRow.getCell(c);

				if (tableCell.isVerticallyMerged()
						&& !tableCell.isFirstVerticallyMerged()) {
					currentEdgeIndex += getNumberColumnsSpanned(tableCellEdges,
							currentEdgeIndex, tableCell);
					continue;
				}

				Element tableCellElement;
				if (tableRow.isTableHeader()) {
					tableCellElement = htmlDocumentFacade
							.createTableHeaderCell();
				} else {
					tableCellElement = htmlDocumentFacade.createTableCell();
				}
				StringBuilder tableCellStyle = new StringBuilder();
				WordToHtmlUtils.addTableCellProperties(tableRow, tableCell,
						r == 0, r == tableRows - 1, c == 0, c == rowCells - 1,
						tableCellStyle);

				int colSpan = getNumberColumnsSpanned(tableCellEdges,
						currentEdgeIndex, tableCell);
				currentEdgeIndex += colSpan;

				if (colSpan == 0)
					continue;

				if (colSpan != 1)
					tableCellElement.setAttribute("colspan", String
							.valueOf(colSpan));

				final int rowSpan = getNumberRowsSpanned(table, tableCellEdges,
						r, c, tableCell);
				if (rowSpan > 1)
					tableCellElement.setAttribute("rowspan", String
							.valueOf(rowSpan));

				processParagraphes(hwpfDocument, tableCellElement, tableCell,
						table.getTableLevel());

				if (!tableCellElement.hasChildNodes()) {
					tableCellElement.appendChild(htmlDocumentFacade
							.createParagraph());
				}
				if (tableCellStyle.length() > 0){
					//htmlDocumentFacade.addStyleClass(tableCellElement,tableCellElement.getTagName(), tableCellStyle.toString());
					tableCellStyle.append("word-break: keep-all;") //for ms, moz
							.append("white-space: nowrap;");  // for wiki
					tableCellElement.setAttribute("style", tableCellStyle.toString());
				}

				tableRowElement.appendChild(tableCellElement);
			}

			if (tableRowStyle.length() > 0)
				tableRowElement.setAttribute("class", htmlDocumentFacade
						.getOrCreateCssClass("r", tableRowStyle.toString()));

			if (tableRow.isTableHeader()) {
				tableHeader.appendChild(tableRowElement);
			} else {
				tableBody.appendChild(tableRowElement);
			}
		}

		final Element tableElement = htmlDocumentFacade.createTable();
		tableElement.setAttribute("class", htmlDocumentFacade.getOrCreateCssClass(
				"t", "table-layout:fixed;border-collapse:collapse;border-spacing:0;"));
		if (tableHeader.hasChildNodes()) {
			tableElement.appendChild(tableHeader);
		}
		if (tableBody.hasChildNodes()) {
			tableElement.appendChild(tableBody);
			flow.appendChild(tableElement);
		} else {
			logger.log(POILogger.WARN, "Table without body starting at [",
					Integer.valueOf(table.getStartOffset()), "; ", Integer
							.valueOf(table.getEndOffset()), ")");
		}
	}

	/**
	 * Creates array of all possible cell edges. In HTML (and FO) cells from
	 * different rows and same column should have same width, otherwise spanning
	 * shall be used.
	 *
	 * @param table
	 *            table to build cell edges array from
	 * @return array of cell edges (including leftest one) in twips
	 */
	static int[] buildTableCellEdgesArray(Table table) {
		Set<Integer> edges = new TreeSet<Integer>();

		for (int r = 0; r < table.numRows(); r++) {
			TableRow tableRow = table.getRow(r);
			for (int c = 0; c < tableRow.numCells(); c++) {
				TableCell tableCell = tableRow.getCell(c);

				edges.add(Integer.valueOf(tableCell.getLeftEdge()));
				edges.add(Integer.valueOf(tableCell.getLeftEdge()
						+ tableCell.getWidth()));
			}
		}

		Integer[] sorted = edges.toArray(new Integer[edges.size()]);
		int[] result = new int[sorted.length];
		for (int i = 0; i < sorted.length; i++) {
			result[i] = sorted[i].intValue();
		}

		return result;
	}

	/** 加入图片 */
	private void processImage(Element parent, CharacterRun cr) {
		/*if (this.picstab != null && this.picstab.hasPicture(cr)) {
			Picture pic = picstab.extractPicture(cr, false);

			String fileName = pic.suggestFullFileName();
			try {
				Element img = htmlDocumentFacade.getDocument().createElement("img");
				String uri = fileName.concat("image").concat(File.separator)
						.concat(fileName);
				img.setAttribute("src", uri);
				if (pic.getWidth() > 600)
					img.setAttribute("style", "width: 600px;");
				Element imgBlock = htmlDocumentFacade.createBlock();
				//this.htmlDocumentFacade.addStyleClass(imgBlock, "imgs","text-align:center;");
				imgBlock.setAttribute("style", "text-align:center;");
				imgBlock.appendChild(img);
				parent.appendChild(imgBlock);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}*/
	}// 图片END

	private boolean itemSymbol = false;

	/** 处理段落. */
	private void processParagraphes(HWPFDocument wordDocument, Element flow,
			Range range, int currentTableLevel) {
		final ListTables listTables = wordDocument.getListTables();
		int currentListInfo = 0;

		final int paragraphs = range.numParagraphs();
		for (int p = 0; p < paragraphs; p++) {
			Paragraph paragraph = range.getParagraph(p);

			// 加入图片
			CharacterRun cr = paragraph.getCharacterRun(0);
			this.processImage(flow, cr);
			// table
			if (paragraph.isInTable()
					&& paragraph.getTableLevel() != currentTableLevel) {
				if (paragraph.getTableLevel() < currentTableLevel)
					throw new IllegalStateException(
							"Trying to process table cell with higher level ("
									+ paragraph.getTableLevel()
									+ ") than current table level ("
									+ currentTableLevel
									+ ") as inner table part");

				Table table = range.getTable(paragraph);
				processTable(wordDocument, flow, table);

				p += table.numParagraphs();
				p--;
				continue;
			}
			// 换页
			if (paragraph.text().equals("\u000c")) {
				processPageBreak(wordDocument, flow);
			}
			if (paragraph.getIlfo() != currentListInfo) {
				currentListInfo = paragraph.getIlfo();
			}
			// 嵌套段落
			if (currentListInfo != 0) {
				if (listTables != null) {

					final ListFormatOverride listFormatOverride = listTables
							.getOverride(paragraph.getIlfo());

					String label = getBulletText(listTables, paragraph,
							listFormatOverride.getLsid());

					if ("".equals(label)) {
						itemSymbol = true;
					}

					processParagraph(wordDocument, flow, currentTableLevel,
							paragraph, label);
				} else {
					logger.log(POILogger.WARN, "Paragraph #"
							+ paragraph.getStartOffset() + "-"
							+ paragraph.getEndOffset()
							+ " has reference to list structure #"
							+ currentListInfo
							+ ", but listTables not defined in file");

					processParagraph(wordDocument, flow, currentTableLevel,
							paragraph, EMPTY);
				}
			} else {
				processParagraph(wordDocument, flow, currentTableLevel,
						paragraph, EMPTY);
			}
		}

	}

	private static String getBulletText(ListTables listTables,
			Paragraph paragraph, int listId) {
		final ListLevel listLevel = listTables.getLevel(listId, paragraph
				.getIlvl());

		if (listLevel.getNumberText() == null)
			return "";

		StringBuffer bulletBuffer = new StringBuffer();
		char[] xst = listLevel.getNumberText().toCharArray();
		for (char element : xst) {
			if (element < 9) {
				ListLevel numLevel = listTables.getLevel(listId, element);

				int num = numLevel.getStartAt();
				bulletBuffer.append(NumberFormatter.getNumber(num, listLevel
						.getNumberFormat()));

				if (numLevel == listLevel) {
					numLevel.setStartAt(numLevel.getStartAt() + 1);
				}

			} else {
				bulletBuffer.append(element);
			}
		}

		byte follow = listLevel.getTypeOfCharFollowingTheNumber();
		switch (follow) {
		case 0:
			bulletBuffer.append("\t");
			break;
		case 1:
			bulletBuffer.append(" ");
			break;
		default:
			break;
		}

		return bulletBuffer.toString();
	}
}
