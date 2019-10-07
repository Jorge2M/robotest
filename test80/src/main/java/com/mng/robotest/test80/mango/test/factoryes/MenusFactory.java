package com.mng.robotest.test80.mango.test.factoryes;

import java.util.*;
import org.testng.ITestContext;
import org.testng.annotations.*;

import com.mng.testmaker.domain.SuiteContextTestMaker;
import com.mng.testmaker.utils.otras.Channel;
import com.mng.robotest.test80.InputParams;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.appshop.PaisIdioma;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.factoryes.Utilidades;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.*;
import com.mng.robotest.test80.mango.test.suites.MenusPaisSuite.VersionMenusPais;

public class MenusFactory {
	
    @Factory
    @Parameters({"countrys", "lineas"})
    public Object[] createInstances(String countrysStr, String lineas, ITestContext ctx) throws Exception {
        ArrayList<PaisIdioma> listTests = new ArrayList<>();
        InputParams inputData = (InputParams)SuiteContextTestMaker.getInputData(ctx);
        AppEcom app = (AppEcom)inputData.getApp();
        Channel channel = inputData.getChannel();
        VersionMenusPais version = VersionMenusPais.valueOf(inputData.getVersionSuite());
        List<Pais> listCountrys = Utilidades.getListCountrysFiltered(countrysStr);
        int prioridad=0;
        for (Pais pais : listCountrys) {
            Iterator<IdiomaPais> itIdiomas = pais.getListIdiomas().iterator();
            IdiomaPais idioma = itIdiomas.next();
            if (app!=AppEcom.outlet || (app==AppEcom.outlet && pais.getOutlet_online().compareTo("true")==0)) {
                Iterator<Linea> itLineas = Utilidades.getLinesToTest(pais, app, lineas).iterator();
                while (itLineas.hasNext()) {
                    Linea linea = itLineas.next();
                    if (Utilidades.lineaToTest(linea, app)) {
                        List<Linea> lineasAprobar = new ArrayList<>();
                        lineasAprobar.add(linea);
           	            DataCtxShop dCtxSh = new DataCtxShop(app, channel, pais, idioma, inputData.getUrlBase()); 
                        listTests.add(new PaisIdioma(version, dCtxSh, lineasAprobar, prioridad));
                        prioridad+=1;            		
                        System.out.println(
                        	"Creado Test \"PaisIdioma\" con datos: " +
                            ",Pais=" + pais.getNombre_pais() +
                            ",Idioma=" + idioma.getCodigo().getLiteral() +
                            ",Linea=" + linea.getType() + 
                            ",Num Idiomas=" + pais.getListIdiomas().size());
                    }
                }
            }
        }
            		
        return listTests.toArray(new Object[listTests.size()]);
    }
}