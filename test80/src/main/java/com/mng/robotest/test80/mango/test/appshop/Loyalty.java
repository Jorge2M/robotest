package com.mng.robotest.test80.mango.test.appshop;

import java.util.Arrays;
import java.util.List;

import com.mng.robotest.test80.mango.test.stpv.shop.loyalty.PageHomeConseguirPor1200LikesStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.loyalty.PageHomeDonateLikesStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.loyalty.PageHomeLikesStpV;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.mng.testmaker.service.TestMaker;
import com.mng.robotest.test80.access.InputParamsMango;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.PaisShop;
import com.mng.robotest.test80.mango.test.datastored.DataBag;
import com.mng.robotest.test80.mango.test.datastored.DataCheckPedidos;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.datastored.FlagsTestCkout;
import com.mng.robotest.test80.mango.test.datastored.DataCheckPedidos.CheckPedido;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.IdiomaPais;
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
import com.mng.robotest.test80.mango.test.stpv.shop.menus.SecMenusUserStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.menus.SecMenusWrapperStpV;
import com.mng.robotest.test80.mango.test.utils.PaisGetter;

public class Loyalty {
	
	private final static Pais españa = PaisGetter.get(PaisShop.España);
	private final static IdiomaPais castellano = españa.getListIdiomas().get(0);
	
	final static String userWithLPoints = "ticket_digital_es@mango.com";
	final static String passwUserWithLPoints = "mango123";
	
	final static String userWithLPointsOnlyInTest = "test.performance10@mango.com";
	final static String passwUserWithLPointsOnlyInTest = "Mango123";

	private DataCtxShop getCtxShForTest() throws Exception {
		InputParamsMango inputParamsSuite = (InputParamsMango)TestMaker.getTestCase().getInputParamsSuite();
		DataCtxShop dCtxSh = new DataCtxShop();
		dCtxSh.setAppEcom((AppEcom)inputParamsSuite.getApp());
		dCtxSh.setChannel(inputParamsSuite.getChannel());
		dCtxSh.urlAcceso = inputParamsSuite.getUrlBase();
		dCtxSh.pais = españa;
		dCtxSh.idioma = castellano;
		return dCtxSh;
	}
	
    @Test (
        groups={"Loyalty", "Canal:all_App:shop"},
        description="Se realiza una compra mediante un usuario loyalty con Likes")
    public void LOY001_Compra_LikesStored() throws Exception {
    	WebDriver driver = TestMaker.getDriverTestCase();
        DataCtxShop dCtxSh = getCtxShForTest();
        dCtxSh.userConnected = userWithLPoints;
        dCtxSh.passwordUser = passwUserWithLPoints;
        dCtxSh.userRegistered = true;
        
        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, true, driver);
        
        //Queremos asegurarnos que obtenemos artículos no-rebajados para poder aplicarle el descuento por Loyalty Points
        DataBag dataBag = addBagArticleNoRebajado(dCtxSh, driver);
        
        //Seleccionar el botón comprar y completar el proceso hasta la página de checkout con los métodos de pago
        FlagsTestCkout FTCkout = new FlagsTestCkout();
        FTCkout.validaPasarelas = false;  
        FTCkout.validaPagos = false;
        FTCkout.emailExist = true; 
        FTCkout.loyaltyPoints = true;
        DataCtxPago dCtxPago = new DataCtxPago(dCtxSh);
        dCtxPago.setFTCkout(FTCkout);
        dCtxPago.getDataPedido().setDataBag(dataBag);
        PagoNavigationsStpV.testFromBolsaToCheckoutMetPago(dCtxSh, dCtxPago, driver); 

        //Informamos datos varios necesarios para el proceso de pagos de modo que se pruebe el pago StoreCredit
        dCtxPago.getDataPedido().setEmailCheckout(dCtxSh.userConnected);
        dCtxPago.getFTCkout().validaPagos = true;
        Pago pagoVISA = dCtxSh.pais.getPago("VISA");
        dCtxPago.getDataPedido().setPago(pagoVISA);
        PagoNavigationsStpV.checkPasarelaPago(dCtxPago, dCtxSh, driver);
        
        //Validación en Manto de los Pedidos (si existen)
    	List<CheckPedido> listChecks = Arrays.asList(
    		CheckPedido.consultarBolsa, 
    		CheckPedido.consultarPedido);
        DataCheckPedidos checksPedidos = DataCheckPedidos.newInstance(dCtxPago.getListPedidos(), listChecks);
        PedidoNavigations.testPedidosEnManto(checksPedidos, dCtxSh.appE, driver);
    }
    
    private DataBag addBagArticleNoRebajado(DataCtxShop dCtxSh, WebDriver driver) throws Exception {
		Menu1rstLevel menuPersonalizacion = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(LineaType.she, null, "faldas"));
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
		WebDriver driver = TestMaker.getDriverTestCase();
		DataCtxShop dCtxSh = getCtxShForTest();
		dCtxSh.userConnected = userWithLPoints;
		dCtxSh.passwordUser = passwUserWithLPoints;
		dCtxSh.userRegistered = true;

		AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, false, driver);
		SecMenusUserStpV secMenusUserStpV = SecMenusUserStpV.getNew(dCtxSh.channel, dCtxSh.appE, driver);
		int loyaltyPointsIni = secMenusUserStpV.clickMenuMangoLikesYou();
		PageHomeLikesStpV pageHomeLikesStpV = PageHomeLikesStpV.getNewInstance(driver);
		pageHomeLikesStpV.clickButtonDonarLikes();
		if (!UtilsMangoTest.isEntornoPRO(dCtxSh.appE, driver)) {
			PageHomeDonateLikesStpV pageHomeDonateLikesStpV = PageHomeDonateLikesStpV.getNew(driver);
			ButtonLikes donateButton = ButtonLikes.Button50likes;
			pageHomeDonateLikesStpV.selectDonateButton(donateButton);
			int loyaltyPointsFin = secMenusUserStpV.clickMenuMangoLikesYou();
			secMenusUserStpV.checkLoyaltyPoints(loyaltyPointsIni, donateButton.getNumLikes(), loyaltyPointsFin);
		}
	}
    
    @Test (
        groups={"Loyalty", "Canal:all_App:shop"},
        description="Exchange entrada de cine mediante 1200 Likes")
    public void LOY003_Exhange_Compra_Entrada() throws Exception {
    	WebDriver driver = TestMaker.getDriverTestCase();
        DataCtxShop dCtxSh = getCtxShForTest();
        boolean isEntornoPro = UtilsMangoTest.isEntornoPRO(dCtxSh.appE, driver);
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

        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, false, driver);
        SecMenusUserStpV secMenusUserStpV = SecMenusUserStpV.getNew(dCtxSh.channel, dCtxSh.appE, driver);
		int loyaltyPointsIni = secMenusUserStpV.clickMenuMangoLikesYou();
        PageHomeLikesStpV.getNewInstance(driver).clickButtonConseguirPor1200Likes();
        if (!isEntornoPro) {
            PageHomeConseguirPor1200LikesStpV pageHomeConseguirPor1200LikesStpV = PageHomeConseguirPor1200LikesStpV.getNew(driver);
            pageHomeConseguirPor1200LikesStpV.selectConseguirButton();
    		int loyaltyPointsFin = secMenusUserStpV.clickMenuMangoLikesYou();
    		secMenusUserStpV.checkLoyaltyPoints(loyaltyPointsIni, 1200, loyaltyPointsFin);
        }
    }
}
