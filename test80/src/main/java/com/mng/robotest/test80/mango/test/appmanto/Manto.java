package com.mng.robotest.test80.mango.test.appmanto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.github.jorge2m.testmaker.domain.InputParamsTM;
//import com.github.jorge2m.testmaker.domain.InputParamsTM;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.github.jorge2m.testmaker.domain.suitetree.TestRunTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.test80.mango.test.data.Constantes;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.stpv.manto.DataMantoAccess;
import com.mng.robotest.test80.mango.test.stpv.manto.PageConsultaIdEansStpV;
import com.mng.robotest.test80.mango.test.stpv.manto.PageConsultaTiendaStpV;
import com.mng.robotest.test80.mango.test.stpv.manto.PageGestionarClientesStpV;
import com.mng.robotest.test80.mango.test.stpv.manto.PageGestorChequesStpV;
import com.mng.robotest.test80.mango.test.stpv.manto.PageGestorConsultaCambioFamiliaStpV;
import com.mng.robotest.test80.mango.test.stpv.manto.PageGestorEstadisticasPedidoStpV;
import com.mng.robotest.test80.mango.test.stpv.manto.PageGestorSaldosTPVStpV;
import com.mng.robotest.test80.mango.test.stpv.manto.PageLoginMantoStpV;
import com.mng.robotest.test80.mango.test.stpv.manto.PageMenusMantoStpV;
import com.mng.robotest.test80.mango.test.stpv.manto.PageOrdenacionDePrendasStpV;
import com.mng.robotest.test80.mango.test.stpv.manto.PageSelTdaMantoStpV;
import com.mng.robotest.test80.mango.test.stpv.manto.SecFiltrosMantoStpV;
import com.mng.robotest.test80.mango.test.stpv.manto.SecFiltrosMantoStpV.TypeSearch;
import com.mng.robotest.test80.mango.test.stpv.manto.pedido.PagePedidosMantoStpV;

public class Manto {

	private DataMantoAccess dMantoAcc = null;
	private DataPedido dPedidoPrueba;
	private Pais espanya = new Pais();
	private Pago dPagoPrueba = new Pago();
	
	private String codigoEspanya = "001";
	private String almacenEspanya = "001";
	private String tpv;

	public void setDataMantoAccess() throws Exception {
		if (dMantoAcc==null) {
			dMantoAcc = new DataMantoAccess();
			TestCaseTM testCase = TestMaker.getTestCase();
			TestRunTM testRun = testCase.getTestRunParent();
			InputParamsTM inputParams = testCase.getInputParamsSuite();
			dMantoAcc.urlManto = inputParams.getUrlBase();
			dMantoAcc.userManto = testRun.getParameter(Constantes.paramUsrmanto);
			dMantoAcc.passManto = testRun.getParameter(Constantes.paramPasmanto);
			dMantoAcc.appE = AppEcom.shop;
		}
	}
	
    @Test(
    	groups={"Manto", "Canal:desktop_App:all"}, alwaysRun=true, 
        description="Compra en España")
    public void MAN000_GenerarPedidoFicticioMANTO() throws Exception {
    	setDataMantoAccess();
		WebDriver driver = TestMaker.getDriverTestCase();
    	PageLoginMantoStpV.login(dMantoAcc.urlManto, dMantoAcc.userManto, dMantoAcc.passManto, driver);
    	
    	this.espanya.setCodigo_pais(codigoEspanya);
    	this.espanya.setNombre_pais("España");
    	this.dPedidoPrueba = new DataPedido(espanya);
		this.dPedidoPrueba.setCodigopais(codigoEspanya);
		this.dPagoPrueba.setNombre("");
		this.dPedidoPrueba.setPago(dPagoPrueba);
		PageSelTdaMantoStpV.selectTienda(almacenEspanya, codigoEspanya, dMantoAcc.appE, driver);
    	PageMenusMantoStpV.goToPedidos(driver);
    	
    	LocalDate dateSevenDaysAgo = LocalDate.now().minusDays(7);
    	SecFiltrosMantoStpV.setFiltrosWithoutChequeRegaloYbuscar(dPedidoPrueba, TypeSearch.PEDIDO, dateSevenDaysAgo, driver);
    	this.dPedidoPrueba = PagePedidosMantoStpV.getPedidoUsuarioRegistrado(dPedidoPrueba, driver);
    	this.dPedidoPrueba = PagePedidosMantoStpV.getDataPedido(dPedidoPrueba, driver);
    	this.dPedidoPrueba = PagePedidosMantoStpV.getDataCliente(dPedidoPrueba, driver);
    	this.dPedidoPrueba = PagePedidosMantoStpV.getTiendaFisicaListaPedidos(dPedidoPrueba, driver);
    }

