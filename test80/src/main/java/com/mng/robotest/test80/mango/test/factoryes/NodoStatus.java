package com.mng.robotest.test80.mango.test.factoryes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.AppEcomEnum;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.getdata.json.gestorDatosStatusJSON;
import com.mng.robotest.test80.mango.test.pageobject.utils.NombreYRef;
import com.mng.robotest.test80.mango.test.pageobject.utils.NombreYRefList;

@SuppressWarnings("javadoc")
public class NodoStatus {

    String ip;
    Set<Cookie> cookies;
    AppEcom appE;
    String sourceDataURL;
    gestorDatosStatusJSON statusJSON = new gestorDatosStatusJSON();
    NombreYRefList articlesNuevo = null;
    boolean tested = false;

    public NodoStatus() {}
	
    public String getIp() {
        return this.ip;
    }
	
    public void setIp(String ip) {
        this.ip = ip;
    }	
	
    public Set<Cookie> getCookies() {
        return this.cookies;
    }
    
    public AppEcom getAppEcom() {
        return this.appE;
    }
	
    public void setCookies(Set<Cookie> cookies) {
        this.cookies = cookies;
    }
	
    public String getSourceDataURL() {
        return this.sourceDataURL;
    }
    
    public void setSourceDataURL(String sourceDataURL) {
        this.sourceDataURL = sourceDataURL;
    }
    
    public gestorDatosStatusJSON getStatusJSON() {
        return this.statusJSON;
    }
    
    public NombreYRefList getArticlesNuevo() {
        return this.articlesNuevo;
    }
    
    public void setAppEcom(String app) {
        this.appE = AppEcomEnum.getAppEcom(app);
    }
    
    public void setAppEcom(AppEcom appE) {
        this.appE = appE;
    }
    
    public void setArticlesNuevo(NombreYRefList articlesNuevo) {
        this.articlesNuevo = articlesNuevo;
    }
    
    public boolean getTested() {
        return this.tested;
    }
    
    public void setTested(boolean tested) {
        this.tested = tested;
    }
    
    /**
     * Cargamos al URL de status y almacenamos los datos JSON obtenidos
     */
    public void setDataStateNodeFromBrowser(WebDriver driver) throws Exception {
        this.statusJSON.setDataStateNodeFromBrowser(driver, this);
    }
    
    /**
     * Busca una cookie por su nombre
     */
    public Cookie getCookieByName(String nameCookie) {
        boolean encontrado = false; 
        Cookie cookie = null;
        Iterator<Cookie> itCookies = this.cookies.iterator();
        while (itCookies.hasNext() && !encontrado){
            Cookie cookieTmp = itCookies.next();
            if (cookieTmp.getName().compareTo(nameCookie)==0) {
                encontrado = true;
                cookie = cookieTmp;
            }
        }
                
        return cookie;
    }
    
    /**
     * Función que revisa los stocks del nodo con los de otro nodo. Valida que si la difencia en alguno de los stocks es superior a un porcentaje determinado
     */
    public boolean comparaStocksWarehouses(NodoStatus nodoAnt, final double porcentaje) {

        String warehouseAct = this.getStatusJSON().getWarehouses().toString().replace("{", "").replace("}", "");
        String warehouseAnt = nodoAnt.getStatusJSON().getWarehouses().toString().replace("{", "").replace("}", "");
        
        List<String> listWareAct = new ArrayList<>(Arrays.asList(warehouseAct.split(",")));
        List<String> listWareAnt = new ArrayList<>(Arrays.asList(warehouseAnt.split(",")));

        for (int i = 0; i < listWareAct.size(); i++) {
            int valorStock = Integer.parseInt(listWareAct.get(i).substring(listWareAct.get(i).indexOf(":") + 1).trim());
            int valorStock_C = Integer.parseInt(listWareAnt.get(i).substring(listWareAnt.get(i).indexOf(":") + 1).trim());
            if (valorStock_C <= 0) {
                return false;
            }
            int porcentajeValor = Math.abs((valorStock - valorStock_C) / valorStock);
            if (porcentajeValor >= porcentaje) {
                return false;
            }
        }

        return true;
    }
    
    public NombreYRef getArticleNuevoThatNotFitWith(NodoStatus nodoAnt) { 
        return (this.getArticlesNuevo().getFirstArticleThatNotFitWith(nodoAnt.getArticlesNuevo()));
    }
}
