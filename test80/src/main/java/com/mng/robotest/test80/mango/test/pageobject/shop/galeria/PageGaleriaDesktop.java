package com.mng.robotest.test80.mango.test.pageobject.shop.galeria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.mng.robotest.test80.arq.utils.otras.Constantes;
import com.mng.robotest.test80.arq.utils.webdriver.maker.FactoryWebdriverMaker.TypeWebDriver;
import com.mng.robotest.test80.mango.test.data.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test80.arq.webdriverwrapper.TypeOfClick;
import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.desktop.SecMenusDesktop;

/**getArticuloConVariedadColoresAndHover
 * Clase que define la automatización de las diferentes funcionalidades de la página de "GALERÍA DE PRODUCTOS"
 * @author jorge.munoz
 */
public class PageGaleriaDesktop extends PageGaleria {
	//TODO REFACTORIZAR!!!
    //TODO acabar de modificar orientando a "WebElement articulo"
    public static SecBannerHeadGallery secBannerHead;
    public static SecSelectorPrecios secSelectorPrecios;
    public static SecCrossSelling secCrossSelling;
    
    final AppEcom app;
    
    public enum NumColumnas {dos, tres, cuatro}
    public enum TypeSlider {prev, next}
    public enum TypeColor {codigo, nombre}
    public enum TypeArticleDesktop {
    	Simple("//self::*[@data-imgsize = 'A1']"),
    	Doble("//self::*[@data-imgsize = 'A2']"),
    	Panoramica("//self::*[@class[contains(.,'-A2')] or @class[contains(.,'-A3')]]"),
    	Video("//video");
    	
    	String xpathRelativeArticle;
    	private TypeArticleDesktop(String xpathRelativeArticle) {
    		this.xpathRelativeArticle = xpathRelativeArticle;
    	}
    	
    	public String getXPathRelativeArticle() {
    		return this.xpathRelativeArticle;
    	}
    }
    
    static String classProductName = 
    	"(@class[contains(.,'productList__name')] or " +
    	 "@class[contains(.,'product-list-name')] or " + 
    	 "@class='product-list-info-name' or " +
    	 "@class='product-name')";
    
    private final static String TagFlagSelected = "@TagFlagSelected";
    private final static String XPathArticuloConColores = "//div[@class[contains(.,'color')]]/" + getXPathAncestorArticulo();
    private final static String XPathArticuloColoresNoDoble = XPathArticuloConColores + "//self::*[not(@class[contains(.,'layout-2-coumns-A2')])]";
    private final static String iniXPathPaginaGaleria = "//div[@id='page";
    private final static String XPathNombreRelativeToArticle = "//*[" + classProductName + "]";
    private final static String XPathImgRelativeArticle = 
    	"//img[@src and (" + 
    		   "@class[contains(.,'productListImg')] or " + 
    		   "@class[contains(.,'product-list-image')] or " + 
    		   "@class[contains(.,'product-list-im')])]";
    private final static String XPathImgSliderActiveRelativeArticleDesktop = 
        "//div[@class[contains(.,'swiper-slide-active')]]" + 
        "//img[@src and (@class[contains(.,'productListImg')] or @class[contains(.,'product-list-im')])]";
    private final static String XPathPrecioOriginalRelativeArticle = 
    	"//*[@class[contains(.,'productList__price')] or " + 
    		"@class[contains(.,'productList__salePrice')] or " + 
    		"@class[contains(.,'product-list-info-price')] or " + 
    		"@class[contains(.,'product-list-price')]]"; 
    private final static String XPathPrecioRebajadoRelativeArticle = 
    	"//*[@class[contains(.,'product-list-sale-price')] or " + 
    		"@class[contains(.,'product-list-info-price-sale')]]";
    private final static String XPathPrecioNoRebajadoRelativeArticle = 
        "//span[@class='product-list-price']";
    private final static String TagIdColor = "@TagIdColor";
    private final static String XPathImgCodColorWithTagColor = "//img[@class[contains(.,'other-color')] and @data-id='" + TagIdColor + "']";
    private final static String XPathImgColorRelativeArticleWithTagSelected = "//div[@class[contains(.,'color" + TagFlagSelected + "')]]//img";
    private final static String XPpathIconoUpGalery = "//div[@id='scroll-top-step']";
    private final static String XpathTallaNoDisponibleArticulo = "//span[@data-id and (@class[contains(.,'no-stock')])]";
    private final static String XPathHeaderArticles = "//div[@id[contains(.,'title')]]/h1";
    
