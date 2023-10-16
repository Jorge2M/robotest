package com.mng.robotest.tests.domains.transversal.acceso.steps;

import java.util.Iterator;
import java.util.List;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.mng.robotest.testslegacy.pageobject.shop.menus.MenuUserItem.UserMenu.*;

import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.base.datatest.DataTest;
import com.mng.robotest.tests.domains.bolsa.pageobjects.SecBolsa;
import com.mng.robotest.tests.domains.bolsa.steps.SecBolsaSteps;
import com.mng.robotest.tests.domains.footer.steps.SecFooterSteps;
import com.mng.robotest.tests.domains.login.steps.PageIdentificacionSteps;
import com.mng.robotest.tests.domains.transversal.acceso.navigations.AccesoFlows;
import com.mng.robotest.tests.domains.transversal.menus.steps.SecMenusUserSteps;
import com.mng.robotest.tests.domains.transversal.prehome.steps.PagePrehomeSteps;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.pageobject.shop.menus.MenusUserWrapper;
import com.mng.robotest.testslegacy.pageobject.shop.modales.ModalCambioPais;

public class AccesoSteps extends StepBase {

	public void oneStep(boolean clearArticulos) throws Exception {
		new AccesoFlows().accesoHomeAppWeb();
		if (dataTest.isUserRegistered() && app!=AppEcom.votf) {
			identification(dataTest, clearArticulos);
		}
	}
	
