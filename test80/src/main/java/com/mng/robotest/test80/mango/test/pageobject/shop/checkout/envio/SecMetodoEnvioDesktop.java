package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.envio;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import com.mng.testmaker.utils.controlTest.FmwkTest;
import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.envio.TipoTransporteEnum.TipoTransporte;


public class SecMetodoEnvioDesktop extends WebdrvWrapp {
	static Logger pLogger = LogManager.getLogger(FmwkTest.log4jLogger);
	
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
    
    public static void selectMetodoIfNotSelected(TipoTransporte tipoTransporte, WebDriver driver) throws Exception {
    	int zeroSecondsToWait = 0;
        if (!isBlockSelectedUntil(tipoTransporte, zeroSecondsToWait, driver)) {
        	selectMetodo(tipoTransporte, driver);
        }
    }
    
    public static void selectMetodo(TipoTransporte tipoTransporte, WebDriver driver) throws Exception {
        String xpathMethodRadio = getXPathRadioMetodo(tipoTransporte);
        if (isElementVisible(driver, By.xpath(xpathMethodRadio)) && 
        	tipoTransporte != TipoTransporte.POSTNORD) {
        	clickAndWaitLoad(driver, By.xpath(xpathMethodRadio), 5/*waitSeconds*/);
        } else {
        	String xpathBlock = getXPathBlockMetodo(tipoTransporte);
        	clickAndWaitLoad(driver, By.xpath(xpathBlock), 5/*waitSeconds*/);
        }
    }
    
    
    public static boolean isPresentBlockMetodo(TipoTransporte tipoTransporte, WebDriver driver) {
        String xpathBLock = getXPathBlockMetodo(tipoTransporte);
        return (isElementPresent(driver, By.xpath(xpathBLock)));
    }
    
    public static boolean isBlockSelectedUntil(TipoTransporte tipoTransporte, int maxSecondsToWait, WebDriver driver) throws Exception {
        String xpathBlockSelected = getXPathBlockMetodoSelected(tipoTransporte);
    	waitForPageLoaded(driver);
        return (isElementVisibleUntil(driver, By.xpath(xpathBlockSelected), maxSecondsToWait));
    }
    
    public static void selectFranjaHorariaUrgente(int posicion, WebDriver driver) {
        Select selectHorario = new Select(driver.findElement(By.xpath(XPathSelectFranjaHorariaMetodoUrgente)));
        selectHorario.selectByIndex(posicion);
    }
}
