package com.mng.robotest.test80.mango.test.stpv.shop.menus;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep.SaveWhen;
import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Sublinea.SublineaNinosType;
import com.mng.robotest.test80.mango.test.generic.PasosGenAnalitica;
import com.mng.robotest.test80.mango.test.generic.stackTrace;
import com.mng.robotest.test80.mango.test.getdata.productos.ArticleStock;
import com.mng.robotest.test80.mango.test.getdata.productos.ManagerArticlesStock;
import com.mng.robotest.test80.mango.test.getdata.productos.ManagerArticlesStock.TypeArticleStock;
import com.mng.robotest.test80.mango.test.pageobject.shop.AllPages;
import com.mng.robotest.test80.mango.test.pageobject.shop.bannersNew.ManagerBannersScreen;
import com.mng.robotest.test80.mango.test.pageobject.shop.filtros.FilterCollection;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.LabelArticle;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.ListSizesArticle;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleria;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleriaDesktop;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleriaDesktop.ControlTemporada;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleriaDesktop.TypeArticleDesktop;
import com.mng.robotest.test80.mango.test.pageobject.shop.landing.PageLanding;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.KeyMenu1rstLevel;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.Menu2onLevel;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.MenuLateralDesktop;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.MenuLateralDesktop.Element;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.MenuLateralDesktop.GroupMenu;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.MenuTreeApp;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.SecMenusWrap;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.desktop.SecBloquesMenuDesktop;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.desktop.SecMenusDesktop;
import com.mng.robotest.test80.mango.test.pageobject.shop.modales.ModalCambioPais;
import com.mng.robotest.test80.mango.test.pageobject.utils.DataFichaArt;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.SecCabeceraStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.StdValidationFlags;
import com.mng.robotest.test80.mango.test.stpv.shop.banner.SecBannersStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.ficha.PageFichaArtStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.galeria.PageGaleriaStpV;
import com.mng.robotest.test80.mango.test.utils.WebDriverMngUtils;

@SuppressWarnings({"static-access"})
public class SecMenusDesktopStpV {

    private final static String prefixSale = "<b style=\"color:blue\">Rebajas</b></br>";
	
    public static SecMenusUserStpV secMenusUser;
    
    /**
     * Selección de un menú superior (lateral en el caso de móvil) con un catálogo de artículos asociado (p.e. vestidos, camisas, etc.)
     */
    @Step(
    	description="Seleccionar el menú superior <b>#{menu1rstLevel}</b>", 
        expected="Aparece la galería asociada al menú",
        saveNettraffic=SaveWhen.Always)
    public static void selectMenuSuperiorTypeCatalog(Menu1rstLevel menu1rstLevel, DataCtxShop dCtxSh, WebDriver driver) 
    throws Exception {
        SecMenusDesktop
        	.secMenuSuperior
        	.secBlockMenus.clickMenuAndGetName(menu1rstLevel, dCtxSh.appE, driver);
        
        SecMenusWrapperStpV.validaSelecMenu(menu1rstLevel, dCtxSh, driver);
    }
    
    @Step (
    	description="Seleccionar el menú lateral de 1er nivel <b>#{menu1rstLevel}</b>", 
        expected="Aparecen artículos de tipo Camiseta",
        saveNettraffic=SaveWhen.Always)
    public static void selectMenuLateral1rstLevelTypeCatalog(Menu1rstLevel menu1rstLevel, DataCtxShop dCtxSh, WebDriver driver) 
    throws Exception {
        SecMenusDesktop.secMenuLateral.clickMenu(menu1rstLevel, driver);         
        SecMenusWrapperStpV.validaSelecMenu(menu1rstLevel, dCtxSh, driver);
    }
    
    @Step (
    	description="Seleccionar el menú lateral de 2o nivel <b>#{menu2onLevel}</b>", 
        expected="Aparecen artículos asociados al menú",
        saveNettraffic=SaveWhen.Always)
    public static void selectMenuLateral2oLevel(Menu2onLevel menu2onLevel, DataCtxShop dCtxSh, WebDriver driver) 
    throws Exception {
        SecMenusDesktop.secMenuLateral.clickMenu(menu2onLevel, driver);        
        SecMenusWrapperStpV.validaSelecMenu(menu2onLevel, dCtxSh, driver);
    }
    
