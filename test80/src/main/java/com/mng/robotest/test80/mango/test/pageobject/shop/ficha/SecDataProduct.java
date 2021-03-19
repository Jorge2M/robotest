package com.mng.robotest.test80.mango.test.pageobject.shop.ficha;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.robotest.test80.mango.test.data.Constantes;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.ElementPage;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.PageFicha.TypeFicha;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.tallas.SSecSelTallasFicha;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

/**
 * Sección de la página de Ficha que incluye los datos del artículo: nombre, precio, referencia, colores disponibles y selector de tallas
 * @author jorge.munoz
 *
 */
@SuppressWarnings({"static-access"})
public class SecDataProduct extends PageObjTM {
    
    public enum ProductNav {Prev, Next}
    private final SSecSelTallasFicha secSelTallas;

    private static final String XPathNombreArticuloDesktop = "//h1[@itemprop='name']";
    
    //Existe un Test A/B que hace que el nombre del artículo salga debajo del botón de "Añadir a la bolsa" o en la cabecera, por eso el or.
    private static final String XPathNombreArticuloMobil = "//*[@class[contains(.,'product-info-name')] or @class='headerMobile__text']";
    
    public SecDataProduct(TypeFicha typeFicha, WebDriver driver) {
    	super(driver);
    	this.secSelTallas = SSecSelTallasFicha.make(typeFicha, driver);
    }
    
    public SSecSelTallasFicha getSecSelTallas() {
    	return secSelTallas; 
    }
    
//xpaths asociados a los links prev/next
    private static final String XPathProductNavBlock = "//div[@class='nav-product-container' or @class='nav-product-navigation']";
    private static final String XPathPrevLink = XPathProductNavBlock + "//a[@id='prev' or text()[contains(.,'Anterior')]]";
    private static final String XPathNextLink = XPathProductNavBlock + "//a[@id='next' or text()[contains(.,'Siguiente')]]";
    private String getXPathLinkProductNav(ProductNav productNav) {
        switch (productNav) {
        case Prev:
            return XPathPrevLink;
        case Next:
        default:
            return XPathNextLink;
        }
    }
    
//xpaths asociados a los colores
	private static final String XPathColor = "//div[@class[contains(.,'color-container')]]";
    private static final String ClassColorNoDisp = "@class[contains(.,'--no-stock')] or @class[contains(.,'--cross-out')]";
    public enum ColorType implements ElementPage {
    	Selected("//div[@class[contains(.,'color-container--selected')]]"),
    	Last(XPathColor + "[last()]"),
    	Available(XPathColor + "/img[not(" + ClassColorNoDisp + ")]/.."),
    	Unavailable(XPathColor + "/img[" + ClassColorNoDisp + "]/..");

    	String xpath;
    	By by;
    	private ColorType(String xPath) {
    		xpath = xPath;
    		by = By.xpath(xpath);
    	}
    	
    	@Override
    	public By getBy() {
    		return by;
    	}
    	public String getXPath() {
    		return xpath;
    	}
    	public By getByIcon() {
    		return By.xpath(xpath + "/img");
    	}
    }
    
    private static final String XPathNombreColorSelectedDesktop = ColorType.Selected.getXPath() + "//img[@class='color-image']";
    
//xpaths asociados al tema tallas
    private static final String XPathCapaAvisame = "//*[@id='bocataAvisame']";
    private static final String XPathGuiaDeTallasLink = "//*[@id='productFormSizesGuide']";
    private static final String XPathMsgAvisoTallaDevice = "//p[@class[contains(.,'sizes-notify-error')]]";
    private static final String XPathMsgAvisoTallaDesktop = "//p[@class[contains(.,'sg-inp-sugg--error')]]";  
    private String getXPathMsgAvisoTalla(Channel channel) {
    	if (channel.isDevice()) {
    		return XPathMsgAvisoTallaDevice;
    	}
    	return XPathMsgAvisoTallaDesktop;
    }
    
//xpaths asociados a los precios
    //El class es diferente en ficha old y la new (product-price-sale vs product-sale)
    private static final String XPathItemsPrecioFinalArt = "//span[(@class[contains(.,'product-prices-sale')] or @class[contains(.,'product-sale')]) and not(@class[contains(.,'--cross')])]";
    private static final String XPathItemsPrecioSinDesc = "//span[(@class[contains(.,'product-prices-sale--cross')] or @class[contains(.,'product-sale--cross')])]";

    //xpaths asociados a los colores de la prenda
    private static final String XPathColoresPrendaSinIdentificar = "//div[@class[contains(.,'color-container')]]";
    
    private String getXPathPastillaColorClick(String codigoColor) {
        return ("//div[@class[contains(.,'color-container')] and @id='" + codigoColor + "']/img");
    }    
    
//xpath asociados a los datos básicoos del artículo (nombre y referencia)
    public String getXPathLinReferencia(String referencia, Channel channel) {
    	return "//*[@class[contains(.,'-reference')] and text()[contains(.,'" + referencia + "')]]";
    }
    
    private String getXPathNombreArt(Channel channel) {
        if (channel.isDevice()) {
            return XPathNombreArticuloMobil;
        }
        return XPathNombreArticuloDesktop;
    }
    
    public ArticuloScreen getArticuloObject(Channel channel, AppEcom app) {
        ArticuloScreen articulo = new ArticuloScreen();
        articulo.setReferencia(getReferenciaProducto());
        articulo.setNombre(getTituloArt(channel));
        articulo.setPrecio(getPrecioFinalArticulo());
        articulo.setCodigoColor(getCodeColor(ColorType.Selected));
        articulo.setColorName(getNombreColorSelected(channel));
        articulo.setTalla(secSelTallas.getTallaSelected(app));
        articulo.setNumero(1);
        return articulo;
    }
    
//Funciones referentes a los datos básicos del artículo
    public String getReferenciaProducto() {
        String url = driver.getCurrentUrl();
        Pattern pattern = Pattern.compile("_(.*?).html");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }
    
