package com.mng.robotest.conftestmaker;

import java.net.URI;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.domain.InputParamsTM;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.service.webdriver.maker.FactoryWebdriverMaker.EmbeddedDriver;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.github.jorge2m.testmaker.testreports.stepstore.EvidenceStorer;
import com.github.jorge2m.testmaker.testreports.stepstore.StepEvidence;
import com.mng.robotest.domains.base.PageBase;

public class ErrorStorer extends EvidenceStorer {

	public ErrorStorer() {
		super(StepEvidence.ErrorPage);
	}
	
	@Override
	protected String captureContent(StepTM step) {
		String content = "";
		try {
			content = capturaErrorPage();
		}
		catch (Exception e) {
			step.getSuiteParent().getLogger().warn("Exception capturin error. {}", e);
		}
		return content;
	}
	
	/**
	 * Se realiza una captura de ./errorPage.faces pues allí se pueden encontrar los datos de la instancia
	 */
	private String capturaErrorPage() throws Exception {
		String htmlPageError = "";
		InputParamsTM inputParams = TestMaker.getInputParamsSuite();
		String driverId = inputParams.getDriver();
		if (driverId.compareTo(EmbeddedDriver.browserstack.name())!=0) {
			//Cargamos la página errorPage en una pestaña aparte y nos posicionamos en ella
			//BrowserStack parece que no soporta abrir ventanas aparte
			String windowHandle = loadErrorPage();
			WebDriver driver = TestMaker.getDriverTestCase();
			htmlPageError = driver.getPageSource();
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.close('" + Thread.currentThread().getName() + "');");
			driver.switchTo().window(windowHandle);
			driver.switchTo().window(windowHandle);
		}
		return htmlPageError;
	}

	/**
	 * Carga la página errorPage.faces en una pestaña aparte y nos devuelve el windowHandle de la pantalla padre
	 */
	public String loadErrorPage() throws Exception {
		WebDriver driver = TestMaker.getDriverTestCase();
		String currentURL = driver.getCurrentUrl();
		URI uri = new URI(currentURL);
		String windowHandle = driver.getWindowHandle();

		// Abrimos una nueva pestaña en la que cargamos la página de errorPage (sólo con JS es compatible con todos los navegadores)
		String titlePant = Thread.currentThread().getName();
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.open('" + uri.getScheme() + "://" + uri.getHost() + "/errorPage.faces" + "', '" + titlePant + "');");
		driver.switchTo().window(titlePant);
		new PageBase(driver).state(State.Present, By.xpath("//*[@class='stackTrace']")).wait(5).check();
		driver.getPageSource();
		return windowHandle;
	}
}
