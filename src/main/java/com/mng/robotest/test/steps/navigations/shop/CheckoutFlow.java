package com.mng.robotest.test.steps.navigations.shop;

import static com.mng.robotest.test.data.PaisShop.ESPANA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.testng.ITestContext;

import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.domain.InputParamsTM.TypeAccess;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.compra.beans.ConfigCheckout;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.beans.AccesoEmpl;
import com.mng.robotest.test.beans.Pago;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.beans.Pago.TypePago;
import com.mng.robotest.test.datastored.DataBag;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.exceptions.NotFoundException;
import com.mng.robotest.test.factoryes.entities.EgyptCity;
import com.mng.robotest.test.generic.UtilsMangoTest;
import com.mng.robotest.test.generic.beans.ValeDiscount;
import com.mng.robotest.test.getdata.products.data.GarmentCatalog;
import com.mng.robotest.test.pageobject.shop.checkout.DataDireccion;
import com.mng.robotest.test.pageobject.shop.checkout.Page1EnvioCheckoutMobil;
import com.mng.robotest.test.pageobject.shop.checkout.DataDireccion.DataDirType;
import com.mng.robotest.test.pageobject.shop.identificacion.PageIdentificacion;
import com.mng.robotest.test.pageobject.shop.modales.ModalCambioPais;
import com.mng.robotest.test.steps.shop.AllPagesSteps;
import com.mng.robotest.test.steps.shop.SecBolsaSteps;
import com.mng.robotest.test.steps.shop.checkout.Page1DktopCheckoutSteps;
import com.mng.robotest.test.steps.shop.checkout.Page1IdentCheckoutSteps;
import com.mng.robotest.test.steps.shop.checkout.Page2IdentCheckoutSteps;
import com.mng.robotest.test.steps.shop.checkout.PageCheckoutWrapperSteps;
import com.mng.robotest.test.steps.shop.checkout.PageResultPagoSteps;
import com.mng.robotest.test.steps.shop.checkout.PageResultPagoTpvSteps;
import com.mng.robotest.test.steps.shop.checkout.pagosfactory.FactoryPagos;
import com.mng.robotest.test.steps.shop.checkout.pagosfactory.PagoSteps;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks.GenericCheck;
import com.mng.robotest.test.utils.UtilsTest;

public class CheckoutFlow extends StepBase {

	public enum From { PREHOME, BOLSA, IDENTIFICATION, CHECKOUT, METODOSPAGO }
	
	private final DataPago dataPago;
	private final Pago pago;
	private final EgyptCity egyptCity;
	private final List<Pais> finalCountrys;
	private final List<GarmentCatalog> listArticles;
	
	//TODO pendiente definir el texto real asociado al vale TEST
	private final ValeDiscount valeTest = new ValeDiscount("TEST", 10, "EXTRA SOBRE LOS ARTÍCULOS");
	
	private final SecBolsaSteps secBolsaSteps = new SecBolsaSteps();
	private final PageCheckoutWrapperSteps pageCheckoutWrapperSteps = new PageCheckoutWrapperSteps();
	
	private CheckoutFlow (
			DataPago dataPago, 
			Pago pago, 
			List<GarmentCatalog> listArticles, 
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
			secBolsaSteps.selectButtonComprar(dataPago.getDataPedido().getDataBag());
		}
		
		if (from==From.IDENTIFICATION || from==From.BOLSA || from==From.PREHOME) {
			testFromIdentificationToMetodosPago();
		}
		
