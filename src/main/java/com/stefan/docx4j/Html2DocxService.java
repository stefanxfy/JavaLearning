package com.stefan.docx4j;
import java.io.File;
import java.util.List;
import javax.xml.bind.JAXBException;
import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.DocumentSettingsPart;
import org.docx4j.openpackaging.parts.WordprocessingML.FooterPart;
import org.docx4j.openpackaging.parts.WordprocessingML.HeaderPart;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.openpackaging.parts.WordprocessingML.NumberingDefinitionsPart;
import org.docx4j.relationships.Relationship;
import org.docx4j.wml.CTCompat;
import org.docx4j.wml.FldChar;
import org.docx4j.wml.FooterReference;
import org.docx4j.wml.Ftr;
import org.docx4j.wml.Hdr;
import org.docx4j.wml.HdrFtrRef;
import org.docx4j.wml.HeaderReference;
import org.docx4j.wml.Jc;
import org.docx4j.wml.JcEnumeration;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.P;
import org.docx4j.wml.PPr;
import org.docx4j.wml.R;
import org.docx4j.wml.STFldCharType;
import org.docx4j.wml.SectPr;
import org.docx4j.wml.Text;
public class Html2DocxService {

    public static void main(String[] args) {
        Html2DocxService service = new Html2DocxService();
        String htmlPath = "C:\\Users\\faisco\\Downloads\\jumpServer\\心理咨询的基本要求.docx.html";
        File html = new File(htmlPath);
        File docx = new File(htmlPath + "123.docx");
        service.convert(html, docx);
    }
    // Factory for creating WML Object.
    private static final ObjectFactory factory = Context.getWmlObjectFactory();
    /**
     * Convert HTML to DOCX(OPENXML).
     *
     * @param html the source, a HTML file
     * @param docx the target, a DOCX file
     */
    public void convert(File html, File docx) {
        try {
            // Creates a WordprocessingMLPackage, using default page size and orientation,
            // default to A4 portrait.
            WordprocessingMLPackage pack = WordprocessingMLPackage.createPackage();
            NumberingDefinitionsPart numDefPart = new NumberingDefinitionsPart();
            pack.getMainDocumentPart().addTargetPart(numDefPart);
            numDefPart.unmarshalDefaultNumbering();
            // Convert XHTML + CSS to WordML content.
            // XHTML must be well formed XML.
            XHTMLImporterImpl importer = new XHTMLImporterImpl(pack);
            // Convert the well formed XHTML contained in file to a list of WML objects.
            List<Object> list = importer.convert(html, null);
            MainDocumentPart mainPart = pack.getMainDocumentPart();
            // Add all WML objects to MainDocumentPart.
            mainPart.getContent().addAll(list);
            // create footer
            Ftr footer = createFooterWithPageNumber();
            FooterPart footerPart = new FooterPart();
            footerPart.setPackage(pack);
            footerPart.setJaxbElement(footer);
            Relationship footerRelation = mainPart.addTargetPart(footerPart);
            FooterReference footerRef = factory.createFooterReference();
            footerRef.setId(footerRelation.getId());
            footerRef.setType(HdrFtrRef.DEFAULT);
            SectPr sectPr = pack.getDocumentModel().getSections().get(0).getSectPr();
            sectPr.getEGHdrFtrReferences().add(footerRef);
            // create header
            Hdr header = createHeader("北京****科技有限公司");
            HeaderPart headerPart = new HeaderPart();
            headerPart.setPackage(pack);
            headerPart.setJaxbElement(header);
            Relationship headerRelation = mainPart.addTargetPart(headerPart);
            HeaderReference headerRef = factory.createHeaderReference();
            headerRef.setId(headerRelation.getId());
            headerRef.setType(HdrFtrRef.DEFAULT);
            sectPr.getEGHdrFtrReferences().add(headerRef);
            // set compatibilityMode to 15
            // to avoid Word 365/2016 saying "Compatibility Mode"
            DocumentSettingsPart settingsPart = mainPart.getDocumentSettingsPart(true);
            CTCompat compat = Context.getWmlObjectFactory().createCTCompat();
            compat.setCompatSetting("compatibilityMode", "http://schemas.microsoft.com/office/word", "15");
            settingsPart.getContents().setCompat(compat);
            // save to a file
            pack.save(docx);
        } catch (Docx4JException | JAXBException e) {
            throw new HTML2DocxException("covert html to docx error.", e);
        }
    }
    // create a header
    private Hdr createHeader(String content) {
        Hdr header = factory.createHdr();
        P paragraph = factory.createP();
        PPr ppr = factory.createPPr();
        Jc jc = factory.createJc();
        jc.setVal(JcEnumeration.CENTER);
        ppr.setJc(jc);
        paragraph.setPPr(ppr);
        R run = factory.createR();
        Text text = new Text();
        text.setValue(content);
        run.getContent().add(text);
        paragraph.getContent().add(run);
        header.getContent().add(paragraph);
        return header;
    }
    // create a header with page number
    private Ftr createFooterWithPageNumber() {
        Ftr ftr = factory.createFtr();
        P paragraph = factory.createP();
        PPr ppr = factory.createPPr();
        Jc jc = factory.createJc();
        jc.setVal(JcEnumeration.RIGHT);
        ppr.setJc(jc);
        paragraph.setPPr(ppr);
        addFieldBegin(paragraph);
        addPageNumberField(paragraph);
        addFieldEnd(paragraph);
        ftr.getContent().add(paragraph);
        return ftr;
    }
    private void addPageNumberField(P paragraph) {
        Text txt = new Text();
        txt.setSpace("preserve");
        txt.setValue(" PAGE * ArabicDash ");
        R run = factory.createR();
        run.getContent().add(factory.createRInstrText(txt));
        paragraph.getContent().add(run);
    }
    private void addFieldBegin(P paragraph) {
        FldChar fldChar = factory.createFldChar();
        fldChar.setFldCharType(STFldCharType.BEGIN);
        R run = factory.createR();
        run.getContent().add(fldChar);
        paragraph.getContent().add(run);
    }
    private void addFieldEnd(P paragraph) {
        FldChar fldChar = factory.createFldChar();
        fldChar.setFldCharType(STFldCharType.END);
        R run = factory.createR();
        run.getContent().add(fldChar);
        paragraph.getContent().add(run);
    }
    public class HTML2DocxException extends RuntimeException {
        private static final long serialVersionUID = -436242367279880231L;
        public HTML2DocxException() {
            super();
        }
        public HTML2DocxException(String message, Throwable cause, boolean enableSuppression,
                                  boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }
        public HTML2DocxException(String message, Throwable cause) {
            super(message, cause);
        }
        public HTML2DocxException(String message) {
            super(message);
        }
        public HTML2DocxException(Throwable cause) {
            super(cause);
        }
    }
}
