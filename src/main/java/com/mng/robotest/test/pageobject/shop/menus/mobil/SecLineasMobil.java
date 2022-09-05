package com.mng.robotest.test.pageobject.shop.menus.mobil;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Present;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.Linea.LineaType;

public abstract class SecLineasMobil extends SecLineasDevice {

	protected static String[] tagsRebajasMobil = {"mujer", "hombre", "ninos", "teen"};

	protected static String XPathHeaderMobile = "//div[@id='headerMobile']";
	protected static String XPathCapaLevelLinea = "//div[@class[contains(.,'menu-section')]]";
	
	public static SecLineasMobil getNew(AppEcom app, WebDriver driver) {
		switch (app) {
		case outlet:
			return new SecLineasMobilOutlet();
		default:
			return new SecLineasMobilShop();
		}
	}
	
	public SecLineasMobil() {
		super();
	}
	
	public String getXPathHeaderMobile() {
		return XPathHeaderMobile;
	}

	public boolean isLineaPresentUntil(LineaType lineaType, int maxSeconds) {
		String xpathLinea = getXPathLineaLink(lineaType);
		return (state(Present, By.xpath(xpathLinea)).wait(maxSeconds).check());
	}

}
