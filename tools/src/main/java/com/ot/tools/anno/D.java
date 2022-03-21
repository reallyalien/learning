package com.ot.tools.anno;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

public class D {

    public static void main(String[] args) throws IOException {
        String s = UUID.randomUUID().toString().replaceAll("-", "");
        System.out.println(s);
        File tempFile = File.createTempFile(s, ".pdf");
        System.out.println(tempFile.getPath().replaceAll("\\/",""));
        System.out.println(tempFile.getAbsolutePath());
    }
}
