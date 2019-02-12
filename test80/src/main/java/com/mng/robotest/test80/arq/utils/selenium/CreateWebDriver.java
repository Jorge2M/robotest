package com.mng.robotest.test80.arq.utils.selenium;

//import io.appium.java_client.android.AndroidDriver;
//import io.appium.java_client.remote.MobileCapabilityType;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;
import net.lightbody.bmp.client.ClientUtil;

import java.awt.Toolkit;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;

import com.mng.robotest.test80.arq.utils.NetTrafficMng;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.arq.utils.otras.Constantes;
import com.mng.robotest.test80.arq.utils.otras.Constantes.TypeDriver;
import com.mng.robotest.test80.arq.utils.selenium.plugins.PluginBrowserFactory;
import com.mng.robotest.test80.arq.utils.selenium.plugins.chrome.PluginChrome;
import com.mng.robotest.test80.arq.utils.selenium.plugins.chrome.PluginChrome.typePluginChrome;
import com.mng.robotest.test80.arq.utils.selenium.plugins.firefox.PluginFirefox;
import com.mng.robotest.test80.arq.utils.selenium.plugins.firefox.PluginFirefox.typePluginFirefox;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;

@SuppressWarnings("javadoc")
public class CreateWebDriver {
    
    static Logger pLogger = LogManager.getLogger(fmwkTest.log4jLogger);
    
    /**
     * Crea y configura un driver de selenium (WebDriver) de un determinado tipo en base al parámetro bpath y otros
     * 
     * @param bpath parámetro que especficiará el tipo de webdriver (firefox, chrome, appium...)
     * @param isMobil identifica si se ha de arrancar un WebDriver compatible con scripts de tipo 'móvil' o 'desktop'
     * @param netAnalysis define si será preciso exportar el NetTraffic
     */    
    public static WebDriver getWebDriver(final TypeDriver canalWebDriver, final Channel channel, final boolean netAnalysis, final ITestContext context, final Method method) 
    throws Exception {
        WebDriver webdriver = null;
        switch (canalWebDriver) {
        case firefox:
            webdriver = CreateWebDriver.createFirefoxDriver(channel, netAnalysis, false/*headless*/, true/*isMarionette*/, method, context);
            break;            
        case firefoxhless:
            webdriver = CreateWebDriver.createFirefoxDriver(channel, netAnalysis, true/*headless*/, true/*isMarionette*/, method, context);
            break;            
        case chrome:
            webdriver = CreateWebDriver.createChromeDriver(channel, netAnalysis, false/*headless*/);
            break;
        case chromehless:            
            webdriver = CreateWebDriver.createChromeDriver(channel, netAnalysis, true/*headless*/);
            break;            
        case explorer: 
            webdriver = CreateWebDriver.createExplorerDriver();
            break;
//        case appium:
//            webdriver = CreateWebDriver.createAppiumAndroidDriver();
//            break;
//        case phantomjs:
//            webdriver = CreateWebDriver.createPhantomJSDriver(); 
//            break;
//        case htmlunit:
//            webdriver = CreateWebDriver.createHtmlUnitDriver();
//            break;
        case browserstack:
            webdriver = createBStackDriver(channel, context);
            //webdriver.manage().window().maximize();
            break;
         default:
             break;
        }            
        
        if (webdriver!=null) {
            //Borramos las cookies
            webdriver.manage().deleteAllCookies();
            
            //Establecemos los timeouts
            webdriver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
            webdriver.manage().timeouts().setScriptTimeout(5, TimeUnit.SECONDS);
            webdriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        }

        return webdriver;
    }
    
    /**
     * Crea un driver de selenium de un determinado tipo en base al parámetro bpath
     * @param bpath parámetro que especficiará el tipo de webdriver (firefox, chrome, appium...)
     */
    public static WebDriver getWebDriver(final TypeDriver canalWebDriver) throws Exception {
        return CreateWebDriver.getWebDriver(canalWebDriver, Channel.desktop, false /*netAnalysis*/, null, null);
    }    
    
