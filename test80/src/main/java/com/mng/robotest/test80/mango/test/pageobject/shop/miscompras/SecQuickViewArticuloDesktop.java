package com.mng.robotest.test80.mango.test.pageobject.shop.miscompras;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class SecQuickViewArticuloDesktop extends PageObjTM {
	
    private static String XPathContainerItem = "//div[@class='container-item']";
    private static String XPathContainerDescription = XPathContainerItem + "//div[@class[contains(.,'container-description-title')]]";
    private static String XPathReferencia = XPathContainerDescription + "//li[@class='reference']";
    private static String XPathNombre = XPathContainerDescription + "//li[1]";
    private static String XPathPrecio = XPathContainerDescription + "//ul/li[2]";
    
	private SecQuickViewArticuloDesktop(WebDriver driver) {
		super(driver);
	}
	public static SecQuickViewArticuloDesktop getNew(WebDriver driver) {
		return new SecQuickViewArticuloDesktop(driver);
	}
    
    public boolean isVisibleUntil(int maxSeconds) {
    	return (state(Visible, By.xpath(XPathContainerItem)).wait(maxSeconds).check());
    }
    
    public String getReferencia() {
        return (driver.findElement(By.xpath(XPathReferencia)).getText().replaceAll("\\D+",""));
    }
    
    public String getNombre() {
        return (driver.findElement(By.xpath(XPathNombre)).getText());
    }
    
    public String getPrecio() {
        return (driver.findElement(By.xpath(XPathPrecio)).getText());
    }
}
