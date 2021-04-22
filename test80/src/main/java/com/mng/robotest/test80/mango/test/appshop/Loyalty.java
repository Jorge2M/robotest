package com.mng.robotest.test80.mango.test.appshop;

import java.util.Arrays;
import java.util.List;

import com.mng.robotest.test80.mango.test.stpv.shop.loyalty.PageHomeConseguirPor1200LikesStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.loyalty.PageHomeDonateLikesStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.loyalty.PageHomeLikesStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.loyalty.PageHomeLikesStpV.DataRegaloPuntos;
import com.mng.robotest.test80.mango.test.stpv.shop.loyalty.PageRegalarMisLikesStpV;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.test80.access.InputParamsMango;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.PaisShop;
import com.mng.robotest.test80.mango.test.datastored.DataBag;
import com.mng.robotest.test80.mango.test.datastored.DataCheckPedidos;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.datastored.FlagsTestCkout;
import com.mng.robotest.test80.mango.test.datastored.DataCheckPedidos.CheckPedido;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.IdiomaPais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.getdata.loyaltypoints.ClientApiLoyaltyPointsDev;
import com.mng.robotest.test80.mango.test.pageobject.shop.loyalty.PageHomeDonateLikes.ButtonLikes;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.KeyMenu1rstLevel;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.MenuTreeApp;
import com.mng.robotest.test80.mango.test.stpv.navigations.manto.PedidoNavigations;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.CheckoutFlow;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.GaleriaNavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test80.mango.test.stpv.shop.AccesoStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.menus.SecMenusUserStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.menus.SecMenusWrapperStpV;
import com.mng.robotest.test80.mango.test.utils.PaisGetter;

public class Loyalty {
	
	private final static Pais españa = PaisGetter.get(PaisShop.España);
	private final static IdiomaPais castellano = españa.getListIdiomas().get(0);
	
	final static String userProWithLPoints = "ticket_digital_es@mango.com";
	final static String passwUserProWithLPoints = "mango123";
//	
//	final static String userWithLPointsOnlyInTest = "test.performance10@mango.com";
//	final static String passwUserWithLPointsOnlyInTest = "Mango123";
	
	public static enum UserTest {
		loy001(userProWithLPoints, passwUserProWithLPoints, "6051483560048388114", "ES"),
		loy002("test.performance21@mango.com", "Mango123", "6877377061230042978", "ES"),
		loy003("test.performance22@mango.com", "Mango123", "6876577027622042923", "ES"),
		loy005_emisor("test.performance23@mango.com", "Mango123", "6875476978997042979", "ES"),
		loy005_receptor("test.performance24@mango.com", "Mango123", "6876477022921042981", "ES");
		
		private String email;
		private String password;
		private String contactId;
		private String country;
		private UserTest(String email, String password, String contactId, String country) {
			this.email = email;
			this.password = password;
			this.contactId = contactId;
			this.country = country;
		}
		
		public String getEmail() {
			return email;
		}
		public String getPassword() {
			return password;
		}
		public String getContactId() {
			return contactId;
		}
		public String getCountry() {
			return country;
		}
	}

	private DataCtxShop getCtxShForTest() throws Exception {
		InputParamsMango inputParamsSuite = (InputParamsMango)TestMaker.getTestCase().getInputParamsSuite();
		DataCtxShop dCtxSh = new DataCtxShop();
		dCtxSh.setAppEcom((AppEcom)inputParamsSuite.getApp());
		dCtxSh.setChannel(inputParamsSuite.getChannel());
		//dCtxSh.urlAcceso = inputParamsSuite.getUrlBase();
		dCtxSh.pais = españa;
		dCtxSh.idioma = castellano;
		return dCtxSh;
	}
	
