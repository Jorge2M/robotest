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
import com.mng.robotest.test80.mango.test.data.Color;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.generic.PasosGenAnalitica;
import com.mng.robotest.test80.mango.test.pageobject.shop.filtros.SecFiltros;

@SuppressWarnings("javadoc")
public class SecFiltrosStpV {

    /**
     * @param validaciones: indica si se aplican o no las validaciones
     * @param litColor: literal del color
     * @param codigoColor: código del color
     * @return número de artículos de la 1a página obtenida
     */
    public static int selectFiltroColoresStep (AppEcom app, Channel channel, boolean validaciones, 
    										   String litMenu, List<Color> colorsToSelect, DataFmwkTest dFTest) 
    throws Exception {
        //Step
        int numArticulos1page = 0;
        datosStep datosStep = new datosStep (
            "Seleccionar los colores <b>" + Color.getListNamesFiltros(colorsToSelect) + "</b>", 
            "Aparece la galería de imágenes");
        datosStep.setGrabNettrafic(dFTest.ctx);
        try {
            SecFiltros secFiltros = SecFiltros.newInstance(channel, app, dFTest.driver);
            numArticulos1page = secFiltros.selecFiltroColoresAndReturnNumArticles(colorsToSelect); 
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }             
                
        if (validaciones) {
            int maxSecondsToWait = 1;
            List<String> listCodColors = Color.getListCodigosColor(colorsToSelect);
            String currentUrl = dFTest.driver.getCurrentUrl();
            String descripValidac =
                "1) En la URL (*) aparece el parámetro c= que contiene los códigos de color <b>" + 
                	listCodColors.toString() + "</b> (lo esperamos hasta " + maxSecondsToWait + " segundos)<br>" + 
                	"(*) " + currentUrl + "<br>" +
                "2) Aparece una pantalla en la que el title contiene \"" + litMenu.toUpperCase() + "\"<br>" +
                "3) En pantalla aparecen >1 artículos (están apareciendo " + numArticulos1page + ")";
            datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);           
            try {
                List<SimpleValidation> listVals = new ArrayList<>();
                //1)
                if (!SecFiltros.checkUrlAfterFilterContainsColors(colorsToSelect, currentUrl))
                    fmwkTest.addValidation(1, State.Warn, listVals);
                //2)
                if (!dFTest.driver.getTitle().toUpperCase().contains(litMenu.toUpperCase())) 
                    fmwkTest.addValidation(2, State.Warn, listVals);                
                //3)
                if (numArticulos1page<=1) 
                    fmwkTest.addValidation(3, State.Warn, listVals);
                                
                datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
            }
            finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
                
            //Validaciones para Analytics (sólo Firefox y NetAnalysis)
            EnumSet<Constantes.AnalyticsVal> analyticSet = EnumSet.of(
                    Constantes.AnalyticsVal.GoogleAnalytics,
                    Constantes.AnalyticsVal.DataLayer);
            PasosGenAnalitica.validaHTTPAnalytics(app, LineaType.she, analyticSet, datosStep, dFTest);
        }
        
        //Validaciones estándar. 
        AllPagesStpV.validacionesEstandar(true/*validaSEO*/, true/*validaJS*/, false/*validaImgBroken*/, datosStep, dFTest);
        
        return numArticulos1page;
    }
}
