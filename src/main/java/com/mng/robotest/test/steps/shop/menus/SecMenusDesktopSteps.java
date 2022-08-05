package com.mng.robotest.test.steps.shop.menus;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.conf.StoreType;
import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.ficha.steps.PageFichaArtSteps;
import com.mng.robotest.test.beans.Linea;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.beans.Sublinea.SublineaType;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.exceptions.NotFoundException;
import com.mng.robotest.test.generic.stackTrace;
import com.mng.robotest.test.getdata.products.GetterProducts;
import com.mng.robotest.test.getdata.products.data.GarmentCatalog;
import com.mng.robotest.test.getdata.products.data.GarmentCatalog.Article;
import com.mng.robotest.test.pageobject.shop.AllPages;
import com.mng.robotest.test.pageobject.shop.bannersNew.ManagerBannersScreen;
import com.mng.robotest.test.pageobject.shop.filtros.FilterCollection;
import com.mng.robotest.test.pageobject.shop.galeria.LabelArticle;
import com.mng.robotest.test.pageobject.shop.galeria.ListSizesArticle;
import com.mng.robotest.test.pageobject.shop.galeria.PageGaleria;
import com.mng.robotest.test.pageobject.shop.galeria.PageGaleriaDesktop;
import com.mng.robotest.test.pageobject.shop.galeria.PageGaleriaDesktop.ControlTemporada;
import com.mng.robotest.test.pageobject.shop.landing.PageLanding;
import com.mng.robotest.test.pageobject.shop.menus.GroupMenu;
import com.mng.robotest.test.pageobject.shop.menus.KeyMenu1rstLevel;
import com.mng.robotest.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test.pageobject.shop.menus.Menu2onLevel;
import com.mng.robotest.test.pageobject.shop.menus.MenuLateralDesktop;
import com.mng.robotest.test.pageobject.shop.menus.MenuTreeApp;
import com.mng.robotest.test.pageobject.shop.menus.SecMenusWrap;
import com.mng.robotest.test.pageobject.shop.menus.MenuLateralDesktop.Element;
import com.mng.robotest.test.pageobject.shop.menus.desktop.SecMenusDesktop;
import com.mng.robotest.test.pageobject.shop.modales.ModalCambioPais;
import com.mng.robotest.test.pageobject.utils.DataFichaArt;
import com.mng.robotest.test.steps.shop.SecCabeceraSteps;
import com.mng.robotest.test.steps.shop.banner.SecBannersSteps;
import com.mng.robotest.test.steps.shop.galeria.PageGaleriaSteps;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks.GenericCheck;
import com.mng.robotest.test.utils.WebDriverMngUtils;

@SuppressWarnings({"static-access"})
public class SecMenusDesktopSteps {

	private static final String PREFIX_SALE = "<b style=\"color:blue\">Rebajas</b></br>";
	
	private final WebDriver driver = TestMaker.getDriverTestCase();
	private final Pais pais;
	private final AppEcom app;
	private final SecMenusDesktop secMenus;
	
	public SecMenusDesktopSteps(Pais pais, AppEcom app, Channel channel) {
		this.pais = pais;
		this.app = app;
		this.secMenus = new SecMenusDesktop(app, channel);
	}
	
	/**
	 * Selección de un menú superior (lateral en el caso de móvil) con un catálogo de artículos asociado (p.e. vestidos, camisas, etc.)
	 */
	@Step(
		description="Seleccionar el menú superior <b>#{menu1rstLevel}</b>", 
		expected="Aparece la galería asociada al menú",
		saveNettraffic=SaveWhen.Always)
	public void selectMenuSuperiorTypeCatalog(Menu1rstLevel menu1rstLevel, DataCtxShop dCtxSh) throws Exception {
		secMenus.secMenuSuperior.secBlockMenus.gotoAndClickMenu(menu1rstLevel);
		validaSelecMenu(menu1rstLevel, dCtxSh);
	}
	@Validation(
		description="No existe el menú superior <b>#{menu1rstLevel}</b>",
		level=State.Defect)
	public boolean checkNotExistsMenuSuperiorTypeCatalog(Menu1rstLevel menu1rstLevel) throws Exception {
		return (!secMenus.secMenuSuperior.secBlockMenus.goToMenuAndCheckIsVisible(menu1rstLevel));
	}
	
