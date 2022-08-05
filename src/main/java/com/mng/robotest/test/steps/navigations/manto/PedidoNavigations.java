package com.mng.robotest.test.steps.navigations.manto;

import static com.mng.robotest.test.pageobject.manto.pedido.PageGenerarPedido.EstadoPedido.*;

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

	public static void testPedidosEnManto(DataCheckPedidos dataCheckPedidos, AppEcom appE, WebDriver driver) throws Exception {
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
	
	/**
	 * Partiendo de la página de menús, ejecutamos todos los pasos/validaciones para validar una lista de pedidos
	 * @param listPaisPedido lista de pedidos a validar
	 */
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
	
	/**
	 * Se ejecuta todo el flujo de pasos/validaciones para validar un pedido concreto y volvemos a la página de pedidos
	 */
	public static void validaPedidoStepss(DataPedido dataPedido, List<CheckPedido> listChecks, AppEcom app, WebDriver driver) 
	throws Exception {
		PageSelTdaMantoSteps.selectTienda(dataPedido.getCodigoAlmacen(), dataPedido.getCodigoPais(), app, driver);
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
		PageMenusMantoSteps.goToBolsas(driver);
		new SecFiltrosMantoSteps(driver).setFiltrosYbuscar(dataPedido, TypeSearch.BOLSA);
		boolean existLinkPedido = PageBolsasMantoSteps.validaLineaBolsa(dataPedido, app, driver).getExistsLinkCodPed();
		if (existLinkPedido) {
			PageConsultaPedidoBolsaSteps.detalleFromListaPedBol(dataPedido, TypeDetalle.bolsa, app, driver);
		}
	}
	
	private static void consultarPedidoSteps(DataPedido dataPedido, AppEcom app, WebDriver driver) throws Exception {
		PageMenusMantoSteps.goToPedidos(driver);
		new SecFiltrosMantoSteps(driver).setFiltrosYbuscar(dataPedido, TypeSearch.PEDIDO);
		boolean existLinkPedido = PagePedidosMantoSteps.validaLineaPedido(dataPedido, app, driver).getExistsLinkCodPed();
		if (existLinkPedido) { 
			PageConsultaPedidoBolsaSteps.detalleFromListaPedBol(dataPedido, TypeDetalle.pedido, app, driver);
		}
	}
	
	private static void anularPedidoSteps(DataPedido dataPedido, AppEcom app, WebDriver driver) throws Exception {
		if (!PageDetallePedido.isPage(dataPedido.getCodigoPedidoManto(), driver)) {
			consultarPedidoSteps(dataPedido, app, driver);
		}
		
		PageConsultaPedidoBolsaSteps.clickButtonIrAGenerar(dataPedido.getCodigoPedidoManto(), driver);
		PageGenerarPedidoSteps.changePedidoToEstado(ANULADO, driver);
	}
}
