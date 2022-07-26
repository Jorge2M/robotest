package com.mng.robotest.domains.ficha.pageobjects;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.conftestmaker.AppEcom;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

@SuppressWarnings({"static-access"})
/**
 * Clase que define la automatización de las diferentes funcionalidades de la página de "GALERÍA DE PRODUCTOS"
 * @author jorge.munoz
 */
public class PageFichaArtOld extends PageFicha {

	public static SecBreadcrumbFichaOld secBreadcrumbAndNextOld;
	public static SecProductDescrOld secProductDescr;
	public static SecSlidersOld secSliders; //Completa Tu Look, Elegido para ti, Lo último que has visto
	
	private static final String XPATH_CONTAINER_FICHA = "//*[@class='product-detail']";
	
	private static final String XPATH_ALTA_BOLSA_BUTTON ="//*[@id='productFormAdd']";
	private static final String XPATH_ANADIR_A_FAVORITOS_BUTTON = "//button[@id='productFormFavorites' and @data-fav='false']";
	private static final String XPATH_ELIMINAR_DE_FAVORITOS_BUTTON = "//button[@id='productFormFavorites' and @data-fav='true']";
	private static final String XPATH_DIV_ANADIENDO_FAVORITOS = "//div[@class[contains(.,'product-banner')]]";
	private static final String XPATH_BUSCAR_ENN_TIENDA_BUTTON = "//button[@class[contains(.,'garment-finder')]]";
	private static final String XPATH_GUIA_DE_TALLAS_LINK = "//span[@id='productFormSizesGuide']";
	
	private static final String XPATH_DIV_IMG_CENTRAL_DIV_DESKTOP = "//div[@class='main-img']";
	private static final String XPATH_DIV_IMG_CENTRAL_DIV_DEVICE = 
			"//div[@class[contains(.,'product-img-container')] and @class[contains(.,'slide-active')]]";
	private static final String XPATH_ASPA_ZOOM_IMAGE_CENTRAL = "//div[@class='zoom-image-close']";
	
	private static final String XPATH_FICHA_CON_ZOOM_DESKTOP = "//div[@class[contains(.,'zoom-out')]]";
	private static final String XPATH_FICHA_CON_ZOOM_DEVICE = "//div[@class[contains(.,'zoom-image-container')]]";
	
	private static final String XPATH_ULTIMOS_PRODUCTOS_SECTION = "//*[@id='ultimos_productos']";
	private static final String XPATH_MODAL_NO_STOCK = "//div[@class='modalNoStock show']";
	private static final String XPATH_IMAGEN_CARRUSEL_IZQ = "//div[@class='carousel-img-container']//img[@class[contains(.,'carousel-img')]]";
	
	private PageFichaArtOld(Channel channel, AppEcom app, WebDriver driver) {
		super(TypeFicha.OLD, channel, app, driver);
	}
	
	public static PageFichaArtOld getNewInstance(Channel channel, AppEcom app, WebDriver driver) {
		return (new PageFichaArtOld(channel, app, driver));
	}
	
	private String getXPathIsPage(String referencia, Channel channel) {
		return XPATH_CONTAINER_FICHA + secDataProduct.getXPathLinReferencia(referencia);
	}
	
	private String getXPathDivImgCentralDiv() {
		if (channel==Channel.mobile) {
			return XPATH_DIV_IMG_CENTRAL_DIV_DEVICE;
		}
		return XPATH_DIV_IMG_CENTRAL_DIV_DESKTOP;
	}
	private String getXPathImagenCentral() {
		String xpathDiv = getXPathDivImgCentralDiv();
		return xpathDiv + "/img";
	}
	
	private String getXPathFichaConZoom() {
		if (channel.isDevice()) {
			return XPATH_FICHA_CON_ZOOM_DEVICE;
		}
		return XPATH_FICHA_CON_ZOOM_DESKTOP;
	}
	private String getXPathImagenCentralConZoom() {
		String xpathDiv = getXPathFichaConZoom();
		return xpathDiv + "//img";
	}
	
	@Override
	public boolean isPageUntil(int maxSeconds) {
		return (
			state(Present, By.xpath(XPATH_CONTAINER_FICHA)).wait(maxSeconds).check() &&
			secDataProduct.getSecSelTallas().isVisibleSelectorTallasUntil(maxSeconds)
		);
	}
	
	@Override
	public boolean isFichaArticuloUntil(String refArticulo, int maxSeconds) {
		String refSinColor = refArticulo.substring(0,8); 
		String xpathFichaRef = getXPathIsPage(refSinColor, channel);
		return (state(Present, By.xpath(xpathFichaRef)).wait(maxSeconds).check());
	}

	@Override
	public void clickAnadirBolsaButtonAndWait() {
		click(By.xpath(XPATH_ALTA_BOLSA_BUTTON)).type(javascript).exec();
	}

	@Override
	public void selectAnadirAFavoritosButton() {
		click(By.xpath(XPATH_ANADIR_A_FAVORITOS_BUTTON)).exec();
	}

	@Override
	public void selectRemoveFromFavoritosButton() {
		click(By.xpath(XPATH_ELIMINAR_DE_FAVORITOS_BUTTON)).exec();
	}
	
	@Override
	public boolean isVisibleDivAnadiendoAFavoritosUntil(int maxSeconds) {
		return (state(Visible, By.xpath(XPATH_DIV_ANADIENDO_FAVORITOS)).wait(maxSeconds).check());
	}
	