    @Override
    public String getXPathLinkRelativeToArticle() {
    	return XPathNombreRelativeToArticle;
    }
 
    private PageGaleriaDesktop(AppEcom app, WebDriver driver) {
    	this.app = app;
    	this.driver = driver;
    }
    
    public static PageGaleriaDesktop getInstance(AppEcom app, WebDriver driver) throws Exception {
    	return (new PageGaleriaDesktop(app, driver));
    }
    
    public boolean isPage() {
        return (isElementPresent(driver, By.xpath("//div[@class[contains(.,'container-fluid catalog')]]")));
    }
    
    private String getXPathImgCodigoColor(String codigoColor) {
    	return XPathImgCodColorWithTagColor.replace(TagIdColor, codigoColor);
    }
    
    private String getXPathImgColorRelativeArticle(boolean selected) {
        String selectedStr = "";
        if (selected) {
            selectedStr = "--selected";
        }
        return (XPathImgColorRelativeArticleWithTagSelected.replace(TagFlagSelected, selectedStr));
    }
    
    public enum TypeArticle {rebajado, norebajado};
    private String getXPathDataArticuloOfType(TypeArticle typeArticle) {
    	switch (typeArticle) {
    	case rebajado:
    		return (XPathArticulo + XPathPrecioRebajadoRelativeArticle + "//ancestor::div[@class[contains(.,'product-list-info')]]");
    	case norebajado:
    	default:
    		return (XPathArticulo + XPathPrecioNoRebajadoRelativeArticle + "//ancestor::div[@class[contains(.,'product-list-info')]]");
    	}
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
    	String xpathResult = XPathArticulo + "/self::*[@id and ";
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
    	return ((XPathArticulo + sizeArticle.getXPathRelativeArticle()));
    }
    
    @Override
    String getXPathPagina(int pagina) {
    	return (iniXPathPaginaGaleria + pagina + "Height']");
    }

    private static String getXPathArticleCapaInferiorDesktop(int posArticulo) {
        String xpathArticulo = "(" + XPathArticulo + ")[" + posArticulo + "]";
        return (xpathArticulo + "//div[@class[contains(.,'add-cart')] and @data-stock]");
    }
    
    private static String getXPathArticleLinkCapaAddDesktop(int posArticulo, boolean capaVisible) {
        String xpathCapaAdd = getXPathArticleCapaInferiorDesktop(posArticulo);
        String classSegunVisible = "not(@class[contains(.,'active')])";
        if (capaVisible) {
            classSegunVisible = "@class[contains(.,'active')]";
        }
        return (xpathCapaAdd + "//p[@class[contains(.,'first-step')] and " + classSegunVisible + "]");
    }
    
    private static String getXPathArticleCapaTallas(int posArticulo, boolean capaVisible) {
        String xpathCapaAdd = getXPathArticleCapaInferiorDesktop(posArticulo);
        String classSegunVisible = "not(@class[contains(.,'active')])";
        if (capaVisible) {
            classSegunVisible = "@class[contains(.,'active')]";
        }
        return (xpathCapaAdd + "//div[@class[contains(.,'add-cart-sizes-container')] and " + classSegunVisible + "]");
    }

    private static String getXPathArticleTallaAvailable(int posArticulo, int posTalla) {
        String xpathCapaTallas = getXPathArticleCapaTallas(posArticulo, true/*capaVisible*/);
        return "(" + xpathCapaTallas + "//span[@data-id and not(@class[contains(.,'no-stock')])]" + ")[" + posTalla + "]";
    }

    private static String getXPathArticleTallaNotAvailable() {
        return  XpathTallaNoDisponibleArticulo;
    }

    private static String getXPathSliderRelativeToArticle(TypeSlider typeSlider) {
    	return ("//span[@class='swiper-button-" + typeSlider + "']");
    }
    
    private static String getXPathArticuloConVariedadColores(int numArticulo) {
        return ("(" + XPathArticuloConColores + ")" + "[" + numArticulo + "]");
    }
    
    private static String getXPathArticuloColoresNoDoble(int numArticulo) {
    	return ("(" + XPathArticuloColoresNoDoble + ")" + "[" + numArticulo + "]");
    }
    
