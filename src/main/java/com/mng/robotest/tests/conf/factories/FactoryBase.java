package com.mng.robotest.tests.conf.factories;

import java.util.List;

import org.testng.ITestContext;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.domain.InputParamsTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.conf.Suites;
import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.utils.PaisGetter;

public class FactoryBase {

	protected InputParamsTM inputParams;

	protected Suites getSuite() {
		return (Suites)inputParams.getSuite();
	}
	
	protected AppEcom getApp() {
		return (AppEcom)inputParams.getApp();
	}
	protected Channel getChannel() {
		return inputParams.getChannel();
	}
	protected InputParamsTM getInputParams(ITestContext ctxTestRun) {
		return TestMaker.getInputParamsSuite(ctxTestRun);
	}
	protected List<Pais> getListCountries(String countrysStr) {
		return PaisGetter.getFromCommaSeparatedCountries(countrysStr);
	}
	
	protected boolean paisToTest(Pais pais, AppEcom app) {
		return (
			"n".compareTo(pais.getExists())!=0 &&
			pais.isVentaOnline() &&
			pais.getTiendasOnlineList().contains(app));
	}	
	
	protected boolean isCountryWithSaleToTest(Pais pais, String countrysStr) {
		boolean isCountryTestable = isCountryTestable(pais);
		if (!isAllCountrysToTest(countrysStr)) {
			return isCountryTestable;
		}
		return isCountryTestable && pais.isVentaOnline(); 

	}
	
	private boolean isCountryTestable(Pais pais) {
		return
			"n".compareTo(pais.getExists())!=0 && //Japan
			pais.getTiendasOnlineList().contains(getApp());		
	}
	
	private boolean isAllCountrysToTest(String countrysStr) {
		return 
			countrysStr==null || 
			"".compareTo(countrysStr)==0 ||
			"*".compareTo(countrysStr)==0 ;
	}
	

}