    /**
     * Creación del WebDriver correspondiente a Firefox
     * 
     * @param isMarionette indica si el driver utilizará GekoDriver
     * @param isMobil indica si queremos simular la ejecución de móvil
     * @param netAnalysis indica si queremos análisis del tráfico de red (con lo que activaríamos el plugin NetExport para poder expotrar dicho tráfico)
     * @return webdriver de Firefox (FirefoxDriver)
     */
    private static FirefoxDriver createFirefoxDriver(Channel channel, boolean netAnalysis, boolean headless, boolean isMarionette, Method method, ITestContext context) 
    throws Exception {

        FirefoxProfile fp = new FirefoxProfile();
        
        //Configuración del Navegador / Webdriver
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
        
        //Si es precisa la ejecución contra móvil lo simularemos mediante la definición de un UserAgent de Android (y finalmente redimensionamiento del navegador a unas dimensiones de 'móvil')
        if (channel==Channel.movil_web) {
            //Simulamos el acceso desde un móbil Android / Chrome
            fp.setPreference("general.useragent.override", "Mozilla/5.0 (Linux; Android 4.0.4; Galaxy Nexus Build/IMM76B) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.133 Mobile Safari/535.19");
        }
        
        // Con esto no esperamos a que la página cargue completamente (si tarda más de 30 segundos (implicitlyWait)) y seguimos operando
        // (Problemas con Selenium 2.46)
        // fp.setPreference("webdriver.load.strategy", "unstable");
        fp.setPreference("media.autoplay.enabled", false); // Con esto desactivamos la visualización de los videos
        fp.setPreference("media.ogg.enabled", false); // Con esto desactivamos la visualización de los videos
        fp.setPreference("media.webm.enabled", false); // Con esto desactivamos la visualización de los videos
        fp.setPreference("media.windows-media-foundation.enabled", false); // Con esto desactivamos la visualización de los videos

        /*
         * String PROXY = "proxy.ext.pro.mango.com:3128"; org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy(); proxy.setHttpProxy(PROXY) .setFtpProxy(PROXY)
         * .setSslProxy(PROXY); dc.setCapability(CapabilityType.PROXY, proxy);
         */

        //Con FirefoxBinary intentamos eliminar el tema de la excepción
        //"org.openqa.selenium.WebDriverException: Unable to bind to locking port 7054 within 45000 ms" -> Aumentamos de 45 a 60 segundos
        FirefoxBinary fb = new FirefoxBinary();  
        fb.setTimeout(java.util.concurrent.TimeUnit.SECONDS.toMillis(60));

        //Creación del WebDriver de Firefox
        FirefoxOptions options = new FirefoxOptions().setBinary(fb);
        
        if (netAnalysis) {
            //Start a Proxy for NetAnalysis
        	new NetTrafficMng();
            Proxy seleniumProxy = ClientUtil.createSeleniumProxy(NetTrafficMng.getProxy());
            options.setCapability(CapabilityType.PROXY, seleniumProxy);
        }
        
        options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, "true");
        options.setCapability("acceptSslCerts", true);
        options.setCapability(FirefoxDriver.PROFILE, fp);
        options.setProfile(fp);
        options.setHeadless(headless);
        CreateWebDriver.activeLogsInWebDriver(options);
        
