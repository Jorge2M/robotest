package com.mng.robotest.test80.mango.test.stpv.shop;

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

import com.mng.robotest.test80.access.InputParamsMango;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.beans.IdiomaPais;
import com.mng.robotest.test80.mango.test.beans.Pais;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.SeleniumUtils;
import com.mng.robotest.test80.mango.test.pageobject.shop.bolsa.SecBolsa;
import com.mng.robotest.test80.mango.test.pageobject.shop.identificacion.PageIdentificacion;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.MenuUserItem.UserMenu;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.MenusUserWrapper;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.SecMenusWrap;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.desktop.SecMenusDesktop;
import com.mng.robotest.test80.mango.test.pageobject.shop.modales.ModalCambioPais;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.AccesoNavigations;
import com.mng.robotest.test80.mango.test.stpv.shop.genericchecks.GenericChecks;
import com.mng.robotest.test80.mango.test.stpv.shop.genericchecks.GenericChecks.GenericCheck;
import com.mng.robotest.test80.mango.test.stpv.votf.PageLoginVOTFStpV;
import com.mng.robotest.test80.mango.test.stpv.votf.PageSelectIdiomaVOTFStpV;
import com.mng.robotest.test80.mango.test.stpv.votf.PageSelectLineaVOTFStpV;
import com.mng.robotest.test80.mango.test.utils.Test80;

@SuppressWarnings({"static-access"})
public class AccesoStpV {

	final static String tagNombrePais = "@TagNombrePais";
	final static String tagLiteralIdioma = "@TagLiteralIdioma";
	final static String tagRegistro = "@TagRegistro";
	
	public static void defaultAccess(WebDriver driver) throws Exception {
		DataCtxShop dCtxSh = Test80.getDefaultDataShop();
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
			PageIdentificacion.iniciarSesion(dCtxSh, driver);
		}

		if (clearArticulos) {
			SecBolsa secBolsa = SecBolsa.make(dCtxSh, driver);
			secBolsa.clearArticulos();
		}

