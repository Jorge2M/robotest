package com.mng.robotest.tests.domains.menus.steps;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.NoSuchElementException;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.ficha.steps.FichaSteps;
import com.mng.robotest.tests.domains.galeria.pageobjects.PageGaleria;
import com.mng.robotest.tests.domains.galeria.steps.GaleriaSteps;
import com.mng.robotest.tests.domains.landings.steps.LandingSteps;
import com.mng.robotest.tests.domains.menus.entity.FactoryMenus;
import com.mng.robotest.tests.domains.menus.entity.GroupResponse;
import com.mng.robotest.tests.domains.menus.entity.GroupTypeO.GroupType;
import com.mng.robotest.tests.domains.menus.pageobjects.GroupWeb;
import com.mng.robotest.tests.domains.menus.pageobjects.LineaWeb;
import com.mng.robotest.tests.domains.menus.pageobjects.MenuWeb;
import com.mng.robotest.tests.domains.menus.pageobjects.MenusWebAll;
import com.mng.robotest.tests.domains.menus.entity.Linea;
import com.mng.robotest.tests.domains.menus.entity.LineaType;
import com.mng.robotest.tests.domains.menus.entity.SublineaType;
import com.mng.robotest.tests.domains.transversal.cabecera.pageobjects.SecCabecera;
import com.mng.robotest.tests.repository.productlist.GetterProducts;
import com.mng.robotest.tests.repository.productlist.entity.GarmentCatalog.Article;
import com.mng.robotest.testslegacy.data.CodIdioma;
import com.mng.robotest.testslegacy.data.Constantes.ThreeState;
import com.mng.robotest.testslegacy.pageobject.utils.DataFichaArt;

import static com.github.jorge2m.testmaker.conf.State.*;
import static com.github.jorge2m.testmaker.conf.StoreType.*;
import static com.mng.robotest.tests.domains.menus.entity.FactoryMenus.MenuItem.ABRIGOS_HE;

public class MenuSteps extends StepBase {

	private static final List<String> MENUS_WITHOUT_ARTICLES = Arrays.asList("Essentials", "Antoine Griezmann", "Trajes", "CAPSULE", "Fiesta y eventos");
	
