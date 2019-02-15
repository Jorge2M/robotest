package com.mng.robotest.test80.arq.utils.selenium;

import java.util.Map;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;


public class BStackDataMovil {

    //Nombre de los parámetros en la llamada a BrowserStack
    public static String os_paramname = "os"; 
    public static String os_version_paramname = "os_version"; 
    public static String device_paramname = "device";
    public static String realmovile_paramname = "realMobile";
    public static String browser_paramname = "browserName";
            
    public String os; //p.e. "Android"
    public String os_version; //p.e. "7.0"
    public String device; //p.e. "Samsung Galaxy S8"
    public String realMobile; //p.e. "true"
    public String browserName; //p.e. "Chrome"
    
    public BStackDataMovil() {}
    
    public BStackDataMovil(String os, String os_version, String device, String realMobile, String browserName) {
        this.os = os;
        this.os_version = os_version;
        this.device = device;        
        this.browserName = browserName;
        this.realMobile = realMobile;
    }
    
    /**
     * Get data from parameters TestNG
     */
    public BStackDataMovil(ITestContext ctx) {
        this.os = ctx.getCurrentXmlTest().getParameter(os_paramname);
        this.os_version = ctx.getCurrentXmlTest().getParameter(os_version_paramname);
        this.device = ctx.getCurrentXmlTest().getParameter(device_paramname);
        this.realMobile = ctx.getCurrentXmlTest().getParameter(realmovile_paramname);
        this.browserName = ctx.getCurrentXmlTest().getParameter(browser_paramname);
    }
    
    /**
     * Set data to parameters TestNG
     */
    public void setParameters(Map<String, String> parametersTest) {
        parametersTest.put(os_paramname, this.os);
        parametersTest.put(os_version_paramname, this.os_version);
        parametersTest.put(device_paramname, this.device);
        parametersTest.put(realmovile_paramname, this.realMobile);
        parametersTest.put(browser_paramname, this.browserName);
    }
    
    public void setCapabilities(DesiredCapabilities capabilities) {
        capabilities.setCapability(os_paramname, this.os);
        capabilities.setCapability(os_version_paramname, this.os_version);
        capabilities.setCapability(device_paramname, this.device);
        capabilities.setCapability(realmovile_paramname, this.realMobile);
        capabilities.setCapability(browser_paramname, this.browserName);
    }
}
