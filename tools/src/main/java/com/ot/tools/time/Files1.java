//package com.ot.tools.time;
//
//import com.ot.tools.util.util.FileUtil;
//import org.apache.commons.io.FileUtils;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.UUID;
//
//public class Files1 {
//
//    public static void main(String[] args) {
//        try {
//
//            String str = "D://http___172.20.105.14_handlers_PdfReport.aspx_file=_huawei.ydyypacsPdfReportsPdfReports2022010297f37ff0-d5cc-c744-f881-08d9cd938ee6.pdf.pdf";
//            FileInputStream fis = new FileInputStream(new File(str));
//            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            byte[] bytes = new byte[1024 * 10];
//            int len = 0;
//            while ((len = fis.read(bytes)) != -1) {
//                bos.write(bytes, 0, len);
//            }
//            bos.flush();
//            byte[] byteArray = bos.toByteArray();
//            String s = UUID.randomUUID().toString().replaceAll("-", "");
//            System.out.println(s);
//            File pdfFile = File.createTempFile(s, ".pdf");
//            FileUtils.copyInputStreamToFile(FileUtil.byte2Input(byteArray), pdfFile);
//            String imageFile = FileUtil.createSingleImageFromPDF(pdfFile.getPath(), "d:/ccc.png");
//            System.out.println();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
