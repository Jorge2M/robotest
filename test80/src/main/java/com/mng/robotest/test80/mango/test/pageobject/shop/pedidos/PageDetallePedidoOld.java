package com.mng.robotest.test80.mango.test.pageobject.shop.pedidos;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;


public class PageDetallePedidoOld extends WebdrvWrapp implements PageDetallePedido {
    private static final String XPathDivDetalle = "//div[@class[contains(.,'detallePedido')]]";
    private static final String XPathLineaPrenda = "//tr/td[@align='left' and @height='30']/..";
    private static final String XPathIrATiendaButton = "(//div[@id[contains(.,'ListaDetail')]])[1]";
    private static final String XPathBackButton = "(//div[@id[contains(.,'ListaDetail')]])[2]";
    
    @Override
    public DetallePedido getTypeDetalle() {
    	return DetallePedido.Old;
    }
    
    @Override
    public boolean isPage(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathDivDetalle)));
    }
    
    @Override
    public boolean isPresentImporteTotal(String importeTotal, String codPais, WebDriver driver) {
        return (ImporteScreen.isPresentImporteInElements(importeTotal, codPais, "//td[@class='txt12ArialB']/../*", driver));
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
        clickAndWaitLoad(driver, By.xpath(XPathBackButton));
    }
    
    public void clickIrATiendaButton(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathIrATiendaButton));
    }    
}
