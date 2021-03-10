package com.mng.robotest.test80.mango.test.pageobject.shop.galeria;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import com.mng.robotest.test80.mango.test.data.Constantes;
import com.mng.robotest.test80.mango.test.data.Talla;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;
import com.github.jorge2m.testmaker.conf.Channel;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleriaDesktop.TypeArticleDesktop;

/**
 * Clase que define la automatización de las diferentes funcionalidades de la página de "GALERÍA DE PRODUCTOS"
 * @author jorge.munoz
 */
public class PageGaleriaDevice extends PageGaleria {
	
    final static String TagIdColor = "@TagIdColor";
    final static String TagFlagSelected = "@TagFlagSelected";

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
    final static String XPathButtonAnyadirRelativeArticle = "//div[@class[contains(.,'product-add')]]/button";
    final static String XPathCapaTallasRelativeArticle = "//div[@class[contains(.,'product-sizes-container')]]";
    final static String XPathIconoUpGaleryMobile = "//div[@class[contains(.,'scroll-container--visible')]]";
    final static String XPathIconoUpGaleryTablet = "//div[@class='scroll-top-step']";
    final static String XPathFiltersDiv = "//div[@class='order-filters-fixed']";
    final static String TagNumPagina = "@tagNumPagina";
    final static String XPathPaginaMobileWithTag = "//div[@id='page" + TagNumPagina + "']";
    final static String XPathPaginaTabletWithTag = "//div[@id='page" + TagNumPagina + "Height']";
    final static String XPathHeaderArticles = "//h1[@class='catalog-title']";
    
    static String classProductName = 
        	"(@class[contains(.,'productList__name')] or " +
        	 "@class[contains(.,'product-list-name')] or " + 
        	 "@class='product-list-info-name' or " +
        	 "@class='product-name')";
    
    //TODO cuando suba el Outlet-Desktop-React a PRO podremos igualar este XPath con el XPathColoresArticuloShop (outlet=shop)
    final static String XPathColoresArticuloOutlet = "//div[@class[contains(.,'product-list-color--stock')] or @class[contains(.,'product-colors')]]";
    
    final static String XPathColoresArticulo = "//div[@class[contains(.,'product-colors')]]";
    final static String XPathColoresArticuloOutletTablet = "//div[@class[contains(.,'product-list-colors')]]";
    
    //TODO cuando suba el Outlet-Desktop-React a PRO podremos igualar este XPath con el XPathColoresArticuloShop (outlet=shop)
    //final static String XPathArticleWithColorsOutlet = "//div[@class[contains(.,'product-list-info-color')] or @class[contains(.,'product-colors')]]/ancestor::li";	
    //final static String XPathArticleWithColorsOutletTablet = "//div[@class[contains(.,'product-list-colors')]]/ancestor::li";
    
    String getXPathColoresArticle() {
    	if (channel==Channel.tablet && app==AppEcom.outlet) {
    		return XPathColoresArticuloOutletTablet;
    	}
    	return XPathColoresArticulo;
    }

    String getXPathArticuloConColores() {
    	return getXPathColoresArticle() + "//" + getXPathAncestorArticulo();
    }
    
    String getXPpathIconoUpGalery() {
    	switch (channel) {
    	case mobile:
    		return XPathIconoUpGaleryMobile;
    	case tablet:
    	default:
    		return XPathIconoUpGaleryTablet;
    	}
    }
    
    String getXPathImgColorRelativeArticleWithTagSelected() {
    	return (
    		getXPathColoresArticle() + 
    		"//self::*[@class[contains(.,'" + TagFlagSelected + "')]]//img");
    }
    
    @Override
    public String getXPathLinkRelativeToArticle() {
    	return "//a";
    }
    
    public String getXPathCabeceraBusquedaProd() {
        return ("//*[@id='buscador_cabecera2']");
    }

    //Número de páginas a partir del que consideramos que se requiere un scroll hasta el final de la galería
    public static int scrollToLast = 20; 
    
    private PageGaleriaDevice(From from, Channel channel, AppEcom app, WebDriver driver) {
    	super(from, channel, app, driver);
    }
    
    public static PageGaleriaDevice getNew(From from, Channel channel, AppEcom app, WebDriver driver) {
    	return (new PageGaleriaDevice(from, channel, app, driver)); 
    }
    
    String getXPathArticuloConVariedadColores(int numArticulo) {
        return ("(" + getXPathArticuloConColores() + ")" + "[" + numArticulo + "]");
    }
    
    String getXPathImgCodigoColor(String codigoColor) {
    	return XPathImgCodColorWithTagColor.replace(TagIdColor, codigoColor);
    }
    
    String getXPathImgColorRelativeArticle(boolean selected) {
        String selectedStr = "";
        if (selected) {
            selectedStr = "selected";
        }
        return (getXPathImgColorRelativeArticleWithTagSelected().replace(TagFlagSelected, selectedStr));
    }
    
    String getXPathButtonAnyadirArticle(int posArticulo) {
    	String xpathArticulo = "(" + XPathArticulo + ")[" + posArticulo + "]";
    	return (xpathArticulo + XPathButtonAnyadirRelativeArticle);
    }
    
    String getXPathArticleCapaTallas(int posArticulo) {
    	String xpathArticulo = "(" + XPathArticulo + ")[" + posArticulo + "]";
    	return (xpathArticulo + XPathCapaTallasRelativeArticle);
    }
    
