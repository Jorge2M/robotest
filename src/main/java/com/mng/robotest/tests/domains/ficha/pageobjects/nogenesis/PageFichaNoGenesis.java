package com.mng.robotest.tests.domains.ficha.pageobjects.nogenesis;

import java.util.List;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.ficha.pageobjects.PageFicha;
import com.mng.robotest.tests.domains.ficha.pageobjects.commons.ColorType;
import com.mng.robotest.tests.domains.ficha.pageobjects.commons.SecSliders;
import com.mng.robotest.tests.domains.ficha.pageobjects.commons.SecSliders.Slider;
import com.mng.robotest.testslegacy.data.PaisShop;
import com.mng.robotest.testslegacy.data.Talla;
import com.mng.robotest.testslegacy.generic.beans.ArticuloScreen;

public abstract class PageFichaNoGenesis extends PageBase implements PageFicha {

	protected final SecDataProduct secDataProduct = new SecDataProduct(); //Name, color, talla section
	protected final SecFitFinder secFitFinder = new SecFitFinder(); //Guía de tallas v.Fit Finder
	protected final SecSliders secSliders = new SecSliders();

	@Override
	public boolean isClickableColor(String colourCode) {
		return secDataProduct.isClickableColor(colourCode);
	}
	@Override
	public void clickColor(int numColor) {
		secDataProduct.clickColor(numColor);
	}
	@Override
	public void selectColor(String color) {
		secDataProduct.selectColor(color);
	}
	@Override
	public void selectColorWaitingForAvailability(String colourCode) {
		secDataProduct.selectColorWaitingForAvailability(colourCode);
	}
	@Override
	public String getCodeColor(ColorType colorType) {
		return secDataProduct.getCodeColor(colorType);
	}
	@Override
	public boolean isPresentColor(ColorType colorType) {
		return secDataProduct.isPresentColor(colorType);
	}
	@Override
	public List<String> getColorsGarment() {
		return secDataProduct.getColorsGarment();
	}
	@Override	
	public String getTituloArt() {
		return secDataProduct.getTituloArt();
	}
	@Override
	public String getPrecioFinalArticulo() {
		return secDataProduct.getPrecioFinalArticulo();
	}
	@Override
	public String getNombreColorSelected() {
		return secDataProduct.getNombreColorSelected();
	}
	@Override
	public String getPrecioTachadoFromFichaArt() {
		return secDataProduct.getPrecioTachadoFromFichaArt();
	}
	@Override
	public void selectTallaByValue(String tallaValue) {
		secDataProduct.getSecSelTallas().selectTallaByValue(tallaValue);
	}
	@Override
	public boolean selectGuiaDeTallasIfVisible() {
		return secDataProduct.selectGuiaDeTallasIfVisible();
	}
	@Override
	public boolean isVisibleCapaAvisame() {
		return secDataProduct.isVisibleCapaAvisame();
	}
	@Override
	public boolean isVisibleAvisoSeleccionTalla() {
		return secDataProduct.getSecSelTallas().isVisibleAvisoSeleccionTalla();
	}
	@Override
	public boolean isVisibleListTallasForSelectUntil(int seconds) {
		return secDataProduct.getSecSelTallas().isVisibleListTallasForSelectUntil(seconds);
	}
	
	@Override
	public ArticuloScreen getArticuloObject() {
		return secDataProduct.getArticuloObject();
	}

	@Override
	public boolean isTallaUnica() {
		return secDataProduct.getSecSelTallas().isTallaUnica();
	}

	@Override
	public Talla getTallaSelected() {
		var pais = PaisShop.from(dataTest.getCodigoPais());
		return secDataProduct.getSecSelTallas().getTallaSelected(app, pais);
	}
	
	@Override
	public String getTallaSelectedAlf() {
		return secDataProduct.getSecSelTallas().getTallaSelectedAlf(app);
	}

	@Override
	public void selectTallaByValue(Talla talla) {
		secDataProduct.getSecSelTallas().selectTallaByValue(talla);
	}

	@Override
	public void selectTallaByLabel(String tallaLabel) {
		secDataProduct.getSecSelTallas().selectTallaByLabel(tallaLabel);
	}

	@Override
	public void selectTallaByIndex(int posicion) {
		secDataProduct.getSecSelTallas().selectTallaByIndex(posicion);
	}

	@Override
	public void selectFirstTallaAvailable() {
		secDataProduct.getSecSelTallas().selectFirstTallaAvailable();
	}

	@Override
	public String getTallaAlf(int posicion) {
		return secDataProduct.getSecSelTallas().getTallaAlf(posicion);
	}

	@Override
	public String getTallaCodNum(int posicion) {
		return secDataProduct.getSecSelTallas().getTallaCodNum(posicion);
	}

	@Override
	public int getNumOptionsTallasNoDisponibles() {
		return secDataProduct.getSecSelTallas().getNumOptionsTallasNoDisponibles();
	}

	@Override
	public int getNumOptionsTallas() {
		return secDataProduct.getSecSelTallas().getNumOptionsTallas();
	}
	
	@Override
	public void closeTallas() {
		secDataProduct.getSecSelTallas().closeTallas();
	}	

	@Override
	public boolean isFichaAccesorio() {
		return (this.driver.getCurrentUrl().contains("accesorio"));
	}

	@Override
	public int getNumColors() {
		return secDataProduct.getNumColors();
	}
	
	@Override
	public boolean isVisibleSlider(Slider slider) {
		return secSliders.isVisible(slider);
	}
	
	@Override
	public int getNumArtVisiblesSlider(Slider slider) {
		return secSliders.getNumVisibleArticles(slider);
	}

}
