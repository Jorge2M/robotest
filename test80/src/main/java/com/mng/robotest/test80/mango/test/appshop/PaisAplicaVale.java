package com.mng.robotest.test80.mango.test.appshop;

import org.testng.ITestContext;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.*;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.controlTest.mango.*;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCheckPedidos;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.datastored.FlagsTestCkout;
import com.mng.robotest.test80.mango.test.datastored.DataCheckPedidos.CheckPedido;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.*;
import com.mng.robotest.test80.mango.test.stpv.navigations.manto.PedidoNavigations;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.PagoNavigationsStpV;

import org.openqa.selenium.WebDriver;

@SuppressWarnings("javadoc")
public class PaisAplicaVale extends GestorWebDriver {

    String baseUrl;
    boolean acceptNextAlert = true;
    StringBuffer verificationErrors = new StringBuffer();
	
    public String index_fact;
    public Continente continente;
    public Pais paisChange;
    public boolean isEmpl;
    public int prioridad;
    String masProductos = "";
    DataCtxShop dCtxSh;
    
    public PaisAplicaVale(DataCtxShop dCtxSh, Continente continente, Pais paisChange, boolean isEmpl, int prioridad) {
        //Recopilación de parámetros
        this.dCtxSh = dCtxSh;
        this.continente = continente;
        this.paisChange = paisChange;
        this.index_fact = 
        	dCtxSh.pais.getNombre_pais() + " (" + dCtxSh.pais.getCodigo_pais() + ") " + " - " + 
        	dCtxSh.idioma.getCodigo().getLiteral();
        if (dCtxSh.vale!=null) {
        	this.index_fact+= 
        		" - " + dCtxSh.vale.getCodigoVale() + 
        		"(" + dCtxSh.vale.isValid() + "_" + dCtxSh.vale.getPorcDescuento() + "perc)";
        }
        this.isEmpl = isEmpl;
        this.prioridad = prioridad;
    }
	  
    @BeforeMethod (groups={"shop-movil-web"})
    @Parameters({"brwsr-path", "urlBase", "masProductos"})
    public void login(String bpath, String urlAcceso, String masProductosI, ITestContext context, Method method) throws Exception {
        //Creamos el WebDriver con el que ejecutaremos el Test
    	getAndStoreDataFmwk(bpath, urlAcceso, this.index_fact, this.dCtxSh.channel, context, method);    
    }
	
    @SuppressWarnings("unused")
    @AfterMethod (alwaysRun = true)
    public void logout(ITestContext context, Method method) throws Exception {
        WebDriver driver = getWebDriver();
        super.quitWebDriver(driver, context);
    }	
	
    @Test (groups={"Pagos", "shop-movil-web"}, alwaysRun=true, description="Compra usuario no registrado")
    @Parameters({"validaPasarelas", "validaPagos", "validaPedidosEnManto"})
    public void CHK001_Compra_noReg(String validaPasarelasStr, String validaPagosStr, String validaPedidosEnMantoStr, 
    								ITestContext context, Method method) throws Exception {
    	DataFmwkTest dFTest = getdFTest();
        CHK001_Compra_noReg_Impl(validaPasarelasStr, validaPagosStr, validaPedidosEnMantoStr, dFTest);
    }
	
    public void CHK001_Compra_noReg_Impl(String validaPasarelasStr, String validaPagosStr, String validaPedidosEnMantoStr, 
    									 DataFmwkTest dFTest) throws Exception {
        this.dCtxSh.userRegistered = false;
        FlagsTestCkout fTCkout = new FlagsTestCkout();
        if (validaPasarelasStr.compareTo("true")==0) 
            fTCkout.validaPasarelas = true;
            
        if (validaPagosStr.compareTo("true")==0) 
            fTCkout.validaPagos = true;
        
    	String forceTestMisComprasStr = dFTest.ctx.getCurrentXmlTest().getParameter("forceTestMisCompras");
        if (forceTestMisComprasStr!=null && "true".compareTo(forceTestMisComprasStr)==0)
        	fTCkout.forceTestMisCompras = true;
        
        if (validaPedidosEnMantoStr.compareTo("true")==0) 
            fTCkout.validaPedidosEnManto = true;        
            
        fTCkout.emailExist = true; 
        fTCkout.trjGuardada = false;
        fTCkout.isEmpl = this.isEmpl;
        DataCtxPago dCtxPago = new DataCtxPago(this.dCtxSh);
        dCtxPago.setFTCkout(fTCkout);
        PagoNavigationsStpV.testFromLoginToExecPaymetIfNeeded(this.dCtxSh, dCtxPago, dFTest);
        if (fTCkout.validaPedidosEnManto) {
        	List<CheckPedido> listChecks = Arrays.asList(
        		CheckPedido.consultarBolsa, 
        		CheckPedido.consultarPedido);
            DataCheckPedidos checksPedidos = DataCheckPedidos.newInstance(dCtxPago.getListPedidos(), listChecks);
            PedidoNavigations.testPedidosEnManto(checksPedidos, this.dCtxSh.appE, dFTest);
        }
    }
}
