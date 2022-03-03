package com.mng.robotest.domains.apiproduct.factory;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.testng.ITestContext;
import org.testng.annotations.*;

import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.domain.InputParamsTM;
import com.mng.robotest.domains.apiproduct.test.ApiProduct;
import com.mng.robotest.test.getdata.products.MenuApi;
import com.mng.robotest.test.getdata.products.MenuI;


public class ListApiCatalogs {
	
	@SuppressWarnings("unused")
	@Parameters({"catalogs"})
	@Factory
	public Object[] createInstances(String catalogs, ITestContext ctxTestRun) throws Exception {
		List<Object> listTests = new ArrayList<>();
		try {
			InputParamsTM inputData = TestMaker.getInputParamsSuite(ctxTestRun);
			List<ParamsTest> listParamsTest = getCatalogsFromParam(catalogs);
			for (ParamsTest paramsTest : listParamsTest) {
				listTests.add(new ApiProduct(paramsTest.pais, paramsTest.idioma, paramsTest.linea, paramsTest.menu));
				System.out.println(
					"Creado Test con datos: " +
					",Pais=" + paramsTest.pais +
					",Idioma=" + paramsTest.idioma +
					",Menu=" + paramsTest.menu);
			}
		}
		catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}

		return (listTests.toArray(new Object[listTests.size()]));
	}	
	
	private List<ParamsTest> getCatalogsFromParam(String catalogs) {
		List<ParamsTest> listParamsTest = new ArrayList<>();
		List<String> listCatalogs = Arrays.asList(catalogs.split(","));
		Pattern pattern = Pattern.compile("(\\d{3})_([A-Z]{2})_([a-z]+)_([a-z]+)_([a-z]+)_(\\d{3})");
		for (String catalog : listCatalogs) {
			Matcher matcher = pattern.matcher(catalog);
			if (matcher.find()) {
				String country = matcher.group(1);
				String idiom = matcher.group(2);
				String linea = matcher.group(3);
				MenuI menu = MenuApi.from(matcher.group(4), matcher.group(5), matcher.group(6));
				listParamsTest.add(new ParamsTest(country, idiom, linea, menu));
			}
		}
		return listParamsTest;
	}
	
	private static class ParamsTest {
		public final String pais;
		public final String idioma;
		public final String linea;
		public final MenuI menu;
		
		public ParamsTest(String pais, String idioma, String linea, MenuI menu) {
			this.pais = pais;
			this.idioma = idioma;
			this.linea = linea;
			this.menu = menu;
		}

		@Override
		public String toString() {
			return "ParamsTest [pais=" + pais + ", idioma=" + idioma + ", linea=" + linea + ", menu=" + menu + "]";
		}
	}
}
