package com.mng.robotest.domains.ficha.tests;

import com.mng.robotest.domains.ficha.pageobjects.SecDataProduct.ProductNav;
import com.mng.robotest.domains.ficha.pageobjects.SecProductDescrOld.TypePanel;
import com.mng.robotest.domains.ficha.steps.PageFichaSteps;
import com.mng.robotest.domains.galeria.steps.LocationArticle;
import com.mng.robotest.domains.galeria.steps.PageGaleriaSteps;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.domains.transversal.menus.pageobjects.MenuWeb;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.pageobject.shop.PagePrehome;
import com.mng.robotest.test.pageobject.utils.DataFichaArt;
import com.mng.robotest.test.utils.PaisGetter;

import static com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.LineaType.*;
import static com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.SublineaType.*;

public class Fic003 extends TestBase {

	private final Pais corea = PaisGetter.from(PaisShop.COREA_DEL_SUR);
	private final PageGaleriaSteps pageGaleriaSteps = new PageGaleriaSteps();
	private final PageFichaSteps pageFichaSteps = new PageFichaSteps();

	public Fic003() throws Exception {
		super();
		dataTest.setPais(corea);
		dataTest.setIdioma(corea.getListIdiomas().get(0));
	}

	@Override
	public void execute() throws Exception {
		access();
		closeModalNewsLetterIfExists();
		clickMenu(new MenuWeb
				.Builder("Pantalones")
				.linea(nina)
				.sublinea(nina_nina).build());
		
		DataFichaArt dataArtOrigin = selectFirstArticleInGalery();

		kcSafetyTest();
		pageFichaSteps.selectLinkNavigation(ProductNav.NEXT, dataArtOrigin.getReferencia());
		pageFichaSteps.selectLinkNavigation(ProductNav.PREV, dataArtOrigin.getReferencia());
	}

	private void closeModalNewsLetterIfExists() {
		new PagePrehome().closeModalNewsLetterIfExists();
	}

	private DataFichaArt selectFirstArticleInGalery() {
		var location1rstArticle = LocationArticle.getInstanceInCatalog(1);
		return pageGaleriaSteps.selectArticulo(location1rstArticle);
	}

	private void kcSafetyTest() {
		if (channel.isDevice()) {
			if (TypePanel.KC_SAFETY.getListApps().contains(app)) {
				pageFichaSteps.getSecProductDescOldSteps().selectPanel(TypePanel.KC_SAFETY);
			}
		} else {
			if (TypePanel.KC_SAFETY.getListApps().contains(app)) {
				pageFichaSteps.getSecBolsaButtonAndLinksNewSteps().selectDetalleDelProducto(nina);
			}
		}
	}

}
