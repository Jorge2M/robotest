package com.mng.robotest.test80.mango.test.generic.beans;

import java.util.ArrayList;

import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.Talla;
import com.mng.robotest.test80.mango.test.getdata.products.data.Garment;
import com.mng.robotest.test80.mango.test.getdata.products.data.Garment.Article;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;


public class ArticuloScreen {
	
	String referencia = "";
	String nombre = "";
	String colorName = "";
	String codigoColor = "";
	Talla talla;
	String precio = "";
	String precioSinDesc = "";
	ValePais valePais=null;
	AppEcom app;

	int numero = 1;
	
	public ArticuloScreen(AppEcom app) {
		this.app = app;
	}

	public ArticuloScreen(Garment productStock) {
		Article articleStock = productStock.getArticleWithMoreStock();
		this.referencia = articleStock.getGarmentId();
		this.codigoColor = articleStock.getColor().getId();
		this.talla = Talla.getTalla(articleStock.getSize().getId());
		this.valePais = productStock.getValePais();
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
	
    public String getColorName() {
        return this.colorName;
    }
	
    public String getCodigoColor() {
        return this.codigoColor;
    }
	
    public void setColorName(String colorName) {
        this.colorName = colorName;
    }
	
    public void setCodigoColor(String codigoColor) {
        this.codigoColor = codigoColor;
    }

	public Talla getTalla() {
		return this.talla;
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
    
    public void setTalla(Talla talla) {
    	this.talla = talla;
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
	
    public boolean compare(ArticuloScreen articulo) {
        boolean iguales = false;
        if (this.referencia.trim().compareTo(articulo.getReferencia().trim())==0 &&
            this.nombre.compareTo(articulo.getNombre())==0 &&
            this.colorName.compareTo(articulo.getColorName())==0 &&
            this.talla==articulo.getTalla()) {
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
       	    (getTalla()==articulo.getTalla()) &&
            (getColorName().compareTo(articulo.getColorName())==0 || getCodigoColor().compareTo(articulo.getCodigoColor())==0));
    }
    
    public Color getColor() {
    	return new Color();
    }
    
    public class Color {
    	public String getCodigoColor() {
    		return codigoColor;
    	}
    	public String getColorName() {
    		return colorName;
    	}
    	@Override
    	public String toString() {
    		StringBuilder retorno = new StringBuilder();
    		if (!colorName.isEmpty()) {
    			retorno.append(colorName + " (");
    		}
    		if (!codigoColor.isEmpty()) {
    			retorno.append(codigoColor);
    		}
    		if (!colorName.isEmpty()) {
    			retorno.append(")");
    		}
    		return retorno.toString();
    	}
    }
}