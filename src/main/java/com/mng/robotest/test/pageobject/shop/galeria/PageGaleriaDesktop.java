package com.mng.robotest.test.pageobject.shop.galeria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.data.Constantes;
import com.mng.robotest.test.data.Talla;
import com.mng.robotest.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test.pageobject.shop.filtros.SecFiltrosDesktop;
import com.mng.robotest.test.pageobject.shop.filtros.SecFiltrosDesktop.Visibility;
import com.mng.robotest.test.pageobject.shop.menus.desktop.SecLineasMenuDesktop;
import com.mng.robotest.test.pageobject.shop.menus.desktop.SecMenusDesktop;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageGaleriaDesktop extends PageGaleria {
	
	private final SecColoresArticuloDesktop secColores;
	private final SecTallasArticuloDesktop secTallas;
	private final SecSubmenusGallery secSubmenusGallery;
	private final SecBannerHeadGallery secBannerHead;
	private final SecCrossSelling secCrossSelling;	
	private final SecSelectorPreciosDesktop secSelectorPreciosDesktop;
	
	public enum NumColumnas { DOS, TRES, CUATRO }
	public enum TypeSlider { PREV, NEXT }
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
	private static final String XPATH_IMG_RELATIVE_ARTICLE_OUTLET = 
			"//img[@src and (" + 
				   "@class[contains(.,'productListImg')] or " + 
				   "@class[contains(.,'product-list-image')] or " +
				   "@class[contains(.,'product-image')] or " + 
				   "@class[contains(.,'TaqRk')] or " + //TODO (Outlet) pendiente Sergio Campillo suba los cambios
				   "@class[contains(.,'product-list-im')])]";
	
	private static final String XPATH_IMG_SLIDER_ACTIVE_RELATIVE_ARTICLE_DESKTOP_OUTLET = 
			"//div[@class[contains(.,'swiper-slide-active')]]" + XPATH_IMG_RELATIVE_ARTICLE_OUTLET ;

	public PageGaleriaDesktop() {
		super();
		secColores = new SecColoresArticuloDesktop(app);
		secTallas = new SecTallasArticuloDesktop(app, xpathArticuloBase);
		secSubmenusGallery = new SecSubmenusGallery();
		secBannerHead = new SecBannerHeadGallery();
		secCrossSelling = new SecCrossSelling();	
		secSelectorPreciosDesktop = new SecSelectorPreciosDesktop();
	}
	
	public PageGaleriaDesktop(From from, Channel channel, AppEcom app) {
		super(from, channel, app);
		secColores = new SecColoresArticuloDesktop(app);
		secTallas = new SecTallasArticuloDesktop(app, xpathArticuloBase);
		secSubmenusGallery = new SecSubmenusGallery();
		secBannerHead = new SecBannerHeadGallery();
		secCrossSelling = new SecCrossSelling();	
		secSelectorPreciosDesktop = new SecSelectorPreciosDesktop();
	}
	
	public enum TypeArticle {rebajado, norebajado};
	private final String XPathAncestorArticle = "//ancestor::div[@class[contains(.,'product-list-info')]]";
	private String getXPathDataArticuloOfType(TypeArticle typeArticle) {
		String xpathPrecio = secPrecios.getXPathPrecioArticulo(typeArticle);
		return (xpathArticuloBase + xpathPrecio + XPathAncestorArticle);
	}
	
	private String getXPathArticuloConColores() {
		return (
			secColores.getXPathColorArticle() + "/" + 
			getXPathAncestorArticulo());
	}

	private static final String XPATH_ICONO_UP_GALERY = "//div[@id='scroll-top-step' or @id='iconFillUp']";
	private static final String XPATH_HEADER_ARTICLES = "//div[@id[contains(.,'title')]]/h1";

	@Override
	public String getXPathLinkRelativeToArticle() {
		return XPATH_NOMBRE_RELATIVE_TO_ARTICLE;
	}

	public boolean isPage() {
		String xpath = "//div[@class[contains(.,'container-fluid catalog')]]";
		return (state(Present, By.xpath(xpath)).check());
	}
	
	@Override
	public void hideMenus() {
		SecMenusDesktop secMenus = new SecMenusDesktop(app, channel);
		secMenus.hideMenus();
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
		return (getXPathDataArticuloOfType(TypeArticle.rebajado) + xpathLabelWithLit + "/..");
	}

	private String getXPathDataArticuloTemporadaXWithLabel(List<Integer> temporadasX, LabelArticle label) {
		String xpathLabelWithLit = getXPathLabel(label);
		return (getXPathArticuloTemporadasX(ControlTemporada.articlesFrom, temporadasX) + xpathLabelWithLit + "/..");
	}

	String getXPathArticuloFromPagina(int pagina, TypeArticleDesktop sizeArticle) {
		String xpathPagina = getXPathPagina(pagina);
		return  (xpathPagina + getXPathArticulo(sizeArticle));
	}

	public enum ControlTemporada {articlesFrom, articlesFromOther};
	String getXPathArticuloTemporadasX(ControlTemporada controlTemp, List<Integer> listTemporadas) {
		String xpathResult = xpathArticuloBase + "/self::*[@id and ";
		for (int i=0; i<listTemporadas.size(); i++) {
			int temporada = listTemporadas.get(i);
			switch (controlTemp) {
			case articlesFrom:
				xpathResult += "starts-with(@id, " + String.valueOf(temporada) + ")";
				if (i<(listTemporadas.size()-1)) {
					xpathResult+=" or ";
				}
				break;
			case articlesFromOther:
				xpathResult += "not(starts-with(@id, " + String.valueOf(temporada) + "))";
				if (i<(listTemporadas.size()-1)) {
					xpathResult+=" and ";
				}
				break;
			}
		}

		return (xpathResult + "]//div[@class[contains(.,'product-list-info')]]");
	}

	String getXPathArticulo(TypeArticleDesktop sizeArticle) {
		return (xpathArticuloBase + sizeArticle.getXPathRelativeArticle());
	}

	private static final String INI_XPATH_PAGINA_GALERIA = "//*[@id='page";

	@Override
	String getXPathPagina(int pagina) {
		return (INI_XPATH_PAGINA_GALERIA + pagina + "']");
	}

	private static String getXPathSliderRelativeToArticle(TypeSlider typeSlider, AppEcom app) {
		if (app==AppEcom.outlet) {
			return ("//*[@class[contains(.,'swiper-button-" + typeSlider + "')] and @role]");
		}
		return ("//*[@data-testid='." + typeSlider + "']");
	}

	private String getXPathArticuloConVariedadColores(int numArticulo) {
		return ("(" + getXPathArticuloConColores() + ")" + "[" + numArticulo + "]");
	}

	@Override
	public WebElement getArticuloConVariedadColoresAndHover(int numArticulo) {
		String xpathArticulo = getXPathArticuloConVariedadColores(numArticulo);
		WebElement articulo = driver.findElement(By.xpath(xpathArticulo)); 
		hoverArticle(articulo);
		return articulo;
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
		if (lineaType!=null && 
		   (lineaType==LineaType.nina || lineaType==LineaType.nino) &&
		   (state(Present, By.xpath(".//a[@href[contains(.,'" + lineaType.getId2() + "')]]")).check())) {
		    return true;
		}
		
		return false;
	}
	
	@Override
	public WebElement getImagenElementArticulo(WebElement articulo) {
		moveToElement(articulo, driver);
		By byImg = By.xpath(getXPathImgArticulo(articulo));			
		if (state(State.Present, articulo).by(byImg).check()) {
			return (articulo.findElement(byImg));
		}
		return null;
	}
	
	private String getXPathImgArticulo(WebElement article) {
		if (app==AppEcom.outlet) {
			return getXPathImgArticuloOutlet(article);
		}
		return getXPathImgArticuloShop(article);
	}
	
	//TODO Test AB nueva variante. Si se mantiene la original igualar con Outlet
	private String getXPathImgArticuloShop(WebElement article) {
		String id = article.getAttribute("id");
		Pattern pattern = Pattern.compile("product-key-id-(.*)");
		Matcher matcher = pattern.matcher(id);
		if (matcher.find()) {
			return ".//img[@id='product-" + matcher.group(1) + "']" ;
		}
		return ".//img[contains(.,'product-')]";
	}
	
	private String getXPathImgArticuloOutlet(WebElement article) {
		if (isPresentSliderInArticle(TypeSlider.NEXT, article)) {
			return "." + XPATH_IMG_SLIDER_ACTIVE_RELATIVE_ARTICLE_DESKTOP_OUTLET;
		} else {
			return "." + XPATH_IMG_RELATIVE_ARTICLE_OUTLET ;
		}
	}
	

	@Override
	public WebElement getColorArticulo(WebElement articulo, boolean selected, int numColor) {
		String xpathImgColorRelArticle = secColores.getXPathImgColorRelativeArticle(selected);
		return (articulo.findElements(By.xpath("." + xpathImgColorRelArticle)).get(numColor-1));
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
		return (xpathArticuloBase + "//*[(" + classProductName + "]) and text()[contains(.,'" + string + "')]]");
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
		By byHearthIcon = By.xpath(getXPathHearthIconRelativeArticle());
		return (driver.findElements(byHearthIcon).size());
	}

	@Override
	public boolean eachArticlesHasOneFavoriteIcon() {  
		int numArticles = getNumArticulos(); 
		int numIcons = getNumFavoritoIcons();
		return (numArticles == numIcons);
	}
 
	public boolean isArticuloWithStringInName(String string) {
		String xpathArtWithString = getXPathNombreArticuloWithString(string);
		return (state(Present, By.xpath(xpathArtWithString)).check());
	}	
 
	@Override
	public int getLayoutNumColumnas() {
		if (app==AppEcom.outlet) {
			return getLayoutNumColumnasOutlet();
		}
		return getLayoutNumColumnasShop(); 
	}	
	
	private int getLayoutNumColumnasShop() {
		WebElement listArt = driver.findElement(By.xpath(XPATH_LIST_ARTICLES ));
		if (listArt.getAttribute("class").contains("columns3")) {
			return 3;
		}
		if (listArt.getAttribute("class").contains("columns4")) {
			return 4;
		}
		return 2;
	}
	
	private int getLayoutNumColumnasOutlet() {
		if (state(Present, By.xpath(xpathArticuloBase)).check()) {
			String classArt = driver.findElement(By.xpath(xpathArticuloBase)).getAttribute("class");
			if (classArt.contains("layout-3-columns")) {
				return 3;
			}
			else {
			   if (classArt.contains("layout-2-columns")) {
				   return 2;
			   }
			   else {
				   if (classArt.contains("layout-4-columns")) {
					   return 4;
				   }
			   }
			}
		}
		
		return 2;
	}

	@Override
	public String getNombreArticulo(WebElement articulo) {
		return (articulo.findElement(By.xpath("." + XPATH_NOMBRE_RELATIVE_TO_ARTICLE)).getText());
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
	 * @param numArticulo: posición en la galería del artículo
	 * @return la referencia de un artículo
	 */
	@Override
	public String getRefColorArticulo(WebElement articulo) {
		int lengthReferencia = 10;
		String id = getRefFromId(articulo);
		if (id.length()>lengthReferencia) {
			return (id.substring(0, lengthReferencia));
		}
		return id;
	}
	
	/**
	 * @return número de doble tamaño de la galería
	 */
	public int getNumArticulos(TypeArticleDesktop sizeArticle) {
		 By byArticulo = By.xpath(getXPathArticulo(sizeArticle));
		 return (driver.findElements(byArticulo).size());
	}
	
	@Override
	public int getNumArticulosFromPagina(int pagina, TypeArticleDesktop sizeArticle) {
		return (getListArticulosFromPagina(pagina).size());
	}
	
	@Override
	public WebElement getArticleFromPagina(int numPagina, int numArticle) {
		List<WebElement> listArticles = getListArticulosFromPagina(numPagina);
		if (listArticles.size()>=numArticle) {
			return listArticles.get(numArticle-1);
		}
		return null;
	}
	
	private List<WebElement> getListArticulosFromPagina(int numPagina) {
		By byArticulo = By.xpath(getXPathArticuloFromPagina(numPagina, TypeArticleDesktop.SIMPLE));
		return (driver.findElements(byArticulo));
	}

	@Override
	public boolean backTo1erArticulo() throws InterruptedException {
		return backTo1erArticulo(XPATH_ICONO_UP_GALERY);
	}
	
	/**
	 * @return la lista de referencia+color de los artículos incorrectos (size div != attr width de la imagen)
	 */
	public ListSizesArticle getArticlesWithWrongSize(int numPagina, double marginPercError) {	
		ListSizesArticle listSizesArtWrong = ListSizesArticle.getInstance();
		List<WebElement> listArticles = getListArticulosFromPagina(numPagina);
		for (WebElement article : listArticles) {
			int attrWidthImg = getWidthFromAtricleSrcImg(article);
			int widthArticle = getWidthArticle(article);
			int numPixelsDiff = Math.abs(attrWidthImg-widthArticle);
			if (attrWidthImg!=0) {
				double diffPercentage = (numPixelsDiff / Double.valueOf(attrWidthImg)) * 100;
				if (diffPercentage > marginPercError) {
					listSizesArtWrong.addData(getRefColorArticulo(article), attrWidthImg, widthArticle);
				}
			}
		}
		
		return listSizesArtWrong;
	}
	
	private int getWidthFromAtricleSrcImg(WebElement article) {
		int widthImg = 0;
		By byImgArticle = By.xpath(getXPathImgArticulo(article));
		if (state(Present, article).by(byImgArticle).check()) {
			WebElement imgArticle = article.findElement(byImgArticle);
			String srcImgArticle;
			if (app==AppEcom.outlet) {
				srcImgArticle = imgArticle.getAttribute("data-original");
			} else {
				//TODO Test AB nueva variante. Si se mantiene la original igualar con Outlet
			    srcImgArticle = imgArticle.getAttribute("original");
			}
			if (srcImgArticle!=null) {
				Pattern pattern = Pattern.compile("(.*?)width=(.*?)&(.*?)");
				Matcher matcher = pattern.matcher(srcImgArticle);
				if (matcher.find()) {
					 widthImg = Integer.valueOf(matcher.group(2));
				}
			}
		}
		
		return widthImg;
	}
	
	private int getWidthArticle(WebElement article) {
		return (article.getSize().getWidth());
	}
	
	public List<String> getArticlesRebajadosWithLiteralInLabel(List<LabelArticle> listLabels) {
		List<String> dataTextArticles = new ArrayList<>();
		for (LabelArticle label : listLabels) {
			String xpathLit = getXPathDataArticuloRebajadoWithLabel(label);
			dataTextArticles.addAll(getDataFromArticlesLiteral(xpathLit));
		}
		
		return dataTextArticles;
	}
	
	public List<String> getArticlesTemporadaxRebajadosWithLiteralInLabel(List<Integer> listTemporadas, List<LabelArticle> listLabels) {
		List<String> listArtSaleWithLabel = getArticlesRebajadosWithLiteralInLabel(listLabels);
		if (listArtSaleWithLabel.size() == 0) {
			return listArtSaleWithLabel;
		}
		
		List<String> listArtTempX = getArticlesTemporadasX(ControlTemporada.articlesFrom, listTemporadas);
		List<String> common = new ArrayList<String>(listArtTempX);
		common.retainAll(listArtSaleWithLabel);
		return common;
	}
	
	public List<String> getArticles(TypeArticle typeArticle, List<Integer> listTemporadas) {
		List<String> listArtOfType = getArticlesOfType(typeArticle);
		if (!listArtOfType.isEmpty()) {
			List<String> listArtTempX = getArticlesTemporadasX(ControlTemporada.articlesFrom, listTemporadas);
			List<String> common = new ArrayList<String>(listArtTempX);
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
		List<String> dataTextArticles = new ArrayList<String>();
	   	for (LabelArticle label : listLabels) {
	   		String xpathLit = getXPathDataArticuloTemporadaXWithLabel(temporadasX, label);
			dataTextArticles.addAll(getDataFromArticlesLiteral(xpathLit));
		}
		
		return dataTextArticles;
	}
	
	private List<String> getDataFromArticlesLiteral(String xpathLiteralArticle) {
		List<String> dataTextArticles = new ArrayList<String>();
		for (WebElement litWebEl : driver.findElements(By.xpath(xpathLiteralArticle))) {
			String referencia = litWebEl.getAttribute("id").replaceAll("_info", "");
			dataTextArticles.add(litWebEl.getText() + " (" + referencia + ")");
		}
		
		return dataTextArticles;
	}
	
	/**
	 * @return lista de artículos que tienen ambas etiquetas
	 */
	public List<String> getArticlesTemporadaXWithLiteralInLabel(List<Integer> temporadasX, LabelArticle label1, 
																	   LabelArticle label2) {
		List<String> listResult = new ArrayList<>();
		List<String> listArticles1 = getArticlesTemporadaXWithLiteralInLabel(temporadasX, Arrays.asList(label1));
		if (listArticles1.size()==0) {
			return listResult;
		}
		
		List<String> listArticles2 = getArticlesTemporadaXWithLiteralInLabel(temporadasX, Arrays.asList(label2));
		if (listArticles2.size()==0) {
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
	
	public void moveToArticleAndGetObject(int posArticulo) {
		By byArticle = By.xpath(getXPathLinkArticulo(posArticulo) + "/..");
		state(State.Visible, byArticle).wait(1).check();
		moveToElement(byArticle, driver);
	}

	@Override
	public void showTallasArticulo(int posArticulo) {
		//Nos posicionamos en el artículo y clicamos la capa. 
		//Es un click muy extraño porque cuando lo ejecutas automáticamente posiciona la capa en el top del navegador y queda oculta por el menú
		moveToArticleAndGetObject(posArticulo);
	}

	@Override
	public boolean isVisibleArticleCapaTallasUntil(int posArticulo, int maxSecondsToWait) {
		return secTallas.isVisibleArticleCapaTallasUntil(posArticulo, maxSecondsToWait);
	}
	
	@Override
	public ArticuloScreen selectTallaAvailableArticle(int posArticulo, int posTalla) throws Exception {
		//Si no está visible la capa de tallas ejecutamos los pasos necesarios para hacer la visible 
		waitForPageLoaded(driver);
		if (!isVisibleArticleCapaTallasUntil(posArticulo, 1)) {
			showTallasArticulo(posArticulo);
		}
		
		By byTalla = By.xpath(secTallas.getXPathArticleTallaAvailable(posArticulo, posTalla));
		if (state(State.Visible, byTalla).check()) {
			WebElement tallaToSelect = driver.findElement(byTalla);
			ArticuloScreen articulo = getArticuloObject(posArticulo);
			articulo.setTalla(Talla.fromLabel(tallaToSelect.getText()));
			tallaToSelect.click();
			return articulo;
		}
		return null;
	}

	public void selectTallaArticleNotAvalaible() {
		String xpathTallaNoDipo = secTallas.getXPathArticleTallaNotAvailable();
		By byTallaToSelect = By.xpath(xpathTallaNoDipo);
		click(byTallaToSelect).exec();
	}

	public boolean isVisibleAnyArticle() {
		return (state(Visible, By.xpath(xpathArticuloBase)).check());
	}

	public void clickArticulo(int numArticulo) {
		By byArticulo = By.xpath(getXPathLinkArticulo(numArticulo));
		click(byArticulo).exec();
		
		//Existe un problema en Firefox-Gecko con este botón: a veces el 1er click no funciona así que ejecutamos un 2o 
		if (state(Visible, byArticulo).check()) {
			try {
				click(byArticulo).type(javascript).exec();
			}
			catch (Exception e) {
				//Hay un caso en el que el artículo justo desaparece y se clicka -> 
				//Excepción pero la acción de click inicial fue correcta
			}
		}
	}
	
	public void clickSliderAfterHoverArticle(WebElement articulo, List<TypeSlider> typeSliderList)
	throws Exception {
		//Click Sliders
		for (TypeSlider typeSlider : typeSliderList) {
			SecCabecera secCabecera = SecCabecera.getNew(channel, app);
			secCabecera.hoverIconoBolsa();
			hoverArticle(articulo);
			waitMillis(500);
			hoverArticle(articulo);
			waitMillis(500);
			String xpathSlider = getXPathSliderRelativeToArticle(typeSlider, app);
			click(articulo).by(By.xpath(xpathSlider)).exec();
			waitForAjax(driver, 1);
			Thread.sleep(1000);
		}
	}

	public boolean isPresentSliderInArticleUntil(TypeSlider typeSlider, WebElement article, int maxSeconds) {
		for (int i=0; i<maxSeconds; i++) {
			if (isPresentSliderInArticle(typeSlider, article)) {
				return true;
			}
			waitMillis(1000);
		}
		return false;
	}
	
	public boolean isPresentSliderInArticle(TypeSlider typeSlider, WebElement article) {
		String xpathSlider = getXPathSliderRelativeToArticle(typeSlider, app);
		return (state(Present, article).by(By.xpath("." + xpathSlider)).check());
	}
	
	public String getNombreArticulo(int numArticulo) {
		return (driver.findElement(By.xpath("(" + xpathArticuloBase + ")[" + numArticulo + "]//span[" + classProductName + "]")).getText().trim());
	}

	/**
	 * Revisa si los nombres de los artículos son válidos (si los nombres contienen alguno de los del conjunto de literales)
	 * @param isMobil
	 * @param nombrePosibles: conjuntos de substrings que han de contener los artículos
	 * @return 
	 *	En caso de que todos los artículso tengan un nombre válido: ""
	 *	En caso de que exista algún artículo no válido: nombre del 1er artículo no válido  
	 */
	public ArrayList<String> nombreArticuloNoValido(String[] nombrePosibles) throws Exception {
		//Obtenemos el xpath de los artículos eliminando el último carácter (]) pues hemos de insertar condiciones en el XPATH
		String xpathLitArticulos = xpathArticuloBase + "//*[" + classProductName + "]";
		xpathLitArticulos = xpathLitArticulos.substring(0, xpathLitArticulos.length() - 1);
		for (int i=0; i<nombrePosibles.length; i++) {
			xpathLitArticulos +=  
				" and text()[not(contains(.,'" + nombrePosibles[i] + "'))]" +
				" and text()[not(contains(.,'" + nombrePosibles[i].toLowerCase() + "'))]" +
				" and text()[not(contains(.,'" + nombrePosibles[i].toUpperCase() + "'))]";
		} 
		xpathLitArticulos += "]";

		//Si existe algún elemento que no pertenece al grupo de nombres válidos -> devolvemos el 1ero que no coincide
		ArrayList<String> listTxtArtNoValidos = new ArrayList<>();
		if (state(Present, By.xpath(xpathLitArticulos)).check()) {
			for (int i=0; i<3; i++) {
				try {
					waitForPageLoaded(driver);
					List<WebElement> listTextosArticulosNoValidos = driver.findElements(By.xpath(xpathLitArticulos));
					for (WebElement textoArticuloNoValido : listTextosArticulosNoValidos) {
						String nombre = textoArticuloNoValido.getText();
						String xpathArtWithoutDoubleSlash = this.xpathArticuloBase.substring(2);
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
		WebElement artWElem = driver.findElements(By.xpath(xpathArticuloBase)).get(numArticulo-1);
		moveToElement(artWElem, driver);
		ArticuloScreen articulo = new ArticuloScreen();
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
		String xpathArticulo = "(" + xpathArticuloBase + ")[" + numArticulo + "]";
		String image = getImagenArticulo(driver.findElement(By.xpath(xpathArticulo)));
		return (UtilsPageGaleria.getCodColorFromSrcImg(image));
	}
	
	//Equivalent to Mobil
	@Override
	public String getNameColorFromCodigo(String codigoColor) {
		String xpathImgColor = secColores.getXPathImgCodigoColor(codigoColor);
		if (!state(Present, By.xpath(xpathImgColor)).check()) {
			return Constantes.colorDesconocido;
		}
		
		WebElement imgColorWeb = driver.findElement(By.xpath(xpathImgColor));
		return (imgColorWeb.getAttribute("data-variant"));
	}
	
	//Equivalent to Mobil
	@Override
	public ArrayList<ArticuloScreen> clickArticleHearthIcons(List<Integer> posIconsToClick) 
	throws Exception {
		ArrayList<ArticuloScreen> listArtFav = new ArrayList<>();
		for (int posIcon : posIconsToClick) {
			clickHearhIcon(posIcon);
			ArticuloScreen articulo = getArticuloObject(posIcon);
			listArtFav.add(articulo);
		}
		
		return listArtFav;
	}
	
	public void clickHearthIcon(WebElement hearthIcon) throws Exception {
		moveToElement(hearthIcon, driver);
		state(Clickable, hearthIcon).wait(1).check();
		try {
			hearthIcon.click();
		} 
		catch (ElementClickInterceptedException e) {
			clickHearthIconHiddindPossibleInterceptors(hearthIcon);
		}
	}
	
	private void clickHearthIconHiddindPossibleInterceptors(WebElement hearthIcon) throws Exception {
		SecFiltrosDesktop secFiltros = SecFiltrosDesktop.getInstance(channel, app, driver);
		secFiltros.makeFilters(Visibility.Invisible);
		
		SecLineasMenuDesktop secLineasMenuDesktop = SecLineasMenuDesktop.factory(app, channel);
		secLineasMenuDesktop.bringMenuBackground();
		
		hearthIcon.click();
		
		secLineasMenuDesktop.bringMenuFront();
		secFiltros.makeFilters(Visibility.Visible);

	}
	
	@Override
	public boolean isArticleWithHearthIconPresentUntil(int posArticle, int maxSeconds) {
		String XPathIcon = getXPathArticleHearthIcon(posArticle);
		return (state(Present, By.xpath(XPathIcon)).wait(maxSeconds).check());
	}
	
	@SuppressWarnings("static-access")
	//Equivalent to Mobil
	@Override
	public void clickHearhIcon(int posArticle) throws Exception {
		//Nos posicionamos en el icono del Hearth 
		String XPathIcon = getXPathArticleHearthIcon(posArticle);
		WebElement hearthIcon = driver.findElement(By.xpath(XPathIcon));
		moveToElement(hearthIcon, driver);
		
		//Clicamos y esperamos a que el icono cambie de estado
		StateFavorito estadoInicial = getStateHearthIcon(hearthIcon);
		clickHearthIcon(hearthIcon);
		int maxSecondsToWait = 2;
		switch (estadoInicial) {
		case MARCADO:
			waitToHearthIconInState(hearthIcon, StateFavorito.DESMARCADO, maxSecondsToWait);
			break;
		case DESMARCADO:
			waitToHearthIconInState(hearthIcon, StateFavorito.MARCADO, maxSecondsToWait);
			break;
		default:
			break;
		}		
	}

	@Override
	public boolean isHeaderArticlesVisible(String textHeader) {
		By byHeader = By.xpath(XPATH_HEADER_ARTICLES);
		if (state(Visible, byHeader).check()) {
			return (driver.findElement(byHeader).getText().toLowerCase().contains(textHeader.toLowerCase()));
		}
		return false;
	}

	@Override
	public StateFavorito getStateHearthIcon(WebElement hearthIcon) {
		if (hearthIcon.getAttribute("class").contains("icon-fill")) {
			return StateFavorito.MARCADO;
		}
		return StateFavorito.DESMARCADO;
	}

	public void clickLinkColumnas(NumColumnas numColumnas) {
		String xpathLink = getXPathLinkNumColumnas(numColumnas);
		click(By.xpath(xpathLink)).exec();
	}

	public boolean isPresentAnyArticle(TypeArticleDesktop typeArticle) {
		String xpathVideo = getXPathArticulo(typeArticle);
		return (state(Present, By.xpath(xpathVideo)).check());
	}

	public SecSubmenusGallery getSecSubmenusGallery() {
		return secSubmenusGallery;
	}

	public SecBannerHeadGallery getSecBannerHead() {
		return secBannerHead;
	}

	public SecCrossSelling getSecCrossSelling() {
		return secCrossSelling;
	}

	public SecSelectorPreciosDesktop getSecSelectorPreciosDesktop() {
		return secSelectorPreciosDesktop;
	}
}