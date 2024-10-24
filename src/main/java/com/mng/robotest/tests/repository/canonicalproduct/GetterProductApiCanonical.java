package com.mng.robotest.tests.repository.canonicalproduct;

import java.util.Optional;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mng.robotest.tests.repository.canonicalproduct.entity.EntityProduct;

public class GetterProductApiCanonical {

	private static final String TARGET = "https://internal-canonical-products.pro.k8s.mango/v1/products";
	private final String codPaisAlf;
	private final String codIdiomAlf;
	private final String channelId;
	
	public GetterProductApiCanonical(String codPaisAlf, String codIdiomAlf, String channelId) {
		this.codPaisAlf = normalizeCodPais(codPaisAlf);
		this.codIdiomAlf = codIdiomAlf;
		this.channelId = channelId;
	}
	
	public Optional<EntityProduct> getProduct(String idProducto) throws Exception {
		var webTarget = getWebTarget(idProducto);
		var response = webTarget.request(MediaType.APPLICATION_JSON).get();
		if (response.getStatus()==Response.Status.OK.getStatusCode()) {
			String responseBody = response.readEntity(String.class);
			var productRedis = new ObjectMapper().readValue(
					responseBody, EntityProduct.class);
			return Optional.of(productRedis);
		}
		return Optional.empty();
	}
	
	private WebTarget getWebTarget(String idProducto) {
		var client = ClientBuilder.newBuilder().build();
		return 
			client
				.target(TARGET)
				.path(idProducto)
				.queryParam("countryId", codPaisAlf)
				.queryParam("languageId", codIdiomAlf)
				.queryParam("channelId", channelId);
	}	
	
	private String normalizeCodPais(String codPaisAlf) {
		if (codPaisAlf.compareTo("ES1")==0) {
			return "ES-CN";
		}
		return codPaisAlf;
	}
	
}
