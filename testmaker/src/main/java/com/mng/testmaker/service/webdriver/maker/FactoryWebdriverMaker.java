package com.mng.testmaker.service.webdriver.maker;

import com.mng.testmaker.domain.TestRunTestMaker;

public class FactoryWebdriverMaker {

    public enum WebDriverType { 
    	firefox(false), 
    	firefoxhless(true), 
    	chrome(false), 
    	chromehless(true),
    	edge(false),
    	safari(false),
    	browserstack(false);
    	
    	boolean headless;
    	private WebDriverType(boolean headless) {
    		this.headless = headless;
    	}
    	
    	public boolean isHeadless() { 
    		return headless;
    	}
    }	

    public static WebdriverMaker make(WebDriverType webDriverType, TestRunTestMaker testRun) {
    	switch (webDriverType) {
    	case firefox:
    		return (FirefoxdriverMaker.getNew(webDriverType));
    	case browserstack:
    		return (BrowserStackDriverMaker.getNew(testRun));
    	case edge:
    		return (EdgedriverMaker.getNew());
    	case chrome:
    	default:
    		return (ChromedriverMaker.getNew(webDriverType));
    	}
    }
}
