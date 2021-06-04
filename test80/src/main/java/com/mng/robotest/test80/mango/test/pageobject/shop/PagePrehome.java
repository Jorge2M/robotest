package com.mng.robotest.test80.mango.test.pageobject.shop;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.mng.robotest.test80.mango.test.data.Constantes;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.test80.mango.test.pageobject.shop.acceptcookies.ModalSetCookies.SectionConfCookies;
import com.mng.robotest.test80.mango.test.pageobject.shop.acceptcookies.SectionCookies;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabeceraOutlet_Mobil;
import com.mng.robotest.test80.mango.test.pageobject.shop.modales.ModalLoyaltyAfterAccess;
import com.mng.robotest.test80.mango.test.pageobject.utils.LocalStorage;
import com.mng.robotest.test80.mango.test.stpv.shop.acceptcookies.ModalSetCookiesStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.acceptcookies.SectionCookiesStpV;
import com.mng.robotest.test80.mango.test.utils.testab.TestABactive;

/**
 * Clase que define la automatización de las diferentes funcionalidades de la página de "GALERÍA DE PRODUCTOS"
 * @author jorge.munoz
 */
public class PagePrehome extends PageObjTM {

	enum ButtonEnter {Enter, Continuar};
	
	private final DataCtxShop dCtxSh;
	
    private final static String XPathSelectPaises = "//select[@id='countrySelect']";
    
    //xpath correspondiente al div con el país seleccionado (cuyo click que permite desplegar la lista de países)
    private final static String XPathDivPaisSeleccionado = "//div[@id='countrySelect_chosen']";
    
    private final static String XPathIconSalePaisSeleccionado = XPathDivPaisSeleccionado + "//span[@class[contains(.,'salesIcon')]]";
    private final static String XPathInputPais = "//div[@class[contains(.,'chosen-search')]]/input";
    
    public PagePrehome(DataCtxShop dCtxSh, WebDriver driver) {
    	super(driver);
    	this.dCtxSh = dCtxSh;
    }
    
    private String getXPath_optionPaisFromName(String nombrePais) {
        return (XPathSelectPaises + "//option[@data-alt-spellings[contains(.,'" + nombrePais + "')]]");
    }   
    
    private String getXPathButtonIdioma(String codigoPais, String nombreIdioma) {
    	return "//div[@id='lang_" + codigoPais + "']//a[text()[contains(.,'" + nombreIdioma + "')]]";
    }
    
    private String getXPathButtonForEnter(ButtonEnter button, String codigoPais) {
    	switch (button) {
    	case Enter:
    		return ("//div[@id='lang_" + codigoPais + "']/div[@class[contains(.,'phFormEnter')]]");
    	case Continuar:
    	default:
    		return("//div[@id='lang_" + codigoPais + "']/div[@class[contains(.,'modalFormEnter')]]");
    	}
    }

	public boolean isPage() {
		return isPage(driver);
	}
	public static boolean isPage(WebDriver driver) {
		return (PageObjTM.state(Present, By.xpath(XPathDivPaisSeleccionado), driver).check());
	}

	public boolean isNotPageUntil(int maxSeconds) {
		return (state(Invisible, By.xpath(XPathDivPaisSeleccionado)).wait(maxSeconds).check());
	}

    /**
     * @return el código de país que existe en pantalla en base a su nombre
     */
    public String getCodigoPais(String nombrePais) {
        String xpathOptionPais = getXPath_optionPaisFromName(nombrePais);
        String codigoPais = driver.findElement(By.xpath(xpathOptionPais)).getAttribute("value");
        return codigoPais;
    }

	public boolean isPaisSelectedWithMarcaCompra() {
		return (state(Visible, By.xpath(XPathIconSalePaisSeleccionado), driver).check());
	}

	public boolean isPaisSelectedDesktop() {
		String nombrePais = dCtxSh.pais.getNombre_pais();
		return (driver.findElement(By.xpath(XPathDivPaisSeleccionado)).getText().contains(nombrePais));
	}

    public void desplieguaListaPaises() {
    	moveToElement(By.xpath(XPathDivPaisSeleccionado), driver);
        driver.findElement(By.xpath(XPathDivPaisSeleccionado + "/a")).click();
    }

	public void seleccionaIdioma(String nombrePais, String nombreIdioma) {
		String codigoPais = getCodigoPais(nombrePais);
		String xpathButtonIdioma = getXPathButtonIdioma(codigoPais, nombreIdioma);
		click(By.xpath(xpathButtonIdioma), driver).type(TypeClick.javascript).exec();
	}

