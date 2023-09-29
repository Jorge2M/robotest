package com.mng.robotest.tests.domains.compra.tests;

import static com.mng.robotest.testslegacy.data.PaisShop.*;

import java.util.Arrays;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.compra.beans.ConfigCheckout;
import com.mng.robotest.testslegacy.steps.navigations.shop.CheckoutFlow.BuilderCheckout;
import com.mng.robotest.testslegacy.steps.navigations.shop.CheckoutFlow.From;

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
