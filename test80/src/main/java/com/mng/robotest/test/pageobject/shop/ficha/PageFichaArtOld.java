package com.mng.robotest.test.pageobject.shop.ficha;

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
	
	//private static final String XPathHtmlFicha = "//html[@class[contains(.,'ficha')]]";
	private static final String XPathContainerFicha = "//*[@class='product-detail']";
	
	//TODO En breve subirá un desarrollo de Juan Mesa que rompe todos los test que añadan a la bolsa algo -> Se debería cambiar por el xpath //*[@id='buttonAddCart'] 
	private static final String XPathAltaBolsaButton ="//*[@id='productFormAdd']";
	private static final String XPathAnadirAFavoritosButton = "//button[@id='productFormFavorites' and @data-fav='false']";
	private static final String XPathEliminarDeFavoritosButton = "//button[@id='productFormFavorites' and @data-fav='true']";
	private static final String XPathDivAnadiendoFavoritos = "//div[@class[contains(.,'product-banner')]]";
	private static final String XPathBuscarEnTiendaButton = "//button[@class[contains(.,'garment-finder')]]";
	private static final String XPathGuiaDeTallasLink = "//span[@id='productFormSizesGuide']";
	
	private static final String XPathDivImgCentralDivDesktop = "//div[@class='main-img']";
	private static final String XPathDivImgCentralDivDevice = 
			"//div[@class[contains(.,'product-img-container')] and @class[contains(.,'slide-active')]]";
	private static final String XPathAspaZoomImageCentral = "//div[@class='zoom-image-close']";
	
	private static final String XPathFichaConZoomDesktop = "//div[@class[contains(.,'zoom-out')]]";
	private static final String XPathFichaConZoomDevice = "//div[@class[contains(.,'zoom-image-container')]]";
	
	private static final String XPathUltimosProductosSection = "//*[@id='ultimos_productos']";
	private static final String XPathModalNoStock = "//div[@class='modalNoStock show']";
	private static final String XPathImagenCarruselIzq = "//div[@class='carousel-img-container']//img[@class[contains(.,'carousel-img')]]";
	
	private PageFichaArtOld(Channel channel, AppEcom app, WebDriver driver) {
		super(TypeFicha.Old, channel, app, driver);
	}
	
	public static PageFichaArtOld getNewInstance(Channel channel, AppEcom app, WebDriver driver) {
		return (new PageFichaArtOld(channel, app, driver));
	}
	
	private String getXPathIsPage(String referencia, Channel channel) {
		return XPathContainerFicha + secDataProduct.getXPathLinReferencia(referencia);
	}
	
	private String getXPathDivImgCentralDiv() {
		if (channel==Channel.mobile) {
			return XPathDivImgCentralDivDevice;
		}
		return XPathDivImgCentralDivDesktop;
	}
	private String getXPathImagenCentral() {
		String xpathDiv = getXPathDivImgCentralDiv();
		return xpathDiv + "/img";
	}
	
	private String getXPathFichaConZoom() {
		if (channel.isDevice()) {
			return XPathFichaConZoomDevice;
		}
		return XPathFichaConZoomDesktop;
	}
	private String getXPathImagenCentralConZoom() {
		String xpathDiv = getXPathFichaConZoom();
		return xpathDiv + "//img";
	}
	
	@Override
	public boolean isPageUntil(int maxSeconds) {
		return (
			state(Present, By.xpath(XPathContainerFicha)).wait(maxSeconds).check() &&
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
		click(By.xpath(XPathAltaBolsaButton)).type(javascript).exec();
	}

	@Override
	public void selectAnadirAFavoritosButton() {
		click(By.xpath(XPathAnadirAFavoritosButton)).exec();
	}

	@Override
	public void selectRemoveFromFavoritosButton() {
		click(By.xpath(XPathEliminarDeFavoritosButton)).exec();
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
		return (state(Visible, By.xpath(XPathEliminarDeFavoritosButton)).wait(2).check());
	}
	
	@Override
	public boolean isVisibleButtonAnadirFavoritos() {
		return (state(Visible, By.xpath(XPathAnadirAFavoritosButton)).wait(2).check());
	} 
	
	@Override
	public String getNameLinkBuscarEnTienda() {
		return "Botón Buscar en tienda";
	}

	@Override
	public boolean isVisibleBuscarEnTiendaLink() {
		return state(State.Visible, By.xpath(XPathBuscarEnTiendaButton)).check();
	}

	@Override
	public void selectBuscarEnTiendaLink() {
		click(By.xpath(XPathBuscarEnTiendaButton)).exec();
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

	public void clickImagenFichaCentral() {
		String xpathImg = getXPathDivImgCentralDiv();
		click(By.xpath(xpathImg)).exec();
	}
	
	public void closeZoomImageCentralDevice() {
		click(By.xpath(XPathAspaZoomImageCentral)).exec();
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
		String xpathImagenX = "(" + XPathImagenCarruselIzq + ")[" + numImagen + "]";
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
		click(By.xpath(XPathGuiaDeTallasLink)).exec();
	}

	public boolean isPresentPageUntil(int maxSeconds) {
		return (state(Present, By.xpath(XPathContainerFicha)).wait(maxSeconds).check());
	}
}