    /**
     * Introducimos el nombre del país en el campo de input de "Busca tu país..." y lo seleccionamos
     */
    public void inputPaisAndSelect(String nombrePais) throws Exception {
        String codigoPais = getCodigoPais(nombrePais);
        if (!dCtxSh.channel.isDevice()) {
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
    public void selectButtonForEnter(String codigoPais) {
        try {
        	boolean buttonEnterSelected = clickButtonForEnterIfExists(ButtonEnter.Enter, codigoPais); 
            if (!buttonEnterSelected) {
            	clickButtonForEnterIfExists(ButtonEnter.Continuar, codigoPais);
            }
        } 
        catch (Exception e) {
        	Log4jTM.getLogger().warn("Exception clicking button for Enter. But perhaps the click have work fine", e);
        }
    }

	public boolean clickButtonForEnterIfExists(ButtonEnter buttonEnter, String codigoPais) {
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
	public void closeModalNewsLetterIfExists() {
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

    public void setInitialModalsOff() {
        LocalStorage localStorage = new LocalStorage(driver);
        localStorage.setItemInLocalStorage("modalRegistroNewsletter", "0");
        localStorage.setItemInLocalStorage("modalAdhesionLoyalty", "true");
    }

    public void accesoShopViaPrehome(boolean acceptCookies) throws Exception {
    	previousAccessShopSteps(acceptCookies);
        selecPaisIdiomaYAccede();
        ModalLoyaltyAfterAccess.closeModalIfVisible(driver);
        //ModalNewsLetterAfterAccess.closeModalIfVisible(driver);
        if (dCtxSh.channel.isDevice()) {
        	SecCabeceraOutlet_Mobil secCabecera = (SecCabeceraOutlet_Mobil)SecCabeceraOutlet_Mobil.getNew(Channel.mobile, dCtxSh.appE, driver);
        	secCabecera.closeSmartBannerIfExistsMobil();
        }
        
    }
    
    public void previousAccessShopSteps(boolean acceptCookies) throws Exception {
    	reloadIfServiceUnavailable();
    	identJCASifExists();
        TestABactive.currentTestABsToActivate(dCtxSh.channel, dCtxSh.appE, driver);
        manageCookies(acceptCookies);
    }
    
    private void manageCookies(boolean acceptCookies) {
    	SectionCookies sectionCookies = new SectionCookies(driver);
    	SectionCookiesStpV sectionCookiesStpV = new SectionCookiesStpV(driver);
    	if (acceptCookies) {
		    if (sectionCookies.isVisible(2)) {
		    	sectionCookiesStpV.accept();
		    	//changeCookie_OptanonConsent(driver);
		    	//setupCookies(driver);
		    }
    	} else {
        	ModalSetCookiesStpV modalSetCookiesStpV = sectionCookiesStpV.setCookies();
        	modalSetCookiesStpV.saveConfiguration();
    	}
    }
    
    private void reloadIfServiceUnavailable() {
    	if (driver.getPageSource().contains("Service Unavailable")) {
    		driver.navigate().refresh();
    	}
    }
    
    private void changeCookie_OptanonConsent() {
    	SectionCookiesStpV sectionCookiesStpV = new SectionCookiesStpV(driver);
    	sectionCookiesStpV.changeCookie_OptanonConsent();
    }
    
    private void setupCookies() {
    	SectionCookiesStpV sectionCookiesStpV = new SectionCookiesStpV(driver);
    	ModalSetCookiesStpV modalSetCookiesStpV = 
    		sectionCookiesStpV.setCookies();
    	modalSetCookiesStpV.select(SectionConfCookies.Cookies_dirigidas);
        ((JavascriptExecutor) driver).executeScript("document.getElementsByClassName('ot-tgl')[0].style.display='block'");    
    	modalSetCookiesStpV.disableSwitchCookies();
    	
    	modalSetCookiesStpV.select(SectionConfCookies.Cookies_de_redes_sociales);
    	modalSetCookiesStpV.disableSwitchCookies();
    	modalSetCookiesStpV.saveConfiguration();
    }
    
    public void selecPaisIdiomaYAccede() throws Exception {
        selecionPais();
        selecionIdiomaAndEnter();
    }
    
    public void identJCASifExists() {
        waitForPageLoaded(driver);
        if (PageJCAS.thisPageIsShown(driver)) {
            PageJCAS.identication(driver, Constantes.userManto, Constantes.passwordManto);
        }
    }    
    
    public void selecionPais() throws Exception {
        new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(By.xpath(XPathSelectPaises)));
        
        //Damos de alta la cookie de newsLetter porque no podemos gestionar correctamente el cierre 
        //del modal en la página de portada (es aleatorio y aparece en un intervalo de 0 a 5 segundos)
        setInitialModalsOff();
        if (dCtxSh.channel.isDevice() ||
        	!isPaisSelectedDesktop()) {
            if (!dCtxSh.channel.isDevice()) {
                //Nos posicionamos y desplegamos la lista de países (en el caso de mobile no desplegamos 
            	//porque entonces es complejo manejar el desplegable que aparece en este tipo de dispositivos)
                desplieguaListaPaises();
            }
            
            inputPaisAndSelect(dCtxSh.pais.getNombre_pais());
        }
    }
    
    public void selecionIdiomaAndEnter() throws Exception { 
        if (dCtxSh.pais.getListIdiomas().size() > 1) {
            //Si el país tiene más de 1 idioma seleccionar el que nos llega como parámetro
            seleccionaIdioma(dCtxSh.pais.getNombre_pais(), dCtxSh.idioma.getCodigo().getLiteral());
        } else {
            String codigoPais = getCodigoPais(dCtxSh.pais.getNombre_pais());
            selectButtonForEnter(codigoPais);
        }
    
        //Esperamos a que desaparezca la página de Prehome
        isNotPageUntil(30);
        waitForPageLoaded(driver);
    }
}
