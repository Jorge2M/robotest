package com.mng.robotest.test.pageobject.shop.cabecera;

import org.openqa.selenium.By;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.ElementPage;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.pageobject.shop.cabecera.SecCabecera_MostFrequent.IconoCabeceraShop_DesktopMobile;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecCabeceraOutlet_Mobil extends SecCabecera {
	
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

	public SecCabeceraOutlet_Mobil() {
		super();
	}
	
	@Override
	String getXPathNumberArtIcono() {
		return SecCabecera_MostFrequent.getXPathNumberArtIcono(Channel.mobile, app);
	}
	
	@Override
	public boolean isInStateIconoBolsa(State state, int maxSeconds) {
		return (isElementInStateUntil(IconoCabOutletMobil.BOLSA, state, maxSeconds));
	}
	
	@Override
	public void clickIconoBolsa() {
		click(IconoCabOutletMobil.BOLSA);
	}
	
	@Override
	public void clickIconoBolsaWhenDisp(int maxSecondsToWait) {
		clickIfClickableUntil(IconoCabOutletMobil.BOLSA, maxSecondsToWait);
	}
	
	@Override
	public void hoverIconoBolsa() {
		hoverIcono(IconoCabOutletMobil.BOLSA);
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
