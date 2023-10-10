package com.mng.robotest.testslegacy.steps.navigations.shop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.testng.ITestContext;

import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.domain.InputParamsTM.TypeAccess;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.service.exceptions.NotFoundException;
import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.conf.factories.entities.EgyptCity;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.bolsa.pageobjects.SecBolsa;
import com.mng.robotest.tests.domains.bolsa.steps.SecBolsaSteps;
import com.mng.robotest.tests.domains.compra.beans.ConfigCheckout;
import com.mng.robotest.tests.domains.compra.pageobjects.DataDireccion;
import com.mng.robotest.tests.domains.compra.pageobjects.Page1EnvioCheckoutMobil;
import com.mng.robotest.tests.domains.compra.payments.FactoryPagos;
import com.mng.robotest.tests.domains.compra.payments.PagoSteps;
import com.mng.robotest.tests.domains.compra.steps.CheckoutSteps;
import com.mng.robotest.tests.domains.compra.steps.Page1DktopCheckoutSteps;
import com.mng.robotest.tests.domains.compra.steps.Page1IdentCheckoutSteps;
import com.mng.robotest.tests.domains.compra.steps.Page2IdentCheckoutSteps;
import com.mng.robotest.tests.domains.compra.steps.PageResultPagoSteps;
import com.mng.robotest.tests.domains.compra.steps.PageResultPagoTpvSteps;
import com.mng.robotest.tests.domains.login.pageobjects.PageLogin;
import com.mng.robotest.tests.domains.transversal.acceso.navigations.AccesoFlows;
import com.mng.robotest.tests.repository.productlist.entity.GarmentCatalog.Article;
import com.mng.robotest.tests.repository.usuarios.GestorUsersShop;
import com.mng.robotest.tests.repository.usuarios.UserShop;
import com.mng.robotest.testslegacy.beans.AccesoEmpl;
import com.mng.robotest.testslegacy.beans.Pago;
import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.beans.TypePago;
import com.mng.robotest.testslegacy.datastored.DataPago;
import com.mng.robotest.testslegacy.datastored.DataPedido;
import com.mng.robotest.testslegacy.generic.UtilsMangoTest;
import com.mng.robotest.testslegacy.generic.beans.ValeDiscount;
import com.mng.robotest.testslegacy.pageobject.shop.modales.ModalCambioPais;
import com.mng.robotest.testslegacy.utils.UtilsTest;

import static com.mng.robotest.tests.domains.compra.pageobjects.DataDireccion.DataDirType.*;
import static com.mng.robotest.testslegacy.data.PaisShop.ESPANA;

public class CheckoutFlow extends StepBase {

	public enum From { PREHOME, BOLSA, IDENTIFICATION, CHECKOUT, METODOSPAGO }
	
	private final DataPago dataPago;
	private final Pago pago;
	private final EgyptCity egyptCity;
	private final List<Pais> finalCountrys;
	private final List<Article> listArticles;
	private final Pais pais = dataTest.getPais();
	
	private final ValeDiscount valeTest = new ValeDiscount("TEST", 10, "EXTRA SOBRE LOS ARTÍCULOS");
	private final SecBolsaSteps secBolsaSteps = new SecBolsaSteps();
	private final CheckoutSteps pageCheckoutWrapperSteps = new CheckoutSteps();
	
	private static final String TAG_LOGIN_OR_LOGOFF = "@TagLoginOfLogoff";
	
	private CheckoutFlow (
			DataPago dataPago, 
			Pago pago, 
			List<Article> listArticles, 
			List<Pais> finalCountrys,
			EgyptCity egyptCity) {
		this.finalCountrys = finalCountrys;
		this.pago = pago;
		this.egyptCity = egyptCity;
		this.dataPago = dataPago;
		if (listArticles!=null) {
			this.listArticles = listArticles;
		} else {
			this.listArticles = makeListArticles();
		}
	}
	
