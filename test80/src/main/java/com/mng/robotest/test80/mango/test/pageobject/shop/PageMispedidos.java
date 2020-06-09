package com.mng.robotest.test80.mango.test.pageobject.shop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageMispedidos {

    static String XPathPanelPedidos = "//div[@id[contains(.,'panelPedidos')]]";
    
    /**
     * @return el xpath correspondiente al element cuya presencia indica que la lista de pedidos está vacía
     */
    public static String getXPATH_listaPedidosVacia() {
        //La última fila es la de la cabecera de la tabla (contiene el literal 'Código de pedido')
        return ("//tr[last()]//td[contains(.,'digo pedido')]");
    }

	public static boolean isPage(WebDriver driver) {
		return (state(Present, By.xpath(XPathPanelPedidos), driver).check());
	}

	public static boolean elementContainsText(WebDriver driver, String text) {
		String xpath = "//*[text()[contains(.,'" + text.toUpperCase() + "')] or text()[contains(.,'" + text.toLowerCase() + "')]]";
		return (state(Present, By.xpath(xpath), driver).check());
	}

	public static boolean listaPedidosVacia(WebDriver driver) {
		String xpath = getXPATH_listaPedidosVacia();
		return (state(Present, By.xpath(xpath), driver).check());
	}

}
