package com.mng.robotest.tests.domains.compra.payments.amazon.steps;

import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.compra.payments.amazon.pageobjects.PageAmazonIdent;
import com.mng.robotest.testslegacy.datastored.DataPedido;
import com.mng.robotest.testslegacy.utils.ImporteScreen;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageAmazonIdentSteps extends StepBase {
	
	private final PageAmazonIdent pgAmazonIdent = new PageAmazonIdent();
	
	@Validation
	public ChecksTM validateIsPage(DataPedido dataPedido) {
		var checks = ChecksTM.getNew();
		checks.add(
			"Aparece una página con el logo de Amazon",
			pgAmazonIdent.isLogoAmazon(), WARN);
		
		checks.add(
			"Aparece los campos para la identificación (usuario/password)",
			pgAmazonIdent.isPageIdent());
		
		if (isDesktop()) {
			checks.add(
				"En la página resultante figura el importe total de la compra (" + dataPedido.getImporteTotal() + ")",
				ImporteScreen.isPresentImporteInScreen(dataPedido.getImporteTotal(), dataTest.getCodigoPais(), driver), WARN);
		}
		return checks;
	}
}
