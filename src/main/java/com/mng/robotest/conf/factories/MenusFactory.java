package com.mng.robotest.conf.factories;

import java.util.*;
import org.testng.ITestContext;
import org.testng.annotations.*;

import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.conf.AppEcom;
import com.mng.robotest.conf.suites.MenusPaisSuite.VersionMenusPais;
import com.mng.robotest.test.appshop.paisidioma.PaisIdioma;
import com.mng.robotest.test.beans.*;
import com.mng.robotest.test.utils.PaisGetter;

public class MenusFactory {
	
	@Factory
	@Parameters({"countrys", "lineas"})
	public Object[] createInstances(String countrysStr, String lineas, ITestContext ctxTestRun) {
		List<PaisIdioma> listTests = new ArrayList<>();
		var inputData = TestMaker.getInputParamsSuite(ctxTestRun);
		var app = (AppEcom)inputData.getApp();
		var version = VersionMenusPais.valueOf(inputData.getVersion());
		var listCountrys = PaisGetter.getFromCommaSeparatedCountries(countrysStr);
		for (Pais pais : listCountrys) {
			Iterator<IdiomaPais> itIdiomas = pais.getListIdiomas(app).iterator();
			IdiomaPais idioma = itIdiomas.next();
			if (pais.getTiendasOnlineList().contains(app)) {
				Iterator<Linea> itLineas = Utilidades.getLinesToTest(pais, app, lineas).iterator();
				while (itLineas.hasNext()) {
					Linea linea = itLineas.next();
					if (Utilidades.lineaToTest(linea, app)) {
						List<Linea> lineasAprobar = new ArrayList<>();
						lineasAprobar.add(linea);
						listTests.add(new PaisIdioma(version, pais, idioma, lineasAprobar));
						System.out.println(
							"Creado Test \"PaisIdioma\" con datos: " +
							",Pais=" + pais.getNombre_pais() +
							",Idioma=" + idioma.getCodigo().getLiteral() +
							",Linea=" + linea.getType() + 
							",Num Idiomas=" + pais.getListIdiomas(app).size());
					}
				}
			}
		}
					
		return listTests.toArray(new Object[listTests.size()]);
	}
}