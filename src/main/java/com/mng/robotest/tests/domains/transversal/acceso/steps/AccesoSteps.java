package com.mng.robotest.tests.domains.transversal.acceso.steps;

import java.net.URISyntaxException;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.service.TestMaker;

import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.base.datatest.DataTest;
import com.mng.robotest.tests.domains.bolsa.pageobjects.SecBolsa;
import com.mng.robotest.tests.domains.bolsa.steps.SecBolsaSteps;
import com.mng.robotest.tests.domains.footer.steps.SecFooterSteps;
import com.mng.robotest.tests.domains.login.steps.PageIdentificacionSteps;
import com.mng.robotest.tests.domains.menus.steps.SecMenusUserSteps;
import com.mng.robotest.tests.domains.transversal.acceso.navigations.AccesoFlows;
import com.mng.robotest.tests.domains.transversal.prehome.steps.PagePrehomeSteps;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.pageobject.shop.menus.MenusUserWrapper;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.mng.robotest.testslegacy.pageobject.shop.menus.MenuUserItem.UserMenu.*;
import static com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen.*;

public class AccesoSteps extends StepBase {

	public void oneStep(boolean clearArticulos) throws Exception {
		new AccesoFlows().accesoHomeAppWeb();
		if (dataTest.isUserRegistered() && !isVotf()) {
			identification(dataTest, clearArticulos);
		}
	}
	
	public void quickAccessCountry() throws Exception {
		String urlBase = inputParamsSuite.getUrlBase();
		String urlAccess = dataTest.getPais().getUrlAccess(urlBase);
		quickAccess(urlAccess, dataTest.getPais(), dataTest.getIdioma());
	}
	
	@Step (
		description="Acceso <b style=\"color:brown;\">#{pais.getNombrePais()} / #{idioma.getLiteral()}</b> a través de la URL <a href='#{urlAccess}'>#{urlAccess}</a>",
		expected="el acceso es correcto")
	private void quickAccess(String urlAccess, Pais pais, IdiomaPais idioma) {
		driver.get(urlAccess);
	}
	
	@Step (
		description= 
			"Seleccionar \"Iniciar sesión\" e identificarse con el usuario <b style=\"color:blue;\">#{dataTest.getUserConnected()}</b> " + 
			"(borrar artículos bolsa: <b>#{clearArticulos}</b>)",
		expected="el login es correcto",
		saveNettraffic=ALWAYS)
	public void identification(DataTest dataTest, boolean clearArticulos) {
		new AccesoFlows().identification(dataTest.getUserConnected(), dataTest.getPasswordUser());
		validaIdentificacionEnShop();
		if (clearArticulos) {
			new SecBolsa().clearArticulos();
		}
	}

	public void validaIdentificacionEnShop() {
		checkLinksAfterLogin();
		checksDefault();
		checksGeneric()
			.googleAnalytics()
			.netTraffic().execute();
	}
	
	@Validation(
		description="Aparece el link \"Mi cuenta\" (usuario loginado) " + SECONDS_WAIT)
	public boolean checkIsLogged(int seconds) {
		return new MenusUserWrapper()
				.isMenuInStateUntil(MI_CUENTA, PRESENT, seconds);
	}
	
	@Validation
	public ChecksTM checkLinksAfterLogin() {
		var checks = ChecksTM.getNew();
		int seconds = 7;
		var userMenus = new MenusUserWrapper();
		checks.add(
			"Aparece el link \"Mi cuenta\" " + getLitSecondsWait(seconds),
			userMenus.isMenuInStateUntil(MI_CUENTA, PRESENT, seconds));
		
		boolean isVisibleMenuFav = userMenus.isMenuInStateUntil(FAVORITOS, PRESENT, 0);
		if (isOutlet()) { 
			checks.add(
				"NO aparece el link \"Favoritos\"",
				!isVisibleMenuFav);
		} else {
			checks.add(
				"Aparece el link \"Favoritos\"",
				isVisibleMenuFav);
		}
		
		if (!isDesktop()) {
			boolean isPresentLinkMisCompras = userMenus.isMenuInState(MIS_COMPRAS, PRESENT);
			checks.add(
				"Aparece el link \"Mis Compras\"",
				isPresentLinkMisCompras);
		}
		
		if (!isDesktop()) {
			checks.add(
				"Aparece el link \"Ayuda\"",
				userMenus.isMenuInState(AYUDA, VISIBLE));
			checks.add(
				"Aparece el link \"Cerrar sesión\"",
				userMenus.isMenuInState(CERRAR_SESION, PRESENT));
		}
		
		return checks;
	}

