package com.mng.robotest.test80.mango.test.factoryes;

import java.util.*;
import org.testng.ITestContext;
import org.testng.annotations.*;

import com.mng.robotest.test80.mango.test.data.Constantes;
import com.mng.testmaker.access.InputParamsTestMaker;
import com.mng.testmaker.utils.otras.Channel;
import com.mng.testmaker.utils.webdriver.maker.FactoryWebdriverMaker;
import com.mng.testmaker.utils.webdriver.maker.FactoryWebdriverMaker.TypeWebDriver;
import com.mng.testmaker.data.TestMakerContext;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.appmanto.Menus;
import com.mng.robotest.test80.mango.test.pageobject.shop.PageMenusManto;
import com.mng.robotest.test80.mango.test.stpv.manto.PageLoginMantoStpV;
import com.mng.robotest.test80.mango.test.stpv.manto.PageSelTdaMantoStpV;

import org.openqa.selenium.WebDriver;

public class ListMenusManto {
	
    @SuppressWarnings("unused")
    @Factory
    public Object[] createInstances(ITestContext ctx)
    throws Exception {
    	InputParamsTestMaker inputData = TestMakerContext.getInputData(ctx);
        ArrayList<Menus> listTests = new ArrayList<Menus>();
        AppEcom appEcom = (AppEcom)inputData.getApp();
        try {
            ArrayList<String> listCabeceraMenus = getListCabecerasMenus(inputData.getUrlBase(), ctx);
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
    private ArrayList<String> getListCabecerasMenus(String urlBaseManto, ITestContext context) throws Exception { 
    	WebDriver driver = 
    		FactoryWebdriverMaker.make(TypeWebDriver.firefox, context)
				.setChannel(Channel.desktop)
				.build(); 
        PageLoginMantoStpV.login(urlBaseManto, Constantes.userManto, Constantes.passwordManto, driver);
        String codigoEspanya = "001";
        String almacenEspanya = "001";
        PageSelTdaMantoStpV.selectTienda(almacenEspanya, codigoEspanya, AppEcom.shop, driver);
        ArrayList<String> listMenuNames = PageMenusManto.getListCabecerasMenusName(driver);
        driver.quit();
        return listMenuNames;
    }
}
