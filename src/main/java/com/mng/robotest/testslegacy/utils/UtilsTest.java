package com.mng.robotest.testslegacy.utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;

import org.apache.commons.lang3.tuple.Pair;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.repository.productlist.GetterProducts;
import com.mng.robotest.tests.repository.productlist.ProductFilter.FilterType;
import com.mng.robotest.tests.repository.productlist.entity.GarmentCatalog;
import com.mng.robotest.tests.repository.productlist.entity.GarmentCatalog.Article;
import com.mng.robotest.tests.repository.productlist.sort.SortFactory.SortBy;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.beans.Pais;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.service.exceptions.NotFoundException;


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
	public static boolean todayBeforeDate(String date) {
		var sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dateLimit = sdf.parse(date);
			Date dateToday = new Date();
			return (dateToday.before(dateLimit));
		} 
		catch (ParseException e) {
			Log4jTM.getLogger().error("Error parsing date {}", date, e);
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
	
	public static Article getArticleForTest(Pais pais, AppEcom app, WebDriver driver) throws Exception {
		List<Article> articles = getArticlesForTest(pais, app, driver);
		return articles.get(0);
	}
	
	static int maxArticles = 99;
	public static List<Article> getArticlesForTest(Pais pais, AppEcom app, WebDriver driver) throws Exception {
		return (getArticlesForTest(pais, app, maxArticles, driver));
	}
	
	public static List<Article> getArticlesForTest(
			Pais pais, AppEcom app, int maxArticlesAwayVale, WebDriver driver) throws Exception {
		
		var getterProducts = new GetterProducts
				.Builder(pais.getCodigoAlf(), app, driver)
				.filter(FilterType.STOCK)
				.sortBy(SortBy.STOCK_DESCENDENT)
				.build();

		List<GarmentCatalog> listProducts = getterProducts.getAll();
		List<Article> listArticles = Article.getArticlesForTest(listProducts);
		if (listArticles.size() > maxArticlesAwayVale) {
			return (listArticles.subList(0, maxArticlesAwayVale));
		}
		return listArticles;
	}
	
	public static Pair<Article, Article> getTwoArticlesFromDistinctWarehouses(Pais pais, AppEcom app) throws Exception {
		List<GarmentCatalog> listGarments = getProductFromApi(pais, app);
		
		GarmentCatalog garment1 = listGarments.get(0);
		garment1.removeArticlesWithoutMaxStock();
		String almacen1 = garment1.getAlmacenFirstArticle();
		GarmentCatalog garment2 = null;
		for (GarmentCatalog garment : listGarments) {
			if (garment!=garment1) {
				garment.removeArticlesAlmacen(almacen1);
				if (!garment.getColors().isEmpty() && garment.isAnyArticleWithStock()) {
					garment2 = garment;
					break;
				}
			}
		}
		
		if (garment2==null) {
			throw new NotFoundException("Not found article of warehouse <> " + almacen1);
		}
		
		return Pair.of(
				Article.getArticleForTest(garment1), 
				Article.getArticleForTest(garment2));
	}	
	
	private static List<GarmentCatalog> getProductFromApi(Pais pais, AppEcom app) throws Exception {
		var getterProducts = new GetterProducts.Builder(pais.getCodigoAlf(), app, null)
			.sortBy(SortBy.STOCK_DESCENDENT)
			.extraCanonicalInfo(true)
			.build();
		return getterProducts.getAll();
	}	
	
	
}
