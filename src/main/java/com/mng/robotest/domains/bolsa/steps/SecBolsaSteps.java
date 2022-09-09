package com.mng.robotest.domains.bolsa.steps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.mng.robotest.test.utils.UtilsTest;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.bolsa.pageobjects.SecBolsa;
import com.mng.robotest.domains.bolsa.pageobjects.ValidatorContentBolsa;
import com.mng.robotest.domains.bolsa.pageobjects.LineasArtBolsa.DataArtBolsa;
import com.mng.robotest.domains.bolsa.pageobjects.SecBolsa.StateBolsa;
import com.mng.robotest.domains.ficha.steps.PageFichaArtSteps;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.datastored.DataBag;
import com.mng.robotest.test.generic.UtilsMangoTest;
import com.mng.robotest.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test.getdata.products.GetterProducts;
import com.mng.robotest.test.getdata.products.ProductFilter.FilterType;
import com.mng.robotest.test.getdata.products.data.GarmentCatalog;
import com.mng.robotest.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test.steps.shop.checkout.Page1IdentCheckoutSteps;
import com.mng.robotest.test.steps.shop.checkout.PageCheckoutWrapperSteps;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks.GenericCheck;

public class SecBolsaSteps extends StepBase {

	private final SecBolsa secBolsa = SecBolsa.make(channel, app);
	
	@Step (
		description="Eliminamos los posibles artículos existentes en la Bolsa",
		expected="La bolsa queda vacía")
	public void clear() throws Exception {
		if (SecCabecera.getNew(channel, app).hayArticulosBolsa()) {
			secBolsa.clearArticulos();
		}
	}

	public void close() throws Exception {
		if (channel.isDevice()) {
			closeInMobil();
		} else {
			forceStateBolsaTo(StateBolsa.CLOSED);
		}
	}

	@Step (
		description="Cerrar la bolsa", 
		expected="Se cierra la bolsa")
	public void closeInMobil() throws Exception {
		secBolsa.closeInMobil();
		checkBolsaDisappears(3);
	}

	@Validation (
		description="Desaparece la bolsa (lo esperamos hasta #{maxSeconds} segundos)",
		level=State.Defect)
	private boolean checkBolsaDisappears(int maxSeconds) {
		return (secBolsa.isInStateUntil(StateBolsa.CLOSED, maxSeconds));
	}

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
		GetterProducts getterProducts = new GetterProducts.Builder(dataTest.pais.getCodigo_alf(), app, driver).build();
		List<GarmentCatalog> listParaAlta = getterProducts
				.getFiltered(FilterType.ManyColors)
				.subList(0, numArticulos);
		
