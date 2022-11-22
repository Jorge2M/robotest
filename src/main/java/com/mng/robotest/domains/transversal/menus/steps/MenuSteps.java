package com.mng.robotest.domains.transversal.menus.steps;

import static com.mng.robotest.domains.transversal.menus.beans.FactoryMenus.MenuItem.ABRIGOS_SHE;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.conf.StoreType;
import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.ficha.steps.PageFichaSteps;
import com.mng.robotest.domains.galeria.pageobjects.PageGaleria;
import com.mng.robotest.domains.galeria.pageobjects.PageGaleriaDesktop;
import com.mng.robotest.domains.galeria.steps.PageGaleriaSteps;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.domains.transversal.menus.beans.FactoryMenus;
import com.mng.robotest.domains.transversal.menus.pageobjects.GroupWeb;
import com.mng.robotest.domains.transversal.menus.pageobjects.GroupWeb.GroupResponse;
import com.mng.robotest.domains.transversal.menus.pageobjects.GroupWeb.GroupType;
import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb;
import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.LineaType;
import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.SublineaType;
import com.mng.robotest.getdata.productlist.GetterProducts;
import com.mng.robotest.getdata.productlist.entity.GarmentCatalog;
import com.mng.robotest.getdata.productlist.entity.GarmentCatalog.Article;
import com.mng.robotest.domains.transversal.menus.pageobjects.MenuWeb;
import com.mng.robotest.domains.transversal.menus.pageobjects.MenusWebAll;
import com.mng.robotest.test.beans.Linea;
import com.mng.robotest.test.data.Constantes.ThreeState;
import com.mng.robotest.test.pageobject.shop.AllPages;
import com.mng.robotest.test.pageobject.utils.DataFichaArt;
import com.mng.robotest.test.steps.shop.banner.SecBannersSteps;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks.GenericCheck;

public class MenuSteps extends StepBase {

	public void clickMenu(String menuLabel) {
		click(MenuWeb.of(menuLabel));
	}
	
	public void click(MenuWeb menu) {
		if (menu.getSublinea()==null) {
			clickMenu(menu);
		} else {
			clickMenuSublinea(menu);
		}
	}
	
	@Step (
		description=
			"Selección del menú <b>#{menu.getLinea()} / #{menu.getGroup()} / </b>" + 
		    "<b style=\"color:blue;\">#{menu.getMenu()}</b>", 
		expected=
			"La selección es correcta")	
	public void clickMenu(MenuWeb menu) {
		menuClick(menu);
	}
	
	public void clickGroup(GroupWeb group) {
		if (group.getSublinea()==null) {
			clickGroupLinea(group);
		} else {
			clickGroupSublinea(group);
		}
	}

	@Step (
		description=
			"Selección del grupo <b>#{group.getLinea()} / </b>" + 
		    "<b style=\"color:brown;\">#{group.getGroup()}</b>", 
		expected=
			"La selección es correcta")	
	private void clickGroupLinea(GroupWeb group) {
		selectGroup(group);
	}

	@Step (
		description=
			"Selección del grupo <b>#{group.getLinea()} / #{group.getSublinea()} / </b>" + 
		    "<b style=\"color:brown;\">#{group.getGroup()}</b>", 
		expected=
			"La selección es correcta")	
	private void clickGroupSublinea(GroupWeb group) {
		selectGroup(group);
	}	
	
	private void selectGroup(GroupWeb group) {
		group.click();
		checkSelecGroup(group);
	}
	 
	private void checkSelecGroup(GroupWeb groupWeb) {
		if (groupWeb.getGroup().getGroupResponse()==GroupResponse.ARTICLES) {
			new PageGaleriaSteps().validateGaleriaAfeterSelectMenu();
		} else {
			checkGroupSubMenuVisible(groupWeb);
		}
	}
	
	@Validation (
		description="Es visible la capa de los submenús del grupo #{groupWeb.getGroup()}",
		level=State.Info)
	private boolean checkGroupSubMenuVisible(GroupWeb groupWeb) {
		return groupWeb.isVisibleSubMenus();
	}	

	@Step (
		description=
			"Selección del menú <b>#{menu.getLinea()} / #{menu.getSublinea()} / #{menu.getGroup()} / </b>" + 
		    "<b style=\"color:blue;\">#{menu.getMenu()}</b>", 
		expected=
			"La selección es correcta")	
	private void clickMenuSublinea(MenuWeb menu) {
		menuClick(menu);
	}
	
	private void menuClick(MenuWeb menu) {
		menu.click();
		checkSelecMenu(menu);
	}
	