	@Step (
		description="Seleccionar el menú lateral de 1er nivel <b>#{menu1rstLevel}</b>", 
		expected="Aparecen artículos de tipo Camiseta",
		saveNettraffic=SaveWhen.Always)
	public void selectMenuLateral1rstLevelTypeCatalog(Menu1rstLevel menu1rstLevel, DataCtxShop dCtxSh) throws Exception {
		secMenus.secMenuLateral.clickMenu(menu1rstLevel);
		validaSelecMenu(menu1rstLevel, dCtxSh);
	}
	
	public void selectMenu2oLevel(Menu2onLevel menu2onLevel, DataCtxShop dCtxSh) throws Exception {
		if (app==AppEcom.outlet) {
			selectMenuLateral2oLevel(menu2onLevel);
		} else {
			selectMenuSubfamilia(menu2onLevel, dCtxSh);
		}
		validaSelecMenu(menu2onLevel, dCtxSh);
	}
	
	@Step (
		description="Seleccionar la subfamília <b>#{menu2onLevel}</b>", 
		expected="Aparecen artículos asociados al menú",
		saveNettraffic=SaveWhen.Always)
	private void selectMenuSubfamilia(Menu2onLevel menu2onLevel, DataCtxShop dCtxSh) {
		PageGaleria pageGaleria = PageGaleriaDesktop.getNew(dCtxSh.channel, app, driver);
		((PageGaleriaDesktop)pageGaleria).secSubmenusGallery.clickSubmenu(menu2onLevel.getNombre(), driver);
	}
	
	@Step (
		description="Seleccionar el menú lateral de 2o nivel <b>#{menu2onLevel}</b>", 
		expected="Aparecen artículos asociados al menú",
		saveNettraffic=SaveWhen.Always)
	private void selectMenuLateral2oLevel(Menu2onLevel menu2onLevel) {
		secMenus.secMenuLateral.clickMenu(menu2onLevel);
	}
	
	/**
	 * Validación de la selección de un menú lateral de 1er o 2o nivel 
	 */
	public void validaSelecMenu(MenuLateralDesktop menu, DataCtxShop dCtxSh) throws Exception {
		PageGaleriaSteps pageGaleriaSteps = PageGaleriaSteps.getInstance(dCtxSh.channel, dCtxSh.appE, driver);
		pageGaleriaSteps.validateGaleriaAfeterSelectMenu(dCtxSh.appE);
		checksSelecMenuEspecificDesktop(menu);
		GenericChecks.from(Arrays.asList(
				GenericCheck.CookiesAllowed,
				GenericCheck.SEO,
				GenericCheck.JSerrors,
				GenericCheck.Analitica,
				GenericCheck.TextsTraduced,
				GenericCheck.GoogleAnalytics,
				GenericCheck.NetTraffic)).checks(driver);
	}
	
	@Validation (
		description="Está seleccionada la línea <b>#{lineaType}</b>",
		level=State.Info,
		store=StoreType.None)
	public boolean validateIsLineaSelected(LineaType lineaType) {
		return (secMenus.secMenuSuperior.secLineas.isLineaSelected(lineaType));
	}
	
	/**
	 * Validaciones de selección de un menú de 1er nivel (superior o lateral) (las específicas de Desktop)
	 */
	public void checksSelecMenuEspecificDesktop(MenuLateralDesktop menu) throws Exception {
		PageGaleriaSteps pageGaleriaSteps = PageGaleriaSteps.getInstance(Channel.desktop, app, driver);
		pageGaleriaSteps.bannerHead.validateBannerSuperiorIfExistsDesktop();
		if (menu.isMenuLateral()) {
			checkIsSelectedLateralMenu(menu, 2);
		}
		
		if (menu instanceof Menu1rstLevel) {
			Menu1rstLevel menu1rstLevel = (Menu1rstLevel) menu;
			List<Menu2onLevel> menus2onLevel = menu1rstLevel.getListMenus2onLevel();
			if (menus2onLevel!=null && menus2onLevel.size()>0) {
				if (app==AppEcom.outlet) {
					checkVisibility2onLevelMenusOulet(menus2onLevel);
				} else {
					pageGaleriaSteps.checkVisibilitySubmenus(menus2onLevel);
				}
			}
		}
		
		if (menu.isDataForValidateArticleNames()) {
			String[] textsArticlesGalery = menu.getTextsArticlesGalery();
			checkArticlesContainsLiterals(textsArticlesGalery);
		}
		
		pageGaleriaSteps.getSecSelectorPreciosSteps().validaIsSelector();
		SecMenusWrap secMenus = new SecMenusWrap(Channel.desktop, app);
		LineaType lineaResult = secMenus.getLineaResultAfterClickMenu(menu.getLinea(), menu.getNombre());
		validateIsLineaSelected(lineaResult);	
	}
	
