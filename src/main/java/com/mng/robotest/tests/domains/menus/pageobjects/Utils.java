package com.mng.robotest.tests.domains.menus.pageobjects;

import static com.mng.robotest.testslegacy.data.PaisShop.*;

import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.testslegacy.beans.Pais;

public class Utils {

	private Utils() {}
	
	public static boolean isMenuNewService(AppEcom app, Pais pais) {
		if (app==AppEcom.outlet) {
			return true;
		}
		return (
			!ESPANA.isEquals(pais) &&
			!ISLAS_CANARIAS.isEquals(pais) &&
			!MELILLA.isEquals(pais) &&
			!CEUTA.isEquals(pais) &&
			!ITALIA.isEquals(pais) &&
			!NEDERLAND.isEquals(pais) &&
			!FRANCE.isEquals(pais) &&
			!COREA_DEL_SUR.isEquals(pais) &&
			!PORTUGAL.isEquals(pais));
	}


}
