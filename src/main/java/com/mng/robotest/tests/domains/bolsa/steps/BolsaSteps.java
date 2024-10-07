package com.mng.robotest.tests.domains.bolsa.steps;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.bolsa.pageobjects.SecBolsa;
import com.mng.robotest.tests.domains.bolsa.pageobjects.CheckerContentBolsa;
import com.mng.robotest.tests.domains.bolsa.pageobjects.LineasArticuloBolsa.DataArtBolsa;
import com.mng.robotest.tests.domains.compra.steps.CheckoutSteps;
import com.mng.robotest.tests.domains.compra.steps.Page1DktopCheckoutSteps;
import com.mng.robotest.tests.domains.compra.steps.Page1EnvioCheckoutMobilSteps;
import com.mng.robotest.tests.domains.compra.steps.Page1IdentCheckoutSteps;
import com.mng.robotest.tests.domains.compranew.steps.CheckoutNewSteps;
import com.mng.robotest.tests.domains.ficha.steps.FichaSteps;
import com.mng.robotest.tests.domains.registro.steps.PageRegistroInitialShopSteps;
import com.mng.robotest.tests.domains.transversal.cabecera.pageobjects.SecCabecera;
import com.mng.robotest.tests.repository.productlist.GetterProducts;
import com.mng.robotest.tests.repository.productlist.ProductFilter.FilterType;
import com.mng.robotest.tests.repository.productlist.entity.GarmentCatalog.Article;
import com.mng.robotest.tests.repository.productlist.sort.SortFactory.SortBy;
import com.mng.robotest.testslegacy.generic.UtilsMangoTest;
import com.mng.robotest.testslegacy.generic.beans.ArticuloScreen;
import com.mng.robotest.tests.domains.bolsa.pageobjects.SecBolsaBase.StateBolsa;

import static com.github.jorge2m.testmaker.conf.State.*;
import static com.mng.robotest.tests.domains.bolsa.pageobjects.LineasArticuloBolsa.DataArtBolsa.*;
import static com.mng.robotest.tests.domains.bolsa.steps.BolsaSteps.FluxBolsaCheckout.*;
import static com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen.*;
import static com.mng.robotest.tests.domains.bolsa.pageobjects.SecBolsaBase.StateBolsa.*;

public class BolsaSteps extends StepBase {

	private static final String TAG_LISTA_ART = "@TagListaArt";
	
	public enum FluxBolsaCheckout {
		INICIAR_SESION,
		CONTINUAR_SIN_CUENTA,
		REGISTRO
	}
	
	private final SecBolsa secBolsa = new SecBolsa();
	
	@Step (
		description="Eliminamos los posibles artículos existentes en la Bolsa",
		expected="La bolsa queda vacía")
	public void clear() throws Exception {
		if (SecCabecera.make().hayArticulosBolsa()) {
			secBolsa.clearArticulos();
		}
	}

	public void close() {
		if (channel.isDevice()) {
			closeInMobil();
		} else {
			forceStateBolsaTo(CLOSED);
		}
	}

	@Step (
		description="Cerrar la bolsa", 
		expected="Se cierra la bolsa")
	public void closeInMobil() {
		secBolsa.closeInMobil();
		checkBolsaDisappears(3);
	}

	@Validation (description="Desaparece la bolsa " + SECONDS_WAIT)
	private boolean checkBolsaDisappears(int seconds) {
		return (secBolsa.isInStateUntil(CLOSED, seconds));
	}

	@Step (
		description="Mediante click o hover conseguir que la bolsa quede en estado #{stateBolsaExpected}", 
		expected="La bolsa queda en estado #{stateBolsaExpected}")
	public void forceStateBolsaTo(StateBolsa stateBolsaExpected) {
		secBolsa.setBolsaToStateIfNotYet(stateBolsaExpected);
		checkBagInState(stateBolsaExpected, 1);
	}

	@Validation(description="La bolsa queda en estado #{stateBolsaExpected} " + SECONDS_WAIT)
	private boolean checkBagInState(StateBolsa stateBolsaExpected, int seconds) {
		return secBolsa.isInStateUntil(stateBolsaExpected, seconds);
	}

	public void addArticlesWithColors(int numArticulos) throws Exception {
		var getterProducts = new GetterProducts.Builder(dataTest.getPais()
				.getCodigoAlf(), app, driver)
				.filter(FilterType.MANY_COLORS)
				.sortBy(SortBy.STOCK_DESCENDENT)
				.build();
		
		var garments = getterProducts.getAll().subList(0, numArticulos);
		altaListaArticulosEnBolsa(Article.getArticlesForTest(garments));
	}

