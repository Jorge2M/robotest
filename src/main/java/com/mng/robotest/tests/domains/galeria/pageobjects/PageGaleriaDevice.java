package com.mng.robotest.tests.domains.galeria.pageobjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import com.mng.robotest.tests.domains.galeria.pageobjects.PageGaleriaDesktop.TypeArticleDesktop;
import com.mng.robotest.tests.domains.galeria.pageobjects.article.SecTallasArticulo;
import com.mng.robotest.testslegacy.data.Constantes;
import com.mng.robotest.testslegacy.generic.beans.ArticuloScreen;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.mng.robotest.tests.domains.galeria.pageobjects.article.SecPreciosArticulo.TipoPrecio.*;

public abstract class PageGaleriaDevice extends PageGaleria {
	
	private final SecTallasArticulo secTallas = SecTallasArticulo.make(channel, app, dataTest.getPais());
	
	private static final String TAG_ID_COLOR = "@TagIdColor";
	
	private static final String XP_IMG_COD_COLOR_WITH_TAG_COLOR = 
		"//*[@class[contains(.,'color-container')] and @id='" + TAG_ID_COLOR + "']/img";
	
	private static final String XP_BUTTON_ANYADIR_RELATIVE_ARTICLE = "//*[@data-testid[contains(.,'addToCart')]]";
	private static final String TAG_NUM_PAGINA = "@tagNumPagina";
	private static final String XP_PAGINA_WITH_TAG = "//div[@id='page" + TAG_NUM_PAGINA + "']";
	private static final String XP_PAGINA_TABLET_OUTLET_WITH_TAG = "//div[@id='page" + TAG_NUM_PAGINA + "Height']";
	private static final String XP_HEADER_ARTICLES = "//h1[@class='catalog-title']";
	private static final String XP_BUTTON_FOR_CLOSE_TALLAS = "//button[@data-testid='sheet.overlay']";
	
	protected abstract String getXPathArticuloAncestor();
	protected abstract String getXPathArticuloConColores();
	protected abstract String getXPathColorArticleOption();
	protected abstract void showColors(WebElement articulo);
	
	@Override
	public String getXPathCabeceraBusquedaProd() {
		return ("//*[@id='buscador_cabecera2']");
	}
	
	//Número de páginas a partir del que consideramos que se requiere un scroll hasta el final de la galería
	public static final int SCROLL_TO_LAST = 20; 
	
	protected PageGaleriaDevice() {
		super();
	}
	
	protected PageGaleriaDevice(From from) {
		super(from);
	}
	
	String getXPathArticuloConVariedadColores(int numArticulo) {
		return ("(" + getXPathArticuloConColores() + ")" + "[" + numArticulo + "]");
	}
	
	String getXPathImgCodigoColor(String codigoColor) {
		return XP_IMG_COD_COLOR_WITH_TAG_COLOR.replace(TAG_ID_COLOR, codigoColor);
	}
	
	String getXPathButtonAnyadirArticle(int posArticulo) {
		String xpathArticulo = "(" + getXPathArticulo() + ")[" + posArticulo + "]";
		return (xpathArticulo + XP_BUTTON_ANYADIR_RELATIVE_ARTICLE);
	}
	
	String getXPathPagina(int pagina) {
		if (isTablet() && isOutlet()) {
			return (XP_PAGINA_TABLET_OUTLET_WITH_TAG.replace(TAG_NUM_PAGINA, String.valueOf(pagina)));
		}
		return (XP_PAGINA_WITH_TAG.replace(TAG_NUM_PAGINA, String.valueOf(pagina)));
	}
	
	@Override
	public WebElement getArticuloConVariedadColoresAndHover(int numArticulo) {
		String xpathArticulo = getXPathArticuloConVariedadColores(numArticulo);
		var articulo = getElement(xpathArticulo); 
		hoverArticle(articulo);
		return articulo;
	}
	
	@Override
	public void clickColorArticulo(WebElement articulo, int posColor) {
		showColors(articulo);
		var color = getColorArticulo(posColor);
		color.click(); 
	}
	
	private WebElement getColorArticulo(int posColor) {
		state(VISIBLE, getXPathColorArticleOption()).wait(1).check();
		return getElements("." + getXPathColorArticleOption()).get(posColor-1);
	}

	@Override
	public int getNumFavoritoIcons() {
		return getElements(XP_HEARTH_ICON_RELATIVE_ARTICLE).size();
	}
		
	@Override
	public int getLayoutNumColumnas() {
		return 1;
	}		 
	
