package com.mng.robotest.getdata.canonicalproduct;

import java.util.Optional;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mng.robotest.getdata.canonicalproduct.entity.EntityProduct;


public class GetterProductApiCanonical {

	private static final String target = "https://internal-canonical-products.pro.k8s.mango/v1/products";
	private final String codPaisAlf;
	private final String codIdiomAlf;
	
	public GetterProductApiCanonical(String codPaisAlf, String codIdiomAlf) {
		this.codPaisAlf = normalizeCodPais(codPaisAlf);
		this.codIdiomAlf = codIdiomAlf;
	}
	
	public Optional<EntityProduct> getProduct(String idProducto) throws Exception {
		WebTarget webTarget = getWebTarget(idProducto);
		Response response = webTarget.request(MediaType.APPLICATION_JSON).get();
		if (response.getStatus()==Response.Status.OK.getStatusCode()) {
			String responseBody = response.readEntity(String.class);
			ObjectMapper objectMapper = new ObjectMapper();
			EntityProduct productRedis = objectMapper.readValue(responseBody, EntityProduct.class);
			return Optional.of(productRedis);
		}
		return Optional.empty();
	}
	
	private WebTarget getWebTarget(String idProducto) {
		Client client = ClientBuilder.newBuilder().build();
		return 
			client
				.target(target)
				.path(idProducto)
				.queryParam("countryId", codPaisAlf)
				.queryParam("languageId", codIdiomAlf);
	}	
	
	private String normalizeCodPais(String codPaisAlf) {
		if (codPaisAlf.compareTo("ES1")==0) {
			return "ES-CN";
		}
		return codPaisAlf;
	}
	
}
