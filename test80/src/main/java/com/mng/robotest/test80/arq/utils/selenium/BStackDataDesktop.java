package com.mng.robotest.test80.arq.utils.selenium;

import java.util.Map;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;

public class BStackDataDesktop {

    //Nombre de los parámetros en la llamada a BrowserStack
    public static String os_paramname = "os"; 
    public static String os_version_paramname = "os_version"; 
    public static String browser_paramname = "browser";
    public static String browser_version_paramname = "browser_version";
    public static String resolution_paramname = "resolution";
    public static String use_w3c_paramname = "browserstack.use_w3c";

            
    public String os; //p.e. "Windows"
    public String os_version; //p.e. "10"
    public String browser; //p.e. "Chrome"
    public String browser_version; //p.e. "62.0"
    public String resolution; //p.e. "1024x768"
    public boolean use_w3c = true;
    
    public BStackDataDesktop() {}
    
    public BStackDataDesktop(String os, String os_version, String browser, String browserVersion, String resolution) {
        this.os = os;
        this.os_version = os_version;
        this.browser = browser;        
        this.browser_version = browserVersion;
        this.resolution = resolution;
    }
    
    /**
     * Get data from parameters TestNG
     */
    public BStackDataDesktop(ITestContext ctx) {
        this.os = ctx.getCurrentXmlTest().getParameter(os_paramname);
        this.os_version = ctx.getCurrentXmlTest().getParameter(os_version_paramname);
        this.browser = ctx.getCurrentXmlTest().getParameter(browser_paramname);
        this.browser_version = ctx.getCurrentXmlTest().getParameter(browser_version_paramname);
        this.resolution = ctx.getCurrentXmlTest().getParameter(resolution_paramname);
    }
    
    /**
     * Set data to parameters TestNG
     */
    public void setParameters(Map<String, String> parametersTest) {
        parametersTest.put(os_paramname, this.os);
        parametersTest.put(os_version_paramname, this.os_version);
        parametersTest.put(browser_paramname, this.browser);
        parametersTest.put(browser_version_paramname, this.browser_version);
        parametersTest.put(resolution_paramname, this.resolution);
    }
    
    public void setCapabilities(DesiredCapabilities capabilities) {
        capabilities.setCapability(os_paramname, this.os);
        capabilities.setCapability(os_version_paramname, this.os_version);
        capabilities.setCapability(browser_paramname, this.browser);
        capabilities.setCapability(browser_version_paramname, this.browser_version);
        capabilities.setCapability(resolution_paramname, this.resolution);
        capabilities.setCapability(use_w3c_paramname, this.use_w3c);
    }
}
