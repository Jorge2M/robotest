package com.mng.robotest.test80.mango.conftestmaker;

import java.io.File;
import java.io.FileWriter;
import java.net.URI;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.mng.testmaker.domain.InputParamsTestMaker;
import com.mng.testmaker.domain.StepTestMaker;
import com.mng.testmaker.domain.StepTestMaker.StepEvidence;
import com.mng.testmaker.service.TestMaker;
import com.mng.testmaker.service.testreports.StorerErrorStep;
import com.mng.testmaker.service.webdriver.maker.FactoryWebdriverMaker.WebDriverType;
import com.mng.testmaker.utils.controlTest.StoreStepEvidencies;

public class StorerErrorDataStepValidationMango implements StorerErrorStep {

	@Override
	public void store(StepTestMaker step) throws Exception {
		capturaErrorPage(step);
	}
	
    /**
     * Se realiza una captura de ./errorPage.faces pues allí se pueden encontrar los datos de la instancia
     */
    public static void capturaErrorPage(StepTestMaker step) throws Exception {
    	InputParamsTestMaker inputParams = TestMaker.getTestCase().getInputParamsSuite();
    	WebDriverType webDriverType = inputParams.getWebDriverType();
        if (webDriverType!=WebDriverType.browserstack) {
            //Cargamos la página errorPage en una pestaña aparte y nos posicionamos en ella
            //BrowserStack parece que no soporta abrir ventanas aparte
        	WebDriver driver = TestMaker.getDriverTestCase();
            String windowHandle = loadErrorPage(driver);
            try {
                String nombreErrorFile = StoreStepEvidencies.getPathFileEvidenciaStep(step, StepEvidence.errorpage);
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
