package com.mng.robotest.test80.mango.test.stpv.shop.modales;

import org.openqa.selenium.WebDriver;
import com.mng.testmaker.utils.State;
import com.mng.testmaker.utils.TestCaseData;
import com.mng.testmaker.annotations.step.Step;
import com.mng.testmaker.annotations.validation.Validation;
import com.mng.testmaker.domain.StepTestMaker;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.pageobject.shop.PagePrehome;
import com.mng.robotest.test80.mango.test.pageobject.shop.modales.ModalCambioPais;
import com.mng.robotest.test80.mango.test.stpv.shop.home.PageHomeMarcasStpV;

public class ModalCambioPaisStpV {
    
	@Validation (
		description="Aparece el modal de selección de país (lo esperamos hasta #{maxSecondsWait} segundos)",
		level=State.Defect)
    public static boolean validateIsVisible(int maxSecondsWait, WebDriver driver) {
        return (ModalCambioPais.isVisibleModalUntil(driver, maxSecondsWait));
    }
    
	final static String tagNombrePais = "@TagNombrePais";
	final static String tagCodigoPais = "@TagCodigoPais";
	final static String tagLiteralIdioma = "@TagLiteralIdioma";
	@Step (
		description="Cambiamos al país <b>" + tagNombrePais + "</b> (" + tagCodigoPais + "), idioma <b>" + tagLiteralIdioma + "</b>", 
        expected="Se accede a la shop de " + tagNombrePais + " en " + tagLiteralIdioma)
    public static void cambioPais(DataCtxShop dCtxSh, WebDriver driver) 
    throws Exception {
        StepTestMaker StepTestMaker = TestCaseData.getDatosCurrentStep();
        StepTestMaker.replaceInDescription(tagNombrePais, dCtxSh.pais.getNombre_pais());
        StepTestMaker.replaceInExpected(tagNombrePais, dCtxSh.pais.getNombre_pais());
        StepTestMaker.replaceInDescription(tagCodigoPais, dCtxSh.pais.getCodigo_pais());
        StepTestMaker.replaceInDescription(tagLiteralIdioma, dCtxSh.idioma.getCodigo().getLiteral());
        StepTestMaker.replaceInExpected(tagLiteralIdioma, dCtxSh.idioma.getCodigo().getLiteral());
        
        PagePrehome.selecPaisIdiomaYAccede(dCtxSh, driver);

        //Validation
        PageHomeMarcasStpV.validateIsPageWithCorrectLineas(dCtxSh.pais, dCtxSh.channel, dCtxSh.appE, driver);
    }
}
