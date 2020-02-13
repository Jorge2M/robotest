package com.mng.robotest.test80.mango.test.stpv.shop;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.ChecksResult;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.service.TestMaker;
import com.mng.testmaker.boundary.aspects.step.SaveWhen;
import com.mng.robotest.test80.mango.test.data.Constantes;
import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.conf.State;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataBag;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.generic.PasosGenAnalitica;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test80.mango.test.getdata.products.GetterProducts;
import com.mng.robotest.test80.mango.test.getdata.products.data.Garment;
import com.mng.robotest.test80.mango.test.pageobject.shop.bolsa.SecBolsa;
import com.mng.robotest.test80.mango.test.pageobject.shop.bolsa.ValidatorContentBolsa;
import com.mng.robotest.test80.mango.test.pageobject.shop.bolsa.LineasArticuloBolsa.DataArtBolsa;
import com.mng.robotest.test80.mango.test.pageobject.shop.bolsa.SecBolsa.StateBolsa;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.Page1IdentCheckoutStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.ficha.PageFichaArtStpV;

public class SecBolsaStpV {

	@Step (
		description="Eliminamos los posibles artículos existentes en la Bolsa",
		expected="La bolsa queda vacía")
	public static void clear(DataCtxShop dCtxSh, WebDriver driver) throws Exception {
		if (SecCabecera.getNew(dCtxSh.channel, dCtxSh.appE, driver).hayArticulosBolsa()) {
			SecBolsa.clearArticulos(dCtxSh, driver);
		}
	}

	public static void close(Channel channel, AppEcom app, WebDriver driver) 
	throws Exception {
		if (channel==Channel.movil_web) {
			clickAspaForCloseMobil(driver);
		} else {
			forceStateBolsaTo(StateBolsa.Closed, app, channel, driver);
		}
	}

	@Step (
		description="Click en el aspa para cerrar la bolsa", 
		expected="Se cierra la bolsa")
	public static void clickAspaForCloseMobil(WebDriver driver) throws Exception {
		SecBolsa.clickAspaMobil(driver);
		checkBolsaDisappears(3, driver);
	}

	@Validation (
		description="Desaparece la bolsa (lo esperamos hasta #{maxSecondsWait} segundos)",
		level=State.Defect)
	private static boolean checkBolsaDisappears(int maxSecondsWait, WebDriver driver) {
		return (SecBolsa.isInStateUntil(StateBolsa.Closed, Channel.movil_web, maxSecondsWait, driver));
	}

	/**
	 * Seleccionamos el icono superior de la bolsa para abrirla/cerrar
	 * @param forOpen indica si lo clicamos para abrir (porque está cerrada) o para cerrar (porque está abierta)
	 */
	@Step (
		description="Mediante click o hover conseguir que la bolsa quede en estado #{stateBolsaExpected}", 
		expected="La bolsa queda en estado #{stateBolsaExpected}")
	public static void forceStateBolsaTo(StateBolsa stateBolsaExpected, AppEcom app, Channel channel, WebDriver driver) 
	throws Exception {
		SecBolsa.setBolsaToStateIfNotYet(stateBolsaExpected, channel, app, driver);
		validateBolsaInState(stateBolsaExpected, 1, channel, driver);
	}

	@Validation (
		description="La bolsa queda en estado #{stateBolsaExpected} (lo esperamos hasta #{maxSecondsToWait} segundos)",
		level=State.Defect)
	private static boolean validateBolsaInState(StateBolsa stateBolsaExpected, int maxSecondsWait, Channel channel, WebDriver driver) {
		return (SecBolsa.isInStateUntil(stateBolsaExpected, channel, maxSecondsWait, driver));
	}

	public static void altaArticlosConColores(int numArticulos, DataBag dataBag, DataCtxShop dCtxSh, WebDriver driver) 
	throws Exception {
		GetterProducts getterProducts = new GetterProducts.Builder(dCtxSh).build();
		List<Garment> listParaAlta = getterProducts.getWithManyColors().subList(0, numArticulos);
		altaListaArticulosEnBolsa(listParaAlta, dataBag, dCtxSh, driver);
	}

