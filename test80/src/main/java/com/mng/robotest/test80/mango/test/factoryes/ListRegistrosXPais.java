package com.mng.robotest.test80.mango.test.factoryes;

import java.util.*;

import org.testng.annotations.*;

import com.mng.robotest.test80.mango.test.appshop.Registro;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.Utilidades;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.*;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;

import org.testng.ITestContext;


public class ListRegistrosXPais {
    @SuppressWarnings("unused")
    @Factory
    @Parameters({"AppEcom", "countrys", "lineas"})
    public Object[] createInstances(String appEStr, String listaPaisesStr, String lineas, ITestContext context) throws Exception {
        ArrayList<Object> listTests = new ArrayList<>();
        AppEcom appE = AppEcomEnum.getAppEcom(appEStr);
        try {
            //Obtenemos la lista de países como lista de enteros
            List<Integer> listaPaisesInt = UtilsMangoTest.getListaPaisesInt(listaPaisesStr);
    			
            //Realizamos el filtrado de los países
            Response response = Utilidades.filtradoListaPaises(false/*todosPaises*/, listaPaisesInt);	
            Iterator<Continente> itContinentes = response.getResponse().iterator();
    	        
            //Iteramos a nivel de Continentes -> Países -> Idiomas
            int prioridad=0;
            while (itContinentes.hasNext()) {
                Continente continente = itContinentes.next();
                Iterator<Pais> itPaises = continente.getPaises().iterator();
                while (itPaises.hasNext()) {
                    Pais pais = itPaises.next();
                    Iterator<IdiomaPais> itIdiomas = pais.getListIdiomas().iterator();
                    while (itIdiomas.hasNext()) {
                        IdiomaPais idioma = itIdiomas.next();
                        listTests.add(new Registro(pais, idioma, prioridad));
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
        catch (Throwable e) {
            e.printStackTrace();
            throw e;
        }
    
        return (listTests.toArray(new Object[listTests.size()]));
    }	
}
