package com.mng.robotest.test.getdata.products;

import java.util.Optional;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mng.robotest.domains.apiproduct.entity.ProductRedis;
import com.mng.robotest.test.getdata.JaxRsClient;


public class GetterProductApiCanonical extends JaxRsClient {

	private final String target = "https://internal-canonical-products.dev.k8s.mango/v1/products";
	private final String codPaisAlf;
	private final String codIdiomAlf;
	private final String idProducto;
	
	public GetterProductApiCanonical(String codPaisAlf, String codIdiomAlf, String idProducto) {
		this.codPaisAlf = codPaisAlf;
		this.codIdiomAlf = codIdiomAlf;
		this.idProducto = idProducto;
	}
	
	public Optional<ProductRedis> getProduct() throws Exception {
		WebTarget webTarget = getWebTarget();
		Response response = webTarget.request(MediaType.APPLICATION_JSON).get();
		if (response.getStatus()==Response.Status.OK.getStatusCode()) {
			String responseBody = response.readEntity(String.class);
			ObjectMapper objectMapper = new ObjectMapper();
			//objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			ProductRedis productRedis = objectMapper.readValue(responseBody, ProductRedis.class);
			return Optional.of(productRedis);
		}
		return Optional.empty();
	}
	
	private WebTarget getWebTarget() throws Exception {
		Client client = getClientIgnoreCertificates();
		WebTarget webTarget = 
			client
				.target(target)
				.path(idProducto)
				.queryParam("countryId", codPaisAlf)
				.queryParam("languageId", codIdiomAlf);
		
		return webTarget;
	}	
	
	
}