	/**
	 * Define los pasos/validaciones para dar de alta una lista de artículos en la bolsa
	 * @param listParaAlta lista de artículos que hay que dar de alta
	 * @param listArtEnBolsa lista total de artículos que hay en la bolsa (y en la que se añadirán los nuevos)
	 */
	public static void altaListaArticulosEnBolsa(List<Garment> listArticlesForAdd, DataBag dataBag, DataCtxShop dCtxSh, WebDriver driver) 
	throws Exception {
		if (listArticlesForAdd!=null && !listArticlesForAdd.isEmpty()) {
			altaBolsaArticulos(listArticlesForAdd, dataBag, dCtxSh, driver);
			validaAltaArtBolsa(dataBag, dCtxSh.channel, dCtxSh.appE, driver);
		}

		//Almacenamos el importe SubTotal y el de Transporte
		dataBag.setImporteTotal(SecBolsa.getPrecioSubTotal(dCtxSh.channel, driver));
		dataBag.setImporteTransp(SecBolsa.getPrecioTransporte(driver, dCtxSh.channel));
	}

	final static String tagListaArt = "@TagListaArt";
	@Step (
		description="Buscar y dar de alta los siguientes productos en la bolsa:<br>" + tagListaArt, 
		expected="Los productos se dan de alta en la bolsa correctamente",
		saveNettraffic=SaveWhen.Always)
	public static void altaBolsaArticulos(List<Garment> listParaAlta, DataBag dataBag, DataCtxShop dCtxSh, WebDriver driver) 
	throws Exception {
		includeListaArtInTestCaseDescription(listParaAlta);
		for (int i=0; i<listParaAlta.size(); i++) {
			Garment artTmp = listParaAlta.get(i);
			ArticuloScreen articulo = UtilsMangoTest.addArticuloBolsa(artTmp, dCtxSh.appE, dCtxSh.channel, driver);
			if (artTmp.isVale()) {
				articulo.setVale(artTmp.getValePais());
			}

			dataBag.addArticulo(articulo);
		}

		if (dCtxSh.channel==Channel.desktop) {
			int maxSecondsToWait = 10;
			SecBolsa.isInStateUntil(StateBolsa.Open, dCtxSh.channel, maxSecondsToWait, driver);
		}
	}

	private static void includeListaArtInTestCaseDescription(List<Garment> listParaAlta) {
		//Obtener el literal con la lista de artículos a dar de alta en la bolsa
		String listaArtStr = "";
		for (int i=0; i<listParaAlta.size(); i++) {
			Garment artTmp = listParaAlta.get(i);
			listaArtStr = listaArtStr + artTmp.getGarmentId();
			if (artTmp.isVale()) {
				listaArtStr = listaArtStr + " (le aplica el vale " + artTmp.getValePais().getCodigoVale() + ")";
			}

			//Si no es el último artículo le añadimos una coma
			if (i < (listParaAlta.size() - 1)) {
				listaArtStr = listaArtStr + "<br>";
			}
		}

		TestMaker.getCurrentStepInExecution().replaceInDescription(tagListaArt, listaArtStr);
	}

	/**
	 * Validaciones posteriores al alta de una lista de artículos en la bolsa
	 * @param listArtEnBolsa lista total de artículos dados de alta a la bolsa
	 */
	public static void validaAltaArtBolsa(DataBag dataBag, Channel channel, AppEcom app, WebDriver driver) 
	throws Exception {
		validaNumArtEnBolsa(dataBag, channel, app, driver);
		if (channel==Channel.desktop) {
			checkIsBolsaVisibleInDesktop(driver);
		}

		validaCuadranArticulosBolsa(dataBag, app, channel, driver);
		EnumSet<Constantes.AnalyticsVal> analyticSet = EnumSet.of(
				Constantes.AnalyticsVal.GoogleAnalytics,
				Constantes.AnalyticsVal.Criteo,
				Constantes.AnalyticsVal.NetTraffic, 
				Constantes.AnalyticsVal.DataLayer);
		PasosGenAnalitica.validaHTTPAnalytics(app, LineaType.she, analyticSet, driver);
	}

