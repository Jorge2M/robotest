package com.mng.robotest.test80.mango.test.utils.testab;

import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;

/**
 *
 * @deprecated use TestABOptimize instead
 */
@Deprecated
public class TestABGoogleOptimize implements TestAB {
	
    static Logger pLogger = LogManager.getLogger(fmwkTest.log4jLogger);
	
	final public TestABid testAB;
	final Channel channelTest;
	final AppEcom appTest;
	final WebDriver driver;
	public int varianteActivada = 0;
	
	public TestABGoogleOptimize(TestABid testAB, Channel channel, AppEcom app, WebDriver driver) {
		this.testAB = testAB;
		this.channelTest = channel;
		this.appTest = app;
		this.driver = driver;
	}
	
	public TestABGoogleOptimize(TestABid testAB, int variante, Channel channel, AppEcom app, WebDriver driver) {
		this.testAB = testAB;
		this.varianteActivada = variante;
		this.channelTest = channel;
		this.appTest = app;
		this.driver = driver;
	}
	
	public TestABGoogleOptimize(String paramWithPointSeparator, Channel channel, AppEcom app, WebDriver driver) {
		int posPoint = paramWithPointSeparator.indexOf(".");
	    String idTestAB = paramWithPointSeparator.substring(0, posPoint);
	    int variante = Integer.valueOf(paramWithPointSeparator.substring(posPoint + 1));
	    this.testAB = TestABid.valueOf(idTestAB);
	    this.varianteActivada = variante;
		this.channelTest = channel;
		this.appTest = app;
	    this.driver = driver;
	}
	
	public static void activateTestsAB(List<ActivationData> testsABtoActive, Channel channel, AppEcom app, WebDriver driver) throws Exception {
		String titleTab = "TestABactivations";
		String windowHandlerToReturn = driver.getWindowHandle();
		boolean anyTestABactivated = false;
		for (ActivationData testABtoActive : testsABtoActive) {
			TestABGoogleOptimize testAB = new TestABGoogleOptimize(testABtoActive.getTestAB(), testABtoActive.getvToActive(), channel, app, driver);
			boolean activated = testAB.activateTestABwithoutCloseTab(titleTab);
			if (activated) {
				anyTestABactivated = true;
			}
		}
		
		if (anyTestABactivated) {
			WebdrvWrapp.closeTabByTitleAndReturnToWidow(titleTab, windowHandlerToReturn, driver);
		}
	}
	
	@Override
	public void activateTestAB(int variante) throws Exception {
        this.varianteActivada = variante;
        activateTestAB();
	}

	@Override
	public void activateTestAB() throws Exception {
		String windowHandlerToReturn = driver.getWindowHandle();
		String titleTab = testAB.group;
		boolean activated = activateTestABwithoutCloseTab(titleTab);
		if (activated) {
			WebdrvWrapp.closeTabByTitleAndReturnToWidow(titleTab, windowHandlerToReturn, driver);
		}
	}
	
	public boolean activateTestABwithoutCloseTab(String titleTab) throws Exception {
		if (isActiveForChannelAndApp() && 
			!driver.toString().contains("RemoteWebDriver")) { //En BrowserStack no funciona el tema de mostrar una nueva pestaña
			String urlToSetTestAB = getUrlOptimizeToSetTestAB(this.varianteActivada);
			try {
				WebdrvWrapp.loadUrlInAnotherTabTitle(urlToSetTestAB, titleTab, driver);
				return true;
			}
			catch (WebDriverException e) {
				pLogger.warn("Problem activating TestAB " + this.testAB.group, e);
			}
		}
		return false;
	}

	@Override
	public void activateRandomTestABInBrowser() throws Exception {
		int numVariantes = testAB.variantes.size();
		int variante = RandomNumber(0, numVariantes-1);
		activateTestAB(variante);
	}
	
	@Override
	public int getVariant() throws UnsupportedOperationException {
        throw new UnsupportedOperationException(
        	String.format("%s is of type %s that does not support this method yet.",
        	testAB,
            testAB.getType())
        );
	}	
	
	private String getUrlOptimizeToSetTestAB(int variante) {
		StringBuilder urlResult = new StringBuilder();
		urlResult.append("https://www.google-analytics.com/start_preview/opt?uiv2&");
		urlResult.append("id=" + testAB.group + "&");
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
	
	private boolean isActiveForChannelAndApp() {
		return (
			testAB.channels.contains(channelTest) &&
			testAB.apps.contains(appTest));
	}
}
