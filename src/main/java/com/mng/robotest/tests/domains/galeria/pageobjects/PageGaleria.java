package com.mng.robotest.tests.domains.galeria.pageobjects;

import java.util.List;

import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.galeria.pageobjects.commons.entity.TypeSlider;
import com.mng.robotest.tests.domains.galeria.pageobjects.genesis.PageGaleriaDesktopGenesis;
import com.mng.robotest.tests.domains.galeria.pageobjects.genesis.PageGaleriaDeviceGenesis;
import com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.PageGaleriaDesktopNoGenesis;
import com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.PageGaleriaDesktopOld;
import com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.PageGaleriaDeviceNoGenesis;
import com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.PageGaleriaDeviceOld;
import com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.PageGaleriaDesktopBaseNoGenesis.NumColumnas;
import com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.PageGaleriaDesktopBaseNoGenesis.TypeArticleDesktop;
import com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.PageGaleriaNoGenesis.StateFavorito;
import com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.sections.filters.FilterOrdenacion;
import com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.sections.menus.SecBannerHeadGallery.TypeLinkInfo;
import com.mng.robotest.tests.domains.galeria.steps.GaleriaSteps.TypeActionFav;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.data.Color;
import com.mng.robotest.testslegacy.generic.beans.ArticuloScreen;
import com.mng.robotest.testslegacy.pageobject.utils.DataArticleGalery;
import com.mng.robotest.testslegacy.pageobject.utils.DataScroll;
import com.mng.robotest.testslegacy.pageobject.utils.ListDataArticleGalery;

public interface PageGaleria {

	String getXPathArticulo();
	String getXPathNombreRelativeToArticle();
	boolean isVisibleAnyArticle();
	void clickLinkColumnas(NumColumnas numColumnas);
	WebElement getArticuloConVariedadColoresAndHover(int numArticulo);
	WebElement getImagenElementArticulo(WebElement articulo);
	ArticuloScreen getArticuloObject(int numArticulo) throws Exception;
	String getNombreArticulo(WebElement articulo);
	String getPrecioArticulo(WebElement articulo);
	boolean isArticleRebajado(WebElement articulo);
	String getCodColorArticulo(int numArticulo) throws Exception;
	String getNameColorFromCodigo(String codigoColor);
	void clickColorArticulo(WebElement articulo, int posColor);
	int getNumFavoritoIcons();
	List<ArticuloScreen> clickArticleHearthIcons(Integer... posIconsToClick) throws Exception;
	boolean isArticleWithHearthIconPresentUntil(int posArticle, int seconds);
	void clickHearhIcon(int posArticle) throws Exception;
	String getRefArticulo(WebElement articulo);
	String getRefColorArticulo(WebElement articulo);
	boolean backTo1erArticulo() throws InterruptedException;
	String getXPathPagina(int pagina);
	int getNumArticulosFromPagina(int pagina, TypeArticleDesktop sizeArticle);
	WebElement getArticleFromPagina(int numPagina, int numArticle);
	boolean isHeaderArticlesVisible(String textHeader);
	void showTallasArticulo(int posArticulo);
	ArticuloScreen selectTallaAvailableArticle(int posArticulo) throws Exception;
	void selectTallaArticleNotAvalaible();
	void clickHearthIcon(WebElement hearthIcon) throws Exception;
	void clickSlider(WebElement articulo, TypeSlider typeSlider);
	String getXPathArticleHearthIcon(int posArticulo);
	StateFavorito getStateHearthIcon(int iconNumber);
	boolean preciosInIntervalo(int minimo, int maximo) throws Exception;
	
	boolean isClickableArticuloUntil(int numArticulo, int seconds);
	boolean articlesInOrder(FilterOrdenacion typeOrden) throws Exception;
	void hoverArticle(WebElement article);
	int getNumArticulos();
	boolean isVisibleArticleUntil(int numArticulo, int seconds);
	void moveToArticle(int numArticulo);
	void moveToArticleAndGetObject(int posArticulo);
	WebElement getArticulo(int numArticulo);
	boolean waitToHearthIconInState(int posArticle, StateFavorito stateIcon, int seconds);
	List<DataArticleGalery> searchArticleRepeatedInGallery();
	ListDataArticleGalery getListDataArticles();
	boolean iconsInCorrectState(TypeActionFav typeAction, Integer... posIconosFav);
	boolean backTo1erArticulo(String xpathIconoUpGalery);
	String getNombreArticuloWithText(String literal, int secondsWait);
	void clickArticulo(WebElement articulo);
	boolean isVisibleImageArticle(int numArticulo, int seconds);
	String openArticuloPestanyaAndGo(WebElement article);
	String getImagenArticulo(WebElement articulo);
	int filterByColorsAndReturnNumArticles(List<Color> colorsToSelect);
	int selecOrdenacionAndReturnNumArticles(FilterOrdenacion typeOrden) throws Exception;
	boolean isClickableFiltroUntil(int seconds);
	boolean isVisibleArticleCapaTallasUntil(int posArticulo, int seconds);
	DataScroll scrollToPageFromFirst(int numPage);
	void scrollToLastPage();

	boolean isVisibleSubMenuDesktop(String submenu);
	void clickSubMenuDesktop(String submenu);
	
    boolean isVisibleLinkInfoRebajasBannerHead();
    boolean isVisibleLinkInfoRebajasBannerHead(TypeLinkInfo typeLinkInfo);
    boolean isVisibleBannerHead();
    boolean isBannerHeadLinkable();
    void clickBannerHeadIfClickable();
    boolean isBannerHeadWithoutTextAccesible();
    String getTextBannerHead();
    boolean isBannerHeadSalesBanner(IdiomaPais idioma);
    
	public boolean isVisibleSelectorPrecios();
	public int getMinImportFilter();
	public int getMaxImportFilter();
	public void clickIntervalImportFilter(int margenPixelsLeft, int margenPixelsRight);
	public void showFilters();
	public void acceptFilters();
	
	public List<String> searchForArticlesNoValid(List<String> articleNames);
	
	public enum From { MENU, BUSCADOR }
	public enum AttributeArticle { NOMBRE, REFERENCIA, IMAGEN }
	public static final int MAX_PAGE_TO_SCROLL = 20;

	public static PageGaleria make(Channel channel, AppEcom app, Pais pais) {
		return make(From.MENU, channel, app, pais);
	}
	public static PageGaleria make(From from, Channel channel, AppEcom app, Pais pais) {
		switch (channel) {
			case desktop:
				if (from==From.BUSCADOR) {
					return new PageGaleriaDesktopOld();
				}
				if (pais.isGaleriaGenesis(app)) {
					return new PageGaleriaDesktopGenesis();
				}
				return new PageGaleriaDesktopNoGenesis();
			case mobile, tablet:
			default:
				if (from==From.BUSCADOR) {
					return new PageGaleriaDeviceOld();
				}
				if (pais.isGaleriaGenesis(app)) {
					return new PageGaleriaDeviceGenesis();
				}
				return new PageGaleriaDeviceNoGenesis();
		}
	}
	
}