        setDriverFirefox();
        options.setCapability("marionette", isMarionette);
        System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, String.valueOf(isMarionette));
        
        FirefoxDriver firefoxDriver = new FirefoxDriver(options);

        //Indicamos que el WebDriver tiene interface gráfica
        if (context != null)
            context.setAttribute("browserGUI", Boolean.valueOf(true));

        if (channel==Channel.movil_web)
            //En caso de móvil redimensionaremos el navegador a unas dimensiones tipo 'móvil'
            firefoxDriver.manage().window().setSize(new Dimension(640, 1136));
        else {
            //En caso de Desktop maximizamos la ventana
            firefoxDriver.manage().window().maximize();
            java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            if (screenSize.height<=1024)
            	firefoxDriver.manage().window().setSize(new Dimension(1920, 1080));
        }
        
        return firefoxDriver;
    }
    
    
    /**
     * Creación del WebDriver correspondiente a Chrome
     * @return webdriver de Chrome (ChromeDriver)
     */
    private static ChromeDriver createChromeDriver(Channel channel, boolean netAnalysis, boolean headless) throws Exception {
        boolean headlessToUse = headless;
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--ignore-certificate-errors");
        
        //Activamos los logs a nivel de WebDriver
        LoggingPreferences logs = getLogsWebDriverEnabled();
        options.setCapability(CapabilityType.LOGGING_PREFS, logs);
        options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        
        //Definimos el chromedriver.exe correspondiente a chrome
        CreateWebDriver.setDriverChrome();

        if (channel!=Channel.movil_web) {
            java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            if (screenSize.height<=1024)
                //Esta ñapa es la única que se ha encontrado para solventar el problema del Chrome (no-headless) cuando se ejecuta contra una máquina virtual con un Tomcat as a Service
                //En ese contexto se abre un Chrome con resolución de 1024x768 y no hay forma de modificarlo. En cambio con headless funciona correctamente.  
                headlessToUse=true;
            
            if (headlessToUse)
                options.addArguments("--window-size=1920x1080");
        }
        
        //Configuración del Navegador / Webdriver
        options.addArguments("--no-proxy-server");
        options.setHeadless(headlessToUse);
        if (!headlessToUse) {
            //TODO Desde Chrome61 ya es posible desactivar el autoplay directamente desde el navegador (chrome://flags/#autoplay-policy)
            //options.addArguments("--autoplay-policy", "--document-user-activation-required");        
            //Arrancamos Chrome con la extensión HTML5Autoplay para que no se ejecute el autoplay de los vídeos y sea posible paralelizar varios Chromes en una misma máquina 
            ArrayList<PluginChrome.typePluginChrome> listPlugins = new ArrayList<>();
            listPlugins.add(typePluginChrome.HTML5Autoplay);
            addPluginsChrome(options, listPlugins);
        }
        
        if (netAnalysis) {
            //Start a Proxy for NetAnalysis
        	new NetTrafficMng();
            Proxy seleniumProxy = ClientUtil.createSeleniumProxy(NetTrafficMng.getProxy());
            options.setCapability(CapabilityType.PROXY, seleniumProxy);
        }
        
        //En caso de querer ejecutar contra móvil lo simularemos mediante la opción que nos proporciona un Chrome-Desktop
        if (channel==Channel.movil_web) {
            Map<String, Object> mobileEmulation = new HashMap<>();
            mobileEmulation.put("deviceName", "Nexus 6"); // select the device to emulate
            options.setExperimentalOption("mobileEmulation", mobileEmulation);
        }

        ChromeDriver chromeDriver = new ChromeDriver(options);
        if (channel==Channel.desktop) {
            chromeDriver.manage().window().maximize();
        }

        return chromeDriver;
    }

    /**
     * Da de alta/asocia en chrome una lista de plugins
     */
    protected static void addPluginsChrome(ChromeOptions options, ArrayList<typePluginChrome> listPlugins) throws Exception {
        for (typePluginChrome typePlugin : listPlugins) {
            PluginChrome pluginChrome = PluginBrowserFactory.makePluginChrome(typePlugin);
            pluginChrome.addPluginToChrome(options);
        }
    }
    
    /**
     * Da de alta/asocia en firefox una lista de plugins
     */
    protected static void addPluginsFirefox(FirefoxProfile firefoxProfile, ArrayList<typePluginFirefox> listPlugins) throws Exception {
        for (PluginFirefox.typePluginFirefox typeExtension : listPlugins) {
            PluginFirefox pluginFirefox = PluginBrowserFactory.makePluginFirefox(typeExtension);
            pluginFirefox.addPluginToFirefox(firefoxProfile);
        }
    }    
    
    /**
     * Creación de un WebDriver enlazando con BrowserStack en versión Desktop
     */
    public static WebDriver createBStackWebdriver(String username, String accessKey, String BuildProject, String SessionDevice, BStackDesktop dataDesktop) throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        
        //Datos Desktop necesarios a nivel de BrowserStack
        capabilities.setCapability("os", dataDesktop.getOs());
        capabilities.setCapability("os_version", dataDesktop.getOs_version());
        capabilities.setCapability("browser", dataDesktop.getBrowser());
        capabilities.setCapability("browser_version", dataDesktop.getBrowser_version());
        capabilities.setCapability("resolution", dataDesktop.getResolution());
        
        //Enlazamos con BrowserStack y obtenemos el WebDriver
        return (runBrowserStack(username, accessKey, BuildProject, SessionDevice, capabilities));
    }
    
    public static WebDriver createBStackDriver(Channel channel, ITestContext ctx) throws Exception {
        String buildProject = ctx.getSuite().getName() + " (" + ctx.getCurrentXmlTest().getParameter(Constantes.paramSuiteExecInCtx) + ")";
        String sessionName = ctx.getCurrentXmlTest().getName();
        String userBStack = ctx.getCurrentXmlTest().getParameter(Constantes.UserBStack);
        String passBStack = ctx.getCurrentXmlTest().getParameter(Constantes.PassBStack);
        WebDriver driver;
        switch (channel) {
        case movil_web: 
        	driver = createBStackDriverMobil(userBStack, passBStack, buildProject, sessionName, ctx);
        case desktop:
        default:
        	driver = createBStackDriverDesktop(userBStack, passBStack, buildProject, sessionName, ctx);
        	driver.manage().window().maximize();
        }
        
        return driver;
    }
    
    /**
     * Creación de un WebDriver enlazando con BrowserStack en versión móvil
     */
    public static WebDriver createBStackDriverMobil(String username, String accessKey, String BuildProject, String SessionDevice, ITestContext ctx) 
    throws Exception {
        BStackDataMovil bsDataMovil = new BStackDataMovil(ctx);
        DesiredCapabilities capabilities = new DesiredCapabilities();
        bsDataMovil.setCapabilities(capabilities);
        
        //Enlazamos con BrowserStack y obtenemos el WebDriver
        return (runBrowserStack(username, accessKey, BuildProject, SessionDevice, capabilities));
    }
    
    /**
     * Creación de un WebDriver enlazando con BrowserStack en versión móvil
     */
    public static WebDriver createBStackDriverDesktop(String username, String accessKey, String BuildProject, String SessionDevice, ITestContext ctx) 
    throws Exception {
        BStackDataDesktop bsDataDesktop = new BStackDataDesktop(ctx);
        DesiredCapabilities capabilities = new DesiredCapabilities();
        bsDataDesktop.setCapabilities(capabilities);
        
        //Enlazamos con BrowserStack y obtenemos el WebDriver
        return (runBrowserStack(username, accessKey, BuildProject, SessionDevice, capabilities));
    }    
    
    /**
     * @param username usuario identificación BrowserStack
     * @param accessKey password identificación BrowserStack
     */
    private static WebDriver runBrowserStack(String username, String accessKey, String BuildProject, String SessionDevice, DesiredCapabilities capabilities) 
    throws Exception {
        //Datos comunes
        capabilities.setCapability("build", BuildProject);
        capabilities.setCapability("name", SessionDevice);
        capabilities.setCapability("browserstack.debug", "false");
        capabilities.setCapability("browserstack.local", "false");
        
        //De momento no necesitamos la prueba en local 
//      if(capabilities.getCapability("browserstack.local") != null && capabilities.getCapability("browserstack.local") == "true"){
//          Map<String, String> options = new HashMap<String, String>();
//          options.put("key", accessKey);
//      }
        
        return (new RemoteWebDriver(new URL("http://"+username+":"+accessKey+"@hub-cloud.browserstack.com/wd/hub"), capabilities));
    }    
    
    /**
     * Creación del WebDriver correspondiente a Explorer 
     * @return webdriver de Explorer (InternetExplorerDriver)
     */
    private static InternetExplorerDriver createExplorerDriver() {
    	InternetExplorerOptions options = new InternetExplorerOptions();
    	
        //Activamos los logs a nivel de WebDriver
        LoggingPreferences logs = getLogsWebDriverEnabled();
        options.setCapability(CapabilityType.LOGGING_PREFS, logs);
        
        //Definimos el IEDriverServer.exe correspondiente a explorer
        CreateWebDriver.setDriverExplorer();
        
        //Configuración del Navegador / WebDriver
        options.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
        options.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        
        //Creación del WebDriver
        InternetExplorerDriver explorerDriver = new InternetExplorerDriver(options);
        
        //Maximizamos la ventana
        explorerDriver.manage().window().maximize();
        
        return explorerDriver;
    }
    
