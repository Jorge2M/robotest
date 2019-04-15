package com.mng.robotest.test80.mango.test.generic.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import javax.xml.bind.annotation.XmlRootElement;

import com.mng.robotest.test80.mango.test.data.ValesData.Campanya;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.getdata.productos.ArticleStock;

@XmlRootElement
public class ValePais {
	
	public enum EffectToArticle {aplica, noaplica};
	
    boolean activo = true;
    Pais pais;
    String codigoVale;
    String textoCheckout = "";
    int porcDescuento;
    boolean filterCal = true;
    Calendar fechaInicio;
    Calendar fechaFin;
    List<ArticleStock> listExamplesArtSiAplica = new ArrayList<>();
    List<ArticleStock> listExamplesArtNoAplica = new ArrayList<>();
    Campanya campanya;

    public ArrayList<ArticleStock> getArticlesFromVale() {
    	ArrayList<ArticleStock> listArticlesReturn = new ArrayList<>();
		listArticlesReturn.addAll(getListExamplesArtNoAplica());
		for (ArticleStock articleStock : getListExamplesArtSiAplica()) {
			articleStock.setValePais(this);
			listArticlesReturn.add(articleStock);
		}
		
		return listArticlesReturn;
    }
    
    public boolean isActivo() {
        return this.activo;
    }
    
    public List<ArticleStock> getListExamplesArtSiAplica() {
        return this.listExamplesArtSiAplica;
    }
    
    public List<ArticleStock> getListExamplesArtNoAplica() {
        return this.listExamplesArtNoAplica;
    }    
    
    public Pais getPais() {
        return this.pais;
    }
    
    public Campanya getCampanya() {
    	return this.campanya;
    }
    
    public void setPais(Pais pais) {
        this.pais = pais;
    }
    
    public String getCodigoVale() {
        return this.codigoVale;
    }

    public void setCodigoVale(String codigoVale) {
        this.codigoVale = codigoVale;
    }       
    
    public String getTextoCheckout() {
        return this.textoCheckout;
    }

    public void setTextoCheckout(String textoCheckout) {
        this.textoCheckout = textoCheckout;
    }   
    
    public int getPorcDescuento() {
        return this.porcDescuento;
    }

    public void setPorcDescuento(int porcDescuento) {
        this.porcDescuento = porcDescuento;
    }    
    
    public Calendar getFechaInicio() {
        return this.fechaInicio;
    }
    
    public void setFechaInicio(Calendar fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Calendar getFechaFin() {
        return this.fechaFin;
    }

    public void setFechaFin(Calendar fechaFin) {
        this.fechaFin = fechaFin;
    }        
    

    public boolean isValid() {
        return (isActivo() && (!this.filterCal || isInDates()));
    }
    
    public boolean isInDates() {
        Calendar currDtCal = Calendar.getInstance();
        if (currDtCal.getTimeInMillis() > this.fechaInicio.getTimeInMillis() &&
            currDtCal.getTimeInMillis() < this.fechaFin.getTimeInMillis()) {
            return true;
        }
        return false;
    }
    
    public boolean sameKey(ValePais otherValePais) {
        if (this.pais.getCodigo_pais().compareTo(otherValePais.pais.getCodigo_pais())==0 &&
            this.codigoVale.compareTo(otherValePais.getCodigoVale())==0) {
            return true;
        }
        return false;
    }
}
