package com.mng.robotest.test.appmanto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.github.jorge2m.testmaker.domain.InputParamsTM;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.github.jorge2m.testmaker.domain.suitetree.TestRunTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.Pago;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.data.Constantes;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.exceptions.NotFoundException;
import com.mng.robotest.test.steps.manto.DataMantoAccess;
import com.mng.robotest.test.steps.manto.PageConsultaIdEansSteps;
import com.mng.robotest.test.steps.manto.PageGestionarClientesSteps;
import com.mng.robotest.test.steps.manto.PageGestorChequesSteps;
import com.mng.robotest.test.steps.manto.PageGestorConsultaCambioFamiliaSteps;
import com.mng.robotest.test.steps.manto.PageGestorEstadisticasPedidoSteps;
import com.mng.robotest.test.steps.manto.PageLoginMantoSteps;
import com.mng.robotest.test.steps.manto.PageMenusMantoSteps;
import com.mng.robotest.test.steps.manto.PageOrdenacionDePrendasSteps;
import com.mng.robotest.test.steps.manto.PageSelTdaMantoSteps;
import com.mng.robotest.test.steps.manto.SecFiltrosMantoSteps;
import com.mng.robotest.test.steps.manto.SecFiltrosMantoSteps.TypeSearch;
import com.mng.robotest.test.steps.manto.pedido.PagePedidosMantoSteps;

public class Manto {

	private DataMantoAccess dMantoAcc = null;
	private DataPedido dPedidoPrueba;
	private Pais espanya = new Pais();
	private Pago dPagoPrueba = new Pago();
	
	private String codigoEspanya = "001";
	private String almacenEspanya = "001";

	public void setDataMantoAccess() throws Exception {
		if (dMantoAcc==null) {
			dMantoAcc = new DataMantoAccess();
			TestCaseTM testCase = getTestCase();
			TestRunTM testRun = testCase.getTestRunParent();
			InputParamsTM inputParams = testCase.getInputParamsSuite();
			dMantoAcc.urlManto = inputParams.getUrlBase();
			dMantoAcc.userManto = testRun.getParameter(Constantes.PARAM_USR_MANTO);
			dMantoAcc.passManto = testRun.getParameter(Constantes.PARAM_PAS_MANTO);
			dMantoAcc.appE = AppEcom.shop;
		}
	}
	
	private TestCaseTM getTestCase() throws NotFoundException {
		Optional<TestCaseTM> testCaseOpt = TestMaker.getTestCase();
		if (!testCaseOpt.isPresent()) {
		  throw new NotFoundException("Not found TestCase");
		}
		return testCaseOpt.get();
	}
	
	@Test(
		groups={"Manto", "Canal:desktop_App:all"}, alwaysRun=true, 
		description="Compra en España")
	public void MAN000_GenerarPedidoFicticioMANTO() throws Exception {
		setDataMantoAccess();
		WebDriver driver = TestMaker.getDriverTestCase();
		PageLoginMantoSteps.login(dMantoAcc.urlManto, dMantoAcc.userManto, dMantoAcc.passManto, driver);
		
		this.espanya.setCodigo_pais(codigoEspanya);
		this.espanya.setNombre_pais("España");
		this.dPedidoPrueba = new DataPedido(espanya, null);
		this.dPedidoPrueba.setCodigopais(codigoEspanya);
		this.dPagoPrueba.setNombre("");
		this.dPedidoPrueba.setPago(dPagoPrueba);
		new PageSelTdaMantoSteps().selectTienda(almacenEspanya, codigoEspanya);
		new PageMenusMantoSteps().goToPedidos();
		
		LocalDate dateSevenDaysAgo = LocalDate.now().minusDays(7);
		new SecFiltrosMantoSteps(driver).setFiltrosWithoutChequeRegaloYbuscar(dPedidoPrueba, TypeSearch.PEDIDO, dateSevenDaysAgo, LocalDate.now());
		
		PagePedidosMantoSteps pagePedidosMantoSteps = new PagePedidosMantoSteps();
		this.dPedidoPrueba = pagePedidosMantoSteps.getPedidoUsuarioRegistrado(dPedidoPrueba);
		this.dPedidoPrueba = pagePedidosMantoSteps.getDataPedido(dPedidoPrueba);
		this.dPedidoPrueba = pagePedidosMantoSteps.getDataCliente(dPedidoPrueba);
		this.dPedidoPrueba = pagePedidosMantoSteps.getTiendaFisicaListaPedidos(dPedidoPrueba);
	}

//	@Test(
//		enabled=false, //El menú "Consultar tiendas" ha desaparecido
//		dependsOnMethods = { "MAN000_GenerarPedidoFicticioMANTO" },
//		groups={"Manto", "Canal:desktop_App:all"}, alwaysRun=true, 
//		description="Consulta de varios una tienda existente y otra no existente")
//	public void MAN001_ConsultaTiendas() throws Exception {
//		setDataMantoAccess();
//		WebDriver driver = TestMaker.getDriverTestCase();
//		PageLoginMantoSteps.login(dMantoAcc.urlManto, dMantoAcc.userManto, dMantoAcc.passManto, driver);
//		PageSelTdaMantoSteps.selectTienda(almacenEspanya, codigoEspanya, dMantoAcc.appE, driver);
//		PageMenusMantoSteps.goToConsultarTiendas(driver);
//
//		String tiendaNoExistente = "423";
//		PageConsultaTiendaSteps.consultaTiendaInexistente(tiendaNoExistente, driver);
//		String tiendaExistente = dPedidoPrueba.getDataDeliveryPoint().getCodigo();
//		if (this.dPedidoPrueba.getDataDeliveryPoint().getCodigo() == null) {
//			tiendaExistente = "7543";
//		}
//			
//		PageConsultaTiendaSteps.consultaTiendaExistente(tiendaExistente, driver);
//	}


