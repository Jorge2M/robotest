package com.mng.robotest.tests.conf.factories;

import java.util.*;

import org.testng.ITestContext;
import org.testng.annotations.*;

import com.mng.robotest.tests.conf.suites.PaisIdiomaSuite.VersionPaisSuite;
import com.mng.robotest.tests.domains.menus.tests.PaisIdioma;
import com.mng.robotest.testslegacy.beans.*;

public class LineasBannersFactory extends FactoryBase {
	
	@Factory
	@Parameters({"countrys", "lineas"})
	public Object[] createInstances(String countrysStr, String lineas, ITestContext ctx) {
		inputParams = getInputParams(ctx);		
		List<PaisIdioma> listTests = new ArrayList<>();
		try {
			listTests = getListTests(countrysStr, lineas);
		}
		catch (Throwable e) {
			throw e;
		}		
		return listTests.toArray(new Object[listTests.size()]);
	}

	private List<PaisIdioma> getListTests(String countrysStr, String lineas) {
		List<PaisIdioma> listTests = new ArrayList<>();
		for (var pais : getListCountries(countrysStr)) {
			if (isCountryWithSaleToTest(pais, countrysStr)) {
				for (var idioma : pais.getListIdiomas(getApp())) {
					listTests.add(createTestCase(lineas, pais, idioma));
				}
			}
		}
		return listTests;
	}
	
	private PaisIdioma createTestCase(String lineas, Pais pais, IdiomaPais idioma) {
		var lineasAprobar = Utilidades.getLinesToTest(pais, getApp(), lineas);
		printTestCreation(pais, idioma);
		return new PaisIdioma(getVersion(), pais, idioma, lineasAprobar); 
	}
	
	private VersionPaisSuite getVersion() {
		return VersionPaisSuite.valueOf(inputParams.getVersion());
	}	
	
	private void printTestCreation(Pais pais, IdiomaPais idioma) {
		System.out.println(
				"Creado Test \"PaisIdioma\" con datos: " + 
				",Pais=" + pais.getNombrePais() +
				",Idioma=" + idioma.getCodigo().getLiteral() +
				",Num Idiomas=" + pais.getListIdiomas(getApp()).size());
	}
	
}