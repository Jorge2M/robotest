package com.mng.robotest.tests.domains.ficha.pageobjects;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.ficha.pageobjects.SecSliders.Slider;
import com.mng.robotest.testslegacy.data.Talla;
import com.mng.robotest.testslegacy.generic.beans.ArticuloScreen;

public abstract class PageFicha extends PageBase {

	public abstract boolean isPageUntil(int seconds);
	public abstract boolean isFichaArticuloUntil(String refArticulo, int seconds);
	public abstract boolean isVisibleBolsaButton(int seconds);
	public abstract void clickAnadirBolsaButtonAndWait();
	public abstract void selectAnadirAFavoritosButton();
	public abstract void selectRemoveFromFavoritosButton();
	public abstract boolean isVisibleDivAnadiendoAFavoritosUntil(int seconds);
	public abstract boolean isInvisibleDivAnadiendoAFavoritosUntil(int seconds);
	public abstract boolean isVisibleButtonElimFavoritos(int seconds);
	public abstract boolean isVisibleButtonAnadirFavoritos(int seconds);
	public abstract String getNameLinkBuscarEnTienda();
	public abstract void selectBuscarEnTiendaLink();
	public abstract boolean isVisibleBuscarEnTiendaLink();
	public abstract boolean isModalNoStockVisible(int seconds);

	protected final SecDataProduct secDataProduct = new SecDataProduct(); //Name, color, talla section
	protected final SecFitFinder secFitFinder = new SecFitFinder(); //Guía de tallas v.Fit Finder
	protected final SecSliders secSliders = new SecSliders();

	public SecDataProduct getSecDataProduct() {
		return secDataProduct;
	}

	public static PageFicha of(Channel channel) {
		if (channel.isDevice()) {
			return new PageFichaDevice();
		} else {
			return new PageFichaDesktop();
		}
	}

	public ArticuloScreen getArticuloObject() {
		return (secDataProduct.getArticuloObject());
	}

	public boolean isTallaUnica() {
		return (secDataProduct.getSecSelTallas().isTallaUnica());
	}

	public Talla getTallaSelected() {
		return (secDataProduct.getSecSelTallas().getTallaSelected(app));
	}

	public void selectTallaByValue(Talla talla) {
		secDataProduct.getSecSelTallas().selectTallaByValue(talla);
	}

	public void selectTallaByLabel(String tallaLabel) {
		secDataProduct.getSecSelTallas().selectTallaByLabel(tallaLabel);
	}

	public void selectTallaByIndex(int posicion) {
		secDataProduct.getSecSelTallas().selectTallaByIndex(posicion);
	}

	public void selectFirstTallaAvailable() {
		secDataProduct.getSecSelTallas().selectFirstTallaAvailable();
	}

	public String getTallaAlf(int posicion) {
		return secDataProduct.getSecSelTallas().getTallaAlf(posicion);
	}

	public String getTallaCodNum(int posicion) {
		return secDataProduct.getSecSelTallas().getTallaCodNum(posicion);
	}

	public int getNumOptionsTallasNoDisponibles() {
		return secDataProduct.getSecSelTallas().getNumOptionsTallasNoDisponibles();
	}

	public int getNumOptionsTallas() {
		return secDataProduct.getSecSelTallas().getNumOptionsTallas();
	}
	public void closeTallas() {
		secDataProduct.getSecSelTallas().closeTallas();
	}	

	public boolean isFichaAccesorio() {
		return (this.driver.getCurrentUrl().contains("accesorio"));
	}

	public int getNumColors() {
		return secDataProduct.getNumColors();
	}
	
	public boolean isVisibleSlider(Slider slider) {
		return secSliders.isVisible(slider);
	}
	public int getNumArtVisiblesSlider(Slider slider) {
		return secSliders.getNumVisibleArticles(slider);
	}

}
