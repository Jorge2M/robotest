package com.mng.robotest.test80.mango.test.factoryes;

import java.util.*;
import org.testng.ITestContext;
import org.testng.annotations.*;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.arq.utils.webdriver.maker.FactoryWebdriverMaker;
import com.mng.robotest.test80.arq.utils.webdriver.maker.FactoryWebdriverMaker.TypeWebDriver;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.appshop.TestNodos;
import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;
import com.mng.robotest.test80.arq.xmlprogram.InputDataTestMaker;
import com.mng.robotest.test80.data.TestMakerContext;
import com.mng.robotest.test80.mango.test.pageobject.shop.PageErrorPage;

public class ListAllNodes {
	
    @SuppressWarnings("unused")
    @Factory
    @Parameters({"url-status", "url-errorpage", "testLinksPie"})
    public Object[] createInstances(String urlStatus, String urlErrorpage, String testLinksPie, ITestContext ctx) 
    throws Exception {
        ArrayList<TestNodos> listTests = new ArrayList<TestNodos>();
    	InputDataTestMaker inputData = TestMakerContext.getInputData(ctx);
        AppEcom appEcom = (AppEcom)inputData.getApp();
        try {
            int accesos = 0;
            switch (appEcom) {
            case shop:
                accesos = 100;
                break;
            case outlet:
            default:
                accesos = 30;
                break;
            }

            LinkedHashMap<String, NodoStatus> mapNodosTotal = new LinkedHashMap<>();
            this.addNodosToMap(mapNodosTotal, accesos, urlErrorpage, appEcom, ctx);
	        
            boolean testLinksPieFlag = false;
            if (testLinksPie.compareTo("true")==0) {
            	testLinksPieFlag = true;
            }
	
            int ii=0;
            int prioridad=0;
            String urlBaseTest = "";
            String urlStatusTest = "";
            String urlErrorpageTest = "";

            //Creamos un test para cada uno de los nodos de Mango Shop
            for (Object nodoObject : mapNodosTotal.values()) {
                NodoStatus nodo = (NodoStatus)nodoObject;
                urlBaseTest = inputData.getUrlBase();
                urlStatusTest = urlStatus;
                urlErrorpageTest = urlErrorpage;
                listTests.add(
                	new TestNodos(mapNodosTotal, nodo, prioridad, urlBaseTest, urlStatusTest, urlErrorpageTest, testLinksPieFlag));
                prioridad+=1;
                System.out.println(
                	"Creado Test con datos: URL=" + inputData.getUrlBase() + 
                	", URLStauts=" + urlStatus + ", URLError=" + urlErrorpage + ", testLinksPieFlag=" + testLinksPieFlag);
                ii+=1;
            }
		}
		catch (Exception e) {
		    e.printStackTrace();
		    throw e;
		}
        		
        return listTests.toArray(new Object[listTests.size()]);
    }
	
    /**
     * Obtiene la lista de nodos (ip+cookies) iterando contra la página de errorPage.faces
     * @param iteraciones número de iteraciones en busca de nuevos nodos
     */
    private void addNodosToMap (HashMap<String, NodoStatus> mapNodos, int iteraciones, String urlErrorpage, AppEcom appE, ITestContext context) 
    throws Exception { 
    	WebDriver driver = 
    		FactoryWebdriverMaker.make(TypeWebDriver.chrome, context)
    			.setChannel(Channel.desktop)
    			.build();    	
	    
		for (int i=0; i<iteraciones; i++) {
		    //Cargamos la página de error donde se encuentra la IP del nodo
	    	    driver.get(urlErrorpage);
	    	    WebdrvWrapp.waitForPageLoaded(driver);
		    	
		    //Creamos un nodo y lo almacenamos en la lista
	    	    NodoStatus nodo = new NodoStatus();
	    	    nodo.setIp(PageErrorPage.getIpNode(driver));
	    	    nodo.setCookies(driver.manage().getCookies());
	    	    nodo.setAppEcom(appE);
	    	    nodo.setSourceDataURL(urlErrorpage);
	    	    mapNodos.put(nodo.getIp(), nodo);
		    	
		    //Borramos las cookies para forzar una nueva sesión/ip en la siguiente llamada
		    driver.manage().deleteAllCookies();
		}
		    
		driver.quit();
    }
}