    @Validation (
    	description="Está seleccionada la línea <b>#{lineaType}</b>",
    	//TODO provisionalmente lo ponemos a Info, cuando pasen las rebajas o cuando se resuelva 
    	//el tícket https://jira.mangodev.net/jira/browse/GPS-621
    	level=State.Info)
    public static boolean validateIsLineaSelected(LineaType lineaType, AppEcom app, WebDriver driver) {
    	return (SecMenusDesktop.secMenuSuperior.secLineas.isLineaSelected(lineaType, app, driver));
    }
    
    /**
     * Validaciones de selección de un menú de 1er nivel (superior o lateral) (las específicas de Desktop)
     */
    public static void validationsSelecMenuEspecificDesktop(MenuLateralDesktop menu, Channel channel, AppEcom app, 
    														WebDriver driver) throws Exception {
        PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(channel, app, driver);
    	pageGaleriaStpV.bannerHead.validateBannerSuperiorIfExistsDesktop();
    	if (menu.isMenuLateral()) {
    		int maxSecondsWait = 2;
    		checkIsSelectedLateralMenu(menu, maxSecondsWait, driver);
    	}
    	
        if (menu instanceof Menu1rstLevel) {
        	Menu1rstLevel menu1rstLevel = (Menu1rstLevel) menu;
        	List<Menu2onLevel> menus2onLevel = menu1rstLevel.getListMenus2onLevel();
	        if (menus2onLevel!=null && menus2onLevel.size()>0) {
	        	checkVisibility2onLevelMenus(menus2onLevel, driver);
	        }
    	}
        
        if (menu.isDataForValidateArticleNames()) {
        	String[] textsArticlesGalery = menu.getTextsArticlesGalery();
        	checkArticlesContainsLiterals(textsArticlesGalery, app, driver);
        }
        
        PageGaleriaStpV.secSelectorPrecios.validaIsSelector(driver);
        LineaType lineaResult = SecMenusWrap.getLineaResultAfterClickMenu(menu.getLinea(), menu.getNombre());
        validateIsLineaSelected(lineaResult, app, driver);    
    }
    
    @Validation (
    	description="Aparece seleccionado el menú lateral <b>#{menu.getNombre()}</b> (lo esperamos hasta #{maxSecondsWait} segundos)",
    	level=State.Warn)
    private static boolean checkIsSelectedLateralMenu(MenuLateralDesktop menu, int maxSecondsWait, WebDriver driver) {
        return (SecMenusDesktop.secMenuLateral.isSelectedMenu(menu, maxSecondsWait, driver));
    }
      
    @Validation
    private static ChecksResult checkVisibility2onLevelMenus(List<Menu2onLevel> menus2onLevel, WebDriver driver) 
    throws Exception {
    	ChecksResult validations = ChecksResult.getNew();
        for (Menu2onLevel menu2oNivelTmp : menus2onLevel) {
	    	validations.add(
	    		"Aparecen el menú de 2o nivel <b>" + menu2oNivelTmp.getNombre() + "</b>",
	    		SecMenusDesktop.secMenuLateral.isVisibleMenu(driver, menu2oNivelTmp), State.Warn);
        }
        return validations;
    }
    
    @Validation
    private static ChecksResult checkArticlesContainsLiterals(String[] textsArticlesGalery, AppEcom app, WebDriver driver) 
    throws Exception {
    	ChecksResult validations = ChecksResult.getNew();
    	
    	String litsToContain = "";
        for (int i=0; i<textsArticlesGalery.length; i++) {
        	litsToContain+= "<br>" + textsArticlesGalery[i];
        }
        PageGaleriaDesktop pageGaleriaDesktop = (PageGaleriaDesktop)PageGaleria.getInstance(Channel.desktop, app, driver);
        ArrayList<String> listTxtArtNoValidos = pageGaleriaDesktop.nombreArticuloNoValido(textsArticlesGalery);
        String articlesWrongWarning = "";
        if (listTxtArtNoValidos.size() > 0) {
        	articlesWrongWarning+="<br>" + "<b>Warning!</b> Hay Algún artículo extraño, p.e.:";
	        for (String txtArtNoValido : listTxtArtNoValidos) {
	        	articlesWrongWarning+=("<br>" + txtArtNoValido);
	        }
        }
        State stateVal = State.Defect;
        if (listTxtArtNoValidos.size()<2) {
        	stateVal = State.Warn;
        }
	 	validations.add(
			"Todos los artículos contienen alguno de los literales: " + litsToContain + articlesWrongWarning,
			listTxtArtNoValidos.size()==0, stateVal);
    
    	return validations;
    }
    
