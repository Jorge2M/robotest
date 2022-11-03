package com.mng.robotest.test.appshop.egyptorders;

import java.io.Serializable;
import org.testng.annotations.Test;

import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.mng.robotest.test.factoryes.entities.EgyptCity;

public class EgyptOrderTest implements Serializable {

	private static final long serialVersionUID = 1L;
	private String indexFact;
	private final EgyptCity egyptCity;
	
	
	public EgyptOrderTest(EgyptCity egyptCity) {
		this.egyptCity = egyptCity;
		this.indexFact = getIndexFactoria(egyptCity);
	}
	
	private String getIndexFactoria(EgyptCity egyptCity) {
		return String.format(" %s-%s", egyptCity.getState(), egyptCity.getCity());
	}
	
	@Test (
		groups={"Compra", "Canal:desktop_App:shop"},
		description="Test de compra en Egypto parametrizado por stado y ciudad")
	public void EGY001_Compra() throws Exception {
		TestCaseTM.addNameSufix(this.indexFact);
		new Egy001(egyptCity).execute();
	}
	
	
}
