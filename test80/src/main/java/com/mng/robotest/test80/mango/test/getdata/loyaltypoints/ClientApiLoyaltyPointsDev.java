package com.mng.robotest.test80.mango.test.getdata.loyaltypoints;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import com.mng.robotest.test80.mango.test.appshop.Loyalty.UserTest;
import com.mng.robotest.test80.mango.test.getdata.JaxRsClient;
import com.mng.robotest.test80.mango.test.getdata.loyaltypoints.data.ListConsumers;
import com.mng.robotest.test80.mango.test.getdata.loyaltypoints.data.ResultAddPoints;
import com.mng.robotest.test80.mango.test.getdata.loyaltypoints.data.TransferPoints;

public class ClientApiLoyaltyPointsDev extends JaxRsClient {
	
	private static final Map<String,ListConsumers> consumerDataCache = new HashMap<>();
	
	public ClientApiLoyaltyPointsDev() {}
	
	public ResultAddPoints addLoyaltyPoints(UserTest user, int loyaltyPoints) throws Exception {
		//En los servidores de Robotest la llamada a consumer devuelve un 401
		//String idConsumer = getContactIdConsumer(emailConsumer);
		//String countryConsumer = getCountryConsumer(userTest.getEmail());
		return (
			addLoyaltyPoints(loyaltyPoints, user));
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
		ListConsumers listConsumers = getDataConsumerFromCache(emailConsumer);
		if (listConsumers==null) {
			listConsumers = getDataConsumerFromRest(emailConsumer);
			storeDataConsumerInCache(emailConsumer, listConsumers);
		}
		return listConsumers;
	}
	
	private ListConsumers getDataConsumerFromRest(String emailCustomer) throws Exception {
		Client client = getClientIgnoreCertificates();
		ListConsumers resultsEmail = 
			client
				.target("https://iosb.mango.com/osb/api/consumer/search")
				.queryParam("originType", "12")
				.queryParam("touchpoint", "10251")
				.queryParam("owner", "1")
				.queryParam("input", emailCustomer)
				.queryParam("page", "1")
				.queryParam("pageSize", 10)
				.request(MediaType.APPLICATION_JSON)
				.get(ListConsumers.class);
		
		return resultsEmail;
	}
	
	private ListConsumers getDataConsumerFromCache(String emailConsumer) {
		return (ListConsumers)consumerDataCache.get(emailConsumer); 
	}
	private void storeDataConsumerInCache(String emailConsumer, ListConsumers listConsumers) {
		consumerDataCache.put(emailConsumer, listConsumers);
	}
	
	public ResultAddPoints addLoyaltyPoints(int loyaltyPoints, UserTest user) 
	throws Exception {
		TransferPoints transferPoints = new TransferPoints();
		transferPoints.setScore(loyaltyPoints);
		transferPoints.setCountry(user.getCountry());
		transferPoints.setLocation_id(11667);
		transferPoints.setComments("hola");
				
		Client client = getClientIgnoreCertificates();
		ResultAddPoints result = 
			client
				.target("https://api.loyal.guru/profiles")
				.path(user.getContactId())
				.path("give_score")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Basic dmljdG9yLnBhcmVyYStwcmVAbWFuZ28uY29tOjMyNGU1MTQ0MmUyMDc0YzUwYjZlODFlOGU4MTE0Y2Fk")
				.post(Entity.json(transferPoints), ResultAddPoints.class);
		
		return result;
	}
}
