package com.mng.robotest.test.appshop.galeria;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.LineaType;
import com.mng.robotest.test.data.Constantes;
import com.mng.robotest.test.steps.shop.galeria.PageGaleriaSteps;

import static com.mng.robotest.domains.transversal.menus.pageobjects.MenuWeb.*;
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
			pageGaleriaSteps.secCrossSellingSteps.validaIsCorrect(LineaType.she, null);
			pageGaleriaSteps.hayPanoramicasEnGaleriaDesktop(Constantes.PORC_PANORAMICAS);
		}
		
		if (!channel.isDevice()) {
			pageGaleriaSteps.getSecSelectorPreciosSteps().seleccionaIntervalo();
		}

		if (app!=AppEcom.outlet && !channel.isDevice()) {
			pageGaleriaSteps.secCrossSellingSteps.validaIsCorrect(LineaType.she, null);
			pageGaleriaSteps.hayPanoramicasEnGaleriaDesktop(Constantes.PORC_PANORAMICAS);
		}	
		
		clickMenu(VESTIDOS_SHE);
		if (app==AppEcom.outlet || channel.isDevice()) {
			clickSubMenu(CARDIGANS_Y_JERSEIS_JERSEIS_SHE);
		} else {
			clickSubMenu(CAMISAS_BASICAS_SHE);
		}		
	}
	
}
