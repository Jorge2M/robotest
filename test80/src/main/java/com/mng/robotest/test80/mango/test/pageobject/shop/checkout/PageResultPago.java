package com.mng.robotest.test80.mango.test.pageobject.shop.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago.TypePago;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageResultPago extends PageObjTM {
	
	private final TypePago typePago;
	private final Channel channel;
	
	//public final static String XPathTextoConfirmacionPagoEstandar = "//h2[@class[contains(.,'fdHRp')]]"; //React
	private final static String XpathTextConfirmacionPagoEstandar = "//*[@data-testid[contains(.,'confirmationText')]]";
	
	public final static String XPathTextoConfirmacionPagoContrareembolsoDesktop = "//span[@class[contains(.,'titulos pasos')]]"; 
	public final static String XPathTextoConfirmacionPagoContrareembolsoMobil = "//div[@class='confirmation']";
	
    //public final static String XPathDescubrirLoUltimoButton = "//div[@class[contains(.,'_2koW5')]]/button"; //React
    public final static String XPathDescubrirLoUltimoButton = "//*[@data-testid[contains(.,'cta.goToMain')]]";
    
    //public final static String XPathDataPedido = "//div[@class[contains(.,'_3HaKt')]]"; //React
    public final static String XPathDataPedido = "//*[@data-testid[contains(.,'purchaseData')]]"; 
    
    //public final static String XPathCodigoPedidoEstandar = XPathDataPedido + "//div[@class[contains(.,'_1T2hc')]]/div[3]"; //React
    public final static String XPathCodigoPedidoEstandar = XPathDataPedido + "//*[@data-testid[contains(.,'purchaseIdRow')]]";
    
    public final static String XPathCodigoPedidoContrareembolsoDesktop = "//div[@class='labels']//*[@class[contains(.,'data')] and string-length(text())=6]";
    public final static String XPathCodigoPedidoContrareembolsoMobil = "//div[@class[contains(.,'confirmation-summary-value')]]//p[string-length(text())=6]"; 
    
    public final static String XPathLinkMisCompras = "//a[@href[contains(.,'/mypurchases')]]";
    public final static String XPathLinkPedidos = "//a[@href[contains(.,'/account/orders')]]";
    
    //public final static String XPathBlockNewLoyaltyPoints = "//div[@class[contains(.,'_2h1Ha')]]"; //React
    public final static String XPathBlockNewLoyaltyPoints = "//*[@data-testid[contains(.,'loyaltyPointsBlock')]]";
    
    public PageResultPago(TypePago typePago, Channel channel, WebDriver driver) {
    	super(driver);
    	this.typePago = typePago;
    	this.channel = channel;
    }
    
    private String getXPathTextoConfirmacionPago() {
    	switch (typePago) {
    	case ContraReembolso:
    		switch (channel) {
    		case desktop:
    			return XPathTextoConfirmacionPagoContrareembolsoDesktop;
    		default:
    			return XPathTextoConfirmacionPagoContrareembolsoMobil;
    		}
    	default:
    		return XpathTextConfirmacionPagoEstandar;
    	}
    }
    
    private String getXPathCodigoPedido() {
    	switch (typePago) {
    	case ContraReembolso:
    		switch (channel) {
    		case desktop:
    			return XPathCodigoPedidoContrareembolsoDesktop;
    		default:
    			return XPathCodigoPedidoContrareembolsoMobil;
    		}
    	default:
    		return XPathCodigoPedidoEstandar;
    	}
    }

    public boolean checkUrl(int maxSeconds) {
    	for (int i=0; i<maxSeconds; i++) {
    		if (driver.getCurrentUrl().contains("resultadoOK")) {
    			return true;
    		}
    		waitMillis(1000);
    	}
    	return false;
    }
    
	public boolean isVisibleTextoConfirmacionPago(int seconds) {
		String xpath = getXPathTextoConfirmacionPago();
		return (state(Visible, By.xpath(xpath)).wait(seconds).check());
	}

	public boolean isVisibleDescubrirLoUltimo() {
		return state(Visible, By.xpath(XPathDescubrirLoUltimoButton)).check();
	}
	
	public void clickDescubrirLoUltimo() {
		click(By.xpath(XPathDescubrirLoUltimoButton)).exec();
	}

	public String getCodigoPedido(int seconds) throws Exception {
		By codigoPedidoBy = By.xpath(getXPathCodigoPedido()); 
		if (state(Present, codigoPedidoBy).wait(seconds).check()) {
			return driver.findElement(codigoPedidoBy).getText();
		}
		return "";
	}

	public boolean isLinkPedidos() {
		return (state(Visible, By.xpath(XPathLinkPedidos)).check());
	}

	public void clickMisPedidos() {
		click(By.xpath(XPathLinkPedidos)).exec();
	}

	public boolean isLinkMisCompras() {
		return (state(Visible, By.xpath(XPathLinkMisCompras)).check());
	}

	public void clickMisCompras() {
		click(By.xpath(XPathLinkMisCompras)).exec();
	}

	public boolean isVisibleBlockNewLoyaltyPoints() {
		return (state(Visible, By.xpath(XPathBlockNewLoyaltyPoints)).check());
	}
}
