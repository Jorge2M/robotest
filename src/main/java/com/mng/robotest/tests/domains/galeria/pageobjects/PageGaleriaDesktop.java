package com.mng.robotest.tests.domains.galeria.pageobjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import com.mng.robotest.tests.domains.galeria.pageobjects.article.SecColoresArticuloDesktop;
import com.mng.robotest.tests.domains.galeria.pageobjects.entities.TypeSlider;
import com.mng.robotest.tests.domains.galeria.pageobjects.filters.desktop.SecFiltrosDesktop;
import com.mng.robotest.tests.domains.galeria.pageobjects.filters.desktop.SecFiltrosDesktopKondo;
import com.mng.robotest.tests.domains.galeria.pageobjects.sections.SecBannerHeadGallery;
import com.mng.robotest.tests.domains.galeria.pageobjects.sections.SecSubMenusGallery;
import com.mng.robotest.tests.domains.galeria.pageobjects.sections.SecBannerHeadGallery.TypeLinkInfo;
import com.mng.robotest.tests.domains.menus.pageobjects.LineaWeb.LineaType;
import com.mng.robotest.tests.domains.transversal.cabecera.pageobjects.SecCabecera;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.generic.beans.ArticuloScreen;
import com.github.jorge2m.testmaker.conf.Log4jTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.mng.robotest.tests.domains.galeria.pageobjects.PageGaleria.StateFavorito.*;

public abstract class PageGaleriaDesktop extends PageGaleria {
	
	private final SecSubMenusGallery secSubMenusGallery = SecSubMenusGallery.make(app, dataTest.getPais());
	private final SecColoresArticuloDesktop secColores = SecColoresArticuloDesktop.make(app, dataTest.getPais());
	private final SecBannerHeadGallery secBannerHead = new SecBannerHeadGallery();
	
	public abstract String getXPathIconUpGalery();
	public abstract void clickLinkColumnas(NumColumnas numColumnas);
	public abstract List<String> searchForArticlesNoValid(List<String> articleNames);
	
	public void clickSubMenu(String submenu) {
		secSubMenusGallery.clickSubMenu(submenu);
	}
	public boolean isVisibleSubMenu(String submenu) {
		return secSubMenusGallery.isVisibleSubMenu(submenu);
	}
	
	public enum NumColumnas { DOS, TRES, CUATRO }
	public enum TypeColor { CODIGO, NOMBRE }
	
	public enum TypeArticleDesktop {
		SIMPLE (
			"//self::*[@data-imgsize='A1' or @class[contains(.,'_2zQ2a')]]"), //TODO (Outlet) a la espera de los cambios de Sergio Campillo
		DOBLE (
			"//self::*[@data-imgsize='A2' or @class[contains(.,'_3QWF_')]]"), //TODO (Outlet) a la espera de los cambios de Sergio Campillo
		VIDEO (
			"//video");
		
		String xpathRelativeArticle;
		private TypeArticleDesktop(String xpathRelativeArticle) {
			this.xpathRelativeArticle = xpathRelativeArticle;
		}
		
		public String getXPathRelativeArticle() {
			return xpathRelativeArticle;
		}
	}
	
	protected PageGaleriaDesktop() {
		super();
	}
	
	protected PageGaleriaDesktop(From from) {
		super(from);
	}
	
	public enum TypeArticle { REBAJADO, NO_REBAJADO }
	
	private static final String XP_ANCESTOR_ARTICLE = "//ancestor::div[@class[contains(.,'product-list-info')]]";
	
	private String getXPathDataArticuloOfType(TypeArticle typeArticle) {
		String xpathPrecio = secPrecios.getXPathPrecioArticulo(typeArticle);
		return (getXPathArticulo() + xpathPrecio + XP_ANCESTOR_ARTICLE);
	}
	
	private static final String XP_HEADER_ARTICLES = "//div[@id[contains(.,'title')]]/h1";

	public boolean isPage() {
		String xpath = "//div[@class[contains(.,'container-fluid catalog')]]";
		return state(PRESENT, xpath).check();
	}
	
