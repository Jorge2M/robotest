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
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.bolsa.pageobjects.SecBolsa;
import com.mng.robotest.domains.bolsa.pageobjects.ValidatorContentBolsa;
import com.mng.robotest.domains.bolsa.pageobjects.SecBolsaCommon.StateBolsa;
import com.mng.robotest.domains.compra.steps.CheckoutSteps;
import com.mng.robotest.domains.compra.steps.Page1IdentCheckoutSteps;
import com.mng.robotest.domains.bolsa.pageobjects.LineasArticuloBolsa.DataArtBolsa;
import com.mng.robotest.domains.ficha.steps.PageFichaSteps;
import com.mng.robotest.getdata.productlist.GetterProducts;
import com.mng.robotest.getdata.productlist.ProductFilter.FilterType;
import com.mng.robotest.getdata.productlist.entity.GarmentCatalog;
import com.mng.robotest.getdata.productlist.entity.GarmentCatalog.Article;
import com.mng.robotest.getdata.productlist.sort.SortFactory.SortBy;
import com.mng.robotest.test.generic.UtilsMangoTest;
import com.mng.robotest.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test.pageobject.shop.cabecera.SecCabeceraMostFrequent;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks.GenericCheck;

import static com.github.jorge2m.testmaker.conf.State.*;
import static com.mng.robotest.domains.bolsa.pageobjects.LineasArticuloBolsa.DataArtBolsa.*;

public class SecBolsaSteps extends StepBase {

	private final SecBolsa secBolsa = new SecBolsa();
	
	@Step (
		description="Eliminamos los posibles artículos existentes en la Bolsa",
		expected="La bolsa queda vacía")
	public void clear() throws Exception {
		if (new SecCabeceraMostFrequent().hayArticulosBolsa()) {
			secBolsa.clearArticulos();
		}
	}

	public void close() {
		if (channel.isDevice()) {
			closeInMobil();
		} else {
			forceStateBolsaTo(StateBolsa.CLOSED);
		}
	}

	@Step (
		description="Cerrar la bolsa", 
		expected="Se cierra la bolsa")
	public void closeInMobil() {
		secBolsa.closeInMobil();
		checkBolsaDisappears(3);
	}

	@Validation (
		description="Desaparece la bolsa (lo esperamos hasta #{seconds} segundos)")
	private boolean checkBolsaDisappears(int seconds) {
		return (secBolsa.isInStateUntil(StateBolsa.CLOSED, seconds));
	}

	@Step (
		description="Mediante click o hover conseguir que la bolsa quede en estado #{stateBolsaExpected}", 
		expected="La bolsa queda en estado #{stateBolsaExpected}")
	public void forceStateBolsaTo(StateBolsa stateBolsaExpected) {
		secBolsa.setBolsaToStateIfNotYet(stateBolsaExpected);
		validateBolsaInState(stateBolsaExpected, 1);
	}

	@Validation (
		description="La bolsa queda en estado #{stateBolsaExpected} (lo esperamos hasta #{seconds} segundos)")
	private boolean validateBolsaInState(StateBolsa stateBolsaExpected, int seconds) {
		return (secBolsa.isInStateUntil(stateBolsaExpected, seconds));
	}

	public void altaArticlosConColores(int numArticulos) throws Exception {
		var getterProducts = new GetterProducts.Builder(dataTest.getPais()
				.getCodigo_alf(), app, driver)
				.filter(FilterType.MANY_COLORS)
				.sortBy(SortBy.STOCK_DESCENDENT)
				.build();
		
		List<GarmentCatalog> garments = getterProducts.getAll().subList(0, numArticulos);
		altaListaArticulosEnBolsa(Article.getArticlesCandidateForTest(garments));
	}

	public void altaListaArticulosEnBolsa(List<Article> listArticlesForAdd) 
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
	public void altaBolsaArticulos(List<Article> listParaAlta) {
		insertArticlesInStepDescription(listParaAlta);
		for (int i=0; i<listParaAlta.size(); i++) {
			var artTmp = listParaAlta.get(i);
			var articulo = new UtilsMangoTest().addArticuloBolsa(artTmp);
			dataTest.getDataBag().addArticulo(articulo);
		}

		if (channel==Channel.desktop) {
			secBolsa.isInStateUntil(StateBolsa.OPEN, 10);
		}
	}

	private void insertArticlesInStepDescription(List<Article> listParaAlta) {
		var str = new StringBuilder();
		str.append("<ul style=\"font-weight: bold;\">");
		for (int i=0; i<listParaAlta.size(); i++) {
			Article artTmp = listParaAlta.get(i);
			str.append("<li>" +
					artTmp.getGarmentId() + " - " + 
					artTmp.getColorLabel() + " - " + 
					artTmp.getSize().getLabel());
			
			if (artTmp.getSizeCanonical()!=null && artTmp.getWareHouse()!=null) { 
				str.append(" (store: " + artTmp.getWareHouse() + ")");
			}
			str.append("</li>");
		}
		str.append("</ul>");
		TestMaker.getCurrentStepInExecution().replaceInDescription(TAG_LISTA_ART, str.toString());
	}

