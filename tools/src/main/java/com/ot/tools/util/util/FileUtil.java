package com.ot.tools.util.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

/**
 * @author cyj
 * @date 19-03-13
 */
@Slf4j
public class FileUtil {

    public static final String END_PROPERTIES = ".properties";
    public static final String END_XML = ".xml";
    public static final String LINE_FEED = "\r\n";

    /**
     * 文件读取缓冲区大小
     */
    private static final int CACHE_SIZE = 1024;

    /**
     * 字符集
     */
    public enum CHARSET {
        UTF_8("UTF-8"),
        GBK("GBK"),
        GB2312("GB2312"),
        GB18030("GB18030");

        private String value;

        private CHARSET(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    /**
     * 搜索策略
     */
    public enum SEARCH_STRATEGY {

        START_WIRH,
        ENDS_WITH,
        INDEXOF
    }

    /**
     * 删除指定的文件
     *
     * @param filePath
     * @return
     */
    public static boolean delete(String filePath) {

        File file = new File(filePath);
        if (file.exists()) {

            return file.delete();
        }

        return false;
    }

    /**
     * 删除指定的文件
     *
     * @param file
     * @return
     */
    public static boolean delete(File file) {
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }



    public static void inputstreamtofile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    public static File createSingleImageFromPDF(String pdfName, String imageName) throws Exception {
//        if (!pdfName.endsWith(".pdf")) {
//            throw new RuntimeException("请检查参数pdfName文件名是否以.pdf结尾！");
//        }
//        Document document = new Document();
//        document.setFile(pdfName);
//        float scale = 2f;
//        float rotation = 0f;
//        List<BufferedImage> bufferImgList = new ArrayList();
//        for (int i = 0; i < document.getNumberOfPages(); i++) {
//            BufferedImage image = (BufferedImage) document.getPageImage(i, GraphicsRenderingHints.SCREEN,
//                    Page.BOUNDARY_CROPBOX, rotation, scale);
//            bufferImgList.add(image);
//        }
//        document.dispose();
//
//        int height = 0; // 总高度
//        int width = 0; // 总宽度
//        int deviationHeight = 0; // 临时的高度 , 或保存偏移高度
//        int everyHeight = 0; // 临时的高度，主要保存每个高度
//        int picNum = bufferImgList.size();
//        int[] heightArray = new int[picNum]; // 保存每个文件的高度
//        BufferedImage buffer = null; // 保存图片流
//        List<int[]> imgRGB = new ArrayList(); // 保存所有的图片的RGB
//        int[] everyImgRGB; // 保存一张图片中的RGB数据
//        for (int i = 0; i < picNum; i++) {
//            buffer = bufferImgList.get(i);
//            heightArray[i] = buffer.getHeight();// 图片高度
//            deviationHeight = buffer.getHeight();
//            if (i == 0) {
//                width = buffer.getWidth();// 图片宽度
//            }
//            height += deviationHeight; // 获取总高度
//            everyImgRGB = new int[width * deviationHeight];// 从图片中读取RGB
//            everyImgRGB = buffer.getRGB(0, 0, width, deviationHeight, everyImgRGB, 0, width);
//            imgRGB.add(everyImgRGB);
//        }
//        deviationHeight = 0; // 设置偏移高度为0
//        // 生成新图片
//        BufferedImage imageResult = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//        for (int i = 0; i < picNum; i++) {
//            everyHeight = heightArray[i];
//            if (i != 0) {
//                deviationHeight += everyHeight; // 计算偏移高度
//            }
//            imageResult.setRGB(0, deviationHeight, width, everyHeight, imgRGB.get(i), 0, width); // 写入流中
//        }
//        File outFile = new File(imageName);
//        ImageIO.write(imageResult, "png", outFile);
//        return outFile;
//    }

    public static final InputStream byte2Input(byte[] buf) {
        return new ByteArrayInputStream(buf);
    }


}
