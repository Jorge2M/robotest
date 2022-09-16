package com.mng.robotest.test.datastored;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class DataCheckPedidos {

	public static enum CheckPedido { CONSULTAR_BOLSA, CONSULTAR_PEDIDO, ANULAR };
	
	private final CopyOnWriteArrayList<DataPedido> listPedidos;
	private final List<CheckPedido> listChecks;
	
	private DataCheckPedidos(CopyOnWriteArrayList<DataPedido> listPedidos, List<CheckPedido> listChecks) {
		this.listPedidos = listPedidos;
		this.listChecks = listChecks;
	}
	
	public static DataCheckPedidos newInstance(CopyOnWriteArrayList<DataPedido> listPedidos, List<CheckPedido> listChecks) {
		return (new DataCheckPedidos(listPedidos, listChecks));
	}
	
	public CopyOnWriteArrayList<DataPedido> getListPedidos() {
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
