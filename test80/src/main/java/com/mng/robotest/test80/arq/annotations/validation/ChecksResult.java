package com.mng.robotest.test80.arq.annotations.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;

public class ChecksResult {
	private final List<ResultValidation> listResultValidations;
	private State stateValidation = State.Nok;
	private boolean avoidEvidences;
	private final DatosStep datosStep; //TODO Creo que se puede llegar eliminar
    private String descripcionValidations;
	
    public ChecksResult() {
    	DatosStep datosStep = TestCaseData.getDatosLastStep();
    	listResultValidations = new ArrayList<>();
    	this.datosStep = datosStep;
    }
    
	private ChecksResult(DatosStep datosStep) {
		listResultValidations = new ArrayList<>();
		this.datosStep = datosStep;
	}
	
	public static ChecksResult getNew(DatosStep datosStep) {
		return (new ChecksResult(datosStep));
	}
	
	public static ChecksResult getNew() {
		return (new ChecksResult());
	}
	
	public static ChecksResult of(ResultValidation resultValidation, DatosStep datosStep) {
		ChecksResult listValidations = new ChecksResult(datosStep);
		listValidations.add(resultValidation);
		return listValidations;
	}

	
	public int size() {
		return listResultValidations.size();
	}
	
	public ResultValidation get(int index) {
		return (listResultValidations.get(index));
	}
	
	public DatosStep getDatosStep() {
		return this.datosStep;
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
    	storeGroupValidations(descripcionValidations);
    }
    
    public State calculateStateValidation() {
    	State stateToReturn;
    	if (isStepFinishedWithException()) {
    		return State.Nok;
    	}
    	
    	stateToReturn = State.Ok;
    	for (ResultValidation resultValidation : listResultValidations) {
    		if (!resultValidation.isOvercomed()) {
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
    	datosStep.setResultLastValidation(stateValidation);
    	datosStep.setAvoidEvidences(avoidEvidences);
    	if (stateValidation.isMoreCriticThan(datosStep.getResultSteps()) || !datosStep.isStateUpdated()) {
    		datosStep.setResultSteps(stateValidation);
    	}
    	datosStep.setListResultValidations(this);
    	datosStep.setNumValidations(datosStep.getNumValidations() + 1);
    }
    
    private boolean isStepFinishedWithException() {
    	return (datosStep.getHoraFin()!=null && datosStep.getExcepExists());
    }
    
    /**
     * @return la lista ordenada de resultado de las validaciones que se pueda almacenar en BD
     */
    public List<Integer> getListCodeNumStateValidations() {
    	//List initialized with OKs
    	List<Integer> listCodes = new ArrayList<>();
    	int lastValidation = getIndexLastValidation();
    	for (int i=0; i<lastValidation; i++) {
    		listCodes.add(State.Ok.getIdNumerid());
    	}
    	for (ResultValidation resultValidation : listResultValidations) {
    		if (!resultValidation.isOvercomed()) {
    			listCodes.set(resultValidation.getId()-1, resultValidation.getLevelResult().getIdNumerid());
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
    
    public void storeGroupValidations(String descripcionValidations) {
    	if ("".compareTo(descripcionValidations)!=0) {
    		fmwkTest.grabStepValidation(datosStep, descripcionValidations, TestCaseData.getdFTest());
    	}
    }
}