    /**
     * Función que ejecuta el paso/validaciones correspondiente a la selección de una entrada el menú superior de Desktop
     */
    final static String tagMenu = "@TagMenu";
    @Step (
    	description="Selección del menú <b>" + tagMenu + "</b> (data-ga-label=#{menu1rstLevel.getDataGaLabelMenuSuperiorDesktop()})", 
        expected="El menú se ejecuta correctamente",
        saveNettraffic=SaveWhen.Always)
    public static void stepEntradaMenuDesktop(Menu1rstLevel menu1rstLevel, String paginaLinea, DataCtxShop dCtxSh, WebDriver driver) 
    throws Exception {
        //Si en la pantalla no existen los menús volvemos a la página inicial de la línea
        LineaType lineaMenu = menu1rstLevel.getLinea();
    	if (!SecMenusDesktop.secMenuSuperior.secLineas.isLineaVisible(lineaMenu, dCtxSh.appE, driver)) {
            driver.get(paginaLinea);
    	}
        SecMenusDesktop.secMenuSuperior.secBlockMenus.clickMenuAndGetName(menu1rstLevel, dCtxSh.appE, driver);
        TestCaseData.getDatosCurrentStep().replaceInDescription(tagMenu, menu1rstLevel.getNombre());
        ModalCambioPais.closeModalIfVisible(driver);
        
        validaPaginaResultMenu(menu1rstLevel, dCtxSh, driver);
        LineaType lineaResult = SecMenusWrap.getLineaResultAfterClickMenu(lineaMenu, menu1rstLevel.getNombre());
        SecMenusDesktopStpV.validateIsLineaSelected(lineaResult, dCtxSh.appE, driver);
        PasosGenAnalitica.validaHTTPAnalytics(dCtxSh.appE, lineaMenu, driver);
    }
    
    final static String tagCarruselsLinea = "@TagCarrusels";
    @Step (
    	description="Realizar \"hover\" sobre la línea #{lineaType}",
        expected="Aparecen los carrusels correspondientes a la línea " + tagCarruselsLinea)
    public static void stepValidaCarrusels(Pais pais, LineaType lineaType, AppEcom app, WebDriver driver) throws Exception {
        Linea linea = pais.getShoponline().getLinea(lineaType);
        TestCaseData.getDatosCurrentStep().replaceInDescription(tagCarruselsLinea, linea.getCarrusels());
        
        SecMenusDesktop.secMenuSuperior.secLineas.hoverLinea(lineaType, null, app, driver);
        if (linea.getType()!=LineaType.rebajas) {
        	checkCarruselsAfterHoverLinea(linea, app, driver);
        	
    	    //Steps - Selección de cada uno de los carrusels asociados a la línea
    	    String[] listCarrusels = linea.getListCarrusels();
    	    for (int i=0; i<listCarrusels.length; i++) {
    	        //Pare evitar KOs, sólo seleccionaremos el carrusel si realmente existe (si no existe previamente ya habremos dado un Warning)
    	        if (SecMenusDesktop.secMenuSuperior.secCarrusel.isPresentCarrusel(linea, listCarrusels[i], app, driver)) {
    	            stepSeleccionaCarrusel(pais, lineaType, listCarrusels[i], app, driver);
    	        }
    	    }
        }
    }
    
    @Validation
    private static ChecksResult checkCarruselsAfterHoverLinea(Linea linea, AppEcom app, WebDriver driver) {
    	ChecksResult validations = ChecksResult.getNew();
	    int maxSecondsWait = 1;
      	validations.add(
    		"Aparece el bloque de menús de la línea " + linea.getType() + " (lo esperamos hasta " + maxSecondsWait + " segundos)",
    		SecMenusDesktop.secMenuSuperior.secBlockMenus.isCapaMenusLineaVisibleUntil(linea.getType(), maxSecondsWait, app, driver), 
    		State.Warn);
      	validations.add(
    		"El número de carrusels es de " + linea.getListCarrusels().length,
    		linea.getListCarrusels().length==SecMenusDesktop.secMenuSuperior.secCarrusel.getNumCarrousels(linea.getType(), app, driver), 
    		State.Warn);
      	validations.add(
    		"Aparecen los carrusels: " + linea.getCarrusels().toString(),
    		SecMenusDesktop.secMenuSuperior.secCarrusel.isVisibleCarrusels(linea, app, driver), State.Warn);
    	return validations;
    }
    
