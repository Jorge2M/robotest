package com.mng.robotest.tests.conf.factories;

import java.util.*;

import org.testng.ITestContext;
import org.testng.annotations.*;

import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.domain.InputParamsTM;
import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.conf.suites.PaisIdiomaSuite.VersionPaisSuite;
import com.mng.robotest.testslegacy.appshop.paisidioma.PaisIdioma;
import com.mng.robotest.testslegacy.beans.*;
import com.mng.robotest.testslegacy.utils.PaisGetter;

public class LineasBannersFactory {
	
	private InputParamsTM inputParams;
	
	@Factory
	@Parameters({"countrys", "lineas"})
	public Object[] createInstances(String countrysStr, String lineas, ITestContext ctxTestRun) {
		List<PaisIdioma> listTests = new ArrayList<>();
		inputParams = getInputParams(ctxTestRun);
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
			if (isPaisToTest(pais, countrysStr)) {
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
	
	private void printTestCreation(Pais pais, IdiomaPais idioma) {
		System.out.println(
				"Creado Test \"PaisIdioma\" con datos: " + 
				",Pais=" + pais.getNombrePais() +
				",Idioma=" + idioma.getCodigo().getLiteral() +
				",Num Idiomas=" + pais.getListIdiomas(getApp()).size());
	}
	
	protected boolean isPaisToTest(Pais pais, String countrysStr) {
		boolean isCountryTestable = isCountryTestable(pais);
		if (!isAllCountrysToTest(countrysStr)) {
			return isCountryTestable;
		}
		return isCountryTestable && pais.isVentaOnline(); 

	}
	private boolean isCountryTestable(Pais pais) {
		return
			"n".compareTo(pais.getExists())!=0 && //Japan
			pais.getTiendasOnlineList().contains(getApp());		
	}
	
	private boolean isAllCountrysToTest(String countrysStr) {
		return 
			countrysStr==null || 
			"".compareTo(countrysStr)==0 ||
			"*".compareTo(countrysStr)==0 ;
	}
	
	private AppEcom getApp() {
		return (AppEcom)inputParams.getApp();
	}
	private InputParamsTM getInputParams(ITestContext ctxTestRun) {
		return TestMaker.getInputParamsSuite(ctxTestRun);
	}
	private VersionPaisSuite getVersion() {
		return VersionPaisSuite.valueOf(inputParams.getVersion());
	}
	private List<Pais> getListCountries(String countrysStr) {
		return PaisGetter.getFromCommaSeparatedCountries(countrysStr);
	}
	
}