	public void altaListaArticulosEnBolsa(List<Article> listArticlesForAdd) 
			throws Exception {
		if (listArticlesForAdd!=null && !listArticlesForAdd.isEmpty()) {
			addArticlesBag(listArticlesForAdd);
			checkArticlesAddedToBag();
		}
		dataTest.getDataBag().setImporteTotal(secBolsa.getPrecioSubTotal());
		dataTest.getDataBag().setImporteTransp(secBolsa.getPrecioTransporte());
	}

	@Step (
		description="Utilizar el buscador para acceder a la ficha y dar de alta los siguientes productos en la bolsa:<br>" + TAG_LISTA_ART,
		expected="Los productos se dan de alta en la bolsa correctamente",
		saveErrorData=ALWAYS, saveNettraffic=ALWAYS)
	public void addArticlesBag(List<Article> listParaAlta) {
		insertArticlesInStepDescription(listParaAlta);
		for (int i=0; i<listParaAlta.size(); i++) {
			var artTmp = listParaAlta.get(i);
			var articulo = new UtilsMangoTest().addArticuloBolsa(artTmp);
			dataTest.getDataBag().add(articulo);
		}

		if (isDesktop()) {
			secBolsa.isInStateUntil(OPEN, 10);
		}
	}
	
	@Step (
		description="Añadimos el primer artículo de la bolsa a <b>Favoritos</b>",
		expected="El producto se añade correctamente a favoritos")
	public void addArticleToFavorites() {	
		secBolsa.addArticleToFavorites();
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
		replaceStepDescription(TAG_LISTA_ART, str.toString());
	}

	public void checkArticlesAddedToBag() throws Exception {
		checkNumArticlesInBag();
		if (isDesktop()) {
			checkIsBolsaVisibleInDesktop();
		}
		checkArticulosBolsa();
		checksDefault();
		checksGeneric().googleAnalytics().execute();
	}

	@Validation(description="La bolsa está vacía")
	public boolean checkBolsaIsVoid() {
		return secBolsa.numberItemsIs("0");
	}
	
	@Validation
	private ChecksTM checkIsBolsaVisibleInDesktop() {
		var checks = ChecksTM.getNew();
		int seconds = 1;
		
	 	checks.add(
			"Es visible la capa/página correspondiente a la bolsa " + getLitSecondsWait(seconds),
			secBolsa.isInStateUntil(OPEN, seconds));
	 	
	 	checks.add(
			"Aparece el botón \"Comprar\" " + getLitSecondsWait(seconds),
			secBolsa.isVisibleBotonComprarUntil(seconds));
	 	
		return checks;
	}

	@Validation
	public ChecksTM checkNumArticlesInBag() {
		var checks = ChecksTM.getNew();
		int seconds = 3;
		String itemsSaved = String.valueOf(dataTest.getDataBag().getListArticulos().size());
	 	checks.add(
			"Existen " + dataTest.getDataBag().getListArticulos().size() + " elementos dados de alta en la bolsa " + getLitSecondsWait(seconds),
			secBolsa.numberItemsIsUntil(itemsSaved, seconds), WARN);
	 	
	 	return checks;
	}

	@Validation
	public ChecksTM checkArticulosBolsa() throws Exception {
		var checks = ChecksTM.getNew();
		var checkerBag = new CheckerContentBolsa();
		checks.add(
			"Cuadra el númeo de artículos existentes en la bolsa",
			checkerBag.numArticlesIsCorrect(), WARN);
		
		var listToCheck = new ArrayList<DataArtBolsa>();
		listToCheck.add(REFERENCIA);
		checks.add(
			"Cuadran las referencias de los artículos existentes en la bolsa",
			checkerBag.allArticlesExpectedDataAreInScreen(listToCheck), WARN);
		
		listToCheck.clear();
		listToCheck.add(NOMBRE);
		checks.add(
			"Cuadran los nombres de los artículos existentes en la bolsa",
			checkerBag.allArticlesExpectedDataAreInScreen(listToCheck), WARN);
		
		listToCheck.clear();
		listToCheck.add(COLOR);
		checks.add(
			"Cuadran los colores de los artículos existentes en la bolsa",
			checkerBag.allArticlesExpectedDataAreInScreen(listToCheck), WARN);
		
		listToCheck.clear();
		listToCheck.add(TALLA);
		boolean tallaNumOk = checkerBag.allArticlesExpectedDataAreInScreen(listToCheck);
		checks.add(
			"Cuadran las tallas de los artículos existentes en la bolsa",
			tallaNumOk, WARN);
		
		listToCheck.clear();
		listToCheck.add(PRECIO_TOTAL);
		checks.add(
			"Cuadran los precios de los artículos existentes en la bolsa",
			checkerBag.allArticlesExpectedDataAreInScreen(listToCheck), WARN);
		
		return checks;
	}

