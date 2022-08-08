package com.mng.robotest.test.appshop;

import org.testng.annotations.*;
import java.util.Optional;

import com.github.jorge2m.testmaker.domain.InputParamsTM.TypeAccess;
import com.github.jorge2m.testmaker.service.TestMaker;

import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.data.Constantes;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.datastored.DataCheckPedidos;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.datastored.FlagsTestCkout;
import com.mng.robotest.test.datastored.DataCheckPedidos.CheckPedido;
import com.mng.robotest.test.generic.ChequeRegalo;
import com.mng.robotest.test.getdata.products.GetterProducts;
import com.mng.robotest.test.getdata.products.Menu;
import com.mng.robotest.test.getdata.products.ProductFilter.FilterType;
import com.mng.robotest.test.getdata.products.data.GarmentCatalog;
import com.mng.robotest.test.getdata.usuarios.GestorUsersShop;
import com.mng.robotest.test.getdata.usuarios.UserShop;
import com.mng.robotest.test.pageobject.chequeregalo.PageChequeRegaloInputData.Importe;
import com.mng.robotest.test.pageobject.shop.footer.SecFooter.FooterLink;
import com.mng.robotest.test.steps.navigations.manto.PedidoNavigations;
import com.mng.robotest.test.steps.navigations.shop.NavigationsSteps;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.AccesoSteps;
import com.mng.robotest.test.steps.shop.SecBolsaSteps;
import com.mng.robotest.test.steps.shop.SecFooterSteps;
import com.mng.robotest.test.steps.shop.checqueregalo.PageChequeRegaloInputDataSteps;
import com.mng.robotest.test.steps.shop.menus.SecMenusWrapperSteps;
import com.mng.robotest.test.steps.shop.micuenta.PageMiCuentaSteps;
import com.mng.robotest.test.utils.PaisGetter;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets.SecretType;
import com.mng.robotest.test.exceptions.NotFoundException;
import com.mng.robotest.test.utils.UtilsTest;

import static com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.BuilderCheckout;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;


public class Compra {
	
	private static final Pais espana = PaisGetter.get(PaisShop.ESPANA);
	private static final Pais italia = PaisGetter.get(PaisShop.ITALIA);
	private static final Pais francia = PaisGetter.get(PaisShop.FRANCE);
	private static final IdiomaPais castellano = espana.getListIdiomas().get(0);
	private static final IdiomaPais italiano = italia.getListIdiomas().get(0);


	public Compra() {}

	private DataCtxShop getCtxShForTest() throws Exception {
		InputParamsMango inputParamsSuite = (InputParamsMango)TestMaker.getInputParamsSuite();
		DataCtxShop dCtxSh = new DataCtxShop();
		dCtxSh.setAppEcom((AppEcom)inputParamsSuite.getApp());
		dCtxSh.setChannel(inputParamsSuite.getChannel());
		dCtxSh.pais=espana;
		dCtxSh.idioma=castellano;
		return dCtxSh;
	}

