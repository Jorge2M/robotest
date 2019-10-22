package com.mng.testmaker.domain;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.mng.testmaker.conf.ConstantesTestMaker;
import com.mng.testmaker.service.webdriver.pool.PoolWebDrivers;
import com.mng.testmaker.testreports.mail.SenderMailEndSuite;

public class SuiteTestMaker extends XmlSuite {
	
	private static final long serialVersionUID = 1L;
	private final InputParamsTestMaker inputParams;
	private final String idSuiteExecution;
	private StateRun state = StateRun.NotStarted;
	private final PoolWebDrivers poolWebDrivers = new PoolWebDrivers();
	private SenderMailEndSuite senderMail;
	
	public SuiteTestMaker(String idSuiteExecution, InputParamsTestMaker inputParams) {
		this.idSuiteExecution = idSuiteExecution;
		this.inputParams = inputParams;
		//TODO desasteriscar
		//this.senderMail = new DefaultMailEndSuite();
	}
	
	public String getIdExecution() {
		return idSuiteExecution;
	}
	
	public InputParamsTestMaker getInputParams() {
		return inputParams;
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
	
	public List<TestRunTestMaker> getListTestRuns() {
		List<TestRunTestMaker> listTestRuns = new ArrayList<>();
		for (XmlTest xmlTest : getTests()) {
			listTestRuns.add((TestRunTestMaker)xmlTest);
		}
		return listTestRuns;
	}
	
	public int getNumberTestCases() {
		int numTestCases = 0;
		for (TestRunTestMaker testRun : getListTestRuns()) {
			numTestCases+=testRun.getNumTestCases();
		}
		return numTestCases;
	}
	
	public PoolWebDrivers getPoolWebDrivers() {
		return poolWebDrivers;
	}
	
	public void end() {
		state = StateRun.Finished;
		poolWebDrivers.removeAllStrWd();
    	if (inputParams.isSendMailInEndSuite()) {
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
	
	public String getPathDirectory() {
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
	
	public String getPathReportHtml() {
		return (getPathDirectory() + File.separator + ConstantesTestMaker.nameReportHTMLTSuite);
	}
	
	public String getDnsReportHtml() {
		String pathFileReport = getPathReportHtml();
		return (getDnsOfFileReport(pathFileReport, inputParams.getWebAppDNS()));
	}
	
    /**
     * Obtiene la DNS de un fichero ubicado dentro del contexto de la aplicación de tests
     * @param serverDNS: del tipo "http://robottest.mangodev.net + :port si fuera preciso)  
     */
    public String getDnsOfFileReport(String filePath, String applicationDNS) {
        String pathReport = "";
        if (applicationDNS!=null && "".compareTo(applicationDNS)!=0) {
            pathReport = filePath.substring(filePath.indexOf(ConstantesTestMaker.directoryOutputTests));
            pathReport = applicationDNS + "\\" + pathReport;
        } else {
            Pattern patternDrive = Pattern.compile("^[a-zA-Z]:");
            pathReport = patternDrive.matcher(filePath).replaceFirst("\\\\\\\\" + getNamePC());
        }

        return pathReport;
    }
	
    public static String getNamePC() {
        String hostname = "";
        try {
            InetAddress addr;
            addr = InetAddress.getLocalHost();
            hostname = addr.getHostName();
        }
        catch (UnknownHostException ex) {
            hostname = "Unknown";
        }
		
        return hostname;
    }
}
