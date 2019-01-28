package com.mng.robotest.test80.mango.test.stpv.shop.menus;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.arq.utils.otras.Constantes;
import com.mng.robotest.test80.arq.utils.otras.Constantes.ThreeState;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Sublinea.SublineaNinosType;
import com.mng.robotest.test80.mango.test.generic.PasosGenAnalitica;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.filtros.FilterCollection;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleria;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleriaDesktop.TypeArticle;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.KeyMenu1rstLevel;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.MenuLateralDesktop;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.MenuTreeApp;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.SecMenusFiltroCollection;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.SecMenusWrap;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.galeria.PageGaleriaStpV;

@SuppressWarnings({"javadoc"})
public class SecMenusWrapperStpV {
	static Logger pLogger = LogManager.getLogger(fmwkTest.log4jLogger);
	
    public static SecMenusUserStpV secMenuUser;
    
    public static void validateLineas(Pais pais, AppEcom app, Channel channel, DatosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = "";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
    
            //Ejecutamos las validaciones y obtenemos el literal con la descrpción de cada una de ellas
            descripValidac = getListaValidacionesLineas(listVals, pais, app, channel, dFTest.driver);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        } 
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    /**
     * Genera los literales de las validacioens + Aplica dichas validaciones
     * @listVals con la lista de validaciones fallidas si las hubiera
     * @return la lista de validaciones
     */
    private static String getListaValidacionesLineas(List<SimpleValidation> listVals, Pais pais, AppEcom app, Channel channel, WebDriver driver) {
        LineaType[] lineasToTest = Linea.LineaType.values();
        int numValidacion = 0;
        String descripValidac = "";
        
        for (LineaType lineaType : lineasToTest) {
            ThreeState stateLinea = pais.getShoponline().stateLinea(lineaType, app);
            if ( stateLinea!=ThreeState.UNKNOWN &&
                (lineaType!=LineaType.rebajas || UtilsMangoTest.validarLineaRebajas(pais))) {
                ThreeState apareceLinea = stateLinea;
                
                //Caso especial de un país con una sóla línea de she -> No ha de aparecer la línea de she
                if (lineaType==LineaType.she && app!=AppEcom.outlet && pais.getShoponline().getNumLineasTiendas(app)==1)
                    apareceLinea = ThreeState.FALSE;
                
                if (apareceLinea==ThreeState.TRUE) {
                    numValidacion+=1;
                    descripValidac+=numValidacion + ") <b>Sí</b> aparece el link de la línea \"<b>" + lineaType + "</b>\"<br>";
                    if (!SecMenusWrap.isLineaPresent(lineaType, app, channel, driver))
                        fmwkTest.addValidation(numValidacion, State.Warn, listVals);
                }    
                else {
                    numValidacion+=1;
                    descripValidac+=numValidacion + ") <b>No</b> aparece el link de la línea \"<b>" + lineaType + "</b>\"<br>";
                    if (SecMenusWrap.isLineaPresent(lineaType, app, channel, driver))
                        fmwkTest.addValidation(numValidacion, State.Warn, listVals);
                }
            }
        }
        
        return descripValidac;
    }
    
    /**
     * Recorre todos los menús existentes en la página y crea un step por cada uno de ellos
     */
    public static void stepsMenusLinea(Pais pais, LineaType lineaType, SublineaNinosType sublineaType, Channel channel, AppEcom app, DataFmwkTest dFTest) 
    throws Exception {
        String paginaLinea = dFTest.driver.getCurrentUrl();
        
        //Obtenemos la lista de menús de la línea
        Linea linea = pais.getShoponline().getLinea(lineaType);
        List<String> listMenusLabel = SecMenusWrap.getListDataLabelsMenus(linea, sublineaType, channel, app, dFTest.driver);
        for (int i=0; i<listMenusLabel.size(); i++) {
            try {
            	//Creamos un menú con el nombre=dataGaLabel (pues todavía no lo conocemos)
            	Menu1rstLevel menu1rstLevel = MenuTreeApp.getMenuLevel1From(app, KeyMenu1rstLevel.from(lineaType, sublineaType, listMenusLabel.get(i)));
            	menu1rstLevel.setDataGaLabel(listMenusLabel.get(i));
                if (channel==Channel.movil_web)
                    SecMenuLateralMobilStpV.stepClickMenu1rstLevel(menu1rstLevel, pais, app, dFTest);
                else
                    SecMenusDesktopStpV.stepEntradaMenuDesktop(menu1rstLevel, paginaLinea, channel, app, dFTest);
            }
            catch (Exception e) {
                //En caso de excepción no queremos que el caso de prueba pare
            	pLogger.warn("Problem in selection of menu " + lineaType + " / " + sublineaType + " / " + listMenusLabel.get(i), e);
            }        
        }
    }
    
