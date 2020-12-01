package com.ot.tools.poi;

//import org.apache.poi.util.PackageHelper;
import org.apache.poi.xwpf.converter.core.IURIResolver;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.converter.xhtml.internal.utils.StringEscapeUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;

public class Coverter1 {

    public String viewFile() {
//        String docxFilePath = "e:/6e29a067c6b142c89742de51d0ca016b.docx";
//        String docxFilePath = "d:/11111.docx";
//        String docxFilePath = "d:/122.docx";
//        String docxFilePath = "d:/333.docx";
        String docxFilePath = "d:/2.docx";
        String htmlFilePath = "";
        try {
            htmlFilePath = Word2Pdf.Word2007ToHtml(docxFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileInputStream fileInputStream = null;
//        try {
//            File file = new File(htmlFilePath);
//            fileInputStream = new FileInputStream(file);
//            file.delete();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        return htmlFilePath;
    }

    /**
     *  * word2007和word2003的构建方式不同，
     * 前者的构建方式是xml，后者的构建方式是dom树
     * 文件的后缀也不同，前者后缀为.docx，后者后缀为.doc
     */
//    public String convertToHtml() throws Exception {
////        String docxFilePath = "d:/333.docx";
//        String docxFilePath = "d:/11111.docx";
//        FileInputStream inputStream1 = new FileInputStream(docxFilePath);
//        FileOutputStream out = new FileOutputStream("d:/1.html");
//        //word07文档
//        XWPFDocument document = new XWPFDocument(PackageHelper.open(inputStream1));
//
//        final String imageUrl = "";
//        XHTMLOptions options = XHTMLOptions.create();
////        //不把图片生成出来
////        options.setExtractor(null);
////        options.setIgnoreStylesIfUnused(false);
////        options.setFragment(true);
////        options.URIResolver(new IURIResolver() {
////            //@Override
////            public String resolve(String uri) {
////                return imageUrl + uri;
////            }
////        });
//        //转换
//        XHTMLConverter.getInstance().convert(document, out, options);
//        out.close();
//        //转化数据流，替换特殊字符
////        return StringEscapeUtils.escapeHtml(out.toString());
//        return null;
//    }

    public static void main(String[] args) throws Exception {
        Coverter1 coverter1 = new Coverter1();
        String s = coverter1.viewFile();
//        String s = coverter1.convertToHtml();
        System.out.println(s);
    }
}
