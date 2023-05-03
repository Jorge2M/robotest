package com.mng.robotest.domains.otros.tests;

import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;

import static com.mng.robotest.test.data.PaisShop.*;

import com.mng.robotest.domains.base.TestBase;
import com.mng.robotest.domains.transversal.acceso.steps.AccesoSteps;

public class Otr003 extends TestBase {

	private static final Pais FRANCIA = FRANCE.getPais();
	private static final IdiomaPais FRANCIA_FRANCES = FRANCIA.getListIdiomas().get(0);
	
	@Override
	public void execute() throws Exception {
		new AccesoSteps().accesoPRYCambioPais(FRANCIA, FRANCIA_FRANCES);		
	}
	
}
