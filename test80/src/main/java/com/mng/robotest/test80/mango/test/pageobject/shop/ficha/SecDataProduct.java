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
import com.mng.robotest.test80.mango.test.data.Talla;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.ElementPage;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.SeleniumUtils;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.PageFicha.TypeFicha;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

/**
 * Sección de la página de Ficha que incluye los datos del artículo: nombre, precio, referencia, colores disponibles y selector de tallas
 * @author jorge.munoz
 *
 */
@SuppressWarnings({"static-access"})
public class SecDataProduct extends SeleniumUtils {
    
    public enum ProductNav {Prev, Next}
    public static SSecSelTallasFichaOld secSelTallasOld;
    public static SSecSelTallasFichaNew secSelTallasNew;

    private static final String XPathNombreArticuloDesktop = "//h1[@itemprop='name']";
    
    //Existe un Test A/B que hace que el nombre del artículo salga debajo del botón de "Añadir a la bolsa" o en la cabecera, por eso el or.
    private static final String XPathNombreArticuloMobil = "//*[@class='product-info-name' or @class='headerMobile__text']";
    
//xpaths asociados a los links prev/next
    private static final String XPathProductNavBlock = "//div[@class='nav-product-container' or @class='nav-product-navigation']";
    private static final String XPathPrevLink = XPathProductNavBlock + "//a[@id='prev' or text()[contains(.,'Anterior')]]";
    private static final String XPathNextLink = XPathProductNavBlock + "//a[@id='next' or text()[contains(.,'Siguiente')]]";
    private static final String getXPathLinkProductNav(ProductNav productNav) {
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
    private static final String XPathMsgAvisoTalla = "//p[@class[contains(.,'sugg--error')]]";
    private static final String XPathGuiaDeTallasLink = "//*[@id='productFormSizesGuide']";
    
//xpaths asociados a los precios
    //El class es diferente en ficha old y la new (product-price-sale vs product-sale)
    private static final String XPathItemsPrecioFinalArt = "//span[(@class[contains(.,'product-prices-sale')] or @class[contains(.,'product-sale')]) and not(@class[contains(.,'--cross')])]";
    private static final String XPathItemsPrecioSinDesc = "//span[(@class[contains(.,'product-prices-sale--cross')] or @class[contains(.,'product-sale--cross')])]";

    //xpaths asociados a los colores de la prenda
    private static final String XPathColoresPrendaSinIdentificar = "//div[@class='color-container']";
    
    private static String getXPathPastillaColorClick(String codigoColor) {
        return ("//div[@class[contains(.,'color-container')] and @id='" + codigoColor + "']/img");
    }    
    
//xpath asociados a los datos básicoos del artículo (nombre y referencia)
    public static String getXPathLinReferencia(String referencia, Channel channel) {
        switch (channel) {
        case desktop:
            return "//span[@class[contains(.,'-reference')] and text()[contains(.,'" + referencia + "')]]";
        case mobile:
        default:
            return "//p[@class[contains(.,'-reference')] and text()[contains(.,'" + referencia + "')]]";
        }
    }
    
    private static String getXPathNombreArt(Channel channel) {
        if (channel==Channel.mobile) {
            return XPathNombreArticuloMobil;
        }
        return XPathNombreArticuloDesktop;
    }
    
    public static ArticuloScreen getArticuloObject(Channel channel, AppEcom app, TypeFicha typeFicha, WebDriver driver) {
        ArticuloScreen articulo = new ArticuloScreen();
        articulo.setReferencia(getReferenciaProducto(driver));
        articulo.setNombre(getTituloArt(channel, driver));
        articulo.setPrecio(getPrecioFinalArticulo(driver));
        articulo.setCodigoColor(getCodeColor(ColorType.Selected, driver));
        articulo.setColorName(getNombreColorSelected(channel, driver));
        articulo.setTalla(getTallaSelected(typeFicha, app, driver));
        articulo.setNumero(1);
        return articulo;
    }
    
//Funciones referentes a los datos básicos del artículo
    public static String getReferenciaProducto(WebDriver driver) {
        String url = driver.getCurrentUrl();
        Pattern pattern = Pattern.compile("_(.*?).html");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }
    
    public static String getTituloArt(Channel channel, WebDriver driver) {
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
    
    public static String getCodeColor(ColorType colorType, WebDriver driver) {
    	WebElement color = getElementWeb(colorType.getBy(), driver);
        return (color.getAttribute("id"));
    }
    
    public static String getNombreColorMobil(ColorType colorType, WebDriver driver) {
    	WebElement color = getElementWeb(colorType.getBy(), driver);
    	if (color!=null) {
    		return (color.getAttribute("title"));
    	}
    	return Constantes.colorDesconocido;
    }
    
    public static String getNombreColorSelected(Channel channel, WebDriver driver) {
        switch (channel) {
        case desktop:
        	if (state(Present, By.xpath(XPathNombreColorSelectedDesktop), driver).check()) {
                return (driver.findElement(By.xpath(XPathNombreColorSelectedDesktop)).getAttribute("alt"));
            }
            return Constantes.colorDesconocido;
        case mobile:
        default:
        	return (getNombreColorMobil(ColorType.Selected, driver));
        }
    }

    public static boolean checkPotatoe (WebDriver driver) {
    	return (state(Present, By.xpath(XPathNombreColorSelectedDesktop), driver).check());
    }

	public static void selectColorWaitingForAvailability(String codigoColor, WebDriver driver) {
		By byColor = By.xpath(getXPathPastillaColorClick(codigoColor));
		int maxSecondsToWaitColor = 3;
		int maxSecondsToWaitLoadPage = 5;
		click(byColor, driver)
			.type(TypeClick.javascript)
			.waitLink(maxSecondsToWaitColor).waitLoadPage(maxSecondsToWaitLoadPage).exec();
	}
    
    /**
     * @return si la pastilla de color es o no visible
     */
    public static boolean isClickableColor(String codigoColor, WebDriver driver) {
    	String xpathColor = getXPathPastillaColorClick(codigoColor);
    	return (state(Clickable, By.xpath(xpathColor), driver).check());
    }
    
//Funciones referentes a los precios
    public static String getPrecioFinalArticulo(WebDriver driver) {
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
    public static String getPrecioTachadoFromFichaArt(WebDriver driver) {
    	if (state(Present, By.xpath(XPathItemsPrecioSinDesc), driver).check()) {
            // Entero
            String precioSinDesc = driver.findElement(By.xpath(XPathItemsPrecioSinDesc + "[1]")).getText();
    
            // Decimales
            if (state(Present, By.xpath(XPathItemsPrecioSinDesc + "[2]"), driver).check()) {
                precioSinDesc += driver.findElement(By.xpath(XPathItemsPrecioSinDesc + "[2]")).getText();
            }
            return (ImporteScreen.normalizeImportFromScreen(precioSinDesc));
        }
        
        return "";
    }    
    
//Funciones referentes a las tallas (en algunas se actúa a modo de Wrapper)
    public static boolean isVisibleCapaAvisame(WebDriver driver) {
    	return (state(Visible, By.xpath(XPathCapaAvisame), driver).check());
    }
    
    public static boolean isVisibleAvisoSeleccionTalla(WebDriver driver) {
    	return (state(Visible, By.xpath(XPathMsgAvisoTalla), driver).check());
    }

	public static void selectGuiaDeTallasLink(WebDriver driver) {
		click(By.xpath(XPathGuiaDeTallasLink), driver).exec();
	}

    public static boolean selectGuiaDeTallasIfVisible(WebDriver driver) {
    	boolean isVisible = state(Visible, By.xpath(XPathGuiaDeTallasLink), driver).check();
    	if (isVisible) {
    		selectGuiaDeTallasLink(driver);
    	}
    	return isVisible;
    }

	public static Talla getTallaSelected(TypeFicha typeFicha, AppEcom app, WebDriver driver) {
		if (typeFicha==TypeFicha.Old) {
			return Talla.from(secSelTallasOld.getTallaAlfSelected(app, driver));
		}
		return Talla.from(secSelTallasNew.getTallaAlfSelected(driver));
	}
	
	/**
	 * @return talla eliminando el literal del tipo " [Almacen: 001]
	 */
	public static String removeAlmacenFromTalla(String talla) {
	    Pattern tallaWithAlmacen = Pattern.compile("(.*)( \\[Almacen: [0-9]{3}\\])");
	    Matcher matcher = tallaWithAlmacen.matcher(talla);
	    if (matcher.find()) {
	    	return matcher.group(1);
	    }
	    return talla;
	}
    
//    public static String getTallaNumSelected(TypeFicha typeFicha, AppEcom app, WebDriver driver) {
//        if (typeFicha==TypeFicha.Old) {
//            return secSelTallasOld.getTallaNumSelected(driver);
//        }
//        return secSelTallasNew.getTallaNumSelected(app, driver);
//    }    
    
    public static String getTallaAlf(TypeFicha typeFicha, int posicion, WebDriver driver) {
        if (typeFicha==TypeFicha.Old) {
            return secSelTallasOld.getTallaAlf(posicion, driver);
        }
        return secSelTallasNew.getTallaAlf(posicion, driver);
    }    
    
    public static String getTallaCodNum(TypeFicha typeFicha, int posicion, WebDriver driver) {
        if (typeFicha==TypeFicha.Old) {
            return secSelTallasOld.getTallaCodNum(posicion, driver);
        }
        return secSelTallasNew.getTallaCodNum(posicion, driver);
    }    
    
    public static boolean isTallaUnica(TypeFicha typeFicha, WebDriver driver) {
        if (typeFicha==TypeFicha.Old) {
            return secSelTallasOld.isTallaUnica(driver);
        }
        return secSelTallasNew.isTallaUnica(driver);
    }    
    
	public static void selectTallaByValue(Talla talla, TypeFicha typeFicha, WebDriver driver) {
		if (typeFicha==TypeFicha.Old) {
			secSelTallasOld.selectTallaByValue(talla.getTallaNum(), driver);
		} else {
			secSelTallasNew.selectTallaByValue(talla.getTallaNum(), driver);
		}
	}
    
    public static void selectTallaByIndex(int posicion, TypeFicha typeFicha, WebDriver driver) {
        if (typeFicha==TypeFicha.Old) {
            secSelTallasOld.selectTallaByIndex(posicion, driver);
        } else {
            secSelTallasNew.selectTallaByIndex(posicion, driver);
        }
    }        
    
    public static void selectFirstTallaAvailable(TypeFicha typeFicha, WebDriver driver) {
        if (typeFicha==TypeFicha.Old) {
            secSelTallasOld.selectFirstTallaAvailable(driver);
        } else {
            secSelTallasNew.selectFirstTallaAvailable(driver);
        }
    }    
    
    public static boolean isTallaAvailable(String talla, TypeFicha typeFicha, WebDriver driver) {
        if (typeFicha==TypeFicha.Old) {
            return (secSelTallasOld.isTallaAvailable(talla, driver));
        }
        return (secSelTallasNew.isTallaAvailable(talla, driver));
    }
    
    public static int getNumOptionsTallasNoDisponibles(TypeFicha typeFicha, WebDriver driver) {
        if (typeFicha==TypeFicha.Old) {
            return (secSelTallasOld.getNumOptionsTallasNoDisponibles(driver));
        }
        return (secSelTallasNew.getNumOptionsTallasNoDisponibles(driver));
    }
    
    public static int getNumOptionsTallas(TypeFicha typeFicha, WebDriver driver) {
        if (typeFicha==TypeFicha.Old) {
            return (secSelTallasOld.getNumOptionsTallas(driver));
        }
        return (secSelTallasNew.getNumOptionsTallas(driver));
    }    
    
//Funciones referentes al prev/next
    public static boolean isVisiblePrevNextUntil(ProductNav productNav, int maxSeconds, WebDriver driver) {
        String xpathLink = getXPathLinkProductNav(productNav);
        return (state(Visible, By.xpath(xpathLink), driver)
        		.wait(maxSeconds).check());
    }

	public static void selectLinkNavigation(ProductNav productNav, WebDriver driver) {
		String xpathLink = getXPathLinkProductNav(productNav);
		click(By.xpath(xpathLink), driver).waitLink(2).exec();
	}

	//zona de colores dentro de la ficha

    public static ArrayList<String> getColorsGarment(WebDriver driver) {
        ArrayList<String> colors = new ArrayList<>();
        for (WebElement element : driver.findElements(By.xpath(XPathColoresPrendaSinIdentificar))) {
            colors.add(element.getAttribute("id"));
        }
        return colors;
    }

	public static void selectColor(String codeColor, WebDriver driver) {
		String path = getXPathPastillaColorClick(codeColor);
		click(By.xpath(path), driver).exec();
	}
}
