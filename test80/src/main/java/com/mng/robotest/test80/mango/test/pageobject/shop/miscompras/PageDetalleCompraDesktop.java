package com.mng.robotest.test80.mango.test.pageobject.shop.miscompras;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.test80.mango.test.pageobject.shop.miscompras.PageMisCompras.TypeTicket;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageDetalleCompraDesktop extends PageDetalleCompra {
	
    private static String XPathDataTicket = "//div[@class[contains(.,'dGt6h')]]"; //React
    private static String XPathIdTicket = XPathDataTicket + "//*[@class[contains(.,'_3660d')]]"; //React
    private static String XPathImporte = XPathDataTicket + "//div[@class[contains(.,'_3rNmb')]]/span[2]"; //React
    private static String XPathDireccionEnvio = XPathDataTicket + "/div[2]/div[2]"; //React 
    //private static String XPathCodigoBarrasImg = XPathDataTicket + "//div[@class='code']/img";
    private static String XPathArticulo = "//div[@class[contains(.,'_2K1yc')]]"; //React
    private static String XPathLinkToMisCompras = "//button[@class='fN9KS']"; //React
    
    public PageDetalleCompraDesktop(Channel channel, WebDriver driver) {
    	super(channel, driver);
    }

	private String getXPathArticulo(int position) {
		return "(" + XPathArticulo + ")[" + position + "]";
	}
	private String getXPathLinkArticulo(int position) {
		String xpathArticulo = getXPathArticulo(position);
		return xpathArticulo + "//button";
	}
	private String getXPathDataArticulo(int position) {
		String xpathArticulo = getXPathArticulo(position);
		return xpathArticulo + "//div[@class[contains(.,'_2NEwt')]]"; //React
	}
    private String getXPathReferenciaArticulo(int posArticulo) {
        String xpathDataArticulo = getXPathDataArticulo(posArticulo);
        return (xpathDataArticulo + "//div[@class[contains(.,'sg-overline-small')]]"); //React
    }
    private String getXPathNombreArticulo(int posArticulo) {
        String xpathDataArticulo = getXPathDataArticulo(posArticulo);
        return (xpathDataArticulo + "//button");
    }    
    private String getXPathPrecioArticulo(int posArticulo) {
        String xpathDataArticulo = getXPathDataArticulo(posArticulo);
        return (xpathDataArticulo + "//div[@class[contains(.,'_2H9YI')]]//div[not(@class='_1LGHV')]"); //React
    }

    @Override
    public boolean isPage() {
    	return isVisibleDataTicket(2);
    }
    @Override
    public boolean isPresentImporteTotal(String importeTotal, String codPais) {
    	String importe = getImporte();
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
    public String getIdTicket(TypeTicket typeTicket) {
        String dataNumTicket = driver.findElement(By.xpath(XPathIdTicket)).getText();
        return (getDataRightFrom(":", dataNumTicket));
    }
    @Override
    public boolean isVisibleIdTicket(int maxSeconds) {
    	return state(State.Visible, By.xpath(XPathIdTicket)).wait(maxSeconds).check();
    }
    @Override
    public String getImporte() {
    	state(State.Visible, By.xpath(XPathImporte)).wait(2).check();
        String importe = driver.findElement(By.xpath(XPathImporte)).getText();
        return importe.replaceAll("[^\\d., ]", "");  //Eliminamos la divisa;
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
        String xpathLinkArticulo = getXPathLinkArticulo(posArticulo);
        driver.findElement(By.xpath(xpathLinkArticulo)).click();
    }
    @Override
	public void gotoListaMisCompras() {
    	ModalDetalleArticuloDesktop modalArticulo = new ModalDetalleArticuloDesktop(driver);
    	if (modalArticulo.isVisible(0)) {
    		modalArticulo.clickAspaForClose();
    		modalArticulo.isInvisible(2);
    	}
		click(By.xpath(XPathLinkToMisCompras)).exec();
	}

    public String getDireccionEnvio() {
        return (driver.findElement(By.xpath(XPathDireccionEnvio)).getText());
    }

//    public boolean isVisibleCodigoBarrasImg() {
//    	if (state(Visible, By.xpath(XPathCodigoBarrasImg)).check()) {
//            //Consideramos que la imagen ha de tener unas dimensiones mínimas de 30x50 para ser visible
//            int width=driver.findElement(By.xpath(XPathCodigoBarrasImg)).getSize().getWidth();
//            int height=driver.findElement(By.xpath(XPathCodigoBarrasImg)).getSize().getHeight();
//            return (width>=30 && height>=50);
//        }
//        
//        return false;
//    }
}