	public void clear1erArticuloBolsa() throws Exception {
		var artToClear = dataTest.getDataBag().getArticulo(0);
		clearArticuloBolsa(artToClear);
	}

	@Step (
		description="Eliminar el artículo-1 #{artToClear.getReferencia()} de la bolsa", 
		expected="El artículo se elimina correctamente")
	private void clearArticuloBolsa(ArticuloScreen artToClear) throws Exception {
		String importeTotalOrig = secBolsa.getPrecioSubtotalTextPant();
		secBolsa.getLineasArtBolsa().clearArticuloAndWait(artToClear.getReferencia());
		dataTest.getDataBag().remove(0); 
		checkImporteIsModified(importeTotalOrig, 5);
		checkArticulosBolsa();
	}

	@Validation (
		description="El importe total se acaba modificando " + SECONDS_WAIT,
		level=WARN)
	private boolean checkImporteIsModified(String importeTotalOrig, int seconds) {
		return (secBolsa.isNotThisImporteTotalUntil(importeTotalOrig, seconds));
	}
	
	public void selectButtonComprar() {
		selectButtonComprar(CONTINUAR_SIN_CUENTA);
	}
	
	public void selectButtonComprar(FluxBolsaCheckout fluxMobile) {
		selectButtonComprarBasic();
		fluxPostSelectComprar(fluxMobile);
		checksDefault();
	}

	//TODO eliminar cuando veamos que el problema no se produzca
	private void normalizeRandomErrorIfExistsInMobil() {
		if (!isNotRandomErrorInMobil()) {
			back();
			String url = driver.getCurrentUrl();
			inputUrlMobil();
			get(url);
			forceStateBolsaTo(OPEN);
			selectButtonComprarBasic();
		}
	}
	
	private void inputUrlMobil() {
		try {
			get(inputParamsSuite.getDnsUrlAcceso() + "/mobil");
		}
		catch (URISyntaxException e) {
			Log4jTM.getLogger().warn("Problem getting DNS URL Acceso", e);
		}
	}
	
	@Validation (
		description=
			"No se ha producido el error random según el cual aparece la página de checkout en formato Desktop",
		level=WARN)
	private boolean isNotRandomErrorInMobil() {
		return !isRandomErrorInMobil();
	}
	
	private boolean isRandomErrorInMobil() {
		if (!isMobile() || isPRO()) {
			return false;
		}
		if (!dataTest.isUserRegistered() &&
			!isVisibleContinuarSinCuentaButtonDevice(2) && 
			isDesktopLoginCheckoutPage()) {
			return true;
		}
		return 
			dataTest.isUserRegistered() &&
			!isFirstPageCheckoutMobil(2) &&
			isDesktopCheckoutPage();
	}
	
	private boolean isFirstPageCheckoutMobil(int seconds) {
		return new Page1EnvioCheckoutMobilSteps().isPage(seconds);
	}
	
	private boolean isDesktopLoginCheckoutPage() {
		return new Page1IdentCheckoutSteps().isUrlDesktopPage();
	}
	
	private boolean isDesktopCheckoutPage() {
		return new Page1DktopCheckoutSteps().isPage(0);
	}
	
	private void fluxPostSelectComprar(FluxBolsaCheckout fluxMobile) {
		if (dataTest.getPais().isNewcheckout(app)) {
			fluxPostSelectComprarCheckoutNew(fluxMobile);
		} else {
			fluxPostSelectComprarCheckoutOld(fluxMobile);
		}
	}
	
	private void fluxPostSelectComprarCheckoutNew(FluxBolsaCheckout flux) {
		var checkoutSteps = new CheckoutNewSteps();
		if (dataTest.isUserRegistered()) {
			if (isCheckeableNewCheckout()) {
				checkoutSteps.isPageCheckout(10);
			}
		} else {
			if (isDevice()) {
				checkVisibleContinuarSinCuentaButtonDevice(2);
			} else {
				checkoutSteps.isPageIdentificationDesktop(5);
			}
			
			if (flux==FluxBolsaCheckout.CONTINUAR_SIN_CUENTA) {
				if (isDevice()) {
					clickContinuarSinCuentaMobile();
				} else {
					checkoutSteps.continueAsGuestDesktop();
				}
				if (isCheckeableNewCheckout()) {
					checkoutSteps.isPageGuestUserData(8);
				}
			}
		}
	}
	
