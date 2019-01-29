package com.mng.robotest.test80.arq.annotations;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;

@SuppressWarnings("javadoc")
public class ValidationResult {
	private String description = "";
    private State levelError = State.Undefined;
    private boolean resultOk = false; //?
    private DatosStep datosStep; //Creo que se puede eliminar
    private DataFmwkTest dFTest; //Creo que se puede eliminar y coger directamente de los parámetros de la función
    
	
    public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isResultOk() {
		return resultOk;
	}
	public void setResultOk(boolean resultOk) {
		this.resultOk = resultOk;
	}
	public DatosStep getDatosStep() {
		return datosStep;
	}
	public void setDatosStep(DatosStep datosStep) {
		this.datosStep = datosStep;
	}
	public State getLevelError() {
		return levelError;
	}
	public void setLevelError(State levelError) {
		this.levelError = levelError;
	}
	public DataFmwkTest getdFTest() {
		return dFTest;
	}
	public void setdFTest(DataFmwkTest dFTest) {
		this.dFTest = dFTest;
	}
}
