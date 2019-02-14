package com.mng.robotest.test80.arq.utils;

import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Queue;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;

import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.mango.GestorWebDriver;
import com.mng.robotest.test80.arq.utils.otras.Constantes.TypeDriver;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;

public class ThreadData {
    static ThreadLocal<DataCtxShop> dCtxShInThread = new ThreadLocal<>();
    static ThreadLocal<DataFmwkTest> dFTestInThread = new ThreadLocal<>();
    static ThreadLocal<Queue<DatosStep>> datosStepStack = new ThreadLocal<>();
    static ThreadLocal<DatosStep> maxDatosStep = new ThreadLocal<>();

    public static DataCtxShop getdCtxSh() {
        return dCtxShInThread.get();
    }    
    
    public static DataFmwkTest getdFTest() {
        return dFTestInThread.get();
    }   
    
    public static WebDriver getWebDriver() {
    	return (getdFTest().driver);
    }
    
    public static DatosStep pollDatosStep() {
    	Queue<DatosStep> datosStepStackTmp = datosStepStack.get();
    	return (datosStepStackTmp.poll());
    }
    
    public static DatosStep peekDatosStep() {
    	Queue<DatosStep> datosStepStackTmp = datosStepStack.get();
    	if (datosStepStackTmp!=null && !datosStepStackTmp.isEmpty()) {
    		return (datosStepStackTmp.peek());
    	}
    	
    	return null;
    }
   
    public static void storeInThread(DataCtxShop dCtxShop, DataFmwkTest dFTest) {
    	storeInThread(dCtxShop);
    	storeInThread(dFTest);
    }
    
    public static void storeInThread(DataCtxShop dCtxShop) {
    	dCtxShInThread.set((DataCtxShop)dCtxShop.clone());
    }
    
    public static void storeInThread(DataFmwkTest dFTest) {
    	dFTestInThread.set(dFTest);
    }
    
    public static void storeInThread(DatosStep datosStep) {
    	Queue<DatosStep> datosStepStackTmp = datosStepStack.get();
    	if (datosStepStackTmp==null) {
    		datosStepStackTmp = Collections.asLifoQueue(new ArrayDeque<DatosStep>());
    	}
    	
    	datosStepStackTmp.add(datosStep);
    	datosStepStack.set(datosStepStackTmp);
    	maxDatosStep.set(datosStep);
    }
    
    public static void getAndStoreDataFmwk(String bpath, String appPath, String datosFactoria, Channel channel, ITestContext context, Method method) 
    throws Exception {
    	GestorWebDriver gestorWdrv = new GestorWebDriver();
		WebDriver driver = gestorWdrv.getWebDriver(bpath, appPath, datosFactoria, channel, context, method);
		DataFmwkTest dFTest = new DataFmwkTest(driver, TypeDriver.valueOf(bpath), method, context);
		storeInThread(dFTest);
    }
    
    public static DatosStep getMaxDatosStep() {
    	return (maxDatosStep.get());
    }
}
