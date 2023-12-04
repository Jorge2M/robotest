package com.mng.robotest.tests.domains.galeria.pageobjects.sections;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.mng.robotest.tests.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecCrossSelling extends PageBase {
	
	private static final String XP_SECTION_MOVIL = "//section[@class='cross-selling']";
	private static final String XP_SECTION_DESKTOP = "//section[@id='crossSelling']";

	private String getXPathSection() {
		if (isDesktop()) {
			return XP_SECTION_DESKTOP;
		}
		return XP_SECTION_MOVIL;
	}
	
	public String getXPathLink(int numLink) {
		return ("(" + getXPathSection() + "/a)[" + numLink + "]");
	}

	public boolean isSectionVisible() {
		return (state(VISIBLE, By.xpath(getXPathSection()), driver).check());
	}

	/**
	 * @return si un link del cross-selling está asociado a un determinado menú (validamos que coincida el texto y el href) 
	 */
	public boolean linkAssociatedToMenu(int numLink, String litMenu, String hrefMenu) {
		String xpathLink = getXPathLink(numLink);
		WebElement link = getElementVisible(xpathLink);
		return (
			link!=null &&
			link.getAttribute("innerHTML").compareTo(litMenu)==0 &&
			link.getAttribute("href").compareTo(hrefMenu)==0);
	}
}
