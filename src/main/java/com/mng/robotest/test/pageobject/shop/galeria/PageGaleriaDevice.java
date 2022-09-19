package com.mng.robotest.test.pageobject.shop.galeria;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.data.Constantes;
import com.mng.robotest.test.data.Talla;
import com.mng.robotest.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test.pageobject.shop.galeria.PageGaleriaDesktop.TypeArticleDesktop;
import com.mng.robotest.test.pageobject.shop.galeria.SecPreciosArticulo.TipoPrecio;
import com.github.jorge2m.testmaker.conf.Channel;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageGaleriaDevice extends PageGaleria {
	
	private static final String TAG_ID_COLOR = "@TagIdColor";
	private static final String TAG_FLAG_SELECTED = "@TagFlagSelected";

	private static final String XPATH_IMG_RELATIVE_ARTICLE = 
		"//img[@src and " + 
			 "(@class[contains(.,'productListImg')] or " + 
			  "@class[contains(.,'product-list-image')] or " + 
			  "@class[contains(.,'product-list-img')] or " +
			  "@class[contains(.,'product-image')] or " +
			  "@id[contains(.,'product-image')])]";
	private static final String XPATH_IMG_COD_COLOR_WITH_TAG_COLOR = 
		"//div[@class[contains(.,'color-container')] and @id='" + TAG_ID_COLOR + "']/img";
//	private static final String XPATH_PRECIO_REBAJADO_RELATIVE_ARTICLE = 
//		"//*[@class[contains(.,'product-list-sale-price')] or " + 
//			"@class[contains(.,'product-list-info-price-sale')] or " + 
//			"@class[contains(.,'product-price-crossed')]]";
//	private static final String XPATH_PRECIO_ORIGINAL_RELATIVE_ARTICLE = 
//		"//*[@class[contains(.,'productList__price')] or " + 
//			"@class[contains(.,'productList__salePrice')] or " + 
//			"@class[contains(.,'product-list-info-price')] or " + 
//			"@class[contains(.,'product-list-price')] or " +
//			"@class[contains(.,'product-price')]]";
	private static final String XPATH_BUTTON_ANYADIR_RELATIVE_ARTICLE = "//div[@class[contains(.,'product-actions')]]/button";
	private static final String XPATH_CAPA_TALLAS_RELATIVE_ARTICLE = "//div[@class[contains(.,'product-sizes-container')]]";
	private static final String XPATH_ICONO_GALERY_MOBILE = "//div[@class[contains(.,'scroll-container--visible')]]";
	private static final String XPATH_ICONO_UP_GALERY_TABLET = "//div[@class='scroll-top-step']";
	private static final String TAG_NUM_PAGINA = "@tagNumPagina";
	private static final String XPATH_PAGINA_WITH_TAG = "//div[@id='page" + TAG_NUM_PAGINA + "']";
	private static final String XPATH_PAGINA_TABLET_OUTLET_WITH_TAG = "//div[@id='page" + TAG_NUM_PAGINA + "Height']";
	private static final String XPATH_HEADER_ARTICLES = "//h1[@class='catalog-title']";
	
	private static final String XPATH_COLORES_ARTICULO = "//div[@class[contains(.,'product-colors')]]";
	private static final String XPATH_COLORES_ARTICULO_OUTLET_TABLET = "//div[@class[contains(.,'product-list-colors')]]";
	
	String getXPathColoresArticle() {
		if (channel==Channel.tablet && app==AppEcom.outlet) {
			return XPATH_COLORES_ARTICULO_OUTLET_TABLET;
		}
		return XPATH_COLORES_ARTICULO;
	}

	String getXPathArticuloConColores() {
		return getXPathColoresArticle() + "//" + getXPathAncestorArticulo();
	}
	
	String getXPpathIconoUpGalery() {
		switch (channel) {
		case mobile:
			return XPATH_ICONO_GALERY_MOBILE;
		case tablet:
		default:
			return XPATH_ICONO_UP_GALERY_TABLET;
		}
	}
	
	String getXPathImgColorRelativeArticleWithTagSelected() {
		return (
			getXPathColoresArticle() + 
			"//self::*[@class[contains(.,'" + TAG_FLAG_SELECTED + "')]]//img");
	}
	
	@Override
	public String getXPathLinkRelativeToArticle() {
		return "//a";
	}
	
	public String getXPathCabeceraBusquedaProd() {
		return ("//*[@id='buscador_cabecera2']");
	}
	
	@Override
	public void hideMenus() {
		//TODO
	}

	//Número de páginas a partir del que consideramos que se requiere un scroll hasta el final de la galería
	public static int SCROLL_TO_LAST = 20; 
	
	public PageGaleriaDevice(From from) {
		super(from);
	}
	
	String getXPathArticuloConVariedadColores(int numArticulo) {
		return ("(" + getXPathArticuloConColores() + ")" + "[" + numArticulo + "]");
	}
	
	String getXPathImgCodigoColor(String codigoColor) {
		return XPATH_IMG_COD_COLOR_WITH_TAG_COLOR.replace(TAG_ID_COLOR, codigoColor);
	}
	
	String getXPathImgColorRelativeArticle(boolean selected) {
		String selectedStr = "";
		if (selected) {
			selectedStr = "selected";
		}
		return (getXPathImgColorRelativeArticleWithTagSelected().replace(TAG_FLAG_SELECTED, selectedStr));
	}
	
	String getXPathButtonAnyadirArticle(int posArticulo) {
		String xpathArticulo = "(" + xpathArticuloBase + ")[" + posArticulo + "]";
		return (xpathArticulo + XPATH_BUTTON_ANYADIR_RELATIVE_ARTICLE);
	}
	
	String getXPathArticleCapaTallas(int posArticulo) {
		String xpathArticulo = "(" + xpathArticuloBase + ")[" + posArticulo + "]";
		return (xpathArticulo + XPATH_CAPA_TALLAS_RELATIVE_ARTICLE);
	}
	
	String getXPathPagina(int pagina) {
		if (channel==Channel.tablet && app==AppEcom.outlet ) {
			return (XPATH_PAGINA_TABLET_OUTLET_WITH_TAG.replace(TAG_NUM_PAGINA, String.valueOf(pagina)));
		}
		return (XPATH_PAGINA_WITH_TAG.replace(TAG_NUM_PAGINA, String.valueOf(pagina)));
	}
	
	@Override
	public WebElement getArticuloConVariedadColoresAndHover(int numArticulo) {
		String xpathArticulo = getXPathArticuloConVariedadColores(numArticulo);
		WebElement articulo = getElement(xpathArticulo); 
		hoverArticle(articulo);
		return articulo;
	}
	
	@Override
	public WebElement getImagenElementArticulo(WebElement articulo) {
		moveToElement(articulo);
		By byImg = By.xpath("." + XPATH_IMG_RELATIVE_ARTICLE);
		if (state(Present, articulo).by(byImg).wait(3).check()) {
			return getElement(byImg);
		}
		return null;
	}
	
	@Override
	public WebElement getColorArticulo(WebElement articulo, boolean selected, int numColor) {
		String xpathImgColorRelArticle = getXPathImgColorRelativeArticle(selected);
		return getElements("." + xpathImgColorRelArticle).get(numColor-1);
	}

	@Override
	public int getNumFavoritoIcons() {
		return getElements(getXPathHearthIconRelativeArticle()).size();
	}
		
	@Override
	public boolean eachArticlesHasOneFavoriteIcon() {  
		int numArticles = getNumArticulos(); 
		int numIcons = getNumFavoritoIcons();
		return (numArticles == numIcons);
	}
	
	@Override
	public int getLayoutNumColumnas() {
		return 1;
	}		 
	
	@Override
	public ArticuloScreen getArticuloObject(int numArticulo) throws Exception {
		WebElement artWElem = getElements(xpathArticuloBase).get(numArticulo-1);
		ArticuloScreen articulo = new ArticuloScreen();
		articulo.setReferencia(getRefArticulo(artWElem));
		articulo.setNombre(getNombreArticulo(artWElem));
		articulo.setPrecio(getPrecioArticulo(artWElem));
		articulo.setCodigoColor(getCodColorArticulo(numArticulo));
		articulo.setColorName(getNameColorFromCodigo(articulo.getCodigoColor()));
		articulo.setNumero(1);
		
		return articulo;
	}

	@Override
	public String getNombreArticulo(WebElement articulo) {
		return (articulo.findElement(By.xpath("." + XPATH_NOMBRE_RELATIVE_TO_ARTICLE)).getText());
	}
 
	@Override
	public String getPrecioArticulo(WebElement articulo) {
		if (isArticleRebajado(articulo)) {
			//return (articulo.findElement(By.xpath("." + XPATH_PRECIO_REBAJADO_RELATIVE_ARTICLE)).getText());
			return articulo.findElement(By.xpath("." + TipoPrecio.PRECIO_REBAJADO_DEFINITIVO.getXPath())).getText();
		}
		return articulo.findElement(By.xpath("." + TipoPrecio.PRECIO_NO_REBAJADO_DEFINITIVO.getXPath())).getText();
	}	
	
	@Override
	public boolean isArticleRebajado(WebElement articulo) {
		return state(Present, articulo)
				.by(By.xpath("." + TipoPrecio.PRECIO_INICIAL_TACHADO.getXPath())).check();
	}
	
	@Override
	public String getCodColorArticulo(int numArticulo) throws Exception {
		String xpathArticulo = "(" + xpathArticuloBase + ")[" + numArticulo + "]";
		String image = getImagenArticulo(getElement(xpathArticulo));
		return UtilsPageGaleria.getCodColorFromSrcImg(image);
	}
	
	@Override
	public String getNameColorFromCodigo(String codigoColor) {
		String xpathImgColor = getXPathImgCodigoColor(codigoColor);
		if (!state(Present, xpathImgColor).check()) {
			return Constantes.colorDesconocido;
		}
		WebElement imgColorWeb = getElement(xpathImgColor);
		return imgColorWeb.getAttribute("title");
	}
	
	@Override
	public List<ArticuloScreen> clickArticleHearthIcons(List<Integer> posIconsToClick) 
			throws Exception {
		List<ArticuloScreen> listArtFav = new ArrayList<>();
		for (int posIcon : posIconsToClick) {
			clickHearhIcon(posIcon);
			ArticuloScreen articulo = getArticuloObject(posIcon);
			listArtFav.add(articulo);
			
		}
		return listArtFav;
	}
	
	@Override
	public boolean isArticleWithHearthIconPresentUntil(int posArticle, int seconds) {
		String xpathIcon = getXPathArticleHearthIcon(posArticle);
		return state(Present, xpathIcon).wait(seconds).check();
	}
	
	@Override
	public void clickHearhIcon(int posArticle) throws Exception {
		//Nos posicionamos en el icono del Hearth 
		String xpathIcon = getXPathArticleHearthIcon(posArticle);
		WebElement hearthIcon = getElement(xpathIcon);
		moveToElement(hearthIcon);
		
		//Clicamos y esperamos a que el icono cambie de estado
		StateFavorito estadoInicial = getStateHearthIcon(hearthIcon);
		clickHearthIconPreventingOverlapping(hearthIcon);
		switch (estadoInicial) {
		case MARCADO:
			waitToHearthIconInState(hearthIcon, StateFavorito.DESMARCADO, 2);
			break;
		case DESMARCADO:
			waitToHearthIconInState(hearthIcon, StateFavorito.MARCADO, 2);
			break;
		default:
			break;
		}		
	}
	
	@Override
	public int getNumArticulosFromPagina(int pagina, TypeArticleDesktop sizeArticle) {
		return getListArticulosFromPagina(pagina).size();
	}
	
	@Override
	public WebElement getArticleFromPagina(int numPagina, int numArticle) {
		List<WebElement> listArticles = getListArticulosFromPagina(numPagina);
		if (listArticles.size()>=numArticle) {
			return listArticles.get(numArticle);
		}
		return null;
	}
	
	@Override
	public boolean isHeaderArticlesVisible(String textHeader) {
		if (state(Visible, XPATH_HEADER_ARTICLES).check()) {
			return getElement(XPATH_HEADER_ARTICLES).getText().contains(textHeader);
		}
		return false;
	}

	@Override
	public void showTallasArticulo(int posArticulo) {
		moveToArticleAndGetObject(posArticulo);
		String xpathButtonAnyadir = getXPathButtonAnyadirArticle(posArticulo);
		click(xpathButtonAnyadir).exec();
	}

	@Override
	public boolean isVisibleArticleCapaTallasUntil(int posArticulo, int seconds) {
		String xpathCapa = getXPathArticleCapaTallas(posArticulo);
		return state(Visible, xpathCapa).wait(seconds).check();
	}
	
	private String getXPathTallaAvailableArticle(int posArticulo, int posTalla) {
		String xpathCapa = getXPathArticleCapaTallas(posArticulo);
		return "(" + xpathCapa + "//button[@class='product-size']" + ")[" + posTalla + "]";
	}
	
	@Override
	public ArticuloScreen selectTallaAvailableArticle(int posArticulo, int posTalla) throws Exception {
		//Si no está visible la capa de tallas ejecutamos los pasos necesarios para hacer la visible 
		if (!isVisibleArticleCapaTallasUntil(posArticulo, 0/*secondsToWait*/)) {
			showTallasArticulo(posArticulo);
		}
		
		String xpathTalla = getXPathTallaAvailableArticle(posArticulo, posTalla);
		WebElement tallaToSelect = getElement(xpathTalla);
		ArticuloScreen articulo = getArticuloObject(posArticulo);
		articulo.setTalla(Talla.fromLabel(tallaToSelect.getText()));
		tallaToSelect.click();
		return articulo;
	}

	@Override
	public StateFavorito getStateHearthIcon(WebElement hearthIcon) {
		if (hearthIcon.getAttribute("class").contains("favorite--active")) {
			return StateFavorito.MARCADO;
		}
		return StateFavorito.DESMARCADO;
	}

	@Override
	public void clickHearthIcon(WebElement hearthIcon) throws Exception {
		moveToElement(hearthIcon);
		state(Clickable, hearthIcon).wait(1).check();
		hearthIcon.click();
	}
	
	private List<WebElement> getListArticulosFromPagina(int numPagina) {
		moveToPagina(numPagina);
		return getElements(getXPathArticuloFromPagina(numPagina));
	}
	
	private String getXPathArticuloFromPagina(int pagina) {
		String xpathPagina = getXPathPagina(pagina);
		return  (xpathPagina + xpathArticuloBase);
	}
	
	private void moveToPagina(int numPagina) {
		moveToElement(getXPathPagina(numPagina));
	}
	
	private void clickHearthIconPreventingOverlapping(WebElement hearthIcon) throws Exception {
		try {
			clickHearthIcon(hearthIcon);
		}
		catch (WebDriverException e) {
			hideElementsThatCanOverloapHearthIcon();
			clickHearthIcon(hearthIcon);
		}
	}
	
	private void hideElementsThatCanOverloapHearthIcon() {
		hideHtmlComponent(HtmlLocator.ClassName, "orders-filters", driver);
		hideHtmlComponent(HtmlLocator.ClassName, "order-filters", driver);
		hideHtmlComponent(HtmlLocator.TagName, "header", driver);
	}
	
	@Override
	public boolean backTo1erArticulo() throws InterruptedException {
		String xpathIconoUp = getXPpathIconoUpGalery();
		return backTo1erArticulo(xpathIconoUp);
	}
	
	/**
	 * @param numArticulo: posición en la galería del artículo
	 * @return la referencia de un artículo
	 */
	@Override
	public String getRefColorArticulo(WebElement articulo) {
		int lengthReferencia = 11;
		String refWithColor = getRefColorArticuloMethod1(articulo);
		if ("".compareTo(refWithColor)==0) {
			refWithColor = getRefColorArticuloMethod2(articulo);
		}
			
		if (refWithColor.length()>lengthReferencia) {
			return (refWithColor.substring(0, lengthReferencia));
		}
		return refWithColor;
	}
	
	private String getRefColorArticuloMethod1(WebElement articulo) {
		String xpathDivRelativeArticle = "//div[@id and @class='product-container-image']";
		if (state(Present, articulo).by(By.xpath("." + xpathDivRelativeArticle)).check()) {
			return (articulo.findElement(By.xpath("." + xpathDivRelativeArticle)).getAttribute("id"));
		}
		return "";
	}

	private String getRefColorArticuloMethod2(WebElement articulo) {
		WebElement ancorArticle = getElementVisible(articulo, By.xpath(".//a"));
		if (ancorArticle!=null) {
			String hrefArticle = ancorArticle.getAttribute("href");
			return (UtilsPageGaleria.getReferenciaAndCodColorFromURLficha(hrefArticle));
		}
		return "";
	}
}