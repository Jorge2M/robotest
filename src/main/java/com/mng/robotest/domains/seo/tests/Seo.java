package com.mng.robotest.domains.seo.tests;

import org.testng.annotations.Test;

public class Seo {

	@Test(
		groups = { "Otras", "Canal:desktop_App:shop,outlet"}, 
		description="Comprobar existencia y contenido del fichero robots.txt")
	public void SEO001_check_RobotsSitemap() throws Exception {
		new Seo001().execute();
	}
}