package com.mng.robotest.tests.domains.galeria.steps;

import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.galeria.pageobjects.PageGaleria;
import com.mng.robotest.tests.domains.galeria.pageobjects.sections.SecCrossSelling;
import com.mng.robotest.tests.domains.transversal.menus.pageobjects.LineaWeb.LineaType;
import com.mng.robotest.tests.domains.transversal.menus.pageobjects.LineaWeb.SublineaType;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;

public class SecCrossSellingSteps extends StepBase {

	private final SecCrossSelling secCrossSelling = new SecCrossSelling();
	
	@Validation
	public ChecksTM validaIsCorrect(LineaType lineaType, SublineaType sublineaType) {
		var checks = ChecksTM.getNew();
		var pageGaleria = PageGaleria.make(channel, app, dataTest.getPais());
		if (!secCrossSelling.isSectionVisible()) {
			pageGaleria.scrollToPageFromFirst(PageGaleria.MAX_PAGE_TO_SCROLL);
		}
		
		checks.add(
			"La sección cross-selling existe (si de primeras no existe scrollamos hasta el final de la galería)",
			secCrossSelling.isSectionVisible());
		
		pageGaleria.goToInitPageAndWaitForArticle();
		return checks;
	}
}
