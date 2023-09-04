package com.mng.robotest.domains.galeria.pageobjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import com.mng.robotest.conf.AppEcom;
import com.mng.robotest.domains.galeria.pageobjects.PageGaleriaDesktop.TypeArticleDesktop;
import com.mng.robotest.domains.galeria.pageobjects.SecPreciosArticulo.TipoPrecio;
import com.mng.robotest.test.data.Constantes;
import com.mng.robotest.test.data.Talla;
import com.mng.robotest.test.generic.beans.ArticuloScreen;
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
		"//*[@class[contains(.,'color-container')] and @id='" + TAG_ID_COLOR + "']/img";
	
	private static final String XPATH_BUTTON_ANYADIR_RELATIVE_ARTICLE = "//*[@data-testid[contains(.,'addToCart')]]";
	
	//TODO mantener sólo una versión una vez se resuelva el TestAB
	private static final String XPATH_CAPA_TALLAS_RELATIVE_ARTICLE_OLD = "//div[@class[contains(.,'product-sizes-container')]]";
	private static final String XPATH_CAPA_TALLAS_NEW = "//ul[@data-testid='plp.sizesSelector.list']";
	
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
	
	@Override
	public String getXPathCabeceraBusquedaProd() {
		return ("//*[@id='buscador_cabecera2']");
	}
	
	//Número de páginas a partir del que consideramos que se requiere un scroll hasta el final de la galería
	public static final int SCROLL_TO_LAST = 20; 
	
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
	
	//TODO mantener una sola variante cuando se resuelva el TestAB
	String getXPathArticleCapaTallas(int posArticulo) {
		return "(" + getXPathArticleCapaTallasOld(posArticulo) + " | " + getXPathArticleCapaTallasNew() + ")"; 
	}
	
	String getXPathArticleCapaTallasOld(int posArticulo) {
		String xpathArticulo = "(" + xpathArticuloBase + ")[" + posArticulo + "]";
		return (xpathArticulo + XPATH_CAPA_TALLAS_RELATIVE_ARTICLE_OLD);
	}
	String getXPathArticleCapaTallasNew() {
		return XPATH_CAPA_TALLAS_NEW;
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
			return getElement(articulo, "." + XPATH_IMG_RELATIVE_ARTICLE);
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
		return getElements(XPATH_HEARTH_ICON_RELATIVE_ARTICLE).size();
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
		var articulo = new ArticuloScreen();
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
		String xpathArticulo = xpathArticuloBase + "//self::*[@data-testid[contains(.,'product-" + (numArticulo-1) + "')]]";
		moveToElement(By.xpath(xpathArticulo));
		String image = getImagenArticulo(getElement(xpathArticulo));
		return UtilsPageGaleria.getCodColorFromSrcImg(image);
	}
	
	@Override
	public String getNameColorFromCodigo(String codigoColor) {
		String xpathImgColor = getXPathImgCodigoColor(codigoColor);
		if (!state(Present, xpathImgColor).check()) {
			return Constantes.COLOR_DESCONOCIDO;
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
		var hearthIcon = moveToHearthIcon(posArticle);
		clickHearthIconAndWait(hearthIcon);
	}
	
	private WebElement moveToHearthIcon(int posArticle) {
		moveToArticle(posArticle);
		String xpathIcon = getXPathArticleHearthIcon(posArticle);
		state(Visible, getElement(xpathIcon)).wait(1).check();
		var hearthIcon = getElement(xpathIcon);
		moveToElement(getElement(xpathIcon));
		return hearthIcon;
	}
	
	private void clickHearthIconAndWait(WebElement hearthIcon) throws Exception {
		var estadoInicial = getStateHearthIcon(hearthIcon);
		clickHearthIconPreventingOverlapping(hearthIcon);
		waitToHearthIconInState(hearthIcon, estadoInicial.getOpposite(), 2);
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
			return getElement(XPATH_HEADER_ARTICLES).getText().toLowerCase().contains(textHeader.toLowerCase());
		}
		return false;
	}

	@Override
	public void showTallasArticulo(int posArticulo) {
		moveToArticleAndGetObject(posArticulo);
		if (!isVisibleArticleCapaTallasUntil(posArticulo, 0)) {
			String xpathButtonAnyadir = getXPathButtonAnyadirArticle(posArticulo);
			click(xpathButtonAnyadir).exec();
		}
	}
	
	@Override
	public boolean isVisibleArticleCapaTallasUntil(int posArticulo, int seconds) {
		String xpathCapa = getXPathArticleCapaTallas(posArticulo);
		return state(Visible, xpathCapa).wait(seconds).check();
	}
	
	private String getXPathTallaAvailableArticle(int posArticulo) {
		String xpathCapa = getXPathArticleCapaTallas(posArticulo);
		return xpathCapa + "//*[@data-testid[contains(.,'size.available')]]";
	}
	
	@Override
	public ArticuloScreen selectTallaAvailableArticle(int posArticulo) throws Exception {
		//Si no está visible la capa de tallas ejecutamos los pasos necesarios para hacer la visible 
		if (!isVisibleArticleCapaTallasUntil(posArticulo, 0)) {
			showTallasArticulo(posArticulo);
		}
		
		var xpathTalla = getXPathTallaAvailableArticle(posArticulo);
		var tallaToSelect = getElement(xpathTalla);
		var articulo = getArticuloObject(posArticulo);
		articulo.setTalla(Talla.fromLabel(tallaToSelect.getText()));
		tallaToSelect.click();
		return articulo;
	}
	
	public void selectTallaArticleNotAvalaible() {
		for (int i=1; i<6; i++) {
			showTallasArticulo(i);
			String xpathTallaNoDispo = secTallas.getXPathArticleTallaNotAvailable();
			if (state(Visible, xpathTallaNoDispo).check()) {
				click(xpathTallaNoDispo).exec();
				break;
			}
		}
	}

//	@Override
//	public StateFavorito getStateHearthIcon(WebElement hearthIcon) {
//		if (hearthIcon.getAttribute("class").contains("favorite--active")) {
//			return StateFavorito.MARCADO;
//		}
//		return StateFavorito.DESMARCADO;
//	}
//
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