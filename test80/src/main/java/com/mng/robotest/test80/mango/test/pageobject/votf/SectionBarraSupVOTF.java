package com.mng.robotest.test80.mango.test.pageobject.votf;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class SectionBarraSupVOTF {

    public static final String titleUserName = "USERNAME: "; 
    private static final String XPathBarra = "//div[@class[contains(.,'barraTele')]]";
    
    public static boolean isPresentUsuario(String usuarioVOTF, WebDriver driver) {
        String usuarioLit = titleUserName + usuarioVOTF;
        return (
        	state(Present, By.xpath(XPathBarra), driver).check() &&
            driver.findElement(By.xpath(XPathBarra)).getText().toLowerCase().contains(usuarioLit.toLowerCase()));
    }
    
}
