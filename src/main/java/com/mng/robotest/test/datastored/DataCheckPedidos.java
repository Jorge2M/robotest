package com.mng.robotest.test.datastored;

import java.util.List;


public class DataCheckPedidos {

	public enum CheckPedido { CONSULTAR_BOLSA, CONSULTAR_PEDIDO, ANULAR }
	
	private final List<DataPedido> listPedidos;
	private final List<CheckPedido> listChecks;
	
	private DataCheckPedidos(List<DataPedido> listPedidos, List<CheckPedido> listChecks) {
		this.listPedidos = listPedidos;
		this.listChecks = listChecks;
	}
	
	public static DataCheckPedidos newInstance(List<DataPedido> listPedidos, List<CheckPedido> listChecks) {
		return (new DataCheckPedidos(listPedidos, listChecks));
	}
	
	public List<DataPedido> getListPedidos() {
		return this.listPedidos;
	}
	
	public List<CheckPedido> getListChecks() {
		return this.listChecks;
	}
	
	public boolean areChecksToExecute() {
		return (
			listPedidos!=null && !listPedidos.isEmpty() &&
			listChecks!=null && !listChecks.isEmpty());
	}
}
