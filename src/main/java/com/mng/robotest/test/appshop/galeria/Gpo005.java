package com.mng.robotest.test.appshop.galeria;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.data.Constantes;
import com.mng.robotest.test.steps.shop.galeria.PageGaleriaSteps;

public class Gpo005 extends TestBase {

	private final PageGaleriaSteps pageGaleriaSteps = new PageGaleriaSteps();
	
	public Gpo005() {
		dataTest.setUserRegistered(true);
	}
	
	@Override
	public void execute() throws Exception {
		access();
		if (app==AppEcom.outlet || channel.isDevice()) {
			clickMenu("cardigans-y-jerseis");
		} else {
			clickMenu("New Now");
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
		
		clickMenu("vestidos");
		if (app==AppEcom.outlet || channel.isDevice()) {
			clickMenuLateral("cardigans-y-jerseis", "jerséis");
		} else {
			clickMenuLateral("camisas", "básicas");
		}		
	}
	
}
