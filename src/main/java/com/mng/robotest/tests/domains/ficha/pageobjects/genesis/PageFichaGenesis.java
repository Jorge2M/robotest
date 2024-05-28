package com.mng.robotest.tests.domains.ficha.pageobjects.genesis;

import java.util.List;
import java.util.regex.Pattern;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.ficha.pageobjects.PageFicha;
import com.mng.robotest.tests.domains.ficha.pageobjects.commons.ColorType;
import com.mng.robotest.tests.domains.ficha.pageobjects.commons.SecSliders;
import com.mng.robotest.tests.domains.ficha.pageobjects.commons.SecSliders.Slider;
import com.mng.robotest.tests.domains.ficha.pageobjects.nogenesis.SecFitFinder;
import com.mng.robotest.tests.domains.ficha.pageobjects.nogenesis.SecDetalleProduct.ItemBreadcrumb;
import com.mng.robotest.testslegacy.data.Talla;
import com.mng.robotest.testslegacy.generic.beans.ArticuloScreen;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageFichaGenesis extends PageBase implements PageFicha {

	private final SecColorsGenesis secColors = new SecColorsGenesis();
	private final SecTallasGenesis secTallas = new SecTallasGenesis();
	private final SecStickyContentGenesis secStickyContent = new SecStickyContentGenesis();
	private final SecSliders secSliders = new SecSliders();
	private final SecFitFinder secFitFinder = new SecFitFinder(); //Guía de tallas v.Fit Finder
	
	private static final String XP_PAGE = "//*[@data-testid='pdp.gallery.grid']";
	private static final String XP_PAGE_ARTICLE = "//*[@data-testid='pdp.productInfo.reference']";
	private static final String XP_ADD_FAVORITES_BUTTON = "//*[@data-testid='pdp.productInfo.favorite.inactive']";
	private static final String XP_REMOVE_FAVORITES_BUTTON = "//*[@data-testid='pdp.productInfo.favorite.active']";
	private static final String XP_BOLSA_BUTTON = "//*[@data-testid='pdp.productInfo.addToBag']";
	private static final String XP_TITLE_ART = "//*[@data-testid='pdp.productInfo.title']";
	private static final String XP_WRAPPER_PRICES = "//*[@data-testid='pdp.productInfo.price']";
	private static final String XP_PRICE_CURRENT = XP_WRAPPER_PRICES + "//*[@data-testid='currentPrice']";
	private static final String XP_PRICE_TACHADO = XP_WRAPPER_PRICES + "//*[@data-testid='crossedOutPrice']";
	private static final String XP_DISPONIBILIDAD_TIENDA_BUTTON = "//*[@data-testid='pdp.productInfo.storeFinder']";
	
	private String getXPathPageArticulo(String reference) {
		return XP_PAGE_ARTICLE + "//self::*[text()[contains(.,'" + reference + "')]]";
	}
	
	@Override
	public boolean isPage(int seconds) { //Tested
		return state(VISIBLE, XP_PAGE).check();
	}
	
	@Override	
	public boolean isFichaArticuloUntil(String reference, int seconds) { //Tested
		String refSinColor = reference.substring(0,8);
		return state(PRESENT, getXPathPageArticulo(refSinColor)).wait(seconds).check(); 
	}

	@Override
	public ArticuloScreen getArticuloObject() {
		var articulo = new ArticuloScreen();
		articulo.setReferencia(getReferencia());
		articulo.setNombre(getTituloArt());
		articulo.setPrecio(getPrecioFinalArticulo());
		articulo.setCodigoColor(getCodeColor(ColorType.SELECTED));
		articulo.setColorName(getNombreColorSelected());
		articulo.setTalla(getTallaSelected());
		articulo.setNumero(1);
		return articulo;
	}
	private String getReferencia() {
		String referenceText = getElement(XP_PAGE_ARTICLE).getText();
		var pattern = Pattern.compile("\\b\\d{8}\\b");
		var matcher = pattern.matcher(referenceText);
		if (matcher.find()) {
			return matcher.group();
		}
		throw new NoSuchElementException("Problem obtaining the article reference");
	}
	
	@Override	
	public boolean isVisibleBolsaButton(int seconds) {
		return state(VISIBLE, XP_BOLSA_BUTTON).wait(seconds).check();
	}
	
	@Override	
	public void clickAnadirBolsaButtonAndWait() {
		click(XP_BOLSA_BUTTON).exec();
	}
	
	@Override	
	public boolean isVisibleButtonElimFavoritos(int seconds) {
		return state(VISIBLE, XP_REMOVE_FAVORITES_BUTTON).wait(seconds).check();
	}
	
	@Override	
	public boolean isVisibleButtonAnadirFavoritos(int seconds) {
		return state(VISIBLE, XP_ADD_FAVORITES_BUTTON).wait(seconds).check();
	}
	
	@Override	
	public void selectAnadirAFavoritosButton() {
		state(VISIBLE, XP_ADD_FAVORITES_BUTTON).wait(1).check();
		click(XP_ADD_FAVORITES_BUTTON).exec();
	}
	
	@Override	
	public void selectRemoveFromFavoritosButton() {
		state(VISIBLE, XP_REMOVE_FAVORITES_BUTTON).wait(1).check();
		click(XP_REMOVE_FAVORITES_BUTTON).exec();
	}
	
	@Override	
	public boolean isVisibleDivAnadiendoAFavoritosUntil(int seconds) {
		throw new UnsupportedOperationException();
	}
	
	@Override	
	public boolean isInvisibleDivAnadiendoAFavoritosUntil(int seconds) {
		throw new UnsupportedOperationException();
	}
	
	@Override	
	public void selectBuscarEnTiendaLink() {
		click(XP_DISPONIBILIDAD_TIENDA_BUTTON).exec();
	}
	
	@Override	
	public boolean isVisibleBuscarEnTiendaLink() {
		throw new UnsupportedOperationException();
	}
	
	@Override	
	public boolean isModalNoStockVisible(int seconds) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getTituloArt() {
		return getElement(XP_TITLE_ART).getText();
	}
	@Override
	public String getPrecioFinalArticulo() {
		if (isDevice()) {
			return getElement(XP_PRICE_CURRENT + "//span/span").getAttribute("innerHTML");
		}
		return getElementVisible(XP_PRICE_CURRENT).getText();
	}
	@Override
	public String getPrecioTachadoFromFichaArt() {
	    return findElement(XP_PRICE_TACHADO)
	            .map(WebElement::getText)
	            .orElse("0");
	}
	
	@Override
	public boolean isVisibleCapaAvisame() {
		throw new UnsupportedOperationException();
	}
	
	//-- Colors
	
	@Override
	public boolean isClickableColor(String colorCode) {
		return secColors.isClickableColor(colorCode);
	}
	@Override
	public void selectColor(String colorCode) {
		secColors.selectColor(colorCode);
	}
	@Override
	public void clickColor(int posColor) {
		secColors.clickColor(posColor);
	}
	@Override
	public void selectColorWaitingForAvailability(String colorCode) {
		secColors.selectColorWaitingForAvailability(colorCode);
	}
	@Override
	public String getNombreColorSelected() {
		return secColors.getNombreColorSelected();
	}
	@Override
	public String getCodeColor(ColorType colorType) {
		return secColors.getCodeColor(colorType);
	}
	@Override
	public boolean isPresentColor(ColorType colorType) {
		return secColors.isPresentColor(colorType);
	}
	@Override
	public List<String> getColorsGarment() {
		return secColors.getColorsGarment();
	}
	@Override	
	public int getNumColors() {
		return secColors.getNumColors();
	}

	
	//-- Tallas
	
	@Override
	public boolean isVisibleAvisoSeleccionTalla() {
		return secTallas.isVisibleAvisoSeleccionTalla();
	}
	@Override
	public boolean isVisibleListTallasForSelectUntil(int seconds) {
		return secTallas.isVisibleListTallasForSelectUntil(seconds);
	}
	@Override
	public boolean selectGuiaDeTallasIfVisible() {
		return secTallas.selectGuiaDeTallasIfVisible();
	}
	@Override	
	public boolean isTallaUnica() {
		return secTallas.isTallaUnica();
	}
	@Override	
	public Talla getTallaSelected() {
		return secTallas.getTallaSelected();
	}
	@Override
	public String getTallaSelectedAlf() {
		return secTallas.getTallaSelectedAlf();
	}
	@Override	
	public void selectTallaByLabel(String label) {
		secTallas.selectTallaByLabel(label);
	}
	@Override
	public void selectTallaByValue(String tallaValue) {
		secTallas.selectTallaByValue(tallaValue);
	}
	@Override	
	public void selectTallaByValue(Talla talla) {
		secTallas.selectTallaByValue(talla);
	}
	@Override	
	public void selectTallaByIndex(int posicion) {
		secTallas.selectTallaByIndex(posicion);
	}
	@Override	
	public void selectFirstTallaAvailable() {
		secTallas.selectFirstTallaAvailable();
	}
	@Override	
	public String getTallaAlf(int position) {
		return secTallas.getTallaAlf(position);
	}
	@Override	
	public String getTallaCodNum(int posicion) {
		return secTallas.getTallaCodNum(posicion);
	}
	@Override	
	public int getNumOptionsTallasNoDisponibles() {
		return secTallas.getNumOptionsTallasNoDisponibles();
	}
	@Override	
	public int getNumOptionsTallas() {
		return secTallas.getNumOptionsTallas();
	}
	@Override	
	public void closeTallas() {
		secTallas.closeTallas();
	}

	
	//-- Sliders
	
	@Override	
	public boolean isVisibleSlider(Slider slider) {
		return secSliders.isVisible(slider);
	}
	@Override	
	public int getNumArtVisiblesSlider(Slider slider) {
		return secSliders.getNumVisibleArticles(slider);
	}
	
	
	//-- Sticky Content
	
	@Override
	public boolean isVisibleStickyContent(int seconds) {
		return secStickyContent.isVisibleStickyContent(seconds);
	}
	@Override
	public boolean isInvisibleStickyContent(int seconds) {
		return secStickyContent.isInvisibleStickyContent(seconds);
	}	
	@Override
	public boolean isVisibleReferenciaStickyContent(String referencia) {
		return secStickyContent.isVisibleReferenciaStickyContent(referencia);
	}
	@Override
	public boolean isVisibleTallaLabelStickyContent(String tallaLabel) {
		return secStickyContent.isVisibleTallaLabelStickyContent(tallaLabel);
	}
	@Override
	public boolean isVisibleColorCodeStickyContent(String colorCode) {
		return secStickyContent.isVisibleColorCodeStickyContent(colorCode);
	}
	
	// -- Others
	private static final String XP_ENVIO_GRATIS_A_TIENDA = "//*[@data-testid='pdp.productInfo.shippingAndReturns']";
	private static final String XP_DETALLES_DEL_PRODUCTO = "//*[@data-testid='pdp.productDetails.button']";
	private static final String XP_DESCRIPCION_PRODUCTO = "//*[@data-testid='pdp.productDetails.description']";
	
	@Override
	public void selectEnvioGratisTienda() {
		click(XP_ENVIO_GRATIS_A_TIENDA).exec();
	}
	@Override
	public void selectDetalleDelProducto() {
		click(XP_DETALLES_DEL_PRODUCTO).exec();
	}
	@Override
	public void selectLinkCompartir() {
		//No aparece en la nueva Ficha Genesis
		throw new UnsupportedOperationException();
	}
	@Override
	public boolean isVisibleDescription() {
		return state(VISIBLE, XP_DESCRIPCION_PRODUCTO).check();
	}
	@Override
	public boolean isVisibleBreadcrumbs(int seconds) {
		//No aparece en la nueva Ficha Genesis
		throw new UnsupportedOperationException();
	}
	@Override
	public boolean isVisibleItemBreadCrumb(ItemBreadcrumb item) {
		//No aparece en la nueva Ficha Genesis
		throw new UnsupportedOperationException();
	}
	
	private static final String XP_KC_SAFETY = "//*[@data-testid='pdp.productDetails.kcSafety']";
	
	@Override
	public boolean isVisibleBlockKcSafety() {
		return state(VISIBLE, XP_KC_SAFETY).check();
	}
	
	private static final String XP_MODAL_DATOS_ENVIO = "//dialog[@id='shipping-returns-modal']";
	private static final String XP_CLOSE_MODAL_DATOS_ENVIO = XP_MODAL_DATOS_ENVIO + "//*[@data-testid='modal.close.button']";
	
	@Override
	public boolean isVisibleModalDatosEnvio(int seconds) {
		return state(VISIBLE, XP_MODAL_DATOS_ENVIO).wait(seconds).check();
	}
	
	@Override
	public void closeModalDatosEnvio() {
		click(XP_CLOSE_MODAL_DATOS_ENVIO).exec();
	}	
	
	@Override
	public boolean isVisibleGuiaTallas(int seconds) {
		return secFitFinder.isVisibleUntil(seconds);
	}
	@Override
	public boolean isInvisibleGuiaTallas(int seconds) {
		return secFitFinder.isInvisibileUntil(seconds);
	}
	@Override
	public void closeGuiaTallas() {
		secFitFinder.close();
	}
	
	private static final String XP_ADD_BORDADO = "//div[@class[contains(.,'Customization')]//button";
	
	@Override
	public boolean isLinkAddBordado(int seconds) {
		return state(VISIBLE, XP_ADD_BORDADO).wait(seconds).check();
	}	
	@Override
	public void clickAddBordado() {
		click(XP_ADD_BORDADO).exec();
	}	
	
}
