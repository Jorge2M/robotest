package com.mng.robotest.test80.mango.test.pageobject.shop.modales;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.utils.otras.Channel;
import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;


public class ModalDetalleMisCompras extends WebdrvWrapp {

	private final Channel channel;
	private final WebDriver driver;
	
    private static String XPathModalInfoArticulo = "//div[@id='productDetailsContent']";
    private static String XPathAspaForClose = "//button[@class[contains(.,'icofav-cerrar')]]";
    private static String XPathBuscarTallaTiendaButton = "//div[@id='findInShop']";
    private static String XPathReferenciaArticuloModal = "//li[@class='reference']";
    private static String XPathLinkToMisComprasDesktop = "//div[@class[contains(.,'shopping-breadcrumbs')]]";
    private static String XPathLinkToMisComprasMobil = "//div[@class[contains(.,'iconBack')]]";
    
    private ModalDetalleMisCompras(Channel channel, WebDriver driver) {
    	this.channel = channel;
    	this.driver = driver;
    }
    public static ModalDetalleMisCompras getNew(Channel channel, WebDriver driver) {
    	return new ModalDetalleMisCompras(channel, driver);
    }
    
	private String getXPathLinkToMisCompras() {
		switch (channel) {
		case desktop:
			return XPathLinkToMisComprasDesktop;
		case movil_web:
		default:
			return XPathLinkToMisComprasMobil;
		}
	}
    
    public boolean isVisible() {
        return (isElementVisible(driver, By.xpath(XPathModalInfoArticulo))); 
    }
    
    public void clickAspaForClose() {
        driver.findElement(By.xpath(XPathAspaForClose)).click();
    }
    
    public void clickBuscarTallaTiendaButton() throws Exception {
    	clickAndWaitLoad(driver, By.xpath(XPathBuscarTallaTiendaButton), 3);
    }

	public boolean isReferenciaValidaModal(String idArticulo) {
		String idArticuloModal = driver.findElement(By.xpath(XPathReferenciaArticuloModal)).getText();
		idArticuloModal = idArticuloModal.replace(" ", "");
		return idArticulo.replace(" ", "").equals(idArticuloModal);
	}

	public void gotoListaMisCompras() throws Exception {
		String xpath = getXPathLinkToMisCompras();
		clickAndWaitLoad(driver, By.xpath(xpath));
	}
}
