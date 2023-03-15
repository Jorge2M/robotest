package com.mng.robotest.test.steps.shop;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.data.DataTest;
import com.mng.robotest.domains.bolsa.pageobjects.SecBolsa;
import com.mng.robotest.domains.bolsa.steps.SecBolsaSteps;
import com.mng.robotest.domains.footer.steps.SecFooterSteps;
import com.mng.robotest.domains.login.pageobjects.PageIdentificacion;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.domains.transversal.prehome.steps.PagePrehomeSteps;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.pageobject.shop.menus.MenusUserWrapper;
import com.mng.robotest.test.pageobject.shop.modales.ModalCambioPais;
import com.mng.robotest.test.steps.navigations.shop.AccesoNavigations;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks.GenericCheck;
import com.mng.robotest.test.steps.votf.PageLoginVOTFSteps;
import com.mng.robotest.test.steps.votf.PageSelectIdiomaVOTFSteps;
import com.mng.robotest.test.steps.votf.PageSelectLineaVOTFSteps;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

import static com.github.jorge2m.testmaker.conf.State.*;
import static com.mng.robotest.test.pageobject.shop.menus.MenuUserItem.UserMenu.*;

public class AccesoSteps extends StepBase {

	private static final String TAG_NOMBRE_PAIS = "@TagNombrePais";
	private static final String TAG_LITERAL_IDIOMA = "@TagLiteralIdioma";
	private static final String TAG_REGISTRO = "@TagRegistro";
	
	@Step (
		description="Acceder a Mango (" + TAG_NOMBRE_PAIS + "/" + TAG_LITERAL_IDIOMA + ")<br>" + TAG_REGISTRO, 
		expected="Se accede correctamente",
		saveNettraffic=SaveWhen.Always)
	public void oneStep(boolean clearArticulos) throws Exception {
		String registro = "";
		if (dataTest.isUserRegistered() && app!=AppEcom.votf) {
			registro = "Identificarse con el usuario <b>" + dataTest.getUserConnected() + "</b><br>"; 
		}
		if (clearArticulos) {
			registro+= "Borrar la Bolsa<br>";
		}

		StepTM stepTestMaker = TestMaker.getCurrentStepInExecution();
		stepTestMaker.replaceInDescription(TAG_NOMBRE_PAIS, dataTest.getPais().getNombre_pais());
		stepTestMaker.replaceInDescription(TAG_LITERAL_IDIOMA, dataTest.getIdioma().getCodigo().getLiteral());
		stepTestMaker.replaceInDescription(TAG_REGISTRO, registro);

		new AccesoNavigations().accesoHomeAppWeb();
		if (dataTest.isUserRegistered() && app!=AppEcom.votf) {
			new PageIdentificacion().iniciarSesion(dataTest.getUserConnected(), dataTest.getPasswordUser());
		}

		if (clearArticulos) {
			new SecBolsa().clearArticulos();
		}

		if (dataTest.isUserRegistered() && app!=AppEcom.votf) {
			validaIdentificacionEnShop();
		}
	}

	public void validaIdentificacionEnShop() {
		checkLinksAfterLogin();
		GenericChecks.checkDefault();
		GenericChecks.from(Arrays.asList(
				GenericCheck.GOOGLE_ANALYTICS, 
				GenericCheck.NET_TRAFFIC)).checks();
	}
	
	@Validation(
		description="Aparece el link \"Mi cuenta\" (usuario loginado) (lo esperamos hasta #{seconds} segundos)",
		level=Defect)
	public boolean checkIsLogged(int seconds) {
		return new MenusUserWrapper()
				.isMenuInStateUntil(MI_CUENTA, Present, seconds);
	}
	
	@Validation
	public ChecksTM checkLinksAfterLogin() {
		var checks = ChecksTM.getNew();
		int seconds = 7;
		MenusUserWrapper userMenus = new MenusUserWrapper();
		checks.add(
			"Aparece el link \"Mi cuenta\" (lo esperamos hasta " + seconds + " segundos)",
			userMenus.isMenuInStateUntil(MI_CUENTA, Present, seconds), Defect);
		
		boolean isVisibleMenuFav = userMenus.isMenuInStateUntil(FAVORITOS, Present, 0);
		if (app==AppEcom.outlet) { 
			checks.add(
				"NO aparece el link \"Favoritos\"",
				!isVisibleMenuFav, Defect);
		} else {
			checks.add(
				"Aparece el link \"Favoritos\"",
				isVisibleMenuFav, Defect);
		}
		
		if (channel!=Channel.desktop) {
			boolean isPresentLinkMisCompras = userMenus.isMenuInState(MIS_COMPRAS, Present);
			checks.add(
				"Aparece el link \"Mis Compras\"",
				isPresentLinkMisCompras, Defect);
		}
		
		if (channel!=Channel.desktop) {
			checks.add(
				"Aparece el link \"Ayuda\"",
				userMenus.isMenuInState(AYUDA, Visible), Defect);
			checks.add(
				"Aparece el link \"Cerrar sesión\"",
				userMenus.isMenuInState(CERRAR_SESION, Present), Defect);
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
			new PagePrehomeSteps().seleccionPaisIdiomaAndEnter(false);
			if (dataTest.isUserRegistered()) {
				identificacionEnMango();
				SecBolsaSteps secBolsaSteps = new SecBolsaSteps();
				secBolsaSteps.clear();
			}
		}
	}

