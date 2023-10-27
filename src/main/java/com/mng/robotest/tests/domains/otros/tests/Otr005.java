package com.mng.robotest.tests.domains.otros.tests;

import static com.mng.robotest.testslegacy.data.PaisShop.*;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.otros.steps.PageIniShopJaponSteps;
import com.mng.robotest.tests.domains.transversal.acceso.navigations.AccesoFlows;
import com.mng.robotest.tests.domains.transversal.prehome.steps.PagePrehomeSteps;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.beans.Pais;

public class Otr005 extends TestBase {

	private static final Pais JAPAN = JAPON.getPais();
	private static final IdiomaPais JAPONES = JAPAN.getListIdiomas().get(0);	
	
	@Override
	public void execute() throws Exception {
		dataTest.setPais(JAPAN);
		dataTest.setIdioma(JAPONES);
		new AccesoFlows().previousAccessShopSteps();
		var pagePrehomeSteps = new PagePrehomeSteps();
		pagePrehomeSteps.seleccionPaisIdioma();
		pagePrehomeSteps.entradaShopGivenPaisSeleccionado();
		new PageIniShopJaponSteps().checkPageIniJapon(2);		
	}
	
}
