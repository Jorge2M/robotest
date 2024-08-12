package com.mng.robotest.tests.repository.customerrepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mng.robotest.tests.repository.customerrepository.entity.Token;

public class BaseTokenClient {

	public String getGiftCardToken() {
		return getToken("0oarwtf6aLaBluCTr0x6", "q4mEMEjEWOQep3Tsu37oJvrHL1KKvQ47yQANq2Jt");
	}
	
	public String getCustomerToken() {
		return getToken("0oaosagb7QtCnny7Q0x6", "J1Y_nEvCnrzRzr9uC3oKb93xWf8BAeUBUTkj8C63");
	}
	
    private String getToken(String username, String password) {
        String url = "https://mango.oktapreview.com/oauth2/default/v1/token";
        String grantType = "client_credentials";
        String scope = "mango";
        
        var client = getClient();
        
        Form form = new Form();
        form.param("grant_type", grantType);
        form.param("scope", scope);
        
        Response response = client.target(url)
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Basic " + java.util.Base64.getEncoder().encodeToString((username + ":" + password).getBytes()))
                .post(Entity.form(form));
        
        var token = response.readEntity(Token.class);
        client.close();
        return token.access_token;
    }
    
    protected String getDate2HourEarlier() {
        var now = LocalDateTime.now();
        var oneHourEarlier = now.minusHours(2);
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        return oneHourEarlier.format(formatter);
    }
    
    protected Client getClient() {
    	return ClientBuilder.newClient();
    }

}
