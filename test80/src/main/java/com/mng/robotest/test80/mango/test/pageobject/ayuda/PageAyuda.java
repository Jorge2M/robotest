package com.mng.robotest.test80.mango.test.pageobject.ayuda;

import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.Reader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PageAyuda extends WebdrvWrapp {

    public final static String xPathCloseBuscar = "//div[@class[contains(.,'close-modal')]]";

    private static JSONParser parser = new JSONParser();
    private static JSONObject fileHAR = null;

    public static JSONObject getFileJSON () throws Exception{
        Reader reader = new InputStreamReader(PageAyuda.class.getResourceAsStream("/helpFooter.json"), "utf-8");
        Object JSONFile = parser.parse(reader);
        fileHAR = (JSONObject)JSONFile;
        reader.close();

        return fileHAR;
    }

    public static JSONArray getSectionFromJSON(String section) throws Exception{
        return (JSONArray)getFileJSON().get(section);
    }

    public static ArrayList<String> getKeysFromJSON (JSONObject jsonObject) {
        ArrayList<String> keysFromJSON = new ArrayList<>();
        for (Object key : jsonObject.keySet()) {
            keysFromJSON.add((String) key);
        }
        return keysFromJSON;
    }

    public static String getXPath(String apartado) {
        return ("//*[text()='" + apartado + "']");
    }
    
    public enum StateApartado {	collapsed, expanded};
    
    private static String getXPathApartado(String apartado, StateApartado stateApartado) {
    	return ("//article[@class[contains(.,'" + stateApartado + "')]]" + getXPath(apartado));
    }
    
    public static boolean isApartadoInStateUntil(String apartado, StateApartado stateApartado, int maxSecondsWait, WebDriver driver) {
    	String xpathApartado = getXPathApartado(apartado, stateApartado);
    	return (WebdrvWrapp.isElementPresentUntil(driver, By.xpath(xpathApartado), maxSecondsWait));
    }
}