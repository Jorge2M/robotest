package com.mng.robotest.test80.mango.test.stpv.shop;

import java.util.EnumSet;

import com.mng.robotest.test80.arq.annotations.step.StepAspect;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep.SaveWhen;
import com.mng.robotest.test80.arq.utils.otras.Constantes;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.IdiomaPais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.generic.PasosGenAnalitica;
import com.mng.robotest.test80.mango.test.pageobject.shop.PagePrehome;


public class PagePrehomeStpV {
    
    /**
     * Selecciona un país/idioma de la página de countrys (no llega a entrar en la shop)
     */
    public static void seleccionPaisIdioma(String urlAcceso, DataCtxShop dCtxSh, DataFmwkTest dFTest) 
    throws Exception {
        //Step
        DatosStep datosStep = new DatosStep(
            "Acceder a la página de inicio y seleccionar el país " + dCtxSh.pais.getNombre_pais(),
            "Se selecciona el país/idioma correctamente");
        try {
            PagePrehome.goToPagePrehome(urlAcceso, dFTest);
            PagePrehome.selecionPais(dCtxSh, dFTest);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        } 
        finally {StepAspect.storeDataAfterStep(datosStep);}
        
        //Validaciones.
        String validacion1 = ""; 
        if (dCtxSh.channel==Channel.desktop)
            validacion1 = "1) Queda seleccionado el país con código " + dCtxSh.pais.getCodigo_pais() + " (" + dCtxSh.pais.getNombre_pais() + ")<br>";
        
        String marcaOnline = "NO";
        if (dCtxSh.pais.isVentaOnline())
            marcaOnline = "SÍ";
        
        String descripValidac = 
            validacion1 +
            "2) El país " + marcaOnline + " tiene la marca de venta online";
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (dCtxSh.channel==Channel.desktop) {
                if (!PagePrehome.isPaisSelectedDesktop(dFTest.driver, dCtxSh.pais.getNombre_pais())) {
                	listVals.add(1, State.Warn_NoHardcopy);
                }
            }
            if (!(dCtxSh.pais.isVentaOnline() && PagePrehome.isPaisSelectedWithMarcaCompra(dFTest.driver))) {
            	listVals.add(2, State.Warn_NoHardcopy);
            }
                                    
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    /**
     * Given país/provincia/idioma seleccionados, se realiza el paso para entrar en la shop
     */
    public static DatosStep entradaShopGivenPaisSeleccionado(Pais pais, IdiomaPais idioma, Channel channel, DataFmwkTest dFTest) throws Exception {
        //Step. Selección de país/idioma
        DatosStep datosStep = new DatosStep(
            "Si es preciso introducimos la provincia/idioma y finalmente seleccionamos el botón \"Entrar\"",
            "Se accede a la Shop correctamente");
        try {
            //Seleccionamos la provincia/idioma (si los hubiera) y seleccionamos el botón "Entrar"
            PagePrehome.selecionProvIdiomAndEnter(pais, idioma, channel, dFTest.driver);
            
            datosStep.setExcepExists(false); 
            datosStep.setResultSteps(State.Ok);
        } 
        finally {StepAspect.storeDataAfterStep(datosStep);}
        
        return datosStep;
    }

    // Acceso a través de objetos Pais e Iidoma
    public static DatosStep seleccionPaisIdiomaAndEnter(DataCtxShop dCtxSh, DataFmwkTest dFTest) throws Exception {
        return PagePrehomeStpV.seleccionPaisIdiomaAndEnter(dCtxSh, false/*execValidacs*/, dFTest);
    }
    
    /**
     * Selecciona un país/idioma de la página de countrys y acceder a la Shop
     */
    public static DatosStep seleccionPaisIdiomaAndEnter(DataCtxShop dCtxSh, boolean execValidacs, DataFmwkTest dFTest) throws Exception {
        //Step. Selección de país/idoma + Entrada a la Shop
        DatosStep datosStep = new DatosStep(
            "Acceder a la página de inicio y seleccionar el país <b>" + dCtxSh.pais.getNombre_pais() + "</b>, el idioma <b>" + dCtxSh.idioma.getCodigo().getLiteral() + "</b> y acceder",
            "Se accede correctamente al pais / idioma seleccionados");
        datosStep.setSaveNettrafic(SaveWhen.Always, dFTest.ctx);
        try {
            PagePrehome.accesoShopViaPrehome(dCtxSh, dFTest);

            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        } 
        finally { StepAspect.storeDataAfterStep(datosStep); }

        //VALIDACIONES - PARA ANALYTICS (sólo para firefox y NetAnalysis)
        EnumSet<Constantes.AnalyticsVal> analyticSet = EnumSet.of(
            Constantes.AnalyticsVal.GoogleAnalytics,
            Constantes.AnalyticsVal.NetTraffic, 
            Constantes.AnalyticsVal.DataLayer);        
        PasosGenAnalitica.validaHTTPAnalytics(dCtxSh.appE, LineaType.she, analyticSet, dFTest);
        
        if (execValidacs) {
            //Validaciones
            String tituloContains = "mango";
            if (dCtxSh.appE==AppEcom.outlet)
                tituloContains = "outlet";
            
            String descripValidac = 
                "1) Aparece una pantalla en la que el título contiene \"" + tituloContains + "\"";
            datosStep.setNOKstateByDefault();
            ListResultValidation listVals = ListResultValidation.getNew(datosStep);
            try {
                if (!dFTest.driver.getTitle().toLowerCase().contains(tituloContains)) {
                	listVals.add(1, State.Defect);
                }
                	                 
                datosStep.setListResultValidations(listVals);
            }
            finally { listVals.checkAndStoreValidations(descripValidac); }
        }
        
        //Validaciones estándar. 
        AllPagesStpV.validacionesEstandar(true/*validaSEO*/, true/*validaJS*/, false/*validaImgBroken*/, dFTest);

        return datosStep;
    }    
}
