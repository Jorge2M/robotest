package com.mng.robotest.test80.mango.test.pageobject.shop.galeria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test80.mango.test.pageobject.TypeOfClick;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.PageFicha;
import com.mng.robotest.test80.mango.test.pageobject.shop.filtros.FilterOrdenacion;
import com.mng.robotest.test80.mango.test.pageobject.shop.footer.SecFooter;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleriaDesktop.TypeArticleDesktop;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.desktop.SecMenusDesktop;
import com.mng.robotest.test80.mango.test.pageobject.utils.DataScroll;
import com.mng.robotest.test80.mango.test.pageobject.utils.NombreYRef;
import com.mng.robotest.test80.mango.test.pageobject.utils.NombreYRefList;
import com.mng.robotest.test80.mango.test.stpv.shop.galeria.LocationArticle;
import com.mng.robotest.test80.mango.test.stpv.shop.galeria.PageGaleriaStpV.TypeActionFav;
import com.mng.robotest.test80.mango.test.utils.UtilsTestMango;

public abstract class PageGaleria extends WebdrvWrapp {
	
    //Número de páginas a partir del que consideramos que se requiere un scroll hasta el final de la galería
    public static int maxPageToScroll = 20; 
    WebDriver driver;
	
    abstract public String getXPathLinkRelativeToArticle();
	abstract public int getLayoutNumColumnas();
	abstract public WebElement getArticuloConVariedadColoresAndHover(int numArticulo);
	abstract public WebElement getImagenArticulo(WebElement articulo);
	abstract public WebElement getColorArticulo(WebElement articulo, boolean selected, int numColor);
	abstract public ArticuloScreen getArticuloObject(int numArticulo);
	abstract public String getNombreArticulo(WebElement articulo);
	abstract public String getPrecioArticulo(WebElement articulo);
	abstract public boolean isArticleRebajado(WebElement articulo);
	abstract public String getCodColorArticulo(int numArticulo);
	abstract public String getNameColorFromCodigo(String codigoColor);
	abstract public int getNumFavoritoIcons();
	abstract public boolean eachArticlesHasOneFavoriteIcon();
	abstract public ArrayList<ArticuloScreen> clickArticleHearthIcons(List<Integer> posIconsToClick) throws Exception;
	abstract public boolean isArticleWithHearthIconPresentUntil(int posArticle, int maxSecondsToWait);
	abstract public void clickHearhIcon(int posArticle) throws Exception;
	abstract public String getRefColorArticulo(WebElement articulo);
	abstract public boolean backTo1erArticulo() throws InterruptedException;
	abstract String getXPathPagina(int pagina);
	abstract int getNumArticulosFromPagina(int pagina, TypeArticleDesktop sizeArticle);
	abstract public WebElement getArticleFromPagina(int numPagina, int numArticle);
    
    public static List<LabelArticle> listLabelsNew = Arrays.asList(
    	    LabelArticle.ComingSoon, 
    	    LabelArticle.NewNow, 
    	    LabelArticle.NewCollection);
	
	final static String XPathLinkRelativeToArticle = ".//a[@class='product-link']";
	
    final static String XPathHearthIconRelativeArticle = 
    	"//span[@class[contains(.,'product-favorite')] or " + 
    	       "@class[contains(.,'product-list-fav')]]";
    
	public static PageGaleria getInstance(Channel channel, AppEcom app, WebDriver driver) throws Exception {
		switch (channel) {
		case desktop:
			return (PageGaleriaDesktop.getInstance(app, driver));
		case movil_web:
		default:
			return (PageGaleriaMobil.getInstance(app, driver));
		}
	}
	
	//Código común a Desktop y Móvil...

	final static String XPathArticulo = 
		"//*[" +
			"@id[contains(.,'__item')] or " + 
			"@class[contains(.,'list-item')] or " + 
			"@class='product'" + 
		"]";
	
	final static String XPathArticuloNoDoble =
		XPathArticulo +
			"//self::*[not(@class[contains(.,'layout-2-coumns-A2')])]";
	
