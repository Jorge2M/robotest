package com.mng.robotest.tests.domains.loyalty.getdata;

import static org.apache.http.impl.client.HttpClients.createDefault;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.service.exceptions.NotFoundException;
import com.mng.robotest.tests.domains.loyalty.beans.User;
import com.mng.robotest.tests.domains.loyalty.getdata.data.ListConsumers;
import com.mng.robotest.tests.domains.loyalty.getdata.data.ResultAddPoints;
import com.mng.robotest.tests.domains.loyalty.getdata.data.TransferPoints;

public class ClientApiLoyaltyPointsDev {

    private final HttpClient httpClient = createDefault();
	private static final Logger logger = Log4jTM.getLogger();
	private static final Map<String,ListConsumers> consumerDataCache = new HashMap<>();
	
	public ClientApiLoyaltyPointsDev() {}
	
	public ResultAddPoints addLoyaltyPoints(User user, int loyaltyPoints) {
		//En los servidores de Robotest la llamada a consumer devuelve un 401
		//String idConsumer = getContactIdConsumer(emailConsumer);
		//String countryConsumer = getCountryConsumer(userTest.getEmail());
		return addLoyaltyPoints(loyaltyPoints, user);
	}
	
	public String getContactIdConsumer(String emailConsumer) throws Exception {
		return getDataConsumer(emailConsumer)
				.getResults().get(0)
				.getEmails().get(0)
				.getContactId();
	}
	public String getCountryConsumer(String emailConsumer) throws Exception {
		return getDataConsumer(emailConsumer)
				.getResults().get(0)
				.getPersonal()
				.getCountry();
	}
	
	public ListConsumers getDataConsumer(String emailConsumer) throws Exception {
		var listConsumers = getDataConsumerFromCache(emailConsumer);
		if (listConsumers==null) {
			listConsumers = getDataConsumerFromRest(emailConsumer);
			storeDataConsumerInCache(emailConsumer, listConsumers);
		}
		return listConsumers;
	}
	
	private ListConsumers getDataConsumerFromRest(String emailCustomer) throws Exception {
		String url = "https://iosb.mango.com/osb/api/consumer/search";
		var builder = new URIBuilder(url);
		builder
			.setParameter("originType", "12")
			.setParameter("touchpoint", "10251")
			.setParameter("owner", "1")
			.setParameter("input", emailCustomer)
			.setParameter("page", "1")
			.setParameter("pageSize", "10");
		
		var get = new HttpGet(builder.build());
	    get.addHeader("Content-Type", MediaType.APPLICATION_JSON);
	    
	    var response = httpClient.execute(get);
	    int status = response.getStatusLine().getStatusCode();
	    if (status!=200) {
        	String message = String.format("Error %s calling %s", response.getStatusLine().getStatusCode(), url); 
            logger.error(message);
            throw new NotFoundException(message);
	    }
	    
        return new ObjectMapper()
        		.readValue(response.getEntity().getContent(), ListConsumers.class);
	}
	
	private ListConsumers getDataConsumerFromCache(String emailConsumer) {
		return consumerDataCache.get(emailConsumer); 
	}
	private void storeDataConsumerInCache(String emailConsumer, ListConsumers listConsumers) {
		consumerDataCache.put(emailConsumer, listConsumers);
	}
	
	public ResultAddPoints addLoyaltyPoints(int loyaltyPoints, User user) {
		var transferPoints = new TransferPoints();
		transferPoints.setScore(loyaltyPoints);
		transferPoints.setCountry(user.getCountry());
		transferPoints.setLocation_id(11667);
		transferPoints.setComments("hola");
				
		return 
			ClientBuilder.newBuilder().build()
				.target("https://api.loyal.guru/profiles")
				.path(user.getContactId())
				.path("give_score")	
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Basic dmljdG9yLnBhcmVyYStwcmVAbWFuZ28uY29tOmVjOWU0NmQ5NzIwMWNjN2U0Nzg0NTgxM2FkZWU1MTE4")
				.post(Entity.json(transferPoints), ResultAddPoints.class);
	}
	
}
