package com.mng.robotest.test80.mango.test.pageobject.shop.galeria;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import com.mng.robotest.test80.arq.utils.otras.Constantes;
import com.mng.robotest.test80.mango.test.data.AppEcom;
import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleriaDesktop.TypeArticleDesktop;

/**
 * Clase que define la automatización de las diferentes funcionalidades de la página de "GALERÍA DE PRODUCTOS"
 * @author jorge.munoz
 */
public class PageGaleriaMobil extends PageGaleria {
	final AppEcom app;
	
    final static String TagIdColor = "@TagIdColor";
    final static String TagFlagSelected = "@TagFlagSelected";
    final static String classProductItem = 
    	"@class[contains(.,'productList__name')] or " + 
    	"@class[contains(.,'product-list-name')] or " + 
    	"@class='product-list-info-name' or " +
    	"@class='product-name'";
    final static String XPathNombreRelativeToArticle = "//*[" + classProductItem + "]";
    final static String XPathColoresArticulo = 
    	"//div[@class[contains(.,'product-list-color--stock')] or " + 
    		  "@class[contains(.,'product-colors')]]";
    final static String XPathArticuloConColores = XPathColoresArticulo + "//" + getXPathAncestorArticulo();
    final static String XPathImgColorRelativeArticleWithTagSelected = 
    	XPathColoresArticulo + 
    	"//self::*[@class[contains(.,'" + TagFlagSelected + "')]]//img";
    final static String XPathImgRelativeArticle = 
        "//img[@src and " + 
        	 "(@class[contains(.,'productListImg')] or " + 
        	  "@class[contains(.,'product-list-image')] or " + 
        	  "@class[contains(.,'product-list-img')] or " +
        	  "@class[contains(.,'product-image')])]";
    final static String XPathImgCodColorWithTagColor = 
    	"//div[@class[contains(.,'color-container')] and @id='" + TagIdColor + "']/img";
    final static String XPathPrecioRebajadoRelativeArticle = 
        "//*[@class[contains(.,'product-list-sale-price')] or " + 
        	"@class[contains(.,'product-list-info-price-sale')] or " + 
        	"@class[contains(.,'product-price-crossed')]]";
    final static String XPathPrecioOriginalRelativeArticle = 
        "//*[@class[contains(.,'productList__price')] or " + 
        	"@class[contains(.,'productList__salePrice')] or " + 
        	"@class[contains(.,'product-list-info-price')] or " + 
        	"@class[contains(.,'product-list-price')] or " +
        	"@class[contains(.,'product-price')]]";
    final static String XPathHearthIconRelativeArticle = 
    	"//span[@class[contains(.,'product-list-fav')] or " + 
    		   "@class[contains(.,'product-favorite')]]";
    final static String XPathButtonAnyadirRelativeArticle = "//div[@class[contains(.,'product-add')]]/button";
    final static String XPathCapaTallasRelativeArticle = "//div[@class[contains(.,'product-sizes-container')]]";
    final static String XPpathIconoUpGalery = "//div[@id='scrollTop']";
    final static String XPathFiltersDiv = "//div[@class='order-filters-fixed']";
    final static String TagNumPagina = "@tagNumPagina";
    final static String XPathPaginaWithTag = "//div[@id='page" + TagNumPagina + "']";
    final static String XPathHeaderArticles = "//h1[@class='catalog-title']";
    
    static String classProductName = 
        	"(@class[contains(.,'productList__name')] or " +
        	 "@class[contains(.,'product-list-name')] or " + 
        	 "@class='product-list-info-name' or " +
        	 "@class='product-name')";
    
    @Override
    public String getXPathLinkRelativeToArticle() {
    	return "//a";
    }
    
    public String getXPathCabeceraBusquedaProd() {
        return ("//*[@id='buscador_cabecera2']");
    }

    //Número de páginas a partir del que consideramos que se requiere un scroll hasta el final de la galería
    public static int scrollToLast = 20; 
    