	public DataPago checkout(From from) throws Exception {
		if (from==From.METODOSPAGO) {
			aceptarCompraDesdeMetodosPago();
			return dataPago;
		}
		if (from==From.PREHOME) {
			testFromPrehomeToBolsa();
		}
		
		if (from==From.BOLSA || from==From.PREHOME) {
			secBolsaSteps.selectButtonComprar();
		}
		
		if (from==From.IDENTIFICATION || from==From.BOLSA || from==From.PREHOME) {
			testFromIdentificationToMetodosPago();
		}
		
		if ((from==From.CHECKOUT || from==From.IDENTIFICATION || from==From.BOLSA || from==From.PREHOME) &&
			(!pais.getListPagosForTest(app, dataPago.getFTCkout().userIsEmployee).isEmpty())) {
			checkMetodosPagos(finalCountrys);
		}
		return dataPago;
	}
	
	private List<Article> makeListArticles() {
		try {
			return UtilsTest.getArticlesForTest(pais, app, 2, driver);
		}
		catch (Exception e) {
			Log4jTM.getLogger().error("Problem retrieving articles for Checkout", e);
			return Arrays.asList();
		}		
	}	
	
	private void testFromPrehomeToBolsa() throws Exception {
		accessShopAndLoginOrLogoff();
		if (dataTest.isUserRegistered()) {
			secBolsaSteps.clear();
			checksDefault();
		}
		secBolsaSteps.altaListaArticulosEnBolsa(listArticles);
	}
	
	private void testFromIdentificationToMetodosPago() {
		if (dataTest.isUserRegistered()) {
			dataPago.getDataPedido().setEmailCheckout(dataTest.getUserConnected());
		} else {
			testFromIdentToCheckoutIni();
		}
		
		test1rstPageCheckout();
		if (isMobile()) {
			pageCheckoutWrapperSteps.goToMetodosPagoMobile();
		}
	}
	
	private void testFromIdentToCheckoutIni() {
		boolean validaCharNoLatinos = (pais!=null && dataTest.getPais().getDireccharnolatinos().check() && app!=AppEcom.votf);
		String emailCheckout = getUserEmail(dataPago.getFTCkout().emailExists); 
		dataPago.getDataPedido().setEmailCheckout(emailCheckout);

		var page1IdentCheckoutSteps = new Page1IdentCheckoutSteps();
		page1IdentCheckoutSteps.inputEmailAndContinue(emailCheckout, dataPago.getFTCkout().emailExists);
		var page2IdentCheckoutSteps = new Page2IdentCheckoutSteps(egyptCity);
		boolean emailOk = page2IdentCheckoutSteps.checkEmail(emailCheckout);
		if (!emailOk) {
			//Existe un problema según el cual en ocasiones no se propaga el email desde la página de identificación
			back();
			page1IdentCheckoutSteps.inputEmailAndContinue(emailCheckout, dataPago.getFTCkout().emailExists);
		}
		
		var datosRegistro =
				page2IdentCheckoutSteps.inputDataPorDefecto(emailCheckout, validaCharNoLatinos);
		
		dataPago.setDatosRegistro(datosRegistro);
		if (validaCharNoLatinos) {
			page2IdentCheckoutSteps.clickContinuarAndExpectAvisoDirecWithNoLatinCharacters();
			datosRegistro = page2IdentCheckoutSteps.inputDataPorDefecto(emailCheckout, false);
			dataPago.setDatosRegistro(datosRegistro);
		}
		
		page2IdentCheckoutSteps.clickContinuar(dataTest.isUserRegistered());
		checksDefault();
		checksGeneric()
			.googleAnalytics()
			.netTraffic().execute();
	}
	
