// Funciones genéricas basadas en el uso de WebDriver

package com.mng.robotest.test80.mango.test.generic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestContext;

import com.mng.robotest.test80.mango.test.data.Constantes;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.DataMango;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test80.mango.test.getdata.products.data.Garment;
import com.mng.robotest.test80.mango.test.jdbc.dao.RebajasPaisDAO;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.SeleniumUtils;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.PageFicha;
import com.mng.robotest.test80.mango.test.pageobject.shop.navigations.ArticuloNavigations;


public class UtilsMangoTest {

    /**
     * Devuelve un código postal según el país
     */
    public static String codigoPostal(final Pais pais) {
        String codigoPostal = "08720";
        if (pais != null) {
            if (pais.getCodigo_pais() != null) {
                codigoPostal = pais.getCodpos();
            }
        }

        return codigoPostal;
    }

	public static String getPageSource(WebDriver driver) {
		String idWebKit = "webkit-xml-viewer-source-xml";
		if (PageObjTM.state(Present, By.id(idWebKit), driver).check()) {
			return driver.findElement(By.id(idWebKit)).getAttribute("innerHTML");
		}
		return driver.getPageSource();
	}

    /**
     * Usa diferentes métodos para posicionarse en la página inicial
     */
    public static void goToPaginaInicio(Channel channel, AppEcom app, WebDriver driver) 
    throws Exception {
        // Seleccionamos el logo de MANGO
    	boolean existeLogo = SecCabecera.getNew(channel, app, driver).clickLogoMango();
        if (!existeLogo) {
        	ITestContext ctx = TestMaker.getTestCase().getTestRunParent().getTestNgContext();
            String urlPaginaPostAcceso = (String)ctx.getAttribute(Constantes.attrUrlPagPostAcceso); 
            if (urlPaginaPostAcceso!=null) {
                driver.get(urlPaginaPostAcceso);
            } else {
            	if (PageObjTM.state(Present, By.xpath("//base"), driver).check()) {
                    String urlBase = driver.findElement(By.xpath("//base")).getAttribute("href");
                    driver.get(urlBase);
                }
            }
        }
    }    

	public static ArticuloScreen addArticuloBolsa(Garment selArticulo, AppEcom app, Channel channel, WebDriver driver) {
		ArticuloScreen articulo = ArticuloNavigations.selectArticuloTallaColorByRef(selArticulo, app, channel, driver);
		PageFicha pageFicha = PageFicha.newInstance(channel, app, driver);
		pageFicha.clickAnadirBolsaButtonAndWait(); 
		return articulo;
	}
    
    public static float round(final float d, final int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

    /**
     * Comprueba que dos floats están en un intervalo de %
     */
    public static boolean importesEnIntervalo(final float importe1, final float importe2, final float intervalo) {
        boolean enIntervalo = false;
        if (importe1 >= importe2 * (1 - intervalo / 100.0) && importe1 <= importe2 * (1 + intervalo / 100.0)) {
            enIntervalo = true;
        }
        return enIntervalo;
    }
    
    /**
     * Comprueba que el importe1 <= (importe2 * intervalo * (1/100))
     */
    public static boolean importeEnIntervalo(final float importe1, final float importe2, final float intervalo) {
        boolean enIntervalo = false;
        if (importe1 <= (importe2 * (1 + (intervalo /100))) &&
            importe1 >= (importe2 * (1 - (intervalo /100)))) {
            enIntervalo = true;
        }

        return enIntervalo;
    }


    public static WebElement findDisplayedElement(WebDriver driver, By locator) {
        List<WebElement> listElements = findDisplayedElements(driver, locator);
        return listElements.get(0);
    }
    
    /**
     * Obtenemos sólo los elementos visibles
     */
    public static List<WebElement> findDisplayedElements(WebDriver webdriver, By locator) {
        List<WebElement> elementOptions = webdriver.findElements(locator);
        return (getDisplayedElementsFromList(elementOptions));
    }
    
    public static WebElement findElementPriorizingDisplayed(WebDriver webdriver, By locator) {
    	List<WebElement> elementOptions = webdriver.findElements(locator);
    	if (elementOptions.size() > 0) {
    		List<WebElement> displayedElements = getDisplayedElementsFromList(elementOptions);
	    	if (displayedElements.size() > 0) {
	    		return displayedElements.get(0);
	    	}
	    	return elementOptions.get(0);
    	}
    	
    	return null;
    }
    
    /**
     * Obtenemos sólo los elementos visibles
     */
    public static List<WebElement> findDisplayedElements(WebElement element, final By locator) {
        List<WebElement> elementOptions = element.findElements(locator);
        return (getDisplayedElementsFromList(elementOptions));
    }
    
    private static List<WebElement> getDisplayedElementsFromList(List<WebElement> elementOptions) {
        List<WebElement> displayedOptions = new ArrayList<>();
        try {
            for (WebElement option : elementOptions) {
                if (option.isDisplayed()) {
                    displayedOptions.add(option);
                }
            }
        }
        catch (org.openqa.selenium.StaleElementReferenceException e) {
            //
        }
        
        return displayedOptions;
    }

    /**
     * Funciona que coge los datos de una hashmap y los formatea para mostrarlos en el report HTML
     */
    public static String listaCamposHTML(final HashMap<String, String> datosRegistro) {
        String resultado = "";
        Iterator<Map.Entry<String, String>> it = datosRegistro.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> dupla = it.next();
            resultado = resultado + "<br><b>" + dupla.getKey() + "</b>: " + dupla.getValue();
        }

        return resultado;
    }

