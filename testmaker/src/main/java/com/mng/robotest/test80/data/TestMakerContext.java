package com.mng.robotest.test80.data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.testng.ISuite;
import org.testng.ITestContext;

import com.mng.robotest.test80.arq.jdbc.dao.CorreosGroupDAO;
import com.mng.robotest.test80.arq.xmlprogram.InputDataTestMaker;
import com.mng.robotest.test80.arq.xmlprogram.SuiteTestMaker;

public class TestMakerContext {

	private final String idSuiteExecution;
	private final InputDataTestMaker inputData;
	private final List<String> toMail;
	private final List<String> ccMail;
	private final String subjectMail;

	private TestMakerContext(InputDataTestMaker inputData) {
		this.idSuiteExecution = getIdForSuiteToExecute();
		this.inputData = inputData;
		toMail = createToMail();
		ccMail = new ArrayList<>();
		ccMail.add("jorge.munoz.sge@mango.com");
		subjectMail = "Result TestSuite " + inputData.getNameSuite() + " (" + inputData.getApp() + " / " + inputData.getUrlBase() + ")";
	}
	
	public static TestMakerContext getNew(InputDataTestMaker inputData) {
		return (new TestMakerContext(inputData));
	}
	
	public String getIdSuiteExecution() {
		return this.idSuiteExecution;
	}
	
	public InputDataTestMaker getInputData() {
		return this.inputData;
	}
	
	public List<String> getToMail() {
		return this.toMail;
	}
	
	public List<String> getCcMail() {
		return this.ccMail;
	}
	
	public String getSubjectMail() {
		return this.subjectMail;
	}
	
	public boolean isSendEmail() {
		return (toMail!=null && toMail.size()>0); 
	}
	
	private List<String> createToMail() {
		if (inputData.isEnvioCorreoGroup()) {
			return (CorreosGroupDAO.getCorreosGroup(inputData.getEnvioCorreo()));
		} else {
			return (Arrays.asList("eqp.ecommerce.qamango@mango.com"));
		}
	}
	
	public static SuiteTestMaker getTestMakerSuite(ISuite suite) {
		return (SuiteTestMaker)suite.getXmlSuite();
	}
	
	public static TestMakerContext getTestMakerContext(ITestContext ctxTng) {
		return (getTestMakerContext(ctxTng.getSuite()));
	}
	
	public static TestMakerContext getTestMakerContext(ISuite suite) {
    	SuiteTestMaker suiteXML = getTestMakerSuite(suite);
    	return (suiteXML.getTestMakerContext());
	}
	
    private static String getIdForSuiteToExecute() {
        Calendar c1 = Calendar.getInstance();
        String timestamp = new SimpleDateFormat("yyMMdd_HHmmssSS").format(c1.getTime());
        return (timestamp);
    }
}