	public void clickSubMenu(MenuWeb menu) {
		click(menu);
		if (menu.getSublinea()==null) {
			clickSubMenuLinea(menu);
		} else {
			clickSubMenuSubLinea(menu);
		}
	}	
	
	@Step (
		description=
			"Selección del menú <b>#{menu.getLinea()} / #{menu.getGroup()} / " + 
		    "#{menu.getMenu()} / </b><b style=\"color:blue;\">#{menu.getSubMenu()}</b>", 
		expected=
			"La selección es correcta")	
	private void clickSubMenuLinea(MenuWeb menu) {
		menu.clickSubMenu();
		checkSelectSubMenu(menu);
	}
	
	@Step (
		description=
			"Selección del menú <b>#{menu.getLinea()} / #{menu.getSublinea()} / #{menu.getGroup()} / " + 
		    "#{menu.getMenu()} </b><b style=\"color:blue;\">#{menu.getSubMenu()}</b>", 
		expected=
			"La selección es correcta")	
	private void clickSubMenuSubLinea(MenuWeb menu) {
		clickMenu(menu);
		menu.clickSubMenu();
		checkSelectSubMenu(menu);
	}	
	
	public void checkSelecMenu(MenuWeb menu) {
		isTitleAssociatedMenu(menu.getNameScreen());
		new PageGaleriaSteps().validateGaleriaAfeterSelectMenu();
		if (menu.getSubMenus()!=null && !menu.getSubMenus().isEmpty()) {
			checkVisibilitySubmenus(menu);
		}
		if (channel==Channel.desktop &&
			menu.getArticles()!=null && !menu.getArticles().isEmpty()) {
			checkArticlesContainsLiteralsDesktop(menu.getArticles());
		}
		
		GenericChecks.checkDefault();
		GenericChecks.from(Arrays.asList(
				GenericCheck.GOOGLE_ANALYTICS,
				GenericCheck.NET_TRAFFIC)).checks();
	}
	
	@Validation
	private ChecksTM isTitleAssociatedMenu(String nameMenu) {
		ChecksTM checks = ChecksTM.getNew();
		boolean isTitleAccording = new AllPages().isTitleAssociatedToMenu(nameMenu);
	 	checks.add(
	 		Check.make(
	 				"El title de la página es el asociado al menú <b>" + nameMenu + "</b>",
	 				isTitleAccording, State.Info)
	 			.store(StoreType.None).build());
	 	
	 	if (!isTitleAccording) {
		 	checks.add(
		 	    Check.make(
				    "El título no coincide -> Validamos que exista el header <b>" + 
		 	        nameMenu + "</b> en el inicio de la galería",
		 	       PageGaleria.getNew(channel).isHeaderArticlesVisible(nameMenu), State.Warn)
		 	    .store(StoreType.Evidences).build());
	 	}
	 	return checks;
	}
	
	private void checkSelectSubMenu(MenuWeb menu) {
		new PageGaleriaSteps().validateGaleriaAfeterSelectMenu();
		if (channel==Channel.desktop &&
			menu.getArticlesSubMenu()!=null && !menu.getArticlesSubMenu().isEmpty()) {
			checkArticlesContainsLiteralsDesktop(menu.getArticlesSubMenu());
		}
		GenericChecks.checkDefault();
	}
	
	@Validation (
		description="Son visibles los submenus <b>#{menu.getSubMenus()}</b>",
		level=State.Defect)
	private boolean checkVisibilitySubmenus(MenuWeb menu) {
		return menu.isVisibleSubMenus();
	}
	
	@Validation
	private ChecksTM checkArticlesContainsLiteralsDesktop(List<String> articles) {
		PageGaleriaDesktop pageGaleriaDesktop = (PageGaleriaDesktop)PageGaleria.getNew(channel);
		List<String> articlesNoValid = pageGaleriaDesktop.getArticlesNoValid(articles);
		State stateVal = (articlesNoValid.size()<10) ? State.Warn : State.Defect;
		
		ChecksTM checks = ChecksTM.getNew();
		checks.add(
			Check.make(
				"Todos los artículos contienen alguno de los literales: <b>" + articles + "</b>",
				articlesNoValid.isEmpty(), stateVal)
			.info(getInfoError(articlesNoValid))
			.build());
	
		return checks;
	}	
	
	public void clickLinea(LineaType lineaType) {
		clickLinea(new LineaWeb(lineaType));
	}
	
