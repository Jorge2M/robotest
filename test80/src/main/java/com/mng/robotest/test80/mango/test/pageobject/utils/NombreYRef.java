package com.mng.robotest.test80.mango.test.pageobject.utils;


public class NombreYRef {

    String nombre = "";
    String referencia = "";
    
    public NombreYRef(String nombre, String referencia) {
        this.nombre = nombre;
        this.referencia = referencia;
    }
    
    public String getNombre() {
        return this.nombre;
    }
    
    public String getReferencia() {
        return this.referencia;
    }
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof NombreYRef))
            return false;
        
        NombreYRef otherObject = (NombreYRef)o;
        if (this.nombre.compareTo(otherObject.getNombre())==0 &&
            this.referencia.compareTo(otherObject.getReferencia())==0)
            return true;
        
        return false;    
    }
    
    @Override
    public int hashCode() {
        assert false : "hashCode not designed";
        return 42; // any arbitrary constant will do
    }

    @Override
    public String toString() {
        return (this.nombre + " (" + this.referencia + ")");
    }
}
