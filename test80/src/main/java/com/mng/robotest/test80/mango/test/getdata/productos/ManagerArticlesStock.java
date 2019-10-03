package com.mng.robotest.test80.mango.test.getdata.productos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mng.testmaker.utils.ManageConnectionHTTP;
import com.mng.testmaker.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.getdata.productos.ArticlesStockFactory.SourceArticles;

import java.util.concurrent.CopyOnWriteArrayList;

public class ManagerArticlesStock {
	static Logger pLogger = LogManager.getLogger(fmwkTest.log4jLogger);
	public enum TypeArticleStock {
		articlesWithMoreOneColour, 
		articlesWithTotalLook, 
		articlesOnlyInThisStore, 
		articlesWithoutStock,
		articlesNotExistent,
		articlesForUnitTesting
	}
	
	private static CopyOnWriteArrayList<ArticleStock> listArticlesStock = null;
	private static CopyOnWriteArrayList<Store> listStoresOfCountry = null;
	private int minArticlesDesired = 1;
	private AppEcom app;
	private String urlApp;
	private String filterIdAlmacen = null;
	private String filterIdCountry = null;
	private TypeArticleStock filterTypeArticle = null;
	
	public ManagerArticlesStock(AppEcom app) {
		this(app, "", 10);
	}
	
	public ManagerArticlesStock(AppEcom app, String urlApp) {
		this(app, urlApp, 10);
	}
	
	public ManagerArticlesStock(AppEcom app, String urlApp, int minArticlesDesired) {
		ManageConnectionHTTP.disableSslVerification();
		this.app = app;
		this.urlApp = urlApp;
		this.minArticlesDesired = minArticlesDesired;
		if (listArticlesStock == null) {
			listArticlesStock = new CopyOnWriteArrayList<>();
		}
		if (listStoresOfCountry == null) {
			listStoresOfCountry = new CopyOnWriteArrayList<>();
		}
	}
	
	public void setMinArticlesDesired(int minArticlesDesired) {
		this.minArticlesDesired = minArticlesDesired;
	}
	
	public ArrayList<ArticleStock> getArticles(String idCountry) throws Exception {
		filterIdAlmacen = null;
		filterIdCountry = idCountry;
		filterTypeArticle = null;
		return (getArticlesFiltered());
	}

	public ArrayList<ArticleStock> getArticles(String idCountry, TypeArticleStock typeArticle) 
	throws Exception {
		filterIdAlmacen = null;
		filterIdCountry = idCountry;
		filterTypeArticle = typeArticle;
		return (getArticlesFiltered());
	}
	
	public ArrayList<ArticleStock> getArticles(String idAlmacen, String idCountry, TypeArticleStock typeArticle) 
	throws Exception {
		filterIdAlmacen = idAlmacen;
		filterIdCountry = idCountry;
		filterTypeArticle = typeArticle;
		return (getArticlesFiltered());
	}
	
	public void deleteArticle(String refArticle) throws Exception {
		pLogger.info("Eliminando artículo  " + refArticle + " de memoria y caché");
		deleteArticleFromMemory(refArticle);
		deleteArticleFromCache(refArticle);
	}
	
	private void deleteArticleFromMemory(String refArticle) {
		for (ArticleStock articleStock : listArticlesStock) {
			if (articleStock.getReference().compareTo(refArticle)==0) {
				listArticlesStock.remove(articleStock);
			}
		}
	}
	
	protected static void clearMemory() {
		listArticlesStock = new CopyOnWriteArrayList<>();
	}
	
	private void deleteArticleFromCache(String article) throws Exception {
		ArticlesStockGetter articleStock = ArticlesStockFactory.make(SourceArticles.CacheBd_RelaxFilters, app, "");
		articleStock.deleteProduct(article);
	}
	
