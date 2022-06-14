package com.mng.robotest.test.pageobject.shop.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.test.pageobject.shop.checkout.pci.SecTarjetaPciInIframe;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecKrediKarti extends SecTarjetaPciInIframe {

	private final Channel channel;
	
	static final String XPathCapaPagoPlazoMobil = "//table[@class[contains(.,'installment')]]";
	static final String XPathRadioPagoPlazoMobil = XPathCapaPagoPlazoMobil + "//div[@class[contains(.,'installment-checkbox')]]";
	static final String XPathCapaPagoPlazoDesktop = "//div[@class[contains(.,'installments-content')]]"; 
	static final String XPathRadioPagoPlazoDesktop = XPathCapaPagoPlazoDesktop + "//input[@type='radio' and @name='installment']";
	
	private SecKrediKarti(Channel channel, WebDriver driver) {
		super(channel, driver);
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
		case mobile:
			return XPathCapaPagoPlazoMobil;
		}
	}
	
	private String getXPathRadioPagoPlazo() {
		switch (channel) {
		case desktop:
			return XPathRadioPagoPlazoDesktop;
		default:
		case mobile:
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
		click(radioBy).exec();
		click(radioBy).exec();
		leaveIframe();
	}
}
