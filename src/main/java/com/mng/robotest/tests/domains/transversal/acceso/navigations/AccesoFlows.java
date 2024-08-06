package com.mng.robotest.tests.domains.transversal.acceso.navigations;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.mng.robotest.tests.domains.setcookies.pageobjects.ModalSetCookies.CookiesType.COOKIES_DE_ANALISIS;
import static com.mng.robotest.testslegacy.pageobject.shop.menus.MenuUserItem.UserMenu.CERRAR_SESION;
import static com.mng.robotest.testslegacy.pageobject.shop.menus.MenuUserItem.UserMenu.INICIAR_SESION;

import org.openqa.selenium.JavascriptExecutor;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.changecountry.pageobjects.ModalChangeCountry;
import com.mng.robotest.tests.domains.changecountry.pageobjects.ModalChangeCountryOld;
import com.mng.robotest.tests.domains.changecountry.pageobjects.ModalGeolocation;
import com.mng.robotest.tests.domains.changecountry.tests.Chg001;
import com.mng.robotest.tests.domains.login.pageobjects.PageLogin;
import com.mng.robotest.tests.domains.setcookies.pageobjects.SectionCookies;
import com.mng.robotest.tests.domains.setcookies.steps.SectionCookiesSteps;
import com.mng.robotest.tests.domains.transversal.acceso.steps.AccesoSteps;
import com.mng.robotest.tests.domains.transversal.cabecera.pageobjects.SecCabecera;
import com.mng.robotest.tests.domains.transversal.prehome.pageobjects.PageJCAS;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.pageobject.shop.menus.MenusUserWrapper;
import com.mng.robotest.testslegacy.pageobject.shop.modales.ModalActPoliticaPrivacidad;
import com.mng.robotest.testslegacy.pageobject.shop.modales.ModalLoyaltyAfterAccess;
import com.mng.robotest.testslegacy.pageobject.shop.modales.ModalLoyaltyAfterLogin;
import com.mng.robotest.testslegacy.pageobject.shop.modales.ModalNewsLetterAfterAccess;
import com.mng.robotest.testslegacy.utils.UtilsTest;

import io.netty.handler.timeout.TimeoutException;

public class AccesoFlows extends StepBase {

	public void goToInitURL() {
		String canary = "";
		//Temporal para test Canary!!!
		//canary = "?canary=true";
		String urlInitial = TestMaker.getInputParamsSuite().getUrlBase() + canary;
		String currentUrl = getCurrentUrl();
		if (currentUrl.compareTo(urlInitial)!=0) {
			driver.get(urlInitial);
		}
	}
	
	public void accesoHomeAppWeb() throws Exception {
		accesoHomeAppWeb(true); 
	}
	
	public void accesoHomeAppWeb(boolean acceptCookies) throws Exception {
		new AccesoSteps().accessFromPreHome(false, acceptCookies);
	}
	
	public void previousAccessShopSteps() throws Exception {
		fixRandomSeleniumProblem();
		reloadIfServiceUnavailable();
		new PageJCAS().identJCASifExists();
	}
	
	public void manageCookies(boolean acceptCookies) {
		var sectionCookiesSteps = new SectionCookiesSteps();
		if (acceptCookies) {
			if (new SectionCookies().isVisible(5)) {
				sectionCookiesSteps.accept();
			}
		} else {
			//Enable Only performance cookies for suport to TestABs
			enablePerformanceCookies();
		}
	}	
	
	private void enablePerformanceCookies() {
		var modalSetCookiesSteps = new SectionCookiesSteps().setCookies();
		modalSetCookiesSteps.enable(COOKIES_DE_ANALISIS);
		modalSetCookiesSteps.saveConfiguration();
	}	
	
	private void reloadIfServiceUnavailable() {
		if (driver.getPageSource().contains("Service Unavailable")) {
			driver.navigate().refresh();
		}
	}
	
	public void identification(String user, String password) {
		if (UtilsTest.todayBeforeDate("2024-09-06")) {
			workAroundLoginProblem();
		}
		clickIniciarSesion();
		login(user, password);
	}
	
	public void login(String user, String password) {
		var pageLogin = new PageLogin();
		pageLogin.isPage(8);
		pageLogin.inputUserPassword(user, password);
		pageLogin.clickButtonEntrar();
		closeModalsPostLogin();
	}
	private void closeModalsPostLogin() {
		ModalChangeCountry.make(app).closeModalIfVisible();
		new ModalActPoliticaPrivacidad().clickOkIfVisible();
		new ModalLoyaltyAfterLogin().closeModalIfVisible();
		new ModalChangeCountryOld().closeModalIfVisible();
	}
	public void closeModalsPostAccessAndManageCookies(boolean acceptCookies) {
		manageCookies(acceptCookies);
		closeInitialModals();
	}
	
	private void closeInitialModals() {
		try {
			new ModalLoyaltyAfterAccess().closeModalIfVisible();
			new ModalNewsLetterAfterAccess().closeModalIfVisible();
			new ModalGeolocation().closeModalIfVisible();
			ModalChangeCountry.make(app).closeModalIfVisible();
		} 
		catch (Exception e) {
			Log4jTM.getLogger().warn("Problem closing modals", e);
		}
	}
	
	private void clickIniciarSesion() {
		if (channel.isDevice()) {
			var secCabeceraDevice = SecCabecera.make();
			boolean toOpen = true;
			secCabeceraDevice.clickIconoMenuHamburguerMobil(toOpen);
			boolean menuClicado = new MenusUserWrapper().clickMenuIfInState(CERRAR_SESION, CLICKABLE);
			if (menuClicado) {
				secCabeceraDevice.clickIconoMenuHamburguerMobil(toOpen);
			}
		}
		new MenusUserWrapper().moveAndClick(INICIAR_SESION);
	}	
	
	private void workAroundLoginProblem() {
		//TODO workaround 06-08-2024 para corregir el problema de prehome->login->checkout
		String urlHelp = "";
		try {
			urlHelp = inputParamsSuite.getDnsUrlAcceso() + "/es/help";
			driver.get(urlHelp);
		}
		catch (Exception e) {
			Log4jTM.getLogger().warn("Problem loading " + urlHelp, e);
		}
	}
	
	public void cambioPaisFromHomeIfNeeded(Pais newPais, IdiomaPais newIdioma) {
		cambioPais(newPais, newIdioma);
	}
	
	public void cambioPais(Pais newPais, IdiomaPais newIdioma) {
		new Chg001().changeCountry(newPais, newIdioma);
	}
	
	public void fixRandomSeleniumProblem() {
		String currentUrl = "";
		try {
			currentUrl = getCurrentUrl();
		} catch (TimeoutException e) {
			Log4jTM.getLogger().warn("Timeout trying to capture current url from browser");
		}
		
		if ("".compareTo(currentUrl)==0 || currentUrl.contains("data:,")) {
			Log4jTM.getLogger().warn(String.format("Problem with data:, in url. Trying to get URL %s", inputParamsSuite.getUrlBase()));
			var js = (JavascriptExecutor) driver;
			js.executeScript("window.stop();");
			driver.get(inputParamsSuite.getUrlBase());
			Log4jTM.getLogger().info(String.format("URL in browser %s", getCurrentUrl()));
		}
	}
		
}
