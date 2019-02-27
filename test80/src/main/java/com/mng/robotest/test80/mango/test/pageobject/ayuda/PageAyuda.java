package com.mng.robotest.test80.mango.test.pageobject.ayuda;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.Reader;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class PageAyuda extends WebdrvWrapp {

    public static String xPathCloseBuscar = "//div[@class[contains(.,'close-modal')]]";

    private static JSONParser parser = new JSONParser();
    private static JSONObject fileHAR = null;
    private static ClassLoader classLoader = PageAyuda.class.getClassLoader();

    public static JSONObject getFileJSON () throws Exception {
        File file = new File(classLoader.getResource("helpFooter.json").getFile());
        Reader reader = new FileReader(file);
        try {
            Object JSONFile = parser.parse(reader);
            fileHAR = (JSONObject)JSONFile;
        } catch (ParseException | IOException | NullPointerException e) {
            e.printStackTrace();
        }
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