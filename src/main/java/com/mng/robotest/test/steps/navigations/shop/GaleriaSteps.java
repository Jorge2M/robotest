package com.mng.robotest.test.steps.navigations.shop;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.galeria.steps.PageGaleriaSteps;

public class GaleriaSteps extends StepBase {
	
	public void selectTalla() throws Exception {
		int posArticulo=1;
		boolean articleAvailable = false;
		var pageGaleriaSteps = new PageGaleriaSteps();
		while (!articleAvailable && posArticulo<5) {
			pageGaleriaSteps.showTallasArticulo(posArticulo);
			int tallaToSelect = 1;
			try {
				articleAvailable = pageGaleriaSteps.selectTallaAvailableArticulo(posArticulo, tallaToSelect);
			}
			catch (Exception e) {
				Log4jTM.getLogger().warn("Problem selecting talla " + tallaToSelect + " from article " + posArticulo, e);
			}
			if (!articleAvailable) {
				posArticulo+=1;
			}
		}
	}
}