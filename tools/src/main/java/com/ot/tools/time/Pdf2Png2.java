package com.ot.tools.time;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 可以转多页
 */
public class Pdf2Png2 {


    public static void pdfFileToImage(File pdffile, String targetPath, int height_offset) {
        try {
            FileInputStream instream = new FileInputStream(pdffile);
            InputStream byteInputStream = null;
            try {
                PDDocument doc = PDDocument.load(instream);
                PDFRenderer renderer = new PDFRenderer(doc);
                int pageCount = doc.getNumberOfPages();
                List<BufferedImage> list = new ArrayList<BufferedImage>();
                if (pageCount > 0) {
                    int totalHeight = 0;
                    int width = 0;
                    for (int i = 0; i < pageCount; i++) {
                        BufferedImage image = renderer.renderImage(i, 1.0f);
                        list.add(image);
                        totalHeight += image.getHeight();
                        if (width < image.getWidth()) {
                            width = image.getWidth();
                        }
                        image.flush();
                    }
                    BufferedImage tag = new BufferedImage(width, totalHeight, BufferedImage.TYPE_INT_RGB);
                    tag.getGraphics();
                    Graphics g = tag.createGraphics();
                    int startHeight = 0;
                    for (BufferedImage image : list) {
                        g.drawImage(image, 0, startHeight, width, image.getHeight(), null);
                        g.drawImage(image, 0, startHeight, width, image.getHeight(), null);
                        startHeight += image.getHeight() + height_offset;
                    }
                    g.dispose();

                    ByteArrayOutputStream bs = new ByteArrayOutputStream();
                    ImageOutputStream imOut;
                    imOut = ImageIO.createImageOutputStream(bs);
                    ImageIO.write(tag, "png", imOut);
                    byteInputStream = new ByteArrayInputStream(bs.toByteArray());
                    byteInputStream.close();

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            File uploadFile = new File(targetPath);
            FileOutputStream fops;
            fops = new FileOutputStream(uploadFile);
            fops.write(readInputStream(byteInputStream));
            fops.flush();
            fops.close();
        } catch (Exception e) {
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
        File file = new File("D://检验报告单.pdf");
        // 上传的是png格式的图片结尾
        String targetfile = "D://wdg1.png";
        pdfFileToImage(file, targetfile, 100);
    }
}
