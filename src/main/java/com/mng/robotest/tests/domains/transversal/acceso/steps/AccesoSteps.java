package com.mng.robotest.tests.domains.transversal.acceso.steps;

import java.net.URISyntaxException;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.base.datatest.DataTest;
import com.mng.robotest.tests.domains.bolsa.pageobjects.SecBolsa;
import com.mng.robotest.tests.domains.bolsa.steps.BolsaSteps;
import com.mng.robotest.tests.domains.login.steps.PageIdentificacionSteps;
import com.mng.robotest.tests.domains.menus.steps.MenusUserSteps;
import com.mng.robotest.tests.domains.transversal.acceso.navigations.AccesoFlows;
import com.mng.robotest.tests.domains.transversal.browser.LocalStorageMango;
import com.mng.robotest.tests.domains.transversal.prehome.steps.PagePrehomeSteps;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.pageobject.shop.menus.MenusUserWrapper;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.mng.robotest.testslegacy.pageobject.shop.menus.MenuUserItem.UserMenu.*;
import static com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen.*;
import static com.github.jorge2m.testmaker.conf.State.*;

public class AccesoSteps extends StepBase {

	public void oneStep(boolean clearArticulos) throws Exception {
		new AccesoFlows().accesoHomeAppWeb();
		if (dataTest.isUserRegistered()) {
			identification(dataTest, clearArticulos);
		}
	}
	
	public void quickAccessCountry() throws Exception {
		String urlBase = inputParamsSuite.getUrlBase();
		String urlAccess = dataTest.getPais().getUrlAccess(urlBase);
		quickAccess(urlAccess, dataTest.getPais(), dataTest.getIdioma());
	}
	
	public void manySteps() throws Exception {
		accessFromPreHome(false, true);
		if (dataTest.isUserRegistered()) {
			identificacionEnMango();
			new BolsaSteps().clear();
		}
	}

	@Step (
		description="Acceso <b style=\"color:brown;\">#{pais.getNombrePais()} / #{idioma.getLiteral()}</b> a través de la URL <a href='#{urlAccess}'>#{urlAccess}</a>",
		expected="el acceso es correcto")
	private void quickAccess(String urlAccess, Pais pais, IdiomaPais idioma) {
		new AccesoFlows().fixRandomSeleniumProblem();
		new LocalStorageMango().setInitialModalsOff();
		if (getCurrentUrl().compareTo(urlAccess)!=0) {
			driver.get(urlAccess);
		}
	}

	public void accessFromPreHome() throws Exception {
		accessFromPreHome(false, true);
	}
	
	public void accessFromPreHome(boolean execValidacs, boolean acceptCookies) throws Exception {
		var accesoFlows = new AccesoFlows();
		accesoFlows.previousAccessShopSteps();
		new PagePrehomeSteps().accessShopViaPreHome(execValidacs, acceptCookies);
		accesoFlows.closeModalsPostAccessAndManageCookies(acceptCookies);
	}
	
	@Step (
		description= 
			"Seleccionar \"Iniciar sesión\" e identificarse con el usuario <b style=\"color:blue;\">#{dataTest.getUserConnected()}</b> " + 
			"(borrar artículos bolsa: <b>#{clearArticulos}</b>)",
		expected="el login es correcto",
		saveNettraffic=ALWAYS)
	private void identification(DataTest dataTest, boolean clearArticulos) {
		new AccesoFlows().identification(dataTest.getUserConnected(), dataTest.getPasswordUser());
		checkIdentificacionEnShop();
		if (clearArticulos) {
			new SecBolsa().clearArticulos();
		}
	}

	private void checkIdentificacionEnShop() {
		checkLinksAfterLogin();
		checksDefault();
		checksGeneric()
			.googleAnalytics()
			.netTraffic().execute();
	}
	
	public void checkIsLogged() {
		checkIsLogged(6);
	}
	
	public void checkIsLogged(int seconds) {
		if (isPRO()) {
			checkIsLogged(seconds, DEFECT);
		} else {
			//Overcome random performance problem in PRE
			var checks = checkIsLogged(seconds, WARN);
			if (!checks.areAllChecksOvercomed()) {
				checkIsLogged(seconds, DEFECT);
			}
		}		
	}
	
	private void checkLinksAfterLogin() {
		checkIsLogged();
		checkOtherLinksAfterLogin();
	}	
	
