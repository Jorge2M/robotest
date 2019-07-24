package com.mng.robotest.test80.data;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.testng.ISuite;
import org.testng.ITestContext;

import com.mng.robotest.test80.arq.xmlprogram.InputDataTestMaker;
import com.mng.robotest.test80.arq.xmlprogram.SuiteTestMaker;
import com.mng.robotest.test80.arq.xmlprogram.TestRunTestMaker;

public class TestMakerContext {

	private final String idSuiteExecution;
	private final InputDataTestMaker inputData;
	private final MailEndSuite sendMailData;

	private TestMakerContext(InputDataTestMaker inputData) {
		this.idSuiteExecution = getIdForSuiteToExecute();
		this.inputData = inputData;
		this.sendMailData = MailEndSuite.getNew(inputData);
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
	
	public boolean isSendMailInEndSuite() {
		return (sendMailData!=null);
	}
	
	public MailEndSuite getSendMailData() {
		return this.sendMailData;
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
	
	public static TestRunTestMaker getTestRun(ITestContext ctxTng) {
		return ((TestRunTestMaker)ctxTng.getCurrentXmlTest());
	}
	
    private static String getIdForSuiteToExecute() {
        Calendar c1 = Calendar.getInstance();
        String timestamp = new SimpleDateFormat("yyMMdd_HHmmssSS").format(c1.getTime());
        return (timestamp);
    }
}
