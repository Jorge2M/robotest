package com.mng.robotest.tests.domains.compra.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class PageResultPago extends PageBase {
	
	private static final String XP_TEXT_CONFIRMACION_PAGO_ESTANDAR = "//*[@data-testid[contains(.,'confirmationText')] or @data-testid='purchaseConfirmation.confirmationText']";
	public static final String XP_DESCUBRIR_LO_ULTIMO_BUTTON = "//*[@data-testid[contains(.,'cta.goToMain')]]";
	public static final String XP_DATA_PEDIDO = "//*[@data-testid[contains(.,'purchaseData')]]"; 
	public static final String XP_CODIGO_PEDIDO_ESTANDAR = XP_DATA_PEDIDO + "//*[@data-testid[contains(.,'.orderId')]]";
	public static final String XP_CODIGO_PEDIDO_CONTRAREEMBOLSO_DESKTOP = "//div[@class='labels']//*[@class[contains(.,'data')] and string-length(text())=6]";
	public static final String XP_CODIGO_PEDIDO_CONTRAREEMBOLSO_MOBIL = "//div[@class[contains(.,'confirmation-summary-value')]]//p[string-length(text())=6]"; 
	public static final String XP_BUTTON_MIS_COMPRAS = "//button[@data-testid[contains(.,'goToMyPurchases')] or @data-testid[contains(.,'goToMyOrders')]]";
	public static final String XP_MICROFRONTEND_LOYALTY = "//micro-frontend[@name='purchaseConfirmation']";
	public static final String XP_BLOCK_NEW_LOYALTY_POINTS = "//*[@data-testid[contains(.,'loyaltyPointsBlock')]]";
	public static final String XP_LINK_DESCUBRIR_VENTAJAS = "//*[@data-testid='purchaseConfirmation.loyalty.club.link']";

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
		return state(VISIBLE, XP_TEXT_CONFIRMACION_PAGO_ESTANDAR).wait(seconds).check();
	}

	public boolean isVisibleDescubrirLoUltimo() {
		return state(VISIBLE, XP_DESCUBRIR_LO_ULTIMO_BUTTON).check();
	}
	
	public void clickDescubrirLoUltimo() {
		click(XP_DESCUBRIR_LO_ULTIMO_BUTTON).exec();
	}

	public String getCodigoPedido(int seconds) {
		if (state(PRESENT, XP_CODIGO_PEDIDO_ESTANDAR).wait(seconds).check()) {
			return getElement(XP_CODIGO_PEDIDO_ESTANDAR).getText();
		}
		return "";
	}

	public boolean isButtonMisCompras(int seconds) {
		return state(VISIBLE, XP_BUTTON_MIS_COMPRAS).wait(seconds).check();
	}

	public void clickMisCompras() {
		click(XP_BUTTON_MIS_COMPRAS).exec();
		if (!state(INVISIBLE, XP_BUTTON_MIS_COMPRAS).wait(1).check()) {
			click(XP_BUTTON_MIS_COMPRAS).type(JAVASCRIPT).exec();
		}
	}

	public boolean isVisibleBlockNewLoyaltyPoints() {
		return state(VISIBLE, XP_BLOCK_NEW_LOYALTY_POINTS).check();
	}
	public int getLikesGenerated() {
		var microLikes = getElement(XP_MICROFRONTEND_LOYALTY);
		if (microLikes!=null) {
			return Integer.valueOf(microLikes.getAttribute("likes"));
		}
		return 0;
	}
	
	public void clickLinkDescubrirVentajas() {
		state(VISIBLE, XP_LINK_DESCUBRIR_VENTAJAS).wait(2).check();
		click(XP_LINK_DESCUBRIR_VENTAJAS).exec();
	}
}