    private PageGaleriaMobil(AppEcom app, WebDriver driver) {
    	this.driver = driver;
    	this.app = app;
    }
    
    public static PageGaleriaMobil getInstance(AppEcom app, WebDriver driver) {
    	return (new PageGaleriaMobil(app, driver)); 
    }
    
    static String getXPathArticuloConVariedadColores(int numArticulo) {
        return ("(" + XPathArticuloConColores + ")" + "[" + numArticulo + "]");
    }
    
    static String getXPathImgCodigoColor(String codigoColor) {
    	return XPathImgCodColorWithTagColor.replace(TagIdColor, codigoColor);
    }
    
    static String getXPathImgColorRelativeArticle(boolean selected) {
        String selectedStr = "";
        if (selected) {
            selectedStr = "selected";
        }
        return (XPathImgColorRelativeArticleWithTagSelected.replace(TagFlagSelected, selectedStr));
    }
    
    static String getXPathArticleHearthIcon(int posArticulo) {
        String xpathArticulo = "(" + XPathArticulo + ")[" + posArticulo + "]";
        return (xpathArticulo + XPathHearthIconRelativeArticle);
    }
    
    static String getXPathButtonAnyadirArticle(int posArticulo) {
    	String xpathArticulo = "(" + XPathArticulo + ")[" + posArticulo + "]";
    	return (xpathArticulo + XPathButtonAnyadirRelativeArticle);
    }
    
    static String getXPathArticleCapaTallas(int posArticulo) {
    	String xpathArticulo = "(" + XPathArticulo + ")[" + posArticulo + "]";
    	return (xpathArticulo + XPathCapaTallasRelativeArticle);
    }
    
    String getXPathPagina(int pagina) {
    	return (XPathPaginaWithTag.replace(TagNumPagina, String.valueOf(pagina)));
    }
    
    @Override
    public WebElement getArticuloConVariedadColoresAndHover(int numArticulo) {
        String xpathArticulo = getXPathArticuloConVariedadColores(numArticulo);
        WebElement articulo = driver.findElement(By.xpath(xpathArticulo)); 
        hoverArticle(articulo);
        return articulo;
    }
    
    @Override
    public WebElement getImagenArticulo(WebElement articulo) {
    	return (articulo.findElement(By.xpath("." + XPathImgRelativeArticle)));
    }
    
