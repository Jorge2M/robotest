package com.mng.robotest.test80.arq.annotations;

import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;

@SuppressWarnings("javadoc")
public class ValidationResult {
	private String description = "";
    private State levelError = State.Undefined;
    boolean overcomed = false;
    private DatosStep datosStep; //Creo que se puede eliminar
    
    public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public State getLevelError() {
		return levelError;
	}
	public void setLevelError(State levelError) {
		this.levelError = levelError;
	}
    public boolean isOvercomed() {
		return overcomed;
	}
	public void setOvercomed(boolean overcomed) {
		this.overcomed = overcomed;
	}
	public DatosStep getDatosStep() {
		return datosStep;
	}
	public void setDatosStep(DatosStep datosStep) {
		this.datosStep = datosStep;
	}
}
