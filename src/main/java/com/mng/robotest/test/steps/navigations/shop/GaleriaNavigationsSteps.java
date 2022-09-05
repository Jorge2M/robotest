package com.mng.robotest.test.steps.navigations.shop;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.datastored.DataBag;
import com.mng.robotest.test.steps.shop.galeria.PageGaleriaSteps;

public class GaleriaNavigationsSteps extends StepBase {
	
	public DataBag selectArticleAvailableFromGaleria(Pais pais) throws Exception {
		DataBag dataBag = new DataBag();
		int posArticulo=1;
		boolean articleAvailable = false;
		PageGaleriaSteps pageGaleriaSteps = new PageGaleriaSteps();
		while (!articleAvailable && posArticulo<5) {
			pageGaleriaSteps.shopTallasArticulo(posArticulo);
			int tallaToSelect = 1;
			try {
				articleAvailable = pageGaleriaSteps.selectTallaAvailableArticulo(posArticulo, tallaToSelect, dataBag, pais);
			}
			catch (Exception e) {
				Log4jTM.getLogger().warn("Problem selecting talla " + tallaToSelect + " from article " + posArticulo, e);
			}
			if (!articleAvailable) {
				posArticulo+=1;
			}
		}
		
		return dataBag;
	}
}
