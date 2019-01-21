package com.mng.robotest.test80.mango.test.stpv.shop;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.arq.utils.otras.Constantes;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.IdiomaPais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.generic.PasosGenAnalitica;
import com.mng.robotest.test80.mango.test.pageobject.shop.PagePrehome;

@SuppressWarnings("javadoc")
public class PagePrehomeStpV {
    
    /**
     * Selecciona un país/idioma de la página de countrys (no llega a entrar en la shop)
     */
    public static void seleccionPaisIdioma(String urlAcceso, DataCtxShop dCtxSh, DataFmwkTest dFTest) 
    throws Exception {
        //Step
        datosStep datosStep = new datosStep(
            "Acceder a la página de inicio y seleccionar el país " + dCtxSh.pais.getNombre_pais(),
            "Se selecciona el país/idioma correctamente");
        try {
            PagePrehome.goToPagePrehome(urlAcceso, dCtxSh, dFTest);
            PagePrehome.selecionPais(dCtxSh, dFTest);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        } 
        finally {datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest));}
        
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
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (dCtxSh.channel==Channel.desktop) {
                if (!PagePrehome.isPaisSelectedDesktop(dFTest.driver, dCtxSh.pais.getNombre_pais()))
                    fmwkTest.addValidation(1, State.Warn_NoHardcopy, listVals);
            }
            //2)
            if (!(dCtxSh.pais.isVentaOnline() && PagePrehome.isPaisSelectedWithMarcaCompra(dFTest.driver)))
                fmwkTest.addValidation(2, State.Warn_NoHardcopy, listVals);
                                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    /**
     * Given país/provincia/idioma seleccionados, se realiza el paso para entrar en la shop
     */
    public static datosStep entradaShopGivenPaisSeleccionado(Pais pais, IdiomaPais idioma, Channel channel, DataFmwkTest dFTest) throws Exception {
        //Step. Selección de país/idioma
        datosStep datosStep = new datosStep(
            "Si es preciso introducimos la provincia/idioma y finalmente seleccionamos el botón \"Entrar\"",
            "Se accede a la Shop correctamente");
        try {
            //Seleccionamos la provincia/idioma (si los hubiera) y seleccionamos el botón "Entrar"
            PagePrehome.selecionProvIdiomAndEnter(pais, idioma, channel, dFTest.driver);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        } 
        finally {datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest));}
        
        return datosStep;
    }

    // Acceso a través de objetos Pais e Iidoma
    public static datosStep seleccionPaisIdiomaAndEnter(DataCtxShop dCtxSh, DataFmwkTest dFTest) throws Exception {
        return PagePrehomeStpV.seleccionPaisIdiomaAndEnter(dCtxSh, false/*execValidacs*/, dFTest);
    }
    
    /**
     * Selecciona un país/idioma de la página de countrys y acceder a la Shop
     */
    public static datosStep seleccionPaisIdiomaAndEnter(DataCtxShop dCtxSh, boolean execValidacs, DataFmwkTest dFTest) throws Exception {
        //Step. Selección de país/idoma + Entrada a la Shop
        datosStep datosStep = new datosStep(
            "Acceder a la página de inicio y seleccionar el país <b>" + dCtxSh.pais.getNombre_pais() + "</b>, el idioma <b>" + dCtxSh.idioma.getCodigo().getLiteral() + "</b> y acceder",
            "Se accede correctamente al pais / idioma seleccionados");
        datosStep.setGrabNettrafic(dFTest.ctx);
        try {
            PagePrehome.accesoShopViaPrehome(dCtxSh, dFTest);

            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        } 
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

        //VALIDACIONES - PARA ANALYTICS (sólo para firefox y NetAnalysis)
        EnumSet<Constantes.AnalyticsVal> analyticSet = EnumSet.of(
            Constantes.AnalyticsVal.GoogleAnalytics,
            Constantes.AnalyticsVal.NetTraffic, 
            Constantes.AnalyticsVal.DataLayer);        
        PasosGenAnalitica.validaHTTPAnalytics(dCtxSh.appE, LineaType.she, analyticSet, datosStep, dFTest);
        
        if (execValidacs) {
            //Validaciones
            String tituloContains = "mango";
            if (dCtxSh.appE==AppEcom.outlet)
                tituloContains = "outlet";
            
            String descripValidac = 
                "1) Aparece una pantalla en la que el título contiene \"" + tituloContains + "\"";
            datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
            try {
                List<SimpleValidation> listVals = new ArrayList<>();
                //1)
                if (!dFTest.driver.getTitle().toLowerCase().contains(tituloContains))
                    fmwkTest.addValidation(1, State.Defect, listVals);
                                        
                datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
            }
            finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        }
        
        //Validaciones estándar. 
        AllPagesStpV.validacionesEstandar(true/*validaSEO*/, true/*validaJS*/, false/*validaImgBroken*/, datosStep, dFTest);

        return datosStep;
    }    
}