	@Validation (
		description="Aparece seleccionado el menú lateral <b>#{menu.getNombre()}</b> (lo esperamos hasta #{maxSeconds} segundos)",
		level=State.Warn)
	private boolean checkIsSelectedLateralMenu(MenuLateralDesktop menu, int maxSeconds) {
		if (app!=AppEcom.outlet && menu instanceof Menu2onLevel) {
			PageGaleria pageGaleria = PageGaleriaDesktop.getNew(Channel.desktop, app, driver);
			return ((PageGaleriaDesktop)pageGaleria).secSubmenusGallery.isMenuSelected(menu.getNombre(), driver);
		}
		return (secMenus.secMenuLateral.isSelectedMenu(menu, maxSeconds));
	}
	  
	@Validation
	private ChecksTM checkVisibility2onLevelMenusOulet(List<Menu2onLevel> menus2onLevel) throws Exception {
		ChecksTM checks = ChecksTM.getNew();
		for (Menu2onLevel menu2oNivelTmp : menus2onLevel) {
			checks.add(
				"Aparece el menú de 2o nivel <b>" + menu2oNivelTmp.getNombre() + "</b>",
				secMenus.secMenuLateral.isVisibleMenu(menu2oNivelTmp), State.Warn);
		}
		return checks;
	}
	
	@Validation
	private ChecksTM checkArticlesContainsLiterals(String[] textsArticlesGalery) throws Exception {
		ChecksTM checks = ChecksTM.getNew();
		
		String litsToContain = "";
		for (int i=0; i<textsArticlesGalery.length; i++) {
			litsToContain+= "<br>" + textsArticlesGalery[i];
		}
		PageGaleriaDesktop pageGaleriaDesktop = (PageGaleriaDesktop)PageGaleria.getNew(Channel.desktop, app, driver);
		ArrayList<String> listTxtArtNoValidos = pageGaleriaDesktop.nombreArticuloNoValido(textsArticlesGalery);
		String articlesWrongWarning = "";
		if (listTxtArtNoValidos.size() > 0) {
			articlesWrongWarning+="<br>" + "<b>Warning!</b> Hay Algún artículo extraño, p.e.:";
			for (String txtArtNoValido : listTxtArtNoValidos) {
				articlesWrongWarning+=("<br>" + txtArtNoValido);
			}
		}
		State stateVal = State.Defect;
		if (listTxtArtNoValidos.size()<5) {
			stateVal = State.Warn;
		}
	 	checks.add(
			"Todos los artículos contienen alguno de los literales: " + litsToContain + articlesWrongWarning,
			listTxtArtNoValidos.size()==0, stateVal);
	
		return checks;
	}
	
	/**
	 * Función que ejecuta el paso/validaciones correspondiente a la selección de una entrada el menú superior de Desktop
	 */
	static final String tagMenu = "@TagMenu";
	@Step (
		description="Selección del menú <b>" + tagMenu + "</b> (data-testid=#{menu1rstLevel.getDataTestIdMenuSuperiorDesktop()})", 
		expected="El menú se ejecuta correctamente",
		saveNettraffic=SaveWhen.Always)
	public void stepEntradaMenuDesktop(Menu1rstLevel menu1rstLevel, String paginaLinea) throws Exception {
		//Si en la pantalla no existen los menús volvemos a la página inicial de la línea
		LineaType lineaMenu = menu1rstLevel.getLinea();
		if (!secMenus.secMenuSuperior.secLineas.isLineaVisible(lineaMenu)) {
			driver.get(paginaLinea);
		}
		secMenus.secMenuSuperior.secBlockMenus.gotoAndClickMenu(menu1rstLevel);
		TestMaker.getCurrentStepInExecution().replaceInDescription(tagMenu, menu1rstLevel.getNombre());
		ModalCambioPais.closeModalIfVisible(driver);
		
		validaPaginaResultMenu(menu1rstLevel);
		
		SecMenusWrap secMenus = new SecMenusWrap(Channel.desktop, app);
		LineaType lineaResult = secMenus.getLineaResultAfterClickMenu(lineaMenu, menu1rstLevel.getNombre());
		validateIsLineaSelected(lineaResult);
	}
	