	@Test(
		dependsOnMethods = { "MAN000_GenerarPedidoFicticioMANTO" },
		groups={"Manto", "Canal:desktop_App:all"}, alwaysRun=true, 
		description="Consulta de la información referente a varios pedidos")
	public void MAN002_Consulta_ID_EAN() throws Exception {
		setDataMantoAccess();
		WebDriver driver = TestMaker.getDriverTestCase();
		PageLoginMantoSteps.login(dMantoAcc.urlManto, dMantoAcc.userManto, dMantoAcc.passManto, driver);
		new PageSelTdaMantoSteps().selectTienda(almacenEspanya, codigoEspanya);
		new PageMenusMantoSteps().goToIdEans();

		List<String> pedidosPrueba = new ArrayList<>();
		pedidosPrueba.add(this.dPedidoPrueba.getCodigoPedidoManto());
		List<String> articulosPrueba = new ArrayList<>();
		for (int i = 0; i < dPedidoPrueba.getDataBag().getListArticulos().size(); i++) {
			articulosPrueba.add(dPedidoPrueba.getDataBag().getListArticulos().get(i).getReferencia().toString());
		}
		
		new PageConsultaIdEansSteps().consultaDatosContacto(pedidosPrueba);
		
		PageConsultaIdEansSteps pageConsultaIdEansSteps = new PageConsultaIdEansSteps();
		pageConsultaIdEansSteps.consultaIdentificadoresPedido(pedidosPrueba);
		pageConsultaIdEansSteps.consultaTrackings(pedidosPrueba);
		pageConsultaIdEansSteps.consultaDatosEan(articulosPrueba);
	}

	@Test(
		dependsOnMethods = { "MAN000_GenerarPedidoFicticioMANTO" },
		groups={"Manto", "Canal:desktop_App:all"}, alwaysRun=true, 
		description="Consulta y gestión de clientes")
	public void MAN003_GestionarClientes() throws Exception {
		setDataMantoAccess();
		WebDriver driver = TestMaker.getDriverTestCase();
		PageLoginMantoSteps.login(dMantoAcc.urlManto, dMantoAcc.userManto, dMantoAcc.passManto, driver);
		new PageSelTdaMantoSteps().selectTienda(almacenEspanya, codigoEspanya);
		new PageMenusMantoSteps().goToGestionarClientes();

		String dni = dPedidoPrueba.getPago().getDni();
		
		PageGestionarClientesSteps pageGestionarClientesSteps = new PageGestionarClientesSteps();
		pageGestionarClientesSteps.inputDniAndClickBuscar(dni);
		pageGestionarClientesSteps.clickThirdButton();
		pageGestionarClientesSteps.clickThirdButton();
		pageGestionarClientesSteps.clickDetallesButton(dni);
	}
	
