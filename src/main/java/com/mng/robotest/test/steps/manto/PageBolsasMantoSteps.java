package com.mng.robotest.test.steps.manto;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.pageobject.manto.PageBolsas;

/**
 * Clase que implementa los diferentes steps/validations asociados asociados a la página de Bolsas en Manto
 * @author jorge.munoz
 *
 */
public class PageBolsasMantoSteps {

	@Validation
	public static ChecksResultWithFlagLinkCodPed validaLineaBolsa(DataPedido dataPedido, AppEcom appE, WebDriver driver) {
		ChecksResultWithFlagLinkCodPed checks = ChecksResultWithFlagLinkCodPed.getNew();
		int maxSeconds = 10;
		boolean isPresentLinkPedido = PageBolsas.presentLinkPedidoInBolsaUntil(dataPedido.getCodigoPedidoManto(), maxSeconds, driver);
	 	if (isPresentLinkPedido) {
	 		dataPedido.setIdCompra(PageBolsas.getIdCompra(dataPedido.getCodigoPedidoManto(), driver));
	 	}
		checks.setExistsLinkCodPed(isPresentLinkPedido);
	 	checks.add(
			"En la columna 1 aparece el código de pedido: " + dataPedido.getCodigoPedidoManto() + " (lo esperamos hasta " + maxSeconds + " segundos)",
			isPresentLinkPedido, State.Warn);
	 	
	 	checks.add(
			"Aparece una sola bolsa",
			PageBolsas.getNumLineas(driver)==1, State.Warn);
	 	
	 	//En el caso de Outlet no tenemos la información del TPV que toca
	 	if (appE!=AppEcom.outlet) {
	 		String idTpv = dataPedido.getPago().getTpv().getId();
		 	checks.add(
				"En la columna 8 Aparece el Tpv asociado: " + idTpv,
				PageBolsas.presentIdTpvInBolsa(driver, idTpv), State.Warn);
	 	}
	 	
	 	checks.add(
			"En la columna 7 aparece el email asociado: " + dataPedido.getEmailCheckout(),
			PageBolsas.presentCorreoInBolsa(driver, dataPedido.getEmailCheckout()), State.Warn);
		
		return checks;
	}
}
