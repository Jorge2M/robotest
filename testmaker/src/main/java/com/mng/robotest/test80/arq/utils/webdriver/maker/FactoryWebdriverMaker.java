package com.mng.robotest.test80.arq.utils.webdriver.maker;

import org.testng.ITestContext;

public class FactoryWebdriverMaker {

    public enum TypeWebDriver { 
    	firefox(false), 
    	firefoxhless(true), 
    	chrome(false), 
    	chromehless(true),
    	edge(false),
    	browserstack(false);
    	
    	boolean headless;
    	private TypeWebDriver(boolean headless) {
    		this.headless = headless;
    	}
    	
    	public boolean isHeadless() {
    		return headless;
    	}
    }	

    public static WebdriverMaker make(TypeWebDriver typeWebDriver, ITestContext context) {
    	switch (typeWebDriver) {
    	case firefox:
    		return (FirefoxdriverMaker.getNew(typeWebDriver));
    	case browserstack:
    		return (BrowserStackDriverMaker.getNew(context));
    	case edge:
    		return (EdgedriverMaker.getNew());
    	case chrome:
    	default:
    		return (ChromedriverMaker.getNew(typeWebDriver));
    	}
    }
}