	String getXPathArticuloFromPagina(int pagina, TypeArticleDesktop sizeArticle) {
		String xpathPagina = getXPathPagina(pagina);
		return  (xpathPagina + getXPathArticulo(sizeArticle));
	}

	public enum ControlTemporada {ARTICLES_FROM, ARTICLES_FROM_OTHER}
	
	String getXPathArticuloTemporadasX(ControlTemporada controlTemp, List<Integer> listTemporadas) {
		String xpathResult = getXPathArticulo() + "/self::*[@id and ";
		for (int i=0; i<listTemporadas.size(); i++) {
			int temporada = listTemporadas.get(i);
			if (controlTemp == ControlTemporada.ARTICLES_FROM) {
				xpathResult += "starts-with(@id, " + temporada + ")";
				if (i<(listTemporadas.size()-1)) {
					xpathResult+=" or ";
				}
			}
			else {
				xpathResult += "not(starts-with(@id, " + temporada + "))";
				if (i<(listTemporadas.size()-1)) {
					xpathResult+=" and ";
				}
			}
		}

		return (xpathResult + "]//div[@class[contains(.,'product-list-info')]]");
	}

	String getXPathArticulo(TypeArticleDesktop sizeArticle) {
		return (getXPathArticulo() + sizeArticle.getXPathRelativeArticle());
	}

	private static final String INI_XP_PAGINA_GALERIA = "//*[@id='page";

	@Override
	String getXPathPagina(int pagina) {
		return (INI_XP_PAGINA_GALERIA + pagina + "']");
	}

	@Override
	public void clickColorArticulo(WebElement articulo, int posColor) {
		secColores.clickColorArticulo(articulo, posColor);
	}
	
	@Override
	public WebElement getArticuloConVariedadColoresAndHover(int numArticulo) {
		var articulo = secColores.getArticuloConVariedadColores(numArticulo);
		if (articulo.isPresent()) {
			hoverArticle(articulo.get());
			return articulo.get();
		}
		return null;
	}

	public boolean isArticleFromLinea(int numArticle, LineaType lineaType) {
		return (isArticleFromLinCarrusel(numArticle, lineaType.toString()));
	}
	
	public boolean isArticleFromLinCarrusel(int numArticle, String idLinCarrusel) {
		var article = getArticulo(numArticle);
		if (article==null) {
			return false;
		}
		
		//Tenemos en cuenta los casos de tipo "s=Rebajas_T2" y "_kidsA"
		String linea1rstCharCapital = idLinCarrusel.substring(0,1).toUpperCase() + idLinCarrusel.substring(1, idLinCarrusel.length());
		String lastCharUpper = idLinCarrusel.substring(idLinCarrusel.length()-1,idLinCarrusel.length()).toUpperCase();
		String lineaLastCharCapital = idLinCarrusel.substring(0,idLinCarrusel.length()-1) + lastCharUpper;
		if (state(PRESENT, article).by(By.xpath(".//a[@href[contains(.,'" + idLinCarrusel + "')]]")).check() ||
			state(PRESENT, article).by(By.xpath(".//a[@href[contains(.,'s=" + linea1rstCharCapital + "')]]")).check() ||
			state(PRESENT, article).by(By.xpath(".//a[@href[contains(.,'_" + lineaLastCharCapital + "')]]")).check()) {
			return true;
		}
		
		//Los ids de carrusels para niño/niña son nino/nina pero a nivel del HTML de los artículos figura KidsA/KidsO
		var lineaType = LineaType.getLineaType(idLinCarrusel);
		return 
		    (lineaType!=null && 
		    (lineaType==LineaType.NINA || lineaType==LineaType.NINO) &&
		    (state(PRESENT, ".//a[@href[contains(.,'" + lineaType.getId2() + "')]]").check()));
	}
	
	/**
	 * @param categoriaProducto categoría de producto (p.e. "BOLSOS")
	 * @return el xpath correspondiente a la cabecera de resultado de una búsqueda de una determinada categoría de producto
	 */
	@Override
	public String getXPathCabeceraBusquedaProd() {
		return ("//*[@id='buscador_cabecera2']");
	}

