package com.mng.robotest.test.appshop.egyptorders;

import java.util.Arrays;

import com.mng.robotest.domains.bolsa.steps.SecBolsaSteps;
import com.mng.robotest.domains.compra.beans.ConfigCheckout;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.factoryes.entities.EgyptCity;
import com.mng.robotest.test.getdata.products.data.GarmentCatalog;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.BuilderCheckout;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.utils.PaisGetter;
import com.mng.robotest.test.utils.UtilsTest;

public class Egy001 extends TestBase {

	private final EgyptCity egyptCity;
	
	public Egy001(EgyptCity egyptCity) {
		dataTest.pais = PaisGetter.get(PaisShop.EGYPT);
		dataTest.idioma = dataTest.pais.getListIdiomas().get(0);
		this.egyptCity = egyptCity;
	}
	
	public void execute() throws Exception {
		access();
		GarmentCatalog article = UtilsTest.getArticleForTest(dataTest.pais, app, driver);
		new SecBolsaSteps().altaListaArticulosEnBolsa(Arrays.asList(article));
		DataPago dataPago = makeDataPayment();
		dataPago = new BuilderCheckout(dataPago)
			.pago(dataTest.pais.getPago("VISA"))
			.egyptCity(egyptCity)
			.build()
			.checkout(From.BOLSA);
	}
	
	private DataPago makeDataPayment() {
		ConfigCheckout configCheckout = ConfigCheckout.config()
				.checkPagos().build();
		
		DataPago dataPago = getDataPago(configCheckout);
		return dataPago;
	}

}
