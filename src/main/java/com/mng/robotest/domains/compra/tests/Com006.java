package com.mng.robotest.domains.compra.tests;

import com.mng.robotest.domains.compra.beans.ConfigCheckout;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.BuilderCheckout;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;

import static com.mng.robotest.test.data.PaisShop.*;

public class Com006 extends TestBase {

	private static final Pais ITALIA_PAIS = ITALIA.getPais();
	private static final IdiomaPais ITALIANO = ITALIA_PAIS.getListIdiomas().get(0);
	
	private final DataPago dataPago;
	
	public Com006() throws Exception {
		dataTest.setPais(ITALIA_PAIS);
		dataTest.setIdioma(ITALIANO);
		
		ConfigCheckout configCheckout = ConfigCheckout.config()
				.checkMisCompras()
				.emaiExists().build();
		
		dataPago = getDataPago(configCheckout);
	}
	
	@Override
	public void execute() throws Exception {
		fromPrehomeToCheckout();
		checkPedido();		
	}

	private void fromPrehomeToCheckout() throws Exception {
		new BuilderCheckout(dataPago)
			.build()
			.checkout(From.PREHOME);
	}

	private void checkPedido() throws Exception {
		new CompraSteps().checkPedidosManto(dataPago.getListPedidos()); 
	}
}
