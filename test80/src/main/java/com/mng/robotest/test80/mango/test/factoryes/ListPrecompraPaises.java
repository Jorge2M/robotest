package com.mng.robotest.test80.mango.test.factoryes;

import java.util.*;
import org.testng.annotations.*;

import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.arq.xmlprogram.InputDataTestMaker;
import com.mng.robotest.test80.data.TestMakerContext;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.appshop.PaisAplicaVale;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.factoryes.Utilidades;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.*;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.utils.UtilsTestMango;
import com.mng.robotest.test80.mango.test.xmlprogram.PagosPaisesSuite.VersionPagosSuite;

import org.testng.ITestContext;

public class ListPrecompraPaises {
	
	Collection<Integer> Pais5SHOPDAY = new ArrayList<>();
	
	@SuppressWarnings("unused")
    @Factory
	@Parameters({"countrys"})
	public Object[] createInstances(String countrysStr, ITestContext ctx) throws Exception {
	    ArrayList<Object> listTests = new ArrayList<>();
	    try {
	    	InputDataTestMaker inputData = TestMakerContext.getInputData(ctx);
	    	
	        List<Integer> listaPaisesInt = UtilsMangoTest.getListaPaisesInt(countrysStr);
	        Response response = Utilidades.filtradoListaPaises(false, listaPaisesInt);	
	        Iterator<Continente> itContinentes = response.getResponse().iterator();
    	        
	        int prioridad=0;
	        while (itContinentes.hasNext()) {
	            Continente continente = itContinentes.next();
	            Iterator<Pais> itPaises = continente.getPaises().iterator();
	            while (itPaises.hasNext()) {
	                Pais pais = itPaises.next();
        	            	
	                Iterator<IdiomaPais> itIdiomas = pais.getListIdiomas().iterator();
	                IdiomaPais primerIdioma = itIdiomas.next();
        	            	
	                AppEcom app = (AppEcom)inputData.getApp();
	                Channel channel = inputData.getChannel();
	                VersionPagosSuite version = VersionPagosSuite.valueOf(inputData.getVersionSuite());
	                if (UtilsTestMango.paisConCompra(pais, app)) {
	                    if (!(version.isEmpl() && pais.getAccesoEmpl().getTarjeta()==null)) {
	                        if (!(app==AppEcom.votf && pais.getAccesoVOTF().getUsuario()==null)) {
	                            DataCtxShop dCtxSh = new DataCtxShop(app, channel, pais, pais.getListIdiomas().get(0), inputData.getUrlBase());
	                            listTests.add(new PaisAplicaVale(version, dCtxSh, continente, pais, prioridad));
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
