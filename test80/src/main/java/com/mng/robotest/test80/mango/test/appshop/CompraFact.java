package com.mng.robotest.test80.mango.test.appshop;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod; 
import org.testng.annotations.Test;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.utils.controlTest.mango.GestorWebDriver;
import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.mango.test.data.Constantes;
import com.mng.robotest.test80.arq.xmlprogram.InputDataTestMaker;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.conftestmaker.Utils;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataBag;
import com.mng.robotest.test80.mango.test.datastored.DataCheckPedidos;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.datastored.FlagsTestCkout;
import com.mng.robotest.test80.mango.test.datastored.DataCheckPedidos.CheckPedido;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.IdiomaPais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.getdata.productos.ArticleStock;
import com.mng.robotest.test80.mango.test.getdata.usuarios.GestorUsersShop;
import com.mng.robotest.test80.mango.test.getdata.usuarios.UserShop;
import com.mng.robotest.test80.mango.test.stpv.navigations.manto.PedidoNavigations;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.PagoNavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.AccesoStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.SecBolsaStpV;
import com.mng.robotest.test80.mango.test.utils.UtilsTestMango;

public class CompraFact extends GestorWebDriver {

    Pais españa = null;
    IdiomaPais castellano = null;
    String baseUrl;
    boolean acceptNextAlert = true;
    StringBuffer verificationErrors = new StringBuffer();
    private String index_fact = "";
    public int prioridad;
    Pais paisFactory = null;
    IdiomaPais idiomaFactory = null;
    Pago pago = null;
    boolean usrRegistrado = false;
    boolean empleado = false;
    boolean testVale = false;
    boolean manyArticles = false;
    boolean checkAnulaPedido = false;
    
    //Si añadimos un constructor para el @Factory hemos de añadir este constructor para la invocación desde SmokeTest
    public CompraFact() {}
    
    /**
     * Constructor para invocación desde @Factory
     */
    public CompraFact(Pais pais, IdiomaPais idioma, Pago pago, AppEcom appE, Channel channel, 
    				  boolean usrRegistrado, boolean empleado, boolean testVale, boolean manyArticles, 
    				  boolean checkAnulaPedido, int prioridad) {
        this.paisFactory = pais;
        this.idiomaFactory = idioma;
        this.pago = pago;
        this.usrRegistrado = usrRegistrado;
        this.empleado = empleado;
        this.testVale = testVale;
        this.manyArticles = manyArticles;
        this.checkAnulaPedido = checkAnulaPedido;
        this.prioridad = prioridad;
        this.index_fact = getIndexFactoria(pais, pago, appE, channel);
    }
    
    private String getIndexFactoria(Pais pais, Pago pago, AppEcom appE, Channel channel) {
    	String index = 
	    pais.getNombre_pais().replace(" (Península y Baleares)", "") + 
	    "-" + 
		pago.getNameFilter(channel) +
		"-" +
		pago.getTipoEnvioType(appE);
		if (usrRegistrado) {
			index+="-síUsrReg";
		}
		if (empleado) {
			index+="-síEmpl";
		}
		if (testVale) {
			index+="-síVale";
		}
		if (manyArticles) {
			index+="-síManyArt";
		}
		if (checkAnulaPedido) {
			index+="-anulPedido";
		}
    	return index;
    }
    
    @BeforeMethod(groups={"CompraFact", "Canal:all_App:all", "SupportsFactoryCountrys"})
    public void login(ITestContext context, Method method) 
    throws Exception {
        InputDataTestMaker inputData = TestCaseData.getInputDataTestMaker(context);
        DataCtxShop dCtxSh = new DataCtxShop();
        dCtxSh.setAppEcom((AppEcom)inputData.getApp());
        dCtxSh.setChannel(inputData.getChannel());
        dCtxSh.urlAcceso = inputData.getUrlBase();
        dCtxSh.pais = this.paisFactory;
        dCtxSh.idioma = this.idiomaFactory;
        
        Utils.storeDataShopForTestMaker(inputData.getTypeWebDriver(), this.index_fact, dCtxSh, context, method);     
    }    
    
