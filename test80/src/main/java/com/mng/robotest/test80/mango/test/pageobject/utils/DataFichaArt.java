package com.mng.robotest.test80.mango.test.pageobject.utils;

@SuppressWarnings("javadoc")
public class DataFichaArt {
    private String refArticulo = "";
    private String nombreArticulo = "";
    
    public DataFichaArt() {
        
    }
    
    public DataFichaArt(String referencia, String nombre) {
        this.refArticulo = referencia;
        this.nombreArticulo = nombre;
    }
    
    public String getReferencia() {
        return this.refArticulo;
    }
    
    public void setReferencia(String referencia) {
        this.refArticulo = referencia;
    }
    
    public String getNombre() {
        return this.nombreArticulo;
    }
    
    public void setNombre(String nombre) {
        this.nombreArticulo = nombre;
    }
    
    public boolean availableReferencia() {
        return ("".compareTo(this.refArticulo)!=0);
    }
    
    public boolean availableNombre() {
        return ("".compareTo(this.nombreArticulo)!=0);
    }
}
