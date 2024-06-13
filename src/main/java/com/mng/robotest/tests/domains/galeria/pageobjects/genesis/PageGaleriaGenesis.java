package com.mng.robotest.tests.domains.galeria.pageobjects.genesis;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.mng.robotest.tests.domains.galeria.pageobjects.PageGaleria.AttributeArticle.*;
import static com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.PageGaleriaNoGenesis.StateFavorito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Locatable;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.ficha.pageobjects.PageFicha;
import com.mng.robotest.tests.domains.galeria.pageobjects.PageGaleria;
import com.mng.robotest.tests.domains.galeria.pageobjects.SecFiltros;
import com.mng.robotest.tests.domains.galeria.pageobjects.commons.entity.TypeSlider;
import com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.PageGaleriaDesktopBaseNoGenesis.TypeArticleDesktop;
import com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.PageGaleriaNoGenesis.StateFavorito;
import com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.UtilsPageGaleria;
import com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.sections.filters.FilterOrdenacion;
import com.mng.robotest.tests.domains.galeria.steps.GaleriaSteps.TypeActionFav;
import com.mng.robotest.testslegacy.data.Color;
import com.mng.robotest.testslegacy.data.Constantes;
import com.mng.robotest.testslegacy.data.PaisShop;
import com.mng.robotest.testslegacy.data.Talla;
import com.mng.robotest.testslegacy.generic.UtilsMangoTest;
import com.mng.robotest.testslegacy.generic.beans.ArticuloScreen;
import com.mng.robotest.testslegacy.pageobject.utils.DataArticleGalery;
import com.mng.robotest.testslegacy.pageobject.utils.DataScroll;
import com.mng.robotest.testslegacy.pageobject.utils.ListDataArticleGalery;

public abstract class PageGaleriaGenesis extends PageBase implements PageGaleria {

	SecFiltros secFiltros = SecFiltros.make(channel, app, dataTest.getPais());	
	
	private static final String XP_HEADER = "//*[@data-testid='plp.products.list.h1Seo']";
	private static final String XP_LISTA_ARTICULOS = "//*[@data-testid[contains(.,'plp.products.list')]]//ul";
	public static final String XP_ARTICULO = XP_LISTA_ARTICULOS + "//li[@data-slot]";
	protected static final String XP_ICONO_UP_GALERY = "//button/*[@data-testid='up-large']/..";
	private static final String XP_IMAGE_ARTICLE = "//img[@data-testid[contains(.,'plp.product-slot')]]";
	private static final String XP_HEARTH_ICON = "//button[@data-testid[contains(.,'plp.product.favorite.heart')]]";
	private static final String XP_TITLE_ARTICLE = "//p[@class[contains(.,'productTitle')]]";
	
	private static final String XP_TALLA_AVAILABLE = "//*[@data-testid='plp.product.sizeSelector.available']";
	private static final String XP_TALLA_UNAVAILABLE = "//*[@data-testid='plp.product.sizeSelector.unavailable']";
	
	@Override
	public String getXPathArticulo() {
		return XP_ARTICULO;
	}
	
	private String getXPathArticulo(int numArticulo) {
		return "(" + XP_ARTICULO + ")[" + numArticulo + "]";
	}
	
	private String getXPathTallaAvailableArticle(int posArticulo) {
		if (isDevice()) {
			return XP_TALLA_AVAILABLE;
		}
		return getXPathArticulo(posArticulo) + XP_TALLA_AVAILABLE;
	}
	
	private String getXPathTallaUnavailable(int posArticulo) {
		if (isDevice()) {
			return XP_TALLA_UNAVAILABLE;
		}
		return getXPathArticulo(posArticulo) + XP_TALLA_UNAVAILABLE;
	}

	@Override
	public boolean isVisibleArticleUntil(int numArticulo, int seconds) {
		return state(VISIBLE, getXPathArticulo(numArticulo)).wait(seconds).check();
	}

	@Override
	public boolean isVisibleAnyArticle() {
		return state(VISIBLE, getXPathArticulo()).check();
	}
	
	private String getXPathSliderRelativeToArticle(TypeSlider typeSlider) {
		return "//button[@data-testid[contains(.,'slideshow" + typeSlider.getGenesis() + "')]";
	}	

	@Override
	public String getXPathNombreRelativeToArticle() {
		return XP_TITLE_ARTICLE;
	}

	@Override
	public String getXPathArticleHearthIcon(int posArticulo) {
		String xpathArticulo = "(" + getXPathArticulo() + ")[" + posArticulo + "]";
		return (xpathArticulo + XP_HEARTH_ICON);
	}
	
