package com.mng.robotest.test.utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.generic.beans.FactoryVale;
import com.mng.robotest.test.generic.beans.ValePais;
import com.mng.robotest.test.getdata.products.GetterProducts;
import com.mng.robotest.test.getdata.products.ProductFilter.FilterType;
import com.mng.robotest.test.getdata.products.data.GarmentCatalog;


public class UtilsTestMango {
	
	/**
	 * @return si se ha de crear un test para un país concreto
	 */
	public static boolean paisConCompra(Pais pais, AppEcom appE) {
		return (
			"n".compareTo(pais.getExists())!=0 &&
			pais.getTiendasOnlineList().contains(appE));
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

	public static String getReferenciaFromSrcImg(String srcImage) {
		Pattern pattern = Pattern.compile("(\\d+)_(.*?).jpg");
		Matcher matcher = pattern.matcher(srcImage);
		if (matcher.find()) {
			return matcher.group(1);
		}
		return "";
	}

	public static String getReferenciaFromHref(String hrefArticulo) {
		String referencia = getReferenciaFromHref_type1(hrefArticulo);
		if ("".compareTo(referencia)!=0) {
			return referencia;
		}
		return getReferenciaFromHref_type2(hrefArticulo);
	}

	private static String getReferenciaFromHref_type1(String hrefArticulo) {
		Pattern pattern = Pattern.compile("(\\d+).html");
		Matcher matcher = pattern.matcher(hrefArticulo);
		if (matcher.find()) {
			return matcher.group(1);
		}
		return "";
	}
	
	private static String getReferenciaFromHref_type2(String hrefArticulo) {
		Pattern pattern = Pattern.compile("\\?producto=(\\d+)\\&");
		Matcher matcher = pattern.matcher(hrefArticulo);
		if (matcher.find()) {
			return matcher.group(1);
		}
		return "";
	}
	
	public static GarmentCatalog getArticleForTest(DataCtxShop dCtxSh, WebDriver driver) throws Exception {
		List<GarmentCatalog> articles = getArticlesForTest(dCtxSh, driver);
		return articles.get(0);
	}
	
	static int maxArticles = 99;
	public static List<GarmentCatalog> getArticlesForTest(DataCtxShop dCtxSh, WebDriver driver) throws Exception {
		return (getArticlesForTestDependingVale(dCtxSh, maxArticles, driver));
	}
	
	public static List<GarmentCatalog> getArticlesForTest(DataCtxShop dCtxSh, int maxArticlesAwayVale, boolean withValeTest, WebDriver driver) 
	throws Exception {
		ValePais valeTest = null;
		if (withValeTest) {
			valeTest = FactoryVale.makeValeTest(dCtxSh.pais.getCodigo_pais());
			dCtxSh.vale = valeTest;
		}
		
		return (getArticlesForTestDependingVale(dCtxSh, maxArticlesAwayVale, driver));
	}
	
	public static List<GarmentCatalog> getArticlesForTestDependingVale(DataCtxShop dCtxSh, WebDriver driver) throws Exception {
		return (getArticlesForTestDependingVale(dCtxSh, maxArticles, driver));
	}
	
	public static List<GarmentCatalog> getArticlesForTestDependingVale(DataCtxShop dCtxSh, int maxArticlesAwayVale, WebDriver driver) 
	throws Exception {
		List<GarmentCatalog> listProducts;
		if (dCtxSh.vale!=null) {
			listProducts = dCtxSh.vale.getArticlesFromVale();
			if (listProducts.size()>0) {
				return listProducts;
			}
		}
		
		GetterProducts getterProducts = new GetterProducts
				.Builder(dCtxSh.pais.getCodigo_alf(), dCtxSh.appE, driver)
				.build();

		listProducts = getterProducts.getFiltered(FilterType.Stock);
		
		if (dCtxSh.vale!=null) {
			for (GarmentCatalog product : listProducts) {
				product.setValePais(dCtxSh.vale);
			}
		}

		if (listProducts.size() > maxArticlesAwayVale) {
			return (listProducts.subList(0, maxArticlesAwayVale));
		}
		return listProducts;
	}
}