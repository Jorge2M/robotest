package com.mng.robotest.tests.conf.factories;

import java.util.*;

import org.testng.ITestContext;
import org.testng.annotations.*;

import com.mng.robotest.tests.conf.suites.MenusPaisSuite.VersionMenusPais;
import com.mng.robotest.tests.domains.menus.beans.Linea;
import com.mng.robotest.tests.domains.menus.tests.PaisIdioma;
import com.mng.robotest.testslegacy.beans.*;

public class MenusFactory extends FactoryBase {
	
	@Factory
	@Parameters({"countrys", "lineas"})
	public Object[] createInstances(String countrysStr, String lineas, ITestContext ctx) {
	    inputParams = getInputParams(ctx);
	    List<PaisIdioma> listTests = new ArrayList<>();
	    var version = VersionMenusPais.valueOf(inputParams.getVersion());

	    for (Pais pais : getListCountries(countrysStr)) {
	        var optionalIdioma = pais.getListIdiomas(getApp()).stream().findFirst();

	        if (optionalIdioma.isPresent() && pais.getTiendasOnlineList().contains(getApp())) {
	            IdiomaPais idioma = optionalIdioma.get();
	            Utilidades.getLinesToTest(pais, getApp(), lineas).stream()
	                .filter(linea -> Utilidades.lineaToTest(linea, getApp()))
	                .forEach(linea -> {
	                    List<Linea> lineasAprobar = Collections.singletonList(linea);
	                    listTests.add(new PaisIdioma(version, pais, idioma, lineasAprobar));
	                    printTestCreated(pais, idioma, linea);
	                });
	        }
	    }

	    return listTests.toArray(new Object[0]);
	}

	private void printTestCreated(Pais pais, IdiomaPais idioma, Linea linea) {
		System.out.println(String.format("Creado Test \"PaisIdioma\" con datos: " +
		        "Pais=%s, Idioma=%s, Linea=%s, Num Idiomas=%d",
		        pais.getNombrePais(), idioma.getCodigo().getLiteral(),
		        linea.getType(), pais.getListIdiomas(getApp()).size()));
	}
}