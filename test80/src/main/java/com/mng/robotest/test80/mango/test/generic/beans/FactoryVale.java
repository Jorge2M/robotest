package com.mng.robotest.test80.mango.test.generic.beans;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.mng.robotest.test80.mango.test.data.PaisShop;
import com.mng.robotest.test80.mango.test.data.ValesData.Campanya;
import com.mng.robotest.test80.mango.test.factoryes.Utilidades;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.generic.beans.ValePais.EffectToArticle;
import com.mng.robotest.test80.mango.test.getdata.productos.ArticleStock;

public class FactoryVale {
	
	private static List<Pais> listPaises = null;
	
	private FactoryVale() {}
	
    public static ValePais makeBase (Campanya campanya, String codigoPais, String codigoVale, int porcDescuento) throws Exception {
    	ValePais vale = new ValePais();
    	vale.campanya = campanya;
    	vale.codigoVale = codigoVale;
    	vale.porcDescuento = porcDescuento;
    	vale.filterCal = false;
    	vale.pais = UtilsMangoTest.getPaisFromCodigo(codigoPais, getListaPaisesSingleton());
    	return vale;
    }
    
    private static List<Pais> getListaPaisesSingleton() throws Exception {
    	if (listPaises==null) {
    		List<Integer> listCountrys = null;
    		listPaises = Utilidades.getListCountrysFiltered(listCountrys);
    	}
    	return listPaises;
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
    
    public static ValePais makeWithArticles (
    		Campanya campanya, PaisShop paisShop, int porcDescuento, String fechaInicio, String fechaFin, boolean filterCal, 
    		List<String> listArticlesSiAplica, List<String> listArticlesNoAplica) throws Exception {
        ValePais vale = makeWithoutArticles(campanya, paisShop.getCodigoPais(), campanya.name(), porcDescuento, fechaInicio, fechaFin, filterCal);
        setArticlesToVale(vale, listArticlesSiAplica, EffectToArticle.aplica);
        setArticlesToVale(vale, listArticlesNoAplica, EffectToArticle.noaplica);
        return vale;
    }
    
    public static ValePais makeValeTest(String codigoPais) throws Exception {
		return (makeBase(Campanya.Test, codigoPais, "TEST", 10));
    }
    
    public static void setArticlesToVale(ValePais vale, List<String> listArticles, EffectToArticle effectToArticle) {
    	if (listArticles!=null) {
	    	switch (effectToArticle) {
	    	case aplica:
	    		vale.listExamplesArtSiAplica = new ArrayList<>();
	                for (String refArticle : listArticles) {
	                    vale.listExamplesArtSiAplica.add(new ArticleStock(refArticle));
	                }
	    		break;
	    	case noaplica:
	            for (String refArticle : listArticles) {
	                vale.listExamplesArtNoAplica.add(new ArticleStock(refArticle));
	            }
	    	}
    	}
    }
}
