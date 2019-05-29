package com.mng.robotest.test80.arq.utils.webdriver.maker;


import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.arq.utils.otras.Constantes;
import com.mng.robotest.test80.arq.utils.webdriver.BStackDataDesktop;
import com.mng.robotest.test80.arq.utils.webdriver.BStackDataMovil;

import java.net.URL;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;

public class BrowserStackDriverMaker implements WebdriverMaker {
	
	String buildProject;
	String sessionName;
	String userBStack;
	String passBStack;
	ITestContext tContext;
	DesiredCapabilities capabilities = new DesiredCapabilities();
	Channel channel = Channel.desktop;
	boolean nettraffic = false;
	
	private BrowserStackDriverMaker(ITestContext tContext) {
		this.tContext = tContext;
        buildProject = 
            tContext.getSuite().getName() + 
            " (" + tContext.getCurrentXmlTest().getParameter(Constantes.paramSuiteExecInCtx) + ")";
        sessionName = tContext.getCurrentXmlTest().getName();
        userBStack = tContext.getCurrentXmlTest().getParameter(Constantes.UserBStack);
        passBStack = tContext.getCurrentXmlTest().getParameter(Constantes.PassBStack);
	}
	
	public static BrowserStackDriverMaker getNew(ITestContext tContext) {
		return (new BrowserStackDriverMaker(tContext));
	}
	
    @Override
	public WebdriverMaker setChannel(Channel channel) {
		this.channel = channel;
		return this;
	}
    
    @Override
	public WebdriverMaker setNettraffic(boolean nettraffic) {
    	this.nettraffic = nettraffic;
    	return this;
    }

    @Override
	public WebDriver build() throws Exception {
    	WebDriver driver;
        switch (channel) {
        case movil_web: 
        	driver = createBStackDriverMobil();
        case desktop:
        default:
        	driver = createBStackDriverDesktop();
        	driver.manage().window().maximize();
        }
        
        WebdriverMaker.deleteCookiesAndSetTimeouts(driver);
        return driver;
	}
    
    private WebDriver createBStackDriverMobil() throws Exception {
        BStackDataMovil bsDataMovil = new BStackDataMovil(tContext);
        bsDataMovil.setCapabilities(capabilities);
        return (runBrowserStack());
    }
    
    private WebDriver createBStackDriverDesktop() throws Exception {
        BStackDataDesktop bsDataDesktop = new BStackDataDesktop(tContext);
        bsDataDesktop.setCapabilities(capabilities);
        return (runBrowserStack());
    }    
    
    private WebDriver runBrowserStack() 
    throws Exception {
        capabilities.setCapability("build", buildProject);
        capabilities.setCapability("name", sessionName);
        capabilities.setCapability("browserstack.debug", "false");
        capabilities.setCapability("browserstack.local", "false");
        return (new RemoteWebDriver(new URL("http://"+userBStack+":"+passBStack+"@hub-cloud.browserstack.com/wd/hub"), capabilities));
    }    
}
