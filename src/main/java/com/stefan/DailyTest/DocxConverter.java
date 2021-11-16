package com.stefan.DailyTest;

import fai.comm.util.Log;
import org.apache.poi.hwpf.converter.HtmlDocumentFacade;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

public class DocxConverter {
	/** represents the html page */
	private HtmlDocumentFacade htmlDocumentFacade;

	private Element window;

	private XWPFDocument docx;

	private DocxPicturesManager picturesManager = null;

	private String encoding = "UTF-8";

	private String format = "html";
	// 图片width > 600 时 设置其固定长度，-1 不设置
	private int imgWidth = 600;

	public static abstract class DocxPicturesManager {
		public abstract String savePicture(byte[] data, String imgName);
	}

	public DocxConverter(InputStream is,
			DocxPicturesManager picturesManager, String encoding, String format, int imgWidth) throws IOException,
		InvalidFormatException, ParserConfigurationException {
		this.picturesManager = picturesManager;
		this.encoding = encoding;
		this.format = format;
		this.imgWidth = imgWidth;

		Document document = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder().newDocument();

		OPCPackage container = OPCPackage.open(is);
		docx = new XWPFDocument(container);
		this.htmlDocumentFacade = new HtmlDocumentFacade(document);
		window = htmlDocumentFacade.createBlock();
		Element center = document.createElement("center");
		center.appendChild(window);
		htmlDocumentFacade.getBody().appendChild(center);
		addStyle(htmlDocumentFacade.getBody(), "background:white;");

		StringBuilder windowStyle = new StringBuilder();
		windowStyle.append("width:800px;margin:0 auto;padding:14px;text-align:left;");
		windowStyle.append("border:1px solid #7F9DB9;border-radius:4px;");
		windowStyle.append("box-shadow:0 0 8px rgba(0, 0, 0, .5);");
		htmlDocumentFacade.addStyleClass(window, "window", windowStyle.toString());

		// task paragraphs
		List<IBodyElement> elements = docx.getBodyElements();
		for (IBodyElement element : elements) {
			if (element instanceof XWPFParagraph) {
				processParagraph((XWPFParagraph) element, window);
			} else if (element instanceof XWPFTable) {
				processTable((XWPFTable) element, window);
			}
		}

		htmlDocumentFacade.updateStylesheet();
	}

	public DocxConverter(InputStream is,
						 DocxPicturesManager picturesManager, String encoding, String format) throws IOException,
			InvalidFormatException, ParserConfigurationException {
		this(is, picturesManager, encoding, format, 600);
	}

