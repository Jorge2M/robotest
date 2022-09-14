package com.mng.robotest.test.steps.shop.menus;

import static com.mng.robotest.test.data.Constantes.PrefixRebajas;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.conf.StoreType;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.beans.Linea;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.beans.Sublinea.SublineaType;
import com.mng.robotest.test.data.CodIdioma;
import com.mng.robotest.test.data.Constantes.ThreeState;
import com.mng.robotest.test.generic.UtilsMangoTest;
import com.mng.robotest.test.pageobject.shop.filtros.FilterCollection;
import com.mng.robotest.test.pageobject.shop.galeria.PageGaleria;
import com.mng.robotest.test.pageobject.shop.galeria.PageGaleriaDesktop.TypeArticle;
import com.mng.robotest.test.pageobject.shop.menus.KeyMenu1rstLevel;
import com.mng.robotest.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test.pageobject.shop.menus.Menu2onLevel;
import com.mng.robotest.test.pageobject.shop.menus.MenuTreeApp;
import com.mng.robotest.test.pageobject.shop.menus.SecMenusFiltroCollection;
import com.mng.robotest.test.pageobject.shop.menus.SecMenusWrap;
import com.mng.robotest.test.steps.shop.SecFiltrosSteps;
import com.mng.robotest.test.steps.shop.galeria.PageGaleriaSteps;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks.GenericCheck;
import com.mng.robotest.test.utils.ListComparator;
import com.mng.robotest.test.utils.checkmenus.DataScreenMenu;
import com.mng.robotest.test.utils.checkmenus.Label;
import com.mng.robotest.test.utils.checkmenus.MenuTraduc;

public class SecMenusWrapperSteps extends StepBase {

	private final SecMenusUserSteps secMenusUserSteps = new SecMenusUserSteps();
	private final SecMenuLateralMobilSteps secMenuLateralMobilSteps = new SecMenuLateralMobilSteps();
	private final SecMenusWrap secMenusWrap = new SecMenusWrap();
	private final SecMenusDesktopSteps secMenusDesktopSteps = new SecMenusDesktopSteps();
	
	private final Pais pais = dataTest.pais;
	
	public SecMenusUserSteps getMenusUser() {
		return this.secMenusUserSteps;
	}
	
	@Validation
	public ChecksTM validateLineas() throws Exception {
		ChecksTM checks = ChecksTM.getNew();
		LineaType[] lineasToTest = Linea.LineaType.values();
		for (LineaType lineaType : lineasToTest) {
			ThreeState apareceLinea = pais.getShoponline().stateLinea(lineaType, app);
			if (checkLinea(lineaType, apareceLinea)) {
//				//Caso especial de un país con una sóla línea de she -> No ha de aparecer la línea de she
//				if (lineaType==LineaType.she && app!=AppEcom.outlet && pais.getShoponline().getNumLineasTiendas(app)==1) {
//					apareceLinea = ThreeState.FALSE;
//				}
				
				boolean isLineaPresent = isLineaPresent(lineaType);
				if (apareceLinea==ThreeState.TRUE) {
					checks.add (
						"<b>Sí</b> aparece el link de la línea <b>" + lineaType + "</b>",
						isLineaPresent, State.Warn);
				} else {
					checks.add (
						"<b>No</b> aparece el link de la línea <b>" + lineaType + "</b>",
						!isLineaPresent, State.Warn);
				}
			}
		}
			
		return checks;
	}
	
