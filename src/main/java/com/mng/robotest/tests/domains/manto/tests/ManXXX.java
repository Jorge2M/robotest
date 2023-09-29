package com.mng.robotest.tests.domains.manto.tests;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.tests.domains.base.TestMantoBase;
import com.mng.robotest.tests.domains.compra.tests.CompraSteps;
import com.mng.robotest.testslegacy.datastored.DataPedido;
import com.mng.robotest.testslegacy.datastored.DataCheckPedidos.CheckPedido;

public class ManXXX extends TestMantoBase {

	private final List<DataPedido> listPedidos;
	private final List<CheckPedido> listChecks;
	
	private static final String URL_MANTO_DEFAULT = "https://manto.pre.mango.com";
	
	public ManXXX(List<DataPedido> listPedidos) {
		this.listPedidos = listPedidos;
		this.listChecks = new ArrayList<>();
	}
	
	public ManXXX(List<CheckPedido> listChecks, List<DataPedido> listPedidos) {
		this.listPedidos = listPedidos;
		this.listChecks = listChecks;
	}	
	
	@Override
	public void execute() throws Exception {
		setUrlMantoParameter();
		new CompraSteps().checkPedidosManto(listChecks, listPedidos);
	}
	
	private void setUrlMantoParameter() {
		if (inputParamsSuite.getUrlManto()==null) {
			inputParamsSuite.setUrlManto(URL_MANTO_DEFAULT);
		}
		dataMantoTest.setUrlManto(inputParamsSuite.getUrlManto());
	}

}
