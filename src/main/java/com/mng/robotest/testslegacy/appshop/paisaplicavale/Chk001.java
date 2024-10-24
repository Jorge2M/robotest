package com.mng.robotest.testslegacy.appshop.paisaplicavale;

import java.util.Arrays;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.bolsa.steps.BolsaSteps;
import com.mng.robotest.tests.domains.compra.beans.ConfigCheckout;
import com.mng.robotest.tests.domains.compranew.steps.CheckoutNewSteps;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.datastored.DataCheckPedidos.CheckPedido;
import com.mng.robotest.testslegacy.steps.navigations.shop.CheckoutFlow;
import com.mng.robotest.testslegacy.steps.navigations.shop.CheckoutFlow.From;

public class Chk001 extends TestBase {

	private final ConfigCheckout fTCkoutIni;
	
	public Chk001(Pais pais, IdiomaPais idioma, ConfigCheckout fTCkoutIni) {
		this.dataTest.setPais(pais);
		this.dataTest.setIdioma(idioma);
		this.fTCkoutIni = fTCkoutIni;
	}
	
	@Override
	public void execute() throws Exception {
		dataTest.setDataPago(fTCkoutIni);
		if (dataTest.getPais().isNewcheckout(app)) {
			fluxNewCheckout();	
		} else {
			fluxOldCheckout();
		}
	}
	
	private void fluxNewCheckout() throws Exception {
		accessLoginAndClearBolsa();
		altaArticulosBolsaAndClickComprar();
		if (isCheckeableNewCheckout()) {
			var checkoutSteps = new CheckoutNewSteps();
			checkoutSteps.inputDeliveryGuestDefaultData();
			checkoutSteps.clickContinueToPaymentButton();
			executeVisaPayment();
		}
	}
	
	private void accessLoginAndClearBolsa() throws Exception {
		access();
		new BolsaSteps().clear();
	}
	
	private void fluxOldCheckout() throws Exception {
		new CheckoutFlow.BuilderCheckout(dataTest.getDataPago())
			.build()
			.checkout(From.PREHOME);
		
		var dataPago = dataTest.getDataPago();
		if (dataPago.getFTCkout().checkManto) {
			var listChecks = Arrays.asList(
					CheckPedido.CONSULTAR_BOLSA, 
					CheckPedido.CONSULTAR_PEDIDO);
			checkPedidosManto(listChecks, dataPago.getListPedidos());
		}		
	}

}
