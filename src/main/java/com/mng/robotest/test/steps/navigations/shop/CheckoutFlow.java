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
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pago;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.beans.Pago.TypePago;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.datastored.DataBag;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.exceptions.NotFoundException;
import com.mng.robotest.test.factoryes.entities.EgyptCity;
import com.mng.robotest.test.generic.UtilsMangoTest;
import com.mng.robotest.test.generic.beans.ValePais;
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
import com.mng.robotest.test.utils.PaisGetter;
import com.mng.robotest.test.utils.UtilsTest;

public class CheckoutFlow extends StepBase {

	public enum From { PREHOME, BOLSA, IDENTIFICATION, CHECKOUT, METODOSPAGO }
	
	private final DataCtxShop dCtxSh;
	private final DataCtxPago dCtxPago;
	private final Pago pago;
	private final EgyptCity egyptCity;
	private final List<Pais> finalCountrys;
	private final List<GarmentCatalog> listArticles;
	
	private SecBolsaSteps secBolsaSteps;
	private final PageCheckoutWrapperSteps pageCheckoutWrapperSteps;
	
	private CheckoutFlow(
			DataCtxShop dCtxSh, 
			DataCtxPago dCtxPago, 
			Pago pago, 
			List<GarmentCatalog> listArticles, 
			List<Pais> finalCountrys,
			EgyptCity egyptCity) {
		this.finalCountrys = finalCountrys;
		this.listArticles = listArticles;
		this.dCtxSh = dCtxSh;
		this.dCtxPago = dCtxPago;
		this.pago = pago;
		this.egyptCity = egyptCity;
		this.secBolsaSteps = new SecBolsaSteps(dCtxSh);
		this.pageCheckoutWrapperSteps = new PageCheckoutWrapperSteps(dCtxSh.channel, dCtxSh.appE);
	}
	
	public DataCtxPago checkout(From from) throws Exception {
		if (from==From.METODOSPAGO) {
			aceptarCompraDesdeMetodosPago();
			return dCtxPago;
		}
		if (from==From.PREHOME) {
			testFromPrehomeToBolsa();
		}
		
		if (from==From.BOLSA || from==From.PREHOME) {
			secBolsaSteps.selectButtonComprar(dCtxPago.getDataPedido().getDataBag(), dCtxSh);
		}
		
		if (from==From.IDENTIFICATION || from==From.BOLSA || from==From.PREHOME) {
			testFromIdentificationToMetodosPago();
		}
		
		if (from==From.CHECKOUT || from==From.IDENTIFICATION || from==From.BOLSA || from==From.PREHOME) {
			if (dCtxSh.pais.getListPagosForTest(dCtxSh.appE, dCtxPago.getFTCkout().userIsEmployee).size() > 0) {
				checkMetodosPagos(finalCountrys);
			}
		}
		return dCtxPago;
	}
	
	private void testFromPrehomeToBolsa() throws Exception {
		accessShopAndLoginOrLogoff();
		if (dCtxSh.userRegistered) {
			secBolsaSteps.clear();
			GenericChecks.checkDefault(driver);
		}
	
		DataBag dataBag = dCtxPago.getDataPedido().getDataBag();
		secBolsaSteps.altaListaArticulosEnBolsa(listArticles, dataBag);
	}
	
	private void testFromIdentificationToMetodosPago() throws Exception {
		if (dCtxSh.userRegistered) {
			dCtxPago.getDataPedido().setEmailCheckout(dCtxSh.userConnected);
		} else {
			testFromIdentToCheckoutIni();
		}
		
		test1rstPageCheckout();
		if (dCtxSh.channel==Channel.mobile) {
			boolean isSaldoEnCuenta = dCtxPago.getFTCkout().storeCredit;
			pageCheckoutWrapperSteps.getPage1CheckoutMobilSteps()
				.clickContinuarToMetodosPago(dCtxSh, isSaldoEnCuenta);
		}
	}
	
