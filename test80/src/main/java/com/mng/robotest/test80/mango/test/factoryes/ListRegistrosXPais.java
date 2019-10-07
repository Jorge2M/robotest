package com.mng.robotest.test80.mango.test.factoryes;

import java.util.*;
import org.testng.annotations.*;

import com.mng.robotest.test80.InputParams;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.appshop.Registro;
import com.mng.robotest.test80.mango.test.factoryes.Utilidades;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.*;
import com.mng.testmaker.domain.SuiteContextTestMaker;

import org.testng.ITestContext;

public class ListRegistrosXPais {
    @SuppressWarnings("unused")
    @Factory
    @Parameters({"countrys", "lineas"})
    public Object[] createInstances(String listaPaisesStr, String lineas, ITestContext ctx) throws Exception {
        ArrayList<Object> listTests = new ArrayList<>();
        InputParams inputData = (InputParams)SuiteContextTestMaker.getInputData(ctx);
        AppEcom appE = (AppEcom)inputData.getApp();
        try {
	    	List<Pais> listCountrys = Utilidades.getListCountrysFiltered(listaPaisesStr);
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
