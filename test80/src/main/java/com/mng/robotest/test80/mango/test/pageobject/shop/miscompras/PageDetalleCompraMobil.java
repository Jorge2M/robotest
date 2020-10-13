package com.mng.robotest.test80.mango.test.pageobject.shop.miscompras;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.shop.miscompras.PageMisCompras.TypeTicket;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageDetalleCompraMobil extends PageDetalleCompra {
	
	private static String XPathDataTicket = "//div[@class='_3vFiY' or @class='_3KavU']"; //React
	private static String XPathItemDataTicket = XPathDataTicket + "//*[@class[contains(.,'_1rWLb')] or @class[contains(.,'wcdEz')]]"; //React
    private static String XPathIdTicket = XPathItemDataTicket + "[1]/span";
	private static String XPathLineaImporte = XPathItemDataTicket + "[2]//span[@class[contains(.,'sg-subtitle-small')]]";
	private static String XPathArticulo = "//div[@class[contains(.,'_1cg73 ')]]"; //React
    private static String XPathLinkToMisCompras = "//button/*[@class[contains(.,'icon-fill-prev')]]/.."; 
    
	private String getXPathArticulo(int position) {
		return "(" + XPathArticulo + ")[" + position + "]";
	}
    private String getXPathReferenciaArticulo(int posArticulo) {
        String xpathArticulo = getXPathArticulo(posArticulo);
        return (xpathArticulo + "//div[@class[contains(.,'_13V8z')]]"); //React
    }
    private String getXPathNombreArticulo(int posArticulo) {
        String xpathArticulo = getXPathArticulo(posArticulo);
        return (xpathArticulo + "//button[@class[contains(.,'sg-subtitle-small')]]");
    }
    private String getXPathPrecioArticulo(int posArticulo) {
        String xpathArticulo = getXPathArticulo(posArticulo);        
        return (xpathArticulo + "//div[@class[contains(.,'saSFe')]]//span[last()]"); //React
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
    	return (state(Visible, By.xpath(XPathDataTicket)).wait(maxSeconds).check());
    }
    @Override
    public boolean isVisibleIdTicket(int maxSeconds) {
    	return state(State.Visible, By.xpath(XPathIdTicket)).wait(maxSeconds).check();
    }
    @Override
    public String getIdTicket(TypeTicket typeTicket) {
    	state(State.Visible, By.xpath(XPathIdTicket)).wait(5).check();
        return driver.findElement(By.xpath(XPathIdTicket)).getText();
    }
    @Override
    public String getImporte() {
        return driver.findElement(By.xpath(XPathLineaImporte)).getText();
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