    public static void navSeleccionaCarruselsLinea(Pais pais, LineaType lineaNuevoOReb, AppEcom app, Channel channel, DataFmwkTest dFTest) throws Exception {
        if (channel==Channel.movil_web)
            SecMenuLateralMobilStpV.navClickLineaAndCarrusels(lineaNuevoOReb, pais, app, dFTest);
        else
            SecMenusDesktopStpV.stepValidaCarrusels(pais, lineaNuevoOReb, app, dFTest);
    }
    
    public static DatosStep accesoMenuXRef(Menu1rstLevel menu1rstLevel, DataCtxShop dCtxSh, DataFmwkTest dFTest) 
    throws Exception {
        //Step
        DatosStep datosStep = new DatosStep(
            "Seleccionar el menú <b>" + menu1rstLevel + "</b>",
            "Se obtiene el catálogo de artículos asociados al menú");
        try {
            SecMenusWrap.seleccionarMenuXHref(menu1rstLevel, dCtxSh.pais, dCtxSh.channel, dFTest.driver);

            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        } 
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

        //Validaciones
        String descripValidac =
            "1) Como mínimo se obtiene 1 artículo (lo esperamos un máximo de 3 segundos)";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            PageGaleria pageGaleria = PageGaleria.getInstance(dCtxSh.channel, dCtxSh.appE, dFTest.driver);
            if (!pageGaleria.isVisibleArticuloUntil(1/*numArticulo*/, 3/*max seconds to wait*/))
                fmwkTest.addValidation(1, State.Warn_NoHardcopy, listVals);
                 
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
            
        } finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
        //Validaciones estándar. 
        AllPagesStpV.validacionesEstandar(true/*validaSEO*/, true/*validaJS*/, true/*validaImgBroken*/, datosStep, dFTest);