    final static String XPathPrecioDefinitivoRelativeArticle = 
        "//span[@class[contains(.,'price')] and " + 
        	   "@class[not(contains(.,'line__through'))] and " + 
        	   "@class[not(contains(.,'line-through'))]]";
	
	public static String getXPathAncestorArticulo() {
		return (XPathArticulo.replaceFirst("//", "ancestor::"));
	}
	
    static String classProductName = 
        	"(@class[contains(.,'productList__name')] or " +
        	 "@class[contains(.,'product-list-name')] or " + 
        	 "@class='product-list-info-name' or " +
        	 "@class='product-name')";
    
    public String getXPathCabeceraBusquedaProd() {
        return ("//*[@id='buscador_cabecera2']");
    }
	
    /**
     * @param numArticulo: posición del artículo en la galería
     * @return el xpath correspondiente al link de un artículo
     */
    static String getXPathLinkArticulo(int numArticulo) {
        return ("(" + XPathArticulo + ")[" + numArticulo + "]//a");
    }  

    public boolean isVisibleArticuloUntil(int numArticulo, int seconds) {
        String xpathArticulo = XPathArticulo + "[" + numArticulo + "]"; 
        return (isElementVisibleUntil(driver, By.xpath(xpathArticulo), seconds));
    }
    
    public boolean isClickableArticuloUntil(int numArticulo, int seconds) {
        String xpathArticulo = XPathArticulo + "[" + numArticulo + "]"; 
        return (isElementClickableUntil(driver, By.xpath(xpathArticulo), seconds));
    }
	
    public List<WebElement> getListaArticulos() {
        List<WebElement> listaArticulos = driver.findElements(By.xpath(XPathArticulo));
        return listaArticulos;
    }
	
    public boolean articlesInOrder(FilterOrdenacion typeOrden) {
        return ("".compareTo(getAnyArticleNotInOrder(typeOrden))==0);
    }

    static String getXPathArticleHearthIcon(int posArticulo) {
        String xpathArticulo = "(" + XPathArticulo + ")[" + posArticulo + "]";
        return (xpathArticulo + XPathHearthIconRelativeArticle);
    }
    
    public void hoverArticle(WebElement article) {
    	moveToElement(article, driver);
    }
    
    /**
     * @return número de artículos de la galería
     */
    public int getNumArticulos() {
        return (driver.findElements(By.xpath(XPathArticulo)).size());
    }    
    
    public int waitForArticleVisibleAndGetNumberOfThem(int maxSecondsToWait) {
    	int numArticle = 1;
        isVisibleArticleUntil(numArticle, maxSecondsToWait);
        return (getNumArticulos());
    }
    
	public boolean waitArticleAndGoTo(int numArticulo, int maxSecondsToWait) { 
        String xpathUltArticulo = getXPathLinkArticulo(numArticulo);
        if (isVisibleArticleUntil(numArticulo, maxSecondsToWait)) {
            ((Locatable) driver.findElement(By.xpath(xpathUltArticulo))).getCoordinates().inViewPort();
            return true;
        }
        
        return false;
    }
    
    /**
     * Espera hasta que está presente un artículo en la galería
     * @return número de artículos que aparecen en la galería
     */
    public boolean isVisibleArticleUntil(int numArticulo, int seconds) {
        //Esperamos a que esté la imagen del 1er artículo pintada
        String xpathArtGaleria = "(" + XPathArticulo + ")[" + numArticulo + "]";
        return (isElementVisibleUntil(driver, By.xpath(xpathArtGaleria), seconds));
    }
    
    public boolean isFirstArticleOfType(LineaType lineaType) {
	    List<WebElement> listaArticulos = driver.findElements(By.xpath(XPathArticulo));
	    return (listaArticulos.size() > 0 &&
	    		isElementPresent(listaArticulos.get(0), By.xpath("//a[@href[contains(.,'" + lineaType + "')]]")));
    }
    
    /**
     * Indica si los artículos de la galería realmente están ordenados por precio ascendente o descendente
     */
    public String getAnyArticleNotInOrder(FilterOrdenacion typeOrden) {
        switch (typeOrden) {
        case NOordenado:
            return "";
        case PrecioAsc:
        case PrecioDesc:
            return getAnyPrecioNotInOrder(typeOrden);
        case TemporadaDesc:
        case TemporadaAsc:
        case BloqueTemporadas_2y3_despues_la_4:
        case BloqueTemporada_4_despues_la_2y3:
            return getAnyRefNotInOrderTemporada(typeOrden);
        default:
            return "";
        }
    }
    