//    /**
//     * Creación vía Appium del WebDriver correspondiente a Android 
//     * @return webdriver de Android (AndroidDriver)
//     */    
//    private static AndroidDriver<WebElement> createAppiumAndroidDriver() {
//        // Create object of  DesiredCapabilities class and specify android platform
//        DesiredCapabilities capabilities=DesiredCapabilities.android();
//
//        // set the capability to execute test in chrome browser
//        capabilities.setCapability(CapabilityType.BROWSER_NAME,BrowserType.CHROME);
//
//        //Definimos el chromedriver.exe correspondiente a chrome
//        //CreateWebDriver.setDriverChrome();
//        capabilities.setCapability("chromedriverExecutable", "C:/Users/00556106/.m2/repository/webdriver/chromedriver/win32/2.33/chromedriver.exe");
//         
//        // set the capability to execute our test in Android Platform
//        capabilities.setCapability(CapabilityType.PLATFORM,Platform.ANDROID);
//         
//        // we need to define platform name
//        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME,"Android");
//         
//        // Set the device name as well (you can give any name)
//        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME,"ZX1D22JPJX"); //Motorola MotoG Jorge
//        //capabilities.setCapability(MobileCapabilityType.DEVICE_NAME,"0816087BC3333304");
//
//        // set the android version as well 
//        capabilities.setCapability(CapabilityType.VERSION,"6.0.1");
//         
//        // Create object of URL class and specify the appium server address
//        URL url = null;
//        try {
//            url= new URL("http://127.0.0.1:4723/wd/hub");
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//         
//        // Create object of  AndroidDriver class and pass the url and capability that we created
//        AndroidDriver<WebElement> androidDriver = new AndroidDriver<>(url, capabilities);
//         
//        return androidDriver;
//    }
    
