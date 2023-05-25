package com.mng.robotest.test.steps.navigations.manto;

import java.util.List;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.base.StepMantoBase;
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
import com.mng.robotest.test.datastored.DataCheckPedidos;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.datastored.DataCheckPedidos.CheckPedido;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.domain.InputParamsTM.TypeAccess;

public class PedidoNavigations extends StepMantoBase {

	public void testPedidosShopEnManto(DataCheckPedidos dataCheckPedidos) 
			throws Exception {
		//En el caso de Votf se ha de realizar un paso manual para que los pedidos aparezcan en Manto
		if (app!=AppEcom.votf) {  
			if (dataCheckPedidos.areChecksToExecute() && 
				inputParamsSuite.getTypeAccess()!=TypeAccess.Bat) {
				new PageLoginMantoSteps().login();
				validacionListPedidosStepss(dataCheckPedidos);
			}
		}
	}
	
	private void validacionListPedidosStepss(DataCheckPedidos dataCheckPedidos) {
		List<CheckPedido> listChecks = dataCheckPedidos.getListChecks();
		for (DataPedido dataPedido : dataCheckPedidos.getListPedidos()) {
			if (dataPedido.isResultadoOk()) {
				try {
					validaPedidoSteps(dataPedido, listChecks);
				}
				catch (Exception e) {
					Log4jTM.getLogger().warn("Problem in validation of Pedido", e);
				}	  
			}
		}
	}	
	
	public void validaPedidoSteps(DataPedido dataPedido, List<CheckPedido> listChecks) {
		new PageSelTdaMantoSteps().selectTienda(dataPedido.getCodigoAlmacen(), dataPedido.getCodigoPais());
		if (listChecks.contains(CheckPedido.CONSULTAR_BOLSA)) {
			consultarBolsaSteps(dataPedido);
		}
		
		if (app!=AppEcom.votf) {
			if (listChecks.contains(CheckPedido.CONSULTAR_PEDIDO)) {
				consultarPedidoSteps(dataPedido);	
			}
			
			if (listChecks.contains(CheckPedido.ANULAR)) {
				anularPedidoSteps(dataPedido);
			}
		}
	}
	
	private void consultarBolsaSteps(DataPedido dataPedido) {
		new PageMenusMantoSteps().goToBolsas();
		new SecFiltrosMantoSteps().setFiltrosYbuscar(dataPedido, TypeSearch.BOLSA);
		boolean existLinkPedido = new PageBolsasMantoSteps().validaLineaBolsa(dataPedido).getExistsLinkCodPed();
		if (existLinkPedido) {
			new PageConsultaPedidoBolsaSteps().detalleFromListaPedBol(dataPedido, TypeDetalle.BOLSA);
		}
	}
	
	private void consultarPedidoSteps(DataPedido dataPedido) {
		new PageMenusMantoSteps().goToPedidosStep();
		new SecFiltrosMantoSteps().setFiltrosYbuscar(dataPedido, TypeSearch.PEDIDO);
		boolean existLinkPedido = new PagePedidosMantoSteps().validaLineaPedido(dataPedido).getExistsLinkCodPed();
		if (existLinkPedido) { 
			new PageConsultaPedidoBolsaSteps().detalleFromListaPedBol(dataPedido, TypeDetalle.PEDIDO);
		}
	}
	
	private void anularPedidoSteps(DataPedido dataPedido) {
		if (!new PageDetallePedido().isPage(dataPedido.getCodigoPedidoManto())) {
			consultarPedidoSteps(dataPedido);
		}
		
		new PageConsultaPedidoBolsaSteps().clickButtonIrAGenerar(dataPedido.getCodigoPedidoManto());
		new PageGenerarPedidoSteps().anulaPedido();
	}
}
