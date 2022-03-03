package com.mng.robotest.test.factoryes;

import java.util.*;
import org.testng.annotations.*;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.appshop.rebajas.RebajasJun2019;
import com.mng.robotest.test.beans.*;
import com.mng.robotest.test.factoryes.Utilidades;
import com.mng.robotest.test.jdbc.dao.RebajasPaisDAO;
import com.mng.robotest.test.utils.PaisGetter;
import com.github.jorge2m.testmaker.domain.InputParamsTM;
import com.github.jorge2m.testmaker.service.TestMaker;

import org.testng.ITestContext;

public class ListRebajasXPais {
	@SuppressWarnings("unused")
	@Factory
	@Parameters({"countrys", "lineas"})
	public Object[] createInstances(String listaPaisesStr, String lineas, ITestContext ctxTestRun) throws Exception {
		ArrayList<Object> listTests = new ArrayList<>();
		InputParamsTM inputData = TestMaker.getInputParamsSuite(ctxTestRun);
		AppEcom appE = (AppEcom)inputData.getApp();
		try {
			List<Pais> listCountrys = PaisGetter.getFromCommaSeparatedCountries(listaPaisesStr);
			RebajasPaisDAO rebajasDAO = new RebajasPaisDAO();
			List<String> listCountryCodesInSalePeriod = rebajasDAO.listCountryCodesInRebajas();
				
			int prioridad=0;
			for (Pais pais : listCountrys) {
				if (listCountryCodesInSalePeriod.contains(pais.getCodigo_pais())) {
					List<Linea> lineasAprobar = Utilidades.getLinesToTest(pais, appE, lineas);
					Iterator<IdiomaPais> itIdiomas = pais.getListIdiomas().iterator();
					while (itIdiomas.hasNext()) {
						IdiomaPais idioma = itIdiomas.next();
						//listTests.add(new RebajasJun2018(pais, idioma, lineasAprobar, prioridad));
						//listTests.add(new RebajasSpringIsHere2019(pais, idioma, lineasAprobar, prioridad));
						listTests.add(new RebajasJun2019(pais, idioma, lineasAprobar, prioridad));
						prioridad+=1;
						System.out.println(
							"Creado Test con datos: " + 
							",Pais=" + pais.getNombre_pais() +
							",Idioma=" + idioma.getCodigo().getLiteral() 
						);
					}
				}
			}
		}
		catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	
		return (listTests.toArray(new Object[listTests.size()]));
	}	
}
