package com.mng.sapfiori.test.testcase.script;

import java.lang.reflect.Method;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.mng.robotest.test80.arq.access.InputParamsTestMaker;
import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.utils.controlTest.mango.GestorWebDriver;
import com.mng.sapfiori.test.testcase.stpv.PageLoginStpV;

public class Piloto extends GestorWebDriver {

	InputParamsTestMaker inputDataTMaker;
    String baseUrl;
        
    public Piloto() {}         
      
    @BeforeMethod (groups={"Piloto", "Canal:desktop_App:all"})
    synchronized public void login(ITestContext context, Method method) 
    throws Exception {
        inputDataTMaker = TestCaseData.getInputDataTestMaker(context);
        
        //TODO refactorizar TestMaker para que no sea necesaria esta llamada
		TestCaseData.getAndStoreDataFmwk(
			inputDataTMaker.getTypeWebDriver(), 
			inputDataTMaker.getUrlBase(),
			"", 
			inputDataTMaker.getChannel(), 
			null, context, method);
    }
    
    @AfterMethod (groups={"Piloto", "Canal:desktop_App:all"}, alwaysRun = true)
    public void logout(ITestContext context, Method method) throws Exception {
        WebDriver driver = TestCaseData.getWebDriver();
        super.quitWebDriver(driver, context);
    }       

    @Test (
        groups={"Piloto", "Canal:desktop_App:all"}, alwaysRun=true, 
        description="Se realiza un login de usuario")
    public void PIL001_Login() throws Exception {
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
        dFTest.driver.get(inputDataTMaker.getUrlBase());
    	
    	//Ejecutar el Login
    	PageLoginStpV pageLogin = PageLoginStpV.getNew(dFTest.driver);
    	pageLogin.inputCredentialsAndEnter("00556106", "Martina_2010");
    }
}
