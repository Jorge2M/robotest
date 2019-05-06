package com.mng.robotest.test80.mango.test.appshop;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mng.robotest.test80.mango.test.stpv.shop.loyalty.PageHomeLikesStpV;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.utils.controlTest.mango.GestorWebDriver;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataBag;
import com.mng.robotest.test80.mango.test.datastored.DataCheckPedidos;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.datastored.FlagsTestCkout;
import com.mng.robotest.test80.mango.test.datastored.DataCheckPedidos.CheckPedido;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.stpv.navigations.manto.PedidoNavigations;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.PagoNavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.AccesoStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.SecBolsaStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.SecCabeceraStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.menus.SecMenusUserStpV;

public class Loyalty extends GestorWebDriver {
	
	final static String userWithLoyaltyPoints = "ticket_digital_es@mango.com";
	final static String passwUserWithLoyaltyPoints = "mango123";
			
    DataCtxShop dCtxSh;
	
    @BeforeMethod (groups={"Otras", "Canal:all_App:shop"})
    @Parameters({"brwsr-path","urlBase", "AppEcom", "Channel"})
    public void login(String bpath, String urlAcceso, String appEcom, String channel, ITestContext context, Method method) 
    throws Exception {
        //Recopilación de parámetros
        dCtxSh = new DataCtxShop();
        dCtxSh.setAppEcom(appEcom);
        dCtxSh.setChannel(channel);
        dCtxSh.urlAcceso = urlAcceso;
        
        Integer codEspanya = Integer.valueOf(1);
        List<Pais> listaPaises = UtilsMangoTest.listaPaisesXML(new ArrayList<>(Arrays.asList(codEspanya)));
        dCtxSh.pais = UtilsMangoTest.getPaisFromCodigo("001", listaPaises);
        dCtxSh.idioma = dCtxSh.pais.getListIdiomas().get(0);
        
        //Almacenamiento final a nivel de Thread (para disponer de 1 x cada @Test)
        TestCaseData.storeInThread(dCtxSh);
        TestCaseData.getAndStoreDataFmwk(bpath, dCtxSh.urlAcceso, "", dCtxSh.channel, context, method);
    }

    /**
     * Acciones posteriores a cualquier @Test
     * @throws Exception
     */    
    @SuppressWarnings("unused")
    @AfterMethod (groups={"Otras", "Canal:all_App:shop"}, alwaysRun = true)
    public void logout(ITestContext context, Method method) throws Exception {
        WebDriver driver = TestCaseData.getWebDriver();
        super.quitWebDriver(driver, context);
    }		
	
    /**
     * Realiza un checkout utilizando el Saldo en Cuenta 
     */
    @Test (
        groups={"Loyalty", "Canal:desktop_App:shop"},
        description="Se realiza una compra mediante un usuario loyalty con Likes")
    public void LOY001_Compra_LikesStored() throws Exception {
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
        DataCtxShop dCtxSh = TestCaseData.getdCtxSh();
        
        //Obtenemos el usuario/password de acceso
        dCtxSh.userConnected = userWithLoyaltyPoints;
        dCtxSh.passwordUser = passwUserWithLoyaltyPoints;
        dCtxSh.userRegistered = true;
        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, true, dFTest.driver);
        actionsLoyaltyPostLogin(dCtxSh.channel, dFTest.driver);
        
        //Damos de alta 1 artículos en la bolsa
        DataBag dataBag = new DataBag(); 
        SecBolsaStpV.altaArticlosConColores(1, dataBag, dCtxSh, dFTest.driver);
        