	@Test (
		groups={"Compra", "Canal:desktop,mobile_App:shop,outlet"}, alwaysRun=true, priority=2, 
		description="[Usuario registrado][Tarjeta guardada][Productos Home e Intimissimi (Shop)][No aceptación cookies] Compra con descuento empleado. Verificar compra en sección 'Mis compras'") //Lo marcamos con prioridad 2 para dar tiempo a que otro caso de prueba registre la tarjeta 
	public void COM001_Compra_Home_Intimissimi_TrjSaved_Empl() throws Exception {
		WebDriver driver = TestMaker.getDriverTestCase();
		DataCtxShop dCtxSh = getCtxShForTest();
		
		dCtxSh.userRegistered = true;
		dCtxSh.userConnected = "test.performance10@mango.com";
		dCtxSh.passwordUser = GetterSecrets.factory()
			.getCredentials(SecretType.SHOP_PERFORMANCE_USER)
			.getPassword();

		//To checkout
		FlagsTestCkout FTCkout = new FlagsTestCkout();
		FTCkout.validaPasarelas = true;  
		FTCkout.validaPagos = true;
		FTCkout.forceTestMisCompras = true;
		FTCkout.emailExist = true; 
		FTCkout.trjGuardada = true;
		FTCkout.isEmpl = true;
		FTCkout.acceptCookies = false;
		DataCtxPago dCtxPago = new DataCtxPago(dCtxSh);
		dCtxPago.setFTCkout(FTCkout);
		
		dCtxPago = executeCheckoutForCOM001(dCtxPago, dCtxSh, driver);

		//Validación en Manto de los Pedidos (si existen)
		List<CheckPedido> listChecks = Arrays.asList(
			CheckPedido.consultarBolsa, 
			CheckPedido.consultarPedido);
		DataCheckPedidos checksPedidos = DataCheckPedidos.newInstance(dCtxPago.getListPedidos(), listChecks);
		PedidoNavigations.testPedidosEnManto(checksPedidos, dCtxSh.appE, driver);
	}
	
	private DataCtxPago executeCheckoutForCOM001(DataCtxPago dCtxPago, DataCtxShop dCtxSh, WebDriver driver) 
			throws Exception {
		
		if (dCtxSh.appE == AppEcom.outlet) {
			return new BuilderCheckout(dCtxSh, dCtxPago, driver)
					.pago(espana.getPago("VISA"))
					.build()
					.checkout(From.PREHOME);
		} else {
			Optional<List<GarmentCatalog>> articlesHomeOpt = getArticlesHome(dCtxSh, driver);
			if (articlesHomeOpt.isEmpty()) {
				throw new NotFoundException("Home Garment Not Found");
			}
			
			//TODO actualmente no funciona el buscador por referencia de productos Intimissimi (CFIT-1265)
			//confiamos que esté listo el 1-julio-2022
			//cuando esté listo habrá que eliminar el 1er bloque del if
			if (UtilsTest.dateBeforeToday("2022-11-01")) {
				return new BuilderCheckout(dCtxSh, dCtxPago, driver)
						.pago(espana.getPago("VISA"))
						.listArticles(articlesHomeOpt.get().subList(0, 2))
						.build()
						.checkout(From.PREHOME);
			} else {
				Optional<List<GarmentCatalog>> articlesIntimissimiOpt = getArticlesIntimissimi(dCtxSh, driver);
				if (articlesIntimissimiOpt.isEmpty()) {
					throw new NotFoundException("Home Garment Not Found");
				}
				List<GarmentCatalog> listArticles = Arrays.asList(articlesHomeOpt.get().get(0), articlesIntimissimiOpt.get().get(0));
				return new BuilderCheckout(dCtxSh, dCtxPago, driver)
						.pago(espana.getPago("VISA"))
						.listArticles(listArticles)
						.build()
						.checkout(From.PREHOME);
			}
		}
	}
	
	private Optional<List<GarmentCatalog>> getArticlesHome(
			DataCtxShop dCtxSh, WebDriver driver) throws Exception {
		if (dCtxSh.appE==AppEcom.outlet) {
			return Optional.empty();
		}
		
		GetterProducts getterProducts = new GetterProducts.Builder(espana.getCodigo_alf(), dCtxSh.appE, driver)
			.linea(LineaType.home)
			.menusCandidates(Arrays.asList(
					Menu.Albornoces, 
					Menu.Toallas, 
					Menu.Alfombras))
			.build();
		
		return Optional.of(getterProducts.getFiltered(FilterType.Stock));
	}
	
	private Optional<List<GarmentCatalog>> getArticlesIntimissimi(DataCtxShop dCtxSh, WebDriver driver) 
			throws Exception {
		if (dCtxSh.appE==AppEcom.outlet) {
			return Optional.empty();
		}
		
		GetterProducts getterProducts = new GetterProducts.Builder(espana.getCodigo_alf(), dCtxSh.appE, driver)
			.linea(LineaType.she)
			.menusCandidates(Arrays.asList(
					Menu.Sujetadores, 
					Menu.Braguitas, 
					Menu.Lenceria))
			.build();
		
		return Optional.of(getterProducts.getFiltered(FilterType.Stock));
	}

