package com.mng.robotest.tests.repository.productlist.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.mng.robotest.tests.repository.UtilsData;
import com.mng.robotest.tests.repository.productlist.entity.GarmentCatalog;
import com.mng.robotest.tests.repository.productlist.entity.GarmentDetails;
import com.mng.robotest.tests.repository.productlist.entity.GarmentCatalog.Article;

public class FilterTotalLook implements Filter {

	private final String urlForJavaCall;
	private final String stockId;
	
	public FilterTotalLook(String urlForJavaCall, String stockId) {
		this.urlForJavaCall = urlForJavaCall;
		this.stockId = stockId;
	}
	
	@Override
	public List<GarmentCatalog> filter(List<GarmentCatalog> garments) throws Exception {
		List<GarmentCatalog> listFiltered = new ArrayList<>();
		for (var garment : garments) {
			if (getTotalLookGarment(garment)!=null) {
				listFiltered.add(garment);
			}
		}
		return listFiltered;
	}
	
	@Override
	public Optional<GarmentCatalog> getOne(List<GarmentCatalog> garments) throws Exception {
		for (var garment : garments) {
			if (getTotalLookGarment(garment)!=null) {
				return Optional.of(garment);
			}
		}
		return Optional.empty();
	}
	
	private GarmentDetails getTotalLookGarment(GarmentCatalog product) {
		var webTarget = getWebTargetTotalLookGarment(product);
		var builder = webTarget
				.request(MediaType.APPLICATION_JSON)
				.header("stock-id", stockId);
		
		String nameCloudTest = UtilsData.getNameCloudTest();
		if ("".compareTo(nameCloudTest)!=0) {
			builder = builder.cookie("cloudtest-name", nameCloudTest);
		}
		var response = builder.get();
		if (response.getStatus()==Response.Status.OK.getStatusCode()) {
			try {
				return response.readEntity(GarmentDetails.class);
			}
			catch (Exception e) {
				Log4jTM.getLogger().warn(String.format("Problem in call to %s. Status response %s", webTarget.getUri(), response.getStatus()), e);
				return null;
			}
		} else {
			Log4jTM.getLogger().warn(() -> String.format("Problem in call to %s. Status response %s", webTarget.getUri(), response.getStatus()));
			return null;
		}

	}
	
	private WebTarget getWebTargetTotalLookGarment(GarmentCatalog product) {
		var article = Article.getArticleForTest(product);
		var client = ClientBuilder.newClient();
		return ( 
			client
				.target(urlForJavaCall.replace("http:", "https:") + "services/garments")
				.path(article.getGarmentId())
				.path("looktotal")
				.queryParam("color", article.getColor().getId()));
	}
	
}