	private void test1rstPageCheckout() {
		if ((dataPago.getFTCkout().checkPromotionalCode || dataPago.getFTCkout().userIsEmployee) && 
			 app!=AppEcom.votf) {
			if (dataPago.getFTCkout().userIsEmployee && ESPANA.isEquals(pais)) {
				testInputCodPromoEmplSpain();
			} else {
				if (dataPago.getFTCkout().checkPromotionalCode) {
					if (isMobile()) {
						new Page1EnvioCheckoutMobil().inputCodigoPromo(valeTest.getCodigoVale());
					} else {
						testValeDescuento();
					}
				}
			}
		}
		
		if (app==AppEcom.votf && dataTest.getCodigoPais().compareTo("001")==0) {
			new Page1DktopCheckoutSteps().stepIntroduceCodigoVendedorVOTF("111111");
		}
		
	}
	
	public void testInputCodPromoEmplSpain() {
		AccesoEmpl accesoEmpl = AccesoEmpl.forSpain(); 
		pageCheckoutWrapperSteps.inputTarjetaEmplEnCodPromo(pais, accesoEmpl);
		pageCheckoutWrapperSteps.inputDataEmplEnPromoAndAccept(accesoEmpl);
	}
	
	private void checkMetodosPagos(List<Pais> paisesDestino) throws Exception {
		try {
			DataPedido dataPedido = dataPago.getDataPedido();
			if (!isMobile()) {
				pageCheckoutWrapperSteps.getPageCheckoutWrapper().getDataPedidoFromCheckout(dataPedido);
			}
				
			if (!dataPago.getFTCkout().chequeRegalo) {
				pageCheckoutWrapperSteps.despliegaYValidaMetodosPago(dataPago.getFTCkout().userIsEmployee);
			}
			if (dataPago.getFTCkout().checkPasarelas) {
				if (pago==null) { 
					validaPasarelasPagoPais();
				} else {
					dataPago.setPago(pago);
					checkPasarelaPago();
				}
			}
				
			//En el caso de españa, después de validar todos los países probamos el botón "CHANGE DETAILS" sobre los países indicados en la lista
			if (dataTest.getCodigoPais().compareTo("001")==0 /*España*/ && paisesDestino!=null && !paisesDestino.isEmpty()) {
				Pais paisChange = null;
				Iterator<Pais> itPaises = paisesDestino.iterator();
				while (itPaises.hasNext()) {
					paisChange = itPaises.next();
					if (app==AppEcom.shop) {
						//Test funcionalidad "Quiero recibir factura"
						pageCheckoutWrapperSteps.clickSolicitarFactura();
						var dataDirFactura = new DataDireccion();
						dataDirFactura.put(NIF, "76367949Z");
						dataDirFactura.put(NAME, "Carolina");
						dataDirFactura.put(APELLIDOS, "Rancaño Pérez");
						dataDirFactura.put(CODPOSTAL, "08720");
						dataDirFactura.put(DIRECCION, "c./ mossen trens nº6 5º1ª");
						dataDirFactura.put(EMAIL, "voyadominarelmundo@gmail.com");
						dataDirFactura.put(TELEFONO, "665015122");
						dataDirFactura.put(POBLACION, "PEREPAU");
						new CheckoutSteps().getModalDirecFacturaSteps()
							.inputDataAndActualizar(dataDirFactura);
					}
					
					if (app!=AppEcom.votf) {
						//Test funcionalidad "Cambio dirección de envío"
						pageCheckoutWrapperSteps.clickEditarDirecEnvio();
						var dataDirEnvio = new DataDireccion();
						dataDirEnvio.put(CODIGOPAIS, paisChange.getCodigoPais());
						dataDirEnvio.put(CODPOSTAL, paisChange.getCodpos());					
						dataDirEnvio.put(NAME, "Jorge");
						dataDirEnvio.put(APELLIDOS, "Muñoz Martínez");
						dataDirEnvio.put(DIRECCION, "c./ mossen trens nº6 5º1ª");
						UserShop userShop = GestorUsersShop.getUser();
						dataDirEnvio.put(EMAIL, userShop.user);
						dataDirEnvio.put(TELEFONO, "665015122");
						pageCheckoutWrapperSteps.getModalDirecEnvioSteps().inputDataAndActualizar(dataDirEnvio);
						pageCheckoutWrapperSteps.getModalAvisoCambioPaisSteps().clickConfirmar(paisChange);
						dataTest.setPais(paisChange);
						pageCheckoutWrapperSteps.validaMetodosPagoDisponibles(dataPago.getFTCkout().userIsEmployee);
					}
				}
			}
		}
		catch (Exception e) {
			Log4jTM.getLogger().warn("Problem validating Payments methods of country {} ",  dataTest.getPais().getNombrePais(), e);
			throw e; 
		}
	}
	
