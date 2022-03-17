package com.mng.robotest.test.getdata.garment;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientProperties;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.test.getdata.JaxRsClient;
import com.mng.robotest.test.getdata.garment.data.GarmentFicha;


public class GetterGarment extends JaxRsClient {

	private final Client client = getClient();
	private final String target;
	private final String nameCloudTest = getNameCloudTest();
	private final String stockId;
	
	public GetterGarment(String stockId) {
		this(stockId, ((InputParamsMango)TestMaker.getInputParamsSuite()).getUrlBase());
	}
	
	public GetterGarment(String stockId, String initialURL) {
		this.stockId = stockId;
		this.target = getUrlBase(initialURL);
	}
	
	public GarmentFicha getGarment(String idGarment) throws Exception {
		WebTarget webTarget = getWebTargetGarment(idGarment);
		Builder builder = webTarget
				.request(MediaType.APPLICATION_JSON)
				.header("stock-id", stockId);
		
		if ("".compareTo(nameCloudTest)!=0) {
			builder = builder.cookie("cloudtest-name", nameCloudTest);
		}
		Response response = builder.get();
		
		if (response.getStatus()==Response.Status.OK.getStatusCode()) {
			return response.readEntity(GarmentFicha.class);
		}
		return null;
	}
	
	private WebTarget getWebTargetGarment(String idGarment) {
		WebTarget webTarget =  
			client
				.target(target)
				.path("services")
				.path("garments")
				.path(idGarment)
				.property(ClientProperties.FOLLOW_REDIRECTS, Boolean.TRUE);
				
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
	
	private static String getUrlBase(String initialURL) {
		try {
			URI uri = new URI(initialURL);
			String urlTmp = (uri.getScheme() + "://" + uri.getHost());
			if (urlTmp.charAt(urlTmp.length()-1)=='/') {
				return urlTmp;
			} else {
				return urlTmp + "/";
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	private static String getNameCloudTest() {
		String initialURL = ((InputParamsMango)TestMaker.getInputParamsSuite()).getUrlBase();
		Pattern pattern = Pattern.compile(".*://.*name=(.*)");
		Matcher match = pattern.matcher(initialURL);
		if (match.find()) {
			return match.group(1);
		}
		return "";
	}
	
}
