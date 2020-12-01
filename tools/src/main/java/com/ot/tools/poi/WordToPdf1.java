//package com.ot.tools.poi;
//
//import org.apache.poi.xwpf.converter.pdf.PdfConverter;
//import org.apache.poi.xwpf.converter.pdf.PdfOptions;
//import org.apache.poi.xwpf.usermodel.*;
//import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
//
//import java.io.*;
//import java.math.BigInteger;
//
//public class WordToPdf1 {
//    public static void main(String[] args) throws IOException {
//        String docPath = "D:/111.docx";
//        String pdfPath = "D:/1.pdf";
//        FileInputStream fileInputStream = new FileInputStream(docPath);
//        XWPFDocument xwpfDocument = new XWPFDocument(fileInputStream);
//        PdfOptions pdfOptions = PdfOptions.create();
//        FileOutputStream fileOutputStream = new FileOutputStream(pdfPath);
//        PdfConverter.getInstance().convert(xwpfDocument,fileOutputStream,pdfOptions);
//        fileInputStream.close();
//        fileOutputStream.close();
//    }
//}
