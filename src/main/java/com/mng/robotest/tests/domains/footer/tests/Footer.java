package com.mng.robotest.tests.domains.footer.tests;

import org.testng.annotations.Test;

public class Footer {

	@Test(
		groups={ "Footer", "Canal:desktop,mobile_App:shop", "Canal:desktop_App:outlet"}, alwaysRun = true, 
		description="Verificar que los links del footer aparecen y redirigen correctamente")
	public void FOO001_Menu() throws Exception {
		new Foo001().execute();
	}

}
