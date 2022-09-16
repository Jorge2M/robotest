package com.mng.robotest.domains.transversal;

import com.mng.robotest.domains.compra.beans.ConfigCheckout;
import com.mng.robotest.test.data.DataTest;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.datastored.DataPedido;

public abstract class TestBase extends NavigationBase {

	public abstract void execute() throws Exception;
	
	public static ThreadLocal<DataTest> DATA_TEST = new ThreadLocal<>();
	
	protected TestBase() {
		this.dataTest = DataTest.getData(PaisShop.ESPANA);
		DATA_TEST.set(dataTest);
	}
	
	protected DataPago getDataPago() {
		return getDataPago(ConfigCheckout.config().build());
	}
	
	protected DataPago getDataPago(ConfigCheckout configCheckout) {
		DataPago dataPago = new DataPago(configCheckout);
		DataPedido dataPedido = new DataPedido(dataTest.pais, dataTest.dataBag);
		dataPago.setDataPedido(dataPedido);
		return dataPago;
	}	
}
