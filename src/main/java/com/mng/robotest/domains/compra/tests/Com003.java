package com.mng.robotest.domains.compra.tests;

import java.util.Arrays;

import com.mng.robotest.domains.base.TestBase;
import com.mng.robotest.domains.compra.beans.ConfigCheckout;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.BuilderCheckout;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;

import static com.mng.robotest.test.data.PaisShop.*;

public class Com003 extends TestBase {

	private final ConfigCheckout configCheckout = ConfigCheckout.config().emaiExists().build(); 
	
	@Override
	public void execute() throws Exception {
		new BuilderCheckout(getDataPago(configCheckout))
			.finalCountrys(Arrays.asList(FRANCE.getPais()))
			.build()
			.checkout(From.PREHOME);		
	}

}
