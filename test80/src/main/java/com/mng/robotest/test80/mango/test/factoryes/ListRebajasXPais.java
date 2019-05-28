package com.mng.robotest.test80.mango.test.factoryes;

import java.util.*;
import org.testng.annotations.*;

import com.mng.robotest.test80.mango.test.appshop.rebajas.RebajasSpringIsHere2019;
import com.mng.robotest.test80.mango.test.data.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.Utilidades;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.*;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.jdbc.dao.RebajasPaisDAO;

import org.testng.ITestContext;

public class ListRebajasXPais {
    @SuppressWarnings("unused")
    @Factory
    @Parameters({"AppEcom", "countrys", "lineas"})
    public Object[] createInstances(String appEStr, String listaPaisesStr, String lineas, ITestContext context) 
    throws Exception {
        ArrayList<Object> listTests = new ArrayList<>();
        AppEcom appE = AppEcom.valueOf(appEStr);
        try {
            //Obtenemos la lista de países como lista de enteros
            List<Integer> listaPaisesInt = UtilsMangoTest.getListaPaisesInt(listaPaisesStr);
    			
            //Realizamos el filtrado de los países
            Response response = Utilidades.filtradoListaPaises(false/*todosPaises*/, listaPaisesInt);	
            Iterator<Continente> itContinentes = response.getResponse().iterator();
            
            //Obtenemos la lista de países en rebajas
            ArrayList<String> listCountryCodesInSalePeriod = RebajasPaisDAO.listCountryCodesInRebajas();
    	        
            //Iteramos a nivel de Continentes -> Países -> Idiomas
            int prioridad=0;
            while (itContinentes.hasNext()) {
                Continente continente = itContinentes.next();
                Iterator<Pais> itPaises = continente.getPaises().iterator();
                while (itPaises.hasNext()) {
                    Pais pais = itPaises.next();
                    if (listCountryCodesInSalePeriod.contains(pais.getCodigo_pais())) {
                        List<Linea> lineasAprobar = Utilidades.getLinesToTest(pais, appE, lineas);
                        Iterator<IdiomaPais> itIdiomas = pais.getListIdiomas().iterator();
                        while (itIdiomas.hasNext()) {
                            IdiomaPais idioma = itIdiomas.next();
                            //listTests.add(new RebajasJun2018(pais, idioma, lineasAprobar, prioridad));
                            listTests.add(new RebajasSpringIsHere2019(pais, idioma, lineasAprobar, prioridad));
                            prioridad+=1;
                            System.out.println(
                                "Creado Test con datos: Continente=" + continente.getNombre_continente() +
                                ",Pais=" + pais.getNombre_pais() +
                                ",Idioma=" + idioma.getCodigo().getLiteral() 
                            );
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
