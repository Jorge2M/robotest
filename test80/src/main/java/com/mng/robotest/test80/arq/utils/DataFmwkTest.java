package com.mng.robotest.test80.arq.utils;

import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;

import com.mng.robotest.test80.arq.utils.otras.Constantes.TypeDriver;

@SuppressWarnings("javadoc")
public class DataFmwkTest {

    public WebDriver driver;
    public TypeDriver typeDriver;
    public Method meth;
    public ITestContext ctx;
    
    public DataFmwkTest(DataWebdriver dataWebDrv, Method methodI, ITestContext contextI) {
        this.driver = dataWebDrv.driver;
        this.typeDriver = dataWebDrv.typeDriver;
        this.meth = methodI;
        this.ctx = contextI;
    }
    
    public DataFmwkTest(DataWebdriver dataWebDrv, ITestContext contextI) {
        this.driver = dataWebDrv.driver;
        this.typeDriver = dataWebDrv.typeDriver;
        this.meth = null;
        this.ctx = contextI;
    }    
}
