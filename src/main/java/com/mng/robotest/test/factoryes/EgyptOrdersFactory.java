package com.mng.robotest.test.factoryes;

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.testng.ITestContext;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.domain.InputParamsTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.cookiescheck.entities.CookiesData;
import com.mng.robotest.test.appshop.EgyptOrderTest;
import com.mng.robotest.test.appshop.PaisAplicaVale;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.factoryes.entities.EgyptCities;
import com.mng.robotest.test.factoryes.entities.EgyptCity;
import com.mng.robotest.test.pageobject.ayuda.PageAyuda;
import com.mng.robotest.test.suites.PagosPaisesSuite.VersionPagosSuite;
import com.mng.robotest.test.utils.PaisGetter;
import com.mng.robotest.test.utils.UtilsTestMango;

public class EgyptOrdersFactory {

	@Factory
	public Object[] createOrders(ITestContext ctxTestRun) throws Exception {
		List<Object> listTests = new ArrayList<>();
		try {
			InputParamsTM inputData = TestMaker.getInputParamsSuite(ctxTestRun);
			EgyptCities egyptCities = getEgyptCities();
			for (EgyptCity egyptCity : egyptCities.getCities()) {
				createTest(listTests, egyptCity);
			}
		}
		catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}

		return (listTests.toArray(new Object[listTests.size()]));
	}

	private void createTest(List<Object> listTests, EgyptCity egyptCity) {
		listTests.add(new EgyptOrderTest(egyptCity));
		System.out.println(
			"Created Test from Factory with data: " +
			",State=" + egyptCity.getState() +
			",City=" + egyptCity.getCity());
	}	
	
	private EgyptCities getEgyptCities() throws Exception {
		Reader reader = new InputStreamReader(
				EgyptOrdersFactory.class.getResourceAsStream("/egypt_towns.json"), "utf-8");
		
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(reader, EgyptCities.class);
	}
	
}
