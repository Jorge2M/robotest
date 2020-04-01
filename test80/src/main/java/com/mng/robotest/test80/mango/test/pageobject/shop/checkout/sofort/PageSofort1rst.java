package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.sofort;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.conf.Channel;
import static com.mng.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;


/**
 * Page1: la página inicial de Sofort (la posterior a la selección del botón "Confirmar Pago")
 * @author jorge.munoz
 *
 */
public class PageSofort1rst {
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
    	return (state(Visible, By.xpath(xpPathClickFollowing), driver)
    			.wait(maxSeconds).check());
    }

	public static void clickGoToSofort(WebDriver driver, Channel channel) {
		String xpPathClickFollowing = getXPathClickToFollow(channel);
		click(By.xpath(xpPathClickFollowing), driver).exec();
	}
}
