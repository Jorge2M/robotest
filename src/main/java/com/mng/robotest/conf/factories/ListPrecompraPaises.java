package com.mng.robotest.conf.factories;

import java.util.*;

import org.testng.ITestContext;
import org.testng.annotations.*;

import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.domain.InputParamsTM;
import com.mng.robotest.conf.AppEcom;
import com.mng.robotest.conf.suites.PagosPaisesSuite.VersionPagosSuite;
import com.mng.robotest.test.appshop.paisaplicavale.PaisAplicaVale;
import com.mng.robotest.test.beans.*;
import com.mng.robotest.test.utils.PaisGetter;
import com.mng.robotest.test.utils.UtilsTest;

public class ListPrecompraPaises {
	
	Collection<Integer> Pais5SHOPDAY = new ArrayList<>();
	
	@SuppressWarnings("unused")
	@Parameters({"countrys"})
	@Factory
	public Object[] createInstances(String countrys, ITestContext ctxTestRun) {
		List<Object> listTests = new ArrayList<>();
		try {
			InputParamsTM inputData = TestMaker.getInputParamsSuite(ctxTestRun);
			VersionPagosSuite version = VersionPagosSuite.valueOf(inputData.getVersion());
			List<Pais> listCountrys = PaisGetter.getFromCommaSeparatedCountries(countrys);
			int prioridad=0;
			AppEcom app = (AppEcom)inputData.getApp();
			for (Pais pais : listCountrys) {
				IdiomaPais primerIdioma = pais.getListIdiomas(app).get(0);
				Channel channel = inputData.getChannel();
				if (UtilsTest.paisConCompra(pais, app)) {
					listTests.add(new PaisAplicaVale(version, pais, primerIdioma, prioridad));
					prioridad+=1;
					System.out.println(
						"Creado Test con datos: " +
						",Pais=" + pais.getNombre_pais() +
						",Idioma=" + primerIdioma.getCodigo().getLiteral() +
						",Num Idiomas=" + pais.getListIdiomas(app).size());
				}
			}
		}
		catch (Throwable e) {
			throw e;
		}

		return (listTests.toArray(new Object[listTests.size()]));
	}	
}