	public void clickLinea(LineaType lineaType, SublineaType sublineaType) {
		if (sublineaType==null) {
			clickLinea(new LineaWeb(lineaType));
		}
		if (sublineaType!=null) {
			if (channel.isDevice()) {
				clickLinea(new LineaWeb(lineaType));
			} else {
				hoverLineaDesktop(new LineaWeb(lineaType));
			}
			clickSublinea(new LineaWeb(lineaType, sublineaType));
		}
	}	
	
	@Step (
		description=
			"Seleccionar la <b style=\"color:chocolate\">Línea</b> " + 
			"<b style=\"color:brown;\">\"#{lineaWeb.getLinea()}</b>",
		expected=
			"Aparece la página correcta asociada a la línea #{lineaWeb.getLinea()}")
	public void clickLinea(LineaWeb lineaWeb) {
		lineaWeb.clickLinea();	   
		validaSelecLinea(lineaWeb);
	}
	
	private void validaSelecLinea(LineaWeb lineaWeb) {
		validateIsLineaSelected(lineaWeb);
		Linea linea = Linea.getLinea(lineaWeb.getLinea(), dataTest.getPais());
		checkContentGaleriaAfterClickLinea(linea);

		GenericChecks.checkDefault();
		GenericChecks.from(Arrays.asList(GenericCheck.IMGS_BROKEN)).checks();
	}	
	
	@Step (
		description=
			"Hover sobre la <b style=\"color:chocolate\">Línea</b> " + 
			"<b style=\"color:brown;\">\"#{lineaWeb.getLinea()}</b>",
		expected=
			"Aparecen los menús asociados a la línea #{lineaWeb.getLinea()}")
	public void hoverLineaDesktop(LineaWeb lineaWeb) {
		lineaWeb.hoverLinea();	   
		validateHoverLineaDesktop(lineaWeb);
	}	
	
	@Validation (
		description="Aparecen los menús",
		level=State.Info,
		store=StoreType.None)
	public boolean validateHoverLineaDesktop(LineaWeb lineaWeb) {
		return MenusWebAll.make(channel).isMenuInState(true, 1);
	}	
	
	@Validation (
		description="Está seleccionada la línea <b>#{lineaWeb.getLinea()}</b>",
		level=State.Info,
		store=StoreType.None)
	public boolean validateIsLineaSelected(LineaWeb lineaWeb) {
		return lineaWeb.isLineaSelected(0);
	}	
		
	private void checkContentGaleriaAfterClickLinea(Linea linea) {
		switch (linea.getContentDeskType()) {
		case articulos:
			new PageGaleriaSteps().validaArtEnContenido(3);
			break;
		case banners:
			if (!channel.isDevice()) {
				new SecBannersSteps(1).validaBannEnContenido();
			}
			break;
		case vacio:
			break;
		default:
			break;
		}
	}	
	
	@Step (
		description=
			"Seleccionar la línea / <b style=\"color:chocolate\">Sublínea</b> " + 
			"<b style=\"color:brown;\">\"#{lineaWeb.getLinea()} / #{lineaWeb.getSublinea()}</b>",
		expected=
			"Aparece la página correcta asociada a la línea/sublínea")
	public void clickSublinea(LineaWeb lineaWeb) {
		lineaWeb.clickSublinea();
		validaSelecSublinea(lineaWeb);
	}	
	
	private void validaSelecSublinea(LineaWeb lineaWeb) {
		validateIsSubLineaSelected(lineaWeb);
		Linea linea = Linea.getLinea(lineaWeb.getLinea(), dataTest.getPais());
		Linea subLinea = linea.getSublineaNinos(lineaWeb.getSublinea());
		checkContentGaleriaAfterClickLinea(subLinea);

		GenericChecks.checkDefault();
		GenericChecks.from(Arrays.asList(GenericCheck.IMGS_BROKEN)).checks();
	}		
	
	@Validation (
		description="Está seleccionada la sublínea #{lineaWeb.getSublinea()} / <b>#{lineaWeb.getSublinea()}</b>",
		level=State.Info,
		store=StoreType.None)
	public boolean validateIsSubLineaSelected(LineaWeb lineaWeb) {
		return lineaWeb.isSublineaSelected(0);
	}		
	