	private void testValeDescuento() {
		var page1 = new Page1DktopCheckoutSteps();
		page1.inputValeDescuento(valeTest);
	}
	
	private void testPagoFromCheckoutToEnd(Pago pagoToTest) throws Exception {
		DataPedido dataPedido = dataPago.getDataPedido();
		dataPedido.setPago(pagoToTest);
		dataPedido.setResejecucion(com.github.jorge2m.testmaker.conf.State.Nok);
		
		PagoSteps pagoSteps = FactoryPagos.makePagoSteps(dataPago);
		boolean execPay = iCanExecPago(pagoSteps);
		pagoSteps.startPayment(execPay);
		dataPedido = dataPago.getDataPedido();
		if (execPay) {
			var pageResultPagoSteps = new PageResultPagoSteps();
			if (dataPago.getFTCkout().stressMode) {
				pageResultPagoSteps.checkUrl(10);
			}
			else {
				if (pagoToTest.getTypePago()!=TypePago.TPV_VOTF) {
					pageResultPagoSteps.validateIsPageOk(dataPago);
					if (!isMobile() && 
						!dataPago.getFTCkout().chequeRegalo &&
						dataPago.getFTCkout().checkMisCompras) {
						pageResultPagoSteps.selectLinkMisComprasAndValidateCompra(dataPago);
					}
				} else {
					new PageResultPagoTpvSteps().validateIsPageOk(dataPedido, dataTest.getCodigoPais());
				}
				
				//Almacenamos el pedido en el contexto para la futura validación en Manto
				pagoSteps.storePedidoForMantoAndResetData();
				checksDefault();
				checksGeneric()
					.googleAnalytics()
					.netTraffic().execute();
			}
		}
	}
	
	@Step (
		description="Nos posicionamos en la página inicial", 
		expected="La acción se ejecuta correctamente")
	private void fluxQuickInitToCheckout() throws Exception {
		DataPedido dataPedido = dataPago.getDataPedido();
		new UtilsMangoTest().goToPaginaInicio();
		
		//(en Chrome, cuando existe paralelización en ocasiones se pierden las cookies cuando se completa un pago con pasarela externa)
		actionsWhenSessionLoss(); 
		
		secBolsaSteps.altaArticlosConColores(1);
		secBolsaSteps.selectButtonComprar();
		testFromIdentificationToMetodosPago();
		if (!isMobile()) {
			pageCheckoutWrapperSteps.getPageCheckoutWrapper().getDataPedidoFromCheckout(dataPedido);
		}
	}	
	
	private void actionsWhenSessionLoss() {
		new ModalCambioPais().closeModalIfVisible();
		new AccesoFlows().cambioPaisFromHomeIfNeeded(pais, dataTest.getIdioma());
	}
	
	private void validaPasarelasPagoPais() throws Exception {
		List<Pago> listPagosToTest = getListPagosToTest(dataPago.getFTCkout().userIsEmployee);
		for (Iterator<Pago> it = listPagosToTest.iterator(); it.hasNext(); ) {
			Pago pagoToTest = it.next();
			dataPago.setPago(pagoToTest);
			String urlPagChekoutToReturn = driver.getCurrentUrl();
			checkPasarelaPago();
			if (it.hasNext()) {
				if (!dataPago.isPaymentExecuted(pagoToTest)) {
					pageCheckoutWrapperSteps.getPageCheckoutWrapper().backPageMetodosPagos(urlPagChekoutToReturn);
				} else {
					fluxQuickInitToCheckout();
				}
			}
		}
	}
	
