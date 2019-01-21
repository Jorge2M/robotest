package com.mng.robotest.test80.arq.utils.selenium.plugins.chrome;

import java.io.File;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@SuppressWarnings("javadoc")
public abstract class PluginChrome {
    public String folderInResources = "pluginsBrowser"; 
    public static enum typePluginChrome { HTML5Autoplay }
    
    public void addPluginToChrome(ChromeOptions options, String fileNamePlugin) throws Exception {
        try (InputStream inputStream = getClass().getResourceAsStream("/" + this.folderInResources + "/" + fileNamePlugin)) {
            File tmpFile = File.createTempFile("pluginChrome", "temp.crx");
            tmpFile.deleteOnExit();
            FileUtils.copyInputStreamToFile(inputStream, tmpFile);
            options.addExtensions(tmpFile);
        }
    }
    
    /**
     * idPlugin p.e. edbkhhpodjkbgenodomhfoldapghpddk
     * Nota: de momento no le podemos dar uso porque ChromDriver no permite la ejecución de ShortCuts
     */
    public static void setShortCut(String idPlugin, String shortCut, WebDriver driver) {
        //Save the current window
        //String windowHandle = driver.getWindowHandle();
        
        //Open the Extension's Page in another Tab
        //String titlePant = Thread.currentThread().getName() + "_Extensiones";
        //JavascriptExecutor js = (JavascriptExecutor)driver;
        //js.executeScript("window.open('chrome://extensions/', '" + titlePant + "');");
        //driver.switchTo().window(titlePant);
        driver.get("chrome://extensions-frame");
        
        //Wait 2 seconds for the page of Extensions
        String xpathLinkShortCuts = "//a[@class='extension-commands-config']";
        new WebDriverWait(driver, 2).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathLinkShortCuts)));
        
        //Set the ShortCut Extension
        driver.findElement(By.xpath(xpathLinkShortCuts)).click();
        driver.findElement(By.xpath("//div[@id[contains(.,'" + idPlugin + "')]]//span[@class='command-shortcut-text']")).sendKeys(shortCut);
        driver.findElement(By.xpath("//button[@id[contains(.,'extension-commands')]]")).click();
        
        //Close Tab and Switch to the Original Page
        //js.executeScript("window.close('" + Thread.currentThread().getName() + "');");
        //driver.switchTo().window(windowHandle);
    }
    
    public abstract void addPluginToChrome(ChromeOptions options) throws Exception;
}
