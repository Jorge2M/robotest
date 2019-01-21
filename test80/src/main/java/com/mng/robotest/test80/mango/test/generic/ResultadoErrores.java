package com.mng.robotest.test80.mango.test.generic;

import java.util.ArrayList;

@SuppressWarnings("javadoc")
public class ResultadoErrores {

    public enum Resultado {OK, ERRORES, MAX_ERRORES}
	
    private Resultado resultado = Resultado.OK;
    private ArrayList<String> listaLogError;
	
    public Resultado getResultado() {
        return this.resultado;
    }
	
    public void setResultado(Resultado resultado) {
        this.resultado = resultado;
    }
	
    public ArrayList<String> getlistaLogError() {
        return this.listaLogError;
    }
	
    public void setListaLogError(ArrayList<String> listaLogError) {
        this.listaLogError = listaLogError;
    }
	
    public boolean isOK() {
        return (getResultado() == Resultado.OK);
    }
}
