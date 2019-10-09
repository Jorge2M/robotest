package com.mng.testmaker.boundary.aspects.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.mng.testmaker.domain.StepTestMaker;
import com.mng.testmaker.domain.SuiteTestMaker;
import com.mng.testmaker.domain.TestCaseTestMaker;
import com.mng.testmaker.domain.TestRunTestMaker;
import com.mng.testmaker.utils.State;
import com.mng.testmaker.utils.controlTest.FmwkTest;

public class ChecksResult {
	private final List<ResultValidation> listResultValidations;
	private State stateValidation = State.Nok;
	private boolean avoidEvidences;
	private final SuiteTestMaker suiteParent;
	private final TestRunTestMaker testRunParent;
	private final TestCaseTestMaker testCaseParent;
	private final StepTestMaker stepParent; 
    private String descripcionValidations;
	
    public ChecksResult() {
    	this.listResultValidations = new ArrayList<>();
    	this.testCaseParent = TestCaseTestMaker.getTestCaseInThread();
    	this.stepParent = testCaseParent.getLastStepFinished();
    	this.testRunParent = testCaseParent.getTestRunParent();
    	this.suiteParent = testCaseParent.getSuiteParent();
    }
    
	private ChecksResult(StepTestMaker stepParent) {
		listResultValidations = new ArrayList<>();
		this.stepParent = stepParent;
		this.testCaseParent = stepParent.getTestCaseParent();
		this.testRunParent = stepParent.getTestRunParent();
		this.suiteParent = stepParent.getSuiteParent();
	}
	
	public static ChecksResult getNew(StepTestMaker stepParent) {
		return (new ChecksResult(stepParent));
	}
	
	public static ChecksResult getNew() {
		return (new ChecksResult());
	}
	
	public static ChecksResult of(ResultValidation resultValidation, StepTestMaker datosStep) {
		ChecksResult listValidations = new ChecksResult(datosStep);
		listValidations.add(resultValidation);
		return listValidations;
	}

	public SuiteTestMaker getSuiteParent() {
		return this.suiteParent;
	}
	public TestRunTestMaker getTestRunParent() {
		return this.testRunParent;
	}
	public TestCaseTestMaker getTestCaseParent() {
		return this.testCaseParent;
	}
	public StepTestMaker getStepParent() {
		return this.stepParent;
	}
	
	public int size() {
		return listResultValidations.size();
	}
	
	public ResultValidation get(int index) {
		return (listResultValidations.get(index));
	}
	
    public boolean isAvoidEvidences() {
		return avoidEvidences;
	}
    
	public void setAvoidEvidences(boolean avoidEvidences) {
		this.avoidEvidences = avoidEvidences;
	}
	
	public void add(ResultValidation resultValidation) {
		listResultValidations.add(resultValidation);
	}
	
	public void add(String description, boolean overcomed, State levelResult) {
		add(description, overcomed, levelResult, false);
	}
	
	public void add(String description, boolean overcomed, State levelResult, boolean avoidEvidences) {
		int id = listResultValidations.size() + 1;
		ResultValidation resultValidation = ResultValidation.of(id, id + ") " + description, overcomed, levelResult, avoidEvidences);
		add(resultValidation);
	}
	
	public void add(int id, State levelResult) {
		ResultValidation resultValidation = ResultValidation.of(id, levelResult);
		add(resultValidation);
	}
	
    public void checkAndStoreValidations() {
    	checkValidations();
    	if ("".compareTo(descripcionValidations)!=0) {
    		FmwkTest.grabStepValidation(stepParent, descripcionValidations, TestCaseData.getdFTest());
    	}
    }
    
    public State calculateStateValidation() {
    	State stateToReturn;
    	if (isStepFinishedWithException()) {
    		return State.Nok;
    	}
    	
    	stateToReturn = State.Ok;
    	for (ResultValidation resultValidation : listResultValidations) {
    		if (!resultValidation.isOvercomed()) {
    			//sound();
    			State criticityValidation = resultValidation.getLevelResult();
    			if (criticityValidation.isMoreCriticThan(stateToReturn)) {
    				stateToReturn = criticityValidation;
    			}
    		}
    	}
    	
    	return stateToReturn;
    }
    
    public boolean calculateAvoidEvidences() {
    	for (ResultValidation resultValidation : listResultValidations) {
    		if (!resultValidation.isOvercomed() &&
    			resultValidation.getLevelResult()!=State.Ok &&
    			!resultValidation.isAvoidEvidences()) {
    			return false;
    		}
    	}
    	
    	return true;
    }
    
    private void checkValidations() {
    	checkAndSetStateValidation();
    	setDatosStepAfterCheckValidation();
    }
    
    private void checkAndSetStateValidation() {
    	stateValidation = calculateStateValidation();
    	descripcionValidations=calculateDescriptionValidation();
    	avoidEvidences=calculateAvoidEvidences();
    }
    
    private String calculateDescriptionValidation() {
    	List<String> textValidations = new ArrayList<>();
    	for (ResultValidation resultValidation : listResultValidations) {
    		textValidations.add(resultValidation.getDescription());
    	}

    	return (textValidations.stream().collect(Collectors.joining("<br>")));
    }
    
    private void setDatosStepAfterCheckValidation() {
    	stepParent.setAvoidEvidences(avoidEvidences);
    	if (stateValidation.isMoreCriticThan(stepParent.getResultSteps()) || !stepParent.isStateUpdated()) {
    		stepParent.setResultSteps(stateValidation);
    	}
    	stepParent.setListResultValidations(this);
    }
    
    private boolean isStepFinishedWithException() {
    	return (stepParent.getHoraFin()!=null && stepParent.getExcepExists());
    }
    
    /**
     * @return la lista ordenada de resultado de las validaciones que se pueda almacenar en BD
     */
    public List<State> getListStateValidations() {
    	List<State> listCodes = new ArrayList<>();
    	int lastValidation = getIndexLastValidation();
    	for (int i=0; i<lastValidation; i++) {
    		listCodes.add(State.Ok);
    	}
    	for (ResultValidation resultValidation : listResultValidations) {
    		if (!resultValidation.isOvercomed()) {
    			listCodes.set(resultValidation.getId()-1, resultValidation.getLevelResult());
    		}
    	}
    	
    	return listCodes;
    }
    
	private int getIndexLastValidation() {
		int maxIndexValidation = 0;
		for (ResultValidation resultValidation : listResultValidations) {
			if (resultValidation.getId() > maxIndexValidation) {
				maxIndexValidation = resultValidation.getId();
			}
		}
		
		return maxIndexValidation;
	}
}