	@Step (
		description=
			"Seleccionar la <b style=\"color:chocolate\">Línea</b> " + 
			"<b style=\"color:brown;\">\"#{lineaType.getNameUpper()}</b>",
		expected=
			"Aparece la página correcta asociada a la línea #{lineaType.getNameUpper()}")
	public void seleccionLinea(LineaType lineaType) throws Exception {
		secMenus.secMenuSuperior.secLineas.selecLinea(pais, lineaType);	   
		validaSelecLinea(lineaType, null);
	}
	
	@Step (
		description=
			"Seleccionar la línea / <b style=\"color:chocolate\">Sublínea</b> " + 
			"<b style=\"color:brown;\">\"#{lineaType.name()} / #{sublineaType.getNameUpper()}</b>",
		expected=
			"Aparece la página correcta asociada a la línea/sublínea")
	public void seleccionSublinea(LineaType lineaType, SublineaType sublineaType) throws Exception {
		validaSelecLinea(lineaType, sublineaType);
	}	
	
	public void validaSelecLinea(LineaType lineaType, SublineaType sublineaType) throws Exception {
		SecCabeceraSteps secCabeceraSteps = new SecCabeceraSteps(pais, Channel.desktop, app);
		if (sublineaType==null) {
			validateIsLineaSelected(lineaType);
		}

		secCabeceraSteps.validateIconoBolsa();

		//Validaciones en función del tipo de página que debería aparecer al seleccionar la línea
		Linea linea = pais.getShoponline().getLinea(lineaType);
		if (sublineaType!=null) {
			linea = linea.getSublineaNinos(sublineaType);
		}
			
		switch (linea.getContentDeskType()) {
		case articulos:
			PageGaleriaSteps pageGaleriaSteps = PageGaleriaSteps.getInstance(Channel.desktop, app, driver);
			int maxSeconds = 3;
			pageGaleriaSteps.validaArtEnContenido(maxSeconds);
			break;
		case banners:
			int maxBannersToLoad = 1;
			SecBannersSteps secBannersSteps = new SecBannersSteps(maxBannersToLoad, driver);
			secBannersSteps.validaBannEnContenido();
			break;
		case vacio:
			break;
		default:
			break;
		}

		GenericChecks.from(Arrays.asList(
				GenericCheck.CookiesAllowed,
				GenericCheck.SEO, 
				GenericCheck.JSerrors, 
				GenericCheck.Analitica,
				GenericCheck.TextsTraduced,
				GenericCheck.ImgsBroken)).checks(driver);
	}

	@Step (
		description="Contamos el número de pestañas y menús de #{lineaType} / #{sublineaType}",
		expected="El número de pestañas/menús coincide con el del nodo anterior")
	public void countSaveMenusEntorno(LineaType lineaType, SublineaType sublineaType, String inodo, String urlBase) 
	throws Exception {
		int numPestanyas = secMenus.secMenuSuperior.secLineas.getListaLineas().size();
		int numMenus = secMenus.secMenuSuperior.secBlockMenus.getListDataScreenMenus(lineaType, sublineaType).size();
		checkNumPestanyasYmenusEqualsInBothNodes(numPestanyas, numMenus, lineaType, sublineaType, inodo, urlBase);
	}

