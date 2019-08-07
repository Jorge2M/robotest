package org.pruebasws.thread;

import java.util.Set;

import com.mng.robotest.test80.Test80mng;
import com.mng.robotest.test80.arq.xmlprogram.ParamsBean;
import com.mng.robotest.test80.arq.xmlprogram.SuiteMaker;

import java.util.ArrayList;

@SuppressWarnings("javadoc")
public class TSuiteThreadsManager {

    public static final String prefixIdThread = "/Test/";

    public static String getLocatorThreadGroupTestSuite(String suiteName, String channel) {
        return (
            prefixIdThread +
            suiteName + ":" +
            channel
        );        
    }
    
	public static String startSuiteInThread(ParamsBean paramsTSuite) throws Exception { 
		SuiteMaker suite = Test80mng.makeSuite(paramsTSuite);
		String idExecSuite = suite.getIdSuiteExecution();
		
	  	Thread.UncaughtExceptionHandler h = new Thread.UncaughtExceptionHandler() {
		    public void uncaughtException(Thread th, Throwable ex) {
			System.out.println("Uncaught exception: " + ex);
			}
		};
	  	
	  	ThreadGroup tg1 = new ThreadGroup(TSuiteThreadsManager.getLocatorThreadTestSuite(paramsTSuite, idExecSuite));
	    Thread test = new Thread(tg1, new Runnable() {
	        public void run(){
	          	try {
	        	    suite.run();
	          	}
	          	catch (Throwable e) {
	          		e.printStackTrace();
	          	}
	        }
	    });
	  	
	  	test.start();
	  	
	  	//Nombramos el Thread con los datos de la TSuite
		test.setName(TSuiteThreadsManager.getLocatorThreadTestSuite(paramsTSuite, idExecSuite));
	  	//return test.getId();
		
	  	return (idExecSuite);
	}
    
    public static String getLocatorThreadTestSuite(ParamsBean paramsTSuite, String idExecSuite) {
        return (getLocatorThreadTestSuite(paramsTSuite.getSuiteName(), paramsTSuite.getApp().toString(), paramsTSuite.getChannel().toString(), paramsTSuite.getBrowser(), paramsTSuite.getVersion(), idExecSuite));   
    }
    
    public static String getLocatorThreadTestSuite(String suiteName, String application, String channel, String browser, String version, String idSuiteExecution) {
        return (
            prefixIdThread +
            suiteName + ":" +
            channel + ":" +
            application + ":" +            
            browser + ":" + 
            version + ":" +
            idSuiteExecution 
        );
    }

    public static String getIdExecSuiteFromLocator(String locatorThreadTestSuite) {
        int iniId = locatorThreadTestSuite.lastIndexOf(":");
        return (locatorThreadTestSuite.substring(iniId + 1));
    }
    
    public static ArrayList<Thread> getThreadsTestSuiteGroup() {
        return (getThreadsTestSuiteGroup("", ""));
    }
	
    public static ArrayList<Thread> getThreadsTestSuiteGroup(String suiteName, String channel) { 
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        ArrayList<Thread> threadsTest = new ArrayList<>();
        for (Thread thread : threadSet) {
            if (thread.getName().contains(getLocatorThreadGroupTestSuite(suiteName, channel))) 
                threadsTest.add(thread);
        }
		
        return threadsTest;
    }
    
    //Interrumpe todos los threads creados en la ejecución de una TestSuite concreta
    public static ArrayList<Thread> stopThreadsTestSuite(String locatorThreadTestSuite) throws Exception {
        boolean existThreads = true;
        ArrayList<Thread> threadsTest = new ArrayList<>();
        int i=0;
        while (existThreads && i<=20) {
            i+=1;
            existThreads = false;
            Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
            for (Thread thread : threadSet) {
                String threadGroup = "";
                if (thread.getThreadGroup()!=null) 
                    threadGroup = thread.getThreadGroup().getName();
				
                //Borraremos todos los threads pertenecientes al grupo del thread padre (el que se arranca en execTest.jsp donde se arranca TestNG)
                //Es decir, el thread padre + todos los threads que se han creado desde el thread padre
                if (threadGroup.contains(locatorThreadTestSuite)) {
                    existThreads = true;
                    threadsTest.add(thread);
                    thread.interrupt();
                }
            }
			
            //Este mecanismo tiene un problema y es que por alguna razón en ocasiones se crean nuevos Threads hijos de TestNG con lo que el test no finaliza
            //Así que esperamos 1 segundo y volvemos a revisar si se han creado nuevos Threads
            if (existThreads) 
                Thread.sleep(1000);
        }
		
        return threadsTest;
    }	
}
