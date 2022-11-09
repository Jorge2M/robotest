package com.mng.robotest.test.steps.navigations.manto;

import java.util.List;
import java.util.Optional;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.InputParamsTM.TypeAccess;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.github.jorge2m.testmaker.domain.suitetree.TestRunTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.data.Constantes;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.exceptions.NotFoundException;
import com.mng.robotest.test.pageobject.manto.pedido.PageDetallePedido;
import com.mng.robotest.test.pageobject.manto.pedido.PagePedidos.TypeDetalle;
import com.mng.robotest.test.steps.manto.DataMantoAccess;
import com.mng.robotest.test.steps.manto.PageBolsasMantoSteps;
import com.mng.robotest.test.steps.manto.PageConsultaPedidoBolsaSteps;
import com.mng.robotest.test.steps.manto.PageLoginMantoSteps;
import com.mng.robotest.test.steps.manto.PageMenusMantoSteps;
import com.mng.robotest.test.steps.manto.PagePedidosMantoSteps;
import com.mng.robotest.test.steps.manto.PageSelTdaMantoSteps;
import com.mng.robotest.test.steps.manto.SecFiltrosMantoSteps;
import com.mng.robotest.test.steps.manto.SecFiltrosMantoSteps.TypeSearch;

public class PedidosNavigations {

	public static void testPedidosEnManto(List<DataPedido> listPedidos, AppEcom appE, WebDriver driver)
			throws Exception {
		//En el caso de Votf se ha de realizar un paso manual para que los pedidos aparezcan en Manto
		if (appE!=AppEcom.votf) {  
			TestCaseTM testCase = getTestCase();
			TestRunTM testRun = testCase.getTestRunParent();
			DataMantoAccess dMantoAcc = new DataMantoAccess();
			dMantoAcc.setUrlManto(testRun.getParameter(Constantes.PARAM_URL_MANTO));
			dMantoAcc.setUserManto(testRun.getParameter(Constantes.PARAM_USR_MANTO));
			dMantoAcc.setPassManto(testRun.getParameter(Constantes.PARAM_PAS_MANTO));
			dMantoAcc.setAppE(appE);
			testPedidosEnManto(dMantoAcc, listPedidos, driver);
		}
	}
	
	private static TestCaseTM getTestCase() throws NotFoundException {
		Optional<TestCaseTM> testCaseOpt = TestMaker.getTestCase();
		if (!testCaseOpt.isPresent()) {
		  throw new NotFoundException("Not found TestCase");
		}
		return testCaseOpt.get();
	}
	
	private static void testPedidosEnManto(DataMantoAccess dMantoAcc, List<DataPedido> listPedidos, WebDriver driver) 
	throws Exception {
		TypeAccess typeAccess = ((InputParamsMango)TestMaker.getInputParamsSuite()).getTypeAccess();
		if (typeAccess==TypeAccess.Bat) {
			return;
		}

		//Si existen pedidos que validar y no se trata de un acceso desde la línea de comandos (típicamente .bat)
		if (listPedidos!=null && listPedidos.size()>0 && typeAccess!=TypeAccess.Bat) {
			PageLoginMantoSteps.login(dMantoAcc.getUrlManto(), dMantoAcc.getUserManto(), dMantoAcc.getPassManto(), driver);
			PedidosNavigations.validacionListaPagosStepss(listPedidos, dMantoAcc.getAppE(), driver);
		}
	}
	
	/**
	 * Partiendo de la página de menús, ejecutamos todos los pasos/validaciones para validar una lista de pedidos
	 * @param listPaisPedido lista de pedidos a validar
	 */
	public static void validacionListaPagosStepss(List<DataPedido> listDataPedidos, AppEcom appE, WebDriver driver) {
		//Bucle para obtener la lista de Países -> Pedidos
		for (DataPedido dataPedido : listDataPedidos) {
			//Sólo consultamos el pedido si el pago se realizó de forma correcta
			if (dataPedido.getResejecucion()==State.Ok) {
				try {
					//Ejecutamos todo el flujo de pasos/validaciones para validar un pedido concreto y volvemos a la página de pedidos
					validaPedidoStepss(dataPedido, appE, driver);
				}
				catch (Exception e) {
					Log4jTM.getLogger().warn("Problem in validation of Pedido", e);
				}	  
			}
		}
	}	
	
	public static void validaPedidoStepss(DataPedido dataPedido, AppEcom appE, WebDriver driver) {
		new PageSelTdaMantoSteps().selectTienda(dataPedido.getCodigoAlmacen(), dataPedido.getCodigoPais());
		
		new PageMenusMantoSteps().goToBolsas();
		SecFiltrosMantoSteps secFiltrosMantoSteps = new SecFiltrosMantoSteps(driver);
		secFiltrosMantoSteps.setFiltrosYbuscar(dataPedido, TypeSearch.BOLSA);
		boolean existLinkPedido = new PageBolsasMantoSteps().validaLineaBolsa(dataPedido).getExistsLinkCodPed();
		
		PageConsultaPedidoBolsaSteps pageConsultaPedidoBolsaSteps = new PageConsultaPedidoBolsaSteps();
		if (existLinkPedido) {
			pageConsultaPedidoBolsaSteps.detalleFromListaPedBol(dataPedido, TypeDetalle.BOLSA);
		}
		
		if (appE!=AppEcom.votf) {
			new PageMenusMantoSteps().goToPedidos();
			secFiltrosMantoSteps.setFiltrosYbuscar(dataPedido, TypeSearch.PEDIDO);
			boolean existsLinkCodPed = new PagePedidosMantoSteps().validaLineaPedido(dataPedido).getExistsLinkCodPed();	
			if (existsLinkCodPed) {
				pageConsultaPedidoBolsaSteps.detalleFromListaPedBol(dataPedido, TypeDetalle.PEDIDO);
			}
		}
		
		new PageDetallePedido().gotoListaPedidos();
	}
}