	private void fluxPostSelectComprarCheckoutOld(FluxBolsaCheckout fluxMobile) {
		normalizeRandomErrorIfExistsInMobil();
		if (!dataTest.isUserRegistered()) {
			if (isMobile()) {
				fluxPostSelectComprarUserNotIdentifiedMobile(fluxMobile);
			} else {
				new Page1IdentCheckoutSteps().isPage(7);
			}
		} else {
			new CheckoutSteps().checkIsFirstPage(dataTest.isUserRegistered());
		}
	}

	private void fluxPostSelectComprarUserNotIdentifiedMobile(FluxBolsaCheckout flux) {
		if (dataTest.getPais().isNewcheckout(app)) {
			fluxPostSelectComprarUserNotIdentifiedMobileNew(flux);
		} else {
			fluxPostSelectComprarUserNotIdentifiedMobileOld(flux);
		}
	}
	
	//TODO [flux-bolsa] reactivar cuando se reactive el nuevo flujo
	private void fluxPostSelectComprarUserNotIdentifiedMobileNew(FluxBolsaCheckout flux) {
		checkVisibleContinuarSinCuentaButtonDevice(2);
		switch (flux) {
		case INICIAR_SESION:
			clickIniciarSesionMobile();
			new PageIniciarSesionBolsaMobileSteps().isPage(3);			
			break;
		case CONTINUAR_SIN_CUENTA:
			clickContinuarSinCuentaMobile();
			new Page1IdentCheckoutSteps().isPage(7);
			break;
		case REGISTRO:
			clickRegistroMobile();
			new PageRegistroInitialShopSteps().checkIsPage(5);
		}
	}
	
	private void fluxPostSelectComprarUserNotIdentifiedMobileOld(FluxBolsaCheckout flux) {
		if (flux==REGISTRO) {
			throw new UnsupportedOperationException("Registro is not supported in the old bolsa->checkout flux");			
		}
		new Page1IdentCheckoutSteps().isPage(7);
	}	
	
	@Step (
		description="Se selecciona el botón \"COMPRAR\" de la bolsa", 
		expected="Se muestra la página de identificación",
		saveNettraffic=ALWAYS)
	public void selectButtonComprarBasic() {
		secBolsa.setBolsaToStateIfNotYet(OPEN);
		secBolsa.clickBotonComprar(10);
	}
	
	@Step (
		description="Se selecciona el botón <b>Iniciar sesión</b>",
		expected="Se muestra la página de identificación")
	public void clickIniciarSesionMobile() {
		secBolsa.clickIniciarSesionMobile();
	}	
	
	@Step (
		description="Se selecciona el botón <b>Continuar sin cuenta</b>",
		expected="Se muestra la página de continuar como invitado/a")
	public void clickContinuarSinCuentaMobile() {
		secBolsa.clickContinuarSinCuentaMobile();
	}
	
	@Step (
		description="Se selecciona el botón <b>Registro</b>",
		expected="Se muestra la página de inicio del registro")
	public void clickRegistroMobile() {
		secBolsa.clickRegistroMobile();
	}

	@Validation (
		description="Es visible el botón \"Continuar sin cuenta\" " + SECONDS_WAIT)
	private boolean checkVisibleContinuarSinCuentaButtonDevice(int seconds) {
		return isVisibleContinuarSinCuentaButtonDevice(seconds);
	}
	
	private boolean isVisibleContinuarSinCuentaButtonDevice(int seconds) {
		return secBolsa.isVisibleContinuarSinCuentaButtonMobile(seconds);
	}

	public void click1erArticuloBolsa() {
		ArticuloScreen articuloClickado = dataTest.getDataBag().getArticulo(0);
		clickArticuloBolsa(articuloClickado);
	}

	@Step (
		description="Lincar con el artículo existente en la bolsa" + " #{articuloClickado.getReferencia()})", 
		expected="El link al artículo es correcto")
	public void clickArticuloBolsa(ArticuloScreen articuloClickado) {
		secBolsa.setBolsaToStateIfNotYet(OPEN);
		secBolsa.click1erArticuloBolsa();

		String refArticulo = articuloClickado.getReferencia();
		new FichaSteps().checkIsFichaArtDisponible(refArticulo, 3);
		checksDefault();
	}
	
}
