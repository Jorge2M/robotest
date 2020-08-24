package com.mng.robotest.test80.mango.test.pageobject.shop;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick;
import com.mng.robotest.test80.mango.test.data.Constantes;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.IdiomaPais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabeceraOutletMobil;
import com.mng.robotest.test80.mango.test.pageobject.shop.modales.ModalLoyaltyAfterAccess;
import com.mng.robotest.test80.mango.test.pageobject.shop.modales.ModalNewsLetterAfterAccess;
import com.mng.robotest.test80.mango.test.pageobject.utils.LocalStorage;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.AccesoNavigations;
//import com.mng.robotest.test80.mango.test.stpv.navigations.shop.AccesoNavigations;
import com.mng.robotest.test80.mango.test.utils.testab.TestABactive;

/**
 * Clase que define la automatización de las diferentes funcionalidades de la página de "GALERÍA DE PRODUCTOS"
 * @author jorge.munoz
 */
public class PagePrehome {

	enum ButtonEnter {Enter, Continuar};
	
    static String XPathSelectPaises = "//select[@id='countrySelect']";
    
    //xpath correspondiente al div con el país seleccionado (cuyo click que permite desplegar la lista de países)
    static String XPathDivPaisSeleccionado = "//div[@id='countrySelect_chosen']";
    
    static String XPathIconSalePaisSeleccionado = XPathDivPaisSeleccionado + "//span[@class[contains(.,'salesIcon')]]";
    static String XPathDivProvincias = "//div[@class[contains(.,'Cnt on')]]/div[@class[contains(.,'provinceSelect')]]";
    static String XPathInputPais = "//div[@class[contains(.,'chosen-search')]]/input";
    
    public static String getXPath_optionPaisFromName(String nombrePais) {
        return (XPathSelectPaises + "//option[@data-alt-spellings[contains(.,'" + nombrePais + "')]]");
    }
    
    public static String getXPath_optionPaisFromCodigo(String codigoPais) {
        return (XPathSelectPaises + "//option[@value[contains(.,'" + codigoPais + "')]]");
    }    
    
    //TODO eliminar cuando haya desaparecido definitivamente la lista de provincias de la Shop/Outlet
    public static String getXPathListaProvincias(String codigoPais) {
    	return ("//div[@id='province_" + codigoPais + "_chosen']");
    }
    
    public static String getXPathButtonIdioma(String codigoPais, String nombreIdioma) {
    	return "//div[@id='lang_" + codigoPais + "']//a[text()[contains(.,'" + nombreIdioma + "')]]";
    }
    
    public static String getXPathButtonForEnter(ButtonEnter button, String codigoPais) {
    	switch (button) {
    	case Enter:
    		return ("//div[@id='lang_" + codigoPais + "']/div[@class[contains(.,'phFormEnter')]]");
    	case Continuar:
    	default:
    		return("//div[@id='lang_" + codigoPais + "']/div[@class[contains(.,'modalFormEnter')]]");
    	}
    }

	public static boolean isPage(WebDriver driver) {
		return (state(Present, By.xpath(XPathDivPaisSeleccionado), driver).check());
	}

	public static boolean isNotPageUntil(int maxSeconds, WebDriver driver) {
		return (state(Invisible, By.xpath(XPathDivPaisSeleccionado), driver)
				.wait(maxSeconds).check());
	}

    /**
     * @return el código de país que existe en pantalla en base a su nombre
     */
    public static String getCodigoPais(WebDriver driver, String nombrePais) {
        String xpathOptionPais = getXPath_optionPaisFromName(nombrePais);
        String codigoPais = driver.findElement(By.xpath(xpathOptionPais)).getAttribute("value");
        return codigoPais;
    }

	public static boolean isPaisSelectedWithMarcaCompra(WebDriver driver) {
		return (state(Visible, By.xpath(XPathIconSalePaisSeleccionado), driver).check());
	}

	public static boolean isPaisSelectedDesktop(WebDriver driver, String nombrePais) {
		return (driver.findElement(By.xpath(XPathDivPaisSeleccionado)).getText().contains(nombrePais));
	}

	public static boolean existeDesplProvincias(WebDriver driver) {
		return (
			state(Present, By.xpath(XPathDivProvincias), driver).check() &&
			driver.findElement(By.xpath(XPathDivProvincias)).isDisplayed());
	}

    public static void desplieguaListaPaises(WebDriver driver) {
    	moveToElement(By.xpath(XPathDivPaisSeleccionado), driver);
        driver.findElement(By.xpath(XPathDivPaisSeleccionado + "/a")).click();
    }
    
