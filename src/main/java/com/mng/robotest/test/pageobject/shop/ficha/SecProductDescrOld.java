package com.mng.robotest.test.pageobject.shop.ficha;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.conftestmaker.AppEcom;


public class SecProductDescrOld extends PageObjTM {

	private final Channel channel;
	private final AppEcom app;
	
	public enum TypeStatePanel {folded, unfolded, missing}
	public enum TypePanel {
		Description(
			"//div[@id='descriptionPanel']", "//div[@class[contains(.,'product-description')]]",
			Arrays.asList(AppEcom.shop, AppEcom.outlet, AppEcom.votf), TypeStatePanel.unfolded),
		Composition(
			"//div[@id='compositionPanel']", "//div[@class[contains(.,'more-info')]]",
			Arrays.asList(AppEcom.shop, AppEcom.outlet, AppEcom.votf), TypeStatePanel.folded),
		Shipment(
			"//div[@id='shipmentPanel']", "//div[@class[contains(.,'shipment-and-returns')]]",
			Arrays.asList(AppEcom.shop, AppEcom.votf), TypeStatePanel.folded), 
		Returns(
			"//div[@id='returnsPanel']", "//div[@class[contains(.,'shipment-and-returns')]]",
			Arrays.asList(AppEcom.shop, AppEcom.votf), TypeStatePanel.folded),
		KcSafety(
			"//div[@id='kcSafetyPanel']", "//div[@id='kcSafetyPanel']", //?
			Arrays.asList(AppEcom.shop, AppEcom.outlet, AppEcom.votf), TypeStatePanel.missing);
		
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
		
		private String XPathDivProductDescriptionDesktop = "//div[@class='product-description']";
		private String XPathDivProductDescriptionDevice = "//div[@class[contains(.,'product-detail')]]";
		public String getXPath(Channel channel, AppEcom app) {
			if (channel==Channel.mobile || (channel==Channel.tablet && app!=AppEcom.outlet)) {
				return XPathDivProductDescriptionDevice + xPathDevice;
			}
			return XPathDivProductDescriptionDesktop + xPathDesktop;
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
	
	public SecProductDescrOld(Channel channel, AppEcom app, WebDriver driver) {
		super(driver);
		this.channel = channel;
		this.app = app;
	}
	
	public TypeStatePanel getStatePanelAfterClick(TypeStatePanel stateOriginal) {
		switch (stateOriginal) {
		case folded:
			return TypeStatePanel.unfolded;
		case unfolded:
			return TypeStatePanel.folded;
		case missing:
		default:
			return TypeStatePanel.missing;
		}
	}
	
	public TypeStatePanel getStatePanel(TypePanel typePanel) {
		waitMillis(200);
		String xpathPanel = typePanel.getXPath(channel, app);
		if (!state(Present, By.xpath(xpathPanel), driver).check()) {
			return TypeStatePanel.missing;
		}
		
		WebElement panel = driver.findElement(By.xpath(xpathPanel));
		if (channel==Channel.mobile || (channel==Channel.tablet && app!=AppEcom.outlet)) {
			By byCapa = By.xpath(".//div[@class[contains(.,'collapsible-info-body')]]");
			if (state(State.Present, panel).by(byCapa).check()) {
				WebElement capa = driver.findElement(byCapa);
				if (capa.getAttribute("class").contains("-opened")) {
					return TypeStatePanel.unfolded;
				}
			} else {
				return TypeStatePanel.unfolded;
			}
		} else {
			if (panel.getAttribute("class").contains("-active")) {
				return TypeStatePanel.unfolded;
			}
		}
		return TypeStatePanel.folded;
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
		click(By.xpath(xpathPanelLink), driver).exec();
	}
}