	public void identificacionEnMango() {
		if (!new MenusUserWrapper().isMenuInState(CERRAR_SESION, Present)) {
			iniciarSesion(dataTest);
		}
	}

	@Step (
		description="Seleccionar \"Iniciar Sesión\" e identificarse con #{dataTest.getUserConnected()}", 
		expected="La identificación es correcta",
		saveHtmlPage=SaveWhen.Always,
		saveNettraffic=SaveWhen.Always)
	private void iniciarSesion(DataTest dataTest) {
		new PageIdentificacion().iniciarSesion(dataTest.getUserConnected(), dataTest.getPasswordUser());
		validaIdentificacionEnShop();
	}

	public void accesoVOTFtoHOME() throws Exception {
		String urlAcceso = inputParamsSuite.getUrlBase();
		int numIdiomas = dataTest.getPais().getListIdiomas().size();
		
		new PageLoginVOTFSteps().goToAndLogin(urlAcceso);
		if (numIdiomas > 1) {
			new PageSelectIdiomaVOTFSteps().selectIdiomaAndContinue();
		}

		PageSelectLineaVOTFSteps pageSelectLineaVOTFSteps = new PageSelectLineaVOTFSteps();
		pageSelectLineaVOTFSteps.validateIsPage();
		GenericChecks.checkDefault();
		
		pageSelectLineaVOTFSteps.selectMenuAndLogoMango(1);
	}


	private static final String TAG_NOMBRE_PAIS_ORIGEN = "@TagPaisOrigen";
	private static final String TAG_CODIGO_PAIS_ORIGEN = "@TagCodigoPaisOrigen";
	private static final String TAG_NOMBRE_IDIOMA_ORIGEN = "@TagIdiomaOrigen";
	
