package com.mng.robotest.tests.domains.micuenta.repository;

import java.util.Optional;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.mng.robotest.tests.domains.micuenta.repository.beans.Purchase;
import com.mng.robotest.tests.repository.UtilsRepository;

public class PurchasesRepositoryClient extends TokenClient {

	private final String urlBasePurchase;
	private final String username;
	private final String password;
	
	public PurchasesRepositoryClient(String initialURL, String username, String password) {
		super(UtilsRepository.getUrlBase(initialURL));
		this.urlBasePurchase = UtilsRepository.getUrlBase(initialURL);
		this.username = username;
		this.password = password;
	}
	
	public Optional<Purchase> getPurchase(String idPurchase) {
		String bearerToken = getCustomerToken(username, password);
		
		var builder = UriBuilder.fromUri(urlBasePurchase)
        		.path("ws-my-purchases/purchases/online/{idPurchase}")
        		.resolveTemplate("idPurchase", idPurchase);
		
        String purchaseUrl = builder.build().toString();

        var client = getClient();
        try {
            Response response = client.target(purchaseUrl)
                    .request(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Bearer " + bearerToken)
                    .header("Accept-Language", "es-ES")
                    .get();

            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                return Optional.of(response.readEntity(Purchase.class));
            }

            Log4jTM.getLogger().error("Error getting purchase from {}: {} ({})", purchaseUrl, response.getStatus(), response.getStatusInfo());
            return Optional.empty();
        } finally {
            client.close();
        }
	}

}
