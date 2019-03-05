package com.mng.robotest.test80.mango.test.stpv.shop.galeria;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleria;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleriaDesktop;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.StdValidationFlags;

@SuppressWarnings({"static-access"})
public class SecSelectorPreciosStpV {

	@Validation (
		description="Es visible el selector de precios",
		level=State.Warn)
    public static boolean validaIsSelector(WebDriver driver) {
        return (PageGaleriaDesktop.secSelectorPrecios.isVisible(driver));
    }
    
    /**
     * Selecciona un intervalo de precio mínimo/precio máximo. 
     * No es posible pasar como parámetro el mínimo/máximo pues lo único que podemos hacer es 'click por la derecha' + 'click por la izquierda'
      */
    final static String tagMinimo = "[MINIMO]";
    final static String tagMaximo = "[MAXIMO]";
	@Step (
		description="Utilizar el selector de precio: Mínimo=" + tagMinimo + " Máximo=" + tagMaximo, 
        expected="Aparecen artículos con precio en el intervalo seleccionado")
    public static void seleccionaIntervalo(AppEcom app, WebDriver driver) throws Exception {
		DataFilterPrecios dataFilter = new DataFilterPrecios();
        
        //Obtenemos los mínimo/máximo originales
		dataFilter.minimoOrig = PageGaleriaDesktop.secSelectorPrecios.getImporteMinimo(driver);
		dataFilter.maximoOrig = PageGaleriaDesktop.secSelectorPrecios.getImporteMaximo(driver);
                
        //Seleccionamos un intervalo de mínimo/máximo
        PageGaleriaDesktop.secSelectorPrecios.clickMinAndMax(30/*margenPixelsIzquierda*/, 30/*margenPixelsDerecha*/, driver);

        //Obtenemos el nuevo mínimo/máximo
        dataFilter.minimoFinal = PageGaleriaDesktop.secSelectorPrecios.getImporteMinimo(driver);
        dataFilter.maximoFinal = PageGaleriaDesktop.secSelectorPrecios.getImporteMaximo(driver);
                
        //Sustituímos los tags
        TestCaseData.getDatosCurrentStep().replaceInDescription(tagMinimo, String.valueOf(dataFilter.minimoFinal));
        TestCaseData.getDatosCurrentStep().replaceInDescription(tagMaximo, String.valueOf(dataFilter.maximoFinal));    
            
        //Validaciones
        checkResultSelectFiltro(dataFilter, app, driver);
        
        //Validaciones estándar. 
        StdValidationFlags flagsVal = StdValidationFlags.newOne();
        flagsVal.validaSEO = true;
        flagsVal.validaJS = true;
        flagsVal.validaImgBroken = false;
        AllPagesStpV.validacionesEstandar(flagsVal, driver);
    }
	
	@Validation
	private static ListResultValidation checkResultSelectFiltro(DataFilterPrecios dataFilter, AppEcom app, WebDriver driver) throws Exception {
		ListResultValidation validations = ListResultValidation.getNew();
    	validations.add(
    		"El nuevo mínimo es mayor que el anterior. Era de <b>" + dataFilter.minimoOrig + "</b> y ahora es <b>" + dataFilter.minimoFinal + "</b><br>",
    		dataFilter.minimoFinal > dataFilter.minimoOrig, State.Warn);
    	validations.add(
    		"El nuevo máximo es menor que el anterior. Era de <b>" + dataFilter.maximoOrig + "</b> y ahora es <b>" + dataFilter.maximoFinal + "</b><br>",
    		dataFilter.maximoFinal < dataFilter.maximoOrig, State.Warn);
    	PageGaleria pageGaleria = PageGaleria.getInstance(Channel.desktop, app, driver);
    	validations.add(
    		"Todos los precios están en el intervalo [" + dataFilter.minimoFinal + ", " + dataFilter.maximoFinal + "]",
    		pageGaleria.preciosInIntervalo(dataFilter.minimoFinal, dataFilter.maximoFinal), State.Warn);
    	return validations;
	}
}

class DataFilterPrecios {
    public int minimoOrig = 0;
    public int maximoOrig = 0;
    public int minimoFinal = 0;
    public int maximoFinal = 0;
    public DataFilterPrecios() {}
}