	@Step (
		description=
			"Datos del cambio de país <br>" + 
			"<b>" + TAG_NOMBRE_PAIS_ORIGEN + "</b> (" + TAG_CODIGO_PAIS_ORIGEN + "), <b>idioma " + TAG_NOMBRE_IDIOMA_ORIGEN + "</b><br>" +  
			"<b>#{paisDestino.getNombre_pais()}</b> (#{paisDestino.getCodigo_pais()}), <b>idioma #{idiomaDestino.getLiteral()}</b>",
		expected=
			"Se accede a la shop de #{paisDestino.getNombre_pais()} en #{idiomaDestino.getLiteral()}",
		saveHtmlPage=SaveWhen.Always)
	public void accesoPRYCambioPais(Pais paisDestino, IdiomaPais idiomaDestino) throws Exception {
		StepTM StepTestMaker = TestMaker.getCurrentStepInExecution();
		StepTestMaker.replaceInDescription(TAG_NOMBRE_PAIS_ORIGEN, dataTest.getPais().getNombre_pais());
		StepTestMaker.replaceInDescription(TAG_CODIGO_PAIS_ORIGEN, dataTest.getCodigoPais());
		StepTestMaker.replaceInDescription(TAG_NOMBRE_IDIOMA_ORIGEN, dataTest.getIdioma().getLiteral());
	
		manySteps();

		Pais paisOriginal = dataTest.getPais();
		IdiomaPais idiomaOriginal = dataTest.getIdioma();
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
		description="Acceder a la shop vía la URL <b>" + TAG_URL_ACCESO_PAIS_NO_IP + "</b> (#{paisAccesoNoIP.getNombre_pais()} / " + TAG_LITERAL_IDIOMA_ORIGEN + ")", 
		expected="Aparece un modal solicitando confirmación del país")
	public static Pais accesoConURLPaisNoIP(String urlBaseTest, Pais paisAccesoNoIP, Pais paisAccesoPrevio, Pais paisPrevConf, 
											int vecesPaisConfPrev, List<Pais> listPaisAsocIP, WebDriver driver) throws Exception {
		Pais paisAsocIP = null;
		String urlAccesoPaisNoIp = paisAccesoNoIP.getUrlPaisEstandar(urlBaseTest);
		IdiomaPais idiomaOrigen = paisAccesoNoIP.getListIdiomas().get(0);
		TestMaker.getCurrentStepInExecution().replaceInDescription(TAG_URL_ACCESO_PAIS_NO_IP, urlAccesoPaisNoIp);
		TestMaker.getCurrentStepInExecution().replaceInDescription(TAG_LITERAL_IDIOMA_ORIGEN, idiomaOrigen.getLiteral());
		
		driver.get(urlAccesoPaisNoIp);
		PageObjTM.waitForPageLoaded(driver);
		if (vecesPaisConfPrev < 2) {
			//Si se ha confirmado el país < 2 veces debería aparecer el modal del cambio de país
			ResultValWithPais resultVal = validacAccesoSiApareceModal(urlBaseTest, paisAccesoNoIP, paisAccesoPrevio, paisPrevConf, listPaisAsocIP, driver);
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
		ModalCambioPais modalCambioPais = new ModalCambioPais();
		
		checks.add(
			"Aparece un modal solicitando confirmación de país",
			modalCambioPais.isVisibleModalUntil(0), Defect);
		
		if (paisAccesoPrevio==null) {
			checks.add(
				"En el modal <b>No</b> aparece un link con la opción de confirmar el país " + paisAccesoNoIP.getNombre_pais() + 
				" (" + paisAccesoNoIP.getCodigo_pais() + ")",
				!modalCambioPais.isLinkToConfirmPais(paisAccesoNoIP.getNombre_pais()), Defect);
		} else {
			if (paisConfirmado==null) {
				checks.add(
					"En el modal <b>Sí</b> aparece un link con la opción de confirmar el acceso al país por el que previsamente se ha accedido vía URL: " + 
					paisAccesoPrevio.getNombre_pais() + " (" + paisAccesoPrevio.getCodigo_pais() + ")",
					modalCambioPais.isLinkToConfirmPais(paisAccesoPrevio.getUrlPaisEstandar(urlBaseTest)), Defect);
			} else {
				checks.add(
					"En el modal <b>No</b> aparece un link con la opción de confirmar el acceso al país por el que previsamente se ha accedido vía URL: " + 
					paisAccesoPrevio.getNombre_pais() + " (" + paisAccesoPrevio.getCodigo_pais() + ")",
					!modalCambioPais.isLinkToConfirmPais(paisAccesoPrevio.getNombre_pais()), Defect);
			}
		}
		
		String paisesAsocIP = "";
		Iterator<Pais> it = listPaisAsocIP.iterator();
		while (it.hasNext()) {
			paisesAsocIP = paisesAsocIP + ", " + it.next().getNombre_pais();
		}
		Pais paisButtonAssociated = modalCambioPais.getPaisOfButtonForChangePais(listPaisAsocIP, urlBaseTest);
		checks.add(
			"En el modal aparece un botón con la opción de cambiar a uno de los posibles países asociados a la IP (" + paisesAsocIP + ")",
			paisButtonAssociated!=null, Defect);
		
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
			!new ModalCambioPais().isVisibleModalUntil(0), Defect);
		
		String nombrePaisPrevConf = paisPrevConf.getNombre_pais();
		String hrefPaisPrevConf = paisPrevConf.getUrlPaisEstandar(urlBaseTest);
		checks.add(
			"Se ha redirigido a la URL del país confirmado previamente <b>" + nombrePaisPrevConf + "</b> (" + hrefPaisPrevConf + ")",
			(driver.getCurrentUrl().toLowerCase().contains(hrefPaisPrevConf.toLowerCase())), Defect);
		
		return checks;
	}
	
	private static final String TAG_PAIS_BOTON_CAMBIO = "@TagPaisBotonCambio";
	private static final String TAG_HREF_BOTON_CAMBIO = "@TagHrefBotonCambio";
	
	@Step (
		description="Confirmamos la propuesta de país del modal <b>" + TAG_PAIS_BOTON_CAMBIO + "</b>", 
		expected="Se redirige a la URL " + TAG_HREF_BOTON_CAMBIO)
	public static void selectConfirmPaisModal(WebDriver driver) {
		ModalCambioPais modalCambioPais = new ModalCambioPais();
		
		String paisBotonCambio = modalCambioPais.getTextPaisButtonChagePais();
		String hrefBotonCambioPais = modalCambioPais.getHRefPaisButtonChagePais();
		TestMaker.getCurrentStepInExecution().replaceInDescription(TAG_PAIS_BOTON_CAMBIO, paisBotonCambio);
		TestMaker.getCurrentStepInExecution().replaceInExpected(TAG_HREF_BOTON_CAMBIO, hrefBotonCambioPais);
		
		modalCambioPais.clickButtonChangePais();
		checkIsDoneRedirectToCountry(paisBotonCambio, hrefBotonCambioPais, driver);
	}
	
	@Validation (
		description="Se redirige a la URL del país #{paisBotonCambio} (#{hrefBotonCambioPais})",
		level=Defect)
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