	@Override
	public String getRefArticulo(WebElement articulo) {
		return getReference(articulo).substring(0, 8);
	}

	private String getReference(WebElement articulo) {
		return articulo.getAttribute("data-slot");
	}

	@Override
	public String getNombreArticulo(WebElement articulo) {
		state(PRESENT, articulo).by(By.xpath(XP_TITLE_ARTICLE)).wait(1).check();
		return getElement(articulo, "." + XP_TITLE_ARTICLE).getText();
	}
	
	@Override
	public String getImagenArticulo(WebElement articulo) {
		moveToElement(articulo);
		String srcImage = getElement(articulo, "." + XP_IMAGE_ARTICLE).getAttribute("src");
		return srcImage.split("\\?")[0];
	}	
	
	@Override
	public String getRefColorArticulo(WebElement articulo) {
		return getReference(articulo).replace(":", "");
	}
	
	@Override
	public WebElement getImagenElementArticulo(WebElement articulo) {
		moveToElement(articulo);
		if (state(PRESENT, articulo).by(By.xpath("." + XP_IMAGE_ARTICLE)).wait(1).check()) {
			return getElement(articulo, "." + XP_IMAGE_ARTICLE);
		}
		return null;
	}
	
	@Override
	public void clickSlider(WebElement articulo, TypeSlider typeSlider) {
		if (isDevice()) {
			throw new UnsupportedOperationException();
		}
		clickSliderDesktop(articulo, typeSlider);
	}
	
	private void clickSliderDesktop(WebElement articulo, TypeSlider typeSlider) {
		String xpathSlider = getXPathSliderRelativeToArticle(typeSlider);
		waitMillis(500);
		click(articulo).by(By.xpath("." + xpathSlider)).exec();		
	}	
	
	@Override
	public void clickSliders(WebElement articulo, TypeSlider... typeSliderList) {
		if (isDevice()) {
			throw new UnsupportedOperationException();
		}
		clickSlidersDesktop(articulo, typeSliderList);
	}	
	
	private void clickSlidersDesktop(WebElement articulo, TypeSlider... typeSliderList) {
		Arrays.stream(typeSliderList)
			.forEach(s -> hoverArticleAndclickSlider(articulo, s));
	}
	
	private void hoverArticleAndclickSlider(WebElement articulo, TypeSlider typeSlider) {
		hoverArticle(articulo);
		clickSlider(articulo, typeSlider);
	}	
	
	@Override
	public StateFavorito getStateHearthIcon(int iconNumber) {
		var icon = getElement(getXPathArticleHearthIcon(iconNumber));
		if (icon.getAttribute("data-testid").contains(".active")) {
			return MARCADO;
		}
		return DESMARCADO;
	}	

	@Override
	public boolean isArticleWithHearthIconPresentUntil(int posArticle, int seconds) {
		String xpathIcon = getXPathArticleHearthIcon(posArticle);
		return state(PRESENT, xpathIcon).wait(seconds).check();
	}
	
	@Override
	public int getNumFavoritoIcons() {
		return getElements(XP_HEARTH_ICON).size();
	}	
	
	@Override
	public boolean backTo1erArticulo() {
		scrollEjeY(-50); //Assure icon showed
		if (state(VISIBLE, XP_ICONO_UP_GALERY).wait(1).check()) {
			click(XP_ICONO_UP_GALERY).exec();
		}
		boolean isVisible1erArt = isVisibleArticuloUntil(1, 2);
		waitMillis(500);
		return isVisible1erArt;
	}
	
	@Override
	public void scrollToLastPage() {
		int numArticles = getNumArticulos();
		while (numArticles<500) {
			moveToArticle(numArticles);
			if (!isVisibleArticuloUntil(numArticles+1, 2)) {
				return;
			}
			numArticles = getNumArticulos();
		}
	}

	@Override
	public int getNumArticulos() {
		return getElements(XP_ARTICULO).size();
	}

	@Override
	public void moveToArticle(int numArticulo) {
		moveToArticle(getElement(getXPathArticulo(numArticulo)));
	}

	@Override
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
	
	@Override
	public ListDataArticleGalery getListDataArticles() {
		return getListArticles(Arrays.asList(NOMBRE, REFERENCIA));
	}

	@Override
	public WebElement getArticulo(int numArticulo) {
		var listArticulos = getArticulos();
		if (listArticulos.size()>=numArticulo) {
			return (listArticulos.get(numArticulo-1));
		}
		return null;
	}

	@Override
	public void clickArticulo(WebElement articulo) {
		click(articulo).waitLoadPage(30).exec();
	}