    @Test (
        groups={"Loyalty", "Canal:desktop,mobile_App:shop"},
        description="Se realiza una compra mediante un usuario loyalty con Likes")
    public void LOY001_Compra_LikesStored() throws Exception {
    	WebDriver driver = TestMaker.getDriverTestCase();
        DataCtxShop dCtxSh = getCtxShForTest();
        dCtxSh.userConnected = UserTest.loy001.getEmail();
        dCtxSh.passwordUser = UserTest.loy001.getPassword();
        dCtxSh.userRegistered = true;
        
        AccesoStpV.oneStep(dCtxSh, true, driver);
        
        //Queremos asegurarnos que obtenemos artículos no-rebajados para poder aplicarle el descuento por Loyalty Points
        DataBag dataBag = addBagArticleNoRebajado(dCtxSh, driver);
        
        //Seleccionar el botón comprar y completar el proceso hasta la página de checkout con los métodos de pago
        FlagsTestCkout FTCkout = new FlagsTestCkout();
        FTCkout.validaPasarelas = true;  
        FTCkout.validaPagos = true;
        FTCkout.emailExist = true; 
        FTCkout.loyaltyPoints = true;
        DataCtxPago dCtxPago = new DataCtxPago(dCtxSh);
        dCtxPago.setFTCkout(FTCkout);
        dCtxPago.getDataPedido().setDataBag(dataBag);
        
        dCtxPago = new CheckoutFlow.BuilderCheckout(dCtxSh, dCtxPago, driver)
        	.pago(dCtxSh.pais.getPago("VISA"))
        	.build()
        	.checkout(From.Bolsa);
        
        //Validación en Manto de los Pedidos (si existen)
    	List<CheckPedido> listChecks = Arrays.asList(
    		CheckPedido.consultarBolsa, 
    		CheckPedido.consultarPedido);
        DataCheckPedidos checksPedidos = DataCheckPedidos.newInstance(dCtxPago.getListPedidos(), listChecks);
        PedidoNavigations.testPedidosEnManto(checksPedidos, dCtxSh.appE, driver);
    }
    
    private DataBag addBagArticleNoRebajado(DataCtxShop dCtxSh, WebDriver driver) throws Exception {
    	Menu1rstLevel menuNewCollection;
    	menuNewCollection = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(LineaType.she, null, "nuevo"));
		SecMenusWrapperStpV secMenusStpV = SecMenusWrapperStpV.getNew(dCtxSh, driver);
		secMenusStpV.selectMenu1rstLevelTypeCatalog(menuNewCollection, dCtxSh);
		//secMenusStpV.selectFiltroCollectionIfExists(FilterCollection.nextSeason);
		DataBag dataBag = GaleriaNavigationsStpV.selectArticleAvailableFromGaleria(dCtxSh, driver);
		return dataBag;
    }
    
	@Test (
		groups={"Loyalty", "Canal:desktop,mobile_App:shop"},
		description="Exchange mediante donación de Likes")
	public void LOY002_Exhange_Donacion_Likes() throws Exception {
		WebDriver driver = TestMaker.getDriverTestCase();
		DataCtxShop dCtxSh = getCtxShForTest();
		boolean isEntornoPro = UtilsMangoTest.isEntornoPRO(dCtxSh.appE, driver);
		dCtxSh.userRegistered = true;
		if (isEntornoPro) {
			dCtxSh.userConnected = userProWithLPoints;
			dCtxSh.passwordUser = passwUserProWithLPoints;
		} else {
			dCtxSh.userConnected = UserTest.loy002.getEmail();
			dCtxSh.passwordUser = UserTest.loy002.getPassword();
		}

		AccesoStpV.oneStep(dCtxSh, false, driver);
		SecMenusUserStpV secMenusUserStpV = SecMenusUserStpV.getNew(dCtxSh.channel, dCtxSh.appE, driver);
		int loyaltyPointsIni = secMenusUserStpV.clickMenuMangoLikesYou();
		if (loyaltyPointsIni < 3000 && !isEntornoPro) {
			ClientApiLoyaltyPointsDev client = new ClientApiLoyaltyPointsDev();
			client.addLoyaltyPoints(UserTest.loy002, 25000);
			loyaltyPointsIni = secMenusUserStpV.clickMenuMangoLikesYou();
		}
		
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
		groups={"Loyalty", "Canal:desktop,mobile_App:shop"},
		description="Conseguir algo por 1200 likes")
	public void LOY003_Exhange_Compra_Entrada() throws Exception {
		WebDriver driver = TestMaker.getDriverTestCase();
		DataCtxShop dCtxSh = getCtxShForTest();
		boolean isEntornoPro = UtilsMangoTest.isEntornoPRO(dCtxSh.appE, driver);
		dCtxSh.userRegistered = true;
		if (isEntornoPro) {
			dCtxSh.userConnected = userProWithLPoints;
			dCtxSh.passwordUser = passwUserProWithLPoints;
		} else {
			dCtxSh.userConnected = UserTest.loy003.getEmail();
			dCtxSh.passwordUser = UserTest.loy003.getPassword();
		}

		AccesoStpV.oneStep(dCtxSh, false, driver);
		SecMenusUserStpV secMenusUserStpV = SecMenusUserStpV.getNew(dCtxSh.channel, dCtxSh.appE, driver);
		int loyaltyPointsIni = secMenusUserStpV.clickMenuMangoLikesYou();
		if (loyaltyPointsIni < 3000 && !isEntornoPro) {
			ClientApiLoyaltyPointsDev client = new ClientApiLoyaltyPointsDev();
			client.addLoyaltyPoints(UserTest.loy003, 25000);
			loyaltyPointsIni = secMenusUserStpV.clickMenuMangoLikesYou();
		}
		
		PageHomeLikesStpV.getNewInstance(driver).clickButtonConseguirPor1200Likes();
		if (!isEntornoPro) {
			PageHomeConseguirPor1200LikesStpV pageHomeConseguirPor1200LikesStpV = PageHomeConseguirPor1200LikesStpV.getNew(driver);
			pageHomeConseguirPor1200LikesStpV.selectConseguirButton();
			int loyaltyPointsFin = secMenusUserStpV.clickMenuMangoLikesYou();
			secMenusUserStpV.checkLoyaltyPoints(loyaltyPointsIni, 1200, loyaltyPointsFin);
		}
	}

