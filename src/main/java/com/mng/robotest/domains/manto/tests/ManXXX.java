package com.mng.robotest.domains.manto.tests;

import java.util.List;

import com.mng.robotest.domains.base.TestMantoBase;
import com.mng.robotest.domains.compra.tests.CompraSteps;
import com.mng.robotest.test.datastored.DataPedido;

public class ManXXX extends TestMantoBase {

	private final List<DataPedido> listPedidos;
	
	private static final String URL_MANTO_DEFAULT = "https://manto.pre.mango.com";
	
	public ManXXX(List<DataPedido> listPedidos) {
		this.listPedidos = listPedidos;
	}
	
	@Override
	public void execute() throws Exception {
		setUrlMantoParameter();
		new CompraSteps().checkPedidosManto(listPedidos);
	}
	
	private void setUrlMantoParameter() {
		if (inputParamsSuite.getUrlManto()==null) {
			inputParamsSuite.setUrlManto(URL_MANTO_DEFAULT);
		}
		dataMantoTest.setUrlManto(inputParamsSuite.getUrlManto());
	}

}
