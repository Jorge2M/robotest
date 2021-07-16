package com.mng.robotest.test80.mango.test.pageobject.shop.galeria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Locatable;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.PageFicha;
import com.mng.robotest.test80.mango.test.pageobject.shop.filtros.FilterOrdenacion;
import com.mng.robotest.test80.mango.test.pageobject.shop.footer.SecFooter;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleriaDesktop.TypeArticleDesktop;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.desktop.SecMenusDesktop;
import com.mng.robotest.test80.mango.test.pageobject.utils.DataArticleGalery;
import com.mng.robotest.test80.mango.test.pageobject.utils.DataScroll;
import com.mng.robotest.test80.mango.test.pageobject.utils.ListDataArticleGalery;
import com.mng.robotest.test80.mango.test.stpv.shop.galeria.LocationArticle;
import com.mng.robotest.test80.mango.test.stpv.shop.galeria.PageGaleriaStpV.TypeActionFav;
import com.mng.robotest.test80.mango.test.utils.UtilsTestMango;

public abstract class PageGaleria extends PageObjTM {
	
	public enum From {menu, buscador}
	
	public static int maxPageToScroll = 20; 
	final Channel channel;
	final AppEcom app;
	final From from;
	final String XPathArticulo;
	final SecPreciosArticulo secPrecios;

	public PageGaleria(From from, Channel channel, AppEcom app, WebDriver driver) {
		super(driver);
		this.from = from;
		this.channel = channel;
		this.app = app;
		this.XPathArticulo = getXPathArticulo();
		this.secPrecios = new SecPreciosArticulo(channel, app, driver);
	}
	
	abstract public String getXPathLinkRelativeToArticle();
	abstract public int getLayoutNumColumnas();
	abstract public WebElement getArticuloConVariedadColoresAndHover(int numArticulo);
	abstract public WebElement getImagenElementArticulo(WebElement articulo) throws Exception;
	abstract public WebElement getColorArticulo(WebElement articulo, boolean selected, int numColor);
	abstract public ArticuloScreen getArticuloObject(int numArticulo) throws Exception;
	abstract public String getNombreArticulo(WebElement articulo);
	abstract public String getPrecioArticulo(WebElement articulo);
	abstract public boolean isArticleRebajado(WebElement articulo);
	abstract public String getCodColorArticulo(int numArticulo) throws Exception;
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
	abstract public boolean isHeaderArticlesVisible(String textHeader);
	abstract public void showTallasArticulo(int posArticulo);
	abstract public boolean isVisibleArticleCapaTallasUntil(int posArticulo, int maxSecondsToWait);
	abstract public ArticuloScreen selectTallaAvailableArticle(int posArticulo, int posTalla) throws Exception;
	abstract public StateFavorito getStateHearthIcon(WebElement hearthIcon);

	
    public static List<LabelArticle> listLabelsNew = Arrays.asList(
    	    LabelArticle.ComingSoon, 
    	    LabelArticle.NewNow, 
    	    LabelArticle.NewCollection);
	
    final static String classProductItem = 
        	"@class[contains(.,'productList__name')] or " + 
        	"@class[contains(.,'product-list-name')] or " + 
        	"@class='product-list-info-name' or " +
        	"@class[contains(.,'_1P8s4')] or " + //TODO (Outlet) a la espera que Sergio Campillo proporcione un identificador válido
        	"@class[contains(.,'product-name')]";
    final static String XPathNombreRelativeToArticle = "//*[" + classProductItem + "]";
    final static String XPathLinkRelativeToArticle = ".//a[@class='product-link']";

