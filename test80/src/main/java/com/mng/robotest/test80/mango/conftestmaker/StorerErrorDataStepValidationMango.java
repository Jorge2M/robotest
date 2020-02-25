package com.mng.robotest.test80.mango.conftestmaker;

import java.io.File;
import java.io.FileWriter;
import java.net.URI;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.mng.testmaker.domain.InputParamsTM;
import com.mng.testmaker.domain.suitetree.StepTM;
import com.mng.testmaker.service.TestMaker;
import com.mng.testmaker.service.webdriver.maker.FactoryWebdriverMaker.WebDriverType;
import com.mng.testmaker.testreports.html.StoreStepEvidencies;
import com.mng.testmaker.testreports.html.StoreStepEvidencies.StepEvidence;
import com.mng.testmaker.testreports.html.StorerErrorStep;

public class StorerErrorDataStepValidationMango implements StorerErrorStep {

	@Override
	public void store(StepTM step) throws Exception {
		capturaErrorPage(step);
	}
	
    /**
     * Se realiza una captura de ./errorPage.faces pues allí se pueden encontrar los datos de la instancia
     */
    public static void capturaErrorPage(StepTM step) throws Exception {
    	InputParamsTM inputParams = TestMaker.getTestCase().getInputParamsSuite();
    	WebDriverType webDriverType = inputParams.getWebDriverType();
        if (webDriverType!=WebDriverType.browserstack) {
            //Cargamos la página errorPage en una pestaña aparte y nos posicionamos en ella
            //BrowserStack parece que no soporta abrir ventanas aparte
        	WebDriver driver = TestMaker.getDriverTestCase();
            String windowHandle = loadErrorPage(driver);
            try {
            	StoreStepEvidencies storer = new StoreStepEvidencies(step);
                String nombreErrorFile = storer.getPathFileEvidencia(StepEvidence.errorpage);
                File errorImage = new File(nombreErrorFile);
                try (FileWriter fw = new FileWriter(errorImage)) {
                    fw.write(driver.getPageSource());
                }
            } 
            catch (Exception e) {
                throw e;
            } 
            finally {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("window.close('" + Thread.currentThread().getName() + "');");
                driver.switchTo().window(windowHandle);
            }
        }
    }
    
    /**
     * Carga la página errorPage.faces en una pestaña aparte y nos devuelve el windowHandle
     */
    public static String loadErrorPage(WebDriver driver) throws Exception {
        String currentURL = driver.getCurrentUrl();
        URI uri = new URI(currentURL);
        String windowHandle = driver.getWindowHandle();

        // Abrimos una nueva pestaña en la que cargamos la página de errorPage (sólo con JS es compatible con todos los navegadores)
        String titlePant = Thread.currentThread().getName();
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("window.open('" + uri.getScheme() + "://" + uri.getHost() + "/errorPage.faces" + "', '" + titlePant + "');");
        driver.switchTo().window(titlePant);
        try {
            new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(By.className("stackTrace")));
        }
        catch (Exception e) {
            //
        }
        
        driver.getPageSource();
        return windowHandle;
    }
}