	private List<Pago> getListPagosToTest(boolean isEmpl) {
		List<Pago> listPagosToTest = new ArrayList<>();
		ITestContext ctx = getTestCase().getTestRunContext();
		List<Pago> listPagosPais = pais.getListPagosForTest(app, isEmpl);
		for (Pago pagoPais : listPagosPais) {
			if (pagoPais.isNeededTestPasarelaDependingFilter(channel, app, ctx)) {
				listPagosToTest.add(pagoPais);
			}
		}
		return listPagosToTest;
	}
	
	private TestCaseTM getTestCase() throws NotFoundException {
		Optional<TestCaseTM> testCaseOpt = TestMaker.getTestCase();
		if (!testCaseOpt.isPresent()) {
		  throw new NotFoundException("Not found TestCase");
		}
		return testCaseOpt.get();
	}

	private void checkPasarelaPago() throws Exception {
		DataPedido dataPedido = dataPago.getDataPedido(); 
		Pago pagoPais = dataPedido.getPago();
		try {
			if (!isMobile()) {
				pageCheckoutWrapperSteps.getPageCheckoutWrapper().getDataPedidoFromCheckout(dataPedido);
			}
			testPagoFromCheckoutToEnd(pagoPais);
			updateInfoExecutionSuite(dataPedido.getCodpedido());
		}
		catch (Exception e) {
			Log4jTM.getLogger().warn("Problem checking Payment {} from country {}", pagoPais.getNombre(), pais.getNombrePais(), e);
		}
	}
	
	private void updateInfoExecutionSuite(String codigoPedido) {
		System.out.println("Compra realizada con código de pedido: " + codigoPedido);	
		String infoExecution = TestMaker.getSuite().getInfoExecution();
		if (infoExecution==null  || "".compareTo(infoExecution)==0) {
			infoExecution=codigoPedido;
		} else {
			infoExecution+="," + codigoPedido;
		}
		TestMaker.getSuite().setInfoExecution(infoExecution);
	}
	
	/**
	 * Pasos para aceptar la compra desde la página inicial de checkout
	 *	 Desktop: simplemente se selecciona el botón "Confirmar Compra"
	 *	 Movil  : se selecciona los botones "Ver resumen" y "Confirmación del pago)
	 */
	private void aceptarCompraDesdeMetodosPago() {
		DataPedido dataPedido = dataPago.getDataPedido();
		dataPedido.setCodtipopago("R");
		if (!isMobile()) {
			pageCheckoutWrapperSteps.getPageCheckoutWrapper().getDataPedidoFromCheckout(dataPedido);
			pageCheckoutWrapperSteps.pasoBotonAceptarCompraDesktop();
		} else {
			pageCheckoutWrapperSteps.getPageCheckoutWrapper().getDataPedidoFromCheckout(dataPedido);
			pageCheckoutWrapperSteps.pasoBotonConfirmarPagoCheckout3Mobil();
		}	   
	}
	
	private boolean iCanExecPago(PagoSteps pagoSteps) {
		boolean validaPagos = pagoSteps.dataPago.getFTCkout().checkPagos;
		Pago pagoPais = pagoSteps.dataPago.getDataPedido().getPago();
		TypeAccess typeAccess = ((InputParamsMango)TestMaker.getInputParamsSuite()).getTypeAccess();
		return (
			//No estamos en el entorno productivo
			!isPRO() &&
			//No estamos en modo BATCH
			typeAccess!=TypeAccess.Bat &&
			//Está activado el flag de pago en el fichero XML de configuración del test (testNG)
			validaPagos &&  
			//Está activado el test en el pago concreto que figura en el XML de países
			pagoPais.getTestpago()!=null && pagoPais.getTestpago().compareTo("s")==0 &&
			//Está implementado el test a nivel de la confirmación del pago
			pagoSteps.isAvailableExecPay()
		);
	}
	
