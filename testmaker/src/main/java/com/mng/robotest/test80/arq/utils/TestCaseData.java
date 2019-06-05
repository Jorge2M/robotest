package com.mng.robotest.test80.arq.utils;

import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;

import com.mng.robotest.test80.arq.utils.conf.StorerErrorDataStepValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.mango.GestorWebDriver;
import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.arq.utils.webdriver.maker.FactoryWebdriverMaker.TypeWebDriver;

public class TestCaseData {
    static ThreadLocal<Map<String, Object>> dataInThread = new ThreadLocal<>();
    static ThreadLocal<DataFmwkTest> dFTestInThread = new ThreadLocal<>();
    static ThreadLocal<Queue<DatosStep>> datosStepStack = new ThreadLocal<>();
    static ThreadLocal<DatosStep> maxDatosStep = new ThreadLocal<>();

    public static Object getData(String idData) throws Exception {
    	Map<String, Object> data = dataInThread.get();
    	if (data!=null) {
    		return (data.get(idData));
    	}
    	return null;
    }
    
    public static void storeData(String idData, Object data) {
    	Map<String, Object> dataMap = dataInThread.get();
    	if (dataMap==null) {
    		dataMap = new HashMap<String, Object>();
    	}
    	dataMap.put(idData, data);
    	dataInThread.set(dataMap);
    }
    
    public static DataFmwkTest getdFTest() {
        return dFTestInThread.get();
    }   
    
    public static WebDriver getWebDriver() {
    	return (getdFTest().driver);
    }
    
    public static DatosStep pollDatosStepForStep() {
    	Queue<DatosStep> datosStepStackTmp = datosStepStack.get();
    	return (datosStepStackTmp.poll());
    }
    
    public static DatosStep peekDatosStepForStep() {
    	Queue<DatosStep> datosStepStackTmp = datosStepStack.get();
    	if (datosStepStackTmp!=null && !datosStepStackTmp.isEmpty()) {
    		return (datosStepStackTmp.peek());
    	}
    	
    	return null;
    }
    
    public static void clearStackDatosStep() {
    	Queue<DatosStep> datosStepStackTmp = datosStepStack.get();
    	datosStepStackTmp.clear();
    	maxDatosStep.remove();
    }
    
    public static DatosStep getDatosLastStep() {
    	return (maxDatosStep.get());
    }
    
    public static DatosStep getDatosCurrentStep() {
    	return (peekDatosStepForStep());
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
    
    public static void getAndStoreDataFmwk(String bpath, String appPath, String datosFactoria, Channel channel, 
    									   StorerErrorDataStepValidation storerDataError, ITestContext context, Method method) 
    throws Exception {
    	GestorWebDriver gestorWdrv = new GestorWebDriver();
		WebDriver driver = gestorWdrv.getWebDriver(bpath, appPath, datosFactoria, channel, context, method);
		DataFmwkTest dFTest = new DataFmwkTest(driver, TypeWebDriver.valueOf(bpath), method, context);
		dFTest.setStorerDataError(storerDataError);
		storeInThread(dFTest);
    }
}
