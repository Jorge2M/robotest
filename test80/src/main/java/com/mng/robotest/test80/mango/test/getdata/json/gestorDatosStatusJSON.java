package com.mng.robotest.test80.mango.test.getdata.json;

import java.net.URI;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.factoryes.NodoStatus;

/**
 * Clase para obtener y gestionar los datos JSON devueltos por la URL http://shop.mango.com/services/controles/status 
 * @author jorge.munoz
 *
 */

public class gestorDatosStatusJSON {

    JSONObject dataStatusNode = null;
    public final String pathStatus = "/services/controles/status";
    
    public gestorDatosStatusJSON() { }
    
    /**
     * Se utiliza un browser para establecer los datos del servicio "/services/controles/status" asociados al nodo 
     */
    public void setDataStateNodeFromBrowser(WebDriver driver, NodoStatus nodo) throws Exception {
        this.dataStatusNode = getDataStateNodeFromBrowser(driver, nodo);
    }
    
    public JSONObject getDataStatusNode() {
        return this.dataStatusNode;
    }
    
    /**
     * @return si el estatus es "UP"
     */
    public boolean isStatusOk() {
        return ("UP".compareTo((String)this.dataStatusNode.get("status"))==0);
    }
    
    public int getSessionCount() {
        return (Integer.valueOf(this.dataStatusNode.get("sessions-count").toString()).intValue());
    }
    
    public JSONObject getMdm() {
        return (JSONObject)this.dataStatusNode.get("mdm");
    }
    
    public JSONObject getStock() {
        return (JSONObject)getMdm().get("stock");
    }
    
    public int getVersionStock() {
        return (Integer.valueOf(getStock().get("version").toString()).intValue());
    }
    
    public JSONObject getWarehouses() {
        return (JSONObject)getStock().get("warehouses");
    }
    
    public JSONObject getConfig() {
        return (JSONObject)this.dataStatusNode.get("config");
    }
    
    public JSONObject getShopconfig() {
        return (JSONObject)getConfig().get("shop-config");
    }
    
    public String getVShopconfig() {
        return (getShopconfig().get("version").toString());
    }
    
    /**
     * Se utiliza un browser para obtener los datos del servicio "/services/controles/status" asociados al nodo
     */
    public JSONObject getDataStateNodeFromBrowser(WebDriver webdriver, NodoStatus nodo) throws Exception {
        //Accedemos a la shop (para poder añadir cookies con el dominio de la shop)
        webdriver.get(nodo.getSourceDataURL());
                    
        //Sustituímos las cookies de sesión por las que teníamos almacenadas en el objecto Nodo
        webdriver.manage().deleteAllCookies();
        Cookie cookieJS = nodo.getCookieByName("MNGSESSIONID");
        if (cookieJS==null) {
            cookieJS = nodo.getCookieByName("JSESSIONID");
        }
        if (cookieJS==null) {
            cookieJS = nodo.getCookieByName("JSESSIONID2");
        }
        webdriver.manage().addCookie(cookieJS);
        Cookie cookieAW = nodo.getCookieByName("AWSELB");
        if (cookieAW!=null) {
            webdriver.manage().addCookie(cookieAW);
        }
        
        //Obtenemos la URL del servicio de status
        URI uriBaseTest = new URI(nodo.getSourceDataURL());
        String urlStatus = uriBaseTest.getScheme() + "://" +  uriBaseTest.getHost() + this.pathStatus;
        
        //Accedemos a la URL y obtenemos el contenido fuente con los datos JSON
        webdriver.get(urlStatus);
        String dataJSON = webdriver.findElement(By.tagName("body")).getText();
        
        //Generamos los datos JSON obtenidos mediante la llamada a la URL /services/controles/status 
        JSONObject jsonObject = readJsonFromString(dataJSON);
        
        return jsonObject;
    }
    
    private JSONObject readJsonFromString(String dataJSON) throws Exception {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(dataJSON);
        return ((JSONObject) obj);
    }
}