    public List<WebElement> getListaPreciosPrendas() {
    	List<WebElement> listPrecios = new ArrayList<WebElement>();
    	List<WebElement> listaArticulos = getListaArticulos(); 
    	for (WebElement articulo : listaArticulos) {
	        WebElement precio = getElementVisible(articulo, By.xpath("." + XPathPrecioDefinitivoRelativeArticle));
	        if (precio!=null)
	        	listPrecios.add(precio);
    	}
    	
    	return listPrecios;
    }
    
    public String getAnyPrecioNotInOrder(FilterOrdenacion typeOrden) {
        List<WebElement> listaPreciosPrendas = getListaPreciosPrendas();
        float precioAnt = 0;
        if (typeOrden==FilterOrdenacion.PrecioDesc)
            precioAnt = 9999999;

        for (WebElement prendaPrecio : listaPreciosPrendas) {
            String entero = prendaPrecio.getText();
            float precioActual = Float.valueOf(entero.replace(",",".").replaceAll("[^\\d.]", "")).floatValue();
            if (typeOrden==FilterOrdenacion.PrecioAsc) {
                if (precioActual < precioAnt)
                    return (precioAnt + "->" + precioActual);
            } 
            else {
                if (precioActual > precioAnt)
                    return (precioAnt + "->" + precioActual);
            }

            precioAnt = precioActual;
        }

        return "";        
    }
    
    public String getAnyRefNotInOrderTemporada(FilterOrdenacion typeOrden) {
        ArrayList<String> listaReferencias = getListaReferenciasPrendas();
        String refAnterior="";
        for (String refActual : listaReferencias) {
            String tempActual = refActual.substring(0,1);
            if ("".compareTo(refAnterior)!=0) {
                String tempAnterior = refAnterior.substring(0,1);
                int tempActualInt = Integer.valueOf(tempActual).intValue();
                int tempAnteriorInt = Integer.valueOf(tempAnterior).intValue();
                switch (typeOrden) {
                case TemporadaDesc:
                    if (tempActualInt > tempAnteriorInt)
                        return (refAnterior + "->" + refActual);
                    break;
                case TemporadaAsc:
                    if (tempActualInt < tempAnteriorInt)
                        return (refAnterior + "->" + refActual);
                    break;
            	case BloqueTemporadas_2y3_despues_la_4:
            		if ((tempActualInt==1 || tempActualInt==2) && tempAnteriorInt==3)
            			return (refAnterior + "->" + refActual);
            		break;
            	case BloqueTemporada_4_despues_la_2y3:
            	default:
            		if (tempActualInt==3 && (tempAnteriorInt==1 || tempAnteriorInt==2))
            			return (refAnterior + "->" + refActual);
            		break;
                }
            }
            
            refAnterior = refActual;
        }

        return "";        
    }    
    
    /**
     * @return si los precios de los artículos están en un determinado intervalo de mínimo/máximo
     */
    public boolean preciosInIntervalo(int minimo, int maximo) {
        List<WebElement> listaPreciosPrendas = getListaPreciosPrendas();
        for (WebElement prendaPrecio : listaPreciosPrendas) {
            String entero = prendaPrecio.getText();
            float precioActual = Float.valueOf(entero.replace(",",".").replaceAll("[^\\d.]", "")).floatValue();
            if (precioActual < minimo || precioActual > maximo)
                return false;
        }
        
        return true;
    }
    
    /**
     * @return la lista de elementos que contienen la referencia del artículo
     */
    public ArrayList<String> getListaReferenciasPrendas() {
        ArrayList<String> listaReferencias = new ArrayList<>();
        List<WebElement> listaArticulos = getArticulos();
        for (WebElement articulo : listaArticulos)
            listaReferencias.add(getRefArticulo(articulo));
        
        return listaReferencias;
    }
    
