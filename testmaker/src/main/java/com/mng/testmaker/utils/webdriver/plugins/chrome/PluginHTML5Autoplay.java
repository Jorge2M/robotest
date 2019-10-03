package com.mng.testmaker.utils.webdriver.plugins.chrome;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;


public class PluginHTML5Autoplay extends PluginChrome {
    
    private final String fileName = "html5autoplay_0_6_2.crx";
    private static final String PageSetup = "chrome-extension://efdhoaajjjgckpbkoglidkeendpkolai/options.html";
    private static final String XPathSelect = "//select[@id='default-mode']";
    
    @Override
    public void addPluginToChrome(ChromeOptions options) throws Exception {
        super.addPluginToChrome(options, this.fileName);
    }
    
    public static void disableAutoplay(WebDriver driver) {
        driver.get(PageSetup);
        new Select(driver.findElement(By.xpath(XPathSelect))).selectByVisibleText("Disable autoplay");
    }
    
    public void disableNothing(WebDriver driver) {
        driver.get(PageSetup);
        new Select(driver.findElement(By.xpath(XPathSelect))).selectByVisibleText("Disable nothing");
    }
}