	public String getXPathNombreArticuloWithString(String string) {
		return getXPathArticulo() + getXPathNombreRelativeToArticle() + "[text()[contains(.,'" + string + "')]]";
	}
	
	public int getNumColumnas(NumColumnas numColumnas) {
		switch (numColumnas) {
		case DOS:
			return 2;
		case TRES:
			return 3;
		case CUATRO:
			return 4;
		default:
			return -1;
		}
	}

	@Override
	public int getNumFavoritoIcons() {
		return getElements(XP_HEARTH_ICON_RELATIVE_ARTICLE).size();
	}

	@Override
	public boolean isArticleRebajado(WebElement articulo) {
		return (secPrecios.isArticleRebajado(articulo));
	}
	
	@Override
	public String getPrecioArticulo(WebElement articulo) {
		return (secPrecios.getPrecioDefinitivo(articulo));
	}	
	
	/**
	 * @return número de doble tamaño de la galería
	 */
	public int getNumArticulos(TypeArticleDesktop sizeArticle) {
		 return getElements(getXPathArticulo(sizeArticle)).size();
	}
	
	@Override
	public int getNumArticulosFromPagina(int pagina, TypeArticleDesktop sizeArticle) {
		return (getListArticulosFromPagina(pagina).size());
	}
	
	@Override
	public WebElement getArticleFromPagina(int numPagina, int numArticle) {
		var listArticles = getListArticulosFromPagina(numPagina);
		if (listArticles.size()>=numArticle) {
			return listArticles.get(numArticle-1);
		}
		return null;
	}
	
	private List<WebElement> getListArticulosFromPagina(int numPagina) {
		String xpathArticulo = getXPathArticuloFromPagina(numPagina, TypeArticleDesktop.SIMPLE);
		return getElements(xpathArticulo);
	}

	@Override
	public boolean backTo1erArticulo() {
		return backTo1erArticulo(getXPathIconUpGalery());
	}
	
	public List<String> getArticles(TypeArticle typeArticle, List<Integer> listTemporadas) {
		var listArtOfType = getArticlesOfType(typeArticle);
		if (!listArtOfType.isEmpty()) {
			var listArtTempX = getArticlesTemporadasX(ControlTemporada.ARTICLES_FROM, listTemporadas);
			List<String> common = new ArrayList<>(listArtTempX);
			common.retainAll(listArtOfType);
			return common;
		}
		return listArtOfType;
	}
	
	public List<String> getArticlesOfType(TypeArticle typeArticle) {
		String xpathArtReb = getXPathDataArticuloOfType(typeArticle);
		return (getDataFromArticlesLiteral(xpathArtReb));
	}
	
	public List<String> getArticlesTemporadasX(ControlTemporada controlTemporada, List<Integer> listTemporadas) {
		String xpathLit = getXPathArticuloTemporadasX(controlTemporada, listTemporadas);
		return (getDataFromArticlesLiteral(xpathLit));
	}
	
	private List<String> getDataFromArticlesLiteral(String xpathLiteralArticle) {
		List<String> dataTextArticles = new ArrayList<>();
		for (var litWebEl : getElements(xpathLiteralArticle)) {
			String referencia = litWebEl.getAttribute("id").replace("_info", "");
			dataTextArticles.add(litWebEl.getText() + " (" + referencia + ")");
		}
		return dataTextArticles;
	}
	
	@Override
	public void moveToArticleAndGetObject(int posArticulo) {
		String xpathArticle = getXPathLinkArticulo(posArticulo) + "/..";
		state(VISIBLE, xpathArticle).wait(1).check();
		moveToElement(xpathArticle);
	}

	@Override
	public void showTallasArticulo(int posArticulo) {
		//Nos posicionamos en el artículo y clicamos la capa. 
		//Es un click muy extraño porque cuando lo ejecutas automáticamente posiciona la capa en el top del navegador y queda oculta por el menú
		moveToArticleAndGetObject(posArticulo);
	}

