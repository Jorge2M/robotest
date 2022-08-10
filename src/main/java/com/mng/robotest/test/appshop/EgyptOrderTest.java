package com.mng.robotest.test.appshop;

import java.io.Serializable;
import java.util.Arrays;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.compra.beans.ConfigCheckout;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.datastored.DataBag;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.factoryes.entities.EgyptCity;
import com.mng.robotest.test.getdata.products.data.GarmentCatalog;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.BuilderCheckout;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.AccesoSteps;
import com.mng.robotest.test.steps.shop.SecBolsaSteps;
import com.mng.robotest.test.utils.PaisGetter;
import com.mng.robotest.test.utils.UtilsTest;

public class EgyptOrderTest implements Serializable {

	private static final long serialVersionUID = 1L;
	private final EgyptCity egyptCity;
	private String index_fact;
	
	
	public EgyptOrderTest(EgyptCity egyptCity) {
		this.egyptCity = egyptCity;
		this.index_fact = getIndexFactoria(egyptCity);
	}
	
	private String getIndexFactoria(EgyptCity egyptCity) {
		return String.format(" %s-%s", egyptCity.getState(), egyptCity.getCity());
	}
	
	@Test (
		groups={"Compra", "Canal:desktop_App:shop"},
		description="Test de compra en Egypto parametrizado por stado y ciudad")
	public void EGY001_Compra() throws Exception {
		TestCaseTM.addNameSufix(this.index_fact);
		
		WebDriver driver = TestMaker.getDriverTestCase();
		DataCtxShop dCtxSh = makeEgyptDataTest();
		AccesoSteps.oneStep(dCtxSh, false, driver);
		GarmentCatalog article = UtilsTest.getArticleForTest(dCtxSh, driver);
		
		DataBag dataBag = new DataBag(); 
		SecBolsaSteps secBolsaSteps = new SecBolsaSteps(dCtxSh);
		secBolsaSteps.altaListaArticulosEnBolsa(Arrays.asList(article), dataBag);
		
		DataCtxPago dCtxPago = makeDataPayment(dCtxSh, dataBag);
		dCtxPago = new BuilderCheckout(dCtxSh, dCtxPago, driver)
			.pago(dCtxSh.pais.getPago("VISA"))
			.egyptCity(egyptCity)
			.build()
			.checkout(From.BOLSA);
	}

	DataCtxShop makeEgyptDataTest() {
		DataCtxShop dCtxSh = new DataCtxShop();
		InputParamsMango inputParamsSuite = (InputParamsMango)TestMaker.getInputParamsSuite();
		dCtxSh.setAppEcom((AppEcom)inputParamsSuite.getApp());
		dCtxSh.setChannel(inputParamsSuite.getChannel());
		dCtxSh.pais = PaisGetter.get(PaisShop.EGYPT);
		dCtxSh.idioma = dCtxSh.pais.getListIdiomas().get(0);
		return dCtxSh;
	}
	
	private DataCtxPago makeDataPayment(DataCtxShop dCtxSh, DataBag dataBag) {
		ConfigCheckout configCheckout = ConfigCheckout.config()
				.checkPagos().build();
		
		DataCtxPago dCtxPago = new DataCtxPago(dCtxSh, configCheckout);
		dCtxPago.getDataPedido().setDataBag(dataBag);
		return dCtxPago;
	}	
	
}
