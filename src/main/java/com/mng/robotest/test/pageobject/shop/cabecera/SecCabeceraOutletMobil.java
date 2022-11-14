package com.mng.robotest.test.pageobject.shop.cabecera;

import org.openqa.selenium.By;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.ElementPage;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.pageobject.shop.cabecera.SecCabeceraMostFrequent.IconoCabeceraShop_DesktopMobile;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecCabeceraOutletMobil extends SecCabecera {
	
	public enum IconoCabOutletMobil implements ElementPage {
		BOLSA(IconoCabeceraShop_DesktopMobile.bolsa.getXPath(Channel.mobile, AppEcom.outlet)),
		LUPA(IconoCabeceraShop_DesktopMobile.lupa.getXPath(Channel.mobile, AppEcom.outlet));
		
		private By by;
		private IconoCabOutletMobil(String xpath) {
			by = By.xpath(xpath);
		}
		
		@Override
		public By getBy() {
			return by;
		}
	}

	public SecCabeceraOutletMobil() {
		super();
	}
	
	@Override
	String getXPathNumberArtIcono() {
		return new SecCabeceraMostFrequent().getXPathNumberArtIcono();
	}
	
	@Override
	public boolean isInStateIconoBolsa(State state, int seconds) {
		return (isElementInStateUntil(IconoCabOutletMobil.BOLSA, state, seconds));
	}
	
	@Override
	public void clickIconoBolsa() {
		click(IconoCabOutletMobil.BOLSA);
	}
	
	@Override
	public void clickIconoBolsaWhenDisp(int seconds) {
		clickIfClickableUntil(IconoCabOutletMobil.BOLSA, seconds);
	}
	
	@Override
	public void hoverIconoBolsa() {
		hoverIcono(IconoCabOutletMobil.BOLSA);
	}
	
	public boolean isElementInStateUntil(IconoCabOutletMobil icono, State state, int seconds) {
		return (state(state, icono.getBy()).wait(seconds).check());
	}

	 public boolean isClickableUntil(IconoCabOutletMobil icono, int seconds) {
		return (state(Clickable, icono.getBy()).wait(seconds).check());
	}

	public void clickIfClickableUntil(IconoCabOutletMobil icono, int seconds) {
		if (isClickableUntil(icono, seconds)) {
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
		moveToElement(icono.getBy());
	}
}
