package com.mng.robotest.test80.mango.test.pageobject.shop.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.jorge2m.testmaker.conf.Channel;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class SecKlarna {

    //Parte del error que aparece cuando se introduce un teléfono incorrecto desde Desktop
    public static final String errorTlfDesktop = "The telephone number you submitted is not in the correct format";
    
    //Parte del error que aparece cuando se introduce un teléfono incorrecto desde Móvil
    public static final String errorTlfMovil   = "The mobile phone / cell phone number you submitted is not formatted correctly";
    
    static String XPathButtonSearchAddress = "//*[@id[contains(.,'btnKlarnaConfirmar')]]";
    static String XPathModalDirecciones = "//div[@class[contains(.,'modalDireccionesKlarna')]]";
    static String XPathNombreAddress = "//div[@class[contains(.,'klarnaAddressNombre')]]";
    static String XPathDireccionAddress = "//div[@class[contains(.,'klarnaAddressDireccion')]]";
    static String XPathProvinciaAddress = "//div[@class[contains(.,'klarnaAddressProvincia')]]";
    static String XPathCapaKlarnaMobil = "//div[@class[contains(.,'klarna')] and @class[contains(.,'show')]]";
    static String XPathCapaKlarnaDesktop = "//div[@class[contains(.,'klarnaInput')]]";
    static String XPathButtonConfirmAddressMobil = "//span[@id[contains(.,'formDireccionesKlarna')] and @class[contains(.,'modalConfirmar')]]"; 
    static String XPathButtonConfirmAddressDesktop = "//span[@id[contains(.,'FormularioKlarna')] and @class[contains(.,'modalConfirmar')]]";
    
    public static String getXPath_capaKlarna(Channel channel) {
        if (channel==Channel.mobile) {
            return XPathCapaKlarnaMobil;
        }
        return XPathCapaKlarnaDesktop; 
    }
    
    public static String getXPath_inputNumPersonal(Channel channel) {
        String xpathCapaKlarna = getXPath_capaKlarna(channel);
        if (channel==Channel.mobile) {
            return (xpathCapaKlarna + "//input[@id[contains(.,'number-card')]]");
        }
        return (xpathCapaKlarna + "//input[@id[contains(.,'personalno')] and @class[contains(.,'personalno-input')]]");
    }
    
    public static String getXPATH_buttonConfirmAddress(Channel channel) {
        if (channel==Channel.mobile) {
            return XPathButtonConfirmAddressMobil; 
        }
        return XPathButtonConfirmAddressDesktop;
    }

	public static boolean isVisibleUntil(Channel channel, int maxSeconds, WebDriver driver) {
		String xpath = getXPath_capaKlarna(channel);
		return (state(Visible, By.xpath(xpath), driver)
				.wait(maxSeconds).check());
	}
    
    /**
     * Espera un determinado número de segundos a que esté disponible el input y posteriormente introduce el número personal
     */
    public static void waitAndinputNumPersonal(WebDriver driver, int secondsWait, String numPerKlarna, Channel channel) {
        String xpathInput = getXPath_inputNumPersonal(channel);
        new WebDriverWait(driver, secondsWait).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathInput)));
        driver.findElement(By.xpath(xpathInput)).sendKeys(numPerKlarna);
    }
    
    /**
     * @return si un determinado mensaje es el correspondiente al mensaje de error por teléfono introducido con formato incorrecto
     */
    public static boolean isErrorTlfn(String mensajeError, Channel channel) {
        boolean isError = false;
        if (channel==Channel.mobile) {
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
    
	/**
	 * Selección del botón "Search Address" (se trata de un botón que aparece en algunos tipos de Klarna como p.e. el de Sweden)
	 */
	public static void clickSearchAddress(WebDriver driver) {
		click(By.xpath(XPathButtonSearchAddress), driver).exec();
	}

	/**
	 * @return indicador de si existe o no el modal de la confirmación de la dirección (aparece sólo en algunos tipos de Klarna como p.e. el de Sweden)
	 */
	public static boolean isModalDireccionesVisibleUntil(int maxSeconds, WebDriver driver) {
		return (state(Visible, By.xpath(XPathModalDirecciones), driver)
				.wait(maxSeconds).check());
	}

	public static boolean isModalDireccionesInvisibleUntil(int maxSeconds, WebDriver driver) {
		return (state(Invisible, By.xpath(XPathModalDirecciones), driver)
				.wait(maxSeconds).check());
	}

	public static String getTextNombreAddress(WebDriver driver) {
		return driver.findElement(By.xpath(XPathNombreAddress)).getText();
	}

	public static String getTextDireccionAddress(WebDriver driver) {
		return driver.findElement(By.xpath(XPathDireccionAddress)).getText();
	}

	public static String getTextProvinciaAddress(WebDriver driver) {
		return driver.findElement(By.xpath(XPathProvinciaAddress)).getText();
	}

	public static void clickConfirmAddress(WebDriver driver, Channel channel) {
		By byElem = By.xpath(getXPATH_buttonConfirmAddress(channel));
		click(byElem, driver).exec();
	}
}