	private void testFromIdentToCheckoutIni() throws Exception {
		boolean validaCharNoLatinos = (dCtxSh.pais!=null && dCtxSh.pais.getDireccharnolatinos().check() && dCtxSh.appE!=AppEcom.votf);
		DataBag dataBag = dCtxPago.getDataPedido().getDataBag();
		String emailCheckout = UtilsMangoTest.getEmailForCheckout(dCtxSh.pais, dCtxPago.getFTCkout().emailExists); 
		dCtxPago.getDataPedido().setEmailCheckout(emailCheckout);

		Page1IdentCheckoutSteps page1IdentCheckoutSteps = new Page1IdentCheckoutSteps();
		page1IdentCheckoutSteps.inputEmailAndContinue(emailCheckout, dCtxPago.getFTCkout().emailExists, dCtxSh.userRegistered, dCtxSh.pais);
		Page2IdentCheckoutSteps page2IdentCheckoutSteps = new Page2IdentCheckoutSteps(dCtxSh.channel, dCtxSh.pais, egyptCity);
		boolean emailOk = page2IdentCheckoutSteps.checkEmail(emailCheckout);
		if (!emailOk) {
			//Existe un problema según el cual en ocasiones no se propaga el email desde la página de identificación
			AllPagesSteps.backNagegador(driver);
			page1IdentCheckoutSteps.inputEmailAndContinue(emailCheckout, dCtxPago.getFTCkout().emailExists, dCtxSh.userRegistered, dCtxSh.pais);
		}
		
		Map<String, String> datosRegistro;
		datosRegistro = page2IdentCheckoutSteps.inputDataPorDefecto(emailCheckout, validaCharNoLatinos);
		
		dCtxPago.setDatosRegistro(datosRegistro);
		if (validaCharNoLatinos) {
			page2IdentCheckoutSteps.clickContinuarAndExpectAvisoDirecWithNoLatinCharacters();
			datosRegistro = page2IdentCheckoutSteps.inputDataPorDefecto(emailCheckout, false);
		}
		
		page2IdentCheckoutSteps.clickContinuar(dCtxSh.userRegistered, dCtxSh.appE, dataBag);
		GenericChecks.checkDefault(driver);
		GenericChecks.from(Arrays.asList(
				GenericCheck.GoogleAnalytics, 
				GenericCheck.NetTraffic)).checks(driver);
	}
	
	private void test1rstPageCheckout() throws Exception {
		if ((dCtxPago.getFTCkout().checkPromotionalCode || dCtxPago.getFTCkout().userIsEmployee) && 
			 dCtxSh.appE!=AppEcom.votf) {
			DataBag dataBag = dCtxPago.getDataPedido().getDataBag();	
			if (dCtxPago.getFTCkout().userIsEmployee && ESPANA.isEquals(dCtxSh.pais)) {
				testInputCodPromoEmplSpain(dataBag);
			} else {
				if (dCtxSh.vale!=null) {
					if (dCtxSh.channel==Channel.mobile) {
						new Page1EnvioCheckoutMobil().inputCodigoPromo(dCtxSh.vale.getCodigoVale());
					} else {
						testValeDescuento(dCtxSh.vale, dataBag);
					}
				}
			}
		}
		
		if (dCtxSh.appE==AppEcom.votf && dCtxSh.pais.getCodigo_pais().compareTo("001")==0) {
			new Page1DktopCheckoutSteps(dCtxSh.channel, dCtxSh.appE).stepIntroduceCodigoVendedorVOTF("111111");
		}
		
		if (dCtxPago.getFTCkout().checkLoyaltyPoints) {
			pageCheckoutWrapperSteps.validateBlockLoyalty();
			pageCheckoutWrapperSteps.loyaltyPointsApply();
		}
	}
	
	public void testInputCodPromoEmplSpain(DataBag dataBag) throws Exception {
		AccesoEmpl accesoEmpl = AccesoEmpl.forSpain(); 
		pageCheckoutWrapperSteps.inputTarjetaEmplEnCodPromo(dCtxSh.pais, accesoEmpl);
		pageCheckoutWrapperSteps.inputDataEmplEnPromoAndAccept(dataBag, accesoEmpl, dCtxSh.pais, dCtxSh.appE);
	}
	