		if (from==From.CHECKOUT || from==From.IDENTIFICATION || from==From.BOLSA || from==From.PREHOME) {
			if (dataTest.pais.getListPagosForTest(app, dataPago.getFTCkout().userIsEmployee).size() > 0) {
				checkMetodosPagos(finalCountrys);
			}
		}
		return dataPago;
	}
	
	private List<GarmentCatalog> makeListArticles() {
		try {
			return UtilsTest.getArticlesForTest(dataTest.pais, app, 2, driver);
		}
		catch (Exception e) {
			Log4jTM.getLogger().error("Problem retrieving articles for Checkout", e);
			return null;
		}		
	}	
	
	private void testFromPrehomeToBolsa() throws Exception {
		accessShopAndLoginOrLogoff();
		if (dataTest.userRegistered) {
			secBolsaSteps.clear();
			GenericChecks.checkDefault(driver);
		}
	
		DataBag dataBag = dataPago.getDataPedido().getDataBag();
		secBolsaSteps.altaListaArticulosEnBolsa(listArticles, dataBag);
	}
	
	private void testFromIdentificationToMetodosPago() throws Exception {
		if (dataTest.userRegistered) {
			dataPago.getDataPedido().setEmailCheckout(dataTest.userConnected);
		} else {
			testFromIdentToCheckoutIni();
		}
		
		test1rstPageCheckout();
		if (channel==Channel.mobile) {
			boolean isSaldoEnCuenta = dataPago.getFTCkout().storeCredit;
			pageCheckoutWrapperSteps.getPage1CheckoutMobilSteps()
				.clickContinuarToMetodosPago(dataTest.pais, isSaldoEnCuenta);
		}
	}
	
	private void testFromIdentToCheckoutIni() throws Exception {
		boolean validaCharNoLatinos = (dataTest.pais!=null && dataTest.pais.getDireccharnolatinos().check() && app!=AppEcom.votf);
		DataBag dataBag = dataPago.getDataPedido().getDataBag();
		String emailCheckout = UtilsMangoTest.getEmailForCheckout(dataTest.pais, dataPago.getFTCkout().emailExists); 
		dataPago.getDataPedido().setEmailCheckout(emailCheckout);

		Page1IdentCheckoutSteps page1IdentCheckoutSteps = new Page1IdentCheckoutSteps();
		page1IdentCheckoutSteps.inputEmailAndContinue(emailCheckout, dataPago.getFTCkout().emailExists, dataTest.userRegistered, dataTest.pais);
		Page2IdentCheckoutSteps page2IdentCheckoutSteps = new Page2IdentCheckoutSteps(egyptCity);
		boolean emailOk = page2IdentCheckoutSteps.checkEmail(emailCheckout);
		if (!emailOk) {
			//Existe un problema según el cual en ocasiones no se propaga el email desde la página de identificación
			AllPagesSteps.backNagegador(driver);
			page1IdentCheckoutSteps.inputEmailAndContinue(emailCheckout, dataPago.getFTCkout().emailExists, dataTest.userRegistered, dataTest.pais);
		}
		
		Map<String, String> datosRegistro;
		datosRegistro = page2IdentCheckoutSteps.inputDataPorDefecto(emailCheckout, validaCharNoLatinos);
		
		dataPago.setDatosRegistro(datosRegistro);
		if (validaCharNoLatinos) {
			page2IdentCheckoutSteps.clickContinuarAndExpectAvisoDirecWithNoLatinCharacters();
			datosRegistro = page2IdentCheckoutSteps.inputDataPorDefecto(emailCheckout, false);
		}
		
		page2IdentCheckoutSteps.clickContinuar(dataTest.userRegistered, dataBag);
		GenericChecks.checkDefault(driver);
		GenericChecks.from(Arrays.asList(
				GenericCheck.GoogleAnalytics, 
				GenericCheck.NetTraffic)).checks(driver);
	}
	
	private void test1rstPageCheckout() throws Exception {
		if ((dataPago.getFTCkout().checkPromotionalCode || dataPago.getFTCkout().userIsEmployee) && 
			 app!=AppEcom.votf) {
			DataBag dataBag = dataPago.getDataPedido().getDataBag();	
			if (dataPago.getFTCkout().userIsEmployee && ESPANA.isEquals(dataTest.pais)) {
				testInputCodPromoEmplSpain(dataBag);
			} else {
				if (dataPago.getFTCkout().chequeRegalo) {
					if (channel==Channel.mobile) {
						new Page1EnvioCheckoutMobil().inputCodigoPromo(valeTest.getCodigoVale());
					} else {
						testValeDescuento(dataBag);
					}
				}
			}
		}
		
		if (app==AppEcom.votf && dataTest.pais.getCodigo_pais().compareTo("001")==0) {
			new Page1DktopCheckoutSteps().stepIntroduceCodigoVendedorVOTF("111111");
		}
		
		if (dataPago.getFTCkout().checkLoyaltyPoints) {
			pageCheckoutWrapperSteps.validateBlockLoyalty();
			pageCheckoutWrapperSteps.loyaltyPointsApply();
		}
	}
	
	public void testInputCodPromoEmplSpain(DataBag dataBag) throws Exception {
		AccesoEmpl accesoEmpl = AccesoEmpl.forSpain(); 
		pageCheckoutWrapperSteps.inputTarjetaEmplEnCodPromo(dataTest.pais, accesoEmpl);
		pageCheckoutWrapperSteps.inputDataEmplEnPromoAndAccept(dataBag, accesoEmpl, dataTest.pais, app);
	}
	
	private void checkMetodosPagos(List<Pais> paisesDestino) throws Exception {
		try {
			DataPedido dataPedido = dataPago.getDataPedido();
			if (channel!=Channel.mobile) {
				pageCheckoutWrapperSteps.getPageCheckoutWrapper().getDataPedidoFromCheckout(dataPedido);
			}
				
			if (!dataPago.getFTCkout().chequeRegalo) {
				pageCheckoutWrapperSteps.despliegaYValidaMetodosPago(dataTest.pais, dataPago.getFTCkout().userIsEmployee);
			}
			if (dataPago.getFTCkout().checkPasarelas) {
				if (pago==null) { 
					validaPasarelasPagoPais();
				} else {
					dataPago.getDataPedido().setPago(pago);
					checkPasarelaPago();
				}
			}
				
			//En el caso de españa, después de validar todos los países probamos el botón "CHANGE DETAILS" sobre los países indicados en la lista
			if (dataTest.pais.getCodigo_pais().compareTo("001")==0 /*España*/ && paisesDestino!=null && paisesDestino.size()>0) {
				Pais paisChange = null;
				Iterator<Pais> itPaises = paisesDestino.iterator();
				while (itPaises.hasNext()) {
					paisChange = itPaises.next();
					if (app==AppEcom.shop) {
						//Test funcionalidad "Quiero recibir factura"
						pageCheckoutWrapperSteps.clickSolicitarFactura();
						DataDireccion dataDirFactura = new DataDireccion();
						dataDirFactura.put(DataDirType.nif, "76367949Z");
						dataDirFactura.put(DataDirType.name, "Carolina");
						dataDirFactura.put(DataDirType.apellidos, "Rancaño Pérez");
						dataDirFactura.put(DataDirType.codpostal, "08720");
						dataDirFactura.put(DataDirType.direccion, "c./ mossen trens nº6 5º1ª");
						dataDirFactura.put(DataDirType.email, "crp1974@hotmail.com");
						dataDirFactura.put(DataDirType.telefono, "665015122");
						dataDirFactura.put(DataDirType.poblacion, "PEREPAU");
						new PageCheckoutWrapperSteps().getModalDirecFacturaSteps()
							.inputDataAndActualizar(dataDirFactura);
					}
					
					if (app!=AppEcom.votf) {
						//Test funcionalidad "Cambio dirección de envío"
						pageCheckoutWrapperSteps.clickEditarDirecEnvio();
						DataDireccion dataDirEnvio = new DataDireccion();
						dataDirEnvio.put(DataDirType.codigoPais, paisChange.getCodigo_pais());
						dataDirEnvio.put(DataDirType.codpostal, paisChange.getCodpos());					
						dataDirEnvio.put(DataDirType.name, "Jorge");
						dataDirEnvio.put(DataDirType.apellidos, "Muñoz Martínez");
						dataDirEnvio.put(DataDirType.direccion, "c./ mossen trens nº6 5º1ª");
						dataDirEnvio.put(DataDirType.email, "jorge.munoz.sge@mango.com");
						dataDirEnvio.put(DataDirType.telefono, "665015122");
						pageCheckoutWrapperSteps.getModalDirecEnvioSteps().inputDataAndActualizar(dataDirEnvio);
						pageCheckoutWrapperSteps.getModalAvisoCambioPaisSteps().clickConfirmar(paisChange);
						pageCheckoutWrapperSteps.validaMetodosPagoDisponibles(paisChange, dataPago.getFTCkout().userIsEmployee);
					}
				}
			}
		}
		catch (Exception e) {
			Log4jTM.getLogger().warn("Problem validating Payments methods of country {} ",  dataTest.pais.getNombre_pais(), e);
			throw e; 
		}
	}
	
	private void testValeDescuento(DataBag dataBag) throws Exception {
		Page1DktopCheckoutSteps page1 = new Page1DktopCheckoutSteps();
		if ("".compareTo(valeTest.getTextoCheckout())!=0) {
			page1.checkIsVisibleTextVale(valeTest);
		}
		page1.inputValeDescuento(valeTest, dataBag);
	}
	
	private void testPagoFromCheckoutToEnd(Pago pagoToTest) throws Exception {
		DataPedido dataPedido = dataPago.getDataPedido();
		dataPedido.setPago(pagoToTest);
		dataPedido.setResejecucion(com.github.jorge2m.testmaker.conf.State.Nok);
		
		PagoSteps pagoSteps = FactoryPagos.makePagoSteps(dataPago);
		boolean execPay = iCanExecPago(pagoSteps);
		pagoSteps.testPagoFromCheckout(execPay);
		dataPedido = dataPago.getDataPedido();
		if (execPay) {
			PageResultPagoSteps pageResultPagoSteps = new PageResultPagoSteps();
			if (dataPago.getFTCkout().stressMode) {
				pageResultPagoSteps.checkUrl(10);
			}
			else {
				if (pagoToTest.getTypePago()!=TypePago.TpvVotf) {
					pageResultPagoSteps.validateIsPageOk(dataPago);
					if (channel!=Channel.mobile && !dataPago.getFTCkout().chequeRegalo) {
						if (dataPago.getFTCkout().checkMisCompras) {
							pageResultPagoSteps.selectLinkMisComprasAndValidateCompra(dataPago);
						}
//						} else {
//							pageResultPagoSteps.selectLinkPedidoAndValidatePedido(dataPedido);
//						}
					}
				} else {
					PageResultPagoTpvSteps.validateIsPageOk(dataPedido, dataTest.pais.getCodigo_pais(), driver);
				}
				
				//Almacenamos el pedido en el contexto para la futura validación en Manto
				pagoSteps.storePedidoForMantoAndResetData();
				GenericChecks.checkDefault(driver);
				GenericChecks.from(Arrays.asList(
						GenericCheck.GoogleAnalytics,
						GenericCheck.NetTraffic)).checks(driver);
			}
		}
	}
	
	@Step (
		description="Nos posicionamos en la página inicial", 
		expected="La acción se ejecuta correctamente")
	private void fluxQuickInitToCheckout() throws Exception {
		DataPedido dataPedido = dataPago.getDataPedido();
		DataBag dataBag = dataPedido.getDataBag();
		new UtilsMangoTest().goToPaginaInicio();
		
		//(en Chrome, cuando existe paralelización en ocasiones se pierden las cookies cuando se completa un pago con pasarela externa)
		actionsWhenSessionLoss();
		
		secBolsaSteps.altaArticlosConColores(1, dataBag);
		secBolsaSteps.selectButtonComprar(dataPago.getDataPedido().getDataBag());
		testFromIdentificationToMetodosPago();
		if (channel!=Channel.mobile) {
			pageCheckoutWrapperSteps.getPageCheckoutWrapper().getDataPedidoFromCheckout(dataPedido);
		}
	}	
	
	private void actionsWhenSessionLoss() throws Exception {
		new ModalCambioPais().closeModalIfVisible();
		new AccesoNavigations().cambioPaisFromHomeIfNeeded(dataTest.pais, dataTest.idioma);
	}
	
	private void validaPasarelasPagoPais() throws Exception {
		List<Pago> listPagosToTest = getListPagosToTest(dataPago.getFTCkout().userIsEmployee);
		for (Iterator<Pago> it = listPagosToTest.iterator(); it.hasNext(); ) {
			Pago pagoToTest = it.next();
			dataPago.getDataPedido().setPago(pagoToTest);
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
		List<Pago> listPagosPais = dataTest.pais.getListPagosForTest(app, isEmpl);
		for (Pago pago : listPagosPais) {
			if (pago.isNeededTestPasarelaDependingFilter(channel, app, ctx)) {
				listPagosToTest.add(pago);
			}
		}
		return listPagosToTest;
	}
	
	private TestCaseTM getTestCase() throws NotFoundException {
		Optional<TestCaseTM> testCaseOpt = TestMaker.getTestCase();
		if (testCaseOpt.isEmpty()) {
		  throw new NotFoundException("Not found TestCase");
		}
		return testCaseOpt.get();
	}

	private void checkPasarelaPago() throws Exception {
		DataPedido dataPedido = dataPago.getDataPedido(); 
		Pago pago = dataPedido.getPago();
		try {
			if (channel!=Channel.mobile) {
				pageCheckoutWrapperSteps.getPageCheckoutWrapper().getDataPedidoFromCheckout(dataPedido);
			}
			testPagoFromCheckoutToEnd(pago);
			updateInfoExecutionSuite(dataPedido.getCodpedido());
		}
		catch (Exception e) {
			Log4jTM.getLogger().warn("Problem checking Payment {} from country {}", pago.getNombre(), dataTest.pais.getNombre_pais(), e);
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
	private void aceptarCompraDesdeMetodosPago() throws Exception {
		DataPedido dataPedido = dataPago.getDataPedido();
		dataPedido.setCodtipopago("R");
		if (channel!=Channel.mobile) {
			pageCheckoutWrapperSteps.getPageCheckoutWrapper().getDataPedidoFromCheckout(dataPedido);
			pageCheckoutWrapperSteps.pasoBotonAceptarCompraDesktop();
		} else {
			//pageCheckoutWrapperSteps.pasoBotonVerResumenCheckout2Mobil(driver);
			pageCheckoutWrapperSteps.getPageCheckoutWrapper().getDataPedidoFromCheckout(dataPedido);
			pageCheckoutWrapperSteps.pasoBotonConfirmarPagoCheckout3Mobil();
		}	   
	}
	
	private boolean iCanExecPago(PagoSteps pagoSteps) {
		boolean validaPagos = pagoSteps.dataPago.getFTCkout().checkPagos;
		Pago pago = pagoSteps.dataPago.getDataPedido().getPago();
		TypeAccess typeAccess = ((InputParamsMango)TestMaker.getInputParamsSuite()).getTypeAccess();
		return (
			//No estamos en el entorno productivo
			!new UtilsMangoTest().isEntornoPRO() &&
			//No estamos en modo BATCH
			typeAccess!=TypeAccess.Bat &&
			//Está activado el flag de pago en el fichero XML de configuración del test (testNG)
			validaPagos &&  
			//Está activado el test en el pago concreto que figura en el XML de países
			pago.getTestpago()!=null && pago.getTestpago().compareTo("s")==0 &&
			//Está implementado el test a nivel de la confirmación del pago
			pagoSteps.isAvailableExecPay
		);
	}
	
	static final String tagLoginOrLogoff = "@TagLoginOfLogoff";
	@Step (
		description="Acceder a Mango " + tagLoginOrLogoff, 
		expected="Se accede a Mango",
		saveNettraffic=SaveWhen.Always)
	private void accessShopAndLoginOrLogoff() throws Exception {
		StepTM StepTestMaker = TestMaker.getCurrentStepInExecution();		
		if (dataTest.userRegistered) {
			StepTestMaker.replaceInDescription(
				tagLoginOrLogoff, "e Identificarse con el usuario <b>" + dataTest.userConnected + "</b>");
			new AccesoNavigations().accesoHomeAppWeb(dataPago.getFTCkout().acceptCookies);
			new PageIdentificacion().login(dataTest.userConnected, dataTest.passwordUser);
		} else {
			StepTestMaker.replaceInDescription(
				tagLoginOrLogoff, "(si estamos logados cerramos sesión)");
			new AccesoNavigations().accesoHomeAppWeb(dataPago.getFTCkout().acceptCookies);
			new PageIdentificacion().logoff();
		}
	}
	
	public static class BuilderCheckout {
		private List<GarmentCatalog> listArticles = null;
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
		
		public BuilderCheckout listArticles(List<GarmentCatalog> listArticles) {
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
			DataPago dataPago = new DataPago(
				ConfigCheckout.config()
					.checkPasarelas(validaPasarelas)
					.checkPagos(validaPagos)
					.emaiExists(emailExist)
					.checkSavedCard(trjGuardada)
					.checkManto(validaPedidosEnManto)
					.userIsEmployee(isEmpl).build());
			
			if (pago!=null) {
				dataPago.getDataPedido().setPago(pago);
			}
			return dataPago;
		}
		
		public CheckoutFlow build() throws Exception {
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
	
}
