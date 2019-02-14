package com.mng.robotest.test80.mango.test.appshop;

import org.testng.ITestContext;
import org.testng.annotations.*;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.ThreadData;
import com.mng.robotest.test80.arq.utils.controlTest.mango.*;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.*;
import com.mng.robotest.test80.mango.test.stpv.shop.AccesoStpV;

import org.openqa.selenium.*;

import java.lang.reflect.Method;

@SuppressWarnings("javadoc")
public class PaisIdiomaCambioPais extends GestorWebDriver /*Funcionalidades genéricas propias de MANGO*/ {

    String baseUrl;
    boolean acceptNextAlert = true;
    StringBuffer verificationErrors = new StringBuffer();
	
    private String index_fact;
    private Pais paisDestino;
    private IdiomaPais idiomaDestino;
    public int prioridad;
    private DataCtxShop dCtxSh;
    
    public PaisIdiomaCambioPais(DataCtxShop dCtxSh, Pais paisDestino, IdiomaPais idiomaDestino, int prioridad) {
        this.dCtxSh = dCtxSh;
		this.paisDestino = paisDestino;
		this.idiomaDestino = idiomaDestino;
		this.index_fact = dCtxSh.pais.getNombre_pais() + " (" + dCtxSh.pais.getCodigo_pais() + ") " + "-" + dCtxSh.idioma.getCodigo().getLiteral();
		this.prioridad = prioridad;
    }
	  
    @BeforeMethod
    @Parameters({"brwsr-path","urlBase"})
    public void login(String bpath, String urlAcceso, ITestContext context, Method method) throws Exception {
    	ThreadData.getAndStoreDataFmwk(bpath, urlAcceso, this.index_fact, this.dCtxSh.channel, context, method);
    }
	
    @SuppressWarnings("unused")
    @AfterMethod (alwaysRun = true)
    public void logout(ITestContext context, Method method) throws Exception {
        WebDriver driver = ThreadData.getWebDriver();
        super.quitWebDriver(driver, context);
    }	
	
    @Test
    public void CAM001_PR_CambioPais(ITestContext context, Method method) throws Exception {
    	DataFmwkTest dFTest = ThreadData.getdFTest();
        AccesoStpV.accesoPRYCambioPais(this.dCtxSh, this.paisDestino, this.idiomaDestino, dFTest);
    }
}
