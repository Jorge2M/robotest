package com.mng.robotest.tests.domains.galeria.pageobjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import com.mng.robotest.tests.domains.galeria.pageobjects.article.LabelArticle;
import com.mng.robotest.tests.domains.galeria.pageobjects.article.SecColoresArticuloDesktop;
import com.mng.robotest.tests.domains.galeria.pageobjects.entities.TypeSlider;
import com.mng.robotest.tests.domains.galeria.pageobjects.filters.desktop.SecFiltrosDesktop;
import com.mng.robotest.tests.domains.galeria.pageobjects.filters.desktop.SecFiltrosDesktopKondo;
import com.mng.robotest.tests.domains.galeria.pageobjects.sections.SecBannerHeadGallery;
import com.mng.robotest.tests.domains.galeria.pageobjects.sections.SecCrossSelling;
import com.mng.robotest.tests.domains.galeria.pageobjects.sections.SecSubMenusGallery;
import com.mng.robotest.tests.domains.galeria.pageobjects.sections.SecBannerHeadGallery.TypeLinkInfo;
import com.mng.robotest.tests.domains.transversal.cabecera.pageobjects.SecCabecera;
import com.mng.robotest.tests.domains.transversal.menus.pageobjects.LineaWeb.LineaType;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.data.Talla;
import com.mng.robotest.testslegacy.generic.beans.ArticuloScreen;
import com.github.jorge2m.testmaker.conf.Log4jTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public abstract class PageGaleriaDesktop extends PageGaleria {
	
	private final SecSubMenusGallery secSubMenusGallery = SecSubMenusGallery.make(app, dataTest.getPais());
	private final SecColoresArticuloDesktop secColores = SecColoresArticuloDesktop.make(app, dataTest.getPais());
	private final SecBannerHeadGallery secBannerHead = new SecBannerHeadGallery();
	private final SecCrossSelling secCrossSelling = new SecCrossSelling();
	
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
	
	private static final String XPATH_LIST_ARTICLES = "//div[@class[contains(.,'columns')] and @id='list']";
	
	public PageGaleriaDesktop() {
		super();
	}
	
	public PageGaleriaDesktop(From from) {
		super(from);
	}
	
	public enum TypeArticle { REBAJADO, NO_REBAJADO }
	
	private static final String XPATH_ANCESTOR_ARTICLE = "//ancestor::div[@class[contains(.,'product-list-info')]]";
	
	private String getXPathDataArticuloOfType(TypeArticle typeArticle) {
		String xpathPrecio = secPrecios.getXPathPrecioArticulo(typeArticle);
		return (getXPathArticulo() + xpathPrecio + XPATH_ANCESTOR_ARTICLE);
	}
	
	private static final String XPATH_ICONO_UP_GALERY = "//div[@id='scroll-top-step' or @id='iconFillUp']";
	private static final String XPATH_HEADER_ARTICLES = "//div[@id[contains(.,'title')]]/h1";

	public boolean isPage() {
		String xpath = "//div[@class[contains(.,'container-fluid catalog')]]";
		return state(Present, xpath).check();
	}
	
	private String getXPathLabel(LabelArticle label) {
		String xpath = "//span[@class='product-list-label' and (";
		for (int i=0; i<label.getListTraducciones().size(); i++) {
			String labelTraduction = label.getListTraducciones().get(i);
			xpath+="text()[contains(.,'" + labelTraduction + "')]";
			if (i<label.getListTraducciones().size()-1) {
				xpath+=" or ";
			}
		}
		xpath+=")]";
		return xpath;
	}

	private String getXPathDataArticuloRebajadoWithLabel(LabelArticle label) {
		String xpathLabelWithLit = getXPathLabel(label);
		return (getXPathDataArticuloOfType(TypeArticle.REBAJADO) + xpathLabelWithLit + "/..");
	}

	private String getXPathDataArticuloTemporadaXWithLabel(List<Integer> temporadasX, LabelArticle label) {
		String xpathLabelWithLit = getXPathLabel(label);
		return (getXPathArticuloTemporadasX(ControlTemporada.ARTICLES_FROM, temporadasX) + xpathLabelWithLit + "/..");
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

	private static final String INI_XPATH_PAGINA_GALERIA = "//*[@id='page";

	@Override
	String getXPathPagina(int pagina) {
		return (INI_XPATH_PAGINA_GALERIA + pagina + "']");
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
		WebElement article = getArticulo(numArticle);
		if (article==null) {
			return false;
		}
		
		//Tenemos en cuenta los casos de tipo "s=Rebajas_T2" y "_kidsA"
		String linea1rstCharCapital = idLinCarrusel.substring(0,1).toUpperCase() + idLinCarrusel.substring(1, idLinCarrusel.length());
		String lastCharUpper = idLinCarrusel.substring(idLinCarrusel.length()-1,idLinCarrusel.length()).toUpperCase();
		String lineaLastCharCapital = idLinCarrusel.substring(0,idLinCarrusel.length()-1) + lastCharUpper;
		if (state(Present, article).by(By.xpath(".//a[@href[contains(.,'" + idLinCarrusel + "')]]")).check() ||
			state(Present, article).by(By.xpath(".//a[@href[contains(.,'s=" + linea1rstCharCapital + "')]]")).check() ||
			state(Present, article).by(By.xpath(".//a[@href[contains(.,'_" + lineaLastCharCapital + "')]]")).check()) {
			return true;
		}
		
		//Los ids de carrusels para niño/niña son nino/nina pero a nivel del HTML de los artículos figura KidsA/KidsO
		LineaType lineaType = LineaType.getLineaType(idLinCarrusel);
		return 
		    (lineaType!=null && 
		    (lineaType==LineaType.NINA || lineaType==LineaType.NINO) &&
		    (state(Present, ".//a[@href[contains(.,'" + lineaType.getId2() + "')]]").check()));
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
		return (getXPathArticulo() + "//*[(" + classProductName + "]) and text()[contains(.,'" + string + "')]]");
	}
	
	public String getXPathLinkNumColumnas(NumColumnas numColumnas) {
		return ("//button[@id='navColumns" + getNumColumnas(numColumnas) + "']");
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
		return getElements(XPATH_HEARTH_ICON_RELATIVE_ARTICLE).size();
	}

	public boolean isArticuloWithStringInName(String string) {
		String xpathArtWithString = getXPathNombreArticuloWithString(string);
		return state(Present, xpathArtWithString).check();
	}	
 
	@Override
	public int getLayoutNumColumnas() {
		return getLayoutNumColumnasShop(); 
	}	
	
	private int getLayoutNumColumnasShop() {
		var listArt = getElement(XPATH_LIST_ARTICLES );
		if (listArt.getAttribute("class").contains("columns3")) {
			return 3;
		}
		if (listArt.getAttribute("class").contains("columns4")) {
			return 4;
		}
		return 2;
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
	public boolean backTo1erArticulo() throws InterruptedException {
		return backTo1erArticulo(XPATH_ICONO_UP_GALERY);
	}
	
	public List<String> getArticlesRebajadosWithLiteralInLabel(List<LabelArticle> listLabels) {
		List<String> dataTextArticles = new ArrayList<>();
		for (var label : listLabels) {
			String xpathLit = getXPathDataArticuloRebajadoWithLabel(label);
			dataTextArticles.addAll(getDataFromArticlesLiteral(xpathLit));
		}
		return dataTextArticles;
	}
	
	public List<String> getArticlesTemporadaxRebajadosWithLiteralInLabel(List<Integer> listTemporadas, List<LabelArticle> listLabels) {
		var listArtSaleWithLabel = getArticlesRebajadosWithLiteralInLabel(listLabels);
		if (listArtSaleWithLabel.isEmpty()) {
			return listArtSaleWithLabel;
		}
		
		List<String> listArtTempX = getArticlesTemporadasX(ControlTemporada.ARTICLES_FROM, listTemporadas);
		List<String> common = new ArrayList<>(listArtTempX);
		common.retainAll(listArtSaleWithLabel);
		return common;
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
	
	public List<String> getArticlesTemporadaXWithLiteralInLabel(List<Integer> temporadasX, List<LabelArticle> listLabels) {
		List<String> dataTextArticles = new ArrayList<>();
	   	for (var label : listLabels) {
	   		String xpathLit = getXPathDataArticuloTemporadaXWithLabel(temporadasX, label);
			dataTextArticles.addAll(getDataFromArticlesLiteral(xpathLit));
		}
		return dataTextArticles;
	}
	
	private List<String> getDataFromArticlesLiteral(String xpathLiteralArticle) {
		List<String> dataTextArticles = new ArrayList<>();
		for (var litWebEl : getElements(xpathLiteralArticle)) {
			String referencia = litWebEl.getAttribute("id").replace("_info", "");
			dataTextArticles.add(litWebEl.getText() + " (" + referencia + ")");
		}
		
		return dataTextArticles;
	}
	
	/**
	 * @return lista de artículos que tienen ambas etiquetas
	 */
	public List<String> getArticlesTemporadaXWithLiteralInLabel(
			List<Integer> temporadasX, LabelArticle label1, LabelArticle label2) {
		List<String> listResult = new ArrayList<>();
		var listArticles1 = getArticlesTemporadaXWithLiteralInLabel(temporadasX, Arrays.asList(label1));
		if (listArticles1.isEmpty()) {
			return listResult;
		}
		
		var listArticles2 = getArticlesTemporadaXWithLiteralInLabel(temporadasX, Arrays.asList(label2));
		if (listArticles2.isEmpty()) {
			return listResult;
		}
			
		for (String article1 : listArticles1) {
			for (String article2 : listArticles2) {
				if (article1.compareTo(article2)==0) {
					listResult.add(article1);
				}
			}
		}
		
		return listResult;
	}	
	
	@Override
	public void moveToArticleAndGetObject(int posArticulo) {
		String xpathArticle = getXPathLinkArticulo(posArticulo) + "/..";
		state(Visible, xpathArticle).wait(1).check();
		moveToElement(xpathArticle);
	}

	@Override
	public void showTallasArticulo(int posArticulo) {
		//Nos posicionamos en el artículo y clicamos la capa. 
		//Es un click muy extraño porque cuando lo ejecutas automáticamente posiciona la capa en el top del navegador y queda oculta por el menú
		moveToArticleAndGetObject(posArticulo);
	}

//	@Override
//	public boolean isVisibleArticleCapaTallasUntil(int posArticulo, int seconds) {
//		return secTallas.isVisibleArticleCapaTallasUntil(posArticulo, seconds);
//	}
//	
	@Override
	public ArticuloScreen selectTallaAvailableArticle(int posArticulo) throws Exception {
		waitLoadPage();
		showTallasArticulo(posArticulo);
		Talla talla = secTallas.selectTallaAvailableArticle(posArticulo);
		var articulo = getArticuloObject(posArticulo);
		articulo.setTalla(talla);
		return articulo;
	}

	@Override
	public void selectTallaArticleNotAvalaible() {
		secTallas.selectTallaArticleNotAvalaible();
	}

	public boolean isVisibleAnyArticle() {
		return (state(Visible, getXPathArticulo()).check());
	}

	public void clickArticulo(int numArticulo) {
		String xpathArticulo = getXPathLinkArticulo(numArticulo);
		click(xpathArticulo).exec();
		
		//Existe un problema en Firefox-Gecko con este botón: a veces el 1er click no funciona así que ejecutamos un 2o 
		if (state(Visible, xpathArticulo).check()) {
			try {
				click(xpathArticulo).type(javascript).exec();
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

	public String getNombreArticulo(int numArticulo) {
		return getElement("(" + getXPathArticulo() + ")[" + numArticulo + "]//span[" + classProductName + "]").getText().trim();
	}

	/**
	 * Revisa si los nombres de los artículos son válidos (si los nombres contienen alguno de los del conjunto de literales)
	 * @param isMobil
	 * @param nombrePosibles: conjuntos de substrings que han de contener los artículos
	 * @return 
	 *	En caso de que todos los artículso tengan un nombre válido: ""
	 *	En caso de que exista algún artículo no válido: nombre del 1er artículo no válido  
	 */
	public List<String> getArticlesNoValid(List<String> articleNames) {
		//Obtenemos el xpath de los artículos eliminando el último carácter (]) pues hemos de insertar condiciones en el XPATH
		String xpathLitArticulos = getXPathArticulo() + "//*[" + classProductName + "]";
		xpathLitArticulos = xpathLitArticulos.substring(0, xpathLitArticulos.length() - 1);
		for (String article : articleNames) {
			xpathLitArticulos +=  
				" and text()[not(contains(.,'" + article + "'))]" +
				" and text()[not(contains(.,'" + article.toLowerCase() + "'))]" +
				" and text()[not(contains(.,'" + article.toUpperCase() + "'))]";
		} 
		xpathLitArticulos += "]";

		//Si existe algún elemento que no pertenece al grupo de nombres válidos -> devolvemos el 1ero que no coincide
		List<String> listTxtArtNoValidos = new ArrayList<>();
		if (state(Present, xpathLitArticulos).check()) {
			for (int i=0; i<3; i++) {
				try {
					waitLoadPage();
					List<WebElement> listTextosArticulosNoValidos = getElements(xpathLitArticulos);
					for (WebElement textoArticuloNoValido : listTextosArticulosNoValidos) {
						String nombre = textoArticuloNoValido.getText();
						String xpathArtWithoutDoubleSlash = getXPathArticulo().substring(2);
						//String referencia = textoArticuloNoValido.findElement(By.xpath("./ancestor::*[@class[contains(.,'product-list-item')]]")).getAttribute("id");
						String referencia = textoArticuloNoValido.findElement(By.xpath("./ancestor::" + xpathArtWithoutDoubleSlash)).getAttribute("id");
						listTxtArtNoValidos.add(nombre + " (" + referencia + ")");
					}
					break;
				}
				catch (StaleElementReferenceException e) {
					Log4jTM.getLogger().info("StaleElementReferenceException getting listTextos no validos from Galery");
				}
			}
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
		state(Clickable, hearthIcon).wait(1).check();
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
		return state(Present, xpathIcon).wait(seconds).check();
	}
	
	//Equivalent to Mobil
	@Override
	public void clickHearhIcon(int posArticle) throws Exception {
		//Nos posicionamos en el icono del Hearth 
		String xpathIcon = getXPathArticleHearthIcon(posArticle);
		state(Visible, xpathIcon).wait(1).check();
		var hearthIcon = getElement(xpathIcon);
		moveToElement(hearthIcon);
		
		//Clicamos y esperamos a que el icono cambie de estado
		StateFavorito estadoInicial = getStateHearthIcon(hearthIcon);
		clickHearthIcon(hearthIcon);
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
	public boolean isHeaderArticlesVisible(String textHeader) {
		String headerText = getHeaderText(3).toLowerCase();
		return headerText.contains(textHeader.toLowerCase());
	}
	
	private String getHeaderText(int seconds) {
		if (state(Visible, XPATH_HEADER_ARTICLES).wait(1).check()) {
			for (int i=0; i<seconds; i++) {
				String headerText = getElement(XPATH_HEADER_ARTICLES).getText();
				if (headerText.compareTo("")!=0) {
					return headerText;
				}
				waitMillis(1000);
			}
		}
		return "";
	}

	public void clickLinkColumnas(NumColumnas numColumnas) {
		String xpathLink = getXPathLinkNumColumnas(numColumnas);
		click(xpathLink).exec();
	}

	public boolean isPresentAnyArticle(TypeArticleDesktop typeArticle) {
		String xpathVideo = getXPathArticulo(typeArticle);
		return state(Present, xpathVideo).check();
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