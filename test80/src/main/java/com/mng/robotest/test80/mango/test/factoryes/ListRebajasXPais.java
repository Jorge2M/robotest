package com.mng.robotest.test80.mango.test.factoryes;

import java.util.*;
import org.testng.annotations.*;

import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.appshop.rebajas.RebajasJun2019;
import com.mng.robotest.test80.mango.test.factoryes.Utilidades;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.*;
import com.mng.robotest.test80.mango.test.jdbc.dao.RebajasPaisDAO;
import com.mng.robotest.test80.mango.test.utils.PaisExtractor;
import com.mng.testmaker.domain.InputParamsTM;
import com.mng.testmaker.service.TestMaker;

import org.testng.ITestContext;

public class ListRebajasXPais {
    @SuppressWarnings("unused")
    @Factory
    @Parameters({"countrys", "lineas"})
    public Object[] createInstances(String listaPaisesStr, String lineas, ITestContext ctxTestRun) throws Exception {
        ArrayList<Object> listTests = new ArrayList<>();
        InputParamsTM inputData = TestMaker.getInputParamsSuite(ctxTestRun);
        AppEcom appE = (AppEcom)inputData.getApp();
        try {
            List<Pais> listCountrys = PaisExtractor.getFromCommaSeparatedCountries(listaPaisesStr);
            RebajasPaisDAO rebajasDAO = new RebajasPaisDAO();
            List<String> listCountryCodesInSalePeriod = rebajasDAO.listCountryCodesInRebajas();
    	        
            int prioridad=0;
            for (Pais pais : listCountrys) {
                if (listCountryCodesInSalePeriod.contains(pais.getCodigo_pais())) {
                    List<Linea> lineasAprobar = Utilidades.getLinesToTest(pais, appE, lineas);
                    Iterator<IdiomaPais> itIdiomas = pais.getListIdiomas().iterator();
                    while (itIdiomas.hasNext()) {
                        IdiomaPais idioma = itIdiomas.next();
                        //listTests.add(new RebajasJun2018(pais, idioma, lineasAprobar, prioridad));
                        //listTests.add(new RebajasSpringIsHere2019(pais, idioma, lineasAprobar, prioridad));
                        listTests.add(new RebajasJun2019(pais, idioma, lineasAprobar, prioridad));
                        prioridad+=1;
                        System.out.println(
                            "Creado Test con datos: " + 
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