    @Step (
    	description="Seleccionar el carrusel de la línea #{lineaType} correspondiente a <b>#{idCarrusel}</b>",
        expected="Aparece la página asociada al carrusel #{lineaType} / #{idCarrusel}")
    public static void stepSeleccionaCarrusel(Pais pais, LineaType lineaType, String idCarrusel, AppEcom app, WebDriver driver) 
    throws Exception {
        Linea linea = pais.getShoponline().getLinea(lineaType);
        SecMenusDesktop.secMenuSuperior.secLineas.hoverLinea(lineaType, null, app, driver);
        SecMenusDesktop.secMenuSuperior.secCarrusel.clickCarrousel(pais, lineaType, idCarrusel, app, driver);
        checkAfterSelectCarrusel(linea, idCarrusel, app, driver);
    }
    
    @Validation
    private static ChecksResult checkAfterSelectCarrusel(Linea linea, String idCarrusel, AppEcom app, WebDriver driver) 
    throws Exception {
    	ChecksResult validations = ChecksResult.getNew();
    	LineaType lineaType = linea.getType();
	    PageGaleriaDesktop pageGaleriaDesktop = (PageGaleriaDesktop)PageGaleria.getInstance(Channel.desktop, app, driver);

	    int maxSecondsWait = 3;
      	validations.add(
    		"Aparece algún artículo (lo esperamos hasta " + maxSecondsWait + " segundos)",
    		pageGaleriaDesktop.isVisibleArticleUntil(1, maxSecondsWait), State.Info, true);
      	validations.add(
    		"El 1er artículo es de tipo " + linea.getType(),
    		pageGaleriaDesktop.isArticleFromLinea(1, lineaType), State.Warn);
	    if (lineaType!=LineaType.nuevo) {
	      	validations.add(
        		"El 1er artículo es de la línea " + idCarrusel,
        		pageGaleriaDesktop.isArticleFromCarrusel(1, linea, idCarrusel), State.Warn);
	    }
	    boolean panoramEnLinea = (linea.getPanoramicas()!=null && linea.getPanoramicas().compareTo("s")==0);
	    if (panoramEnLinea) {
	      	validations.add(
        		"Aparece algún artículo en panorámica",
        		pageGaleriaDesktop.getNumArticulos(TypeArticleDesktop.Panoramica)!=0, State.Warn);
	    }
	    	
	    return validations;
    }
    
    @Step (
    	description=
    		"Seleccionar la <b style=\"color:chocolate\">Línea</b> " + 
    		"<b style=\"color:brown;\">\"#{lineaType.getNameUpper()}</b>",
        expected=
    		"Aparece la página correcta asociada a la línea #{lineaType.getNameUpper()}")
    public static void seleccionLinea(LineaType lineaType, DataCtxShop dCtxSh, WebDriver driver) 
    throws Exception {
        SecMenusDesktop.secMenuSuperior.secLineas.selecLinea(dCtxSh.pais, lineaType, dCtxSh.appE, driver);       
        validaSelecLinea(lineaType, null, dCtxSh, driver);
    }
    
    @Step (
    	description=
    		"Seleccionar la línea / <b style=\"color:chocolate\">Sublínea</b> " + 
    		"<b style=\"color:brown;\">\"#{lineaType.name()} / #{sublineaType.getNameUpper()}</b>",
        expected=
    		"Aparece la página correcta asociada a la línea/sublínea")
    public static void seleccionSublinea(LineaType lineaType, SublineaNinosType sublineaType, 
    									 DataCtxShop dCtxSh, WebDriver driver) throws Exception {
        validaSelecLinea(lineaType, sublineaType, dCtxSh, driver);
    }    
    
    public static void validaSelecLinea(LineaType lineaType, SublineaNinosType sublineaType, 
    									DataCtxShop dCtxSh, WebDriver driver) throws Exception {
    	SecCabeceraStpV secCabeceraStpV = SecCabeceraStpV.getNew(dCtxSh, driver);
        if (sublineaType==null) {
            validateIsLineaSelected(lineaType, dCtxSh.appE, driver);
        }

        secCabeceraStpV.validateIconoBolsa();

        //Validaciones en función del tipo de página que debería aparecer al seleccionar la línea
        Linea linea = dCtxSh.pais.getShoponline().getLinea(lineaType);
        if (sublineaType!=null) {
            linea = linea.getSublineaNinos(sublineaType);
        }
            
        switch (linea.getContentDeskType()) {
        case articulos:
        	PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(Channel.desktop, dCtxSh.appE, driver);
        	int maxSecondsWait = 3;
            pageGaleriaStpV.validaArtEnContenido(maxSecondsWait);
            break;
        case banners:
        	int maxBannersToLoad = 1;
        	SecBannersStpV secBannersStpV = new SecBannersStpV(maxBannersToLoad, driver);
        	secBannersStpV.validaBannEnContenido();
            break;
        case vacio:
            break;
        default:
            break;
        }
        
        //Validaciones
        //AllPagesStpV.validatePageWithFooter(dCtxSh.pais, dCtxSh.appE, datosStep, dFTest);
        
        //Validaciones estándar. 
        StdValidationFlags flagsVal = StdValidationFlags.newOne();
        flagsVal.validaSEO = true;
        flagsVal.validaJS = true;
        flagsVal.validaImgBroken = true;
        AllPagesStpV.validacionesEstandar(flagsVal, driver);
    }
    
