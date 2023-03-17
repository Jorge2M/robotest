package com.mng.robotest.domains.manto.pageobjects;

import com.mng.robotest.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageBolsas extends PageBase {

	private static final String XPATH_LINEA = "//table[@width='100%']/tbody/tr[5]/td/input[@class='botones']";
	private static final String XPATH_MAIN_FORM = "//form[@action='/bolsas.faces']";
	
	private String getXpathLinkPedidoInBolsa(String pedidoManto) {
		return "//table//tr/td[1]/a[text()[contains(.,'" + pedidoManto + "')]]";
	}
	
	public String getXpathLinkIdCompraInBolsa(String pedidoManto) {
		String xpathPedido = getXpathLinkPedidoInBolsa(pedidoManto);
		return xpathPedido + "/../a[2]";
	}
		
	public String getXpathIdTpvInBolsa(String idTpv) {
		return "//table//tr/td[8]/span[text()[contains(.,'" + idTpv + "')]]";
	}
	
	public String getXpath_correoInBolsa(String correo) {
		return "//table//tr/td[7]/span[text()[contains(.,'" + correo.toLowerCase() + "')] or text()[contains(.,'" + correo.toUpperCase() + "')]]";
	}

	public boolean isPage() {
		return (state(Present, XPATH_MAIN_FORM).check());
	}

	public int getNumLineas() {
		return getElements(XPATH_LINEA).size();
	}
	
	public boolean presentLinkPedidoInBolsaUntil(String codigoPedido, int seconds) {
		String xpath = getXpathLinkPedidoInBolsa(codigoPedido);
		return state(Present, xpath).wait(seconds).check();
	}

	public boolean presentIdTpvInBolsa(String idTpv) {
		String xpath = getXpathIdTpvInBolsa(idTpv);
		return state(Present, xpath).check();
	}

	public boolean presentCorreoInBolsa(String correo) {
		String xpath = getXpath_correoInBolsa(correo);
		return state(Present, xpath).check();
	}

	public String getIdCompra(String idPedido) {
		String xpathIdCompra = getXpathLinkIdCompraInBolsa(idPedido);
		if (state(Present, xpathIdCompra).check()) {
			String textIdCompra = getElement(xpathIdCompra).getText();
			return (textIdCompra.substring(0, textIdCompra.indexOf(" ")));
		}
		return "";
	}
}
