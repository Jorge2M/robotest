package com.mng.testmaker.utils;

import java.lang.reflect.Method;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;

import com.mng.testmaker.service.webdriver.maker.FactoryWebdriverMaker.WebDriverType;
import com.mng.testmaker.utils.conf.StorerErrorDataStepValidation;

public class DataFmwkTest {

    public WebDriver driver;
    public WebDriverType webDriverType;
    public Method meth;
    public ITestContext ctx;
    public StorerErrorDataStepValidation storerDataError = null;
    
    public DataFmwkTest(WebDriver driver, WebDriverType webDriverType, Method methodI, ITestContext contextI) {
        this.driver = driver;
        this.webDriverType = webDriverType;
        this.meth = methodI;
        this.ctx = contextI;
    }
    
    public DataFmwkTest(WebDriver driver, WebDriverType webDriverType, ITestContext contextI) {
        this.driver = driver;
        this.webDriverType = webDriverType;
        this.meth = null;
        this.ctx = contextI;
    }    
    
    public void setStorerDataError(StorerErrorDataStepValidation storerDataError) {
    	this.storerDataError = storerDataError;
    }
}