	@Override
	public ArticuloScreen selectTallaAvailableArticle(int posArticulo) throws Exception {
		waitLoadPage();
		showTallasArticulo(posArticulo);
		var talla = secTallas.selectTallaAvailableArticle(posArticulo);
		var articulo = getArticuloObject(posArticulo);
		articulo.setTalla(talla);
		return articulo;
	}

	@Override
	public void selectTallaArticleNotAvalaible() {
		secTallas.selectTallaArticleNotAvalaible();
	}

	public boolean isVisibleAnyArticle() {
		return (state(VISIBLE, getXPathArticulo()).check());
	}

	public void clickArticulo(int numArticulo) {
		String xpathArticulo = getXPathLinkArticulo(numArticulo);
		click(xpathArticulo).exec();
		
		//Existe un problema en Firefox-Gecko con este botón: a veces el 1er click no funciona así que ejecutamos un 2o 
		if (state(VISIBLE, xpathArticulo).check()) {
			try {
				click(xpathArticulo).type(JAVASCRIPT).exec();
			}
			catch (Exception e) {
				//Hay un caso en el que el artículo justo desaparece y se clicka -> 
				//Excepción pero la acción de click inicial fue correcta
			}
		}
	}
	
	public void clickSliders(WebElement articulo, TypeSlider... typeSliderList) {
		Arrays.stream(typeSliderList)
			.forEach(s -> hoverArticleAndclickSlider(articulo, s));
	}
	
	public void hoverArticleAndclickSlider(WebElement articulo, TypeSlider typeSlider) {
		hoverArticle(articulo);
		clickSlider(articulo, typeSlider);
	}

