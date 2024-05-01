package com.mng.robotest.tests.domains.changecountry.tests;

import org.testng.annotations.*;

public class ChangeCountry {

	@Test (
		testName="CHG001",
		groups={"Changecountry", "Smoke", "Canal:desktop,mobile_App:shop,outlet"}, 
		description="Cambiar de país a través de click en link del footer")
	public void changeCountry() throws Exception {
		new Chg001().execute();
	}

}