package com.mng.testmaker.boundary.aspects.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.mng.testmaker.conf.State;
import com.mng.testmaker.domain.StepTM;
import com.mng.testmaker.domain.SuiteTM;
import com.mng.testmaker.domain.TestCaseTM;
import com.mng.testmaker.domain.TestRunTM;
import com.mng.testmaker.domain.util.ParsePathClass;

public class ChecksResult {
	private final List<ResultValidation> listResultValidations;
	private State stateValidation = State.Ok;
	private boolean avoidEvidences;
	private final SuiteTM suiteParent;
	private final TestRunTM testRunParent;
	private final TestCaseTM testCaseParent;
	private final StepTM stepParent; 
	private String pathMethod;
	
    public ChecksResult() {
    	this.listResultValidations = new ArrayList<>();
    	this.testCaseParent = TestCaseTM.getTestCaseInExecution();
    	this.stepParent = testCaseParent.getLastStep();
    	this.testRunParent = testCaseParent.getTestRunParent();
    	this.suiteParent = testCaseParent.getSuiteParent();
    }
    
	private ChecksResult(StepTM stepParent) {
		listResultValidations = new ArrayList<>();
		this.stepParent = stepParent;
		this.testCaseParent = stepParent.getTestCaseParent();
		this.testRunParent = stepParent.getTestRunParent();
		this.suiteParent = stepParent.getSuiteParent();
	}
	
	public static ChecksResult getNew(StepTM stepParent) {
		return (new ChecksResult(stepParent));
	}
	
	public static ChecksResult getNew() {
		return (new ChecksResult());
	}
	
	public static ChecksResult of(ResultValidation resultValidation, StepTM datosStep) {
		ChecksResult listValidations = new ChecksResult(datosStep);
		listValidations.add(resultValidation);
		return listValidations;
	}

	public List<ResultValidation> getListResultValidations() {
		return listResultValidations;
	}
	
	public State getStateValidation() {
		return stateValidation;
	}
	
	public SuiteTM getSuiteParent() {
		return this.suiteParent;
	}
	public TestRunTM getTestRunParent() {
		return this.testRunParent;
	}
	public TestCaseTM getTestCaseParent() {
		return this.testCaseParent;
	}
	public StepTM getStepParent() {
		return this.stepParent;
	}
	public String getPathMethod() {
		return pathMethod;
	}
	public void setPathMethod(String pathMethod) {
		this.pathMethod = pathMethod;
	}
    public String getPathClass() {
    	return ParsePathClass.getPathClass(getPathMethod());
    }
    public String getNameClass() {
    	return ParsePathClass.getNameClass(getPathClass());
    }
    public String getNameMethod() {
    	return ParsePathClass.getNameMethod(getPathMethod());
    }
	
	public int size() {
		return listResultValidations.size();
	}
	
	public ResultValidation get(int index) {
		return (listResultValidations.get(index));
	}
	
	public int getPositionInStep() {
		List<ChecksResult> listChecksResultInStep = stepParent.getListChecksResult();
		for (int i=0; i<listChecksResultInStep.size(); i++) {
			ChecksResult checksResult = listChecksResultInStep.get(i);
			if (checksResult==this) {
				return i+1;
			}
		}
		return -1;
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
    
    public void checkValidations() {
    	checkAndSetStateValidation();
    	setDatosStepAfterCheckValidation();
    }
    
    private void checkAndSetStateValidation() {
    	stateValidation = calculateStateValidation();
    	avoidEvidences=calculateAvoidEvidences();
    }
    
    public String getTextValidationsBrSeparated() {
    	List<String> textValidations = new ArrayList<>();
    	for (ResultValidation resultValidation : listResultValidations) {
    		textValidations.add(resultValidation.getDescription());
    	}

    	return (textValidations.stream().collect(Collectors.joining("<br>")));
    }
    
    public String getHtmlValidationsBrSeparated() {
    	List<String> textValidations = new ArrayList<>();
    	for (ResultValidation resultValidation : listResultValidations) {
    		String htmlValidation = 
    			"<validac style=\"color:" + resultValidation.getStateResult().getColorCss() + "\">" + 
    			resultValidation.getDescription() + 
    			"</validac>";
    		textValidations.add(htmlValidation);
    	}

    	return (textValidations.stream().collect(Collectors.joining("<br>")));
    }
    
    private void setDatosStepAfterCheckValidation() {
    	if (stateValidation.isMoreCriticThan(stepParent.getResultSteps()) || !stepParent.isStateUpdated()) {
    		stepParent.setResultSteps(stateValidation);
    	}
    	//stepParent.setChecksResult(this);
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
