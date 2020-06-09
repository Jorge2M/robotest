package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.mercadopago;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageMercpagoLogin {
    
    static String XPathInputUser = "//input[@id='user_id']";
    static String XPathInputPassword = "//input[@id='password']";
    static String XPathInputCaptcha = "//input[@id[contains(.,'captcha')]]";
    static String XPathImgCaptcha = "//img[@src[contains(.,'captcha')]]";
    static String XPathBotonContinuar = "//input[@id='init' and @type='submit']";
    
    public static boolean isPage(WebDriver driver) {
        return (driver.getTitle().contains("Mercado Pago"));
    }
    
    /**
     * Introducción de texto en el campo correspondiente al email/user
     */
    public static void sendInputUser(WebDriver driver, String input) {
        driver.findElement(By.xpath(XPathInputUser)).clear();
        driver.findElement(By.xpath(XPathInputUser)).sendKeys(input);
    }
    
    /**
     * Introducció de texto en el campo correspondiente al password
     */
    public static void sendInputPassword(WebDriver driver, String input) {
        driver.findElement(By.xpath(XPathInputPassword)).clear();
        driver.findElement(By.xpath(XPathInputPassword)).sendKeys(input);
    }
    
    /**
     * @return si es visible el input para la introducción del usuario
     */
    public static boolean isInputUserVisible(WebDriver driver) {
    	return (state(Visible, By.xpath(XPathInputUser), driver).check());
    }
    
    /**
     * @return si es visible el input para la introducción del password del usuario
     */
    public static boolean isInputPasswordVisible(WebDriver driver) {
    	return (state(Visible, By.xpath(XPathInputPassword), driver).check());
    }    
    
//    /**
//     * En caso de que aparezca un input/imagen para la introducción del captcha, lo hackeamos y lo introducimos 
//     * @param usrServCaptcha: usuario de DeathCaptcha
//     * @param passServCaptcha: password de DeathCaptcha
//     * @param passUsuario: el password del usuario de acceso a Mercadopago (después de la introducción del Captcha lo vuelve a solicitar)
//     */
//    public static void inputCaptchaIfExists(WebDriver driver, String usrDeathCaptcha, String passDeathCaptcha, String passUsuario) throws Exception {
//        //Si aparece el captcha lo introducimos y volvemos a introducir la clave
//        if (wbdrvUtils.isElementPresent(driver, By.xpath(XPathInputCaptcha))) {
//            
//            //Capturamos la URL de la imagen y obtenemos el InputStream
//            String src = driver.findElement(By.xpath(XPathInputCaptcha)).getAttribute("src").replace("https", "http");
//            try (InputStream imagenCaptcha = new URL(src).openStream()) {
//                //Llamamos al servicio para la resolución del captcha a partir de la imagen e introducimos el texto en el input
//                String textoImg = FGenericas.getTextFromImageCaptcha(imagenCaptcha, usrDeathCaptcha, passDeathCaptcha);
//                driver.findElement(By.xpath(XPathInputCaptcha)).sendKeys(textoImg);
//                                
//                //Volvemos el password de acceso a mercadopago
//                sendInputPassword(driver, passUsuario);
//                
//                //Selecionamos el botón "Continuar"
//                clickBotonContinuar(driver);
//            }
//        }
//    }    
    
	public static boolean isVisibleBotonContinuarPageId(WebDriver driver) {
		return (state(Visible, By.xpath(XPathBotonContinuar), driver).check());
	}

	public static void clickBotonContinuar(WebDriver driver) {
		click(By.xpath(XPathBotonContinuar), driver).exec();
	}
}