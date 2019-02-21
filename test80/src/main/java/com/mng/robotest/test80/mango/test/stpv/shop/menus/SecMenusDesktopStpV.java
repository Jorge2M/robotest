package com.mng.robotest.test80.mango.test.stpv.shop.menus;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.step.StepAspect;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
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
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.MenuTreeApp;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.SecMenusWrap;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.desktop.SecMenusDesktop;
import com.mng.robotest.test80.mango.test.pageobject.shop.modales.ModalCambioPais;
import com.mng.robotest.test80.mango.test.pageobject.utils.DataFichaArt;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.SecCabeceraStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.banner.SecBannersStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.ficha.PageFichaArtStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.galeria.PageGaleriaStpV;
import com.mng.robotest.test80.mango.test.utils.WebDriverMngUtils;

@SuppressWarnings({"static-access"})
public class SecMenusDesktopStpV {

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
        SecMenusDesktop.
        	secMenuSuperior.
        	secBlockMenus.clickMenuAndGetName(menu1rstLevel, dCtxSh.appE, dFTest.driver);
        
        //Validaciones
        SecMenusWrapperStpV.validaSelecMenu(menu1rstLevel, dCtxSh, dFTest);
    }
    
    public static DatosStep selectMenuLateral1rstLevelTypeCatalog(Menu1rstLevel menu1rstLevel, DataCtxShop dCtxSh, DataFmwkTest dFTest) 
    throws Exception {
        DatosStep datosStep = new DatosStep       (
            "Seleccionar el menú lateral de 1er nivel <b>" + menu1rstLevel + "</b>", 
            "Aparecen artículos de tipo Camiseta");
        datosStep.setSaveNettrafic(SaveWhen.Always, dFTest.ctx);
        try {
            SecMenusDesktop.secMenuLateral.clickMenu(menu1rstLevel, dFTest.driver);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }           
            
        //Validaciones
        SecMenusWrapperStpV.validaSelecMenu(menu1rstLevel, dCtxSh, dFTest);
        
        return datosStep;
    }
    
    public static DatosStep selectMenuLateral2oLevel(Menu2onLevel menu2onLevel, DataCtxShop dCtxSh, DataFmwkTest dFTest) 
    throws Exception {
        //Step.
        DatosStep datosStep = new DatosStep       (
            "Seleccionar el menú lateral de 2o nivel <b>" + menu2onLevel + "</b>", 
            "Aparecen artículos asociados al menú");
        datosStep.setSaveNettrafic(SaveWhen.Always, dFTest.ctx);
        try {
            SecMenusDesktop.secMenuLateral.clickMenu(menu2onLevel, dFTest.driver);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }           
            
        //Validaciones
        SecMenusWrapperStpV.validaSelecMenu(menu2onLevel, dCtxSh, dFTest);
        
        return datosStep;
    }
    

    public static void validateIsLineaSelected(LineaType lineaType, AppEcom app, DatosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) Está seleccionada la línea <b>" + lineaType + "</b>";
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!SecMenusDesktop
            	.secMenuSuperior.secLineas.isLineaSelected(lineaType, app, dFTest.driver)) {
            	//TODO provisionalmente lo ponemos a Info, cuando pasen las rebajas o cuando se resuelva el tícket https://jira.mangodev.net/jira/browse/GPS-621
                listVals.add(1, State.Info);
            }
                
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }        
    }
    
    /**
     * Validaciones de selección de un menú de 1er nivel (superior o lateral) (las específicas de Desktop)
     */
    public static void validationsSelecMenuEspecificDesktop(MenuLateralDesktop menu, Channel channel, AppEcom app, 
    														DataFmwkTest dFTest) throws Exception {
    	DatosStep datosStep = TestCaseData.getDatosLastStep();
        PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(channel, app, dFTest);
    	pageGaleriaStpV.validateBannerSuperiorIfExistsDesktop();
        if (menu.isMenuLateral()) {
            //Validaciones
            int maxSecondsToWait = 2;
            String descripValidac = 
                "1) Aparece seleccionado el menú lateral <b>" + menu.getNombre() + "</b> (lo esperamos hasta " + maxSecondsToWait + "segundos)";
            datosStep.setNOKstateByDefault();
            ListResultValidation listVals = ListResultValidation.getNew(datosStep);
            try {
                if (!SecMenusDesktop.secMenuLateral.isSelectedMenu(menu, maxSecondsToWait, dFTest.driver)) {
                    listVals.add(1, State.Warn);
                }
                    
                datosStep.setListResultValidations(listVals);
            }
            finally { listVals.checkAndStoreValidations(descripValidac); }
        }
        
        //Validación específica para Menú de 1er nivel
        if (menu instanceof Menu1rstLevel) {
        	Menu1rstLevel menu1rstLevel = (Menu1rstLevel) menu;
        	List<Menu2onLevel> menus2onLevel = menu1rstLevel.getListMenus2onLevel();
	        if (menus2onLevel!=null && menus2onLevel.size()>0) {
	            //Validaciones
	            String descripValidac = 
	                "1) Aparecen los submenús de 2o nivel: ";
	            for (Menu2onLevel menu2oNivelTmp : menus2onLevel)
	                descripValidac = descripValidac + "<br>" + menu2oNivelTmp.getNombre();
	
	            datosStep.setNOKstateByDefault();       
	            ListResultValidation listVals = ListResultValidation.getNew(datosStep);
	            try {
	                if (!SecMenusDesktop.secMenuLateral.areVisibleMenus2oNivel(menu1rstLevel, dFTest.driver)) {
	                    listVals.add(1, State.Warn);
	                }
	                    
	                datosStep.setListResultValidations(listVals);
	            }
	            finally { listVals.checkAndStoreValidations(descripValidac); }
	        }        
        }
    
        //En caso de que tengamos substrings para validar los nombres de los artículos en la galería...
        if (menu.isDataForValidateArticleNames()) {
            //Validaciones 
            String[] textsArticlesGalery = menu.getTextsArticlesGalery();
            String descripValidac =  
                "1) Todos los artículos contienen alguno de los literales: ";
                for (int i=0; i<textsArticlesGalery.length; i++)
                    descripValidac =  descripValidac + "<br>" + textsArticlesGalery[i];
 
            datosStep.setNOKstateByDefault();
            ListResultValidation listVals = ListResultValidation.getNew(datosStep);
            try {
                PageGaleriaDesktop pageGaleriaDesktop = (PageGaleriaDesktop)PageGaleria.getInstance(Channel.desktop, app, dFTest.driver);
                ArrayList<String> listTxtArtNoValidos = pageGaleriaDesktop.nombreArticuloNoValido(textsArticlesGalery);
                if (listTxtArtNoValidos.size() > 0) {
                    descripValidac+="<br>" + "<b>Warning!</b> Hay Algún artículo extraño, p.e.:";
                    for (String txtArtNoValido : listTxtArtNoValidos) {
                        descripValidac+=("<br>" + txtArtNoValido);
                    }
                    
                    listVals.add(1, State.Warn);
                }                
            
                datosStep.setListResultValidations(listVals);
            }
            finally { listVals.checkAndStoreValidations(descripValidac); }
        }
        
        //Validaciones. Aparece el selector de precios
        PageGaleriaStpV.secSelectorPrecios.validaIsSelector(datosStep, dFTest);
        
        //Obtenemos la línea a la que debería redirigir el menú
        LineaType lineaResult = SecMenusWrap.getLineaResultAfterClickMenu(menu.getLinea(), menu.getNombre());
        
        //La línea actual es la correcta según el menú seleccionado
        validateIsLineaSelected(lineaResult, app, datosStep, dFTest);        
    }
    
    /**
     * Función que ejecuta el paso/validaciones correspondiente a la selección de una entrada el menú superior de Desktop
     */
    public static DatosStep stepEntradaMenuDesktop(Menu1rstLevel menu1rstLevel, String paginaLinea, Channel channel, 
    											   AppEcom app, DataFmwkTest dFTest) throws Exception {
        String tagMenu = "[UnknownText]";
        LineaType lineaMenu = menu1rstLevel.getLinea();
        
        //Step
        DatosStep datosStep = new DatosStep     (
        	"Selección del menú <b>" + tagMenu + "</b> (data-ga-label=" + menu1rstLevel.getDataGaLabelMenuSuperiorDesktop() + ")", 
            "El menú se ejecuta correctamente");
        datosStep.setSaveNettrafic(SaveWhen.Always, dFTest.ctx);
        try {
            //Si en la pantalla no existen los menús volvemos a la página inicial de la línea
        	if (!SecMenusDesktop.secMenuSuperior.secLineas.isLineaVisible(lineaMenu, app, dFTest.driver))
                dFTest.driver.get(paginaLinea);
            
            SecMenusDesktop.secMenuSuperior.secBlockMenus.clickMenuAndGetName(menu1rstLevel, app, dFTest.driver);
            datosStep.setDescripcion(datosStep.getDescripcion().replace(tagMenu, menu1rstLevel.getNombre()));
            ModalCambioPais.closeModalIfVisible(dFTest.driver);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }
        
        //Validaciones
        validaPaginaResultMenu(menu1rstLevel, channel, app, datosStep, dFTest);
        
        //Obtenemos la línea a la que debería redirigir el menú
        LineaType lineaResult = SecMenusWrap.getLineaResultAfterClickMenu(lineaMenu, menu1rstLevel.getNombre());
        
        //La línea actual es la correcta según el menú seleccionado
        SecMenusDesktopStpV.validateIsLineaSelected(lineaResult, app, datosStep, dFTest);
        
        //VALIDACIONES - PARA ANALYTICS (sólo para firefox y NetAnalysis)
        PasosGenAnalitica.validaHTTPAnalytics(app, lineaMenu, dFTest);
        
        return datosStep;
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
        validaSelecLinea(datosStep, lineaType, null/*sublineaType*/, dCtxSh, dFTest);
        
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
        validaSelecLinea(datosStep, lineaType, sublineaType, dCtxSh, dFTest);
        
        return datosStep;
    }    
    
    public static void validaSelecLinea(DatosStep datosStep, LineaType lineaType, SublineaNinosType sublineaType, 
    									DataCtxShop dCtxSh, DataFmwkTest dFTest) throws Exception {
    	SecCabeceraStpV secCabeceraStpV = SecCabeceraStpV.getNew(dCtxSh, dFTest);
        if (sublineaType==null) {
        	secCabeceraStpV.validaLogoDesktop(lineaType, datosStep);
            validateIsLineaSelected(lineaType, dCtxSh.appE, datosStep, dFTest);
        }

        secCabeceraStpV.validateIconoBolsa(datosStep);

        //Validaciones en función del tipo de página que debería aparecer al seleccionar la línea
        Linea linea = dCtxSh.pais.getShoponline().getLinea(lineaType);
        if (sublineaType!=null) {
            linea = linea.getSublineaNinos(sublineaType);
        }
            
        switch (linea.getContentDeskType()) {
        case articulos:
        	PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(Channel.desktop, dCtxSh.appE, dFTest);
            pageGaleriaStpV.validaArtEnContenido(datosStep);
            break;
        case banners:
        	int maxBannersToLoad = 1;
        	SecBannersStpV secBannersStpV = new SecBannersStpV(maxBannersToLoad, dFTest.driver);
        	secBannersStpV.validaBannEnContenido(datosStep, dFTest);
            break;
        case vacio:
            break;
        default:
            break;
        }
        
        //Validaciones
        //AllPagesStpV.validatePageWithFooter(dCtxSh.pais, dCtxSh.appE, datosStep, dFTest);
        
        //Validaciones estándar. 
        AllPagesStpV.validacionesEstandar(true/*validaSEO*/, true/*validaJS*/, true/*validaImgBroken*/);
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
        validationsSelecMenuEspecificDesktop(menu1erNivel, channel, app, dFTest);
        
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
        pageFichaStpV.validaDetallesProducto(datosArticulo, datosStep);
        
        return datosStep;
    }
    
    public static void validaPaginaResultMenu(MenuLateralDesktop menu, Channel channel, AppEcom app, DatosStep datosStep, DataFmwkTest dFTest) 
    throws Exception {
        String validacion1 = "";
        String validacion2_2 = "";
        boolean containsArt = false;
        boolean containsBan = false;
        boolean validac2_2 = false;
        boolean containsArtsObannersOmaps = false;
        String datagalabel_MenuSuperior = menu.getDataGaLabelMenuSuperiorDesktop();

        //En función de la columna determinamos si la página resultante ha de contener banners o artículos
        if (datagalabel_MenuSuperior.contains("nuevo")) {
            containsArt = true;
            validacion1 = "1) Menú perteneciente a la columna con id \"nuevo\" -> aparecen artículos";
        }
        else {
            if (datagalabel_MenuSuperior.contains("prendas-")) {
                validac2_2 = true;
                containsArt = true;
                validacion1 = "1) Menú perteneciente a la columna con id \"prendas\" -> aparecen artículos";
            }
            else  {
                if (datagalabel_MenuSuperior.contains("accesorios-")) {
                    validac2_2 = true;
                    containsArt = true;
                    validacion1 = "1) Menú perteneciente a la columna con id \"accesorios\" -> aparecen artículos";
                }
                else {
                    if (datagalabel_MenuSuperior.contains("recien_nacido")) {
                        validac2_2 = true;
                        containsArt = true;
                        validacion1 = "1) Menú perteneciente a la columna con id \"recien nacido\" -> aparecen artículos";
                    }
                    else {
                        if (datagalabel_MenuSuperior.contains("bebe_nina")) {
                            validac2_2 = true;
                            containsArt = true;
                            validacion1 = "1) Menú perteneciente a la columna con id \"bebe nina\" -> aparecen artículos";
                        }
                        else {
                            if (datagalabel_MenuSuperior.contains("bebe_nino")) {
                                validac2_2 = true;
                                containsArt = true;
                                validacion1 = "1) Menú perteneciente a la columna con id \"bebe nino\" -> aparecen artículos";
                            }
                            else {
                                if (datagalabel_MenuSuperior.contains("colecciones-")) {
                                    validac2_2 = true;
                                    containsArt = true;
                                    validacion1 = "1) Menú perteneciente a la columna con id \"colecciones\" -> aparecen artículos";
                                }
                                else {
                                    if (datagalabel_MenuSuperior.contains("extras-")) {
                                        containsBan = true;
                                        validacion1 = "1) Menú perteneciente a la columna con id \"extras\" -> aparecen campañas";
                                    }
                                    else {
                                        containsArtsObannersOmaps = true;
                                        validacion1 = "1) Menú perteneciente a columna con id desconocido (\"" + datagalabel_MenuSuperior + "\") -> aparecen artículos, campañas, sliders, maps, iframes";
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        
        if (validac2_2)
            validacion2_2 = "<br>2) El title de la página es el asociado al menú<b>" + menu.getNombre() + "</b>";
    
        PageGaleria pageGaleria = PageGaleria.getInstance(Channel.desktop, app, dFTest.driver);
        String descripValidac = validacion1;
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!containsArtsObannersOmaps) {
                if (containsArt && 
                    !pageGaleria.isVisibleArticleUntil(1/*numArticulo*/, 3/*seconds*/) ||
                    (containsBan && 
                    !ManagerBannersScreen.existBanners(dFTest.driver))) {
                    listVals.add(1, State.Warn);
                }
            }
            else {
                if (!pageGaleria.isVisibleArticleUntil(1/*numArticulo*/, 3/*seconds*/) &&
                    !PageLanding.hayIframes(dFTest.driver) &&
                    !PageLanding.hayMaps(dFTest.driver) &&
                    !PageLanding.haySliders(dFTest.driver) &&
                    !ManagerBannersScreen.existBanners(dFTest.driver)) {
                    listVals.add(1, State.Warn);
                }
            }
                
            datosStep.setListResultValidations(listVals);
       }
       finally { listVals.checkAndStoreValidations(descripValidac); }
        
       if (containsArt) {
           String guiones = "--";
           descripValidac = 
               "1) No hay artículos con \"" + guiones + "\""; 
           datosStep.setNOKstateByDefault();
           listVals = ListResultValidation.getNew(datosStep);
           try {
               if (((PageGaleriaDesktop)pageGaleria).isArticuloWithStringInName(guiones)) {
                   listVals.add(1, State.Warn);
               }

               datosStep.setListResultValidations(listVals);
           } 
           finally { listVals.checkAndStoreValidations(descripValidac); }

           //Validaciones relacionadas con Rebajas
           validationsRebajas(channel, app, datosStep, dFTest);
       }
       
       descripValidac = 
           "1) El errorPage.faces no devuelve una excepción" +
           validacion2_2;
       datosStep.setNOKstateByDefault();
       listVals = ListResultValidation.getNew(datosStep);
       try {
           stackTrace exception = WebDriverMngUtils.stackTaceException(dFTest.driver, dFTest.ctx);
           if (exception.getRepetida()) {
               descripValidac+="<br><b>Warning!</b>Se ha detectado una excepción detectada previamente (" + exception.getNumExcepciones() + ")";
           }
           else {
               if (exception.getExiste()) {
                   listVals.add(1, State.Warn);
               }
           }                     
           if (validac2_2) {
               if (!AllPages.isTitleAssociatedToMenu(menu.getNombre(), dFTest.driver)) {
                   //TODO modificación temporal para no grabar la imagen en caso de Baby. Hasta que se corrija (http://ci.mangodev.net/redmine/issues/50111)
            	   String datagalabel_Menu = menu.getDataGaLabelMenuLateralDesktop();
                   if (datagalabel_Menu.contains("bebe")) {
                       listVals.add(2, State.Info_NoHardcopy);
                   }
                   else {
                       listVals.add(2, State.Warn);
                   }
               }
           }
        
           datosStep.setListResultValidations(listVals);
       }
       finally { listVals.checkAndStoreValidations(descripValidac); }
       
       //Validaciones estándar. 
       AllPagesStpV.validacionesEstandar(true/*validaSEO*/, true/*validaJS*/, true/*validaImgBroken*/);
    }    
    
    public static void validationsRebajas(Channel channel, AppEcom app, DatosStep datosStep, DataFmwkTest dFTest) 
    throws Exception {
        //Validación especialmente útil en periodo de Rebajas
    	PageGaleriaDesktop pageGaleriaDesktop = (PageGaleriaDesktop)PageGaleria.getInstance(channel, app, dFTest.driver);
    	List<LabelArticle> listLabelsWrong = PageGaleria.listLabelsNew;
    	List<Integer> tempSales = FilterCollection.sale.getListTempArticles();
        String descripValidac = 
            "<b style=\"color:blue\">Rebajas</b></br>" +        		   
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
            "<b style=\"color:blue\">Rebajas</b></br>" +        		   
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
            "<b style=\"color:blue\">Rebajas</b></br>" +        		   
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
}
