package com.mng.robotest.test80.mango.test.pageobject.shop.cabecera;

import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.arq.webdriverwrapper.ElementPage;
import com.mng.robotest.test80.arq.webdriverwrapper.TypeOfClick;
import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.IdiomaPais;
import com.mng.robotest.test80.mango.test.pageobject.shop.Mensajes;
import com.mng.robotest.test80.mango.test.pageobject.shop.buscador.SecSearch;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.mobil.SecMenuLateralMobil;

/**
 * Clase que define la automatización de las diferentes funcionalidades de la sección de "Cabecera" (de Desktop y Movil)
 * @author jorge.munoz
 *
 */
public class SecCabeceraOutletMobil extends SecCabeceraOutlet {
	
    private final static String XPathLinkLogo = SecCabeceraOutletDesktop.XPathLinkLogo;
	private final static String XPathIconoBolsa = "//a[@class[contains(.,'cartlink')]]";
	private final static String XPathNumArticles = "//span[@class[contains(.,'_cartNum')]]";

    private SecCabeceraOutletMobil(Channel channel, AppEcom app, WebDriver driver) {
    	super(channel, app, driver);
    }
    
    public static SecCabeceraOutletMobil getNew(Channel channel, AppEcom app, WebDriver driver) {
    	return (new SecCabeceraOutletMobil(channel, app, driver));
    }

    @Override
    String getXPathLogoMango() {
    	return XPathLinkLogo;
    }
    
    @Override
    String getXPathNumberArtIcono() {
    	return XPathNumArticles;
    }
    
    @Override
    public boolean isVisibleIconoBolsa() {
    	return (WebdrvWrapp.isElementVisible(driver, By.xpath(XPathIconoBolsa)));
    }
    
    @Override
    public void clickIconoBolsa() throws Exception {
    	click(Icono.Bolsa);
    }
    
    @Override
    public void clickIconoBolsaWhenDisp(int maxSecondsToWait) throws Exception {
    	clickIfClickableUntil(Icono.Bolsa, maxSecondsToWait);
    }
    
    @Override
    public void hoverIconoBolsa() {
    	hoverIcono(Icono.Bolsa);
    }
    
    public boolean isVisible(Icono icono) {
    	return (isElementVisible(driver, By.xpath(icono.getXPath(app))));
    }
    
    public boolean isClicable(Icono icono) {
    	return (isClickableUntil(icono, 0));
    }
    
    public boolean isClickableUntil(Icono icono, int maxSeconds) {
    	return (isElementClickableUntil(driver, By.xpath(icono.getXPathLink(app)), maxSeconds));
    }
    
    public void clickIfClickableUntil(Icono icono, int maxSecondsToWait) 
    throws Exception {
    	if (isClickableUntil(icono, maxSecondsToWait)) {
    		click(icono);
    	}
    }
    
    public void click(Icono icono) throws Exception {
    	click(icono, app, driver);
    }
    
    public static void click(Icono icono, AppEcom app, WebDriver driver) throws Exception {
    	clickAndWaitLoad(driver, By.xpath(icono.getXPathLink(app)), TypeOfClick.javascript);
    }
    
    public void clickIconoLupaIfVisible() throws Exception {
    	if (isElementVisible(driver, By.xpath(Icono.Lupa.getXPath(app)))) {
    		click(Icono.Lupa);
    	}
    }
    
    public void hoverIcono(Icono icono) {
    	moveToElement(By.xpath(icono.getXPathLink(app)), driver);
    }

    /**
     * Busca un determinado artículo por su referencia y no espera a la página de resultado
     */
    public void buscarRefNoWait(String referencia) throws Exception {
        clickLupaAndWaitInput(2/*maxSecondsToWait*/);
        setTextAndReturn(referencia);
    }
    
    public boolean clickLupaAndWaitInput(int maxSecondsWait) throws Exception {
    	clickIconoLupaIfVisible();
    	return (getSearchBar().isVisibleUntil(maxSecondsWait));
    }
    
    /**
     * Introducimos la referencia en el buscador y seleccionamos RETURN
     */
    public void setTextAndReturn(String referencia) throws Exception {
    	if (getSearchBar().isVisibleUntil(2)) {
    		getSearchBar().search(referencia);
    	}
    }    
}
