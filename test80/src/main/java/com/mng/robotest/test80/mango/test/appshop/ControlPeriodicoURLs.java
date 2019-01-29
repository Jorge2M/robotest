package com.mng.robotest.test80.mango.test.appshop;

import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.utils;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.arq.utils.controlTest.mango.GestorWebDriver;
import com.mng.robotest.test80.arq.utils.otras.Constantes;
import com.mng.robotest.test80.mango.test.data.ChannelEnum;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.generic.PasosGenAnalitica;
import com.mng.robotest.test80.mango.test.getdata.json.gestorDatosHarJSON;
import com.mng.robotest.test80.mango.test.pageobject.shop.modales.ModalCambioPais;
import com.mng.robotest.test80.mango.test.stpv.shop.PageHomeMarcasStpV;

/**
 * Test que comprueba los menús del footer
 * 
 * @author 00559942
 */
@SuppressWarnings({ "javadoc" })
public class ControlPeriodicoURLs extends GestorWebDriver /*Funcionalidades genéricas propias de MANGO*/ {
    static Logger pLogger = LogManager.getLogger(fmwkTest.log4jLogger);
    
    private String index_fact;
    private ArrayList<ControlPeriodicoURLsList> listaPaisesControlUrl;
    
    @SuppressWarnings({ "unchecked" })
    public ControlPeriodicoURLs(ArrayList<ControlPeriodicoURLsList> thisListaPaisesControlUrl, String index_fact_count) {     
        this.index_fact = index_fact_count;
        this.listaPaisesControlUrl = (ArrayList<ControlPeriodicoURLsList>) thisListaPaisesControlUrl.clone();
    }
    
    
    @BeforeMethod
    @Parameters({"brwsr-path", "urlBase", "Channel"})
    public void login(String bpath, String urlAcceso, String channelI, ITestContext context, Method method) throws Exception {
        Channel channel = ChannelEnum.getChannel(channelI);
        getAndStoreDataFmwk(bpath, urlAcceso, this.index_fact, channel, context, method);
    }
        
    @SuppressWarnings({ "unused" })
    @AfterMethod (alwaysRun = true)
    public void logout(ITestContext context, Method method) throws Exception {
        WebDriver driver = getWebDriver();
        try {
            super.quitWebDriver(driver, context);
        }
        catch (UnreachableBrowserException e) {
            //Hay un bug en Phantom que provoca que salte esta excepción cuando se intenta cerrar PhantomJS... aunque realmente el cierre ha sido correcto
        }
    }   
    
    @Test
    public void CP001_URL_ControlPeriodico(ITestContext context, Method method) throws Exception {
    	DataFmwkTest dFTest = getdFTest();
        try {
            accesoURLPais(this.listaPaisesControlUrl, true/*netAnalysis*/, dFTest);
        }
        catch (UnreachableBrowserException e) {
            //Si hay un problema técnico con el acceso al navegador lo ignoramos para no crear una falsa alarma
            pLogger.error("Problem accessing browser", e);
        }
    }
    
    @Override
    public String toString() {
        String resultado = "";
        for (int i=0; i<this.listaPaisesControlUrl.size(); i++)
            resultado += "Pais: " + this.listaPaisesControlUrl.get(i).getPais().getNombre_pais() + " / Idioma: " + this.listaPaisesControlUrl.get(i).getIdioma().getCodigo().getLiteral() + " -- ";   
        
        return resultado;
    }    
    
