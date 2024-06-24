package com.mng.robotest.tests.domains.availability.tests;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.mng.robotest.tests.domains.availability.exceptions.CatalogsNotFoundException;
import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.ficha.steps.FichaSteps;
import com.mng.robotest.tests.domains.galeria.steps.GaleriaSteps;
import com.mng.robotest.tests.domains.landings.steps.LandingSteps;
import com.mng.robotest.tests.domains.menus.steps.MenuSteps;
import com.mng.robotest.tests.repository.productlist.GetterProducts;
import com.mng.robotest.tests.repository.productlist.entity.GarmentCatalog;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.beans.Pais;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ava001 extends TestBase {
	
	private static final int MAX_CATALOGS = 10;
	private static final int MAX_FICHAS = 4;
	
	private final String countryId; 
	private final String lang;
	
	private static final String STR_MENUS = "menus";
	
	public Ava001(Pais pais, IdiomaPais idioma) {
		this.dataTest.setPais(pais);
		this.dataTest.setIdioma(idioma);
		this.countryId = pais.getCodigoAlf();
		this.lang = idioma.getCodigo().name();
	}

	@Override
	public void execute() throws Exception {
		quickAccess();
		if (channel.isDevice()) {
			checkMenuLineas();
		}		
		checkLanding();
		checkCatalogAndFicha();
	}
	
	private void checkMenuLineas() {
		new MenuSteps().checkLineasCountry();
	}
	
	private void checkLanding() {
		var pgLandingSteps = new LandingSteps();
		pgLandingSteps.checkIsLandingMultimarca(5);
		pgLandingSteps.checkIsCountryWithCorrectLineas(2);
	}	
	
	private void checkCatalogAndFicha() throws Exception {
        checkCatalogsAvailable();
        checkFichasAvailable();
    }
	
    private void checkFichasAvailable() throws Exception {
        var randomGarmentIds = getRandomUrlProductsPage();
        var fichaSteps = new FichaSteps();
        for (int i=0; i<randomGarmentIds.size(); i++) {
        	String urlFicha = randomGarmentIds.get(i);
        	if (i==0) {
        		fichaSteps.loadFichaWithRetry(urlFicha);
        	} else {
        		fichaSteps.loadFicha(urlFicha);
        	}
        }
    }	
    
    private void checkCatalogsAvailable() {
    	var galeriaSteps = new GaleriaSteps();
        for (var urlCatalog : retrieveRandomUrlCatalogsFromMenu()) {
           	galeriaSteps.loadCatalog(urlCatalog);
        }
    }    
    private List<String> retrieveRandomUrlCatalogsFromMenu() throws CatalogsNotFoundException {
    	int maxRetrys = 2;
    	List<String> catalogsOpt = new ArrayList<>();
    	for (int i=1; i<=maxRetrys; i++) {
    		try {
    			catalogsOpt = retrieveRandomUrlCatalogsFromMenuWithoutRetry();
    		} catch (CatalogsNotFoundException e) {
    			if (i==maxRetrys) {
    				throw e;
    			}
    		}
    	}
    	return catalogsOpt;
    }
    
    private List<String> retrieveRandomUrlCatalogsFromMenuWithoutRetry() throws CatalogsNotFoundException {
        List<String> allLinks = new ArrayList<>();
        try {
	    	String baseUrl = inputParamsSuite.getDnsUrlAcceso(); 
	        var client = HttpClient.newBuilder().build();
	        var request = HttpRequest.newBuilder()
	            .uri(URI.create(baseUrl + "/services/menu/v1.0/shop/desktop/" + countryId + "/" + lang + "?isMobile=" + channel.isDevice()))
	            .build();

	        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
	        var json = JsonParser.parseString(response.body()).getAsJsonObject().get(STR_MENUS).getAsJsonArray();
	        collectLinks(json, true, allLinks);
        }
        catch (InterruptedException e) {
        	Log4jTM.getLogger().warn("Proglem getting random url catalogs from menus", e);	    	  
        	Thread.currentThread().interrupt();
	    }        
        catch (Exception e) {
        	throw new CatalogsNotFoundException("Problem getting catalogs from " + countryId + " / " + lang + " menus", e);
        }
        
        if (allLinks.size() < MAX_CATALOGS) {
        	return allLinks;
        }
        return allLinks.subList(0, MAX_CATALOGS);
    }    

    private List<String> getRandomUrlProductsPage() throws Exception {
    	String baseUrl = inputParamsSuite.getDnsUrlAcceso(); 
		var getterProducts = new GetterProducts.Builder(countryId, app, driver).build();
		var products = getterProducts.getAll().stream()
			.map(GarmentCatalog::getColors).flatMap(List::stream)
			.map(c -> baseUrl + c.getLinkAnchor())
			.toList();
		
		var productsRandom = new ArrayList<>(products);
		Collections.shuffle(productsRandom);
		return productsRandom.subList(0, MAX_FICHAS);
    }
    
    private void collectLinks(JsonArray jsonArray, boolean first, List<String> allLinks) throws URISyntaxException {
    	String baseUrl = inputParamsSuite.getDnsUrlAcceso(); 
        for (int i = 0; i < jsonArray.size(); i++) {
            var jsonObject = jsonArray.get(i).getAsJsonObject();
            if (!first) {
            	var jsonLink = jsonObject.get("link");
            	if (jsonLink!=null && !jsonLink.isJsonNull() &&
            		!jsonLink.getAsString().contains("edit") && 
            		!jsonLink.getAsString().contains("live-shopping")) {
                    allLinks.add(baseUrl + jsonLink.getAsString());
            	}
            }
            if (jsonObject.has(STR_MENUS)) {
                collectLinks(jsonObject.getAsJsonArray(STR_MENUS), false, allLinks);
            }
        }
    }
	
}
