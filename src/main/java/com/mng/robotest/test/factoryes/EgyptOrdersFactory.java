package com.mng.robotest.test.factoryes;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.testng.ITestContext;
import org.testng.annotations.Factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mng.robotest.test.appshop.EgyptOrderTest;
import com.mng.robotest.test.factoryes.entities.EgyptCities;
import com.mng.robotest.test.factoryes.entities.EgyptCity;


public class EgyptOrdersFactory {

	@Factory
	public Object[] createOrders(ITestContext ctxTestRun) throws Exception {
		List<Object> listTests = new ArrayList<>();
		try {
			EgyptCities egyptCities = getEgyptCities();
			for (EgyptCity egyptCity : egyptCities.getCities()) {
				createTest(listTests, egyptCity);
			}
		}
		catch (Throwable e) {
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
