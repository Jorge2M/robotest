package com.mng.testmaker.service.webdriver.maker;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;

import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.service.webdriver.maker.FactoryWebdriverMaker.WebDriverType;
import com.mng.testmaker.service.webdriver.maker.plugins.PluginBrowserFactory;
import com.mng.testmaker.service.webdriver.maker.plugins.chrome.PluginChrome;
import com.mng.testmaker.service.webdriver.maker.plugins.chrome.PluginChrome.typePluginChrome;

import io.github.bonigarcia.wdm.ChromeDriverManager;

class ChromedriverMaker implements WebdriverMaker {
	
    //Nota: si se modifica la versión sería conveniente regenerar la AMI correspondiente al Robotest en Cloud
	final static String ChromeDriverVersion = "77.0.3865.40";
	final WebDriverType webDriverType;
	boolean isHeadless;
	ChromeOptions options = new ChromeOptions();
	Channel channel = Channel.desktop;
	boolean nettraffic = false;
	
	private ChromedriverMaker(WebDriverType webDriverType) {
		this.webDriverType = webDriverType;
		isHeadless = webDriverType.isHeadless();
		initialConfig();
        setDriverChrome();
	}
	
	static ChromedriverMaker getNew(WebDriverType WebDriverType) {
		return (new ChromedriverMaker(WebDriverType));
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
	public WebDriver build() {
    	preBuildConfig();
        ChromeDriver driver = new ChromeDriver(options);
        if (channel==Channel.desktop) {
            driver.manage().window().maximize();
        }

        WebdriverMaker.deleteCookiesAndSetTimeouts(driver);
        return driver;
	}
    
	private void initialConfig() {
        options.addArguments("--ignore-certificate-errors");	
        options.addArguments("--no-proxy-server");
        activateLogs();
	}
	
	private void activateLogs() {
        LoggingPreferences logs = getLogsWebDriverEnabled();
        options.setCapability(CapabilityType.LOGGING_PREFS, logs);
        options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
	}

    private void setDriverChrome() {       
        ChromeDriverManager.chromedriver().version(ChromeDriverVersion).setup();
    }
	
	private boolean isDesktopInVirtualMachine() {
		if (channel!=Channel.movil_web) {
	        java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	        if (screenSize.height<=1024) {
	        	return true;
	        }
		}
		
		return false;
	}
	
    private void preBuildConfig() {
		if (isDesktopInVirtualMachine()) {
            //Esta ñapa es la única que se ha encontrado para solventar el problema del Chrome (no-headless) 
			//cuando se ejecuta contra una máquina virtual con un Tomcat as a Service. En ese contexto se abre un Chrome 
			//con resolución de 1024x768 y no hay forma de modificarlo. En cambio con headless funciona correctamente.  
            isHeadless=true;
		}
		if (channel!=Channel.movil_web && isHeadless) {
			options.addArguments("--window-size=1920x1080");
		}
		
        options.setHeadless(isHeadless);
        addPlugins();
        if (nettraffic) {
        	configNettrafficSnifer();
        }
        if (channel==Channel.movil_web) {
        	configMobilSimulator();
        }
    }
    
    /**
     * Da de alta/asocia en chrome una lista de plugins
     */
    private void addPlugins() {
    	if (!isHeadless) {
            //TODO Desde Chrome61 ya es posible desactivar el autoplay directamente desde el navegador (chrome://flags/#autoplay-policy)
            //options.addArguments("--autoplay-policy", "--document-user-activation-required");        
            //Arrancamos Chrome con la extensión HTML5Autoplay para que no se ejecute el autoplay de los vídeos y sea posible paralelizar varios Chromes en una misma máquina
            ArrayList<PluginChrome.typePluginChrome> listPlugins = new ArrayList<>();
            listPlugins.add(typePluginChrome.HTML5Autoplay);
	        for (typePluginChrome typePlugin : listPlugins) {
	            PluginChrome pluginChrome = PluginBrowserFactory.makePluginChrome(typePlugin);
	            pluginChrome.addPluginToChrome(options);
	        }
    	}
    }
    
    //TODO se puede pasar el código de generación del Proxy al interface
    private void configNettrafficSnifer() {
    	Proxy seleniumProxy = getProxyForNettraffic();
        options.setCapability(CapabilityType.PROXY, seleniumProxy);
    }
    
    private void configMobilSimulator() {
        Map<String, Object> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceName", "Nexus 6"); // select the device to emulate
        options.setExperimentalOption("mobileEmulation", mobileEmulation);
    }
}
