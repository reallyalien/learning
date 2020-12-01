package com.ot.tools.poi;


import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.core.FileURIResolver;
import org.apache.poi.xwpf.converter.core.XWPFConverterException;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;


public class Word2Pdf {

    /**
     * 2007版本word转换成html
     *
     * @throws IOException
     */
    public static String Word2007ToHtml(String docxFilePath) throws IOException {
        InputStream in = null;
        XWPFDocument document = null;
        OutputStream out = null;
        String property = System.getProperty("java.io.tmpdir");
        String fileName = System.currentTimeMillis() + ".html";
        String htmlFilePath = "";
        try {
            File f = new File(docxFilePath);
            // 1) 加载word文档生成 XWPFDocument对象
            in = new FileInputStream(f);
            document = new XWPFDocument(in);
            // 2) 解析 XHTML配置 (这里设置IURIResolver来设置图片存放的目录)
            File imageFolderFile = new File(property);
            XHTMLOptions options = XHTMLOptions.create();
            options.setExtractor(new FileImageExtractor(imageFolderFile));
            options.setIgnoreStylesIfUnused(false);
            options.setFragment(true);
            // 3) 将 XWPFDocument转换成XHTML
            htmlFilePath = property + fileName;
            out = new FileOutputStream(new File(htmlFilePath));
            XHTMLConverter.getInstance().convert(document, out, options);
            f.delete();
            return htmlFilePath;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XWPFConverterException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return htmlFilePath;

    }

    /**
     * /**
     * 2003版本word转换成html
     *
     * @throws IOException
     * @throws TransformerException
     * @throws ParserConfigurationException
     */
    public static void Word2003ToHtml() throws IOException, TransformerException, ParserConfigurationException {
        final String imagepath = "F:/test/image/";//解析时候如果doc文件中有图片  图片会保存在此路径
        String filepath = "F:/test/";
        String fileName = "123.doc";
        String htmlName = "123.html";
        final String file = filepath + fileName;
        InputStream input = new FileInputStream(new File(file));
        HWPFDocument wordDocument = new HWPFDocument(input);
        WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
        //设置图片存放的位置
        wordToHtmlConverter.setPicturesManager(new PicturesManager() {
            public String savePicture(byte[] content, PictureType pictureType, String suggestedName, float widthInches, float heightInches) {
                File imgPath = new File(imagepath);
                if (!imgPath.exists()) {//图片目录不存在则创建
                    imgPath.mkdirs();
                }
                File file = new File(imagepath + suggestedName);
                try {
                    OutputStream os = new FileOutputStream(file);
                    os.write(content);
                    os.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return imagepath + suggestedName;
            }
        });

        //解析word文档
        wordToHtmlConverter.processDocument(wordDocument);
        Document htmlDocument = wordToHtmlConverter.getDocument();

        File htmlFile = new File(filepath + htmlName);
        OutputStream outStream = new FileOutputStream(htmlFile);

        //也可以使用字符数组流获取解析的内容
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        OutputStream outStream = new BufferedOutputStream(baos);

        DOMSource domSource = new DOMSource(htmlDocument);
        StreamResult streamResult = new StreamResult(outStream);

        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer serializer = factory.newTransformer();
        serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        serializer.setOutputProperty(OutputKeys.METHOD, "html");

        serializer.transform(domSource, streamResult);

        //也可以使用字符数组流获取解析的内容
//        String content = baos.toString();
//        System.out.println(content);
//        baos.close();
        outStream.close();
    }
}