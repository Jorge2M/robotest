package com.mng.robotest.test80.mango.test.utils.testab;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.AppEcom;

public class TestABGoogleExperiments implements TestAB {
    
	public final static String nameCookieGoogleExperiments = "googleexperiments";
    public TestABid testAB;
	public int varianteActivada = 0;
	public AppEcom app;
	
	public TestABGoogleExperiments(TestABid testAB, AppEcom app) {
		this.testAB = testAB;
		this.app = app;
	}
	
	public TestABGoogleExperiments(TestABid testAB, AppEcom app, int variante) {
		this.testAB = testAB;
		this.varianteActivada = variante;
		this.app = app;
	}
	
	public TestABGoogleExperiments(String paramWithPointSeparator, AppEcom app) {
		int posPoint = paramWithPointSeparator.indexOf(".");
	    String idTestAB = paramWithPointSeparator.substring(0, posPoint);
	    int variante = Integer.valueOf(paramWithPointSeparator.substring(posPoint + 1));
	    this.testAB = TestABid.valueOf(idTestAB);
	    this.varianteActivada = variante;
	    this.app = app;
	}
	
	@Override
	public void activateTestAB(int variante, WebDriver driver) throws Exception {
        this.varianteActivada = variante;
        activateTestAB(driver);
	}
	
	@Override
	public void activateTestAB(WebDriver driver) throws Exception {
		String valueCookieRemovingTestAB = getValueCookieResetingAllTestABvariants(driver);
		if (valueCookieRemovingTestAB!=null) { 
	        String testABvalueForVariant = testAB.getValueCookie(app) + "%3A" + varianteActivada + "%2C";
	        String newValueCookie = valueCookieRemovingTestAB + testABvalueForVariant;
	        Cookie actualCookie = driver.manage().getCookieNamed(nameCookieGoogleExperiments);
	        Cookie newCookie = getClonedWithNewValue(actualCookie, newValueCookie);
	        driver.manage().deleteCookieNamed(nameCookieGoogleExperiments);
	        driver.manage().addCookie(newCookie);
		}
	}
	
	private Cookie getClonedWithNewValue(Cookie actualCookie, String newValue) {
        Map<String,Object> jsonCookie = actualCookie.toJson();
        Cookie newCookie = 
        	new Cookie(
        		(String)jsonCookie.get("name"), 
        		newValue, 
        		(String)jsonCookie.get("domain"), 
        		(String)jsonCookie.get("path"), 
        		(Date)jsonCookie.get("expiry"), 
        		(boolean)jsonCookie.get("secure"), 
        		(boolean)jsonCookie.get("httpOnly"));
        return newCookie;
	}
	
	@Override
	public int getVariant(WebDriver driver) {
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
		return (testAB.getValueCookie(app) + "%3A" + variante + "%2C");
	}

	
	String getValueCookieGoogleExperiments(WebDriver driver) {
		Cookie cookieGoogleExperiments = driver.manage().getCookieNamed(nameCookieGoogleExperiments);
		if (cookieGoogleExperiments!=null) {
			return (cookieGoogleExperiments.getValue());
		}
		
		return null;
	}
	
	@Override
	public void activateRandomTestABInBrowser(WebDriver driver) throws Exception {
		int numVariantes = testAB.variantes.size();
		int variante = RandomNumber(0, numVariantes-1);
		activateTestAB(variante, driver);
	}
	
	private int RandomNumber(int minimo, int maximo) {
		Random random = new Random();
		return (random.nextInt(maximo - minimo + 1) + minimo);
	}

}
