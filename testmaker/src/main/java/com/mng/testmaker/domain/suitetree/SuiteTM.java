package com.mng.testmaker.domain.suitetree;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.mng.testmaker.conf.ConstantesTM;
import com.mng.testmaker.conf.State;
import com.mng.testmaker.domain.InputParamsTM;
import com.mng.testmaker.domain.SenderMailEndSuiteI;
import com.mng.testmaker.domain.StateExecution;
import com.mng.testmaker.domain.SuitesExecuted;
import com.mng.testmaker.service.TestMaker;
import com.mng.testmaker.service.webdriver.pool.PoolWebDrivers;
import com.mng.testmaker.testreports.html.GenerateReports;

public class SuiteTM extends XmlSuite {
	
	private static final long serialVersionUID = 1L;
	private final InputParamsTM inputParams;
	private final String idSuiteExecution;
	private long threadId;
	private StateExecution stateExecution = StateExecution.NotStarted;
	private State result = State.Ok;
	private long timeInicio = 0;
	private long timeFin = 0;
	private final PoolWebDrivers poolWebDrivers = new PoolWebDrivers();
	private SenderMailEndSuiteI senderMail;
	
	public SuiteTM(String idSuiteExecution, InputParamsTM inputParams) {
		this.idSuiteExecution = idSuiteExecution;
		this.inputParams = inputParams;
		//TODO desasteriscar
		//this.senderMail = new DefaultMailEndSuite();
	}
	
	public String getIdExecution() {
		return idSuiteExecution;
	}

	public long getThreadId() {
		return threadId;
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
		this.threadId = Thread.currentThread().getId();
		stateExecution = StateExecution.Started;
		timeInicio = (new Date()).getTime(); 
    	SuitesExecuted.add(this);
		if (inputParams.isStoreResult()) {
			TestMaker.getRepository().storeSuite(this);
		}
	}
	
	public void end() {
		stateExecution = StateExecution.Finished;
		result = getResultFromTestsRun();
		timeFin = (new Date()).getTime(); 
		poolWebDrivers.removeAllStrWd();
		if (inputParams.isSendMailInEndSuite()) {
			senderMail.sendMail(this);
		}
		if (inputParams.isStoreResult()) {
			TestMaker.getRepository().storeAll(this);
		}
		SuitesExecuted.remove(this);
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
		return new Date(getTimeInicio());
	}
	public Date getFin() {
		return new Date(getTimeFin());
	}
	
	public long getTimeInicio() {
		return timeInicio;
	}
	public void setTimeInicio(long timeInicio) {
		this.timeInicio = timeInicio;
	}
	public long getTimeFin() {
		return timeFin;
	}
	public void setTimeFin(long timeFin) {
		this.timeFin = timeFin;
	}

	public long getDurationMillis() {
		return timeFin - timeInicio;
	}
	
	public void setListenersClass(List<Class<?>> listListeners) {
		List<String> listListenersStr = new ArrayList<>();
		for (Class<?> listener : listListeners) {
			listListenersStr.add(listener.getCanonicalName());
		}
		setListeners(listListenersStr);
	}
	
	public String getPathDirectory() {
		return getPathDirectory(getName(), getIdExecution());
	}
	public static String getPathDirectory(String nameSuite, String idExecutionSuite) {
		String userDir = System.getProperty("user.dir");
		String lastCharUserDir = userDir.substring(userDir.length() - 1);
		if (File.separator.compareTo(lastCharUserDir)!=0) {
			userDir+=File.separator;
		}
		return (
			userDir +
			ConstantesTM.directoryOutputTests + File.separator + 
			nameSuite + File.separator + 
			idExecutionSuite);
	}
	
	public String getPathReportHtml() {
		return (getPathDirectory() + File.separator + ConstantesTM.nameReportHTMLTSuite);
	}
	
	public String getDnsReportHtml() {
		String pathFileReport = getPathReportHtml();
		return (GenerateReports.getDnsOfFileReport(pathFileReport, inputParams.getWebAppDNS(), inputParams.getTypeAccess()));
	}
	
	public SuiteBean getSuiteBean() {
		SuiteBean suiteBean = new SuiteBean();
		InputParamsTM inputParams = getInputParams();
		
		suiteBean.setIdExecSuite(getIdExecution());
		suiteBean.setName(getName());
		suiteBean.setVersion(inputParams.getVersion());
		suiteBean.setChannel(inputParams.getChannel());
		suiteBean.setApp(inputParams.getApp().toString());
		suiteBean.setWebDriverType(inputParams.getWebDriverType());
		suiteBean.setResult(getResult());
		suiteBean.setInicioDate(getInicio());
		suiteBean.setFinDate(getFin());
		suiteBean.setDurationMillis(getDurationMillis());
		suiteBean.setNumberTestCases(getNumberTestCases());
		suiteBean.setMoreInfo(inputParams.getMoreInfo());
		suiteBean.setUrlBase(inputParams.getUrlBase());
		suiteBean.setPathReportHtml(getPathReportHtml());
		suiteBean.setUrlReportHtml(getDnsReportHtml());
		suiteBean.setStateExecution(getStateExecution());
		
		List<TestRunBean> listTestRun = new ArrayList<>();
		for (TestRunTM testRun : getListTestRuns()) {
			listTestRun.add(testRun.getTestRunBean());
		}
		suiteBean.setListTestRun(listTestRun);
		
		return suiteBean;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj==this) {
			return true;
		}
		return false;
//		if (!(obj instanceof SuiteTM)) {
//			return false;
//		}
//		SuiteTM suite = (SuiteTM)obj;
//		return (
//			getIdExecution().compareTo(suite.getIdExecution())==0 &&
//			getThreadId()==suite.getThreadId());
	}
}