	@Test(
		enabled=false, //El menú "Consultar tiendas" ha desaparecido
		dependsOnMethods = { "MAN000_GenerarPedidoFicticioMANTO" },
		groups={"Manto", "Canal:desktop_App:all"}, alwaysRun=true, 
		description="Consulta de varios una tienda existente y otra no existente")
	public void MAN001_ConsultaTiendas() throws Exception {
    	setDataMantoAccess();
		WebDriver driver = TestMaker.getDriverTestCase();
		PageLoginMantoStpV.login(dMantoAcc.urlManto, dMantoAcc.userManto, dMantoAcc.passManto, driver);
		PageSelTdaMantoStpV.selectTienda(almacenEspanya, codigoEspanya, dMantoAcc.appE, driver);
		PageMenusMantoStpV.goToConsultarTiendas(driver);

		String tiendaNoExistente = "423";
		PageConsultaTiendaStpV.consultaTiendaInexistente(tiendaNoExistente, driver);
		String tiendaExistente = dPedidoPrueba.getDataDeliveryPoint().getCodigo();
		if (this.dPedidoPrueba.getDataDeliveryPoint().getCodigo() == null) {
			tiendaExistente = "7543";
		}
			
		PageConsultaTiendaStpV.consultaTiendaExistente(tiendaExistente, driver);
	}


	@Test(
		dependsOnMethods = { "MAN000_GenerarPedidoFicticioMANTO" },
		groups={"Manto", "Canal:desktop_App:all"}, alwaysRun=true, 
		description="Consulta de la información referente a varios pedidos")
	public void MAN002_Consulta_ID_EAN() throws Exception {
		setDataMantoAccess();
		WebDriver driver = TestMaker.getDriverTestCase();
		PageLoginMantoStpV.login(dMantoAcc.urlManto, dMantoAcc.userManto, dMantoAcc.passManto, driver);
		PageSelTdaMantoStpV.selectTienda(almacenEspanya, codigoEspanya, dMantoAcc.appE, driver);
		PageMenusMantoStpV.goToIdEans(driver);

		List<String> pedidosPrueba = new ArrayList<>();
		pedidosPrueba.add(this.dPedidoPrueba.getCodigoPedidoManto());
		List<String> articulosPrueba = new ArrayList<>();
		for (int i = 0; i < dPedidoPrueba.getDataBag().getListArticulos().size(); i++) {
			articulosPrueba.add(dPedidoPrueba.getDataBag().getListArticulos().get(i).getReferencia().toString());
		}
		
		PageConsultaIdEansStpV.consultaDatosContacto(pedidosPrueba, driver);
		PageConsultaIdEansStpV.consultaIdentificadoresPedido(pedidosPrueba, driver);
		PageConsultaIdEansStpV.consultaTrackings(pedidosPrueba, driver);
		PageConsultaIdEansStpV.consultaDatosEan(articulosPrueba, driver);
	}


	@Test(
		dependsOnMethods = { "MAN000_GenerarPedidoFicticioMANTO" },
		groups={"Manto", "Canal:desktop_App:all"}, alwaysRun=true, 
		description="Consulta y gestión de clientes")
	public void MAN003_GestionarClientes() throws Exception {
		setDataMantoAccess();
		WebDriver driver = TestMaker.getDriverTestCase();
		PageLoginMantoStpV.login(dMantoAcc.urlManto, dMantoAcc.userManto, dMantoAcc.passManto, driver);
		PageSelTdaMantoStpV.selectTienda(almacenEspanya, codigoEspanya, dMantoAcc.appE, driver);
		PageMenusMantoStpV.goToGestionarClientes(driver);

		String dni = dPedidoPrueba.getPago().getDni();
		PageGestionarClientesStpV.inputDniAndClickBuscar(dni, driver);
		PageGestionarClientesStpV.clickThirdButton(driver);
		PageGestionarClientesStpV.clickThirdButton(driver);
		PageGestionarClientesStpV.clickDetallesButton(dni, driver);
	}
	
	
	@Test(
		groups={"Manto", "Canal:desktop_App:all"}, alwaysRun=true, 
		description="Consulta de cheques")
	public void MAN004_GestorCheques() throws Exception {
		setDataMantoAccess();
		WebDriver driver = TestMaker.getDriverTestCase();
		PageLoginMantoStpV.login(dMantoAcc.urlManto, dMantoAcc.userManto, dMantoAcc.passManto, driver);
		PageSelTdaMantoStpV.selectTienda(almacenEspanya, codigoEspanya, dMantoAcc.appE, driver);
		PageMenusMantoStpV.goToGestorCheques(driver);

		String mail = "esther.esteve@mango.com";
		String cheque = "204028046151";
		PageGestorChequesStpV pageGestChecksStpV = new PageGestorChequesStpV(driver);
		pageGestChecksStpV.inputMailAndClickCorreoCliente(mail);
		pageGestChecksStpV.clickPedido(10, mail);
		pageGestChecksStpV.volverCheques();
		pageGestChecksStpV.inputCheque(cheque);
		pageGestChecksStpV.chequeDetails();
		pageGestChecksStpV.volverCheques();
	}
	
