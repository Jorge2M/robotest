package com.mng.robotest.test80.mango.test.pageobject.shop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;


/**
 * Clase que define la automatización de las diferentes funcionalidades de la página de autentificación de 
 * Jasig CAS que aparece en los entornos de test cuando se accede desde fuera 
 * @author jorge.munoz
 *
 */
public class PageJCAS extends WebdrvWrapp {

    static String XPathInputUser = "//input[@id='username']";
    static String XPathInputPass = "//input[@id='password']";
    static String XPathButtonLogin = "//input[@value='LOGIN']";
    
    /**
     * Función que nos indica si la página actualmente mostrada es la de autentificación mediante JasigCAS
     * @return indicador de si es o no la página de Jasig CAS
     */
    public static boolean thisPageIsShown(WebDriver driver) {
        return (driver.getTitle().contains("Central Authentication Service"));
    }
    
    /**
     * Realiza el proceso de identificación en la página de Jasig CAS
     */
    public static void identication(WebDriver driver, String usuario, String password) throws Exception {
        inputCredenciales(usuario, password, driver);
        clickButtonLogin(driver);
    }
    
    public static void inputCredenciales(String usuario, String password, WebDriver driver) {
        driver.findElement(By.xpath(XPathInputUser)).sendKeys(usuario);
        driver.findElement(By.xpath(XPathInputPass)).sendKeys(password);        
    }
    
    public static void clickButtonLogin(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathButtonLogin));
    }
}
