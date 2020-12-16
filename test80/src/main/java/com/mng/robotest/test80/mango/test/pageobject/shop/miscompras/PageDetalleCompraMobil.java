package com.mng.robotest.test80.mango.test.pageobject.shop.miscompras;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.robotest.test80.mango.test.pageobject.shop.miscompras.PageMisCompras.TypeTicket;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageDetalleCompraMobil extends PageDetalleCompra {
	
	private static String XPathDataTicket = "//div[@class[contains(.,'U8w6k')]]"; //React
	private static String XPathItemDataTicketOnline = XPathDataTicket + "//*[@class[contains(.,'_1iQV_')]]";
	private static String XPathItemDataTicketTienda = XPathDataTicket + "//span[@class[contains(.,'_2glQv')]]"; //React
    private static String XPathIdTicketOnline = XPathItemDataTicketOnline + "[1]/span";
    private static String XPathIdTicketTienda = XPathItemDataTicketTienda + "[1]/span";
	private static String XPathLineaImporteOnline = "(" + XPathItemDataTicketOnline + ")[2]";
	private static String XPathLineaImporteTienda = "(" + XPathItemDataTicketTienda + ")[2]";
	private static String XPathArticulo = "//div[@class[contains(.,'_17bwV')]]"; //React
    private static String XPathLinkToMisCompras = "//button/*[@class[contains(.,'icon-fill-prev')]]/.."; 
    
	private String getXPathArticulo(int position) {
		return "(" + XPathArticulo + ")[" + position + "]";
	}
    private String getXPathReferenciaArticulo(int posArticulo) {
        String xpathArticulo = getXPathArticulo(posArticulo);
        return (xpathArticulo + "//div[@class='sg-overline-small']"); //React
    }
    private String getXPathNombreArticulo(int posArticulo) {
        String xpathArticulo = getXPathArticulo(posArticulo);
        return (xpathArticulo + "//button[@class[contains(.,'sg-subtitle-small')]]");
    }
    private String getXPathPrecioArticulo(int posArticulo) {
        String xpathArticulo = getXPathArticulo(posArticulo);        
        return (xpathArticulo + "//div[@class[contains(.,'_2H9YI')]]"); //React
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
    	return 
    		state(State.Visible, By.xpath(XPathIdTicketTienda)).wait(maxSeconds).check() ||
    		state(State.Visible, By.xpath(XPathIdTicketOnline)).wait(maxSeconds).check();
    }
    @Override
    public String getIdTicket(TypeTicket typeTicket) {
        WebElement idTicket = PageObjTM.getElementWeb(By.xpath(XPathIdTicketOnline), driver);
        if (idTicket!=null) {
        	return idTicket.getText();
        }
        return driver.findElement(By.xpath(XPathIdTicketTienda)).getText();
    }
    @Override
    public String getImporte() {
    	state(State.Visible, By.xpath(XPathLineaImporteOnline)).wait(2).check();
        WebElement linImport = PageObjTM.getElementWeb(By.xpath(XPathLineaImporteOnline), driver);
        if (linImport!=null) {
        	return linImport.getText();
        }
        String importe = driver.findElement(By.xpath(XPathLineaImporteTienda)).getText();
        return getDataRightFrom(": ", importe);
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
