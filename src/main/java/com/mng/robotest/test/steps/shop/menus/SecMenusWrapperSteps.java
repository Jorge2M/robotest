package com.mng.robotest.test.steps.shop.menus;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.conf.StoreType;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.pageobject.shop.filtros.FilterCollection;
import com.mng.robotest.test.pageobject.shop.galeria.PageGaleria;
import com.mng.robotest.test.pageobject.shop.galeria.PageGaleriaDesktop.TypeArticle;
import com.mng.robotest.test.pageobject.shop.menus.SecMenusFiltroCollection;
import com.mng.robotest.test.steps.shop.galeria.PageGaleriaSteps;

public class SecMenusWrapperSteps extends StepBase {

	private final SecMenusUserSteps secMenusUserSteps = new SecMenusUserSteps();
	private final SecMenusDesktopSteps secMenusDesktopSteps = new SecMenusDesktopSteps();
	
	private final Pais pais = dataTest.getPais();
	
	public SecMenusUserSteps getMenusUser() {
		return this.secMenusUserSteps;
	}
	
//	@Validation
//	public ChecksTM validateLineas() throws Exception {
//		ChecksTM checks = ChecksTM.getNew();
//		LineaType[] lineasToTest = LineaType.values();
//		for (LineaType lineaType : lineasToTest) {
//			ThreeState apareceLinea = pais.getShoponline().stateLinea(lineaType, app);
//			if (checkLinea(lineaType, apareceLinea)) {
//				boolean isLineaPresent = new LineaWeb(lineaType).isLineaPresent();
//				if (apareceLinea==ThreeState.TRUE) {
//					checks.add (
//						"<b>Sí</b> aparece el link de la línea <b>" + lineaType + "</b>",
//						isLineaPresent, State.Warn);
//				} else {
//					checks.add (
//						"<b>No</b> aparece el link de la línea <b>" + lineaType + "</b>",
//						!isLineaPresent, State.Warn);
//				}
//			}
//		}
//		return checks;
//	}
//	
//	private boolean checkLinea(LineaType lineaType, ThreeState stateLinea) throws Exception {
//		if (lineaType.isActiveIn(channel)) {
//			if (stateLinea!=ThreeState.UNKNOWN &&
//				(lineaType!=LineaType.rebajas || UtilsMangoTest.validarLineaRebajas(pais))) {
//				return true;
//			}
//		}
//		return false;
//	}
	
//	@Validation
//	public ChecksTM checkOrderAndTranslationMenus(Linea linea, CodIdioma codIdioma) throws Exception {
//		ChecksTM checks = ChecksTM.getNew();
//		List<Label> menuInOrderTraduc = MenuTraduc.getLabels(linea.getType(), codIdioma);
//		List<DataScreenMenu> listMenusScreen = secMenusWrap.getListDataScreenMenus(linea, null);
//		ListComparator comparator = ListComparator.getNew(menuInOrderTraduc, listMenusScreen);
//		boolean menusMatch = comparator.listsMatch();
//		String html = "";
//		if (!menusMatch) {
//			html = "<br>" + comparator.getHtml();
//		}
//		checks.add(
//			"Los menús tienen la label y el orden esperado" + html,
//			menusMatch, State.Warn);
//
//		return checks;
//	}
	
//	/**
//	 * Recorre todos los menús existentes en la página y crea un step por cada uno de ellos
//	 */
//	public void clickAllMenus(LineaType lineaType, SublineaType sublineaType) throws Exception {
//		String paginaLinea = driver.getCurrentUrl();
//		List<DataScreenMenu> listMenusLabel = getListMenus(lineaType, sublineaType);
//		for (int i=0; i<listMenusLabel.size(); i++) {
//			try {
//				if (channel.isDevice()) {
//					secMenuLateralMobilSteps.stepClickMenu1rstLevel(menu1rstLevel, pais);
//				} else {
//					secMenusDesktopSteps.stepEntradaMenuDesktop(menu1rstLevel, paginaLinea);
//				}
//			}
//			catch (Exception e) {
//				Log4jTM.getLogger().warn("Problem in selection of menu " + lineaType + " / " + sublineaType + " / " + listMenusLabel.get(i), e);
//			}
//		}
//	}

//	private List<DataScreenMenu> getListMenus(LineaType lineaType, SublineaType sublineaType) throws Exception {
//		Linea linea = pais.getShoponline().getLinea(lineaType);
//		List<DataScreenMenu> listMenus = secMenusWrap.getListDataScreenMenus(linea, sublineaType);
//		List<DataScreenMenu> listWithoutDuplicates = listMenus.stream()
//				.distinct()
//				.toList();
//		
//		return listWithoutDuplicates;
//	}

	@Validation (
		description="Como mínimo se obtiene 1 artículo (lo esperamos un máximo de #{seconds} segundos)",
		level=State.Warn,
		store=StoreType.None)
	private boolean checkIsVisibleAarticle(int seconds) throws Exception {
		PageGaleria pageGaleria = PageGaleria.getNew(channel);
		return (pageGaleria.isVisibleArticuloUntil(1, seconds));
	}
	
	public void selectFiltroCollectionIfExists(FilterCollection typeMenu) throws Exception {
		SecMenusFiltroCollection filtrosCollection = SecMenusFiltroCollection.make(channel, app);
		if (filtrosCollection.isVisibleMenu(FilterCollection.nextSeason)) {
			selectFiltroCollection(typeMenu);
		}
	}
	
	@Step (
		description="Seleccionar filtro de colecciones <b>#{typeMenu}</b>", 
		expected="Aparece una galería con artículos de temporadas#{typeMenu.getListTempArticles()}")
	public void selectFiltroCollection(FilterCollection typeMenu) {
		SecMenusFiltroCollection filtrosCollection = SecMenusFiltroCollection.make(channel, app);
		filtrosCollection.click(typeMenu);
		if (channel==Channel.desktop) {
			PageGaleriaSteps pageGaleriaSteps = new PageGaleriaSteps();
			if (typeMenu == FilterCollection.sale) {
				pageGaleriaSteps.validaArticlesOfTemporadas(typeMenu.getListTempArticles());
				pageGaleriaSteps.validaNotArticlesOfTypeDesktop(TypeArticle.norebajado, State.Warn, StoreType.Evidences);
			}
			
			if (typeMenu == FilterCollection.nextSeason) {
				pageGaleriaSteps.validaNotArticlesOfTypeDesktop(TypeArticle.rebajado, State.Info, StoreType.None);
				pageGaleriaSteps.validaArticlesOfTemporadas(typeMenu.getListTempArticles(), State.Info, StoreType.None);
			}
		}
	}	
}
