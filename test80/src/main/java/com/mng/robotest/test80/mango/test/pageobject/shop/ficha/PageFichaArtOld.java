package com.mng.robotest.test80.mango.test.pageobject.shop.ficha;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.service.webdriver.wrapper.TypeOfClick;
import com.mng.testmaker.conf.Channel;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;

@SuppressWarnings({"static-access"})
/**
 * Clase que define la automatización de las diferentes funcionalidades de la página de "GALERÍA DE PRODUCTOS"
 * @author jorge.munoz
 */
public class PageFichaArtOld extends PageFicha {

    public static SecBreadcrumbFichaOld secBreadcrumbAndNextOld;
    public static SecProductDescrOld secProductDescr;
    public static SecSlidersOld secSliders; //Completa Tu Look, Elegido para ti, Lo último que has visto
    
    //private static final String XPathHtmlFicha = "//html[@class[contains(.,'ficha')]]";
    private static final String XPathContainerFicha = "//*[@class='product-detail']";
    
    //TODO En breve subirá un desarrollo de Juan Mesa que rompe todos los test que añadan a la bolsa algo -> Se debería cambiar por el xpath //*[@id='buttonAddCart'] 
    private static final String XPathAltaBolsaButton ="//*[@id='productFormAdd']";
    private static final String XPathAnadirAFavoritosButton = "//button[@id='productFormFavorites' and @data-fav='false']";
    private static final String XPathEliminarDeFavoritosButton = "//button[@id='productFormFavorites' and @data-fav='true']";
    private static final String XPathDivAnadiendoFavoritos = "//div[@class[contains(.,'product-banner')]]";
    private static final String XPathBuscarEnTiendaButton = "//button[@class[contains(.,'garment-finder')]]";
    private static final String XPathGuiaDeTallasLink = "//span[@id='productFormSizesGuide']";
    private static final String XPathDivImgCentralDiv = "//div[@class='main-img']";
    private static final String XPathImagenCentral = XPathDivImgCentralDiv + "/img";
    private static final String XPathFichaConZoom = "//div[@class[contains(.,'zoom-out')]]";
    private static final String XPathImagenCentralConZoom = XPathFichaConZoom + "//img";
    private static final String XPathUltimosProductosSection = "//*[@id='ultimos_productos']";
    private static final String XPathModalNoStock = "//div[@class='modalNoStock show']";
    private static final String XPathImagenCarruselIzq = "//div[@class='carousel-img-container']//img[@class[contains(.,'carousel-img')]]";
    
    private PageFichaArtOld(Channel channel, WebDriver driver) {
        super(driver);
        this.channel = channel;
        this.typeFicha = TypeFicha.Old;
    }
    
    //Static constructor
    public static PageFichaArtOld getNewInstance(Channel channel, WebDriver driver) {
    	return (new PageFichaArtOld(channel, driver));
    }
    
    private String getXPathIsPage(String referencia, Channel channel) {
        return XPathContainerFicha + secDataProduct.getXPathLinReferencia(referencia, channel);
    }
    
    @Override
    public boolean isPageUntil(int maxSeconds) {
        return (
        	state(Present, By.xpath(XPathContainerFicha)).wait(maxSeconds).check() &&
            secDataProduct.secSelTallasOld.isVisibleSelectorTallasUntil(maxSeconds, driver)
        );
    }
    
    @Override
    public boolean isFichaArticuloUntil(String refArticulo, int maxSeconds) {
        String refSinColor = refArticulo.substring(0,8); 
        String xpathFichaRef = getXPathIsPage(refSinColor, channel);
        return (state(Present, By.xpath(xpathFichaRef)).wait(maxSeconds).check());
    }
    
