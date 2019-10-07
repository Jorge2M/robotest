package com.mng.robotest.test80.mango.test.appshop;

import org.testng.ITestContext;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import org.testng.annotations.*;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.utils.DataFmwkTest;
import com.mng.testmaker.utils.TestCaseData;
import com.mng.testmaker.utils.controlTest.mango.*;
import com.mng.robotest.test80.InputParams;
import com.mng.robotest.test80.mango.conftestmaker.Utils;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCheckPedidos;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.datastored.FlagsTestCkout;
import com.mng.robotest.test80.mango.test.datastored.DataCheckPedidos.CheckPedido;
import com.mng.robotest.test80.mango.test.stpv.navigations.manto.PedidoNavigations;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.PagoNavigationsStpV;
import com.mng.robotest.test80.mango.test.suites.PagosPaisesSuite.VersionPagosSuite;
import com.mng.robotest.test80.mango.test.suites.ValesPaisesSuite.VersionValesSuite;

public class PaisAplicaVale extends GestorWebDriver {

    String baseUrl;
    boolean acceptNextAlert = true;
    StringBuffer verificationErrors = new StringBuffer();
	
    private String index_fact;
    public int prioridad;
    private FlagsTestCkout fTCkoutIni;
    String masProductos = "";
    DataCtxShop dCtxSh;
    
    public PaisAplicaVale(VersionPagosSuite version, DataCtxShop dCtxSh, int prioridad) {
    	this.prioridad = prioridad;
        this.fTCkoutIni = FlagsTestCkout.getNew(version);
        setDataFromConstruct(dCtxSh);
    }
    
    public PaisAplicaVale(VersionValesSuite version, DataCtxShop dCtxSh, int prioridad) {
    	this.prioridad = prioridad;
        this.fTCkoutIni = FlagsTestCkout.getNew(version);
        setDataFromConstruct(dCtxSh);
    }
    
    private void setDataFromConstruct(DataCtxShop dCtxSh) {
	    this.dCtxSh = dCtxSh;
	    this.index_fact = 
	    	dCtxSh.pais.getNombre_pais() + " (" + dCtxSh.pais.getCodigo_pais() + ") " + " - " + 
	    	dCtxSh.idioma.getCodigo().getLiteral();
	    if (dCtxSh.vale!=null) {
	    	this.index_fact+= 
	    		" - " + dCtxSh.vale.getCodigoVale() + 
	    		"(" + dCtxSh.vale.isValid() + "_" + dCtxSh.vale.getPorcDescuento() + "perc)";
	    }
    }
	  
    @BeforeMethod (groups={"shop-movil-web", "Canal:all_App:all"})
    public void login(ITestContext context, Method method) throws Exception {
        InputParams inputData = (InputParams)TestCaseData.getInputDataTestMaker(context);
    	this.dCtxSh.urlAcceso = inputData.getUrlBase();
    	Utils.storeDataShopForTestMaker(inputData.getWebDriverType(), this.index_fact, this.dCtxSh, context, method);    
    }
	
    @SuppressWarnings("unused")
    @AfterMethod (groups={"shop-movil-web", "Canal:all_App:all"}, alwaysRun = true)
    public void logout(ITestContext context, Method method) throws Exception {
        WebDriver driver = TestCaseData.getWebDriver();
        super.quitWebDriver(driver, context);
    }	
	
    @Test (
    	groups={"Pagos", "shop-movil-web", "Canal:all_App:all"}, alwaysRun=true, 
    	description="Compra usuario no registrado")
    public void CHK001_Compra() throws Exception {
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
        dCtxSh.userRegistered = false;
        DataCtxPago dCtxPago = new DataCtxPago(this.dCtxSh);
        FlagsTestCkout fTCkout = (FlagsTestCkout)fTCkoutIni.clone();
        fTCkout.emailExist = true; 
        fTCkout.trjGuardada = false;
        dCtxPago.setFTCkout(fTCkout);

        PagoNavigationsStpV.testFromLoginToExecPaymetIfNeeded(this.dCtxSh, dCtxPago, dFTest);
        if (fTCkout.validaPedidosEnManto) {
        	List<CheckPedido> listChecks = Arrays.asList(
        		CheckPedido.consultarBolsa, 
        		CheckPedido.consultarPedido);
            DataCheckPedidos checksPedidos = DataCheckPedidos.newInstance(dCtxPago.getListPedidos(), listChecks);
            PedidoNavigations.testPedidosEnManto(checksPedidos, dCtxSh.appE, dFTest);
        }
    }
}
