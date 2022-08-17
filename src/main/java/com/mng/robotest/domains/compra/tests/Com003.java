package com.mng.robotest.domains.compra.tests;

import java.util.Arrays;

import com.mng.robotest.domains.compra.beans.ConfigCheckout;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.BuilderCheckout;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.utils.PaisGetter;


public class Com003 extends TestBase {

	private final ConfigCheckout configCheckout = ConfigCheckout.config().emaiExists().build(); 
	
	@Override
	public void execute() throws Exception {
		DataCtxPago dCtxPago = new DataCtxPago(dataTest, configCheckout);
		dCtxPago = new BuilderCheckout(dataTest, dCtxPago)
			.finalCountrys(Arrays.asList(PaisGetter.get(PaisShop.FRANCE)))
			.build()
			.checkout(From.PREHOME);		
	}

}
