package com.mng.robotest.domains.base;

import java.util.List;

import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.domains.base.datatest.DataTest;
import com.mng.robotest.domains.bolsa.steps.SecBolsaSteps;
import com.mng.robotest.domains.compra.beans.ConfigCheckout;
import com.mng.robotest.domains.compra.steps.PageResultPagoSteps;
import com.mng.robotest.domains.compra.tests.CompraSteps;
import com.mng.robotest.getdata.productlist.entity.GarmentCatalog.Article;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.datastored.DataPedido;

public abstract class TestBase extends StepBase {

	public abstract void execute() throws Exception;
	
	public static final ThreadLocal<DataTest> DATA_TEST = new ThreadLocal<>();
	
	protected TestBase() {
		if (!inputParamsSuite.getListaPaises().isEmpty()) {
			String pais = inputParamsSuite.getListaPaises().get(0);
			dataTest = DataTest.getData(PaisShop.from(pais));
		} else {
			dataTest = DataTest.getData(PaisShop.ESPANA);
		}
		DATA_TEST.set(dataTest);
	}
	
	protected void altaArticulosBolsaAndClickComprar() throws Exception {
		altaArticulosBolsaAndClickComprar(1);
	}
	
    protected void altaArticulosBolsaAndClickComprar(int numArticles) throws Exception {
    	altaArticulosBolsaAndClickComprar(getArticles(numArticles));
    }	
    
    protected void altaArticulosBolsaAndClickComprar(List<Article> articles) throws Exception {
        new SecBolsaSteps().altaListaArticulosEnBolsa(articles);
        new SecBolsaSteps().selectButtonComprar();
    }
	
    protected DataPago executeVisaPayment() throws Exception {
    	return executePayment("VISA");
    }	    
    protected DataPago executeMastercardPayment() throws Exception {
    	return executePayment("MASTERCARD");
    }
    protected DataPago executePayment(String payment) throws Exception {
        DataPago dataPago = getDataPago();
        dataPago.setPago(dataTest.getPais().getPago(payment));
        new CompraSteps().startPayment(dataPago, true);
        new PageResultPagoSteps().validateIsPageOk(dataPago);
        return dataPago;
    }
    
	protected DataPago getDataPago() {
		return getDataPago(ConfigCheckout.config().build());
	}
	
	protected DataPago getDataPago(ConfigCheckout configCheckout) {
		var dataPago = new DataPago(configCheckout);
		var dataPedido = new DataPedido(dataTest.getPais(), dataTest.getDataBag());
		dataPago.setDataPedido(dataPedido);
		return dataPago;
	}	
	
	protected void renewBrowser() {
		dataTest.setUserConnected("");
		TestMaker.renewDriverTestCase();
	}
	protected void renewTest() {
		dataTest.setUserConnected("");
		driver.get(inputParamsSuite.getUrlBase());		
	}
}