    /**
     * Test que valida el control periódico de las URLs de todos los paises.
     * @param listaPaisesControlUrl Lista de 10 países
     */
    public static void accesoURLPais (ArrayList<ControlPeriodicoURLsList> listaPaisesControlUrl, boolean netAnalysis, DataFmwkTest dFTest) 
    throws Exception {
        String urlBaseTest = (String)dFTest.ctx.getAttribute("appPath");
        JSONObject jsonMessageResult = null;
        
        // FASE 2: tratamiento de las URLs de tipo 'http://shop.mango.com/XX' en grupos de 10
        for (ControlPeriodicoURLsList controlPeriodicoURLsList : listaPaisesControlUrl) {
            String urlAccesoIdiomaOr = controlPeriodicoURLsList.getIdioma().getUrlIdioma(urlBaseTest);
            int contadorValidationKO = 0;
            boolean more7seconds = false;
            while(contadorValidationKO < 2) {
                Pais pais = controlPeriodicoURLsList.getPais();
                
                //Step. Acceder a la shop vía la URL del país origen
                DatosStep datosStep = new DatosStep(
                    "Acceder a la shop vía la URL <b>" + urlAccesoIdiomaOr + "</b> (" + pais.getNombre_pais() + " / " + controlPeriodicoURLsList.getIdioma().getCodigo().getLiteral() + ")", 
                    "Aparece una página inicial correcta");
                datosStep.setGrabNettrafic(dFTest.ctx);
                try {
                    // Introducir la URL asociada al país/idioma
                    dFTest.driver.get(urlAccesoIdiomaOr);
                    ModalCambioPais.closeModalIfVisible(dFTest.driver);
                    
                    //Si el tiempo supera los 7 segundos grabamos el NetTraffic
                    if ((System.currentTimeMillis() - datosStep.getHoraInicio().getTime()) > 7000) {
                        //Realizamos un Export del tráfico de red (sólo para firefox y NetAnalysis).
                        //Informamos el tiempo fin para que no se contabilice la exportación al HAR.
                        datosStep.setHoraFin(new Date(System.currentTimeMillis()));
                        more7seconds = true;
                    }
                    
                    datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
                } 
                finally {
                    datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest));
                    
                    // Obtenemos el tiempo TOTAL de carga de la página y enviamos la correspondiente alerta HTTP_TIEMPOS_USR a Tableau
                    float tiempoTotal = datosStep.getHoraFin().getTime() - datosStep.getHoraInicio().getTime(); /*tiempo total de respuesta*/
                    
                    String absolutePathHARP = fmwkTest.getLinkNetTraffic(datosStep.getStepNumber(), dFTest.meth, dFTest.ctx);
                    String applicationDNS = dFTest.ctx.getCurrentXmlTest().getParameter(Constantes.paramApplicationDNS);
                    String netTraffic = Constantes.URL_SOFTWAREISHARD + utils.obtainDNSFromFile(absolutePathHARP, applicationDNS).replace('\\', '/').replace(" ", "%20");
                    jsonMessageResult = controlPeriodicoURLsList.mensajeJson(ControlPeriodicoURLsList.codiSQL.HTTP_TIEMPOS_USR.toString(), urlAccesoIdiomaOr, tiempoTotal, netTraffic);
                    controlPeriodicoURLsList.sendPostTableau(jsonMessageResult);
                
                    // Obtenemos el tiempo de carga de la 1a petición tipo http://shop.mango.com/ES Alerta y enviamos la correspondiente alerta HTTP_TIEMPOS_WEB
                    if (pais.isPaisTop()  && 
                    	more7seconds) {
                        //Instanciamos el gestor de los datos HAR que apuntará al fichero .HARP con el NetTraffic y obtenemos el tiempo de carga de la 1a petición de tipo text/html
                        gestorDatosHarJSON gestorHAR = null;
                        try {
                            gestorHAR = new gestorDatosHarJSON(datosStep.getStepNumber(), dFTest.ctx, dFTest.meth);
                            
                            //Obtenemos el tiempo correspondiente a la petición de tipo text/html (la del tipo http://shop.mango.com/ES)
                            float tiempo1aPet = PasosGenAnalitica.getTime1rstPet(gestorHAR, "text/html");       
                            
                            //Enviamos alerta HTTP_TIEMPOS_WEB a Tableau
                            jsonMessageResult = controlPeriodicoURLsList.mensajeJson(ControlPeriodicoURLsList.codiSQL.HTTP_TIEMPOS_WEB.toString(), urlAccesoIdiomaOr, tiempo1aPet, netTraffic);
                            controlPeriodicoURLsList.sendPostTableau(jsonMessageResult);
                        }
                        catch (FileNotFoundException e) {
                            //Capturamos la excepción para que no se produzca error (las posteriores validaciones generarán un warning para este caso)
                            pLogger.warn("Problem getting HAR file for method {} and step {}", dFTest.meth, Integer.valueOf(datosStep.getStepNumber()), e);
                        }
                    }
                }
                
                // VALIDACIONES - FASE 1: tratamiento de las URLs de tipo 'http://shop.mango.com/XX'
                try {
                    PageHomeMarcasStpV.validateIsPageWithCorrectLineas(pais, Channel.desktop, AppEcom.shop, datosStep, dFTest);
                    
                    //VALIDACIONES - PARA ANALYTICS (sólo para firefox y NetAnalysis)
                    //PasosGenericos.validaHTTPAnalytics(dFTest.driver, false/*isOutlet*/, netAnalysis, datosStep, context, method);
                }
                finally {
                    //Enviamos información a Tableau
                    if(datosStep.getResultTodasValidaciones() != State.Ok) {
                        contadorValidationKO++;
                        switch (contadorValidationKO) {
                        case 1:
                            //Esperamos 30 segundos para dar tiempo a que se resuelva el actual problema con los menús
                            //(La 1a ejecución contra un país/nodo tarda mucho y los menús no se pintan)
                            Thread.sleep(30000);
                            break;
                        case 2:
                            jsonMessageResult = controlPeriodicoURLsList.mensajeJson(ControlPeriodicoURLsList.codiSQL.HTTP_CONTENT_WEB.toString(), urlAccesoIdiomaOr, -1, fmwkTest.getURLImgStep(datosStep, true, dFTest.meth, dFTest.ctx));
                            controlPeriodicoURLsList.sendPostTableau(jsonMessageResult);
                            break;
                        default:
                            break;
                        }
                    } else {
                        jsonMessageResult = controlPeriodicoURLsList.mensajeJson(ControlPeriodicoURLsList.codiSQL.HTTP_CONTENT_WEB.toString(), urlAccesoIdiomaOr, 0, "");
                        controlPeriodicoURLsList.sendPostTableau(jsonMessageResult);
                        contadorValidationKO = 2;
                    } 
                }
            }
        }
    }
}
