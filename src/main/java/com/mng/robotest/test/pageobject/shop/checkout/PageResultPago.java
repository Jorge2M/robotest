package com.mng.robotest.test.pageobject.shop.checkout;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageResultPago extends PageBase {
	
	private static final String XPATH_TEXT_CONFIRMACION_PAGO_ESTANDAR = "//*[@data-testid[contains(.,'confirmationText')] or @data-testid='purchaseConfirmation.confirmationText']";
	public static final String XPATH_DESCUBRIR_LO_ULTIMO_BUTTON = "//*[@data-testid[contains(.,'cta.goToMain')]]";
	public static final String XPATH_DATA_PEDIDO = "//*[@data-testid[contains(.,'purchaseData')]]"; 
	public static final String XPATH_CODIGO_PEDIDO_ESTANDAR = XPATH_DATA_PEDIDO + "//*[@data-testid[contains(.,'.orderId')]]";
	public static final String XPATH_CODIGO_PEDIDO_CONTRAREEMBOLSO_DESKTOP = "//div[@class='labels']//*[@class[contains(.,'data')] and string-length(text())=6]";
	public static final String XPATH_CODIGO_PEDIDO_CONTRAREEMBOLSO_MOBIL = "//div[@class[contains(.,'confirmation-summary-value')]]//p[string-length(text())=6]"; 
	public static final String XPATH_BUTTON_MIS_COMPRAS = "//button[@data-testid[contains(.,'goToMyPurchases')]]";
	public static final String XPATH_BLOCK_NEW_LOYALTY_POINTS = "//*[@data-testid[contains(.,'loyaltyPointsBlock')]]";

	public boolean checkUrl(int seconds) {
		for (int i=0; i<seconds; i++) {
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
		return state(Visible, XPATH_TEXT_CONFIRMACION_PAGO_ESTANDAR).wait(seconds).check();
	}

	public boolean isVisibleDescubrirLoUltimo() {
		return state(Visible, XPATH_DESCUBRIR_LO_ULTIMO_BUTTON).check();
	}
	
	public void clickDescubrirLoUltimo() {
		click(XPATH_DESCUBRIR_LO_ULTIMO_BUTTON).exec();
	}

	public String getCodigoPedido(int seconds) throws Exception {
		if (state(Present, XPATH_CODIGO_PEDIDO_ESTANDAR).wait(seconds).check()) {
			return getElement(XPATH_CODIGO_PEDIDO_ESTANDAR).getText();
		}
		return "";
	}

	public boolean isButtonMisCompras() {
		return state(Visible, XPATH_BUTTON_MIS_COMPRAS).check();
	}

	public void clickMisCompras() {
		click(XPATH_BUTTON_MIS_COMPRAS).exec();
	}

	public boolean isVisibleBlockNewLoyaltyPoints() {
		return state(Visible, XPATH_BLOCK_NEW_LOYALTY_POINTS).check();
	}
}
