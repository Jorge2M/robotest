package com.mng.robotest.domains.transversal;

import com.mng.robotest.domains.compra.beans.ConfigCheckout;
import com.mng.robotest.test.data.DataTest;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.datastored.DataPedido;

public abstract class TestBase extends StepBase {

	public abstract void execute() throws Exception;
	
	public static final ThreadLocal<DataTest> DATA_TEST = new ThreadLocal<>();
	
	protected TestBase() {
		if (!inputParamsSuite.getListaPaises().isEmpty()) {
			String pais = inputParamsSuite.getListaPaises().get(0);
			dataTest = DataTest.getData(PaisShop.from(pais));
		} else {
			dataTest = DataTest.getData(PaisShop.ESPANA);
		}
		DATA_TEST.set(dataTest);
	}
	
	protected DataPago getDataPago() {
		return getDataPago(ConfigCheckout.config().build());
	}
	
	protected DataPago getDataPago(ConfigCheckout configCheckout) {
		DataPago dataPago = new DataPago(configCheckout);
		DataPedido dataPedido = new DataPedido(dataTest.getPais(), dataTest.getDataBag());
		dataPago.setDataPedido(dataPedido);
		return dataPago;
	}	
}
