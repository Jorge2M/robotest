package com.mng.robotest.domains.ficha.pageobjects;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.test.data.Talla;
import com.mng.robotest.test.generic.beans.ArticuloScreen;

public abstract class PageFicha extends PageBase {

	public enum TypeFicha { OLD, NEW }
	
	public abstract boolean isPageUntil(int seconds);
	public abstract boolean isFichaArticuloUntil(String refArticulo, int seconds);
	public abstract void clickAnadirBolsaButtonAndWait();
	public abstract void selectAnadirAFavoritosButton();
	public abstract void selectRemoveFromFavoritosButton();
	public abstract boolean isVisibleDivAnadiendoAFavoritosUntil(int seconds);
	public abstract boolean isInvisibleDivAnadiendoAFavoritosUntil(int seconds);
	public abstract boolean isVisibleButtonElimFavoritos();
	public abstract boolean isVisibleButtonAnadirFavoritos();
	public abstract String getNameLinkBuscarEnTienda();
	public abstract void selectBuscarEnTiendaLink();
	public abstract boolean isVisibleBuscarEnTiendaLink();
	public abstract boolean isVisibleSlider(Slider typeSlider);
	public abstract int getNumArtVisiblesSlider(Slider typeSlider);
	public abstract boolean isModalNoStockVisible(int seconds);
	
	protected final SecDataProduct secDataProduct; //Name, color, talla section
	public static SecFitFinder secFitFinder; //Guía de tallas v.Fit Finder
	
	private final TypeFicha typeFicha;
	
	public PageFicha(TypeFicha typeFicha) {
		this.secDataProduct = new SecDataProduct(typeFicha);
		this.typeFicha = typeFicha;
	}
	
	public TypeFicha getTypeFicha() {
		return typeFicha;
	}
	
	public SecDataProduct getSecDataProduct() {
		return secDataProduct;
	}
	
	public static PageFicha of(Channel channel) {
//		PageFichaArt_DesktopShop pageFichaNew = PageFichaArt_DesktopShop.getNewInstance();
//		PageFichaArtOld pageFichaOld = PageFichaArtOld.getNewInstance();
//		for (int i=0; i<5; i++) {
//			if (pageFichaNew.isPageUntil(1)) {
//				return pageFichaNew;
//			}
//			if (pageFichaOld.isPageUntil(1)) {
//				return pageFichaOld;
//			}
//		}
//		return pageFichaNew;
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
		return (secDataProduct.getSecSelTallas().getTallaAlf(posicion));
	}
	
	public String getTallaCodNum(int posicion) {
		return (secDataProduct.getSecSelTallas().getTallaCodNum(posicion));
	}
	
	public int getNumOptionsTallasNoDisponibles() {
		return (secDataProduct.getSecSelTallas().getNumOptionsTallasNoDisponibles());
	}
	
	public int getNumOptionsTallas() {
		return (secDataProduct.getSecSelTallas().getNumOptionsTallas());
	}
	
	public boolean isFichaAccesorio() {
		return (this.driver.getCurrentUrl().contains("accesorio"));
	}
	
	public int getNumColors() {
		return secDataProduct.getNumColors();
	}
}
