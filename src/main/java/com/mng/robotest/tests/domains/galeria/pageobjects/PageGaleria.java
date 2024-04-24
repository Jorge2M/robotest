package com.mng.robotest.tests.domains.galeria.pageobjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Locatable;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.ficha.pageobjects.PageFicha;
import com.mng.robotest.tests.domains.footer.pageobjects.SecFooter;
import com.mng.robotest.tests.domains.galeria.pageobjects.PageGaleriaDesktop.TypeArticleDesktop;
import com.mng.robotest.tests.domains.galeria.pageobjects.filters.FilterOrdenacion;
import com.mng.robotest.tests.domains.galeria.pageobjects.article.LabelArticle;
import com.mng.robotest.tests.domains.galeria.pageobjects.article.SecPreciosArticulo;
import com.mng.robotest.tests.domains.galeria.pageobjects.article.SecTallasArticulo;
import com.mng.robotest.tests.domains.galeria.pageobjects.entities.TypeSlider;
import com.mng.robotest.tests.domains.galeria.pageobjects.filters.SecFiltros;
import com.mng.robotest.tests.domains.galeria.steps.GaleriaSteps.TypeActionFav;
import com.mng.robotest.tests.domains.menus.pageobjects.LineaWeb.LineaType;
import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.data.Color;
import com.mng.robotest.testslegacy.generic.UtilsMangoTest;
import com.mng.robotest.testslegacy.generic.beans.ArticuloScreen;
import com.mng.robotest.testslegacy.pageobject.utils.DataArticleGalery;
import com.mng.robotest.testslegacy.pageobject.utils.DataScroll;
import com.mng.robotest.testslegacy.pageobject.utils.ListDataArticleGalery;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.mng.robotest.tests.domains.galeria.pageobjects.PageGaleria.AttributeArticle.*;
import static com.mng.robotest.tests.domains.galeria.pageobjects.filters.FilterOrdenacion.*;
import static com.mng.robotest.tests.domains.galeria.pageobjects.PageGaleria.StateFavorito.*;

public abstract class PageGaleria extends PageBase {

	public enum From { MENU, BUSCADOR }
	enum AttributeArticle { NOMBRE, REFERENCIA, IMAGEN }

	public static final int MAX_PAGE_TO_SCROLL = 20;

	protected final SecTallasArticulo secTallas = SecTallasArticulo.make(channel, dataTest.getPais());
	protected final SecPreciosArticulo secPrecios = new SecPreciosArticulo();
	protected final SecFiltros secFiltros = SecFiltros.make(channel, app, dataTest.getPais());

	protected PageGaleria() {
		super();
	}

	protected abstract String getXPathArticulo();
	protected abstract String getXPathNombreRelativeToArticle();
	
	public abstract int getLayoutNumColumnas();
	public abstract WebElement getArticuloConVariedadColoresAndHover(int numArticulo);
	public abstract WebElement getImagenElementArticulo(WebElement articulo);
	public abstract ArticuloScreen getArticuloObject(int numArticulo) throws Exception;
	public abstract String getNombreArticulo(WebElement articulo);
	public abstract String getPrecioArticulo(WebElement articulo);
	public abstract boolean isArticleRebajado(WebElement articulo);
	public abstract String getCodColorArticulo(int numArticulo) throws Exception;
	public abstract String getNameColorFromCodigo(String codigoColor);
	public abstract void clickColorArticulo(WebElement articulo, int posColor);
	public abstract int getNumFavoritoIcons();
	public abstract List<ArticuloScreen> clickArticleHearthIcons(Integer... posIconsToClick) throws Exception;
	public abstract boolean isArticleWithHearthIconPresentUntil(int posArticle, int seconds);
	public abstract void clickHearhIcon(int posArticle) throws Exception;
	public abstract String getRefArticulo(WebElement articulo);
	public abstract String getRefColorArticulo(WebElement articulo);
	public abstract boolean backTo1erArticulo() throws InterruptedException;
	abstract String getXPathPagina(int pagina);
	abstract int getNumArticulosFromPagina(int pagina, TypeArticleDesktop sizeArticle);
	public abstract WebElement getArticleFromPagina(int numPagina, int numArticle);
	public abstract boolean isHeaderArticlesVisible(String textHeader);
	public abstract void showTallasArticulo(int posArticulo);
	public abstract ArticuloScreen selectTallaAvailableArticle(int posArticulo) throws Exception;
	public abstract void selectTallaArticleNotAvalaible();
	public abstract void clickHearthIcon(WebElement hearthIcon) throws Exception;
	public abstract void clickSlider(WebElement articulo, TypeSlider typeSlider);

