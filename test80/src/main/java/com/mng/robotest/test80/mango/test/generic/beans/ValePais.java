package com.mng.robotest.test80.mango.test.generic.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import javax.xml.bind.annotation.XmlRootElement;

import com.mng.robotest.test80.mango.test.data.ValesData.Campanya;
import com.mng.robotest.test80.mango.test.getdata.productos.ArticleStock;


@XmlRootElement
public class ValePais {
	public enum EffectToArticle {aplica, noaplica};
	
    boolean activo = true;
    String codigoPais;
    String codigoVale;
    int porcDescuento;
    boolean filterCal = true;
    Calendar fechaInicio;
    Calendar fechaFin;
    List<ArticleStock> listExamplesArtSiAplica = new ArrayList<>();
    List<ArticleStock> listExamplesArtNoAplica = new ArrayList<>();
    Campanya campanya;

    public static ValePais makeBase (Campanya campanya, String codigoPais, String codigoVale, int porcDescuento) {
    	ValePais vale = new ValePais();
    	vale.campanya = campanya;
        vale.codigoPais = codigoPais;
    	vale.codigoVale = codigoVale;
    	vale.porcDescuento = porcDescuento;
    	vale.filterCal = false;
    	return vale;
    }
    
    /**
     * @param filterCal indica si en la validez del vale se ha de tener en cuenta o no la fecha_inicio/fecha_fin
     * @param fechaInicio en formato "DD/MM/AAAA HH:MM"
     * @param fechaFin    en formato "DD/MM/AAAA HH:MM"
     */
    public static ValePais makeWithoutArticles (Campanya campanya, String codigoPais, String codigoVale, int porcDescuento, 
    											String fechaInicio, String fechaFin, boolean filterCal) 
    throws Exception {
    	ValePais vale = makeBase(campanya, codigoPais, codigoVale, porcDescuento);
    	vale.filterCal = filterCal;
    	vale.fechaInicio = fechaToCalendar(fechaInicio);
    	vale.fechaFin = fechaToCalendar(fechaFin);
    	return vale;
    }
   
    /**
     * @param fecha en formato "DD/MM/AAAA HH:MM"
     */
    public static Calendar fechaToCalendar(String fecha) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Calendar cal  = Calendar.getInstance();
        cal.setTime(sdf.parse(fecha));
        return cal;
    }
    
    public static ValePais makeWithArticles (Campanya campanya, String codigoPais, String codigoVale, int porcDescuento, 
    										String fechaInicio, String fechaFin, boolean filterCal, List<String> listArticlesSiAplica, 
    										List<String> listArticlesNoAplica) throws Exception {
        ValePais vale = makeWithoutArticles(campanya, codigoPais, codigoVale, porcDescuento, fechaInicio, fechaFin, filterCal);
        setArticlesToVale(vale, listArticlesSiAplica, EffectToArticle.aplica);
        setArticlesToVale(vale, listArticlesNoAplica, EffectToArticle.noaplica);
        return vale;
    }
    
    public static ValePais makeValeTest(String codigoPais) {
		return (ValePais.makeBase(Campanya.Test, codigoPais, "TEST", 10));
    }
    
    public static void setArticlesToVale(ValePais vale, List<String> listArticles, EffectToArticle effectToArticle) {
    	if (listArticles!=null) {
	    	switch (effectToArticle) {
	    	case aplica:
	    		vale.listExamplesArtSiAplica = new ArrayList<>();
	                for (String refArticle : listArticles)
	                    vale.listExamplesArtSiAplica.add(new ArticleStock(refArticle));
	    		break;
	    	case noaplica:
	            for (String refArticle : listArticles)
	                vale.listExamplesArtNoAplica.add(new ArticleStock(refArticle));
	    	}
    	}
    }
    
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
    
    public String getCodigoPais() {
        return this.codigoPais;
    }
    
    public Campanya getCampanya() {
    	return this.campanya;
    }
    
    public void setCodigoPais(String codigoPais) {
        this.codigoPais = codigoPais;
    }
    
    public String getCodigoVale() {
        return this.codigoVale;
    }

    public void setCodigoVale(String codigoVale) {
        this.codigoVale = codigoVale;
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
            currDtCal.getTimeInMillis() < this.fechaFin.getTimeInMillis())
            return true;

        return false;
    }
    
    public boolean sameKey(ValePais otherValePais) {
        if (this.codigoPais.compareTo(otherValePais.getCodigoPais())==0 &&
            this.codigoVale.compareTo(otherValePais.getCodigoVale())==0)
            return true;
        
        return false;
    }
}
