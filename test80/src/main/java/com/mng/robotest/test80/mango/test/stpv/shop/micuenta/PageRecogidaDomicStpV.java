package com.mng.robotest.test80.mango.test.stpv.shop.micuenta;

import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.pageobject.shop.PageRecADomic;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.PageMisDatos;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;
import org.openqa.selenium.WebDriver;


public class PageRecogidaDomicStpV {

    @Validation
    public static ListResultValidation vaidaIsPageSinDevoluciones (WebDriver driver) throws Exception {
        ListResultValidation validations = ListResultValidation.getNew();
        validations.add(
                "Aparece la página de Recogida a Domicilio<br>",
                PageRecADomic.isPage(driver), State.Defect);
        validations.add(
                "Aparece la tabla de devoluciones<br>",
                PageRecADomic.isTableDevoluciones(driver), State.Defect);
        validations.add(
                "No aparece ningún pedido",
                !PageRecADomic.hayPedidos(driver), State.Info);

        AllPagesStpV.validacionesEstandar(true/*validaSEO*/, true/*validaJS*/, false/*validaImgBroken*/);

        return validations;
    }
}
