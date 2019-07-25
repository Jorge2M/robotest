package com.mng.robotest.test80.mango.test.appshop;

import org.testng.ITestContext;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import org.testng.annotations.*;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.utils.controlTest.mango.*;
import com.mng.robotest.test80.arq.xmlprogram.InputDataTestMaker;
import com.mng.robotest.test80.data.TestMakerContext;
import com.mng.robotest.test80.mango.conftestmaker.Utils;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCheckPedidos;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.datastored.FlagsTestCkout;
import com.mng.robotest.test80.mango.test.datastored.DataCheckPedidos.CheckPedido;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.*;
import com.mng.robotest.test80.mango.test.stpv.navigations.manto.PedidoNavigations;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.PagoNavigationsStpV;
import com.mng.robotest.test80.mango.test.xmlprogram.PagosPaisesSuite.VersionPagosSuite;

public class PaisAplicaVale extends GestorWebDriver {

    String baseUrl;
    boolean acceptNextAlert = true;
    StringBuffer verificationErrors = new StringBuffer();
	
    public String index_fact;
    public Continente continente;
    public Pais paisChange;
    public VersionPagosSuite version;
    public int prioridad;
    String masProductos = "";
    DataCtxShop dCtxSh;
    
    public PaisAplicaVale(VersionPagosSuite version, DataCtxShop dCtxSh, Continente continente, Pais paisChange, int prioridad) {
        this.version = version;
        this.dCtxSh = dCtxSh;
        this.continente = continente;
        this.paisChange = paisChange;
        this.prioridad = prioridad;
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
        InputDataTestMaker inputData = TestCaseData.getInputDataTestMaker(context);
    	this.dCtxSh.urlAcceso = inputData.getUrlBase();
    	Utils.storeDataShopForTestMaker(inputData.getTypeWebDriver(), this.index_fact, this.dCtxSh, context, method);    
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
    	InputDataTestMaker inputData = TestMakerContext.getTestMakerContext(dFTest.ctx).getInputData();
    	VersionPagosSuite version = VersionPagosSuite.valueOf(inputData.getVersionSuite());

        dCtxSh.userRegistered = false;
        DataCtxPago dCtxPago = new DataCtxPago(this.dCtxSh);
        FlagsTestCkout fTCkout = FlagsTestCkout.getNew(version);
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
