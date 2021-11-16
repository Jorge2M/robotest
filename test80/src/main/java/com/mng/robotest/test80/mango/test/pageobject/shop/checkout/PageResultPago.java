package com.mng.robotest.test80.mango.test.pageobject.shop.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.mng.robotest.test80.mango.test.beans.Pago.TypePago;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageResultPago extends PageObjTM {
	
	private final Channel channel;
	private final static String XpathTextConfirmacionPagoEstandar = "//*[@data-testid[contains(.,'confirmationText')] or @data-testid='purchaseConfirmation.confirmationText']";
	public final static String XPathDescubrirLoUltimoButton = "//*[@data-testid[contains(.,'cta.goToMain')]]";
	public final static String XPathDataPedido = "//*[@data-testid[contains(.,'purchaseData')]]"; 
	public final static String XPathCodigoPedidoEstandar = XPathDataPedido + "//*[@data-testid[contains(.,'.orderId')]]";
	public final static String XPathCodigoPedidoContrareembolsoDesktop = "//div[@class='labels']//*[@class[contains(.,'data')] and string-length(text())=6]";
	public final static String XPathCodigoPedidoContrareembolsoMobil = "//div[@class[contains(.,'confirmation-summary-value')]]//p[string-length(text())=6]"; 
	public final static String XPathButtonMisCompras = "//button[@data-testid[contains(.,'goToMyPurchases')]]";
	public final static String XPathBlockNewLoyaltyPoints = "//*[@data-testid[contains(.,'loyaltyPointsBlock')]]";

	public PageResultPago(TypePago typePago, Channel channel, WebDriver driver) {
		super(driver);
		this.channel = channel;
	}

	public boolean checkUrl(int maxSeconds) {
		for (int i=0; i<maxSeconds; i++) {
			if (channel.isDevice()) {
				if (driver.getCurrentUrl().contains("success.faces")) {
					return true;
				}
			} else {
				if (driver.getCurrentUrl().contains("resultadoOK")) {
					return true;
				}
			}
			waitMillis(1000);
		}
		return false;
	}

	public boolean isVisibleTextoConfirmacionPago(int seconds) {
		return (state(Visible, By.xpath(XpathTextConfirmacionPagoEstandar)).wait(seconds).check());
	}

	public boolean isVisibleDescubrirLoUltimo() {
		return state(Visible, By.xpath(XPathDescubrirLoUltimoButton)).check();
	}
	
	public void clickDescubrirLoUltimo() {
		click(By.xpath(XPathDescubrirLoUltimoButton)).exec();
	}

	public String getCodigoPedido(int seconds) throws Exception {
		By codigoPedidoBy = By.xpath(XPathCodigoPedidoEstandar); 
		if (state(Present, codigoPedidoBy).wait(seconds).check()) {
			return driver.findElement(codigoPedidoBy).getText();
		}
		return "";
	}

	public boolean isButtonMisCompras() {
		return (state(Visible, By.xpath(XPathButtonMisCompras)).check());
	}

	public void clickMisCompras() {
		click(By.xpath(XPathButtonMisCompras)).exec();
	}

	public boolean isVisibleBlockNewLoyaltyPoints() {
		return (state(Visible, By.xpath(XPathBlockNewLoyaltyPoints)).check());
	}
}
