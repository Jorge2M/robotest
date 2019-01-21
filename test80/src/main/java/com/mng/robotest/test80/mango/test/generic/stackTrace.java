package com.mng.robotest.test80.mango.test.generic;

@SuppressWarnings("javadoc")
public class stackTrace {
    private boolean existe;
    private boolean repetida;
    private String exception;
    private int numExcepciones;
    
    public boolean getExiste() {
        return this.existe;
    }
    
    public boolean getRepetida() {
        return this.repetida;
    }
    
    public String getException() {
        return this.exception;
    }
    
    public int getNumExcepciones() {
        return this.numExcepciones;
    }    
    
    public void setExiste(boolean existe) {
        this.existe = existe;
    }
    
    public void setRepetida(boolean repetida) {
        this.repetida = repetida;
    }
    
    public void setException(String exception) {
        this.exception = exception;
    }
    
    public void setNumExcepciones(int numExcepciones) {
        this.numExcepciones = numExcepciones;
    }
}
