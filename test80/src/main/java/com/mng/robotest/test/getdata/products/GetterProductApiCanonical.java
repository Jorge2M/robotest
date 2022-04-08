package com.mng.robotest.test.getdata.products;

import java.util.Optional;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.mng.robotest.domains.apiproduct.entity.ProductRedis;
import com.mng.robotest.test.getdata.JaxRsClient;


public class GetterProductApiCanonical extends JaxRsClient {

	private final Client client = getClient();
	private final String target = "https://internal-canonical-products.pre.k8s.mango/v1/products";
	private final String codPaisAlf;
	private final String codIdiomAlf;
	
	public GetterProductApiCanonical(String codPaisAlf, String codIdiomAlf) {
		this.codPaisAlf = normalizeCodPais(codPaisAlf);
		this.codIdiomAlf = codIdiomAlf;
	}
	
	public Optional<ProductRedis> getProduct(String idProducto) throws Exception {
		WebTarget webTarget = getWebTarget(idProducto);
		Response response = webTarget.request(MediaType.APPLICATION_JSON).get();
		if (response.getStatus()==Response.Status.OK.getStatusCode()) {
			String responseBody = response.readEntity(String.class);
			ObjectMapper objectMapper = new ObjectMapper();
			ProductRedis productRedis = objectMapper.readValue(responseBody, ProductRedis.class);
			return Optional.of(productRedis);
		}
		return Optional.empty();
	}
	
	private WebTarget getWebTarget(String idProducto) throws Exception {
		WebTarget webTarget = 
			client
				.target(target)
				.path(idProducto)
				.queryParam("countryId", codPaisAlf)
				.queryParam("languageId", codIdiomAlf);
		
		return webTarget;
	}	
	
	private Client getClient() {
		try {
			return getClientIgnoreCertificates();
		} catch (Exception e) {
			Log4jTM.getLogger().error("Exception getting rest client ", e);
			return null;
		}
	}
	
	private String normalizeCodPais(String codPaisAlf) {
		switch (codPaisAlf) {
		case "ES1":
			return "ES-CN";
		default:
			return codPaisAlf;
		}
	}
	
}