	/**
	 * Función que parte de la página de "Resumen de artículos" y que valida todos los métodos de pago del país
	 */
	@SuppressWarnings("static-access")
	private void checkMetodosPagos(List<Pais> paisesDestino) throws Exception {
		try {
			DataPedido dataPedido = dCtxPago.getDataPedido();
			if (dCtxSh.channel!=Channel.mobile) {
				pageCheckoutWrapperSteps.getPageCheckoutWrapper().getDataPedidoFromCheckout(dataPedido);
			}
				
			if (!dCtxPago.getFTCkout().chequeRegalo) {
				pageCheckoutWrapperSteps.despliegaYValidaMetodosPago(dCtxSh.pais, dCtxPago.getFTCkout().userIsEmployee);
			}
			if (dCtxPago.getFTCkout().checkPasarelas) {
				if (pago==null) { 
					validaPasarelasPagoPais();
				} else {
					dCtxPago.getDataPedido().setPago(pago);
					checkPasarelaPago();
				}
			}
				
			//En el caso de españa, después de validar todos los países probamos el botón "CHANGE DETAILS" sobre los países indicados en la lista
			if (dCtxSh.pais.getCodigo_pais().compareTo("001")==0 /*España*/ && paisesDestino!=null && paisesDestino.size()>0) {
				Pais paisChange = null;
				Iterator<Pais> itPaises = paisesDestino.iterator();
				while (itPaises.hasNext()) {
					paisChange = itPaises.next();
					if (dCtxSh.appE==AppEcom.shop) {
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
						new PageCheckoutWrapperSteps(dCtxSh.channel, dCtxSh.appE).getModalDirecFacturaSteps()
							.inputDataAndActualizar(dataDirFactura);
					}
					
					if (dCtxSh.appE!=AppEcom.votf) {
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
						pageCheckoutWrapperSteps.validaMetodosPagoDisponibles(paisChange, dCtxPago.getFTCkout().userIsEmployee);
					}
				}
			}
		}
		catch (Exception e) {
			Log4jTM.getLogger().warn("Problem validating Payments methods of country {} ",  dCtxSh.pais.getNombre_pais(), e);
			throw e; 
		}
	}
	
	private void testValeDescuento(ValePais vale, DataBag dataBag) throws Exception {
		Page1DktopCheckoutSteps page1 = new Page1DktopCheckoutSteps(dCtxSh.channel, dCtxSh.appE);
		if ("".compareTo(vale.getTextoCheckout())!=0) {
			if (vale.isValid()) {
				page1.checkIsVisibleTextVale(vale);
			} else {
				page1.checkIsNotVisibleTextVale(vale);
			}
		}
		page1.inputValeDescuento(vale, dataBag);
	}
	
