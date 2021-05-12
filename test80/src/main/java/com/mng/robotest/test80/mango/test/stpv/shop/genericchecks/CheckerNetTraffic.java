package com.mng.robotest.test80.mango.test.stpv.shop.genericchecks;

import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.testreports.stepstore.GestorDatosHarJSON;

public class CheckerNetTraffic implements Checker {

	public ChecksTM check(WebDriver driver) {
		ChecksTM validations = ChecksTM.getNew();
		GestorDatosHarJSON gestorHAR = UtilsChecker.getGestorHar(driver);
        if (gestorHAR!=null) {
        	validaNetTraffic(gestorHAR);                                                  
        }
        return validations;
	}
	
    @SuppressWarnings({ "rawtypes" })
    @Validation
    private ChecksTM validaNetTraffic(GestorDatosHarJSON gestorHAR) {
    	ChecksTM validations = ChecksTM.getNew();
    	boolean peticionesOk = true;
    	String infoWarnings = "";
        JSONArray listEntriesTotal = getListEntries(gestorHAR);
        Iterator entriesJSON = listEntriesTotal.iterator();
        while (entriesJSON.hasNext() && peticionesOk) {
            JSONObject entrieJSON = (JSONObject)entriesJSON.next();
            JSONObject responseJSON = (JSONObject)entrieJSON.get("response");
            JSONObject requestJSON = (JSONObject)entrieJSON.get("request");
            if (responseJSON==null) {
            	infoWarnings+="<br><b style=\"color:" + State.Warn.getColorCss() + "\">Warning!</b>: hay peticiones sin respuesta, por ejemplo la <a href=\"" + (String)requestJSON.get("url") + "\">" + (String)requestJSON.get("url") + "</a>";
                peticionesOk = false;
            } else {
                long statusResponse = (long)responseJSON.get("status");
                if (statusResponse >= 400) {
                	infoWarnings+="<br><b style=\"color:" + State.Warn.getColorCss() + "\">Warning!</b>: hay peticiones con status KO, por ejemplo la <a href=\"" + (String)requestJSON.get("url") + "\">" + (String)requestJSON.get("url") + "</a> ( " + statusResponse + ")";
                    peticionesOk = false;
                }
            }
        }
	 	validations.add(
			"En el tráfico de red no existe ninguna sin respuesta o con status KO" + infoWarnings,
			peticionesOk, State.Warn);
    	
	 	return validations;
    }  
    
	private JSONArray getListEntries(GestorDatosHarJSON gestorHAR) {
		try {
			return gestorHAR.getListEntriesFilterURL("", "");
		}
		catch (Exception e) {
        	Log4jTM.getLogger().warn(
    			". Problem listing entries for NetTraffic", e);
    		return null;
		}
	}

}
