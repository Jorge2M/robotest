package com.mng.robotest.test80.mango.test.pageobject.shop.miscompras;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;


public class PageDetalleCompraOld extends PageObjTM implements PageDetallePedido {
	
    private static final String XPathDivDetalle = "//div[@id='myPurchasesPage']";
    private static final String XPathLineaPrenda = "//div[@class[contains(.,'small-box-container')]]";
    private static final String XPathBackButton = "//div[@class[contains(.,'shopping-breadcrumbs')]]";
    
    public PageDetalleCompraOld(WebDriver driver) {
    	super(driver);
    }
    
    @Override
    public DetallePedido getTypeDetalle() {
    	return DetallePedido.Old;
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
    	click(By.xpath(XPathBackButton)).exec();
    }
}
