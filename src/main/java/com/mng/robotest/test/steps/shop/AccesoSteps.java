package com.mng.robotest.test.steps.shop;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.pageobject.shop.bolsa.SecBolsa;
import com.mng.robotest.test.pageobject.shop.identificacion.PageIdentificacion;
import com.mng.robotest.test.pageobject.shop.menus.MenusUserWrapper;
import com.mng.robotest.test.pageobject.shop.menus.SecMenusWrap;
import com.mng.robotest.test.pageobject.shop.menus.MenuUserItem.UserMenu;
import com.mng.robotest.test.pageobject.shop.menus.desktop.SecMenusDesktop;
import com.mng.robotest.test.pageobject.shop.modales.ModalCambioPais;
import com.mng.robotest.test.steps.navigations.shop.AccesoNavigations;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks.GenericCheck;
import com.mng.robotest.test.steps.votf.PageLoginVOTFSteps;
import com.mng.robotest.test.steps.votf.PageSelectIdiomaVOTFSteps;
import com.mng.robotest.test.steps.votf.PageSelectLineaVOTFSteps;
import com.mng.robotest.test.utils.Robotest;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.SeleniumUtils;

@SuppressWarnings({"static-access"})
public class AccesoSteps {

	static final String tagNombrePais = "@TagNombrePais";
	static final String tagLiteralIdioma = "@TagLiteralIdioma";
	static final String tagRegistro = "@TagRegistro";
	
	public static void defaultAccess(WebDriver driver) throws Exception {
		DataCtxShop dCtxSh = Robotest.getDefaultDataShop();
		oneStep(dCtxSh, false, driver);
	}
	
	@Step (
		description="Acceder a Mango (" + tagNombrePais + "/" + tagLiteralIdioma + ")<br>" + tagRegistro, 
		expected="Se accede correctamente",
		saveNettraffic=SaveWhen.Always)
	public static void oneStep(DataCtxShop dCtxSh, boolean clearArticulos, WebDriver driver) 
	throws Exception {
		String registro = "";
		if (dCtxSh.userRegistered && dCtxSh.appE!=AppEcom.votf) {
			registro = "Identificarse con el usuario <b>" + dCtxSh.userConnected + "</b><br>"; 
		}
		if (clearArticulos) {
			registro+= "Borrar la Bolsa<br>";
		}

		StepTM StepTestMaker = TestMaker.getCurrentStepInExecution();
		StepTestMaker.replaceInDescription(tagNombrePais, dCtxSh.pais.getNombre_pais());
		StepTestMaker.replaceInDescription(tagLiteralIdioma, dCtxSh.idioma.getCodigo().getLiteral());
		StepTestMaker.replaceInDescription(tagRegistro, registro);

		AccesoNavigations.accesoHomeAppWeb(dCtxSh, driver);
		if (dCtxSh.userRegistered && dCtxSh.appE!=AppEcom.votf) {
			new PageIdentificacion().iniciarSesion(dCtxSh);
		}

		if (clearArticulos) {
			SecBolsa secBolsa = SecBolsa.make(dCtxSh);
			secBolsa.clearArticulos();
		}

		if (dCtxSh.userRegistered && dCtxSh.appE!=AppEcom.votf) {
			validaIdentificacionEnShop(dCtxSh, driver);
		}
	}

	public static void validaIdentificacionEnShop(DataCtxShop dCtxSh, WebDriver driver) throws Exception {
		checkLinksAfterLogin(dCtxSh, driver);
		GenericChecks.from(Arrays.asList(
				GenericCheck.CookiesAllowed,
				GenericCheck.GoogleAnalytics, 
				GenericCheck.JSerrors, 
				GenericCheck.Analitica,
				GenericCheck.TextsTraduced,
				GenericCheck.NetTraffic)).checks(driver);
	}
	
