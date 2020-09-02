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
    
    public static SecDataProduct secDataProduct; //Name, color, talla section
    public static SecFitFinder secFitFinder; //Guía de tallas v.Fit Finder
    Channel channel;
    AppEcom appE;
    TypeFicha typeFicha;
    
    public PageFicha(WebDriver driver) {
    	super(driver);
    };
    
    //Constructor estático
    public static PageFicha newInstance(Channel channel, AppEcom appE, WebDriver driver) {
    	PageFicha pageFicha;
        if (appE==AppEcom.outlet || channel==Channel.mobile) {
        	pageFicha = PageFichaArtOld.getNewInstance(channel, driver);
        } else {
        	pageFicha = PageFichaArtNew.getNewInstance(channel, driver);
        }
        pageFicha.appE = appE;
        return pageFicha;
    }
    
    public static PageFicha newInstanceFichaNew(Channel channel, AppEcom appE, WebDriver driver) {
    	PageFicha pageFicha = PageFichaArtNew.getNewInstance(channel, driver);
    	pageFicha.appE = appE;
    	return pageFicha;
    }

    public TypeFicha getTypeFicha() {
    	return this.typeFicha;
    }
    
    public ArticuloScreen getArticuloObject(AppEcom app) {
        return (secDataProduct.getArticuloObject(channel, app, typeFicha, driver));
    }
    
    public boolean isTallaUnica() {
        return (secDataProduct.isTallaUnica(typeFicha, driver));
    }
    
    public Talla getTallaSelected() {
        return (SecDataProduct.getTallaSelected(typeFicha, appE, driver));
    }
    
    public void selectTallaByValue(Talla talla) {
        secDataProduct.selectTallaByValue(talla, typeFicha, driver);
    }
    
    public void selectTallaByIndex(int posicion) {
        secDataProduct.selectTallaByIndex(posicion, typeFicha, driver);
    }
    
    public void selectFirstTallaAvailable() {
        secDataProduct.selectFirstTallaAvailable(typeFicha, driver);
    }
    
    public String getTallaAlf(int posicion) {
    	return (secDataProduct.getTallaAlf(typeFicha, posicion, driver));
    }
    
    public String getTallaCodNum(int posicion) {
    	return (secDataProduct.getTallaCodNum(typeFicha, posicion, driver));
    }
    
    public int getNumOptionsTallasNoDisponibles() {
        return (secDataProduct.getNumOptionsTallasNoDisponibles(typeFicha, driver));
    }
    
    public int getNumOptionsTallas() {
        return (secDataProduct.getNumOptionsTallas(typeFicha, driver));
    }
    
    public boolean isFichaAccesorio() {
        return (this.driver.getCurrentUrl().contains("accesorio"));
    }
}
