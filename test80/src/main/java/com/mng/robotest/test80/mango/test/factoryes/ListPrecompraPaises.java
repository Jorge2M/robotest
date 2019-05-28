package com.mng.robotest.test80.mango.test.factoryes;

import java.util.*;

import org.testng.annotations.*;

import com.mng.robotest.test80.arq.utils.controlTest.mango.GestorWebDriver;
import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.arq.utils.webdriver.maker.FactoryWebdriverMaker.TypeWebDriver;
import com.mng.robotest.test80.mango.test.appshop.PaisAplicaVale;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.Utilidades;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.*;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.utils.UtilsTestMango;

import org.testng.ITestContext;

public class ListPrecompraPaises {
	
	Collection<Integer> Pais5SHOPDAY = new ArrayList<>();
	
	@SuppressWarnings("unused")
    @Factory
	@Parameters({"brwsr-path", "urlBase", "AppEcom", "Channel", "countrys", "isEmpl"})
	public Object[] createInstances(String bpath, String urlAcceso, String appEcom, String channelStr,
									String countrysStr, String IsEmpl, ITestContext context) throws Exception {
	    ArrayList<Object> listTests = new ArrayList<>();
	    try {
	        //Parseo de los parámetros de entrada
	        AppEcom appE = AppEcom.valueOf(appEcom);
	        Channel channel = Channel.valueOf(channelStr);
	        
	        boolean isEmpl=false;
	        if (IsEmpl.compareTo("true")==0) {
	            isEmpl = true;
	        }
	            
	        //Obtenemos la lista de países como lista de enteros
	        List<Integer> listaPaisesInt = UtilsMangoTest.getListaPaisesInt(countrysStr);
    			
	        //Realizamos el filtrado de los países
	        Response response = Utilidades.filtradoListaPaises(false/*todosPaises*/, listaPaisesInt);	
	        Iterator<Continente> itContinentes = response.getResponse().iterator();
    	        
	        int i=0;/*Temporal*/
	        int prioridad=0;
		//Iteramos a nivel de Continentes -> Países -> Idiomas
	        int j=0; /*Temporal*/
    	    	
	        while (itContinentes.hasNext()) {
	            Continente continente = itContinentes.next();
	            Iterator<Pais> itPaises = continente.getPaises().iterator();
	            while (itPaises.hasNext()) {
	                Pais pais = itPaises.next();
        	            	
	                //Sólo creamos test para el 1er idioma
	                Iterator<IdiomaPais> itIdiomas = pais.getListIdiomas().iterator();
	                IdiomaPais primerIdioma = itIdiomas.next();
        	            	
	                //Creamos un test por cada vale encontrado
	                if (UtilsTestMango.paisConCompra(pais, appE)) {
	                    //En caso de Compra-empleado sólo crearemos el test si están definidas las credenciales para ese país concreto
	                    if (!(isEmpl && pais.getAccesoEmpl().getTarjeta()==null)) {
	                        //En caso de VOTF sólo crearemos el test si están definido el usuario/password de acceso para ese país concreto
	                        if (!(appE==AppEcom.votf && pais.getAccesoVOTF().getUsuario()==null)) {
	                            //Creamos un test u otro según si queremos NetTraffic o no
	                            TypeWebDriver canalWebDriver = GestorWebDriver.getTypeWebdriver(bpath);
	                            DataCtxShop dCtxSh = new DataCtxShop(appE, channel, pais, pais.getListIdiomas().get(0), urlAcceso);
	                            listTests.add(new PaisAplicaVale(dCtxSh, continente, pais/*paisChange*/, isEmpl, prioridad));
	                            prioridad+=1;
	                            System.out.println(
	                                "Creado Test con datos: Continente=" + continente.getNombre_continente() +
	                                ",Pais=" + pais.getNombre_pais() +
	                                ",Idioma=" + primerIdioma.getCodigo().getLiteral() +
	                                ",Num Idiomas=" + pais.getListIdiomas().size());
	                        }
	                    }
	                }
	            }
	        }
	    }
	    catch (Throwable e) {
	        e.printStackTrace();
	        throw e;
	    }
    
            return (listTests.toArray(new Object[listTests.size()]));
	}	
}
