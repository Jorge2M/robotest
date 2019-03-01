package com.mng.robotest.test80.mango.test.stpv.shop.micuenta;

import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.mango.test.pageobject.shop.PageMispedidos;
import org.openqa.selenium.WebDriver;

public class PagePedidosStpV {

    @Validation
    public static ListResultValidation validaIsPageSinPedidos (String usrRegistrado, WebDriver driver) {
        ListResultValidation validations = ListResultValidation.getNew();
        validations.add(
            "Aparece la página de \"Mis Pedidos\"<br>",
            PageMispedidos.isPage(driver), State.Defect);
        validations.add(
            "2) La página contiene " + usrRegistrado + "<br>",
            PageMispedidos.elementContainsText(driver, usrRegistrado), State.Warn);
        validations.add(
            "La lista de pedidos está vacía",
            PageMispedidos.listaPedidosVacia(driver), State.Warn);
        return validations;
    }
}
