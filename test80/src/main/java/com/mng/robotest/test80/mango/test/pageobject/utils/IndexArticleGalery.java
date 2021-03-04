package com.mng.robotest.test80.mango.test.pageobject.utils;


public class IndexArticleGalery {

    private final String nombre;
    private final String referencia;
    private final String imagen;
    
    
    public IndexArticleGalery(String nombre, String referencia, String imagen) {
        this.nombre = nombre;
        this.referencia = referencia;
        this.imagen = imagen;
    }
    
    public String getNombre() {
        return this.nombre;
    }
    
    public String getReferencia() {
        return this.referencia;
    }
    
    public String getImagen() {
    	return this.imagen;
    }
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof IndexArticleGalery)) {
            return false;
        }
        
        IndexArticleGalery otherObject = (IndexArticleGalery)o;
        return (
        	this.nombre.compareTo(otherObject.getNombre())==0 &&
            this.referencia.compareTo(otherObject.getReferencia())==0 &&
            this.imagen.compareTo(otherObject.imagen)==0);   
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