	@Validation
	private ChecksTM checkNumPestanyasYmenusEqualsInBothNodes(
			int numPestanyas, int numMenus, LineaType lineaType, SublineaType sublineaType, String inodo, String urlBase) {
		
		ChecksTM checks = ChecksTM.getNew();
		String clave = lineaType.name();
		if (sublineaType!=null) {
			clave+=sublineaType.name();
		}
		clave+=urlBase;	
		
		//Si están registrados en el contexto el número de pestañas y menús...
		TestCaseTM testCase = getTestCase();
		ITestContext ctx = testCase.getTestRunParent().getTestNgContext();
		if (ctx.getAttribute("numPestanyas" + clave) != null && 
			ctx.getAttribute("numMenus" + clave) != null) {
			
			//Obtenemos el número de pestañas y menús almacenados en el contexto
			int numPestanyas_C = ((Integer)ctx.getAttribute("numPestanyas" + clave)).intValue();
			int numMenus_C = ((Integer)ctx.getAttribute("numMenus" + clave)).intValue();
			
		  	checks.add(
				"El número de pestañas (" + numPestanyas + ") coincide con el del nodo " + ctx.getAttribute("NodoMenus" + clave) + " (" + numPestanyas_C + ")",
				(numPestanyas==numPestanyas_C), State.Warn);
		  	checks.add(
				"El número de menús (" + numMenus + ") coincide con el del nodo " + ctx.getAttribute("NodoMenus" + clave) + " (" + numMenus_C + ")",
				(numMenus==numMenus_C), State.Warn);
		}

		//Almacenamos los nuevos datos en el contexto
		ctx.setAttribute("numPestanyas" + clave, Integer.valueOf(numPestanyas));
		ctx.setAttribute("numMenus" + clave, Integer.valueOf(numMenus));
		ctx.setAttribute("NodoMenus" + clave, inodo);
		
		return checks;
	}
	
	@Step (
		description="Seleccionar el banner existente a la derecha de los menús", 
		expected="Aparece una página con banners o artículos")
	public void clickRightBanner(LineaType lineaType, SublineaType sublineaType) throws Exception {
		secMenus.secMenuSuperior.secBlockMenus.clickRightBanner(lineaType, sublineaType);
		checkAreValidMangoObjectsInPage();
	}
	
	@Validation (
		description="Aparece una página con banners, artículos, iframes, maps o sliders",
		level=State.Warn)
	private boolean checkAreValidMangoObjectsInPage() throws Exception {
		PageGaleria pageGaleria = PageGaleria.getNew(Channel.desktop, app, driver);
		PageLanding pageLanding = new PageLanding(driver);
		if (!pageGaleria.isVisibleArticleUntil(1, 3) &&
			!pageLanding.hayIframes() &&
			!pageLanding.hayMaps() &&
			!pageLanding.haySliders()) {
			int maxBannersToLoad = 1;
			ManagerBannersScreen managerBanners = new ManagerBannersScreen(maxBannersToLoad, driver);
			return (managerBanners.existBanners());
		}
		return true;
	}
	
	static final String tagUrlAcceso = "@TagUrlAcceso";
	@Step (
		description="Cargar la siguiente URL de redirect a <b>España / HE / Abrigos / Parkas </b>:<br>" + tagUrlAcceso,
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
		TestMaker.getCurrentStepInExecution().replaceInDescription(tagUrlAcceso, urlAccesoCorreo);

		driver.navigate().to(urlAccesoCorreo);
		Menu1rstLevel menuAbrigos = MenuTreeApp.getMenuLevel1From(app, KeyMenu1rstLevel.from(LineaType.he, null, "abrigos"));
//		MenuLateralDesktop submenuParkas = MenuTreeApp.getMenuLevel2From(menuAbrigos, "Parkas");
//		if (app==AppEcom.outlet) {
//			checksSelecMenuEspecificDesktop(submenuParkas);
//		} else {
			checksSelecMenuEspecificDesktop(menuAbrigos);
//		}
	}
	
	static final String tagRefArticle = "@TagRefArticle";
	@Step (
		description=
			"Cargar la siguiente URL de redirect a la ficha del producto <b>" + tagRefArticle + 
			" (#{pais.getNombre_pais()})</b>:<br>" + tagUrlAcceso,
		expected=
			"Aparece la ficha del producto " + tagRefArticle)
	public static void checkURLRedirectFicha(Pais pais, DataCtxShop dCtxSh, WebDriver driver) throws Exception {
		GetterProducts getterProducts = new GetterProducts.Builder(dCtxSh.pais.getCodigo_alf(), dCtxSh.appE, driver).build();
		GarmentCatalog product = getterProducts.getAll().get(0);
		Article article = product.getArticleWithMoreStock();
		TestMaker.getCurrentStepInExecution().replaceInDescription(tagRefArticle, article.getGarmentId());
		TestMaker.getCurrentStepInExecution().replaceInExpected(tagRefArticle, article.getGarmentId());
		
		URI uri = new URI(driver.getCurrentUrl());
		String tiendaId = "she";
		if (dCtxSh.appE==AppEcom.outlet) {
			tiendaId = "outlet";
		}
		
		String urlAccesoCorreo = 
			uri.getScheme() + "://" + uri.getHost() + "/redirect.faces?op=conta&tiendaid=" + tiendaId + "&pais=" + pais.getCodigo_pais() + 
			"&producto=" + article.getGarmentId() + "&color=" + article.getColor().getId() ;
		TestMaker.getCurrentStepInExecution().replaceInDescription(tagUrlAcceso, urlAccesoCorreo);
		driver.navigate().to(urlAccesoCorreo);

		DataFichaArt datosArticulo = new DataFichaArt(article.getGarmentId(), "");
		PageFichaArtSteps pageFichaSteps = new PageFichaArtSteps(dCtxSh.appE, dCtxSh.channel, dCtxSh.pais);
		pageFichaSteps.validaDetallesProducto(datosArticulo);
	}
	
