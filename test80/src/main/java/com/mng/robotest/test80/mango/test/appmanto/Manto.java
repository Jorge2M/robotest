package com.mng.robotest.test80.mango.test.appmanto;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.utils.controlTest.mango.GestorWebDriver;
import com.mng.robotest.test80.arq.utils.otras.Constantes;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
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


public class Manto extends GestorWebDriver {

	DataMantoAccess dMantoAcc;
	DataPedido dPedidoPrueba;
	Pais espanya = new Pais();
	Pago dPagoPrueba = new Pago();
	
	String codigoEspanya = "001";
	String almacenEspanya = "001";
	int posicionPedidoActual = 7;
	String tpv;

	@BeforeMethod(groups={"Manto", "Canal:desktop_App:all", "SupportsFactoryCountrys"})
	@Parameters({"brwsr-path", "urlBase"})
	public void login(String bpath, String urlBase, ITestContext ctx, Method method) throws Exception {
		this.dMantoAcc = new DataMantoAccess();
		this.dMantoAcc.urlManto = ctx.getCurrentXmlTest().getParameter(Constantes.paramUrlBase);
		this.dMantoAcc.userManto = ctx.getCurrentXmlTest().getParameter(Constantes.paramUsrmanto);
		this.dMantoAcc.passManto = ctx.getCurrentXmlTest().getParameter(Constantes.paramPasmanto);
		this.dMantoAcc.appE = AppEcom.shop;
		TestCaseData.getAndStoreDataFmwk(bpath, urlBase, "", Channel.desktop, ctx, method);
	}

	@SuppressWarnings("unused")
	@AfterMethod (groups={"Manto", "Canal:desktop_App:all", "SupportsFactoryCountrys"}, alwaysRun = true)
	public void logout(ITestContext context, Method method) throws Exception {
		WebDriver driver = TestCaseData.getWebDriver();
		super.quitWebDriver(driver, context);
	}
	
    @Test(
    	groups={"Manto", "Canal:desktop_App:all"}, alwaysRun=true, 
        description="Compra en España")
    public void MAN000_GenerarPedidoFicticioMANTO() throws Exception {
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
    	PageLoginMantoStpV.login(this.dMantoAcc.urlManto, this.dMantoAcc.userManto, this.dMantoAcc.passManto, dFTest.driver);

		//Accedemos a la tienda asociada al país/pedido (sólo si no estamos ya en ella)
    	this.espanya.setCodigo_pais(this.codigoEspanya);
    	this.espanya.setNombre_pais("España");
    	this.dPedidoPrueba = new DataPedido(this.espanya);
		this.dPedidoPrueba.setCodigopais(this.codigoEspanya);
		this.dPagoPrueba.setNombre("");
		this.dPedidoPrueba.setPago(this.dPagoPrueba);
		
		PageSelTdaMantoStpV.selectTienda(this.almacenEspanya, this.codigoEspanya, this.dMantoAcc.appE, dFTest.driver);
    	
    	PageMenusMantoStpV.goToPedidos(dFTest.driver);
    	
    	SecFiltrosMantoStpV.setFiltrosHoyYbuscar(this.dPedidoPrueba, TypeSearch.PEDIDO, dFTest.driver);
    	//this.dPedidoPrueba = PagePedidosMantoStpV.getDataPedidoUsuarioRegistrado(this.dPedidoPrueba, dFTest);
    	this.dPedidoPrueba = PagePedidosMantoStpV.getPedidoUsuarioRegistrado(this.dPedidoPrueba, dFTest);
    	this.dPedidoPrueba = PagePedidosMantoStpV.getDataPedido(this.dPedidoPrueba, dFTest);
    	this.dPedidoPrueba = PagePedidosMantoStpV.getDataCliente(this.dPedidoPrueba, dFTest);
    	this.dPedidoPrueba = PagePedidosMantoStpV.getTiendaFisicaListaPedidos(this.dPedidoPrueba, dFTest);
    }


