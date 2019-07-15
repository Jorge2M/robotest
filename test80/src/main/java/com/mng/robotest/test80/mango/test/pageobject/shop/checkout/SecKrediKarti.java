package com.mng.robotest.test80.mango.test.pageobject.shop.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.pci.SecTarjetaPciInIframe;

public class SecKrediKarti extends SecTarjetaPciInIframe {

	private final Channel channel;
	
    final static String XPathCapaPagoPlazoMobil = "//table[@class[contains(.,'installment-msu')]]";
    final static String XPathRadioPagoPlazoMobil = XPathCapaPagoPlazoMobil + "//div[@class[contains(.,'custom-radio')] and @data-custom-radio-id]";
    final static String XPathCapaPagoPlazoDesktop = "//div[@class[contains(.,'installmentsTable')]]"; 
    final static String XPathRadioPagoPlazoDesktop = XPathCapaPagoPlazoDesktop + "//input[@type='radio' and @name='installment']";
    
    private SecKrediKarti(Channel channel, WebDriver driver) {
    	super(driver);
    	this.channel = channel;
    }
    
    public static SecKrediKarti getNew(Channel channel, WebDriver driver) {
    	return (new SecKrediKarti(channel, driver));
    }
    
    private String getXPathCapaPagoPlazo() {
    	switch (channel) {
    	case desktop:
    		return XPathCapaPagoPlazoDesktop;
    	default:
    	case movil_web:
    		return XPathCapaPagoPlazoDesktop;
    	}
    }
    
    private String getXPathRadioPagoPlazo() {
    	switch (channel) {
    	case desktop:
    		return XPathRadioPagoPlazoDesktop;
    	default:
    	case movil_web:
    		return XPathRadioPagoPlazoDesktop;
    	}
    }
    
    private String getXPathRadioPagoAPlazo(int numRadio) {
    	return ("(" + getXPathRadioPagoPlazo() + ")[" + numRadio + "]");
    }

    public boolean isVisiblePagoAPlazoUntil(int maxSecondsWait) {
        String xpathCapaPlazo = getXPathCapaPagoPlazo();
        return (isElementVisibleUntil(driver, By.xpath(xpathCapaPlazo), maxSecondsWait));
    }
    
    public void clickRadioPagoAPlazo(int numRadio) throws Exception {
    	By radioBy = By.xpath(getXPathRadioPagoAPlazo(numRadio));
    	WebdrvWrapp.clickAndWaitLoad(driver, radioBy);
    }
}
