package com.mng.robotest.test80.mango.test.pageobject.votf;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;


public class SectionBarraSupVOTF extends WebdrvWrapp {

    public static final String titleUserName = "USERNAME: "; 
    private static final String XPathBarra = "//div[@class[contains(.,'barraTele')]]";
    
    public static boolean isPresentUsuario(String usuarioVOTF, WebDriver driver) {
        String usuarioLit = titleUserName + usuarioVOTF;
        return (
        	isElementPresent(driver, By.xpath(XPathBarra)) &&
            driver.findElement(By.xpath(XPathBarra)).getText().toLowerCase().contains(usuarioLit.toLowerCase()));
    }
    
}
