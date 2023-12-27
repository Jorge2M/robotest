package com.mng.robotest.tests.conf.factories;

import java.util.*;

import org.testng.ITestContext;
import org.testng.annotations.*;

import com.mng.robotest.tests.domains.menus.beans.Linea;
import com.mng.robotest.tests.domains.menus.beans.Sublinea;
import com.mng.robotest.tests.domains.menus.pageobjects.GroupWeb.GroupResponse;
import com.mng.robotest.tests.domains.menus.pageobjects.GroupWeb.GroupType;
import com.mng.robotest.tests.domains.menus.tests.Menus;
import com.mng.robotest.testslegacy.beans.*;

public class MenusFactory2 extends FactoryBase {
	
	@Factory
	@Parameters({"countrys", "lineas"})
	public Object[] createInstances(String countrysStr, String lineas, ITestContext ctx) {
	    inputParams = getInputParams(ctx);
	    List<Menus> listTests = new ArrayList<>();

	    for (Pais pais : getListCountries(countrysStr)) {
	        var optionalIdioma = pais.getListIdiomas(getApp()).stream().findFirst();

	        if (optionalIdioma.isPresent() && pais.getTiendasOnlineList().contains(getApp())) {
	            IdiomaPais idioma = optionalIdioma.get();
	            Utilidades.getLinesToTest(pais, getApp(), lineas).stream()
	                .filter(linea -> Utilidades.lineaToTest(linea, getApp()))
	                .forEach(linea -> {
	                    makeTestsForEachLine(listTests, pais, idioma, linea);
	                });
	        }
	    }

	    return listTests.toArray(new Object[0]);
	}

	private void makeTestsForEachLine(List<Menus> listTests, Pais pais, IdiomaPais idioma, Linea linea) {
		List<Sublinea> sublineas = linea.getListSublineas(getApp());
		var listGroups = GroupType.getGroups(linea.getType());
		for (var group : listGroups) {
			if (group.getGroupResponse()==GroupResponse.ARTICLES) {
				//TODO pending
			} else {
				if (sublineas != null && !sublineas.isEmpty()) {
				    for (var sublinea : sublineas) {
				        listTests.add(new Menus(pais, idioma, linea, sublinea, group));
				        printTestCreated(pais, idioma, linea, sublinea, group);
				    }
				} else {
				    listTests.add(new Menus(pais, idioma, linea, group));
				    printTestCreated(pais, idioma, linea, null, group);
				}
			}
		}
	}

	private void printTestCreated(Pais pais, IdiomaPais idioma, Linea linea, Sublinea sublinea, GroupType group) {
		String sublineaStr = (sublinea!=null) ? sublinea.getTypeSublinea().name() : "null";
		System.out.println(String.format("Creado Test \"PaisIdioma\" con datos: " +
		        "Pais=%s, Idioma=%s, Linea=%s, Sublinea=%s, Grupo=%s",
		        pais.getNombrePais(), idioma.getCodigo().getLiteral(),
		        linea.getType(), sublineaStr, group));
	}
}