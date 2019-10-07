package com.mng.robotest.test80.mango.test.pageobject.shop.ficha;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.utils.UtilsTestMango;


public class SSecSelTallasFichaOld extends WebdrvWrapp {
    
    static String XPathSelectTalla = "//select[@id[contains(.,'productFormSelect')]]";
    static String XPathOptionTallaUnica = XPathSelectTalla + "/option[@data-available='true' and @value[contains(.,'99')]]";
    static String XPathOptionTalla = XPathSelectTalla + "/option[not(@data-text='0')]"; 
    
    public static String getXPathOptionTallaSegunDisponible(boolean disponible) {
        String disponibleStr = String.valueOf(disponible);
        return (XPathOptionTalla + "[@data-available='" + disponibleStr + "']");
    }
    
    public static String getXPathOptionTallaSegunDisponible(boolean disponible, String talla) {
    	String xpathOption = getXPathOptionTallaSegunDisponible(disponible);
    	return (xpathOption + "//self::*[text()[contains(.,'" + talla + "')]]");
    }
    
    public static boolean isVisibleSelectorTallasUntil(int maxSecondsToWait, WebDriver driver) {
        return (isElementVisibleUntil(driver, By.xpath(XPathSelectTalla), maxSecondsToWait));
    }
    
    public static int getNumOptionsTallas(WebDriver driver) {
        return (driver.findElements(By.xpath(XPathOptionTalla)).size());
    }
    
    public static int getNumOptionsTallasNoDisponibles(WebDriver driver) {
        String xpathOptions = getXPathOptionTallaSegunDisponible(false/*disponible*/);
        return (driver.findElements(By.xpath(xpathOptions)).size());
    }
    
    public static boolean isTallaAvailable(String talla, WebDriver driver) {
    	String xpathTalla = getXPathOptionTallaSegunDisponible(true, talla);
    	return (isElementPresent(driver, By.xpath(xpathTalla)));
    }
    
    public static boolean isTallaUnica(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathOptionTallaUnica)));
    }

    public static Select despliegaSelectTallas(WebDriver driver) {
        return (new Select(driver.findElement(By.xpath(XPathSelectTalla))));
    }
    
    /**
     * @param value talla existente en el atributo value (se trata de la talla en formato número)
     */
    public static void selectTallaByValue(String tallaValue, WebDriver driver) {
        new Select(driver.findElement(By.xpath(XPathSelectTalla))).selectByValue(tallaValue);
    }
    
    public static void selectTallaByIndex(int posicionEnDesplegable, WebDriver driver) {
        new Select(driver.findElement(By.xpath(XPathSelectTalla))).selectByIndex(posicionEnDesplegable);
    }
    
    public static void selectFirstTallaAvailable(WebDriver driver) {
        Select selectTalla = despliegaSelectTallas(driver);
        List<WebElement> listOptions = selectTalla.getOptions();
        String valueTallaToSelect = "";
        for (WebElement talla : listOptions) {
            if ("true".compareTo(talla.getAttribute("data-available"))==0) {
                valueTallaToSelect = talla.getAttribute("value");
                break;
            }
        }
        
        selectTalla.selectByValue(valueTallaToSelect);
    }    
    
    /**
     * @return el literal visible de la talla seleccionada en el desplegable
     */
    public static String getTallaAlfSelected(AppEcom app, WebDriver driver) {
        Select select = despliegaSelectTallas(driver);
        String tallaVisible = select.getFirstSelectedOption().getText(); 
        
        //Tratamos el caso relacionado con los entornos de test y eliminamos la parte a partir de " - " para contemplar casos como el de 'S - Delivery in 4-7 business day')
        if (tallaVisible.indexOf(" - ") >= 0) {
            tallaVisible = tallaVisible.substring(0, tallaVisible.indexOf(" - "));
        }
        
        //Tratamos el caso de talla única donde unificamos el valor a "U"
        String codTallaUnica = UtilsTestMango.getCodigoTallaUnica(app);
        if (getTallaNumSelected(driver).compareTo(codTallaUnica)==0) {
            tallaVisible = "U";
        }
        
        return tallaVisible;        
    }
    
    /**
     * @return el value de la talla seleccionada en el desplegable
     */
    public static String getTallaNumSelected(WebDriver driver) {
        Select select = despliegaSelectTallas(driver);
        return (select.getFirstSelectedOption().getAttribute("value"));        
    }
    
    public static String getTallaAlf(int posicion, WebDriver driver) {
    	String xpathTalla = "(" + XPathOptionTalla + ")[" + posicion + "]";
    	if (isElementPresent(driver, By.xpath(xpathTalla))) {
    		return (driver.findElement(By.xpath(xpathTalla)).getText());
    	}
    	return "";
    }
    
    public static String getTallaCodNum(int posicion, WebDriver driver) {
    	String xpathTalla = "(" + XPathOptionTalla + ")[" + posicion + "]";
    	if (isElementPresent(driver, By.xpath(xpathTalla))) {
    		return (driver.findElement(By.xpath(xpathTalla)).getAttribute("value"));
    	}
    	return "";
    }
}
