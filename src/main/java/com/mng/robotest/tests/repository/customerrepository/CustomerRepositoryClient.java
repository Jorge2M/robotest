package com.mng.robotest.tests.repository.customerrepository;

import com.mng.robotest.tests.repository.customerrepository.entity.CustomerRequest;
import com.mng.robotest.tests.repository.customerrepository.entity.Personal;
import com.mng.robotest.tests.repository.customerrepository.entity.Registration;
import com.mng.robotest.tests.repository.customerrepository.entity.Email;
import com.mng.robotest.tests.repository.customerrepository.entity.Phone;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.mng.robotest.tests.repository.customerrepository.entity.Address;
import com.mng.robotest.tests.repository.customerrepository.entity.Communication;
import com.mng.robotest.tests.repository.customerrepository.entity.Consent;
import com.mng.robotest.tests.repository.customerrepository.entity.Customer;

public class CustomerRepositoryClient extends BaseCustomerClient {

	public Optional<Customer> createCustomerWithContactability(String email) {
	    return execCreateCustomerWithContactability(email);
	}
	
	public Optional<Customer> getCustomer(String email) {
		return execGetCustomer(email);
	}
	
	private Optional<Customer> execGetCustomer(String email) {
		String bearerToken = getCustomerToken();
		
        var builder = UriBuilder.fromUri(urlBase)
        		.path("contact")
                .queryParam("contact", email);
        String customerUrl = builder.build().toString();

        var client = getClient();
        try {
            Response response = client.target(customerUrl)
                    .request(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Bearer " + bearerToken)
                    .get();

            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                return Optional.of(response.readEntity(Customer.class));
            }

            Log4jTM.getLogger().error("Error getting CR customer: {} ({})", response.getStatus(), response.getStatusInfo());
            return Optional.empty();
        } finally {
            client.close();
        }
	}

	private Optional<Customer> execCreateCustomerWithContactability(String email) {
	    String bearerToken = getCustomerToken();
	    var customerRequest = getBodyForCreateCustomerWithContactability(email, true);

	    var client = getClient();
	    try {
	        Response response = client.target(urlBase)
	                .request(MediaType.APPLICATION_JSON)
	                .header("Authorization", "Bearer " + bearerToken)
	                .post(Entity.entity(customerRequest, MediaType.APPLICATION_JSON));

	        if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
	            return Optional.of(response.readEntity(Customer.class));
	        }

	        Log4jTM.getLogger().error("Error creating CR customer {} ({})", response.getStatus(), response.getStatusInfo());
	        return Optional.empty();
	    } finally {
	        client.close();
	    }
	}
	
    private CustomerRequest getBodyForCreateCustomerWithContactability(String mail, boolean contactable) {
    	var personal = new Personal();
    	personal.country = "ES";
    	personal.language = "es";
    	personal.consumerType = 1;
    	
    	var registration = new Registration();
    	registration.touchpoint = "2100000000";
    	registration.originType = 101;
    	registration.commercialArea = "001";
    	registration.modifierId = "52.213.222.146";
    	registration.creationDate = getDate2HourEarlier();
    	registration.owner = "Mango";
    	
    	var email = new Email();
    	email.contactable = contactable;
    	email.email = mail;
    	email.webAccount = false;
    	
    	var phone = new Phone();
    	phone.contactable = contactable;
    	phone.countryCode = "+34";
    	phone.phone = "665015122";
    	
    	var address = new Address();
    	address.contactable = contactable;
    	address.country = "ES";
    	
    	var communication = new Communication();
    	communication.lines = new ArrayList<>(Arrays.asList("SHE"));
    	
    	var consent = new Consent();
    	consent.agreementDate = getDate2HourEarlier();
    	consent.country = "ES";
    	consent.language = "es";
    	consent.originType = 101;
    	
    	var customerRequest = new CustomerRequest();
    	customerRequest.personal  = personal;
    	customerRequest.registration = registration;
    	customerRequest.emails = new ArrayList<>(Arrays.asList(email));
    	customerRequest.phones = new ArrayList<>(Arrays.asList(phone));
    	customerRequest.addresses = new ArrayList<>(Arrays.asList(address));
    	customerRequest.communication = communication;
    	customerRequest.consent = consent;
    	
    	return customerRequest;
    }

}
