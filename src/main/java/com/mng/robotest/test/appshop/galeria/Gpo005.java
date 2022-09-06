package com.mng.robotest.test.appshop.galeria;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.data.Constantes;
import com.mng.robotest.test.getdata.usuarios.GestorUsersShop;
import com.mng.robotest.test.getdata.usuarios.UserShop;
import com.mng.robotest.test.pageobject.shop.menus.KeyMenu1rstLevel;
import com.mng.robotest.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test.pageobject.shop.menus.Menu2onLevel;
import com.mng.robotest.test.pageobject.shop.menus.MenuTreeApp;
import com.mng.robotest.test.steps.shop.AccesoSteps;
import com.mng.robotest.test.steps.shop.galeria.PageGaleriaSteps;
import com.mng.robotest.test.steps.shop.menus.SecMenusWrapperSteps;

public class Gpo005 extends TestBase {

	@Override
	public void execute() throws Exception {
		UserShop userShop = GestorUsersShop.checkoutBestUserForNewTestCase();
		dataTest.userConnected = userShop.user;
		dataTest.passwordUser = userShop.password;
		dataTest.userRegistered = true;
	
		new AccesoSteps().oneStep(false);
		PageGaleriaSteps pageGaleriaSteps = new PageGaleriaSteps();
		Menu1rstLevel menuCamisas = MenuTreeApp.getMenuLevel1From(app, KeyMenu1rstLevel.from(LineaType.she, null, "camisas"));
		Menu1rstLevel menuCardigans = MenuTreeApp.getMenuLevel1From(app, KeyMenu1rstLevel.from(LineaType.she, null, "cardigans-y-jerseis"));
		SecMenusWrapperSteps secMenusSteps = new SecMenusWrapperSteps();
		if (app==AppEcom.outlet || channel.isDevice()) {
			secMenusSteps.selectMenu1rstLevelTypeCatalog(menuCardigans);
		} else {
			Menu1rstLevel menuNuevo = MenuTreeApp.getMenuLevel1From(app, KeyMenu1rstLevel.from(LineaType.she, null, "New Now"));
			secMenusSteps.selectMenu1rstLevelTypeCatalog(menuNuevo);
			pageGaleriaSteps.secCrossSellingSteps.validaIsCorrect(LineaType.she, null);
			pageGaleriaSteps.hayPanoramicasEnGaleriaDesktop(Constantes.PORC_PANORAMICAS);
		}
		
		if (!channel.isDevice()) {
			pageGaleriaSteps.getSecSelectorPreciosSteps().seleccionaIntervalo();
		}

		if (app!=AppEcom.outlet && !channel.isDevice()) {
			pageGaleriaSteps.secCrossSellingSteps.validaIsCorrect(LineaType.she, null);
			pageGaleriaSteps.hayPanoramicasEnGaleriaDesktop(Constantes.PORC_PANORAMICAS);
		}	
		
		selectMenuVestidos(secMenusSteps);
		
		if (app==AppEcom.outlet || channel.isDevice()) {
			secMenusSteps.selectMenuLateral1erLevelTypeCatalog(menuCardigans);
			Menu2onLevel menuCardigansJerseis = MenuTreeApp.getMenuLevel2From(menuCardigans, "jerséis");
			secMenusSteps.selectMenu2onLevel(menuCardigansJerseis);
		} else {
			secMenusSteps.selectMenuLateral1erLevelTypeCatalog(menuCamisas);
			Menu2onLevel menuCamisasBasicas = MenuTreeApp.getMenuLevel2From(menuCamisas, "básicas");
			secMenusSteps.selectMenu2onLevel(menuCamisasBasicas);
		}		
	}
	
	private void selectMenuVestidos(SecMenusWrapperSteps secMenusSteps) throws Exception {
		String menuToClick = "vestidos";
		Menu1rstLevel menuVestidos = MenuTreeApp.getMenuLevel1From(app, KeyMenu1rstLevel.from(LineaType.she, null, menuToClick));
		secMenusSteps.selectMenu1rstLevelTypeCatalog(menuVestidos);
	}	

}
