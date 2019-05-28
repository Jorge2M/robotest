package com.mng.robotest.test80.mango.test.getdata.productos;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.AppEcom;
import com.mng.robotest.test80.mango.test.getdata.productos.ArticlesStockFactory.SourceArticles;
import com.mng.robotest.test80.mango.test.getdata.productos.ManagerArticlesStock.TypeArticleStock;

public class ArticlesStockServiceMng implements ArticlesStockGetter {
	static Logger pLogger = LogManager.getLogger(fmwkTest.log4jLogger);
	
	AppEcom app;
	String urlTestAppMango;
	
	public ArticlesStockServiceMng(AppEcom app, String urlTestAppMango) throws Exception {
		this.app = app;
		this.urlTestAppMango = urlTestAppMango;
	}

	@Override
    public ArrayList<ArticleStock> getProducts(String storeId, String countryId) throws Exception {
		LinkedHashMap<String,Object> dataJsonArticlesStock = invokeServiceMangoForObtainArticles(storeId, countryId);
		return (mapDataJsonToListArticles(countryId, dataJsonArticlesStock));
    }
	
	protected LinkedHashMap<String,Object> invokeServiceMangoForObtainArticles(String storeId, String countryId) 
	throws Exception {
    	String urlServiceMangoStock = getUrlService(storeId, countryId);
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<LinkedHashMap<String,Object>> typeRef = new TypeReference<LinkedHashMap<String,Object>>() {};
		LinkedHashMap<String,Object> dataJsonArticlesStock = null;
		try {
			pLogger.info("Llamando al servicio para obtener artículos: " + urlServiceMangoStock);
			dataJsonArticlesStock = mapper.readValue(new URL(urlServiceMangoStock), typeRef);
		}
		catch (Exception e) {
			pLogger.warn("Error en la llamada al servicio: " + urlServiceMangoStock, e);
		}
		
		return (dataJsonArticlesStock);
	}
    
	@Override
	public void deleteProduct(String producto) {
	}
	
    @SuppressWarnings("unchecked")
	private ArrayList<ArticleStock> mapDataJsonToListArticles(String countryId, LinkedHashMap<String,Object> dataJsonArticles) {
    	if (dataJsonArticles==null) {
    		return null;
    	}
    	
    	ArrayList<ArticleStock> listArticlesToReturn = new ArrayList<>();
    	ArrayList<LinkedHashMap<String,Object>> listStores = (ArrayList<LinkedHashMap<String,Object>>)dataJsonArticles.get("stock");
    	if (listStores!=null) {
	    	for (LinkedHashMap<String,Object> store : listStores) {
	    		for (TypeArticleStock typeArticle : TypeArticleStock.values())
	    			listArticlesToReturn.addAll(getArticlesStockFromStore(store, countryId, typeArticle));
	    	}
    	}
    	
    	return listArticlesToReturn;
    }
    
	@SuppressWarnings("unchecked")
    private ArrayList<ArticleStock> getArticlesStockFromStore(LinkedHashMap<String,Object> store, String countryId, TypeArticleStock typeArticle) {
    	ArrayList<ArticleStock> listArticlesToReturn = new ArrayList<>();
		ArrayList<LinkedHashMap<String,Object>> listArticlesGroup = (ArrayList<LinkedHashMap<String,Object>>)store.get(typeArticle.toString());
		if (listArticlesGroup!=null) {
			for (LinkedHashMap<String,Object> articleData : listArticlesGroup) {
				ArticleStock articleStock = new ArticleStock();
				articleStock.app = app;
				articleStock.urlApp = urlTestAppMango;
				articleStock.idAlmacen = (String)store.get("store");
				articleStock.idCountry = countryId;
				articleStock.type = typeArticle;
				articleStock.source = SourceArticles.ServiceMango;
				articleStock.idArticle = (String)articleData.get("id");
				articleStock.season = (String)articleData.get("season");
				articleStock.size = (String)articleData.get("size");
				articleStock.colourCode = (String)articleData.get("colour");
				listArticlesToReturn.add(articleStock);
			}
		}
		
		return listArticlesToReturn;
    }
    
    /**
     * Construye la URL correspondiente al servicio de stock.
     * Por ejemplo: http://shop.mango.com/services/shopconfig-stock/stock/001/001
     * @param storeId 
     * @param countryId
     *   Si storeId != null / countryId != null -> Obtenemos los datos de Stock del almacén/país
     *   Si storeId null / countryId null       -> Obtenemos datos de todos los países (sin el stock)
	 *   Si storeId != null / countryId null    -> Obtenemos la lista de países de un almacén (sin el stock)
	 *   Si storeId null / countryId != null    -> Obtenemos sólo la información del país indicado (sin el stock)
     */
    private String getUrlService(String storeId, String countryId) throws Exception {
		URI urlTestAppMangoURI = new URI(urlTestAppMango);
	    String url = "https://" +  urlTestAppMangoURI.getHost() + "/services/shopconfig-stock/stock";
	    if (storeId!=null) {
	        url+= "/" + storeId;
	    }
	    if (countryId!=null) {
	        url+= "/" + countryId;
	    }
	    return url;
    }
}
