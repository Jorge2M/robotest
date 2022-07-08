package com.mng.robotest.test.utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;

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

import com.github.jorge2m.testmaker.conf.Log4jTM;


public class UtilsTest {
	
    private UtilsTest() {
	    throw new IllegalStateException("Utility class");
	}
	
	public static boolean paisConCompra(Pais pais, AppEcom appE) {
		return (
			"n".compareTo(pais.getExists())!=0 &&
			pais.getTiendasOnlineList().contains(appE));
	}
	
	/**
	 * @param date format yyyy-MM-dd
	 */
	public static boolean dateReachToday(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dateLimit = sdf.parse(date);
			Date dateToday = new Date();
			return (!dateToday.before(dateLimit));
		} 
		catch (ParseException e) {
			Log4jTM.getLogger().error("Error parsing date " + date, e);
			return false; 
		}
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
		if ("ZH".compareTo(idioma.getCodigo().name())==0) {
			return "折";
		}
		return "%";
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
		String referencia = getReferenciaFromHrefType1(hrefArticulo);
		if ("".compareTo(referencia)!=0) {
			return referencia;
		}
		return getReferenciaFromHrefType2(hrefArticulo);
	}

	private static String getReferenciaFromHrefType1(String hrefArticulo) {
		Pattern pattern = Pattern.compile("(\\d+).html");
		Matcher matcher = pattern.matcher(hrefArticulo);
		if (matcher.find()) {
			return matcher.group(1);
		}
		return "";
	}
	
	private static String getReferenciaFromHrefType2(String hrefArticulo) {
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
			if (!listProducts.isEmpty()) {
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
