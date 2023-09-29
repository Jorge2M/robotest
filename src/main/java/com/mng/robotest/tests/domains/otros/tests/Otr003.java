package com.mng.robotest.tests.domains.otros.tests;

import static com.mng.robotest.testslegacy.data.PaisShop.*;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.transversal.acceso.steps.AccesoSteps;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.beans.Pais;

public class Otr003 extends TestBase {

	private static final Pais FRANCIA = FRANCE.getPais();
	private static final IdiomaPais FRANCIA_FRANCES = FRANCIA.getListIdiomas().get(0);
	
	@Override
	public void execute() throws Exception {
		new AccesoSteps().accesoPRYCambioPais(FRANCIA, FRANCIA_FRANCES);		
	}
	
}
