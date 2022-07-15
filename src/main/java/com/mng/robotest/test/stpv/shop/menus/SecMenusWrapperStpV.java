package com.mng.robotest.test.stpv.shop.menus;

import static com.mng.robotest.test.data.Constantes.PrefixRebajas;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.conf.StoreType;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.Linea;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.beans.Sublinea.SublineaType;
import com.mng.robotest.test.data.CodIdioma;
import com.mng.robotest.test.data.DataCtxShop;
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
import com.mng.robotest.test.stpv.shop.SecFiltrosStpV;
import com.mng.robotest.test.stpv.shop.galeria.PageGaleriaStpV;
import com.mng.robotest.test.stpv.shop.genericchecks.GenericChecks;
import com.mng.robotest.test.stpv.shop.genericchecks.GenericChecks.GenericCheck;
import com.mng.robotest.test.utils.ListComparator;
import com.mng.robotest.test.utils.checkmenus.DataScreenMenu;
import com.mng.robotest.test.utils.checkmenus.Label;
import com.mng.robotest.test.utils.checkmenus.MenuTraduc;

public class SecMenusWrapperStpV {
	
	private final Channel channel;
	private final AppEcom app;
	private final Pais pais;
	private final WebDriver driver;
	private final SecMenusUserSteps secMenusUserStpV;
	private final SecMenuLateralMobilStpV secMenuLateralMobilStpV;
	private final SecMenusDesktopStpV secMenusDesktopStpV;
	private final SecMenusWrap secMenusWrap;
	
	private SecMenusWrapperStpV(Channel channel, AppEcom app, Pais pais, WebDriver driver) {
		this.channel = channel;
		this.app = app;
		this.pais = pais;
		this.driver = driver;
		this.secMenusUserStpV = SecMenusUserSteps.getNew(channel, app, driver);
		this.secMenuLateralMobilStpV = SecMenuLateralMobilStpV.getNew(channel, app, driver);
		this.secMenusDesktopStpV = SecMenusDesktopStpV.getNew(pais, app, channel, driver);
		this.secMenusWrap = SecMenusWrap.getNew(channel, app, driver);
	}
	
	public static SecMenusWrapperStpV getNew(Channel channel, AppEcom app, Pais pais, WebDriver driver) {
		return (new SecMenusWrapperStpV(channel, app, pais, driver));
	}
	
	public static SecMenusWrapperStpV getNew(DataCtxShop dCtxSh, WebDriver driver) {
		return (getNew(dCtxSh.channel, dCtxSh.appE, dCtxSh.pais, driver));
	}
	
	public SecMenusUserSteps getMenusUser() {
		return this.secMenusUserStpV;
	}
	
	@Validation
	public ChecksTM validateLineas(Pais pais) throws Exception {
		ChecksTM validations = ChecksTM.getNew();
		LineaType[] lineasToTest = Linea.LineaType.values();
		for (LineaType lineaType : lineasToTest) {
			if (lineaType.isActiveIn(channel)) {
				ThreeState stateLinea = pais.getShoponline().stateLinea(lineaType, app);
				if ( stateLinea!=ThreeState.UNKNOWN &&
					(lineaType!=LineaType.rebajas || UtilsMangoTest.validarLineaRebajas(pais))) {
					ThreeState apareceLinea = stateLinea;
					
					//Caso especial de un país con una sóla línea de she -> No ha de aparecer la línea de she
					if (lineaType==LineaType.she && app!=AppEcom.outlet && pais.getShoponline().getNumLineasTiendas(app)==1) {
						apareceLinea = ThreeState.FALSE;
					}
					
					boolean isLineaPresent = isLineaPresent(lineaType);
					if (apareceLinea==ThreeState.TRUE) {
						validations.add (
							"<b>Sí</b> aparece el link de la línea <b>" + lineaType + "</b>",
							isLineaPresent, State.Warn);
					} else {
						validations.add (
							"<b>No</b> aparece el link de la línea <b>" + lineaType + "</b>",
							!isLineaPresent, State.Warn);
					}
				}
			}
		}
			
		return validations;
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
		ChecksTM validations = ChecksTM.getNew();
		List<Label> menuInOrderTraduc = MenuTraduc.getLabels(linea.getType(), codIdioma);
		List<DataScreenMenu> listMenusScreen = secMenusWrap.getListDataScreenMenus(linea, null);
		ListComparator comparator = ListComparator.getNew(menuInOrderTraduc, listMenusScreen);
		boolean menusMatch = comparator.listsMatch();
		String html = "";
		if (!menusMatch) {
			html = "<br>" + comparator.getHtml();
		}
		validations.add(
			"Los menús tienen la label y el orden esperado" + html,
			menusMatch, State.Warn);

		return validations;
	}
	
