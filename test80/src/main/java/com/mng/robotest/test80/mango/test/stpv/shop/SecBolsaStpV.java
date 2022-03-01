package com.mng.robotest.test80.mango.test.stpv.shop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.beans.Pais;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataBag;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test80.mango.test.getdata.products.GetterProducts;
import com.mng.robotest.test80.mango.test.getdata.products.ProductFilter.FilterType;
import com.mng.robotest.test80.mango.test.getdata.products.data.Garment;
import com.mng.robotest.test80.mango.test.pageobject.shop.bolsa.ValidatorContentBolsa;
import com.mng.robotest.test80.mango.test.pageobject.shop.bolsa.LineasArtBolsa.DataArtBolsa;
import com.mng.robotest.test80.mango.test.pageobject.shop.bolsa.SecBolsa;
import com.mng.robotest.test80.mango.test.pageobject.shop.bolsa.SecBolsa.StateBolsa;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.MenuUserItem.UserMenu;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.Page1IdentCheckoutStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.ficha.PageFichaArtStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.genericchecks.GenericChecks;
import com.mng.robotest.test80.mango.test.stpv.shop.genericchecks.GenericChecks.GenericCheck;
import com.mng.robotest.test80.mango.test.stpv.shop.menus.SecMenusWrapperStpV;

public class SecBolsaStpV {

	private final SecBolsa secBolsa;
	
	private final Channel channel;
	private final AppEcom app;
	private final Pais pais;
	private final WebDriver driver;
	
	public SecBolsaStpV(DataCtxShop dCtxSh, WebDriver driver) {
		this.secBolsa = SecBolsa.make(dCtxSh, driver);
		this.driver = driver;
		this.channel = dCtxSh.channel;
		this.app = dCtxSh.appE;
		this.pais = dCtxSh.pais;
	}
	
	public SecBolsaStpV(Channel channel, AppEcom app, Pais pais, WebDriver driver) {
		this.secBolsa = SecBolsa.make(channel, app, pais, driver);
		this.driver = driver;
		this.channel = channel;
		this.app = app;
		this.pais = pais;
	}
	
	@Step (
		description="Eliminamos los posibles artículos existentes en la Bolsa",
		expected="La bolsa queda vacía")
	public void clear() throws Exception {
		if (SecCabecera.getNew(channel, app, driver).hayArticulosBolsa()) {
			secBolsa.clearArticulos();
		}
	}

	public void close() throws Exception {
		if (channel.isDevice()) {
			clickAspaForCloseMobil();
		} else {
			forceStateBolsaTo(StateBolsa.Closed);
		}
	}

	@Step (
		description="Click en el aspa para cerrar la bolsa", 
		expected="Se cierra la bolsa")
	public void clickAspaForCloseMobil() throws Exception {
		secBolsa.clickAspaMobil();
		checkBolsaDisappears(3);
	}

	@Validation (
		description="Desaparece la bolsa (lo esperamos hasta #{maxSeconds} segundos)",
		level=State.Defect)
	private boolean checkBolsaDisappears(int maxSeconds) {
		return (secBolsa.isInStateUntil(StateBolsa.Closed, maxSeconds));
	}

	/**
	 * Seleccionamos el icono superior de la bolsa para abrirla/cerrar
	 * @param forOpen indica si lo clicamos para abrir (porque está cerrada) o para cerrar (porque está abierta)
	 */
	@Step (
		description="Mediante click o hover conseguir que la bolsa quede en estado #{stateBolsaExpected}", 
		expected="La bolsa queda en estado #{stateBolsaExpected}")
	public void forceStateBolsaTo(StateBolsa stateBolsaExpected) throws Exception {
		secBolsa.setBolsaToStateIfNotYet(stateBolsaExpected);
		validateBolsaInState(stateBolsaExpected, 1);
	}

	@Validation (
		description="La bolsa queda en estado #{stateBolsaExpected} (lo esperamos hasta #{maxSecondsToWait} segundos)",
		level=State.Defect)
	private boolean validateBolsaInState(StateBolsa stateBolsaExpected, int maxSeconds) {
		return (secBolsa.isInStateUntil(stateBolsaExpected, maxSeconds));
	}

