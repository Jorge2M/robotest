package com.mng.robotest.tests.conf.factories;

import java.util.*;

import org.testng.ITestContext;
import org.testng.annotations.*;

import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.registro.tests.RegistroNew;
import com.mng.robotest.testslegacy.beans.*;

public class ListRegistrosNewXPais extends FactoryBase {

	@Factory
	@Parameters({"countrys"})
	public Object[] createInstances(String listaPaisesStr, ITestContext ctx) {
		inputParams = getInputParams(ctx);
		List<Object> listTests;
		try {
			listTests = createTests(listaPaisesStr, getApp());
		}
		catch (Throwable e) {
			throw e;
		}
		return (listTests.toArray(new Object[listTests.size()]));
	}

	private List<Object> createTests(String listaPaisesStr, AppEcom appE) {
		List<Object> listTests = new ArrayList<>();
		for (var pais : getListCountries(listaPaisesStr)) {
			var itIdiomas = pais.getListIdiomas(appE).iterator();
			while (itIdiomas.hasNext()) {
				var idioma = itIdiomas.next();
				listTests.add(new RegistroNew(pais, idioma));
				printTestCreation(pais, idioma);
			}
		}
		return listTests;
	}

	private void printTestCreation(Pais pais, IdiomaPais idioma) {
		System.out.println(
			"Creado Test con datos: " + 
			",Pais=" + pais.getNombrePais() +
			",Idioma=" + idioma.getCodigo().getLiteral() 
		);
	}	
}
