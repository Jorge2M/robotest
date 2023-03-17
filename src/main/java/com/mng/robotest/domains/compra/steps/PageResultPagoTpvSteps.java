package com.mng.robotest.domains.compra.steps;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.compra.pageobjects.PageResultPagoTpv;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.utils.ImporteScreen;

public class PageResultPagoTpvSteps extends StepBase {
	
	private final PageResultPagoTpv pageResultPagoTpv = new PageResultPagoTpv();
	
	@Validation
	public ChecksTM validateIsPageOk(DataPedido dataPedido, String codPais) {
		var checks = ChecksTM.getNew();
	 	checks.add(
			"Aparece un texto de confirmación de la compra",
			pageResultPagoTpv.isPresentCabeceraConfCompra(), State.Warn);
	 	
		String importeTotal = dataPedido.getImporteTotal();
	 	checks.add(
			"Aparece el importe " + importeTotal + " de la operación",
			ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), State.Warn);
	 	
	 	boolean isVisibleCodPedido = pageResultPagoTpv.isVisibleCodPedido();
	 	String codPedido = "";
	 	if (isVisibleCodPedido) {
	 		codPedido = pageResultPagoTpv.getCodigoPedido();
	 	}
	 	checks.add(
			"Aparece el código de pedido <b>" + codPedido + "</b>",
			isVisibleCodPedido, State.Defect);
	 	
		dataPedido.setCodpedido(codPedido); 
		dataPedido.setResejecucion(State.Ok);
		
		return checks;
	}
}
