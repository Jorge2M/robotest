package com.mng.robotest.test80.mango.test.appshop;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.json.simple.JSONObject;

import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.IdiomaPais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;

/**
 * Clase con los métodos necesarios para el test de control perioóico de URLs y envío de alertas a Tableau
 * 
 * @author 00559942
 */
public class ControlPeriodicoURLsList {
    
    //VARIABLES
    private Pais pais;
    private IdiomaPais idioma;
    
    private String CODI_SQL = "codiSQL";
    private String DESCRIPCION = "descripcion";
    private String VALOR = "valor";
    private String INFO_ADICIONAL = "infoAdicional";
    private String URL_TABLEAU = "http://tableau.ext.pro.mango.com:8180/mangoAlertas/captures/insert";
//    private String URL_TABLEAU = "http://10.18.2.80:8888/mangoAlertas/captures/insert";
    
    /**
     * Constructor
     * 
     * @param pais
     * @param idioma
     */
    public ControlPeriodicoURLsList(Pais pais, IdiomaPais idioma) {
        this.pais = pais;
        this.idioma = idioma;        
    }

    /**
     * Getter pais
     * 
     * @return pais
     */
    public Pais getPais() {
        return this.pais;
    }
    
    /**
     * Setter pais
     * 
     * @param pais
     */
    public void setPais(Pais pais) {
        this.pais = pais;
    }
    
    /**
     * Getter idioma
     * 
     * @return idioma
     */
    public IdiomaPais getIdioma() {
        return this.idioma;
    }
    
    /**
     * Setter idioma
     *
     * @param idioma
     */
    public void setIdioma(IdiomaPais idioma) {
        this.idioma = idioma;
    }
    
    
    /**
     * 
     */
    public enum codiSQL {
        /**
         * 
         */
        HTTP_TIEMPOS_WEB,
        /**
         * 
         */
        HTTP_CONTENT_WEB,
        /**
         * 
         */
        HTTP_TIEMPOS_USR        
    }
    
    /**
     * Devuelve mensaje en formato JSON
     * 
     * @param httpCodiSQL enum HTTP_CONTENT_WEB, HTTP_CONTENT_WEB
     * @param descripcion String url país
     * @param valor Float tiempo [HTTP_CONTENT_WEB]; 0,-1 [HTTP_CONTENT_WEB]
     * @param infoAdicional String direccion de imagen si procede
     * @return jsonMessage
     */
    @SuppressWarnings({ "unchecked", "boxing" })
    public JSONObject mensajeJson(final String httpCodiSQL, final String descripcion, final float valor, final String infoAdicional){
        JSONObject jsonMessage = new JSONObject();
        jsonMessage.put(this.CODI_SQL, httpCodiSQL);
        jsonMessage.put(this.DESCRIPCION, descripcion);
        jsonMessage.put(this.VALOR, valor);
        jsonMessage.put(this.INFO_ADICIONAL, infoAdicional);
        return jsonMessage;
    }
    
    /**
     * Método que envía un post con el mensaje json a la dirección de Tableau
     * 
     * @param mensajeJSON mensaje JSON a enviar en POST
     */
    @SuppressWarnings({ "unused", "resource" })
    public void sendPostTableau(final JSONObject mensajeJSON) {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(this.URL_TABLEAU);
        
        try {             
            StringEntity params = new StringEntity(mensajeJSON.toJSONString());
            httpPost.setEntity(params);
            httpPost.setHeader("Content-Type", "application/json");
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity entity = httpResponse.getEntity();
        } catch (UnsupportedEncodingException e) {
            LogManager.getLogger(fmwkTest.log4jLogger).warn("Problem sending message to Tableau. " + e.getStackTrace());
        } catch (ClientProtocolException e) {
            LogManager.getLogger(fmwkTest.log4jLogger).warn("Problem sending message to Tableau. " + e.getStackTrace());
        } catch (IOException e) {
            LogManager.getLogger(fmwkTest.log4jLogger).warn("Problem sending message to Tableau. " + e.getStackTrace());
        }       
    }
}
