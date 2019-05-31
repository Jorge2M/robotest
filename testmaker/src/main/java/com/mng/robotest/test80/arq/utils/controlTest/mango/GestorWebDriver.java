package com.mng.robotest.test80.arq.utils.controlTest.mango;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;

import com.mng.robotest.test80.arq.utils.NetTrafficMng;
import com.mng.robotest.test80.arq.utils.controlTest.GestorWebDrv;
import com.mng.robotest.test80.arq.utils.controlTest.StoredWebDrv;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.arq.utils.otras.Constantes;
import com.mng.robotest.test80.arq.utils.webdriver.BStackDataMovil;
import com.mng.robotest.test80.arq.utils.webdriver.maker.FactoryWebdriverMaker;
import com.mng.robotest.test80.arq.utils.webdriver.maker.FactoryWebdriverMaker.TypeWebDriver;

import java.lang.reflect.Method;
import java.text.Normalizer;
import java.util.regex.Pattern;

public class GestorWebDriver extends fmwkTest {
    static Logger pLogger = LogManager.getLogger(fmwkTest.log4jLogger);
    static Object startupSync = new Object();

    
    /**
     * Creamos el driver del tipo especificado y lo almacenamos en una estructura de tipo ThreadLocal que nos permite obtener datos almacenados a nivel de Thread
     * 
     * @param bpath         especifica el typo de WebDriver a crear (firefox, chrome, appium...)
     * @param appPath       indica la URL inicial con la que se ha de arrancar el WebDriver
     * @param datosFactoria identificador de los casos de prueba creados desde factorías
     * @param isMobil       indicador de si las pruebas son de móvil o desktop
     */
    public WebDriver getWebDriver(String bpath, String appPath, String datosFactoria, Channel channel, ITestContext context, Method method) 
    throws Exception {
        WebDriver driver = null;
        if (!"ROBOTEST2".equals(System.getProperty("ROBOTEST2"))) {
            // Lo ideal sería ejecutar este método desde el InvokeListener.onTestStart, pero desde allí no tiene efecto
            // la SkipException
            fmwkTest.sendSkipTestExceptionIfSuiteStopping(context);
	    
            //Guardamos los datos enviados por la factoria indexados por nombre de Thread (soporte para la paralelización)
            context.setAttribute("factory-"+String.valueOf(Thread.currentThread().getId()), deAccent(datosFactoria));
            String browser = bpath;

            //Obtenemos navegador/canal de ejecución de las pruebas en base al bpath del testng.xml
            TypeWebDriver canalWebDriver = getTypeWebdriver(browser);
        	    	
            //Obtenemos el gestor de WebDrivers (lo busca en el contexto y si no existe lo crea/almacena en dicho contexto)
            GestorWebDrv gestorWd = GestorWebDrv.getInstance(context);
    	    
            //Obtenemos información adicional del WebDriver que necesitamos (básicamente el modelo del dispositivo en el caso de BrowserStack)
            String moreDataWdrv = getMoreDataWdrv(canalWebDriver, context);
                    
            //Buscamos un webdriver libre del tipo que necesitamos (y automáticamente se marca como 'busy')
            boolean netAnalysis = isParamNetTrafficActive(context);
            driver = gestorWd.getWebDrvFree(canalWebDriver, moreDataWdrv);
            if (driver == null) {
        		driver = 
        			FactoryWebdriverMaker.make(canalWebDriver, context)
        				.setChannel(channel)
        				.setNettraffic(netAnalysis)
        				.build();
                
                gestorWd.storeWebDriver(driver, StoredWebDrv.stateWd.busy, canalWebDriver, moreDataWdrv);
            }
                    
            //Almacenamiento en el contexto de algunos datos útiles
            context.setAttribute("bpath", bpath);
            context.setAttribute("appPath", appPath);
        }

        return driver;
    }
    
    private boolean isParamNetTrafficActive(ITestContext ctx) {
        String netAnalysis = ctx.getCurrentXmlTest().getParameter(Constantes.paramNetAnalysis);
        return ("true".compareTo(netAnalysis)==0);
    }    
	
    public void quitWebDriver(WebDriver driver, ITestContext contextTng) {
    	//Borramos el proxy para la gestión del NetTraffic asociado a nivel de Thread de TestNG
    	if (isParamNetTrafficActive(contextTng)) {
        	NetTrafficMng.stopNetTrafficThread();
    	}
    	
        //Obtenemos el gestor de WebDrivers (lo busca en el contexto)
        GestorWebDrv gestorWd = GestorWebDrv.getGestorFromCtx(contextTng);
        
        //Obtenemos el valor del parámetro 'recycleWD' almacenado en el contexto de ejecución de TestNG
        boolean recycleWD = this.getRecycleWD(contextTng);
        if (recycleWD) {
            //Los WebDriver no se destruyen al finalizar el método (se destruyen al finalizar la suite), sólo se marcan como 'free' y se reaprovechan por los siguientes
            driver.manage().deleteAllCookies();
	        
            //Lo buscamos en el gestor y marcamos nuestro webdriver a estado 'free'
            gestorWd.setWebDriverToFree(driver);
        } else {
            //Lo buscamos en el gestor de WebDrivers y lo eliminamos
            gestorWd.deleteStrWebDriver(driver);
            
            //Los WebDriver se crean al comenzar un método y se destruyen al finalizar
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
    public static TypeWebDriver getTypeWebdriver(String bpath) {
        return TypeWebDriver.valueOf(bpath);
    }
	
    /**
     * Devuelve datos adicionales del WebDriver que necesitamos.
     * actualmente sólo lo informaremos para el caso de 'BrowserStack' (devolvemos el modelo de de dispositivo móvil especificado en BrowserStack)
     * en el resto de casos devolveremos ""
     */
    public String getMoreDataWdrv(TypeWebDriver canalWebDriver, ITestContext contextTng) {
        String moreDataWdrv = "";
        switch (canalWebDriver) {
        //En el caso de BrowserStack como información específica del WebDriver incluiremos el modelo de dispositivo móvil asociado
        case browserstack:
            if (contextTng.getCurrentXmlTest().getParameter(BStackDataMovil.device_paramname)!=null) {
                moreDataWdrv = contextTng.getCurrentXmlTest().getParameter(BStackDataMovil.device_paramname);
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
	
    /**
     * Función que obtiene el valor del parámetro 'recycleWD' almacenado en el contexto de ejecución de TestNG
     */
    private boolean getRecycleWD(ITestContext contextTng) {
        if (contextTng.getCurrentXmlTest().getParameter(Constantes.paramRecycleWD)!=null) {
            String recycleWDStr = contextTng.getCurrentXmlTest().getParameter(Constantes.paramRecycleWD);
            if ("true".compareTo(recycleWDStr)==0) {
                return true;
            }
        }
            
        return false;
    }
}