	public void validaPaginaResultMenu(MenuLateralDesktop menu) throws Exception {
		checkResultDependingMenuGroup(menu);
		checkErrorPageWithoutException();
		GroupMenu groupMenu = menu.getGroup(Channel.desktop);
		if (groupMenu.canContainElement(Element.article)) {
			if (pais.isEspanya()) {
				checkSizeDivImages();
			}
			Menu1rstLevel menuPromocion = MenuTreeApp.getMenuLevel1From(app, KeyMenu1rstLevel.from(menu.getLinea(), menu.getSublinea(), "promocion"));
			menuPromocion.setDataGaLabel("promocion");
			checksRebajas();
		}
		
		GenericChecks.from(Arrays.asList(
				GenericCheck.CookiesAllowed,
				GenericCheck.SEO, 
				GenericCheck.JSerrors, 
				GenericCheck.Analitica,
				GenericCheck.TextsTraduced,
				GenericCheck.ImgsBroken)).checks(driver);
	}

	@Validation
	private ChecksTM checkSizeDivImages() throws Exception {
		ChecksTM checks = ChecksTM.getNew();
		PageGaleriaDesktop pageGaleriaDesktop = (PageGaleriaDesktop)PageGaleria.getNew(Channel.desktop, app, driver);
		int numPage = 1; 
		double marginPercError = 2;
		ListSizesArticle listArtWrong1rstPage = pageGaleriaDesktop.getArticlesWithWrongSize(numPage, marginPercError);
		checks.add(
			"Los div de los artículos de la " + numPage + "a página tienen un tamaño acorde al especificado en el atributo width de su imagen " + 
			"(margen del " + marginPercError + "%)" +
			getLiteralWarningArticlesSizesWrong(listArtWrong1rstPage),
			listArtWrong1rstPage.size()==0, State.Defect);
	 	
	 	return checks;
	}

	private String getLiteralWarningArticlesSizesWrong(ListSizesArticle listArtWrong) {
	  	String warningMessage = "";
		if (listArtWrong.size() > 0) {
			warningMessage+=(
				"<br><lin style=\"color:" + State.Warn.getColorCss() + ";\"><b>Warning!</b>: " + 
				"hay " + listArtWrong.size() + " artículos con tamaño incorrecto:<br>" +
				listArtWrong.getListHtml() +
				"</lin>");
		}
		
		return warningMessage;
	}
	
	@Validation
	private ChecksTM checkResultDependingMenuGroup(MenuLateralDesktop menu) throws Exception {
		ChecksTM checks = ChecksTM.getNew();
		GroupMenu groupMenu = menu.getGroup(Channel.desktop);
		List<Element> elemsCanBeContained = groupMenu.getElementsCanBeContained();
		boolean contentPageOk = (new PageLanding(driver)).isSomeElementVisibleInPage(elemsCanBeContained, app, Channel.desktop, 2);
	 	checks.add(
			"Aparecen alguno de los siguientes elementos: <b>" + elemsCanBeContained + "</b> (es un menú perteneciente al grupo <b>" + groupMenu + ")</b>",
			contentPageOk, State.Warn);
		
		PageGaleria pageGaleria = PageGaleria.getNew(Channel.desktop, app, driver);
		if (groupMenu.canContainElement(Element.article)) {
		 	String guiones = "--";
		 	checks.add(
				"No hay artículos con \"" + guiones + "\"",
				!((PageGaleriaDesktop)pageGaleria).isArticuloWithStringInName(guiones), State.Warn);
		}
		
		boolean isTitleAccording = AllPages.isTitleAssociatedToMenu(menu.getNombre(), driver);
	 	checks.add(
			"El title de la página es el asociado al menú <b>" + menu.getNombre() + "</b>",
			isTitleAccording, State.Info);
	 	if (!isTitleAccording) {
		 	checks.add(
		 	    Check.make(
				    "El título no coincide -> Validamos que exista el header <b>" + 
		 	        menu.getNombre() + "</b> en el inicio de la galería",
				    pageGaleria.isHeaderArticlesVisible(menu.getNombre()), State.Warn)
		 	    .store(StoreType.Evidences).build());
	 	}
		
	 	return checks;
	}	

