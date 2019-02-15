package com.mng.robotest.test80.mango.test.pageobject.shop.registro;

import java.util.StringTokenizer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;


public class PageRegistroSegunda extends WebdrvWrapp {

    private static final String XPathNewsletterTitle = "//div[@class[contains(.,'additionalData')]]//span[@class='info']";
    private static final String XPathFormStep2 = "//form[@class[contains(.,'customFormIdSTEP2')]]";
    private static final String XPathCheckboxLinea = "//input[@type='checkbox']";
    private static final String XPathSelectDiaNacim = "//select[@id[contains(.,'naciDia')]]";
    private static final String XPathSelectMesNacim = "//select[@id[contains(.,'naciMes')]]";
    private static final String XPathSelectAnyNacim = "//select[@id[contains(.,'naciAny')]]";
    private static final String XPathButtonContinuar = 
    	"//div[@class[contains(.,'registerStepsModal')]]//div[@class='submitContent']//input[@type='submit']";    
    
    public static String getXPath_checkboxLinea(String linea) {
        return ("//input[@type='checkbox' and @id[contains(.,':" + linea + "')]]");
    }
    
    /**
     * @return el xpath del elemento que hay que clicar para marcar/desmarcar los checkbox de líneas
     */
    public static String getXPath_checkboxLineaClickable(String linea) {
        return (getXPath_checkboxLinea(linea) + "/..");
    }
    
    public static String getNewsLetterTitleText(WebDriver driver) {
        try {
            WebElement titleNws = driver.findElement(By.xpath(XPathNewsletterTitle));
            if (titleNws!=null)
                return driver.findElement(By.xpath(XPathNewsletterTitle)).getText();
        }
        catch (Exception e) {
            //retornaremos ""
        }
        
        return "";
    }
    
    public static boolean newsLetterTitleContains(String literal, WebDriver driver) {
        return (getNewsLetterTitleText(driver).contains(literal));
    }
    
    static String getXPath_radioNinosInFamily(int numNinos) {
        return ("//div[@class='radiobuttonBtn']/input[@value='" + numNinos + "']");
    }
    
    public static boolean isPageUntil(WebDriver driver, int maxSecondsToWait) throws Exception {
        return (isElementPresentUntil(driver, By.xpath(XPathFormStep2), maxSecondsToWait));    
    }
    
    /**
     * @return el número de colecciones existentes a nivel de checkbox (she, he, kids, violeta...)
     */
    public static int getNumColecciones(WebDriver driver) {
        return (driver.findElements(By.xpath(XPathCheckboxLinea)).size());
    }
    
    /**
     * @param fechaNacimiento en formato DD/MM/AAAA
     */
    public static void setFechaNacimiento(WebDriver driver, String fechaNacimiento) {
        StringTokenizer st = new StringTokenizer(fechaNacimiento, "/");
        setFechaNacimiento(driver, st.nextToken(), st.nextToken(), st.nextToken());
    }
    
    public static void setNumeroNinos(int numNinos, WebDriver driver) {
        String xpathRadio = getXPath_radioNinosInFamily(numNinos);
        driver.findElement(By.xpath(xpathRadio)).click();
    }
    
    public static void setFechaNacimiento(WebDriver driver, String dia, String mes, String any) {
        PageRegistroSegunda.selectDiaNacimByText(driver, dia);
        PageRegistroSegunda.selectMesNacimByText(driver, mes);
        PageRegistroSegunda.selectAnyNacimByText(driver, any);
    }
    
    public static boolean isPresentSelectDiaNacim(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathSelectDiaNacim)));
    }
    
    public static void selectDiaNacimByText(WebDriver driver, String dia) {
        new Select(driver.findElement(By.xpath(XPathSelectDiaNacim))).selectByVisibleText(dia);
    }
    
    public static void selectMesNacimByText(WebDriver driver, String mes) {
        new Select(driver.findElement(By.xpath(XPathSelectMesNacim))).selectByValue(mes);
    }
    
    public static void selectAnyNacimByText(WebDriver driver, String any) {
        new Select(driver.findElement(By.xpath(XPathSelectAnyNacim))).selectByVisibleText(any);
    }
    
    /**
     * @return si existen todos los checkbox correspondientes a una lista de líneas separadas por comas
     */
    public static boolean isPresentInputForLineas(WebDriver driver, String lineasComaSeparated) {
        boolean isPresentInputs = true;
        StringTokenizer tokensLinea = new StringTokenizer(lineasComaSeparated, ",");
        while (tokensLinea.hasMoreElements()) {
            String lineaStr=tokensLinea.nextToken();
            String xpathCheckboxLinea = getXPath_checkboxLinea(lineaStr);
            if (!isElementPresent(driver, By.xpath(xpathCheckboxLinea))) {
                isPresentInputs = false;
                break;
            }
        }
        
        return isPresentInputs; 
    }
    
    /**
     * Desmarca una serie de líneas al azar (de entre las contenidas en lineasComaSeparated)
     * @return las líneas desmarcadas separadas por comas
     */
    public static String desmarcarLineasRandom(WebDriver driver, String lineasComaSeparated) throws Exception {
        StringTokenizer tokensLin = new StringTokenizer(lineasComaSeparated, ",");
        String lineasDesmarcadas = "";
        int i=0;
        while (tokensLin.hasMoreElements()) { 
            String lineaStr=tokensLin.nextToken();
            if (Math.random() < 0.5) {
                String xpathLineaClick = getXPath_checkboxLineaClickable(lineaStr);
                if (isElementPresent(driver, By.xpath(xpathLineaClick)))
                    clickAndWaitLoad(driver, By.xpath(xpathLineaClick));
                        
                //Las líneas que desmarcamos las guardamos
                if (i>0) 
                    lineasDesmarcadas+=",";
                
                lineasDesmarcadas+=lineaStr;
                i+=1;
            }
        }    
        
        return lineasDesmarcadas;
    }
    
    public static void clickButtonContinuar(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathButtonContinuar));
    }
}