    /**
     * Despliega la lista de provincias y selecciona la provincia "Barcelona"
     */
    //TODO eliminar cuando haya desaparecido definitivamente la lista de provincias de la Shop/Outlet
    public static void seleccionaProvincia(WebDriver driver, String nombrePais, Channel channel) {
        if (channel==Channel.desktop) {
            //Desplegamos la lista de provincias
            String codigoPais = getCodigoPais(driver, nombrePais);
            String xpathListaProv = getXPathListaProvincias(codigoPais);
            moveToElement(By.xpath(xpathListaProv), driver);
            driver.findElement(By.xpath(xpathListaProv)).click();
            
            //Introducimos la provincia Barcelona
            sendKeysWithRetry("Barcelona", By.xpath(xpathListaProv + "//div[@class[contains(.,'chosen-search')]]/input"), 3, driver);
            
            //Seleccionamos la provincia encontrada
            driver.findElement(By.xpath(xpathListaProv + "//div[@class='chosen-drop']/ul/li")).click();
        }
        else
            //Seleccionamos la provincia 8 (Barcelona)
            driver.findElement(By.xpath("//select[@id[contains(.,'province')]]/option[@value='" + "8" + "']")).click();
    }    

	public static void seleccionaIdioma(WebDriver driver, String nombrePais, String nombreIdioma) {
		String codigoPais = getCodigoPais(driver, nombrePais);
		String xpathButtonIdioma = getXPathButtonIdioma(codigoPais, nombreIdioma);
		click(By.xpath(xpathButtonIdioma), driver).type(TypeClick.javascript).exec();
	}

