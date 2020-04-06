package com.mng.robotest.test80.mango.test.pageobject.shop.pedidos;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;


public class PageDetallePedidoNew extends PageObjTM implements PageDetallePedido {
	
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

    public PageDetallePedidoNew(WebDriver driver) {
    	super(driver);
    }
    
    @Override
    public DetallePedido getTypeDetalle() {
    	return DetallePedido.New;
    }    
    
    @Override
    public boolean isPage() {
    	return (state(Present, By.xpath(XPathDivDetalle)).check());
    }
    
    @Override
    public boolean isPresentImporteTotal(String importeTotal, String codPais) {
        return (ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver));
    }
    
    @Override
    public boolean isVisiblePrendaUntil(int maxSeconds) {
    	return (state(Visible, By.xpath(XPathLineaPrenda)).wait(maxSeconds).check());
    }
    
    @Override
    public int getNumPrendas() {
        return (driver.findElements(By.xpath(XPathLineaPrenda)).size());
    }
    
    @Override
    public void clickBackButton(Channel channel) {
    	By byLinkBack = By.xpath(getXPathBackButton(channel));
    	click(byLinkBack).exec();
    }
}