	@Override
	public boolean isInvisibleDivAnadiendoAFavoritosUntil(int maxSeconds) {
		return (state(Invisible, By.xpath(XPATH_DIV_ANADIENDO_FAVORITOS))
				.wait(maxSeconds).check());
	}	
	
	@Override
	public boolean isVisibleButtonElimFavoritos() {
		return (state(Visible, By.xpath(XPATH_ELIMINAR_DE_FAVORITOS_BUTTON)).wait(2).check());
	}
	
	@Override
	public boolean isVisibleButtonAnadirFavoritos() {
		return (state(Visible, By.xpath(XPATH_ANADIR_A_FAVORITOS_BUTTON)).wait(2).check());
	} 
	
	@Override
	public String getNameLinkBuscarEnTienda() {
		return "Botón Buscar en tienda";
	}

	@Override
	public boolean isVisibleBuscarEnTiendaLink() {
		return state(State.Visible, By.xpath(XPATH_BUSCAR_ENN_TIENDA_BUTTON)).check();
	}

	@Override
	public void selectBuscarEnTiendaLink() {
		click(By.xpath(XPATH_BUSCAR_ENN_TIENDA_BUTTON)).exec();
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
		return (state(Visible, By.xpath(XPATH_MODAL_NO_STOCK)).wait(maxSeconds).check());
	}

	public boolean isVisibleUltimosProductosSection() {
		return (state(Visible, By.xpath(XPATH_ULTIMOS_PRODUCTOS_SECTION)).check());
	}

	public void clickImagenFichaCentral() {
		String xpathImg = getXPathDivImgCentralDiv();
		click(By.xpath(xpathImg)).exec();
	}
	
	public void closeZoomImageCentralDevice() {
		click(By.xpath(XPATH_ASPA_ZOOM_IMAGE_CENTRAL)).exec();
	}

	public int getNumImgsCarruselIzq() {
		return (driver.findElements(By.xpath(XPATH_IMAGEN_CARRUSEL_IZQ)).size());
	}
	
	public String getSrcImgCarruselIzq(int numImagen) {
		String srcImagen = "";
		String xpathImagenX = "(" + XPATH_IMAGEN_CARRUSEL_IZQ + ")[" + numImagen + "]";
		if (state(Present, By.xpath(xpathImagenX)).check()) {
			String srcImagenO = driver.findElement(By.xpath(xpathImagenX)).getAttribute("src"); 
			srcImagen = srcImagenO.substring(srcImagenO.lastIndexOf("/"));			
		}
		return srcImagen;
	}
	
	public String getSrcImagenCentral() {
		By byImgCentral = By.xpath(getXPathImagenCentral());
		if (state(Present, byImgCentral).check()) {
			String srcImagenO = driver.findElement(byImgCentral).getAttribute("src");
			return srcImagenO.substring(srcImagenO.lastIndexOf("/"));
		}
		return "";
	}
	
	public String getSrcImagenCentralConZoom() {
		By byImg = By.xpath(getXPathImagenCentralConZoom());
		if (state(Present, byImg).check()) {
			String srcImagenO = driver.findElement(byImg).getAttribute("src");
			return srcImagenO.substring(srcImagenO.lastIndexOf("/"));
		}
		return "";
	}	
	
	public void clickImgCarruselIzq(int numImagen) {
		String xpathImagenX = "(" + XPATH_IMAGEN_CARRUSEL_IZQ + ")[" + numImagen + "]";
		moveToElement(By.xpath(xpathImagenX), driver);
		click(By.xpath(xpathImagenX)).exec();
	}
	
	public boolean srcImagenCentralCorrespondsToImgCarrusel(String srcImgCarrusel) {
		String srcImgCentral = getSrcImagenCentral();
		String nameFileImgCentral = getNameFileImgArticleWithoutExt(srcImgCentral);
		String nameFileImgCarrusel = getNameFileImgArticleWithoutExt(srcImgCarrusel);
		return (nameFileImgCentral.compareTo(nameFileImgCarrusel)==0); 
	}
	
	private String getNameFileImgArticleWithoutExt(String srcImgCarrusel) {
		Pattern pattern = Pattern.compile("(.*?).jpg");
		Matcher matcher = pattern.matcher(srcImgCarrusel);
		if (matcher.find()) {
			return matcher.group(1);
		}
		return "";
	}
	
	public boolean srcImagenCentralConZoomContains(String srcImagen) {
		String srcImagenCentralConZoom = getSrcImagenCentralConZoom();
		if ("".compareTo(srcImagen)!=0 &&
			"".compareTo(srcImagenCentralConZoom)!=0) {
			String nameOld = getNameFileImgArticleWithoutExt(srcImagen);
			String nameNew = getNameFileImgArticleWithoutExt(srcImagenCentralConZoom);
			return nameOld.compareTo(nameNew)==0;
		}
		return false;
	}	
	
	public boolean isVisibleFichaConZoom() {
		By byDivZoom = By.xpath(getXPathDivImgCentralDiv());
		return (state(Visible, byDivZoom).check());
	}

	public void selectGuiaDeTallasLink() {
		click(By.xpath(XPATH_GUIA_DE_TALLAS_LINK)).exec();
	}

	public boolean isPresentPageUntil(int maxSeconds) {
		return (state(Present, By.xpath(XPATH_CONTAINER_FICHA)).wait(maxSeconds).check());
	}
}