	@Validation
	private static ChecksResult checkIsBolsaVisibleInDesktop(WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
		int maxSecondsWait = 1;
	 	validations.add(
			"Es visible la capa/página correspondiente a la bolsa (la esperamos hasta " + maxSecondsWait + " segundos)",
			SecBolsa.isInStateUntil(StateBolsa.Open, Channel.desktop, maxSecondsWait, driver), State.Defect);
	 	validations.add(
			"Aparece el botón \"Comprar\" (lo esperamos hasta " + maxSecondsWait + " segundos)",
			SecBolsa.isVisibleBotonComprarUntil(driver, Channel.desktop, maxSecondsWait), State.Defect);
		return validations;
	}

	@Validation
	public static ChecksResult validaNumArtEnBolsa(DataBag dataBag, Channel channel, AppEcom app, WebDriver driver) 
	throws Exception {
		ChecksResult validations = ChecksResult.getNew();
		int maxSecondsWait = 2;
		String itemsSaved = String.valueOf(dataBag.getListArticulos().size());
	 	validations.add(
			"Existen " + dataBag.getListArticulos().size() + " elementos dados de alta en la bolsa (los esperamos hasta " + maxSecondsWait + " segundos)",
			SecBolsa.numberItemsIsUntil(itemsSaved, channel, app, maxSecondsWait, driver), State.Warn);
	 	return validations;
	}

	@Validation
	public static ChecksResult validaCuadranArticulosBolsa(DataBag dataBag, AppEcom app, Channel channel, WebDriver driver) 
	throws Exception {
		ChecksResult validations = ChecksResult.getNew();
		ValidatorContentBolsa validatorBolsa = new ValidatorContentBolsa(dataBag, app, channel, driver);
		validations.add(
			"Cuadra el número de artículos existentes en la bolsa",
			validatorBolsa.numArticlesIsCorrect(), State.Warn);
		
		ArrayList<DataArtBolsa> listDataToValidate = new ArrayList<>();
		listDataToValidate.add(DataArtBolsa.Referencia);
		validations.add(
			"Cuadran los nombres de los artículos existentes en la bolsa",
			validatorBolsa.allArticlesExpectedDataAreInScreen(listDataToValidate), State.Warn);
		
		listDataToValidate.clear();
		listDataToValidate.add(DataArtBolsa.Nombre);
		validations.add(
			"Cuadran los nombres de los artículos existentes en la bolsa",
			validatorBolsa.allArticlesExpectedDataAreInScreen(listDataToValidate), State.Warn);
		
		listDataToValidate.clear();
		listDataToValidate.add(DataArtBolsa.Color);
		validations.add(
			"Cuadran los colores de los artículos existentes en la bolsa",
			validatorBolsa.allArticlesExpectedDataAreInScreen(listDataToValidate), State.Warn);
		
		listDataToValidate.clear();
		listDataToValidate.add(DataArtBolsa.Talla);
		boolean tallaNumOk = validatorBolsa.allArticlesExpectedDataAreInScreen(listDataToValidate);
		listDataToValidate.clear();
		listDataToValidate.add(DataArtBolsa.Talla);
		boolean tallaAlfOk = validatorBolsa.allArticlesExpectedDataAreInScreen(listDataToValidate);
		validations.add(
			"Cuadran las tallas de los artículos existentes en la bolsa",
			tallaNumOk || tallaAlfOk, State.Warn);
		
		listDataToValidate.clear();
		listDataToValidate.add(DataArtBolsa.PrecioTotal);
		validations.add(
			"Cuadran los precios de los artículos existentes en la bolsa",
			validatorBolsa.allArticlesExpectedDataAreInScreen(listDataToValidate), State.Warn);
		
		return validations;
	}

