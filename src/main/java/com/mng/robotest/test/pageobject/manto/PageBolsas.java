package com.mng.robotest.test.pageobject.manto;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageBolsas {

	static String XPathLinea = "//table[@width='100%']/tbody/tr[5]/td/input[@class='botones']";
	static String XPathMainForm = "//form[@action='/bolsas.faces']";
	
	public static String getXpath_linkPedidoInBolsa(String pedidoManto) {
		return ("//table//tr/td[1]/a[text()[contains(.,'" + pedidoManto + "')]]");
	}
	
	public static String getXpath_linkIdCompraInBolsa(String pedidoManto) {
		String xpathPedido = getXpath_linkPedidoInBolsa(pedidoManto);
		return (xpathPedido + "/../a[2]");
	}
		
	/**
	 * @param idTpv
	 * @return el xpath correspondiente al elemento de un tpv concreto en la lista de bolsas
	 */
	public static String getXpath_idTpvInBolsa(String idTpv) {
		return ("//table//tr/td[8]/span[text()[contains(.,'" + idTpv + "')]]");
	}
	
	/**
	 * @param correo
	 * @return el xpath correspondiente al elemento de un correo concreto en la lista de bolsas
	 */
	public static String getXpath_correoInBolsa(String correo) {
		return ("//table//tr/td[7]/span[text()[contains(.,'" + correo.toLowerCase() + "')] or text()[contains(.,'" + correo.toUpperCase() + "')]]");
	}

	public static boolean isPage(WebDriver driver) {
		return (state(Present, By.xpath(XPathMainForm), driver).check());
	}

	/**
	 * @return el número de líneas de bolsa que aparecen en pantalla
	 */
	public static int getNumLineas(WebDriver driver) {
		return (driver.findElements(By.xpath(XPathLinea)).size());
	}
	
	public static boolean presentLinkPedidoInBolsaUntil(String codigoPedido, int maxSecondsToWait, WebDriver driver) {
		String xpath = getXpath_linkPedidoInBolsa(codigoPedido);
		return (state(Present, By.xpath(xpath), driver)
				.wait(maxSecondsToWait).check());
	}

	public static boolean presentIdTpvInBolsa(WebDriver driver, String idTpv) {
		String xpath = getXpath_idTpvInBolsa(idTpv);
		return (state(Present, By.xpath(xpath), driver).check());
	}

	public static boolean presentCorreoInBolsa(WebDriver driver, String correo) {
		String xpath = getXpath_correoInBolsa(correo);
		return (state(Present, By.xpath(xpath), driver).check());
	}

	public static String getIdCompra(String idPedido, WebDriver driver) {
		String xpathIdCompra = getXpath_linkIdCompraInBolsa(idPedido);
		if (state(Present, By.xpath(xpathIdCompra), driver).check()) {
			String textIdCompra = driver.findElement(By.xpath(xpathIdCompra)).getText();
			return (textIdCompra.substring(0, textIdCompra.indexOf(" ")));
		}
		return "";
	}
}