	@Step (
		description= 
			"Seleccionar \"Iniciar sesión\" e identificarse con el usuario <b style=\"color:blue;\">#{dataTest.getUserConnected()}</b> " + 
			"(borrar artículos bolsa: <b>#{clearArticulos}</b>)",
		expected="el login es correcto",
		saveNettraffic=SaveWhen.Always)
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
				.isMenuInStateUntil(MI_CUENTA, Present, seconds);
	}
	
	@Validation
	public ChecksTM checkLinksAfterLogin() {
		var checks = ChecksTM.getNew();
		int seconds = 7;
		var userMenus = new MenusUserWrapper();
		checks.add(
			"Aparece el link \"Mi cuenta\" " + getLitSecondsWait(seconds),
			userMenus.isMenuInStateUntil(MI_CUENTA, Present, seconds));
		
		boolean isVisibleMenuFav = userMenus.isMenuInStateUntil(FAVORITOS, Present, 0);
		if (app==AppEcom.outlet) { 
			checks.add(
				"NO aparece el link \"Favoritos\"",
				!isVisibleMenuFav);
		} else {
			checks.add(
				"Aparece el link \"Favoritos\"",
				isVisibleMenuFav);
		}
		
		if (channel!=Channel.desktop) {
			boolean isPresentLinkMisCompras = userMenus.isMenuInState(MIS_COMPRAS, Present);
			checks.add(
				"Aparece el link \"Mis Compras\"",
				isPresentLinkMisCompras);
		}
		
		if (channel!=Channel.desktop) {
			checks.add(
				"Aparece el link \"Ayuda\"",
				userMenus.isMenuInState(AYUDA, Visible));
			checks.add(
				"Aparece el link \"Cerrar sesión\"",
				userMenus.isMenuInState(CERRAR_SESION, Present));
		}
		
		return checks;
	}

	/**
	 * Accedemos a la aplicación (shop/outlet/votf)
	 * Se ejecutan cada acción en un paso
	 */
	public void manySteps() throws Exception {
		if (app==AppEcom.votf && !dataTest.isUserRegistered()) { //En VOTF no tiene sentido identificarte con las credenciales del cliente
			accesoVOTFtoHOME();					
		} else {
			new PagePrehomeSteps().seleccionPaisIdiomaAndEnter(false, true);
			if (dataTest.isUserRegistered()) {
				identificacionEnMango();
				new SecBolsaSteps().clear();
			}
		}
	}

	public void identificacionEnMango() {
		if (!new MenusUserWrapper().isMenuInState(CERRAR_SESION, Present)) {
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
		saveNettraffic=SaveWhen.Always)
	public void identification(String userConnect, String userPassword) {
		new AccesoFlows().identification(userConnect, userPassword);
		validaIdentificacionEnShop();
	}
	
	public void login() {
		login(dataTest.getUserConnected(), dataTest.getPasswordUser());
	}
	
	@Step (
		description="Logarse con el usuario <b>#{userConnect}</b>", 
		expected="El login es correcto",
		saveNettraffic=SaveWhen.Always)
	private void login(String userConnect, String userPassword) {
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
		saveHtmlPage=SaveWhen.Always)
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

	/**
	 * Acceso vía URL de un país (no asociado a la IP del usuario)
	 * @param paisAccesoNoIP un país que no es el asociado a la IP del usuario
	 * @param paisAccesoPrevio en caso de ser <> null indica el país por cuya URL se ha accedido previamente
	 * @param paisPrevConf en caso de ser <> null indica el país que previamente se ha confirmado vía la opción de cambio existente en el modal
	 * @param vecesPaisConfPrev indica las veces que "paisConfirmado" se ha confirmado previamente 
	 * @param urlBaseTest URL base del test
	 * @param listPaisAsocIP lista de posibles países asociados a la IP del usuario (actualmente España, Irlanda o USA)
	 * @return el país asociado a la IP (al que te proponen cambiar en el modal)
	 */
	private static final String TAG_URL_ACCESO_PAIS_NO_IP = "@TagUrlAccesoPaisNoIp";
	private static final String TAG_LITERAL_IDIOMA_ORIGEN = "@TagLiteralIdiomaOrigen";
	
	@Step (
		description="Acceder a la shop vía la URL <b>" + TAG_URL_ACCESO_PAIS_NO_IP + "</b> (#{paisAccesoNoIP.getNombrePais()} / " + TAG_LITERAL_IDIOMA_ORIGEN + ")", 
		expected="Aparece un modal solicitando confirmación del país")
	public Pais accesoConURLPaisNoIP(
			String urlBaseTest, Pais paisAccesoNoIP, Pais paisAccesoPrevio, Pais paisPrevConf, int vecesPaisConfPrev, List<Pais> listPaisAsocIP) 
			throws Exception {
		Pais paisAsocIP = null;
		String urlAccesoPaisNoIp = paisAccesoNoIP.getUrlPaisEstandar(urlBaseTest);
		var idiomaOrigen = paisAccesoNoIP.getListIdiomas(app).get(0);
		replaceStepDescription(TAG_URL_ACCESO_PAIS_NO_IP, urlAccesoPaisNoIp);
		replaceStepDescription(TAG_LITERAL_IDIOMA_ORIGEN, idiomaOrigen.getLiteral());
		
		driver.get(urlAccesoPaisNoIp);
		waitForPageLoaded(driver);
		if (vecesPaisConfPrev < 2) {
			//Si se ha confirmado el país < 2 veces debería aparecer el modal del cambio de país
			var resultVal = validacAccesoSiApareceModal(urlBaseTest, paisAccesoNoIP, paisAccesoPrevio, paisPrevConf, listPaisAsocIP, driver);
			paisAsocIP = resultVal.getPais();
		} else {
			//Si el país se ha confirmado > 1 vez no debería aparecer el modal de cambio de país
			validacAccesoNoApareceModal(urlBaseTest, paisPrevConf, driver);
		}
			
		return paisAsocIP;
	}
		
	/**
	 * Valicaciones correspondientes al caso en que después del acceso SÍ aparece el modal de confirmacióin del país
	 * @return el país asociado a la IP (al que te proponen cambiar en el modal)
	 */
	@Validation 
	private static ResultValWithPais validacAccesoSiApareceModal(
			String urlBaseTest, Pais paisAccesoNoIP, Pais paisAccesoPrevio, Pais paisConfirmado, 
			List<Pais> listPaisAsocIP, WebDriver driver) throws Exception {
		
		ResultValWithPais checks = ResultValWithPais.getNew();
		var modalCambioPais = new ModalCambioPais();
		
		checks.add(
			"Aparece un modal solicitando confirmación de país",
			modalCambioPais.isVisibleModalUntil(0));
		
		if (paisAccesoPrevio==null) {
			checks.add(
				"En el modal <b>No</b> aparece un link con la opción de confirmar el país " + paisAccesoNoIP.getNombrePais() + 
				" (" + paisAccesoNoIP.getCodigoPais() + ")",
				!modalCambioPais.isLinkToConfirmPais(paisAccesoNoIP.getNombrePais()));
		} else {
			if (paisConfirmado==null) {
				checks.add(
					"En el modal <b>Sí</b> aparece un link con la opción de confirmar el acceso al país por el que previsamente se ha accedido vía URL: " + 
					paisAccesoPrevio.getNombrePais() + " (" + paisAccesoPrevio.getCodigoPais() + ")",
					modalCambioPais.isLinkToConfirmPais(paisAccesoPrevio.getUrlPaisEstandar(urlBaseTest)));
			} else {
				checks.add(
					"En el modal <b>No</b> aparece un link con la opción de confirmar el acceso al país por el que previsamente se ha accedido vía URL: " + 
					paisAccesoPrevio.getNombrePais() + " (" + paisAccesoPrevio.getCodigoPais() + ")",
					!modalCambioPais.isLinkToConfirmPais(paisAccesoPrevio.getNombrePais()));
			}
		}
		
		String paisesAsocIP = "";
		Iterator<Pais> it = listPaisAsocIP.iterator();
		while (it.hasNext()) {
			paisesAsocIP = paisesAsocIP + ", " + it.next().getNombrePais();
		}
		Pais paisButtonAssociated = modalCambioPais.getPaisOfButtonForChangePais(listPaisAsocIP, urlBaseTest);
		checks.add(
			"En el modal aparece un botón con la opción de cambiar a uno de los posibles países asociados a la IP (" + paisesAsocIP + ")",
			paisButtonAssociated!=null);
		
		checks.setPais(paisButtonAssociated);
		
		return checks;
	}
	
	/**
	 * Valicaciones correspondientes al caso en que después del acceso NO aparece el modal de confirmacióin del país
	 * @throws Exception
	 */
	@Validation
	private static ChecksTM validacAccesoNoApareceModal(String urlBaseTest, Pais paisPrevConf, WebDriver driver) 
			throws Exception {
		ResultValWithPais checks = ResultValWithPais.getNew();
		checks.add(
			"No aparece un modal solicitando confirmación de país",
			!new ModalCambioPais().isVisibleModalUntil(0));
		
		String nombrePaisPrevConf = paisPrevConf.getNombrePais();
		String hrefPaisPrevConf = paisPrevConf.getUrlPaisEstandar(urlBaseTest);
		checks.add(
			"Se ha redirigido a la URL del país confirmado previamente <b>" + nombrePaisPrevConf + "</b> (" + hrefPaisPrevConf + ")",
			(driver.getCurrentUrl().toLowerCase().contains(hrefPaisPrevConf.toLowerCase())));
		
		return checks;
	}
	
	private static final String TAG_PAIS_BOTON_CAMBIO = "@TagPaisBotonCambio";
	private static final String TAG_HREF_BOTON_CAMBIO = "@TagHrefBotonCambio";
	
	@Step (
		description="Confirmamos la propuesta de país del modal <b>" + TAG_PAIS_BOTON_CAMBIO + "</b>", 
		expected="Se redirige a la URL " + TAG_HREF_BOTON_CAMBIO)
	public void selectConfirmPaisModal() {
		var modalCambioPais = new ModalCambioPais();
		String paisBotonCambio = modalCambioPais.getTextPaisButtonChagePais();
		String hrefBotonCambioPais = modalCambioPais.getHRefPaisButtonChagePais();
		replaceStepDescription(TAG_PAIS_BOTON_CAMBIO, paisBotonCambio);
		replaceStepExpected(TAG_HREF_BOTON_CAMBIO, hrefBotonCambioPais);
		
		modalCambioPais.clickButtonChangePais();
		checkIsDoneRedirectToCountry(paisBotonCambio, hrefBotonCambioPais, driver);
	}
	
	@Validation (
		description="Se redirige a la URL del país #{paisBotonCambio} (#{hrefBotonCambioPais})")
	private static boolean checkIsDoneRedirectToCountry(
			String paisBotonCambio, String hrefBotonCambioPais, WebDriver driver) {
		return (driver.getCurrentUrl().toLowerCase().contains(hrefBotonCambioPais.toLowerCase()));
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