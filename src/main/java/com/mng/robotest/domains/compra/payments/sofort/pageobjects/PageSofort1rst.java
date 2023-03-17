package com.mng.robotest.domains.compra.payments.sofort.pageobjects;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageSofort1rst extends PageBase {
	
	private static final String XPATH_FIGURAS_BUTTON_DESKTOP = "//input[@class[contains(.,'paySubmit')]]";
	private static final String XPATH_ICONO_SOFORT = "//input[@name='brandName' and @type='submit']";
	
	private String getXPathClickToFollow(Channel channel) {
		if (channel.isDevice()) {
			return XPATH_ICONO_SOFORT;
		} else {
			return XPATH_FIGURAS_BUTTON_DESKTOP;
		}
	}
	
	public boolean isPageVisibleUntil(int seconds) {
		String xpPathClickFollowing = getXPathClickToFollow(channel);
		return state(Visible, xpPathClickFollowing).wait(seconds).check();
	}

	public void clickGoToSofort() {
		click(getXPathClickToFollow(channel)).exec();
	}
}
