package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.envio;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.envio.TipoTransporteEnum.TipoTransporte;


public class SecMetodoEnvioDesktop {
	
    public static ModalDroppoints modalDroppoints;
    static String XPathRadioInput = "//input[@id[contains(.,'Transportes')]]";
    static String XPathSelectFranjaHorariaMetodoUrgente = "//select[@data-component-id='time-range-sameday_nextday_franjas']";
    
    private static String getXPathBlockMetodo(TipoTransporte tipoTransporte) {
        return (
        	"//div[(@class[contains(.,'bloqueMetodos')] or @class[contains(.,'metodoSelected')]) and @data-analytics-id='" + 
        	tipoTransporte.getIdAnalytics() + "']");
    }
    
    private static String getXPathBlockMetodoSelected(TipoTransporte tipoTransporte) {
    	String xpathBlockMethod = getXPathBlockMetodo(tipoTransporte);
    	return (xpathBlockMethod + "//self::*[@class[contains(.,'metodoSelected')]]");
    }

	private static String getXPathRadioMetodo(TipoTransporte tipoTransporte) {
        return getXPathBlockMetodo(tipoTransporte) + XPathRadioInput;
    }
    
    public static void selectMetodoIfNotSelected(TipoTransporte tipoTransporte, WebDriver driver) {
    	int zeroSecondsToWait = 0;
        if (!isBlockSelectedUntil(tipoTransporte, zeroSecondsToWait, driver)) {
        	selectMetodo(tipoTransporte, driver);
        }
    }

	public static void selectMetodo(TipoTransporte tipoTransporte, WebDriver driver) {
		String xpathMethodRadio = getXPathRadioMetodo(tipoTransporte);
		if (state(Visible, By.xpath(xpathMethodRadio), driver).check() &&
				tipoTransporte != TipoTransporte.POSTNORD) {
			click(By.xpath(xpathMethodRadio), driver).waitLoadPage(5).exec();
		} else {
			String xpathBlock = getXPathBlockMetodo(tipoTransporte);
			click(By.xpath(xpathBlock), driver).waitLoadPage(5).exec();
		}
	}

    public static boolean isPresentBlockMetodo(TipoTransporte tipoTransporte, WebDriver driver) {
        String xpathBLock = getXPathBlockMetodo(tipoTransporte);
        return (state(Present, By.xpath(xpathBLock), driver).check());
    }
    
    public static boolean isBlockSelectedUntil(TipoTransporte tipoTransporte, int maxSeconds, WebDriver driver) {
        String xpathBlockSelected = getXPathBlockMetodoSelected(tipoTransporte);
    	waitForPageLoaded(driver);
    	return (state(Visible, By.xpath(xpathBlockSelected), driver)
    			.wait(maxSeconds).check());
    }
    
    public static void selectFranjaHorariaUrgente(int posicion, WebDriver driver) {
        Select selectHorario = new Select(driver.findElement(By.xpath(XPathSelectFranjaHorariaMetodoUrgente)));
        selectHorario.selectByIndex(posicion);
    }
}