	private void testPagoFromCheckoutToEnd(Pago pagoToTest) throws Exception {
		DataPedido dataPedido = dCtxPago.getDataPedido();
		dataPedido.setPago(pagoToTest);
		dataPedido.setResejecucion(com.github.jorge2m.testmaker.conf.State.Nok);
		
		//Obtenemos el objeto PagoSteps específico según el TypePago y ejecutamos el test 
		PagoSteps pagoSteps = FactoryPagos.makePagoSteps(dCtxSh, dCtxPago, driver);
		boolean execPay = iCanExecPago(pagoSteps);
		pagoSteps.testPagoFromCheckout(execPay);
		dataPedido = dCtxPago.getDataPedido();
		if (execPay) {
			PageResultPagoSteps pageResultPagoSteps = new PageResultPagoSteps(pagoToTest.getTypePago(), dCtxSh.channel);
			if (dCtxPago.getFTCkout().stressMode) {
				pageResultPagoSteps.checkUrl(10);
			}
			else {
				if (pagoToTest.getTypePago()!=TypePago.TpvVotf) {
					pageResultPagoSteps.validateIsPageOk(dCtxPago, dCtxSh);
					if (dCtxSh.channel!=Channel.mobile && !dCtxPago.getFTCkout().chequeRegalo) {
						if (dCtxPago.getFTCkout().checkMisCompras) {
							pageResultPagoSteps.selectLinkMisComprasAndValidateCompra(dCtxPago, dCtxSh);
						}
//						} else {
//							pageResultPagoSteps.selectLinkPedidoAndValidatePedido(dataPedido);
//						}
					}
				} else {
					PageResultPagoTpvSteps.validateIsPageOk(dataPedido, dCtxSh.pais.getCodigo_pais(), driver);
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
	
//	private boolean testMisCompras() {
//		return (
//			(dCtxSh.appE!=AppEcom.outlet) ||
//			 dCtxPago.getFTCkout().forceTestMisCompras
//		);
//	}
	
	@Step (
		description="Nos posicionamos en la página inicial", 
		expected="La acción se ejecuta correctamente")
	private void fluxQuickInitToCheckout() throws Exception {
		DataPedido dataPedido = dCtxPago.getDataPedido();
		DataBag dataBag = dataPedido.getDataBag();
		UtilsMangoTest.goToPaginaInicio(dCtxSh.channel, dCtxSh.appE, driver);
		
		//(en Chrome, cuando existe paralelización en ocasiones se pierden las cookies cuando se completa un pago con pasarela externa)
		actionsWhenSessionLoss();
		
		secBolsaSteps.altaArticlosConColores(1, dataBag);
		secBolsaSteps.selectButtonComprar(dCtxPago.getDataPedido().getDataBag(), dCtxSh);
		testFromIdentificationToMetodosPago();
		if (dCtxSh.channel!=Channel.mobile) {
			pageCheckoutWrapperSteps.getPageCheckoutWrapper().getDataPedidoFromCheckout(dataPedido);
		}
	}	
	
	private void actionsWhenSessionLoss() throws Exception {
		new ModalCambioPais().closeModalIfVisible();
		AccesoNavigations.cambioPaisFromHomeIfNeeded(dCtxSh, driver);
	}
	
	private void validaPasarelasPagoPais() throws Exception {
		List<Pago> listPagosToTest = getListPagosToTest(dCtxPago.getFTCkout().userIsEmployee);
		for (Iterator<Pago> it = listPagosToTest.iterator(); it.hasNext(); ) {
			Pago pagoToTest = it.next();
			dCtxPago.getDataPedido().setPago(pagoToTest);
			String urlPagChekoutToReturn = driver.getCurrentUrl();
			checkPasarelaPago();
			if (it.hasNext()) {
				if (!dCtxPago.isPaymentExecuted(pagoToTest)) {
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
		List<Pago> listPagosPais = dCtxSh.pais.getListPagosForTest(dCtxSh.appE, isEmpl);
		for (Pago pago : listPagosPais) {
			if (pago.isNeededTestPasarelaDependingFilter(dCtxSh.channel, dCtxSh.appE, ctx)) {
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
		DataPedido dataPedido = dCtxPago.getDataPedido(); 
		Pago pago = dataPedido.getPago();
		try {
			if (dCtxSh.channel!=Channel.mobile) {
				pageCheckoutWrapperSteps.getPageCheckoutWrapper().getDataPedidoFromCheckout(dataPedido);
			}
			testPagoFromCheckoutToEnd(pago);
			updateInfoExecutionSuite(dataPedido.getCodpedido());
		}
		catch (Exception e) {
			Log4jTM.getLogger().warn("Problem checking Payment {} from country {}", pago.getNombre(), dCtxSh.pais.getNombre_pais(), e);
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
		DataPedido dataPedido = dCtxPago.getDataPedido();
		dataPedido.setCodtipopago("R");
		if (dCtxSh.channel!=Channel.mobile) {
			pageCheckoutWrapperSteps.getPageCheckoutWrapper().getDataPedidoFromCheckout(dataPedido);
			pageCheckoutWrapperSteps.pasoBotonAceptarCompraDesktop();
		} else {
			//pageCheckoutWrapperSteps.pasoBotonVerResumenCheckout2Mobil(driver);
			pageCheckoutWrapperSteps.getPageCheckoutWrapper().getDataPedidoFromCheckout(dataPedido);
			pageCheckoutWrapperSteps.pasoBotonConfirmarPagoCheckout3Mobil();
		}	   
	}
	
	private boolean iCanExecPago(PagoSteps pagoSteps) {
		boolean validaPagos = pagoSteps.dCtxPago.getFTCkout().checkPagos;
		Pago pago = pagoSteps.dCtxPago.getDataPedido().getPago();
		TypeAccess typeAccess = ((InputParamsMango)TestMaker.getInputParamsSuite()).getTypeAccess();
		return (
			//No estamos en el entorno productivo
			!UtilsMangoTest.isEntornoPRO(dCtxSh.appE, driver) &&
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
		if (dCtxSh.userRegistered) {
			StepTestMaker.replaceInDescription(
				tagLoginOrLogoff, "e Identificarse con el usuario <b>" + dCtxSh.userConnected + "</b>");
		} else {
			StepTestMaker.replaceInDescription(
				tagLoginOrLogoff, "(si estamos logados cerramos sesión)");
		}
		
		AccesoNavigations.accesoHomeAppWeb(dCtxSh, dCtxPago.getFTCkout().acceptCookies, driver);
		new PageIdentificacion().loginOrLogoff(dCtxSh);
	}
	
	public static class BuilderCheckout {
		private final Channel channel;
		private final AppEcom app;
		private String user = "";
		private String password = "";
		private Pais country = PaisGetter.get(PaisShop.ESPANA);
		private IdiomaPais idioma = country.getListIdiomas().get(0);
		private ValePais vale = null;
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
		private DataCtxPago dCtxPago = null;
		
		public BuilderCheckout(Channel channel, AppEcom app) {
			this.channel = channel;
			this.app = app;
		}
		public BuilderCheckout(DataCtxShop dCtxSh, DataCtxPago dCtxPago) {
			this.channel = dCtxSh.channel;
			this.app = dCtxSh.appE;
			this.user = dCtxSh.userConnected;
			this.password = dCtxSh.passwordUser;
			this.country = dCtxSh.pais;
			this.idioma = dCtxSh.idioma;
			this.vale = dCtxSh.vale;
			this.dCtxPago = dCtxPago;
		}
		
		public BuilderCheckout user(String user) {
			this.user = user;
			return this;
		}
		public BuilderCheckout password(String password) {
			this.password = password;
			return this;
		}
		public BuilderCheckout country(Pais country) {
			this.country = country;
			this.idioma = country.getListIdiomas().get(0);
			return this;
		}
		public BuilderCheckout vale(ValePais vale) {
			this.vale = vale;
			return this;
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
		
		private DataCtxPago getDataCtxPago() {
			if (dCtxPago!=null) {
				return dCtxPago;
			}
			ConfigCheckout configCheckout = ConfigCheckout.config()
					.checkPasarelas(validaPasarelas)
					.checkPagos(validaPagos)
					.emaiExists(emailExist)
					.checkSavedCard(trjGuardada)
					.checkManto(validaPedidosEnManto)
					.userIsEmployee(isEmpl).build();
			
			DataCtxPago dCtxPago = new DataCtxPago(getdCtxSh(), configCheckout);
			if (pago!=null) {
				dCtxPago.getDataPedido().setPago(pago);
			}
			return dCtxPago;
		}
		private DataCtxShop getdCtxSh() {
			DataCtxShop dCtxSh = new DataCtxShop(app, channel, country, idioma, vale);
			dCtxSh.userConnected = user;
			dCtxSh.passwordUser = password;
			dCtxSh.userRegistered = "".compareTo(user)!=0;
			return dCtxSh;
		}
		
		public CheckoutFlow build() throws Exception {
			if (listArticles==null) {
				listArticles = UtilsTest.getArticlesForTestDependingVale(getdCtxSh(), 2, TestMaker.getDriverTestCase());
			}
			return new CheckoutFlow(
					getdCtxSh(), 
					getDataCtxPago(), 
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
