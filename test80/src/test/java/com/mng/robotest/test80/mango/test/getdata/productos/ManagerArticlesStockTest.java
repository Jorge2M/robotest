package com.mng.robotest.test80.mango.test.getdata.productos;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.getdata.productos.ArticlesStockFactory.SourceArticles;
import com.mng.robotest.test80.mango.test.getdata.productos.ManagerArticlesStock.TypeArticleStock;

import junit.framework.TestCase;

@PowerMockIgnore({"javax.management.*", "javax.net.ssl.*"})
@RunWith(PowerMockRunner.class)
@PrepareForTest({ArticlesStockServiceMng.class, ArticlesStockFactory.class, GetStoresServiceMng.class})
public class ManagerArticlesStockTest extends TestCase {

	final static String codeEspanya = "001";
	final static String storeIdEspanya = "001";
	final static AppEcom appForTest = AppEcom.shop;
	final static String urlForTest = "https://shop.mango.com/preHome.faces";
	final static int numArticlesDesired = 3;
	
	final static TypeArticleStock typeArticlesToManage = TypeArticleStock.articlesForUnitTesting;
	
    @Override
    protected void setUp() throws Exception {
		//Initial Context without articles in cache nor memory
		ManagerArticlesStock.clearMemory();
		resetArticlesForUnitTestInCacheBD();
		mockServiceGetStoresOfCountry("StoresSpain.json");
		mockServiceArticlesMngWithJsonOfArticles("ThreeArticlesForUnitTesting.json");
    }
	
	@Test
	@Ignore
	public void testGetProductsFromServiceMng() throws Exception {
		//Code to test
		ManagerArticlesStock managerArticles = new ManagerArticlesStock(appForTest, urlForTest, numArticlesDesired);
		ArrayList<ArticleStock> listArticlesObtained = managerArticles.getArticles(codeEspanya, typeArticlesToManage);
		
		//Validations
		assertTrue("Three articles are obtained", listArticlesObtained.size()==3);
		assertTrue("All articles obtained have the Source " + SourceArticles.ServiceMango, 
				   allArticlesHaveTheSource(listArticlesObtained, SourceArticles.ServiceMango));
		assertTrue("All articles obtained have the type" + typeArticlesToManage, 
				   allArticlesHaveTheType(listArticlesObtained, typeArticlesToManage));
		assertTrue("All articles obtained are now stored in memory", 
				   allArticlesAreInMemory(listArticlesObtained, managerArticles));
		assertTrue("All articles obtained are now stored in caché", 
				   allArticlesAreInCache(listArticlesObtained));
	}
	
	@Test
	@Ignore
	public void testGetProductsFromCache() throws Exception {
		//Define initial context. Articles exists only in caché
		ManagerArticlesStock managerArticles = new ManagerArticlesStock(appForTest, urlForTest);
		managerArticles.getArticles(codeEspanya, typeArticlesToManage);
		ManagerArticlesStock.clearMemory();
		
		//Code to test
		ArrayList<ArticleStock> listArticlesObtained = managerArticles.getArticles(codeEspanya, typeArticlesToManage);
		
		//Validations
		assertTrue("Three articles are obtained", listArticlesObtained.size()==3);
		assertTrue("All articles obtained have the Source " + SourceArticles.CacheBd_NotRelaxFilters, 
				   allArticlesHaveTheSource(listArticlesObtained, SourceArticles.CacheBd_NotRelaxFilters));
		assertTrue("All articles obtained have the type" + typeArticlesToManage, 
				   allArticlesHaveTheType(listArticlesObtained, typeArticlesToManage));
		assertTrue("All articles obtained are now stored in memory", 
				   allArticlesAreInMemory(listArticlesObtained, managerArticles));
	}

	private boolean allArticlesHaveTheSource(ArrayList<ArticleStock> listArticles, SourceArticles sourceArticles) {
		for (ArticleStock articleStock : listArticles) {
			if (articleStock.getSource()!=sourceArticles)
				return false;
		}
		
		return true;
	}
	
