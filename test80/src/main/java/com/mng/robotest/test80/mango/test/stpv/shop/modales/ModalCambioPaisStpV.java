package com.mng.robotest.test80.mango.test.stpv.shop.modales;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.conf.State;
import com.mng.testmaker.domain.StepTM;
import com.mng.testmaker.service.TestMaker;
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
        StepTM step = TestMaker.getCurrentStepInExecution();
        step.replaceInDescription(tagNombrePais, dCtxSh.pais.getNombre_pais());
        step.replaceInExpected(tagNombrePais, dCtxSh.pais.getNombre_pais());
        step.replaceInDescription(tagCodigoPais, dCtxSh.pais.getCodigo_pais());
        step.replaceInDescription(tagLiteralIdioma, dCtxSh.idioma.getCodigo().getLiteral());
        step.replaceInExpected(tagLiteralIdioma, dCtxSh.idioma.getCodigo().getLiteral());
        
        PagePrehome.selecPaisIdiomaYAccede(dCtxSh, driver);

        //Validation
        PageHomeMarcasStpV.validateIsPageWithCorrectLineas(dCtxSh.pais, dCtxSh.channel, dCtxSh.appE, driver);
    }
}
