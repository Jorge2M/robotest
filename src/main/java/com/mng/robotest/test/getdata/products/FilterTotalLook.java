package com.mng.robotest.test.getdata.products;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.getdata.UtilsData;
import com.mng.robotest.test.getdata.products.data.GarmentCatalog;
import com.mng.robotest.test.getdata.products.data.GarmentDetails;
import com.mng.robotest.test.getdata.products.data.GarmentCatalog.Article;

public class FilterTotalLook implements Filter {

	private final String urlForJavaCall;
	private final AppEcom app;
	private final String stockId;
	
	public FilterTotalLook(String urlForJavaCall, AppEcom app, String stockId) {
		this.urlForJavaCall = urlForJavaCall;
		this.app = app;
		this.stockId = getIdStockNormalized(stockId);
	}
	
	@Override
	public List<GarmentCatalog> filter(List<GarmentCatalog> garments) throws Exception {
		List<GarmentCatalog> listFiltered = new ArrayList<>();
		for (GarmentCatalog garment : garments) {
			if (getTotalLookGarment(garment)!=null) {
				listFiltered.add(garment);
			}
		}
		return listFiltered;
	}
	
	@Override
	public Optional<GarmentCatalog> getOne(List<GarmentCatalog> garments) throws Exception {
		for (GarmentCatalog garment : garments) {
			if (getTotalLookGarment(garment)!=null) {
				return Optional.of(garment);
			}
		}
		return Optional.empty();
	}
	
	private GarmentDetails getTotalLookGarment(GarmentCatalog product) throws Exception {
		WebTarget webTarget = getWebTargetTotalLookGarment(product);
		Builder builder = webTarget
				.request(MediaType.APPLICATION_JSON)
				.header("stock-id", stockId);
		
		String nameCloudTest = UtilsData.getNameCloudTest();
		if ("".compareTo(nameCloudTest)!=0) {
			builder = builder.cookie("cloudtest-name", nameCloudTest);
		}
		Response response = builder.get();

		if (response.getStatus()==Response.Status.OK.getStatusCode()) {
			try {
				return response.readEntity(GarmentDetails.class);
			}
			catch (Exception e) {
				Log4jTM.getLogger().warn(String.format("Problem in call to %s. Status response %s", webTarget.getUri(), response.getStatus()), e);
				return null;
			}
		} else {
			Log4jTM.getLogger().warn(String.format("Problem in call to %s. Status response %s", webTarget.getUri(), response.getStatus()));
			return null;
		}

	}
	
	private WebTarget getWebTargetTotalLookGarment(GarmentCatalog product) {
		Article article = product.getArticleWithMoreStock();
		Client client = ClientBuilder.newClient();
		return ( 
			client
				.target(urlForJavaCall.replace("http:", "https:") + "services/garments")
				.path(article.getGarmentId())
				.path("looktotal")
				.queryParam("color", article.getColor().getId()));
	}
	
	private String getIdStockNormalized(String stockId) {
		if (app==AppEcom.votf) {
			//Por algún motivo que no entiendo, falla "001.ES.0.false.true.v0" pero funciona "001.ES.0.false.false.v0"
			return (stockId
						.replace("false.true", "false.false")
						.replace("true.true", "true.false"));
		}
		return stockId;
	}
}
