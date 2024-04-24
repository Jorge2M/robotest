package com.mng.robotest.tests.domains.galeria.tests;

import static com.mng.robotest.tests.domains.galeria.pageobjects.entities.TypeSlider.*;
import static com.mng.robotest.tests.domains.menus.beans.FactoryMenus.MenuItem.*;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.galeria.steps.GaleriaSteps;

public class Gpo006 extends TestBase {

	private final GaleriaSteps galeriaSteps = new GaleriaSteps();
	
	@Override
	public void execute() throws Exception {
		access();
		clickMenu(VESTIDOS_SHE);
		checkSliders();		
	}

	private void checkSliders() {
		String src2onImage = galeriaSteps.clicksSliderArticuloConColores(1, NEXT);
		galeriaSteps.clicksSliderArticuloConColores(1, PREVIOUS);
		galeriaSteps.clicksSliderArticuloConColores(1, src2onImage, NEXT);
		String srcImgAfterClickColor = galeriaSteps.selecColorFromArtGaleriaStep(1, 2);
		galeriaSteps.clicksSliderArticuloConColores(1, NEXT, NEXT);
		galeriaSteps.clicksSliderArticuloConColores(1, srcImgAfterClickColor, PREVIOUS, PREVIOUS);
	}

}
