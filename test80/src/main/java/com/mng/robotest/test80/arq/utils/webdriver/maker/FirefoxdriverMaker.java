package com.mng.robotest.test80.arq.utils.webdriver.maker;

import java.awt.Toolkit;
import io.github.bonigarcia.wdm.FirefoxDriverManager;

import com.mng.robotest.test80.arq.utils.webdriver.maker.FactoryWebdriverMaker.TypeWebDriver;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;

public class FirefoxdriverMaker implements WebdriverMaker {
	
    //Nota: si se modifica la versión sería conveniente regenerar la AMI correspondiente al Robotest en Cloud
	final static String GeckoDriverVersion = "0.24.0";
	TypeWebDriver typeWebDriver;
	FirefoxProfile fp = new FirefoxProfile();
	FirefoxOptions options;
	Channel channel = Channel.desktop;
	boolean nettraffic = false;
	
	private FirefoxdriverMaker(TypeWebDriver typeWebDriver) {
		this.typeWebDriver = typeWebDriver;
		initialConfig();
        setDriverFirefox();
	}
	
	public static FirefoxdriverMaker getNew(TypeWebDriver typeWebDriver) {
		return (new FirefoxdriverMaker(typeWebDriver));
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
    	preBuildConfig();
    	FirefoxDriver driver = new FirefoxDriver(options);
    	resizeBrowserIfNeeded(driver);
        WebdriverMaker.deleteCookiesAndSetTimeouts(driver);
        return driver;
	}
    
	private void initialConfig() {
	    fp.setPreference("network.proxy.type", 0); // Sin proxy (1 -> Con proxy de sistema)
	    fp.setPreference("toolkit.startup.max_resumed_crashes", -1); // Desactivar el Safe Mode
	    fp.setPreference("browser.sessionstore.postdata", -1); // Desactivar el "Document Expired"
	    fp.setPreference("browser.cache.disk.enable", true);
	    fp.setPreference("browser.cache.memory.enable", true);
	    fp.setPreference("browser.cache.offline.enable", true);
	    fp.setPreference("network.http.use-cache", true);
	    fp.setPreference("xpinstall.signatures.required", false);
	    fp.setPreference("xpinstall.whitelist.required", false);
	    fp.setPreference("extensions.checkCompatibility.nightly", false);
	    fp.setPreference("startup.homepage_welcome_url.additional", "");
	    fp.setPreference("startup.homepage_welcome_url", "");
	    setAutoplayOff();
	}
	
	private void setAutoplayOff() {
	    fp.setPreference("media.autoplay.default", 1); 
	    fp.setPreference("media.autoplay.allow-muted", false);
	    fp.setPreference("media.autoplay.enabled", false);
	    fp.setPreference("media.ogg.enabled", false); 
	    fp.setPreference("media.webm.enabled", false); 
	    fp.setPreference("media.windows-media-foundation.enabled", false); 
	}
	    
    private static void setDriverFirefox() {        
        FirefoxDriverManager.getInstance().version(GeckoDriverVersion).setup();
    }
	
	private void activateLogs() {
        LoggingPreferences logs = getLogsWebDriverEnabled();
        options.setCapability(CapabilityType.LOGGING_PREFS, logs);
        options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
	}
	
    private void preBuildConfig() throws Exception {
    	if (channel==Channel.movil_web) {
    		configMobilSimulator();
    	}
    	setFirefoxOptions();
		activateLogs();
    	activeLogs();
        if (nettraffic) {
        	configNettrafficSnifer();
        }
    }
    
    private void setFirefoxOptions() {
        //Con FirefoxBinary intentamos eliminar el tema de la excepción
        //"org.openqa.selenium.WebDriverException: Unable to bind to locking port 7054 within 45000 ms" -> Aumentamos de 45 a 60 segundos
        FirefoxBinary fb = new FirefoxBinary();  
        fb.setTimeout(java.util.concurrent.TimeUnit.SECONDS.toMillis(60));
        options = new FirefoxOptions().setBinary(fb);
        options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, "true");
        options.setCapability("acceptSslCerts", true);
        options.setCapability(FirefoxDriver.PROFILE, fp);
        options.setProfile(fp);
        options.setCapability("marionette", true);
        System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, String.valueOf(true));
        options.setHeadless(typeWebDriver.isHeadless());
    }
    
    private void activeLogs() {
        LoggingPreferences logs = getLogsWebDriverEnabled();
        options.setCapability(CapabilityType.LOGGING_PREFS, logs);
    }    
    
    private void configNettrafficSnifer() {
    	Proxy seleniumProxy = getProxyForNettraffic();
    	options.setCapability(CapabilityType.PROXY, seleniumProxy);
    }
    
    private void configMobilSimulator() {
        fp.setPreference(
        	"general.useragent.override", 
        	"Mozilla/5.0 (Linux; Android 4.0.4; Galaxy Nexus Build/IMM76B) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.133 Mobile Safari/535.19");
    }
    
    private void resizeBrowserIfNeeded(WebDriver driver) {
        if (channel==Channel.movil_web) {
            //En caso de móvil redimensionaremos el navegador a unas dimensiones tipo 'móvil'
            driver.manage().window().setSize(new Dimension(640, 1136));
        } else {
            //En caso de Desktop maximizamos la ventana
            driver.manage().window().maximize();
            java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            if (screenSize.height<=1024) {
            	driver.manage().window().setSize(new Dimension(1920, 1080));
            }
        }
    }
}