	@Override
	public String openArticuloPestanyaAndGo(WebElement article) {
		String galeryWindowHandle = driver.getWindowHandle();
		new UtilsMangoTest().openLinkInNewTab(article);
		String detailWindowHandle = switchToAnotherWindow(driver, galeryWindowHandle);
		var pageFicha = PageFicha.make(channel, app, dataTest.getPais());
		pageFicha.isPage(10);
		return detailWindowHandle;
	}

	@Override
	public boolean isHeaderArticlesVisible(String textHeader) {
		return state(VISIBLE, XP_HEADER).check();
	}
	
	@Override
	public void showFilters() {
		secFiltros.showFilters();
	}
	@Override
	public void acceptFilters() {
		secFiltros.acceptFilters();
	}	
	@Override
	public boolean isVisibleSelectorPrecios() {
		return secFiltros.isVisibleSelectorPrecios();
	}
	@Override
	public int getMinImportFilter() {
		return secFiltros.getMinImportFilter();
	}
	@Override
	public int getMaxImportFilter() {
		return secFiltros.getMaxImportFilter();
	}
	@Override
	public void clickIntervalImportFilter(int margenPixelsLeft, int margenPixelsRight) {
		secFiltros.clickIntervalImportFilter(margenPixelsLeft, margenPixelsRight);
	}
	