	@Validation
	public ChecksTM checkLineaRebajas(boolean salesOnInCountry, DataCtxShop dCtxSh) {
		ChecksTM validations = ChecksTM.getNew();
		int maxSeconds = 3;
		boolean isPresentLinRebajas = secMenusWrap.isLineaPresentUntil(LineaType.rebajas, maxSeconds);
		if (salesOnInCountry && dCtxSh.pais.isVentaOnline()) {
			validations.add(
				PrefixRebajas + "Aparece la línea \"Rebajas\" (lo esperamos hasta " + maxSeconds + " segundos)",
				isPresentLinRebajas, State.Defect);
		} else {
			validations.add(
				PrefixRebajas + "No aparece la línea \"Rebajas\"",
				!isPresentLinRebajas, State.Defect);
		}
	   
		return validations;
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
					secMenuLateralMobilStpV.stepClickMenu1rstLevel(menu1rstLevel, pais);
				} else {
					secMenusDesktopStpV.stepEntradaMenuDesktop(menu1rstLevel, paginaLinea);
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
	public void accesoMenuXRef(Menu1rstLevel menu1rstLevel, DataCtxShop dCtxSh) throws Exception {
		secMenusWrap.seleccionarMenuXHref(menu1rstLevel, dCtxSh.pais);
		checkIsVisibleAarticle(dCtxSh, 3);
		GenericChecks.from(Arrays.asList(
				GenericCheck.CookiesAllowed,
				GenericCheck.SEO, 
				GenericCheck.JSerrors, 
				GenericCheck.Analitica,
				GenericCheck.TextsTraduced,
				GenericCheck.ImgsBroken)).checks(driver);
	}

	@Validation (
		description="Como mínimo se obtiene 1 artículo (lo esperamos un máximo de #{maxSeconds} segundos)",
		level=State.Warn,
		store=StoreType.None)
	private boolean checkIsVisibleAarticle(DataCtxShop dCtxSh, int maxSeconds) throws Exception {
		PageGaleria pageGaleria = PageGaleria.getNew(dCtxSh.channel, dCtxSh.appE, driver);
		return (pageGaleria.isVisibleArticuloUntil(1, maxSeconds));
	}
	
	public void selectMenu1rstLevelTypeCatalog(Menu1rstLevel menu1rstLevel, DataCtxShop dCtxSh) throws Exception {
		if (dCtxSh.channel.isDevice()) {
			secMenuLateralMobilStpV.selectMenuLateral1rstLevelTypeCatalog(menu1rstLevel, dCtxSh.pais);
		} else {	
			secMenusDesktopStpV.selectMenuSuperiorTypeCatalog(menu1rstLevel, dCtxSh);
		}
	}
	public boolean checkExistMenu1rstLevelTypeCatalog(Menu1rstLevel menu1rstLevel, DataCtxShop dCtxSh) throws Exception {
		if (dCtxSh.channel.isDevice()) {
			return secMenuLateralMobilStpV.checkNotExistsMenuLateral1rstLevelTypeCatalog(menu1rstLevel, dCtxSh.pais);
		} else {	
			return secMenusDesktopStpV.checkNotExistsMenuSuperiorTypeCatalog(menu1rstLevel);
		}
	}
	
	public void selectMenuLateral1erLevelTypeCatalog(Menu1rstLevel menu1rstLevel, DataCtxShop dCtxSh) throws Exception {
		if (dCtxSh.channel.isDevice()) {
			secMenuLateralMobilStpV.selectMenuLateral1rstLevelTypeCatalog(menu1rstLevel, dCtxSh.pais); 
		} else {
			secMenusDesktopStpV.selectMenuLateral1rstLevelTypeCatalog(menu1rstLevel, dCtxSh);		
		}
	}
	
	public void selectMenu2onLevel(Menu2onLevel menu2onLevel, DataCtxShop dCtxSh) throws Exception {
		if (dCtxSh.channel.isDevice()) {
			SecFiltrosStpV.selectFiltroMenus(app, channel, Arrays.asList(menu2onLevel), driver);
		} else {
			secMenusDesktopStpV.selectMenu2oLevel(menu2onLevel, dCtxSh);
		}
	}
	
	public void seleccionLinea(LineaType lineaType, SublineaType sublineaType, DataCtxShop dCtxSh) throws Exception {
		if (sublineaType==null) {
			seleccionLinea(lineaType);
		} else {
			seleccionSublinea(lineaType, sublineaType, dCtxSh);
		}
	}
	
	public void seleccionLinea(LineaType lineaType) throws Exception {
		if (channel.isDevice()) {
			secMenuLateralMobilStpV.seleccionLinea(lineaType, pais);
		} else {
			secMenusDesktopStpV.seleccionLinea(lineaType);
		}
	}
	
	public void seleccionSublinea(LineaType lineaType, SublineaType sublineaType, DataCtxShop dCtxSh)
	throws Exception {
		if (dCtxSh.channel.isDevice()) {
			secMenuLateralMobilStpV.seleccionSublineaNinos(lineaType, sublineaType, pais);
		} else {
			secMenusDesktopStpV.seleccionSublinea(lineaType, sublineaType);
		}
	}
	
	public void selectFiltroCollectionIfExists(FilterCollection typeMenu) throws Exception {
		SecMenusFiltroCollection filtrosCollection = SecMenusFiltroCollection.make(channel, app, driver);
		if (filtrosCollection.isVisibleMenu(FilterCollection.nextSeason)) {
			selectFiltroCollection(typeMenu);
		}
	}
	
	@Step (
		description="Seleccionar filtro de colecciones <b>#{typeMenu}</b>", 
		expected="Aparece una galería con artículos de temporadas#{typeMenu.getListTempArticles()}")
	public void selectFiltroCollection(FilterCollection typeMenu) {
		SecMenusFiltroCollection filtrosCollection = SecMenusFiltroCollection.make(channel, app, driver);
		filtrosCollection.click(typeMenu);
		if (channel==Channel.desktop) {
			PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(channel, app, driver);
			if (typeMenu == FilterCollection.sale) {
				pageGaleriaStpV.validaArticlesOfTemporadas(typeMenu.getListTempArticles());
				pageGaleriaStpV.validaNotArticlesOfTypeDesktop(TypeArticle.norebajado, State.Warn, StoreType.Evidences);
			}
			
			if (typeMenu == FilterCollection.nextSeason) {
				pageGaleriaStpV.validaNotArticlesOfTypeDesktop(TypeArticle.rebajado, State.Info, StoreType.None);
				pageGaleriaStpV.validaArticlesOfTemporadas(typeMenu.getListTempArticles(), State.Info, StoreType.None);
			}
		}
	}	
}
