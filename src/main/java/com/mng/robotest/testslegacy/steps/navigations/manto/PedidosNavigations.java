package com.mng.robotest.testslegacy.steps.navigations.manto;

import java.util.List;
import java.util.Optional;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.domain.InputParamsTM.TypeAccess;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.github.jorge2m.testmaker.domain.suitetree.TestRunTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.service.exceptions.NotFoundException;
import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.base.datatest.DataMantoTest;
import com.mng.robotest.tests.domains.manto.pageobjects.PageDetallePedido;
import com.mng.robotest.tests.domains.manto.pageobjects.PagePedidos.TypeDetalle;
import com.mng.robotest.tests.domains.manto.steps.PageBolsasMantoSteps;
import com.mng.robotest.tests.domains.manto.steps.PageConsultaPedidoBolsaSteps;
import com.mng.robotest.tests.domains.manto.steps.PageLoginMantoSteps;
import com.mng.robotest.tests.domains.manto.steps.PageMenusMantoSteps;
import com.mng.robotest.tests.domains.manto.steps.PageSelTdaMantoSteps;
import com.mng.robotest.tests.domains.manto.steps.SecFiltrosMantoSteps;
import com.mng.robotest.tests.domains.manto.steps.SecFiltrosMantoSteps.TypeSearch;
import com.mng.robotest.tests.domains.manto.steps.pedidos.PagePedidosMantoSteps;
import com.mng.robotest.testslegacy.data.Constantes;
import com.mng.robotest.testslegacy.datastored.DataPedido;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PedidosNavigations extends StepBase {

	public void testPedidosEnManto(List<DataPedido> listPedidos) throws Exception {
		TestCaseTM testCase = getTestCase();
		TestRunTM testRun = testCase.getTestRunParent();
		DataMantoTest dMantoAcc = DataMantoTest.make();
		dMantoAcc.setUrlManto(testRun.getParameter(Constantes.PARAM_URL_MANTO));
		dMantoAcc.setUserManto(testRun.getParameter(Constantes.PARAM_USR_MANTO));
		dMantoAcc.setPassManto(testRun.getParameter(Constantes.PARAM_PAS_MANTO));
		dMantoAcc.setAppE(app);
		testPedidosManto(listPedidos);
	}
	
	private TestCaseTM getTestCase() throws NotFoundException {
		Optional<TestCaseTM> testCaseOpt = TestMaker.getTestCase();
		if (!testCaseOpt.isPresent()) {
		  throw new NotFoundException("Not found TestCase");
		}
		return testCaseOpt.get();
	}
	
	private void testPedidosManto(List<DataPedido> listPedidos) {
		TypeAccess typeAccess = ((InputParamsMango)TestMaker.getInputParamsSuite()).getTypeAccess();
		if (typeAccess==TypeAccess.Bat) {
			return;
		}

		//Si existen pedidos que validar y no se trata de un acceso desde la línea de comandos (típicamente .bat)
		if (listPedidos!=null && !listPedidos.isEmpty()) {
			new PageLoginMantoSteps().login();
			new PedidosNavigations().validacionListaPagosStepss(listPedidos);
		}
	}
	
	/**
	 * Partiendo de la página de menús, ejecutamos todos los pasos/validaciones para validar una lista de pedidos
	 * @param listPaisPedido lista de pedidos a validar
	 */
	public void validacionListaPagosStepss(List<DataPedido> listDataPedidos) {
		//Bucle para obtener la lista de Países -> Pedidos
		for (DataPedido dataPedido : listDataPedidos) {
			//Sólo consultamos el pedido si el pago se realizó de forma correcta
			if (dataPedido.getResejecucion()==OK) {
				try {
					//Ejecutamos todo el flujo de pasos/validaciones para validar un pedido concreto y volvemos a la página de pedidos
					validaPedidoStepss(dataPedido);
				}
				catch (Exception e) {
					Log4jTM.getLogger().warn("Problem in validation of Pedido", e);
				}	  
			}
		}
	}	
	
	public void validaPedidoStepss(DataPedido dataPedido) {
		new PageSelTdaMantoSteps().selectTienda(dataPedido.getCodigoAlmacen(), dataPedido.getCodigoPais());
		
		new PageMenusMantoSteps().goToBolsas();
		var secFiltrosMantoSteps = new SecFiltrosMantoSteps();
		secFiltrosMantoSteps.setFiltrosYbuscar(dataPedido, TypeSearch.BOLSA);
		boolean existLinkPedido = new PageBolsasMantoSteps().validaLineaBolsa(dataPedido).getExistsLinkCodPed();
		
		var pageConsultaPedidoBolsaSteps = new PageConsultaPedidoBolsaSteps();
		if (existLinkPedido) {
			pageConsultaPedidoBolsaSteps.detalleFromListaPedBol(dataPedido, TypeDetalle.BOLSA);
		}
		
		new PageMenusMantoSteps().goToPedidosStep();
		secFiltrosMantoSteps.setFiltrosYbuscar(dataPedido, TypeSearch.PEDIDO);
		boolean existsLinkCodPed = new PagePedidosMantoSteps().validaLineaPedido(dataPedido).getExistsLinkCodPed();	
		if (existsLinkCodPed) {
			pageConsultaPedidoBolsaSteps.detalleFromListaPedBol(dataPedido, TypeDetalle.PEDIDO);
		}
		
		new PageDetallePedido().gotoListaPedidos();
	}
}
