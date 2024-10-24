package com.mng.robotest.tests.domains.compra.tests;

import static com.mng.robotest.testslegacy.data.PaisShop.*;

import java.util.Arrays;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.bolsa.steps.BolsaSteps;
import com.mng.robotest.tests.domains.compra.steps.PageResultPagoSteps;
import com.mng.robotest.tests.domains.micuenta.steps.ModalDetalleCompraSteps;
import com.mng.robotest.tests.domains.micuenta.steps.PageMisComprasSteps;

public class Com011 extends TestBase {

	public Com011() throws Exception {
		if (inputParamsSuite.getListaPaises().isEmpty()) {
			dataTest.setPais(DEUTSCHLAND.getPais());
		}
	}
	
	@Override
	public void execute() throws Exception {
        accessLoginAndClearBolsa();
        altaArticulosBolsaAndComprar();
		if (!isPRO()) {
			executeVisaPayment();
			checkMisCompras();
			checkPedidos();
		} 
	}
	
    private void accessLoginAndClearBolsa() throws Exception {
        accessAndLogin();
        new BolsaSteps().clear();
    }
    private void altaArticulosBolsaAndComprar() throws Exception {
		var twoArticles = getTwoArticlesFromDistinctWarehouses();
		var articles = Arrays.asList(twoArticles.getLeft(), twoArticles.getRight());
		altaArticulosBolsaAndClickComprar(articles);
    }	

	private void checkMisCompras() {
		String codigoPedido = dataTest.getDataPago().getDataPedido().getCodpedido();
		new PageResultPagoSteps().selectMisCompras();
		
		var pageMisComprasSteps = new PageMisComprasSteps();
		if (pageMisComprasSteps.checkIsCompraOnline(codigoPedido)) {
			pageMisComprasSteps.selectCompra(codigoPedido);
			new ModalDetalleCompraSteps().checkIsDataVisible();
		}
	}    
	
	private void checkPedidos() throws Exception {
		var dataPago = dataTest.getDataPago();
		var dataPedido = dataPago.getDataPedido();
		dataPedido.setEmailCheckout(dataTest.getUserConnected());
		checkPedidosManto(Arrays.asList(dataPago.getDataPedido()));
	}
}
