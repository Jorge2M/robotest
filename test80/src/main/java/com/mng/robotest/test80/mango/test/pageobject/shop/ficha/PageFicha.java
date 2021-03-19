package com.mng.robotest.test80.mango.test.pageobject.shop.ficha;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.Talla;
import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

@SuppressWarnings({"static-access"})
public abstract class PageFicha extends PageObjTM {

    public enum TypeFicha {Old, New}
    
    abstract public boolean isPageUntil(int maxSeconds);
    abstract public boolean isFichaArticuloUntil(String refArticulo, int maxSecondsToWait);
    abstract public void clickAnadirBolsaButtonAndWait();
    abstract public void selectAnadirAFavoritosButton();
    abstract public void selectRemoveFromFavoritosButton();
    abstract public boolean isVisibleDivAnadiendoAFavoritosUntil(int maxSecondsToWait);
    abstract public boolean isInvisibleDivAnadiendoAFavoritosUntil(int maxSecondsToWait);
    abstract public boolean isVisibleButtonElimFavoritos();
    abstract public boolean isVisibleButtonAnadirFavoritos();
    abstract public String getNameLinkBuscarEnTienda();
    abstract public void selectBuscarEnTiendaLink();
    abstract public boolean isVisibleSlider(Slider typeSlider);
    abstract public int getNumArtVisiblesSlider(Slider typeSlider);
    abstract public boolean isModalNoStockVisible(int maxSecondsToWait);
    
    protected final SecDataProduct secDataProduct; //Name, color, talla section
    public static SecFitFinder secFitFinder; //Guía de tallas v.Fit Finder
    
    private final TypeFicha typeFicha;
    Channel channel;
    AppEcom appE;
    
    public PageFicha(TypeFicha typeFicha, WebDriver driver) {
    	super(driver);
    	this.secDataProduct = new SecDataProduct(typeFicha, driver);
    	this.typeFicha = typeFicha;
    }
    
    public TypeFicha getTypeFicha() {
    	return typeFicha;
    }
    
    public SecDataProduct getSecDataProduct() {
    	return getSecDataProduct();
    }
    
    //Constructor estático
    public static PageFicha newInstance(Channel channel, AppEcom appE, WebDriver driver) {
    	PageFicha pageFicha;
        if (appE==AppEcom.outlet || channel.isDevice()) {
        	pageFicha = PageFichaArtOld.getNewInstance(channel, driver);
        } else {
        	pageFicha = PageFichaArt_DesktopShop.getNewInstance(channel, driver);
        }
        pageFicha.appE = appE;
        return pageFicha;
    }
    
    public static PageFicha newInstanceFichaNew(Channel channel, AppEcom appE, WebDriver driver) {
    	PageFicha pageFicha = PageFichaArt_DesktopShop.getNewInstance(channel, driver);
    	pageFicha.appE = appE;
    	return pageFicha;
    }
    
    public ArticuloScreen getArticuloObject(AppEcom app) {
        return (secDataProduct.getArticuloObject(channel, app));
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
}
