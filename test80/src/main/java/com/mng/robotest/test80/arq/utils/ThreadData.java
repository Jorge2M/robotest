package com.mng.robotest.test80.arq.utils;

import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;

import com.mng.robotest.test80.arq.utils.controlTest.mango.GestorWebDriver;
import com.mng.robotest.test80.arq.utils.otras.Constantes.TypeDriver;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;

public class ThreadData {
    protected ThreadLocal<DataCtxShop> dCtxShInThread = new ThreadLocal<>();
    static ThreadLocal<DataFmwkTest> dFTestInThread = new ThreadLocal<>();

    public DataCtxShop getdCtxSh() {
        return this.dCtxShInThread.get();
    }    
    
    public static DataFmwkTest getdFTest() {
        return dFTestInThread.get();
    }   
    
    public WebDriver getWebDriver() {
    	return (getdFTest().driver);
    }
   
    public void storeInThread(DataCtxShop dCtxShop, DataFmwkTest dFTest) {
    	storeInThread(dCtxShop);
    	storeInThread(dFTest);
    }
    
    public void storeInThread(DataCtxShop dCtxShop) {
    	this.dCtxShInThread.set((DataCtxShop)dCtxShop.clone());
    }
    
    public static void storeInThread(DataFmwkTest dFTest) {
    	dFTestInThread.set(dFTest);
    }
    
    public static void getAndStoreDataFmwk(String bpath, String appPath, String datosFactoria, Channel channel, ITestContext context, Method method) 
    throws Exception {
    	GestorWebDriver gestorWdrv = new GestorWebDriver();
		WebDriver driver = gestorWdrv.getWebDriver(bpath, appPath, datosFactoria, channel, context, method);
		DataFmwkTest dFTest = new DataFmwkTest(driver, TypeDriver.valueOf(bpath), method, context);
		storeInThread(dFTest);
    }
}