		altaListaArticulosEnBolsa(listParaAlta, dataBag);
	}

	/**
	 * Define los pasos/validaciones para dar de alta una lista de artículos en la bolsa
	 * @param listParaAlta lista de artículos que hay que dar de alta
	 * @param listArtEnBolsa lista total de artículos que hay en la bolsa (y en la que se añadirán los nuevos)
	 */
	public void altaListaArticulosEnBolsa(List<GarmentCatalog> listArticlesForAdd, DataBag dataBag) throws Exception {
		if (listArticlesForAdd!=null && !listArticlesForAdd.isEmpty()) {
			altaBolsaArticulos(listArticlesForAdd, dataBag);
			validaAltaArtBolsa(dataBag);
		}

		dataBag.setImporteTotal(secBolsa.getPrecioSubTotal());
		dataBag.setImporteTransp(secBolsa.getPrecioTransporte());
	}

	static final String tagListaArt = "@TagListaArt";
	@Step (
		description="Utilizar el buscador para acceder a la ficha y dar de alta los siguientes productos en la bolsa:<br>" + tagListaArt, 
		expected="Los productos se dan de alta en la bolsa correctamente",
		saveNettraffic=SaveWhen.Always)
	public void altaBolsaArticulos(List<GarmentCatalog> listParaAlta, DataBag dataBag) throws Exception {
		includeListaArtInTestCaseDescription(listParaAlta);
		for (int i=0; i<listParaAlta.size(); i++) {
			GarmentCatalog artTmp = listParaAlta.get(i);
			ArticuloScreen articulo = new UtilsMangoTest().addArticuloBolsa(artTmp);
			if (artTmp.isVale()) {
				articulo.setVale(artTmp.getValePais());
			}

			dataBag.addArticulo(articulo);
		}

		if (channel==Channel.desktop) {
			int maxSecondsToWait = 10;
			secBolsa.isInStateUntil(StateBolsa.OPEN,maxSecondsToWait);
		}
	}

	private void includeListaArtInTestCaseDescription(List<GarmentCatalog> listParaAlta) {
		//Obtener el literal con la lista de artículos a dar de alta en la bolsa
		String listaArtStr = "";
		for (int i=0; i<listParaAlta.size(); i++) {
			GarmentCatalog artTmp = listParaAlta.get(i);
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
		
		GenericChecks.checkDefault();
		GenericChecks.from(Arrays.asList(GenericCheck.GoogleAnalytics)).checks();
	}

	@Validation
	private ChecksTM checkIsBolsaVisibleInDesktop() {
		ChecksTM checks = ChecksTM.getNew();
		int maxSeconds = 1;
	 	checks.add(
			"Es visible la capa/página correspondiente a la bolsa (la esperamos hasta " + maxSeconds + " segundos)",
			secBolsa.isInStateUntil(StateBolsa.OPEN, maxSeconds), State.Defect);
	 	
	 	checks.add(
			"Aparece el botón \"Comprar\" (lo esperamos hasta " + maxSeconds + " segundos)",
			secBolsa.isVisibleBotonComprarUntil(maxSeconds), State.Defect);
	 	
		return checks;
	}

	@Validation
	public ChecksTM validaNumArtEnBolsa(DataBag dataBag) throws Exception {
		ChecksTM checks = ChecksTM.getNew();
		int maxSeconds = 3;
		String itemsSaved = String.valueOf(dataBag.getListArticulos().size());
	 	checks.add(
			"Existen " + dataBag.getListArticulos().size() + " elementos dados de alta en la bolsa (los esperamos hasta " + maxSeconds + " segundos)",
			secBolsa.numberItemsIsUntil(itemsSaved, channel, app, maxSeconds), State.Warn);
	 	
	 	return checks;
	}

	@Validation
	public ChecksTM validaCuadranArticulosBolsa(DataBag dataBag) throws Exception {
		ChecksTM checks = ChecksTM.getNew();
		ValidatorContentBolsa validatorBolsa = new ValidatorContentBolsa(dataBag);
		checks.add(
			"Cuadra el númeo de artículos existentes en la bolsa",
			validatorBolsa.numArticlesIsCorrect(), State.Warn);
		
		ArrayList<DataArtBolsa> listDataToValidate = new ArrayList<>();
		listDataToValidate.add(DataArtBolsa.REFERENCIA);
		checks.add(
			"Cuadran las referencias de los artículos existentes en la bolsa",
			validatorBolsa.allArticlesExpectedDataAreInScreen(listDataToValidate), State.Warn);
		
		listDataToValidate.clear();
		listDataToValidate.add(DataArtBolsa.NOMBRE);
		checks.add(
			"Cuadran los nombres de los artículos existentes en la bolsa",
			validatorBolsa.allArticlesExpectedDataAreInScreen(listDataToValidate), State.Warn);
		
		listDataToValidate.clear();
		listDataToValidate.add(DataArtBolsa.COLOR);
		checks.add(
			"Cuadran los colores de los artículos existentes en la bolsa",
			validatorBolsa.allArticlesExpectedDataAreInScreen(listDataToValidate), State.Warn);
		
		listDataToValidate.clear();
		listDataToValidate.add(DataArtBolsa.TALLA);
		boolean tallaNumOk = validatorBolsa.allArticlesExpectedDataAreInScreen(listDataToValidate);
		listDataToValidate.clear();
		listDataToValidate.add(DataArtBolsa.TALLA);
		boolean tallaAlfOk = validatorBolsa.allArticlesExpectedDataAreInScreen(listDataToValidate);
		checks.add(
			"Cuadran las tallas de los artículos existentes en la bolsa",
			tallaNumOk || tallaAlfOk, State.Warn);
		
		listDataToValidate.clear();
		listDataToValidate.add(DataArtBolsa.PRECIO_TOTAL);
		checks.add(
			"Cuadran los precios de los artículos existentes en la bolsa",
			validatorBolsa.allArticlesExpectedDataAreInScreen(listDataToValidate), State.Warn);
		
		return checks;
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
	private boolean checkImporteIsModified(String importeTotalOrig, int maxSeconds) {
		return (secBolsa.isNotThisImporteTotalUntil(importeTotalOrig, maxSeconds));
	}

	@Step (
		description="Se selecciona el botón \"COMPRAR\" de la bolsa", 
		expected="Se muestra la página de identificación",
		saveNettraffic=SaveWhen.Always)
	public void selectButtonComprar(DataBag dataBag) throws Exception {
		secBolsa.clickBotonComprar(10);
		validaSelectButtonComprar(dataBag);
		if (!UtilsTest.dateBeforeToday("2022-08-01") &&
			!dataTest.userRegistered) {
			new Page1IdentCheckoutSteps().validaRGPDText();
		}
	}

	public void validaSelectButtonComprar(DataBag dataBag) throws Exception {
		if (dataTest.userRegistered) {
			new PageCheckoutWrapperSteps().validateIsFirstPage(dataTest.userRegistered, dataBag);
		} else {
			int maxSeconds = 5;
			new Page1IdentCheckoutSteps().validateIsPage(maxSeconds);
			GenericChecks.checkDefault();
			
		}
	}

	public void click1erArticuloBolsa(DataBag dataBag) throws Exception {
		ArticuloScreen articuloClickado = dataBag.getArticulo(0);
		clickArticuloBolsa(articuloClickado);
	}

	@Step (
		description="Lincar con el artículo existente en la bolsa" + " #{articuloClickado.getReferencia()})", 
		expected="El link al artículo es correcto")
	public void clickArticuloBolsa(ArticuloScreen articuloClickado) {
		secBolsa.setBolsaToStateIfNotYet(StateBolsa.OPEN);
		secBolsa.click1erArticuloBolsa();

		String refArticulo = articuloClickado.getReferencia();
		new PageFichaArtSteps().validateIsFichaArtDisponible(refArticulo, 3);
	}
}
