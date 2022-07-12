package com.mng.robotest.test.datastored;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import com.mng.robotest.test.beans.Pago;
import com.mng.robotest.test.data.DataCtxShop;


public class DataCtxPago {

	FlagsTestCkout FTCkout;
	DataPedido dataPedido;
	CopyOnWriteArrayList<DataPedido> listPedidos = new CopyOnWriteArrayList<>();
	boolean userWithStoreC = false;
	float saldoCta = 0;
	boolean okCheckChar;
	Map<String, String> datosRegistro = new HashMap<>();
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	public DataCtxPago(DataCtxShop dCtxSh) {
		this.dataPedido = new DataPedido(dCtxSh.pais);
	}
	
	public FlagsTestCkout getFTCkout() {
		return this.FTCkout;
	}
	
	public void setFTCkout(FlagsTestCkout FTCkout) {
		this.FTCkout = FTCkout;
	}
	
	public DataPedido getDataPedido() {
		return this.dataPedido;
	}
	
	public void setDataPedido(DataPedido dataPedido) {
		this.dataPedido = dataPedido;
	}
	
	public boolean getUserWithStoreC() {
		return this.userWithStoreC;
	}
	
	public void setUserWithStoreC(boolean userWithStoreC) {
		this.userWithStoreC = userWithStoreC;
	}
	
	public boolean getOkCheckChar() {
		return this.okCheckChar;
	}
	
	public void setOkCheckChar(boolean okCheckChar) {
		this.okCheckChar = okCheckChar;
	}
	
	public Map<String, String> getDatosRegistro() {
		return this.datosRegistro;
	}
	
	public void setDatosRegistro(Map<String, String> datosRegistro) {
		this.datosRegistro = datosRegistro;
	}	
	
	public float getSaldoCta() {
		return this.saldoCta;
	}
		
	public void setSaldoCta(float saldoCta) {
		this.saldoCta = saldoCta;
	}
	
	public CopyOnWriteArrayList<DataPedido> getListPedidos() {
		return this.listPedidos;
	}
	
	public void storePedidoForManto() {
		this.listPedidos.add(getDataPedido());
	}
	
	public boolean isPaymentExecuted(Pago pago) {
		for (DataPedido dataPedido : listPedidos) {
			if (dataPedido.getPago()==pago) {
				if ("".compareTo(dataPedido.getCodpedido())!=0) {
					return true;
				}
			}
		}
		return false;
	}
}
