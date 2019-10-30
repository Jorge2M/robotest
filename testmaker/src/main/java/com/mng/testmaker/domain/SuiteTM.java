package com.mng.testmaker.domain;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.mng.testmaker.conf.ConstantesTM;
import com.mng.testmaker.conf.Log4jConfig;
import com.mng.testmaker.conf.State;
import com.mng.testmaker.service.webdriver.pool.PoolWebDrivers;
import com.mng.testmaker.testreports.html.GenerateReports;

public class SuiteTM extends XmlSuite {
	
	private static final long serialVersionUID = 1L;
	private final InputParamsTM inputParams;
	private final String idSuiteExecution;
	private StateExecution stateExecution = StateExecution.NotStarted;
	private State result = State.Ok;
	private Date inicio;
	private Date fin;
	private final PoolWebDrivers poolWebDrivers = new PoolWebDrivers();
	private SenderMailEndSuiteI senderMail;
	private PersistorDataI storerResult;
	
	public SuiteTM(String idSuiteExecution, InputParamsTM inputParams) {
		this.idSuiteExecution = idSuiteExecution;
		this.inputParams = inputParams;
		//TODO desasteriscar
		//this.senderMail = new DefaultMailEndSuite();
	}
	
	public String getIdExecution() {
		return idSuiteExecution;
	}
	
	public InputParamsTM getInputParams() {
		return inputParams;
	}
	
	public StateExecution getStateExecution() {
		return stateExecution;
	}
	
	public void setStateExecution(StateExecution stateExecution) {
		this.stateExecution = stateExecution;
	}
	
	public State getResult() {
		return result;
	}
	
	public void setSenderMail(SenderMailEndSuiteI senderMail) {
		this.senderMail = senderMail;
	}
	public void setStorerResult(PersistorDataI storerResult) {
		this.storerResult = storerResult;
	}
	
	public List<TestRunTM> getListTestRuns() {
		List<TestRunTM> listTestRuns = new ArrayList<>();
		for (XmlTest xmlTest : getTests()) {
			listTestRuns.add((TestRunTM)xmlTest);
		}
		return listTestRuns;
	}
	
	public int getNumberTestCases() {
		int numTestCases = 0;
		for (TestRunTM testRun : getListTestRuns()) {
			numTestCases+=testRun.getNumTestCases();
		}
		return numTestCases;
	}
	
	public PoolWebDrivers getPoolWebDrivers() {
		return poolWebDrivers;
	}
	
	public void start() {
		stateExecution = StateExecution.Started;
		inicio = new Date(); 
	}
	
	public void end() {
		stateExecution = StateExecution.Finished;
    	Log4jConfig.pLogger.info("End Suite 1");
		result = getResultFromTestsRun();
    	Log4jConfig.pLogger.info("End Suite 2");
		fin = new Date(); 
    	Log4jConfig.pLogger.info("End Suite 3");
		poolWebDrivers.removeAllStrWd();
    	Log4jConfig.pLogger.info("End Suite 4");
    	if (inputParams.isSendMailInEndSuite()) {
        	Log4jConfig.pLogger.info("End Suite 5");
    		senderMail.sendMail(this);
        	Log4jConfig.pLogger.info("End Suite 6");
        }
    	Log4jConfig.pLogger.info("End Suite 7");
    	if (inputParams.isStoreResult()) {
        	Log4jConfig.pLogger.info("End Suite 8");
    		storerResult.store(this);
        	Log4jConfig.pLogger.info("End Suite 9");
    	}
	}
	
	private State getResultFromTestsRun() {
		State stateReturn = State.Ok;
		for (TestRunTM testRun : getListTestRuns()) {
			if (testRun.getResult().isMoreCriticThan(stateReturn)) {
				stateReturn = testRun.getResult();
			}
		}
		return stateReturn;
	}
	
	public Date getInicio() {
		return inicio;
	}
	
	public Date getFin() {
		return fin;
	}
	
	public long getDurationMillis() {
		return fin.getTime() - fin.getTime();
	}
	
	public void setListenersClass(List<Class<?>> listListeners) {
		List<String> listListenersStr = new ArrayList<>();
		for (Class<?> listener : listListeners) {
			listListenersStr.add(listener.getCanonicalName());
		}
		setListeners(listListenersStr);
	}
	
	public String getPathDirectory() {
        String userDir = System.getProperty("user.dir");
        String lastCharUserDir = userDir.substring(userDir.length() - 1);
        if (File.separator.compareTo(lastCharUserDir)!=0) {
            userDir+=File.separator;
        }
        return (
        	userDir +
        	ConstantesTM.directoryOutputTests + File.separator + 
        	getName() + File.separator + 
        	getIdExecution());
	}
	
	public String getPathReportHtml() {
		return (getPathDirectory() + File.separator + ConstantesTM.nameReportHTMLTSuite);
	}
	
	public String getDnsReportHtml() {
		String pathFileReport = getPathReportHtml();
		return (GenerateReports.getDnsOfFileReport(pathFileReport, inputParams.getWebAppDNS()));
	}

}