    @Override
    public WebElement getColorArticulo(WebElement articulo, boolean selected, int numColor) {
        String xpathImgColorRelArticle = getXPathImgColorRelativeArticle(selected);
        return (articulo.findElements(By.xpath("." + xpathImgColorRelArticle)).get(numColor-1));
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
    
    @Override
    public int getLayoutNumColumnas() {
    	return 1;
    }         
    
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
    
    @Override
    public String getNombreArticulo(WebElement articulo) {
        return (articulo.findElement(By.xpath("." + XPathNombreRelativeToArticle)).getText());
    }
    
    @Override
    public String getPrecioArticulo(WebElement articulo) {
    	if (isArticleRebajado(articulo)) {
            return (articulo.findElement(By.xpath("." + XPathPrecioRebajadoRelativeArticle)).getText());    
    	}
        return (articulo.findElement(By.xpath("." + XPathPrecioOriginalRelativeArticle)).getText());
    }    
    
    @Override
    public boolean isArticleRebajado(WebElement articulo) {
    	return (isElementPresent(articulo, By.xpath("." + XPathPrecioRebajadoRelativeArticle)));
    }
    
    @Override
    public String getCodColorArticulo(int numArticulo) {
        String xpathArticulo = "(" + XPathArticulo + ")[" + numArticulo + "]";
        WebElement imgArticle = getImagenArticulo(driver.findElement(By.xpath(xpathArticulo)));
        return (UtilsPageGaleria.getCodColorFromSrcImg(imgArticle.getAttribute("src")));
    }
    
    @Override
    public String getNameColorFromCodigo(String codigoColor) {
    	String xpathImgColor = getXPathImgCodigoColor(codigoColor);
    	if (!isElementPresent(driver, By.xpath(xpathImgColor))) {
    		return Constantes.colorDesconocido;
    	}
    	WebElement imgColorWeb = driver.findElement(By.xpath(xpathImgColor));
    	return (imgColorWeb.getAttribute("title"));
    }
    
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
    
    @Override
	public void clickHearhIcon(int posArticle) throws Exception {
        //Nos posicionamos en el icono del Hearth 
        String XPathIcon = getXPathArticleHearthIcon(posArticle);
        WebElement hearthIcon = driver.findElement(By.xpath(XPathIcon));
        moveToElement(hearthIcon, driver);
        
        //Clicamos y esperamos a que el icono cambie de estado
        StateFavorito estadoInicial = getStateHearthIcon(hearthIcon);
        clickHearthIconPreventingOverlapping(hearthIcon);
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
    public int getNumArticulosFromPagina(int pagina, TypeArticleDesktop sizeArticle) {
    	return (getListArticulosFromPagina(pagina).size());
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
    	By byHeader = By.xpath(XPathHeaderArticles);
    	if (WebdrvWrapp.isElementVisible(driver, byHeader)) {
    		return (driver.findElement(byHeader).getText().contains(textHeader));
    	}
    	
    	return false;
    }
    
    @Override
    public void selectLinkAddArticleToBag(int posArticulo) throws Exception {
        moveToArticleAndGetObject(posArticulo);
        String xpathButtonAnyadir = getXPathButtonAnyadirArticle(posArticulo);
        WebdrvWrapp.clickAndWaitLoad(driver, By.xpath(xpathButtonAnyadir));
    }
    
    @Override
    public boolean isVisibleArticleCapaTallasUntil(int posArticulo, int maxSecondsToWait) {
        String xpathCapa = getXPathArticleCapaTallas(posArticulo);
        return (isElementVisibleUntil(driver, By.xpath(xpathCapa), maxSecondsToWait));
    }
    
    private static String getXPathTallaAvailableArticle(int posArticulo, int posTalla) {
        String xpathCapa = getXPathArticleCapaTallas(posArticulo);
        return "(" + xpathCapa + "//button[@class='product-size']" + ")[" + posTalla + "]";
    }
    
    @Override
    public ArticuloScreen selectTallaArticle(int posArticulo, int posTalla) throws Exception {
        //Si no está visible la capa de tallas ejecutamos los pasos necesarios para hacer la visible 
        if (!isVisibleArticleCapaTallasUntil(posArticulo, 0/*maxSecondsToWait*/)) {
            selectLinkAddArticleToBag(posArticulo);
        }
        
        String xpathTalla = getXPathTallaAvailableArticle(posArticulo, posTalla);
        WebElement tallaToSelect = driver.findElement(By.xpath(xpathTalla));
        ArticuloScreen articulo = getArticuloObject(posArticulo);
        articulo.setTallaAlf(tallaToSelect.getText());
        articulo.setTallaNum(tallaToSelect.getAttribute("data-id"));
        tallaToSelect.click();
        return articulo;
    }
    
    private List<WebElement> getListArticulosFromPagina(int numPagina) {
	    By byArticulo = By.xpath(getXPathArticuloFromPagina(numPagina));
	    return (driver.findElements(byArticulo));
    }
    
    String getXPathArticuloFromPagina(int pagina) {
    	String xpathPagina = getXPathPagina(pagina);
    	return  (xpathPagina + XPathArticulo);
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
    	return backTo1erArticulo(XPpathIconoUpGalery);
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
    
//    private String getRefColorArticuloMethod1(WebElement articulo) {
//    	return (articulo.getAttribute("id"));
//    }
    
    private String getRefColorArticuloMethod1(WebElement articulo) {
    	String xpathDivRelativeArticle = "//div[@id and @class='product-container-image']";
    	if (WebdrvWrapp.isElementPresent(articulo, By.xpath("." + xpathDivRelativeArticle))) {
	    	return (articulo.findElement(By.xpath(xpathDivRelativeArticle)).getAttribute("id"));
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