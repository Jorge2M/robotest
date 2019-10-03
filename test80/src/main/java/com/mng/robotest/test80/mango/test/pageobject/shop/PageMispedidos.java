package com.mng.robotest.test80.mango.test.pageobject.shop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.webdriverwrapper.WebdrvWrapp;


public class PageMispedidos extends WebdrvWrapp {

    static String XPathPanelPedidos = "//div[@id[contains(.,'panelPedidos')]]";
    
    /**
     * @return el xpath correspondiente al element cuya presencia indica que la lista de pedidos está vacía
     */
    public static String getXPATH_listaPedidosVacia() {
        //La última fila es la de la cabecera de la tabla (contiene el literal 'Código de pedido')
        return ("//tr[last()]//td[contains(.,'digo pedido')]");
    }
    
    /**
     * @return indicador de si realmente se trata de la página de Mis Pedidos
     */
    public static boolean isPage(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathPanelPedidos))); 
    }
    
    /**
     * @return si algún elemento de la página contiene el texto indicado ya sea en mayúscula o minúscula
     */
    public static boolean elementContainsText(WebDriver driver, String text) {
        return (isElementPresent(driver, By.xpath("//*[text()[contains(.,'" + text.toUpperCase() + "')] or text()[contains(.,'" + text.toLowerCase() + "')]]")));
    }
    
    /**
     * @return si la lista de pedidos está vacía
     */
    public static boolean listaPedidosVacia(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(getXPATH_listaPedidosVacia()))); 
    }
    
}
