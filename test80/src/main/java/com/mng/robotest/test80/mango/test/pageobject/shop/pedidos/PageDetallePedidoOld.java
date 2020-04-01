package com.mng.robotest.test80.mango.test.pageobject.shop.pedidos;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.service.webdriver.pageobject.PageObjTM;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;


public class PageDetallePedidoOld extends PageObjTM implements PageDetallePedido {
	
    private static final String XPathDivDetalle = "//div[@class[contains(.,'detallePedido')]]";
    private static final String XPathLineaPrenda = "//tr/td[@align='left' and @height='30']/..";
    private static final String XPathIrATiendaButton = "(//div[@id[contains(.,'ListaDetail')]])[1]";
    private static final String XPathBackButton = "(//div[@id[contains(.,'ListaDetail')]])[2]";
    
    public PageDetallePedidoOld(WebDriver driver) {
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
        return (ImporteScreen.isPresentImporteInElements(importeTotal, codPais, "//td[@class='txt12ArialB']/../*", driver));
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
    
    public void clickIrATiendaButton() {
    	click(By.xpath(XPathIrATiendaButton)).exec();
    }    
}
