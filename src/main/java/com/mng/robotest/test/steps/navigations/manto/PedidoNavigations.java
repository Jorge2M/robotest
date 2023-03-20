package com.mng.robotest.test.steps.navigations.manto;

import java.util.List;
import java.util.Optional;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.base.datatest.DataMantoTest;
import com.mng.robotest.domains.manto.pageobjects.PageDetallePedido;
import com.mng.robotest.domains.manto.pageobjects.PagePedidos.TypeDetalle;
import com.mng.robotest.domains.manto.steps.PageBolsasMantoSteps;
import com.mng.robotest.domains.manto.steps.PageLoginMantoSteps;
import com.mng.robotest.domains.manto.steps.PageMenusMantoSteps;
import com.mng.robotest.domains.manto.steps.PagePedidosMantoSteps;
import com.mng.robotest.domains.manto.steps.PageSelTdaMantoSteps;
import com.mng.robotest.domains.manto.steps.SecFiltrosMantoSteps;
import com.mng.robotest.domains.manto.steps.SecFiltrosMantoSteps.TypeSearch;
import com.mng.robotest.domains.manto.steps.pedidos.PageConsultaPedidoBolsaSteps;
import com.mng.robotest.domains.manto.steps.pedidos.PageGenerarPedidoSteps;
import com.mng.robotest.test.data.Constantes;
import com.mng.robotest.test.datastored.DataCheckPedidos;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.datastored.DataCheckPedidos.CheckPedido;
import com.mng.robotest.test.exceptions.NotFoundException;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.domain.InputParamsTM.TypeAccess;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.github.jorge2m.testmaker.domain.suitetree.TestRunTM;
import com.github.jorge2m.testmaker.service.TestMaker;

public class PedidoNavigations {

	private PedidoNavigations() {}
	
	public static void testPedidosEnManto(DataCheckPedidos dataCheckPedidos, AppEcom appE, WebDriver driver) 
			throws Exception {
		//TODO activar cuando funcione manto en k8s-toos-pro (pdt vuelva Carlos)
		if (false) {
			//En el caso de Votf se ha de realizar un paso manual para que los pedidos aparezcan en Manto
			if (appE!=AppEcom.votf) {  
				TestRunTM testRun = getTestCase().getTestRunParent();
				DataMantoTest dMantoAcc = DataMantoTest.make();
				dMantoAcc.setUrlManto(testRun.getParameter(Constantes.PARAM_URL_MANTO));
				dMantoAcc.setUserManto(testRun.getParameter(Constantes.PARAM_USR_MANTO));
				dMantoAcc.setPassManto(testRun.getParameter(Constantes.PARAM_PAS_MANTO));
				dMantoAcc.setAppE(appE);
				testPedidosEnManto(dMantoAcc, dataCheckPedidos, driver);
			}
		}
	}
	
	private static TestCaseTM getTestCase() throws NotFoundException {
		Optional<TestCaseTM> testCaseOpt = TestMaker.getTestCase();
		if (!testCaseOpt.isPresent()) {
		  throw new NotFoundException("Not found TestCase");
		}
		return testCaseOpt.get();
	}
	
	private static void testPedidosEnManto(DataMantoTest dMantoAcc, DataCheckPedidos dataCheckPedidos, WebDriver driver) {
		TypeAccess typeAccess = ((InputParamsMango)TestMaker.getInputParamsSuite()).getTypeAccess();
		if (dataCheckPedidos.areChecksToExecute() && typeAccess!=TypeAccess.Bat) {
			new PageLoginMantoSteps().login();
			PedidoNavigations.validacionListPedidosStepss(dataCheckPedidos, dMantoAcc.getAppE(), driver);
		}
	}
	
	public static void validacionListPedidosStepss(DataCheckPedidos dataCheckPedidos, AppEcom appE, WebDriver driver) {
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
	
	public static void validaPedidoStepss(
			DataPedido dataPedido, List<CheckPedido> listChecks, AppEcom app, WebDriver driver) {
		new PageSelTdaMantoSteps().selectTienda(dataPedido.getCodigoAlmacen(), dataPedido.getCodigoPais());
		if (listChecks.contains(CheckPedido.CONSULTAR_BOLSA)) {
			consultarBolsaSteps(dataPedido, driver);
		}
		
		if (app!=AppEcom.votf) {
			if (listChecks.contains(CheckPedido.CONSULTAR_PEDIDO)) {
				consultarPedidoSteps(dataPedido, driver);	
			}
			
			if (listChecks.contains(CheckPedido.ANULAR)) {
				anularPedidoSteps(dataPedido, app, driver);
			}
		}
		
		if (listChecks.contains(CheckPedido.CONSULTAR_BOLSA)) {
			
		}
	}
	
	private static void consultarBolsaSteps(DataPedido dataPedido, WebDriver driver) {
		new PageMenusMantoSteps().goToBolsas();
		new SecFiltrosMantoSteps().setFiltrosYbuscar(dataPedido, TypeSearch.BOLSA);
		boolean existLinkPedido = new PageBolsasMantoSteps().validaLineaBolsa(dataPedido).getExistsLinkCodPed();
		if (existLinkPedido) {
			new PageConsultaPedidoBolsaSteps().detalleFromListaPedBol(dataPedido, TypeDetalle.BOLSA);
		}
	}
	
	private static void consultarPedidoSteps(DataPedido dataPedido, WebDriver driver) {
		new PageMenusMantoSteps().goToPedidosStep();
		new SecFiltrosMantoSteps().setFiltrosYbuscar(dataPedido, TypeSearch.PEDIDO);
		boolean existLinkPedido = new PagePedidosMantoSteps().validaLineaPedido(dataPedido).getExistsLinkCodPed();
		if (existLinkPedido) { 
			new PageConsultaPedidoBolsaSteps().detalleFromListaPedBol(dataPedido, TypeDetalle.PEDIDO);
		}
	}
	
	private static void anularPedidoSteps(DataPedido dataPedido, AppEcom app, WebDriver driver) {
		if (!new PageDetallePedido().isPage(dataPedido.getCodigoPedidoManto())) {
			consultarPedidoSteps(dataPedido, driver);
		}
		
		new PageConsultaPedidoBolsaSteps().clickButtonIrAGenerar(dataPedido.getCodigoPedidoManto());
		new PageGenerarPedidoSteps().anulaPedido();
	}
}