	public void validaAltaArtBolsa() throws Exception {
		validaNumArtEnBolsa();
		if (channel==Channel.desktop) {
			checkIsBolsaVisibleInDesktop();
		}
		validaCuadranArticulosBolsa();
		GenericChecks.checkDefault();
		GenericChecks.from(Arrays.asList(GenericCheck.GOOGLE_ANALYTICS)).checks();
	}

	@Validation
	private ChecksTM checkIsBolsaVisibleInDesktop() {
		var checks = ChecksTM.getNew();
		int seconds = 1;
	 	checks.add(
			"Es visible la capa/página correspondiente a la bolsa (la esperamos hasta " + seconds + " segundos)",
			secBolsa.isInStateUntil(StateBolsa.OPEN, seconds));
	 	
	 	checks.add(
			"Aparece el botón \"Comprar\" (lo esperamos hasta " + seconds + " segundos)",
			secBolsa.isVisibleBotonComprarUntil(seconds));
	 	
		return checks;
	}

	@Validation
	public ChecksTM validaNumArtEnBolsa() {
		var checks = ChecksTM.getNew();
		int seconds = 3;
		String itemsSaved = String.valueOf(dataTest.getDataBag().getListArticulos().size());
	 	checks.add(
			"Existen " + dataTest.getDataBag().getListArticulos().size() + " elementos dados de alta en la bolsa (los esperamos hasta " + seconds + " segundos)",
			secBolsa.numberItemsIsUntil(itemsSaved, seconds), Warn);
	 	
	 	return checks;
	}

	@Validation
	public ChecksTM validaCuadranArticulosBolsa() throws Exception {
		var checks = ChecksTM.getNew();
		var validatorBolsa = new ValidatorContentBolsa();
		checks.add(
			"Cuadra el númeo de artículos existentes en la bolsa",
			validatorBolsa.numArticlesIsCorrect(), Warn);
		
		var listDataToValidate = new ArrayList<DataArtBolsa>();
		listDataToValidate.add(REFERENCIA);
		checks.add(
			"Cuadran las referencias de los artículos existentes en la bolsa",
			validatorBolsa.allArticlesExpectedDataAreInScreen(listDataToValidate), Warn);
		
		listDataToValidate.clear();
		listDataToValidate.add(NOMBRE);
		checks.add(
			"Cuadran los nombres de los artículos existentes en la bolsa",
			validatorBolsa.allArticlesExpectedDataAreInScreen(listDataToValidate), Warn);
		
		listDataToValidate.clear();
		listDataToValidate.add(COLOR);
		checks.add(
			"Cuadran los colores de los artículos existentes en la bolsa",
			validatorBolsa.allArticlesExpectedDataAreInScreen(listDataToValidate), Warn);
		
		listDataToValidate.clear();
		listDataToValidate.add(TALLA);
		boolean tallaNumOk = validatorBolsa.allArticlesExpectedDataAreInScreen(listDataToValidate);
		listDataToValidate.clear();
		listDataToValidate.add(TALLA);
		boolean tallaAlfOk = validatorBolsa.allArticlesExpectedDataAreInScreen(listDataToValidate);
		checks.add(
			"Cuadran las tallas de los artículos existentes en la bolsa",
			tallaNumOk || tallaAlfOk, Warn);
		
		listDataToValidate.clear();
		listDataToValidate.add(PRECIO_TOTAL);
		checks.add(
			"Cuadran los precios de los artículos existentes en la bolsa",
			validatorBolsa.allArticlesExpectedDataAreInScreen(listDataToValidate), Warn);
		
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
		level=Warn)
	private boolean checkImporteIsModified(String importeTotalOrig, int seconds) {
		return (secBolsa.isNotThisImporteTotalUntil(importeTotalOrig, seconds));
	}

	//TODO ajustar cuando suba a producción el nuevo flujo de login-checkout (11-abril-2023)
	public void selectButtonComprar() {
		selectButtonComprarBasic();
		if (!dataTest.isUserRegistered()) {
			if (channel!=Channel.desktop && 
			checkVisibleContinuarSinCuentaButtonDevice(1)) {
				clickIniciarSesionDevice();
			}
			new Page1IdentCheckoutSteps().checkIsPage(5);
			new Page1IdentCheckoutSteps().checkRGPDText();
		} else {
			new CheckoutSteps().validateIsFirstPage(dataTest.isUserRegistered());
		}
		
		GenericChecks.checkDefault();
	}
	
	@Step (
		description="Se selecciona el botón \"COMPRAR\" de la bolsa", 
		expected="Se muestra la página de identificación",
		saveNettraffic=SaveWhen.Always)
	public void selectButtonComprarBasic() {
		secBolsa.clickBotonComprar(10);
	}
	
	@Step (
		description="Se selecciona el botón <b>Continuar sin cuenta</b>",
		expected="Se muestra la página de continuar como invitado/a")
	public void clickIniciarSesionDevice() {
		secBolsa.clickIniciarSesionDevice();
	}

	//TODO poner en defect cuando suba a producción el nuevo flujo de login-checkout (11-abril-2023)
	@Validation (
		description="Es visible el botón \"Continuar sin cuenta\" (lo esperamos ahsta #{seconds} segundos)",
		level=State.Info)
	private boolean checkVisibleContinuarSinCuentaButtonDevice(int seconds) {
		return secBolsa.isVisibleContinuarSinCuentaButtonDevice(seconds);
	}

	public void click1erArticuloBolsa() {
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