    @SuppressWarnings("unused")
    @AfterMethod (groups={"CompraFact", "Canal:all_App:all"}, alwaysRun = true)
    public void logout(ITestContext context, Method method) throws Exception {
        WebDriver driver = TestCaseData.getWebDriver();
        super.quitWebDriver(driver, context);
    }    
      
    @Test (
        groups={"Compra", "Canal:all_App:all"}, alwaysRun=true, priority=1, 
        description="Test de compra (creado desde Factoría) con valores específicos a nivel de Pago, Tipo de Envío, Usuario Conectado y Empleado")
    public void COM010_Pago() throws Exception {
        DataFmwkTest dFTest = TestCaseData.getdFTest();
        DataCtxShop dCtxSh = (DataCtxShop)TestCaseData.getData(Constantes.idCtxSh);
        dCtxSh.userRegistered = this.usrRegistrado;
        this.testVale = includeValeValidation(dCtxSh.appE, dFTest);
        if (dCtxSh.userRegistered) {
	        UserShop userShop = GestorUsersShop.checkoutBestUserForNewTestCase();
	        dCtxSh.userConnected = userShop.user;
	        dCtxSh.passwordUser = userShop.password;
//	        dCtxSh.userConnected = "test.performance27@mango.com";
//	        dCtxSh.passwordUser = "Mango123";
        }
        
        //TestAB.activateTestABiconoBolsaDesktop(0, dCtxSh, dFTest.driver);
        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, this.usrRegistrado/*clearArticulos*/, dFTest.driver);
        //TestAB.activateTestABcheckoutMovilEnNPasos(0, dCtxSh, dFTest.driver);
        
        int maxArticlesAwayVale = 3;
        List<ArticleStock> listArticles = UtilsTestMango.getArticlesForTest(dCtxSh, maxArticlesAwayVale, this.testVale);
        if (!manyArticles) {
        	listArticles = Arrays.asList(listArticles.get(0));
        }
        
        DataBag dataBag = new DataBag(); 
        SecBolsaStpV.altaListaArticulosEnBolsa(listArticles, dataBag, dCtxSh, dFTest.driver);
        
        //Hasta página Checkout
        FlagsTestCkout fTCkout = new FlagsTestCkout();
        fTCkout.validaPasarelas = true;  
        fTCkout.validaPagos = true;
        fTCkout.validaPedidosEnManto = true;
        fTCkout.emailExist = true; 
        fTCkout.trjGuardada = false;
        fTCkout.isEmpl = this.empleado;
        DataCtxPago dCtxPago = new DataCtxPago(dCtxSh);
        dCtxPago.setFTCkout(fTCkout);
        dCtxPago.getDataPedido().setDataBag(dataBag);
        dCtxPago.getFTCkout().testCodPromocional = this.testVale || this.empleado;
        PagoNavigationsStpV.testFromBolsaToCheckoutMetPago(dCtxSh, dCtxPago, dFTest.driver);
        
        //Pago
        dCtxPago.getFTCkout().validaPagos = true;
        dCtxPago.getDataPedido().setPago(this.pago);
        PagoNavigationsStpV.checkPasarelaPago(dCtxPago, dCtxSh, dFTest.driver);
        
        //Validación en Manto de los Pedidos (si existen)
        if (fTCkout.validaPedidosEnManto) {
        	List<CheckPedido> listChecks = new ArrayList<CheckPedido>(Arrays.asList(
        		CheckPedido.consultarBolsa, 
        		CheckPedido.consultarPedido));
        	if (checkAnulaPedido) {
        		listChecks.add(CheckPedido.anular);
        	}
            DataCheckPedidos checksPedidos = DataCheckPedidos.newInstance(dCtxPago.getListPedidos(), listChecks);
            PedidoNavigations.testPedidosEnManto(checksPedidos, dCtxSh.appE, dFTest);
        }
    }
    
    private boolean includeValeValidation(AppEcom app, DataFmwkTest dFTest) {
    	return (
    		this.testVale && 
    		!UtilsMangoTest.isEntornoPRO(app, dFTest.driver));
    }
}
