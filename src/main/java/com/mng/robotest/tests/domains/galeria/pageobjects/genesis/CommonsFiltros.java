package com.mng.robotest.tests.domains.galeria.pageobjects.genesis;

import com.mng.robotest.tests.domains.base.PageBase;

public class CommonsFiltros extends PageBase {

	public void selectIntervalImport(int minim, int maxim) {
		//It has not been possible to select the filters from the browser
		driver.get(getCurrentUrl() + "?range=" + minim + "-" + maxim);
	}	
	


}