	private ArrayList<ArticleStock> getArticlesFiltered() throws Exception {
		if (filterTypeArticle==TypeArticleStock.articlesNotExistent) {
			return getArticlesNotExistentHardcoded();
		}
		
		boolean relaxFilters = true;
		ArrayList<ArticleStock> listArticlesToReturn = getListArticlesFilteredFromMemory(!relaxFilters);
		if (listArticlesToReturn.size() < minArticlesDesired) {
			getArticlesFromCacheAndStoreInMemory(SourceArticles.CacheBd_NotRelaxFilters);
			listArticlesToReturn = getListArticlesFilteredFromMemory(!relaxFilters);
			if (listArticlesToReturn.size() < minArticlesDesired) {
				getArticlesFromServiceMangoAndStoreInCache();
				listArticlesToReturn = getListArticlesFilteredFromMemory(!relaxFilters);
				if (listArticlesToReturn.size() < minArticlesDesired) {
					getArticlesFromCacheAndStoreInMemory(SourceArticles.CacheBd_RelaxFilters);
					listArticlesToReturn = getListArticlesFilteredFromMemory(relaxFilters);
				}
			}
		}
		
		return listArticlesToReturn;
	}
	
	private void getArticlesFromServiceMangoAndStoreInCache() throws Exception {
		ArrayList<ArticleStock> listNewArticles = getAndStoreNewArticlesInMemory(filterIdAlmacen, filterIdCountry, SourceArticles.ServiceMango);
		ArticlesStockCacheBD.storeNewArticles(listNewArticles);	
	}
	
	private void getArticlesFromCacheAndStoreInMemory(SourceArticles source) throws Exception {
		getAndStoreNewArticlesInMemory(filterIdAlmacen, filterIdCountry, source);
	}
	
	private ArrayList<ArticleStock> getArticlesNotExistentHardcoded() {
		ArrayList<ArticleStock> listArticlesToReturn = new ArrayList<>();
		ArticleStock article = new ArticleStock("12345678");
		article.setType(TypeArticleStock.articlesNotExistent);
		listArticlesToReturn.add(article);
		return listArticlesToReturn;
	}
	
	private ArrayList<ArticleStock> getListArticlesFilteredFromMemory(boolean relaxFilters) 
	throws Exception {
		ArrayList<ArticleStock> listArticlesToReturn = new ArrayList<>();
		for (ArticleStock articleStock : listArticlesStock) {
			if (matchesArticleWithFilter(articleStock, relaxFilters)) {
				listArticlesToReturn.add(articleStock);
			}
		}
		
		return listArticlesToReturn;
	}
	
	private boolean matchesArticleWithFilter(ArticleStock articleStock, boolean relaxFilters) throws Exception {
		boolean matchesIdAlmacen = (filterIdAlmacen==null || articleStock.idAlmacen.compareTo(filterIdAlmacen)==0);
		boolean matchesIdCountry = (relaxFilters || filterIdCountry==null || articleStock.idCountry.compareTo(filterIdCountry)==0);
		boolean matchesTypeArticle = (filterTypeArticle==null || articleStock.type==filterTypeArticle);
		boolean matchesApp = articleStock.app == app;
		boolean matchesUrlApp = (relaxFilters || articleStock.urlApp.compareTo(urlApp)==0);
		boolean matchesStoreAndApp = isAnyStoreAssociated(articleStock.idCountry, articleStock.idAlmacen, app);	
		return (matchesIdAlmacen && matchesIdCountry && matchesTypeArticle && matchesApp && matchesUrlApp && matchesStoreAndApp);
	}
	
	private boolean isAnyStoreAssociated(String countryId, String storeId, AppEcom app) throws Exception {
		Store store = getStoreAssociated(countryId, storeId, app);
		if (store!=null) {
			return true;
		}
		return false;
	}
	
	private Store getStoreAssociated(String countryId, String idStore, AppEcom app) throws Exception {
		CopyOnWriteArrayList<Store> listStoresOfCountry = getStoresOfCountry(countryId);
		for (Store storeOfCountry : listStoresOfCountry) {
			if (storeOfCountry.storeId.compareTo(idStore)==0 &&
				matchesSaleStoreAndApp(storeOfCountry.sale, app)) {
				return storeOfCountry;
			}
		}
		
		return null;
	}
	
	private boolean matchesSaleStoreAndApp(String saleStore, AppEcom app) {
		switch (app) {
		case shop:
		case outlet:
			return ("S".compareTo(saleStore)==0);
		case votf:
		default:
			return ("V".compareTo(saleStore)==0);
		}
	}
	
