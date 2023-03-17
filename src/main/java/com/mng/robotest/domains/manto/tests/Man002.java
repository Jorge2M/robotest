package com.mng.robotest.domains.manto.tests;

import java.util.Arrays;
import java.util.List;

import com.mng.robotest.domains.base.TestMantoBase;
import com.mng.robotest.domains.manto.steps.PageConsultaIdEansSteps;
import com.mng.robotest.domains.manto.steps.PageMenusMantoSteps;
import com.mng.robotest.test.datastored.DataPedido;

public class Man002 extends TestMantoBase {

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
