package org.example.webmagic;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @Title: BaiduPageProcessor
 * @Author wangtao
 * @Date 2023/10/18 14:37
 * @description:
 */
public class BaiduPageProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    @Override
    public void process(Page page) {
        // 解析页面，提取需要的数据
        String title = page.getHtml().xpath("//title").get();
        String content = page.getHtml().smartContent().get();

        // 输出结果
        System.out.println("Title: " + title);
        System.out.println("Content: " + content);
    }

    @Override
    public Site getSite() {
        return site;
    }
}
