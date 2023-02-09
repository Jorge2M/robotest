package com.mng.robotest.domains.ficha.pageobjects;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageFichaDevice extends PageFicha {

	private final SecProductDescrOld secProductDescr = new SecProductDescrOld();
	private final SecSlidersOld secSliders = new SecSlidersOld();

	private static final String XPATH_CONTAINER_FICHA = "//*[@class='product-detail']";

	private static final String XPATH_ALTA_BOLSA_BUTTON ="//*[@id='productFormAdd']";

	private static final String XPATH_BUTTON_FAVORITOS = "//*[@data-testid='button-icon']";
	private static final String XPATH_BUTTON_FAVORITOS_FOR_ADD = XPATH_BUTTON_FAVORITOS + "//self::*[@class[contains(.,'outline')]]";
	private static final String XPATH_BUTTON_FAVORITOS_FOR_REMOVE = XPATH_BUTTON_FAVORITOS + "//self::*[@class[contains(.,'fill')]]";
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

	private String getXPathIsPage(String referencia) {
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
	public boolean isPageUntil(int seconds) {
		return (
				state(Present, XPATH_CONTAINER_FICHA).wait(seconds).check() &&
						//secDataProduct.getSecSelTallas().isVisibleSelectorTallasUntil(seconds)
						secDataProduct.getSecSelTallas().isSectionUntil(seconds)
		);
	}

	@Override
	public boolean isFichaArticuloUntil(String refArticulo, int seconds) {
		String refSinColor = refArticulo.substring(0,8);
		String xpathFichaRef = getXPathIsPage(refSinColor);
		return state(Present, xpathFichaRef).wait(seconds).check();
	}

	@Override
	public void clickAnadirBolsaButtonAndWait() {
		click(XPATH_ALTA_BOLSA_BUTTON).waitLink(2).type(javascript).exec();
	}

	@Override
	public void selectAnadirAFavoritosButton() {
		click(XPATH_BUTTON_FAVORITOS_FOR_ADD).exec();
	}

	@Override
	public void selectRemoveFromFavoritosButton() {
		click(XPATH_BUTTON_FAVORITOS_FOR_REMOVE).exec();
	}

	@Override
	public boolean isVisibleDivAnadiendoAFavoritosUntil(int seconds) {
		return state(Visible, XPATH_DIV_ANADIENDO_FAVORITOS).wait(seconds).check();
	}

	@Override
	public boolean isInvisibleDivAnadiendoAFavoritosUntil(int seconds) {
		return state(Invisible, XPATH_DIV_ANADIENDO_FAVORITOS).wait(seconds).check();
	}

	@Override
	public boolean isVisibleButtonElimFavoritos(int seconds) {
		return state(Visible, XPATH_BUTTON_FAVORITOS_FOR_REMOVE).wait(seconds).check();
	}

	@Override
	public boolean isVisibleButtonAnadirFavoritos(int seconds) {
		return state(Visible, XPATH_BUTTON_FAVORITOS_FOR_ADD).wait(seconds).check();
	}

	@Override
	public String getNameLinkBuscarEnTienda() {
		return "Botón Buscar en tienda";
	}

	@Override
	public boolean isVisibleBuscarEnTiendaLink() {
		return state(State.Visible, XPATH_BUSCAR_ENN_TIENDA_BUTTON).check();
	}

	@Override
	public void selectBuscarEnTiendaLink() {
		click(XPATH_BUSCAR_ENN_TIENDA_BUTTON).exec();
	}

	@Override
	public boolean isVisibleSlider(Slider typeSlider) {
		return secSliders.isVisible(typeSlider);
	}

	@Override
	public int getNumArtVisiblesSlider(Slider typeSlider) {
		return secSliders.getNumVisibleArticles(typeSlider);
	}

	@Override
	public boolean isModalNoStockVisible(int seconds) {
		return state(Visible, XPATH_MODAL_NO_STOCK).wait(seconds).check();
	}

	public boolean isVisibleUltimosProductosSection() {
		return state(Visible, XPATH_ULTIMOS_PRODUCTOS_SECTION).check();
	}

	public void clickImagenFichaCentral() {
		click(getXPathDivImgCentralDiv()).exec();
	}

	public void closeZoomImageCentralDevice() {
		click(XPATH_ASPA_ZOOM_IMAGE_CENTRAL).exec();
	}

	public int getNumImgsCarruselIzq() {
		return getElements(XPATH_IMAGEN_CARRUSEL_IZQ).size();
	}

	public String getSrcImgCarruselIzq(int numImagen) {
		String srcImagen = "";
		String xpathImagenX = "(" + XPATH_IMAGEN_CARRUSEL_IZQ + ")[" + numImagen + "]";
		if (state(Present, xpathImagenX).check()) {
			String srcImagenO = getElement(xpathImagenX).getAttribute("src");
			srcImagen = srcImagenO.substring(srcImagenO.lastIndexOf("/"));
		}
		return srcImagen;
	}

	public String getSrcImagenCentral() {
		String xpathImg = getXPathImagenCentral();
		if (state(Present, xpathImg).check()) {
			String srcImagenO = getElement(xpathImg).getAttribute("src");
			return srcImagenO.substring(srcImagenO.lastIndexOf("/"));
		}
		return "";
	}

	public String getSrcImagenCentralConZoom() {
		String xpathImg = getXPathImagenCentralConZoom();
		if (state(Present, xpathImg).check()) {
			String srcImagenO = getElement(xpathImg).getAttribute("src");
			return srcImagenO.substring(srcImagenO.lastIndexOf("/"));
		}
		return "";
	}

	public void clickImgCarruselIzq(int numImagen) {
		String xpathImagenX = "(" + XPATH_IMAGEN_CARRUSEL_IZQ + ")[" + numImagen + "]";
		moveToElement(xpathImagenX);
		click(xpathImagenX).exec();
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
		return (state(Visible, getXPathDivImgCentralDiv()).check());
	}

	public void selectGuiaDeTallasLink() {
		click(XPATH_GUIA_DE_TALLAS_LINK).exec();
	}

	public boolean isPresentPageUntil(int seconds) {
		return (state(Present, XPATH_CONTAINER_FICHA).wait(seconds).check());
	}
}
