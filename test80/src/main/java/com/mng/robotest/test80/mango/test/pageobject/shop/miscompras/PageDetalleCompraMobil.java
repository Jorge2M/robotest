package com.mng.robotest.test80.mango.test.pageobject.shop.miscompras;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.shop.miscompras.PageMisCompras.TypeTicket;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageDetalleCompraMobil extends PageDetalleCompra {
	
	//private static String XPathDataTicket = "//div[@class[contains(.,'U8w6k')]]"; //React
	//private static String XPathItemDataTicket = "//*[@data-testid[contains(.,'_1iQV_')]]";
    //private static String XPathIdTicketOnline = XPathItemDataTicketOnline + "[1]/span";
    private static String XPathIdTicket = "//*[@data-testid[contains(.,'order.orderId')]]";
	private static String XPathLineaImporte = "//*[@data-testid[contains(.,'order.price')]]";
	//private static String XPathLineaImporteTienda = "(" + XPathItemDataTicketTienda + ")[2]";
	private static String XPathArticulo = "//*[@data-testid='myPurchases.detail.product']";
    private static String XPathLinkToMisCompras = "//button/*[@class[contains(.,'icon-fill-prev')]]/.."; 
    
	private String getXPathArticulo(int position) {
		return "(" + XPathArticulo + ")[" + position + "]";
	}
    private String getXPathReferenciaArticulo(int posArticulo) {
        String xpathArticulo = getXPathArticulo(posArticulo);
        return (xpathArticulo + "//*[@data-testid[contains(.,'detail.reference')]]");
    }
    private String getXPathNombreArticulo(int posArticulo) {
        String xpathArticulo = getXPathArticulo(posArticulo);
        return (xpathArticulo + "//*[@data-testid[contains(.,'product.openModal')]]");
    }
    private String getXPathPrecioArticulo(int posArticulo) {
        String xpathArticulo = getXPathArticulo(posArticulo);        
        return (xpathArticulo + "//*[@data-testid[contains(.,'product.paidPrice')]]");
    }
    
    public PageDetalleCompraMobil(Channel channel, WebDriver driver) {
    	super(channel, driver);
    }
    
    @Override
    public boolean isPage() {
    	return isVisibleDataTicket(2);
    }
    @Override
    public boolean isPresentImporteTotal(String importeTotal, String codPais) {
    	String importe = getImporte().replaceAll("[^\\d.,]", "");  //Eliminamos la divisa;
    	return (importe.compareTo(importeTotal)==0);
    }
    @Override
    public boolean isVisiblePrendaUntil(int maxSeconds) {
    	return getNumPrendas()>0;
    }
    @Override
    public void clickBackButton(Channel channel) {
    	gotoListaMisCompras();
    }
    @Override
    public int getNumPrendas() {
        return (driver.findElements(By.xpath(XPathArticulo)).size());
    }
    @Override
    public boolean isVisibleDataTicket(int maxSeconds) {
    	return (state(Visible, By.xpath(XPathIdTicket)).wait(maxSeconds).check());
    }
    @Override
    public boolean isVisibleIdTicket(int maxSeconds) {
    	return state(State.Visible, By.xpath(XPathIdTicket)).wait(maxSeconds).check();
    }
    @Override
    public String getIdTicket(TypeTicket typeTicket) {
        return PageObjTM.getElementWeb(By.xpath(XPathIdTicket), driver).getText();
    }
    @Override
    public String getImporte() {
    	state(State.Visible, By.xpath(XPathLineaImporte)).wait(2).check();
        return PageObjTM.getElementWeb(By.xpath(XPathLineaImporte), driver).getText();
    }
    @Override
    public String getReferenciaArticulo(int posArticulo) {
        String xpathReferencia = getXPathReferenciaArticulo(posArticulo);
        return (driver.findElement(By.xpath(xpathReferencia)).getText().replaceAll("\\D+",""));
    }
    @Override
    public String getNombreArticulo(int posArticulo) {
        String xpathNombre = getXPathNombreArticulo(posArticulo);
        return (driver.findElement(By.xpath(xpathNombre)).getText());
    }
    @Override
    public String getPrecioArticulo(int posArticulo) {
        String xpathPrecio = getXPathPrecioArticulo(posArticulo);
        return (driver.findElement(By.xpath(xpathPrecio)).getText());
    }
    @Override
    public void selectArticulo(int posArticulo) {
        String xpathArticulo = getXPathArticulo(posArticulo) + "//button";
        driver.findElement(By.xpath(xpathArticulo)).click();
    }
    @Override
	public void gotoListaMisCompras() {
		click(By.xpath(XPathLinkToMisCompras)).exec();
	}
}
