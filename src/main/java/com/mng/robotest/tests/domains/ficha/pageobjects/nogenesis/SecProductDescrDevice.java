package com.mng.robotest.tests.domains.ficha.pageobjects.nogenesis;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.mng.robotest.tests.conf.AppEcom.*;
import static com.mng.robotest.tests.domains.ficha.pageobjects.nogenesis.SecProductDescrDevice.TypeStatePanel.*;

public class SecProductDescrDevice extends PageBase {

	public enum TypeStatePanel { FOLDED, UNFOLDED, MISSING }
	public enum TypePanel {
		DESCRIPTION(
			"//div[@id='descriptionPanel']", "//div[@class[contains(.,'product-description')]]",
			Arrays.asList(shop, outlet), UNFOLDED),
		COMPOSITION(
			"//div[@id='compositionPanel']", "//div[@class[contains(.,'more-info')]]",
			Arrays.asList(shop, outlet), FOLDED),
		SHIPMENT(
			"//div[@id='shipmentPanel']", "//div[@class[contains(.,'shipment-and-returns')]]",
			Arrays.asList(shop, outlet), FOLDED), 
		RETURNS(
			"//div[@id='returnsPanel']", "//div[@class[contains(.,'shipment-and-returns')]]",
			Arrays.asList(shop, outlet), FOLDED),
		KC_SAFETY(
			"//div[@id='kcSafetyPanel']", "//div[@id='kcSafetyPanel']", //?
			Arrays.asList(shop, outlet), MISSING);
		
		private final String xPathDesktop;
		private final String xPathDevice;
		private final List<AppEcom> listApps;
		private final TypeStatePanel stateInitial;
		TypePanel(String xPathDesktop, String xPathDevice, List<AppEcom> listApps, TypeStatePanel stateInitial) {
			this.xPathDesktop = xPathDesktop;
			this.xPathDevice = xPathDevice;
			this.listApps = listApps;
			this.stateInitial = stateInitial;
		}
		
		private static final String XP_DIV_PRODUCT_DESCRIPTION_DESKTOP = "//div[@class='product-description']";
		private static final String XP_DIV_PRODUCT_DESCRIPTION_DEVICE = "//div[@class[contains(.,'product-detail')]]";
		
		public String getXPath(Channel channel, AppEcom app) {
			if (channel==Channel.mobile || (channel==Channel.tablet && app!=AppEcom.outlet)) {
				return XP_DIV_PRODUCT_DESCRIPTION_DEVICE + xPathDevice;
			}
			return XP_DIV_PRODUCT_DESCRIPTION_DESKTOP + xPathDesktop;
		}
		public String getXPathLink(Channel channel, AppEcom app) {
			if (channel==Channel.mobile || (channel==Channel.tablet && app!=AppEcom.outlet)) {
				return getXPath(channel, app);
			}
			return getXPath(channel, app) + "//*[@role='button']";
		}
		
		public List<AppEcom> getListApps() {
			return this.listApps;
		}
		
		public TypeStatePanel getStateInitial() {
			return this.stateInitial;
		}
	}
	
	public TypeStatePanel getStatePanelAfterClick(TypeStatePanel stateOriginal) {
		switch (stateOriginal) {
		case FOLDED:
			return UNFOLDED;
		case UNFOLDED:
			return FOLDED;
		case MISSING:
		default:
			return MISSING;
		}
	}
	
	public TypeStatePanel getStatePanel(TypePanel typePanel) {
		waitMillis(200);
		String xpathPanel = typePanel.getXPath(channel, app);
		if (!state(PRESENT, xpathPanel).check()) {
			return MISSING;
		}
		
		var panel = getElement(xpathPanel);
		if (channel==Channel.mobile || (channel==Channel.tablet && app!=AppEcom.outlet)) {
			By byCapa = By.xpath(".//div[@class[contains(.,'collapsible-info-body')]]");
			if (state(PRESENT, panel).by(byCapa).check()) {
				var capaElem = driver.findElement(byCapa);
				if (capaElem.getAttribute("class").contains("-opened")) {
					return UNFOLDED;
				}
			} else {
				return UNFOLDED;
			}
		} else {
			if (panel.getAttribute("class").contains("-active")) {
				return UNFOLDED;
			}
		}
		return FOLDED;
	}
	
	public boolean isPanelInStateUntil(TypePanel typePanel, TypeStatePanel stateExpected, int maxSeconds) {
		var statePanel = getStatePanel(typePanel);
		int seconds=0;
		while (statePanel!=stateExpected && seconds<maxSeconds) {
			waitMillis(1000);
			seconds+=1;
			statePanel = getStatePanel(typePanel);
		}

		return statePanel==stateExpected;
	}

	public void clickPanel(TypePanel typePanel) {
		String xpathPanelLink = typePanel.getXPathLink(channel, app);
		moveToElement(xpathPanelLink);
		click(xpathPanelLink).exec();
	}
	
}
