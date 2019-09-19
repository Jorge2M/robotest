package com.mng.robotest.test80.mango.test.factoryes;

import java.util.*;
import org.testng.annotations.*;
import org.testng.ITestContext;

import com.mng.robotest.test80.InputParams;
import com.mng.robotest.test80.data.TestMakerContext;
import com.mng.robotest.test80.mango.conftestmaker.Suites;
import com.mng.robotest.test80.mango.test.appshop.Favoritos;
import com.mng.robotest.test80.mango.test.appshop.MiCuenta;
import com.mng.robotest.test80.mango.test.factoryes.Utilidades;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.*;


public class GenericFactory {
	
    @Factory
    @Parameters({"countrys"})
    public Object[] createInstances(String listaPaisesStr, ITestContext ctx) throws Exception {
        ArrayList<Object> listTests = new ArrayList<>();
        try {
        	InputParams inputData = (InputParams)TestMakerContext.getInputData(ctx);
        	Suites suite = (Suites)inputData.getSuite();
            List<Pais> listCountrys = Utilidades.getListCountrysFiltered(listaPaisesStr);
            int prioridad=0;
            for (Pais pais : listCountrys) {
                IdiomaPais primerIdioma = pais.getListIdiomas().get(0);
                if (paisToTest(pais, false)) {
                	addTestToList(listTests, suite, pais, primerIdioma, prioridad);
                	prioridad+=1;
                }
            }
        }
        catch (Throwable e) {
            e.printStackTrace();
            throw e;
        }
    
        return (listTests.toArray(new Object[listTests.size()]));
    }	
    
    public void addTestToList(List<Object> listTests, Suites suite, Pais pais, IdiomaPais idioma, int prioridad) {
    	switch (suite) {
    	case ListFavoritos:
    		listTests.add(new Favoritos(pais, idioma, prioridad));
    		break;
    	case ListMiCuenta:
    		listTests.add(new MiCuenta(pais, idioma, prioridad));
    		break;
    	default:
    	}
    	
        System.out.println (
            "Creados test de la Suite " + suite + " con datos: " +
            ",Pais=" + pais.getNombre_pais() +
            ",Idioma=" + idioma.getCodigo().getLiteral()
        );
    }
	
    protected boolean paisToTest(Pais pais, boolean isOutlet) {
        return (
            "n".compareTo(pais.getExists())!=0 &&
            pais.getShop_online().compareTo("true")==0 &&
            (!isOutlet || (isOutlet && pais.getOutlet_online().compareTo("true")==0))
        );
    }
}
