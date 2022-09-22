package com.mng.robotest.test.pageobject.shop.galeria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Locatable;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.ficha.pageobjects.PageFicha;
import com.mng.robotest.domains.footer.pageobjects.SecFooter;
import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.generic.UtilsMangoTest;
import com.mng.robotest.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test.pageobject.shop.filtros.FilterOrdenacion;
import com.mng.robotest.test.pageobject.shop.galeria.PageGaleriaDesktop.TypeArticleDesktop;
import com.mng.robotest.test.pageobject.shop.menus.desktop.SecMenusDesktop;
import com.mng.robotest.test.pageobject.utils.DataArticleGalery;
import com.mng.robotest.test.pageobject.utils.DataScroll;
import com.mng.robotest.test.pageobject.utils.ListDataArticleGalery;
import com.mng.robotest.test.steps.shop.galeria.LocationArticle;
import com.mng.robotest.test.steps.shop.galeria.PageGaleriaSteps.TypeActionFav;
import com.mng.robotest.test.utils.UtilsTest;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public abstract class PageGaleria extends PageBase {
	
	public enum From {MENU, BUSCADOR}
	
	public static final int MAX_PAGE_TO_SCROLL = 20;
	
	protected final From from;
	protected final String xpathArticuloBase;
	protected final SecPreciosArticulo secPrecios = new SecPreciosArticulo();

	protected PageGaleria() {
		super();
		this.from = From.MENU;
		this.xpathArticuloBase = getXPathArticulo();
	}
	
	protected PageGaleria(From from) {
		this.from = from;
		this.xpathArticuloBase = getXPathArticulo();
	}
	
	public abstract String getXPathLinkRelativeToArticle();
	public abstract int getLayoutNumColumnas();
	public abstract WebElement getArticuloConVariedadColoresAndHover(int numArticulo);
	public abstract WebElement getImagenElementArticulo(WebElement articulo) throws Exception;
	public abstract WebElement getColorArticulo(WebElement articulo, boolean selected, int numColor);
	public abstract ArticuloScreen getArticuloObject(int numArticulo) throws Exception;
	public abstract String getNombreArticulo(WebElement articulo);
	public abstract String getPrecioArticulo(WebElement articulo);
	public abstract boolean isArticleRebajado(WebElement articulo);
	public abstract String getCodColorArticulo(int numArticulo) throws Exception;
	public abstract String getNameColorFromCodigo(String codigoColor);
	public abstract int getNumFavoritoIcons();
	public abstract boolean eachArticlesHasOneFavoriteIcon();
	public abstract List<ArticuloScreen> clickArticleHearthIcons(List<Integer> posIconsToClick) throws Exception;
	public abstract boolean isArticleWithHearthIconPresentUntil(int posArticle, int seconds);
	public abstract void clickHearhIcon(int posArticle) throws Exception;
	public abstract String getRefColorArticulo(WebElement articulo);
	public abstract boolean backTo1erArticulo() throws InterruptedException;
	abstract String getXPathPagina(int pagina);
	abstract int getNumArticulosFromPagina(int pagina, TypeArticleDesktop sizeArticle);
	public abstract WebElement getArticleFromPagina(int numPagina, int numArticle);
	public abstract boolean isHeaderArticlesVisible(String textHeader);
	public abstract void showTallasArticulo(int posArticulo);
	public abstract boolean isVisibleArticleCapaTallasUntil(int posArticulo, int seconds);
	public abstract ArticuloScreen selectTallaAvailableArticle(int posArticulo, int posTalla) throws Exception;
	public abstract StateFavorito getStateHearthIcon(WebElement hearthIcon);
	public abstract void clickHearthIcon(WebElement hearthIcon) throws Exception;
	public abstract void hideMenus();

	protected static final List<LabelArticle> listLabelsNew = Arrays.asList(
			LabelArticle.ComingSoon, 
			LabelArticle.NewNow, 
			LabelArticle.NewCollection);
	
	static final String CLASS_PRODUCT_ITEM = 
			"@class[contains(.,'productList__name')] or " + 
			"@class[contains(.,'product-list-name')] or " + 
			"@class='product-list-info-name' or " +
			"@class[contains(.,'_1P8s4')] or " + //TODO (Outlet) a la espera que Sergio Campillo proporcione un identificador válido
			"@class[contains(.,'product-name')]";
	static final String XPATH_NOMBRE_RELATIVE_TO_ARTICLE = "//*[" + CLASS_PRODUCT_ITEM + "]";
	static final String XPATH_LINK_RELATIVE_TO_ARTICLE = ".//a[@class='product-link']";

	public static PageGaleria getNew(Channel channel) {
		return getNew(From.MENU, channel);
	}
	public static PageGaleria getNew(From from, Channel channel) {
		switch (channel) {
		case desktop:
			return new PageGaleriaDesktop(from);
		case mobile, tablet:
		default:
			return new PageGaleriaDevice(from);
		}
	}
	
	public Channel getChannel() {
		return channel;
	}
	
	public AppEcom getApp() {
		return app;
	}
	
	public SecPreciosArticulo getSecPreciosArticulo() {
		return secPrecios;
	}
	
	static final String XPATH_ARTICULO_DESKTOP = "//li[@id[contains(.,'product-key-id')]]";
	
	//TODO adaptar React (pendiente petición a Jesús Bermúdez 3-Marzo-2021)
	private static final String XPATH_ARTICULO_DEVICE = "//li[@class='product']";
	private static final String XPATH_ARTICULO_TABLET_OUTLET = XPATH_ARTICULO_DESKTOP;
	private String getXPathArticulo() {
		if (channel==Channel.desktop) {
			return XPATH_ARTICULO_DESKTOP;
		}
		else {
			if (channel==Channel.tablet &&
			   (app==AppEcom.outlet || from==From.BUSCADOR)) {
				return XPATH_ARTICULO_TABLET_OUTLET;
			}
			return XPATH_ARTICULO_DEVICE;
		}
	}
	
	private static final String XPATH_HEARTH_ICON_RELATIVE_ARTICLE_DESKTOP = "//span[@class[contains(.,'icon-favorite')]]";
	private static final String XPATH_HEARTH_ICON_RELATIVE_ARTICLE_MOVIL = "//span[@class[contains(.,'product-favorite')]]";
	String getXPathHearthIconRelativeArticle() {
		switch (channel) {
		case desktop:
			return XPATH_HEARTH_ICON_RELATIVE_ARTICLE_DESKTOP;
		case mobile:
		default:
			return XPATH_HEARTH_ICON_RELATIVE_ARTICLE_MOVIL;
		}
	}
	
	String getXPathArticleHearthIcon(int posArticulo) {
		String xpathArticulo = "(" + xpathArticuloBase + ")[" + posArticulo + "]";
		return (xpathArticulo + getXPathHearthIconRelativeArticle());
	}
	
	String getXPathArticuloNoDoble() {
		return (
			xpathArticuloBase + 
			"//self::*[not(@class[contains(.,'layout-2-coumns-A2')])]");
	}
	
	String getXPathAncestorArticulo() {
		return (xpathArticuloBase.replaceFirst("//", "ancestor::"));
	}
	
	static String classProductName = 
			"(@class[contains(.,'productList__name')] or " +
			 "@class[contains(.,'product-list-name')] or " + 
			 "@class='product-list-info-name' or " +
			 "@class='product-name')";

	public String getXPathCabeceraBusquedaProd() {
		return ("//*[@id='buscador_cabecera2']");
	}
	
	String getXPathLinkArticulo(int numArticulo) {
		return ("(" + xpathArticuloBase + ")[" + numArticulo + "]//a");
	}  

	public boolean isVisibleArticuloUntil(int numArticulo, int seconds) {
		String xpathArticulo = xpathArticuloBase + "[" + numArticulo + "]"; 
		return state(Visible, xpathArticulo).wait(seconds).check();
	}

	public boolean isClickableArticuloUntil(int numArticulo, int seconds) {
		String xpathArticulo = xpathArticuloBase + "[" + numArticulo + "]"; 
		return state(Clickable, xpathArticulo).wait(seconds).check();
	}
	
	public List<WebElement> getListaArticulos() {
		return getElements(xpathArticuloBase);
	}
	
	public boolean articlesInOrder(FilterOrdenacion typeOrden) throws Exception {
		return ("".compareTo(getAnyArticleNotInOrder(typeOrden))==0);
	}

	public void hoverArticle(WebElement article) {
		moveToElement(article);
	}
	
	/**
	 * @return número de artículos de la galería
	 */
	public int getNumArticulos() {
		return getElements(xpathArticuloBase).size();
	}	
	
	public int waitForArticleVisibleAndGetNumberOfThem(int seconds) {
		int numArticle = 1;
		isVisibleArticleUntil(numArticle, seconds);
		return (getNumArticulos());
	}
	
	public boolean waitArticleAndGoTo(int numArticulo, int seconds) { 
		String xpathUltArticulo = getXPathLinkArticulo(numArticulo);
		if (isVisibleArticleUntil(numArticulo, seconds)) {
			((Locatable)getElement(xpathUltArticulo)).getCoordinates().inViewPort();
			return true;
		}
		
		return false;
	}
	
	public boolean isVisibleArticleUntil(int numArticulo, int seconds) {
		String xpathArtGaleria = "(" + xpathArticuloBase + ")[" + numArticulo + "]";
		return state(Visible, xpathArtGaleria).wait(seconds).check();
	}
	
	public boolean isFirstArticleOfType(LineaType lineaType) {
		List<WebElement> listaArticulos = getElements(xpathArticuloBase);
		return (
			!listaArticulos.isEmpty() &&
			state(Present, listaArticulos.get(0))
				.by(By.xpath("//a[@href[contains(.,'" + lineaType + "')]]")).check());
	}
	
	public void moveToArticleAndGetObject(int posArticulo) {
		moveToElement(getXPathLinkArticulo(posArticulo) + "/..");
	}
	
	/**
	 * Indica si los artículos de la galería realmente están ordenados por precio ascendente o descendente
	 */
	public String getAnyArticleNotInOrder(FilterOrdenacion typeOrden) throws Exception {
		switch (typeOrden) {
		case NOordenado:
			return "";
		case PrecioAsc, PrecioDesc:
			return secPrecios.getAnyPrecioNotInOrder(typeOrden, getListaArticulos());
		case TemporadaDesc, TemporadaAsc, BloqueTemporadas_3y4_despues_la_5, BloqueTemporada_5_despues_la_3y4:
			return getAnyRefNotInOrderTemporada(typeOrden);
		default:
			return "";
		}
	}

	public boolean preciosInIntervalo(int minimo, int maximo) throws Exception {
		boolean inInterval = false;
		for (int i=0; i<3; i++) {
			try {
				inInterval = secPrecios.preciosInIntervalo(minimo, maximo, getListaArticulos());
				break;
			}
			catch (StaleElementReferenceException e) {
				Log4jTM.getLogger().info("StaleElementReferenceException checking articles filtered by prize");
			}
		}
		return inInterval;
}

	
	public String getAnyRefNotInOrderTemporada(FilterOrdenacion typeOrden) {
		List<String> listaReferencias = getListaReferenciasPrendas();
		String refAnterior="";
		for (String refActual : listaReferencias) {
			String tempActual = refActual.substring(0,1);
			if ("".compareTo(refAnterior)!=0) {
				String tempAnterior = refAnterior.substring(0,1);
				int tempActualInt = Integer.parseInt(tempActual);
				int tempAnteriorInt = Integer.parseInt(tempAnterior);
				switch (typeOrden) {
				case TemporadaDesc:
					if (tempActualInt > tempAnteriorInt) {
						return (refAnterior + "->" + refActual);
					}
					break;
				case TemporadaAsc:
					if (tempActualInt < tempAnteriorInt) {
						return (refAnterior + "->" + refActual);
					}
					break;
				case BloqueTemporadas_3y4_despues_la_5:
					if ((tempActualInt==1 || tempActualInt==2) && tempAnteriorInt==3) {
						return (refAnterior + "->" + refActual);
					}
					break;
				case BloqueTemporada_5_despues_la_3y4:
				default:
					if (tempActualInt==3 && (tempAnteriorInt==1 || tempAnteriorInt==2)) {
						return (refAnterior + "->" + refActual);
					}
					break;
				}
			}
			
			refAnterior = refActual;
		}

		return "";
	}

	/**
	 * @return la lista de elementos que contienen la referencia del artículo
	 */
	public List<String> getListaReferenciasPrendas() {
		List<String> listaReferencias = new ArrayList<>();
		List<WebElement> listaArticulos = getArticulos();
		for (WebElement articulo : listaArticulos)
			listaReferencias.add(getRefArticulo(articulo));
		
		return listaReferencias;
	}
	
	public String getReferencia(int posArticle) {
		if (!getListaReferenciasPrendas().isEmpty()) {
			return (getListaReferenciasPrendas().get(posArticle-1));
		}
		return "";
	}
	
	public List<WebElement> getArticulos() {
		return getElements(xpathArticuloBase); 
	}
	
	public WebElement getArticulo(LocationArticle locationArt) {
		switch (locationArt.accessFrom) {
		case InitCatalog:
			return (getArticulo(locationArt.numArticle));
		case InitPage:
		default:
			return (getArticleFromPagina(locationArt.numPage, locationArt.numArticle));
		}
	}

	public WebElement getArticulo(int numArticulo) {
		List<WebElement> listArticulos = getArticulos();
		if (listArticulos.size()>=numArticulo) {
			return (listArticulos.get(numArticulo-1));
		}
		return null;
	}

	public String getRefFromId(WebElement articulo) {
		String id = articulo.getAttribute("id");
		return (id.replace("product-key-id-", ""));
	}

	public String getRefArticulo(WebElement articulo) {
		int lengthReferencia = 8;
		String id = getRefFromId(articulo);
		if ("".compareTo(id)!=0) {
			if (id.length()>lengthReferencia) {
				return (id.substring(0, lengthReferencia));
			}
			return id;
		}
		
		//Para el caso TestAB-1 se ejecutará este caso para conseguir los atributos del artículo
		String href = articulo.findElement(By.xpath(XPATH_LINK_RELATIVE_TO_ARTICLE)).getAttribute("href");
		return UtilsTest.getReferenciaFromHref(href);
	}

	public boolean waitToHearthIconInState(WebElement hearthIcon, StateFavorito stateIcon, int seconds) {
		for (int i=0; i<seconds; i++) {
			if (getStateHearthIcon(hearthIcon)==stateIcon) {
				return true;
			}
			waitMillis(1000);
		}
		return false;
	}

	private enum AttributeArticle { NOMBRE, REFERENCIA, IMAGEN }
	
	public List<DataArticleGalery> searchArticleRepeatedInGallery() throws Exception {
		ListDataArticleGalery list = getListArticles(Arrays.asList(
				AttributeArticle.NOMBRE, 
				AttributeArticle.REFERENCIA));
		
		if (!list.getArticlesRepeated().isEmpty()) {
			//Obtener la imagen de cada artículo es muy costoso así que sólo lo hacemos en este caso
			list = getListArticles(list.getArticlesRepeated(),
					Arrays.asList(
					    AttributeArticle.NOMBRE, 
					    AttributeArticle.REFERENCIA,
					    AttributeArticle.IMAGEN));
		}
		return (list.getArticlesRepeated());
	}
	
	public ListDataArticleGalery getListDataArticles() throws Exception {
		return getListArticles(Arrays.asList(
				AttributeArticle.NOMBRE, 
				AttributeArticle.REFERENCIA));
	}
	
	private ListDataArticleGalery getListArticles(List<AttributeArticle> attributes) throws Exception {
		return getListArticles(null, attributes);
	}
	
	private ListDataArticleGalery getListArticles(
			List<DataArticleGalery> filter,
			List<AttributeArticle> attributes) throws Exception {
		ListDataArticleGalery listReturn = new ListDataArticleGalery();
		for (WebElement articulo : getListaArticulos()) {
			String refColor = getRefColorArticulo(articulo);
			if (filter==null ||	isPresentArticleWithReferencia(filter, refColor)) {
				listReturn.add(getDataArticulo(articulo, attributes));
			}
		}
		return listReturn;
	}
	
	private boolean isPresentArticleWithReferencia(List<DataArticleGalery> listArticles, String referencia) {
		return listArticles.stream()
				.anyMatch(a -> a.getReferencia().compareTo(referencia)==0);
	}
	
	private DataArticleGalery getDataArticulo(WebElement articulo, List<AttributeArticle> attributes) 
	throws Exception {
		DataArticleGalery dataArticle = new DataArticleGalery();
		for (AttributeArticle attribute : attributes) {
			switch (attribute) {
			case NOMBRE:
				dataArticle.setNombre(getNombreArticulo(articulo));
				break;
			case REFERENCIA:
				dataArticle.setReferencia(getRefColorArticulo(articulo));
				break;
			case IMAGEN:
				dataArticle.setImagen(getImagenArticulo(articulo));
				break;
			}
		}
		return dataArticle;
	}

	public enum StateFavorito { MARCADO, DESMARCADO } 
	public boolean iconsInCorrectState(List<Integer> posIconosFav, TypeActionFav typeAction) {
		for (int posIcon : posIconosFav) {
			String xPathIcon = getXPathArticleHearthIcon(posIcon);
			WebElement hearthIcon = getElement(xPathIcon);
			switch (typeAction) {
			case MARCAR:
				if (getStateHearthIcon(hearthIcon)!=StateFavorito.MARCADO) {
					return false;
				}
				break;
			case DESMARCAR:
				if (getStateHearthIcon(hearthIcon)!=StateFavorito.DESMARCADO) {
					return false;
				}
				break;
			default:
				break;
			}
		}
		
		return true;
	}
	
	public boolean backTo1erArticulo(String xpathIconoUpGalery) throws InterruptedException {
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-50)", ""); //Asure icon showed
		clickIconoUpToGaleryIfVisible(xpathIconoUpGalery);
		boolean isVisible1erArt = isVisibleArticuloUntil(1, 2);
		Thread.sleep(1000);
		return isVisible1erArt;
	}
	
	public void clickIconoUpToGaleryIfVisible(String xpathIconoUpGalery) {
		if (state(Visible, xpathIconoUpGalery).wait(1).check()) {
			getElement(xpathIconoUpGalery).click();
		}
	}

	public String getNombreArticuloWithText(String literal, int secondsWait) {
		WebElement articulo = getArticleThatContainsLitUntil(literal, secondsWait);
		if (articulo!=null) {
			return getNombreArticulo(articulo);
		}
		return "";
	}
	
	public WebElement getArticleThatContainsLitUntil(String literal, int seconds) {
		By byArticleName = By.xpath(
				xpathArticuloBase + 
				XPATH_NOMBRE_RELATIVE_TO_ARTICLE + 
				"//self::*[text()[contains(.,'" + literal + "')]]");
		if (state(Present, byArticleName).wait(seconds).check()) {
			return getElement(xpathArticuloBase);
		}
		return null;
	}
	
	/**
	 * Función que realiza un scroll/paginación hasta el final de los artículos. Retorna el número de elementos obtenidos
	 * Desktop: scrolla reiteradamente hasta el último elemento para forzar la paginación
	 */
	public DataScroll scrollToPageFromFirst(int numPage) throws Exception {
		DataScroll datosScroll = new DataScroll();
		int pageToScroll = getPageToScroll(numPage);
		goToInitPageAndWaitForArticle();
		
		int lastPage = getNumLastPage();
		List<Integer> numArticlesXpage = new ArrayList<>();
		List<Integer> numArticlesDoubleXpage = new ArrayList<>();
		initializeDataNumArticles(numArticlesXpage, numArticlesDoubleXpage, pageToScroll + 10);
		updateDataNumArticles(numArticlesXpage, numArticlesDoubleXpage, lastPage);
		SecFooter secFooter = new SecFooter();
		while (lastPage < pageToScroll) {
			goToLastPage();
			int newLastPage = getNumLastPage();
			updateDataNumArticles(numArticlesXpage, numArticlesDoubleXpage, newLastPage);
			if (newLastPage==lastPage) {
				break;
			}
			lastPage=newLastPage;
		}
		
		if (secFooter.isVisible()) {
			secFooter.moveTo();
		}
		
		datosScroll.paginaFinal = lastPage;
		datosScroll.finalAlcanzado = secFooter.isVisible();
		datosScroll.articulosMostrados = getNumArticulos();
		datosScroll.articulosDobleTamano = getTotalNumArticles(numArticlesDoubleXpage);
		datosScroll.articulosTotalesPagina = getTotalNumArticles(numArticlesXpage);
		return (datosScroll);
	}
	
	private int getPageToScroll(int numPage) {
		if (numPage > MAX_PAGE_TO_SCROLL) {
			return MAX_PAGE_TO_SCROLL;
		}
		return numPage;
	}

	public void goToInitPageAndWaitForArticle() {
		//TODO en estos momentos algo raro le pasa al menú Nuevo que requiere un refresh para funcionar ok
		driver.navigate().refresh();
		int seconds = 2;
		isVisibleArticleUntil(1, seconds);
	}
	
	private void initializeDataNumArticles(List<Integer> numArticlesXpage, List<Integer> numArticlesDoubleXpage, int maxPages) {
		for (int i=0; i<=maxPages; i++) {
			numArticlesXpage.add(i, 0);
			numArticlesDoubleXpage.add(i, 0);
		}
	}
	
	private void updateDataNumArticles(List<Integer> numArticlesXpage, List<Integer> numArticlesDoubleXpage,
									   int lastPage) {
		int page = lastPage;
		while (page>0) { 
			if (isPresentPagina(page)) {
				if (numArticlesDoubleXpage.get(page)==0) {
					int numArticlesPage = getNumArticulosFromPagina(page, TypeArticleDesktop.DOBLE);
					numArticlesDoubleXpage.set(page, numArticlesPage);
				}
			
				if (numArticlesXpage.get(page)==0) {
					int numArticlesPage = getNumArticulosFromPagina(page, TypeArticleDesktop.SIMPLE) + 
										  numArticlesDoubleXpage.get(page);
					numArticlesXpage.set(page, numArticlesPage);
				}
			}
		
			page-=1;
		}
	}
	
	private int getTotalNumArticles(List<Integer> numArticlesXpage) {
		int numTotalArticles = 0;
		for (int i=0; i<numArticlesXpage.size(); i++) {
			numTotalArticles+=numArticlesXpage.get(i);
		}
		return numTotalArticles;
	}
	
	private void goToLastPage() throws Exception {
		goToPage(99);
	}
	
	private void goToPage(int numPageToGo) {
		boolean lastPageReached = false;
		int paginaActual = 1;
		while (!lastPageReached && paginaActual<numPageToGo) {
			By byPagina = By.xpath(getXPathPagina(paginaActual));
			if (state(Visible, byPagina).check()) {
				moveToElement(byPagina);
				((JavascriptExecutor) driver).executeScript("window.scrollBy(0,+50)", "");
				paginaActual+=1;
			} else {
				lastPageReached = true;
			}
		}
		waitAndGotoLastArticle();
		waitLoadPage();
	}
	
	private void waitAndGotoLastArticle() {
		List<WebElement> listaArticulos = getListaArticulos();
		int seconds = 5;
		waitArticleAndGoTo(listaArticulos.size(), seconds);
	}
	
	public int getNumLastPage() {
		int maxPagesToReview = 20;
		int lastPageWatched = 0;
		boolean aPageLocatedYet = false;
		while (lastPageWatched<maxPagesToReview) {
			int pageToReview = lastPageWatched + 1;
			boolean isPresentPage = isPresentPagina(pageToReview);
			if (isPresentPage) {
				aPageLocatedYet = true;
			} else {
				if (aPageLocatedYet) {
					return lastPageWatched;
				}
			}
			lastPageWatched=pageToReview;
		}
		
		return 0;
	}
	
	public boolean isPresentPagina(int pagina) {
		return state(Visible, getXPathPagina(pagina)).check();
	}

	public void clickArticulo(WebElement articulo) {
		moveToElement(articulo);
		click(articulo).waitLoadPage(30).exec();
	}

	public String openArticuloPestanyaAndGo(WebElement article, AppEcom app) 
			throws Exception {
		String galeryWindowHandle = driver.getWindowHandle();
		if (channel==Channel.desktop) {
			//En el caso de Firefox-Geckodriver el moveToElement (que se acaba realizando mediante el workarround basado en JavaScript) 
			//nos posiciona en la esquina superior izquierda que queda debajo del menú superior... así que tenemos que enviar dicho menú al fondo
			SecMenusDesktop secMenus = new SecMenusDesktop();
			secMenus.secMenuSuperior.secLineas.bringMenuBackground();
		}
		
		new UtilsMangoTest().openLinkInNewTab(article);
		
		//Cambiamos el foco de driver a la nueva pestaña que hemos creado y esperamos hasta que está disponible
		String detailWindowHandle = switchToAnotherWindow(driver, galeryWindowHandle);
		
		PageFicha pageFicha = PageFicha.of(channel);
		pageFicha.isPageUntil(10);
		
		return detailWindowHandle;
	}	
	
	public static List<String> getNotNewArticlesFrom(List<String> listNameAndLabelArticles) {
		List<String> listArtToReturn = new ArrayList<>();
		for (String nameAndLabelArticle : listNameAndLabelArticles) {
			if (!isArticleNew(nameAndLabelArticle)) {
				listArtToReturn.add(nameAndLabelArticle);
			}
		}
		   
		return listArtToReturn;
	}

	private static boolean isArticleNew(String nameAndLabelArticle) {
		for (LabelArticle label : listLabelsNew) {
			for (String labelNew : label.getListTraducciones()) {
				if (nameAndLabelArticle.contains(labelNew) || nameAndLabelArticle.contains(labelNew.toUpperCase())) {
					return true;
				}
			}
		}
		return false;
	}
	
	public String getImagenArticulo(WebElement articulo) throws Exception {
		waitLoadPage();
		WebElement imagen = getImagenElementArticulo(articulo);
		if (imagen!=null) {
			try {
				if (app==AppEcom.outlet) {
					return imagen.getAttribute("src");
				}
				//TODO Test AB nueva variante. Si se mantiene la original igualar con Outlet
				return imagen.getAttribute("original");
			}
			catch (StaleElementReferenceException e) {
				imagen = getImagenElementArticulo(articulo);
				if (imagen!=null) {
					return imagen.getAttribute("src");
				}
			}
		}
		return "";
	}

	public static List<LabelArticle> getListlabelsnew() {
		return listLabelsNew;
	}
}
