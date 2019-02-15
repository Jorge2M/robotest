package com.mng.robotest.test80.arq.utils.controlTest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;

import com.mng.robotest.test80.arq.utils.controlTest.StoredWebDrv.stateWd;
import com.mng.robotest.test80.arq.utils.otras.Constantes.TypeDriver;

/**
 * Clase encargada de gestionar la lista de WebDriver en el contexto de TestNG. Se trata de una clase Singleton que recupera la instancia de dicho contexto de TestNG
 * @author jorge.munoz
 *
 */

public class GestorWebDrv {
    static Logger pLogger = LogManager.getLogger(fmwkTest.log4jLogger);
    
    private static final String nameGestorInCtx = "gestorWebDrv";
    private List<StoredWebDrv> listWd = new CopyOnWriteArrayList<>();
    
    protected GestorWebDrv() {}
    
    /**
     * Obtiene el gestor del contexto de TestNG y si no existe lo crea y almacena en dicho contexto
     * @param context
     * @return el gestor de WebDrivers
     */
    public static GestorWebDrv getInstance(ITestContext context) {
        
        GestorWebDrv gestor = getGestorFromCtx(context);
        
        if (gestor==null) {
            gestor = new GestorWebDrv();
            context.setAttribute(nameGestorInCtx, gestor);
        }
        
        return gestor;
    }
    
    public List<StoredWebDrv> getListWd() {
        return this.listWd;
    }
    
    public void addWebdriver(StoredWebDrv strWd) {
        this.listWd.add(strWd);
    }
    
    /**
     * @return el gestor de WebDrivers almacenado en el contexto
     */
    public static GestorWebDrv getGestorFromCtx(ITestContext context) {
        
        if (context.getAttribute(nameGestorInCtx)!=null)
            return ((GestorWebDrv)context.getAttribute(nameGestorInCtx));

        return null;
    }
    
    /**
     * Busca un WebDriver libre entre la lista de webdrivers que sea del tipo especificado en los parámetros de entrada
     * @param typeWebdrv tipo de WebDriver (Firefox, Chrome, BrowserStack...)
     * @param moreDataWdrv especifica datos adicionales del WebDriver que necesitamos.
     *                     actualmente sólo viene informado para el caso de 'BrowserStack' (especificamos el modelo de 'device' documentado en BrowserStack)
     *                     en el resto de casos viene a "".
     */
    public synchronized WebDriver getWebDrvFree(TypeDriver typeWdrv, String moreDataWdrv) {
        WebDriver webdriverFree = null;
        Iterator<StoredWebDrv> itStrWd = this.listWd.iterator();
        pLogger.debug(": Buscando WebDriver free. Type {}, moreDataWrdrv {}", typeWdrv, moreDataWdrv);
        boolean encontrado = false;
        while (itStrWd.hasNext() && !encontrado) {
            StoredWebDrv strWd = itStrWd.next();
            if (strWd.isFree() &&
                strWd.getTypeWdrv() == typeWdrv &&
                strWd.getMoreDataWdrv().compareTo(moreDataWdrv) == 0) {
                
                //Lo obtenemos
                webdriverFree = strWd.getWebDriver();
                encontrado = true;
                
                //Le cambiamos el estado a 'busy' en el gestor
                strWd.markAsBusy();
                pLogger.debug("Encontrado -> Mark as Busy WebDriver: {} (state: {}, type: {}, moreDataWdrv: {})", strWd.getWebDriver(), strWd.getState(), strWd.getTypeWdrv(), strWd.getMoreDataWdrv());
            }
        }
        
        if (!encontrado)
            pLogger.debug("No encontrado Webdriver free. Type: {}, moreDataWrdrv: {}", typeWdrv, moreDataWdrv);
        
        return webdriverFree;
    }
    
    /**
     * Marca el webdriver en la lista como libre
     */
    public void setWebDriverToFree(WebDriver driver) {
        //Buscamos el WebDriver
        StoredWebDrv strWd = searchWebDriver(driver);
        
        //Si existe lo marcamos como 'free' 
        if (strWd != null) {
            strWd.markAsFree();
            pLogger.debug("Mark as Free WebDriver: {} (state: {}, type: {}, moreDataWdrv: {})", strWd.getWebDriver(), strWd.getState(), strWd.getTypeWdrv(), strWd.getMoreDataWdrv());
        }
    }
    
    /**
     * Borra el elemento de la lista que contiene el objeto webdriver
     */
    public void deleteStrWebDriver(WebDriver driver) {
        //Buscamos el WebDriver
        StoredWebDrv strWd = searchWebDriver(driver);
        
        //Si existe lo borramos
        if (strWd != null) 
            deleteStrWedDriver(strWd);
    }
    
    /**
     * Borra un elemento StoredWebDrv de la lista
     */
    public void deleteStrWedDriver(StoredWebDrv strWd) {
        this.listWd.remove(strWd);
        pLogger.debug("Removed Stored WebDriver: {} (state: {}, type: {}, moreDataWdrv: {})", strWd.getWebDriver(), strWd.getState(), strWd.getTypeWdrv(), strWd.getMoreDataWdrv());
    }

    /**
     * Almacenamos el webdriver en la lista con los datos especificados en los parámetros
     * @param state   estado 'busy', 'free'
     * @param type    tipo de navegador/canal de ejecución de los scripts
     * @param moreDataDrv especifica datos adicionales del WebDriver que necesitamos.
     *                    actualmente sólo viene informado para el caso de 'browserstack' (especificamos el modelo de 'device' documentado en browserstack)
     *                    en el resto de casos viene a "". 
     */
    public void storeWebDriver(WebDriver driver, stateWd state, TypeDriver type, String moreDataWdrv) {
        StoredWebDrv strWd = new StoredWebDrv(driver, state, type, moreDataWdrv);
        this.listWd.add(strWd);
        pLogger.debug("Alta Stored WebDriver: {} (state: {}, type: {}, moreDataWdrv: {})", driver, state, type, moreDataWdrv);
    }
    
    /**
     * Buscamos un webdriver concreto
     * @return StoredWebDrv que contiene el webdriver buscado
     */
    private StoredWebDrv searchWebDriver(WebDriver driver) {
        StoredWebDrv strWdRet = null;
        
        //Buscamos el webdriver en la lista
        Iterator<StoredWebDrv> itStrWd = this.listWd.iterator();
        boolean encontrado = false;
        while (itStrWd.hasNext() && !encontrado) {
            StoredWebDrv strWd = itStrWd.next();
            if (strWd.getWebDriver() == driver) {
                strWdRet = strWd;
                encontrado = true;
            }
        }
        
        return strWdRet;
    }

    /**
     * Por cada uno de los WebDriver ejecutamos un .quit() y finalmente borramos todos los WebDrivers almacenados
     */
    public void removeAllStrWd() {
        List<StoredWebDrv> strWdToDelete = new ArrayList<>();
        for (StoredWebDrv strWd : this.listWd) {
            try {
                strWdToDelete.add(strWd);
                strWd.getWebDriver().quit();
            }
            catch (Exception e) {
                //En caso de error en un .quit() hemos de continuar para cerrar el resto
                pLogger.error("Problem removing all WebDrivers", e);
            }
        }
        
        //Finalmente eliminamos todos los storedWebDrv de la lista
        this.listWd.removeAll(strWdToDelete);
        pLogger.info("Removed all WebDriver");
    }
}