    public String getReferencia(int posArticle) {
        if (getListaReferenciasPrendas().size()>0)
            return (getListaReferenciasPrendas().get(posArticle-1));
        
        return "";
    }
    
    public List<WebElement> getArticulos() {
        return (driver.findElements(By.xpath(XPathArticulo))); 
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
        if (listArticulos.size()>=numArticulo)
        	return (listArticulos.get(numArticulo-1));
        
        return null;
    }
    
    public static String getRefArticulo(WebElement articulo) {
    	int lengthReferencia = 8;
    	String id = articulo.getAttribute("id");
    	if ("".compareTo(id)!=0) {
	    	if (id.length()>lengthReferencia)
	    		return (id.substring(0, lengthReferencia));
	    	
	    	return id;
    	}
    	
    	//Para el caso TestAB-1 se ejecutará este caso para conseguir los atributos del artículo
    	String href = articulo.findElement(By.xpath(XPathLinkRelativeToArticle)).getAttribute("href");
    	return (UtilsTestMango.getReferenciaFromHref(href));
    }
    
    /**
     * @return el nombre y referencia con color del artículo en formato "NOMBRE (REFERENCIACOLOR)"
     */
    public NombreYRef getNombreYRefArticulo(WebElement articulo) {
        String nombreArticulo = getNombreArticulo(articulo);
        String refArticulo = getRefColorArticulo(articulo);
        return (new NombreYRef(nombreArticulo, refArticulo));
    }
    
