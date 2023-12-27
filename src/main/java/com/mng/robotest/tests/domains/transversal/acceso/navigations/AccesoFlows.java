package com.mng.robotest.tests.domains.transversal.acceso.navigations;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.mng.robotest.tests.domains.transversal.acceptcookies.pageobjects.ModalSetCookies.SectionConfCookies.COOKIES_DE_RENDIMIENTO;
import static com.mng.robotest.testslegacy.pageobject.shop.menus.MenuUserItem.UserMenu.CERRAR_SESION;
import static com.mng.robotest.testslegacy.pageobject.shop.menus.MenuUserItem.UserMenu.INICIAR_SESION;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.tests.conf.testab.TestABactive;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.footer.steps.SecFooterSteps;
import com.mng.robotest.tests.domains.login.pageobjects.PageLogin;
import com.mng.robotest.tests.domains.menus.pageobjects.LineaWeb.LineaType;
import com.mng.robotest.tests.domains.menus.steps.SecMenusUserSteps;
import com.mng.robotest.tests.domains.transversal.acceptcookies.pageobjects.SectionCookies;
import com.mng.robotest.tests.domains.transversal.acceptcookies.steps.SectionCookiesSteps;
import com.mng.robotest.tests.domains.transversal.acceso.pageobjects.PageAlertaVOTF;
import com.mng.robotest.tests.domains.transversal.acceso.pageobjects.PageLoginVOTF;
import com.mng.robotest.tests.domains.transversal.acceso.pageobjects.PageSelectIdiomaVOTF;
import com.mng.robotest.tests.domains.transversal.acceso.pageobjects.PageSelectLineaVOTF;
import com.mng.robotest.tests.domains.transversal.acceso.steps.AccesoSteps;
import com.mng.robotest.tests.domains.transversal.cabecera.pageobjects.SecCabecera;
import com.mng.robotest.tests.domains.transversal.home.pageobjects.PageLanding;
import com.mng.robotest.tests.domains.transversal.prehome.pageobjects.PageJCAS;
import com.mng.robotest.testslegacy.beans.AccesoVOTF;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.data.PaisShop;
import com.mng.robotest.testslegacy.pageobject.shop.menus.MenusUserWrapper;
import com.mng.robotest.testslegacy.pageobject.shop.modales.ModalActPoliticaPrivacidad;
import com.mng.robotest.testslegacy.pageobject.shop.modales.ModalCambioPais;
import com.mng.robotest.testslegacy.pageobject.shop.modales.ModalLoyaltyAfterAccess;
import com.mng.robotest.testslegacy.pageobject.shop.modales.ModalLoyaltyAfterLogin;
import com.mng.robotest.testslegacy.pageobject.shop.modales.ModalNewsLetterAfterAccess;

public class AccesoFlows extends StepBase {

	private final Pais pais = dataTest.getPais();
	private final IdiomaPais idioma = dataTest.getIdioma();
	
	public void goToInitURL() {
		String canary = "";
		//Temporal para test Canary!!!
		//canary = "?canary=true";
		String urlInitial = TestMaker.getInputParamsSuite().getUrlBase() + canary;
		String currentUrl = driver.getCurrentUrl();
		if (currentUrl.compareTo(urlInitial)!=0) {
			driver.get(urlInitial);
		}
	}
	
	public void accesoHomeAppWeb() throws Exception {
		accesoHomeAppWeb(true); 
	}
	
	public void accesoHomeAppWeb(boolean acceptCookies) throws Exception {
		if (isVotf()) {
			accesoVOTF();
			goFromLineasToMultimarcaVOTF();
			previousAccessShopSteps();
			manageCookies(acceptCookies);			
		} else {
			new AccesoSteps().accessFromPreHome(false, acceptCookies);
		}
	}
	
	public void previousAccessShopSteps() throws Exception {
		reloadIfServiceUnavailable();
		new PageJCAS().identJCASifExists();
		new TestABactive().currentTestABsToActivate();
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
		modalSetCookiesSteps.select(COOKIES_DE_RENDIMIENTO);
		modalSetCookiesSteps.enableSwitchCookies();
		modalSetCookiesSteps.saveConfiguration();
	}	
	
	private void reloadIfServiceUnavailable() {
		if (driver.getPageSource().contains("Service Unavailable")) {
			driver.navigate().refresh();
		}
	}
	
