package com.mng.robotest.test80.mango.test.pageobject.shop.pedidos;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.utils.otras.Channel;
import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;


public class PageDetallePedidoNew extends WebdrvWrapp implements PageDetallePedido {
    private static final String XPathDivDetalle = "//div[@id='myPurchasesPage']";
    private static final String XPathLineaPrenda = "//div[@onclick[contains(.,'openProductDetails')]]";
    private static final String XPathBackButtonDesktop = "//div[@class[contains(.,'shopping-breadcrumbs')]]";
    private static final String XPathBackButtonMobil = "//div[@class[contains(.,'iconBack')]]";
    
    static String getXPathBackButton(Channel channel) {
    	switch (channel) {
    	case desktop:
    		return XPathBackButtonDesktop;
    	case movil_web:
    	default:
    		return XPathBackButtonMobil;
    	}    	
    }

    @Override
    public DetallePedido getTypeDetalle() {
    	return DetallePedido.New;
    }    
    
    @Override
    public boolean isPage(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathDivDetalle)));
    }
    
    @Override
    public boolean isPresentImporteTotal(String importeTotal, String codPais, WebDriver driver) {
        return (ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver));
    }
    
    @Override
    public boolean isVisiblePrendaUntil(int maxSecondsToWait, WebDriver driver) throws Exception {
    	return (isElementVisibleUntil(driver, By.xpath(XPathLineaPrenda), maxSecondsToWait));
    }
    
    @Override
    public int getNumPrendas(WebDriver driver) {
        return (driver.findElements(By.xpath(XPathLineaPrenda)).size());
    }
    
    @Override
    public void clickBackButton(Channel channel, WebDriver driver) throws Exception {
    	By byLinkBack = By.xpath(getXPathBackButton(channel));
    	clickAndWaitLoad(driver, byLinkBack);
    }
}