        //Seleccionar el botón comprar y completar el proceso hasta la página de checkout con los métodos de pago
        FlagsTestCkout FTCkout = new FlagsTestCkout();
        FTCkout.validaPasarelas = false;  
        FTCkout.validaPagos = false;
        FTCkout.emailExist = true; 
        FTCkout.loyaltyPoints = true;
        DataCtxPago dCtxPago = new DataCtxPago(dCtxSh);
        dCtxPago.setFTCkout(FTCkout);
        dCtxPago.getDataPedido().setDataBag(dataBag);
        PagoNavigationsStpV.testFromBolsaToCheckoutMetPago(dCtxSh, dCtxPago, dFTest.driver); 

        //Informamos datos varios necesarios para el proceso de pagos de modo que se pruebe el pago StoreCredit
        dCtxPago.getDataPedido().setEmailCheckout(dCtxSh.userConnected);
        dCtxPago.getFTCkout().validaPagos = true;
        Pago pagoVISA = dCtxSh.pais.getPago("VISA");
        dCtxPago.getDataPedido().setPago(pagoVISA);
        PagoNavigationsStpV.checkPasarelaPago(dCtxPago, dCtxSh, dFTest.driver);
        
        //Validación en Manto de los Pedidos (si existen)
    	List<CheckPedido> listChecks = Arrays.asList(
    		CheckPedido.consultarBolsa, 
    		CheckPedido.consultarPedido);
    		//CheckPedido.checkLoyalty);
        DataCheckPedidos checksPedidos = DataCheckPedidos.newInstance(dCtxPago.getListPedidos(), listChecks);
        PedidoNavigations.testPedidosEnManto(checksPedidos, dCtxSh.appE, dFTest);
    }
    
    /**
     * Realiza un checkout utilizando el Saldo en Cuenta 
     */
    @Test (
        groups={"Loyalty", "Canal:all_App:shop"},
        description="Se accede a la Home Mango Likes You con un usuario Loyalty con Likes")
    public void LOY002_LikesHome_LikesStored() throws Exception {
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
        DataCtxShop dCtxSh = TestCaseData.getdCtxSh();
        
        //Obtenemos el usuario/password de acceso
        dCtxSh.userConnected = userWithLoyaltyPoints;
        dCtxSh.passwordUser = passwUserWithLoyaltyPoints;
        dCtxSh.userRegistered = true;
        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, true, dFTest.driver);
        actionsLoyaltyPostLogin(dCtxSh.channel, dFTest.driver);
        SecMenusUserStpV.clickMenuMangoLikesYou(dCtxSh.channel, dFTest.driver);

        //Validación sección de loyalty pagina principal
        PageHomeLikesStpV pageHomeLikesStpV = PageHomeLikesStpV.getNewInstance(dFTest.driver);
        pageHomeLikesStpV.clickOpcionCompraUnDescuento();
        pageHomeLikesStpV.checkHomePurchaseWithDiscountPageOk();

        SecMenusUserStpV.clickMenuMangoLikesYou(dCtxSh.channel, dFTest.driver);
        if (!UtilsMangoTest.isEntornoPRO(dCtxSh.appE, dFTest.driver)) {
            //Validacion seccion de loyalty pagina donar likes
            pageHomeLikesStpV.clickOpcionDonarLikes();
            pageHomeLikesStpV.checkHomeDonateLikesPageOk();
        }
    }
    
    private void actionsLoyaltyPostLogin(Channel channel, WebDriver driver) throws Exception {
	    SecMenusUserStpV.checkIsVisibleLinkMangoLikesYou(channel, driver);
	    makeLoyaltyPointsVisible(dCtxSh, driver);
    	SecMenusUserStpV.checkIsPresentLoyaltyPoints(2, driver);
    }
    
    private void makeLoyaltyPointsVisible(DataCtxShop dCtxSh, WebDriver driver) throws Exception {
	    if (dCtxSh.channel==Channel.desktop) {
	    	SecMenusUserStpV.hoverLinkForShowMenu(driver);
	    } else {
	    	boolean setVisible = true;
	    	SecCabeceraStpV.getNew(dCtxSh, driver).setVisibilityLeftMenuMobil(setVisible);
	    }
    }
}
