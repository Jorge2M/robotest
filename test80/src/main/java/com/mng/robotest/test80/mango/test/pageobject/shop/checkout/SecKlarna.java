package com.mng.robotest.test80.mango.test.pageobject.shop.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class SecKlarna extends PageObjTM {

	private final Channel channel;
	
    private final static String errorTlfDesktop = "The telephone number you submitted is not in the correct format";
    private final static String errorTlfMovil   = "The mobile phone / cell phone number you submitted is not formatted correctly";
    
    private final static String XPathButtonSearchAddress = "//*[@id[contains(.,'btnKlarnaConfirmar')]]";
    private final static String XPathModalDirecciones = "//div[@class[contains(.,'modalDireccionesKlarna')]]";
    private final static String XPathNombreAddress = "//div[@class[contains(.,'klarnaAddressNombre')]]";
    private final static String XPathDireccionAddress = "//div[@class[contains(.,'klarnaAddressDireccion')]]";
    private final static String XPathProvinciaAddress = "//div[@class[contains(.,'klarnaAddressProvincia')]]";
    private final static String XPathCapaKlarnaMobil = "//div[@class[contains(.,'klarna')] and @class[contains(.,'show')]]";
    private final static String XPathCapaKlarnaDesktop = "//div[@class[contains(.,'klarnaInput')]]";
    private final static String XPathButtonConfirmAddressMobil = "//span[@id[contains(.,'formDireccionesKlarna')] and @class[contains(.,'modalConfirmar')]]"; 
    private final static String XPathButtonConfirmAddressDesktop = "//span[@id[contains(.,'FormularioKlarna')] and @class[contains(.,'modalConfirmar')]]";

    public SecKlarna(Channel channel, WebDriver driver) {
    	super(driver);
    	this.channel = channel;
    }
    
    public String getXPath_capaKlarna() {
        if (channel.isDevice()) {
            return XPathCapaKlarnaMobil;
        }
        return XPathCapaKlarnaDesktop; 
    }
    
    public String getXPath_inputNumPersonal() {
        String xpathCapaKlarna = getXPath_capaKlarna();
        if (channel.isDevice()) {
            return (xpathCapaKlarna + "//input[@id[contains(.,'number-card')]]");
        }
        return (xpathCapaKlarna + "//input[@id[contains(.,'personalno')] and @class[contains(.,'personalno-input')]]");
    }
    
    public String getXPATH_buttonConfirmAddress() {
        if (channel.isDevice()) {
            return XPathButtonConfirmAddressMobil; 
        }
        return XPathButtonConfirmAddressDesktop;
    }

	public boolean isVisibleUntil(int maxSeconds) {
		String xpath = getXPath_capaKlarna();
		return (state(Visible, By.xpath(xpath)).wait(maxSeconds).check());
	}
    
    public void waitAndinputNumPersonal(int secondsWait, String numPerKlarna) {
        String xpathInput = getXPath_inputNumPersonal();
        new WebDriverWait(driver, secondsWait).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathInput)));
        driver.findElement(By.xpath(xpathInput)).sendKeys(numPerKlarna);
    }
    
    public boolean isErrorTlfn(String mensajeError) {
        boolean isError = false;
        if (channel.isDevice()) {
            if (mensajeError.contains(errorTlfMovil)) {
                isError = true;
            }
        } else {
            if (mensajeError.contains(errorTlfDesktop)) {
                isError = true;
            }
        }
        
        return isError;
    }
    
	public void clickSearchAddress() {
		click(By.xpath(XPathButtonSearchAddress)).exec();
	}

	public boolean isModalDireccionesVisibleUntil(int maxSeconds) {
		return (state(Visible, By.xpath(XPathModalDirecciones)).wait(maxSeconds).check());
	}

	public boolean isModalDireccionesInvisibleUntil(int maxSeconds) {
		return (state(Invisible, By.xpath(XPathModalDirecciones)).wait(maxSeconds).check());
	}

	public String getTextNombreAddress() {
		return driver.findElement(By.xpath(XPathNombreAddress)).getText();
	}

	public String getTextDireccionAddress() {
		return driver.findElement(By.xpath(XPathDireccionAddress)).getText();
	}

	public String getTextProvinciaAddress() {
		return driver.findElement(By.xpath(XPathProvinciaAddress)).getText();
	}

	public void clickConfirmAddress() {
		By byElem = By.xpath(getXPATH_buttonConfirmAddress());
		click(byElem).exec();
	}
}
