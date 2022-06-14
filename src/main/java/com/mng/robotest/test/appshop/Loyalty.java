package com.mng.robotest.test.appshop;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.datastored.DataBag;
import com.mng.robotest.test.datastored.DataCheckPedidos;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.datastored.FlagsTestCkout;
import com.mng.robotest.test.datastored.DataCheckPedidos.CheckPedido;
import com.mng.robotest.test.generic.UtilsMangoTest;
import com.mng.robotest.test.getdata.loyaltypoints.ClientApiLoyaltyPointsDev;
import com.mng.robotest.test.pageobject.shop.loyalty.PageHomeDonateLikes.ButtonLikes;
import com.mng.robotest.test.pageobject.shop.menus.KeyMenu1rstLevel;
import com.mng.robotest.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test.pageobject.shop.menus.MenuTreeApp;
import com.mng.robotest.test.stpv.navigations.manto.PedidoNavigations;
import com.mng.robotest.test.stpv.navigations.shop.CheckoutFlow;
import com.mng.robotest.test.stpv.navigations.shop.GaleriaNavigationsStpV;
import com.mng.robotest.test.stpv.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.stpv.shop.AccesoStpV;
import com.mng.robotest.test.stpv.shop.loyalty.PageHomeConseguirPor1200LikesStpV;
import com.mng.robotest.test.stpv.shop.loyalty.PageHomeDonateLikesStpV;
import com.mng.robotest.test.stpv.shop.loyalty.PageHomeLikesStpV;
import com.mng.robotest.test.stpv.shop.loyalty.PageRegalarMisLikesStpV;
import com.mng.robotest.test.stpv.shop.loyalty.PageHomeLikesStpV.DataRegaloPuntos;
import com.mng.robotest.test.stpv.shop.menus.SecMenusUserStpV;
import com.mng.robotest.test.stpv.shop.menus.SecMenusWrapperStpV;
import com.mng.robotest.test.utils.PaisGetter;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets.SecretType;


public class Loyalty {
	
	private static final Pais espana = PaisGetter.get(PaisShop.ESPANA);
	private static final IdiomaPais castellano = espana.getListIdiomas().get(0);

	static final String USER_PRO_WITH_LOY_POINTS = "ticket_digital_es@mango.com";

	
	public static enum UserTest {
		LOY001(USER_PRO_WITH_LOY_POINTS, "6051483560048388114", "ES"),
		LOY002("test.performance21@mango.com", "6877377061230042978", "ES"),
		LOY003("test.performance22@mango.com", "6876577027622042923", "ES"),
		LOY005_EMISOR("test.performance23@mango.com", "6875476978997042979", "ES"),
		LOY005_RECEPTOR("test.performance24@mango.com", "6876477022921042981", "ES");
		
		private String email;
		private String contactId;
		private String country;
		private UserTest(String email, String contactId, String country) {
			this.email = email;
			this.contactId = contactId;
			this.country = country;
		}
		
		public String getEmail() {
			return email;
		}
		public String getContactId() {
			return contactId;
		}
		public String getCountry() {
			return country;
		}
	}

	private DataCtxShop getCtxShForTest() throws Exception {
		InputParamsMango inputParamsSuite = (InputParamsMango)TestMaker.getInputParamsSuite();
		DataCtxShop dCtxSh = new DataCtxShop();
		dCtxSh.setAppEcom((AppEcom)inputParamsSuite.getApp());
		dCtxSh.setChannel(inputParamsSuite.getChannel());
		dCtxSh.pais = espana;
		dCtxSh.idioma = castellano;
		return dCtxSh;
	}
	
	@Test (
		groups={"Loyalty", "Canal:desktop,mobile_App:shop"},
		description="Se realiza una compra mediante un usuario loyalty con Likes")
	public void LOY001_Compra_LikesStored() throws Exception {
		WebDriver driver = TestMaker.getDriverTestCase();
		DataCtxShop dCtxSh = getCtxShForTest();
		dCtxSh.userConnected = UserTest.LOY001.getEmail();
		dCtxSh.userRegistered = true;
		dCtxSh.passwordUser = GetterSecrets.factory()
				.getCredentials(SecretType.SHOP_STANDARD_USER)
				.getPassword();

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
		//TODO en estos momentos algo raro le pasa al menú Nuevo que requiere un refresh para funcionar ok
		driver.navigate().refresh();
		
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
			dCtxSh.userConnected = USER_PRO_WITH_LOY_POINTS;
			dCtxSh.passwordUser = GetterSecrets.factory()
					.getCredentials(SecretType.SHOP_STANDARD_USER)
					.getPassword();
		} else {
			dCtxSh.userConnected = UserTest.LOY002.getEmail();
			dCtxSh.passwordUser = GetterSecrets.factory()
					.getCredentials(SecretType.SHOP_PERFORMANCE_USER)
					.getPassword();
		}

