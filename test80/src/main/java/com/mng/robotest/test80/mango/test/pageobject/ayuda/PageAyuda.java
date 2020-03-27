package com.mng.robotest.test80.mango.test.pageobject.ayuda;

import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.service.webdriver.pageobject.PageObjTM;

import java.io.Reader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PageAyuda extends PageObjTM {
	
	public final static String xPathCloseBuscar = "//div[@class[contains(.,'close-modal')]]";

    private static JSONParser parser = new JSONParser();
    private static JSONObject fileHAR = null;

	public PageAyuda(WebDriver driver) {
		super(driver);
	}
    
    private JSONObject getFileJSON () throws Exception {
        Reader reader = new InputStreamReader(PageAyuda.class.getResourceAsStream("/helpFooter.json"), "utf-8");
        Object JSONFile = parser.parse(reader);
        fileHAR = (JSONObject)JSONFile;
        reader.close();

        return fileHAR;
    }

    public JSONArray getSectionFromJSON(String section) throws Exception{
        return (JSONArray)getFileJSON().get(section);
    }

    public List<String> getKeysFromJSON () throws Exception {
    	JSONObject jsonObject = getFileJSON();
        ArrayList<String> keysFromJSON = new ArrayList<>();
        for (Object key : jsonObject.keySet()) {
            keysFromJSON.add((String) key);
        }
        return keysFromJSON;
    }

    public String getXPath(String apartado) {
        return ("//*[text()='" + apartado + "']");
    }
    
    public enum StateApartado {	collapsed, expanded };
    
    private String getXPathApartado(String apartado, StateApartado stateApartado) {
    	return ("//article[@class[contains(.,'" + stateApartado + "')]]" + getXPath(apartado));
    }

	public boolean isApartadoInStateUntil(String apartado, StateApartado stateApartado, int maxSeconds) {
		String xpathApartado = getXPathApartado(apartado, stateApartado);
		return (state(Present, By.xpath(xpathApartado), driver)
				.wait(maxSeconds)
				.check());
	}
}