package com.mng.robotest.test80.mango.test.stpv.shop.modales;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
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
        DatosStep datosStep = TestCaseData.getDatosCurrentStep();
        datosStep.replaceInDescription(tagNombrePais, dCtxSh.pais.getNombre_pais());
        datosStep.replaceInExpected(tagNombrePais, dCtxSh.pais.getNombre_pais());
        datosStep.replaceInDescription(tagCodigoPais, dCtxSh.pais.getCodigo_pais());
        datosStep.replaceInDescription(tagLiteralIdioma, dCtxSh.idioma.getCodigo().getLiteral());
        datosStep.replaceInExpected(tagLiteralIdioma, dCtxSh.idioma.getCodigo().getLiteral());
        
        PagePrehome.selecPaisIdiomaYAccede(dCtxSh, driver);

        //Validation
        PageHomeMarcasStpV.validateIsPageWithCorrectLineas(dCtxSh.pais, dCtxSh.channel, dCtxSh.appE, driver);
    }
}
