package com.mng.robotest.conf;

import java.net.URI;
import java.util.Optional;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.service.webdriver.maker.FactoryWebdriverMaker.EmbeddedDriver;
import com.github.jorge2m.testmaker.testreports.stepstore.EvidenceStorer;
import com.github.jorge2m.testmaker.testreports.stepstore.StepEvidence;
import com.mng.robotest.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class ErrorStorer extends EvidenceStorer {

	public ErrorStorer() {
		super(StepEvidence.ErrorPage);
	}
	
	@Override
	protected String captureContent(StepTM step) {
		try {
			String contentErrorPage = capturaErrorPage();
			return addCookieValueToContent(contentErrorPage);
		}
		catch (Exception e) {
			step.getSuiteParent().getLogger().warn("Exception capturing error", e);
		}
		return "";
	}
	
	public String capturaErrorPage() throws Exception {
		if (isBrowserStack()) {
			//BrowserStack doesn't allow create other Tab
			return "";
		}

		var driver = TestMaker.getDriverTestCase();
		String windowHandler = loadErrorPageAndWait(driver);
		String htmlErrorPage = driver.getPageSource();
		closeErrorPage(windowHandler, driver);
		
		return htmlErrorPage;
	}
	
	private boolean isBrowserStack() {
		var inputParams = TestMaker.getInputParamsSuite();
		String driverId = inputParams.getDriver();
		return (driverId.compareTo(EmbeddedDriver.browserstack.name())==0);
	}

	private String addCookieValueToContent(String contentErrorPage) {
		var jsessionPreCookieValue = getJssesionIdPreValue();
		if (jsessionPreCookieValue.isPresent()) {
			return 
					contentErrorPage +
					"<h2>JSESSIONIDPRE VALUE: </h2>" +
					"<p>" + jsessionPreCookieValue.get() + "</p>";
		}
		return contentErrorPage;
	}
	
	private Optional<String> getJssesionIdPreValue() {
		var driver = TestMaker.getDriverTestCase();
		Cookie jsessionCookie = driver.manage().getCookieNamed("JSESSIONIDPRE");
		if (jsessionCookie==null) {
			return Optional.empty();
		}
		return Optional.of(jsessionCookie.getValue());
	}
	
	/**
	 * Loads the errorPage.faces in other tab
	 * @return windowHandle of the screen pather
	 * @throws Exception
	 */
	private String loadErrorPageAndWait(WebDriver driver) throws Exception {
		String windowHandle = driver.getWindowHandle();
		var jsExecutor = (JavascriptExecutor)driver;
		
		URI uri = new URI(driver.getCurrentUrl());
		jsExecutor.executeScript("window.open('" + uri.getScheme() + "://" + uri.getHost() + "/errorPage.faces" + "', '" + getTabName() + "');");
		driver.switchTo().window(getTabName());
		waitForErrorPage(driver);
		
		return windowHandle;
	}
	
	private void closeErrorPage(String windowHandle, WebDriver driver) {
		var jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.close('" + getTabName() + "');");
		driver.switchTo().window(windowHandle);
		driver.switchTo().window(windowHandle);
	}

	private void waitForErrorPage(WebDriver driver) {
		new PageBase(driver).state(Present, "//*[@class='stackTrace']").wait(5).check();
	}
	
	private String getTabName() {
		return Thread.currentThread().getName();
	}
	
}
