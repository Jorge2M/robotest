package com.mng.robotest.test80.arq.annotations.validation;

import com.mng.robotest.test80.arq.utils.State;


@SuppressWarnings("javadoc")
public class ResultValidation {
	//TODO este campo es prescindible cuando esté completada la migración a Aspectos
    private final int id;
    
	private String description = "";
    private State levelResult = State.Undefined;
    boolean overcomed = false;
    
    public ResultValidation(int id) {
    	this.id = id;
    }
    
    public static ResultValidation of(int id, String description, boolean overcomed, State levelResult) {
    	ResultValidation resultValidation = of(id, levelResult);
    	resultValidation.setDescription(description);
    	resultValidation.setOvercomed(overcomed);
    	return resultValidation;
    }
    
    public static ResultValidation of(int id, State levelResult) {
    	ResultValidation resultValidation = new ResultValidation(id);
    	resultValidation.setLevelResult(levelResult);
    	return resultValidation;
    }
    
    public int getId() {
		return id;
	}

    public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public State getLevelResult() {
		return levelResult;
	}
	public void setLevelResult(State levelError) {
		this.levelResult = levelError;
	}
    public boolean isOvercomed() {
		return overcomed;
	}
	public void setOvercomed(boolean overcomed) {
		this.overcomed = overcomed;
	}
}
