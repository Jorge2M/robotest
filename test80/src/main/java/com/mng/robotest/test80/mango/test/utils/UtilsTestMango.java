package com.mng.robotest.test80.mango.test.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.IdiomaPais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.generic.beans.FactoryVale;
import com.mng.robotest.test80.mango.test.generic.beans.ValePais;
import com.mng.robotest.test80.mango.test.generic.beans.ValePais.EffectToArticle;
import com.mng.robotest.test80.mango.test.getdata.productos.ArticleStock;
import com.mng.robotest.test80.mango.test.getdata.productos.ManagerArticlesStock;
import com.mng.robotest.test80.mango.test.getdata.productos.ManagerArticlesStock.TypeArticleStock;


public class UtilsTestMango {
    /**
     * @return si se ha de crear un test para un país concreto
     */     
    public static boolean paisConCompra(Pais pais, AppEcom appE) {
        return (
            "n".compareTo(pais.getExists())!=0 &&
            pais.getShop_online().compareTo("true")==0 &&
            (appE!=AppEcom.outlet || (appE==AppEcom.outlet && pais.getOutlet_online().compareTo("true")==0))
        );
    }

    public static String getSaleTraduction(IdiomaPais idioma) {
        switch (idioma.getCodigo().name()) {
        case "ES":
            return "REBAJAS";
        case "AR": 
            return "تنزيلات";
        case "HU":
            return "AKCIÓ";
        case "HR":
            return "SEZONSKO";
        case "RO":
            return "PROMOȚIE";
        default:
            return "SALE";
        }        
    }
    
    public static String getPercentageSymbol(IdiomaPais idioma) {
        switch (idioma.getCodigo().name()) {
        case "ZH": 
            return "折";
        default:
            return "%";
        }
    }
    
    public static String getSetenta(IdiomaPais idioma) {
        switch (idioma.getCodigo().name()) {
        case "AR": 
            return "٧٠";
        case "ES":
        	default:
            return "70";
        }
    }
    
    public static boolean textContainsPercentage(String text, IdiomaPais idioma) {
        String percentageSymbol = getPercentageSymbol(idioma);
        return (text.contains(percentageSymbol));
    }
    
    public static boolean textContainsSetenta(String text, IdiomaPais idioma) {
    	String setenta = getSetenta(idioma);
    	return (text.contains(setenta));
    }
    
	public static String getReferenciaFromHref(String hrefArticulo) {
        Pattern pattern = Pattern.compile("(\\d+).html");
        Matcher matcher = pattern.matcher(hrefArticulo);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
	}
	
	static int maxArticles = 99;
	public static List<ArticleStock> getArticlesForTest(DataCtxShop dCtxSh) throws Exception {
		return (getArticlesForTestDependingVale(dCtxSh, maxArticles));
	}
	
	public static List<ArticleStock> getArticlesForTest(DataCtxShop dCtxSh, int maxArticlesAwayVale, boolean withValeTest) 
	throws Exception {
		ValePais valeTest = null;
		if (withValeTest) {
			valeTest = FactoryVale.makeValeTest(dCtxSh.pais.getCodigo_alf());
			dCtxSh.vale = valeTest;
		}
		
		return (getArticlesForTestDependingVale(dCtxSh, maxArticlesAwayVale));
	}
	
	public static List<ArticleStock> getArticlesForTestDependingVale(DataCtxShop dCtxSh) throws Exception {
		return (getArticlesForTestDependingVale(dCtxSh, maxArticles));
	}
	
    public static List<ArticleStock> getArticlesForTestDependingVale(DataCtxShop dCtxSh, int maxArticlesAwayVale) throws Exception {
    	ArrayList<ArticleStock> listArticles;
    	if (dCtxSh.vale!=null) {
    		listArticles = dCtxSh.vale.getArticlesFromVale();
    		if (listArticles.size()>0) {
    			return listArticles;
    		}
    	}
    	
    	ManagerArticlesStock managerArticles = new ManagerArticlesStock(dCtxSh.appE, dCtxSh.urlAcceso, maxArticlesAwayVale);
    	listArticles = managerArticles.getArticles(dCtxSh.pais.getCodigo_pais(), TypeArticleStock.articlesWithMoreOneColour);
        if (dCtxSh.vale!=null) {
        	List<String> listReferences = getListArticleReferences(listArticles);
        	FactoryVale.setArticlesToVale(dCtxSh.vale, listReferences, EffectToArticle.aplica);
        	listArticles = dCtxSh.vale.getArticlesFromVale();
        }
        
        if (listArticles.size() > maxArticlesAwayVale) {
        	return (listArticles.subList(0, maxArticlesAwayVale));
        }
        return listArticles;
    }
    
    private static List<String> getListArticleReferences(ArrayList<ArticleStock> listArticles) {
    	List<String> listReferences = new ArrayList<>();
    	for (ArticleStock article : listArticles)
    		listReferences.add(article.getReference());
    	
    	return listReferences;
    }
}
