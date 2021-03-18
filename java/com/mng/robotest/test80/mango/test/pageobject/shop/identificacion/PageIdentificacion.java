package com.mng.robotest.test80.mango.test.pageobject.shop.identificacion;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.MenuUserItem.UserMenu;
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
public class PageIdentificacion {
	
	private static String avisoCredencialesKO = "Tu e-mail o contraseña no son correctos";
	static String XPathErrorCredencialesKO = "//div[@class='formErrors']//li[text()[contains(.,'" + avisoCredencialesKO + "')]]";
	static String XPathHasOlvidadoContrasenya = "//span[text()[contains(.,'¿Has olvidado tu contraseña?')]]/../../a";
	static String XPathInputUser = "//input[@id[contains(.,'userMail')]]";
	static String XPathInputPassword = "//input[@id[contains(.,'chkPwd')]]";
	static String XPathSubmitButton = "//div[@class='submitContent']/input[@type='submit']";

	public static boolean isVisibleUserUntil(int maxSeconds, WebDriver driver) {
		return (state(Visible, By.xpath(XPathInputUser), driver)
				.wait(maxSeconds).check());
	}

	public static String getLiteralAvisiCredencialesKO() {
		return avisoCredencialesKO;
	}

	public static void inputUserPassword(String usuario, String password, WebDriver driver) {
		By byInput = By.xpath(XPathInputUser);
		try {
			driver.findElement(byInput).clear();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		waitMillis(250);
		driver.findElement(byInput).sendKeys(usuario);
		waitMillis(250);
		driver.findElement(By.xpath(XPathInputPassword)).sendKeys(password);
	}

	/**
	 * Realizamos un login/logoff según el parámetro 'accUsrReg' que identifica si es preciso que el usuario esté logado
	 */
	public static void loginOrLogoff(DataCtxShop dCtxSh, WebDriver driver) throws Exception {
		if (dCtxSh.userRegistered) {
			iniciarSesion(dCtxSh, driver);
		} else {
			SecMenusWrap secMenus = SecMenusWrap.getNew(dCtxSh.channel, dCtxSh.appE, driver);
			secMenus.closeSessionIfUserLogged();
		}
	}
    
    /**
     * Función que ejecuta la identificación del usuario. Introduce las credenciales del usuario y seleccióna el botón de submit
     */
    public static void iniciarSesion(DataCtxShop dCtxSh, WebDriver driver) throws Exception {
        iniciarSesion(dCtxSh.userConnected, dCtxSh.passwordUser, dCtxSh.channel, dCtxSh.appE, driver);
    }
     
    public static void iniciarSesion(String user, String password, Channel channel, AppEcom app, WebDriver driver) {
        clickIniciarSesionAndWait(channel, app, driver);
        isVisibleUserUntil(10, driver);
        //normalizeLoginForDefeatAkamai(channel, app, driver);
        PageIdentificacion.inputUserPassword(user, password, driver);
        clickButtonEntrar(driver);
        ModalCambioPais.closeModalIfVisible(driver);
        ModalActPoliticaPrivacidad.clickOkIfVisible(driver);
        ModalLoyaltyAfterLogin.closeModalIfVisible(driver);
    }    
    
//    /**
//     * Permite superar el control de Akamai a nivel de las peticiones /login.faces?
//     */
//    private static void clickButtonEntrar_BypassAkamai(AppEcom app, WebDriver driver) {
//    	if (UtilsMangoTest.isEntornoPRO(app, driver)) {
//    		clickButtonEntrarSync(driver);
//    	} else {
//    		clickButtonEntrar(driver);
//    	}
//    }
//    
//    private static synchronized void clickButtonEntrarSync(WebDriver driver) {
//    	click(By.xpath(XPathSubmitButton), driver).waitLoadPage(10).exec(); 
//        if (isButtonEntrarVisible(driver)) {
//        	click(By.xpath(XPathSubmitButton), driver).type(javascript).waitLoadPage(10).exec();
//        }
//    }
    private static void clickButtonEntrar(WebDriver driver) {
    	click(By.xpath(XPathSubmitButton), driver).waitLoadPage(10).exec(); 
        if (isButtonEntrarVisible(driver)) {
        	click(By.xpath(XPathSubmitButton), driver).type(javascript).waitLoadPage(10).exec();
        }
    }
   
    private static void normalizeLoginForDefeatAkamai(Channel channel, AppEcom app, WebDriver driver) {
    	if (channel==Channel.desktop && UtilsMangoTest.isEntornoPRO(app, driver)) {
    		String actualUrl = driver.getCurrentUrl();
    		if (actualUrl.contains("login.faces?")) {
    			driver.get(actualUrl.replace("login.faces?", "mobile/login.faces?"));
    		}
    	}
    }
    
    public static boolean isButtonEntrarVisible(WebDriver driver) {
    	return (state(Visible, By.xpath(XPathSubmitButton), driver).check());
    }

    /**
     * Seleccionamos el link "Iniciar Sesión" y esperamos a que los campos de input estén disponibles
     */
    @SuppressWarnings("static-access")
    public static void clickIniciarSesionAndWait(Channel channel, AppEcom app, WebDriver driver) {
        if (channel.isDevice()) {
            //En el caso de mobile nos tenemos que asegurar que están desplegados los menús
        	SecCabecera secCabeceraDevice = SecCabecera.getNew(channel, app, driver);
        	boolean toOpen = true;
        	secCabeceraDevice.clickIconoMenuHamburguerMobil(toOpen);
            
            // Si existe, nos posicionamos y seleccionamos el link \"CERRAR SESIÓN\" 
            // En el caso de iPhone parece que mantiene la sesión abierta después de un caso de prueba 
        	SecMenusWrap secMenus = SecMenusWrap.getNew(channel, app, driver);
        	boolean menuClicado = secMenus.getMenusUser().clickMenuIfInState(UserMenu.cerrarSesion, Clickable);
            
            //Si hemos clicado el menú 'Cerrar Sesión' volvemos a abrir los menús
            if (menuClicado) {
            	secCabeceraDevice.clickIconoMenuHamburguerMobil(toOpen);
            }
        }
        
        SecMenusWrap secMenus = SecMenusWrap.getNew(channel, app, driver);
        secMenus.getMenusUser().moveAndClick(UserMenu.iniciarSesion);
    }
    
    public static boolean isErrorEmailoPasswordKO(WebDriver driver) {
    	return (state(Present, By.xpath(XPathErrorCredencialesKO), driver).check());
    }

	public static void clickHasOlvidadoContrasenya(WebDriver driver) {
		click(By.xpath(XPathHasOlvidadoContrasenya), driver).exec();
	}
}