    @Override
    public WebElement getArticuloConVariedadColoresAndHover(int numArticulo) {
        String xpathArticulo = getXPathArticuloConVariedadColores(numArticulo);
        WebElement articulo = driver.findElement(By.xpath(xpathArticulo)); 
        hoverArticle(articulo);
        return articulo;
    }
    
    public WebElement getArticuloConVariedadColoresAndHoverNoDoble(int numArticulo) {
        String xpathArticulo = getXPathArticuloColoresNoDoble(numArticulo);
        WebElement articulo = driver.findElement(By.xpath(xpathArticulo)); 
        hoverArticle(articulo);
        return articulo;
    }
    
    public boolean isArticleFromLinea(int numArticle, LineaType lineaType) {
    	return (isArticleFromLinCarrusel(numArticle, lineaType.toString()));
    }
    
    public boolean isArticleFromCarrusel(int numArticle, Linea linea, String idCarrusel) {
    	return (isArticleFromLinCarrusel(numArticle, idCarrusel));    	
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
		if (article!=null) {
			if (isElementPresent(article, By.xpath(".//a[@href[contains(.,'" + idLinCarrusel + "')]]")) ||
				isElementPresent(article, By.xpath(".//a[@href[contains(.,'s=" + linea1rstCharCapital + "')]]")) ||
				isElementPresent(article, By.xpath(".//a[@href[contains(.,'_" + lineaLastCharCapital + "')]]"))) {
				return true;
			}
			
			//Los ids de carrusels para niño/niña son nino/nina pero a nivel del HTML de los artículos figura KidsA/KidsO
			LineaType lineaType = LineaType.getLineaType(idLinCarrusel);
			if (lineaType!=null && 
			   (lineaType==LineaType.nina || lineaType==LineaType.nino)) {
				if (isElementPresent(article, By.xpath(".//a[@href[contains(.,'" + lineaType.getId2() + "')]]"))) {
					return true;
				}
			}
		}
		
		return false;
    }
    
    @Override
    public WebElement getImagenArticulo(WebElement articulo) {
    	if (isPresentSliderInArticle(TypeSlider.next, articulo)) {
    		hoverSliderUntilClickable(TypeSlider.next, articulo);
    		return (articulo.findElement(By.xpath("." + XPathImgSliderActiveRelativeArticleDesktop)));
    	} else {
    		return (articulo.findElement(By.xpath("." + XPathImgRelativeArticle)));
    	}
    }

    @Override
    public WebElement getColorArticulo(WebElement articulo, boolean selected, int numColor) {
    	String xpathImgColorRelArticle = getXPathImgColorRelativeArticle(selected);
        return (articulo.findElements(By.xpath("." + xpathImgColorRelArticle)).get(numColor-1));
    }

    /**
     * @param categoriaProducto categoría de producto (p.e. "BOLSOS")
     * @return el xpath correspondiente a la cabecera de resultado de una búsqueda de una determinada categoría de producto
     */
    public String getXPathCabeceraBusquedaProd() {
        return ("//*[@id='buscador_cabecera2']");
    }

    public String getXPATH_nombreArticuloWithString(String string) {
        return (XPathArticulo + "//*[(" + classProductName + "]) and text()[contains(.,'" + string + "')]]");
    }
    
    public String getXPathLinkNumColumnas(NumColumnas numColumnas) {
        return ("//button[@id='navColumns" + getNumColumnas(numColumnas) + "']");
    }
    
    public int getNumColumnas(NumColumnas numColumnas) {
        switch (numColumnas) {
        case dos:
            return 2;
        case tres:
            return 3;
        case cuatro:
            return 4;
        default:
            return -1;
        }
    }

    @Override
    public int getNumFavoritoIcons() {
        return (driver.findElements(By.xpath(XPathHearthIconRelativeArticle)).size());
    }
    
    @Override
    public boolean eachArticlesHasOneFavoriteIcon() {  
        int numArticles = getNumArticulos(); 
        int numIcons = getNumFavoritoIcons();
        return (numArticles == numIcons);
    }
 
    public boolean isArticuloWithStringInName(String string) {
        String xpathArtWithString = getXPATH_nombreArticuloWithString(string);
        return (isElementPresent(driver, By.xpath(xpathArtWithString)));
    }    
 
