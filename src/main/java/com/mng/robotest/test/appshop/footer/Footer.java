package com.mng.robotest.test.appshop.footer;

import org.testng.annotations.Test;

public class Footer {

	@Test(
		groups = { "Footer", "Canal:desktop,mobile_App:shop", "Canal:desktop_App:outlet" }, alwaysRun = true, 
		description="Verificar que los links del footer aparecen y redirigen correctamente")
	public void FOO001_Menu() throws Exception {
		new Foo001().execute();
	}
}
