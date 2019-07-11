package com.mng.robotest.test80.mango.test.pageobject.shop.ficha;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;

@SuppressWarnings({"static-access"})
public abstract class PageFicha extends WebdrvWrapp {

    public enum TypeFicha {Old, New}
    
    abstract public boolean isPageUntil(int maxSecondsWait);
    abstract public boolean isFichaArticuloUntil(String refArticulo, int maxSecondsToWait);
    abstract public void clickAnadirBolsaButtonAndWait() throws Exception;
    abstract public void selectAnadirAFavoritosButton() throws Exception;
    abstract public void selectRemoveFromFavoritosButton() throws Exception;
    abstract public boolean isVisibleDivAnadiendoAFavoritosUntil(int maxSecondsToWait);
    abstract public boolean isInvisibleDivAnadiendoAFavoritosUntil(int maxSecondsToWait);
    abstract public boolean isVisibleButtonElimFavoritos();
    abstract public boolean isVisibleButtonAnadirFavoritos();
    abstract public String getNameLinkBuscarEnTienda();
    abstract public void selectBuscarEnTiendaLink() throws Exception;
    abstract public boolean isVisibleSlider(Slider typeSlider);
    abstract public int getNumArtVisiblesSlider(Slider typeSlider);
    abstract public boolean isModalNoStockVisible(int maxSecondsToWait);
    
    public static SecDataProduct secDataProduct; //Name, color, talla section
    public static SecFitFinder secFitFinder; //Guía de tallas v.Fit Finder
    WebDriver driver;
    Channel channel;
    AppEcom appE;
    TypeFicha typeFicha;
    
    public PageFicha() {};
    
    //Constructor estático
    public static PageFicha newInstance(Channel channel, AppEcom appE, WebDriver driver) {
    	PageFicha pageFicha;
        if (appE==AppEcom.outlet || channel==Channel.movil_web) {
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
    
    public ArticuloScreen getArticuloObject() {
        return (secDataProduct.getArticuloObject(channel, appE, typeFicha, driver));
    }
    
    public boolean isTallaUnica() {
        return (secDataProduct.isTallaUnica(typeFicha, driver));
    }
    
    public String getTallaAlfSelected() {
        return (SecDataProduct.getTallaAlfSelected(typeFicha, appE, driver));
    }
    
    public String getTallaNumSelected() {
        return (secDataProduct.getTallaNumSelected(typeFicha, appE, driver));
    }
    
    public void selectTallaByValue(String tallaCodNum) {
        secDataProduct.selectTallaByValue(tallaCodNum, typeFicha, driver);
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
