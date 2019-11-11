package com.mng.sapfiori.test.testcase.stpv.pedidos;

import com.mng.sapfiori.test.testcase.webobject.pedidos.PagePosSolicitudPedido;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.conf.State;

public class PagePosSolicitudPedidoStpV {

	private final PagePosSolicitudPedido pageObject;

	private PagePosSolicitudPedidoStpV(PagePosSolicitudPedido pageObject) {
		this.pageObject = pageObject;
	}
	public static PagePosSolicitudPedidoStpV getNew(PagePosSolicitudPedido pageObject) {
		return new PagePosSolicitudPedidoStpV(pageObject);
	}
	
	@Validation (
		description=
			"Aparece la página con título <b>" + PagePosSolicitudPedido.TitlePage + "</b> " + 
			"(la esperamos hasta #{maxSeconds} segundos)",
		level=State.Defect)
	public boolean checkIsPage(int maxSeconds) throws Exception {
		return (pageObject.checkIsPage(maxSeconds));
	}
}
