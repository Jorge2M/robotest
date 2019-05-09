package com.mng.robotest.test80.mango.test.pageobject.shop.identificacion;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.TypeOfClick;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabeceraMobil;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.SecMenusWrap;
import com.mng.robotest.test80.mango.test.pageobject.shop.modales.ModalActPoliticaPrivacidad;
import com.mng.robotest.test80.mango.test.pageobject.shop.modales.ModalCambioPais;
import com.mng.robotest.test80.mango.test.pageobject.shop.modales.ModalLoyaltyAfterLogin;


/**
 * Clase que define la automatización de las diferentes funcionalidades de la página de autentificación de 
 * Jasig CAS que aparece en los entornos de test cuando se accede desde fuera 
 * @author jorge.munoz
 *
 */
public class PageIdentificacion extends WebdrvWrapp {
    private static String avisoCredencialesKO = "Tu e-mail o contraseña no son correctos";
    static String XPathErrorCredencialesKO = "//div[@class='formErrors']//li[text()[contains(.,'" + avisoCredencialesKO + "')]]";
    static String XPathHasOlvidadoContrasenya = "//span[text()[contains(.,'¿Has olvidado tu contraseña?')]]/../../a";
    static String XPathInputUser = "//input[@id[contains(.,'userMail')]]";
    static String XPathInputPassword = "//input[@id[contains(.,'chkPwd')]]";
    static String XPathSubmitButton = "//div[@class='submitContent']/input[@type='submit']";
    
    public static boolean isVisibleUserUntil(int maxSecondsToWait, WebDriver driver) {
        return isElementVisibleUntil(driver, By.xpath(XPathInputUser), maxSecondsToWait);
    }
    
    public static String getLiteralAvisiCredencialesKO() {
    	return avisoCredencialesKO;
    }
    
    public static void inputUserPassword(String usuario, String password, WebDriver driver) throws Exception {
        sendKeysWithRetry(2, usuario, By.xpath(XPathInputUser), driver);
        sendKeysWithRetry(2, password, By.xpath(XPathInputPassword), driver);
        sendKeysWithRetry(2, usuario, By.xpath(XPathInputUser), driver);
    }
    
    /**
     * Realizamos un login/logoff según el parámetro 'accUsrReg' que identifica si es preciso que el usuario esté logado
     */
    public static void loginOrLogoff(DataCtxShop dCtxSh, WebDriver driver) throws Exception {
        if (dCtxSh.userRegistered) {
            iniciarSesion(dCtxSh, driver);
        } else {
            SecMenusWrap.closeSessionIfUserLogged(dCtxSh.channel, dCtxSh.appE, driver);
        }
    }
    
    /**
     * Función que ejecuta la identificación del usuario. Introduce las credenciales del usuario y seleccióna el botón de submit
     */
    public static void iniciarSesion(DataCtxShop dCtxSh, WebDriver driver) throws Exception {
        iniciarSesion(dCtxSh.userConnected, dCtxSh.passwordUser, dCtxSh.channel, dCtxSh.appE, driver);
    }
     
    public static void iniciarSesion(String user, String password, Channel channel, AppEcom appE, WebDriver driver) 
    throws Exception {
        clickIniciarSesionAndWait(channel, appE, driver);
        int maxSecondsToWait = 10;
        isVisibleUserUntil(maxSecondsToWait, driver);
        PageIdentificacion.inputUserPassword(user, password, driver);
        clickButtonEntrar(driver);
        ModalCambioPais.closeModalIfVisible(driver);
        ModalActPoliticaPrivacidad.clickOkIfVisible(driver);
        ModalLoyaltyAfterLogin.closeModalIfVisible(driver);
    }    
    
    public static void clickButtonEntrar(WebDriver driver) throws Exception {
    	clickAndWaitLoad(driver, By.xpath(XPathSubmitButton));

    	//Existe un problema en Firefox-Gecko con este botón: a veces el 1er click no funciona así que ejecutamos un 2o 
        if (isButtonEntrarVisible(driver)) {
        	clickAndWaitLoad(driver, By.xpath(XPathSubmitButton), TypeOfClick.javascript);
        }
    }
    
    public static boolean isButtonEntrarVisible(WebDriver driver) {
    	return (isElementVisible(driver, By.xpath(XPathSubmitButton)));
    }

    /**
     * Seleccionamos el link "Iniciar Sesión" y esperamos a que los campos de input estén disponibles
     */
    @SuppressWarnings("static-access")
    public static void clickIniciarSesionAndWait(Channel channel, AppEcom app, WebDriver driver) throws Exception {
        if (channel==Channel.movil_web) {
            //En el caso de mobile nos tenemos que asegurar que están desplegados los menús
        	SecCabeceraMobil secCabeceraMobil = (SecCabeceraMobil)SecCabecera.getNew(Channel.movil_web, app, driver);
        	boolean toOpen = true;
        	secCabeceraMobil.clickIconoMenuHamburguer(toOpen);
            
            // Si existe, nos posicionamos y seleccionamos el link \"CERRAR SESIÓN\" 
            // En el caso de iPhone parece que mantiene la sesión abierta después de un caso de prueba 
            boolean menuClicado = SecMenusWrap.secMenusUser.clickCerrarSessionIfLinkExists(channel, driver);
            
            //Si hemos clicado el menú 'Cerrar Sesión' volvemos a abrir los menús
            if (menuClicado) {
            	secCabeceraMobil.clickIconoMenuHamburguer(toOpen);
            }
        }
        
        SecMenusWrap.secMenusUser.MoveAndclickIniciarSesion(channel, driver);
    }
    
    public static boolean isErrorEmailoPasswordKO(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathErrorCredencialesKO))); 
    }
    
    public static void clickHasOlvidadoContrasenya(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathHasOlvidadoContrasenya));
    }
}
