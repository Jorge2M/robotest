package com.mng.robotest.test80.arq.utils;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.webdriver.maker.FactoryWebdriverMaker.TypeWebDriver;

public class DataWebdriver {
    public WebDriver driver;
    public TypeWebDriver typeDriver;
    
    public DataWebdriver(WebDriver driver, TypeWebDriver typeDriver) {
        this.driver = driver;
        this.typeDriver = typeDriver;
    }
}