    /**
     * Returns a pseudo-random number between min and max, inclusive. The difference between min and max can be at most <code>Integer.MAX_VALUE - 1</code>.
     * @param min Minimum value
     * @param max Maximum value. Must be greater than min.
     * @return Integer between min and max, inclusive.
     * @see java.util.Random#nextInt(int)
     */
    public static int randInt(final int min, final int max) {
        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt(max - min + 1) + min;

        return randomNum;
    }
    
    /**
     * Abrimos un enlacen en una nueva pestaña
     */
    public static void openLinkInNewTab(WebDriver driver, By by) throws Exception {
        WebElement element = driver.findElement(by);
        openLinkInNewTab(driver, element);
    }
    
    public static void openLinkInNewTab(WebDriver driver, WebElement element) throws Exception {
        Actions a = new Actions(driver);
        SeleniumUtils.moveToElement(element, driver);
        Thread.sleep(500);
        SeleniumUtils.moveToElement(element, driver);
        Thread.sleep(500);
        a.moveToElement(element)
        	.keyDown(Keys.CONTROL)
        	.click()
        	.keyUp(Keys.CONTROL).build().perform();
        Thread.sleep(500);
    }
    
    /**
     * Determina si nos encontramos en un entorno de PRO
     */
    public static boolean isEntornoPRO(AppEcom app, WebDriver driver) {
        boolean isEntornoPRO = false;
        List<String> URLsProShop   = Arrays.asList("shop.mango.com", "shoptest.pro.mango.com");
        List<String> URLsProOutlet = Arrays.asList("www.mangooutlet.com", "outlettest.pro.mango.com");
        String xmlURL = TestMaker.getTestCase().getInputParamsSuite().getUrlBase();
        String browserURL = driver.getCurrentUrl();
        Iterator<String> itURLsPRO = null;
        if (app==AppEcom.outlet) {
            itURLsPRO = URLsProOutlet.iterator();
        } else {
            itURLsPRO = URLsProShop.iterator();
        }
        
        while (itURLsPRO.hasNext() && !isEntornoPRO) {
            String URL = itURLsPRO.next();
            if (xmlURL.contains(URL) || browserURL.contains(URL)) {
                isEntornoPRO = true; 
            }
        }
        
        return isEntornoPRO;
    }
    
    public static boolean isEntornoCI(AppEcom app) {
    	if (app==AppEcom.shop) { //De momento sólo tenemos CI para Shop
    		String xmlURL = TestMaker.getTestCase().getInputParamsSuite().getUrlBase();
    		if (xmlURL.contains("shop-ci.")) {
    			return true;
    		}
    	}
    	
    	return false;
    }

    /** Metodo de acceso a cualquier menú de la pantalla principal de Manto.
     * Requiere de por lo menos un criterio de búsqueda, hasta dos opcionales (y en modo AND lógico) y el WebDriver para hacer un waitForPageLoaded 
     */
    public static void accesoMenusManto(WebDriver driver, String criterio1, String criterio2) {
        if (criterio1 != null && !criterio1.isEmpty() && "".compareTo(criterio1)!=0) {
            if (criterio2 != null && !criterio2.isEmpty() && "".compareTo(criterio2)!=0) {
            	By byElem = By.xpath("//a[text()[contains(.,'" + criterio1 + "') and contains(.,'" + criterio2 + "')]]");
            	PageObjTM.click(byElem, driver).exec();
            } else {
            	By byElem = By.xpath("//a[text()[contains(.,'" + criterio1 + "')]]");
            	PageObjTM.click(byElem, driver).exec();
            }
        }
        
        SeleniumUtils.waitForPageLoaded(driver, 5);
    }
    
    /**
     * @return indica si se ha de probar o no una determinada línea
     */
    public static boolean validarLinea(Pais pais, Linea linea, AppEcom app) throws Exception {
        //En caso de tratarse de la línea de rebajas miramos si se ha de probar
        if (linea.getType().compareTo(LineaType.rebajas)==0) {
            return validarLineaRebajas(pais);
        }

        //Indicador de si la línea es la única principal del país
        boolean solo1Linea = pais.getShoponline().isLineaTienda(linea) && pais.getShoponline().getNumLineasTiendas(app) == 1;
        return !solo1Linea;
    }
    
    /**
     * @return indica si se ha de probar o no la línea de rebajas
     */
    public static boolean validarLineaRebajas(Pais pais) throws Exception {
        //Obtenemos el indicador de si se han de validar las rebajas
        //boolean validarPestRebajas = Utilidades.validarRebajasFromBD();
    	//TODO mejor sacar las rebajas de fichero de configuración
    	boolean validarPestRebajas = false;
        if (validarPestRebajas) {
            //Obtenemos el indicador de si las rebajas están activas para ese país concreto
        	RebajasPaisDAO rebajasDAO = new RebajasPaisDAO();
            return rebajasDAO.isRebajasEnabledPais(pais.getCodigo_pais());
        }
        
        return false;
    }
    
    /**
     * Nos retorna un mail adecuado para el proceso de Checkout
     */
    public static String getEmailForCheckout(Pais pais, boolean emailThatExists) {
        String emailCheckout = "";
        if (pais != null && pais.getEmailuser() != null && pais.getEmailuser().trim().compareTo("") != 0) {
            emailCheckout = pais.getEmailuser();
        } else {
            if (emailThatExists) {
                emailCheckout = Constantes.mail_standard;
            } else {
                emailCheckout = DataMango.getEmailNonExistentTimestamp();
            }
        }
        
        return emailCheckout;
    }
}