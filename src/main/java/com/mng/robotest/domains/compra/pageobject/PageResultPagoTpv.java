package com.mng.robotest.domains.compra.pageobject;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.transversal.PageBase;

public class PageResultPagoTpv extends PageBase {
	
	private static final String XPATH_CABECERA_CONF_COMPRA = "//div[@class[contains(.,'details')]]/h2";
	private static final String XPATH_COD_PEDIDO = "//div[@id[contains(.,'num-pedido')]]/div[@class='valor']";
	private static final String XPATH_GASTOS_TRANSPORTE = "//div[@id='transporte']/div[@class='valor']";

	public boolean isPresentCabeceraConfCompra() {
		return state(Present, XPATH_CABECERA_CONF_COMPRA).check();
	}

	public boolean isVisibleCodPedido() {
		return state(Visible, XPATH_COD_PEDIDO).check();
	}

	public String getCodigoPedido() {
		if (isVisibleCodPedido()) {
			return getElement(XPATH_COD_PEDIDO).getText();
		}
		return "";
	}
	
	public boolean isGastoTransporteAcero() {
		return getElement(XPATH_GASTOS_TRANSPORTE).getText().compareTo("0 €")==0;
	}
}
