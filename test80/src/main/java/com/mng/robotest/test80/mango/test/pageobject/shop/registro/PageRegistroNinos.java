package com.mng.robotest.test80.mango.test.pageobject.shop.registro;

import java.util.StringTokenizer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import com.mng.testmaker.webdriverwrapper.WebdrvWrapp;


public class PageRegistroNinos extends WebdrvWrapp {
    
    private static final String xpathInputNombre = "//input[@id[contains(.,'cfNameKid')]]";
    private static final String xpathSelectDiaNacimiento = "//select[@id[contains(.,'naciDia')]]";
    private static final String xpathSelectMesNacimiento = "//select[@id[contains(.,'naciMes')]]";
    private static final String xpathSelectAnyNacimiento = "//select[@id[contains(.,'naciAny')]]";
    private static final String xpathBotonContinuar = 
    	"//div[@class[contains(.,'registerStepsModal')]]//div[@class='submitContent']//input[@type='submit']";
    
    public static boolean isPageUntil(WebDriver driver, int maxSeconds) {
        return (isElementPresentUntil(driver, By.xpath("//form[@id[contains(.,'cfKids')]]"), maxSeconds));
    }
    
    public static int getNumInputsNameNino(WebDriver driver) {
        return (driver.findElements(By.xpath(xpathInputNombre)).size());
    }
    
    public static String getXPath_inputNombre(int numNino) {
        return ("(" + xpathInputNombre + ")[" + numNino + "]");
    }
    
    public static String getXPath_selectDiaNac(int numNino) {
        return ("(" + xpathSelectDiaNacimiento + ")[" + numNino + "]");
    }
    
    public static String getXPath_selectMesNac(int numNino) {
        return ("(" + xpathSelectMesNacimiento + ")[" + numNino + "]");
    }
    
    public static String getXPath_selectAnyNac(int numNino) {
        return ("(" + xpathSelectAnyNacimiento + ")[" + numNino + "]");
    }
    
    /**
    /* Repetimos la introducción de datos n times para paliar errores aleatorios de desaparición de datos ya introducidos
     */
    public static void setDataNinoIfNotExists(ListDataNinos listNinos, int nTimes, WebDriver driver) {
        for (int i=0; i<nTimes; i++)
            setDataNinoIfNotExists(listNinos, driver);
    }
    
    public static void setDataNinoIfNotExists(ListDataNinos listNinos, WebDriver driver) {
        int i=1;
        for (DataNino dataNino : listNinos.getListNinos()) {
            String nombreNino = dataNino.getNombre();
            String xpathInputNombreNino = getXPath_inputNombre(i);
            if (driver.findElement(By.xpath(xpathInputNombreNino)).getAttribute("value").compareTo(nombreNino)!=0) {
                driver.findElement(By.xpath(xpathInputNombreNino)).clear();
                driver.findElement(By.xpath(xpathInputNombreNino)).sendKeys(nombreNino);
            }
            
            String xpathDia = getXPath_selectDiaNac(i);
            String xpathMes = getXPath_selectMesNac(i);
            String xpathAny = getXPath_selectAnyNac(i);
            StringTokenizer fechaToken = new StringTokenizer(dataNino.getFechaNacimiento(), "/");
            new Select(driver.findElement(By.xpath(xpathDia))).selectByVisibleText(fechaToken.nextToken()); //Día nacimiento
            new Select(driver.findElement(By.xpath(xpathMes))).selectByValue(fechaToken.nextToken()); //Mes de nacimiento
            new Select(driver.findElement(By.xpath(xpathAny))).selectByVisibleText(fechaToken.nextToken()); //Año de nacimiento
            
            i+=1;
        }
    }
    
    public static void clickContinuar(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(xpathBotonContinuar));
    }
}
