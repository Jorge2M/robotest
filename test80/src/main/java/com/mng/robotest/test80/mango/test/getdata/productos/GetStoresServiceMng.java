package com.mng.robotest.test80.mango.test.getdata.productos;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mng.testmaker.service.webdriver.utils.WebUtils;

public class GetStoresServiceMng {

	String urlTestAppMango;
	
	public GetStoresServiceMng(String urlTestAppMango) throws Exception {
        WebUtils.acceptAllCertificates();
		this.urlTestAppMango = urlTestAppMango;
	}
	
    public ArrayList<Store> getStores(String countryId) throws Exception {
    	LinkedHashMap<String, Object> dataJsonStoresCountry = invokeServiceMangoForObtainStores(countryId);
		return (mapDataJsonToListStores(countryId, dataJsonStoresCountry));
    }
    
    private LinkedHashMap<String, Object> invokeServiceMangoForObtainStores(String countryId) throws Exception {
    	String urlServiceMangoStock = getUrlService(null, countryId);
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<LinkedHashMap<String,Object>> typeRef = new TypeReference<LinkedHashMap<String,Object>>() {};
		return (mapper.readValue(new URL(urlServiceMangoStock), typeRef));
    }
    
    
    @SuppressWarnings("unchecked")
	private ArrayList<Store> mapDataJsonToListStores(String countryId, LinkedHashMap<String,Object> dataJsonArticles) {
    	ArrayList<Store> listStoresToReturn = new ArrayList<>();
    	ArrayList<LinkedHashMap<String,Object>> listCountries = (ArrayList<LinkedHashMap<String,Object>>)dataJsonArticles.get("countries");
    	if (listCountries!=null) {
	    	LinkedHashMap<String,Object> country = listCountries.get(0);
	    	ArrayList<LinkedHashMap<String,Object>> listStoresCountry = (ArrayList<LinkedHashMap<String,Object>>)country.get("stores");
	    	for (LinkedHashMap<String,Object> storeJson : listStoresCountry) {
	    		Store store = new Store();
	    		store.sale = (String)storeJson.get("sale");
	    		store.storeId = (String)storeJson.get("store");
	    		store.countryId = countryId;
	    		listStoresToReturn.add(store);
	    	}
    	}
    	
    	return listStoresToReturn;
    }
	
    /**
     * Invoca al servicio JSON para obtener datos de los países (sin incluir el stock) asociados a un almacén
     * http://shop.mango.com/services/shopconfig-countries/countries/storeId/countryId
     * Obtenemos los países de un determinado almacén
     * @param storeId
     * @param countryId
     * Si storeId null / countryId null    -> Obtenemos datos de todos los países (sin el stock)
     * Si storeId != null / countryId null -> Obtenemos la lista de países de un almacén (sin el stock)
     * Si storeId null / countryId != null -> Obtenemos sólo la información del país indicado (sin el stock)
     */
    private String getUrlService(String storeId, String countryId) throws Exception {
    	URI uriBaseTest = new URI(urlTestAppMango);
    	String url = "https://" +  uriBaseTest.getHost() + "/services/shopconfig-countries/countries"; 
    	if (!(countryId==null && storeId==null)) {
    		url+= "/" + storeId;
    		if (countryId!=null) {
    			url+= "/" + countryId;
    		}
    	}
    	
    	return url;
    }
}
