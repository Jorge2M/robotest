package com.mng.robotest.test.appshop.paisaplicavale;

import java.util.Arrays;

import com.mng.robotest.domains.base.TestBase;
import com.mng.robotest.domains.compra.beans.ConfigCheckout;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.datastored.DataCheckPedidos.CheckPedido;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;

public class Chk001 extends TestBase {

	private final ConfigCheckout fTCkoutIni;
	
	public Chk001(Pais pais, IdiomaPais idioma, ConfigCheckout fTCkoutIni) {
		this.dataTest.setPais(pais);
		this.dataTest.setIdioma(idioma);
		this.fTCkoutIni = fTCkoutIni;
	}
	
	@Override
	public void execute() throws Exception {
		DataPago dataPago = getDataPago(fTCkoutIni);
		dataPago = new CheckoutFlow.BuilderCheckout(dataPago)
			.build()
			.checkout(From.PREHOME);
		
		if (dataPago.getFTCkout().checkManto) {
			var listChecks = Arrays.asList(
					CheckPedido.CONSULTAR_BOLSA, 
					CheckPedido.CONSULTAR_PEDIDO);
			checkPedidosManto(listChecks, dataPago.getListPedidos());
		}		
	}

}