	public enum StateFavorito { 
		MARCADO, 
		DESMARCADO;
		
		public StateFavorito getOpposite() {
			if (this==MARCADO) {
				return DESMARCADO;
			}
			return MARCADO;
		}
	}
	
	private static final List<LabelArticle> LIST_LABELS_NEW = Arrays.asList(
			LabelArticle.COMING_SOON,
			LabelArticle.NEW_NOW,
			LabelArticle.NEW_COLLECTION);

	protected static final String XP_LINK_RELATIVE_TO_ARTICLE = ".//a[@class='product-link']";
	protected static final String XP_HEARTH_ICON_RELATIVE_ARTICLE = "//*[@data-testid='button-icon']";

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
				return new PageGaleriaDesktopNormal();
			case mobile, tablet:
			default:
				if (from==From.BUSCADOR) {
					return new PageGaleriaDeviceOld();
				}
				if (pais.isGaleriaGenesis(app)) {
					return new PageGaleriaDeviceGenesis();
				}
				return new PageGaleriaDeviceNormal();
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

	private String getXPathArticulo(int numArticulo) {
		return "(" + getXPathArticulo() + ")[" + numArticulo + "]";
	}	

	String getXPathArticleHearthIcon(int posArticulo) {
		String xpathArticulo = "(" + getXPathArticulo() + ")[" + posArticulo + "]";
		return (xpathArticulo + XP_HEARTH_ICON_RELATIVE_ARTICLE);
	}

	String getXPathArticuloNoDoble() {
		return (getXPathArticulo() +
				"//self::*[not(@class[contains(.,'layout-2-coumns-A2')])]");
	}

	public String getXPathCabeceraBusquedaProd() {
		return "//*[@id='buscador_cabecera2']";
	}

	String getXPathLinkArticulo(int numArticulo) {
		return getXPathArticulo(numArticulo) + "//a";
	}

	public boolean isVisibleArticuloUntil(int numArticulo, int seconds) {
		String xpathArticulo = "(" + getXPathArticulo() + ")[" + numArticulo + "]";
		return state(VISIBLE, xpathArticulo).wait(seconds).check();
	}

	public boolean isClickableArticuloUntil(int numArticulo, int seconds) {
		String xpathArticulo = "(" + getXPathArticulo() + ")[" + numArticulo + "]";
		return state(CLICKABLE, xpathArticulo).wait(seconds).check();
	}

	public List<WebElement> getListaArticulos() {
		return getElements(getXPathArticulo());
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
		return getElements(getXPathArticulo()).size();
	}

	public int waitForArticleVisibleAndGetNumberOfThem(int seconds) {
		int numArticle = 1;
		isVisibleArticleUntil(numArticle, seconds);
		return (getNumArticulos());
	}

	public boolean waitArticleAndGoTo(int numArticulo, int seconds) {
		if (isVisibleArticleUntil(numArticulo, seconds)) {
			moveToArticle(numArticulo);
			return true;
		}
		return false;
	}

	public boolean isVisibleArticleUntil(int numArticulo, int seconds) {
		return state(VISIBLE, getXPathArticulo(numArticulo)).wait(seconds).check();
	}
	public void moveToArticle(int numArticulo) {
		moveToArticle(getElement(getXPathArticulo(numArticulo)));
	}
	public void moveToArticle(String xpathArticle) {
		moveToArticle(getElement(xpathArticle));
	}
	public void moveToArticle(WebElement article) {
		((Locatable)article).getCoordinates().inViewPort();
	}	

