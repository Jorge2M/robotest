package com.mng.robotest.tests.conf.factories;

import java.util.*;
import org.testng.ITestContext;
import org.testng.annotations.*;

import com.mng.robotest.tests.conf.suites.MenusPaisSuite.VersionMenusPais;
import com.mng.robotest.testslegacy.appshop.paisidioma.PaisIdioma;
import com.mng.robotest.testslegacy.beans.*;

public class MenusFactory extends FactoryBase {
	
	@Factory
	@Parameters({"countrys", "lineas"})
	public Object[] createInstances(String countrysStr, String lineas, ITestContext ctx) {
		inputParams = getInputParams(ctx);
		List<PaisIdioma> listTests = new ArrayList<>();
		var version = VersionMenusPais.valueOf(inputParams.getVersion());
		for (Pais pais : getListCountries(countrysStr)) {
			var itIdiomas = pais.getListIdiomas(getApp()).iterator();
			var idioma = itIdiomas.next();
			if (pais.getTiendasOnlineList().contains(getApp())) {
				var itLineas = Utilidades.getLinesToTest(pais, getApp(), lineas).iterator();
				while (itLineas.hasNext()) {
					Linea linea = itLineas.next();
					if (Utilidades.lineaToTest(linea, getApp())) {
						List<Linea> lineasAprobar = new ArrayList<>();
						lineasAprobar.add(linea);
						listTests.add(new PaisIdioma(version, pais, idioma, lineasAprobar));
						System.out.println(
							"Creado Test \"PaisIdioma\" con datos: " +
							",Pais=" + pais.getNombrePais() +
							",Idioma=" + idioma.getCodigo().getLiteral() +
							",Linea=" + linea.getType() + 
							",Num Idiomas=" + pais.getListIdiomas(getApp()).size());
					}
				}
			}
		}
					
		return listTests.toArray(new Object[listTests.size()]);
	}
}