	@Override
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
	@Override
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
			new GaleriaSteps().checkGaleriaAfeterSelectMenu();
		} else {
			checkGroupSubMenuVisible(groupWeb);
		}
	}
	
	@Validation (
		description="Es visible la capa de los submenús del grupo #{groupWeb.getGroup()}",
		level=INFO)
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
	
	@Override
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
		String menuName = menu.getNameScreen();
		isTitleAssociatedMenu(menuName, menu.getLinea());
		if (isMenuWithArticles(menuName)) {
			new GaleriaSteps().checkGaleriaAfeterSelectMenu();
		}
		if (menu.getSubMenus()!=null && !menu.getSubMenus().isEmpty() &&
			dataTest.getIdioma().getCodigo()==CodIdioma.ES) {
			checkVisibilitySubmenus(menu);
		}
		if (isDesktop() && 
			menu.getArticles()!=null && !menu.getArticles().isEmpty() &&
			dataTest.getIdioma().getCodigo()==CodIdioma.ES) {
			checkArticlesContainsLiteralsDesktop(menu.getArticles());
		}
		
		checksDefault();
		checksGeneric()
			.googleAnalytics()
			.netTraffic().execute();
	}
	
	private boolean isMenuWithArticles(String menuName) {
		return !MENUS_WITHOUT_ARTICLES.contains(menuName);
	}
	
	@Validation
	private ChecksTM isTitleAssociatedMenu(String nameMenu, LineaType linea) {
		var checks = ChecksTM.getNew();
		boolean isTitleAccording = isTitleAssociatedToMenu(nameMenu, 1);
	 	checks.add(
	 		Check.make(
	 				"El title de la página es el asociado al menú <b>" + nameMenu + "</b>",
	 				isTitleAccording, INFO)
	 			.store(NONE).build());
	 	
	 	if (!isTitleAccording) {
	 		String nameInGaleryExpected = getNameInGaleryExpected(nameMenu, linea);
		 	checks.add(
		 	    Check.make(
				    "El título no coincide -> Validamos que exista el header <b>" + 
				    nameInGaleryExpected + "</b> en el inicio de la galería",
		 	       PageGaleria.make(channel).isHeaderArticlesVisible(nameInGaleryExpected), WARN)
		 	    .store(EVIDENCES).build());
	 	}
	 	return checks;
	}
	
	private String getNameInGaleryExpected(String nameMenu, LineaType linea) {
		if (linea==LineaType.TEEN && 
			nameMenu.toLowerCase().compareTo("trousers")==0) {
			return "Pants";
		}
		return nameMenu;
	}
	
	private void checkSelectSubMenu(MenuWeb menu) {
		new GaleriaSteps().checkGaleriaAfeterSelectMenu();
		if (isDesktop() &&
			menu.getArticlesSubMenu()!=null && !menu.getArticlesSubMenu().isEmpty()) {
			checkArticlesContainsLiteralsDesktop(menu.getArticlesSubMenu());
		}
		checksDefault();
	}
	
	@Validation (description="Son visibles los submenus <b>#{menu.getSubMenus()}</b>")
	private boolean checkVisibilitySubmenus(MenuWeb menu) {
		return menu.isVisibleSubMenus();
	}
	
	@Validation
	private ChecksTM checkArticlesContainsLiteralsDesktop(List<String> articles) {
		var pageGaleria = PageGaleria.make(channel);
		var articlesNoValid = pageGaleria.searchForArticlesNoValid(articles);
		var stateVal = (articlesNoValid.size()<10) ? WARN : DEFECT;
		
		var checks = ChecksTM.getNew();
		checks.add(
			Check.make(
				"Todos los artículos contienen alguno de los literales: <b>" + articles + "</b>",
				articlesNoValid.isEmpty(), stateVal)
			.info(getInfoError(articlesNoValid))
			.build());
	
		return checks;
	}	
	
	@Override
	public void clickLinea(LineaType lineaType) {
		clickLinea(LineaWeb.make(lineaType, dataTest.getPais(), app));
	}
	
	@Override
	public void clickLinea(LineaType lineaType, SublineaType sublineaType) {
		if (sublineaType==null) {
			clickLinea(LineaWeb.make(lineaType, dataTest.getPais(), app));
		}
		if (sublineaType!=null) {
			if (channel.isDevice()) {
				clickSublineaDevice(lineaType, sublineaType);
			} else {
				clickSublineaDesktop(lineaType, sublineaType);
			}
		}
	}
	
	private void clickSublineaDevice(LineaType lineaType, SublineaType sublineaType) {
		clickLinea(LineaWeb.make(lineaType, dataTest.getPais(), app));
		clickSublinea(LineaWeb.make(lineaType, sublineaType, dataTest.getPais(), app));
	}
	
	private void clickSublineaDesktop(LineaType lineaType, SublineaType sublineaType) {
		var linea = LineaWeb.make(lineaType, dataTest.getPais(), app);
		var sublinea = LineaWeb.make(lineaType, sublineaType, dataTest.getPais(), app);
		hoverLineaDesktop(linea);
		clickSublinea(sublinea);
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
		Linea linea = Linea.getLinea(lineaWeb.getLinea(), dataTest.getPais());
		checkContentGaleriaAfterClickLinea(linea);

		checksDefault();
		checksGeneric().imgsBroken().execute();
	}	
	
	@Step (
		description=
			"Hover sobre la <b style=\"color:chocolate\">Línea</b> " + 
			"<b style=\"color:brown;\">\"#{lineaWeb.getLinea()}</b>",
		expected=
			"Aparecen los menús asociados a la línea #{lineaWeb.getLinea()}")
	public void hoverLineaDesktop(LineaWeb lineaWeb) {
		lineaWeb.hoverLinea();
        
        //En ocasiones no aparecen los menús después de un Hover sobre la línea de niños
		if (!checkHoverLineaDesktop(lineaWeb)) {
	        LineaWeb.make(LineaType.SHE, dataTest.getPais(), app).hoverLinea();
	        lineaWeb.hoverLinea();
	        checkHoverLineaDesktop(lineaWeb);
		}
		waitMillis(100);
	}	
	
	@Validation (description="Aparecen los menús", level=INFO)
	public boolean checkHoverLineaDesktop(LineaWeb lineaWeb) {
		return MenusWebAll.make(channel, dataTest.getPais(), app).isMenuInState(true, 1);
	}	
	
	@Validation (
		description="Está seleccionada la línea <b>#{lineaWeb.getLinea()}</b>",
		level=INFO, store=NONE)
	public boolean validateIsLineaSelected(LineaWeb lineaWeb) {
		return lineaWeb.isLineaSelected(0);
	}	
		
	private void checkContentGaleriaAfterClickLinea(Linea linea) {
		switch (linea.getContentDeskType()) {
		case ARTICULOS:
			new GaleriaSteps().validaArtEnContenido(3);
			break;
		case BANNERS:
			if (!channel.isDevice()) {
				new LandingSteps().checkBannersInContent();
			}
			break;
		case VACIO:
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
		clickSublineaManagingException(lineaWeb);
	}
	
	private void clickSublineaManagingException(LineaWeb lineaWeb) {
		try {
			lineaWeb.clickSublinea();
		}
		catch (NoSuchElementException e) {
			//Try to fix random problem in selection of Teen - Nina
			if (isDesktop()) {
				lineaWeb.hoverLinea();
			} else {
				lineaWeb.clickLinea();
			}
			lineaWeb.clickSublinea();					
		}
		checkSelecSublinea(lineaWeb);
		
	}
	
	private void checkSelecSublinea(LineaWeb lineaWeb) {
		validateIsSubLineaSelected(lineaWeb);
		Linea linea = Linea.getLinea(lineaWeb.getLinea(), dataTest.getPais());
		Linea subLinea = linea.getSublineaNinos(lineaWeb.getSublinea());
		checkContentGaleriaAfterClickLinea(subLinea);

		checksDefault();
		checksGeneric().imgsBroken().execute();
	}		
	
	@Validation (
		description="Está seleccionada la sublínea #{lineaWeb.getSublinea()} / <b>#{lineaWeb.getSublinea()}</b>",
		level=INFO, store=NONE)
	public boolean validateIsSubLineaSelected(LineaWeb lineaWeb) {
		return lineaWeb.isSublineaSelected(0);
	}		
	
	@Validation
	public ChecksTM checkLineasCountry() {
		var checks = ChecksTM.getNew();
		showMenuIfDevice();
		for (var lineaType : LineaType.values()) {
			int seconds = (lineaType==LineaType.SHE) ? 2 : 0;
			checkLineaVisible(checks, lineaType, seconds);
		}
		unshowMenuIfDevice();
		return checks;
	}

	private void checkLineaVisible(ChecksTM checks, LineaType lineaType, int seconds) {
		var apareceLinea = dataTest.getPais().getShoponline().stateLinea(lineaType, app);
		if (checkLinea(lineaType, apareceLinea)) {
			boolean isLineaPresent = LineaWeb.make(lineaType, dataTest.getPais(), app).isLineaPresent(seconds);
			if (apareceLinea==ThreeState.TRUE) {
				checks.add (
					"<b>Sí</b> aparece el link de la línea <b>" + lineaType + "</b>",
					isLineaPresent);
			} else {
				checks.add (
					"<b>No</b> aparece el link de la línea <b>" + lineaType + "</b>",
					!isLineaPresent, WARN);
			}
		}
	}
	
	private void showMenuIfDevice() {
		if (channel.isDevice()) {
			SecCabecera.make().clickIconoMenuHamburguerMobil(true);
		}
	}
	private void unshowMenuIfDevice() {
		if (channel.isDevice()) {
			SecCabecera.make().clickIconoMenuHamburguerMobil(true);
		}
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
	
	public void clickAllMenus(LineaWeb lineaWeb, GroupType group) {
		clickAllMenus(GroupWeb.make(lineaWeb.getLinea(), lineaWeb.getSublinea(), group, dataTest.getPais(), app));
	}
	
	public void clickAllMenus(LineaWeb lineaWeb) {
		var listGroups = GroupType.getGroups(lineaWeb.getLinea());
		for (var group : listGroups) {
			if (group.getGroupResponse()==GroupResponse.ARTICLES) {
				//TODO pending
			} else {
				clickAllMenus(GroupWeb.make(lineaWeb.getLinea(), lineaWeb.getSublinea(), group, dataTest.getPais(), app));
			}
		}
	}
	
	public void clickAllMenus(GroupWeb groupWeb) {
		var menusWebAll = MenusWebAll.make(channel, dataTest.getPais(), app);
		if (groupWeb.isPresent()) {
			clickGroup(groupWeb);
			var listMenus = menusWebAll.getMenus(groupWeb);
			for (var menuWeb : listMenus) {
				clickMenu(menuWeb);
			}
		}
	}
	
	public void checkURLRedirectParkasHeEspanya() throws Exception {
		if (isOutlet()) {
			checkURLRedirectParkasHeEspanyaOutlet();
		} else {
			checkURLRedirectParkasHeEspanyaShop();
		}
		
	}
	
	private static final String TAG_URL_ACCESO = "@TagUrlAcceso";
	@Step (
		description="Cargar la siguiente URL de redirect a <b>España / HE / Abrigos / Parkas </b>:<br>" + TAG_URL_ACCESO,
		expected="Aparece desplegada la página de Parkas (HE)")
	private void checkURLRedirectParkasHeEspanyaShop() throws Exception {
		URI uri = new URI(getCurrentUrl());
		String urlAccesoCorreo = 
			uri.getScheme() + "://" + 
			uri.getHost() + 
			"/redirect.faces?op=conta&seccion=prendas_he.abrigos_he&menu_abrigos106=Parkas&tiendaid=he";
		replaceStepDescription(TAG_URL_ACCESO, urlAccesoCorreo);

		driver.navigate().to(urlAccesoCorreo);
		new MenuSteps().checkSelecMenu(FactoryMenus.get(ABRIGOS_HE));
	}

	@Step (
		description="Cargar la siguiente URL de redirect a <b>España / HE / Abrigos</b> :<br>" + TAG_URL_ACCESO,
		expected="Aparece desplegada la página de Abrigos (HE)")
	private void checkURLRedirectParkasHeEspanyaOutlet() throws Exception {
		URI uri = new URI(getCurrentUrl());
		String urlAccesoCorreo = 
			uri.getScheme() + "://" + 
			uri.getHost() + 
			"/redirect.faces?op=conta&seccion=prendas_he.abrigos_he&tiendaid=outletH";
		replaceStepDescription(TAG_URL_ACCESO, urlAccesoCorreo);

		driver.navigate().to(urlAccesoCorreo);
		new MenuSteps().checkSelecMenu(FactoryMenus.get(ABRIGOS_HE));
	}
	
	private static final String TAG_REF_ARTICLE = "@TagRefArticle";
	@Step (
		description=
			"Cargar la siguiente URL de redirect a la ficha del producto <b>" + 
			TAG_REF_ARTICLE + "</b>:<br>" + TAG_URL_ACCESO,
		expected=
			"Aparece la ficha del producto " + TAG_REF_ARTICLE)
	public void checkURLRedirectFicha() throws Exception {
		var getterProducts = new GetterProducts.Builder(dataTest.getPais().getCodigoAlf(), app, driver).build();
		var product = getterProducts.getAll().get(0);
		var article = Article.getArticleForTest(product);
		replaceStepDescription(TAG_REF_ARTICLE, article.getGarmentId());
		replaceStepExpected(TAG_REF_ARTICLE, article.getGarmentId());
		
		URI uri = new URI(getCurrentUrl());
		String tiendaId = "she";
		if (isOutlet()) {
			tiendaId = "outlet";
		}
		
		String urlAccesoCorreo = 
			uri.getScheme() + "://" + uri.getHost() + "/redirect.faces?op=conta&tiendaid=" + tiendaId + "&pais=" + dataTest.getCodigoPais() + 
			"&producto=" + article.getGarmentId() + "&color=" + article.getColor().getId() ;
		replaceStepDescription(TAG_URL_ACCESO, urlAccesoCorreo);
		driver.navigate().to(urlAccesoCorreo);

		var datosArticulo = new DataFichaArt(article.getGarmentId(), "");
		new FichaSteps().checkDetallesProducto(datosArticulo);
	}	
	
}
