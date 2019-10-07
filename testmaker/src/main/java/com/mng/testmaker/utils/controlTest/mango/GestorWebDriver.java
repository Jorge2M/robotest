package com.mng.testmaker.utils.controlTest.mango;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;

import com.mng.testmaker.domain.SuiteContextTestMaker;
import com.mng.testmaker.domain.InputParamsTestMaker.ManagementWebdriver;
import com.mng.testmaker.service.webdriver.maker.FactoryWebdriverMaker;
import com.mng.testmaker.service.webdriver.maker.FactoryWebdriverMaker.WebDriverType;
import com.mng.testmaker.utils.NetTrafficMng;
import com.mng.testmaker.utils.controlTest.GestorWebDrv;
import com.mng.testmaker.utils.controlTest.StoredWebDrv;
import com.mng.testmaker.utils.controlTest.FmwkTest;
import com.mng.testmaker.utils.otras.Channel;
import com.mng.testmaker.utils.webdriver.BrowserStackMobil;

import java.lang.reflect.Method;
import java.text.Normalizer;
import java.util.regex.Pattern;

public class GestorWebDriver extends FmwkTest {
    static Logger pLogger = LogManager.getLogger(FmwkTest.log4jLogger);
    static Object startupSync = new Object();

    
    /**
     * Creamos el driver del tipo especificado y lo almacenamos en una estructura de tipo ThreadLocal que nos permite obtener datos almacenados a nivel de Thread
     * 
     * @param bpath         especifica el typo de WebDriver a crear (firefox, chrome, appium...)
     * @param appPath       indica la URL inicial con la que se ha de arrancar el WebDriver
     * @param datosFactoria identificador de los casos de prueba creados desde factorías
     * @param isMobil       indicador de si las pruebas son de móvil o desktop
     */
    public WebDriver getWebDriver(WebDriverType webDriverType, String appPath, String datosFactoria, Channel channel, ITestContext context, Method method) 
    throws Exception {
        WebDriver driver = null;
        if (!"ROBOTEST2".equals(System.getProperty("ROBOTEST2"))) {
            // Lo ideal sería ejecutar este método desde el InvokeListener.onTestStart, pero desde allí no tiene efecto
            // la SkipException
            FmwkTest.sendSkipTestExceptionIfSuiteStopping(context);
	    
            //Guardamos los datos enviados por la factoria indexados por nombre de Thread (soporte para la paralelización)
            context.setAttribute("factory-"+String.valueOf(Thread.currentThread().getId()), deAccent(datosFactoria));
        	    	
            //Obtenemos el gestor de WebDrivers (lo busca en el contexto y si no existe lo crea/almacena en dicho contexto)
            GestorWebDrv gestorWd = GestorWebDrv.getInstance(context);
    	    
            //Obtenemos información adicional del WebDriver que necesitamos (básicamente el modelo del dispositivo en el caso de BrowserStack)
            String moreDataWdrv = getMoreDataWdrv(webDriverType, context);
                    
            //Buscamos un webdriver libre del tipo que necesitamos (y automáticamente se marca como 'busy')
            SuiteContextTestMaker testMakerCtx = SuiteContextTestMaker.getTestMakerContext(context);
            boolean netAnalysis = testMakerCtx.getInputData().isNetAnalysis();
            driver = gestorWd.getWebDrvFree(webDriverType, moreDataWdrv);
            if (driver == null) {
        		driver = 
        			FactoryWebdriverMaker.make(webDriverType, context)
        				.setChannel(channel)
        				.setNettraffic(netAnalysis)
        				.build();
                
                gestorWd.storeWebDriver(driver, StoredWebDrv.stateWd.busy, webDriverType, moreDataWdrv);
            }
                    
            //Almacenamiento en el contexto de algunos datos útiles
            context.setAttribute("bpath", webDriverType.name());
            context.setAttribute("appPath", appPath);
        }

        return driver;
    }   
	
    public void quitWebDriver(WebDriver driver, ITestContext contextTng) {
    	//Borramos el proxy para la gestión del NetTraffic asociado a nivel de Thread de TestNG
        SuiteContextTestMaker testMakerCtx = SuiteContextTestMaker.getTestMakerContext(contextTng);
        boolean netAnalysis = testMakerCtx.getInputData().isNetAnalysis();
    	if (netAnalysis) {
        	NetTrafficMng.stopNetTrafficThread();
    	}
    	
        //Obtenemos el gestor de WebDrivers (lo busca en el contexto)
        GestorWebDrv gestorWd = GestorWebDrv.getGestorFromCtx(contextTng);
        
        //Obtenemos el valor del parámetro 'recycleWD' almacenado en el contexto de ejecución de TestNG
        ManagementWebdriver managementWdrv = testMakerCtx.getInputData().getTypeManageWebdriver();
        switch (managementWdrv) {
        case recycle:
            driver.manage().deleteAllCookies();
            gestorWd.setWebDriverToFree(driver);
            break;
        case discard:
            gestorWd.deleteStrWebDriver(driver);
            try {
                if (driver!=null) {
                    driver.quit();
                }
            }
            catch (Exception e) {
                pLogger.error("Problem deleging WebDriver",  e);
            }
        }
    }
	
    public static String deAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD); 
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }
	
    /**
     * Transforma el valor del bpath en un canal de ejecución de pruebas controlado
     */
    public static WebDriverType getWebDriverType(String bpath) {
        return WebDriverType.valueOf(bpath);
    }
	
    /**
     * Devuelve datos adicionales del WebDriver que necesitamos.
     * actualmente sólo lo informaremos para el caso de 'BrowserStack' (devolvemos el modelo de de dispositivo móvil especificado en BrowserStack)
     * en el resto de casos devolveremos ""
     */
    public String getMoreDataWdrv(WebDriverType canalWebDriver, ITestContext contextTng) {
        String moreDataWdrv = "";
        switch (canalWebDriver) {
        //En el caso de BrowserStack como información específica del WebDriver incluiremos el modelo de dispositivo móvil asociado
        case browserstack:
            BrowserStackMobil bsStackMobil = SuiteContextTestMaker.getTestRun(contextTng).getBrowserStackMobil();
            if (bsStackMobil!=null) {
                moreDataWdrv = bsStackMobil.getDevice();
            }
            break;
	        
        //En el resto de tipos de WebDriver no habrá información específica sobre el WebDriver / Dispositivo de ejecución
        case firefox:
        case chrome:
        case edge:
            moreDataWdrv = "";
                break;	  
        default:
            break;
        }
	    
        return moreDataWdrv;
    }
}