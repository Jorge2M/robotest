package com.mng.robotest.test80.mango.test.utils.testab;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;

public class TestABGoogleOptimize implements TestAB {
	
    static Logger pLogger = LogManager.getLogger(fmwkTest.log4jLogger);
	
	public TestABid testAB;
	public int varianteActivada = 0;
	
	public TestABGoogleOptimize(TestABid testAB) {
		this.testAB = testAB;
	}
	
	public TestABGoogleOptimize(TestABid testAB, int variante) {
		this.testAB = testAB;
		this.varianteActivada = variante;
	}
	
	public TestABGoogleOptimize(String paramWithPointSeparator) {
		int posPoint = paramWithPointSeparator.indexOf(".");
	    String idTestAB = paramWithPointSeparator.substring(0, posPoint);
	    int variante = Integer.valueOf(paramWithPointSeparator.substring(posPoint + 1));
	    this.testAB = TestABid.valueOf(idTestAB);
	    this.varianteActivada = variante;
	}
	
	@Override
	public void activateTestAB(int variante, WebDriver driver) throws Exception {
        this.varianteActivada = variante;
        activateTestAB(driver);
	}
	
	@Override
	public void activateTestAB(WebDriver driver) throws Exception {
		//En BrowserStack no funciona el tema de mostrar una nueva pestaña
		if (!driver.toString().contains("RemoteWebDriver")) {
			String urlToSetTestAB = getUrlOptimizeToSetTestAB(this.varianteActivada);
			String titleTab = testAB.id;
			String windowHandlerToReturn = driver.getWindowHandle();
			try {
				WebdrvWrapp.loadUrlInAnotherTabTitle(urlToSetTestAB, titleTab, driver);
				WebdrvWrapp.closeTabByTitleAndReturnToWidow(titleTab, windowHandlerToReturn, driver);
			}
			catch (WebDriverException e) {
				pLogger.warn("Problem in activate TestAB", e);
			}
		}
	}
	
	@Override
	public void activateRandomTestABInBrowser(WebDriver driver) throws Exception {
		int numVariantes = testAB.variantes.size();
		int variante = RandomNumber(0, numVariantes-1);
		activateTestAB(variante, driver);
	}
	
	@Override
	public int getVariant(WebDriver driver) throws UnsupportedOperationException {
        throw new UnsupportedOperationException(
        	String.format("%s is of type %s that does not support this method yet.",
        	testAB,
            testAB.getType())
        );
	}	
	
	private String getUrlOptimizeToSetTestAB(int variante) {
		StringBuilder urlResult = new StringBuilder();
		urlResult.append("https://www.google-analytics.com/start_preview/opt?uiv2&");
		urlResult.append("id=" + testAB.id + "&");
		urlResult.append("gtm_auth=" + testAB.auth + "&");
		urlResult.append("gtm_experiment=" + testAB.getExperimentWithVariant(variante) + "&");
		urlResult.append("gtm_preview=" + testAB.preview + "&");
		urlResult.append("gtm_debug&optimize_editor");
		return (urlResult.toString());
	}
	
	private int RandomNumber(int minimo, int maximo) {
		Random random = new Random();
		return (random.nextInt(maximo - minimo + 1) + minimo);
	}
}