    @Step (
    	description="Contamos el número de pestañas y menús de #{lineaType} / #{sublineaType}",
        expected="El número de pestañas/menús coincide con el del nodo anterior")
    public static void countSaveMenusEntorno(LineaType lineaType, SublineaNinosType sublineaType, String inodo, String urlBase, AppEcom app, WebDriver driver) 
    throws Exception {
    	int numPestanyas = SecMenusDesktop.secMenuSuperior.secLineas.getListaLineas(driver).size();
        int numMenus = SecMenusDesktop.secMenuSuperior.secBlockMenus.getListMenusLinea(lineaType, sublineaType, app, driver).size();
        checkNumPestanyasYmenusEqualsInBothNodes(numPestanyas, numMenus, lineaType, sublineaType, inodo, urlBase);
    }    
    
    @Validation
    private static ChecksResult checkNumPestanyasYmenusEqualsInBothNodes(int numPestanyas, int numMenus, LineaType lineaType, SublineaNinosType sublineaType, 
    																	 String inodo, String urlBase) {
    	ChecksResult validations = ChecksResult.getNew();
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
    	
        String clave = lineaType.name();
        if (sublineaType!=null) {
            clave+=sublineaType.name();
        }
        clave+=urlBase;    
        
        //Si están registrados en el contexto el número de pestañas y menús...
        if (dFTest.ctx.getAttribute("numPestanyas" + clave) != null && 
            dFTest.ctx.getAttribute("numMenus" + clave) != null) {
        	
            //Obtenemos el número de pestañas y menús almacenados en el contexto
            int numPestanyas_C = ((Integer)dFTest.ctx.getAttribute("numPestanyas" + clave)).intValue();
            int numMenus_C = ((Integer)dFTest.ctx.getAttribute("numMenus" + clave)).intValue();
        	
	      	validations.add(
	    		"El número de pestañas (" + numPestanyas + ") coincide con el del nodo " + dFTest.ctx.getAttribute("NodoMenus" + clave) + " (" + numPestanyas_C + ")",
	    		(numPestanyas==numPestanyas_C), State.Warn);
	      	validations.add(
	    		"El número de menús (" + numMenus + ") coincide con el del nodo " + dFTest.ctx.getAttribute("NodoMenus" + clave) + " (" + numMenus_C + ")",
	    		(numMenus==numMenus_C), State.Warn);
        }

        //Almacenamos los nuevos datos en el contexto
        dFTest.ctx.setAttribute("numPestanyas" + clave, Integer.valueOf(numPestanyas));
        dFTest.ctx.setAttribute("numMenus" + clave, Integer.valueOf(numMenus));
        dFTest.ctx.setAttribute("NodoMenus" + clave, inodo);
        
        return validations;
    }
    
    @Step (
    	description="Seleccionar el banner existente a la derecha de los menús", 
        expected="Aparece una página con banners o artículos")
    public static void clickRightBanner(LineaType lineaType, SublineaNinosType sublineaType, AppEcom app, WebDriver driver) 
    throws Exception {
        SecMenusDesktop.secMenuSuperior.secBlockMenus.clickRightBanner(lineaType, sublineaType, app, driver);
        checkAreValidMangoObjectsInPage(app, driver);
    }
    
    @Validation (
    	description="Aparece una página con banners, artículos, iframes, maps o sliders",
    	level=State.Warn)
    private static boolean checkAreValidMangoObjectsInPage(AppEcom app, WebDriver driver) throws Exception {
        PageGaleria pageGaleria = PageGaleria.getInstance(Channel.desktop, app, driver);
        if (!pageGaleria.isVisibleArticleUntil(1, 3) &&
            !PageLanding.hayIframes(driver) &&
            !PageLanding.hayMaps(driver) &&
            !PageLanding.haySliders(driver)) {
            int maxBannersToLoad = 1;
            ManagerBannersScreen managerBanners = new ManagerBannersScreen(maxBannersToLoad, driver);
            return (managerBanners.existBanners());
        }
        return true;
    }
    
