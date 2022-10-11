package com.mng.robotest.test.factoryes;

import java.util.*;
import org.testng.ITestContext;
import org.testng.annotations.*;

import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.domain.InputParamsTM;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.appshop.paisidioma.PaisIdioma;
import com.mng.robotest.test.beans.*;
import com.mng.robotest.test.suites.MenusPaisSuite.VersionMenusPais;
import com.mng.robotest.test.utils.PaisGetter;

public class MenusFactory {
	
	@Factory
	@Parameters({"countrys", "lineas"})
	public Object[] createInstances(String countrysStr, String lineas, ITestContext ctxTestRun) throws Exception {
		List<PaisIdioma> listTests = new ArrayList<>();
		InputParamsTM inputData = TestMaker.getInputParamsSuite(ctxTestRun);
		AppEcom app = (AppEcom)inputData.getApp();
		VersionMenusPais version = VersionMenusPais.valueOf(inputData.getVersion());
		List<Pais> listCountrys = PaisGetter.getFromCommaSeparatedCountries(countrysStr);
		int prioridad=0;
		for (Pais pais : listCountrys) {
			Iterator<IdiomaPais> itIdiomas = pais.getListIdiomas().iterator();
			IdiomaPais idioma = itIdiomas.next();
			if (pais.getTiendasOnlineList().contains(app)) {
				Iterator<Linea> itLineas = Utilidades.getLinesToTest(pais, app, lineas).iterator();
				while (itLineas.hasNext()) {
					Linea linea = itLineas.next();
					if (Utilidades.lineaToTest(linea, app)) {
						List<Linea> lineasAprobar = new ArrayList<>();
						lineasAprobar.add(linea);
						listTests.add(new PaisIdioma(version, pais, idioma, lineasAprobar, prioridad));
						prioridad+=1;					
						System.out.println(
							"Creado Test \"PaisIdioma\" con datos: " +
							",Pais=" + pais.getNombre_pais() +
							",Idioma=" + idioma.getCodigo().getLiteral() +
							",Linea=" + linea.getType() + 
							",Num Idiomas=" + pais.getListIdiomas().size());
					}
				}
			}
		}
					
		return listTests.toArray(new Object[listTests.size()]);
	}
}