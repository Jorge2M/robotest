package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.sofort;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;


/**
 * Page1: la página inicial de Sofort (la posterior a la selección del botón "Confirmar Pago")
 * @author jorge.munoz
 *
 */
public class PageSofort1rst extends WebdrvWrapp {
    final static String xPathFigurasButtonDesktop = "//input[@class[contains(.,'paySubmit')]]";
    final static String xPathIconoSofort = "//input[@name='brandName' and @type='submit']";
    
    static String getXPathClickToFollow(Channel channel) {
        if (channel==Channel.movil_web) {
            return xPathIconoSofort;
        } else {
            return xPathFigurasButtonDesktop;
        }
    }
    
    public static boolean isPageVisibleUntil(int maxSeconds, Channel channel, WebDriver driver) {
    	String xpPathClickFollowing = getXPathClickToFollow(channel);
    	return (isElementVisibleUntil(driver, By.xpath(xpPathClickFollowing), maxSeconds));
    }
    
    public static void clickGoToSofort(WebDriver driver, Channel channel) throws Exception {
        String xpPathClickFollowing = getXPathClickToFollow(channel);
        clickAndWaitLoad(driver, By.xpath(xpPathClickFollowing));
    }
}