    final static String tagUrlAcceso = "@TagUrlAcceso";
    @Step (
    	description="Cargar la siguiente URL de redirect a <b>España / HE / Zapatos</b>:<br>" + tagUrlAcceso,
        expected="Aparece desplegada la página de Zapatos (HE)")
    public static void checkURLRedirectZapatosHeEspanya(Channel channel, AppEcom app, WebDriver driver) 
    throws Exception {
        URI uri = new URI(driver.getCurrentUrl());
        String tiendaId = "he";
        if (app==AppEcom.outlet) {
            tiendaId = "outletH";
        }
        String urlAccesoCorreo = 
        	uri.getScheme() + "://" + 
        	uri.getHost() + 
        	"/redirect.faces?op=conta&seccion=accesorios_he&tiendaid=" + 
        	tiendaId + 
        	"&menu_temporada=2&menu_accesorio=140";
        TestCaseData.getDatosCurrentStep().replaceInDescription(tagUrlAcceso, urlAccesoCorreo);

        driver.navigate().to(urlAccesoCorreo);
    	Menu1rstLevel menu1erNivel = MenuTreeApp.getMenuLevel1From(app, KeyMenu1rstLevel.from(LineaType.he, null, "zapatos"));
        validationsSelecMenuEspecificDesktop(menu1erNivel, channel, app, driver);
    }
    
    final static String tagRefArticle = "@TagRefArticle";
    @Step (
    	description=
    		"Cargar la siguiente URL de redirect a la ficha del producto <b>" + tagRefArticle + 
    		" (#{pais.getNombre_pais()})</b>:<br>" + tagUrlAcceso,
        expected=
        	"Aparece la ficha del producto " + tagRefArticle)
    public static void checkURLRedirectFicha(Pais pais, DataCtxShop dCtxSh, WebDriver driver) 
    throws Exception {
    	ArticleStock articulo = ManagerArticlesStock.getArticleStock(TypeArticleStock.articlesWithMoreOneColour, dCtxSh);
    	TestCaseData.getDatosCurrentStep().replaceInDescription(tagRefArticle, articulo.getReference());
    	TestCaseData.getDatosCurrentStep().replaceInExpected(tagRefArticle, articulo.getReference());
    	
        URI uri = new URI(driver.getCurrentUrl());
        String tiendaId = "she";
        if (dCtxSh.appE==AppEcom.outlet) {
            tiendaId = "outlet";
        }
        
        String urlAccesoCorreo = 
        	uri.getScheme() + "://" + uri.getHost() + "/redirect.faces?op=conta&tiendaid=" + tiendaId + "&pais=" + pais.getCodigo_pais() + 
        	"&producto=" + articulo.getReference() + "&color=" + articulo.getColourCode() ;
        TestCaseData.getDatosCurrentStep().replaceInDescription(tagUrlAcceso, urlAccesoCorreo);
        driver.navigate().to(urlAccesoCorreo);

        DataFichaArt datosArticulo = new DataFichaArt(articulo.getReference(), "");
        PageFichaArtStpV pageFichaStpV = new PageFichaArtStpV(dCtxSh.appE, dCtxSh.channel);
        pageFichaStpV.validaDetallesProducto(datosArticulo);
    }
    
    public static void validaPaginaResultMenu(MenuLateralDesktop menu, DataCtxShop dCtxSh, WebDriver driver) 
    throws Exception {
    	checkResultDependingMenuGroup(menu, dCtxSh.appE, driver);
    	checkErrorPageWithoutException(driver);
    	GroupMenu groupMenu = menu.getGroup();
    	if (groupMenu.canContainElement(Element.article)) {
    		if (dCtxSh.pais.isEspanya()) {
    			checkSizeDivImages(dCtxSh, driver);
    		}
            Menu1rstLevel menuPromocion = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(menu.getLinea(), menu.getSublinea(), "promocion"));
            menuPromocion.setDataGaLabel("promocion");

            //TODO activar en época de rebajas
//            if (dCtxSh.pais.getCodigo_pais().compareTo("720")==0) {
//            	validationsSpecificEndRebajasChina(dCtxSh, driver);
//            }
//            validationsRebajas(dCtxSh.channel, dCtxSh.appE, driver);
    	}
    	
