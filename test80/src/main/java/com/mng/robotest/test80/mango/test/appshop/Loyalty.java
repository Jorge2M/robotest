package com.mng.robotest.test80.mango.test.appshop;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mng.robotest.test80.mango.test.stpv.shop.loyalty.PageHomeConseguirPor1200LikesStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.loyalty.PageHomeDonateLikesStpV;
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
import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.mango.test.data.Constantes;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.conftestmaker.Utils;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataBag;
import com.mng.robotest.test80.mango.test.datastored.DataCheckPedidos;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.datastored.FlagsTestCkout;
import com.mng.robotest.test80.mango.test.datastored.DataCheckPedidos.CheckPedido;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.filtros.FilterCollection;
import com.mng.robotest.test80.mango.test.pageobject.shop.loyalty.PageHomeDonateLikes.ButtonLikes;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.KeyMenu1rstLevel;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.MenuTreeApp;
import com.mng.robotest.test80.mango.test.stpv.navigations.manto.PedidoNavigations;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.GaleriaNavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.PagoNavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.AccesoStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.SecCabeceraStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.menus.SecMenusUserStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.menus.SecMenusWrapperStpV;

public class Loyalty extends GestorWebDriver {
	
	final static String userWithLPoints = "ticket_digital_es@mango.com";
	final static String passwUserWithLPoints = "mango123";
	
	final static String userWithLPointsOnlyInTest = "test.performance10@mango.com";
	final static String passwUserWithLPointsOnlyInTest = "Mango123";

    DataCtxShop dCtxSh;
	
    @BeforeMethod (groups={"Otras", "Canal:all_App:shop"})
    @Parameters({"brwsr-path","urlBase", "AppEcom", "Channel"})
    public void login(String bpath, String urlAcceso, String appEcom, String channel, ITestContext context, Method method) 
    throws Exception {
        dCtxSh = new DataCtxShop();
        dCtxSh.setAppEcom(appEcom);
        dCtxSh.setChannel(channel);
        dCtxSh.urlAcceso = urlAcceso;
       
        Integer codEspanya = Integer.valueOf(1);
        List<Pais> listaPaises = UtilsMangoTest.listaPaisesXML(new ArrayList<>(Arrays.asList(codEspanya)));
        dCtxSh.pais = UtilsMangoTest.getPaisFromCodigo("001", listaPaises);
        dCtxSh.idioma = dCtxSh.pais.getListIdiomas().get(0);
        
        Utils.storeDataShopForTestMaker(bpath, "", dCtxSh, context, method);
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
        groups={"Loyalty", "Canal:all_App:shop"},
        description="Se realiza una compra mediante un usuario loyalty con Likes")
    public void LOY001_Compra_LikesStored() throws Exception {
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
        DataCtxShop dCtxSh = (DataCtxShop)TestCaseData.getData(Constantes.idCtxSh);
        
        //Obtenemos el usuario/password de acceso
        dCtxSh.userConnected = userWithLPoints;
        dCtxSh.passwordUser = passwUserWithLPoints;
        dCtxSh.userRegistered = true;
        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, true, dFTest.driver);
        
        SecMenusUserStpV userMenusStpV = SecMenusUserStpV.getNew(dCtxSh.channel, dCtxSh.appE, dFTest.driver);
        actionsLoyaltyPostLogin(userMenusStpV, dFTest.driver);
        
