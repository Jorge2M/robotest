package com.mng.robotest.test80.arq.annotations.validation;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.arq.utils.controlTest.mango.GestorWebDriver;

@SuppressWarnings("javadoc")
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
	
	public static ListResultValidation of(ResultValidation resultValidation, DatosStep datosStep) {
		ListResultValidation listValidations = new ListResultValidation(datosStep);
		listValidations.add(resultValidation);
		return listValidations;
	}
	
	public ResultValidation get(int index) {
		return (listResultValidations.get(index));
	}
	
	public void add(ResultValidation resultValidation) {
		listResultValidations.add(resultValidation);
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
    	if (datosStep.getExcepExists()) {
    		stateValidation = State.Nok;
    	}
    	else {
	    	stateValidation = State.Ok;
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
	    	datosStep.setListResultValidations(this);
    	}
    }
    
    /**
     * @return la lista ordenada de resultado de las validaciones que se pueda almacenar en BD
     */
    public List<Integer> getListCodeNumStateValidations() {
    	List<Integer> listCodes = new ArrayList<>();
    	for (ResultValidation resultValidation : listResultValidations) {
    		listCodes.add(resultValidation.getLevelResult().getIdNumerid());
    	}
    	
    	return listCodes;
    }
    
    public void storeGroupValidations(String descripcionValidations) {
    	fmwkTest.grabStepValidation(datosStep, descripcionValidations, GestorWebDriver.getdFTest());
    }
}