	public void altaArticlosConColores(int numArticulos, DataBag dataBag) throws Exception {
		GetterProducts getterProducts = new GetterProducts.Builder(pais.getCodigo_alf(), app, driver).build();
		List<Garment> listParaAlta = getterProducts
				.getFiltered(FilterType.ManyColors)
				.subList(0, numArticulos);
		
		altaListaArticulosEnBolsa(listParaAlta, dataBag);
	}

	/**
	 * Define los pasos/validaciones para dar de alta una lista de artículos en la bolsa
	 * @param listParaAlta lista de artículos que hay que dar de alta
	 * @param listArtEnBolsa lista total de artículos que hay en la bolsa (y en la que se añadirán los nuevos)
	 */
	public void altaListaArticulosEnBolsa(List<Garment> listArticlesForAdd, DataBag dataBag) throws Exception {
		if (listArticlesForAdd!=null && !listArticlesForAdd.isEmpty()) {
			altaBolsaArticulos(listArticlesForAdd, dataBag);
			validaAltaArtBolsa(dataBag);
		}

		dataBag.setImporteTotal(secBolsa.getPrecioSubTotal());
		dataBag.setImporteTransp(secBolsa.getPrecioTransporte());
	}

	final static String tagListaArt = "@TagListaArt";
	@Step (
		description="Utilizar el buscador para acceder a la ficha y dar de alta los siguientes productos en la bolsa:<br>" + tagListaArt, 
		expected="Los productos se dan de alta en la bolsa correctamente",
		saveNettraffic=SaveWhen.Always)
	public void altaBolsaArticulos(List<Garment> listParaAlta, DataBag dataBag) throws Exception {
		includeListaArtInTestCaseDescription(listParaAlta);
		for (int i=0; i<listParaAlta.size(); i++) {
			Garment artTmp = listParaAlta.get(i);
			ArticuloScreen articulo = UtilsMangoTest.addArticuloBolsa(artTmp, app, channel, driver);
			if (artTmp.isVale()) {
				articulo.setVale(artTmp.getValePais());
			}

			dataBag.addArticulo(articulo);
		}

		if (channel==Channel.desktop) {
			int maxSecondsToWait = 10;
			secBolsa.isInStateUntil(StateBolsa.Open,maxSecondsToWait);
		}
	}

