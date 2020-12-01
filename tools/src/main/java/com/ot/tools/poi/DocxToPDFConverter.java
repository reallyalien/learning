//package com.ot.tools.poi;
//
//import java.awt.Color;
//import java.io.*;
//
//import org.apache.poi.xwpf.converter.pdf.PdfConverter;
//import org.apache.poi.xwpf.converter.pdf.PdfOptions;
//import org.apache.poi.xwpf.usermodel.XWPFDocument;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.core.io.Resource;
//import com.lowagie.text.Font;
//import com.lowagie.text.pdf.BaseFont;
//import fr.opensagres.xdocreport.itext.extension.font.ITextFontRegistry;
//
//public class DocxToPDFConverter extends Converter {
//    public DocxToPDFConverter(InputStream inStream, OutputStream outStream, boolean showMessages, boolean closeStreamsWhenComplete) {
//        super(inStream, outStream, showMessages, closeStreamsWhenComplete);
//    }
//
//    @Override
//    public void convert() throws Exception {
//        loading();
//        PdfOptions options = PdfOptions.create();
//        XWPFDocument document = new XWPFDocument(inStream);//支持中文字体
//        processing();
//        PdfConverter.getInstance().convert(document, outStream, options);
//        finished();
//    }
//
//    public static void main(String[] args) throws Exception {
//        Converter converter;
//        String docPath = "D:/222.docx";
//        String pdfPath = "D:/1.pdf";
//        File file = new File(pdfPath);
//        OutputStream outputStream = new FileOutputStream(file);
//        FileInputStream inputStream = new FileInputStream(docPath);
//        converter = new DocxToPDFConverter(inputStream, outputStream, true, true);
//        converter.convert();
//    }
//}