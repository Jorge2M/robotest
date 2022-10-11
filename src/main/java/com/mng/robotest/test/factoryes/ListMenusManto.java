package com.mng.robotest.test.factoryes;

import java.util.*;

import org.testng.ITestContext;
import org.testng.annotations.*;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.domain.InputParamsTM;
import com.github.jorge2m.testmaker.domain.suitetree.TestRunTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.service.webdriver.maker.FactoryWebdriverMaker;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.appmanto.Menus;
import com.mng.robotest.test.data.TiendaMantoEnum.TiendaManto;
import com.mng.robotest.test.pageobject.manto.PageSelTda;
import com.mng.robotest.test.pageobject.manto.SecCabecera;
import com.mng.robotest.test.pageobject.shop.PageJCAS;
import com.mng.robotest.test.pageobject.shop.PageMenusManto;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets;
import com.mng.robotest.test.utils.awssecrets.Secret;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets.SecretType;

import org.openqa.selenium.WebDriver;

public class ListMenusManto {
	
	@SuppressWarnings("unused")
	@Factory
	public Object[] createInstances(ITestContext ctxTestRun) throws Exception {
		InputParamsTM inputData = TestMaker.getInputParamsSuite(ctxTestRun);
		List<Menus> listTests = new ArrayList<Menus>();
		AppEcom appEcom = (AppEcom)inputData.getApp();
		try {
			List<String> listCabeceraMenus = getListCabecerasMenus(inputData.getUrlBase(), ctxTestRun);
			int prioridad=0;
			for (int i=0; i<listCabeceraMenus.size(); i++) {
				System.out.println("Creado Test con datos: URL=" + inputData.getUrlBase() + ", cabeceraMenuName=" + listCabeceraMenus.get(i));
				if (i < listCabeceraMenus.size()-1) {
					listTests.add(new Menus(listCabeceraMenus.get(i), listCabeceraMenus.get(i+1), prioridad));
				} else {
					listTests.add(new Menus(listCabeceraMenus.get(i), null, prioridad));
				}
				prioridad+=1;
			}
		}
		catch (Exception e) {
			throw e;
		}
	
		return listTests.toArray(new Object[listTests.size()]);
	}
	
	private List<String> getListCabecerasMenus(String urlBaseManto, ITestContext ctxTestRun) throws Exception { 
		TestRunTM testRun = TestMaker.getTestRun(ctxTestRun);
		InputParamsTM inputParams = TestMaker.getInputParamsSuite(ctxTestRun);
		WebDriver driver = 
			FactoryWebdriverMaker.make(testRun)
				.setChannel(Channel.desktop)
				.setupDriverVersionFluent(inputParams.getDriverVersion())
				.build(); 

		driver.manage().deleteAllCookies();
		driver.get(urlBaseManto);
	
		Secret secret = GetterSecrets.factory().getCredentials(SecretType.MANTO_USER);
		goToMantoLoginAndSelectTienda(secret.getUser(), secret.getPassword(), driver);
		
		List<String> listMenuNames = new PageMenusManto().getListCabecerasMenusName();
		driver.quit();
		return listMenuNames;
	}
	
	private void goToMantoLoginAndSelectTienda(/*String urlManto, */String usrManto, String passManto, WebDriver driver) 
	throws Exception {
		String codigoEspanya = "001";
		String almacenEspanya = "001";
//		driver.manage().deleteAllCookies();
//		driver.get(urlManto);
		new PageJCAS().identication(usrManto, passManto);
		TiendaManto tienda = TiendaManto.getTienda(almacenEspanya, codigoEspanya, AppEcom.shop);
		if (!PageSelTda.isPage(driver)) {
			SecCabecera.clickButtonSelTienda(driver);
		}
		if (!PageSelTda.isPage(driver)) {
			SecCabecera.clickButtonSelTienda(driver);
		}
		PageSelTda.selectTienda(tienda, driver);
	}
}
