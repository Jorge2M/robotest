package com.mng.robotest.test80.mango.test.factoryes;

import java.util.*;
import org.testng.annotations.*;

import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.mango.test.appshop.PaisIdioma;
import com.mng.robotest.test80.mango.test.appshop.PaisIdiomaCambioPais;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.Utilidades;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.*;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.getdata.usuarios.GestorUsersShop;
import com.mng.robotest.test80.mango.test.getdata.usuarios.UserShop;

public class ListAllCountrys {
	
    @Factory
    @Parameters({"urlBase", "AppEcom", "Channel", "countrys", "recorreMenus", "recorreBanners", "typeTest", "lineas"})
    public Object[] createInstances(String urlAcceso, String appEStr, String channelStr, String countrysStr, String RecorreMenus, String RecorreBanners, String typeTest, String lineas) 
    throws Exception {
        ArrayList<String> listaPaises = new ArrayList<>();
        ArrayList<PaisIdiomaCambioPais> listTestsCambioPais = new ArrayList<>();
        ArrayList<PaisIdioma> listTests = new ArrayList<>();
        try {
            //Parseo de los parámetros de entrada
            AppEcom appE = AppEcom.valueOf(appEStr);
            Channel channel = Channel.valueOf(channelStr);
            
            boolean recorreMenus=false;
            if (RecorreMenus.compareTo("true")==0) {
                recorreMenus = true;
            }
            boolean recorreBanners=false;
            if (RecorreBanners.compareTo("true")==0) {
                recorreBanners = true;
            }
	
            //Obtenemos la lista de países como lista de enteros
            List<Integer> listaPaisesInt = UtilsMangoTest.getListaPaisesInt(countrysStr);
			
            //Realizamos el filtrado de los países del XML
            Response response = Utilidades.filtradoListaPaises(false/*todosPaises*/, listaPaisesInt);	
            Iterator<Continente> itContinentes = response.getResponse().iterator();
			
            int prioridad=0; /*Nota: ha de ser secuencial para cada uno de los tests creados*/

            //Iteramos a nivel de Continentes -> Países -> Idiomas
            Pais paisAnt = null;
            IdiomaPais idiomaAnt = null;
            while (itContinentes.hasNext()) {
                Continente continente = itContinentes.next();
                Iterator<Pais> itPaises = continente.getPaises().iterator();
                while (itPaises.hasNext()) {
                    Pais pais = itPaises.next();

                    //Algunos de los nombres del XML no coinciden con los de la WEB. De momento los regularizamos
                    //Utilidades.normalizaPais(pais, isMobile, isOutlet);
                    Iterator<IdiomaPais> itIdiomas = pais.getListIdiomas().iterator();
                    while (itIdiomas.hasNext()) {
                        IdiomaPais idioma = itIdiomas.next();
                        if (paisToTest(pais, appE==AppEcom.outlet)) {
                            listaPaises.add(pais.getNombre_pais().trim());
                            List<Linea> lineasAprobar = Utilidades.getLinesToTest(pais, appE, lineas);
                            DataCtxShop dCtxSh = new DataCtxShop(appE, channel, pais, idioma, urlAcceso);
                            UserShop userShop = GestorUsersShop.checkoutBestUserForNewTestCase();
                            dCtxSh.userConnected = userShop.user;
                            dCtxSh.passwordUser = userShop.password;
	            					
                            //Discrimina si se trata del test que valida el registro o el que valida líneas+banners
                            switch (typeTest) {
                            case "1": //Test países - idiomas - líneas - banners
                                listTests.add(new PaisIdioma(dCtxSh, lineasAprobar, recorreMenus, recorreBanners, prioridad));
                                System.out.println(
                                    "Creado Test \"PaisIdioma\" con datos: " + 
                                    "Continente=" + continente.getNombre_continente() +
                                    ",Pais=" + pais.getNombre_pais() +
                                    ",Idioma=" + idioma.getCodigo().getLiteral() +
                                    ",Num Idiomas=" + pais.getListIdiomas().size());
                                break;
                            case "3": //Test acceso país + cambio de país
                                //En el 1er caso de prueba país/idioma origen y país/idioma destino serán los mismos
                                if (paisAnt==null) {
                                    paisAnt = pais;
                                }
                                if (idiomaAnt==null) {
                                    idiomaAnt = idioma;
                                }			    
                                
                                //Probamos el acceso con el país actual + el cambio de país con el anterior
                                listTestsCambioPais.add(new PaisIdiomaCambioPais(dCtxSh, paisAnt, idiomaAnt, prioridad));
                                System.out.println(
                                    "Creado Test \"PaisIdiomaCambioPais\" con datos: " + 
                                    "Continente=" + continente.getNombre_continente() +
                                    ",Pais=" + pais.getNombre_pais() +
                                    ",Idioma=" + idioma.getCodigo().getLiteral() +
                                    ",País destion=" + paisAnt.getNombre_pais() +
                                    ",Num Idiomas=" + pais.getListIdiomas().size());
                                break;
                            default:
                                break;
                            }
	            					
                            //Guardamos el par país/idioma
                            paisAnt = pais;
                            idiomaAnt = idioma;
                            prioridad+=1;
                        }
                    }
                }
            }
        }
        catch (Throwable e) {
            e.printStackTrace();
            throw e;
        }        
		
        switch (typeTest) {
        case "1": 
            return listTests.toArray(new Object[listTests.size()]);
        case "3": 
            return listTestsCambioPais.toArray(new Object[listTestsCambioPais.size()]);
        default:
            return listTests.toArray(new Object[listTests.size()]);
        }
    }
	
    /**
     * @return si se ha de crear un test para un país concreto
     */
    protected boolean paisToTest(Pais pais, boolean isOutlet) {
        return (
            pais.getExists().compareTo("n")!=0 &&
            (!isOutlet || (isOutlet && pais.getOutlet_online().compareTo("true")==0))
        );
    }
}