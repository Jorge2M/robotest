package com.mng.robotest.test80.mango.test.pageobject.manto;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.webdriverwrapper.WebdrvWrapp;


public class PageBolsas extends WebdrvWrapp {

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
    
    /**
     * @param driver
     * @return si se trata de la página de bolsas de manto
     */
    public static boolean isPage(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathMainForm)));
    }
    
    /**
     * @return el número de líneas de bolsa que aparecen en pantalla
     */
    public static int getNumLineas(WebDriver driver) {
        return (driver.findElements(By.xpath(XPathLinea)).size());
    }
    
    /**
     * @param driver
     * @param codigoPedido
     * @return si en la lista de bolsas existe el link a un determinado pedido
     */
    public static boolean presentLinkPedidoInBolsaUntil(String codigoPedido, int maxSecondsToWait, WebDriver driver) {
        String xpath = getXpath_linkPedidoInBolsa(codigoPedido);
        return (isElementPresentUntil(driver, By.xpath(xpath), maxSecondsToWait));    
    }
    
    /**
     * @param driver
     * @param idTpv
     * @return si en la lista de bolsas existe un determinado idTpv
     */
    public static boolean presentIdTpvInBolsa(WebDriver driver, String idTpv) {
        String xpath = getXpath_idTpvInBolsa(idTpv);
        return (isElementPresent(driver, By.xpath(xpath)));    
    }
    
    /**
     * @param driver
     * @param correo
     * @return si en la lista de bolsas existe un determinado correo
     */
    public static boolean presentCorreoInBolsa(WebDriver driver, String correo) {
        String xpath = getXpath_correoInBolsa(correo);
        return (isElementPresent(driver, By.xpath(xpath)));    
    }    
    
    public static String getIdCompra(String idPedido, WebDriver driver) {
        String xpathIdCompra = getXpath_linkIdCompraInBolsa(idPedido);
        if (isElementPresent(driver, By.xpath(xpathIdCompra))) {
            String textIdCompra = driver.findElement(By.xpath(xpathIdCompra)).getText();
            return (textIdCompra.substring(0, textIdCompra.indexOf(" ")));
        }
            
        return "";
    }
}
