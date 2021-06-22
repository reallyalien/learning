package com.ot.learn.system;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.AccessControlException;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipInputStream;

public class SecurityManageTest {

    /**
     * 安全管理器是一个允许应用程序实现安全策略的类。它允许应用程序在执行一个可能不安全或敏感的操作前确定该操作是什么，
     * 以及是否是在允许执行该操作的安全上下文中执行它。应用程序可以允许或不允许该操作。
     * SecurityManager 类包含了很多名称以单词 check 开头的方法。Java 库中的各种方法在执行某些潜在的敏感操作前可以调用这些方法。
     */

    static class MySecurityManage extends SecurityManager {
        @Override
        public void checkRead(String file) {
            if ("java.policy".contains(file)) {
                throw new AccessControlException("cannot find file:" + file);
            }
            super.checkRead(file);
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        System.setSecurityManager(new MySecurityManage());
        FileInputStream fis = new FileInputStream(new File("java.policy"));
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            System.setSecurityManager(null);
        }
    }

}
