package com.mng.robotest.domains.ficha.pageobjects;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.base.PageBase;


public class SecProductDescrOld extends PageBase {

	public enum TypeStatePanel { FOLDED, UNFOLDED, MISSING }
	public enum TypePanel {
		DESCRIPTION(
			"//div[@id='descriptionPanel']", "//div[@class[contains(.,'product-description')]]",
			Arrays.asList(AppEcom.shop, AppEcom.outlet, AppEcom.votf), TypeStatePanel.UNFOLDED),
		COMPOSITION(
			"//div[@id='compositionPanel']", "//div[@class[contains(.,'more-info')]]",
			Arrays.asList(AppEcom.shop, AppEcom.outlet, AppEcom.votf), TypeStatePanel.FOLDED),
		SHIPMENT(
			"//div[@id='shipmentPanel']", "//div[@class[contains(.,'shipment-and-returns')]]",
			Arrays.asList(AppEcom.shop, AppEcom.votf), TypeStatePanel.FOLDED), 
		RETURNS(
			"//div[@id='returnsPanel']", "//div[@class[contains(.,'shipment-and-returns')]]",
			Arrays.asList(AppEcom.shop, AppEcom.votf), TypeStatePanel.FOLDED),
		KC_SAFETY(
			"//div[@id='kcSafetyPanel']", "//div[@id='kcSafetyPanel']", //?
			Arrays.asList(AppEcom.shop, AppEcom.outlet, AppEcom.votf), TypeStatePanel.MISSING);
		
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
		
		private static final String XPATH_DIV_PRODUCT_DESCRIPTION_DESKTOP = "//div[@class='product-description']";
		private static final String XPATH_DIV_PRODUCT_DESCRIPTION_DEVICE = "//div[@class[contains(.,'product-detail')]]";
		
		public String getXPath(Channel channel, AppEcom app) {
			if (channel==Channel.mobile || (channel==Channel.tablet && app!=AppEcom.outlet)) {
				return XPATH_DIV_PRODUCT_DESCRIPTION_DEVICE + xPathDevice;
			}
			return XPATH_DIV_PRODUCT_DESCRIPTION_DESKTOP + xPathDesktop;
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
			return TypeStatePanel.UNFOLDED;
		case UNFOLDED:
			return TypeStatePanel.FOLDED;
		case MISSING:
		default:
			return TypeStatePanel.MISSING;
		}
	}
	
	public TypeStatePanel getStatePanel(TypePanel typePanel) {
		waitMillis(200);
		String xpathPanel = typePanel.getXPath(channel, app);
		if (!state(Present, xpathPanel).check()) {
			return TypeStatePanel.MISSING;
		}
		
		WebElement panel = getElement(xpathPanel);
		if (channel==Channel.mobile || (channel==Channel.tablet && app!=AppEcom.outlet)) {
			By byCapa = By.xpath(".//div[@class[contains(.,'collapsible-info-body')]]");
			if (state(Present, panel).by(byCapa).check()) {
				WebElement capa = driver.findElement(byCapa);
				if (capa.getAttribute("class").contains("-opened")) {
					return TypeStatePanel.UNFOLDED;
				}
			} else {
				return TypeStatePanel.UNFOLDED;
			}
		} else {
			if (panel.getAttribute("class").contains("-active")) {
				return TypeStatePanel.UNFOLDED;
			}
		}
		return TypeStatePanel.FOLDED;
	}
	
	public boolean isPanelInStateUntil(TypePanel typePanel, TypeStatePanel stateExpected, int maxSeconds) {
		TypeStatePanel statePanel = getStatePanel(typePanel);
		int seconds=0;
		while (statePanel!=stateExpected && seconds<maxSeconds) {
			waitMillis(1000);
			seconds+=1;
			statePanel = getStatePanel(typePanel);
		}

		return (statePanel==stateExpected);
	}

	public void clickPanel(TypePanel typePanel) {
		String xpathPanelLink = typePanel.getXPathLink(channel, app);
		click(xpathPanelLink).exec();
	}
}