	private boolean allArticlesHaveTheType(ArrayList<ArticleStock> listArticles, TypeArticleStock typeArticle) {
		for (ArticleStock articleStock : listArticles) {
			if (articleStock.getType()!=typeArticle)
				return false;
		}
		
		return true;
	}
	
	private boolean allArticlesAreInMemory(ArrayList<ArticleStock> listArticlesObtained, ManagerArticlesStock managerArticles)
	throws Exception {
		for (ArticleStock article : listArticlesObtained) {
			if (!managerArticles.isArticleInMemory(article))
				return false;
		}
		
		return true;
	}
	
	private boolean allArticlesAreInCache(ArrayList<ArticleStock> listArticlesObtained) throws Exception {
		ArrayList<ArticleStock> listArticlesInCache = getListArticlesInCache();
		for (ArticleStock article : listArticlesObtained) {
			if (!articleExistsInList(article, listArticlesInCache))
				return false;
		}
		
		return true;
	}
	
	private ArrayList<ArticleStock> getListArticlesInCache() throws Exception {
		ArticlesStockCacheBD articlesStock = new ArticlesStockCacheBD(appForTest, urlForTest);
		ArrayList<ArticleStock> listArticlesCache = articlesStock.getProducts(storeIdEspanya, codeEspanya);
		return listArticlesCache;
	}
	
	private boolean articleExistsInList(ArticleStock articleStock, ArrayList<ArticleStock> listArticlesStock) {
		for (ArticleStock article : listArticlesStock) {
			if (articleStock.equals(article))
				return true;
		}
		
		return false;
	}

	private void resetArticlesForUnitTestInCacheBD() {
		ArticlesStockCacheBD.deleteProducs(TypeArticleStock.articlesForUnitTesting);
	}
	
	private void mockServiceGetStoresOfCountry(String nameFileStoresJson) throws Exception {
		//Get data from file stores JSON
		LinkedHashMap<String,Object> dataJsonStoresOfCountry = getDataFromArchiveJson(nameFileStoresJson);
		
		//Mock method that return stores from Service Mango
		GetStoresServiceMng storesServiceMngSpy = PowerMockito.spy(new GetStoresServiceMng(urlForTest));
		PowerMockito.doReturn(dataJsonStoresOfCountry).when(storesServiceMngSpy, "invokeServiceMangoForObtainStores", Mockito.anyString());
		
		//Return mock when created object
		PowerMockito.whenNew(GetStoresServiceMng.class).withAnyArguments().thenReturn(storesServiceMngSpy);

	}
	
	private void mockServiceArticlesMngWithJsonOfArticles(String nameFileArticlesJson) throws Exception {
		//Get data from file articles JSON
		LinkedHashMap<String,Object> dataJsonArticlesStock = getDataFromArchiveJson(nameFileArticlesJson);
		
		//Mock method that return articles from ServiceMango
		ArticlesStockServiceMng articlesServiceMngSpy = PowerMockito.spy(new ArticlesStockServiceMng(appForTest, urlForTest));
		PowerMockito.doReturn(dataJsonArticlesStock).when(articlesServiceMngSpy, "invokeServiceMangoForObtainArticles", Mockito.anyString(), Mockito.anyString());
		
		//Return mock when create the ArticlesStockFactory
		PowerMockito.spy(ArticlesStockFactory.class);
		PowerMockito.when(ArticlesStockFactory.make(SourceArticles.ServiceMango, appForTest, urlForTest)).thenReturn(articlesServiceMngSpy);
	}
	
	private LinkedHashMap<String,Object> getDataFromArchiveJson(String archiveJson) throws Exception {
		TypeReference<LinkedHashMap<String,Object>> typeRef = new TypeReference<LinkedHashMap<String,Object>>() {};
		InputStream dataInputStream = ManagerArticlesStockTest.class.getResourceAsStream(archiveJson);
		ObjectMapper mapper = new ObjectMapper();
		LinkedHashMap<String,Object> dataJson = mapper.readValue(dataInputStream, typeRef);
		return dataJson;
	}
}