	@Validation
	public ChecksTM checkErrorPageWithoutException() throws Exception {
		ChecksTM checks = ChecksTM.getNew();
		TestCaseTM testCase = getTestCase();
		ITestContext ctx = testCase.getTestRunParent().getTestNgContext();
		stackTrace exception = WebDriverMngUtils.stackTaceException(driver, ctx);
		String excepcionDuplicada = "";
		if (exception.getRepetida()) {
			excepcionDuplicada+=
				"<br><b>Warning!</b>Se ha detectado una excepción detectada previamente (" + exception.getNumExcepciones() + ")";
		}
	 	checks.add(
			"El errorPage.faces no devuelve una excepción" + excepcionDuplicada,
			!exception.getExiste(), State.Warn);
	 	return checks;
	}
	
	private TestCaseTM getTestCase() throws NotFoundException {
		Optional<TestCaseTM> testCaseOpt = TestMaker.getTestCase();
		if (testCaseOpt.isEmpty()) {
		  throw new NotFoundException("Not found TestCase");
		}
		return testCaseOpt.get();
	}
	
	//Temporal para prueba fin rebajas en China
	@Validation
	public ChecksTM checksSpecificEndRebajasChina() throws Exception {
		ChecksTM checks = ChecksTM.getNew();
		PageGaleriaDesktop pageGaleriaDesktop = (PageGaleriaDesktop)PageGaleria.getNew(Channel.desktop, app, driver);
	  	List<Integer> tempSale = FilterCollection.sale.getListTempArticles();
	  	List<String> listArtWrong = pageGaleriaDesktop.getArticlesTemporadasX(ControlTemporada.articlesFrom, tempSale);
	  	String warningMessage = "";
		if (listArtWrong.size() > 0) {
			warningMessage+=
				"<br><lin style=\"color:" + State.Warn.getColorCss() + ";\"><b>Warning!</b>: " + 
				"hay " + listArtWrong.size() + " artículos de T2 ó T3:<br>";
			for (String nameWrong : listArtWrong) {
				warningMessage+=(nameWrong + "<br>");
			}
			warningMessage+="</lin>";
		}
		
		checks.add(
			PREFIX_SALE +	 
			"No hay artículos con las siguientes características:<br>" + 
			" * De temporadas T2 y T3 " + tempSale + warningMessage,
			listArtWrong.size()==0, State.Defect);
	  	return checks;
	}
	
	public void checksRebajas() throws Exception {
		checkNoArticlesRebajadosWithLabelIncorrect();
		checkNoArticlesTemporadaOldWithLabelsWrong();
		checkNoArticlesTemporadaNewWithLabelsWrong();
	}
	
	@Validation
	private ChecksTM checkNoArticlesRebajadosWithLabelIncorrect() throws Exception {
		ChecksTM checks = ChecksTM.getNew();
		PageGaleriaDesktop pageGaleriaDesktop = (PageGaleriaDesktop)PageGaleria.getNew(Channel.desktop, app, driver);
		List<LabelArticle> listLabelsWrong = PageGaleria.listLabelsNew;
		List<Integer> tempSales = FilterCollection.sale.getListTempArticles();
		List<String> listArtWrong = pageGaleriaDesktop.getArticlesTemporadaxRebajadosWithLiteralInLabel(tempSales, listLabelsWrong);
		String warningMessage = "";
		if (listArtWrong.size() > 0) {
			warningMessage+=
				"<br><lin style=\"color:" + State.Warn.getColorCss() + ";\"><b>Warning!</b>: " + 
				"hay " + listArtWrong.size() + " artículos rebajados con label errónea:<br>";
			for (String nameWrong : listArtWrong) {
				warningMessage+=(nameWrong + "<br>");
			}
			warningMessage+="</lin>";
		}
		
		checks.add(
			PREFIX_SALE +				   
			"No hay artículos con las siguientes características:<br>" + 
			" * Rebajados</b><br>" +
			" * De temporadas anteriores " + tempSales + "<br>" +
			" * Con alguna de las etiquetas <b>" + listLabelsWrong + "</b> (en sus correspondientes traducciones)" + warningMessage,
			listArtWrong.size()==0, State.Warn);
		return checks;
	}
	
