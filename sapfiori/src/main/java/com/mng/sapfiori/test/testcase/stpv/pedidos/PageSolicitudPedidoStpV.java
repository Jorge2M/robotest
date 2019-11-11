package com.mng.sapfiori.test.testcase.stpv.pedidos;

import com.mng.sapfiori.test.testcase.webobject.pedidos.PageSolicitudPedido;
import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.conf.State;

public class PageSolicitudPedidoStpV {

	private final PageSolicitudPedido pageObject;

	
	private PageSolicitudPedidoStpV(PageSolicitudPedido pageObject) {
		this.pageObject = pageObject;
	}
	public static PageSolicitudPedidoStpV getNew(PageSolicitudPedido pageObject) {
		return new PageSolicitudPedidoStpV(pageObject);
	}
	
	@Validation (
		description=
			"Aparece la página con título <b>" + PageSolicitudPedido.TitlePage + "</b> " + 
			"(la esperamos hasta #{maxSeconds} segundos)",
		level=State.Defect)
	public boolean checkIsPage(int maxSeconds) {
		return pageObject.checkIsPage(maxSeconds);
	}
	
	@Step (
		description="Selecciona el icono de + para añadir articulos y seleccionar <b>#{concepto}</b>",
		expected="Aparece la página de \"Posición de solicitud de pedido\"")
	public PagePosSolicitudPedidoStpV añadirArticulo(String concepto) throws Exception {
		PagePosSolicitudPedidoStpV pagePosSolPedidoStpV = 
			PagePosSolicitudPedidoStpV.getNew(
				pageObject.añadirArticulo(concepto));
				
		pagePosSolPedidoStpV.checkIsPage(5);
		return pagePosSolPedidoStpV;
	}
	
}
