package com.mng.robotest.test.steps.navigations.shop;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.galeria.steps.PageGaleriaSteps;

public class GaleriaSteps extends StepBase {
	
	public void selectTallaAvailable() throws Exception {
		int posArticulo=1;
		boolean articleAvailable = false;
		var pageGaleriaSteps = new PageGaleriaSteps();
		while (!articleAvailable && posArticulo<5) {
			pageGaleriaSteps.showTallasArticulo(posArticulo);
			try {
				articleAvailable = pageGaleriaSteps.selectTallaAvailableArticulo(posArticulo);
			}
			catch (Exception e) {
				Log4jTM.getLogger().warn("Problem selecting first talla available from article " + posArticulo, e);
			}
			posArticulo+=1;
		}
	}
}