    @Override
    public int getLayoutNumColumnas() {
        if (isElementPresent(driver, By.xpath(XPathArticulo))) {
            String classArt = driver.findElement(By.xpath(XPathArticulo)).getAttribute("class");
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
        return (articulo.findElement(By.xpath("." + XPathNombreRelativeToArticle)).getText());
    }

    @Override
    public boolean isArticleRebajado(WebElement articulo) {
    	return (isElementPresent(articulo, By.xpath("." + XPathPrecioRebajadoRelativeArticle)));
    }
    
    @Override
    public String getPrecioArticulo(WebElement articulo) {
    	if (isArticleRebajado(articulo)) {
            return (articulo.findElement(By.xpath("." + XPathPrecioRebajadoRelativeArticle)).getText());    
    	}
        return (articulo.findElement(By.xpath("." + XPathPrecioOriginalRelativeArticle)).getText());
    }    
    
    /**
     * @param numArticulo: posición en la galería del artículo
     * @return la referencia de un artículo
     */
    @Override
    public String getRefColorArticulo(WebElement articulo) {
    	int lengthReferencia = 10;
    	String id = articulo.getAttribute("id");
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
    	By byArticulo = By.xpath(getXPathArticuloFromPagina(numPagina, TypeArticleDesktop.Simple));
    	return (driver.findElements(byArticulo));
    }

    @Override
    public boolean backTo1erArticulo() throws InterruptedException {
    	return backTo1erArticulo(XPpathIconoUpGalery);
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
    	By byImgArticle = By.xpath("." + XPathImgRelativeArticle);
    	if (WebdrvWrapp.isElementPresent(article, byImgArticle)) {
    		WebElement imgArticle = article.findElement(byImgArticle);
	    	String srcImgArticle = imgArticle.getAttribute("data-original");
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
    
    public ArrayList<String> getArticlesRebajadosWithLiteralInLabel(List<LabelArticle> listLabels) {
    	ArrayList<String> dataTextArticles = new ArrayList<String>();
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
    	if (listArtOfType.size()>0) {
    		List<String> listArtTempX = getArticlesTemporadasX(ControlTemporada.articlesFrom, listTemporadas);
    		List<String> common = new ArrayList<String>(listArtTempX);
    		common.retainAll(listArtOfType);
    		return common;
    	}
    	
    	return listArtOfType;
    }
    
    public List<String> getArticlesOfType(TypeArticle typeArticle) {
    	//ArrayList<String> dataTextArticles = new ArrayList<String>();
    	String xpathArtReb = getXPathDataArticuloOfType(typeArticle);

//    	for (WebElement litWebEl : driver.findElements(By.xpath(xpathArtReb))) {
//    		dataTextArticles.add(litWebEl.getText());
//    	}
//    	return dataTextArticles;
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
        moveToElement(By.xpath(getXPathLinkArticulo(posArticulo) + "/.."), driver);
    }
    
    @Override
    public void selectLinkAddArticleToBag(int posArticulo) throws Exception {
        //Nos posicionamos en el artículo y clicamos la capa. 
        //Es un click muy extraño porque cuando lo ejecutas automáticamente posiciona la capa en el top del navegador y queda oculta por el menú
        moveToArticleAndGetObject(posArticulo);
        String xpathCapaAlta = getXPathArticleLinkCapaAddDesktop(posArticulo, true/*capaVisible*/);
        int i=0;
        while (i<5) {
            try {
                driver.findElement(By.xpath(xpathCapaAlta)).click();
                break;
            }
            catch (WebDriverException e) {
                //Scrollamos un poquito hacia arriba para asegurar
                ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-100)", "");
                Thread.sleep(200);
                i+=1;
            }
        }
    }
    
    @Override
    public boolean isVisibleArticleCapaTallasUntil(int posArticulo, int maxSecondsToWait) {
        String xpathCapa = getXPathArticleCapaTallas(posArticulo, true/*capaVisible*/);
        return (isElementVisibleUntil(driver, By.xpath(xpathCapa), maxSecondsToWait));
    }
    
    @Override
    public ArticuloScreen selectTallaArticle(int posArticulo, int posTalla) throws Exception {
        //Si no está visible la capa de tallas ejecutamos los pasos necesarios para hacer la visible 
        if (!isVisibleArticleCapaTallasUntil(posArticulo, 0/*maxSecondsToWait*/)) {
            selectLinkAddArticleToBag(posArticulo);
        }
        
        String xpathTalla = getXPathArticleTallaAvailable(posArticulo, posTalla);
        WebElement tallaToSelect = driver.findElement(By.xpath(xpathTalla));
        ArticuloScreen articulo = getArticuloObject(posArticulo);
        articulo.setTallaAlf(tallaToSelect.getText());
        articulo.setTallaNum(tallaToSelect.getAttribute("data-id"));
        tallaToSelect.click();
        return articulo;
    }

    public void selectTallaArticleNotAvalaible() throws Exception {

        String xpathTallaNoDipo = getXPathArticleTallaNotAvailable();
        By byTallaToSelect = By.xpath(xpathTallaNoDipo);
        clickAndWaitLoad(driver, byTallaToSelect);
    }
    
    public boolean isVisibleAnyArticle() {
    	return (isElementVisible(driver, By.xpath(XPathArticulo)));
    }

    public void clickArticulo(int numArticulo) throws Exception {
    	By byArticulo = By.xpath(getXPathLinkArticulo(numArticulo));
        clickAndWaitLoad(driver, byArticulo);
        
        //Existe un problema en Firefox-Gecko con este botón: a veces el 1er click no funciona así que ejecutamos un 2o 
        if (isElementVisible(driver, byArticulo)) {
        	try {
        		clickAndWaitLoad(driver, byArticulo, TypeOfClick.javascript);
        	}
        	catch (Exception e) {
        		//Hay un caso en el que el artículo justo desaparece y se clicka -> 
        		//Excepción pero la acción de click inicial fue correcta
        	}
        }
    }
    
    public void clickSliderAfterHoverArticle(WebElement articulo, ArrayList<TypeSlider> typeSliderList)
    throws Exception {
        //Click Sliders
        for (TypeSlider typeSlider : typeSliderList) {
        	WebElement slider = hoverSliderUntilClickable(typeSlider, articulo);
            slider.click();
            waitForAjax(driver, 1/*timeoutInSeconds*/);
            Thread.sleep(1000);
        }
    }

    public boolean isPresentSliderInArticle(TypeSlider typeSlider, WebElement article) {
    	String xpathSlider = getXPathSliderRelativeToArticle(typeSlider);
    	return (isElementPresent(article, By.xpath("." + xpathSlider)));
    }
    
    public WebElement hoverSliderUntilClickable(TypeSlider typeSlider, WebElement article) {
    	String xpathSlider = getXPathSliderRelativeToArticle(typeSlider);
    	WebElement slider = article.findElement(By.xpath("." + xpathSlider));
    	hoverArticle(article);
    	if (getTypeDriver(driver)!=TypeWebDriver.firefox) {
    		isElementClickableUntil(driver, slider, 5);
    	} else {
    		//TODO En el caso de Firefox-Geckodriver hay problemas con los moveToElement. 
    		//En este caso parece que se posiciona en la esquina superior izquierda
    		//Cuando se solvente podremos eliminar este código específico
    		Actions actions = new Actions(driver);
    		int i=0;
    		while (!isElementClickableUntil(driver, slider, 1) && i<5) {
    			actions.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).build().perform();
    			hoverArticle(article);
    			i+=1;
    		}
    	}
    	
    	return slider;
    }
    
    public String getNombreArticulo(int numArticulo) {
        return (driver.findElement(By.xpath("(" + XPathArticulo + ")[" + numArticulo + "]//span[" + classProductName + "]")).getText().trim());
    }

	/**
     * Revisa si los nombres de los artículos son válidos (si los nombres contienen alguno de los del conjunto de literales)
     * @param isMobil
     * @param nombrePosibles: conjuntos de substrings que han de contener los artículos
     * @return 
     *    En caso de que todos los artículso tengan un nombre válido: ""
     *    En caso de que exista algún artículo no válido: nombre del 1er artículo no válido  
     */
    public ArrayList<String> nombreArticuloNoValido(String[] nombrePosibles) {
        //Obtenemos el xpath de los artículos eliminando el último carácter (]) pues hemos de insertar condiciones en el XPATH
        String xpathLitArticulos = XPathArticulo + "//*[" + classProductName + "]";
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
        if (isElementPresent(driver, By.xpath(xpathLitArticulos))) {
            List<WebElement> listTextosArticulosNoValidos = driver.findElements(By.xpath(xpathLitArticulos));
            for (WebElement textoArticuloNoValido : listTextosArticulosNoValidos) {
                String nombre = textoArticuloNoValido.getText();
                String referencia = textoArticuloNoValido.findElement(By.xpath("./ancestor::*[@class[contains(.,'product-list-item')]]")).getAttribute("id");
                listTxtArtNoValidos.add(nombre + " (" + referencia + ")");
            }
        }
        
        return listTxtArtNoValidos;
    }
    
    //Equivalent to Mobil
    @Override
    public ArticuloScreen getArticuloObject(int numArticulo) {
        WebElement artWElem = driver.findElements(By.xpath(XPathArticulo)).get(numArticulo-1);
        ArticuloScreen articulo = new ArticuloScreen();
        articulo.setReferencia(getRefArticulo(artWElem));
        articulo.setNombre(getNombreArticulo(artWElem));
        articulo.setPrecio(getPrecioArticulo(artWElem));
        articulo.setCodigoColor(getCodColorArticulo(numArticulo));
        articulo.setColor(getNameColorFromCodigo(articulo.getCodigoColor()));
        articulo.setNumero(1);
        
        return articulo;
    }
    
    //Equivalent to Mobil
    @Override
    public String getCodColorArticulo(int numArticulo) {
        String xpathArticulo = "(" + XPathArticulo + ")[" + numArticulo + "]";
        WebElement imgArticle = getImagenArticulo(driver.findElement(By.xpath(xpathArticulo)));
        return (UtilsPageGaleria.getCodColorFromSrcImg(imgArticle.getAttribute("src")));
    }
    
    //Equivalent to Mobil
    @Override
    public String getNameColorFromCodigo(String codigoColor) {
    	String xpathImgColor = getXPathImgCodigoColor(codigoColor);
    	if (!isElementPresent(driver, By.xpath(xpathImgColor))) {
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
    
    @Override
    public boolean isArticleWithHearthIconPresentUntil(int posArticle, int maxSecondsToWait) {
    	String XPathIcon = getXPathArticleHearthIcon(posArticle);
    	return (isElementPresentUntil(driver, By.xpath(XPathIcon), maxSecondsToWait));
    }
    
    @SuppressWarnings("static-access")
    //Equivalent to Mobil
    @Override
	public void clickHearhIcon(int posArticle) throws Exception {
        //Nos posicionamos en el icono del Hearth 
        String XPathIcon = getXPathArticleHearthIcon(posArticle);
        WebElement hearthIcon = driver.findElement(By.xpath(XPathIcon));
        moveToElement(hearthIcon, driver);
        
        //Hacemos el menú superior transparente porque en ocasiones tapa el icono de favoritos
        SecMenusDesktop.secMenuSuperior.secLineas.bringMenuBackground(app, driver);
        
        //Clicamos y esperamos a que el icono cambie de estado
        StateFavorito estadoInicial = getStateHearthIcon(hearthIcon);
        clickHearthIcon(hearthIcon);
        int maxSecondsToWait = 2;
        switch (estadoInicial) {
        case Marcado:
            waitToHearthIconInState(hearthIcon, StateFavorito.Desmarcado, maxSecondsToWait);
            break;
        case Desmarcado:
            waitToHearthIconInState(hearthIcon, StateFavorito.Marcado, maxSecondsToWait);
            break;
        default:
            break;
        }        
    }
    
    @Override
    public boolean isHeaderArticlesVisible(String textHeader) {
    	By byHeader = By.xpath(XPathHeaderArticles);
    	if (WebdrvWrapp.isElementVisible(driver, byHeader)) {
    		return (driver.findElement(byHeader).getText().toLowerCase().contains(textHeader.toLowerCase()));
    	}
    	
    	return false;
    }

    public void clickLinkColumnas(NumColumnas numColumnas) throws Exception {
        String xpathLink = getXPathLinkNumColumnas(numColumnas);
        clickAndWaitLoad(driver, By.xpath(xpathLink));
    }
    
    public boolean isPresentAnyArticle(TypeArticleDesktop typeArticle) {
    	String xpathVideo = getXPathArticulo(typeArticle);
    	return (isElementPresent(driver, By.xpath(xpathVideo)));
    }

}