    //Equivalent to Mobil
    public boolean waitToHearthIconInState(WebElement hearthIcon, StateFavorito stateIcon, int maxSecondsToWait) {
        try {
            new WebDriverWait(driver, maxSecondsToWait).until(attributeContains(hearthIcon, "data-fav", stateIcon.getDataFav()));
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
    
    //Equivalent to Mobil
    void clickHearthIcon(WebElement hearthIcon) throws Exception {
    	moveToElement(hearthIcon, driver);
    	isElementClickableUntil(driver, hearthIcon, 1/*seconds*/);
        hearthIcon.click();
    }
    
    static StateFavorito getStateHearthIcon(WebElement hearthIcon) {
    	String dataFav = hearthIcon.getAttribute("data-fav");
    	if (dataFav!=null) {
	        if (hearthIcon.getAttribute("data-fav").contains(StateFavorito.Desmarcado.getDataFav()))
	            return StateFavorito.Desmarcado;
	        return StateFavorito.Marcado;
    	}
    	
    	if (hearthIcon.getAttribute("class").contains("active"))
    		return StateFavorito.Marcado;
    	return StateFavorito.Desmarcado;
    }
    
    /**
     * Busca artículos repetidos en la galería
     * @return 1er artículo repetido en la galería. Si no encuentra ninguno devuelve null
     */
    public ArrayList<NombreYRef> searchArticleRepeatedInGallery() {
        NombreYRefList list = getListaNombreYRefArticulos();
        return (list.getArticlesRepeated());
    }
    
    public NombreYRefList getListaNombreYRefArticulos() {
        NombreYRefList listReturn = new NombreYRefList();
        for (WebElement articulo : getListaArticulos()) {
            listReturn.add(getNombreYRefArticulo(articulo)); 
        }
        return listReturn;
    }

    /**
     * @return si todos los iconos de favorito están marcados
     */
    public boolean iconsInCorrectState(List<Integer> posIconosFav, TypeActionFav typeAction) {
        for (int posIcon : posIconosFav) {
            String XPathIcon = getXPathArticleHearthIcon(posIcon);
            WebElement hearthIcon = driver.findElement(By.xpath(XPathIcon));
            switch (typeAction) {
            case Marcar:
                if (getStateHearthIcon(hearthIcon)!=StateFavorito.Marcado)
                    return false;
                break;
            case Desmarcar:
                if (getStateHearthIcon(hearthIcon)!=StateFavorito.Desmarcado)
                    return false;
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
        int numArtToWait = 1;
        int secondsToWait = 2;
        boolean isVisible1erArt = isVisibleArticuloUntil(numArtToWait, secondsToWait);
        Thread.sleep(1000);
        return isVisible1erArt;
    }
    
    public void clickIconoUpToGaleryIfVisible(String xpathIconoUpGalery) {
    	int maxSecondsToWait = 1;
        if (isElementVisibleUntil(driver, By.xpath(xpathIconoUpGalery), maxSecondsToWait))
            driver.findElement(By.xpath(xpathIconoUpGalery)).click();
    }
    
    /**
     * @param driver
     * @param categoriaProducto
     * @return si existe la cabecera con el resultado de la búsqueda de una determinada categoría de producto
     */

    public String getArticuloWithText(String name, int secondsWait) {
        String articulo = "";
        
        //Obtenemos el xpath de los artículos eliminando el último carácter (]) pues hemos de insertar condiciones en el XPATH
        String xpathLitArticulos = XPathArticulo + "//*[" + classProductName + "]";
        xpathLitArticulos = xpathLitArticulos.substring(0, xpathLitArticulos.length() - 1);
        
        xpathLitArticulos = xpathLitArticulos + " and text()[contains(.,'" + name + "')]]"; 
        
        if (isElementVisibleUntil(driver, By.xpath(xpathLitArticulos), secondsWait)) {
            articulo = driver.findElement(By.xpath(xpathLitArticulos)).getText();
        }
        
        return articulo;
    }
    
    /**
     * @return devuelve un artículo con un determinado texto (lo espera durante los segundos indicados) 
     */

    public boolean isCabeceraResBusqueda(String categoriaProducto) {
        boolean isCabecera = false;
        String xpathCabe = getXPathCabeceraBusquedaProd();
        if (isElementPresent(driver, By.xpath(xpathCabe)))
            if (driver.findElement(By.xpath(xpathCabe)).getText().toLowerCase().contains(categoriaProducto.toLowerCase()))
                isCabecera = true;
        
        return isCabecera;
    }
    
    /**
     * Función que realiza un scroll/paginación hasta el final de los artículos. Retorna el número de elementos obtenidos
     * Desktop: scrolla reiteradamente hasta el último elemento para forzar la paginación
     */
    public DataScroll scrollToPageFromFirst(int numPage, AppEcom app) throws Exception {
        DataScroll datosScroll = new DataScroll();
        int pageToScroll = getPageToScroll(numPage);
        goToInitPageAndWaitForArticle();
        
        int lastPage = getNumLastPage();
        List<Integer> numArticlesXpage = new ArrayList<>();
        List<Integer> numArticlesDoubleXpage = new ArrayList<>();
        initializeDataNumArticles(numArticlesXpage, numArticlesDoubleXpage, pageToScroll);
        updateDataNumArticles(numArticlesXpage, numArticlesDoubleXpage, lastPage);
        while (!SecFooter.isVisible(app, driver) &&
               lastPage < pageToScroll) {
        	forceScroll();
        	int newLastPage = getNumLastPage();
        	updateDataNumArticles(numArticlesXpage, numArticlesDoubleXpage, newLastPage);
        	if (newLastPage==lastPage)
        		break;
        	lastPage=newLastPage;
        }
        
        datosScroll.paginaFinal = lastPage;
        datosScroll.finalAlcanzado = SecFooter.isVisible(app, driver);
        datosScroll.articulosMostrados = getNumArticulos();
        datosScroll.articulosDobleTamaño = getTotalNumArticles(numArticlesDoubleXpage);
        datosScroll.articulosTotalesPagina = getTotalNumArticles(numArticlesXpage);
        return (datosScroll);
    }
    
    private int getPageToScroll(int numPage) {
        if (numPage > maxPageToScroll)
            return maxPageToScroll;
        
        return numPage;
    }
    
    private void goToInitPageAndWaitForArticle() throws Exception {
	    Long pagePosition = (Long) ((JavascriptExecutor) driver).executeScript("return window.pageYOffset;");
	    if (pagePosition != 0)
	    	backTo1erArticulo();
	    
	    int maxSecondsWait = 2;
	    isVisibleArticleUntil(1, maxSecondsWait);
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
	    			int numArticlesPage = getNumArticulosFromPagina(page, TypeArticleDesktop.Doble);
	    			numArticlesDoubleXpage.set(page, numArticlesPage);
	    		}
    		
	    		if (numArticlesXpage.get(page)==0) {
	    			int numArticlesPage = getNumArticulosFromPagina(page, TypeArticleDesktop.Simple) + 
	    								  numArticlesDoubleXpage.get(page);
	    			numArticlesXpage.set(page, numArticlesPage);
	    		}
    		}
		
    		page-=1;
    	}
    }
    
    private int getTotalNumArticles(List<Integer> numArticlesXpage) {
    	int numTotalArticles = 0;
    	for (int i=0; i<numArticlesXpage.size(); i++)
    		numTotalArticles+=numArticlesXpage.get(i);
    	
    	return numTotalArticles;
    }
    
    private void forceScroll() throws Exception {
    	waitAndGotoLastArticle();
    	
	    //Scrollamos un poco más para asegurar que forzamos el scroll de artículos
	    //(En simulador-móvil-chrome esto no acaba de funcionar bien)
	    ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,+50)", "");
	    
	    waitForPageLoaded(driver);
    }
    
