package com.mng.robotest.tests.conf.factories;

import java.util.*;

import org.testng.ITestContext;
import org.testng.annotations.*;

import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.conf.suites.PagosPaisesSuite.VersionPagosSuite;
import com.mng.robotest.testslegacy.appshop.paisaplicavale.PaisAplicaVale;
import com.mng.robotest.testslegacy.beans.*;
import com.mng.robotest.testslegacy.utils.PaisGetter;
import com.mng.robotest.testslegacy.utils.UtilsTest;

public class ListPrecompraPaises {
	
	@SuppressWarnings("unused")
	@Parameters({"countrys"})
	@Factory
	public Object[] createInstances(String countrys, ITestContext ctxTestRun) {
		List<Object> listTests = new ArrayList<>();
		try {
			var inputData = TestMaker.getInputParamsSuite(ctxTestRun);
			var version = VersionPagosSuite.valueOf(inputData.getVersion());
			var listCountrys = PaisGetter.getFromCommaSeparatedCountries(countrys);
			int prioridad=0;
			var app = (AppEcom)inputData.getApp();
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
