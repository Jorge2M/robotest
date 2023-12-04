package com.mng.robotest.tests.domains.manto.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class PageBolsas extends PageBase {

	private static final String XP_LINEA = "//table[@width='100%']/tbody/tr[5]/td/input[@class='botones']";
	private static final String XP_MAIN_FORM = "//form[@action='/bolsas.faces']";
	
	private String getXpathLinkPedidoInBolsa(String pedidoManto) {
		return "//table//tr/td[1]/a[text()[contains(.,'" + pedidoManto + "')]]";
	}
	
	private String getXpathLinkIdCompraInBolsa(String pedidoManto) {
		String xpathPedido = getXpathLinkPedidoInBolsa(pedidoManto);
		return xpathPedido + "/../a[2]";
	}
		
	private String getXpathIdTpvInBolsa(String idTpv) {
		return "//table//tr/td[8]/span[text()[contains(.,'" + idTpv + "')]]";
	}
	
	private String getXpathCorreoInBolsa(String correo) {
		return "//table//tr/td[7]/span[text()[contains(.,'" + correo.toLowerCase() + "')] or text()[contains(.,'" + correo.toUpperCase() + "')]]";
	}

	public boolean isPage() {
		return state(PRESENT, XP_MAIN_FORM).check();
	}

	public int getNumLineas() {
		return getElements(XP_LINEA).size();
	}
	
	public boolean presentLinkPedidoInBolsaUntil(String codigoPedido, int seconds) {
		String xpath = getXpathLinkPedidoInBolsa(codigoPedido);
		return state(PRESENT, xpath).wait(seconds).check();
	}

	public boolean presentIdTpvInBolsa(String idTpv) {
		String xpath = getXpathIdTpvInBolsa(idTpv);
		return state(PRESENT, xpath).check();
	}

	public boolean presentCorreoInBolsa(String correo) {
		String xpath = getXpathCorreoInBolsa(correo);
		return state(PRESENT, xpath).check();
	}

	public String getIdCompra(String idPedido) {
		String xpathIdCompra = getXpathLinkIdCompraInBolsa(idPedido);
		if (state(PRESENT, xpathIdCompra).check()) {
			String textIdCompra = getElement(xpathIdCompra).getText();
			return textIdCompra.substring(0, textIdCompra.indexOf(" "));
		}
		return "";
	}
}
