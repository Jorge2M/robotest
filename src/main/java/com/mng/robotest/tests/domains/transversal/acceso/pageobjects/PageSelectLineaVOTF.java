package com.mng.robotest.tests.domains.transversal.acceso.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.transversal.menus.pageobjects.LineaWeb.LineaType;

public class PageSelectLineaVOTF extends PageBase {

	private String getXPathLineaSection(LineaType linea) {
		return ("//div[@id='" + linea.name().toLowerCase() + "' and @class[contains(.,'section')]]");
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
		return state(PRESENT, xpathBanner).check();
	}
	
	public void clickBanner(LineaType linea) {
		click(getXPathLineaLink(linea)).exec();
	}
	
	public void clickMenu(LineaType linea, int numMenu) {
		click(getXPathMenu(linea, numMenu)).exec();
	}
}
