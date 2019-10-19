package com.mng.testmaker.domain;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.mng.testmaker.boundary.aspects.step.SaveWhen;
import com.mng.testmaker.boundary.aspects.validation.ChecksResult;
import com.mng.testmaker.utils.NetTrafficMng;
import com.mng.testmaker.utils.State;

public class StepTestMaker {
	
	private final SuiteTestMaker suiteParent;
	private final TestRunTestMaker testRunParent;
	private final TestCaseTestMaker testCaseParent;
	private final List<ChecksResult> listChecksResult = new ArrayList<>();
			
	private StateRun state = StateRun.Started;
	private boolean isStateUpdated = false;
	private String descripcion; 
	private String res_expected; 
	private SaveWhen saveImagePage = SaveWhen.IfProblem;
	private SaveWhen saveErrorPage = SaveWhen.IfProblem;
	private SaveWhen saveHtmlPage = SaveWhen.Never;
	private SaveWhen saveNettraffic = SaveWhen.Never;
	private int type_page = 0; 
	private Date hora_inicio; 
	private Date hora_fin;
	private State result_steps = State.Ok;
	private boolean excep_exists = true;
	private String nameMethodWithFactory = "";
	
    public enum StepEvidence {
    	imagen("png"), 
    	html("html"), 
    	errorpage("-error.html"), 
    	har("har"), 
    	harp("harp");
    	
    	public String fileExtension;
    	private StepEvidence(String fileExtension) {
    		this.fileExtension = fileExtension;
    	}
    }
    
	public StepTestMaker() {
		testCaseParent = TestCaseTestMaker.getTestCaseInExecution();
		testRunParent = testCaseParent.getTestRunParent();
		suiteParent = testRunParent.getSuiteParent();
	}
	
	public SuiteTestMaker getSuiteParent() {
		return suiteParent;
	}
	public TestRunTestMaker getTestRunParent() {
		return testRunParent;
	}
	public TestCaseTestMaker getTestCaseParent() {
		return testCaseParent;
	}
	
	public int getPositionInTestCase() {
		List<StepTestMaker> listSteps = testCaseParent.getStepsList();
		for (int i=0; i<listSteps.size(); i++) {
			if (listSteps.get(i)==this) {
				return i+1;
			}
		}
		return -1;
	}
	
    public void setState(StateRun state) {
    	this.state = state;
    }
    
    public StateRun getState() {
    	return this.state;
    }

    public void setDescripcion(String c_descripcion) { 
        this.descripcion = c_descripcion; 
    }
    
    public void replaceInDescription(String oldChar, String newChar) {
    	this.descripcion = this.descripcion.replace(oldChar, newChar);
    }
    
    public void addDescriptionText(String text) {
    	this.descripcion+=text;
    }
    
    public void addRightDescriptionText(String text) {
    	this.descripcion = text + this.descripcion;
    }
    
    public void addExpectedText(String text) {
    	this.res_expected+=text;
    }
    
    public void replaceInExpected(String oldChar, String newChar) {
    	this.res_expected = this.res_expected.replace(oldChar, newChar);
    }
    
    public void setResExpected(String c_res_expected) { 
        this.res_expected = c_res_expected; 
    }
    
    public void setSaveImagePage(SaveWhen saveImagePage) { 
        this.saveImagePage = saveImagePage; 
    }
    
    public void setSaveErrorPage(SaveWhen saveErrorPage) { 
        this.saveErrorPage = saveErrorPage; 
    }
    
    public void setSaveHtmlPage(SaveWhen saveHtmlPage) { 
        this.saveHtmlPage = saveHtmlPage; 
    }
    
    public void setSaveNettrafic(SaveWhen saveNettraffic) {
        if (suiteParent.getInputParams().isNetAnalysis()) {
        	this.saveNettraffic = saveNettraffic;
        	NetTrafficMng netTraffic = new NetTrafficMng();
        	netTraffic.resetAndStartNetTraffic();
        }
    }    
    
    public void setTypePage(int c_type_page) { 
        this.type_page = c_type_page; 
    }
    
    public void setHoraInicio(Date c_hora_inicio) { 
        this.hora_inicio = c_hora_inicio; 
    }
    
    public void setHoraFin(Date c_hora_fin) {
        this.hora_fin = c_hora_fin; 
    }
    
    public void setResultSteps(State c_result_steps) {
        this.result_steps = c_result_steps; 
        this.isStateUpdated = true;
    }
    
    public boolean isStateUpdated() {
    	return this.isStateUpdated;
    }

    public void setExcepExists(boolean c_excep_exists) {
        this.excep_exists = c_excep_exists; 
    }
    
    public String getDescripcion() { 
        return this.descripcion; 
    }
    
    public String getResExpected() { 
        return this.res_expected; 
    }
    
    public SaveWhen getWhenSave(StepEvidence evidencia) {
    	switch (evidencia) {
    	case html:
    		return saveHtmlPage;
    	case errorpage:
    		return saveErrorPage;
    	case har:
    	case harp:
    		return saveNettraffic;
    	case imagen:
    	default:
    		return saveImagePage;
    	}
    } 
    
    public boolean isAllValidationsWithAvoidEvidences() {
    	if (listChecksResult==null || listChecksResult.isEmpty()) {
    		return false;
    	}
    	for (ChecksResult validation : listChecksResult) {
    		if (!validation.isAvoidEvidences()) {
    			return false;
    		}
    	}
    	return true;
    }
    
    public int getTypePage() { 
        return this.type_page; 
    }
    
    public Date getHoraInicio() { 
        return this.hora_inicio; 
    }
    
    public Date getHoraFin() { 
        return this.hora_fin; 
    }
    
    public State getResultSteps() { 
        return this.result_steps; 
    }
    
    public boolean getExcepExists() { 
        return this.excep_exists; 
    }
    
    public void setNOKstateByDefault() {
    	setExcepExists(true); 
    	//setResultSteps(State.Nok);
    }
    
    public void setNameMethodWithFactory(String nameMethodWithFactory) {
    	this.nameMethodWithFactory = nameMethodWithFactory;
    }
    
    public String getNameMethodWithFactory() {
    	return this.nameMethodWithFactory;
    }
    
    public void addChecksResult(ChecksResult checksResult) {
    	setExcepExists(false);
    	this.listChecksResult.add(checksResult);
    }
    
    public int getNumChecksResult() {
    	return getListChecksResult().size();
    }
    
    public List<ChecksResult> getListChecksResult() {
    	return listChecksResult;
    }
}
