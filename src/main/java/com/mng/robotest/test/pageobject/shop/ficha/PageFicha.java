package com.mng.robotest.test.pageobject.shop.ficha;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.data.Talla;
import com.mng.robotest.test.generic.beans.ArticuloScreen;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

@SuppressWarnings({"static-access"})
public abstract class PageFicha extends PageObjTM {

	public enum TypeFicha {Old, New}
	
	public abstract boolean isPageUntil(int maxSeconds);
	public abstract boolean isFichaArticuloUntil(String refArticulo, int maxSecondsToWait);
	public abstract void clickAnadirBolsaButtonAndWait();
	public abstract void selectAnadirAFavoritosButton();
	public abstract void selectRemoveFromFavoritosButton();
	public abstract boolean isVisibleDivAnadiendoAFavoritosUntil(int maxSecondsToWait);
	public abstract boolean isInvisibleDivAnadiendoAFavoritosUntil(int maxSecondsToWait);
	public abstract boolean isVisibleButtonElimFavoritos();
	public abstract boolean isVisibleButtonAnadirFavoritos();
	public abstract String getNameLinkBuscarEnTienda();
	public abstract void selectBuscarEnTiendaLink();
	public abstract boolean isVisibleBuscarEnTiendaLink();
	public abstract boolean isVisibleSlider(Slider typeSlider);
	public abstract int getNumArtVisiblesSlider(Slider typeSlider);
	public abstract boolean isModalNoStockVisible(int maxSecondsToWait);
	
	protected final SecDataProduct secDataProduct; //Name, color, talla section
	public static SecFitFinder secFitFinder; //Guía de tallas v.Fit Finder
	
	private final TypeFicha typeFicha;
	final Channel channel;
	final AppEcom appE;
	
	public PageFicha(TypeFicha typeFicha, Channel channel, AppEcom app, WebDriver driver) {
		super(driver);
		this.secDataProduct = new SecDataProduct(typeFicha, channel, app, driver);
		this.typeFicha = typeFicha;
		this.channel = channel;
		this.appE = app;
	}
	
	public TypeFicha getTypeFicha() {
		return typeFicha;
	}
	
	public SecDataProduct getSecDataProduct() {
		return secDataProduct;
	}
	
	public static PageFicha newInstance(Channel channel, AppEcom app, WebDriver driver) {
		PageFicha pageFicha;
		if (app==AppEcom.outlet || channel.isDevice()) {
			pageFicha = PageFichaArtOld.getNewInstance(channel, app, driver);
		} else {
			pageFicha = PageFichaArt_DesktopShop.getNewInstance(channel, app, driver);
		}
		return pageFicha;
	}
	
	public static PageFicha newInstanceFichaNew(Channel channel, AppEcom app, WebDriver driver) {
		return PageFichaArt_DesktopShop.getNewInstance(channel, app, driver);
	}
	
	public ArticuloScreen getArticuloObject() {
		return (secDataProduct.getArticuloObject());
	}
	
	public boolean isTallaUnica() {
		return (secDataProduct.getSecSelTallas().isTallaUnica());
	}
	
	public Talla getTallaSelected() {
		return (secDataProduct.getSecSelTallas().getTallaSelected(appE));
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
