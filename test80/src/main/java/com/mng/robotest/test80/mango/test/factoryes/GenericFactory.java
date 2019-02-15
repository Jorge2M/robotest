package com.mng.robotest.test80.mango.test.factoryes;

import java.util.*;

import org.testng.annotations.*;

import com.mng.robotest.test80.mango.test.appshop.Favoritos;
import com.mng.robotest.test80.mango.test.appshop.MiCuenta;
import com.mng.robotest.test80.mango.test.data.Suites;
import com.mng.robotest.test80.mango.test.factoryes.Utilidades;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.*;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;

import org.testng.ITestContext;


public class GenericFactory {
    @Factory
    @Parameters({"countrys"})
    public Object[] createInstances(String listaPaisesStr, ITestContext context) throws Exception {
        ArrayList<Object> listTests = new ArrayList<>();
        try {
            Suites suite = Suites.valueOf(context.getSuite().getName());
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
        	            	
                    //Sólo creamos test para el 1er idioma
                    Iterator<IdiomaPais> itIdiomas = pais.getListIdiomas().iterator();
                    IdiomaPais primerIdioma = itIdiomas.next();
	                
                    //Sólo creamos el test para países con compra
                    if (paisToTest(pais, false/*isOutlet*/)) {
                    	addTestToList(listTests, suite, pais, primerIdioma, prioridad);
                    	prioridad+=1;
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
	
    /**
     * @param pais
     * @param isOutlet
     * @return si se ha de crear un test para un país concreto
     */	
    protected boolean paisToTest(Pais pais, boolean isOutlet) {
        return (
            "n".compareTo(pais.getExists())!=0 &&
            pais.getShop_online().compareTo("true")==0 &&
            (!isOutlet || (isOutlet && pais.getOutlet_online().compareTo("true")==0))
        );
    }
}