	private enum TypeCheque {Old, New}
	
	@Test (
		groups={"Compra", "Canal:desktop_App:shop"}, alwaysRun=true,
		description="Consulta datos cheque existente y posterior compra Cheque regalo New (España)")
	public void COM004_Cheque_Regalo_New() throws Exception {
		testChequeRegalo(TypeCheque.New);
	}
	
	@Test (
		groups={"Compra", "Canal:desktop_App:shop"}, alwaysRun=true,
		description="Compra cheque regalo Old (Francia)")
	public void COM007_Cheque_Regalo_Old() throws Exception {
		testChequeRegalo(TypeCheque.Old);
	}
	
	private void testChequeRegalo(TypeCheque typeCheque) throws Exception {
		WebDriver driver = TestMaker.getDriverTestCase();
		DataCtxShop dCtxSh = getCtxShForTest();
		UserShop userShop = GestorUsersShop.checkoutBestUserForNewTestCase();
		dCtxSh.userConnected = userShop.user;
		dCtxSh.passwordUser = userShop.password;		
		dCtxSh.userRegistered = true;
		if (typeCheque==TypeCheque.New) {
			dCtxSh.pais = espana;
		} else {
			dCtxSh.pais = francia;
			
			dCtxSh.userConnected = "francia.test@mango.com";
			dCtxSh.passwordUser = GetterSecrets.factory()
				.getCredentials(SecretType.SHOP_FRANCIA_USER)
				.getPassword();
		}

		//Creamos una estructura para ir almacenando los datos del proceso de pagos
		String nTarjeta;
		String cvvTarjeta = "";
		AccesoSteps.oneStep(dCtxSh, false, driver);
		new SecBolsaSteps(dCtxSh).clear();
		SecMenusWrapperSteps secMenusSteps = SecMenusWrapperSteps.getNew(dCtxSh);
		secMenusSteps.seleccionLinea(LineaType.she, null, dCtxSh);
		
		PageChequeRegaloInputDataSteps pageChequeRegaloInputDataSteps = new PageChequeRegaloInputDataSteps(dCtxSh.pais, driver);
		SecFooterSteps secFooterSteps = new SecFooterSteps(dCtxSh.channel, dCtxSh.appE, driver);
		if (typeCheque==TypeCheque.Old) {
			secFooterSteps.clickLinkFooter(FooterLink.CHEQUE_REGALO_OLD, false);
			pageChequeRegaloInputDataSteps.clickQuieroComprarChequeRegalo();
			pageChequeRegaloInputDataSteps.seleccionarCantidades(Importe.euro50);
		} else {
			secFooterSteps.clickLinkFooter(FooterLink.CHEQUE_REGALO, false);
			if(dCtxSh.channel.isDevice()){
				nTarjeta = "100000040043";
				cvvTarjeta = "618";
				pageChequeRegaloInputDataSteps.paginaConsultarSaldo(nTarjeta);
				pageChequeRegaloInputDataSteps.insertCVVConsultaSaldo(cvvTarjeta);
			}
			pageChequeRegaloInputDataSteps.seleccionarCantidades(Importe.euro50);
			pageChequeRegaloInputDataSteps.clickQuieroComprarChequeRegalo();
		}

		ChequeRegalo chequeRegalo = new ChequeRegalo();
		chequeRegalo.setNombre("Jorge");
		chequeRegalo.setApellidos("Muñoz Martínez");
		chequeRegalo.setEmail(Constantes.MAIL_PERSONAL);
		chequeRegalo.setImporte(Importe.euro50);
		chequeRegalo.setMensaje("Te conocía aún antes de haberte formado en el vientre de tu madre");
		pageChequeRegaloInputDataSteps.inputDataAndClickComprar(dCtxSh.channel, dCtxSh.appE, chequeRegalo);

		//Ejecutar el pago
		FlagsTestCkout fTCkout = new FlagsTestCkout();
		fTCkout.validaPasarelas = true;  
		fTCkout.validaPagos = true;
		fTCkout.forceTestMisCompras = true;
		fTCkout.validaPedidosEnManto = true;
		fTCkout.emailExist = false; 
		fTCkout.trjGuardada = false;
		fTCkout.isEmpl = false;
		fTCkout.isChequeRegalo = true;
		DataCtxPago dCtxPago = new DataCtxPago(dCtxSh);
		dCtxPago.setFTCkout(fTCkout);

		dCtxPago = new BuilderCheckout(dCtxSh, dCtxPago, driver)
			.pago(espana.getPago("VISA"))
			.build()
			.checkout(From.IDENTIFICATION);
		
		if (dCtxPago.getFTCkout().validaPedidosEnManto) {
			List<CheckPedido> listChecks = Arrays.asList(
				CheckPedido.consultarBolsa, 
				CheckPedido.consultarPedido,
				CheckPedido.anular); 
			DataCheckPedidos checksPedidos = DataCheckPedidos.newInstance(dCtxPago.getListPedidos(), listChecks);
			PedidoNavigations.testPedidosEnManto(checksPedidos, dCtxSh.appE, driver);
		}
	}
	
