package com.mng.robotest.tests.domains.micuenta.repository;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.repository.customerrepository.entity.Token;

public class TokenClient {

	private final String urlIdp;
    private static final String USERNAME = "loggedAppCustomer";
    private static final String PASSWORD = "secret";
	
	public TokenClient(String urlBase) {
		var environment = PageBase.getEnvironment(urlBase);
		switch (environment) {
		case PRODUCTION:
			this.urlIdp = "https://mngidp.pro.mango.com";
			break;
		case DEVELOPMENT:
			this.urlIdp = "https://mngidp.dev.mango.com";
			break;
		case PREPRODUCTION:
		default:
			this.urlIdp = "https://mngidp.pre.mango.com";
		}
	}
	
    public String getCustomerToken(String username, String password) {
        var client = getClient();
        var feature = HttpAuthenticationFeature.basic(USERNAME, PASSWORD);
        client.register(feature);
        
        Form form = new Form();
        form.param("grant_type", "password");
        form.param("username", username);
        form.param("password", password);
        
        Response response = client.target(urlIdp + "/oauth/token")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.form(form));
        
        var token = response.readEntity(Token.class);
        client.close();
        return token.access_token;
    }
    
    protected Client getClient() {
    	return ClientBuilder.newClient();
    }

}