	@Test(
		dependsOnMethods = { "MAN000_GenerarPedidoFicticioMANTO" },
		groups={"Manto", "Canal:desktop_App:all"}, alwaysRun=true, 
		description="Consulta de varios una tienda existente y otra no existente")
	public void MAN001_ConsultaTiendas() throws Exception {
		WebDriver driver = TestCaseData.getWebDriver();
		
		PageLoginMantoStpV.login(this.dMantoAcc.urlManto, this.dMantoAcc.userManto, this.dMantoAcc.passManto, driver);
		PageSelTdaMantoStpV.selectTienda(this.almacenEspanya, this.codigoEspanya, this.dMantoAcc.appE, driver);
		PageMenusMantoStpV.goToConsultarTiendas(driver);

		String tiendaNoExistente = "423";
		PageConsultaTiendaStpV.consultaTiendaInexistente(tiendaNoExistente, driver);
		String tiendaExistente = this.dPedidoPrueba.getDataDeliveryPoint().getCodigo();
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
		WebDriver driver = TestCaseData.getWebDriver();
		
		PageLoginMantoStpV.login(this.dMantoAcc.urlManto, this.dMantoAcc.userManto, this.dMantoAcc.passManto, driver);
		PageSelTdaMantoStpV.selectTienda(this.almacenEspanya, this.codigoEspanya, this.dMantoAcc.appE, driver);
		PageMenusMantoStpV.goToIdEans(driver);

		List<String> pedidosPrueba = new ArrayList<>();
		pedidosPrueba.add(this.dPedidoPrueba.getCodigoPedidoManto());
		List<String> articulosPrueba = new ArrayList<>();
		for (int i = 0; i < this.dPedidoPrueba.getDataBag().getListArticulos().size(); i++) {
			articulosPrueba.add(this.dPedidoPrueba.getDataBag().getListArticulos().get(i).getReferencia().toString());
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
		WebDriver driver = TestCaseData.getWebDriver();
		
		PageLoginMantoStpV.login(this.dMantoAcc.urlManto, this.dMantoAcc.userManto, this.dMantoAcc.passManto, driver);
		PageSelTdaMantoStpV.selectTienda(this.almacenEspanya, this.codigoEspanya, this.dMantoAcc.appE, driver);
		PageMenusMantoStpV.goToGestionarClientes(driver);

		String dni = this.dPedidoPrueba.getPago().getDni();
		PageGestionarClientesStpV.inputDniAndClickBuscar(dni, driver);
		PageGestionarClientesStpV.clickThirdButton(driver);
		PageGestionarClientesStpV.clickThirdButton(driver);
		PageGestionarClientesStpV.clickDetallesButton(dni, driver);
	}
	
	
	@Test(
		groups={"Manto", "Canal:desktop_App:all"}, alwaysRun=true, 
		description="Consulta de cheques")
	public void MAN004_GestorCheques() throws Exception {
		WebDriver driver = TestCaseData.getWebDriver();
		
		PageLoginMantoStpV.login(this.dMantoAcc.urlManto, this.dMantoAcc.userManto, this.dMantoAcc.passManto, driver);
		PageSelTdaMantoStpV.selectTienda(this.almacenEspanya, this.codigoEspanya, this.dMantoAcc.appE, driver);
		PageMenusMantoStpV.goToGestorCheques(driver);

		String mail = "esther.esteve@mango.com";
		String cheque = "204028046151";
		PageGestorChequesStpV.inputMailAndClickCorreoCliente(mail, driver);
		
		PageGestorChequesStpV.clickPedido(10, mail, driver);
		PageGestorChequesStpV.volverCheques(driver);
		PageGestorChequesStpV.inputCheque(cheque, driver);
		PageGestorChequesStpV.chequeDetails(driver);
		PageGestorChequesStpV.volverCheques(driver);
	}
	
	
	@Test(
		groups={"Manto", "Canal:desktop_App:all"}, alwaysRun=true, 
		description="Consulta de estadísticas de pedidos")
	public void MAN005_GestorEstadisticasPedidos() throws Exception {
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
		PageLoginMantoStpV.login(this.dMantoAcc.urlManto, this.dMantoAcc.userManto, this.dMantoAcc.passManto, dFTest.driver);
	
		//Accedemos a la tienda asociada al país/pedido (sólo si no estamos ya en ella)
		PageSelTdaMantoStpV.selectTienda(this.almacenEspanya, this.codigoEspanya, this.dMantoAcc.appE, dFTest.driver);
	
		PageMenusMantoStpV.goToGestorEstadisticasPedido(dFTest.driver);
	
		PageGestorEstadisticasPedidoStpV.searchZalandoOrdersInformation(dFTest);
		
		PageGestorEstadisticasPedidoStpV.compareLastDayInformation(dFTest);
	}
	
	
	@Test(
		groups={"Manto", "Canal:desktop_App:all"}, alwaysRun=true, 
		description="Gestor de saldos de TPV")
	public void MAN006_GestorSaldosTPV() throws Exception {
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
		PageLoginMantoStpV.login(this.dMantoAcc.urlManto, this.dMantoAcc.userManto, this.dMantoAcc.passManto, dFTest.driver);
	
		//Accedemos a la tienda asociada al país/pedido (sólo si no estamos ya en ella)
		PageSelTdaMantoStpV.selectTienda(this.almacenEspanya, this.codigoEspanya, this.dMantoAcc.appE, dFTest.driver);
	
		PageMenusMantoStpV.goToGestorSaldosTPV(dFTest.driver);
	
		this.tpv = "600";
		PageGestorSaldosTPVStpV.searchValidTPV(this.tpv, dFTest);
		
		this.tpv = "4238";
		PageGestorSaldosTPVStpV.searchUnvalidTPV(this.tpv, dFTest);
	}
	
	@Test(
		groups={"Manto", "Canal:desktop_App:all"}, alwaysRun=true, 
		description="Gestor de consulta y cambio de familia")
	public void MAN007_GestorConsultaCambioFamilia() throws Exception {
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
		PageLoginMantoStpV.login(this.dMantoAcc.urlManto, this.dMantoAcc.userManto, this.dMantoAcc.passManto, dFTest.driver);
	
		//Accedemos a la tienda asociada al país/pedido (sólo si no estamos ya en ella)
		PageSelTdaMantoStpV.selectTienda(this.almacenEspanya, this.codigoEspanya, this.dMantoAcc.appE, dFTest.driver);
	
		PageMenusMantoStpV.goToGestorConsultaCambioFamilia(dFTest.driver);
	
		PageGestorConsultaCambioFamiliaStpV.selectAccesoriosAndClickConsultaPorFamiliaButton(dFTest);
		PageGestorConsultaCambioFamiliaStpV.clickCambioFamiliaButton(dFTest.driver);
	}
	
	@Test(
		groups={"Manto", "Canal:desktop_App:all"}, alwaysRun=true, 
		description="Comprueba el correcto funcionamiento del ordenador de prendas")
	public void MAN008_Ordenador_de_Prendas() throws Exception {
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
		PageLoginMantoStpV.login(this.dMantoAcc.urlManto, this.dMantoAcc.userManto, this.dMantoAcc.passManto, dFTest.driver);

		//Accedemos a la tienda asociada al país/pedido (sólo si no estamos ya en ella)
		PageSelTdaMantoStpV.selectTienda(this.almacenEspanya, this.codigoEspanya, this.dMantoAcc.appE, dFTest.driver);
		
		PageMenusMantoStpV.goToOrdenadorDePrendas(dFTest.driver);
		PageOrdenacionDePrendasStpV.mantoOrdenacionInicio(dFTest);
		PageOrdenacionDePrendasStpV.mantoSeccionPrendas(dFTest);
		PageOrdenacionDePrendasStpV.ordenacionModal(dFTest);		
	}
}