	@Test (
		groups={"Compra", "Canal:desktop_App:shop,outlet"}, alwaysRun=true,
		description="[Usuario no registrado] Compra con cambio datos en dirección de envío en checkout")
	public void COM003_Compra_y_CambioPais_Noreg_emailExist() throws Exception {
		WebDriver driver = TestMaker.getDriverTestCase();
		DataCtxShop dCtxSh = getCtxShForTest();
		dCtxSh.userRegistered = false;
		
		//Hasta página de Checkout
		FlagsTestCkout FTCkout = new FlagsTestCkout();
		FTCkout.validaPasarelas = false;  
		FTCkout.validaPagos = false;
		FTCkout.emailExist = true; 
		FTCkout.trjGuardada = false;
		FTCkout.isEmpl = false;
		DataCtxPago dCtxPago = new DataCtxPago(dCtxSh);
		dCtxPago.setFTCkout(FTCkout);
		
		dCtxPago = new BuilderCheckout(dCtxSh, dCtxPago, driver)
			.finalCountrys(Arrays.asList(francia))
			.build()
			.checkout(From.PREHOME);
		
		if (dCtxPago.getFTCkout().validaPedidosEnManto) {
			List<CheckPedido> listChecks = Arrays.asList(
				CheckPedido.consultarBolsa, 
				CheckPedido.consultarPedido);
			DataCheckPedidos checksPedidos = DataCheckPedidos.newInstance(dCtxPago.getListPedidos(), listChecks);
			PedidoNavigations.testPedidosEnManto(checksPedidos, dCtxSh.appE, driver);
		}
	}
			
