package com.mng.testmaker.utils.webdriver.maker;

import java.net.URL;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;

import com.mng.testmaker.data.TestMakerContext;
import com.mng.testmaker.utils.otras.Channel;
import com.mng.testmaker.utils.webdriver.BrowserStackDesktop;
import com.mng.testmaker.utils.webdriver.BrowserStackMobil;

public class BrowserStackDriverMaker implements WebdriverMaker {
	
	String buildProject;
	String sessionName;
	String userBStack;
	String passBStack;
	ITestContext ctx;
	Channel channel;
	boolean nettraffic = false;
	
	private BrowserStackDriverMaker(ITestContext ctx) {
		this.ctx = ctx;
    	TestMakerContext testMakerCtx = TestMakerContext.getTestMakerContext(ctx);
        buildProject = 
            ctx.getSuite().getName() + 
            " (" + testMakerCtx.getIdSuiteExecution() + ")";
        sessionName = ctx.getCurrentXmlTest().getName();
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
        	break;
        case desktop:
        default:
        	driver = createBStackDriverDesktop();
        	driver.manage().window().maximize();
        }
        
        WebdriverMaker.deleteCookiesAndSetTimeouts(driver);
        return driver;
	}
    
    private WebDriver createBStackDriverMobil() throws Exception {
        BrowserStackMobil bsStackMobil = TestMakerContext.getTestRun(ctx).getBrowserStackMobil();
        if (bsStackMobil==null) {
        	throw new RuntimeException("The data for connect with BrowserStack is not in the context");
        }
        
    	DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("os", bsStackMobil.getSo());
        capabilities.setCapability("os_version", bsStackMobil.getSoVersion());
        capabilities.setCapability("device", bsStackMobil.getDevice());
        capabilities.setCapability("realMobile", bsStackMobil.getRealMobil());
        capabilities.setCapability("browserName", bsStackMobil.getBrowser());
        return (runBrowserStack(bsStackMobil.getUser(), bsStackMobil.getPassword(), capabilities));
    }
    
    private WebDriver createBStackDriverDesktop() throws Exception {
    	BrowserStackDesktop bsStackDesktop = TestMakerContext.getTestRun(ctx).getBrowserStackDesktop();
        if (bsStackDesktop==null) {
        	throw new RuntimeException("The data for connect with BrowserStack is not in the context");
        }
        
    	DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("os", bsStackDesktop.getSo());
        capabilities.setCapability("os_version", bsStackDesktop.getSoVersion());
        capabilities.setCapability("browser", bsStackDesktop.getBrowser());
        capabilities.setCapability("browser_version", bsStackDesktop.getBrowserVersion());
        capabilities.setCapability("resolution", bsStackDesktop.getResolution());
        capabilities.setCapability("browserstack.use_w3c", true);
        return (runBrowserStack(bsStackDesktop.getUser(), bsStackDesktop.getPassword(), capabilities));
    }    
    
    private WebDriver runBrowserStack(String user, String password, DesiredCapabilities capabilities) 
    throws Exception {
        capabilities.setCapability("build", buildProject);
        capabilities.setCapability("name", sessionName);
        capabilities.setCapability("browserstack.debug", "false");
        capabilities.setCapability("browserstack.local", "false");
        return (new RemoteWebDriver(new URL("http://"+user+":"+password+"@hub-cloud.browserstack.com/wd/hub"), capabilities));
    }    
}