	@Validation
	public ChecksTM checkLineasCountry() {
		ChecksTM checks = ChecksTM.getNew();
		LineaType[] lineasToTest = LineaType.values();
		for (LineaType lineaType : lineasToTest) {
			ThreeState apareceLinea = dataTest.getPais().getShoponline().stateLinea(lineaType, app);
			if (checkLinea(lineaType, apareceLinea)) {
				boolean isLineaPresent = new LineaWeb(lineaType).isLineaPresent(0);
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
	
	private boolean checkLinea(LineaType lineaType, ThreeState stateLinea) {
		return (
			lineaType.isActiveIn(channel) &&
			stateLinea!=ThreeState.UNKNOWN);
	}
	
	private String getInfoError(List<String> articlesNoValid) {
		if (!articlesNoValid.isEmpty()) {
			String info = "Hay Algún artículo extraño, por ejemplo:<ul>";
			for (String articuloNoValido : articlesNoValid) {
				info+="<li>" + articuloNoValido + "</li>";
			}
			info+="</ul>";
			return info;
		}
		return "";
	}	
	
	public void clickAllMenus(LineaWeb lineaWeb) {
		List<GroupType> listGroups = GroupType.getGroups(lineaWeb.getLinea());
		//listGroups=Arrays.asList(GroupType.PROMOCION); //TODO eliminar
		for (GroupType group : listGroups) {
			if (group.getGroupResponse()==GroupResponse.ARTICLES) {
				//TODO pending
			} else {
				clickAllMenus(new GroupWeb(lineaWeb.getLinea(), lineaWeb.getSublinea(), group));
			}
		}
	}
	
	public void clickAllMenus(GroupWeb groupWeb) {
		MenusWebAll menusWebAll = MenusWebAll.make(channel);
		if (groupWeb.isPresent()) {
			clickGroup(groupWeb);
			List<MenuWeb> listMenus = menusWebAll.getMenus(groupWeb);
			for (MenuWeb menuWeb : listMenus) {
				clickMenu(menuWeb);
			}
		}
	}
	
	private static final String TAG_URL_ACCESO = "@TagUrlAcceso";
	@Step (
		description="Cargar la siguiente URL de redirect a <b>España / HE / Abrigos / Parkas </b>:<br>" + TAG_URL_ACCESO,
		expected="Aparece desplegada la página de Parkas (HE)")
	public void checkURLRedirectParkasHeEspanya() throws Exception {
		URI uri = new URI(driver.getCurrentUrl());
		String tiendaId = "he";
		if (app==AppEcom.outlet) {
			tiendaId = "outletH";
		}
		String urlAccesoCorreo = 
			uri.getScheme() + "://" + 
			uri.getHost() + 
			"/redirect.faces?op=conta&seccion=prendas_he.abrigos_he&menu_abrigos106=Parkas&tiendaid=" + tiendaId;
		TestMaker.getCurrentStepInExecution().replaceInDescription(TAG_URL_ACCESO, urlAccesoCorreo);

		driver.navigate().to(urlAccesoCorreo);
		new MenuSteps().checkSelecMenu(FactoryMenus.get(ABRIGOS_SHE));
	}
	
	private static final String TAG_REF_ARTICLE = "@TagRefArticle";
	@Step (
		description=
			"Cargar la siguiente URL de redirect a la ficha del producto <b>" + TAG_REF_ARTICLE + 
			" (#{pais.getNombre_pais()})</b>:<br>" + TAG_URL_ACCESO,
		expected=
			"Aparece la ficha del producto " + TAG_REF_ARTICLE)
	public void checkURLRedirectFicha() throws Exception {
		GetterProducts getterProducts = new GetterProducts.Builder(dataTest.getPais().getCodigo_alf(), app, driver).build();
		GarmentCatalog product = getterProducts.getAll().get(0);
		Article article = Article.getArticleCandidateForTest(product);
		TestMaker.getCurrentStepInExecution().replaceInDescription(TAG_REF_ARTICLE, article.getGarmentId());
		TestMaker.getCurrentStepInExecution().replaceInExpected(TAG_REF_ARTICLE, article.getGarmentId());
		
		URI uri = new URI(driver.getCurrentUrl());
		String tiendaId = "she";
		if (app==AppEcom.outlet) {
			tiendaId = "outlet";
		}
		
		String urlAccesoCorreo = 
			uri.getScheme() + "://" + uri.getHost() + "/redirect.faces?op=conta&tiendaid=" + tiendaId + "&pais=" + dataTest.getCodigoPais() + 
			"&producto=" + article.getGarmentId() + "&color=" + article.getColor().getId() ;
		TestMaker.getCurrentStepInExecution().replaceInDescription(TAG_URL_ACCESO, urlAccesoCorreo);
		driver.navigate().to(urlAccesoCorreo);

		DataFichaArt datosArticulo = new DataFichaArt(article.getGarmentId(), "");
		PageFichaSteps pageFichaSteps = new PageFichaSteps();
		pageFichaSteps.validaDetallesProducto(datosArticulo);
	}	
}
