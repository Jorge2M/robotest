package com.mng.robotest.test80.mango.test.pageobject.shop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

@SuppressWarnings("javadoc")
public class PageErrorPage {
    static String XPathIpNode = "//h2[text()[contains(.,'IP :')]]/following-sibling::*[1]";
    
    public static String getIpNode(WebDriver driver) {
        return (driver.findElement(By.xpath(XPathIpNode)).getText());
    }
    
}
