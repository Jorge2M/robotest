package com.mng.robotest.tests.conf.factories;

import java.util.*;

import org.testng.ITestContext;
import org.testng.annotations.*;

import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.conf.Suites;
import com.mng.robotest.tests.domains.availability.tests.AvailabilityShop;
import com.mng.robotest.tests.domains.compra.tests.CompraMultiAddress;
import com.mng.robotest.tests.domains.favoritos.tests.Favoritos;
import com.mng.robotest.tests.domains.micuenta.tests.MiCuenta;
import com.mng.robotest.tests.domains.temporal.tests.Temporal;
import com.mng.robotest.testslegacy.beans.*;

public class GenericFactory extends FactoryBase {
	
	@Factory
	@Parameters({"countrys"})
	public Object[] createInstances(String listaPaisesStr, ITestContext ctx) {
		inputParams = getInputParams(ctx);
		List<Object> listTests = new ArrayList<>();
		try {
			var listCountrys = getListCountries(listaPaisesStr);
			for (var pais : listCountrys) {
				var primerIdioma = pais.getListIdiomas(getApp()).get(0);
				if (paisToTest(pais, AppEcom.shop)) {
					addTestToList(listTests, getSuite(), pais, primerIdioma);
				}
			}
		}
		catch (Throwable e) {
			throw e;
		}
	
		return (listTests.toArray(new Object[listTests.size()]));
	}	
	
	public void addTestToList(List<Object> listTests, Suites suite, Pais pais, IdiomaPais idioma) {
		switch (suite) {
		case CheckoutMultiAddress:
			listTests.add(new CompraMultiAddress(pais, idioma));
			break;
		case ListFavoritos:
			listTests.add(new Favoritos(pais, idioma));
			break;
		case ListMiCuenta:
			listTests.add(new MiCuenta(pais, idioma));
			break;
		case ModalPortada:
			listTests.add(new Temporal(pais, idioma));
			break;
		case AvailabilityShop:
			listTests.add(new AvailabilityShop(pais, idioma));
			break;			
		default:
		}
		
		System.out.println (
			"Creados test de la Suite " + suite + " con datos: " +
			",Pais=" + pais.getNombrePais() +
			",Idioma=" + idioma.getCodigo().getLiteral()
		);
	}
	
}
