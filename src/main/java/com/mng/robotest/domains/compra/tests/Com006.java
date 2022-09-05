package com.mng.robotest.domains.compra.tests;

import com.mng.robotest.domains.compra.beans.ConfigCheckout;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.BuilderCheckout;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.utils.PaisGetter;


public class Com006 extends TestBase {

	private static final Pais ITALIA = PaisGetter.get(PaisShop.ITALIA);
	private static final IdiomaPais ITALIANO = ITALIA.getListIdiomas().get(0);
	
	private final DataPago dataPago;
	
	public Com006() throws Exception {
		dataTest.pais=ITALIA;
		dataTest.idioma=ITALIANO;
		
		ConfigCheckout configCheckout = ConfigCheckout.config()
				.checkMisCompras()
				.emaiExists().build();
		
		dataPago = new DataPago(configCheckout);
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
		CompraCommons.checkPedidosManto(dataPago.getListPedidos(), app, driver); 
	}
}