	@Step (
		description="Acceder a Mango " + TAG_LOGIN_OR_LOGOFF, 
		expected="Se accede a Mango",
		saveNettraffic=SaveWhen.Always,
		saveErrorData=SaveWhen.Always)
	private void accessShopAndLoginOrLogoff() throws Exception {
		if (dataTest.isUserRegistered()) {
			accessShopAndLogin();
		} else {
			accessShopAndLogoff();
		}
	}
	
	private void accessShopAndLogin() throws Exception {
		replaceStepDescription(
				TAG_LOGIN_OR_LOGOFF, "e Identificarse con el usuario <b>" + dataTest.getUserConnected() + "</b>");
		new AccesoFlows().accesoHomeAppWeb(dataPago.getFTCkout().acceptCookies);
		new AccesoFlows().identification(dataTest.getUserConnected(), dataTest.getPasswordUser());	
		new SecBolsa().clearArticulos();
	}
	
	private void accessShopAndLogoff() throws Exception {
		replaceStepDescription(
				TAG_LOGIN_OR_LOGOFF, "(si estamos logados cerramos sesión)");
		new AccesoFlows().accesoHomeAppWeb(dataPago.getFTCkout().acceptCookies);
		new PageLogin().logoff();		
	}
	
	public static class BuilderCheckout {
		private List<Article> listArticles = null;
		private List<Pais> finalCountrys = null;
		private Pago pago = null;
		private EgyptCity egyptCity = null;
		
		private boolean validaPasarelas = false;  
		private boolean validaPagos = false;
		private boolean emailExist = false; 
		private boolean trjGuardada = false;
		private boolean validaPedidosEnManto = false;
		private boolean isEmpl = false;
		private DataPago dataPago;
		
		public BuilderCheckout() {
		}
		public BuilderCheckout(DataPago dataPago) {
			this.dataPago = dataPago;
		}
		
		public BuilderCheckout listArticles(List<Article> listArticles) {
			this.listArticles = listArticles;
			return this;
		}
		public BuilderCheckout finalCountrys(List<Pais> finalCountrys) {
			this.finalCountrys = finalCountrys;
			return this;
		}
		public BuilderCheckout pago(Pago pago) {
			this.pago = pago;
			return this;
		}
		public BuilderCheckout egyptCity(EgyptCity egyptCity) {
			this.egyptCity = egyptCity;
			return this;
		}
		
		public BuilderCheckout validaPasarelas(boolean flag) {
			this.validaPasarelas = flag;
			return this;
		}
		public BuilderCheckout validaPagos(boolean flag) {
			this.validaPagos = flag;
			return this;
		}
		public BuilderCheckout emailExist(boolean flag) {
			this.emailExist = flag;
			return this;
		}
		public BuilderCheckout trjGuardada(boolean flag) {
			this.trjGuardada = flag;
			return this;
		}
		public BuilderCheckout validaPedidosEnManto(boolean flag) {
			this.validaPedidosEnManto = flag;
			return this;
		}
		public BuilderCheckout isEmpl(boolean flag) {
			this.isEmpl = flag;
			return this;
		}
		
		private DataPago getDataPago() {
			if (dataPago!=null) {
				return dataPago;
			}
			var dPago = new DataPago(
				ConfigCheckout.config()
					.checkPasarelas(validaPasarelas)
					.checkPagos(validaPagos)
					.emaiExists(emailExist)
					.checkSavedCard(trjGuardada)
					.checkManto(validaPedidosEnManto)
					.userIsEmployee(isEmpl).build());
			
			if (pago!=null) {
				dPago.setPago(pago);
			}
			return dPago;
		}
		
		public CheckoutFlow build() {
			return new CheckoutFlow(
					getDataPago(), 
					pago, 
					listArticles, 
					finalCountrys, 
					egyptCity);
		}
		public void exec(From from) throws Exception {
			build().checkout(from);
		}
	}
	
	private boolean isMobile() {
		return channel==Channel.mobile;
	}		
}