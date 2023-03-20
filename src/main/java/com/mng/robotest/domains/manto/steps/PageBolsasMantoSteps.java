package com.mng.robotest.domains.manto.steps;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.base.StepMantoBase;
import com.mng.robotest.domains.manto.pageobjects.PageBolsas;
import com.mng.robotest.test.datastored.DataPedido;

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
			"En la columna 1 aparece el código de pedido: " + dataPedido.getCodigoPedidoManto() + " (lo esperamos hasta " + seconds + " segundos)",
			isPresentLinkPedido, State.Warn);
	 	
	 	checks.add(
			"Aparece una sola bolsa",
			pageBolsas.getNumLineas()==1, State.Warn);
	 	
	 	//En el caso de Outlet no tenemos la información del TPV que toca
	 	if (app!=AppEcom.outlet) {
	 		String idTpv = dataPedido.getPago().getTpv().getId();
		 	checks.add(
				"En la columna 8 Aparece el Tpv asociado: " + idTpv,
				pageBolsas.presentIdTpvInBolsa(idTpv), State.Warn);
	 	}
	 	
	 	checks.add(
			"En la columna 7 aparece el email asociado: " + dataPedido.getEmailCheckout(),
			pageBolsas.presentCorreoInBolsa(dataPedido.getEmailCheckout()), State.Warn);
		
		return checks;
	}
}