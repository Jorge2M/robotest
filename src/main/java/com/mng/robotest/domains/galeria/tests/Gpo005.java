package com.mng.robotest.domains.galeria.tests;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.galeria.steps.PageGaleriaSteps;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.LineaType;

import static com.mng.robotest.domains.transversal.menus.beans.FactoryMenus.MenuItem.*;
import static com.mng.robotest.domains.transversal.menus.pageobjects.GroupWeb.GroupType.*;

public class Gpo005 extends TestBase {

	private final PageGaleriaSteps pageGaleriaSteps = new PageGaleriaSteps();
	
	public Gpo005() {
		dataTest.setUserRegistered(true);
	}
	
	@Override
	public void execute() throws Exception {
		access();
		if (app==AppEcom.outlet || channel.isDevice()) {
			clickMenu(CARDIGANS_Y_JERSEIS_SHE);
		} else {
			clickGroup(NEW_NOW);
			pageGaleriaSteps.secCrossSellingSteps.validaIsCorrect(LineaType.SHE, null);
		}
		
		if (!channel.isDevice()) {
			pageGaleriaSteps.getSecSelectorPreciosSteps().seleccionaIntervalo();
		}

		if (app!=AppEcom.outlet && !channel.isDevice()) {
			pageGaleriaSteps.secCrossSellingSteps.validaIsCorrect(LineaType.SHE, null);
		}	
		
		clickMenu(VESTIDOS_SHE);
		if (app==AppEcom.outlet || channel.isDevice()) {
			clickSubMenu(CARDIGANS_Y_JERSEIS_JERSEIS_SHE);
		} else {
			clickSubMenu(CAMISAS_BASICAS_SHE);
		}		
	}
	
}
