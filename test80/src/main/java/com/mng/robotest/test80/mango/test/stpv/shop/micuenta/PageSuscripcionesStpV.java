package com.mng.robotest.test80.mango.test.stpv.shop.micuenta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.PageSuscripciones;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.PageSuscripciones.idNewsletters;

public class PageSuscripcionesStpV {

    @Validation(
        description="1) Aparece la página de \"Suscripciones\"",
        level=State.Warn)
    public static boolean validaIsPage (WebDriver driver) {
        return (PageSuscripciones.isPage(driver));
    }

    @Validation
    public static ChecksResult validaIsDataAssociatedToRegister (HashMap<String,String> datosRegOk, WebDriver driver) {
        int numLineasTotales = Integer.valueOf(datosRegOk.get("numlineas")).intValue();
        String lineasUnchecked = datosRegOk.get("clicklineas");
        StringTokenizer tokensLinDesmarcadas = new StringTokenizer(lineasUnchecked, ",");
        int numLinDesmarcadas = tokensLinDesmarcadas.countTokens();

        ChecksResult validations = ChecksResult.getNew();
        validations.add(
            "Aparecen "  + numLineasTotales + " Newsletter",
            PageSuscripciones.getNumNewsletters(driver)==numLineasTotales, State.Warn);
        validations.add(
            "Aparecen "  + numLinDesmarcadas + " suscripciones desmarcadas",
            PageSuscripciones.getNumNewslettersDesmarcadas(driver)==numLinDesmarcadas, State.Warn);
        while (tokensLinDesmarcadas.hasMoreElements()) {
            String lineaStr = tokensLinDesmarcadas.nextToken();
            validations.add(
                "Aparecen desmarcadas las suscripciones de: " + lineasUnchecked,
                PageSuscripciones.isNewsletterDesmarcada(lineaStr, driver), State.Warn);
        }
        return validations;
    }

    @Step(
        description = "Seleccionar los checkbox de las Newsletter <b>#{listNewsletters.toString()}</b> + Botón \"Guardar Cambios\"",
        expected = "parece la confirmación que los datos se han modificado")
    public static void selectNewslettersAndGuarda(ArrayList<idNewsletters> listNewsletters, DataFmwkTest dFTest) throws Exception {
        for (idNewsletters idNewsletter : listNewsletters) {
            PageSuscripciones.clickRadioNewsletter(dFTest.driver, idNewsletter);
        }

        PageSuscripciones.clickGuardarCambios(dFTest.driver);
        validateIsPageResult(5, dFTest.driver);
    }

    @Validation(
        description="1) Aparece una pantalla de resultado OK (la esperamos hasta #{maxSecondsToWait} segundos)",
        level=State.Defect)
    private static boolean validateIsPageResult (int maxSecondsToWait, WebDriver driver) {
        return (PageSuscripciones.isPageResOKUntil(maxSecondsToWait, driver));
    }
}
