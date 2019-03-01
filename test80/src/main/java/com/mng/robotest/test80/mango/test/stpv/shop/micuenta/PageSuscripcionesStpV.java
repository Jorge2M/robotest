package com.mng.robotest.test80.mango.test.stpv.shop.micuenta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.StepAspect;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum;
import com.mng.robotest.test80.mango.test.data.ChannelEnum;
import com.mng.robotest.test80.mango.test.pageobject.ElementPageFunctions;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.SecModalPersonalizacion;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.PageMiCuenta;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.PageSuscripciones;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.PageSuscripciones.idNewsletters;
import com.mng.robotest.test80.mango.test.stpv.shop.menus.SecMenusUserStpV;
import org.openqa.selenium.WebDriver;

public class PageSuscripcionesStpV {

    @Validation(
            description="1) Aparece la página de \"Suscripciones\"",
            level=State.Warn)
    public static boolean validaIsPage (WebDriver driver) {
        return (PageSuscripciones.isPage(driver));
    }

    @Validation
    public static ListResultValidation validaIsDataAssociatedToRegister (HashMap<String,String> datosRegOk, WebDriver driver) {
        int numLineasTotales = Integer.valueOf(datosRegOk.get("numlineas")).intValue();
        String lineasUnchecked = datosRegOk.get("clicklineas");
        StringTokenizer tokensLinDesmarcadas = new StringTokenizer(lineasUnchecked, ",");
        int numLinDesmarcadas = tokensLinDesmarcadas.countTokens();

        ListResultValidation validations = ListResultValidation.getNew();
        validations.add(
                "Aparecen "  + numLineasTotales + " Newsletter<br>",
                PageSuscripciones.getNumNewsletters(driver)==numLineasTotales, State.Warn);
        validations.add(
                "Aparecen "  + numLinDesmarcadas + " suscripciones desmarcadas<br>",
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
        for (idNewsletters idNewsletter : listNewsletters)
            PageSuscripciones.clickRadioNewsletter(dFTest.driver, idNewsletter);

        PageSuscripciones.clickGuardarCambios(dFTest.driver);

        //Validation
        validateIsPageResult(3, dFTest.driver);
    }

    @Validation(
            description="1) Aparece una pantalla de resultado OK (la esperamos hasta #{maxSecondsToWait} segundos)",
            level=State.Defect)
    private static boolean validateIsPageResult (int maxSecondsToWait, WebDriver driver) {
        return (PageSuscripciones.isPageResOKUntil(maxSecondsToWait, driver));
    }
}