	@Validation
	private ChecksTM checkNoArticlesTemporadaOldWithLabelsWrong() throws Exception {
		ChecksTM checks = ChecksTM.getNew();
		
		Integer temporadaOldOld = FilterCollection.sale.getListTempArticles().get(0);
		ArrayList<Integer> temporadaOldOldList = new ArrayList<Integer>(Arrays.asList(temporadaOldOld));  
	   	List<LabelArticle> listLabelsWrong = PageGaleria.listLabelsNew;
		PageGaleriaDesktop pageGaleriaDesktop = (PageGaleriaDesktop)PageGaleria.getNew(Channel.desktop, app, driver);
	   	List<String> listArtWrong = pageGaleriaDesktop.getArticlesTemporadaXWithLiteralInLabel(temporadaOldOldList, listLabelsWrong);
		String warningMessage = "";
		if (listArtWrong.size() > 0) {
			warningMessage+=
				"<br><lin style=\"color:" + State.Warn.getColorCss() + ";\"><b>Warning!</b>: " + 
				"hay " + listArtWrong.size() + " artículos de temporada " + temporadaOldOldList + " con label errónea:<br>";
			for (String nameWrong : listArtWrong) {
				warningMessage+=(nameWrong + "<br>");
			}
			warningMessage+="</lin>";
		}
		
		checks.add(
			PREFIX_SALE +				   
			"No hay artículos <b>de Temporada " + temporadaOldOldList + "</b> con alguna de las etiquetas <b>" + listLabelsWrong + "</b> " + 
			"(en sus correspondientes traducciones)" + warningMessage,
			listArtWrong.size()==0, State.Warn);
		return checks;
	}
		
	@Validation
	private ChecksTM checkNoArticlesTemporadaNewWithLabelsWrong() throws Exception {
		ChecksTM checks = ChecksTM.getNew();
		
		List<Integer> temporadaNew = FilterCollection.nextSeason.getListTempArticles();
		PageGaleriaDesktop pageGaleriaDesktop = (PageGaleriaDesktop)PageGaleria.getNew(Channel.desktop, app, driver);
		List<String> listArtWrong = pageGaleriaDesktop.getArticlesTemporadaXWithLiteralInLabel(temporadaNew, LabelArticle.NewNow, LabelArticle.NewCollection);
		String warningMessage = "";
		if (listArtWrong.size() > 0) {
			warningMessage+=
				"<br><lin style=\"color:" + State.Warn.getColorCss() + ";\"><b>Warning!</b>: " + 
				"hay " + listArtWrong.size() + " artículos de temporada " + temporadaNew + " con las 2 labels asociadas:<br>";
			for (String nameWrong : listArtWrong) {
				warningMessage+=(nameWrong + "<br>");
			}
			warningMessage+="</lin>";
		}
		
		checks.add(
		    Check.make(
			    PREFIX_SALE +				   
			    "No hay artículos <b>de Temporada " + temporadaNew + "</b> con las 2 etiquetas <b>New Collection</b> y <b>New Now</b> " +
			    "(en sus correspondientes traducciones)" + warningMessage,
			    listArtWrong.size()==0, State.Info)
		    .store(StoreType.None).build());
		return checks;	
	}
	
	@Validation (
		description=PREFIX_SALE + "1) No es visible el menú superior <b>#{menu1rstLevel.getNombre()}</b>",
		level=State.Warn)
	public boolean isNotPresentMenuSuperior(Menu1rstLevel menu1rstLevel) throws Exception {
		return (!secMenus.secBloquesMenu.isPresentMenuFirstLevel(menu1rstLevel));
	}
	
	@Validation (
		description=PREFIX_SALE + "1) Sí es visible el menú superior <b>#{menu1rstLevel.getNombre()}</b>",
		level=State.Warn)
	public boolean isPresentMenuSuperior(Menu1rstLevel menu1rstLevel) throws Exception {
		return (secMenus.secBloquesMenu.isPresentMenuFirstLevel(menu1rstLevel));
	}
}
