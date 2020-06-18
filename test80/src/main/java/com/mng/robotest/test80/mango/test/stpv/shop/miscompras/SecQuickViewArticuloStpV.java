package com.mng.robotest.test80.mango.test.stpv.shop.miscompras;

import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.conf.State;
import com.mng.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test80.mango.test.pageobject.shop.miscompras.SecQuickViewArticulo;

public class SecQuickViewArticuloStpV {
    
	private final SecQuickViewArticulo secQuickViewArticulo;
	
	private SecQuickViewArticuloStpV(SecQuickViewArticulo sectionObject) {
		this.secQuickViewArticulo = sectionObject;
	}
	public static SecQuickViewArticuloStpV getNew(SecQuickViewArticulo sectionObject) {
		return new SecQuickViewArticuloStpV(sectionObject);
	}
	
    @SuppressWarnings("static-access")
    @Validation
    public ChecksTM validateIsOk(ArticuloScreen articulo) {
        ChecksTM validations = ChecksTM.getNew();
        int maxSecondsWait = 2;
        validations.add(
        	"Aparece el quickview correspondiente al artículo (la esperamos hasta " + maxSecondsWait + " segundos)",
        	secQuickViewArticulo.isVisibleUntil(maxSecondsWait), State.Warn);
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
