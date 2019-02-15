package com.mng.robotest.test80.mango.test.jdbc.to;

import java.sql.Date;

/**
 * Almacena los datos de la tabla PRODUCT_CACHE
 * @author jorge.munoz
 *
 */

public class ProductCache {

    private String producto;
    private String codigoPais;
    private String color;
    private String talla;
    private String appMango;
    private String urlEntorno;
    private String tipo;
    private String almacen;
    private Date obtenido;
    private Date caducidad;
    
    public String getProducto() {
        return this.producto;
    }
    
    public void setProducto(String producto) {
        this.producto = producto;
    }
    
    public String getCodigoPais() {
        return this.codigoPais;
    }
    
    public void setCodigoPais(String codigoPais) {
        this.codigoPais = codigoPais;
    }    
    
    public String getColor() {
        return this.color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
    
    public String getTalla() {
        return this.talla;
    }
    
    public void setTalla(String talla) {
        this.talla = talla;
    }
    
    public String getAppMango() {
        return this.appMango;
    }
    
    public void setAppMango(String appMango) {
        this.appMango = appMango;
    }
    
    public String getUrlEntorno() {
        return this.urlEntorno;
    }
    
    public void setUrlEntorno(String urlEntorno) {
        this.urlEntorno = urlEntorno;
    }
    
    public String getTipo() {
        return this.tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }    
    
    public String getAlmacen() {
        return this.almacen;
    }
    
    public void setAlmacen(String almacen) {
        this.almacen = almacen;
    } 
    
    public Date getObtenido() {
        return this.obtenido;
    }
    
    public void setObtenido(Date obtenido) {
        this.obtenido = obtenido;
    }

    public Date getCaducidad() {
        return this.caducidad;
    }
    
    public void setCaducidad(Date caducidad) {
        this.caducidad = caducidad;
    }    
}