	public static void clear1erArticuloBolsa(DataBag dataBag, AppEcom app, Channel channel, WebDriver driver) 
	throws Exception {
		ArticuloScreen artToClear = dataBag.getArticulo(0);
		clearArticuloBolsa(artToClear, dataBag, app, channel, driver);
	}

	@Step (
		description="Eliminar el artículo-1 #{artToClear.getReferencia()} de la bolsa", 
		expected="El artículo se elimina correctamente")
	private static void clearArticuloBolsa(ArticuloScreen artToClear, DataBag dataBag, AppEcom app, Channel channel, WebDriver driver) 
	throws Exception {
		String importeTotalOrig = SecBolsa.getPrecioSubtotalTextPant(channel, driver);
		SecBolsa.clearArticuloAndWait(channel, artToClear.getReferencia(), driver);
		dataBag.removeArticulo(0); 
		checkImporteIsModified(importeTotalOrig, 5, channel, driver);
		validaCuadranArticulosBolsa(dataBag, app, channel, driver);
	}

	@Validation (
		description="El importe total se acaba modificando (lo esperamos hasta #{maxSecondsWait} segundos)",
		level=State.Warn)
	private static boolean checkImporteIsModified(String importeTotalOrig, int maxSecondsWait, Channel channel, WebDriver driver) 
	throws Exception {
		return (SecBolsa.isNotThisImporteTotalUntil(importeTotalOrig, channel, maxSecondsWait, driver));
	}

	@SuppressWarnings("static-access")
	@Step (
		description="Se selecciona el botón \"COMPRAR\" de la bolsa", 
		expected="Se muestra la página de identificación",
		saveNettraffic=SaveWhen.Always)
	public static void selectButtonComprar(DataBag dataBag, DataCtxShop dCtxSh, WebDriver driver) throws Exception {
		SecBolsa.clickBotonComprar(driver, dCtxSh.channel, 10);
		validaSelectButtonComprar(dataBag, dCtxSh, driver);
		if(!dCtxSh.userRegistered) {
			Page1IdentCheckoutStpV.secSoyNuevo.validaRGPDText(dCtxSh, driver);
		}
	}

	/**
	 * Validaciones resultado de seleccionar el botón "Comprar" de la bolsa
	 * @param accUsrReg si la operación se está realizando con un usuario registrado
	 */
	public static void validaSelectButtonComprar(DataBag dataBag, DataCtxShop dCtxSh, WebDriver driver) 
	throws Exception {
		if (dCtxSh.userRegistered) {
			PageCheckoutWrapperStpV.validateIsFirstPage(dCtxSh.userRegistered, dataBag, dCtxSh.channel, driver);
		} else {
			int maxSecondsWait = 5;
			Page1IdentCheckoutStpV.validateIsPage(maxSecondsWait, driver);
		}
	}

	public static void click1erArticuloBolsa(DataBag dataBag, AppEcom app, Channel channel, WebDriver driver) 
	throws Exception {
		ArticuloScreen articuloClickado = dataBag.getArticulo(0);
		clickArticuloBolsa(articuloClickado, app, channel, driver);
	}

	@Step (
		description="Lincar con el artículo existente en la bolsa" + " #{articuloClickado.getReferencia()})", 
		expected="El link al artículo es correcto")
	public static void clickArticuloBolsa(ArticuloScreen articuloClickado, AppEcom app, Channel channel, WebDriver driver) 
	throws Exception {
		SecBolsa.setBolsaToStateIfNotYet(StateBolsa.Open, channel, app, driver);
		SecBolsa.click1erArticuloBolsa(driver);

		String refArticulo = articuloClickado.getReferencia();
		PageFichaArtStpV pageFichaStpv = new PageFichaArtStpV(app, channel);
		pageFichaStpv.validateIsFichaArtDisponible(refArticulo, 3);
	}
}
