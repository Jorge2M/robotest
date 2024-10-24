package com.mng.robotest.tests.domains.manto.steps;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepMantoBase;
import com.mng.robotest.tests.domains.manto.pageobjects.PageBolsas;
import com.mng.robotest.testslegacy.datastored.DataPedido;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageBolsasMantoSteps extends StepMantoBase {

	private final PageBolsas pageBolsas = new PageBolsas();
	
	@Validation
	public ChecksResultWithFlagLinkCodPed validaLineaBolsa(DataPedido dataPedido) {
		ChecksResultWithFlagLinkCodPed checks = ChecksResultWithFlagLinkCodPed.getNew();
		int seconds = 10;
		boolean isPresentLinkPedido = pageBolsas.presentLinkPedidoInBolsaUntil(dataPedido.getCodigoPedidoManto(), seconds);
	 	if (isPresentLinkPedido) {
	 		dataPedido.setIdCompra(pageBolsas.getIdCompra(dataPedido.getCodigoPedidoManto()));
	 	}
		checks.setExistsLinkCodPed(isPresentLinkPedido);
	 	checks.add(
			"En la columna 1 aparece el código de pedido: " + dataPedido.getCodigoPedidoManto() + " " + getLitSecondsWait(seconds),
			isPresentLinkPedido, WARN);
	 	
	 	checks.add(
			"Aparece una sola bolsa",
			pageBolsas.getNumLineas()==1, WARN);
	 	
	 	checks.add(
			"En la columna 7 aparece el email asociado: " + dataPedido.getEmailCheckout(),
			pageBolsas.presentCorreoInBolsa(dataPedido.getEmailCheckout()), WARN);
		
		return checks;
	}
}