//	@Test (
//		groups={"Loyalty", "Canal:desktop,mobile_App:shop"},
//		description="Registro nuevo usuario comprobando que funciona el vale de descuento MANGOLIKESYOU")
//	public void LOY004_Registro_y_vale_MANGOLIKESYOU() throws Exception {
//		InputParamsMango inputParamsSuite = (InputParamsMango)TestMaker.getTestCase().getInputParamsSuite();
//		if (inputParamsSuite.getTypeAccess()==TypeAccess.Bat) {
//			return; 
//		}
//
//		WebDriver driver = TestMaker.getDriverTestCase();
//		DataCtxShop dCtxSh = getCtxShForTest();
//		Map<String,String> dataRegistro = Registro.registro_e_irdeshopping_sipubli(dCtxSh, VersionRegistroSuite.V2, driver);
//		dCtxSh.userConnected = dataRegistro.get("cfEmail");
//		dCtxSh.passwordUser = dataRegistro.get("cfPass");
//		dCtxSh.userRegistered = true;
//
//		//Crear el vale
//		ValePais vale = FactoryVale.makeWithArticles(
//				Campanya.MANGOLIKESYOU, 
//				PaisShop.España, 
//				10, 
//				"01/01/2000 00:00", "01/01/2200 00:00", false, 
//				Arrays.asList("67004418", "67004418", "67054412"), null);
//		dCtxSh.vale = vale;
//		List<Garment> listArticles = UtilsTestMango.getArticlesForTestDependingVale(dCtxSh, 0);
//
//		DataBag dataBag = new DataBag(); 
//		SecBolsaStpV.altaListaArticulosEnBolsa(listArticles, dataBag, dCtxSh, driver);
//		
//		//Seleccionar el botón comprar y completar el proceso hasta la página de checkout con los métodos de pago
//		FlagsTestCkout FTCkout = new FlagsTestCkout();
//		FTCkout.validaPasarelas = false;  
//		FTCkout.validaPagos = false;
//		FTCkout.emailExist = true; 
//		FTCkout.loyaltyPoints = false;
//		FTCkout.testCodPromocional = true;
//		DataCtxPago dCtxPago = new DataCtxPago(dCtxSh);
//		dCtxPago.setFTCkout(FTCkout);
//		dCtxPago.getDataPedido().setDataBag(dataBag);
//		PagoNavigationsStpV.testFromBolsaToCheckoutMetPago(dCtxSh, dCtxPago, driver); 
//	}
	
	@Test (
		groups={"Loyalty", "Canal:desktop,mobile_App:shop"},
		description="Transferencia de Likes de un cliente a otro")
	public void LOY005_TransferLikes_To_Another_Client() throws Exception {
		DataCtxShop dCtxSh = getCtxShForTest();
		WebDriver driver = TestMaker.getDriverTestCase();
		boolean isEntornoPro = UtilsMangoTest.isEntornoPRO(dCtxSh.appE, driver);
		if (isEntornoPro) {
			return;
		}
		//Nota: la operativa de transferencia de likes no funciona si restando los puntos el emisor se queda < 1500 likes
		int pointsRegalar = 2500;
		dCtxSh.userConnected = UserTest.loy005_receptor.getEmail();
		dCtxSh.passwordUser = UserTest.loy005_receptor.getPassword();
		dCtxSh.userRegistered = true;
		AccesoStpV.oneStep(dCtxSh, false, driver);
		SecMenusUserStpV secMenusUserStpV = SecMenusUserStpV.getNew(dCtxSh.channel, dCtxSh.appE, driver);
		int iniPointsReceptor = secMenusUserStpV.clickMenuMangoLikesYou();
		
		secMenusUserStpV.logoffLogin(UserTest.loy005_emisor.getEmail(), UserTest.loy005_receptor.getPassword());
		int iniPointsEmisor = secMenusUserStpV.clickMenuMangoLikesYou();
		if (iniPointsEmisor < pointsRegalar && !isEntornoPro) {
			ClientApiLoyaltyPointsDev client = new ClientApiLoyaltyPointsDev();
			client.addLoyaltyPoints(UserTest.loy005_emisor, 25000);
			iniPointsEmisor = secMenusUserStpV.clickMenuMangoLikesYou();
		}
		PageRegalarMisLikesStpV pageRegalarMisLikesStpV = 
			PageHomeLikesStpV.getNewInstance(driver).clickButtonRegalarMisLikes();
		
		pageRegalarMisLikesStpV.inputReceptorAndClickContinuar(
			"Regalo a mi usuario ficticio favorito",
			UserTest.loy005_receptor.getEmail());
		pageRegalarMisLikesStpV.inputNumLikesAndClickEnviarRegalo(pointsRegalar);
		
		int finPointsEmisor = secMenusUserStpV.clickMenuMangoLikesYou();
		secMenusUserStpV.logoffLogin(UserTest.loy005_receptor.getEmail(), UserTest.loy005_receptor.getPassword());
		int finPointsReceptor = secMenusUserStpV.clickMenuMangoLikesYou();
		
		DataRegaloPuntos dataPoints = new DataRegaloPuntos();
		dataPoints.setClienteEmisor(UserTest.loy005_emisor.getEmail());
		dataPoints.setClienteReceptor(UserTest.loy005_receptor.getEmail());
		dataPoints.setPointsRegalados(pointsRegalar);
		dataPoints.setIniPointsEmisor(iniPointsEmisor); 
		dataPoints.setIniPointsReceptor(iniPointsReceptor);
		dataPoints.setFinPointsEmisorExpected(iniPointsEmisor - pointsRegalar);
		dataPoints.setFinPointsReceptorExpected(iniPointsReceptor + pointsRegalar);
		dataPoints.setFinPointsEmisorReal(finPointsEmisor);
		dataPoints.setFinPointsReceptorReal(finPointsReceptor);
		PageHomeLikesStpV.checkRegalarPointsOk(dataPoints);
	}
}