	@Validation
	private static ChecksTM checkLinksAfterLogin(DataCtxShop dCtxSh, WebDriver driver) throws Exception {
		ChecksTM checks = ChecksTM.getNew();
		int maxSeconds = 5;
		MenusUserWrapper userMenus = new SecMenusWrap(dCtxSh.channel, dCtxSh.appE).getMenusUser();
		checks.add(
			"Aparece el link \"Mi cuenta\" (lo esperamos hasta " + maxSeconds + " segundos)",
			userMenus.isMenuInStateUntil(UserMenu.miCuenta, Present, maxSeconds), State.Defect);
		
		boolean isVisibleMenuFav = userMenus.isMenuInStateUntil(UserMenu.favoritos, Present, 0);
		if (dCtxSh.appE==AppEcom.outlet) { 
			checks.add(
				"NO aparece el link \"Favoritos\"",
				!isVisibleMenuFav, State.Defect);
//			if (dCtxSh.channel.isDevice()) {
//				checks.add(
//					"Aparece el link \"Mis Pedidos\"",
//					userMenus.isMenuInState(UserMenu.pedidos, Present), State.Defect);
//			}
		} else {
			checks.add(
				"Aparece el link \"Favoritos\"",
				isVisibleMenuFav, State.Defect);
		}
		
		if (dCtxSh.channel!=Channel.desktop) {
			boolean isPresentLinkMisCompras = userMenus.isMenuInState(UserMenu.misCompras, Present);
			checks.add(
				"Aparece el link \"Mis Compras\"",
				isPresentLinkMisCompras, State.Defect);
		}
		
		if (dCtxSh.channel!=Channel.desktop) {
			checks.add(
				"Aparece el link \"Ayuda\"",
				userMenus.isMenuInState(UserMenu.ayuda, Visible), State.Defect);
			checks.add(
				"Aparece el link \"Cerrar sesión\"",
				userMenus.isMenuInState(UserMenu.cerrarSesion, Present), State.Defect);
		}
		
		if (dCtxSh.channel==Channel.desktop) {
			SecMenusDesktop secMenus = new SecMenusDesktop(dCtxSh.appE, dCtxSh.channel);
			checks.add(
				"Aparece una página con menús de MANGO",
				secMenus.secMenuSuperior.secLineas.isPresentLineasMenuWrapp(), State.Warn);
		}

		return checks;
	}

	/**
	 * Accedemos a la aplicación (shop/outlet/votf)
	 * Se ejecutan cada acción en un paso
	 */
	public static void manySteps(DataCtxShop dCtxSh, WebDriver driver) throws Exception {
		if (dCtxSh.appE==AppEcom.votf && !dCtxSh.userRegistered) { //En VOTF no tiene sentido identificarte con las credenciales del cliente
			AccesoSteps.accesoVOTFtoHOME(dCtxSh, driver);					
		} else {
			new PagePrehomeSteps(dCtxSh, driver).seleccionPaisIdiomaAndEnter(false);
			if (dCtxSh.userRegistered) {
				identificacionEnMango(dCtxSh, driver);
				SecBolsaSteps secBolsaSteps = new SecBolsaSteps(dCtxSh);
				secBolsaSteps.clear();
			}
		}
	}

	public static void identificacionEnMango(DataCtxShop dCtxSh, WebDriver driver) throws Exception {
		MenusUserWrapper userMenus = new SecMenusWrap(dCtxSh.channel, dCtxSh.appE).getMenusUser();
		if (!userMenus.isMenuInState(UserMenu.cerrarSesion, Present)) {
			iniciarSesion(dCtxSh, driver);
		}
	}

	@Step (
		description="Seleccionar \"Iniciar Sesión\" e identificarse con #{dCtxSh.getUserConnected()}", 
		expected="La identificación es correcta",
		saveHtmlPage=SaveWhen.Always,
		saveNettraffic=SaveWhen.Always)
	private static void iniciarSesion(DataCtxShop dCtxSh, WebDriver driver) throws Exception {
		new PageIdentificacion().iniciarSesion(dCtxSh);
		validaIdentificacionEnShop(dCtxSh, driver);
	}

