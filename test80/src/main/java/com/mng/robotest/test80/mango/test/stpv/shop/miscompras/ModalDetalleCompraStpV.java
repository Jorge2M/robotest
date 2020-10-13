package com.mng.robotest.test80.mango.test.stpv.shop.miscompras;

import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.Ticket;
import com.mng.robotest.test80.mango.test.pageobject.shop.miscompras.PageDetalleCompra;

public class ModalDetalleCompraStpV {
	
	private final PageDetalleCompra modalDetalleCompra;
    private final ModalDetalleArticuloStpV modalDetalleArticuloStpV;
	
	private ModalDetalleCompraStpV(PageDetalleCompra section) {
		this.modalDetalleCompra = section;
		this.modalDetalleArticuloStpV = ModalDetalleArticuloStpV.getNew(modalDetalleCompra.getModalDetalleArticulo());
		
	}
	public static ModalDetalleCompraStpV getNew(PageDetalleCompra section) {
		return new ModalDetalleCompraStpV(section);
	}
	public ModalDetalleArticuloStpV getModalDetalleArticulo() {
		return modalDetalleArticuloStpV;
	}
	
	public void validateIsOk(Ticket compraTienda) {
		ChecksTM checks = checkIsDataVisible();
		if (!checks.calculateStateValidation().isMoreCriticThan(State.Warn)) {
			checkDataContent(compraTienda);
		}
	}
	
    @SuppressWarnings("static-access")
    @Validation
    private ChecksTM checkIsDataVisible() {
        ChecksTM validations = ChecksTM.getNew();
        int maxSeconds = 1;
    	validations.add(
        	"Es visible la capa correspondiente al detalle del tícket de compra (la esperamos hasta " + maxSeconds + " segundos)",
        	modalDetalleCompra.isVisibleDataTicket(maxSeconds), State.Defect);
        maxSeconds = 2;
        validations.add(
        	"Son visibles los datos del tícket (los esperamos hasta " + maxSeconds + " segundos)",
        	modalDetalleCompra.isVisibleDataTicket(maxSeconds), State.Defect);
        validations.add(
        	"Figura un id de tícket (lo esperamos hasta " + maxSeconds + " segundos)",
        	modalDetalleCompra.isVisibleIdTicket(maxSeconds), State.Defect);
        validations.add(
        	"Figura alguna prenda (la esperamos hasta " + maxSeconds + " segundos)",
        	modalDetalleCompra.isVisiblePrendaUntil(maxSeconds), State.Warn);
        return validations;
    }
	
    @SuppressWarnings("static-access")
    @Validation
    private ChecksTM checkDataContent(Ticket compra) {
        ChecksTM validations = ChecksTM.getNew();
        validations.add(
        	"Figura un id de tícket " + compra.getId(),
        	modalDetalleCompra.getIdTicket(compra.getType()).compareTo(compra.getId())==0, State.Warn);
        validations.add(
        	"Figura el importe " + compra.getPrecio(),
        	modalDetalleCompra.getImporte().compareTo(compra.getPrecio())==0, State.Warn);
        validations.add(
        	"Existen " + compra.getNumItems() + " prendas",
        	modalDetalleCompra.getNumPrendas()==compra.getNumItems(), State.Warn);
        return validations;
    }
    
//    @SuppressWarnings("static-access")
//    @Validation (
//    	description="Aparece la imagen correspondiente al código de barras de la compra",
//    	level=State.Warn)
//    private boolean checkIsVisibleImgCodigoBarrasMovil() {
//        return (modalDetalleCompra.isVisibleCodigoBarrasImg());
//    }
    
    @SuppressWarnings("static-access")
    @Step (
    	description="Seleccionar el #{posArticulo}o artículo de la Compra", 
        expected="Aparece la sección correspondiente al \"QuickView\" del artículo")
    public void selectArticulo(int posArticulo) {
        ArticuloScreen articulo = modalDetalleCompra.getDataArticulo(posArticulo);
        modalDetalleCompra.selectArticulo(posArticulo);
        modalDetalleArticuloStpV.validateIsOk(articulo);
    }
    
	@Step (
		description="Clickamos el link para volver a la lista de \"Mis compras\"",
		expected="Volvemos a la página de Mis Compras")
	public void gotoListaMisCompras() {
		modalDetalleCompra.gotoListaMisCompras();
	}
}
