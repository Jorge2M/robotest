package com.mng.robotest.domains.compra.tests;

import java.util.Arrays;

import com.mng.robotest.domains.base.TestBase;
import com.mng.robotest.domains.bolsa.steps.SecBolsaSteps;
import com.mng.robotest.domains.compra.steps.PageResultPagoSteps;
import com.mng.robotest.domains.micuenta.steps.ModalDetalleCompraSteps;
import com.mng.robotest.domains.micuenta.steps.PageMisComprasSteps;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.datastored.DataPedido;

import static com.mng.robotest.test.data.PaisShop.*;

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
			DataPago dataPago = executeVisaPayment();
			checkMisCompras(dataPago);
			checkPedidos(dataPago);
		} 
	}
	
    private void accessLoginAndClearBolsa() throws Exception {
        accessAndLogin();
        new SecBolsaSteps().clear();
    }
    private void altaArticulosBolsaAndComprar() throws Exception {
		var twoArticles = getTwoArticlesFromDistinctWarehouses();
		var articles = Arrays.asList(twoArticles.getLeft(), twoArticles.getRight());
		altaArticulosBolsaAndClickComprar(articles);
    }	

	private void checkMisCompras(DataPago dataPago) {
		String codigoPedido = dataPago.getDataPedido().getCodpedido();
		new PageResultPagoSteps().selectMisCompras();
		
		var pageMisComprasSteps = new PageMisComprasSteps();
		if (pageMisComprasSteps.validateIsCompraOnline(codigoPedido)) {
			pageMisComprasSteps.selectCompra(codigoPedido);
			new ModalDetalleCompraSteps().checkIsDataVisible();
		}
	}    
	
	private void checkPedidos(DataPago dataPago) throws Exception {
		DataPedido dataPedido = dataPago.getDataPedido();
		dataPedido.setEmailCheckout(dataTest.getUserConnected());
		checkPedidosManto(Arrays.asList(dataPago.getDataPedido()));
	}
}
