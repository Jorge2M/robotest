package com.mng.testmaker.utils;

import java.lang.reflect.Method;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;

import com.mng.testmaker.utils.conf.StorerErrorDataStepValidation;
import com.mng.testmaker.utils.webdriver.maker.FactoryWebdriverMaker.TypeWebDriver;

public class DataFmwkTest {

    public WebDriver driver;
    public TypeWebDriver typeDriver;
    public Method meth;
    public ITestContext ctx;
    public StorerErrorDataStepValidation storerDataError = null;
    
    public DataFmwkTest(WebDriver driver, TypeWebDriver typeDriver, Method methodI, ITestContext contextI) {
        this.driver = driver;
        this.typeDriver = typeDriver;
        this.meth = methodI;
        this.ctx = contextI;
    }
    
    public DataFmwkTest(WebDriver driver, TypeWebDriver typeDriver, ITestContext contextI) {
        this.driver = driver;
        this.typeDriver = typeDriver;
        this.meth = null;
        this.ctx = contextI;
    }    
    
    public void setStorerDataError(StorerErrorDataStepValidation storerDataError) {
    	this.storerDataError = storerDataError;
    }
}