        return datosStep;
    }
    
    public static DatosStep selectMenu1rstLevelTypeCatalog(Menu1rstLevel menu1rstLevel, DataCtxShop dCtxSh, DataFmwkTest dFTest) 
    throws Exception {
        if (dCtxSh.channel==Channel.movil_web)
            return SecMenuLateralMobilStpV.selectMenuLateral1rstLevelTypeCatalog(menu1rstLevel, dCtxSh, dFTest);
        
        return SecMenusDesktopStpV.selectMenuSuperiorTypeCatalog(menu1rstLevel, dCtxSh, dFTest);
    }
    
    public static DatosStep selectMenuLateral1erLevelTypeCatalog(Menu1rstLevel menu1rstLevel, DataCtxShop dCtxSh, DataFmwkTest dFTest) 
    throws Exception {
        if (dCtxSh.channel==Channel.movil_web)
            return SecMenuLateralMobilStpV.selectMenuLateral1rstLevelTypeCatalog(menu1rstLevel, dCtxSh, dFTest); 
        
        return SecMenusDesktopStpV.selectMenuLateral1rstLevelTypeCatalog(menu1rstLevel, dCtxSh, dFTest);        
    }
    
    /**
     * Validación de la selección de un menú lateral de 1er o 2o nivel 
     */
    @SuppressWarnings("static-access")
	public static void validaSelecMenu(MenuLateralDesktop menu, DataCtxShop dCtxSh, DatosStep datosStep, DataFmwkTest dFTest)
    throws Exception {
        //Validaciones
    	int maxSecondsToWaitArticle = 3;
    	int maxSecondsToWaitIcon = 2;
        String descripValidac = 
            "1) Como mínimo se obtiene un artículo (lo esperamos hasta " + maxSecondsToWaitArticle + " segundos)<br>";
        if (dCtxSh.appE==AppEcom.shop) {
        	descripValidac+=
        	"2) El 1er artículo tiene 1 icono de favorito asociado (lo esperamos hasta " + maxSecondsToWaitIcon + " segundos)<br>";
            descripValidac+=
            "3) Cada artículo tiene 1 icono de favoritos asociado";
        }
        else
            descripValidac+=
            "3) No aparece ningún icono de favoritos asociado a ningún artículo";        
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            PageGaleria pageGaleria = PageGaleria.getInstance(dCtxSh.channel, dCtxSh.appE, dFTest.driver);
            //1)
            if (!pageGaleria.isVisibleArticleUntil(1/*numArticulo*/, maxSecondsToWaitArticle)) 
                fmwkTest.addValidation(1, State.Warn, listVals);
            if (dCtxSh.appE==AppEcom.shop) {
            	//2)
            	if (!pageGaleria.isArticleWithHearthIconPresentUntil(1, maxSecondsToWaitIcon))
            		fmwkTest.addValidation(2, State.Defect, listVals);
                //3)
                if (!pageGaleria.eachArticlesHasOneFavoriteIcon()) 
                    fmwkTest.addValidation(3, State.Warn, listVals);            
            }
            else {
                //3)
                if (pageGaleria.getNumFavoritoIcons() > 0)
                    fmwkTest.addValidation(3, State.Defect, listVals);
            }
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
        //Validaciones específicas para el caso de Desktop
        if (dCtxSh.channel==Channel.desktop)
            SecMenusDesktopStpV.validationsSelecMenuEspecificDesktop(menu, dCtxSh.channel, dCtxSh.appE, datosStep, dFTest);
       
        //Validaciones estándar. 
        AllPagesStpV.validacionesEstandar(true/*validaSEO*/, true/*validaJS*/, false/*validaImgBroken*/, datosStep, dFTest);
        
        //Por defecto aplicaremos todas las avalidaciones (Google Analytics, Criteo, NetTraffic y DataLayer)
        EnumSet<Constantes.AnalyticsVal> analyticSet = EnumSet.of(Constantes.AnalyticsVal.GoogleAnalytics,
                                                                  Constantes.AnalyticsVal.NetTraffic, 
                                                                  Constantes.AnalyticsVal.Criteo,
                                                                  Constantes.AnalyticsVal.DataLayer);
        
        PasosGenAnalitica.validaHTTPAnalytics(dCtxSh.appE, menu.getLinea(), analyticSet, datosStep, dFTest);
    }
    
    public static DatosStep seleccionLinea(LineaType lineaType, SublineaNinosType sublineaType, DataCtxShop dCtxSh, DataFmwkTest dFTest) 
    throws Exception {
        if (sublineaType==null) {
            return seleccionLinea(lineaType, dCtxSh, dFTest);
        }
        
        return seleccionSublinea(lineaType, sublineaType, dCtxSh, dFTest);
    }
    
    public static DatosStep seleccionLinea(LineaType lineaType, DataCtxShop dCtxSh, DataFmwkTest dFTest) throws Exception {
        if (dCtxSh.channel==Channel.movil_web) {
            return SecMenuLateralMobilStpV.seleccionLinea(lineaType, dCtxSh.pais, dCtxSh.appE, dFTest);
        }
        
        return SecMenusDesktopStpV.seleccionLinea(lineaType, dCtxSh, dFTest);
    }
    
    public static DatosStep seleccionSublinea(LineaType lineaType, SublineaNinosType sublineaType, DataCtxShop dCtxSh, DataFmwkTest dFTest)
    throws Exception {
        if (dCtxSh.channel==Channel.movil_web) {
            return SecMenuLateralMobilStpV.seleccionSublineaNinos(lineaType, sublineaType, dCtxSh, dFTest);
        }
        
        return SecMenusDesktopStpV.seleccionSublinea(lineaType, sublineaType, dCtxSh, dFTest);
    }
    
    public static void selectFiltroCollectionIfExists(FilterCollection typeMenu, Channel channel, AppEcom app, DataFmwkTest dFTest) 
    throws Exception {
    	SecMenusFiltroCollection filtrosCollection = SecMenusFiltroCollection.make(channel, app, dFTest.driver);
    	if (filtrosCollection.isVisibleMenu(FilterCollection.nextSeason)) 
    		selectFiltroCollection(typeMenu, channel, app, dFTest);
    }
    
    public static DatosStep selectFiltroCollection(FilterCollection typeMenu, Channel channel, AppEcom app, DataFmwkTest dFTest) 
    throws Exception {
        //Step.
    	SecMenusFiltroCollection filtrosCollection = SecMenusFiltroCollection.make(channel, app, dFTest.driver);
        DatosStep datosStep = new DatosStep       (
            "Seleccionar filtro de colecciones <b>" + typeMenu + "</b>", 
            "Aparece una galería con artículos de temporadas" + typeMenu.getListTempArticles());
        datosStep.setGrabNettrafic(dFTest.ctx);
        try {
        	filtrosCollection.click(typeMenu);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }           
            
        //Validaciones
        if (channel==Channel.desktop) {
	        PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(channel, app, dFTest);
	        if (typeMenu == FilterCollection.sale) {
	            pageGaleriaStpV.validaArticlesOfTemporadas(typeMenu.getListTempArticles(), datosStep);
	            pageGaleriaStpV.validaNotArticlesOfTypeDesktop(TypeArticle.norebajado, State.Warn, datosStep);
	        }
	        
	        if (typeMenu == FilterCollection.nextSeason) {
	        	pageGaleriaStpV.validaNotArticlesOfTypeDesktop(TypeArticle.rebajado, State.Info_NoHardcopy, datosStep);
	        	pageGaleriaStpV.validaArticlesOfTemporadas(typeMenu.getListTempArticles(), State.Info_NoHardcopy, datosStep);
	        }
        }
        
        return datosStep;
    }    
}