        //Queremos asegurarnos que obtenemos artículos no-rebajados para poder aplicarle el descuento por Loyalty Points
        DataBag dataBag = addBagArticleNoRebajado(dFTest.driver);
        
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
        DataCheckPedidos checksPedidos = DataCheckPedidos.newInstance(dCtxPago.getListPedidos(), listChecks);
        PedidoNavigations.testPedidosEnManto(checksPedidos, dCtxSh.appE, dFTest);
    }
    
    private DataBag addBagArticleNoRebajado(WebDriver driver) throws Exception {
		Menu1rstLevel menuPersonalizacion = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(LineaType.she, null, "vestidos"));
		SecMenusWrapperStpV secMenusStpV = SecMenusWrapperStpV.getNew(dCtxSh, driver);
		secMenusStpV.selectMenu1rstLevelTypeCatalog(menuPersonalizacion, dCtxSh);
		secMenusStpV.selectFiltroCollectionIfExists(FilterCollection.nextSeason);
        DataBag dataBag = GaleriaNavigationsStpV.selectArticleAvailableFromGaleria(dCtxSh, driver);
        return dataBag;
    }
    
    @Test (
        groups={"Loyalty", "Canal:all_App:shop"},
        description="Exchange mediante donación de Likes")
    public void LOY002_Exhange_Donacion_Likes() throws Exception {
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
        DataCtxShop dCtxSh = (DataCtxShop)TestCaseData.getData(Constantes.idCtxSh);
        
        dCtxSh.userConnected = userWithLPoints;
        dCtxSh.passwordUser = passwUserWithLPoints;
        dCtxSh.userRegistered = true;
        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, false, dFTest.driver);
        
        SecMenusUserStpV userMenusStpV = SecMenusUserStpV.getNew(dCtxSh.channel, dCtxSh.appE, dFTest.driver);
        int loyaltyPointsIni = actionsLoyaltyPostLogin(userMenusStpV, dFTest.driver);
        userMenusStpV.clickMenuMangoLikesYou();

        PageHomeLikesStpV pageHomeLikesStpV = PageHomeLikesStpV.getNewInstance(dFTest.driver);
        pageHomeLikesStpV.clickOpcionCompraUnDescuento();

        userMenusStpV.clickMenuMangoLikesYou();
        pageHomeLikesStpV.clickButtonDonarLikes();
        if (!UtilsMangoTest.isEntornoPRO(dCtxSh.appE, dFTest.driver)) {
            PageHomeDonateLikesStpV pageHomeDonateLikesStpV = PageHomeDonateLikesStpV.getNew(dFTest.driver);
            ButtonLikes donateButton = ButtonLikes.Button50likes;
            pageHomeDonateLikesStpV.selectDonateButton(donateButton);
            int loyaltyPointsFin = checkAndGetLoyaltyPoints(userMenusStpV, dFTest.driver);
            userMenusStpV.checkLoyaltyPoints(loyaltyPointsIni, donateButton.getNumLikes(), loyaltyPointsFin);
        }
    }
    
    @Test (
        groups={"Loyalty", "Canal:all_App:shop"},
        description="Exchange entrada de cine mediante 1200 Likes")
    public void LOY003_Exhange_Compra_Entrada() throws Exception {
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
        DataCtxShop dCtxSh = (DataCtxShop)TestCaseData.getData(Constantes.idCtxSh);
        boolean isEntornoPro = UtilsMangoTest.isEntornoPRO(dCtxSh.appE, dFTest.driver);
        dCtxSh.userRegistered = true;
        if (isEntornoPro) {
	        dCtxSh.userConnected = userWithLPoints;
	        dCtxSh.passwordUser = passwUserWithLPoints;
        } else {
            //Hemos de utilizar usuarios diferentes en LOY002 y LOY003 porque la ejecución en paralelo
            //puede afectar al cálculo de los Loyalty Points restantes y generar un Defect
	        dCtxSh.userConnected = userWithLPointsOnlyInTest;
	        dCtxSh.passwordUser = passwUserWithLPointsOnlyInTest;
        }

        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, false, dFTest.driver);
        
        SecMenusUserStpV userMenusStpV = SecMenusUserStpV.getNew(dCtxSh.channel, dCtxSh.appE, dFTest.driver);
        int loyaltyPointsIni = actionsLoyaltyPostLogin(userMenusStpV, dFTest.driver);
        userMenusStpV.clickMenuMangoLikesYou();

        PageHomeLikesStpV.getNewInstance(dFTest.driver).clickButtonConseguirPor1200Likes();
        if (!isEntornoPro) {
            PageHomeConseguirPor1200LikesStpV pageHomeConseguirPor1200LikesStpV = PageHomeConseguirPor1200LikesStpV.getNew(dFTest.driver);
            pageHomeConseguirPor1200LikesStpV.selectConseguirButton();
            
            int loyaltyPointsFin = checkAndGetLoyaltyPoints(userMenusStpV, dFTest.driver);
            userMenusStpV.checkLoyaltyPoints(loyaltyPointsIni, 1200, loyaltyPointsFin);
        }
    }
    
    private int actionsLoyaltyPostLogin(SecMenusUserStpV secMenusUserStpV, WebDriver driver) throws Exception {
    	secMenusUserStpV.checkVisibilityLinkMangoLikesYou();
	    return (checkAndGetLoyaltyPoints(secMenusUserStpV, driver));
    }
    
    private int checkAndGetLoyaltyPoints(SecMenusUserStpV secMenusUserStpV, WebDriver driver) throws Exception {
	    makeLoyaltyPointsVisible(dCtxSh, secMenusUserStpV, driver);
		int loyaltyPoints = secMenusUserStpV.checkAngGetLoyaltyPoints(7).getNumberPoints();
		return loyaltyPoints;
    }
    
    private void makeLoyaltyPointsVisible(DataCtxShop dCtxSh, SecMenusUserStpV secMenusUserStpV, WebDriver driver) 
    throws Exception {
	    if (dCtxSh.channel==Channel.desktop && dCtxSh.appE==AppEcom.shop) {
	    	secMenusUserStpV.hoverLinkForShowUserMenuDesktop();
	    } else {
	    	boolean setVisible = true;
	    	SecCabeceraStpV.getNew(dCtxSh.pais, dCtxSh.channel, dCtxSh.appE, driver).setVisibilityLeftMenuMobil(setVisible);
	    }
    }
}