	private CopyOnWriteArrayList<Store> getStoresOfCountry(String countryId) throws Exception {
		CopyOnWriteArrayList<Store> listStoresToReturn = getStoresOfCountrysFromMemory(countryId);
		if (listStoresToReturn.size()==0) {
			storeStoresOfCountryInMemory(countryId);
			listStoresToReturn = getStoresOfCountrysFromMemory(countryId);
		}
		
		return listStoresToReturn;
	}
	
	private ArrayList<String> getListOfStoresIdOfCountry(String countryId) throws Exception {
		ArrayList<String> listStoresToReturn = new ArrayList<>();
		CopyOnWriteArrayList<Store> listStores = getStoresOfCountry(countryId);
		for (Store store : listStores) {
			if (!listStoresToReturn.contains(store.storeId)) {
				listStoresToReturn.add(store.storeId);
			}
		}
		
		return listStoresToReturn;
	}
	
	private CopyOnWriteArrayList<Store> getStoresOfCountrysFromMemory(String countryId) {
		if (listStoresOfCountry!=null) {
			ArrayList<Store> listStoresToReturn = new ArrayList<>();
			for (Store store : listStoresOfCountry) {
				if (countryId.compareTo(store.countryId)==0) {
					listStoresToReturn.add(store);
				}
			}
		}
		
		return listStoresOfCountry;
	}
	
	private void storeStoresOfCountryInMemory(String countryId) throws Exception {
		GetStoresServiceMng getStores = new GetStoresServiceMng(urlApp);
		listStoresOfCountry.addAll(getStores.getStores(countryId));
	}
	
	private ArrayList<ArticleStock> getAndStoreNewArticlesInMemory(String idStore, String countryId, SourceArticles source) 
	throws Exception {
		ArrayList<ArticleStock> listNewArticles = new ArrayList<>();
		ArticlesStockGetter getProducts = ArticlesStockFactory.make(source, app, urlApp);
		if (idStore==null) {
			ArrayList<String> listStoresIdOfCountry = getListOfStoresIdOfCountry(countryId);
			for (String storeId : listStoresIdOfCountry) {
				ArrayList<ArticleStock> listArticlesGetted = getProducts.getProducts(storeId, countryId);
				if (listArticlesGetted!=null) {
					listNewArticles.addAll(listArticlesGetted);
				}
			}
		}
		else
			listNewArticles = getProducts.getProducts(idStore, countryId);
		
		storeNewArticlesInMemoryOrdererByQuality(listNewArticles);
		return listNewArticles;
	}
	
	private void storeNewArticlesInMemoryOrdererByQuality(ArrayList<ArticleStock> listNewArticles) {
		for (ArticleStock articleNew : listNewArticles) {
			if (!isArticleInMemory(articleNew)) {
				listArticlesStock.add(articleNew);
			}
		}
		
		orderArticlesByQualityOfSourceData();
	}
	
	private void orderArticlesByQualityOfSourceData() {
		Collections.sort(listArticlesStock, new CustomComparator());
	}
	
	private class CustomComparator implements Comparator<ArticleStock> {
	    @Override
	    public int compare(ArticleStock a1, ArticleStock a2) {
	    	int levelQualityA1 = a1.getSource().getLevelQualityOfData();
	    	int levelQualityA2 = a2.getSource().getLevelQualityOfData();
	        if (levelQualityA1 == levelQualityA2) {
	        	return 0;
	        }
	        if (levelQualityA1 > levelQualityA2) {
	        	return 1;
	        }
	        return -1;
	    }
	}
	
	protected boolean isArticleInMemory(ArticleStock articleStock) {
		for (ArticleStock articleStockMemory : listArticlesStock) {
			if (articleStockMemory.equals(articleStock)) {
				return true;
			}
		}
		
		return false;
	}
	
    public static ArticleStock getArticleStock(TypeArticleStock typeArticle, DataCtxShop dCtxSh) throws Exception {
    	ManagerArticlesStock managerArticles = new ManagerArticlesStock(dCtxSh.appE, dCtxSh.urlAcceso, 1);
    	List<ArticleStock> listArticles = managerArticles.getArticles(dCtxSh.pais.getCodigo_pais(), typeArticle);
    	return (listArticles.get(0));
    }
}
