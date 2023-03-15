package com.mng.robotest.domains.transversal.prehome.pageobjects;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.pageobject.shop.acceptcookies.SectionCookies;
import com.mng.robotest.test.pageobject.shop.acceptcookies.ModalSetCookies.SectionConfCookies;
import com.mng.robotest.test.pageobject.shop.cabecera.SecCabeceraMostFrequent;
import com.mng.robotest.test.pageobject.shop.modales.ModalLoyaltyAfterAccess;
import com.mng.robotest.test.pageobject.utils.LocalStorage;
import com.mng.robotest.test.steps.shop.acceptcookies.ModalSetCookiesSteps;
import com.mng.robotest.test.steps.shop.acceptcookies.SectionCookiesSteps;
import com.mng.robotest.test.utils.testab.TestABactive;

public abstract class PagePrehomeBase extends PageBase {

	abstract boolean isPageUntil(int seconds);
	abstract boolean isNotPageUntil(int seconds);
	abstract void selecionPais();
	abstract void seleccionaIdioma(String nombrePais, String nombreIdioma);
	abstract void selectButtonForEnter();
	abstract void selecionIdiomaAndEnter();
	
	protected final Pais pais = dataTest.getPais();
	protected final IdiomaPais idioma = dataTest.getIdioma();
	
	public void accesoShopViaPrehome(boolean acceptCookies) throws Exception {
		selecPaisIdiomaYAccede();
		new ModalLoyaltyAfterAccess().closeModalIfVisible();
		if (channel.isDevice()) {
			new SecCabeceraMostFrequent().closeSmartBannerIfExistsMobil();
		}
	}
	
	public void previousAccessShopSteps(boolean acceptCookies) throws Exception {
		reloadIfServiceUnavailable();
		new PageJCAS().identJCASifExists();
		TestABactive.currentTestABsToActivate(channel, app, driver);
		manageCookies(acceptCookies);
	}
	
	public void selecPaisIdiomaYAccede() {
		//TODO eliminar try-catch
		try {
			selecionPais();
			selecionIdiomaAndEnter();
		}
		catch (Exception e) {
			Log4jTM.getLogger().error("Problem accessing prehome. {}. {}", e.getClass().getName(), e.getMessage());
		}		
	}
	
	
	private void reloadIfServiceUnavailable() {
		if (driver.getPageSource().contains("Service Unavailable")) {
			driver.navigate().refresh();
		}
	}
	
	private void manageCookies(boolean acceptCookies) {
		SectionCookiesSteps sectionCookiesSteps = new SectionCookiesSteps();
		if (acceptCookies) {
			if (new SectionCookies().isVisible(2)) {
				sectionCookiesSteps.accept();
			}
		} else {
			//Enable Only performance cookies for suport to TestABs
			changeCookieOptanonConsent();
			enablePerformanceCookies();
		}
	}
	
	private void changeCookieOptanonConsent() {
		new SectionCookiesSteps().changeCookie_OptanonConsent();
	}
	
	private void enablePerformanceCookies() {
		ModalSetCookiesSteps modalSetCookiesSteps = new SectionCookiesSteps().setCookies();
		modalSetCookiesSteps.select(SectionConfCookies.COOKIES_DE_RENDIMIENTO);
		modalSetCookiesSteps.enableSwitchCookies();
		modalSetCookiesSteps.saveConfiguration();
	}	
	
	protected void setInitialModalsOff() {
		//Damos de alta la cookie de newsLetter porque no podemos gestionar correctamente el cierre 
		//del modal en la página de portada (es aleatorio y aparece en un intervalo de 0 a 5 segundos)
		LocalStorage localStorage = new LocalStorage(driver);
		localStorage.setItemInLocalStorage("modalRegistroNewsletter", "0");
		localStorage.setItemInLocalStorage("modalAdhesionLoyalty", "true");
		
		//TODO 15-03 Revisar!
		localStorage.setItemInLocalStorage("modalSPShown", "1");
		localStorage.setItemInLocalStorage("MangoShopModalIPConfirmed", "ES-es__2");
	}

}
