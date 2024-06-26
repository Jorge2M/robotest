package com.mng.robotest.tests.domains.changecountry.tests;

import static com.mng.robotest.testslegacy.data.PaisShop.*;
import static com.mng.robotest.testslegacy.pageobject.shop.menus.MenuUserItem.UserMenu.CAMBIO_PAIS;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.changecountry.steps.ModalChangeCountrySteps;
import com.mng.robotest.tests.domains.footer.steps.FooterSteps;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.beans.Pais;

public class Chg001 extends TestBase {

	private static final Pais FRANCIA = FRANCE.getPais();
	private static final IdiomaPais FRANCES = FRANCIA.getListIdiomas().get(0);
	
	@Override
	public void execute() throws Exception {
		//TODO este caso lo quitamos porque es costoso de implementar (página específica) y
		//en algún momento se migrará a Genesis que sí ya lo tenemos cubierto (outlet-device)
		if (isDevice() && isShop()) {
			return;
		}
		access();
		changeCountry(FRANCIA, FRANCES);
	}

	public void changeCountry(Pais pais, IdiomaPais idioma) {
		clickCambioPais();
		new ModalChangeCountrySteps().changeCountry(pais, idioma);
	}
	
	private void clickCambioPais() {
		if (channel.isDevice()) {
			clickUserMenu(CAMBIO_PAIS);
		} else {
			new FooterSteps().clickChangeCountry();
		}
	}

}
