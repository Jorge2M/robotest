package com.mng.robotest.tests.domains.galeria.tests;

import static com.mng.robotest.tests.domains.menus.beans.FactoryMenus.MenuItem.*;
import static com.mng.robotest.tests.domains.galeria.pageobjects.entity.FilterOrdenacion.*;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.galeria.steps.GaleriaSteps;
import com.mng.robotest.tests.domains.menus.beans.FactoryMenus;
import com.mng.robotest.testslegacy.data.Color;

public class Gpo001 extends TestBase {

	private final GaleriaSteps galeriaSteps = new GaleriaSteps();
	
	@Override
	public void execute() throws Exception {
		accessAndLogin();
		clickMenu(CAMISAS_SHE);
		filterGaleryByColor(CAMISAS_SHE, Color.BLANCO);
		checkScroll();		
	}

	private void checkScroll() throws Exception {
		String nameMenuCamisas = FactoryMenus.get(CAMISAS_SHE).getMenu();
		galeriaSteps.scrollToLast();
		if (channel.isDevice()) {
			galeriaSteps.backTo1erArticleMobilStep();
		}
		int numArticulosPantalla = 
			galeriaSteps.seleccionaOrdenacionGaleria(PRECIO_DESC, nameMenuCamisas);
		
		galeriaSteps.seleccionaOrdenacionGaleria(PRECIO_ASC, nameMenuCamisas, numArticulosPantalla);
		galeriaSteps.selecColorFromArtGaleriaStep(1, 2);
		galeriaSteps.selecArticuloGaleriaStep(1);
	}
	
}
