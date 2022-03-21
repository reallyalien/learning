package com.ot.tools.util.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;

public class File3 {

    public static void pdfFileToImage(File pdffile, String targetPath){
        try {
            FileInputStream instream = new FileInputStream(pdffile);
            InputStream byteInputStream=null;
            try {
                PDDocument doc = PDDocument.load(instream);
                PDFRenderer renderer = new PDFRenderer(doc);
                int pageCount = doc.getNumberOfPages();
                if (pageCount > 0) {
                    BufferedImage image = renderer.renderImage(0, 2.0f);
                    image.flush();
                    ByteArrayOutputStream bs = new ByteArrayOutputStream();
                    ImageOutputStream imOut;
                    imOut = ImageIO.createImageOutputStream(bs);
                    ImageIO.write(image, "png", imOut);
                    byteInputStream = new ByteArrayInputStream(bs.toByteArray());
                    byteInputStream.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            File uploadFile = new File(targetPath);
            FileOutputStream fops;
            fops = new FileOutputStream(uploadFile);
            fops.write(readInputStream(byteInputStream));
            fops.flush();
            fops.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }

    public static void main(String[] args) {
        File file =new File("D://http___172.20.105.14_handlers_PdfReport.aspx_file=_huawei.ydyypacsPdfReportsPdfReports2022010297f37ff0-d5cc-c744-f881-08d9cd938ee6.pdf.pdf");
        //上传的是png格式的图片结尾
        String targetfile="D://wdg3.png";
        pdfFileToImage(file,targetfile);
    }

}
