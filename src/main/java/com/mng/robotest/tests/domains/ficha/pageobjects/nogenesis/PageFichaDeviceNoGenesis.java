package com.mng.robotest.tests.domains.ficha.pageobjects.nogenesis;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageFichaDeviceNoGenesis extends PageFichaNoGenesis {

	private static final String XP_CONTAINER_FICHA = "//*[@class='product-detail']";
	private static final String XP_ALTA_BOLSA_BUTTON ="//*[@data-testid='pdp.form.addToCart']";
	private static final String XP_BUTTON_FAVORITOS = "//*[@data-testid='button-icon']";
	private static final String XP_BUTTON_FAVORITOS_FOR_ADD = XP_BUTTON_FAVORITOS + "//self::*[@class[contains(.,'outline')]]";
	private static final String XP_BUTTON_FAVORITOS_FOR_REMOVE = XP_BUTTON_FAVORITOS + "//self::*[@class[contains(.,'fill')]]";
	private static final String XP_DIV_ANADIENDO_FAVORITOS = "//div[@class[contains(.,'product-banner')]]";

	private static final String XP_BUSCAR_ENN_TIENDA_BUTTON = "//button[@class[contains(.,'garment-finder')]]";
	private static final String XP_GUIA_DE_TALLAS_LINK = "//span[@id='productFormSizesGuide']";

	private static final String XP_DIV_IMG_CENTRAL_DIV_DESKTOP = "//div[@class='main-img']";
	private static final String XP_DIV_IMG_CENTRAL_DIV_DEVICE =
			"//div[@class[contains(.,'product-img-container')] and @class[contains(.,'slide-active')]]";
	private static final String XP_ASPA_ZOOM_IMAGE_CENTRAL = "//div[@class='zoom-image-close']";

	private static final String XP_FICHA_CON_ZOOM_DESKTOP = "//div[@class[contains(.,'zoom-out')]]";
	private static final String XP_FICHA_CON_ZOOM_DEVICE = "//div[@class[contains(.,'zoom-image-container')]]";

	private static final String XP_ULTIMOS_PRODUCTOS_SECTION = "//*[@id='ultimos_productos']";
	private static final String XP_MODAL_NO_STOCK = "//div[@class='modalNoStock show']";
	private static final String XP_IMAGEN_CARRUSEL_IZQ = "//div[@class='carousel-img-container']//img[@class[contains(.,'carousel-img')]]";

	private String getXPathIsPage(String referencia) {
		return XP_CONTAINER_FICHA + secDataProduct.getXPathLinReferencia(referencia);
	}

	private String getXPathDivImgCentralDiv() {
		if (isMobile()) {
			return XP_DIV_IMG_CENTRAL_DIV_DEVICE;
		}
		return XP_DIV_IMG_CENTRAL_DIV_DESKTOP;
	}
	private String getXPathImagenCentral() {
		String xpathDiv = getXPathDivImgCentralDiv();
		return xpathDiv + "/img";
	}

	private String getXPathFichaConZoom() {
		if (channel.isDevice()) {
			return XP_FICHA_CON_ZOOM_DEVICE;
		}
		return XP_FICHA_CON_ZOOM_DESKTOP;
	}
	private String getXPathImagenCentralConZoom() {
		String xpathDiv = getXPathFichaConZoom();
		return xpathDiv + "//img";
	}

	@Override
	public boolean isPage(int seconds) {
		return (
				state(PRESENT, XP_CONTAINER_FICHA).wait(seconds).check() &&
						//secDataProduct.getSecSelTallas().isVisibleSelectorTallasUntil(seconds)
						secDataProduct.getSecSelTallas().isSectionUntil(seconds)
		);
	}

	@Override
	public boolean isFichaArticuloUntil(String refArticulo, int seconds) {
		String refSinColor = refArticulo.substring(0,8);
		String xpathFichaRef = getXPathIsPage(refSinColor);
		return state(PRESENT, xpathFichaRef).wait(seconds).check();
	}

	@Override
	public boolean isVisibleBolsaButton(int seconds) {
		return state(VISIBLE, XP_ALTA_BOLSA_BUTTON).wait(seconds).check();
	}
	
	@Override
	public void clickAnadirBolsaButtonAndWait() {
		click(XP_ALTA_BOLSA_BUTTON).waitLink(2).type(JAVASCRIPT).exec();
	}

	@Override
	public void selectAnadirAFavoritosButton() {
		click(XP_BUTTON_FAVORITOS_FOR_ADD).exec();
	}

	@Override
	public void selectRemoveFromFavoritosButton() {
		click(XP_BUTTON_FAVORITOS_FOR_REMOVE).waitLink(2).exec();
	}

	@Override
	public boolean isVisibleDivAnadiendoAFavoritosUntil(int seconds) {
		return state(VISIBLE, XP_DIV_ANADIENDO_FAVORITOS).wait(seconds).check();
	}

	@Override
	public boolean isInvisibleDivAnadiendoAFavoritosUntil(int seconds) {
		return state(INVISIBLE, XP_DIV_ANADIENDO_FAVORITOS).wait(seconds).check();
	}

	@Override
	public boolean isVisibleButtonElimFavoritos(int seconds) {
		return state(VISIBLE, XP_BUTTON_FAVORITOS_FOR_REMOVE).wait(seconds).check();
	}

	@Override
	public boolean isVisibleButtonAnadirFavoritos(int seconds) {
		return state(VISIBLE, XP_BUTTON_FAVORITOS_FOR_ADD).wait(seconds).check();
	}

	@Override
	public String getNameLinkBuscarEnTienda() {
		return "Botón Buscar en tienda";
	}

	@Override
	public boolean isVisibleBuscarEnTiendaLink() {
		return state(VISIBLE, XP_BUSCAR_ENN_TIENDA_BUTTON).check();
	}

	@Override
	public void selectBuscarEnTiendaLink() {
		click(XP_BUSCAR_ENN_TIENDA_BUTTON).exec();
	}

	@Override
	public boolean isModalNoStockVisible(int seconds) {
		return state(VISIBLE, XP_MODAL_NO_STOCK).wait(seconds).check();
	}
	
	@Override
	public boolean isVisibleStickyContent(int seconds) {
		return false;
	}
	@Override
	public boolean isInvisibleStickyContent(int seconds) {
		return true;
	}	
	@Override
	public boolean isVisibleReferenciaStickyContent(String productoName) {
		return false;
	}
	@Override
	public boolean isVisibleTallaLabelStickyContent(String tallaLabel) {
		return false;
	}
	@Override
	public boolean isVisibleColorCodeStickyContent(String colorCode) {
		return false;
	}	

	public boolean isVisibleUltimosProductosSection() {
		return state(VISIBLE, XP_ULTIMOS_PRODUCTOS_SECTION).check();
	}

	public void clickImagenFichaCentral() {
		click(getXPathDivImgCentralDiv()).exec();
	}

	public void closeZoomImageCentralDevice() {
		click(XP_ASPA_ZOOM_IMAGE_CENTRAL).exec();
	}

	public int getNumImgsCarruselIzq() {
		return getElements(XP_IMAGEN_CARRUSEL_IZQ).size();
	}

	public String getSrcImgCarruselIzq(int numImagen) {
		String srcImagen = "";
		String xpathImagenX = "(" + XP_IMAGEN_CARRUSEL_IZQ + ")[" + numImagen + "]";
		if (state(PRESENT, xpathImagenX).check()) {
			String srcImagenO = getElement(xpathImagenX).getAttribute("src");
			srcImagen = srcImagenO.substring(srcImagenO.lastIndexOf("/"));
		}
		return srcImagen;
	}

	public String getSrcImagenCentral() {
		String xpathImg = getXPathImagenCentral();
		if (state(PRESENT, xpathImg).check()) {
			String srcImagenO = getElement(xpathImg).getAttribute("src");
			return srcImagenO.substring(srcImagenO.lastIndexOf("/"));
		}
		return "";
	}

	public String getSrcImagenCentralConZoom() {
		String xpathImg = getXPathImagenCentralConZoom();
		if (state(PRESENT, xpathImg).check()) {
			String srcImagenO = getElement(xpathImg).getAttribute("src");
			return srcImagenO.substring(srcImagenO.lastIndexOf("/"));
		}
		return "";
	}

	public void clickImgCarruselIzq(int numImagen) {
		String xpathImagenX = "(" + XP_IMAGEN_CARRUSEL_IZQ + ")[" + numImagen + "]";
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
		return (state(VISIBLE, getXPathDivImgCentralDiv()).check());
	}

	public void selectGuiaDeTallasLink() {
		click(XP_GUIA_DE_TALLAS_LINK).exec();
	}

	public boolean isPresentPageUntil(int seconds) {
		return (state(PRESENT, XP_CONTAINER_FICHA).wait(seconds).check());
	}
}