    public String getTituloArt(Channel channel) {
        String xpathNombreArt = getXPathNombreArt(channel);
        List<WebElement> listArticles = getElementsVisible(driver, By.xpath(xpathNombreArt));
        if (listArticles.size()>0) {
	        WebElement tituloArt = listArticles.get(0);
	        if (tituloArt!=null) {
	        	return (tituloArt.getText());
	        }
        }
        
        return "";
    }
    
//Funciones referentes a los colores
    
    public String getCodeColor(ColorType colorType) {
    	WebElement color = getElementWeb(colorType.getBy(), driver);
        return (color.getAttribute("id"));
    }
    
    public String getNombreColorMobil(ColorType colorType) {
    	WebElement color = getElementWeb(colorType.getBy(), driver);
    	if (color!=null) {
    		return (color.getAttribute("title"));
    	}
    	return Constantes.colorDesconocido;
    }
    
    public String getNombreColorSelected(Channel channel) {
        switch (channel) {
        case desktop:
        	if (state(Present, By.xpath(XPathNombreColorSelectedDesktop)).check()) {
                return (driver.findElement(By.xpath(XPathNombreColorSelectedDesktop)).getAttribute("alt"));
            }
            return Constantes.colorDesconocido;
        case mobile:
        default:
        	return (getNombreColorMobil(ColorType.Selected));
        }
    }

    public boolean checkPotatoe () {
    	return (state(Present, By.xpath(XPathNombreColorSelectedDesktop)).check());
    }

	public void selectColorWaitingForAvailability(String codigoColor) {
		By byColor = By.xpath(getXPathPastillaColorClick(codigoColor));
		int maxSecondsToWaitColor = 3;
		int maxSecondsToWaitLoadPage = 5;
		click(byColor)
			.type(TypeClick.javascript)
			.waitLink(maxSecondsToWaitColor).waitLoadPage(maxSecondsToWaitLoadPage).exec();
	}
    
    public boolean isClickableColor(String codigoColor) {
    	String xpathColor = getXPathPastillaColorClick(codigoColor);
    	return (state(Clickable, By.xpath(xpathColor)).check());
    }
    
//Funciones referentes a los precios
    public String getPrecioFinalArticulo() {
        List<WebElement> listElemsPrecio = driver.findElements(By.xpath(XPathItemsPrecioFinalArt));
        ListIterator<WebElement> itPrecioVenta = listElemsPrecio.listIterator();
        String precioArticulo = "";
        while (itPrecioVenta != null && itPrecioVenta.hasNext())
            precioArticulo += (itPrecioVenta.next()).getAttribute("innerHTML");
        
        return (ImporteScreen.normalizeImportFromScreen(precioArticulo));
    }
    
    /**
     * Extrae (si existe) el precio rebajado de la página de ficha de producto. Si no existe devuelve ""
     */
    public String getPrecioTachadoFromFichaArt() {
    	if (state(Present, By.xpath(XPathItemsPrecioSinDesc)).check()) {
            // Entero
            String precioSinDesc = driver.findElement(By.xpath(XPathItemsPrecioSinDesc + "[1]")).getText();
    
            // Decimales
            if (state(Present, By.xpath(XPathItemsPrecioSinDesc + "[2]")).check()) {
                precioSinDesc += driver.findElement(By.xpath(XPathItemsPrecioSinDesc + "[2]")).getText();
            }
            return (ImporteScreen.normalizeImportFromScreen(precioSinDesc));
        }
        
        return "";
    }    
    
//Funciones referentes a las tallas (en algunas se actúa a modo de Wrapper)
    public boolean isVisibleCapaAvisame() {
    	return (state(Visible, By.xpath(XPathCapaAvisame)).check());
    }
    
    public boolean isVisibleAvisoSeleccionTalla(Channel channel) {
    	String xpathAviso = getXPathMsgAvisoTalla(channel);
    	return (state(Visible, By.xpath(xpathAviso)).check());
    }

	public void selectGuiaDeTallasLink() {
		click(By.xpath(XPathGuiaDeTallasLink)).exec();
	}

    public boolean selectGuiaDeTallasIfVisible() {
    	boolean isVisible = state(Visible, By.xpath(XPathGuiaDeTallasLink)).check();
    	if (isVisible) {
    		selectGuiaDeTallasLink();
    	}
    	return isVisible;
    }

     
    
//Funciones referentes al prev/next
    public boolean isVisiblePrevNextUntil(ProductNav productNav, int maxSeconds) {
        String xpathLink = getXPathLinkProductNav(productNav);
        return (state(Visible, By.xpath(xpathLink)).wait(maxSeconds).check());
    }

	public void selectLinkNavigation(ProductNav productNav) {
		String xpathLink = getXPathLinkProductNav(productNav);
		click(By.xpath(xpathLink)).waitLink(2).exec();
	}

	//zona de colores dentro de la ficha

    public ArrayList<String> getColorsGarment() {
        ArrayList<String> colors = new ArrayList<>();
        for (WebElement element : driver.findElements(By.xpath(XPathColoresPrendaSinIdentificar))) {
            colors.add(element.getAttribute("id"));
        }
        return colors;
    }

	public void selectColor(String codeColor) {
		String path = getXPathPastillaColorClick(codeColor);
		click(By.xpath(path)).exec();
	}
}
