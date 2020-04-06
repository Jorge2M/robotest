package com.mng.robotest.test80.mango.conftestmaker;

import java.net.URI;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.jorge2m.testmaker.conf.Log4jConfig;
import com.github.jorge2m.testmaker.domain.InputParamsTM;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.service.webdriver.maker.FactoryWebdriverMaker.WebDriverType;
import com.github.jorge2m.testmaker.testreports.stepstore.EvidenceStorer;
import com.github.jorge2m.testmaker.testreports.stepstore.StepEvidence;

public class ErrorStorer extends EvidenceStorer {

	public ErrorStorer() {
		super(StepEvidence.errorpage);
	}
	
	@Override
	protected String captureContent(StepTM step) {
		String content = "";
		try {
			content = capturaErrorPage(step);
		}
		catch (Exception e) {
			Log4jConfig.pLogger.warn("Exception capturin error. " + e);
		}
		return content;
	}
	
	/**
	 * Se realiza una captura de ./errorPage.faces pues allí se pueden encontrar los datos de la instancia
	 */
	private String capturaErrorPage(StepTM step) throws Exception {
		String htmlPageError = "";
		InputParamsTM inputParams = TestMaker.getTestCase().getInputParamsSuite();
		WebDriverType webDriverType = inputParams.getWebDriverType();
		if (webDriverType!=WebDriverType.browserstack) {
			//Cargamos la página errorPage en una pestaña aparte y nos posicionamos en ella
			//BrowserStack parece que no soporta abrir ventanas aparte
			WebDriver driver = TestMaker.getDriverTestCase();
			String windowHandle = loadErrorPage(driver);
			htmlPageError = driver.getPageSource();
//			try {
				//String nombreErrorFile = StepEvidence.errorpage.getPathFile(step);
//				File errorImage = new File(nombreErrorFile);
//				try (FileWriter fw = new FileWriter(errorImage)) {
//					fw.write(driver.getPageSource());
//				}
//			} 
//			catch (Exception e) {
//				throw e;
//			} 
//			finally {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("window.close('" + Thread.currentThread().getName() + "');");
				driver.switchTo().window(windowHandle);
//			}
		}
		return htmlPageError;
	}

	/**
	 * Carga la página errorPage.faces en una pestaña aparte y nos devuelve el windowHandle de la pantalla padre
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
