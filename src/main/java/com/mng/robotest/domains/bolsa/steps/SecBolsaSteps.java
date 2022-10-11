package com.mng.robotest.domains.bolsa.steps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.bolsa.pageobjects.SecBolsa;
import com.mng.robotest.domains.bolsa.pageobjects.ValidatorContentBolsa;
import com.mng.robotest.domains.bolsa.pageobjects.LineasArticuloBolsaCommon.DataArtBolsa;
import com.mng.robotest.domains.bolsa.pageobjects.SecBolsaCommon.StateBolsa;
import com.mng.robotest.domains.compra.steps.CheckoutSteps;
import com.mng.robotest.domains.compra.steps.Page1IdentCheckoutSteps;
import com.mng.robotest.domains.ficha.steps.PageFichaSteps;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.generic.UtilsMangoTest;
import com.mng.robotest.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test.getdata.products.GetterProducts;
import com.mng.robotest.test.getdata.products.ProductFilter.FilterType;
import com.mng.robotest.test.getdata.products.data.GarmentCatalog;
import com.mng.robotest.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks.GenericCheck;

public class SecBolsaSteps extends StepBase {

	private final SecBolsa secBolsa = new SecBolsa();
	
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
		description="Desaparece la bolsa (lo esperamos hasta #{seconds} segundos)",
		level=State.Defect)
	private boolean checkBolsaDisappears(int seconds) {
		return (secBolsa.isInStateUntil(StateBolsa.CLOSED, seconds));
	}

	@Step (
		description="Mediante click o hover conseguir que la bolsa quede en estado #{stateBolsaExpected}", 
		expected="La bolsa queda en estado #{stateBolsaExpected}")
	public void forceStateBolsaTo(StateBolsa stateBolsaExpected) throws Exception {
		secBolsa.setBolsaToStateIfNotYet(stateBolsaExpected);
		validateBolsaInState(stateBolsaExpected, 1);
	}

	@Validation (
		description="La bolsa queda en estado #{stateBolsaExpected} (lo esperamos hasta #{seconds} segundos)",
		level=State.Defect)
	private boolean validateBolsaInState(StateBolsa stateBolsaExpected, int seconds) {
		return (secBolsa.isInStateUntil(stateBolsaExpected, seconds));
	}

	public void altaArticlosConColores(int numArticulos) throws Exception {
		GetterProducts getterProducts = new GetterProducts.Builder(dataTest.getPais()
				.getCodigo_alf(), app, driver)
				.filter(FilterType.MANY_COLORS)
				.build();
		
		List<GarmentCatalog> listParaAlta = getterProducts.getAll().subList(0, numArticulos);
		altaListaArticulosEnBolsa(listParaAlta);
	}

	public void altaListaArticulosEnBolsa(List<GarmentCatalog> listArticlesForAdd) 
			throws Exception {
		if (listArticlesForAdd!=null && !listArticlesForAdd.isEmpty()) {
			altaBolsaArticulos(listArticlesForAdd);
			validaAltaArtBolsa();
		}
		dataTest.getDataBag().setImporteTotal(secBolsa.getPrecioSubTotal());
		dataTest.getDataBag().setImporteTransp(secBolsa.getPrecioTransporte());
	}

	private static final String TAG_LISTA_ART = "@TagListaArt";
	@Step (
		description="Utilizar el buscador para acceder a la ficha y dar de alta los siguientes productos en la bolsa:<br>" + TAG_LISTA_ART,
		expected="Los productos se dan de alta en la bolsa correctamente",
		saveNettraffic=SaveWhen.Always)
	public void altaBolsaArticulos(List<GarmentCatalog> listParaAlta) throws Exception {
		includeListaArtInTestCaseDescription(listParaAlta);
		for (int i=0; i<listParaAlta.size(); i++) {
			GarmentCatalog artTmp = listParaAlta.get(i);
			ArticuloScreen articulo = new UtilsMangoTest().addArticuloBolsa(artTmp);
			if (artTmp.isVale()) {
				articulo.setVale(artTmp.getValePais());
			}
			dataTest.getDataBag().addArticulo(articulo);
		}

		if (channel==Channel.desktop) {
			secBolsa.isInStateUntil(StateBolsa.OPEN, 10);
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

		TestMaker.getCurrentStepInExecution().replaceInDescription(TAG_LISTA_ART, listaArtStr);
	}

	public void validaAltaArtBolsa() throws Exception {
		validaNumArtEnBolsa();
		if (channel==Channel.desktop) {
			checkIsBolsaVisibleInDesktop();
		}
		validaCuadranArticulosBolsa();
		GenericChecks.checkDefault();
		GenericChecks.from(Arrays.asList(GenericCheck.GoogleAnalytics)).checks();
	}

	@Validation
	private ChecksTM checkIsBolsaVisibleInDesktop() {
		ChecksTM checks = ChecksTM.getNew();
		int seconds = 1;
	 	checks.add(
			"Es visible la capa/página correspondiente a la bolsa (la esperamos hasta " + seconds + " segundos)",
			secBolsa.isInStateUntil(StateBolsa.OPEN, seconds), State.Defect);
	 	
	 	checks.add(
			"Aparece el botón \"Comprar\" (lo esperamos hasta " + seconds + " segundos)",
			secBolsa.isVisibleBotonComprarUntil(seconds), State.Defect);
	 	
		return checks;
	}

	@Validation
	public ChecksTM validaNumArtEnBolsa() throws Exception {
		ChecksTM checks = ChecksTM.getNew();
		int seconds = 3;
		String itemsSaved = String.valueOf(dataTest.getDataBag().getListArticulos().size());
	 	checks.add(
			"Existen " + dataTest.getDataBag().getListArticulos().size() + " elementos dados de alta en la bolsa (los esperamos hasta " + seconds + " segundos)",
			secBolsa.numberItemsIsUntil(itemsSaved, channel, app, seconds), State.Warn);
	 	
	 	return checks;
	}

	@Validation
	public ChecksTM validaCuadranArticulosBolsa() throws Exception {
		ChecksTM checks = ChecksTM.getNew();
		ValidatorContentBolsa validatorBolsa = new ValidatorContentBolsa();
		checks.add(
			"Cuadra el númeo de artículos existentes en la bolsa",
			validatorBolsa.numArticlesIsCorrect(), State.Warn);
		
		List<DataArtBolsa> listDataToValidate = new ArrayList<>();
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

	public void clear1erArticuloBolsa() throws Exception {
		ArticuloScreen artToClear = dataTest.getDataBag().getArticulo(0);
		clearArticuloBolsa(artToClear);
	}

	@Step (
		description="Eliminar el artículo-1 #{artToClear.getReferencia()} de la bolsa", 
		expected="El artículo se elimina correctamente")
	private void clearArticuloBolsa(ArticuloScreen artToClear) throws Exception {
		String importeTotalOrig = secBolsa.getPrecioSubtotalTextPant();
		secBolsa.getLineasArtBolsa().clearArticuloAndWait(artToClear.getReferencia());
		dataTest.getDataBag().removeArticulo(0); 
		checkImporteIsModified(importeTotalOrig, 5);
		validaCuadranArticulosBolsa();
	}

	@Validation (
		description="El importe total se acaba modificando (lo esperamos hasta #{seconds} segundos)",
		level=State.Warn)
	private boolean checkImporteIsModified(String importeTotalOrig, int seconds) {
		return (secBolsa.isNotThisImporteTotalUntil(importeTotalOrig, seconds));
	}

	@Step (
		description="Se selecciona el botón \"COMPRAR\" de la bolsa", 
		expected="Se muestra la página de identificación",
		saveNettraffic=SaveWhen.Always)
	public void selectButtonComprar() throws Exception {
		secBolsa.clickBotonComprar(10);
		validaSelectButtonComprar();
		GenericChecks.checkDefault();
		if (!dataTest.isUserRegistered()) {
			new Page1IdentCheckoutSteps().validaRGPDText();
		}
	}

	public void validaSelectButtonComprar() throws Exception {
		if (dataTest.isUserRegistered()) {
			new CheckoutSteps().validateIsFirstPage(dataTest.isUserRegistered());
		} else {
			new Page1IdentCheckoutSteps().validateIsPage(5);
		}
	}

	public void click1erArticuloBolsa() throws Exception {
		ArticuloScreen articuloClickado = dataTest.getDataBag().getArticulo(0);
		clickArticuloBolsa(articuloClickado);
	}

	@Step (
		description="Lincar con el artículo existente en la bolsa" + " #{articuloClickado.getReferencia()})", 
		expected="El link al artículo es correcto")
	public void clickArticuloBolsa(ArticuloScreen articuloClickado) {
		secBolsa.setBolsaToStateIfNotYet(StateBolsa.OPEN);
		secBolsa.click1erArticuloBolsa();

		String refArticulo = articuloClickado.getReferencia();
		new PageFichaSteps().checkIsFichaArtDisponible(refArticulo, 3);
		GenericChecks.checkDefault();
	}
}
