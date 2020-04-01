package com.mng.robotest.test80.mango.test.factoryes;

import java.util.*;

import org.testng.ITestContext;
import org.testng.annotations.*;

import com.mng.testmaker.service.TestMaker;
import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.domain.InputParamsTM;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.appshop.PaisAplicaVale;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.*;
import com.mng.robotest.test80.mango.test.suites.PagosPaisesSuite.VersionPagosSuite;
import com.mng.robotest.test80.mango.test.utils.PaisGetter;
import com.mng.robotest.test80.mango.test.utils.UtilsTestMango;

public class ListPrecompraPaises {
	
	Collection<Integer> Pais5SHOPDAY = new ArrayList<>();
	
	@SuppressWarnings("unused")
	@Parameters({"countrys"})
	@Factory
	public Object[] createInstances(String countrys, ITestContext ctxTestRun) throws Exception {
		List<Object> listTests = new ArrayList<>();
		try {
			InputParamsTM inputData = TestMaker.getInputParamsSuite(ctxTestRun);
			VersionPagosSuite version = VersionPagosSuite.valueOf(inputData.getVersion());
			List<Pais> listCountrys = PaisGetter.getFromCommaSeparatedCountries(countrys);
			int prioridad=0;
			for (Pais pais : listCountrys) {
				IdiomaPais primerIdioma = pais.getListIdiomas().get(0);
				AppEcom app = (AppEcom)inputData.getApp();
				Channel channel = inputData.getChannel();
				if (UtilsTestMango.paisConCompra(pais, app)) {
					if (!(version.isEmpl() && pais.getAccesoEmpl().getTarjeta()==null)) {
						if (!(app==AppEcom.votf && pais.getAccesoVOTF().getUsuario()==null)) {
							DataCtxShop dCtxSh = new DataCtxShop(app, channel, pais, pais.getListIdiomas().get(0)/*, inputData.getUrlBase()*/);
							listTests.add(new PaisAplicaVale(version, dCtxSh, prioridad));
							prioridad+=1;
							System.out.println(
								"Creado Test con datos: " +
								",Pais=" + pais.getNombre_pais() +
								",Idioma=" + primerIdioma.getCodigo().getLiteral() +
								",Num Idiomas=" + pais.getListIdiomas().size());
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
