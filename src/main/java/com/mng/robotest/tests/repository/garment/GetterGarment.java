package com.mng.robotest.tests.repository.garment;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientProperties;

import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.tests.repository.UtilsRepository;
import com.mng.robotest.tests.repository.garment.entity.GarmentFicha;

public class GetterGarment {

	private final Client client = ClientBuilder.newBuilder().build();
	private final String target;
	private final String nameCloudTest = UtilsRepository.getNameCloudTest();
	private final String stockId;
	
	public GetterGarment(String stockId) {
		this(stockId, ((InputParamsMango)TestMaker.getInputParamsSuite()).getUrlBase());
	}
	
	public GetterGarment(String stockId, String initialURL) {
		this.stockId = stockId;
		this.target = UtilsRepository.getUrlBase(initialURL);
	}
	
	public GarmentFicha getGarment(String idGarment) {
		var webTarget = getWebTargetGarment(idGarment);
		var builder = webTarget
				.request(MediaType.APPLICATION_JSON)
				.header("stock-id", stockId);
		
		if ("".compareTo(nameCloudTest)!=0) {
			builder = builder.cookie("cloudtest-name", nameCloudTest);
		}
		var response = builder.get();
		if (response.getStatus()==Response.Status.OK.getStatusCode()) {
			return response.readEntity(GarmentFicha.class);
		}
		return null;
	}
	
	private WebTarget getWebTargetGarment(String idGarment) {
		return client
				.target(target)
				.path("services")
				.path("garments")
				.path(idGarment)
				.property(ClientProperties.FOLLOW_REDIRECTS, Boolean.TRUE);
	}
	
}
