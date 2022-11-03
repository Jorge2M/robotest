package com.mng.robotest.domains.compra.tests;

import java.util.Arrays;

import com.mng.robotest.domains.compra.beans.ConfigCheckout;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.BuilderCheckout;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.utils.PaisGetter;


public class Com003 extends TestBase {

	private final ConfigCheckout configCheckout = ConfigCheckout.config().emaiExists().build(); 
	
	@Override
	public void execute() throws Exception {
		DataPago dataPago = getDataPago(configCheckout);
		dataPago = new BuilderCheckout(dataPago)
			.finalCountrys(Arrays.asList(PaisGetter.from(PaisShop.FRANCE)))
			.build()
			.checkout(From.PREHOME);		
	}

}