//    /**
//     * Creación del WebDriver correspondiente a PhantomJS
//     */
//    private static PhantomJSDriver createPhantomJSDriver() {
//        //Configuración del Navegador / Webdriver
//        DesiredCapabilities caps = new DesiredCapabilities();
//        caps.setJavascriptEnabled(true); // not really needed: JS enabled by default
//        caps.setCapability("takesScreenshot", false);
//        
//        //Definimos el phantomjs.exe correspondiente a chrome
//        CreateWebDriver.setDriverPhantom(caps);
//        
//        //Configuración a nivel de Proxy/Seguridad
//        org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy();
//        proxy.setProxyType(org.openqa.selenium.Proxy.ProxyType.DIRECT);
//        caps.setCapability(CapabilityType.PROXY, proxy);
//        caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
//    
//        ArrayList<String> cliArgsCap = new ArrayList<>();
//        cliArgsCap.add("--web-security=false");
//        cliArgsCap.add("--ssl-protocol=any");
//        cliArgsCap.add("--ignore-ssl-errors=true");
//        cliArgsCap.add("--webdriver-loglevel=NONE");
//        caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, cliArgsCap);
//    
//        //Creación del WebDriver de PhantomJS
//        PhantomJSDriver phantomJSDriver = new PhantomJSDriver(caps);
//    
//        //Configuración específica de timeouts
//        phantomJSDriver.manage().deleteAllCookies();
//        phantomJSDriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
//        phantomJSDriver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
//        phantomJSDriver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
//        
//        //Maximizamos la ventana
//        phantomJSDriver.manage().window().maximize();
//        
//        return phantomJSDriver;
//    }
    
