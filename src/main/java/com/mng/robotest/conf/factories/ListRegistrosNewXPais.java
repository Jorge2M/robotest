package com.mng.robotest.conf.factories;

import java.util.*;

import org.testng.ITestContext;
import org.testng.annotations.*;

import com.mng.robotest.conf.AppEcom;
import com.mng.robotest.domains.registro.tests.RegistroNew;
import com.mng.robotest.test.beans.*;
import com.mng.robotest.test.utils.PaisGetter;
import com.github.jorge2m.testmaker.service.TestMaker;

public class ListRegistrosNewXPais {

	@Factory
	@Parameters({"countrys"})
	public Object[] createInstances(String listaPaisesStr, ITestContext ctxTestRun) {
		List<Object> listTests;
		var appE = (AppEcom)TestMaker.getInputParamsSuite(ctxTestRun).getApp();
		try {
			listTests = createTests(listaPaisesStr, appE);
		}
		catch (Throwable e) {
			throw e;
		}
		return (listTests.toArray(new Object[listTests.size()]));
	}

	private List<Object> createTests(String listaPaisesStr, AppEcom appE) {
		List<Object> listTests = new ArrayList<>();
		var listCountrys = PaisGetter.getFromCommaSeparatedCountries(listaPaisesStr);
		int prioridad=0;
		for (Pais pais : listCountrys) {
			var itIdiomas = pais.getListIdiomas(appE).iterator();
			while (itIdiomas.hasNext()) {
				var idioma = itIdiomas.next();
				listTests.add(new RegistroNew(pais, idioma, prioridad));
				prioridad+=1;
				printTestCreation(pais, idioma);
			}
		}
		return listTests;
	}

	private void printTestCreation(Pais pais, IdiomaPais idioma) {
		System.out.println(
			"Creado Test con datos: " + 
			",Pais=" + pais.getNombre_pais() +
			",Idioma=" + idioma.getCodigo().getLiteral() 
		);
	}	
}
