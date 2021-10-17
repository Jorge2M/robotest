package com.mng.robotest.test80.mango.test.factoryes;

import java.util.*;

import org.testng.ITestContext;
import org.testng.annotations.*;

import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.appshop.Registro;
import com.mng.robotest.test80.mango.test.beans.*;
import com.mng.robotest.test80.mango.test.utils.PaisGetter;
import com.github.jorge2m.testmaker.service.TestMaker;


public class ListRegistrosXPais {
    @SuppressWarnings("unused")
    @Factory
    @Parameters({"countrys", "lineas"})
    public Object[] createInstances(String listaPaisesStr, String lineas, ITestContext ctxTestRun) throws Exception {
        ArrayList<Object> listTests = new ArrayList<>();
        AppEcom appE = (AppEcom)TestMaker.getInputParamsSuite(ctxTestRun).getApp();
        try {
	    	List<Pais> listCountrys = PaisGetter.getFromCommaSeparatedCountries(listaPaisesStr);
            int prioridad=0;
            for (Pais pais : listCountrys) {
                Iterator<IdiomaPais> itIdiomas = pais.getListIdiomas().iterator();
                while (itIdiomas.hasNext()) {
                    IdiomaPais idioma = itIdiomas.next();
                    listTests.add(new Registro(pais, idioma, prioridad));
                    prioridad+=1;
                    System.out.println(
                        "Creado Test con datos: " + 
                        ",Pais=" + pais.getNombre_pais() +
                        ",Idioma=" + idioma.getCodigo().getLiteral() 
                    );
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