	public void identification(String user, String password) {
		clickIniciarSesionAndWait();
		login(user, password);
	}
	
	public void login(String user, String password) {
		var pageLogin = new PageLogin();
		pageLogin.isPage(5);
		pageLogin.inputUserPassword(user, password);
		pageLogin.clickButtonEntrar();
		closeModalsPostLogin();
	}
	private void closeModalsPostLogin() {
		new ModalCambioPais().closeModalIfVisible();
		new ModalActPoliticaPrivacidad().clickOkIfVisible();
		new ModalLoyaltyAfterLogin().closeModalIfVisible();
	}
	public void closeModalsPostAccessAndManageCookies(boolean acceptCookies) {
		manageCookies(acceptCookies);
		closeInitialModals();
	}
	
	private void closeInitialModals() {
		try {
			new ModalLoyaltyAfterAccess().closeModalIfVisible();
			new ModalNewsLetterAfterAccess().closeModalIfVisible();
			new ModalCambioPais().closeModalIfVisible();
		} 
		catch (Exception e) {
			Log4jTM.getLogger().warn("Problem closing modals", e);
		}
	}
	
	private void clickIniciarSesionAndWait() {
		if (channel.isDevice()) {
			//En el caso de mobile nos tenemos que asegurar que están desplegados los menús
			SecCabecera secCabeceraDevice = SecCabecera.make();
			boolean toOpen = true;
			secCabeceraDevice.clickIconoMenuHamburguerMobil(toOpen);
			
			// Si existe, nos posicionamos y seleccionamos el link \"CERRAR SESIÓN\" 
			// En el caso de iPhone parece que mantiene la sesión abierta después de un caso de prueba 
			boolean menuClicado = new MenusUserWrapper().clickMenuIfInState(CERRAR_SESION, CLICKABLE);
			
			//Si hemos clicado el menú 'Cerrar Sesión' volvemos a abrir los menús
			if (menuClicado) {
				secCabeceraDevice.clickIconoMenuHamburguerMobil(toOpen);
			}
		}
		
		new MenusUserWrapper().moveAndClick(INICIAR_SESION);
	}	
	
	public void goFromLineasToMultimarcaVOTF() {
		var pageSelectLineaVOTF = new PageSelectLineaVOTF();
		pageSelectLineaVOTF.clickBanner(LineaType.SHE);
		pageSelectLineaVOTF.clickMenu(LineaType.SHE, 1);
		
		//Cuando se selecciona el icono de Mango deja de tener efecto el forzado del TestAB de la cabecera que habíamos ejecutado previamente
		SecCabecera.make().clickLogoMango();
	}
	
	public void accesoVOTF() throws Exception {
		var pageLoginVOTF = new PageLoginVOTF();
		pageLoginVOTF.goToFromUrlAndSetTestABs();
		new PageJCAS().identJCASifExists();
		var accesoVOTF = AccesoVOTF.forCountry(PaisShop.getPais(pais));
		pageLoginVOTF.inputUsuario(accesoVOTF.getUsuario());
		pageLoginVOTF.inputPassword(accesoVOTF.getPassword());
		pageLoginVOTF.clickButtonContinue();
		if (pais.getListIdiomas(app).size() > 1) {
			var pageSelectIdiomaVOTF = new PageSelectIdiomaVOTF();
			pageSelectIdiomaVOTF.selectIdioma(idioma.getCodigo());
			pageSelectIdiomaVOTF.clickButtonAceptar();
		}

		var pageAlertaVOTF = new PageAlertaVOTF();
		if (pageAlertaVOTF.isPage()) {
			pageAlertaVOTF.clickButtonContinuar();
		}
	}	
	
	public void cambioPaisFromHomeIfNeeded(Pais newPais, IdiomaPais newIdioma) {
		String codigoPaisActual = (new PageLanding()).getCodigoPais();
		if (newPais.getCodigoPais().compareTo(codigoPaisActual)!=0) {
			cambioPais(newPais, newIdioma);
		}
	}
	
	public void cambioPais(Pais newPais, IdiomaPais newIdioma) {
		if (channel.isDevice() && isOutlet()) {
			new SecMenusUserSteps().cambioPaisMobil(newPais, newIdioma);
		} else {
			new SecFooterSteps().cambioPais(newPais, newIdioma);
		}
	}
		
}