        StdValidationFlags flagsVal = StdValidationFlags.newOne();
        flagsVal.validaSEO = true;
        flagsVal.validaJS = true;
        flagsVal.validaImgBroken = true;
        AllPagesStpV.validacionesEstandar(flagsVal, driver);
    }
    
    @Validation
    private static ChecksResult checkSizeDivImages(DataCtxShop dCtxSh, WebDriver driver) throws Exception {
    	ChecksResult validations = ChecksResult.getNew();
		PageGaleriaDesktop pageGaleriaDesktop = (PageGaleriaDesktop)PageGaleria.getInstance(dCtxSh.channel, dCtxSh.appE, driver);
		int numPage = 1; 
		double marginPercError = 2;
	  	ListSizesArticle listArtWrong1rstPage = pageGaleriaDesktop.getArticlesWithWrongSize(numPage, marginPercError);
	 	validations.add(
			"Los div de los artículos de la " + numPage + "a página tienen un tamaño acorde al especificado en el atributo width de su imagen " + 
			"(margen del " + marginPercError + "%)" +
			getLiteralWarningArticlesSizesWrong(listArtWrong1rstPage),
			listArtWrong1rstPage.size()==0, State.Defect);
	 	
	 	return validations;
    }
    
    private static String getLiteralWarningArticlesSizesWrong(ListSizesArticle listArtWrong) {
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
    private static ChecksResult checkResultDependingMenuGroup(MenuLateralDesktop menu, AppEcom app, WebDriver driver) 
    throws Exception {
    	ChecksResult validations = ChecksResult.getNew();
    	GroupMenu groupMenu = menu.getGroup();
    	List<Element> elemsCanBeContained = groupMenu.getElementsCanBeContained();
    	boolean contentPageOk = PageLanding.isSomeElementVisibleInPage(elemsCanBeContained, app, driver);
	 	validations.add(
			"Aparecen alguno de los siguientes elementos: <b>" + elemsCanBeContained + "</b> (es un menú perteneciente al grupo <b>" + groupMenu + ")</b>",
			contentPageOk, State.Warn);
    	
		PageGaleria pageGaleria = PageGaleria.getInstance(Channel.desktop, app, driver);
    	if (groupMenu.canContainElement(Element.article)) {
    	 	String guiones = "--";
    	 	validations.add(
    			"No hay artículos con \"" + guiones + "\"",
    			!((PageGaleriaDesktop)pageGaleria).isArticuloWithStringInName(guiones), State.Warn);
    	}
    	
    	if (groupMenu.isTitleEquivalentToMenuName()) {
    		boolean isTitleAccording = AllPages.isTitleAssociatedToMenu(menu.getNombre(), driver);
    	 	validations.add(
    			"El title de la página es el asociado al menú <b>" + menu.getNombre() + "</b>",
    			isTitleAccording, State.Info);
    	 	if (!isTitleAccording) {
        	 	validations.add(
        			"El título no coincide -> Validamos que exista el header <b>" + menu.getNombre() + "</b> en el inicio de la galería",
        			pageGaleria.isHeaderArticlesVisible(menu.getNombre()), State.Warn, false);
    	 	}
    	}
    	
	 	return validations;
    }    

    public static ChecksResult checkErrorPageWithoutException(WebDriver driver) throws Exception {
    	ChecksResult validations = ChecksResult.getNew();
		ITestContext ctx = TestCaseData.getdFTest().ctx;
	    stackTrace exception = WebDriverMngUtils.stackTaceException(driver, ctx);
	    String excepcionDuplicada = "";
	    if (exception.getRepetida()) {
	    	excepcionDuplicada+="<br><b>Warning!</b>Se ha detectado una excepción detectada previamente (" + exception.getNumExcepciones() + ")";
	    }
	 	validations.add(
			"El errorPage.faces no devuelve una excepción" + excepcionDuplicada,
			!exception.getExiste(), State.Warn);
	 	return validations;
    }
    
	//Temporal para prueba fin rebajas en China
    @Validation
    public static ChecksResult validationsSpecificEndRebajasChina(DataCtxShop dCtxSh, WebDriver driver) throws Exception {
    	ChecksResult validations = ChecksResult.getNew();
    	PageGaleriaDesktop pageGaleriaDesktop = (PageGaleriaDesktop)PageGaleria.getInstance(dCtxSh.channel, dCtxSh.appE, driver);
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
        
    	validations.add(
    		prefixSale +     
    		"No hay artículos con las siguientes características:<br>" + 
    		" * De temporadas T2 y T3 " + tempSale + warningMessage,
    		listArtWrong.size()==0, State.Defect);
      	return validations;
    }
    
    public static void validationsRebajas(Channel channel, AppEcom app, WebDriver driver) throws Exception {
    	checkNoArticlesRebajadosWithLabelIncorrect(channel, app, driver);
    	checkNoArticlesTemporadaOldWithLabelsWrong(channel, app, driver);
    	checkNoArticlesTemporadaNewWithLabelsWrong(channel, app, driver);
    }
    
    @Validation
    private static ChecksResult checkNoArticlesRebajadosWithLabelIncorrect(Channel channel, AppEcom app, WebDriver driver) 
    throws Exception {
    	ChecksResult validations = ChecksResult.getNew();
    	PageGaleriaDesktop pageGaleriaDesktop = (PageGaleriaDesktop)PageGaleria.getInstance(channel, app, driver);
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
    	
    	validations.add(
			prefixSale +        		   
            "No hay artículos con las siguientes características:<br>" + 
            " * Rebajados</b><br>" +
            " * De temporadas anteriores " + tempSales + "<br>" +
            " * Con alguna de las etiquetas <b>" + listLabelsWrong + "</b> (en sus correspondientes traducciones)" + warningMessage,
    		listArtWrong.size()==0, State.Warn);
    	return validations;
    }
    
    @Validation
    private static ChecksResult checkNoArticlesTemporadaOldWithLabelsWrong(Channel channel, AppEcom app, WebDriver driver) 
    throws Exception {
    	ChecksResult validations = ChecksResult.getNew();
        ArrayList<Integer> temporadaOld = new ArrayList<Integer>(Arrays.asList(2));  
       	List<LabelArticle> listLabelsWrong = PageGaleria.listLabelsNew;
    	PageGaleriaDesktop pageGaleriaDesktop = (PageGaleriaDesktop)PageGaleria.getInstance(channel, app, driver);
       	List<String> listArtWrong = pageGaleriaDesktop.getArticlesTemporadaXWithLiteralInLabel(temporadaOld, listLabelsWrong);
    	String warningMessage = "";
        if (listArtWrong.size() > 0) {
        	warningMessage+=
                "<br><lin style=\"color:" + State.Warn.getColorCss() + ";\"><b>Warning!</b>: " + 
                "hay " + listArtWrong.size() + " artículos de temporada " + temporadaOld + " con label errónea:<br>";
            for (String nameWrong : listArtWrong) {
            	warningMessage+=(nameWrong + "<br>");
            }
            warningMessage+="</lin>";
        }
    	
    	validations.add(
			prefixSale +        		   
            "No hay artículos <b>de Temporada " + temporadaOld + "</b> con alguna de las etiquetas <b>" + listLabelsWrong + "</b> " + 
            "(en sus correspondientes traducciones)" + warningMessage,
    		listArtWrong.size()==0, State.Warn);
    	return validations;
    }
    	
    @Validation
    private static ChecksResult checkNoArticlesTemporadaNewWithLabelsWrong(Channel channel, AppEcom app, WebDriver driver) 
    throws Exception {
		ChecksResult validations = ChecksResult.getNew();
        ArrayList<Integer> temporadaNew = new ArrayList<Integer>(Arrays.asList(3));
        PageGaleriaDesktop pageGaleriaDesktop = (PageGaleriaDesktop)PageGaleria.getInstance(channel, app, driver);
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
        
    	validations.add(
			prefixSale +        		   
            "No hay artículos <b>de Temporada " + temporadaNew + "</b> con las 2 etiquetas <b>New Collection</b> y <b>New Now</b> " +
            "(en sus correspondientes traducciones)" + warningMessage,
    		listArtWrong.size()==0, State.Info, true);
        return validations;	
    }
    
    @Validation (
    	description=prefixSale + "1) No es visible el menú superior <b>#{menu1rstLevel.getNombre()}</b>",
    	level=State.Warn)
    public static boolean isNotPresentMenuSuperior(Menu1rstLevel menu1rstLevel, AppEcom app, WebDriver driver) 
    throws Exception {
    	return (!SecBloquesMenuDesktop.isPresentMenuFirstLevel(menu1rstLevel, driver));
    }
    
    @Validation (
    	description=prefixSale + "1) Sí es visible el menú superior <b>#{menu1rstLevel.getNombre()}</b>",
    	level=State.Warn)
    public static boolean isPresentMenuSuperior(Menu1rstLevel menu1rstLevel, AppEcom app, WebDriver driver) 
    throws Exception {
    	return (SecBloquesMenuDesktop.isPresentMenuFirstLevel(menu1rstLevel, driver));
    }
}