	public static PageGaleria getNew(Channel channel, AppEcom app, WebDriver driver) {
		return PageGaleria.getNew(From.menu, channel, app, driver);
	}
	public static PageGaleria getNew(From from, Channel channel, AppEcom app, WebDriver driver) {
		switch (channel) {
		case desktop:
			return (PageGaleriaDesktop.getNew(from, channel, app, driver));
		case mobile:
		case tablet:
//			if (app==AppEcom.outlet) {
//				return (PageGaleriaDesktop.getNew(from, channel, app, driver));
//			}
		default:
			return (PageGaleriaDevice.getNew(from, channel, app, driver));
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
	
	final static String XPathArticuloDesktop = "//li[@id[contains(.,'product-key-id')]]";
	
	//TODO adaptar React (pendiente petición a Jesús Bermúdez 3-Marzo-2021)
	final static String XPathArticuloDesktopBuscador = 
		"//div[@class[contains(.,'product-list-item')] or @class[contains(.,'z0q8P')] or @class[contains(.,'_29n6N')]]";
	//final static String XPathArticuloMobilOutlet = "//div[@class[contains(.,'product-list-item')] or @id[contains(.,'product-key-id')] or @class='product']";
	//final static String XPathArticuloMobilOutlet = "//li[@class='product-list-item']";
	final static String XPathArticuloDevice = "//li[@class='product']";
	final static String XPathArticuloTabletOutlet = "//*[@class[contains(.,'product-list-item')]]";
	private String getXPathArticulo() {
		if (channel==Channel.desktop) {
			if (from==From.menu) {
				return XPathArticuloDesktop;
			}
			return XPathArticuloDesktopBuscador;
		}
		else {
			if (app==AppEcom.outlet && channel==Channel.tablet) {
				return XPathArticuloTabletOutlet;
			}
			return XPathArticuloDevice;
		}
	}
	
	String XPathHearthIconRelativeArticleDesktop = "//span[@class[contains(.,'icon-favorite')]]";
	String XPathHearthIconRelativeArticleMovil = "//span[@class[contains(.,'product-favorite')]]";
	String getXPathHearthIconRelativeArticle() {
		switch (channel) {
		case desktop:
			return XPathHearthIconRelativeArticleDesktop;
		case mobile:
		default:
			return XPathHearthIconRelativeArticleMovil;
		}
	}
	
	String getXPathArticleHearthIcon(int posArticulo) {
		String xpathArticulo = "(" + XPathArticulo + ")[" + posArticulo + "]";
		return (xpathArticulo + getXPathHearthIconRelativeArticle());
    }
	
	String getXPathArticuloNoDoble() {
		return (
			XPathArticulo + 
			"//self::*[not(@class[contains(.,'layout-2-coumns-A2')])]");
	}
	
	String getXPathAncestorArticulo() {
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
	
	String getXPathLinkArticulo(int numArticulo) {
		return ("(" + XPathArticulo + ")[" + numArticulo + "]//a");
	}  

	public boolean isVisibleArticuloUntil(int numArticulo, int seconds) {
		String xpathArticulo = XPathArticulo + "[" + numArticulo + "]"; 
		return (state(Visible, By.xpath(xpathArticulo)).wait(seconds).check());
	}

	public boolean isClickableArticuloUntil(int numArticulo, int seconds) {
		String xpathArticulo = XPathArticulo + "[" + numArticulo + "]"; 
		return (state(Clickable, By.xpath(xpathArticulo)).wait(seconds).check());
	}
	
	public List<WebElement> getListaArticulos() {
		List<WebElement> listaArticulos = driver.findElements(By.xpath(XPathArticulo));
		return listaArticulos;
	}
	
    public boolean articlesInOrder(FilterOrdenacion typeOrden) throws Exception {
        return ("".compareTo(getAnyArticleNotInOrder(typeOrden))==0);
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
        return (state(Visible, By.xpath(xpathArtGaleria)).wait(seconds).check());
    }
    
    public boolean isFirstArticleOfType(LineaType lineaType) {
	    List<WebElement> listaArticulos = driver.findElements(By.xpath(XPathArticulo));
	    return (
	    	listaArticulos.size() > 0 &&
	    	state(Present, listaArticulos.get(0))
	    		.by(By.xpath("//a[@href[contains(.,'" + lineaType + "')]]")).check());
    }
    
    public void moveToArticleAndGetObject(int posArticulo) {
        moveToElement(By.xpath(getXPathLinkArticulo(posArticulo) + "/.."), driver);
    }
    
    /**
     * Indica si los artículos de la galería realmente están ordenados por precio ascendente o descendente
     */
    public String getAnyArticleNotInOrder(FilterOrdenacion typeOrden) throws Exception {
        switch (typeOrden) {
        case NOordenado:
            return "";
        case PrecioAsc:
        case PrecioDesc:
            return secPrecios.getAnyPrecioNotInOrder(typeOrden, getListaArticulos());
        case TemporadaDesc:
        case TemporadaAsc:
        case BloqueTemporadas_3y4_despues_la_5:
        case BloqueTemporada_5_despues_la_3y4:
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
    public ArrayList<String> getListaReferenciasPrendas() {
        ArrayList<String> listaReferencias = new ArrayList<>();
        List<WebElement> listaArticulos = getArticulos();
        for (WebElement articulo : listaArticulos)
            listaReferencias.add(getRefArticulo(articulo));
        
        return listaReferencias;
    }
    
    public String getReferencia(int posArticle) {
        if (getListaReferenciasPrendas().size()>0) {
            return (getListaReferenciasPrendas().get(posArticle-1));
        }
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
		String href = articulo.findElement(By.xpath(XPathLinkRelativeToArticle)).getAttribute("href");
		return (UtilsTestMango.getReferenciaFromHref(href));
	}

	public boolean waitToHearthIconInState(WebElement hearthIcon, StateFavorito stateIcon, int maxSecondsToWait) {
		for (int i=0; i<maxSecondsToWait; i++) {
			if (getStateHearthIcon(hearthIcon)==stateIcon) {
				return true;
			}
			waitMillis(1000);
		}
		return false;
	}

	//Equivalent to Mobil
	void clickHearthIcon(WebElement hearthIcon) throws Exception {
		moveToElement(hearthIcon, driver);
		state(Clickable, hearthIcon).wait(1).check();
		hearthIcon.click();
	}

	private enum AttributeArticle {Nombre, Referencia, Imagen}
	
    public ArrayList<DataArticleGalery> searchArticleRepeatedInGallery() throws Exception {
        ListDataArticleGalery list = getListArticles(Arrays.asList(
        		AttributeArticle.Nombre, 
        		AttributeArticle.Referencia));
        
        if (list.getArticlesRepeated().size()>0) {
        	//Obtener la imagen de cada artículo es muy costoso así que sólo lo hacemos en este caso
        	list = getListArticles(Arrays.asList(
        			AttributeArticle.Nombre, 
        			AttributeArticle.Referencia,
        			AttributeArticle.Imagen));
        }
        
        return (list.getArticlesRepeated());
    }
    
    public ListDataArticleGalery getListDataArticles() throws Exception {
        return getListArticles(Arrays.asList(
        		AttributeArticle.Nombre, 
        		AttributeArticle.Referencia));
    }
    
    private ListDataArticleGalery getListArticles(List<AttributeArticle> attributes) throws Exception {
        ListDataArticleGalery listReturn = new ListDataArticleGalery();
        for (WebElement articulo : getListaArticulos()) {
            listReturn.add(getDataArticulo(articulo, attributes)); 
        }
        return listReturn;
    }

	private DataArticleGalery getDataArticulo(WebElement articulo, List<AttributeArticle> attributes) 
	throws Exception {
		DataArticleGalery dataArticle = new DataArticleGalery();
		for (AttributeArticle attribute : attributes) {
			switch (attribute) {
			case Nombre:
				dataArticle.setNombre(getNombreArticulo(articulo));
				break;
			case Referencia:
				dataArticle.setReferencia(getRefColorArticulo(articulo));
				break;
			case Imagen:
				dataArticle.setImagen(getImagenArticulo(articulo));
				break;
			}
		}
		return dataArticle;
	}

    public enum StateFavorito {Marcado, Desmarcado} 
    public boolean iconsInCorrectState(List<Integer> posIconosFav, TypeActionFav typeAction) {
        for (int posIcon : posIconosFav) {
            String XPathIcon = getXPathArticleHearthIcon(posIcon);
            WebElement hearthIcon = driver.findElement(By.xpath(XPathIcon));
            switch (typeAction) {
            case Marcar:
                if (getStateHearthIcon(hearthIcon)!=StateFavorito.Marcado) {
                    return false;
                }
                break;
            case Desmarcar:
                if (getStateHearthIcon(hearthIcon)!=StateFavorito.Desmarcado) {
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
        int numArtToWait = 1;
        int secondsToWait = 2;
        boolean isVisible1erArt = isVisibleArticuloUntil(numArtToWait, secondsToWait);
        Thread.sleep(1000);
        return isVisible1erArt;
    }
    
    public void clickIconoUpToGaleryIfVisible(String xpathIconoUpGalery) {
    	if (state(Visible, By.xpath(xpathIconoUpGalery)).wait(1).check()) {
            driver.findElement(By.xpath(xpathIconoUpGalery)).click();
        }
    }

	public String getNombreArticuloWithText(String literal, int secondsWait) {
		WebElement articulo = getArticleThatContainsLitUntil(literal, secondsWait);
		if (articulo!=null) {
			return getNombreArticulo(articulo);
		}
		return "";
		
//        String articulo = "";
//        
//        //Obtenemos el xpath de los artículos eliminando el último carácter (]) pues hemos de insertar condiciones en el XPATH
//        String xpathLitArticulos = XPathArticulo + "//*[" + classProductName + "]";
//        xpathLitArticulos = xpathLitArticulos.substring(0, xpathLitArticulos.length() - 1);
//        
//        xpathLitArticulos = xpathLitArticulos + " and text()[contains(.,'" + name + "')]]"; 
//        
//        if (isElementVisibleUntil(driver, By.xpath(xpathLitArticulos), secondsWait)) {
//            articulo = driver.findElement(By.xpath(xpathLitArticulos)).getText();
//        }
//        
//        return articulo;
    }
	
	public WebElement getArticleThatContainsLitUntil(String literal, int maxSeconds) {
		By byArticleName = By.xpath(
				XPathArticulo + 
				XPathNombreRelativeToArticle + 
				"//self::*[text()[contains(.,'" + literal + "')]]");
		if (state(Present, byArticleName).wait(maxSeconds).check()) {
			return driver.findElement(By.xpath(XPathArticulo));
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
        SecFooter secFooter = new SecFooter(app, driver);
        while (!secFooter.isVisible() && lastPage < pageToScroll) {
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
        datosScroll.articulosDobleTamaño = getTotalNumArticles(numArticlesDoubleXpage);
        datosScroll.articulosTotalesPagina = getTotalNumArticles(numArticlesXpage);
        return (datosScroll);
    }
    
    private int getPageToScroll(int numPage) {
        if (numPage > maxPageToScroll) {
            return maxPageToScroll;
        }
        return numPage;
    }

	public void goToInitPageAndWaitForArticle() throws Exception {
		Object pagePositionObj = ((JavascriptExecutor) driver).executeScript("return window.pageYOffset;");
		if (pagePositionObj instanceof Long) {
			Long pagePosition = (Long)pagePositionObj;
			if (pagePosition != 0) {
				backTo1erArticulo();
			}
		} else {
			Double pagePosition = (Double)pagePositionObj;
			if (pagePosition != 0) {
				backTo1erArticulo();
			}
		}
		int maxSeconds = 2;
		isVisibleArticleUntil(1, maxSeconds);
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
    	for (int i=0; i<numArticlesXpage.size(); i++) {
    		numTotalArticles+=numArticlesXpage.get(i);
    	}
    	return numTotalArticles;
    }
    
    private void goToLastPage() throws Exception {
    	goToPage(99);
    }
    
	private void goToPage(int numPageToGo) throws Exception {
		boolean lastPageReached = false;
		int paginaActual = 1;
		while (!lastPageReached && paginaActual<numPageToGo) {
			By byPagina = By.xpath(getXPathPagina(paginaActual));
			if (state(Visible, byPagina).check()) {
				moveToElement(byPagina, driver);
				((JavascriptExecutor) driver).executeScript("window.scrollBy(0,+50)", "");
				paginaActual+=1;
			} else {
				lastPageReached = true;
			}
		}
		
		waitAndGotoLastArticle();
		waitForPageLoaded(driver);
    }
    
    private void waitAndGotoLastArticle() {
    	List<WebElement> listaArticulos = getListaArticulos();
    	int maxSeconds = 5;
    	waitArticleAndGoTo(listaArticulos.size(), maxSeconds);
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
    	String xpathPagina = getXPathPagina(pagina);
    	return (state(Visible, By.xpath(xpathPagina)).check());
    }

	public void clickArticulo(WebElement articulo) {
		//SeleniumUtils.waitMillis(2000);
		moveToElement(articulo, driver);
		//state(State.Clickable, articulo).wait(10).check();
		//SeleniumUtils.waitMillis(2000);
		click(articulo).waitLoadPage(30).exec();
		//SeleniumUtils.waitMillis(10);
	}

	@SuppressWarnings("static-access")
	public String openArticuloPestanyaAndGo(WebElement article, AppEcom app) 
	throws Exception {
        String galeryWindowHandle = driver.getWindowHandle();
        
        //En el caso de Firefox-Geckodriver el moveToElement (que se acaba realizando mediante el workarround basado en JavaScript) 
        //nos posiciona en la esquina superior izquierda que queda debajo del menú superior... así que tenemos que enviar dicho menú al fondo
        SecMenusDesktop secMenus = SecMenusDesktop.getNew(app, driver);
        secMenus.secMenuSuperior.secLineas.bringMenuBackground();
        
        //WebElement articleName = article.findElement(By.xpath("." + getXPathLinkRelativeToArticle()));
        UtilsMangoTest.openLinkInNewTab(driver, article/*articleName*/);
        
        //Cambiamos el foco de driver a la nueva pestaña que hemos creado y esperamos hasta que está disponible
        String detailWindowHandle = switchToAnotherWindow(driver, galeryWindowHandle);
        
        PageFicha pageFicha = PageFicha.newInstance(Channel.desktop, app, driver);
        pageFicha.isPageUntil(10/*maxSeconds*/);
        
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
		waitForPageLoaded(driver);
		WebElement imagen = getImagenElementArticulo(articulo);
		if (imagen!=null) {
			try {
				return imagen.getAttribute("src");
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
}
