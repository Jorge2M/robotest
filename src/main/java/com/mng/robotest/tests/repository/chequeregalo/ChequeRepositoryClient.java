package com.mng.robotest.tests.repository.chequeregalo;

import java.math.BigDecimal;
import java.util.Optional;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.mng.robotest.tests.repository.chequeregalo.entity.ChequeRegaloOutput;
import com.mng.robotest.tests.repository.customerrepository.BaseTokenClient;

public class ChequeRepositoryClient extends BaseTokenClient {

	private String urlChequeBase = "https://apitest2.mango.com/uat-p-giftcard";
	
	public Optional<ChequeRegaloOutput> create(BigDecimal amount, String codpais) {
		String bearerToken = getGiftCardToken();
		
        var builder = UriBuilder.fromUri(urlChequeBase)
        		.path("register");
        String urlCheque = builder.build().toString();

        var client = getClient();
        try {
        	String body = "{\"amount\": \"" + amount + "\"}";
            Response response = client.target(urlCheque)
                    .request(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Bearer " + bearerToken)
                    .header("account", "web-" + codpais)
                    .post(Entity.entity(body, MediaType.APPLICATION_JSON));

            if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
                return Optional.of(response.readEntity(ChequeRegaloOutput.class));
            }

            Log4jTM.getLogger().error("Error creating GiftCard: {} ({})", response.getStatus(), response.getStatusInfo());
            return Optional.empty();
        } finally {
            client.close();
        }
	}

}
