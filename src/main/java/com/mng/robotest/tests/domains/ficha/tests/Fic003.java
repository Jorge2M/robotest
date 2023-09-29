package com.mng.robotest.tests.domains.ficha.tests;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.ficha.steps.PageFichaSteps;
import com.mng.robotest.tests.domains.galeria.steps.LocationArticle;
import com.mng.robotest.tests.domains.galeria.steps.PageGaleriaSteps;
import com.mng.robotest.tests.domains.transversal.menus.pageobjects.MenuWeb;
import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.pageobject.utils.DataFichaArt;

import static com.mng.robotest.tests.domains.ficha.pageobjects.SecDataProduct.ProductNav.*;
import static com.mng.robotest.tests.domains.ficha.pageobjects.SecProductDescrDevice.TypePanel.*;
import static com.mng.robotest.tests.domains.transversal.menus.pageobjects.LineaWeb.LineaType.*;
import static com.mng.robotest.tests.domains.transversal.menus.pageobjects.LineaWeb.SublineaType.*;
import static com.mng.robotest.testslegacy.data.PaisShop.*;

public class Fic003 extends TestBase {

	private final Pais corea = COREA_DEL_SUR.getPais();
	private final PageGaleriaSteps pGaleriaSteps = new PageGaleriaSteps();
	private final PageFichaSteps pFichaSteps = new PageFichaSteps();

	public Fic003() throws Exception {
		super();
		dataTest.setPais(corea);
		dataTest.setIdioma(corea.getListIdiomas().get(0));
	}

	@Override
	public void execute() throws Exception {
		access();
		clickMenu(new MenuWeb
				.Builder("Pantalones")
				.linea(NINA)
				.sublinea(NINA_NINA).build());
		
		DataFichaArt dataArtOrigin = selectFirstArticleInGalery();

		kcSafetyTest();
		pFichaSteps.selectLinkNavigation(NEXT, dataArtOrigin.getReferencia());
		pFichaSteps.selectLinkNavigation(PREV, dataArtOrigin.getReferencia());
	}

	private DataFichaArt selectFirstArticleInGalery() {
		var location1rstArticle = LocationArticle.getInstanceInCatalog(1);
		return pGaleriaSteps.selectArticulo(location1rstArticle);
	}

	private void kcSafetyTest() {
		if (channel.isDevice()) {
			if (KC_SAFETY.getListApps().contains(app)) {
				pFichaSteps.getSecProductDescDeviceSteps().selectPanel(KC_SAFETY);
			}
		} else {
			if (KC_SAFETY.getListApps().contains(app)) {
				pFichaSteps.getSecBolsaButtonAndLinksNewSteps().selectDetalleDelProducto(NINA);
			}
		}
	}

}
