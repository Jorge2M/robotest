package com.mng.testmaker.utils;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.utils.webdriver.maker.FactoryWebdriverMaker.TypeWebDriver;

public class DataWebdriver {
    public WebDriver driver;
    public TypeWebDriver typeDriver;
    
    public DataWebdriver(WebDriver driver, TypeWebDriver typeDriver) {
        this.driver = driver;
        this.typeDriver = typeDriver;
    }
}