	@Test(
		groups={"Manto", "Canal:desktop_App:all"}, alwaysRun=true, 
		description="Consulta de cheques")
	public void MAN004_GestorCheques() throws Exception {
		setDataMantoAccess();
		WebDriver driver = TestMaker.getDriverTestCase();
		PageLoginMantoSteps.login(dMantoAcc.urlManto, dMantoAcc.userManto, dMantoAcc.passManto, driver);
		new PageSelTdaMantoSteps().selectTienda(almacenEspanya, codigoEspanya);
		new PageMenusMantoSteps().goToGestorCheques();

		String mail = "esther.esteve@mango.com";
		String cheque = "204028046151";
		PageGestorChequesSteps pageGestChecksSteps = new PageGestorChequesSteps(driver);
		pageGestChecksSteps.inputMailAndClickCorreoCliente(mail);
		pageGestChecksSteps.clickPedido(10, mail);
		pageGestChecksSteps.volverCheques();
		pageGestChecksSteps.inputCheque(cheque);
		pageGestChecksSteps.chequeDetails();
		pageGestChecksSteps.volverCheques();
	}
	
	@Test(
		enabled=false, //La operativa siempre falla en pre por timeout
		groups={"Manto", "Canal:desktop_App:all"}, alwaysRun=true, 
		description="Consulta de estadísticas de pedidos")
	public void MAN005_GestorEstadisticasPedidos() throws Exception {
		setDataMantoAccess();
		WebDriver driver = TestMaker.getDriverTestCase();
		PageLoginMantoSteps.login(dMantoAcc.urlManto, dMantoAcc.userManto, dMantoAcc.passManto, driver);
		new PageSelTdaMantoSteps().selectTienda(almacenEspanya, codigoEspanya);
		new PageMenusMantoSteps().goToGestorEstadisticasPedido();
		PageGestorEstadisticasPedidoSteps.searchZalandoOrdersInformation(driver);
		PageGestorEstadisticasPedidoSteps.compareLastDayInformation(driver);
	}
	
//	@Test(
//		enabled=false, //Ha desaparecido el menú de "Gestor de saldos de TPV"
//		groups={"Manto", "Canal:desktop_App:all"}, alwaysRun=true, 
//		description="Gestor de saldos de TPV")
//	public void MAN006_GestorSaldosTPV() throws Exception {
//		setDataMantoAccess();
//		WebDriver driver = TestMaker.getDriverTestCase();
//		PageLoginMantoSteps.login(dMantoAcc.urlManto, dMantoAcc.userManto, dMantoAcc.passManto, driver);
//		PageSelTdaMantoSteps.selectTienda(almacenEspanya, codigoEspanya, dMantoAcc.appE, driver);
//		PageMenusMantoSteps.goToGestorSaldosTPV(driver);
//	
//		this.tpv = "600";
//		PageGestorSaldosTPVSteps.searchValidTPV(this.tpv, driver);
//		
//		this.tpv = "4238";
//		PageGestorSaldosTPVSteps.searchUnvalidTPV(this.tpv, driver);
//	}
	
	@Test(
		groups={"Manto", "Canal:desktop_App:all"}, alwaysRun=true, 
		description="Gestor de consulta y cambio de familia")
	public void MAN007_GestorConsultaCambioFamilia() throws Exception {
		setDataMantoAccess();
		WebDriver driver = TestMaker.getDriverTestCase();
		PageLoginMantoSteps.login(dMantoAcc.urlManto, dMantoAcc.userManto, dMantoAcc.passManto, driver);
		new PageSelTdaMantoSteps().selectTienda(almacenEspanya, codigoEspanya);
		new PageMenusMantoSteps().goToGestorConsultaCambioFamilia();
		PageGestorConsultaCambioFamiliaSteps.selectAccesoriosAndClickConsultaPorFamiliaButton(driver);
		PageGestorConsultaCambioFamiliaSteps.clickCambioFamiliaButton(driver);
	}
	
	@Test(
		groups={"Manto", "Canal:desktop_App:all"}, alwaysRun=true, 
		description="Comprueba el correcto funcionamiento del ordenador de prendas")
	public void MAN008_Ordenador_de_Prendas() throws Exception {
		setDataMantoAccess();
		WebDriver driver = TestMaker.getDriverTestCase();
		PageLoginMantoSteps.login(dMantoAcc.urlManto, dMantoAcc.userManto, dMantoAcc.passManto, driver);
		new PageSelTdaMantoSteps().selectTienda(almacenEspanya, codigoEspanya);
		new PageMenusMantoSteps().goToOrdenadorDePrendas();
		
		PageOrdenacionDePrendasSteps pageOrdenacionDePrendasSteps = new PageOrdenacionDePrendasSteps(driver);
		pageOrdenacionDePrendasSteps.mantoOrdenacionInicio();
		pageOrdenacionDePrendasSteps.mantoSeccionPrendas();
		pageOrdenacionDePrendasSteps.ordenacionModal();		
	}
}