	@Override
	public ArticuloScreen getArticuloObject(int numArticulo) throws Exception {
		var artWElem = getElements(getXPathArticulo()).get(numArticulo-1);
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
	public String getPrecioArticulo(WebElement articulo) {
		if (isArticleRebajado(articulo)) {
			return articulo.findElement(By.xpath("." + PRECIO_REBAJADO_DEFINITIVO.getXPath())).getText();
		}
		return articulo.findElement(By.xpath("." + PRECIO_NO_REBAJADO_DEFINITIVO.getXPath())).getText();
	}	
	
	@Override
	public boolean isArticleRebajado(WebElement articulo) {
		return state(PRESENT, articulo)
				.by(By.xpath("." + PRECIO_INICIAL_TACHADO.getXPath())).check();
	}
	
	@Override
	public String getCodColorArticulo(int numArticulo) throws Exception {
		String xpathArticulo = getXPathArticulo() + "//self::*[@data-testid[contains(.,'product-" + (numArticulo-1) + "')]]";
		moveToElement(By.xpath(xpathArticulo));
		String image = getImagenArticulo(getElement(xpathArticulo));
		return UtilsPageGaleria.getCodColorFromSrcImg(image);
	}
	
	@Override
	public String getNameColorFromCodigo(String codigoColor) {
		String xpathImgColor = getXPathImgCodigoColor(codigoColor);
		if (!state(PRESENT, xpathImgColor).check()) {
			return Constantes.COLOR_DESCONOCIDO;
		}
		return getElement(xpathImgColor).getAttribute("title");
	}
	
	@Override
	public List<ArticuloScreen> clickArticleHearthIcons(Integer... posIconsToClick) 
			throws Exception {
		List<ArticuloScreen> listArtFav = new ArrayList<>();
		for (int posIcon : posIconsToClick) {
			clickHearhIcon(posIcon);
			var articulo = getArticuloObject(posIcon);
			listArtFav.add(articulo);
		}
		return listArtFav;
	}
	
	@Override
	public boolean isArticleWithHearthIconPresentUntil(int posArticle, int seconds) {
		String xpathIcon = getXPathArticleHearthIcon(posArticle);
		return state(PRESENT, xpathIcon).wait(seconds).check();
	}
	
	@Override
	public void clickHearhIcon(int posArticle) throws Exception {
		var hearthIcon = moveToHearthIcon(posArticle);
		clickHearthIconAndWait(hearthIcon);
	}
	
	private WebElement moveToHearthIcon(int posArticle) {
		moveToArticle(posArticle);
		String xpathIcon = getXPathArticleHearthIcon(posArticle);
		state(VISIBLE, getElement(xpathIcon)).wait(1).check();
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
		if (state(VISIBLE, XP_HEADER_ARTICLES).check()) {
			return getElement(XP_HEADER_ARTICLES).getText().toLowerCase().contains(textHeader.toLowerCase());
		}
		return false;
	}

	@Override
	public void showTallasArticulo(int posArticulo) {
		moveToArticleAndGetObject(posArticulo);
		if (!secTallas.isVisibleArticleCapaTallasUntil(posArticulo, 0)) {
			String xpathButtonAnyadir = getXPathButtonAnyadirArticle(posArticulo);
			state(VISIBLE, xpathButtonAnyadir).wait(3).check();
			click(xpathButtonAnyadir).exec();
		}
	}
	public void unshowTallasArticulo() {
		if (state(PRESENT, XP_BUTTON_FOR_CLOSE_TALLAS).check()) {
			click(XP_BUTTON_FOR_CLOSE_TALLAS).exec();
		}
	}
	
	@Override
	public ArticuloScreen selectTallaAvailableArticle(int posArticulo) throws Exception {
		//Si no está visible la capa de tallas ejecutamos los pasos necesarios para hacer la visible 
		if (!secTallas.isVisibleArticleCapaTallasUntil(posArticulo, 0)) {
			showTallasArticulo(posArticulo);
		}
		var tallaSelected = secTallas.selectTallaAvailableArticle(posArticulo);
		var articulo = getArticuloObject(posArticulo);
		articulo.setTalla(tallaSelected);
		return articulo;
	}
	
	public void selectTallaArticleNotAvalaible() {
		for (int i=1; i<20; i++) {
			showTallasArticulo(i);
			if (secTallas.selectTallaNotAvailableIfVisible()) {
				break;
			}
			unshowTallasArticulo();
		}
	}

	@Override
	public void clickHearthIcon(WebElement hearthIcon) throws Exception {
		moveToElement(hearthIcon);
		state(CLICKABLE, hearthIcon).wait(1).check();
		hearthIcon.click();
	}
	
	private List<WebElement> getListArticulosFromPagina(int numPagina) {
		moveToPagina(numPagina);
		return getElements(getXPathArticuloFromPagina(numPagina));
	}
	
	private String getXPathArticuloFromPagina(int pagina) {
		String xpathPagina = getXPathPagina(pagina);
		return  (xpathPagina + getXPathArticulo());
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
	
}