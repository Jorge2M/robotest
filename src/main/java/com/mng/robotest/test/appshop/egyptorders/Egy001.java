package com.mng.robotest.test.appshop.egyptorders;

import java.util.Arrays;

import com.mng.robotest.domains.compra.beans.ConfigCheckout;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.datastored.DataBag;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.factoryes.entities.EgyptCity;
import com.mng.robotest.test.getdata.products.data.GarmentCatalog;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.BuilderCheckout;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.AccesoSteps;
import com.mng.robotest.test.steps.shop.SecBolsaSteps;
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
		new AccesoSteps().oneStep(false);
		GarmentCatalog article = UtilsTest.getArticleForTest(dataTest.pais, app, driver);
		
		DataBag dataBag = new DataBag(); 
		SecBolsaSteps secBolsaSteps = new SecBolsaSteps();
		secBolsaSteps.altaListaArticulosEnBolsa(Arrays.asList(article), dataBag);
		
		DataPago dataPago = makeDataPayment(dataBag);
		dataPago = new BuilderCheckout(dataPago)
			.pago(dataTest.pais.getPago("VISA"))
			.egyptCity(egyptCity)
			.build()
			.checkout(From.BOLSA);
	}
	
	private DataPago makeDataPayment(DataBag dataBag) {
		ConfigCheckout configCheckout = ConfigCheckout.config()
				.checkPagos().build();
		
		DataPago dataPago = getDataPago(configCheckout, dataBag);
		return dataPago;
	}

}
