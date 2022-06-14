package com.mng.robotest.test.factoryes;

import java.util.*;

import org.testng.ITestContext;
import org.testng.annotations.*;

import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.domain.InputParamsTM;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.appshop.PaisAplicaVale;
import com.mng.robotest.test.beans.*;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.suites.PagosPaisesSuite.VersionPagosSuite;
import com.mng.robotest.test.utils.PaisGetter;
import com.mng.robotest.test.utils.UtilsTestMango;

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
					DataCtxShop dCtxSh = new DataCtxShop(app, channel, pais, pais.getListIdiomas().get(0));
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
		catch (Throwable e) {
			throw e;
		}

		return (listTests.toArray(new Object[listTests.size()]));
	}	
}
