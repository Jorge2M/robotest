package com.mng.robotest.test80.mango.test.pageobject.shop.ficha;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.SecBolsaButtonAndLinksNew.ActionFavButton;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.SecBolsaButtonAndLinksNew.LinksAfterBolsa;


@SuppressWarnings({"static-access"})
/**
 * Clase que define la automatización de las diferentes funcionalidades de la página de "GALERÍA DE PRODUCTOS"
 * @author jorge.munoz
 */
public class PageFichaArtNew extends PageFicha {

    public static SecBolsaButtonAndLinksNew secBolsaButtonAndLinks; //Button bolsa and UnderLinks "Detalle Producto"...
    public static ModEnvioYdevolNew modEnvioYdevolNew; //Modal que aparece al seleccionar el link "Envío y devoluciones"
    public static SecFotosNew secFotos; //Foto central y líneas inferiores
    public static SecDetalleProductNew secProductInfo; //Apartado con la descripción y la composición/lavado
    public static SecSlidersNew secSliders; //Completa Tu Look, Elegido para ti, Lo último que has visto
    public static ModNoStock modNoStock; //Modal que aparece cuando no hay stock
    public static SecModalPersonalizacion secModalPersonalizacion; //Modal para la personalización de bordados
    
    private static final String XPathHtmlFicha = "//html[@class[contains(.,'ficha')]]";
    
    private PageFichaArtNew(Channel channel, WebDriver driver) {
        super(driver);
        this.channel = channel;
        this.typeFicha = TypeFicha.New;
    }
    
    //Static constructor
    public static PageFichaArtNew getNewInstance(Channel channel, WebDriver driver) {
    	return (new PageFichaArtNew(channel, driver));
    }
    
    private String getXPathIsPage(String referencia) {
        return XPathHtmlFicha + secDataProduct.getXPathLinReferencia(referencia, channel);
    }
    
    @Override
    public boolean isPageUntil(int maxSeconds) {
    	return (state(Present, By.xpath(XPathHtmlFicha)).wait(maxSeconds).check() &&
                secDataProduct.secSelTallasNew.isVisibleSelectorTallasUntil(maxSeconds, driver));
    }
    
    @Override
    public boolean isFichaArticuloUntil(String refArticulo, int maxSeconds) {
        String refSinColor = refArticulo.substring(0,8); 
        String xpathFichaRef = getXPathIsPage(refSinColor);
        return (state(Visible, By.xpath(xpathFichaRef)).wait(maxSeconds).check());
    }
    
    @Override
    public void clickAnadirBolsaButtonAndWait() {
    	secBolsaButtonAndLinks.clickAnadirBolsaButtonAndWait(this.driver);
    }
    
    @Override
    public void selectAnadirAFavoritosButton() {
        secBolsaButtonAndLinks.selectFavoritosButton(ActionFavButton.Add, driver);
    }
    
    @Override
    public void selectRemoveFromFavoritosButton() {
        secBolsaButtonAndLinks.selectFavoritosButton(ActionFavButton.Remove, driver);
    }    
    
    @Override
    public boolean isVisibleDivAnadiendoAFavoritosUntil(int maxSecondsToWait) {    
        return (secBolsaButtonAndLinks.isVisibleDivAnadiendoAFavoritosUntil(maxSecondsToWait, driver));
    }
    
    @Override
    public boolean isInvisibleDivAnadiendoAFavoritosUntil(int maxSecondsToWait) {    
        return (secBolsaButtonAndLinks.isInvisibleDivAnadiendoAFavoritosUntil(maxSecondsToWait, driver));        
    }
    
    @Override
    public boolean isVisibleButtonElimFavoritos() {
        return (secBolsaButtonAndLinks.isVisibleButtonFavoritos(ActionFavButton.Remove, this.driver));
    }
    
    @Override
    public boolean isVisibleButtonAnadirFavoritos() {
        return (secBolsaButtonAndLinks.isVisibleButtonFavoritos(ActionFavButton.Add, this.driver));
    }    
    
    @Override
    public String getNameLinkBuscarEnTienda() {
        return "Link Disponibilidad en tienda";
    }
    
    @Override
    public void selectBuscarEnTiendaLink() {
    	secBolsaButtonAndLinks.clickLinkAndWaitLoad(LinksAfterBolsa.DisponibilidadTienda, this.driver);
    }
    
    @Override
    public boolean isVisibleSlider(Slider typeSlider) {
        return (secSliders.isVisible(typeSlider, this.driver));
    }
    
    @Override
    public int getNumArtVisiblesSlider(Slider typeSlider) {
        return (secSliders.getNumVisibleArticles(typeSlider, driver));
    }
    
    @Override
	public boolean isModalNoStockVisible(int maxSecondsToWait) {
        return modNoStock.isModalNoStockVisibleFichaNew(maxSecondsToWait, driver);
	}
}
