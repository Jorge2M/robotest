package com.mng.robotest.test80.mango.test.pageobject.ayuda;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class PageAyuda extends WebdrvWrapp {

    static private JSONParser parser = new JSONParser();
    static private JSONObject fileHAR = null;

    public static JSONArray getSectionFromJSON(String section) {
        JSONArray sectionJSON = new JSONArray();

        try {
            Object JSONFile = parser.parse(new FileReader("../../../../resources/helpFooter.json"));
            fileHAR = (JSONObject)JSONFile;

            sectionJSON = (JSONArray)fileHAR.get(section);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }

        return sectionJSON;
    }

    public static String getXPath(String apartado) {
        return ("//*[text()[contains(.,'" + apartado + "']]");
    }
}
