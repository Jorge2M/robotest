package com.mng.robotest.tests.domains.compra.tests;

import static com.mng.robotest.testslegacy.data.PaisShop.*;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.compra.beans.ConfigCheckout;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.datastored.DataPago;
import com.mng.robotest.testslegacy.steps.navigations.shop.CheckoutFlow.BuilderCheckout;
import com.mng.robotest.testslegacy.steps.navigations.shop.CheckoutFlow.From;

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
		checkPedidosManto(dataPago.getListPedidos());		
	}

	private void fromPrehomeToCheckout() throws Exception {
		new BuilderCheckout(dataPago)
			.build()
			.checkout(From.PREHOME);
	}
}
