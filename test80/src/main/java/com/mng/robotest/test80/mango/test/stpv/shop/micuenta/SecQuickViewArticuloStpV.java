package com.mng.robotest.test80.mango.test.stpv.shop.micuenta;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.PageMisCompras;

public class SecQuickViewArticuloStpV {
    
    @SuppressWarnings("static-access")
    @Validation
    public static ListResultValidation validateIsOk(ArticuloScreen articulo, WebDriver driver) {
        ListResultValidation validations = ListResultValidation.getNew();
        int maxSecondsWait = 2;
        validations.add(
        	"Aparece el quickview correspondiente al artículo (la esperamos hasta " + maxSecondsWait + " segundos)<br>",
        	PageMisCompras.SecQuickViewArticulo.isVisibleUntil(maxSecondsWait, driver), State.Warn);
        validations.add(
        	"Se muestra la referencia " + articulo.getReferencia() + "<br>",
        	PageMisCompras.SecQuickViewArticulo.getReferencia(driver).compareTo(articulo.getReferencia())==0, State.Warn);
        validations.add(
        	"Se muestra el nombre " + articulo.getNombre() + "<br>",
        	PageMisCompras.SecQuickViewArticulo.getNombre(driver).compareTo(articulo.getNombre())==0, State.Warn);
        validations.add(
        	"Se muestra el precio " + articulo.getPrecio(),
        	PageMisCompras.SecQuickViewArticulo.getPrecio(driver).contains(articulo.getPrecio()), State.Warn);
        return validations;
    }
}
