package com.mng.robotest.domains.manto.tests;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.testng.annotations.Test;

import com.github.jorge2m.testmaker.domain.InputParamsTM;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.conftestmaker.AppEcom;
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

public class Manto {

	private DataMantoAccess dMantoAcc = null;
	private DataPedido dPedidoPrueba;
	
	private String codigoEspanya = "001";
	private String almacenEspanya = "001";

	public void setDataMantoAccess() throws Exception {
		if (dMantoAcc==null) {
			dMantoAcc = new DataMantoAccess();
			var testCase = getTestCase();
			var testRun = testCase.getTestRunParent();
			InputParamsTM inputParams = getTestCase().getInputParamsSuite();
			dMantoAcc.setUrlManto(inputParams.getUrlBase());
			dMantoAcc.setUserManto(testRun.getParameter(Constantes.PARAM_USR_MANTO));
			dMantoAcc.setPassManto(testRun.getParameter(Constantes.PARAM_PAS_MANTO));
			dMantoAcc.setAppE(AppEcom.shop);
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
		
	}

	@Test(
		dependsOnMethods = { "MAN000_GenerarPedidoFicticioMANTO" },
		groups={"Manto", "Canal:desktop_App:all"}, alwaysRun=true, 
		description="Consulta de la información referente a varios pedidos")
	public void MAN002_Consulta_ID_EAN() throws Exception {
		setDataMantoAccess();
		new PageLoginMantoSteps().login(dMantoAcc.getUrlManto(), dMantoAcc.getUserManto(), dMantoAcc.getPassManto());
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
		new PageLoginMantoSteps().login(dMantoAcc.getUrlManto(), dMantoAcc.getUserManto(), dMantoAcc.getPassManto());
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
		new PageLoginMantoSteps().login(dMantoAcc.getUrlManto(), dMantoAcc.getUserManto(), dMantoAcc.getPassManto());
		new PageSelTdaMantoSteps().selectTienda(almacenEspanya, codigoEspanya);
		new PageMenusMantoSteps().goToGestorCheques();

		String mail = "esther.esteve@mango.com";
		String cheque = "204028046151";
		var pageGestChecksSteps = new PageGestorChequesSteps();
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
		new PageLoginMantoSteps().login(dMantoAcc.getUrlManto(), dMantoAcc.getUserManto(), dMantoAcc.getPassManto());
		new PageSelTdaMantoSteps().selectTienda(almacenEspanya, codigoEspanya);
		new PageMenusMantoSteps().goToGestorEstadisticasPedido();
		
		var pageGestorEstadisticasPedidoSteps = new PageGestorEstadisticasPedidoSteps();
		pageGestorEstadisticasPedidoSteps.searchZalandoOrdersInformation();
		pageGestorEstadisticasPedidoSteps.compareLastDayInformation();
	}
	
	@Test(
		groups={"Manto", "Canal:desktop_App:all"}, alwaysRun=true, 
		description="Gestor de consulta y cambio de familia")
	public void MAN007_GestorConsultaCambioFamilia() throws Exception {
		setDataMantoAccess();
		new PageLoginMantoSteps().login(dMantoAcc.getUrlManto(), dMantoAcc.getUserManto(), dMantoAcc.getPassManto());
		new PageSelTdaMantoSteps().selectTienda(almacenEspanya, codigoEspanya);
		new PageMenusMantoSteps().goToGestorConsultaCambioFamilia();
		
		var pageGestorConsultaCambioFamiliaSteps = new PageGestorConsultaCambioFamiliaSteps();
		pageGestorConsultaCambioFamiliaSteps.selectAccesoriosAndClickConsultaPorFamiliaButton();
		pageGestorConsultaCambioFamiliaSteps.clickCambioFamiliaButton();
	}
	
	@Test(
		groups={"Manto", "Canal:desktop_App:all"}, alwaysRun=true, 
		description="Comprueba el correcto funcionamiento del ordenador de prendas")
	public void MAN008_Ordenador_de_Prendas() throws Exception {
		setDataMantoAccess();
		new PageLoginMantoSteps().login(dMantoAcc.getUrlManto(), dMantoAcc.getUserManto(), dMantoAcc.getPassManto());
		new PageSelTdaMantoSteps().selectTienda(almacenEspanya, codigoEspanya);
		new PageMenusMantoSteps().goToOrdenadorDePrendas();
		
		PageOrdenacionDePrendasSteps pageOrdenacionDePrendasSteps = new PageOrdenacionDePrendasSteps();
		pageOrdenacionDePrendasSteps.mantoOrdenacionInicio();
		pageOrdenacionDePrendasSteps.mantoSeccionPrendas();
		pageOrdenacionDePrendasSteps.ordenacionModal();		
	}
}