		if (dCtxSh.userRegistered && dCtxSh.appE!=AppEcom.votf) {
			validaIdentificacionEnShop(dCtxSh, driver);
		}
	}

	public static void validaIdentificacionEnShop(DataCtxShop dCtxSh, WebDriver driver) throws Exception {
		checkLinksAfterLogin(dCtxSh, driver);
		GenericChecks.from(Arrays.asList(
				GenericCheck.GoogleAnalytics, 
				GenericCheck.JSerrors, 
				GenericCheck.Analitica,
				GenericCheck.TextsTraduced,
				GenericCheck.NetTraffic)).checks(driver);
	}
	
	@Validation
	private static ChecksTM checkLinksAfterLogin(DataCtxShop dCtxSh, WebDriver driver) throws Exception {
		ChecksTM validations = ChecksTM.getNew();
		int maxSeconds = 5;
		MenusUserWrapper userMenus = SecMenusWrap.getNew(dCtxSh.channel, dCtxSh.appE, driver).getMenusUser();
		validations.add(
			"Aparece el link \"Mi cuenta\" (lo esperamos hasta " + maxSeconds + " segundos)",
			userMenus.isMenuInStateUntil(UserMenu.miCuenta, Present, maxSeconds), State.Defect);
		
		boolean isVisibleMenuFav = userMenus.isMenuInStateUntil(UserMenu.favoritos, Present, 0);
		if (dCtxSh.appE==AppEcom.outlet) { 
			validations.add(
				"NO aparece el link \"Favoritos\"",
				!isVisibleMenuFav, State.Defect);
//			if (dCtxSh.channel.isDevice()) {
//				validations.add(
//					"Aparece el link \"Mis Pedidos\"",
//					userMenus.isMenuInState(UserMenu.pedidos, Present), State.Defect);
//			}
		} else {
			validations.add(
				"Aparece el link \"Favoritos\"",
				isVisibleMenuFav, State.Defect);
		}
		
		if (dCtxSh.channel!=Channel.desktop) {
			boolean isPresentLinkMisCompras = userMenus.isMenuInState(UserMenu.misCompras, Present);
			validations.add(
				"Aparece el link \"Mis Compras\"",
				isPresentLinkMisCompras, State.Defect);
		}
		
		if (dCtxSh.channel!=Channel.desktop) {
			validations.add(
				"Aparece el link \"Ayuda\"",
				userMenus.isMenuInState(UserMenu.ayuda, Visible), State.Defect);
			validations.add(
				"Aparece el link \"Cerrar sesión\"",
				userMenus.isMenuInState(UserMenu.cerrarSesion, Present), State.Defect);
		}
		
		if (dCtxSh.channel==Channel.desktop) {
			SecMenusDesktop secMenus = SecMenusDesktop.getNew(dCtxSh.appE, driver);
			validations.add(
				"Aparece una página con menús de MANGO",
				secMenus.secMenuSuperior.secLineas.isPresentLineasMenuWrapp(), State.Warn);
		}

		return validations;
	}

	/**
	 * Accedemos a la aplicación (shop/outlet/votf)
	 * Se ejecutan cada acción en un paso
	 */
	public static void manySteps(DataCtxShop dCtxSh, WebDriver driver) throws Exception {
		if (dCtxSh.appE==AppEcom.votf && !dCtxSh.userRegistered) { //En VOTF no tiene sentido identificarte con las credenciales del cliente
			AccesoStpV.accesoVOTFtoHOME(dCtxSh, driver);					
		} else {
			new PagePrehomeStpV(dCtxSh, driver).seleccionPaisIdiomaAndEnter(false);
			if (dCtxSh.userRegistered) {
				identificacionEnMango(dCtxSh, driver);
				SecBolsaStpV secBolsaStpV = new SecBolsaStpV(dCtxSh, driver);
				secBolsaStpV.clear();
			}
		}
	}

	public static void identificacionEnMango(DataCtxShop dCtxSh, WebDriver driver) throws Exception {
		MenusUserWrapper userMenus = SecMenusWrap.getNew(dCtxSh.channel, dCtxSh.appE, driver).getMenusUser();
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
		PageIdentificacion.iniciarSesion(dCtxSh, driver);
		validaIdentificacionEnShop(dCtxSh, driver);
	}

	//Acceso a VOTF (identificación + selección idioma + HOME she)
	public static void accesoVOTFtoHOME(DataCtxShop dCtxSh, WebDriver driver) throws Exception {
		String urlAcceso = TestMaker.getTestCase().getInputParamsSuite().getUrlBase();
		int numIdiomas = dCtxSh.pais.getListIdiomas().size();
		PageLoginVOTFStpV.goToAndLogin(urlAcceso, dCtxSh, driver);
		if (numIdiomas > 1) {
			PageSelectIdiomaVOTFStpV.selectIdiomaAndContinue(dCtxSh.idioma, driver);
		}

		PageSelectLineaVOTFStpV.validateIsPage(driver);
		GenericChecks.from(Arrays.asList(
				GenericCheck.Analitica, 
				GenericCheck.TextsTraduced,
				GenericCheck.JSerrors)).checks(driver);
		
		PageSelectLineaVOTFStpV.selectMenuAndLogoMango(1, dCtxSh, driver);
	}


	final static String tagNombrePaisOrigen = "@TagPaisOrigen";
	final static String tagCodigoPaisOrigen = "@TagCodigoPaisOrigen";
	final static String tagNombreIdiomaOrigen = "@TagIdiomaOrigen";
	final static String tagNodoOrigen = "@TagNodoOrigen";
	final static String tagNodoDestino = "@TagNodoDestino";
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
	
		AccesoStpV.manySteps(dCtxSh, driver);

		Pais paisOriginal = dCtxSh.pais;
		IdiomaPais idiomaOriginal = dCtxSh.idioma;
		dCtxSh.pais = paisDestino;
		dCtxSh.idioma = idiomaDestino;
		(new SecFooterStpV(dCtxSh.channel, dCtxSh.appE, driver)).cambioPais(dCtxSh);
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
	final static String tagUrlAccesoPaisNoIp = "@TagUrlAccesoPaisNoIp";
	final static String tagLiteralIdiomaOrigen = "@TagLiteralIdiomaOrigen";
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
	private static ResultValWithPais validacAccesoSiApareceModal(String urlBaseTest, Pais paisAccesoNoIP, Pais paisAccesoPrevio, Pais paisConfirmado, 
																 List<Pais> listPaisAsocIP, WebDriver driver) throws Exception {
		ResultValWithPais validations = ResultValWithPais.getNew();
		validations.add(
			"Aparece un modal solicitando confirmación de país",
			ModalCambioPais.isVisibleModalUntil(driver, 0), State.Defect);
		
		if (paisAccesoPrevio==null) {
			validations.add(
				"En el modal <b>No</b> aparece un link con la opción de confirmar el país " + paisAccesoNoIP.getNombre_pais() + 
				" (" + paisAccesoNoIP.getCodigo_pais() + ")",
				!ModalCambioPais.isLinkToConfirmPais(driver, paisAccesoNoIP.getNombre_pais()), State.Defect);
		} else {
			if (paisConfirmado==null) {
				validations.add(
					"En el modal <b>Sí</b> aparece un link con la opción de confirmar el acceso al país por el que previsamente se ha accedido vía URL: " + 
					paisAccesoPrevio.getNombre_pais() + " (" + paisAccesoPrevio.getCodigo_pais() + ")",
					ModalCambioPais.isLinkToConfirmPais(driver, paisAccesoPrevio.getUrlPaisEstandar(urlBaseTest)), State.Defect);
			} else {
				validations.add(
					"En el modal <b>No</b> aparece un link con la opción de confirmar el acceso al país por el que previsamente se ha accedido vía URL: " + 
					paisAccesoPrevio.getNombre_pais() + " (" + paisAccesoPrevio.getCodigo_pais() + ")",
					!ModalCambioPais.isLinkToConfirmPais(driver, paisAccesoPrevio.getNombre_pais()), State.Defect);
			}
		}
		
		String paisesAsocIP = "";
		Iterator<Pais> it = listPaisAsocIP.iterator();
		while (it.hasNext()) {
			paisesAsocIP = paisesAsocIP + ", " + it.next().getNombre_pais();
		}
		Pais paisButtonAssociated = ModalCambioPais.getPaisOfButtonForChangePais(listPaisAsocIP, urlBaseTest, driver);
		validations.add(
			"En el modal aparece un botón con la opción de cambiar a uno de los posibles países asociados a la IP (" + paisesAsocIP + ")",
			paisButtonAssociated!=null, State.Defect);
		
		validations.setPais(paisButtonAssociated);
		return (validations);
	}
	
	/**
	 * Valicaciones correspondientes al caso en que después del acceso NO aparece el modal de confirmacióin del país
	 * @throws Exception
	 */
	@Validation
	private static ChecksTM validacAccesoNoApareceModal(String urlBaseTest, Pais paisPrevConf, WebDriver driver) 
	throws Exception {
		ResultValWithPais validations = ResultValWithPais.getNew();
		validations.add(
			"No aparece un modal solicitando confirmación de país",
			!ModalCambioPais.isVisibleModalUntil(driver, 0), State.Defect);
		
		String nombrePaisPrevConf = paisPrevConf.getNombre_pais();
		String hrefPaisPrevConf = paisPrevConf.getUrlPaisEstandar(urlBaseTest);
		validations.add(
			"Se ha redirigido a la URL del país confirmado previamente <b>" + nombrePaisPrevConf + "</b> (" + hrefPaisPrevConf + ")",
			(driver.getCurrentUrl().toLowerCase().contains(hrefPaisPrevConf.toLowerCase())), State.Defect);
		return validations;
	}
	
	final static String tagPaisBotonCambio = "@TagPaisBotonCambio";
	final static String tagHrefBotonCambio = "@TagHrefBotonCambio";
	@Step (
		description="Confirmamos la propuesta de país del modal <b>" + tagPaisBotonCambio + "</b>", 
		expected="Se redirige a la URL " + tagHrefBotonCambio)
	public static void selectConfirmPaisModal(WebDriver driver) {
		String paisBotonCambio = ModalCambioPais.getTextPaisButtonChagePais(driver);
		String hrefBotonCambioPais = ModalCambioPais.getHRefPaisButtonChagePais(driver);
		TestMaker.getCurrentStepInExecution().replaceInDescription(tagPaisBotonCambio, paisBotonCambio);
		TestMaker.getCurrentStepInExecution().replaceInExpected(tagHrefBotonCambio, hrefBotonCambioPais);
		
		ModalCambioPais.clickButtonChangePais(driver);
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
		InputParamsMango inputParamsSuite = (InputParamsMango)TestMaker.getTestCase().getInputParamsSuite();
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