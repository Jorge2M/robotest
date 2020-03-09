package com.mng.robotest.test80.mango.test.factoryes;

import java.util.*;

import org.testng.ITestContext;
import org.testng.annotations.*;

import com.mng.robotest.test80.mango.test.data.Constantes;
import com.mng.robotest.test80.mango.test.data.TiendaMantoEnum.TiendaManto;
import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.domain.InputParamsTM;
import com.mng.testmaker.domain.suitetree.TestRunTM;
import com.mng.testmaker.service.TestMaker;
import com.mng.testmaker.service.webdriver.maker.FactoryWebdriverMaker;
import com.mng.testmaker.service.webdriver.maker.FactoryWebdriverMaker.WebDriverType;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.appmanto.Menus;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageSelTda;
import com.mng.robotest.test80.mango.test.pageobject.manto.SecCabecera;
import com.mng.robotest.test80.mango.test.pageobject.shop.PageJCAS;
import com.mng.robotest.test80.mango.test.pageobject.shop.PageMenusManto;

import org.apache.commons.lang3.SerializationUtils;
import org.openqa.selenium.WebDriver;

public class ListMenusManto {
	
	@SuppressWarnings("unused")
	@Factory
	public Object[] createInstances(ITestContext ctxTestRun) throws Exception {
		InputParamsTM inputData = TestMaker.getInputParamsSuite(ctxTestRun);
		if (inputData.getTestObject()!=null) {
			List<Object> listTests = new ArrayList<>();	
			listTests.add(
					SerializationUtils.deserialize(Base64.getDecoder().decode(inputData.getTestObject())));
			return listTests.toArray(new Object[listTests.size()]);
		}
		
		ArrayList<Menus> listTests = new ArrayList<Menus>();
		AppEcom appEcom = (AppEcom)inputData.getApp();
		try {
			ArrayList<String> listCabeceraMenus = getListCabecerasMenus(inputData.getUrlBase(), ctxTestRun);
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
			e.printStackTrace();
			throw e;
		}
	
		return listTests.toArray(new Object[listTests.size()]);
	}
	
    /**
     * Obtiene la lista con los nombres de las cabeceras de los grupos de menús de Manto
     */
    private ArrayList<String> getListCabecerasMenus(String urlBaseManto, ITestContext ctxTestRun) throws Exception { 
    	TestRunTM testRun = TestMaker.getTestRun(ctxTestRun);
    	WebDriver driver = 
    		FactoryWebdriverMaker.make(WebDriverType.firefox, testRun)
				.setChannel(Channel.desktop)
				.build(); 

    	goToMantoLoginAndSelectTienda(urlBaseManto, Constantes.userManto, Constantes.passwordManto, driver);
        ArrayList<String> listMenuNames = PageMenusManto.getListCabecerasMenusName(driver);
        driver.quit();
        return listMenuNames;
    }
    
    private void goToMantoLoginAndSelectTienda(String urlManto, String usrManto, String passManto, WebDriver driver) 
    throws Exception {
        String codigoEspanya = "001";
        String almacenEspanya = "001";
    	driver.manage().deleteAllCookies();
        driver.get(urlManto);
        PageJCAS.identication(driver, usrManto, passManto);
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
