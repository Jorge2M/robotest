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
		Pattern pattern = Pattern.compile("(\\d{3})_([A-Z0-9]{2,3})_([a-z]+)_([a-zA-Z0-9\\-]+)_([a-z]+)_(\\d{1,3})([:\\d{1,3}]*)");
		for (String catalog : listCatalogs) {
			Matcher matcher = pattern.matcher(catalog);
			if (matcher.find()) {
				String country = matcher.group(1);
				String idiom = matcher.group(2);
				String linea = matcher.group(3);
				
				String seccion = matcher.group(4).replace("-", "_");
				String galeria = matcher.group(5);
				if ("aaa".compareTo(galeria)==0) {
					galeria = "";
				}
				String familias = matcher.group(6);
				if ("000".compareTo(familias)==0) {
					familias = "";
				}
				if (matcher.groupCount()>6 && matcher.group(7)!=null) {
					familias += matcher.group(7).replace(":", ",");
				}
				MenuI menu = MenuApi.from(seccion, galeria, familias);
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
