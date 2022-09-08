package com.mng.robotest.test.steps.navigations.manto;

import java.util.List;
import java.util.Optional;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.data.Constantes;
import com.mng.robotest.test.datastored.DataCheckPedidos;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.datastored.DataCheckPedidos.CheckPedido;
import com.mng.robotest.test.exceptions.NotFoundException;
import com.mng.robotest.test.pageobject.manto.pedido.PageDetallePedido;
import com.mng.robotest.test.pageobject.manto.pedido.PagePedidos.TypeDetalle;
import com.mng.robotest.test.steps.manto.DataMantoAccess;
import com.mng.robotest.test.steps.manto.PageBolsasMantoSteps;
import com.mng.robotest.test.steps.manto.PageLoginMantoSteps;
import com.mng.robotest.test.steps.manto.PageMenusMantoSteps;
import com.mng.robotest.test.steps.manto.PageSelTdaMantoSteps;
import com.mng.robotest.test.steps.manto.SecFiltrosMantoSteps;
import com.mng.robotest.test.steps.manto.SecFiltrosMantoSteps.TypeSearch;
import com.mng.robotest.test.steps.manto.pedido.PageConsultaPedidoBolsaSteps;
import com.mng.robotest.test.steps.manto.pedido.PageGenerarPedidoSteps;
import com.mng.robotest.test.steps.manto.pedido.PagePedidosMantoSteps;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.domain.InputParamsTM.TypeAccess;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.github.jorge2m.testmaker.domain.suitetree.TestRunTM;
import com.github.jorge2m.testmaker.service.TestMaker;

public class PedidoNavigations {

	private PedidoNavigations() {}
	
	public static void testPedidosEnManto(DataCheckPedidos dataCheckPedidos, AppEcom appE, WebDriver driver) 
			throws Exception {
		//En el caso de Votf se ha de realizar un paso manual para que los pedidos aparezcan en Manto
		if (appE!=AppEcom.votf) {  
			TestRunTM testRun = getTestCase().getTestRunParent();
			DataMantoAccess dMantoAcc = new DataMantoAccess();
			dMantoAcc.urlManto = testRun.getParameter(Constantes.paramUrlmanto);
			dMantoAcc.userManto = testRun.getParameter(Constantes.paramUsrmanto);
			dMantoAcc.passManto = testRun.getParameter(Constantes.paramPasmanto);
			dMantoAcc.appE = appE;
			testPedidosEnManto(dMantoAcc, dataCheckPedidos, driver);
		}
	}
	
	private static TestCaseTM getTestCase() throws NotFoundException {
		Optional<TestCaseTM> testCaseOpt = TestMaker.getTestCase();
		if (testCaseOpt.isEmpty()) {
		  throw new NotFoundException("Not found TestCase");
		}
		return testCaseOpt.get();
	}
	
	private static void testPedidosEnManto(DataMantoAccess dMantoAcc, DataCheckPedidos dataCheckPedidos, WebDriver driver) 
	throws Exception {
		TypeAccess typeAccess = ((InputParamsMango)TestMaker.getInputParamsSuite()).getTypeAccess();
		if (dataCheckPedidos.areChecksToExecute() && typeAccess!=TypeAccess.Bat) {
			PageLoginMantoSteps.login(dMantoAcc.urlManto, dMantoAcc.userManto, dMantoAcc.passManto, driver);
			PedidoNavigations.validacionListPedidosStepss(dataCheckPedidos, dMantoAcc.appE, driver);
		}
	}
	
	public static void validacionListPedidosStepss(DataCheckPedidos dataCheckPedidos, AppEcom appE, WebDriver driver) 
	throws Exception {
		List<CheckPedido> listChecks = dataCheckPedidos.getListChecks();
		for (DataPedido dataPedido : dataCheckPedidos.getListPedidos()) {
			if (dataPedido.isResultadoOk()) {
				try {
					validaPedidoStepss(dataPedido, listChecks, appE, driver);
				}
				catch (Exception e) {
					Log4jTM.getLogger().warn("Problem in validation of Pedido", e);
				}	  
			}
		}
	}	
	
	public static void validaPedidoStepss(DataPedido dataPedido, List<CheckPedido> listChecks, AppEcom app, WebDriver driver) 
			throws Exception {
		new PageSelTdaMantoSteps().selectTienda(dataPedido.getCodigoAlmacen(), dataPedido.getCodigoPais());
		if (listChecks.contains(CheckPedido.consultarBolsa)) {
			consultarBolsaSteps(dataPedido, app, driver);
		}
		
		if (app!=AppEcom.votf) {
			if (listChecks.contains(CheckPedido.consultarPedido)) {
				consultarPedidoSteps(dataPedido, app, driver);	
			}
			
			if (listChecks.contains(CheckPedido.anular)) {
				anularPedidoSteps(dataPedido, app, driver);
			}
		}
		
		if (listChecks.contains(CheckPedido.consultarBolsa)) {
			
		}
	}
	
	private static void consultarBolsaSteps(DataPedido dataPedido, AppEcom app, WebDriver driver) throws Exception {
		new PageMenusMantoSteps().goToBolsas();
		new SecFiltrosMantoSteps(driver).setFiltrosYbuscar(dataPedido, TypeSearch.BOLSA);
		boolean existLinkPedido = new PageBolsasMantoSteps().validaLineaBolsa(dataPedido).getExistsLinkCodPed();
		if (existLinkPedido) {
			new PageConsultaPedidoBolsaSteps().detalleFromListaPedBol(dataPedido, TypeDetalle.BOLSA);
		}
	}
	
	private static void consultarPedidoSteps(DataPedido dataPedido, AppEcom app, WebDriver driver) throws Exception {
		new PageMenusMantoSteps().goToPedidos();
		new SecFiltrosMantoSteps(driver).setFiltrosYbuscar(dataPedido, TypeSearch.PEDIDO);
		boolean existLinkPedido = new PagePedidosMantoSteps().validaLineaPedido(dataPedido).getExistsLinkCodPed();
		if (existLinkPedido) { 
			new PageConsultaPedidoBolsaSteps().detalleFromListaPedBol(dataPedido, TypeDetalle.PEDIDO);
		}
	}
	
	private static void anularPedidoSteps(DataPedido dataPedido, AppEcom app, WebDriver driver) throws Exception {
		if (!new PageDetallePedido().isPage(dataPedido.getCodigoPedidoManto())) {
			consultarPedidoSteps(dataPedido, app, driver);
		}
		
		new PageConsultaPedidoBolsaSteps().clickButtonIrAGenerar(dataPedido.getCodigoPedidoManto());
		new PageGenerarPedidoSteps().anulaPedido();
	}
}