package org.example.webmagic;

import us.codecraft.webmagic.Spider;

/**
 * @Title: Test1
 * @Author wangtao
 * @Date 2023/10/18 14:34
 * @description:
 */
public class Test1 {

    public static void main(String[] args) {
        // 创建爬虫
        Spider.create(new BaiduPageProcessor())
                .addUrl("https://www.baidu.com") // 设置访问的起始URL
                .run(); // 启动爬虫
    }
}
