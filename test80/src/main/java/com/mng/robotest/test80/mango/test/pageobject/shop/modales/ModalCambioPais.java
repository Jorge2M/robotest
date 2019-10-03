package com.mng.robotest.test80.mango.test.pageobject.shop.modales;

import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.testmaker.webdriverwrapper.WebdrvWrapp;


/**
 * Clase que define la automatización de las diferentes funcionalidades de la sección de "Cabecera" (de Desktop y Movil)
 * @author jorge.munoz
 *
 */
public class ModalCambioPais extends WebdrvWrapp {
    
    static String XPathModal = "//div[(@id='cambioPais' or @id='seleccionPais') and @class='modalPopUp']"; 
    static String XPathModalSelecProvincia = "//div[@class='wrapper-cell']/div[@id='seleccionProvincia']";
    static String XPathAspaClose = "//div[@data-ga-category='modal-deteccion-ip' and @class[contains(.,'closeModal')]]";
    
    /**
     * @return el xpath correspondiente al link para confirmación de un país concreto (nos basamos en el href)
     */
    public static String getXPath_linkToConfirmPais(String hrefPais) {
        return "//div[@class='navOtherCountry']//a[@href[contains(.,'" + hrefPais + "')]]";
    }
    
    /**
     * @param urlAccesoPais url de acceso al país. Si es "" se retornará el botón que exista
     * @return el xpath correspondiente al boton para cambio a un país concreto. Si el parámetro de entrada es "" se retornará el botón que exista sin tener en cuenta el país
     */
    public static String getXPath_buttonToChangePais(String urlAccesoPais) {
        return (XPathModal + "//div[@class[contains(.,'modalConfirmacionPais')]]//a[@class[contains(.,'_langBtn')] and @href[contains(.,'" + urlAccesoPais + "')]]");
    }
    
    /**
     * @return indica si el modal es visible
     */
    public static boolean isVisibleModalUntil(WebDriver driver, int secondsMaxWait) {
        return isElementVisibleUntil(driver, By.xpath(XPathModal), secondsMaxWait);
    }
    
    public static boolean isVisibleModalSelecProvincia(WebDriver driver) {
        return isElementVisible(driver, By.xpath(XPathModalSelecProvincia));
    }
    
    /**
     * @return si existe el link para la confirmación de un país concreto (nos basamos en el href del país)
     */
    public static boolean isLinkToConfirmPais(WebDriver driver, String hrefPais) {
        String xpath = getXPath_linkToConfirmPais(hrefPais);
        return (isElementPresent(driver, By.xpath(xpath)));
    }
    
    public static Pais getPaisOfButtonForChangePais(List<Pais> listPaisesCandidatos, String urlBaseTest, WebDriver driver) 
    throws Exception {
    	Iterator<Pais> it = listPaisesCandidatos.iterator();
	    while (it.hasNext()) {
	        Pais paisCandidato = it.next();
	        String urlAccesoPais = paisCandidato.getUrlPaisEstandar(urlBaseTest);
	        if (ModalCambioPais.isButtonToChangePais(driver, urlAccesoPais)) {
	            return (paisCandidato);
	        }
	    }
	    
	    return null;
    }
    
    /**
     * @return si existe el botón para el cambio de país hacia uno concreto
     */
    public static boolean isButtonToChangePais(WebDriver driver, String urlAccesoPais) {
        String xpath = getXPath_buttonToChangePais(urlAccesoPais);
        return (isElementPresent(driver, By.xpath(xpath)));
    }
    
    /**
     * @return el text asociado al botón para el cambio de país
     */
    public static String getTextPaisButtonChagePais(WebDriver driver) {
        //Obtenemos el xpath del botón y el atributo "href" asociado
        String xpath = getXPath_buttonToChangePais(""/*urlAccesoPais*/);
        return (driver.findElement(By.xpath(xpath)).getText());
    }    
    
    /**
     * @return el href asociado al botón para el cambio de país
     */
    public static String getHRefPaisButtonChagePais(WebDriver driver) {
        //Obtenemos el xpath del botón y el atributo "href" asociado
        String xpath = getXPath_buttonToChangePais(""/*urlAccesoPais*/);
        return (driver.findElement(By.xpath(xpath)).getAttribute("href"));
    }
    
    /**
     * Selecciona el botón de confirmación de cambio de país
     */
    public static void clickButtonChangePais(WebDriver driver) throws Exception {
        //Obtenemos el xpath del botón y lo seleccionamos
        String xpath = getXPath_buttonToChangePais(""/*urlAccesoPais*/);
        clickAndWaitLoad(driver, By.xpath(xpath));
    }
    
    /**
     * Se cierra el modal en caso de que esté visible
     */
    public static void closeModalIfVisible(WebDriver driver) {
        if (isVisibleModalUntil(driver, 0/*secondsMaxWait^*/)) {
            //Clicamos el aspa para cerrar el modal
            driver.findElement(By.xpath(XPathAspaClose)).click();
            
            //Esperamos un máximo de 1 segundo hasta que se cierra el modal
            try {
                new WebDriverWait(driver, 1).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(XPathModal)));
            }
            catch (Exception e) {
                /*
                 * Si no se cierra continuamos
                 */
            }
        }
    }
}