	@Test(
		enabled=false, //La operativa siempre falla en pre por timeout
		groups={"Manto", "Canal:desktop_App:all"}, alwaysRun=true, 
		description="Consulta de estadísticas de pedidos")
	public void MAN005_GestorEstadisticasPedidos() throws Exception {
		setDataMantoAccess();
		WebDriver driver = TestMaker.getDriverTestCase();
		PageLoginMantoStpV.login(dMantoAcc.urlManto, dMantoAcc.userManto, dMantoAcc.passManto, driver);
		PageSelTdaMantoStpV.selectTienda(almacenEspanya, codigoEspanya, dMantoAcc.appE, driver);
		PageMenusMantoStpV.goToGestorEstadisticasPedido(driver);
		PageGestorEstadisticasPedidoStpV.searchZalandoOrdersInformation(driver);
		PageGestorEstadisticasPedidoStpV.compareLastDayInformation(driver);
	}
	
	@Test(
		enabled=false, //Ha desaparecido el menú de "Gestor de saldos de TPV"
		groups={"Manto", "Canal:desktop_App:all"}, alwaysRun=true, 
		description="Gestor de saldos de TPV")
	public void MAN006_GestorSaldosTPV() throws Exception {
		setDataMantoAccess();
		WebDriver driver = TestMaker.getDriverTestCase();
		PageLoginMantoStpV.login(dMantoAcc.urlManto, dMantoAcc.userManto, dMantoAcc.passManto, driver);
		PageSelTdaMantoStpV.selectTienda(almacenEspanya, codigoEspanya, dMantoAcc.appE, driver);
		PageMenusMantoStpV.goToGestorSaldosTPV(driver);
	
		this.tpv = "600";
		PageGestorSaldosTPVStpV.searchValidTPV(this.tpv, driver);
		
		this.tpv = "4238";
		PageGestorSaldosTPVStpV.searchUnvalidTPV(this.tpv, driver);
	}
	
	@Test(
		groups={"Manto", "Canal:desktop_App:all"}, alwaysRun=true, 
		description="Gestor de consulta y cambio de familia")
	public void MAN007_GestorConsultaCambioFamilia() throws Exception {
		setDataMantoAccess();
		WebDriver driver = TestMaker.getDriverTestCase();
		PageLoginMantoStpV.login(dMantoAcc.urlManto, dMantoAcc.userManto, dMantoAcc.passManto, driver);
		PageSelTdaMantoStpV.selectTienda(almacenEspanya, codigoEspanya, dMantoAcc.appE, driver);
		PageMenusMantoStpV.goToGestorConsultaCambioFamilia(driver);
		PageGestorConsultaCambioFamiliaStpV.selectAccesoriosAndClickConsultaPorFamiliaButton(driver);
		PageGestorConsultaCambioFamiliaStpV.clickCambioFamiliaButton(driver);
	}
	
	@Test(
		groups={"Manto", "Canal:desktop_App:all"}, alwaysRun=true, 
		description="Comprueba el correcto funcionamiento del ordenador de prendas")
	public void MAN008_Ordenador_de_Prendas() throws Exception {
		setDataMantoAccess();
		WebDriver driver = TestMaker.getDriverTestCase();
		PageLoginMantoStpV.login(dMantoAcc.urlManto, dMantoAcc.userManto, dMantoAcc.passManto, driver);
		PageSelTdaMantoStpV.selectTienda(almacenEspanya, codigoEspanya, dMantoAcc.appE, driver);
		PageMenusMantoStpV.goToOrdenadorDePrendas(driver);
		
		PageOrdenacionDePrendasStpV pageOrdenacionDePrendasStpV = new PageOrdenacionDePrendasStpV(driver);
		pageOrdenacionDePrendasStpV.mantoOrdenacionInicio();
		pageOrdenacionDePrendasStpV.mantoSeccionPrendas();
		pageOrdenacionDePrendasStpV.ordenacionModal();		
	}
}


