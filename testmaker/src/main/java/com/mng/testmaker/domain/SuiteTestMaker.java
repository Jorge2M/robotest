package com.mng.testmaker.domain;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.mng.testmaker.data.ConstantesTestMaker;
//import com.mng.testmaker.service.testreports.DefaultMailEndSuite;
import com.mng.testmaker.service.testreports.SenderMailEndSuite;
import com.mng.testmaker.service.webdriver.pool.PoolWebDrivers;

public class SuiteTestMaker extends XmlSuite {
	
	private static final long serialVersionUID = 1L;
	private final InputParamsTestMaker inputData;
	private final String idSuiteExecution;
	private StateRun state = StateRun.NotStarted;
	private final String directory;
	private final PoolWebDrivers poolWebDrivers = new PoolWebDrivers();
	private SenderMailEndSuite senderMail;
	
	public SuiteTestMaker(String idSuiteExecution, InputParamsTestMaker inputParams) {
		this.idSuiteExecution = idSuiteExecution;
		this.inputData = inputParams;
		this.directory = getPathDirectory();
		//TODO desasteriscar
		//this.senderMail = new DefaultMailEndSuite();
	}
	
	public String getIdExecution() {
		return idSuiteExecution;
	}
	
	public InputParamsTestMaker getInputData() {
		return inputData;
	}
	
	public StateRun getState() {
		return state;
	}
	
	public void setState(StateRun state) {
		this.state = state;
	}
	
	public void setSenderMail(SenderMailEndSuite senderMail) {
		this.senderMail = senderMail;
	}
	
	public String getDirectory() {
		return directory;
	}
	
	public List<TestRunTestMaker> getListTestRuns() {
		List<TestRunTestMaker> listTestRuns = new ArrayList<>();
		for (XmlTest xmlTest : getTests()) {
			listTestRuns.add((TestRunTestMaker)xmlTest);
		}
		return listTestRuns;
	}
	
	public PoolWebDrivers getPoolWebDrivers() {
		return poolWebDrivers;
	}
	
	public void start() {
		
	}
	
	public void end() {
		state = StateRun.Stopped;
		poolWebDrivers.removeAllStrWd();
    	if (inputData.isSendMailInEndSuite()) {
    		senderMail.sendMail(this);
        }   
	}
	
	public void setListenersClass(List<Class<?>> listListeners) {
		List<String> listListenersStr = new ArrayList<>();
		for (Class<?> listener : listListeners) {
			listListenersStr.add(listener.getCanonicalName());
		}
		setListeners(listListenersStr);
	}
	
	private String getPathDirectory() {
        String userDir = System.getProperty("user.dir");
        String lastCharUserDir = userDir.substring(userDir.length() - 1);
        if (File.separator.compareTo(lastCharUserDir)!=0) {
            userDir+=File.separator;
        }
        return (
        	userDir +
        	ConstantesTestMaker.directoryOutputTests + File.separator + 
        	getName() + File.separator + 
        	getIdExecution());
	}
}