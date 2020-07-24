package com.mng.robotest.test80.mango.test.stpv.shop.miscompras;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test80.mango.test.pageobject.shop.miscompras.SecQuickViewArticuloShop;

public class SecQuickViewArticuloStpV {
    
	private final SecQuickViewArticuloShop secQuickViewArticulo;
	
	private SecQuickViewArticuloStpV(SecQuickViewArticuloShop sectionObject) {
		this.secQuickViewArticulo = sectionObject;
	}
	public static SecQuickViewArticuloStpV getNew(SecQuickViewArticuloShop sectionObject) {
		return new SecQuickViewArticuloStpV(sectionObject);
	}
	
    @SuppressWarnings("static-access")
    @Validation
    public ChecksTM validateIsOk(ArticuloScreen articulo) {
        ChecksTM validations = ChecksTM.getNew();
        int maxSeconds = 2;
        validations.add(
        	"Aparece el quickview correspondiente al artículo (la esperamos hasta " + maxSeconds + " segundos)",
        	secQuickViewArticulo.isVisibleUntil(maxSeconds), State.Warn);
        validations.add(
        	"Se muestra la referencia " + articulo.getReferencia(),
        	secQuickViewArticulo.getReferencia().compareTo(articulo.getReferencia())==0, State.Warn);
        validations.add(
        	"Se muestra el nombre " + articulo.getNombre(),
        	secQuickViewArticulo.getNombre().compareTo(articulo.getNombre())==0, State.Warn);
        validations.add(
        	"Se muestra el precio " + articulo.getPrecio(),
        	secQuickViewArticulo.getPrecio().contains(articulo.getPrecio()), State.Warn);
        return validations;
    }
}
