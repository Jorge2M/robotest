package com.mng.robotest.test80.mango.test.utils.testab;

import java.util.List;
import java.util.Random;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;

public class TestABGoogleExperiments implements TestAB {
    
	public final static String nameCookieGoogleExperiments = "googleexperiments";
    final public TestABid testAB;
	final Channel channelTest;
	final AppEcom appTest;
	final WebDriver driver;
	
	public int varianteActivada = 0;
	
	public TestABGoogleExperiments(TestABid testAB, Channel channel, AppEcom app, WebDriver driver) {
		this.testAB = testAB;
		this.channelTest = channel;
		this.appTest = app;
		this.driver = driver;
	}
	
	public TestABGoogleExperiments(TestABid testAB, int variante, Channel channel, AppEcom app, WebDriver driver) {
		this.testAB = testAB;
		this.varianteActivada = variante;
		this.channelTest = channel;
		this.appTest = app;
		this.driver = driver;
	}
	
	public TestABGoogleExperiments(String paramWithPointSeparator, Channel channel, AppEcom app, WebDriver driver) {
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
		if (isActiveForChannelAndApp()) {
			String valueCookieRemovingTestAB = getValueCookieResetingAllTestABvariants(driver);
			if (valueCookieRemovingTestAB!=null) { 
		        String testABvalueForVariant = testAB.getValueCookie(appTest) + "%3A" + varianteActivada + "%2C";
		        String newValueCookie = valueCookieRemovingTestAB + testABvalueForVariant;
		        Cookie actualCookie = driver.manage().getCookieNamed(nameCookieGoogleExperiments);
		        Cookie newCookie = getClonedWithNewValue(actualCookie, newValueCookie);
		        driver.manage().deleteCookieNamed(nameCookieGoogleExperiments);
		        driver.manage().addCookie(newCookie);
			}
		}
	}
	
	public static void activateTestsAB(List<ActivationData> testsABtoActive, Channel channel, AppEcom app, WebDriver driver) throws Exception {
		for (ActivationData testABtoActive : testsABtoActive) {
			TestABGoogleExperiments testAB = new TestABGoogleExperiments(testABtoActive.getTestAB(), testABtoActive.getvToActive(), channel, app, driver);
			testAB.activateTestAB();
		}
	}
	
	@Override
	public int getVariant() {
		return (getVariantFromCookie(driver));
	}
	
	String getValueCookieResetingAllTestABvariants(WebDriver driver) {
		List<Integer> listVariantes = testAB.getVariantesInt();
		String valueCookie = getValueCookieGoogleExperiments(driver);
		if (valueCookie!=null) {
			for (Integer variante : listVariantes) {
				valueCookie = getValueCookieResetingTestABVariant(variante, valueCookie, driver);
			}
			return valueCookie;
		}
		return null;
	}
	
	private String getValueCookieResetingTestABVariant(int variante, String valueCookie, WebDriver driver) {
		if (existVariantInCookie(driver)) {
			String valueTestABvariant = getValueExpectedInCookie(testAB, variante);
			return (valueCookie.replaceAll(valueTestABvariant, ""));
		}
		
		return valueCookie;
	}
	
	
	private boolean existVariantInCookie(WebDriver driver) {
		return (getVariantFromCookie(driver)>=0 ? true : false);
	}
	
	/**
	 * @return id-integer variant if exists or -1 if doesn't exists
	 */
	public int getVariantFromCookie(WebDriver driver) {
		String valueCookie = getValueCookieGoogleExperiments(driver);
		if (valueCookie!=null) {
			List<Integer> listVariantes = testAB.getVariantesInt();
			for (Integer variante : listVariantes) {
				String valueTestABvariantExpected = getValueExpectedInCookie(testAB, variante);
				if (valueCookie.contains(valueTestABvariantExpected)) {
					return variante;
				}
			}
		}
		
		return -1;
	}
	
	String getValueExpectedInCookie(TestABid testAB, int variante) {
		return (testAB.getValueCookie(appTest) + "%3A" + variante + "%2C");
	}

	String getValueCookieGoogleExperiments(WebDriver driver) {
		Cookie cookieGoogleExperiments = driver.manage().getCookieNamed(nameCookieGoogleExperiments);
		if (cookieGoogleExperiments!=null) {
			return (cookieGoogleExperiments.getValue());
		}
		
		return null;
	}
	
	@Override
	public void activateRandomTestABInBrowser() throws Exception {
		int numVariantes = testAB.variantes.size();
		int variante = RandomNumber(0, numVariantes-1);
		activateTestAB(variante);
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
