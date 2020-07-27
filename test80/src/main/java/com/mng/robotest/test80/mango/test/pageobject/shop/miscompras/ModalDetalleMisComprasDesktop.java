package com.mng.robotest.test80.mango.test.pageobject.shop.miscompras;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class ModalDetalleMisComprasDesktop extends PageObjTM {

	private final Channel channel;
	
    private static String XPathModalInfoArticulo = "//div[@id='productDetailsContent']";
    private static String XPathAspaForClose = "//button[@class[contains(.,'icofav-cerrar')]]";
    private static String XPathBuscarTallaTiendaButton = "//div[@id='findInShop']";
    private static String XPathReferenciaArticuloModal = "//li[@class='reference']";
    private static String XPathLinkToMisComprasDesktop = "//div[@class[contains(.,'shopping-breadcrumbs')]]";
    private static String XPathLinkToMisComprasMobil = "//div[@class[contains(.,'iconBack')]]";
    
    private ModalDetalleMisComprasDesktop(Channel channel, WebDriver driver) {
    	super(driver);
    	this.channel = channel;
    }
    public static ModalDetalleMisComprasDesktop getNew(Channel channel, WebDriver driver) {
    	return new ModalDetalleMisComprasDesktop(channel, driver);
    }
    
	private String getXPathLinkToMisCompras() {
		switch (channel) {
		case desktop:
			return XPathLinkToMisComprasDesktop;
		case mobile:
		default:
			return XPathLinkToMisComprasMobil;
		}
	}
    
    public boolean isVisible() {
    	return (state(Visible, By.xpath(XPathModalInfoArticulo)).check());
    }
    
    public void clickAspaForClose() {
        driver.findElement(By.xpath(XPathAspaForClose)).click();
    }
    
    public void clickBuscarTallaTiendaButton() {
    	click(By.xpath(XPathBuscarTallaTiendaButton)).waitLoadPage(3).exec();
    }

	public boolean isReferenciaValidaModal(String idArticulo) {
		String idArticuloModal = driver.findElement(By.xpath(XPathReferenciaArticuloModal)).getText();
		idArticuloModal = idArticuloModal.replace(" ", "");
		return idArticulo.replace(" ", "").equals(idArticuloModal);
	}

	public void gotoListaMisCompras() {
		String xpath = getXPathLinkToMisCompras();
		click(By.xpath(xpath)).exec();
	}
}