    String getXPathPagina(int pagina) {
    	switch (channel) {
    	case mobile:
    		return (XPathPaginaMobileWithTag.replace(TagNumPagina, String.valueOf(pagina)));
    	case tablet:
    	default:
    		return (XPathPaginaTabletWithTag.replace(TagNumPagina, String.valueOf(pagina)));
    	}
    }
    
    @Override
    public WebElement getArticuloConVariedadColoresAndHover(int numArticulo) {
        String xpathArticulo = getXPathArticuloConVariedadColores(numArticulo);
        WebElement articulo = driver.findElement(By.xpath(xpathArticulo)); 
        hoverArticle(articulo);
        return articulo;
    }
    
    @Override
    public WebElement getImagenElementArticulo(WebElement articulo) {
    	moveToElement(articulo, driver);
    	By byImg = By.xpath("." + XPathImgRelativeArticle);
    	state(Clickable, byImg).wait(1).check();
    	return (articulo.findElement(byImg));
    }
    
    @Override
    public WebElement getColorArticulo(WebElement articulo, boolean selected, int numColor) {
        String xpathImgColorRelArticle = getXPathImgColorRelativeArticle(selected);
        return (articulo.findElements(By.xpath("." + xpathImgColorRelArticle)).get(numColor-1));
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
    
    @Override
    public int getLayoutNumColumnas() {
    	return 1;
    }         
    
    @Override
    public ArticuloScreen getArticuloObject(int numArticulo) throws Exception {
        WebElement artWElem = driver.findElements(By.xpath(XPathArticulo)).get(numArticulo-1);
        ArticuloScreen articulo = new ArticuloScreen();
        articulo.setReferencia(getRefArticulo(artWElem));
        articulo.setNombre(getNombreArticulo(artWElem));
        articulo.setPrecio(getPrecioArticulo(artWElem));
        articulo.setCodigoColor(getCodColorArticulo(numArticulo));
        articulo.setColorName(getNameColorFromCodigo(articulo.getCodigoColor()));
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
    	return (state(Present, articulo)
    			.by(By.xpath("." + XPathPrecioRebajadoRelativeArticle)).check());
    }
    
    @Override
    public String getCodColorArticulo(int numArticulo) throws Exception {
        String xpathArticulo = "(" + XPathArticulo + ")[" + numArticulo + "]";
        String image = getImagenArticulo(driver.findElement(By.xpath(xpathArticulo)));
        return (UtilsPageGaleria.getCodColorFromSrcImg(image));
    }
    
    @Override
    public String getNameColorFromCodigo(String codigoColor) {
    	String xpathImgColor = getXPathImgCodigoColor(codigoColor);
    	if (!state(Present, By.xpath(xpathImgColor)).check()) {
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
    public boolean isArticleWithHearthIconPresentUntil(int posArticle, int maxSeconds) {
    	String XPathIcon = getXPathArticleHearthIcon(posArticle);
    	return (state(Present, By.xpath(XPathIcon)).wait(maxSeconds).check());
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
    	if (state(Visible, byHeader).check()) {
    		return (driver.findElement(byHeader).getText().contains(textHeader));
    	}
    	return false;
    }

	@Override
	public void showTallasArticulo(int posArticulo) {
		moveToArticleAndGetObject(posArticulo);
		String xpathButtonAnyadir = getXPathButtonAnyadirArticle(posArticulo);
		click(By.xpath(xpathButtonAnyadir)).exec();
	}

    @Override
    public boolean isVisibleArticleCapaTallasUntil(int posArticulo, int maxSeconds) {
        String xpathCapa = getXPathArticleCapaTallas(posArticulo);
        return (state(Visible, By.xpath(xpathCapa), driver).wait(maxSeconds).check());
    }
    
    private String getXPathTallaAvailableArticle(int posArticulo, int posTalla) {
        String xpathCapa = getXPathArticleCapaTallas(posArticulo);
        return "(" + xpathCapa + "//button[@class='product-size']" + ")[" + posTalla + "]";
    }
    
    @Override
    public ArticuloScreen selectTallaAvailableArticle(int posArticulo, int posTalla) throws Exception {
        //Si no está visible la capa de tallas ejecutamos los pasos necesarios para hacer la visible 
        if (!isVisibleArticleCapaTallasUntil(posArticulo, 0/*maxSecondsToWait*/)) {
            showTallasArticulo(posArticulo);
        }
        
        String xpathTalla = getXPathTallaAvailableArticle(posArticulo, posTalla);
        WebElement tallaToSelect = driver.findElement(By.xpath(xpathTalla));
        ArticuloScreen articulo = getArticuloObject(posArticulo);
        articulo.setTalla(Talla.from(tallaToSelect.getText()));
        tallaToSelect.click();
        return articulo;
    }

	@Override
	public StateFavorito getStateHearthIcon(WebElement hearthIcon) {
		if (hearthIcon.getAttribute("class").contains("favorite--active")) {
			return StateFavorito.Marcado;
		}
		return StateFavorito.Desmarcado;
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
    	String xpathIconoUp = getXPpathIconoUpGalery();
    	return backTo1erArticulo(xpathIconoUp);
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
    	if (state(Present, articulo).by(By.xpath("." + xpathDivRelativeArticle)).check()) {
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