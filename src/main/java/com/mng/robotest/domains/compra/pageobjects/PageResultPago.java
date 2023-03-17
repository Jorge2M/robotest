package com.mng.robotest.domains.compra.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;

import org.openqa.selenium.WebElement;

import com.mng.robotest.domains.base.PageBase;

public class PageResultPago extends PageBase {
	
	private static final String XPATH_TEXT_CONFIRMACION_PAGO_ESTANDAR = "//*[@data-testid[contains(.,'confirmationText')] or @data-testid='purchaseConfirmation.confirmationText']";
	public static final String XPATH_DESCUBRIR_LO_ULTIMO_BUTTON = "//*[@data-testid[contains(.,'cta.goToMain')]]";
	public static final String XPATH_DATA_PEDIDO = "//*[@data-testid[contains(.,'purchaseData')]]"; 
	public static final String XPATH_CODIGO_PEDIDO_ESTANDAR = XPATH_DATA_PEDIDO + "//*[@data-testid[contains(.,'.orderId')]]";
	public static final String XPATH_CODIGO_PEDIDO_CONTRAREEMBOLSO_DESKTOP = "//div[@class='labels']//*[@class[contains(.,'data')] and string-length(text())=6]";
	public static final String XPATH_CODIGO_PEDIDO_CONTRAREEMBOLSO_MOBIL = "//div[@class[contains(.,'confirmation-summary-value')]]//p[string-length(text())=6]"; 
	public static final String XPATH_BUTTON_MIS_COMPRAS = "//button[@data-testid[contains(.,'goToMyPurchases')]]";
	public static final String XPATH_MICROFRONTEND_LOYALTY = "//micro-frontend[@name='purchaseConfirmation']";
	public static final String XPATH_BLOCK_NEW_LOYALTY_POINTS = "//*[@data-testid[contains(.,'loyaltyPointsBlock')]]";
	public static final String XPATH_LINK_DESCUENTOS_Y_EXPERIENCIAS = "//*[@data-testid='mng-link']";

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

	public String getCodigoPedido(int seconds) {
		if (state(Present, XPATH_CODIGO_PEDIDO_ESTANDAR).wait(seconds).check()) {
			return getElement(XPATH_CODIGO_PEDIDO_ESTANDAR).getText();
		}
		return "";
	}

	public boolean isButtonMisCompras(int seconds) {
		return state(Visible, XPATH_BUTTON_MIS_COMPRAS).wait(seconds).check();
	}

	public void clickMisCompras() {
		click(XPATH_BUTTON_MIS_COMPRAS).exec();
		if (!state(Invisible, XPATH_BUTTON_MIS_COMPRAS).wait(1).check()) {
			click(XPATH_BUTTON_MIS_COMPRAS).type(javascript).exec();
		}
	}

	public boolean isVisibleBlockNewLoyaltyPoints() {
		return state(Visible, XPATH_BLOCK_NEW_LOYALTY_POINTS).check();
	}
	public int getLikesGenerated() {
		WebElement microLikes = getElement(XPATH_MICROFRONTEND_LOYALTY);
		if (microLikes!=null) {
			return Integer.valueOf(microLikes.getAttribute("likes"));
		}
		return 0;
	}
	
	public void clickLinkDescuentosExperiencias() {
		click(XPATH_LINK_DESCUENTOS_Y_EXPERIENCIAS).exec();
	}
}