	@SuppressWarnings("static-access")
	@Test (
		groups={"Compra", "Canal:all_App:all"}, alwaysRun=true,
		description="[Usuario no registrado] Pre-compra. Cierre/Inicio sesión correcto")
	public void COM005_Compra_noReg_emailNoExist() throws Exception {
		WebDriver driver = TestMaker.getDriverTestCase();
		DataCtxShop dCtxSh = getCtxShForTest();
		dCtxSh.userRegistered = false;
		
		//No permitiremos la ejecución diaria de este tipo de checkout porque implica la ejecución 
		//de un registro de usuario con el nuevo email introducido 
		InputParamsMango inputParamsSuite = (InputParamsMango)TestMaker.getInputParamsSuite();
		if (inputParamsSuite.getTypeAccess()!=TypeAccess.Bat) {
			//Hasta página de Checkout
			FlagsTestCkout FTCkout = new FlagsTestCkout();
			FTCkout.validaPasarelas = false;  
			FTCkout.validaPagos = false;
			FTCkout.emailExist = false; 
			FTCkout.trjGuardada = false;
			FTCkout.isEmpl = false;
			DataCtxPago dCtxPago = new DataCtxPago(dCtxSh);
			dCtxPago.setFTCkout(FTCkout);
			
			dCtxPago = new BuilderCheckout(dCtxSh, dCtxPago, driver)
				.build()
				.checkout(From.PREHOME);
					
			//Seleccionamos el logo de Mango (necesitamos acceder a una página con los links del menú superior)
			NavigationsSteps.gotoPortada(dCtxSh, driver);
			
			if (dCtxSh.appE!=AppEcom.votf) {
				//Cerramos sesión y nos volvemos a identificar con los datos del registro
				String usrEmail = dCtxPago.getDatosRegistro().get("cfEmail");
				String password = dCtxPago.getDatosRegistro().get("cfPass");
				SecMenusWrapperSteps secMenusSteps = SecMenusWrapperSteps.getNew(dCtxSh);
				secMenusSteps.getMenusUser().logoffLogin(usrEmail, password);
					
				//Ejecutamos la consulta de Mis datos comprobando que son coherentes con los utilizados en el registro
				PageMiCuentaSteps pageMiCuentaSteps = PageMiCuentaSteps.getNew(dCtxSh.channel, dCtxSh.appE, driver);
				Map<String,String> datosRegistro = dCtxPago.getDatosRegistro();
				datosRegistro.put("cfEmail", usrEmail);
				datosRegistro.put("cfPass", password);
				datosRegistro.put("", "Barcelona");
				datosRegistro.put("provinciaPais", "Barcelona");
				pageMiCuentaSteps.goToMisDatosAndValidateData(datosRegistro, dCtxSh.pais.getCodigo_pais());
			}			
			
			//Validación en Manto de los Pedidos (si existen)
			if (dCtxPago.getFTCkout().validaPedidosEnManto) {
				List<CheckPedido> listChecks = Arrays.asList(
					CheckPedido.consultarBolsa, 
					CheckPedido.consultarPedido);
				DataCheckPedidos checksPedidos = DataCheckPedidos.newInstance(dCtxPago.getListPedidos(), listChecks);
				PedidoNavigations.testPedidosEnManto(checksPedidos, dCtxSh.appE, driver);
			}
		}
	}

	@Test (
		groups={"Compra", "Canal:tablet_App:votf"}, alwaysRun=true,
		description="description=[Usuario no registrado] Test en VOTF compra desde tienda Italia")
	public void COM006_Compra_Francia_Tienda() throws Exception {
		WebDriver driver = TestMaker.getDriverTestCase();
		DataCtxShop dCtxSh = getCtxShForTest();
		dCtxSh.pais=italia;
		dCtxSh.idioma=italiano;   
		dCtxSh.userRegistered = false;
		
		//Hasta página de Checkout
		FlagsTestCkout FTCkout = new FlagsTestCkout();
		FTCkout.validaPasarelas = true;  
		FTCkout.validaPagos = true;
		FTCkout.forceTestMisCompras = true;
		FTCkout.validaPedidosEnManto = true;
		FTCkout.emailExist = true; 
		FTCkout.trjGuardada = false;
		FTCkout.isEmpl = false;
		DataCtxPago dCtxPago = new DataCtxPago(dCtxSh);
		dCtxPago.setFTCkout(FTCkout);
		
		dCtxPago = new BuilderCheckout(dCtxSh, dCtxPago, driver)
			.build()
			.checkout(From.PREHOME);
		
		if (dCtxPago.getFTCkout().validaPedidosEnManto) {
			List<CheckPedido> listChecks = Arrays.asList(
				CheckPedido.consultarBolsa, 
				CheckPedido.consultarPedido);
			DataCheckPedidos checksPedidos = DataCheckPedidos.newInstance(dCtxPago.getListPedidos(), listChecks);
			PedidoNavigations.testPedidosEnManto(checksPedidos, dCtxSh.appE, driver);
		}
	}
	
}