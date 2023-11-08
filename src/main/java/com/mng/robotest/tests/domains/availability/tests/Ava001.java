package com.mng.robotest.tests.domains.availability.tests;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.ficha.steps.PageFichaSteps;
import com.mng.robotest.tests.domains.galeria.steps.PageGaleriaSteps;
import com.mng.robotest.tests.domains.transversal.home.steps.PageLandingSteps;
import com.mng.robotest.tests.domains.transversal.menus.steps.MenuSteps;
import com.mng.robotest.tests.repository.productlist.GetterProducts;
import com.mng.robotest.tests.repository.productlist.entity.GarmentCatalog;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.beans.Pais;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ava001 extends TestBase {
	
//	private static final List<LineaType> LINES_TO_CHECK = Arrays.asList(SHE, HE, NINA, TEEN, HOME);
	private static final int MAX_CATALOGS = 10;
	private static final int MAX_FICHAS = 4;
	
	private final String countryId; 
	private final String lang;
	
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
		//checkLines();
	}
	
	private void checkMenuLineas() {
		new MenuSteps().checkLineasCountry();
	}
	
	private void checkLanding() {
		var pageLandingSteps = new PageLandingSteps();
		pageLandingSteps.checkIsPage(5);
		pageLandingSteps.checkIsPageWithCorrectLineas();
	}	
	
	private void checkCatalogAndFicha() throws Exception {
        checkCatalogsAvailable();
        checkFichasAvailable();
    }
	
    private void checkFichasAvailable() throws Exception {
        var randomGarmentIds = getRandomUrlProductsPage();
        var pageFichaSteps = new PageFichaSteps();
        for (String urlFicha : randomGarmentIds) {
        	pageFichaSteps.loadFicha(urlFicha);
        }
    }	
    
    private void checkCatalogsAvailable() throws Exception {
    	var pageGaleriaSteps = new PageGaleriaSteps();
        for (var urlCatalog : retrieveRandomUrlCatalogsFromMenu()) {
           	pageGaleriaSteps.loadCatalog(urlCatalog);
        }
    }    
    
    private List<String> retrieveRandomUrlCatalogsFromMenu() throws Exception {
    	String baseUrl = inputParamsSuite.getDnsUrlAcceso(); 
        var client = HttpClient.newBuilder().build();
        var request = HttpRequest.newBuilder()
            .uri(URI.create(baseUrl + "/services/menu/v1.0/shop/desktop/" + countryId + "/" + lang + "?isMobile=" + channel.isDevice()))
            .build();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        var json = JsonParser.parseString(response.body()).getAsJsonObject().get("menus").getAsJsonArray();
        List<String> allLinks = new ArrayList<>();
        collectLinks(json, true, allLinks);
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
    
    private void collectLinks(JsonArray jsonArray, boolean first, List<String> allLinks) 
    		throws Exception {
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
            if (jsonObject.has("menus")) {
                collectLinks(jsonObject.getAsJsonArray("menus"), false, allLinks);
            }
        }
    }
	
//	private void checkLines() throws Exception {
//		for (var linea : dataTest.getLineas()) {
//			if (mustBeChecked(linea)) {
//				var firstSublinea = getFirstSublinea(linea);
//				checkLine(linea, firstSublinea);
//			}
//		}		
//	}
//	
//	private SublineaType getFirstSublinea(Linea linea) {
//		var listLineas = linea.getListSublineas(app);
//		if (listLineas==null || listLineas.isEmpty()) {
//			return null;
//		}
//		return listLineas.get(0).getTypeSublinea();
//	}
//	
//	private void checkLine(Linea linea, SublineaType sublinea) {
//		clickLinea(linea.getType(), sublinea);
//		clickMenu(linea.getType(), sublinea);
//	}
//	
//	private boolean mustBeChecked(Linea line) {
//		return 
//			LINES_TO_CHECK.contains(line.getType()) &&
//			new UtilsMangoTest().isLineActive(line);
//	}
//	
//	private void clickMenu(LineaType lineaType, SublineaType sublineaType) {
//		var menu = getMenuToTest(lineaType); 
//		clickMenu(new MenuWeb
//			.Builder(menu.getRight())
//			.linea(lineaType)
//			.sublinea(sublineaType)
//			.group(menu.getLeft())
//			.build());
//	}	
//	
//	private Pair<GroupType, String> getMenuToTest(LineaType lineaType) {
//		var groupType = GroupType.PRENDAS;
//		String menu = "pantalones";
//		if (lineaType==NINA || lineaType==NINO) {
//			menu = "camisas";
//		}
//		if (lineaType==HOME) {
//			groupType = GroupType.DORMITORIO;
//			menu = "mantas_dormitorio";
//		}
//		return Pair.of(groupType, menu);
//	}
	
}
