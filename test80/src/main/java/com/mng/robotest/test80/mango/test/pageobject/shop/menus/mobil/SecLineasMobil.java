package com.mng.robotest.test80.mango.test.pageobject.shop.menus.mobil;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Present;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;

public abstract class SecLineasMobil extends SecLineasDevice {

	protected static String[] tagsRebajasMobil = {"mujer", "hombre", "ninos", "violeta"};

	protected static String XPathHeaderMobile = "//div[@id='headerMobile']";
	protected static String XPathCapaLevelLinea = "//div[@class[contains(.,'menu-section')]]";
	
	public static SecLineasMobil getNew(AppEcom app, WebDriver driver) {
		switch (app) {
		case outlet:
			return new SecLineasMobilOutlet(driver);
		default:
			return new SecLineasMobilShop(driver);
		}
	}
	
	public SecLineasMobil(AppEcom app, WebDriver driver) {
		super(Channel.mobile, app, driver);
	}
	
	public String getXPathHeaderMobile() {
		return XPathHeaderMobile;
	}

	public boolean isLineaPresentUntil(LineaType lineaType, int maxSeconds) {
		String xpathLinea = getXPathLineaLink(lineaType);
		return (state(Present, By.xpath(xpathLinea)).wait(maxSeconds).check());
	}

}
