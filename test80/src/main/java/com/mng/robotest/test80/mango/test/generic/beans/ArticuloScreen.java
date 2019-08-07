package com.mng.robotest.test80.mango.test.generic.beans;

import java.util.ArrayList;

import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.getdata.productos.ArticleStock;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;
import com.mng.robotest.test80.mango.test.utils.UtilsTestMango;


public class ArticuloScreen {
	
    String referencia = "";
    String nombre = "";
    String color = "";
    String codigoColor = "";
    String tallaAlf = "";
    String tallaNum = "";
    String precio = "";
    String precioSinDesc = "";
    ValePais valePais=null;

    int numero = 1;
	
    public ArticuloScreen() {}
    
    public ArticuloScreen(ArticleStock articleStock) {
        this.referencia = articleStock.getReference();
        this.codigoColor = articleStock.getColourCode();
        if ("99".compareTo(articleStock.getSize())==0) {
        	this.tallaNum = UtilsTestMango.getCodigoTallaUnica(articleStock.getApp());
        } else {
        	this.tallaNum = articleStock.getSize();
        }
        this.valePais = articleStock.getValePais();
    }
    
    public String getReferencia() {
        return this.referencia.trim();
    }
	
    public String getRefProducto() {
        return (getReferencia().substring(0, 8));
    }
	
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }
	
    public String getNombre() {
        return this.nombre;
    }
	
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
	
    public String getColor() {
        return this.color;
    }
	
    public String getCodigoColor() {
        return this.codigoColor;
    }
	
    public void setColor(String color) {
        this.color = color;
    }
	
    public void setCodigoColor(String codigoColor) {
        this.codigoColor = codigoColor;
    }
	
    public String getTallaNum() {
        return this.tallaNum;
    }
	
    public void setTallaNum(String tallaNum) {
        this.tallaNum = tallaNum;
    }
	
    public String getTallaAlf() {
        return this.tallaAlf;
    }
        
    public void setTallaAlf(String tallaAlf) {
        this.tallaAlf = tallaAlf;
    }	
	
    public String getPrecio() {
        return this.precio;
    }
	
    public void setPrecio(String precio) {
        this.precio = precio;
    }
	
    public String getPrecioSinDesc() {
        return this.precioSinDesc;
    }
	
    public void setPrecioSinDesc(String precioSinDesc) {
        this.precioSinDesc = precioSinDesc;
    }
	
    public int getNumero() {
        return this.numero;
    }
	
    public void setNumero(int numero) {
        this.numero = numero;
    }
	
    public ValePais getValePais() {
        return this.valePais;
    }
	
    public void setVale(ValePais vale) {
        this.valePais = vale;
    }
	
    public String getTallaSinAlmacen() {
        String tallaSinAlmacen = this.tallaAlf;
        int inicioAlm = this.tallaAlf.indexOf(" [");
        if (inicioAlm>0) {
            tallaSinAlmacen = this.tallaAlf.substring(0,inicioAlm);
        }
        return tallaSinAlmacen;
    }
	
    public String getDivisaPrecio() {
        //Retornamos el último carácter
        return (this.precio.substring(this.precio.length()-1));
    }
	
    public float getPrecioDescontado() {
        float precioDescontado = ImporteScreen.getFloatFromImporteMangoScreen(this.precio);
        if (this.valePais!=null) {
            precioDescontado = precioDescontado - (precioDescontado * (this.valePais.getPorcDescuento()/100));
        }
        return precioDescontado;
    }
	
    public void incrementarCantidad(int incremento) {
        this.numero+=incremento;
    }
	
    public void clone(ArticuloScreen articulo) {
        this.referencia = articulo.referencia;
        this.nombre = articulo.nombre;
        this.color = articulo.color;
        this.codigoColor = articulo.codigoColor;
        this.tallaAlf = articulo.tallaAlf;
        this.tallaNum = articulo.tallaNum;
        this.precio = articulo.precio;
        this.precioSinDesc = articulo.precioSinDesc;
        this.numero = articulo.numero;
        this.valePais = articulo.valePais;
    }
	
    public boolean compare(ArticuloScreen articulo) {
        boolean iguales = false;
        if (this.referencia.trim().compareTo(articulo.getReferencia().trim())==0 &&
            this.nombre.compareTo(articulo.getNombre())==0 &&
            this.color.compareTo(articulo.getColor())==0 &&
            this.tallaAlf.compareTo(articulo.getTallaAlf())==0) {
            iguales = true;
        }
        return iguales;
    }
	
    public boolean isPresentInList(ArrayList<ArticuloScreen> listaArticulos) {
        for (ArticuloScreen articulo : listaArticulos) {
        	if (isTheSame(articulo)) {
        		return true;
        	}
        }
		
        return false;
    }
    
    public boolean isTheSame(ArticuloScreen articulo) {
       	return
       	    (getReferencia().compareTo(articulo.getReferencia())==0 &&
       	    (getTallaNum().compareTo(articulo.getTallaNum())==0 || getTallaAlf().compareTo(articulo.getTallaAlf())==0) &&
            (getColor().compareTo(articulo.getColor())==0 || getCodigoColor().compareTo(articulo.getCodigoColor())==0));
    }
}