package com.mng.robotest.test80.arq.utils;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.utils.otras.Constantes.TypeDriver;

@SuppressWarnings("javadoc")
public class DataWebdriver {
    public WebDriver driver;
    public TypeDriver typeDriver;
    
    public DataWebdriver(WebDriver driver, TypeDriver typeDriver) {
        this.driver = driver;
        this.typeDriver = typeDriver;
    }
}
