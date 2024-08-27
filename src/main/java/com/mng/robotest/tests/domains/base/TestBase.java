package com.mng.robotest.tests.domains.base;

import java.util.List;

import com.mng.robotest.tests.domains.base.datatest.DataTest;
import com.mng.robotest.tests.domains.bolsa.steps.BolsaSteps;
import com.mng.robotest.tests.domains.compra.pageobjects.envio.TipoTransporteEnum.TipoTransporte;
import com.mng.robotest.tests.domains.compra.steps.PageResultPagoSteps;
import com.mng.robotest.tests.domains.compra.tests.CompraSteps;
import com.mng.robotest.tests.domains.transversal.cabecera.steps.SecCabeceraSteps;
import com.mng.robotest.tests.repository.productlist.entity.GarmentCatalog.Article;
import com.mng.robotest.testslegacy.beans.Pago;
import com.mng.robotest.testslegacy.data.PaisShop;

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
        new BolsaSteps().altaListaArticulosEnBolsa(articles);
        new BolsaSteps().selectButtonComprar();
    }
	
	protected void clickLogoMango() {
		new SecCabeceraSteps().selecLogo();
	}
    
    protected void executeVisaPayment() throws Exception {
    	executePayment("VISA");
    }	    
    protected void executeMastercardPayment() throws Exception {
    	executePayment("MASTERCARD");
    }
    protected void executeMastercardTiendaPayment() throws Exception {
    	executePayment("MASTERCARD", TipoTransporte.TIENDA);
    }    
    
    protected void executeVisaPaymentSavingCard() throws Exception {
        var dataPago = dataTest.getDataPago();
        dataPago.setPago(dataTest.getPais().getPago("VISA"));
        dataPago.setSelectSaveCard(true);
        executePayment();
    }
    
    protected void executeVisaPaymentUsingSavedCard() throws Exception {
        var dataPago = dataTest.getDataPago();
        dataPago.setPago(dataTest.getPais().getPago("VISA"));
        dataPago.setUseSavedCard(true);
        executePayment();
    }

    protected void executePayment(String payment) throws Exception {
    	executePayment(payment, null);
    }
    
    protected void executePayment(String payment, TipoTransporte tipoTransporte) throws Exception {
        var dataPago = dataTest.getDataPago();
        Pago pago = dataTest.getPais().getPago(payment);
        if (tipoTransporte!=null) {
        	pago.setTipoEnvio(tipoTransporte);
        }
        dataPago.setPago(pago);
        executePayment();
    }
    
    protected void executePayment() throws Exception {
        new CompraSteps().startPayment(true);
        new PageResultPagoSteps().checkIsPageOk();
    }
    
	protected void renewTest() {
		dataTest.setUserConnected("");
		driver.get(inputParamsSuite.getUrlBase());		
	}
	
}
