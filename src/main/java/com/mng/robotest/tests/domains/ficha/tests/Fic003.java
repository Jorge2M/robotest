package com.mng.robotest.tests.domains.ficha.tests;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.ficha.steps.FichaSteps;
import com.mng.robotest.tests.domains.galeria.steps.GaleriaSteps;
import com.mng.robotest.tests.domains.menus.pageobjects.MenuWeb;
import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.pageobject.utils.DataFichaArt;

import static com.mng.robotest.tests.domains.ficha.pageobjects.nogenesis.SecProductDescrDevice.TypePanel.*;
import static com.mng.robotest.tests.domains.menus.pageobjects.LineaWeb.LineaType.*;
import static com.mng.robotest.tests.domains.menus.pageobjects.LineaWeb.SublineaType.*;
import static com.mng.robotest.testslegacy.data.PaisShop.*;

public class Fic003 extends TestBase {

	private final Pais corea = COREA_DEL_SUR.getPais();
	private final GaleriaSteps galeriaSteps;
	private final FichaSteps fichaSteps;

	public Fic003() throws Exception {
		super();
		dataTest.setPais(corea);
		galeriaSteps = new GaleriaSteps();
		fichaSteps = new FichaSteps();
	}

	@Override
	public void execute() throws Exception {
		access();
		clickMenu(new MenuWeb
				.Builder("Pantalones")
				.linea(NINA)
				.sublinea(NINA_NINA).build());
		
		selectFirstArticleInGalery();
		kcSafetyTest();
	}

	private DataFichaArt selectFirstArticleInGalery() {
		return galeriaSteps.selectArticulo(1);
	}

	private void kcSafetyTest() {
		if (channel.isDevice()) {
			if (KC_SAFETY.getListApps().contains(app)) {
				fichaSteps.getSecProductDescDeviceSteps().selectPanel(KC_SAFETY);
			}
		} else {
			if (KC_SAFETY.getListApps().contains(app)) {
				fichaSteps.selectDetalleDelProducto(NINA);
			}
		}
	}

}