    @Override
    public void clickAnadirBolsaButtonAndWait() throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathAltaBolsaButton), TypeOfClick.javascript);
    }
    
    @Override
    public void selectAnadirAFavoritosButton() throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathAnadirAFavoritosButton));
    }
    
    @Override
    public void selectRemoveFromFavoritosButton() throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathEliminarDeFavoritosButton));
    }
    
    @Override
    public boolean isVisibleDivAnadiendoAFavoritosUntil(int maxSeconds) {
    	return (state(Visible, By.xpath(XPathDivAnadiendoFavoritos)).wait(maxSeconds).check());
    }
    
    @Override
    public boolean isInvisibleDivAnadiendoAFavoritosUntil(int maxSeconds) {
    	return (state(Invisible, By.xpath(XPathDivAnadiendoFavoritos))
    			.wait(maxSeconds).check());
    }    
    
    @Override
    public boolean isVisibleButtonElimFavoritos() {
    	return (state(Visible, By.xpath(XPathEliminarDeFavoritosButton)).check());
    }
    
    @Override
    public boolean isVisibleButtonAnadirFavoritos() {
    	return (state(Visible, By.xpath(XPathAnadirAFavoritosButton)).check());
    } 
    
    @Override
    public String getNameLinkBuscarEnTienda() {
    	return "Botón Buscar en tienda";
    }
    
    @Override
    public void selectBuscarEnTiendaLink() throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathBuscarEnTiendaButton));
    }
    
    @Override
    public boolean isVisibleSlider(Slider typeSlider) {
    	return (secSliders.isVisible(typeSlider, this.driver));
    }
    
    @Override
    public int getNumArtVisiblesSlider(Slider typeSlider) {
    	return (secSliders.getNumVisibleArticles(typeSlider, this.driver));
    }
    
    @Override
	public boolean isModalNoStockVisible(int maxSeconds) {
    	return (state(Visible, By.xpath(XPathModalNoStock)).wait(maxSeconds).check());
	}

    public boolean isVisibleUltimosProductosSection() {
    	return (state(Visible, By.xpath(XPathUltimosProductosSection)).check());
    }
    
    public void clickImagenFichaCentral() throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathDivImgCentralDiv));
    }


    public int getNumImgsCarruselIzq() {
        return (driver.findElements(By.xpath(XPathImagenCarruselIzq)).size());
    }
    
    public String getSrcImgCarruselIzq(int numImagen) {
        String srcImagen = "";
        String xpathImagenX = "(" + XPathImagenCarruselIzq + ")[" + numImagen + "]";
        if (state(Present, By.xpath(xpathImagenX)).check()) {
            String srcImagenO = driver.findElement(By.xpath(xpathImagenX)).getAttribute("src"); 
            srcImagen = srcImagenO.substring(srcImagenO.lastIndexOf("/"));            
        }
        return srcImagen;
    }
    
    public String getSrcImagenCentral() {
        String srcImagen = "";
        if (state(Present, By.xpath(XPathImagenCentral)).check()) {
            String srcImagenO = driver.findElement(By.xpath(XPathImagenCentral)).getAttribute("src");
            srcImagen = srcImagenO.substring(srcImagenO.lastIndexOf("/"));
        }
        return srcImagen;
    }
    
    public String getSrcImagenCentralConZoom() {
        String srcImagen = "";
        if (state(Present, By.xpath(XPathImagenCentralConZoom)).check()) {
            String srcImagenO = driver.findElement(By.xpath(XPathImagenCentralConZoom)).getAttribute("src");
            srcImagen = srcImagenO.substring(srcImagenO.lastIndexOf("/"));
        }
        return srcImagen;
    }    
    
    public void clickImgCarruselIzq(int numImagen) throws Exception {
        String xpathImagenX = "(" + XPathImagenCarruselIzq + ")[" + numImagen + "]";
        moveToElement(By.xpath(xpathImagenX), driver);
        clickAndWaitLoad(driver, By.xpath(xpathImagenX));
    }
    
    public boolean srcImagenCentralCorrespondsToImgCarrusel(String srcImgCarrusel) {
        String srcImgCentral = getSrcImagenCentral();
        String nameFileImgCentral = getNameFileImgArticleWithoutExt(srcImgCentral);
        String nameFileImgCarrusel = getNameFileImgArticleWithoutExt(srcImgCarrusel);
        return (nameFileImgCentral.compareTo(nameFileImgCarrusel)==0); 
    }
    
    private static String getNameFileImgArticleWithoutExt(String srcImgCarrusel) {
        Pattern pattern = Pattern.compile("(.*?).jpg");
        Matcher matcher = pattern.matcher(srcImgCarrusel);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }
    
    public boolean srcImagenCentralConZoomContains(String srcImagen) {
        String srcImagenCentralConZoom = getSrcImagenCentralConZoom();
        return (
        	"".compareTo(srcImagen)!=0 &&
            "".compareTo(srcImagenCentralConZoom)!=0 &&
            srcImagenCentralConZoom.contains(srcImagen));
    }    
    
    public boolean isVisibleFichaConZoom() {
    	return (state(Visible, By.xpath(XPathFichaConZoom)).check());
    }
    
    public void selectGuiaDeTallasLink() throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathGuiaDeTallasLink));
    }
    
    public boolean isPresentPageUntil(int maxSeconds) {
    	return (state(Present, By.xpath(XPathContainerFicha)).wait(maxSeconds).check());
    }
}
