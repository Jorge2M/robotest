package com.mng.robotest.test80.mango.test.pageobject.shop.ficha;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;

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
    public static PageFicha newInstance(AppEcom appE, Channel channel, WebDriver driver) {
        if (appE==AppEcom.outlet || channel==Channel.movil_web) {
        	return (PageFichaArtOld.getNewInstance(channel, driver));
        } else {
        	return (PageFichaArtNew.getNewInstance(channel, driver));
        }
    }
    
    public static PageFicha newInstanceFichaNew(Channel channel, WebDriver driver) {
        return (PageFichaArtNew.getNewInstance(channel, driver));
    }

    public TypeFicha getTypeFicha() {
    	return this.typeFicha;
    }
    
    public ArticuloScreen getArticuloObject() {
        return (secDataProduct.getArticuloObject(this.channel, this.typeFicha, this.driver));
    }
    
    public boolean isTallaUnica() {
        return (secDataProduct.isTallaUnica(this.typeFicha, this.driver));
    }
    
    public String getTallaAlfSelected() {
        return (SecDataProduct.getTallaAlfSelected(this.typeFicha, this.driver));
    }
    
    public String getTallaNumSelected() {
        return (secDataProduct.getTallaNumSelected(this.typeFicha, this.driver));
    }
    
    public void selectTallaByValue(String tallaCodNum) {
        secDataProduct.selectTallaByValue(tallaCodNum, this.typeFicha, this.driver);
    }
    
    public void selectTallaByIndex(int posicion) {
        secDataProduct.selectTallaByIndex(posicion, this.typeFicha, this.driver);
    }
    
    public void selectFirstTallaAvailable() {
        secDataProduct.selectFirstTallaAvailable(this.typeFicha, this.driver);
    }
    
    public String getTallaAlf(int posicion) {
    	return (secDataProduct.getTallaAlf(this.typeFicha, posicion, this.driver));
    }
    
    public String getTallaCodNum(int posicion) {
    	return (secDataProduct.getTallaCodNum(this.typeFicha, posicion, this.driver));
    }
    
    public int getNumOptionsTallasNoDisponibles() {
        return (secDataProduct.getNumOptionsTallasNoDisponibles(this.typeFicha, this.driver));
    }
    
    public int getNumOptionsTallas() {
        return (secDataProduct.getNumOptionsTallas(this.typeFicha, this.driver));
    }
    
    public boolean isFichaAccesorio() {
        return (this.driver.getCurrentUrl().contains("accesorio"));
    }
}