		AccesoStpV.oneStep(dCtxSh, false, driver);
		SecMenusUserStpV secMenusUserStpV = SecMenusUserStpV.getNew(dCtxSh.channel, dCtxSh.appE, driver);
		int loyaltyPointsIni = secMenusUserStpV.clickMenuMangoLikesYou();
		if (loyaltyPointsIni < 3000 && !isEntornoPro) {
			ClientApiLoyaltyPointsDev client = new ClientApiLoyaltyPointsDev();
			client.addLoyaltyPoints(UserTest.LOY002, 25000);
			loyaltyPointsIni = secMenusUserStpV.clickMenuMangoLikesYou();
		}
		
		PageHomeLikesStpV pageHomeLikesStpV = PageHomeLikesStpV.getNewInstance(driver);
		pageHomeLikesStpV.clickButtonDonarLikes();
		if (!UtilsMangoTest.isEntornoPRO(dCtxSh.appE, driver)) {
			PageHomeDonateLikesStpV pageHomeDonateLikesStpV = PageHomeDonateLikesStpV.getNew(driver);
			ButtonLikes donateButton = ButtonLikes.Button100likes;
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
			dCtxSh.userConnected = USER_PRO_WITH_LOY_POINTS;
			dCtxSh.passwordUser = GetterSecrets.factory()
					.getCredentials(SecretType.SHOP_STANDARD_USER)
					.getPassword();
		} else {
			dCtxSh.userConnected = UserTest.LOY003.getEmail();
			dCtxSh.passwordUser = GetterSecrets.factory()
					.getCredentials(SecretType.SHOP_PERFORMANCE_USER)
					.getPassword();
		}

		AccesoStpV.oneStep(dCtxSh, false, driver);
		SecMenusUserStpV secMenusUserStpV = SecMenusUserStpV.getNew(dCtxSh.channel, dCtxSh.appE, driver);
		int loyaltyPointsIni = secMenusUserStpV.clickMenuMangoLikesYou();
		if (loyaltyPointsIni < 3000 && !isEntornoPro) {
			ClientApiLoyaltyPointsDev client = new ClientApiLoyaltyPointsDev();
			client.addLoyaltyPoints(UserTest.LOY003, 25000);
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
//		InputParamsMango inputParamsSuite = (InputParamsMango)TestMaker.getInputParamsSuite();
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
//				PaisShop.Espana, 
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
		
		String passwordTestPerformmance = GetterSecrets.factory()
				.getCredentials(SecretType.SHOP_PERFORMANCE_USER)
				.getPassword();
		dCtxSh.userConnected = UserTest.LOY005_RECEPTOR.getEmail();
		dCtxSh.passwordUser = passwordTestPerformmance;
		dCtxSh.userRegistered = true;
		
		AccesoStpV.oneStep(dCtxSh, false, driver);
		SecMenusUserStpV secMenusUserStpV = SecMenusUserStpV.getNew(dCtxSh.channel, dCtxSh.appE, driver);
		int iniPointsReceptor = secMenusUserStpV.clickMenuMangoLikesYou();
		
		secMenusUserStpV.logoffLogin(UserTest.LOY005_EMISOR.getEmail(), passwordTestPerformmance);
		int iniPointsEmisor = secMenusUserStpV.clickMenuMangoLikesYou();
		if (iniPointsEmisor < pointsRegalar && !isEntornoPro) {
			ClientApiLoyaltyPointsDev client = new ClientApiLoyaltyPointsDev();
			client.addLoyaltyPoints(UserTest.LOY005_EMISOR, 25000);
			iniPointsEmisor = secMenusUserStpV.clickMenuMangoLikesYou();
		}
		PageRegalarMisLikesStpV pageRegalarMisLikesStpV = 
			PageHomeLikesStpV.getNewInstance(driver).clickButtonRegalarMisLikes();
		
		pageRegalarMisLikesStpV.inputReceptorAndClickContinuar(
			"Regalo a mi usuario ficticio favorito",
			UserTest.LOY005_RECEPTOR.getEmail());
		pageRegalarMisLikesStpV.inputNumLikesAndClickEnviarRegalo(pointsRegalar);
		
		int finPointsEmisor = secMenusUserStpV.clickMenuMangoLikesYou();
		secMenusUserStpV.logoffLogin(UserTest.LOY005_RECEPTOR.getEmail(), passwordTestPerformmance);
		int finPointsReceptor = secMenusUserStpV.clickMenuMangoLikesYou();
		
		DataRegaloPuntos dataPoints = new DataRegaloPuntos();
		dataPoints.setClienteEmisor(UserTest.LOY005_EMISOR.getEmail());
		dataPoints.setClienteReceptor(UserTest.LOY005_RECEPTOR.getEmail());
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