	public static byte[] getFormatBytes(InputStream is,
			DocxPicturesManager pictureManager, String encoding, String format) throws Exception {
		try {
			DocxConverter converter = new DocxConverter(is, pictureManager, encoding, format, 800);
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			converter.transform(output);
			return output.toByteArray();
		} finally {
			if (is != null) {
				is.close();
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
										DocxPicturesManager pictureManager, String encoding, String format) throws Exception {
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
									  DocxPicturesManager pictureManager, String encoding, String format, boolean lineFeed) throws Exception {
		return HtmlUtil.removeHtmlOfTxt(getFormatContent(is, pictureManager, encoding, format), lineFeed);
	}

	public static void convert(String docxPath, String outputPath,
			DocxPicturesManager pm, String encoding, String format) throws Exception {

		FileInputStream fis = new FileInputStream(docxPath);
		FileOutputStream fos = new FileOutputStream(outputPath);
		try {
			DocxConverter converter = new DocxConverter(fis, pm, encoding, format);
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

	private void processDocument(InputStream is) throws IOException, InvalidFormatException {
		// task paragraphs
		List<IBodyElement> elements = docx.getBodyElements();
		for (IBodyElement element : elements) {
			if (element instanceof XWPFParagraph) {
				processParagraph((XWPFParagraph) element, window);
			} else if (element instanceof XWPFTable) {
				processTable((XWPFTable) element, window);
			}
		}

		htmlDocumentFacade.updateStylesheet();
	}

	/**
	 * task table..
	 *
	 * @param t
	 */
	private void processTable(XWPFTable t, Element container) {
		Element p = htmlDocumentFacade.createParagraph();
		Element table = htmlDocumentFacade.createTable();

		List<XWPFTableRow> rows = t.getRows();
		for (XWPFTableRow row : rows) {
			processRow(row, table);
		}
		p.appendChild(table);
		table.setAttribute("border", "1");
		table.setAttribute("cellspacing", "0");
		table.setAttribute("cellpadding", "3");
		table.setAttribute("style", "border-collapse: collapse;");
		container.appendChild(p);
	}

	private void processRow(XWPFTableRow row, Element table) {
		Element tr = htmlDocumentFacade.createTableRow();
		List<XWPFTableCell> cells = row.getTableCells();
		for (XWPFTableCell cell : cells) {
			processCell(cell, tr);
		}
		// resolve row style.
		{
			StringBuilder sb = new StringBuilder();
			if (row.getHeight() != 0)
				sb.append("height:").append(row.getHeight() / 1440.0)
						.append("in");
			addStyle(tr, sb.toString());
		}
		table.appendChild(tr);
	}

	private void processCell(XWPFTableCell cell, Element tr) {
		Element td = htmlDocumentFacade.createTableCell();
		htmlDocumentFacade.createTableColumn();

		List<XWPFParagraph> paragraphs = cell.getParagraphs();
		for (XWPFParagraph p : paragraphs) {
			try {
				processTableParagraph(p, td);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// resolve cell styles
		StringBuilder sb = new StringBuilder();
		{
			CTTc c = cell.getCTTc();
			sb.append("width:")
					.append(c.getTcPr().getTcW().getW().doubleValue() / 1440.0)
					.append("in");
			/**
			 *  fix bug null-point
			 *
			 * expc=java.lang.NullPointerException
			 *
			 * org.apache.poi.xwpf.usermodel.XWPFTableCell.getColor(XWPFTableCell.java:212)
			 * fai.comm.util.DocxConverter.processCell(DocxConverter.java:192)
			 * fai.comm.util.DocxConverter.processRow(DocxConverter.java:159)
			 * fai.comm.util.DocxConverter.processTable(DocxConverter.java:145)
			 * fai.comm.util.DocxConverter.<init>(DocxConverter.java:95)
			 */
			try {
				if (cell.getColor() != null) {
					sb.append(";background-color:#").append(cell.getColor());
				}
			} catch (Exception e) {
				Log.logErr(e, "cell.getColor err");
			}

			// resovel text alignment
			CTJc jc = null;
			@SuppressWarnings("deprecation")
			CTP[] ctps = c.getPArray();
			if (ctps.length > 0) {
				CTPPr ctppr = c.getPArray(0).getPPr();
				if (ctppr != null) {
					jc = ctppr.getJc();
				}
			}
			if (jc != null) {
				switch (jc.getVal().intValue()) {
					case STJc.INT_RIGHT:
						sb.append(";text-align:").append("right");
						break;
					case STJc.INT_CENTER:
						sb.append(";text-align:").append("center");
						break;
				}
			}
			try {
				// if the verticalAlignment set
				XWPFTableCell.XWPFVertAlign valign = cell
						.getVerticalAlignment();
				switch (valign) {
					case BOTTOM:
						sb.append(";vertical-align:").append("bottom");
						break;
					case CENTER:
						sb.append(";vertical-align:").append("middle");
						break;
					case TOP:
						sb.append(";vertical-align:").append("top");
						break;
					default:
						sb.append(";vertical-align:").append("top");
						break;
				}
			} catch (NullPointerException ex) {
				sb.append(";vertical-align:").append("top");
			}
		}
		addStyle(td, sb.toString());
		tr.appendChild(td);
	}

	private void processTableParagraph(XWPFParagraph paragraph, Element page)
			throws IOException {
		processParagraphMulti(paragraph, page, true);
	}

	private void processParagraph(XWPFParagraph paragraph, Element page)
			throws IOException {
		processParagraphMulti(paragraph, page, false);
	}

	/**
	 * task paragraph
	 *
	 * @param paragraph
	 * @param page
	 *            use to contain the paragraph content.
	 * @param isTableParagraph
	 * @throws IOException
	 *             when icture extract error.
	 */
	private void processParagraphMulti(XWPFParagraph paragraph, Element page,
									   boolean isTableParagraph) throws IOException {

		// p.type : list , picture , title, empty
		Element ctner = null;
		if (paragraph.getText().length() == 0
				&& paragraph.getRuns().size() == 0) {
			// empty
			ctner = htmlDocumentFacade.createParagraph();
			ctner.setTextContent("\u00a0");
			page.appendChild(ctner);
			return;
		} else if (paragraph.getStyle() != null
				&& !"-1".equals(paragraph.getStyle())) {
			// XWPFStyle s = styles.getStyle(paragraph.getStyle());
			// System.out.println(s.getStyleId() + "- " + "----" +
			// paragraph.getText());
			// title & list
			String typeStr = new String(paragraph.getStyle());
			Integer type;
			try {
				// title
				type = Integer.parseInt(typeStr);
				switch (type) {
					case 1:
						ctner = createElement("h1");
						break;
					case 2:
						ctner = createElement("h2");
						break;
					case 3:
						ctner = createElement("h3");
						break;
					case 4:
						ctner = createElement("h4");
						break;
					case 5:
						ctner = createElement("h5");
						break;
					default:
						ctner = htmlDocumentFacade.createParagraph();
				}
			} catch (NumberFormatException ex) {
				// list
				ctner = createElement("li");
				if (isTableParagraph)
					ctner.setAttribute("style", "list-style:none");
				// System.out.println( "LIST--->" + paragraph.getText());
			}
		}

		// normal && picture
		// when normal case, the 'ctner' may not initialized.
		if (ctner == null) {
			if (isTableParagraph)
				ctner = htmlDocumentFacade.createBlock();
			else
				ctner = htmlDocumentFacade.createParagraph();
			// addStyle(ctner,"text-indent:2em;");
		}

		List<XWPFRun> runs = paragraph.getRuns();//此处存在tab键分段混乱
		for (XWPFRun r : runs) {
			processRun(ctner, r);
		}
		// resolve paragraph styles;
		if (paragraph.getAlignment() != ParagraphAlignment.LEFT) {
			switch (paragraph.getAlignment()) {
				case CENTER:
					addStyle(ctner, "text-align:center;");
					break;
				case RIGHT:
					addStyle(ctner, "text-align:right");
					break;
				// case DISTRIBUTE:addStyle(ctner, ""); break; //两边分布
				default:
					break;
			}
		}
		page.appendChild(ctner);

	}

	private void processRun(Element container, XWPFRun r) throws IOException {

		List<XWPFPicture> pics = r.getEmbeddedPictures();
		if (!pics.isEmpty()) {
			processImage(container, pics);
		}

		Element runCtner = null;
		switch (r.getSubscript()) {
			case SUBSCRIPT:
				runCtner = createElement("sub");
				break;
			case SUPERSCRIPT:
				runCtner = createElement("sup");
				break;
			default:
				runCtner = htmlDocumentFacade.getDocument().createElement("span");
		}

		StringBuilder sb = new StringBuilder();
		if (r.getColor() != null)
			sb.append("color:#").append(r.getColor());
		if (r.getFontSize() != -1)
			sb.append(";font-size:").append(r.getFontSize()).append("pt;").append("white-space: pre;");
		if (r.getFontFamily() != null)
			sb.append(";font-family:'").append(r.getFontFamily()).append("'");
		if (r.isBold())
			sb.append(";font-weight:").append(800);
		if (r.isItalic())
			sb.append(";font-style:").append("italic ");
		boolean getUnderLine = false;
		try {
			getUnderLine = r.getUnderline() != UnderlinePatterns.NONE;
			// 内部链式调用会报空指针异常
			// err code : return (pr != null) && (pr.isSetU()) ? UnderlinePatterns.valueOf(pr.getU().getVal().intValue()) : UnderlinePatterns.NONE;
		}catch (NullPointerException e){
		}
		if (getUnderLine) {
			switch (r.getUnderline()) {
				case DOUBLE:
					sb.append(";border-bottom:").append("4px double");
					break;
				case DOTTED:
					sb.append(";border-bottom:").append("1px dotted");
					break;
				case DASH:
					sb.append(";border-bottom:").append("1px dashed");
					break;
				default:
					sb.append(";text-decoration:").append("underline");
					break;
			}
		}

		// r.getClass().isInstance(XWPFHyperlinkRun);
		try {

			XWPFHyperlinkRun hlRun = (XWPFHyperlinkRun) r;
			XWPFHyperlink hyperlink = hlRun.getHyperlink(docx);
			Element a = htmlDocumentFacade.createHyperlink(hyperlink.getURL());
			a.setAttribute("name", hyperlink.getId());
			a.setTextContent(hlRun.getText(0));
			runCtner.appendChild(a);
			if (sb.length() != 0)
				addStyle(a, sb.toString());
		} catch (Exception ex) {
			// ex.printStackTrace();
			String spanText = r.getText(0);
			if ("span".equals(runCtner.getTagName())){ // 参考 XWPFRun.toString(); 4个空格对应一个tab键
				StringBuilder textSb = new StringBuilder();
				XmlCursor c = r.getCTR().newCursor();
				c.selectPath("./*");
				while(c.toNextSelection()) {
					XmlObject o = c.getObject();
					String tagName;
					if (o instanceof CTText) {
						tagName = o.getDomNode().getNodeName();
						if (!"w:instrText".equals(tagName)) {
							textSb.append(((CTText)o).getStringValue());
						}
					}
					if (o instanceof CTPTab) {
						textSb.append("    ");
					}
					if (o instanceof CTBr) {
					}
					if (o instanceof CTEmpty) {
						tagName = o.getDomNode().getNodeName();
						if ("w:tab".equals(tagName)) {
							textSb.append("    ");
						}
					}
				}
				c.dispose();
				runCtner.setTextContent(textSb.toString());
			}else {
				runCtner.setTextContent(spanText);
			}
			if (sb.length() != 0){
				addStyle(runCtner, sb.toString());
			}
		}

		container.appendChild(runCtner);
	}

	/**
	 * extract pictures form pics save to output & add it to the container
	 *
	 * @param output
	 * @param container
	 * @param pics
	 * @throws IOException
	 */
	private void processImage(Element wrap, List<XWPFPicture> pics)
			throws IOException {
		if (picturesManager == null) {
			return;
		}

		// Element wrapDiv = htmlDocumentFacade.createBlock();
		// wrap.setAttribute("class", "img_wrap");
		for (XWPFPicture pic : pics) {
			XWPFPictureData data = pic.getPictureData();
			// sometime data == null,so if　get data == null continue
			if(data == null){
				continue;
			}
			ByteArrayInputStream is = new ByteArrayInputStream(data.getData());
			BufferedImage image = ImageIO.read(is);
			String imgUri = picturesManager.savePicture(data.getData(), data.getFileName());
			{// add picture to html page
				Element img = htmlDocumentFacade.createImage(imgUri);
				if (!isEmpty(pic.getDescription())) {
					img.setAttribute("Title", pic.getDescription());
				}
				if (image != null && image.getWidth() > 600 && imgWidth > 0) {
					img.setAttribute("width", imgWidth + "px");
				}
				img.setAttribute("align", "center");
				wrap.appendChild(img);
			}
		}
		// container.appendChild(wrapDiv);
	}

	private void transform(OutputStream os)
			throws IOException, TransformerException {
		DOMSource domSource = new DOMSource(htmlDocumentFacade.getDocument());
		StreamResult streamResult = new StreamResult(os);

		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer serializer = tf.newTransformer();
		serializer.setOutputProperty(OutputKeys.ENCODING, encoding);
		serializer.setOutputProperty(OutputKeys.INDENT, "yes");
		serializer.setOutputProperty(OutputKeys.METHOD, format);
		serializer.setOutputProperty(OutputKeys.STANDALONE, "yes");

		serializer.transform(domSource, streamResult);
	}

	private void addStyleClass(Element element, String className, String style) {
		htmlDocumentFacade.addStyleClass(element, className, style);
	}

	/**
	 * add the given style to element directly. <tag style="..."
	 *
	 * @param element
	 * @param style
	 */
	private void addStyle(Element element, String style) {
		String exist = element.getAttribute("style");
		if (isEmpty(exist)) {
			element.setAttribute("style", style);
		} else {
			if (exist.endsWith(";"))
				element.setAttribute("style", exist.concat(style));
			else
				element.setAttribute("style", exist.concat(";").concat(style));
		}
	}

	private Element createElement(String tagName) {
		return htmlDocumentFacade.getDocument().createElement(tagName);
	}

	private boolean isEmpty(String text) {
		return text == null || text.isEmpty();
	}
}