	private List<WebElement> getArticulos() {
		return getElements(getXPathArticulo());
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
	
	private List<WebElement> getListaArticulos() {
		return getElements(getXPathArticulo());
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
	
	private void moveToArticle(WebElement article) {
		((Locatable)article).getCoordinates().inViewPort();
	}
	
	public List<String> getArticlesNoValid(List<String> articleNames) {
	    String xpathLitArticulos = getXPathArticleNames(articleNames);
	    List<String> listTxtArtNoValidos = new ArrayList<>();
	    if (state(PRESENT, xpathLitArticulos).check()) {
	        for (int i = 0; i < 3; i++) {
	            try {
	                waitLoadPage();
	                listTxtArtNoValidos = getListTextosNoValidos(xpathLitArticulos);
	                break;
	            } catch (StaleElementReferenceException e) {
	                Log4jTM.getLogger().info("StaleElementReferenceException getting listTextos no validos from Gallery");
	            }
	        }
	    }
	    return listTxtArtNoValidos;
	}
	
	private String getXPathArticleNames(List<String> articleNames) {
		String xpathNombreArticulos = getXPathArticulo() + getXPathNombreRelativeToArticle();
	    xpathNombreArticulos+="//self::*[";
	    for (int i=0; i<articleNames.size(); i++) {
	    	var articleName = articleNames.get(i);
	        xpathNombreArticulos +=  
	                "text()[not(contains(.,'" + articleName + "'))] and " +
	                "text()[not(contains(.,'" + articleName.toLowerCase() + "'))] and " +
	                "text()[not(contains(.,'" + articleName.toUpperCase() + "'))]";
	        
	        if (i<articleNames.size()-1) {
	        	xpathNombreArticulos+=" and ";
	        }
	        
	    }
	    xpathNombreArticulos += "]";
	    return xpathNombreArticulos;
	}	
	
	private List<String> getListTextosNoValidos(String xpath) {
	    var listTextosArticulosNoValidos = getElements(xpath);
	    List<String> listTxtArtNoValidos = new ArrayList<>();
	    for (var textoArticuloNoValido : listTextosArticulosNoValidos) {
	        String nombre = textoArticuloNoValido.getText();
	        String referencia = textoArticuloNoValido
	                .findElement(By.xpath("./ancestor::" + getXPathArticulo().substring(2)))
	                .getAttribute("id");
	        listTxtArtNoValidos.add(nombre + " (" + referencia + ")");
	    }
	    return listTxtArtNoValidos;
	}	
	
	private boolean isVisibleArticuloUntil(int numArticulo, int seconds) {
		String xpathArticulo = "(" + getXPathArticulo() + ")[" + numArticulo + "]";
		return state(VISIBLE, xpathArticulo).wait(seconds).check();
	}

	
	//----
	@Override
	public boolean backTo1erArticulo(String xpathUpButton) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public WebElement getArticuloConVariedadColoresAndHover(int numArticulo) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public ArticuloScreen getArticuloObject(int numArticulo) {
		var articleElem = getElements(getXPathArticulo()).get(numArticulo-1);
		moveToElement(articleElem);
		var articulo = new ArticuloScreen();
		articulo.setReferencia(getRefArticulo(articleElem)); 
		articulo.setNombre(getNombreArticulo(articleElem));
		articulo.setPrecio(getPrecioArticulo(articleElem));
		var codColor = getCodColorArticulo(articleElem);
		articulo.setCodigoColor(codColor);
		articulo.setColorName(getNameColorFromCodigo(codColor));
		articulo.setNumero(1);
		
		return articulo;
	}
	
	private static final String XP_PRICE_ARTICLE = "//*[@data-testid='currentPrice']";
	
	@Override
	public String getPrecioArticulo(WebElement articulo) {
		return getElement(articulo, "." + XP_PRICE_ARTICLE).getText();
	}
	
	public boolean isArticleRebajado(WebElement articulo) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public String getCodColorArticulo(int numArticulo) {
		var articuloElem = getElement(getXPathArticulo(numArticulo));
		return getCodColorArticulo(articuloElem);
	}
	
	private static final String XP_URL_FICHA_ARTICLE = "//*[@data-testid='productCard.images']/a";
	
	private String getCodColorArticulo(WebElement articulo) {
		String urlFicha = getElement(articulo, "." + XP_URL_FICHA_ARTICLE).getAttribute("href");
		return UtilsPageGaleria.getReferenciaAndCodColorFromURLficha(urlFicha);
	}
	
	public String getNameColorFromCodigo(String codigoColor) {
		return Constantes.COLOR_DESCONOCIDO;
	}
	public void clickColorArticulo(WebElement articulo, int posColor) {
		throw new UnsupportedOperationException();
	}
	public List<ArticuloScreen> clickArticleHearthIcons(Integer... posIconsToClick) throws Exception {
		List<ArticuloScreen> listArtFav = new ArrayList<>();
		for (int posIcon : posIconsToClick) {
			clickHearhIcon(posIcon);
			listArtFav.add(getArticuloObject(posIcon));
		}
		return listArtFav;
	}
	public void clickHearhIcon(int posArticle) throws Exception {
		String xpHearth = getXPathArticleHearthIcon(posArticle);
		click(xpHearth).exec();
	}
	public String getXPathPagina(int pagina) {
		throw new UnsupportedOperationException();
	}
	public int getNumArticulosFromPagina(int pagina, TypeArticleDesktop sizeArticle) {
		throw new UnsupportedOperationException();
	}
	public WebElement getArticleFromPagina(int numPagina, int numArticle) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void showTallasArticulo(int posArticulo) {
		if (isDevice()) {
			showTallasArticuloMobile(posArticulo);
		} else {
			showTallasArticuloDesktop(posArticulo);
		}
	}
	
	private static final String XP_ANADIR_ARTICLE_MOBILE = "//*[@data-testid='productCard.sizeSheetButton']";
	
	private void showTallasArticuloMobile(int posArticulo) {
		String xpathArticle = getXPathArticulo(posArticulo);
		state(VISIBLE, xpathArticle).wait(1).check();
		moveToElement(xpathArticle);
		var articleElem = getElement(xpathArticle);
		var showTallasButton = getElement(articleElem, "." + XP_ANADIR_ARTICLE_MOBILE);
		moveToElement(showTallasButton);
		scrollEjeY(70); //Avoid capa article added to bag
		showTallasButton.click();
	}
	private void showTallasArticuloDesktop(int posArticulo) {
		String xpathArticle = getXPathArticulo(posArticulo);
		state(VISIBLE, xpathArticle).wait(1).check();
		moveToElement(xpathArticle);
		moveToElement(xpathArticle, 0, 100); //Try to surpase case when bag is overloaping article partially
	}
	
	@Override
	public ArticuloScreen selectTallaAvailableArticle(int posArticulo) {
		if (isInvisibleArticleCapaTallasUntil(posArticulo, 0)) {
			showTallasArticulo(posArticulo);
		}
		String xpTalla = getXPathTallaAvailableArticle(posArticulo);
		var tallaOpt = selectTalla(xpTalla, posArticulo);
		var articulo = getArticuloObject(posArticulo);
		if (tallaOpt.isPresent()) {
			articulo.setTalla(tallaOpt.get());
		} else {
			articulo.setTalla(null);
		}
		return articulo;
	}

	@Override
	public void selectTallaArticleNotAvalaible() {
		for (int i=1; i<20; i++) {
			var tallaOpt = selectTallaArticleNotAvalaible(i); 
			if (tallaOpt.isPresent()) {
				break;
			}
			if (isDevice()) {
				unshowTallasArticuloDevice();
			}
		}
	}

	private static final String XP_BUTTON_FOR_CLOSE_TALLAS_DEVICE = "//button[@data-testid='modal.close.button']";
	
	private void unshowTallasArticuloDevice() {
		if (state(VISIBLE, XP_BUTTON_FOR_CLOSE_TALLAS_DEVICE).check()) {
			click(XP_BUTTON_FOR_CLOSE_TALLAS_DEVICE).exec();
		}
	}
	
	public Optional<Talla> selectTallaArticleNotAvalaible(int posArticulo) {
		if (isInvisibleArticleCapaTallasUntil(posArticulo, 0)) {
			showTallasArticulo(posArticulo);
		}
		String xpTallaUnavailable = getXPathTallaUnavailable(posArticulo);
		return selectTalla(xpTallaUnavailable, posArticulo);
	}
	
	private Optional<Talla> selectTalla(String xpTalla, int posArticulo) {
		if (state(VISIBLE, xpTalla).wait(1).check()) {
			var tallaToSelect = getElement(xpTalla);
			var labelTalla = tallaToSelect.getText();
			tallaToSelect.click();
			if (isDevice()) { //wait for tallas unshow in webmobil
				isInvisibleArticleCapaTallasUntil(posArticulo, 2);
			}
			var talla = Talla.fromLabel(labelTalla, PaisShop.from(dataTest.getCodigoPais()));
			return Optional.of(talla);
		}
		return Optional.empty();
	}
	
	public void clickHearthIcon(WebElement hearthIcon) throws Exception {
		throw new UnsupportedOperationException();
	}
	public boolean preciosInIntervalo(int minimo, int maximo) throws Exception {
		throw new UnsupportedOperationException();
	}
	public boolean isClickableArticuloUntil(int numArticulo, int seconds) {
		throw new UnsupportedOperationException();
	}
	public boolean articlesInOrder(FilterOrdenacion typeOrden) throws Exception {
		throw new UnsupportedOperationException();
	}
	public void hoverArticle(WebElement article) {
		throw new UnsupportedOperationException();
	}
	public void moveToArticleAndGetObject(int posArticulo) {
		throw new UnsupportedOperationException();
	}
	public boolean waitToHearthIconInState(int posArticle, StateFavorito stateIcon, int seconds) {
		throw new UnsupportedOperationException();
	}
	
	public boolean iconsInCorrectState(TypeActionFav typeAction, Integer... posIconosFav) {
		for (int posIcon : posIconosFav) {
			if (typeAction==TypeActionFav.MARCAR) {
				if (getStateHearthIcon(posIcon)!=MARCADO) {
					return false;
				}
			}
			else { 
				if (getStateHearthIcon(posIcon)!=DESMARCADO) {
					return false;
				}
			}
		}
		return true;
	}
	
	public String getNombreArticuloWithText(String literal, int secondsWait) {
		throw new UnsupportedOperationException();
	}
	public boolean isVisibleImageArticle(int numArticulo, int seconds) {
		throw new UnsupportedOperationException();
	}
	public int filterByColorsAndReturnNumArticles(List<Color> colorsToSelect) {
		throw new UnsupportedOperationException();
	}
	public int selecOrdenacionAndReturnNumArticles(FilterOrdenacion typeOrden) throws Exception {
		throw new UnsupportedOperationException();
	}
	public boolean isClickableFiltroUntil(int seconds) {
		throw new UnsupportedOperationException();
	}
	
	private static final String XP_CAPA_TALLAS_DESKTOP = "//*[@data-testid='plp.product.sizeSelector.panel']";
	private static final String XP_CAPA_TALLAS_MOBILE = "//*[@data-testid='productCard.sizeSheet']/div";
	private String getXPathCapaTallas() {
		if (isDevice()) {
			return XP_CAPA_TALLAS_MOBILE;
		}
		return XP_CAPA_TALLAS_DESKTOP;
	}
	
	private String getXPathCapaTallas(int posArticulo) {
		String xpCapaTallas = getXPathCapaTallas();
		if (isDevice()) {
			return xpCapaTallas;
		}
		return "(" + xpCapaTallas + ")[" + posArticulo + "]";
	}

	@Override
	public boolean isVisibleArticleCapaTallasUntil(int posArticulo, int seconds) {
		return state(VISIBLE, getXPathCapaTallas(posArticulo)).wait(seconds).check();
	}
	private boolean isInvisibleArticleCapaTallasUntil(int posArticulo, int seconds) {
		return state(INVISIBLE, getXPathCapaTallas(posArticulo)).wait(seconds).check();
	}
	
	public DataScroll scrollToPageFromFirst(int numPage) {
		throw new UnsupportedOperationException();
	}


}
