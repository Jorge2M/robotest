package com.mng.sapfiori.access.test.testcase.webobject.pedidos;

public class InfoGeneralSolPedido {
	public String descrSolPedido;
	public String claseDocumento;
	public boolean detFuenteAprov;
	
	@Override
	public String toString() {
		return ( 
			"Descripción de solicitud de pedido:" + descrSolPedido + "<br>" +
			"Clase documento: " + claseDocumento + "<br>" +
			"Det. fuente aprovisionamiento: " + detFuenteAprov + "<br>"
		);
	}
}
