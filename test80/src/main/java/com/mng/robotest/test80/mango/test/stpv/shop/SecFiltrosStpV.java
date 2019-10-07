package com.mng.robotest.test80.mango.test.stpv.shop;

import java.util.EnumSet;
import java.util.List;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.utils.State;
import com.mng.testmaker.utils.TestCaseData;
import com.mng.testmaker.annotations.step.Step;
import com.mng.testmaker.annotations.validation.ChecksResult;
import com.mng.testmaker.annotations.validation.Validation;
import com.mng.testmaker.annotations.step.SaveWhen;
import com.mng.robotest.test80.mango.test.data.Constantes;
import com.mng.testmaker.utils.otras.Channel;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.Color;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.generic.PasosGenAnalitica;
import com.mng.robotest.test80.mango.test.pageobject.shop.filtros.SecFiltros;

public class SecFiltrosStpV {

	final static String tagLitColorsToSelect = "@TagLitColorsToSelect";
	@Step (
		description="Seleccionar los colores <b>" + tagLitColorsToSelect + "</b>", 
        expected="Aparece la galería de imágenes",
        saveNettraffic=SaveWhen.Always)
    public static int selectFiltroColoresStep (AppEcom app, Channel channel, boolean validaciones, String litMenu, 
    										   List<Color> colorsToSelect, WebDriver driver) throws Exception {
        TestCaseData.getDatosCurrentStep().replaceInDescription(tagLitColorsToSelect, Color.getListNamesFiltros(colorsToSelect).toString());
        SecFiltros secFiltros = SecFiltros.newInstance(channel, app, driver);
        int numArticulos1page = secFiltros.selecFiltroColoresAndReturnNumArticles(colorsToSelect);            
                
        if (validaciones) {
        	checkAfterSelectFiltroColores(colorsToSelect, litMenu, numArticulos1page, driver);
                
            //Validaciones para Analytics (sólo Firefox y NetAnalysis)
            EnumSet<Constantes.AnalyticsVal> analyticSet = EnumSet.of(
                    Constantes.AnalyticsVal.GoogleAnalytics,
                    Constantes.AnalyticsVal.DataLayer);
            PasosGenAnalitica.validaHTTPAnalytics(app, LineaType.she, analyticSet, driver);
        }
        
        //Validaciones estándar. 
        StdValidationFlags flagsVal = StdValidationFlags.newOne();
        flagsVal.validaSEO = true;
        flagsVal.validaJS = true;
        flagsVal.validaImgBroken = false;
        AllPagesStpV.validacionesEstandar(flagsVal, driver);
        
        return numArticulos1page;
    }
	
	@Validation
	private static ChecksResult checkAfterSelectFiltroColores(List<Color> colorsSelected, String litMenu, 
															  int numArticulos1page, WebDriver driver) {
    	ChecksResult validations = ChecksResult.getNew();
        int maxSecondsToWait = 1;
        List<String> listCodColors = Color.getListCodigosColor(colorsSelected);
        String currentUrl = driver.getCurrentUrl();
	 	validations.add(
			"En la URL (*) aparece el parámetro c= que contiene los códigos de color <b>" + 
			listCodColors.toString() + "</b> (lo esperamos hasta " + maxSecondsToWait + " segundos)" + 
			"(*) " + currentUrl + "<br>",
			SecFiltros.checkUrlAfterFilterContainsColors(colorsSelected, currentUrl), State.Warn);    	
		
	 	validations.add(
			"Aparece una pantalla en la que el title contiene \"" + litMenu.toUpperCase(),
			driver.getTitle().toUpperCase().contains(litMenu.toUpperCase()), State.Warn);    	
	 	validations.add(
			"En pantalla aparecen >1 artículos (están apareciendo " + numArticulos1page + ")",
			numArticulos1page>1, State.Warn);   
	 	return validations;
	}
}
