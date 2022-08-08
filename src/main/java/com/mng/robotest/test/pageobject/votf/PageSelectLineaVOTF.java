package com.mng.robotest.test.pageobject.votf;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.test.beans.Linea.LineaType;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageSelectLineaVOTF extends PageBase {

	public PageSelectLineaVOTF(WebDriver driver) {
		super(driver);
	}
	
	private String getXPathLineaSection(LineaType linea) {
		return ("//div[@id='" + linea.name() + "' and @class[contains(.,'section')]]");
	}
	
	private String getXPathLineaLink(LineaType linea) {
		String xpathBanner = getXPathLineaSection(linea);
		return (xpathBanner + "//div[@class='clickable']");
	}
	
	private String getXPathMenu(LineaType linea, int numMenu) {
		String xpathMenu = getXPathMenu(linea);
		return ("(" + xpathMenu + ")[" + numMenu + "]");
	}
	
	private String getXPathMenu(LineaType linea) {
		String xpathBanner = getXPathLineaSection(linea);
		return (xpathBanner + "/div[@class[contains(.,'subsection')]]/span/a");
	}
	
	public boolean isBannerPresent(LineaType linea) {
		String xpathBanner = getXPathLineaSection(linea);
		return (state(Present, By.xpath(xpathBanner)).check());
	}
	
	public void clickBanner(LineaType linea) {
		String xpathLinkBanner = getXPathLineaLink(linea);
		click(By.xpath(xpathLinkBanner)).exec();
	}
	
	public void clickMenu(LineaType linea, int numMenu) {
		String xpathMenu = getXPathMenu(linea, numMenu);
		click(By.xpath(xpathMenu)).exec();
	}
}
