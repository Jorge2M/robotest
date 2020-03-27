package com.mng.robotest.test80.mango.test.pageobject.shop.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.pci.SecTarjetaPciInIframe;
import static com.mng.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecKrediKarti extends SecTarjetaPciInIframe {

	private final Channel channel;
	
    final static String XPathCapaPagoPlazoMobil = "//table[@class[contains(.,'installment')]]";
    final static String XPathRadioPagoPlazoMobil = XPathCapaPagoPlazoMobil + "//div[@class[contains(.,'installment-checkbox')]]";
    final static String XPathCapaPagoPlazoDesktop = "//div[@class[contains(.,'installments-content')]]"; 
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
    		return XPathCapaPagoPlazoMobil;
    	}
    }
    
    private String getXPathRadioPagoPlazo() {
    	switch (channel) {
    	case desktop:
    		return XPathRadioPagoPlazoDesktop;
    	default:
    	case movil_web:
    		return XPathRadioPagoPlazoMobil;
    	}
    }
    
    private String getXPathRadioPagoAPlazo(int numRadio) {
    	return ("(" + getXPathRadioPagoPlazo() + ")[" + numRadio + "]");
    }

	public boolean isVisiblePagoAPlazoUntil(int maxSeconds) {
		goToIframe();
		String xpathCapaPlazo = getXPathCapaPagoPlazo();
		boolean result = state(Visible, By.xpath(xpathCapaPlazo), driver).wait(maxSeconds).check();
		leaveIframe();
		return result;
	}

    public void clickRadioPagoAPlazo(int numRadio) throws Exception {
    	goToIframe();
    	By radioBy = By.xpath(getXPathRadioPagoAPlazo(numRadio));
    	WebdrvWrapp.clickAndWaitLoad(driver, radioBy);
    	WebdrvWrapp.clickAndWaitLoad(driver, radioBy);
        leaveIframe();
    }
}
