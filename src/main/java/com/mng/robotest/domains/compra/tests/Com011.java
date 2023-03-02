package com.mng.robotest.domains.compra.tests;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;

import com.mng.robotest.domains.bolsa.steps.SecBolsaSteps;
import com.mng.robotest.domains.compra.steps.PageResultPagoSteps;
import com.mng.robotest.domains.micuenta.steps.ModalDetalleCompraSteps;
import com.mng.robotest.domains.micuenta.steps.PageMisComprasSteps;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.getdata.productlist.entity.GarmentCatalog.Article;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.datastored.DataPedido;

import static com.mng.robotest.test.data.PaisShop.*;

public class Com011 extends TestBase {

	public Com011() throws Exception {
		dataTest.setUserRegistered(true);
		if (inputParamsSuite.getListaPaises().isEmpty()) {
			dataTest.setPais(DEUTSCHLAND.getPais());
		}
	}
	
	@Override
	public void execute() throws Exception {
        accessLoginAndClearBolsa();
        altaArticulosBolsaAndClickComprar();
		if (!isPRO()) {
			DataPago dataPago = executeVisaPayment();
			checkMisCompras(dataPago);
			checkPedidos(dataPago);
		} 
	}
	
    private void accessLoginAndClearBolsa() throws Exception {
        access();
        new SecBolsaSteps().clear();
    }
    private void altaArticulosBolsaAndClickComprar() throws Exception {
		Pair<Article, Article> twoArticles = getTwoArticlesFromDistinctWarehouses();
		List<Article> articles = Arrays.asList(twoArticles.getLeft(), twoArticles.getRight());
        new SecBolsaSteps().altaListaArticulosEnBolsa(articles);
        new SecBolsaSteps().selectButtonComprar();
    }	

    private DataPago executeVisaPayment() throws Exception {
        DataPago dataPago = getDataPago();
        dataPago.setPago(dataTest.getPais().getPago("VISA"));
        new CompraSteps().startPayment(dataPago, true);

        new PageResultPagoSteps().validateIsPageOk(dataPago);
        return dataPago;
    }	
    
	private void checkMisCompras(DataPago dataPago) {
		String codigoPedido = dataPago.getDataPedido().getCodpedido();
		new PageResultPagoSteps().selectMisCompras();
		
		PageMisComprasSteps pageMisComprasSteps = new PageMisComprasSteps();
		if (pageMisComprasSteps.validateIsCompraOnline(codigoPedido)) {
			pageMisComprasSteps.selectCompra(codigoPedido);
			new ModalDetalleCompraSteps().checkIsDataVisible();
		}
	}    
	
	private void checkPedidos(DataPago dataPago) throws Exception {
		DataPedido dataPedido = dataPago.getDataPedido();
		dataPedido.setEmailCheckout(dataTest.getUserConnected());
		new CompraSteps().checkPedidosManto(Arrays.asList(dataPago.getDataPedido())); 
	}
}