    /**
     * Introducimos el nombre del país en el campo de input de "Busca tu país..." y lo seleccionamos
     */
    public static void inputPaisAndSelect(WebDriver driver, String nombrePais, Channel channel) throws Exception {
        String codigoPais = getCodigoPais(driver, nombrePais);
        if (channel!=Channel.mobile) {
            new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class[contains(.,'chosen-with-drop')]]")));
            driver.findElement(By.xpath(XPathInputPais)).sendKeys(nombrePais);
            
            // Seleccionamos el país encontrado
            driver.findElement(By.xpath("//div[@class='chosen-drop']/ul/li")).click();        
        } else {
            //En el caso de mobile no ejecutamos los despliegues porque es muy complejo tratar con los desplegables nativos del dispositivo
            //Seleccionamos el país a partir de su código de país
            driver.findElement(By.xpath(XPathSelectPaises + "/option[@value='" + codigoPais + "']")).click();
        }
    }
    
    /**
     * Selecciona el botón para acceder a la shop (soporta desktop/móvil y prehome/modal)
     */
    public static void selectButtonForEnter(WebDriver driver, String codigoPais) {
        try {
        	boolean buttonEnterSelected = clickButtonForEnterIfExists(ButtonEnter.Enter, codigoPais, driver); 
            if (!buttonEnterSelected) {
            	clickButtonForEnterIfExists(ButtonEnter.Continuar, codigoPais, driver);
            }
        } 
        catch (Exception e) {
        	Log4jTM.getLogger().warn("Exception clicking button for Enter. But perhaps the click have work fine", e);
        }
    }

	public static boolean clickButtonForEnterIfExists(ButtonEnter buttonEnter, String codigoPais, WebDriver driver) {
		String xpathButton = getXPathButtonForEnter(buttonEnter, codigoPais);
		if (state(Present, By.xpath(xpathButton), driver).check() && 
			driver.findElement(By.xpath(xpathButton)).isDisplayed()) {
			moveToElement(By.xpath(xpathButton), driver);
			click(By.xpath(xpathButton + "/a"), driver).type(TypeClick.javascript).exec();
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public static void closeModalNewsLetterIfExists(WebDriver driver) {
		//Capturamos la variable JavaScript "sessionObjectJson"
		JavascriptExecutor js = (JavascriptExecutor) driver;
		Object result = js.executeScript("return sessionObjectsJson");
		if (result!=null) {
			Map<String,Object> resultMap = (Map<String,Object>)result;

			//Si figura para lanzar la llamada JSON de NewsLetter
			if (resultMap.entrySet().toString().contains("modalRegistroNewsletter")) {
				String xpathDivModal = "//div[@id='modalNewsletter']";
				if (state(Visible, By.xpath(xpathDivModal), driver).wait(5).check()) {
					//Clickamos al aspa para cerrar el modal
					driver.findElement(By.xpath(xpathDivModal + "//div[@id='modalClose']")).click();
				}
			}
		}
	}

    public static void setInitialModalsOff(WebDriver driver) {
//        Cookie ck = new Cookie("modalRegistroNewsletter", "0");
//        driver.manage().addCookie(ck);
        LocalStorage localStorage = new LocalStorage(driver);
        localStorage.setItemInLocalStorage("modalRegistroNewsletter", "0");
        localStorage.setItemInLocalStorage("modalAdhesionLoyalty", "true");
    }

    /**
     * Ejecuta una acceso a la shop vía la páinga de prehome
     */
    public static void accesoShopViaPrehome(DataCtxShop dCtxSh, WebDriver driver) throws Exception {
    	identJCASifExists(/*dCtxSh.urlAcceso,*/ driver);
        TestABactive.currentTestABsToActivate(dCtxSh.channel, dCtxSh.appE, driver);
        PagePrehome.selecPaisIdiomaYAccede(dCtxSh, driver);
        ModalLoyaltyAfterAccess.closeModalIfVisible(driver);
        //ModalNewsLetterAfterAccess.closeModalIfVisible(driver);
        if (dCtxSh.channel==Channel.mobile) {
        	SecCabeceraOutletMobil secCabecera = (SecCabeceraOutletMobil)SecCabeceraOutletMobil.getNew(Channel.mobile, dCtxSh.appE, driver);
        	secCabecera.closeSmartBannerIfExistsMobil();
        }
    }
    
    /**
     * Ejecuta el flujo correspondiente a la selección de un país + la posterior selección de provincia/idioma/enter (sirve para prehome y modal) 
     */
    public static void selecPaisIdiomaYAccede(DataCtxShop dCtxSh, WebDriver driver) //No modificar
    throws Exception {
        selecionPais(dCtxSh, driver);
        selecionProvIdiomAndEnter(dCtxSh.pais, dCtxSh.idioma, dCtxSh.channel, driver);
    }
    
    public static void identJCASifExists(/*String urlPreHome, */WebDriver driver) {
    	//Temporal para test Canary!!!
    	//AccesoNavigations.goToInitURL(urlPreHome + "?canary=true", driver);
    	AccesoNavigations.goToInitURL(/*urlPreHome,*/ driver);
        waitForPageLoaded(driver);
        if (PageJCAS.thisPageIsShown(driver)) {
            PageJCAS.identication(driver, Constantes.userManto, Constantes.passwordManto);
        }
    }    
    
    /**
     * Ejecuta el flujo para selecionar el país especificado
     */
    public static void selecionPais(DataCtxShop dCtxSh, WebDriver driver) 
    throws Exception {
        new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(By.xpath(XPathSelectPaises)));
        
        //Damos de alta la cookie de newsLetter porque no podemos gestionar correctamente el cierre 
        //del modal en la página de portada (es aleatorio y aparece en un intervalo de 0 a 5 segundos)
        setInitialModalsOff(driver);
        if (dCtxSh.channel==Channel.mobile || !isPaisSelectedDesktop(driver, dCtxSh.pais.getNombre_pais())) {
            if (dCtxSh.channel!=Channel.mobile) {
                //Nos posicionamos y desplegamos la lista de países (en el caso de mobile no desplegamos 
            	//porque entonces es complejo manejar el desplegable que aparece en este tipo de dispositivos)
                desplieguaListaPaises(driver);
            }
            
            inputPaisAndSelect(driver, dCtxSh.pais.getNombre_pais(), dCtxSh.channel);
        }
    }
    
    /**
     * Ejecuta el flujo (posterior a la selección del país) de selección de país/idioma + entrar
     */
    public static void selecionProvIdiomAndEnter(Pais pais, final IdiomaPais idioma, Channel channel, WebDriver driver) 
    throws Exception { 
        if (existeDesplProvincias(driver)) {
            //Selecciona la provincia "Barcelona"
            seleccionaProvincia(driver, pais.getNombre_pais(), channel);
        }
        
        if (pais.getListIdiomas().size() > 1) {
            //Si el país tiene más de 1 idioma seleccionar el que nos llega como parámetro
            seleccionaIdioma(driver, pais.getNombre_pais(), idioma.getCodigo().getLiteral());
        } else {
            String codigoPais = getCodigoPais(driver, pais.getNombre_pais());
            selectButtonForEnter(driver, codigoPais);
        }
    
        //Esperamos a que desaparezca la página de Prehome
        PagePrehome.isNotPageUntil(30, driver);
        waitForPageLoaded(driver);
    }
}
