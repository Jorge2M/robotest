package com.mng.robotest.test80.arq.annotations.validation;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;


public class ListResultValidation {
	private final List<ResultValidation> listResultValidations;
	private State stateValidation = State.Nok;
    private final DatosStep datosStep; //TODO Creo que se puede llegar eliminar
    private String descripcionValidations;
	
	private ListResultValidation(DatosStep datosStep) {
		listResultValidations = new ArrayList<>();
		this.datosStep = datosStep;
	}
	
	public static ListResultValidation getNew(DatosStep datosStep) {
		return (new ListResultValidation(datosStep));
	}
	
	public static ListResultValidation getNew() {
		DatosStep datosStep = TestCaseData.getDatosStepForValidation();
		return (getNew(datosStep));
	}
	
	public static ListResultValidation of(ResultValidation resultValidation, DatosStep datosStep) {
		ListResultValidation listValidations = new ListResultValidation(datosStep);
		listValidations.add(resultValidation);
		return listValidations;
	}
	
	public ResultValidation get(int index) {
		return (listResultValidations.get(index));
	}
	
	public DatosStep getDatosStep() {
		return this.datosStep;
	}
	
	public void add(ResultValidation resultValidation) {
		listResultValidations.add(resultValidation);
	}
	
	public void add(String description, boolean overcomed, State levelResult) {
		int id = listResultValidations.size() + 1;
		ResultValidation resultValidation = ResultValidation.of(id, id + ") " + description, overcomed, levelResult);
		add(resultValidation);
	}
	
	public void add(int id, State levelResult) {
		ResultValidation resultValidation = ResultValidation.of(id, levelResult);
		add(resultValidation);
	}
	
	public void checkAndStoreValidations(String descripcionValidation) {
		checkValidations();
		this.descripcionValidations = descripcionValidation;
		storeGroupValidations(descripcionValidations);
	}
	
    public void checkAndStoreValidations() {
    	checkValidations();
    	storeGroupValidations(descripcionValidations);
    }
    
    private void checkValidations() {
    	if (isStepFinishedWithException()) {
    		stateValidation = State.Nok;
    	}
    	else {
	    	stateValidation = State.Ok;
    	}
    	
    	descripcionValidations = "";
    	for (ResultValidation resultValidation : listResultValidations) {
    		descripcionValidations+=resultValidation.getDescription();
    		if (!resultValidation.isOvercomed()) {
    			State criticityValidation = resultValidation.getLevelResult();
    			if (criticityValidation.isMoreCriticThan(stateValidation)) {
    				stateValidation = criticityValidation;
    			}
    		}
    	}
    	
    	datosStep.setResultSteps(stateValidation);
    	datosStep.setResultLastValidation(State.getMoreCritic(stateValidation, datosStep.getResultSteps()));
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
    	fmwkTest.grabStepValidation(datosStep, descripcionValidations, TestCaseData.getdFTest());
    }
}
