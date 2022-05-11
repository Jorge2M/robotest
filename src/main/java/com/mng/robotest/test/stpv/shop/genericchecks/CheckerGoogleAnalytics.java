package com.mng.robotest.test.stpv.shop.genericchecks;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.testreports.stepstore.GestorDatosHarJSON;
import com.mng.robotest.conftestmaker.AppEcom;


public class CheckerGoogleAnalytics implements Checker {

	public ChecksTM check(WebDriver driver) {
		ChecksTM validations = ChecksTM.getNew();
		
		GestorDatosHarJSON gestorHAR = UtilsChecker.getGestorHar(driver);
		if (gestorHAR!=null) {
			JSONArray listEntriesFilteredPage = getListEntries(gestorHAR);
			int numLineas = listEntriesFilteredPage.size(); 
			String urlGoogleAnalytics = "://www.google-analytics.com/collect";
		 	validations.add(
				"Está lanzándose 1 petición que contiene <b>" + urlGoogleAnalytics + "</b> y el parámetro <b>\"t=pageview\"</b>",
				numLineas==1, State.Warn);
		 	
		 	if (numLineas!=0) {
				JSONObject entrieJSON = (JSONObject)listEntriesFilteredPage.get(0);
				JSONObject requestJSON = (JSONObject)entrieJSON.get("request");
				JSONObject paramTid = gestorHAR.getParameterFromRequestQuery(requestJSON, "tid");
				JSONObject responseJSON = (JSONObject)entrieJSON.get("response");
			 	validations.add(
					"La petición es de tipo <b>\"GET\"</b>",
					requestJSON.get("method").toString().compareTo("GET")!=0, State.Warn);
			 	validations.add(
					"El response status de la petición es de tipo <b>2xx</b>",
					responseJSON.get("status").toString().matches("2\\d\\d"), State.Warn);
			 	
				String valueTid1 = "UA-855910-26";
				String valueTid2 = "UA-855910-3";
				String valueTid3 = "UA-855910-34";
				if (UtilsChecker.getApp()==AppEcom.outlet) {
					valueTid1 = "UA-855910-5";
					valueTid2 = "UA-855910-5";
					valueTid2 = "UA-855910-5";
				}
			 	validations.add(
					"En la petición figura el parámetro <b>\"tid=" + valueTid1 + "\" o \"tid=" + valueTid2 + "\" o o \"tid=" + valueTid3 + "\"</b>",
					paramTid!=null && 
					(((String)paramTid.get("value")).compareTo(valueTid1)==0 || 
					 ((String)paramTid.get("value")).compareTo(valueTid2)==0 ||
					 ((String)paramTid.get("value")).compareTo(valueTid3)==0), State.Warn);
		 	}	 
		}
		
	 	return validations; 
	}
	
	private JSONArray getListEntries(GestorDatosHarJSON gestorHAR) {
		try {
			return gestorHAR.getListEntriesFilterURL("://www.google-analytics.com/collect","t=pageview");
		}
		catch (Exception e) {
			Log4jTM.getLogger().warn(
				". Problem listing entries of google-analytics", e);
			return null;
		}
	}
}