	//Acceso a VOTF (identificación + selección idioma + HOME she)
	public static void accesoVOTFtoHOME(DataCtxShop dCtxSh, WebDriver driver) throws Exception {
		String urlAcceso = TestMaker.getInputParamsSuite().getUrlBase();
		int numIdiomas = dCtxSh.pais.getListIdiomas().size();
		
		new PageLoginVOTFSteps(driver).goToAndLogin(urlAcceso, dCtxSh);
		if (numIdiomas > 1) {
			new PageSelectIdiomaVOTFSteps(driver).selectIdiomaAndContinue(dCtxSh.idioma);
		}

		PageSelectLineaVOTFSteps pageSelectLineaVOTFSteps = new PageSelectLineaVOTFSteps(driver);
		pageSelectLineaVOTFSteps.validateIsPage();
		GenericChecks.from(Arrays.asList(
				GenericCheck.CookiesAllowed,
				GenericCheck.Analitica, 
				GenericCheck.TextsTraduced,
				GenericCheck.JSerrors)).checks(driver);
		
		pageSelectLineaVOTFSteps.selectMenuAndLogoMango(1, dCtxSh);
	}


	static final String tagNombrePaisOrigen = "@TagPaisOrigen";
	static final String tagCodigoPaisOrigen = "@TagCodigoPaisOrigen";
	static final String tagNombreIdiomaOrigen = "@TagIdiomaOrigen";
	static final String tagNodoOrigen = "@TagNodoOrigen";
	static final String tagNodoDestino = "@TagNodoDestino";
	@Step (
		description=
			"Datos del cambio de país <br>" + 
			"<b>" + tagNombrePaisOrigen + "</b> (" + tagCodigoPaisOrigen + "), <b>idioma " + tagNombreIdiomaOrigen + "</b><br>" +  
			"<b>#{paisDestino.getNombre_pais()}</b> (#{paisDestino.getCodigo_pais()}), <b>idioma #{idiomaDestino.getLiteral()}</b>",
		expected=
			"Se accede a la shop de #{paisDestino.getNombre_pais()} en #{idiomaDestino.getLiteral()}",
		saveHtmlPage=SaveWhen.Always)
	public static void accesoPRYCambioPais(DataCtxShop dCtxSh, Pais paisDestino, IdiomaPais idiomaDestino, WebDriver driver) 
	throws Exception {
		StepTM StepTestMaker = TestMaker.getCurrentStepInExecution();
		StepTestMaker.replaceInDescription(tagNombrePaisOrigen, dCtxSh.pais.getNombre_pais());
		StepTestMaker.replaceInDescription(tagCodigoPaisOrigen, dCtxSh.pais.getCodigo_pais());
		StepTestMaker.replaceInDescription(tagNombreIdiomaOrigen, dCtxSh.idioma.getLiteral());
	
		AccesoSteps.manySteps(dCtxSh, driver);

		Pais paisOriginal = dCtxSh.pais;
		IdiomaPais idiomaOriginal = dCtxSh.idioma;
		dCtxSh.pais = paisDestino;
		dCtxSh.idioma = idiomaDestino;
		(new SecFooterSteps(dCtxSh.channel, dCtxSh.appE, driver)).cambioPais(dCtxSh);
		dCtxSh.pais = paisOriginal;
		dCtxSh.idioma = idiomaOriginal;

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
	static final String tagUrlAccesoPaisNoIp = "@TagUrlAccesoPaisNoIp";
	static final String tagLiteralIdiomaOrigen = "@TagLiteralIdiomaOrigen";
	@Step (
		description="Acceder a la shop vía la URL <b>" + tagUrlAccesoPaisNoIp + "</b> (#{paisAccesoNoIP.getNombre_pais()} / " + tagLiteralIdiomaOrigen + ")", 
		expected="Aparece un modal solicitando confirmación del país")
	public static Pais accesoConURLPaisNoIP(String urlBaseTest, Pais paisAccesoNoIP, Pais paisAccesoPrevio, Pais paisPrevConf, 
											int vecesPaisConfPrev, List<Pais> listPaisAsocIP, WebDriver driver) throws Exception {
		Pais paisAsocIP = null;
		String urlAccesoPaisNoIp = paisAccesoNoIP.getUrlPaisEstandar(urlBaseTest);
		IdiomaPais idiomaOrigen = paisAccesoNoIP.getListIdiomas().get(0);
		TestMaker.getCurrentStepInExecution().replaceInDescription(tagUrlAccesoPaisNoIp, urlAccesoPaisNoIp);
		TestMaker.getCurrentStepInExecution().replaceInDescription(tagLiteralIdiomaOrigen, idiomaOrigen.getLiteral());
		
		driver.get(urlAccesoPaisNoIp);
		SeleniumUtils.waitForPageLoaded(driver);
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
			modalCambioPais.isVisibleModalUntil(0), State.Defect);
		
		if (paisAccesoPrevio==null) {
			checks.add(
				"En el modal <b>No</b> aparece un link con la opción de confirmar el país " + paisAccesoNoIP.getNombre_pais() + 
				" (" + paisAccesoNoIP.getCodigo_pais() + ")",
				!modalCambioPais.isLinkToConfirmPais(paisAccesoNoIP.getNombre_pais()), State.Defect);
		} else {
			if (paisConfirmado==null) {
				checks.add(
					"En el modal <b>Sí</b> aparece un link con la opción de confirmar el acceso al país por el que previsamente se ha accedido vía URL: " + 
					paisAccesoPrevio.getNombre_pais() + " (" + paisAccesoPrevio.getCodigo_pais() + ")",
					modalCambioPais.isLinkToConfirmPais(paisAccesoPrevio.getUrlPaisEstandar(urlBaseTest)), State.Defect);
			} else {
				checks.add(
					"En el modal <b>No</b> aparece un link con la opción de confirmar el acceso al país por el que previsamente se ha accedido vía URL: " + 
					paisAccesoPrevio.getNombre_pais() + " (" + paisAccesoPrevio.getCodigo_pais() + ")",
					!modalCambioPais.isLinkToConfirmPais(paisAccesoPrevio.getNombre_pais()), State.Defect);
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
			paisButtonAssociated!=null, State.Defect);
		
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
			!new ModalCambioPais().isVisibleModalUntil(0), State.Defect);
		
		String nombrePaisPrevConf = paisPrevConf.getNombre_pais();
		String hrefPaisPrevConf = paisPrevConf.getUrlPaisEstandar(urlBaseTest);
		checks.add(
			"Se ha redirigido a la URL del país confirmado previamente <b>" + nombrePaisPrevConf + "</b> (" + hrefPaisPrevConf + ")",
			(driver.getCurrentUrl().toLowerCase().contains(hrefPaisPrevConf.toLowerCase())), State.Defect);
		
		return checks;
	}
	
