package com.mng.robotest.test80.mango.test.pageobject.shop.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;


public class PageResultPago extends WebdrvWrapp {

    static String XPathTextoConfirmacionPagoMobil = "//div[@class='confirmation']"; 
    static String XPathTextoConfirmacionPagoDesktop = "//*[@class[contains(.,'textoConfirmacion')] or @class[contains(.,'confirmation-header-title')] or @id[contains(.,'confirmacionContrareembolso')] or @class[contains(.,'titulos pasos')]]";
    static String XPathLinkSeguirDeShoppingMobil = "//div[@class='confirmation-link']//a";  
    static String XPathLinkSeguirDeShoppingDesktop = "//div[@class='button-wrapper']/a";
    static String XPathTextPedidoMobil = "//div[@class[contains(.,'confirmation-summary-value')]]//p[string-length(text())=6]"; 
    static String XPathTextPedidoDesktop = "//div[@class='labels']//*[@class[contains(.,'data')] and string-length(text())=6]";
    
    //xpath del link hacia los pedidos (sólo aparecen en la versión Desktop de la página)
    static String XPathLinkPedidosDesktop = "//a[@href[contains(.,'/account/orders')] or @href[contains(.,'/loginPedidos.faces')]]"; 
    static String XPathLinkMisComprasDesktop = "//a[@href[contains(.,'/mypurchases')]]";
	static String xpathBlockNewLoyaltyPoints = "//div[@class='simulate-likes']";
    
    /**
     * @return el xpath correspondiente al elemento que contiene el texto con la confirmación del pago
     */
    public static String getXPathTextoConfirmacionPago(Channel channel) {
        if (channel==Channel.movil_web) {
            return XPathTextoConfirmacionPagoMobil;
        }
        return XPathTextoConfirmacionPagoDesktop;
    }    
    
    /**
     * @return el xpath correspondiente al elemento lincable de "Seguir de shopping"
     */
    public static String getXPathLinkSeguirDeShopping(Channel channel) {
        if (channel==Channel.movil_web) {
            return XPathLinkSeguirDeShoppingMobil;
        }
        return XPathLinkSeguirDeShoppingDesktop;
    }
    
    /**
     * @return el xpath correspondiente al elemento que contiene el texto con el id del pedido
     */
    public static String getXPathTextPedido(Channel channel) {
        if (channel==Channel.movil_web) {
            return XPathTextPedidoMobil;
        }
        return XPathTextPedidoDesktop;
    }
    
    /**
     * @return si figura un texto de confirmación del pago (se espera un máximo de segundos)
     */
    public static boolean isVisibleTextoConfirmacionPago(WebDriver driver, Channel channel, int seconds) {
        return (isElementVisibleUntil(driver, By.xpath(getXPathTextoConfirmacionPago(channel)), seconds));
    }
    
    /**
     * Click elemento lincable de "Seguir de shopping"
     */
    public static void clickSeguirDeShopping(WebDriver driver, Channel channel) throws Exception {
        clickAndWaitLoad(driver, By.xpath(getXPathLinkSeguirDeShopping(channel)));
    }
    
    /**
     * @return si es clicable el elemento para "Seguir de shopping"
     */
    public static boolean isClickableSeguirDeShopping(WebDriver driver, Channel channel) {
        return (isElementClickable(driver, By.xpath(getXPathLinkSeguirDeShopping(channel))));
    }
    
    /**
     * @return el texto correspondiente al id del pedido (si no lo encuenta devuelve "")
     */
    public static String getCodigoPedido(WebDriver driver, Channel channel, int secondsWait) throws Exception {
        String codPedido = "";
        String xpathPedido = getXPathTextPedido(channel);
        if (isElementPresentUntil(driver, By.xpath(xpathPedido), secondsWait)) {
            codPedido = driver.findElements(By.xpath(xpathPedido)).get(0).getText();
        }
        return codPedido;
    }
    
    /**
     * @return si está presente el link hacia los pedidos (sólo aparece en la versión Desktop de la página)
     */
    public static boolean isLinkPedidosDesktop(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathLinkPedidosDesktop)));
    }
    
    public static void clickMisPedidos(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathLinkPedidosDesktop));
    }
    
    public static boolean isLinkMisComprasDesktop(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathLinkMisComprasDesktop)));
    }
    
    public static void clickMisCompras(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathLinkMisComprasDesktop));
    }
    
	public static boolean isVisibleBlockNewLoyaltyPoints(WebDriver driver) {
		return (WebdrvWrapp.isElementVisible(driver, By.xpath(xpathBlockNewLoyaltyPoints)));
	}
}
