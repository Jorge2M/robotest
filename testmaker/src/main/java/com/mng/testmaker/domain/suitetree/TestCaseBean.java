package com.mng.testmaker.domain.suitetree;

import java.util.Date;
import java.util.List;

import com.mng.testmaker.conf.State;

public class TestCaseBean {

	private List<StepTM> listStep;
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

	public List<StepTM> getListStep() {
		return listStep;
	}
	public void setListStep(List<StepTM> listStep) {
		this.listStep = listStep;
	}
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
}
