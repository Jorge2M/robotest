package com.mng.robotest.domains.galeria.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class SecCrossSelling extends PageBase {
	
	private static final String XPATH_SECTION_MOVIL = "//section[@class='cross-selling']";
	private static final String XPATH_SECTION_DESKTOP = "//section[@id='crossSelling']";

	private String getXPathSection() {
		switch (channel) {
		case desktop:
			return XPATH_SECTION_DESKTOP;
		default:
			return XPATH_SECTION_MOVIL;
		}
	}
	
	public String getXPath_link(int numLink) {
		return ("(" + getXPathSection() + "/a)[" + numLink + "]");
	}

	public boolean isSectionVisible() {
		return (state(Visible, By.xpath(getXPathSection()), driver).check());
	}

	/**
	 * @return si un link del cross-selling está asociado a un determinado menú (validamos que coincida el texto y el href) 
	 */
	public boolean linkAssociatedToMenu(int numLink, String litMenu, String hrefMenu) {
		String xpathLink = getXPath_link(numLink);
		WebElement link = getElementVisible(xpathLink);
		return (
			link!=null &&
			link.getAttribute("innerHTML").compareTo(litMenu)==0 &&
			link.getAttribute("href").compareTo(hrefMenu)==0);
	}
}
