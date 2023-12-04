package com.mng.robotest.tests.domains.compra.steps;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.compra.pageobjects.PageResultPagoTpv;
import com.mng.robotest.testslegacy.datastored.DataPedido;
import com.mng.robotest.testslegacy.utils.ImporteScreen;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageResultPagoTpvSteps extends StepBase {
	
	private final PageResultPagoTpv pgResultPagoTpv = new PageResultPagoTpv();
	
	@Validation
	public ChecksTM validateIsPageOk(DataPedido dataPedido, String codPais) {
		var checks = ChecksTM.getNew();
	 	checks.add(
			"Aparece un texto de confirmación de la compra",
			pgResultPagoTpv.isPresentCabeceraConfCompra(), WARN);
	 	
		String importeTotal = dataPedido.getImporteTotal();
	 	checks.add(
			"Aparece el importe " + importeTotal + " de la operación",
			ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), WARN);
	 	
	 	boolean isVisibleCodPedido = pgResultPagoTpv.isVisibleCodPedido();
	 	String codPedido = "";
	 	if (isVisibleCodPedido) {
	 		codPedido = pgResultPagoTpv.getCodigoPedido();
	 	}
	 	checks.add(
			"Aparece el código de pedido <b>" + codPedido + "</b>",
			isVisibleCodPedido);
	 	
		dataPedido.setCodpedido(codPedido); 
		dataPedido.setResejecucion(OK);
		
		return checks;
	}
}