	private boolean checkLinea(LineaType lineaType, ThreeState stateLinea) throws Exception {
		if (lineaType.isActiveIn(channel)) {
			if (stateLinea!=ThreeState.UNKNOWN &&
				(lineaType!=LineaType.rebajas || UtilsMangoTest.validarLineaRebajas(pais))) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isLineaPresent(LineaType lineaType) {
		try {
			return secMenusWrap.isLineaPresent(lineaType);
		} 
		catch (Exception e) {
			return false;
		}
	}
	
	@Validation
	public ChecksTM checkOrderAndTranslationMenus(Linea linea, CodIdioma codIdioma) throws Exception {
		ChecksTM checks = ChecksTM.getNew();
		List<Label> menuInOrderTraduc = MenuTraduc.getLabels(linea.getType(), codIdioma);
		List<DataScreenMenu> listMenusScreen = secMenusWrap.getListDataScreenMenus(linea, null);
		ListComparator comparator = ListComparator.getNew(menuInOrderTraduc, listMenusScreen);
		boolean menusMatch = comparator.listsMatch();
		String html = "";
		if (!menusMatch) {
			html = "<br>" + comparator.getHtml();
		}
		checks.add(
			"Los menús tienen la label y el orden esperado" + html,
			menusMatch, State.Warn);

		return checks;
	}
	
	@Validation
	public ChecksTM checkLineaRebajas(boolean salesOnInCountry) {
		ChecksTM checks = ChecksTM.getNew();
		int seconds = 3;
		boolean isPresentLinRebajas = secMenusWrap.isLineaPresentUntil(LineaType.rebajas, seconds);
		if (salesOnInCountry && pais.isVentaOnline()) {
			checks.add(
				PrefixRebajas + "Aparece la línea \"Rebajas\" (lo esperamos hasta " + seconds + " segundos)",
				isPresentLinRebajas, State.Defect);
		} else {
			checks.add(
				PrefixRebajas + "No aparece la línea \"Rebajas\"",
				!isPresentLinRebajas, State.Defect);
		}
	   
		return checks;
	}

	/**
	 * Recorre todos los menús existentes en la página y crea un step por cada uno de ellos
	 */
	public void stepsMenusLinea(LineaType lineaType, SublineaType sublineaType) throws Exception {
		String paginaLinea = driver.getCurrentUrl();
		List<DataScreenMenu> listMenusLabel = getListMenus(lineaType, sublineaType);
		for (int i=0; i<listMenusLabel.size(); i++) {
			try {
				Menu1rstLevel menu1rstLevel = MenuTreeApp.getMenuLevel1From(app, KeyMenu1rstLevel.from(lineaType, sublineaType, listMenusLabel.get(i)));
				if (channel.isDevice()) {
					secMenuLateralMobilSteps.stepClickMenu1rstLevel(menu1rstLevel, pais);
				} else {
					secMenusDesktopSteps.stepEntradaMenuDesktop(menu1rstLevel, paginaLinea);
				}
			}
			catch (Exception e) {
				Log4jTM.getLogger().warn("Problem in selection of menu " + lineaType + " / " + sublineaType + " / " + listMenusLabel.get(i), e);
			}
		}
	}

	private List<DataScreenMenu> getListMenus(LineaType lineaType, SublineaType sublineaType) throws Exception {
		Linea linea = pais.getShoponline().getLinea(lineaType);
		List<DataScreenMenu> listMenus = secMenusWrap.getListDataScreenMenus(linea, sublineaType);
		List<DataScreenMenu> listWithoutDuplicates = listMenus.stream()
				.distinct()
				.collect(Collectors.toList());
		return listWithoutDuplicates;
	}

	@Step (
		description="Seleccionar el menú <b>#{menu1rstLevel}</b>",
		expected="Se obtiene el catálogo de artículos asociados al menú")
	public void accesoMenuXRef(Menu1rstLevel menu1rstLevel) throws Exception {
		secMenusWrap.seleccionarMenuXHref(menu1rstLevel, pais);
		checkIsVisibleAarticle(5);
		GenericChecks.checkDefault();
		GenericChecks.from(Arrays.asList(GenericCheck.ImgsBroken)).checks();
	}

	@Validation (
		description="Como mínimo se obtiene 1 artículo (lo esperamos un máximo de #{seconds} segundos)",
		level=State.Warn,
		store=StoreType.None)
	private boolean checkIsVisibleAarticle(int seconds) throws Exception {
		PageGaleria pageGaleria = PageGaleria.getNew(channel);
		return (pageGaleria.isVisibleArticuloUntil(1, seconds));
	}
	
	public void selectMenu1rstLevelTypeCatalog(Menu1rstLevel menu1rstLevel) throws Exception {
		if (channel.isDevice()) {
			secMenuLateralMobilSteps.selectMenuLateral1rstLevelTypeCatalog(menu1rstLevel, pais);
		} else {	
			secMenusDesktopSteps.selectMenuSuperiorTypeCatalog(menu1rstLevel);
		}
	}
	public boolean checkExistMenu1rstLevelTypeCatalog(Menu1rstLevel menu1rstLevel) throws Exception {
		if (channel.isDevice()) {
			return secMenuLateralMobilSteps.checkNotExistsMenuLateral1rstLevelTypeCatalog(menu1rstLevel, pais);
		} else {	
			return secMenusDesktopSteps.checkNotExistsMenuSuperiorTypeCatalog(menu1rstLevel);
		}
	}
	
	public void selectMenuLateral1erLevelTypeCatalog(Menu1rstLevel menu1rstLevel) throws Exception {
		if (channel.isDevice()) {
			secMenuLateralMobilSteps.selectMenuLateral1rstLevelTypeCatalog(menu1rstLevel, pais); 
		} else {
			secMenusDesktopSteps.selectMenuLateral1rstLevelTypeCatalog(menu1rstLevel);		
		}
	}
	
	public void selectMenu2onLevel(Menu2onLevel menu2onLevel) throws Exception {
		if (channel.isDevice()) {
			new SecFiltrosSteps().selectFiltroMenus(Arrays.asList(menu2onLevel));
		} else {
			secMenusDesktopSteps.selectMenu2oLevel(menu2onLevel);
		}
	}
	
	public void seleccionLinea(LineaType lineaType, SublineaType sublineaType) throws Exception {
		if (sublineaType==null) {
			seleccionLinea(lineaType);
		} else {
			seleccionSublinea(lineaType, sublineaType);
		}
	}
	
	public void seleccionLinea(LineaType lineaType) throws Exception {
		if (channel.isDevice()) {
			secMenuLateralMobilSteps.seleccionLinea(lineaType, pais);
		} else {
			secMenusDesktopSteps.seleccionLinea(lineaType);
		}
	}
	
	public void seleccionSublinea(LineaType lineaType, SublineaType sublineaType) throws Exception {
		if (channel.isDevice()) {
			secMenuLateralMobilSteps.seleccionSublineaNinos(lineaType, sublineaType, pais);
		} else {
			secMenusDesktopSteps.seleccionSublinea(lineaType, sublineaType);
		}
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
