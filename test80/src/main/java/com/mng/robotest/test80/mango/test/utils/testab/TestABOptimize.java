package com.mng.robotest.test80.mango.test.utils.testab;

import java.sql.Timestamp;
import java.util.List;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.InvalidCookieDomainException;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;

public class TestABOptimize implements TestAB {
	
    static Logger pLogger = LogManager.getLogger(fmwkTest.log4jLogger);
	
	final public TestABid testAB;
	final Channel channelTest;
	final AppEcom appTest;
	final WebDriver driver;
	public int varianteActivada = 0;
	
	public TestABOptimize(TestABid testAB, Channel channel, AppEcom app, WebDriver driver) {
		this.testAB = testAB;
		this.channelTest = channel;
		this.appTest = app;
		this.driver = driver;
	}
	
	public TestABOptimize(TestABid testAB, int variante, Channel channel, AppEcom app, WebDriver driver) {
		this.testAB = testAB;
		this.varianteActivada = variante;
		this.channelTest = channel;
		this.appTest = app;
		this.driver = driver;
	}
	
	public TestABOptimize(String paramWithPointSeparator, Channel channel, AppEcom app, WebDriver driver) {
		int posPoint = paramWithPointSeparator.indexOf(".");
	    String idTestAB = paramWithPointSeparator.substring(0, posPoint);
	    int variante = Integer.valueOf(paramWithPointSeparator.substring(posPoint + 1));
	    this.testAB = TestABid.valueOf(idTestAB);
	    this.varianteActivada = variante;
		this.channelTest = channel;
		this.appTest = app;
	    this.driver = driver;
	}
	
	@Override
	public void activateTestAB(int variante) throws Exception {
        this.varianteActivada = variante;
        activateTestAB();
	}

	@Override
	public void activateTestAB() throws Exception {
		if (isActiveForChannelAndApp(testAB, channelTest, appTest)) {
			setCookieGtm_auth(testAB, driver);
			setCookieGtm_preview(testAB, driver);
			setCookieGtm_experiment(getVariantInGtm_experiment(testAB, varianteActivada), driver);
		}
	}
	
	public static void activateTestsAB(List<ActivationData> testsABtoActive, Channel channel, AppEcom app, WebDriver driver) throws Exception {
		String valueCookie = "";
		for (ActivationData testABtoActive : testsABtoActive) {
			TestABid testAB = testABtoActive.getTestAB();
			int vTestAB = testABtoActive.getvToActive();
			if (isActiveForChannelAndApp(testAB, channel, app)) {
				if ("".compareTo(valueCookie)==0) {
					setCookieGtm_auth(testAB, driver);
					setCookieGtm_preview(testAB, driver);
					valueCookie+=getVariantInGtm_experiment(testAB, vTestAB);
				} else {
					valueCookie=valueCookie+"&"+testABtoActive.getTestAB().getExperimentWithVariant(vTestAB);
				}
			}
		}
		
		setCookieGtm_experiment(valueCookie, driver);
	}
	
	private static String getVariantInGtm_experiment(TestABid testAB, int variante) {
		return (testAB.group + "=" + testAB.getExperimentWithVariant(variante));
	}
	
	private static void setCookieGtm_auth(TestABid testAB, WebDriver driver) {
		String gtm_auth = "gtm_auth";
		Cookie cookieGtm_auth = new Cookie(
			gtm_auth, 
    		testAB.group + "=" + testAB.auth, 
    		"www.google-analytics.com", 
    		"/gtm/",
    		null, 
    		false, 
    		false
    	);
		setCookie(cookieGtm_auth, driver);
	}
	
	private static void setCookieGtm_preview(TestABid testAB, WebDriver driver) {
		String gtm_preview = "gtm_preview";
		Cookie cookieGtm_preview = new Cookie(
			gtm_preview, 
    		testAB.group + "=" + testAB.preview, 
    		"www.google-analytics.com",
    		"/gtm/",
    		null, 
    		false, 
    		false
    	);
		setCookie(cookieGtm_preview, driver);
	}
	
	private static void setCookieGtm_experiment(String value, WebDriver driver) {
		String gtm_experiment = "gtm_experiment";
		Cookie cookieGtm_experiment = new Cookie(
			gtm_experiment, 
			value, 
    		"www.google-analytics.com", 
    		"/gtm/", 
    		null, 
    		false, 
    		false
    	);
		setCookie(cookieGtm_experiment, driver);
	}
	
	private static void setCookie(Cookie cookie, WebDriver driver) {
		try {
			driver.manage().addCookie(cookie);
		}
		catch (InvalidCookieDomainException e) {
			setCookieAfterInvalidDomainException(cookie, driver);
		}
	}
	
	private static void setCookieAfterInvalidDomainException(Cookie cookie, WebDriver driver) {
		String windowHandlerToReturn = driver.getWindowHandle();
		try {
			String titleTab = "SetCookie " + cookie.getName() + "_" + cookie.getValue();
			WebdrvWrapp.loadUrlInAnotherTabTitle("https://" + cookie.getDomain() + cookie.getPath(), titleTab, driver);
			driver.manage().addCookie(cookie);
			WebdrvWrapp.closeTabByTitleAndReturnToWidow(titleTab, windowHandlerToReturn, driver);
		}
		catch (Exception e) {
			pLogger.warn("Problem activating TestAB via add of Cookie " + cookie.getName(), e);
		}
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
	
	private int RandomNumber(int minimo, int maximo) {
		Random random = new Random();
		return (random.nextInt(maximo - minimo + 1) + minimo);
	}
	
	private static boolean isActiveForChannelAndApp(TestABid testAB, Channel channel, AppEcom app) {
		return (
			testAB.channels.contains(channel) &&
			testAB.apps.contains(app));
	}
}
