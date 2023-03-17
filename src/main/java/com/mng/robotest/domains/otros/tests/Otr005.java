package com.mng.robotest.domains.otros.tests;

import com.mng.robotest.domains.base.TestBase;
import com.mng.robotest.domains.transversal.prehome.pageobjects.PagePrehome;
import com.mng.robotest.domains.transversal.prehome.steps.PagePrehomeSteps;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.steps.shop.PageIniShopJaponSteps;

import static com.mng.robotest.test.data.PaisShop.*;

public class Otr005 extends TestBase {

	private static final Pais JAPAN = JAPON.getPais();
	private static final IdiomaPais JAPONES = JAPAN.getListIdiomas().get(0);	
	
	@Override
	public void execute() throws Exception {
		dataTest.setPais(JAPAN);
		dataTest.setIdioma(JAPONES);
		PagePrehomeSteps pagePrehomeSteps = new PagePrehomeSteps();
		new PagePrehome().previousAccessShopSteps(true);
		pagePrehomeSteps.seleccionPaisIdioma();
		pagePrehomeSteps.entradaShopGivenPaisSeleccionado();
		new PageIniShopJaponSteps().validaPageIniJapon(2);		
	}
	
}
