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
import com.mng.robotest.testslegacy.data.Talla;
import com.mng.robotest.testslegacy.generic.beans.ArticuloScreen;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageFichaGenesis extends PageBase implements PageFicha {

	private final SecColorsGenesis secColors = new SecColorsGenesis();
	private final SecTallasGenesis secTallas = new SecTallasGenesis();
	private final SecStickyContentGenesis secStickyContent = new SecStickyContentGenesis();
	private final SecSliders secSliders = new SecSliders();
	
	private static final String XP_PAGE = "//*[@data-testid='pdp.gallery.grid']";
	private static final String XP_PAGE_ARTICLE = "//*[@data-testid='pdp.productInfo.reference']";
	private static final String XP_ADD_FAVORITES_BUTTON = "//*[@data-testid='pdp.productInfo.favorite.inactive']";
	private static final String XP_REMOVE_FAVORITES_BUTTON = "//*[@data-testid='pdp.productInfo.favorite.active']";
	private static final String XP_BOLSA_BUTTON = "//*[@data-testid='pdp.productInfo.addToBag']";
	private static final String XP_TITLE_ART = "//*[@data-testid='pdp.productInfo.title']";
	private static final String XP_WRAPPER_PRICES = "//*[@data-testid='pdp.productInfo.price']";
	private static final String XP_PRICE_CURRENT = XP_WRAPPER_PRICES + "//*[@data-testid='currentPrice']";
	private static final String XP_PRICE_TACHADO = XP_WRAPPER_PRICES + "//*[@data-testid='crossedOutPrice']";
	
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
		//TODO implement
		return false;
	}
	
	@Override	
	public boolean isInvisibleDivAnadiendoAFavoritosUntil(int seconds) {
		//TODO implement
		return true;
	}
	
	@Override	
	public String getNameLinkBuscarEnTienda() {
		//TODO implement
		return "";
	}
	
	@Override	
	public void selectBuscarEnTiendaLink() {
		//TODO implement
	}
	
	@Override	
	public boolean isVisibleBuscarEnTiendaLink() {
		//TODO implement
		return false;
	}
	
	@Override	
	public boolean isModalNoStockVisible(int seconds) {
		//TODO implement
		return false;
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
		//TODO esperar a que añadan esta funcionalidad a la página
		return false;
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
	
}
