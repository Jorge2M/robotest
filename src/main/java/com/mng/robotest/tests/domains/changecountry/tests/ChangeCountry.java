package com.mng.robotest.tests.domains.changecountry.tests;

import org.testng.annotations.*;

public class ChangeCountry {

	@Test (
		groups={"Changecountry", "Canal:desktop,mobile_App:shop,outlet"}, 
		description="Cambiar de país a través de click en link del footer")
	public void CHG001_changeCountry() throws Exception {
		new Chg001().execute();
	}

}