	protected List<String> getArticlesNoValid(List<String> articleNames) {
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
	    //String xpathNombreArticulos = getXPathNombreArticulo();
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
	
	//Equivalent to Mobil
	@Override
	public ArticuloScreen getArticuloObject(int numArticulo) throws Exception {
		var artWElem = getElements(getXPathArticulo()).get(numArticulo-1);
		moveToElement(artWElem);
		var articulo = new ArticuloScreen();
		articulo.setReferencia(getRefArticulo(artWElem));
		articulo.setNombre(getNombreArticulo(artWElem));
		articulo.setPrecio(getPrecioArticulo(artWElem));
		articulo.setCodigoColor(getCodColorArticulo(numArticulo));
		articulo.setColorName(getNameColorFromCodigo(articulo.getCodigoColor()));
		articulo.setNumero(1);
		
		return articulo;
	}
	
	//Equivalent to Mobil
	@Override
	public String getCodColorArticulo(int numArticulo) throws Exception {
		String xpathArticulo = "(" + getXPathArticulo() + ")[" + numArticulo + "]";
		String image = getImagenArticulo(getElement(xpathArticulo));
		return UtilsPageGaleria.getCodColorFromSrcImg(image);
	}
	
	//Equivalent to Mobil
	@Override
	public String getNameColorFromCodigo(String codigoColor) {
		return secColores.getNameColorFromCodigo(codigoColor);
	}
	
	//Equivalent to Mobil
	@Override
	public List<ArticuloScreen> clickArticleHearthIcons(Integer... posIconsToClick) throws Exception {
		List<ArticuloScreen> listArtFav = new ArrayList<>();
		for (int posIcon : posIconsToClick) {
			clickHearhIcon(posIcon);
			listArtFav.add(getArticuloObject(posIcon));
		}
		return listArtFav;
	}
	
	public void clickHearthIcon(WebElement hearthIcon) throws Exception {
		moveToElement(hearthIcon);
		state(CLICKABLE, hearthIcon).wait(1).check();
		try {
			hearthIcon.click();
		} 
		catch (ElementClickInterceptedException e) {
			clickHearthIconHiddindPossibleInterceptors(hearthIcon);
		}
	}
	
	private void clickHearthIconHiddindPossibleInterceptors(WebElement hearthIcon) {
		var secCabecera = SecCabecera.make();
		var secFiltros = new SecFiltrosDesktopKondo();
		secCabecera.bring(BringTo.BACKGROUND);
		secFiltros.bring(BringTo.BACKGROUND);
		hearthIcon.click();
		secFiltros.bring(BringTo.FRONT);
		secCabecera.bring(BringTo.FRONT);
	}
	
	@Override
	public boolean isArticleWithHearthIconPresentUntil(int posArticle, int seconds) {
		String xpathIcon = getXPathArticleHearthIcon(posArticle);
		return state(PRESENT, xpathIcon).wait(seconds).check();
	}
	
	//Equivalent to Mobil
	@Override
	public void clickHearhIcon(int posArticle) throws Exception {
	    String xpathIcon = getXPathArticleHearthIcon(posArticle);
	    state(VISIBLE, xpathIcon).wait(1).check();
	    var hearthIcon = getElement(xpathIcon);
	    moveToElement(hearthIcon);

	    var estadoInicial = getStateHearthIcon(hearthIcon);
	    clickHearthIcon(hearthIcon);

	    var estadoFinal = (estadoInicial == MARCADO) ? DESMARCADO : MARCADO;
	    waitToHearthIconInState(hearthIcon, estadoFinal, 2);
	}	

	@Override
	public boolean isHeaderArticlesVisible(String textHeader) {
		String headerText = getHeaderText(3).toLowerCase();
		return headerText.contains(textHeader.toLowerCase());
	}
	
	private String getHeaderText(int seconds) {
		if (state(VISIBLE, XP_HEADER_ARTICLES).wait(1).check()) {
			for (int i=0; i<seconds; i++) {
				String headerText = getElement(XP_HEADER_ARTICLES).getText();
				if (headerText.compareTo("")!=0) {
					return headerText;
				}
				waitMillis(1000);
			}
		}
		return "";
	}

	public boolean isPresentAnyArticle(TypeArticleDesktop typeArticle) {
		String xpathVideo = getXPathArticulo(typeArticle);
		return state(PRESENT, xpathVideo).check();
	}

	public boolean isVisibleSelectorPrecios() {
		return ((SecFiltrosDesktop)secFiltros).isVisibleSelectorPrecios();
	}
	public int getMinImportFilter() {
		return ((SecFiltrosDesktop)secFiltros).getMinImportFilter(); 
	}
	public int getMaxImportFilter() {
		return ((SecFiltrosDesktop)secFiltros).getMaxImportFilter(); 
	}	
	public void clickIntervalImportFilter(int margenPixelsLeft, int margenPixelsRight) {
		((SecFiltrosDesktop)secFiltros).clickIntervalImportFilter(margenPixelsLeft, margenPixelsRight);
	}
	public void showFilters() {
		((SecFiltrosDesktop)secFiltros).showFilters();
	}
	public void acceptFilters() { 
		((SecFiltrosDesktop)secFiltros).acceptFilters();
	}
	
	public void clickRebajasBannerHead() {
		secBannerHead.clickLinkInfoRebajas();
	}
    public boolean isVisibleInfoRebajasBannerHead(int seconds) {
    	return secBannerHead.isVisibleInfoRebajasUntil(seconds);
    }
    public boolean isVisibleLinkInfoRebajasBannerHead() {
    	return secBannerHead.isVisibleLinkInfoRebajas();
    }
    public boolean isVisibleLinkInfoRebajasBannerHead(TypeLinkInfo typeLinkInfo) {
    	return secBannerHead.isVisibleLinkTextInfoRebajas(typeLinkInfo);
    }
    public boolean isVisibleBannerHead() {
    	return secBannerHead.isVisible();
    }
    public boolean isBannerHeadLinkable() {
    	return secBannerHead.isLinkable();
    }
    public void clickBannerHeadIfClickable() { 
    	secBannerHead.clickBannerIfClickable();
    }
    public boolean isBannerHeadWithoutTextAccesible() {
    	return secBannerHead.isBannerWithoutTextAccesible();
    }
    public String getTextBannerHead() {
    	return secBannerHead.getText();
    }    
    public boolean isBannerHeadSalesBanner(IdiomaPais idioma) { 
    	return secBannerHead.isSalesBanner(idioma);
    }
    
}