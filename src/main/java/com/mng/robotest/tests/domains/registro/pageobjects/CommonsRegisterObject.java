package com.mng.robotest.tests.domains.registro.pageobjects;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.testslegacy.data.PaisShop;

public class CommonsRegisterObject extends PageBase {

	public boolean isGenesis() {
		String urlBase = inputParamsSuite.getUrlBase();
		return 
			!isPRO(urlBase) &&
			PaisShop.ESPANA.isEquals(dataTest.getPais()) &&
			!channel.isDevice();
	}

}
