package com.mng.robotest.domains.ficha.tests;

import com.mng.robotest.domains.ficha.pageobjects.PageFicha.TypeFicha;
import com.mng.robotest.domains.ficha.pageobjects.SecDataProduct.ProductNav;
import com.mng.robotest.domains.ficha.pageobjects.SecProductDescrOld.TypePanel;
import com.mng.robotest.domains.ficha.steps.PageFichaArtSteps;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.beans.Sublinea.SublineaType;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.pageobject.shop.PagePrehome;
import com.mng.robotest.test.pageobject.shop.menus.KeyMenu1rstLevel;
import com.mng.robotest.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test.pageobject.shop.menus.MenuTreeApp;
import com.mng.robotest.test.pageobject.utils.DataFichaArt;
import com.mng.robotest.test.steps.shop.AccesoSteps;
import com.mng.robotest.test.steps.shop.galeria.LocationArticle;
import com.mng.robotest.test.steps.shop.galeria.PageGaleriaSteps;
import com.mng.robotest.test.steps.shop.menus.SecMenusWrapperSteps;
import com.mng.robotest.test.utils.PaisGetter;


public class Fic003 extends TestBase {

	final Menu1rstLevel menuPantalonesNina;
	
	final Pais corea = PaisGetter.get(PaisShop.COREA_DEL_SUR);
	final PageGaleriaSteps pageGaleriaSteps = new PageGaleriaSteps();
	final PageFichaArtSteps pageFichaSteps = new PageFichaArtSteps();
	final SecMenusWrapperSteps secMenusSteps;
	
	public Fic003() throws Exception {
		super();

		Pais corea = PaisGetter.get(PaisShop.COREA_DEL_SUR);
		dataTest.pais = corea;
		dataTest.idioma = corea.getListIdiomas().get(1);
		
		menuPantalonesNina = MenuTreeApp.getMenuLevel1From(
				app, KeyMenu1rstLevel.from(
					LineaType.nina, 
					SublineaType.nina_nina, "pantalones"));
		
		secMenusSteps = new SecMenusWrapperSteps();		
	}
	
	@Override
	public void execute() throws Exception {
		new AccesoSteps().oneStep(false);
		closeModalNewsLetterIfExists();
		secMenusSteps.selectMenu1rstLevelTypeCatalog(menuPantalonesNina);
		DataFichaArt dataArtOrigin = selectFirstArticleInGalery();
		
		kcSafetyTest();
		pageFichaSteps.selectLinkNavigation(ProductNav.NEXT, dataArtOrigin.getReferencia());
		pageFichaSteps.selectLinkNavigation(ProductNav.PREV, dataArtOrigin.getReferencia());
	}

	private void closeModalNewsLetterIfExists() {
		new PagePrehome().closeModalNewsLetterIfExists();
	}

	private DataFichaArt selectFirstArticleInGalery() {
		LocationArticle location1rstArticle = LocationArticle.getInstanceInCatalog(1);
		DataFichaArt dataArtOrigin = pageGaleriaSteps.selectArticulo(location1rstArticle);
		return dataArtOrigin;
	}

	private void kcSafetyTest() throws Exception {
		if (pageFichaSteps.getFicha().getTypeFicha()==TypeFicha.OLD) {
			if (TypePanel.KC_SAFETY.getListApps().contains(app)) {
				pageFichaSteps.getSecProductDescOldSteps().selectPanel(TypePanel.KC_SAFETY);
			}
		} else {
			if (TypePanel.KC_SAFETY.getListApps().contains(app)) {
				pageFichaSteps.getSecBolsaButtonAndLinksNewSteps().selectDetalleDelProducto(LineaType.nina);
			}
		}
	}

}