	@Validation
	private ChecksTM checkIsLogged(int seconds, State level) {
		var checks = ChecksTM.getNew();
		var userMenus = new MenusUserWrapper();
		checks.add(
			"Aparece el link \"Mi cuenta\" " + getLitSecondsWait(seconds),
			userMenus.isMenuInStateUntil(MI_CUENTA, PRESENT, seconds), level);
		
		return checks;
	}
	
	@Validation
	private ChecksTM checkOtherLinksAfterLogin() {
		var checks = ChecksTM.getNew();
		var userMenus = new MenusUserWrapper();
		if (isOutlet()) { 
			checks.add(
				"NO aparece el link \"Favoritos\"",
				!userMenus.isMenuInStateUntil(FAVORITOS, PRESENT, 0));
		} else {
			checks.add(
				"Aparece el link \"Favoritos\"",
				userMenus.isMenuInStateUntil(FAVORITOS, PRESENT, 1));
		}
		
		if (!isDesktop() && dataTest.getPais().isVentaOnline()) {
			boolean isPresentLinkMisCompras = userMenus.isMenuInStateUntil(MIS_COMPRAS, PRESENT, 1);
			checks.add(
				"Aparece el link \"Mis Compras\"",
				isPresentLinkMisCompras);
		}
		
		if (!isDesktop()) {
			checks.add(
				"Aparece el link \"Ayuda\"",
				userMenus.isMenuInState(AYUDA, VISIBLE));
			
			//TODO pasarlo a DEFECT cuanto tengamos los tests-ids (actualmente no hay forma de identificar el de Outlet)
			checks.add(
				"Aparece el link \"Cerrar sesión\"",
				userMenus.isMenuInState(CERRAR_SESION, PRESENT), WARN);
		}
		
		return checks;
	}

	public void identificacionEnMango() {
		if (!new MenusUserWrapper().isMenuInState(CERRAR_SESION, PRESENT)) {
			iniciarSesion(dataTest);
		}
	}

	private void iniciarSesion(DataTest dataTest) {
		identification(dataTest.getUserConnected(), dataTest.getPasswordUser());
	}
	
	@Step (
		description="Seleccionar \"Iniciar Sesión\" e introducir credenciales incorrectas: <b>#{usuario}, #{password}</b>",
		expected="Aparece el correspondiente mensaje de error")
	public void inicioSesionDatosKO(String usuario, String password) {
		new AccesoFlows().identification(usuario, password);
		new PageIdentificacionSteps().checkTextoCredencialesKO();
		new MenusUserSteps().checkIsInvisibleLinkCerrarSesion();
		checksDefault();	
	}
	
	@Step (
		description="Seleccionar <b>Iniciar Sesión</b> y logarse con el usuario <b>#{userConnect}</b>", 
		expected="La identificación es correcta",
		saveNettraffic=ALWAYS)
	public void identification(String userConnect, String userPassword) {
		new AccesoFlows().identification(userConnect, userPassword);
		checkIdentificacionEnShop();
	}
	
	public void login() {
		login(dataTest.getUserConnected(), dataTest.getPasswordUser());
	}
	
	@Step (
		description="Renovar el browser, introducir la URL \"/es/account/baja\" y aceptar las cookies", 
		expected="El login es correcto")			
	public void inputUrlAccountBajaAndAcceptCookiesInNewBrowser() throws URISyntaxException { 
		TestMaker.renewDriverTestCase()
			.get(inputParamsSuite.getDnsUrlAcceso() + "/es/account/baja");
		new AccesoFlows().closeModalsPostAccessAndManageCookies(true);
	}
	
	@Step (
		description="Logarse con el usuario <b>#{userConnect} / #{userPassword}</b>", 
		expected="El login es correcto",
		saveNettraffic=ALWAYS)
	public void login(String userConnect, String userPassword) {
		new AccesoFlows().login(userConnect, userPassword);
		checkIdentificacionEnShop();
	}	
	
	@Step (
		description="Cargar la URL inicial", 
		expected="La URL se carga correctamente")
	public void goToInitialURL() {
		driver.get(inputParamsSuite.getUrlBase());
	}

	public static class ResultValWithPais extends ChecksTM {
		Pais pais;
		private ResultValWithPais() {
			super();
		}
		public static ResultValWithPais getNew() {
			return (new ResultValWithPais());
		}
		
		public Pais getPais() {
			return this.pais;
		}
		
		public void setPais(Pais pais) {
			this.pais = pais;
		}
	}
}