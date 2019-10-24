package com.mng.testmaker.domain.data;

import java.util.Date;

import com.mng.testmaker.conf.State;
import com.mng.testmaker.domain.SuiteTestMaker;
import com.mng.testmaker.domain.TestCaseTestMaker;

public class TestCaseData {

	private String idExecSuite;
	private String suiteName;
    private String testRunName;
    private String name;
    private String nameUnique;
    private String description;
    private int indexInTestRun;
    private State result;
    private Date inicioDate;
    private Date finDate; 
    private float durationMillis;
    private int numberSteps;
    private String classSignature;
    private String instance;
    
	public String getIdExecSuite() {
		return idExecSuite;
	}
	public void setIdExecSuite(String idExecSuite) {
		this.idExecSuite = idExecSuite;
	}
	public String getSuiteName() {
		return suiteName;
	}
	public void setSuiteName(String suiteName) {
		this.suiteName = suiteName;
	}
	public String getTestRunName() {
		return testRunName;
	}
	public void setTestRunName(String testRunName) {
		this.testRunName = testRunName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNameUnique() {
		return nameUnique;
	}
	public void setNameUnique(String nameUnique) {
		this.nameUnique = nameUnique;
	}
	public int getIndexInTestRun() {
		return indexInTestRun;
	}
	public void setIndexInTestRun(int indexInTestRun) {
		this.indexInTestRun = indexInTestRun;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public State getResult() {
		return result;
	}
	public void setResult(State result) {
		this.result = result;
	}
	public Date getInicioDate() {
		return inicioDate;
	}
	public void setInicioDate(Date inicioDate) {
		this.inicioDate = inicioDate;
	}
	public Date getFinDate() {
		return finDate;
	}
	public void setFinDate(Date finDate) {
		this.finDate = finDate;
	}
	public float getDurationMillis() {
		return durationMillis;
	}
	public void setDurationMillis(float durationMillis) {
		this.durationMillis = durationMillis;
	}
	public int getNumberSteps() {
		return numberSteps;
	}
	public void setNumberSteps(int numberSteps) {
		this.numberSteps = numberSteps;
	}
	public String getClassSignature() {
		return classSignature;
	}
	public void setClassSignature(String classSignature) {
		this.classSignature = classSignature;
	}
	public String getInstance() {
		return instance;
	}
	public void setInstance(String instance) {
		this.instance = instance;
	}
    
	public static TestCaseData from(TestCaseTestMaker testCase) {
		TestCaseData testCaseData = new TestCaseData();
		SuiteTestMaker suite = testCase.getSuiteParent();
		
		testCaseData.setIdExecSuite(suite.getIdExecution());
		testCaseData.setSuiteName(suite.getName());
		testCaseData.setTestRunName(testCase.getTestRunParent().getName());
		testCaseData.setName(testCase.getNameUnique());
		testCaseData.setNameUnique(testCase.getNameUnique());
		testCaseData.setDescription(testCase.getResult().getMethod().getDescription());
		testCaseData.setIndexInTestRun(testCase.getIndexInTestRun());
		testCaseData.setResult(testCase.getStateResult());
		
        Date inicio = new Date(testCase.getResult().getStartMillis());
        Date fin = new Date(testCase.getResult().getEndMillis());
		testCaseData.setInicioDate(inicio);
		testCaseData.setFinDate(fin); 
		testCaseData.setDurationMillis(fin.getTime() - inicio.getTime());
		
		testCaseData.setNumberSteps(testCase.getStepsList().size());
		testCaseData.setClassSignature(testCase.getResult().getMethod().getClass().getName());
		testCaseData.setInstance(testCase.getResult().getInstanceName());
		
		return testCaseData;
	}
	
}
