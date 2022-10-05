package com.mng.robotest.test.steps.shop.galeria;

import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.LineaType;
import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.SublineaType;
import com.mng.robotest.test.pageobject.shop.galeria.PageGaleria;
import com.mng.robotest.test.pageobject.shop.galeria.PageGaleriaDesktop;
import com.mng.robotest.test.pageobject.shop.galeria.SecCrossSelling;

public class SecCrossSellingSteps extends StepBase {

	private final SecCrossSelling secCrossSelling = new SecCrossSelling();
	
	@Validation
	public ChecksTM validaIsCorrect(LineaType lineaType, SublineaType sublineaType) throws Exception {
		ChecksTM checks = ChecksTM.getNew();
		PageGaleria pageGaleria = PageGaleria.getNew(channel);
		if (!secCrossSelling.isSectionVisible()) {
			pageGaleria.scrollToPageFromFirst(PageGaleriaDesktop.MAX_PAGE_TO_SCROLL);
		}
		
		checks.add(
			"La sección cross-selling existe (si de primeras no existe scrollamos hasta el final de la galería)",
			secCrossSelling.isSectionVisible(), State.Defect);
		
		pageGaleria.goToInitPageAndWaitForArticle();
		return checks;
	}
}
