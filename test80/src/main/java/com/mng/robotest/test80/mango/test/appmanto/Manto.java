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
import com.mng.robotest.test80.mango.test.stpv.manto.PagePedidosMantoStpV;
import com.mng.robotest.test80.mango.test.stpv.manto.PageSelTdaMantoStpV;
import com.mng.robotest.test80.mango.test.stpv.manto.SecFiltrosMantoStpV;
import com.mng.robotest.test80.mango.test.stpv.manto.SecFiltrosMantoStpV.TypeSearch;

@SuppressWarnings("javadoc")
public class Manto  extends GestorWebDriver {

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

		//Creación del WebDriver
		createDriverInThread(bpath, urlBase, "", Channel.desktop, ctx, method);
	}

	@SuppressWarnings("unused")
	@AfterMethod (groups={"Manto", "Canal:desktop_App:all", "SupportsFactoryCountrys"}, alwaysRun = true)
	public void logout(ITestContext context, Method method) throws Exception {
		WebDriver driver = getDriver().driver;
		super.quitWebDriver(driver, context);
	}
	

    @Test(
    	groups={"Manto", "Canal:desktop_App:all"}, alwaysRun=true, 
        description="Compra en España")
    public void MAN000_GenerarPedidoFicticioMANTO(ITestContext context, Method method) throws Exception {
        //Ejecución de un pago y obtención del Pedido
        /*DataFmwkTest dFTest = new DataFmwkTest(getDriver(), method, context);

        DataCtxPago dCtxPago = Compra.testOnePaymentUserNoRegistered("001"España, "TARJETA MANGO", Channel.desktop, AppEcom.shop, "https://shop.pre.mango.com/preHome.faces", dFTest);
        this.dPedidoPrueba = dCtxPago.getDataPedido();
        this.dRegistroPrueba = dCtxPago.getDatosRegistro();*/

    	DataFmwkTest dFTest = new DataFmwkTest(getDriver(), method, context);
    	PageLoginMantoStpV.login(this.dMantoAcc.urlManto, this.dMantoAcc.userManto, this.dMantoAcc.passManto, dFTest);

		//Accedemos a la tienda asociada al país/pedido (sólo si no estamos ya en ella)
		
    	this.espanya.setCodigo_pais(this.codigoEspanya);
    	this.espanya.setNombre_pais("España");
    	this.dPedidoPrueba = new DataPedido(this.espanya);
		this.dPedidoPrueba.setCodigopais(this.codigoEspanya);
		this.dPagoPrueba.setNombre("");
		this.dPedidoPrueba.setPago(this.dPagoPrueba);
		
		PageSelTdaMantoStpV.selectTienda(this.almacenEspanya, this.codigoEspanya, this.dMantoAcc.appE, dFTest);
    	
    	PageMenusMantoStpV.goToPedidos(dFTest);
    	
    	SecFiltrosMantoStpV.setFiltrosHoyYbuscar(this.dPedidoPrueba, TypeSearch.PEDIDO, dFTest);
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
	public void MAN001_ConsultaTiendas(ITestContext context, Method method) throws Exception {
		DataFmwkTest dFTest = new DataFmwkTest(getDriver(), method, context);

		//Script
		PageLoginMantoStpV.login(this.dMantoAcc.urlManto, this.dMantoAcc.userManto, this.dMantoAcc.passManto, dFTest);

		//Accedemos a la tienda asociada al país/pedido (sólo si no estamos ya en ella)
		PageSelTdaMantoStpV.selectTienda(this.almacenEspanya, this.codigoEspanya, this.dMantoAcc.appE, dFTest);

		PageMenusMantoStpV.goToConsultarTiendas(dFTest);

		String tiendaNoExistente = "423";
		PageConsultaTiendaStpV.consultaTiendaInexistente(tiendaNoExistente, dFTest);

		String tiendaExistente = this.dPedidoPrueba.getDataDeliveryPoint().getCodigo();
		if (this.dPedidoPrueba.getDataDeliveryPoint().getCodigo() == null)
			tiendaExistente = "7543";
		
		PageConsultaTiendaStpV.consultaTiendaExistente(tiendaExistente, dFTest);
	}


	@Test(
		dependsOnMethods = { "MAN000_GenerarPedidoFicticioMANTO" },
		groups={"Manto", "Canal:desktop_App:all"}, alwaysRun=true, 
		description="Consulta de la información referente a varios pedidos")
	public void MAN002_Consulta_ID_EAN(ITestContext context, Method method) throws Exception {
		DataFmwkTest dFTest = new DataFmwkTest(getDriver(), method, context);

		//Script
		PageLoginMantoStpV.login(this.dMantoAcc.urlManto, this.dMantoAcc.userManto, this.dMantoAcc.passManto, dFTest);

		//Accedemos a la tienda asociada al país/pedido (sólo si no estamos ya en ella)
		PageSelTdaMantoStpV.selectTienda(this.almacenEspanya, this.codigoEspanya, this.dMantoAcc.appE, dFTest);

		PageMenusMantoStpV.goToIdEans(dFTest);

		List<String> pedidosPrueba = new ArrayList<>();
		pedidosPrueba.add(this.dPedidoPrueba.getCodigoPedidoManto());
		List<String> articulosPrueba = new ArrayList<>();
		
		for (int i = 0; i < this.dPedidoPrueba.getDataBag().getListArticulos().size(); i++)
			articulosPrueba.add(this.dPedidoPrueba.getDataBag().getListArticulos().get(i).getReferencia().toString());

		PageConsultaIdEansStpV.consultaDatosContacto(pedidosPrueba, dFTest);
		PageConsultaIdEansStpV.consultaIdentificadoresPedido(pedidosPrueba, dFTest);
		PageConsultaIdEansStpV.consultaTrackings(pedidosPrueba, dFTest);
		PageConsultaIdEansStpV.consultaDatosEan(articulosPrueba, dFTest);
	}


	@Test(
		dependsOnMethods = { "MAN000_GenerarPedidoFicticioMANTO" },
		groups={"Manto", "Canal:desktop_App:all"}, alwaysRun=true, 
		description="Consulta y gestión de clientes")
	public void MAN003_GestionarClientes(ITestContext context, Method method) throws Exception {
		DataFmwkTest dFTest = new DataFmwkTest(getDriver(), method, context);

		//Script
		PageLoginMantoStpV.login(this.dMantoAcc.urlManto, this.dMantoAcc.userManto, this.dMantoAcc.passManto, dFTest);

		//Accedemos a la tienda asociada al país/pedido (sólo si no estamos ya en ella)
		PageSelTdaMantoStpV.selectTienda(this.almacenEspanya, this.codigoEspanya, this.dMantoAcc.appE, dFTest);

		PageMenusMantoStpV.goToGestionarClientes(dFTest);

		String dni = this.dPedidoPrueba.getPago().getDni();
		
		PageGestionarClientesStpV.inputDniAndClickBuscar(dni, dFTest);
		PageGestionarClientesStpV.clickThirdButton(dFTest);
		PageGestionarClientesStpV.clickThirdButton(dFTest);
		PageGestionarClientesStpV.clickDetallesButton(dni, dFTest);
	}
	
	
	@Test(
		groups={"Manto", "Canal:desktop_App:all"}, alwaysRun=true, 
		description="Consulta de cheques")
	public void MAN004_GestorCheques(ITestContext context, Method method) throws Exception {
		DataFmwkTest dFTest = new DataFmwkTest(getDriver(), method, context);

		//Script
		PageLoginMantoStpV.login(this.dMantoAcc.urlManto, this.dMantoAcc.userManto, this.dMantoAcc.passManto, dFTest);

		//Accedemos a la tienda asociada al país/pedido (sólo si no estamos ya en ella)
		PageSelTdaMantoStpV.selectTienda(this.almacenEspanya, this.codigoEspanya, this.dMantoAcc.appE, dFTest);

		PageMenusMantoStpV.goToGestorCheques(dFTest);

		String mail = "esther.esteve@mango.com";
		String cheque = "204028046151";
		PageGestorChequesStpV.inputMailAndClickCorreoCliente(mail, dFTest);
		
		PageGestorChequesStpV.clickPedido(10/*numFila*/, cheque, mail, dFTest);

		PageGestorChequesStpV.volverCheques(dFTest);
	}
	
	
	@Test(
		groups={"Manto", "Canal:desktop_App:all"}, alwaysRun=true, 
		description="Consulta de estadísticas de pedidos")
	public void MAN005_GestorEstadisticasPedidos(ITestContext context, Method method) throws Exception {
		DataFmwkTest dFTest = new DataFmwkTest(getDriver(), method, context);
	
		//Script
		PageLoginMantoStpV.login(this.dMantoAcc.urlManto, this.dMantoAcc.userManto, this.dMantoAcc.passManto, dFTest);
	
		//Accedemos a la tienda asociada al país/pedido (sólo si no estamos ya en ella)
		PageSelTdaMantoStpV.selectTienda(this.almacenEspanya, this.codigoEspanya, this.dMantoAcc.appE, dFTest);
	
		PageMenusMantoStpV.goToGestorEstadisticasPedido(dFTest);
	
		PageGestorEstadisticasPedidoStpV.searchZalandoOrdersInformation(dFTest);
		
		PageGestorEstadisticasPedidoStpV.compareLastDayInformation(dFTest);
	}
	
	
	@Test(
		groups={"Manto", "Canal:desktop_App:all"}, alwaysRun=true, 
		description="Gestor de saldos de TPV")
	public void MAN006_GestorSaldosTPV(ITestContext context, Method method) throws Exception {
		DataFmwkTest dFTest = new DataFmwkTest(getDriver(), method, context);
	
		//Script
		PageLoginMantoStpV.login(this.dMantoAcc.urlManto, this.dMantoAcc.userManto, this.dMantoAcc.passManto, dFTest);
	
		//Accedemos a la tienda asociada al país/pedido (sólo si no estamos ya en ella)
		PageSelTdaMantoStpV.selectTienda(this.almacenEspanya, this.codigoEspanya, this.dMantoAcc.appE, dFTest);
	
		PageMenusMantoStpV.goToGestorSaldosTPV(dFTest);
	
		this.tpv = "600";
		PageGestorSaldosTPVStpV.searchValidTPV(this.tpv, dFTest);
		
		this.tpv = "4238";
		PageGestorSaldosTPVStpV.searchUnvalidTPV(this.tpv, dFTest);
	}
	
	@Test(
		groups={"Manto", "Canal:desktop_App:all"}, alwaysRun=true, 
		description="Gestor de consulta y cambio de familia")
	public void MAN007_GestorConsultaCambioFamilia(ITestContext context, Method method) throws Exception {
		DataFmwkTest dFTest = new DataFmwkTest(getDriver(), method, context);
	
		//Script
		PageLoginMantoStpV.login(this.dMantoAcc.urlManto, this.dMantoAcc.userManto, this.dMantoAcc.passManto, dFTest);
	
		//Accedemos a la tienda asociada al país/pedido (sólo si no estamos ya en ella)
		PageSelTdaMantoStpV.selectTienda(this.almacenEspanya, this.codigoEspanya, this.dMantoAcc.appE, dFTest);
	
		PageMenusMantoStpV.goToGestorConsultaCambioFamilia(dFTest);
	
		PageGestorConsultaCambioFamiliaStpV.selectAccesoriosAndClickConsultaPorFamiliaButton(dFTest);
		PageGestorConsultaCambioFamiliaStpV.clickCambioFamiliaButton(dFTest);
	}
	
	@Test(
		groups={"Manto", "Canal:desktop_App:all"}, alwaysRun=true, 
		description="Comprueba el correcto funcionamiento del ordenador de prendas")
	public void MAN008_Ordenador_de_Prendas(ITestContext context, Method method) throws Exception {
		DataFmwkTest dFTest = new DataFmwkTest(getDriver(), method, context);

		//Script
		PageLoginMantoStpV.login(this.dMantoAcc.urlManto, this.dMantoAcc.userManto, this.dMantoAcc.passManto, dFTest);

		//Accedemos a la tienda asociada al país/pedido (sólo si no estamos ya en ella)
		PageSelTdaMantoStpV.selectTienda(this.almacenEspanya, this.codigoEspanya, this.dMantoAcc.appE, dFTest);
		
		PageMenusMantoStpV.goToOrdenadorDePrendas(dFTest);
		PageOrdenacionDePrendasStpV.mantoOrdenacionInicio(dFTest);
		PageOrdenacionDePrendasStpV.mantoSeccionPrendas(dFTest);
		PageOrdenacionDePrendasStpV.ordenacionModal(dFTest);		
	}
}