	static final String tagPaisBotonCambio = "@TagPaisBotonCambio";
	static final String tagHrefBotonCambio = "@TagHrefBotonCambio";
	@Step (
		description="Confirmamos la propuesta de país del modal <b>" + tagPaisBotonCambio + "</b>", 
		expected="Se redirige a la URL " + tagHrefBotonCambio)
	public static void selectConfirmPaisModal(WebDriver driver) {
		ModalCambioPais modalCambioPais = new ModalCambioPais();
		
		String paisBotonCambio = modalCambioPais.getTextPaisButtonChagePais();
		String hrefBotonCambioPais = modalCambioPais.getHRefPaisButtonChagePais();
		TestMaker.getCurrentStepInExecution().replaceInDescription(tagPaisBotonCambio, paisBotonCambio);
		TestMaker.getCurrentStepInExecution().replaceInExpected(tagHrefBotonCambio, hrefBotonCambioPais);
		
		modalCambioPais.clickButtonChangePais();
		checkIsDoneRedirectToCountry(paisBotonCambio, hrefBotonCambioPais, driver);
	}
	
	@Validation (
		description="Se redirige a la URL del país #{paisBotonCambio} (#{hrefBotonCambioPais})",
		level=State.Defect)
	private static boolean checkIsDoneRedirectToCountry(@SuppressWarnings("unused") String paisBotonCambio, 
														String hrefBotonCambioPais, WebDriver driver) {
		return (driver.getCurrentUrl().toLowerCase().contains(hrefBotonCambioPais.toLowerCase()));
	}
	
	@Step (
		description="Cargar la URL inicial", 
		expected="La URL se carga correctamente")
	public static void goToInitialURL(WebDriver driver) {
		InputParamsMango inputParamsSuite = (InputParamsMango)TestMaker.getInputParamsSuite();
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