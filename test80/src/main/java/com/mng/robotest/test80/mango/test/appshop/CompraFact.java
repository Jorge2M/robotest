package com.mng.robotest.test80.mango.test.appshop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.mng.testmaker.service.TestMaker;
import com.mng.testmaker.utils.otras.Channel;
import com.mng.robotest.test80.InputParams;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
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

public class CompraFact {

    public int prioridad;
    private String index_fact = "";
    private Pais paisFactory = null;
    private IdiomaPais idiomaFactory = null;
    private Pago pago = null;
    private boolean usrRegistrado = false;
    private boolean empleado = false;
    private boolean testVale = false;
    private boolean manyArticles = false;
    private boolean checkAnulaPedido = false;
    
    //Si añadimos un constructor para el @Factory hemos de añadir este constructor para la invocación desde SmokeTest
    public CompraFact() {}
    
    /**
     * Constructor para invocación desde @Factory
     */
    public CompraFact(
    		Pais pais, IdiomaPais idioma, Pago pago, AppEcom appE, Channel channel, boolean usrRegistrado, 
    		boolean empleado, boolean testVale, boolean manyArticles, boolean checkAnulaPedido, int prioridad) {
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
    
    private DataCtxShop getCtxShForTest() throws Exception {
    	InputParams inputParamsSuite = (InputParams)TestMaker.getTestCase().getInputParamsSuite();
        DataCtxShop dCtxSh = new DataCtxShop();
        dCtxSh.setAppEcom((AppEcom)inputParamsSuite.getApp());
        dCtxSh.setChannel(inputParamsSuite.getChannel());
        dCtxSh.urlAcceso = inputParamsSuite.getUrlBase();
        dCtxSh.pais = this.paisFactory;
        dCtxSh.idioma = this.idiomaFactory;
        return dCtxSh;
    }
      
    @Test (
        groups={"Compra", "Canal:all_App:all"}, alwaysRun=true, priority=1, 
        description="Test de compra (creado desde Factoría) con valores específicos a nivel de Pago, Tipo de Envío, Usuario Conectado y Empleado")
    public void COM010_Pago() throws Exception {
    	TestMaker.getTestCase().setRefineDataName(this.index_fact);
    	WebDriver driver = TestMaker.getDriverTestCase();
        DataCtxShop dCtxSh = getCtxShForTest();
        dCtxSh.userRegistered = this.usrRegistrado;
        this.testVale = includeValeValidation(dCtxSh.appE, driver);
        if (dCtxSh.userRegistered) {
	        UserShop userShop = GestorUsersShop.checkoutBestUserForNewTestCase();
	        dCtxSh.userConnected = userShop.user;
	        dCtxSh.passwordUser = userShop.password;;
        }
        
        //TestAB.activateTestABiconoBolsaDesktop(0, dCtxSh, dFTest.driver);
        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, this.usrRegistrado, driver);
        //TestAB.activateTestABcheckoutMovilEnNPasos(0, dCtxSh, dFTest.driver);

        
        int maxArticlesAwayVale = 3;
        List<ArticleStock> listArticles = UtilsTestMango.getArticlesForTest(dCtxSh, maxArticlesAwayVale, this.testVale);
        if (!manyArticles) {
        	listArticles = Arrays.asList(listArticles.get(0));
        }
        
        DataBag dataBag = new DataBag(); 
        SecBolsaStpV.altaListaArticulosEnBolsa(listArticles, dataBag, dCtxSh, driver);
        
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
        PagoNavigationsStpV.testFromBolsaToCheckoutMetPago(dCtxSh, dCtxPago, driver);
        
        //Pago
        dCtxPago.getFTCkout().validaPagos = true;
        dCtxPago.getDataPedido().setPago(this.pago);
        PagoNavigationsStpV.checkPasarelaPago(dCtxPago, dCtxSh, driver);
        
        //Validación en Manto de los Pedidos (si existen)
        if (fTCkout.validaPedidosEnManto) {
        	List<CheckPedido> listChecks = new ArrayList<CheckPedido>(Arrays.asList(
        		CheckPedido.consultarBolsa, 
        		CheckPedido.consultarPedido));
        	if (checkAnulaPedido) {
        		listChecks.add(CheckPedido.anular);
        	}
            DataCheckPedidos checksPedidos = DataCheckPedidos.newInstance(dCtxPago.getListPedidos(), listChecks);
            PedidoNavigations.testPedidosEnManto(checksPedidos, dCtxSh.appE, driver);
        }
    }
    
    private boolean includeValeValidation(AppEcom app, WebDriver driver) {
    	return (
    		this.testVale && 
    		!UtilsMangoTest.isEntornoPRO(app, driver));
    }
}