	/**
	 * Accedemos a la aplicación (shop/outlet/votf)
	 * Se ejecutan cada acción en un paso
	 */
	public void manySteps() throws Exception {
		if (isVotf() && !dataTest.isUserRegistered()) { //En VOTF no tiene sentido identificarte con las credenciales del cliente
			accesoVOTFtoHOME();					
		} else {
			accessFromPreHome(false, true);
			if (dataTest.isUserRegistered()) {
				identificacionEnMango();
				new SecBolsaSteps().clear();
			}
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
		new SecMenusUserSteps().checkIsInvisibleLinkCerrarSesion();
		checksDefault();	
	}
	
	@Step (
		description="Seleccionar <b>Iniciar Sesión</b> y logarse con el usuario <b>#{userConnect}</b>", 
		expected="La identificación es correcta",
		saveNettraffic=ALWAYS)
	public void identification(String userConnect, String userPassword) {
		new AccesoFlows().identification(userConnect, userPassword);
		validaIdentificacionEnShop();
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
		validaIdentificacionEnShop();
	}	
	
	public void accesoVOTFtoHOME() throws Exception {
		String urlAcceso = inputParamsSuite.getUrlBase();
		int numIdiomas = dataTest.getPais().getListIdiomas(app).size();
		
		new PageLoginVOTFSteps().goToAndLogin(urlAcceso);
		if (numIdiomas > 1) {
			new PageSelectIdiomaVOTFSteps().selectIdiomaAndContinue();
		}

		var pageSelectLineaVOTFSteps = new PageSelectLineaVOTFSteps();
		pageSelectLineaVOTFSteps.validateIsPage();
		checksDefault();
		
		pageSelectLineaVOTFSteps.selectMenuAndLogoMango(1);
	}


	private static final String TAG_NOMBRE_PAIS_ORIGEN = "@TagPaisOrigen";
	private static final String TAG_CODIGO_PAIS_ORIGEN = "@TagCodigoPaisOrigen";
	private static final String TAG_NOMBRE_IDIOMA_ORIGEN = "@TagIdiomaOrigen";
	
	@Step (
		description=
			"Datos del cambio de país <br>" + 
			"<b>" + TAG_NOMBRE_PAIS_ORIGEN + "</b> (" + TAG_CODIGO_PAIS_ORIGEN + "), <b>idioma " + TAG_NOMBRE_IDIOMA_ORIGEN + "</b><br>" +  
			"<b>#{paisDestino.getNombrePais()}</b> (#{paisDestino.getCodigoPais()}), <b>idioma #{idiomaDestino.getLiteral()}</b>",
		expected=
			"Se accede a la shop de #{paisDestino.getNombrePais()} en #{idiomaDestino.getLiteral()}",
		saveHtmlPage=ALWAYS)
	public void accesoPRYCambioPais(Pais paisDestino, IdiomaPais idiomaDestino) throws Exception {
		replaceStepDescription(TAG_NOMBRE_PAIS_ORIGEN, dataTest.getPais().getNombrePais());
		replaceStepDescription(TAG_CODIGO_PAIS_ORIGEN, dataTest.getCodigoPais());
		replaceStepDescription(TAG_NOMBRE_IDIOMA_ORIGEN, dataTest.getIdioma().getLiteral());
	
		manySteps();

		var paisOriginal = dataTest.getPais();
		var idiomaOriginal = dataTest.getIdioma();
		dataTest.setPais(paisDestino);
		dataTest.setIdioma(idiomaDestino);
		new SecFooterSteps().cambioPais(dataTest.getPais(), dataTest.getIdioma());
		dataTest.setPais(paisOriginal);
		dataTest.setIdioma(idiomaOriginal);

		//No hacemos nada, simplemente es un paso informativo
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