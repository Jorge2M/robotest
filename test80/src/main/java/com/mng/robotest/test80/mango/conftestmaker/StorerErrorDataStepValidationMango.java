package com.mng.robotest.test80.mango.conftestmaker;

import java.io.File;
import java.io.FileWriter;
import java.net.URI;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.conf.StorerErrorDataStepValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest.TypeEvidencia;
import com.mng.robotest.test80.arq.utils.webdriver.maker.FactoryWebdriverMaker.TypeWebDriver;

public class StorerErrorDataStepValidationMango implements StorerErrorDataStepValidation {

	@Override
	public void store(DataFmwkTest dFTest, DatosStep datosStep) throws Exception {
		capturaErrorPage(dFTest, datosStep.getStepNumber());
	}
	
    /**
     * Se realiza una captura de ./errorPage.faces pues allí se pueden encontrar los datos de la instancia
     */
    public static void capturaErrorPage(DataFmwkTest dFTest, int stepNumber) throws Exception {
        if (dFTest.typeDriver!=TypeWebDriver.browserstack) {
            //Cargamos la página errorPage en una pestaña aparte y nos posicionamos en ella
            //BrowserStack parece que no soporta abrir ventanas aparte
            String windowHandle = loadErrorPage(dFTest.driver);
            try {
                String methodWithFactory = fmwkTest.getMethodWithFactory(dFTest.meth, dFTest.ctx);
                String nombreErrorFile = fmwkTest.getPathFileEvidenciaStep(dFTest.ctx, methodWithFactory, stepNumber, TypeEvidencia.errorpage);
                File errorImage = new File(nombreErrorFile);
                try (FileWriter fw = new FileWriter(errorImage)) {
                    fw.write(dFTest.driver.getPageSource());
                }
            } 
            catch (Exception e) {
                throw e;
            } 
            finally {
                // Cerramos la pestaña
                JavascriptExecutor js = (JavascriptExecutor) dFTest.driver;
                js.executeScript("window.close('" + Thread.currentThread().getName() + "');");
                dFTest.driver.switchTo().window(windowHandle);
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
