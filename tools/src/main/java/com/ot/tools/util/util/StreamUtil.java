package com.ot.tools.util.util;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author houjiexi
 */
public class StreamUtil {
    private static final String DEFAULT_ENCODING = "utf-8";

    public static String inputStream2String(InputStream in, String charsetName) {
        if (in == null) {
            return null;
        }
        InputStreamReader inReader = null;

        try {
            if (StrUtils.isBlank(charsetName)) {
                inReader = new InputStreamReader(in, DEFAULT_ENCODING);
            } else {
                inReader = new InputStreamReader(in, charsetName);
            }
            int readLen = 0;
            char[] buffer = new char[1024];
            StringBuffer strBuf = new StringBuffer();
            while ((readLen = inReader.read(buffer)) != -1) {
                strBuf.append(buffer, 0, readLen);
            }
            return strBuf.toString();
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            closeStream(inReader);
        }
        return null;
    }

    public static void closeStream(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}