//    /**
//     * Creación del WebDriver correspondiente a PhantomJS
//     */
//    private static HtmlUnitDriver createHtmlUnitDriver() {    
//
//        //Configuración del Navegador / Webdriver
//        DesiredCapabilities caps = new DesiredCapabilities();
//        
//        //Definimos el acceso a la red
//        org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy();
//        proxy.setProxyType(org.openqa.selenium.Proxy.ProxyType.DIRECT);
//        
//        //Configuramos el webdriver
//        caps.setCapability(CapabilityType.PROXY, proxy);
//        DesiredCapabilities.htmlUnit();
//        caps.setCapability(CapabilityType.BROWSER_NAME,BrowserVersion.CHROME); 
//        
//        //Creamos el Webdriver
//        HtmlUnitDriver htmlUnitDriver = new HtmlUnitDriver(caps) {
//            @Override
//            protected WebClient modifyWebClient(final WebClient client) {
//                client.getOptions().setThrowExceptionOnScriptError(false);
//                client.getOptions().setUseInsecureSSL(true);
//                client.getOptions().setJavaScriptEnabled(true);
//                return client;
//              }
//        };
//    
//        //Activamos el uso de JavaScript
//        htmlUnitDriver.setJavascriptEnabled(true);
//        
//        //Maximizamos la ventana
//        htmlUnitDriver.manage().window().maximize();
//    
//        return htmlUnitDriver;
//    }
    
    /**
     * Establece el geckodriver.exe a nivel de parámetro webdriver.gecko.driver
     */    
    private static void setDriverFirefox() {        
        FirefoxDriverManager.getInstance().version("0.24.0").setup();
    }
    
    /**
     * Establece el chromedriver.exe a nivel de parámetro webdriver.chrome.driver
     */
    private static void setDriverChrome() {        
        ChromeDriverManager.getInstance().version("73.0.3683.20").setup();
    }

    /**
     * Establece el IEDriverServer.exe a nivel de parámetro webdriver.ie.driver
     */
    private static void setDriverExplorer() {
        InternetExplorerDriverManager.getInstance().setup();
    }
    
//    /**
//     * Establece el driver phantomjs.exe
//     */
//    private static void setDriverPhantom(DesiredCapabilities caps) {
//        PhantomJsDriverManager.getInstance().setup();
//    }

    private static void activeLogsInWebDriver(FirefoxOptions fo) {
        LoggingPreferences logs = getLogsWebDriverEnabled();
        fo.setCapability(CapabilityType.LOGGING_PREFS, logs);
    }    
    
    private static LoggingPreferences getLogsWebDriverEnabled() {
        //Configuramos la recopilación de logs a nivel de WebDriver
        LoggingPreferences logs = new LoggingPreferences();
        logs.enable(LogType.BROWSER, Level.SEVERE);
        logs.enable(LogType.CLIENT, Level.SEVERE);
        logs.enable(LogType.DRIVER, Level.OFF);
        logs.enable(LogType.PERFORMANCE, Level.SEVERE);
        logs.enable(LogType.PROFILER, Level.SEVERE);
        logs.enable(LogType.SERVER, Level.SEVERE);
        return logs;
    }
}
