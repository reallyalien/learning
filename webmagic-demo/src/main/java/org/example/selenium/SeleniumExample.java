package org.example.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * @Title: SeleniumExample
 * @Author wangtao
 * @Date 2023/10/18 14:47
 * @description:
 */
public class SeleniumExample {

    public static void main(String[] args) {
        // 设置驱动路径（根据自己的浏览器和系统环境进行设置）
        System.setProperty("webdriver.chrome.driver", "D:/path/chromedriver.exe");

        // 创建 ChromeDriver 对象
        WebDriver driver = new ChromeDriver();

        // 打开网页
        driver.get("https://www.baidu.com");

        String currentUrl = driver.getCurrentUrl();
        System.out.println(111+currentUrl);

        // 定位并点击按钮
        WebElement button = driver.findElement(By.xpath("//*[@id=\"s-top-left\"]/a[1]"));
        button.click();

        String href = button.getAttribute("href");
        System.out.println(href);


        driver.get(href);

        // 获取结果
        WebElement result = driver.findElement(By.xpath("//*[@id=\"pane-news\"]/div/ul/li[1]/strong/a"));
        String text = result.getText();
        System.out.println("Result: " + text);

        // 关闭浏览器
        driver.quit();
    }
}
