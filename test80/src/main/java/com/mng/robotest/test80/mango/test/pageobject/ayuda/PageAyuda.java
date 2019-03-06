package com.mng.robotest.test80.mango.test.pageobject.ayuda;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.Reader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PageAyuda extends WebdrvWrapp {

    public static String xPathCloseBuscar = "//div[@class[contains(.,'close-modal')]]";

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
}