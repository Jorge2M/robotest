package com.mng.robotest.test80.mango.test.factoryes;

import java.util.*;

import org.testng.ITestContext;
import org.testng.annotations.*;

import com.mng.testmaker.service.TestMaker;
import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.domain.InputParamsTM;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.appshop.PaisIdioma;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.factoryes.Utilidades;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.*;
import com.mng.robotest.test80.mango.test.getdata.usuarios.GestorUsersShop;
import com.mng.robotest.test80.mango.test.getdata.usuarios.UserShop;
import com.mng.robotest.test80.mango.test.suites.PaisIdiomaSuite.VersionPaisSuite;
import com.mng.robotest.test80.mango.test.utils.PaisGetter;

public class LineasBannersFactory {
	
    @Factory
    @Parameters({"countrys", "lineas"})
    public Object[] createInstances(String countrysStr, String lineas, ITestContext ctxTestRun) throws Exception {
        ArrayList<PaisIdioma> listTests = new ArrayList<>();
        ArrayList<String> listaPaises = new ArrayList<>();
        try {
        	InputParamsTM inputData = TestMaker.getInputParamsSuite(ctxTestRun);
        	AppEcom app = (AppEcom)inputData.getApp();
        	Channel channel = inputData.getChannel();
        	VersionPaisSuite version = VersionPaisSuite.valueOf(inputData.getVersion());
            List<Pais> listCountrys = PaisGetter.getFromCommaSeparatedCountries(countrysStr);
            int prioridad=0;
            for (Pais pais : listCountrys) {
                Iterator<IdiomaPais> itIdiomas = pais.getListIdiomas().iterator();
                while (itIdiomas.hasNext()) {
                    IdiomaPais idioma = itIdiomas.next();
                    if (paisToTest(pais, app==AppEcom.outlet)) {
                        listaPaises.add(pais.getNombre_pais().trim());
                        List<Linea> lineasAprobar = Utilidades.getLinesToTest(pais, app, lineas);
                        DataCtxShop dCtxSh = new DataCtxShop(app, channel, pais, idioma, inputData.getUrlBase());
                        UserShop userShop = GestorUsersShop.checkoutBestUserForNewTestCase();
                        dCtxSh.userConnected = userShop.user;
                        dCtxSh.passwordUser = userShop.password;
                        listTests.add(new PaisIdioma(version, dCtxSh, lineasAprobar, prioridad));
                        System.out.println(
                            "Creado Test \"PaisIdioma\" con datos: " + 
                            ",Pais=" + pais.getNombre_pais() +
                            ",Idioma=" + idioma.getCodigo().getLiteral() +
                            ",Num Idiomas=" + pais.getListIdiomas().size());

                        prioridad+=1;
                    }
                }
            }
        }
        catch (Throwable e) {
            e.printStackTrace();
            throw e;
        }        
		
        return listTests.toArray(new Object[listTests.size()]);
    }
	
    /**
     * @return si se ha de crear un test para un país concreto
     */
    protected boolean paisToTest(Pais pais, boolean isOutlet) {
        return (
            pais.getExists().compareTo("n")!=0 &&
            (!isOutlet || (isOutlet && pais.getOutlet_online().compareTo("true")==0))
        );
    }
}