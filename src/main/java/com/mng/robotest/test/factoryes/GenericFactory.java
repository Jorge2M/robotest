package com.mng.robotest.test.factoryes;

import java.util.*;

import org.testng.ITestContext;
import org.testng.annotations.*;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.conftestmaker.Suites;
import com.mng.robotest.domains.compra.tests.CompraMultiAddress;
import com.mng.robotest.domains.favoritos.tests.Favoritos;
import com.mng.robotest.domains.micuenta.tests.MiCuenta;
import com.mng.robotest.test.beans.*;
import com.mng.robotest.test.utils.PaisGetter;
import com.github.jorge2m.testmaker.service.TestMaker;


public class GenericFactory {
	
	@Factory
	@Parameters({"countrys"})
	public Object[] createInstances(String listaPaisesStr, ITestContext ctxTestRun) {
		List<Object> listTests = new ArrayList<>();
		try {
			Suites suite = (Suites)TestMaker.getInputParamsSuite(ctxTestRun).getSuite();
			List<Pais> listCountrys = PaisGetter.getFromCommaSeparatedCountries(listaPaisesStr);
			int prioridad=0;
			var app = (AppEcom)TestMaker.getInputParamsSuite(ctxTestRun).getApp();
			for (Pais pais : listCountrys) {
				IdiomaPais primerIdioma = pais.getListIdiomas(app).get(0);
				if (paisToTest(pais, AppEcom.shop)) {
					addTestToList(listTests, suite, pais, primerIdioma, prioridad);
					prioridad+=1;
				}
			}
		}
		catch (Throwable e) {
			throw e;
		}
	
		return (listTests.toArray(new Object[listTests.size()]));
	}	
	
	public void addTestToList(List<Object> listTests, Suites suite, Pais pais, IdiomaPais idioma, int prioridad) {
		switch (suite) {
		case CheckoutMultiAddress:
			listTests.add(new CompraMultiAddress(pais, idioma));
			break;
		case ListFavoritos:
			listTests.add(new Favoritos(pais, idioma, prioridad));
			break;
		case ListMiCuenta:
			listTests.add(new MiCuenta(pais, idioma, prioridad));
			break;
		default:
		}
		
		System.out.println (
			"Creados test de la Suite " + suite + " con datos: " +
			",Pais=" + pais.getNombre_pais() +
			",Idioma=" + idioma.getCodigo().getLiteral()
		);
	}
	
	protected boolean paisToTest(Pais pais, AppEcom app) {
		return (
			"n".compareTo(pais.getExists())!=0 &&
			pais.isVentaOnline() &&
			pais.getTiendasOnlineList().contains(app));
	}
}
