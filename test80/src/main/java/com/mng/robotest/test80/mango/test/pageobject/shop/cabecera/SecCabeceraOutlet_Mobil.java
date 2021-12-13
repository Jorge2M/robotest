package com.mng.robotest.test80.mango.test.pageobject.shop.cabecera;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.ElementPage;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabecera_MostFrequent.IconoCabeceraShop_DesktopMobile;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.test80.mango.conftestmaker.AppEcom;

/**
 * Clase que define la automatización de las diferentes funcionalidades de la sección de "Cabecera" (de Desktop y Movil)
 * @author jorge.munoz
 *
 */
public class SecCabeceraOutlet_Mobil extends SecCabecera {
	
	public enum IconoCabOutletMobil implements ElementPage {
		bolsa(IconoCabeceraShop_DesktopMobile.bolsa.getXPath(Channel.mobile)),
		lupa(IconoCabeceraShop_DesktopMobile.lupa.getXPath(Channel.mobile));
		
		private By by;
		private IconoCabOutletMobil(String xpath) {
			by = By.xpath(xpath);
		}
		
		@Override
		public By getBy() {
			return by;
		}
	}
		
	//private final static String XPathNumArticles = "//span[@class[contains(.,'_cartNum')]]";

	private SecCabeceraOutlet_Mobil(Channel channel, AppEcom app, WebDriver driver) {
		super(channel, app, driver);
	}
	
	public static SecCabeceraOutlet_Mobil getNew(Channel channel, AppEcom app, WebDriver driver) {
		return (new SecCabeceraOutlet_Mobil(channel, app, driver));
	}
	
	@Override
	String getXPathNumberArtIcono() {
		return SecCabecera_MostFrequent.getXPathNumberArtIcono(Channel.mobile);
	}
	
	@Override
	public boolean isInStateIconoBolsa(State state, int maxSeconds) {
		return (isElementInStateUntil(IconoCabOutletMobil.bolsa, state, maxSeconds));
	}
	
	@Override
	public void clickIconoBolsa() {
		click(IconoCabOutletMobil.bolsa);
	}
	
	@Override
	public void clickIconoBolsaWhenDisp(int maxSecondsToWait) {
		clickIfClickableUntil(IconoCabOutletMobil.bolsa, maxSecondsToWait);
	}
	
	@Override
	public void hoverIconoBolsa() {
		hoverIcono(IconoCabOutletMobil.bolsa);
	}
	
	public boolean isElementInStateUntil(IconoCabOutletMobil icono, State state, int maxSeconds) {
		return (state(state, icono.getBy()).wait(maxSeconds).check());
	}

	 public boolean isClickableUntil(IconoCabOutletMobil icono, int maxSeconds) {
		return (state(Clickable, icono.getBy()).wait(maxSeconds).check());
	}

	public void clickIfClickableUntil(IconoCabOutletMobil icono, int maxSecondsToWait) {
		if (isClickableUntil(icono, maxSecondsToWait)) {
			click(icono);
		}
	}

	public void click(IconoCabOutletMobil icono) {
		click(icono, app);
	}

	public void click(IconoCabOutletMobil icono, AppEcom app) {
		click(icono.getBy()).type(TypeClick.javascript).exec();
	}

	public void hoverIcono(IconoCabOutletMobil icono) {
		moveToElement(icono.getBy(), driver);
	}
}