    private void waitAndGotoLastArticle() {
    	List<WebElement> listaArticulos = getListaArticulos();
    	int maxSecondsWait = 5;
    	waitArticleAndGoTo(listaArticulos.size(), maxSecondsWait);
    }
    
    public int getNumLastPage() {
    	int maxPagesToReview = 20;
    	int lastPageWatched = 0;
    	while (lastPageWatched<maxPagesToReview) {
    		int pageToReview = lastPageWatched + 1;
    		if (!isPresentPagina(pageToReview))
    			return lastPageWatched;
    	
    		lastPageWatched=pageToReview;
    	}
    	
    	return 0;
    }
    
    public boolean isPresentPagina(int pagina) {
    	String xpathPagina = getXPathPagina(pagina);
    	return (isElementVisible(driver, By.xpath(xpathPagina)));
    }
    
    public void clickArticulo(WebElement articulo) throws Exception {
    	WebElement articleName = articulo.findElement(By.xpath("." + getXPathLinkRelativeToArticle()));
    	moveToElement(articulo, driver);
        clickAndWaitLoad(driver, articleName, 30, TypeOfClick.javascript);
    }
    
    @SuppressWarnings("static-access")
	public String openArticuloPestanyaAndGo(WebElement article, AppEcom app) 
	throws Exception {
        String galeryWindowHandle = driver.getWindowHandle();
        
        //En el caso de Firefox-Geckodriver el moveToElement (que se acaba realizando mediante el workarround basado en JavaScript) 
        //nos posiciona en la esquina superior izquierda que queda debajo del menú superior... así que tenemos que enviar dicho menú al fondo
        SecMenusDesktop.secMenuSuperior.secLineas.bringMenuBackground(app, driver);
        
        WebElement articleName = article.findElement(By.xpath("." + getXPathLinkRelativeToArticle()));
        UtilsMangoTest.openLinkInNewTab(driver, articleName);
        
        //Cambiamos el foco de driver a la nueva pestaña que hemos creado y esperamos hasta que está disponible
        String detailWindowHandle = switchToAnotherWindow(driver, galeryWindowHandle);
        
        PageFicha pageFicha = PageFicha.newInstance(app, Channel.desktop, driver);
        pageFicha.isPageUntil(10/*maxSecondsWait*/);
        
        return detailWindowHandle;
    }    
    
    public static List<String> getNotNewArticlesFrom(List<String> listNameAndLabelArticles) {
    	List<String> listArtToReturn = new ArrayList<>();
    	for (String nameAndLabelArticle : listNameAndLabelArticles) {
    		if (!isArticleNew(nameAndLabelArticle))
    			listArtToReturn.add(nameAndLabelArticle);
    	}
    	   
    	return listArtToReturn;
    }

    private static boolean isArticleNew(String nameAndLabelArticle) {
    	for (LabelArticle label : listLabelsNew) {
    		for (String labelNew : label.getListTraducciones()) {
    			if (nameAndLabelArticle.contains(labelNew) || nameAndLabelArticle.contains(labelNew.toUpperCase()))
    				return true;
    		}
    	}
    	   
    	return false;
    }
    

}