	private void includeListaArtInTestCaseDescription(List<Garment> listParaAlta) {
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
	public void validaAltaArtBolsa(DataBag dataBag) throws Exception {
		validaNumArtEnBolsa(dataBag);
		if (channel==Channel.desktop) {
			checkIsBolsaVisibleInDesktop();
		}

		validaCuadranArticulosBolsa(dataBag);
		
		GenericChecks.from(Arrays.asList(
				GenericCheck.GoogleAnalytics,
				GenericCheck.Analitica,
				GenericCheck.TextsTraduced,
				GenericCheck.NetTraffic)).checks(driver);
	}

	@Validation
	private ChecksTM checkIsBolsaVisibleInDesktop() {
		ChecksTM validations = ChecksTM.getNew();
		int maxSeconds = 1;
	 	validations.add(
			"Es visible la capa/página correspondiente a la bolsa (la esperamos hasta " + maxSeconds + " segundos)",
			secBolsa.isInStateUntil(StateBolsa.Open, maxSeconds), State.Defect);
	 	validations.add(
			"Aparece el botón \"Comprar\" (lo esperamos hasta " + maxSeconds + " segundos)",
			secBolsa.isVisibleBotonComprarUntil(maxSeconds), State.Defect);
		return validations;
	}

	@Validation
	public ChecksTM validaNumArtEnBolsa(DataBag dataBag) throws Exception {
		ChecksTM validations = ChecksTM.getNew();
		int maxSeconds = 3;
		String itemsSaved = String.valueOf(dataBag.getListArticulos().size());
	 	validations.add(
			"Existen " + dataBag.getListArticulos().size() + " elementos dados de alta en la bolsa (los esperamos hasta " + maxSeconds + " segundos)",
			secBolsa.numberItemsIsUntil(itemsSaved, channel, app, maxSeconds), State.Warn);
	 	return validations;
	}

	@Validation
	public ChecksTM validaCuadranArticulosBolsa(DataBag dataBag) throws Exception {
		ChecksTM validations = ChecksTM.getNew();
		ValidatorContentBolsa validatorBolsa = new ValidatorContentBolsa(dataBag, app, channel, pais, driver);
		validations.add(
			"Cuadra el número de artículos existentes en la bolsa",
			validatorBolsa.numArticlesIsCorrect(), State.Warn);
		
		ArrayList<DataArtBolsa> listDataToValidate = new ArrayList<>();
		listDataToValidate.add(DataArtBolsa.Referencia);
		validations.add(
			"Cuadran las referencias de los artículos existentes en la bolsa",
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

	public void clear1erArticuloBolsa(DataBag dataBag) throws Exception {
		ArticuloScreen artToClear = dataBag.getArticulo(0);
		clearArticuloBolsa(artToClear, dataBag);
	}

	@Step (
		description="Eliminar el artículo-1 #{artToClear.getReferencia()} de la bolsa", 
		expected="El artículo se elimina correctamente")
	private void clearArticuloBolsa(ArticuloScreen artToClear, DataBag dataBag) throws Exception {
		String importeTotalOrig = secBolsa.getPrecioSubtotalTextPant();
		secBolsa.getLineasArtBolsa().clearArticuloAndWait(artToClear.getReferencia());
		dataBag.removeArticulo(0); 
		checkImporteIsModified(importeTotalOrig, 5);
		validaCuadranArticulosBolsa(dataBag);
	}

	@Validation (
		description="El importe total se acaba modificando (lo esperamos hasta #{maxSeconds} segundos)",
		level=State.Warn)
	private boolean checkImporteIsModified(String importeTotalOrig, int maxSeconds) 	throws Exception {
		return (secBolsa.isNotThisImporteTotalUntil(importeTotalOrig, maxSeconds));
	}

	@SuppressWarnings("static-access")
	@Step (
		description="Se selecciona el botón \"COMPRAR\" de la bolsa", 
		expected="Se muestra la página de identificación",
		saveNettraffic=SaveWhen.Always)
	public void selectButtonComprar(DataBag dataBag, DataCtxShop dCtxSh) throws Exception {
		secBolsa.clickBotonComprar(10);
		validaSelectButtonComprar(dataBag, dCtxSh);
		if(!dCtxSh.userRegistered) {
			Page1IdentCheckoutStpV.secSoyNuevo.validaRGPDText(dCtxSh, driver);
		}
	}

	/**
	 * Validaciones resultado de seleccionar el botón "Comprar" de la bolsa
	 * @param accUsrReg si la operación se está realizando con un usuario registrado
	 */
	public void validaSelectButtonComprar(DataBag dataBag, DataCtxShop dCtxSh) throws Exception {
		if (dCtxSh.userRegistered) {
			new PageCheckoutWrapperStpV(dCtxSh.channel, dCtxSh.appE, driver).validateIsFirstPage(dCtxSh.userRegistered, dataBag);
		} else {
			int maxSeconds = 5;
			Page1IdentCheckoutStpV.validateIsPage(maxSeconds, driver);
			GenericChecks.from(Arrays.asList(
					GenericCheck.Analitica,
					GenericCheck.TextsTraduced)).checks(driver);
			
		}
	}

	public void click1erArticuloBolsa(DataBag dataBag) throws Exception {
		ArticuloScreen articuloClickado = dataBag.getArticulo(0);
		clickArticuloBolsa(articuloClickado);
	}

	@Step (
		description="Lincar con el artículo existente en la bolsa" + " #{articuloClickado.getReferencia()})", 
		expected="El link al artículo es correcto")
	public void clickArticuloBolsa(ArticuloScreen articuloClickado) throws Exception {
		secBolsa.setBolsaToStateIfNotYet(StateBolsa.Open);
		secBolsa.click1erArticuloBolsa();

		String refArticulo = articuloClickado.getReferencia();
		PageFichaArtStpV pageFichaStpv = new PageFichaArtStpV(app, channel, pais);
		pageFichaStpv.validateIsFichaArtDisponible(refArticulo, 3);
	}
}
