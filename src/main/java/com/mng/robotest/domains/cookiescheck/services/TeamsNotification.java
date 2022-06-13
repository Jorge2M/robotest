package com.mng.robotest.domains.cookiescheck.services;

import static org.apache.http.impl.client.HttpClients.createDefault;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import com.mng.robotest.domains.cookiescheck.exceptions.UnsendTeamsNotification;

public class TeamsNotification implements NotificationSender {

	private final static String TEAMS_CHANNEL_URL = 
			"https://365mango.webhook.office.com/webhookb2/24612853-3c4f-40ae-9cd1-69c2d82bc17e@599df23c-c4a7-4575-ba97-d3d7d28b2b1d/IncomingWebhook/66b8c054fb0d4b4892581d235ff1b4c7/32e10c85-66d0-411e-a56c-559e248f360c";
	
	@Override
	public void send(String jsonMessage) {
        try (CloseableHttpClient httpClient = createDefault()) {
            HttpPost post = new HttpPost(TEAMS_CHANNEL_URL);
            post.addHeader("Content-Type", "application/json");
            StringEntity entity = new StringEntity(jsonMessage);
            post.setEntity(entity);
            HttpResponse response = httpClient.execute(post);
            if (response.getStatusLine().getStatusCode() != 200) {
            	String errorMessage = String.format(
            			"HttpError %s sending Teams Notification", 
            			response.getStatusLine().getStatusCode());
                throw new UnsendTeamsNotification(errorMessage);
            }
        } catch (Exception e) {
            throw new UnsendTeamsNotification(e);
        }
	}
	
}
