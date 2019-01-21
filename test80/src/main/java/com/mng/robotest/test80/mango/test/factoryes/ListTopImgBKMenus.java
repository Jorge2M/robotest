package com.mng.robotest.test80.mango.test.factoryes;

import java.util.*;

import org.testng.annotations.*;

import com.mng.robotest.test80.mango.test.appshop.PaisIdioma;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum;
import com.mng.robotest.test80.mango.test.data.ChannelEnum;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.factoryes.Utilidades;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.*;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;

@SuppressWarnings("javadoc")
public class ListTopImgBKMenus {
	
    @Factory
    @Parameters({"urlBase", "AppEcom", "Channel", "countrys", "recorreMenus", "recorreBanners", "lineas"})
    public Object[] createInstances(String urlAcceso, String appEStr, String channelStr, String countrysStr, String RecorreMenus, String RecorreBanners, String lineas) 
    throws Exception {
        ArrayList<PaisIdioma> listTests = new ArrayList<>();
        
        //Parseo de los parámetros de entrada
        AppEcom appE = AppEcomEnum.getAppEcom(appEStr);
        Channel channel = ChannelEnum.getChannel(channelStr);
        
        boolean recorreMenus=false;
        if (RecorreMenus.compareTo("true")==0) 
            recorreMenus = true;
		
        boolean recorreBanners=false;
        if (RecorreBanners.compareTo("true")==0) 
            recorreBanners = true;
           
        //Obtenemos la lista de países como lista de enteros
        List<Integer> listaPaisesInt = UtilsMangoTest.getListaPaisesInt(countrysStr);
            
        //Realizamos el filtrado de los países del XML
        Response response = Utilidades.filtradoListaPaises(false/*todosPaises*/, listaPaisesInt); 
        Iterator<Continente> itContinentes = response.getResponse().iterator();
            
        //Iteramos a nivel de Continentes -> Países -> Idiomas
        int prioridad=0;
        while (itContinentes.hasNext()) {
            Continente continente = itContinentes.next();
            Iterator<Pais> itPaises = continente.getPaises().iterator();
            while (itPaises.hasNext()) {
                Pais pais = itPaises.next();

                //Algunos de los nombres del XML no coinciden con los de la WEB. De momento los regularizamos
                //Utilidades.normalizaPais(pais, isMobile, isOutlet);
                Iterator<IdiomaPais> itIdiomas = pais.getListIdiomas().iterator();
                IdiomaPais idioma = itIdiomas.next();
                if (appE!=AppEcom.outlet || (appE==AppEcom.outlet && pais.getOutlet_online().compareTo("true")==0)) {
                    Iterator<Linea> itLineas = Utilidades.getLinesToTest(pais, appE, lineas).iterator();
                    while (itLineas.hasNext()) {
                        Linea linea = itLineas.next();
                        if (Utilidades.lineaToTest(linea, appE)) {
                            List<Linea> lineasAprobar = new ArrayList<>();
                            lineasAprobar.add(linea);
               	            DataCtxShop dCtxSh = new DataCtxShop(appE, channel, pais, idioma, urlAcceso); 
                            listTests.add(new PaisIdioma(dCtxSh, lineasAprobar, recorreMenus, recorreBanners, prioridad));
                            prioridad+=1;
                                		            		
                            System.out.println("Creado Test \"PaisIdioma\" con datos: Continente=" + continente.getNombre_continente() +
                                               ",Pais=" + pais.getNombre_pais() +
                                               ",Idioma=" + idioma.getCodigo().getLiteral() +
                                               ",Linea=" + linea.getType() + 
                                               ",Num Idiomas=" + pais.getListIdiomas().size());
                        }
                    }
                }
            }
        }
            		
        return listTests.toArray(new Object[listTests.size()]);
    }
}