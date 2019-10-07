package com.mng.testmaker.utils;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.service.webdriver.maker.FactoryWebdriverMaker.WebDriverType;

public class DataWebdriver {
    public WebDriver driver;
    public WebDriverType webDriverType;
    
    public DataWebdriver(WebDriver driver, WebDriverType webDriverType) {
        this.driver = driver;
        this.webDriverType = webDriverType;
    }
}
