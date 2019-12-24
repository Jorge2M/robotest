package com.mng.robotest.test80.mango.test.generic.beans;

import java.util.ArrayList;

import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.getproducts.data.Garment;
import com.mng.robotest.test80.mango.test.getproducts.data.Garment.Article;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;
import com.mng.robotest.test80.mango.test.utils.UtilsTestMango;


public class ArticuloScreen {
	
	String referencia = "";
	String nombre = "";
	String colorName = "";
	String codigoColor = "";
	String tallaAlf = "";
	String tallaNum = "";
	String precio = "";
	String precioSinDesc = "";
	ValePais valePais=null;
	AppEcom app;

	int numero = 1;
	
	public ArticuloScreen(AppEcom app) {
		this.app = app;
	}

	public ArticuloScreen(Garment productStock) {
		Article articleStock = productStock.getOneWithStock();
		this.referencia = articleStock.getGarmentId();
		this.codigoColor = articleStock.getColor().getId();
		String talla = String.valueOf(articleStock.getSize().getId());
		if ("99".compareTo(talla)==0) {
			this.tallaNum = UtilsTestMango.getCodigoTallaUnica(app);
		} else {
			this.tallaNum = talla;
		}
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
	
    public boolean compare(ArticuloScreen articulo) {
        boolean iguales = false;
        if (this.referencia.trim().compareTo(articulo.getReferencia().trim())==0 &&
            this.nombre.compareTo(articulo.getNombre())==0 &&
            this.colorName.compareTo(articulo.getColorName())==0 &&
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