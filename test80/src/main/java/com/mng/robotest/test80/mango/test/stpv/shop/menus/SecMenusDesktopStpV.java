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
import com.mng.robotest.test80.arq.annotations.step.StepAspect;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep.SaveWhen;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
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
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleria;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleriaDesktop;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleriaDesktop.TypeArticleDesktop;
import com.mng.robotest.test80.mango.test.pageobject.shop.landing.PageLanding;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.KeyMenu1rstLevel;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.Menu2onLevel;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.MenuLateralDesktop;
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
    public static void selectMenuSuperiorTypeCatalog(Menu1rstLevel menu1rstLevel, DataCtxShop dCtxSh, DataFmwkTest dFTest) 
    throws Exception {
        SecMenusDesktop
        	.secMenuSuperior
        	.secBlockMenus.clickMenuAndGetName(menu1rstLevel, dCtxSh.appE, dFTest.driver);
        
        SecMenusWrapperStpV.validaSelecMenu(menu1rstLevel, dCtxSh, dFTest.driver);
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
        PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(channel, app);
    	pageGaleriaStpV.validateBannerSuperiorIfExistsDesktop();
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
    private static ListResultValidation checkVisibility2onLevelMenus(List<Menu2onLevel> menus2onLevel, WebDriver driver) 
    throws Exception {
    	ListResultValidation validations = ListResultValidation.getNew();
        for (Menu2onLevel menu2oNivelTmp : menus2onLevel) {
	    	validations.add(
	    		"Aparecen el menú de 2o nivel <b>" + menu2oNivelTmp.getNombre() + "</b>",
	    		SecMenusDesktop.secMenuLateral.isVisibleMenu(driver, menu2oNivelTmp), State.Warn);
        }
        return validations;
    }
    
    @Validation
    private static ListResultValidation checkArticlesContainsLiterals(String[] textsArticlesGalery, AppEcom app, WebDriver driver) 
    throws Exception {
    	ListResultValidation validations = ListResultValidation.getNew();
    	
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
	 	validations.add(
			"Todos los artículos contienen alguno de los literales: " + litsToContain + articlesWrongWarning,
			listTxtArtNoValidos.size()==0, State.Defect);
    
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
    public static void stepEntradaMenuDesktop(Menu1rstLevel menu1rstLevel, String paginaLinea, Channel channel, 
    										  AppEcom app, WebDriver driver) throws Exception {
        //Si en la pantalla no existen los menús volvemos a la página inicial de la línea
        LineaType lineaMenu = menu1rstLevel.getLinea();
    	if (!SecMenusDesktop.secMenuSuperior.secLineas.isLineaVisible(lineaMenu, app, driver)) {
            driver.get(paginaLinea);
    	}
        SecMenusDesktop.secMenuSuperior.secBlockMenus.clickMenuAndGetName(menu1rstLevel, app, driver);
        TestCaseData.getDatosCurrentStep().replaceInDescription(tagMenu, menu1rstLevel.getNombre());
        ModalCambioPais.closeModalIfVisible(driver);
        
        validaPaginaResultMenu(menu1rstLevel, channel, app, driver);
        LineaType lineaResult = SecMenusWrap.getLineaResultAfterClickMenu(lineaMenu, menu1rstLevel.getNombre());
        SecMenusDesktopStpV.validateIsLineaSelected(lineaResult, app, driver);
        PasosGenAnalitica.validaHTTPAnalytics(app, lineaMenu, driver);
    }
    
    /**
     * Ejecuta el hover sobre el menú nuevo de Desktop y valida los carrusels que aparecen 
     */
    public static void stepValidaCarrusels(Pais pais, LineaType lineaType, AppEcom app, DataFmwkTest dFTest) throws Exception {
        Linea linea = pais.getShoponline().getLinea(lineaType);
        
        //Step
        DatosStep datosStep = new DatosStep(
            "Realizar \"hover\" sobre la línea " + linea.getType(),
            "Aparecen los carrusels correspondientes a la línea " + linea.getCarrusels());
        try {
            SecMenusDesktop.
            	secMenuSuperior.secLineas.hoverLinea(lineaType, null/*sublineaType*/, app, dFTest.driver);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok); 
        } 
        finally { StepAspect.storeDataAfterStep(datosStep); }
    
        //Validaciones
        if (linea.getType()!=LineaType.rebajas) {
	        int maxSecondsToWait = 1;
	        String descripValidac =
	        	"1) Aparece el bloque de menús de la línea " + linea.getType() + " (lo esperamos hasta " + maxSecondsToWait + " segundos)<br>" +
	            "2) El número de carrusels es de " + linea.getListCarrusels().length + "<br>" +
	            "3) Aparecen los carrusels: " + linea.getCarrusels().toString();
	        datosStep.setNOKstateByDefault();
	        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
	        try {
	            if (!SecMenusDesktop.secMenuSuperior.secBlockMenus.isCapaMenusLineaVisibleUntil(linea.getType(), maxSecondsToWait, dFTest.driver)) {
	            	listVals.add(1, State.Warn);
	            }
	            if (linea.getListCarrusels().length != SecMenusDesktop.secMenuSuperior.secCarrusel.getNumCarrousels(linea.getType(), dFTest.driver)) {
	                listVals.add(2, State.Warn);
	            }
	            if (!SecMenusDesktop.secMenuSuperior.secCarrusel.isVisibleCarrusels(linea, dFTest.driver)) {
	                listVals.add(3, State.Warn);
	            }
	
	            datosStep.setListResultValidations(listVals);
	        } 
	        finally { listVals.checkAndStoreValidations(descripValidac); }
	
	        //Steps - Selección de cada uno de los carrusels asociados a la línea
	        String[] listCarrusels = linea.getListCarrusels();
	        for (int i=0; i<listCarrusels.length; i++) {
	            //Pare evitar KOs, sólo seleccionaremos el carrusel si realmente existe (si no existe previamente ya habremos dado un Warning)
	            if (SecMenusDesktop.secMenuSuperior.secCarrusel.isPresentCarrusel(linea, listCarrusels[i], dFTest.driver))
	                stepSeleccionaCarrusel(pais, lineaType, listCarrusels[i], app, dFTest);
	        }
        }
    }
    
    /**
     * Ejecuta el paso/validación que selecciona un determinado bloque del "nuevo" (de los que aparecen al realizar 'hover' sobre la línea)
     */
    public static DatosStep stepSeleccionaCarrusel(Pais pais, LineaType lineaType, String idCarrusel, AppEcom app, DataFmwkTest dFTest) 
    throws Exception {
        Linea linea = pais.getShoponline().getLinea(lineaType);
        
        //Step. Seleccionamos el bloque asociado a las lineas de tipo she, he, niños, niñas
        DatosStep datosStep = new DatosStep(
            "Seleccionar el carrusel de la línea " + lineaType + " correspondiente a <b>" + idCarrusel + "</b>",
            "Aparece la página asociada al carrusel " + lineaType + " / " + idCarrusel);
        try {
            SecMenusDesktop.
            	secMenuSuperior.secLineas.hoverLinea(lineaType, null/*sublineaType*/, app, dFTest.driver);
            SecMenusDesktop.
            	secMenuSuperior.secCarrusel.clickCarrousel(pais, lineaType, idCarrusel, dFTest.driver);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        } 
        finally { StepAspect.storeDataAfterStep(datosStep); }
        
        //Validaciones. Aparece la galería de nuevo correcta
        PageGaleriaDesktop pageGaleriaDesktop = (PageGaleriaDesktop)PageGaleria.getInstance(Channel.desktop, app, dFTest.driver);
        boolean panoramEnLinea = (linea.getPanoramicas()!=null && linea.getPanoramicas().compareTo("s")==0);
        String validacion3 = "";
        String validacion4 = "";
        if (lineaType!=LineaType.nuevo)
        	validacion3 = 
            "3) El 1er artículo es de la línea " + idCarrusel + "<br>";        	
        
        if (panoramEnLinea)
            validacion4 = 
            "4) Aparece algún artículo en panorámica";

        int maxSecondsWait = 3;
        String descripValidac = 
            "1) Aparece algún artículo (lo esperamos hasta " + maxSecondsWait + " segundos)<br>" +
            "2) El 1er artículo es de tipo " + linea.getType() + "<br>" +
            validacion3 +
            validacion4;
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!pageGaleriaDesktop.isVisibleArticleUntil(1, maxSecondsWait)) {
                listVals.add(1, State.Info_NoHardcopy);
            }
            
            if (!pageGaleriaDesktop.isArticleFromLinea(1/*numArticle*/, lineaType)) {
                listVals.add(2, State.Warn);
            }
            if (lineaType!=LineaType.nuevo) {
            	if (!pageGaleriaDesktop.isArticleFromCarrusel(1/*numArticle*/, linea, idCarrusel)) {
            		listVals.add(3, State.Warn);
            	}
            }
            
            if (panoramEnLinea) {
                if (pageGaleriaDesktop.getNumArticulos(TypeArticleDesktop.Panoramica)==0) {
                	listVals.add(4, State.Warn);
                }
            }
            
            datosStep.setListResultValidations(listVals);
        } 
        finally { listVals.checkAndStoreValidations(descripValidac); }
        
        return datosStep;
    }
    
    /**
     * Selecciona una línea (he, she, he...) o sublínea (p.e. bebe_nino)
     */
    public static DatosStep seleccionLinea(LineaType lineaType, DataCtxShop dCtxSh, DataFmwkTest dFTest) 
    throws Exception {
        //Step
        DatosStep datosStep = null;
        String nombreLinea = "<b style=\"color:brown;\">\"" + lineaType.name().toUpperCase() + "\"</b>";
        datosStep = new DatosStep(
            "Seleccionar la <b style=\"color:chocolate\">Línea</b> " + nombreLinea,
            "Aparece la página correcta asociada a la línea " + lineaType.name().toUpperCase());
        try {
            SecMenusDesktop.
            	secMenuSuperior.secLineas.selecLinea(dCtxSh.pais, lineaType, dCtxSh.appE, dFTest.driver);

            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        } 
        finally { StepAspect.storeDataAfterStep(datosStep); }            
        
        //Validaciones
        validaSelecLinea(lineaType, null/*sublineaType*/, dCtxSh, dFTest);
        
        return datosStep;
    }
    
    /**
     * Selecciona una línea (he, she, he...) o sublínea (p.e. bebe_nino)
     */
    public static DatosStep seleccionSublinea(LineaType lineaType, SublineaNinosType sublineaType, 
    										  DataCtxShop dCtxSh, DataFmwkTest dFTest) throws Exception {
        //Step. 
        String nombreLineaSublinea = "<b style=\"color:brown;\">\"" + lineaType.name() + " / " + sublineaType.name().toUpperCase() + "\"</b>";
        DatosStep datosStep = new DatosStep(
            "Seleccionar la línea / <b style=\"color:chocolate\">Sublínea</b> " + nombreLineaSublinea,
            "Aparece la página correcta asociada a la línea/sublínea");
        try {
            SecMenusDesktop.
            	secMenuSuperior.secLineas.selectSublinea(lineaType, sublineaType, dCtxSh.appE, dFTest.driver);

            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        } 
        finally { StepAspect.storeDataAfterStep(datosStep); }

        //Validaciones
        validaSelecLinea(lineaType, sublineaType, dCtxSh, dFTest);
        
        return datosStep;
    }    
    
    public static void validaSelecLinea(LineaType lineaType, SublineaNinosType sublineaType, 
    									DataCtxShop dCtxSh, DataFmwkTest dFTest) throws Exception {
    	SecCabeceraStpV secCabeceraStpV = SecCabeceraStpV.getNew(dCtxSh, dFTest);
        if (sublineaType==null) {
        	secCabeceraStpV.validaLogoDesktop(1, lineaType);
            validateIsLineaSelected(lineaType, dCtxSh.appE, dFTest.driver);
        }

        secCabeceraStpV.validateIconoBolsa();

        //Validaciones en función del tipo de página que debería aparecer al seleccionar la línea
        Linea linea = dCtxSh.pais.getShoponline().getLinea(lineaType);
        if (sublineaType!=null) {
            linea = linea.getSublineaNinos(sublineaType);
        }
            
        switch (linea.getContentDeskType()) {
        case articulos:
        	PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(Channel.desktop, dCtxSh.appE);
        	int maxSecondsWait = 3;
            pageGaleriaStpV.validaArtEnContenido(maxSecondsWait);
            break;
        case banners:
        	int maxBannersToLoad = 1;
        	SecBannersStpV secBannersStpV = new SecBannersStpV(maxBannersToLoad, dFTest.driver);
        	secBannersStpV.validaBannEnContenido(dFTest.driver);
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
        AllPagesStpV.validacionesEstandar(flagsVal, dFTest.driver);
    }
    
    /**
     * Contamos los menús de una línea (she, he, nina, niño, violeta), validamos que sean iguales y los almacenamos en el contexto
     */
    public static DatosStep countSaveMenusEntorno(LineaType lineaType, SublineaNinosType sublineaType, String inodo, String urlBase, AppEcom app, DataFmwkTest dFTest) 
    throws Exception {
        //Step. Contamos los menús
        int numPestanyas = 0;
        int numMenus = 0;
        String descripValidac = "";
        DatosStep datosStep = new DatosStep(
            "Contamos el número de pestañas y menús de " + lineaType + "/" + sublineaType,
            "El número de pestañas/menús coincide con el del nodo anterior");
        try {
            numPestanyas = 
            	SecMenusDesktop.
            		secMenuSuperior.secLineas.getListaLineas(dFTest.driver).size();
            numMenus = 
            	SecMenusDesktop.
            		secMenuSuperior.secBlockMenus.getListMenusLinea(lineaType, sublineaType, app, dFTest.driver).size();

            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        } 
        finally { StepAspect.storeDataAfterStep(datosStep); }

        //Validaciones
        int numPestanyas_C;
        int numMenus_C;
        String clave = lineaType.name();
        if (sublineaType!=null)
            clave+=sublineaType.name();
            
        clave+=urlBase;        

        //Si están registrados en el contexto el número de pestañas y menús...
        if (dFTest.ctx.getAttribute("numPestanyas" + clave) != null && 
            dFTest.ctx.getAttribute("numMenus" + clave) != null) {
            
            //Obtenemos el número de pestañas y menús almacenados en el contexto
            numPestanyas_C = ((Integer)dFTest.ctx.getAttribute("numPestanyas" + clave)).intValue();
            numMenus_C = ((Integer)dFTest.ctx.getAttribute("numMenus" + clave)).intValue();
            
            //Validaciones. Comprobamos que el número de pestañas/menús no ha variado con respecto a otro nodo anterior
            descripValidac = 
                "1) El número de pestañas (" + numPestanyas + ") coincide con el del nodo " + dFTest.ctx.getAttribute("NodoMenus" + clave) + " (" + numPestanyas_C + ")<br>" + 
                "2) El número de menús (" + numMenus + ") coincide con el del nodo " + dFTest.ctx.getAttribute("NodoMenus" + clave) + " (" + numMenus_C + ")";
            ListResultValidation listVals = ListResultValidation.getNew(datosStep);
            try {
                if (numPestanyas != numPestanyas_C || numMenus != numMenus_C) {
                    datosStep.setResultSteps(State.Warn);
                }
                else {
                    datosStep.setResultSteps(State.Ok);
                }

                datosStep.setExcepExists(false);
            } 
            finally { listVals.checkAndStoreValidations(descripValidac); }
        }

        //Almacenamos los nuevos datos en el contexto
        dFTest.ctx.setAttribute("numPestanyas" + clave, Integer.valueOf(numPestanyas));
        dFTest.ctx.setAttribute("numMenus" + clave, Integer.valueOf(numMenus));
        dFTest.ctx.setAttribute("NodoMenus" + clave, inodo);

        return datosStep;
    }    
    
    /**
     * Función que ejecuta el paso/validaciones correspondiente a la selección de una entrada el menú superior de Desktop
     */
    public static DatosStep clickRightBanner(LineaType lineaType, SublineaNinosType sublineaType, AppEcom app, DataFmwkTest dFTest) 
    throws Exception {
        //Step
        DatosStep datosStep = new DatosStep     (
            "Seleccionar el banner existente a la derecha de los menús", 
            "Aparece una página con banners o artículos");
        try {
            SecMenusDesktop.
            	secMenuSuperior.secBlockMenus.clickRightBanner(lineaType, sublineaType, app, dFTest.driver);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }
        
        //Validaciones
        PageGaleria pageGaleria = PageGaleria.getInstance(Channel.desktop, app, dFTest.driver);
        String descripValidac = 
            "1) Aparece una página con banners, artículos, iframes, maps o sliders";
        datosStep.setNOKstateByDefault();  
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!pageGaleria.isVisibleArticleUntil(1/*numArticulo*/, 3/*seconds*/) &&
                !PageLanding.hayIframes(dFTest.driver) &&
                !PageLanding.hayMaps(dFTest.driver) &&
                !PageLanding.haySliders(dFTest.driver)) {
            	int maxBannersToLoad = 1;
            	ManagerBannersScreen managerBanners = new ManagerBannersScreen(maxBannersToLoad, dFTest.driver);
            	if (!managerBanners.existBanners()) {
                    listVals.add(1, State.Warn);
            	}
            }
                            
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
        
        return datosStep;
    }
    
    /**
     * Chequear una URL de redirect que linca a HE - Zapatos
     */
    public static DatosStep checkURLRedirectZapatosHeEspanya(Channel channel, AppEcom app, DataFmwkTest dFTest) 
    throws Exception {
        URI uri = new URI(dFTest.driver.getCurrentUrl());
        String tiendaId = "he";
        if (app==AppEcom.outlet)
            tiendaId = "outletH";
        
        String urlAccesoCorreo = 
        	uri.getScheme() + "://" + 
        	uri.getHost() + 
        	"/redirect.faces?op=conta&seccion=accesorios_he&tiendaid=" + 
        	tiendaId + 
        	"&menu_temporada=2&menu_accesorio=140";

        //Step
        DatosStep datosStep = new DatosStep(
            "Cargar la siguiente URL de redirect a <b>España / HE / Zapatos</b>:<br>" + urlAccesoCorreo,
            "Aparece desplegada la página de Zapatos (HE)");
        try {
            dFTest.driver.navigate().to(urlAccesoCorreo);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        } 
        finally { StepAspect.storeDataAfterStep(datosStep); }

        //Validaciones
    	Menu1rstLevel menu1erNivel = MenuTreeApp.getMenuLevel1From(app, KeyMenu1rstLevel.from(LineaType.he, null, "zapatos"));
        validationsSelecMenuEspecificDesktop(menu1erNivel, channel, app, dFTest.driver);
        
        return datosStep;
    }
    
    /**
     * Chequear una URL de redirect que linca a HE - Zapatos
     */
    public static DatosStep checkURLRedirectFicha(Pais pais, DataCtxShop dCtxSh, DataFmwkTest dFTest) 
    throws Exception {
    	ArticleStock articulo = ManagerArticlesStock.getArticleStock(TypeArticleStock.articlesWithMoreOneColour, dCtxSh);
        URI uri = new URI(dFTest.driver.getCurrentUrl());
        String tiendaId = "she";
        if (dCtxSh.appE==AppEcom.outlet)
            tiendaId = "outlet";
        
        String urlAccesoCorreo = 
        	uri.getScheme() + "://" + uri.getHost() + "/redirect.faces?op=conta&tiendaid=" + tiendaId + "&pais=" + pais.getCodigo_pais() + 
        	"&producto=" + articulo.getReference() + "&color=" + articulo.getColourCode() ;

        //Step
        DatosStep datosStep = new DatosStep(
            "Cargar la siguiente URL de redirect a la ficha del producto <b>" + articulo.getReference() + " (" + pais.getNombre_pais()+ ")</b>:<br>" + urlAccesoCorreo,
            "Aparece la ficha del producto " + articulo.getReference());
        try {
            dFTest.driver.navigate().to(urlAccesoCorreo);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        } 
        finally { StepAspect.storeDataAfterStep(datosStep); }

        //Validaciones
        DataFichaArt datosArticulo = new DataFichaArt(articulo.getReference(), "");
        PageFichaArtStpV pageFichaStpV = new PageFichaArtStpV(dCtxSh.appE, dCtxSh.channel);
        pageFichaStpV.validaDetallesProducto(datosArticulo);
        
        return datosStep;
    }
    
    public static void validaPaginaResultMenu(MenuLateralDesktop menu, Channel channel, AppEcom app, WebDriver driver) 
    throws Exception {
    	checkResultDependingMenuGroup(menu, app, driver);
    	checkErrorPageWithoutException(driver);
    	GroupMenu groupMenu = menu.getGroup();
    	if (groupMenu.containsArticles()) {
            validationsRebajas(channel, app, driver);
    	}
    	
        StdValidationFlags flagsVal = StdValidationFlags.newOne();
        flagsVal.validaSEO = true;
        flagsVal.validaJS = true;
        flagsVal.validaImgBroken = true;
        AllPagesStpV.validacionesEstandar(flagsVal, driver);
    }
    
    @Validation
    private static ListResultValidation checkResultDependingMenuGroup(MenuLateralDesktop menu, AppEcom app, WebDriver driver) 
    throws Exception {
    	ListResultValidation validations = ListResultValidation.getNew();
    	GroupMenu groupMenu = menu.getGroup();
    	if (groupMenu.containsArticles()) {
    		PageGaleria pageGaleria = PageGaleria.getInstance(Channel.desktop, app, driver);
    		int maxSecondsWait = 3;
    	 	validations.add(
    			"Aparecen artículos (es un menú perteneciente al grupo <b>" + groupMenu + ")</b><br>",
    			pageGaleria.isVisibleArticleUntil(1, maxSecondsWait), State.Warn);
            
    	 	String guiones = "--";
    	 	validations.add(
    			"No hay artículos con \"" + guiones + "\"<br>",
    			!((PageGaleriaDesktop)pageGaleria).isArticuloWithStringInName(guiones), State.Warn);
    	}
    	
    	if (groupMenu.isTitleEquivalentToMenuName()) {
    		//TODO modificación temporal para no grabar la imagen en caso de Baby. Hasta que se corrija (http://ci.mangodev.net/redmine/issues/50111)
    		State stateVal = State.Warn;
            if (groupMenu==GroupMenu.BebeNina || groupMenu==GroupMenu.BebeNino) {
            	stateVal = State.Info_NoHardcopy;
            }
    	 	validations.add(
    			"El title de la página es el asociado al menú<b>" + menu.getNombre() + "</b><br>",
    			AllPages.isTitleAssociatedToMenu(menu.getNombre(), driver), stateVal);
    	}
    	
    	if (groupMenu.containsOnlyCampaigns()) {
    	 	validations.add(
    			"Aparecen campañas (es un menú perteneciente al grupo " + groupMenu + ")<br>",
    			ManagerBannersScreen.existBanners(driver), State.Warn);
    	}
    	
    	if (groupMenu==GroupMenu.Desconocido) {
    		int maxSecondsWait = 3;
    		PageGaleria pageGaleria = PageGaleria.getInstance(Channel.desktop, app, driver);
    	 	validations.add(
    			"Aparecen artículos, campañas, sliders, maps, iframes (es un menú perteneciente al grupo " + groupMenu + ")<br>",
    		pageGaleria.isVisibleArticleUntil(1, maxSecondsWait) ||
            PageLanding.hayIframes(driver) ||
            PageLanding.hayMaps(driver) ||
            PageLanding.haySliders(driver) ||
            ManagerBannersScreen.existBanners(driver), State.Warn);
    	}
    	
	 	return validations;
    }    
    
    public static ListResultValidation checkErrorPageWithoutException(WebDriver driver) throws Exception {
    	ListResultValidation validations = ListResultValidation.getNew();
		ITestContext ctx = TestCaseData.getdFTest().ctx;
	    stackTrace exception = WebDriverMngUtils.stackTaceException(driver, ctx);
	    String excepcionDuplicada = "";
	    if (exception.getRepetida()) {
	    	excepcionDuplicada+="<br><b>Warning!</b>Se ha detectado una excepción detectada previamente (" + exception.getNumExcepciones() + ")<br>";
	    }
	 	validations.add(
			"El errorPage.faces no devuelve una excepción" + excepcionDuplicada,
			!exception.getExiste(), State.Warn);
	 	return validations;
    }
    
    public static void validationsRebajas(Channel channel, AppEcom app, WebDriver driver) 
    throws Exception {
        //Validación especialmente útil en periodo de Rebajas
    	DatosStep datosStep = TestCaseData.getDatosLastStep();
    	PageGaleriaDesktop pageGaleriaDesktop = (PageGaleriaDesktop)PageGaleria.getInstance(channel, app, driver);
    	List<LabelArticle> listLabelsWrong = PageGaleria.listLabelsNew;
    	List<Integer> tempSales = FilterCollection.sale.getListTempArticles();
        String descripValidac = 
            prefixSale +        		   
            "1) No hay artículos con las siguientes características:<br>" + 
            " * Rebajados</b><br>" +
            " * De temporadas anteriores " + tempSales + "<br>" +
            " * Con alguna de las etiquetas <b>" + listLabelsWrong + "</b> (en sus correspondientes traducciones)"; 
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            List<String> listArtWrong = 
            	pageGaleriaDesktop.getArticlesTemporadaxRebajadosWithLiteralInLabel(tempSales, listLabelsWrong);
            	//pageGaleriaDesktop.getArticlesRebajadosWithLiteralInLabel(listLabelsWrong);
            if (listArtWrong.size() > 0) {
                listVals.add(1, State.Warn);
                descripValidac+=
                    "<br><lin style=\"color:" + State.Warn.getColorCss() + ";\"><b>Warning!</b>: " + 
                    "hay " + listArtWrong.size() + " artículos rebajados con label errónea:<br>";
                for (String nameWrong : listArtWrong) {
             	   descripValidac+=(nameWrong + "<br>");
                }
                descripValidac+="</lin>";
            }

            datosStep.setListResultValidations(listVals);
        } 
        finally { listVals.checkAndStoreValidations(descripValidac); }
        
        //Validación especialmente útil en periodo de Rebajas
        ArrayList<Integer> temporadaOld = new ArrayList<Integer>(Arrays.asList(2));  
        descripValidac = 
            prefixSale +        		   
            "1) No hay artículos <b>de Temporada " + temporadaOld + "</b> con alguna de las etiquetas <b>" + listLabelsWrong + "</b> " + 
              "(en sus correspondientes traducciones)"; 
        datosStep.setNOKstateByDefault();
        listVals = ListResultValidation.getNew(datosStep);
        try {
            List<String> listArtWrong = pageGaleriaDesktop.getArticlesTemporadaXWithLiteralInLabel(temporadaOld, listLabelsWrong);
            if (listArtWrong.size() > 0) {
                listVals.add(1, State.Warn);
                descripValidac+=
                    "<br><lin style=\"color:" + State.Warn.getColorCss() + ";\"><b>Warning!</b>: " + 
                    "hay " + listArtWrong.size() + " artículos de temporada " + temporadaOld + " con label errónea:<br>";
                for (String nameWrong : listArtWrong) {
             	   descripValidac+=(nameWrong + "<br>");
                }
                descripValidac+="</lin>";
            }

            datosStep.setListResultValidations(listVals);
        } 
        finally { listVals.checkAndStoreValidations(descripValidac); }
    
        ArrayList<Integer> temporadaNew = new ArrayList<Integer>(Arrays.asList(3));
        descripValidac = 
            prefixSale +        		   
            "1) No hay artículos <b>de Temporada " + temporadaNew + "</b> con las 2 etiquetas <b>New Collection</b> y <b>New Now</b> " + 
              "(en sus correspondientes traducciones)"; 
        datosStep.setNOKstateByDefault();
        listVals = ListResultValidation.getNew(datosStep);
        try {
            List<String> listArtWrong = 
            	pageGaleriaDesktop.getArticlesTemporadaXWithLiteralInLabel(temporadaNew, LabelArticle.NewNow, LabelArticle.NewCollection);
            if (listArtWrong.size() > 0) {
                listVals.add(1, State.Info_NoHardcopy);
                descripValidac+=
                    "<br><lin style=\"color:" + State.Warn.getColorCss() + ";\"><b>Warning!</b>: " + 
                    "hay " + listArtWrong.size() + " artículos de temporada " + temporadaNew + " con las 2 labels asociadas:<br>";
                for (String nameWrong : listArtWrong) {
             	    descripValidac+=(nameWrong + "<br>");
                }
                descripValidac+="</lin>";
            }

            datosStep.setListResultValidations(listVals);
        } 
        finally { listVals.checkAndStoreValidations(descripValidac); }    	
    }
    
    @Validation (
    	description=prefixSale + "1) No es visible el menú superior <b>#{menu1rstLevel.getNombre()}</b>",
    	level=State.Warn)
    public static boolean isNotPresentMenuSuperior(Menu1rstLevel menu1rstLevel, AppEcom app, WebDriver driver) 
    throws Exception {
    	return (!SecBloquesMenuDesktop.isPresentMenuFirstLevel(menu1rstLevel, app, driver));
    }
}
