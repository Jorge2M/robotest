package com.mng.robotest.test80.mango.test.stpv.shop.micuenta;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.mango.test.pageobject.shop.PageRecADomic;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.StdValidationFlags;

public class PageRecogidaDomicStpV {

    @Validation
    public static ChecksResult vaidaIsPageSinDevoluciones (WebDriver driver) throws Exception {
        ChecksResult validations = ChecksResult.getNew();
        validations.add(
        	"Aparece la página de Recogida a Domicilio<br>",
        	PageRecADomic.isPage(driver), State.Defect);
        validations.add(
        	"Aparece la tabla de devoluciones<br>",
        	PageRecADomic.isTableDevoluciones(driver), State.Defect);
        validations.add(
        	"No aparece ningún pedido",
             !PageRecADomic.hayPedidos(driver), State.Info);

        StdValidationFlags flagsVal = StdValidationFlags.newOne();
        flagsVal.validaSEO = true;
        flagsVal.validaJS = true;
        flagsVal.validaImgBroken = false;
        AllPagesStpV.validacionesEstandar(flagsVal, driver);

        return validations;
    }
}
