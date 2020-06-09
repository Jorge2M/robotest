package com.mng.robotest.test80.mango.test.pageobject.votf;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageAlertaVOTF {
    
    private static final String XPathCapaAlerta = "//div[@class='alert']";
    private static final String XPathButtonContinuar = "//div[@class='alert']//span[@class='button']";

    public static boolean isPage(WebDriver driver) {
    	return (state(Present, By.xpath(XPathCapaAlerta), driver).check());
    }
     
    public static void clickButtonContinuar(WebDriver driver) {
    	click(By.xpath(XPathButtonContinuar), driver).exec();
    }
}