	public boolean isFirstArticleOfType(LineaType lineaType) {
		var listaArticulos = getElements(getXPathArticulo());
		return (!listaArticulos.isEmpty() &&
				state(PRESENT, listaArticulos.get(0))
					.by(By.xpath("//a[@href[contains(.,'" + lineaType + "')]]")).check());
	}

	public void moveToArticleAndGetObject(int posArticulo) {
		moveToElement(getXPathLinkArticulo(posArticulo) + "/..");
	}
	
	/**
	 * Indica si los artículos de la galería realmente están ordenados por precio ascendente o descendente
	 */
	public String getAnyArticleNotInOrder(FilterOrdenacion typeOrden) throws Exception {
		if (typeOrden==PRECIO_ASC || typeOrden==PRECIO_DESC) {
			return secPrecios.getAnyPrecioNotInOrder(typeOrden, getListaArticulos());
		}
		return "";
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

	/**
	 * @return la lista de elementos que contienen la referencia del artículo
	 */
	public List<String> getListaReferenciasPrendas() {
		List<String> listaReferencias = new ArrayList<>();
		for (var articulo : getArticulos()) {
			listaReferencias.add(getRefArticulo(articulo));
		}
		return listaReferencias;
	}

	public String getReferencia(int posArticle) {
		if (!getListaReferenciasPrendas().isEmpty()) {
			return (getListaReferenciasPrendas().get(posArticle-1));
		}
		return "";
	}

	public List<WebElement> getArticulos() {
		return getElements(getXPathArticulo());
	}

	public WebElement getArticulo(int numArticulo) {
		var listArticulos = getArticulos();
		if (listArticulos.size()>=numArticulo) {
			return (listArticulos.get(numArticulo-1));
		}
		return null;
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

	public List<DataArticleGalery> searchArticleRepeatedInGallery() {
		var list = getListArticles(Arrays.asList(NOMBRE, REFERENCIA));
		if (!list.getArticlesRepeated().isEmpty()) {
			//Obtener la imagen de cada artículo es muy costoso así que sólo lo hacemos en este caso
			list = getListArticles(
					list.getArticlesRepeated(),
					Arrays.asList(NOMBRE, REFERENCIA, IMAGEN));
		}
		return list.getArticlesRepeated();
	}

	public ListDataArticleGalery getListDataArticles() {
		return getListArticles(Arrays.asList(NOMBRE, REFERENCIA));
	}

	private ListDataArticleGalery getListArticles(List<AttributeArticle> attributes) {
		return getListArticles(null, attributes);
	}

	private ListDataArticleGalery getListArticles(
			List<DataArticleGalery> filter, List<AttributeArticle> attributes) {
		var listReturn = new ListDataArticleGalery();
		for (var articulo : getListaArticulos()) {
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

	private DataArticleGalery getDataArticulo(WebElement articulo, List<AttributeArticle> attributes) {
		moveToArticle(articulo);
		var dataArticle = new DataArticleGalery();
		for (var attribute : attributes) {
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

	public boolean iconsInCorrectState(TypeActionFav typeAction, Integer... posIconosFav) {
		for (int posIcon : posIconosFav) {
			String xPathIcon = getXPathArticleHearthIcon(posIcon);
			var hearthIcon = getElement(xPathIcon);
			switch (typeAction) {
				case MARCAR:
					if (getStateHearthIcon(hearthIcon)!=MARCADO) {
						return false;
					}
					break;
				case DESMARCAR:
					if (getStateHearthIcon(hearthIcon)!=DESMARCADO) {
						return false;
					}
					break;
				default:
					break;
			}
		}

		return true;
	}

	public boolean backTo1erArticulo(String xpathIconoUpGalery) {
		scrollEjeY(-50); //Assure icon showed
		clickIconoUpToGaleryIfVisible(xpathIconoUpGalery);
		boolean isVisible1erArt = isVisibleArticuloUntil(1, 2);
		waitMillis(1000);
		return isVisible1erArt;
	}

	public void clickIconoUpToGaleryIfVisible(String xpathIconoUpGalery) {
		if (state(VISIBLE, xpathIconoUpGalery).wait(1).check()) {
			getElement(xpathIconoUpGalery).click();
		}
	}

	public String getNombreArticuloWithText(String literal, int secondsWait) {
		var articulo = getArticleThatContainsLitUntil(literal, secondsWait);
		if (articulo!=null) {
			return getNombreArticulo(articulo);
		}
		return "";
	}

	public WebElement getArticleThatContainsLitUntil(String literal, int seconds) {
		By byArticleName = By.xpath(
				getXPathArticulo() +
				getXPathNombreRelativeToArticle() +
				"//self::*[text()[contains(.,'" + literal + "')]]");
		
		if (state(PRESENT, byArticleName).wait(seconds).check()) {
			return getElement(getXPathArticulo());
		}
		return null;
	}

	/**
	 * Función que realiza un scroll/paginación hasta el final de los artículos. Retorna el número de elementos obtenidos
	 * Desktop: scrolla reiteradamente hasta el último elemento para forzar la paginación
	 */
	public DataScroll scrollToPageFromFirst(int numPage) {
		var datosScroll = new DataScroll();
		int pageToScroll = getPageToScroll(numPage);
		goToInitPageAndWaitForArticle();

		int lastPage = getNumLastPage();
		List<Integer> numArticlesXpage = new ArrayList<>();
		List<Integer> numArticlesDoubleXpage = new ArrayList<>();
		initializeDataNumArticles(numArticlesXpage, numArticlesDoubleXpage, pageToScroll + 10);
		updateDataNumArticles(numArticlesXpage, numArticlesDoubleXpage, lastPage);
		var secFooter = new SecFooter();
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

		datosScroll.setPaginaFinal(lastPage);
		datosScroll.setFinalAlcanzado(secFooter.isVisible());
		datosScroll.setArticulosMostrados(getNumArticulos());
		datosScroll.setArticulosDobleTamano(getTotalNumArticles(numArticlesDoubleXpage));
		datosScroll.setArticulosTotalesPagina(getTotalNumArticles(numArticlesXpage));
		return (datosScroll);
	}

	private int getPageToScroll(int numPage) {
		if (numPage > MAX_PAGE_TO_SCROLL) {
			return MAX_PAGE_TO_SCROLL;
		}
		return numPage;
	}

	public void goToInitPageAndWaitForArticle() {
		//Algo raro le pasa al menú Nuevo que requiere un refresh para funcionar ok
		driver.navigate().refresh();
		isVisibleArticleUntil(1, 10);
	}

	private void initializeDataNumArticles(List<Integer> numArticlesXpage, List<Integer> numArticlesDoubleXpage, int maxPages) {
		for (int i=0; i<=maxPages; i++) {
			numArticlesXpage.add(i, 0);
			numArticlesDoubleXpage.add(i, 0);
		}
	}

	private void updateDataNumArticles(
			List<Integer> numArticlesXpage, List<Integer> numArticlesDoubleXpage, int lastPage) {
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

	private void goToLastPage() {
		goToPage(99);
	}

	private void goToPage(int numPageToGo) {
		boolean lastPageReached = false;
		int paginaActual = 1;
		while (!lastPageReached && paginaActual<numPageToGo) {
			By byPagina = By.xpath(getXPathPagina(paginaActual));
			if (state(VISIBLE, byPagina).check()) {
				moveToElement(byPagina);
				scrollEjeY(+50);
				paginaActual+=1;
			} else {
				lastPageReached = true;
			}
		}
		waitAndGotoLastArticle();
		waitLoadPage();
	}

	private void waitAndGotoLastArticle() {
		int seconds = 5;
		waitArticleAndGoTo(getListaArticulos().size(), seconds);
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
		return state(VISIBLE, getXPathPagina(pagina)).check();
	}

	public void clickArticulo(WebElement articulo) {
		forceVisibilityImageArticle(articulo);
		if (isDesktop() && isOutlet()) {
			//En Desktop-Outlet la cabecera tapa en ocasiones el artículo  
			scrollEjeY(-300);
		}
		click(articulo).waitLoadPage(30).exec();
	}
	
	private void forceVisibilityImageArticle(WebElement articulo) {
		var originalSize = driver.manage().window().getSize();
		for (int i=1; i<5; i++) {
			if (isVisibleImageArticle(articulo, 2)) {
				return;
			}
			driver.manage().window().setSize(new Dimension(i*200,i*200));
			driver.manage().window().setSize(originalSize);
			scrollEjeY(-800);
			moveToElement(articulo);
		}
	}
	
	public boolean isVisibleImageArticle(int numArticulo, int seconds) {
		var articulo = getElement(getXPathArticulo(numArticulo));
		return isVisibleImageArticle(articulo, seconds);
	}
	
	private boolean isVisibleImageArticle(WebElement articulo, int seconds) {
		for (int i=0; i<seconds; i++) {
			if (getImagenElementArticulo(articulo)!=null) {
				return true;
			}
			waitMillis(1000);
		}
		return false;
	}

	public String openArticuloPestanyaAndGo(WebElement article) {
		String galeryWindowHandle = driver.getWindowHandle();
		forceVisibilityImageArticle(article);
		new UtilsMangoTest().openLinkInNewTab(article);

		//Cambiamos el foco de driver a la nueva pestaña que hemos creado y esperamos hasta que está disponible
		String detailWindowHandle = switchToAnotherWindow(driver, galeryWindowHandle);

		var pageFicha = PageFicha.make(channel, app, dataTest.getPais());
		pageFicha.isPage(10);

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
		for (var label : LIST_LABELS_NEW) {
			for (String labelNew : label.getListTraducciones()) {
				if (nameAndLabelArticle.contains(labelNew) || nameAndLabelArticle.contains(labelNew.toUpperCase())) {
					return true;
				}
			}
		}
		return false;
	}

	public String getImagenArticulo(WebElement articulo) {
		var imgOpt = getImagenArticuloUnit(articulo);
		if (imgOpt.isEmpty() || "".compareTo(imgOpt.get())==0) {
			waitMillis(200);
			imgOpt = getImagenArticuloUnit(articulo);
		}
		if (!imgOpt.isEmpty()) {
			return imgOpt.get();
		}
		return null;
	}
	
	private Optional<String> getImagenArticuloUnit(WebElement articulo) {
		waitLoadPage();
		var imagen = getImagenElementArticulo(articulo);
		if (imagen!=null) {
			try {
				return Optional.of(getSrcImage(imagen));
			}
			catch (StaleElementReferenceException e) {
				imagen = getImagenElementArticulo(articulo);
				if (imagen!=null) {
					return Optional.of(getSrcImage(imagen));
				}
			}
		}
		return Optional.empty();
	}
	
	private String getSrcImage(WebElement image) {
		if (image.getAttribute("original")!=null) {
			return image.getAttribute("original");
		}
		return image.getAttribute("src");
	}

	public static List<LabelArticle> getListlabelsnew() {
		return LIST_LABELS_NEW;
	}
	
	public StateFavorito getStateHearthIcon(WebElement hearthIcon) {
		if (hearthIcon.getAttribute("class").contains("icon-fill")) {
			return MARCADO;
		}
		return DESMARCADO;
	}
	public int selectColoresAndReturnNumArticles(List<Color> colorsToSelect) {
		secFiltros.selecFiltroColores(colorsToSelect);
		return waitForArticleVisibleAndGetNumberOfThem(10);
	}
	
	public int selecOrdenacionAndReturnNumArticles(FilterOrdenacion typeOrden) throws Exception {
		secFiltros.selecOrdenacion(typeOrden);
		return waitForArticleVisibleAndGetNumberOfThem(10);
	}
	public boolean isClickableFiltroUntil(int seconds) {
		return secFiltros.isClickableFiltroUntil(seconds);
	}
	
	public boolean isVisibleArticleCapaTallasUntil(int posArticulo, int seconds) {
		return secTallas.isVisibleArticleCapaTallasUntil(posArticulo, seconds);
	}
	
}
