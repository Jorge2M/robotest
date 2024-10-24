package com.mng.robotest.tests.domains.manto.tests;

import java.util.Arrays;
import java.util.List;

import com.mng.robotest.tests.domains.base.TestMantoBase;
import com.mng.robotest.tests.domains.manto.steps.PageConsultaIdEansSteps;
import com.mng.robotest.tests.domains.manto.steps.PageMenusMantoSteps;
import com.mng.robotest.testslegacy.datastored.DataPedido;

public class Man001 extends TestMantoBase {

	@Override
	public void execute() {
		accesoAlmacenEspanya();
		var dataPedido = searchPedido();
		gotoIdEans();
		consultaDatosPedido(Arrays.asList(dataPedido.getCodigoPedidoManto()));
		consultaDatosEanArticulosPedido(dataPedido);
	}
	
	private void gotoIdEans() {
	    new PageMenusMantoSteps().goToIdEans();
	}
	
	private void consultaDatosPedido(List<String> pedidos) {
		var pageConsultaIdEansSteps = new PageConsultaIdEansSteps();
		pageConsultaIdEansSteps.consultaDatosContacto(pedidos);
		pageConsultaIdEansSteps.consultaIdentificadoresPedido(pedidos);
		pageConsultaIdEansSteps.consultaTrackings(pedidos);
	}
	
	private void consultaDatosEanArticulosPedido(DataPedido dataPedido) {
		List<String> articulos = dataPedido.getDataBag().getListArticulos().stream()
				.map(a -> a.getReferencia().toString()).toList();
		
		new PageConsultaIdEansSteps().consultaDatosEan(articulos);		
	}

}
