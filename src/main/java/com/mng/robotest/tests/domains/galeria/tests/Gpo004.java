package com.mng.robotest.tests.domains.galeria.tests;

import java.util.ArrayList;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.galeria.steps.GaleriaSteps;
import com.mng.robotest.testslegacy.data.Color;

import static com.mng.robotest.tests.conf.AppEcom.*;
import static com.mng.robotest.tests.domains.menus.entity.FactoryMenus.MenuItem.*;
import static com.mng.robotest.testslegacy.data.Color.*;

public class Gpo004 extends TestBase {

	private final GaleriaSteps galeriaSteps = new GaleriaSteps();
	
	@Override
	public void execute() throws Exception {
		access();
		selectGaleryAndFilterByColor();
		scrollToLastAndSelectArticle();
	}

	private void selectGaleryAndFilterByColor() {
		if (app==shop) {
			clickMenu(CAMISAS_SHE);
		} else {
			clickMenu(ABRIGOS_SHE);
		}

		var colorsToFilter = new ArrayList<Color>();
		colorsToFilter.add(BLANCO);
		colorsToFilter.add(NEGRO);
		colorsToFilter.add(AZUL);			
		if (app==shop) {
			filterGaleryByColors(CAMISAS_SHE, colorsToFilter);
		} else {
			colorsToFilter.add(GRIS);
			filterGaleryByColors(ABRIGOS_SHE, colorsToFilter);
		}
	}

	private void scrollToLastAndSelectArticle() {
		galeriaSteps.scrollToLast();
		int position = (app==shop) ? 50 : 30;
		galeriaSteps.selectArticulo(position);
		goBackToGalery(position);
		if (!channel.isDevice()) {
			selectArticleInOtherLabel();
		}
	}
	
	private void selectArticleInOtherLabel() {
		int position = (app==shop) ? 50 : 30; 
		galeriaSteps.selectArticuloEnPestanyaAndBack(position);
	}

	private void goBackToGalery(int posArticleToSelect) {
		back();
		galeriaSteps.checkArticleGaleriaVisibleInScreen(posArticleToSelect);
	}

}
