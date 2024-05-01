package com.mng.robotest.tests.conf.factories;

import java.util.*;

import org.testng.ITestContext;
import org.testng.annotations.*;

import com.mng.robotest.tests.conf.suites.PagosPaisesSuite.VersionPagosSuite;
import com.mng.robotest.testslegacy.appshop.paisaplicavale.PaisCompra;
import com.mng.robotest.testslegacy.beans.*;
import com.mng.robotest.testslegacy.utils.UtilsTest;

public class ListPrecompraPaises extends FactoryBase {
	
	@Parameters({"countrys"})
	@Factory
	public Object[] createInstances(String countrys, ITestContext ctx) {
		inputParams = getInputParams(ctx);
		List<Object> listTests = new ArrayList<>();
		try {
			var version = VersionPagosSuite.valueOf(inputParams.getVersion());
			var listCountrys = getListCountries(countrys);
			int prioridad=0;
			for (Pais pais : listCountrys) {
				var primerIdioma = pais.getListIdiomas(getApp()).get(0);
				if (UtilsTest.paisConCompra(pais, getApp())) {
					listTests.add(new PaisCompra(version, pais, primerIdioma, prioridad));
					prioridad+=1;
					System.out.println(
						"Creado Test con datos: " +
						",Pais=" + pais.getNombrePais() +
						",Idioma=" + primerIdioma.getCodigo().getLiteral() +
						",Num Idiomas=" + pais.getListIdiomas(getApp()).size());
				}
			}
		}
		catch (Throwable e) {
			throw e;
		}

		return (listTests.toArray(new Object[listTests.size()]));
	}	
}
