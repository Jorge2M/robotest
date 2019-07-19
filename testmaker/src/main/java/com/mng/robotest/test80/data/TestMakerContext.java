package com.mng.robotest.test80.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mng.robotest.test80.arq.jdbc.dao.CorreosGroupDAO;
import com.mng.robotest.test80.arq.xmlprogram.InputDataTestMaker;

public class TestMakerContext {

	private final InputDataTestMaker inputData;
	private final List<String> toMail;
	private final List<String> ccMail;
	private final String subjectMail;
	private String idSuiteExecution;
	
	private TestMakerContext(InputDataTestMaker inputData) {
		this.inputData = inputData;
		toMail = createToMail();
		ccMail = new ArrayList<>();
		ccMail.add("jorge.munoz.sge@mango.com");
		subjectMail = "Result TestSuite " + inputData.getNameSuite() + " (" + inputData.getApp() + " / " + inputData.getUrlBase() + ")";
	}
	
	public static TestMakerContext getNew(InputDataTestMaker inputData) {
		return (new TestMakerContext(inputData));
	}
	
	private List<String> createToMail() {
		if (inputData.isEnvioCorreoGroup()) {
			return (CorreosGroupDAO.getCorreosGroup(inputData.getEnvioCorreo()));
		} else {
			return (Arrays.asList("eqp.ecommerce.qamango@mango.com"));
		}
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
}
