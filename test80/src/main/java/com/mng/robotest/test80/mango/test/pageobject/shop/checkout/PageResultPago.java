package com.mng.robotest.test80.mango.test.pageobject.shop.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageResultPago {

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

	public static boolean isVisibleTextoConfirmacionPago(WebDriver driver, Channel channel, int seconds) {
		String xpath = getXPathTextoConfirmacionPago(channel);
		return (state(Visible, By.xpath(xpath), driver).wait(seconds).check());
	}

	public static void clickSeguirDeShopping(WebDriver driver, Channel channel) {
		click(By.xpath(getXPathLinkSeguirDeShopping(channel)), driver).exec();
	}

	public static boolean isClickableSeguirDeShopping(WebDriver driver, Channel channel) {
		String xpath = getXPathLinkSeguirDeShopping(channel);
		return (state(Clickable, By.xpath(xpath), driver).check());
	}

	public static String getCodigoPedido(WebDriver driver, Channel channel, int seconds) throws Exception {
		String codPedido = "";
		String xpathPedido = getXPathTextPedido(channel);
		if (state(Present, By.xpath(xpathPedido), driver).wait(seconds).check()) {
			codPedido = driver.findElements(By.xpath(xpathPedido)).get(0).getText();
		}
		return codPedido;
	}

	public static boolean isLinkPedidosDesktop(WebDriver driver) {
		return (state(Present, By.xpath(XPathLinkPedidosDesktop), driver).check());
	}

	public static void clickMisPedidos(WebDriver driver) {
		click(By.xpath(XPathLinkPedidosDesktop), driver).exec();
	}

	public static boolean isLinkMisComprasDesktop(WebDriver driver) {
		return (state(Present, By.xpath(XPathLinkMisComprasDesktop), driver).check());
	}

	public static void clickMisCompras(WebDriver driver) {
		click(By.xpath(XPathLinkMisComprasDesktop), driver).exec();
	}

	public static boolean isVisibleBlockNewLoyaltyPoints(WebDriver driver) {
		return (state(Visible, By.xpath(xpathBlockNewLoyaltyPoints), driver).check());
	}
}
