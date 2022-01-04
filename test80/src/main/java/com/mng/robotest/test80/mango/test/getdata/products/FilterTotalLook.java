package com.mng.robotest.test80.mango.test.getdata.products;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.getdata.products.data.Garment;
import com.mng.robotest.test80.mango.test.getdata.products.data.GarmentDetails;
import com.mng.robotest.test80.mango.test.getdata.products.data.Garment.Article;

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
	public List<Garment> filter(List<Garment> garments) throws Exception {
		List<Garment> listFiltered = new ArrayList<>();
		for (Garment garment : garments) {
			if (getTotalLookGarment(garment)!=null) {
				listFiltered.add(garment);
			}
		}
		return listFiltered;
	}
	
	@Override
	public Optional<Garment> getOne(List<Garment> garments) throws Exception {
		for (Garment garment : garments) {
			if (getTotalLookGarment(garment)!=null) {
				return Optional.of(garment);
			}
		}
		return Optional.empty();
	}
	
	private GarmentDetails getTotalLookGarment(Garment product) throws Exception {
		WebTarget webTarget = getWebTargetTotalLookGarment(product);
		Response response = webTarget
				.request(MediaType.APPLICATION_JSON)
				.header("stock-id", stockId)
				.get();
		
		if (response.getStatus()==Response.Status.OK.getStatusCode()) {
			return response.readEntity(GarmentDetails.class);
		}
		return null;
	}
	
	private WebTarget getWebTargetTotalLookGarment(Garment product) {
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
