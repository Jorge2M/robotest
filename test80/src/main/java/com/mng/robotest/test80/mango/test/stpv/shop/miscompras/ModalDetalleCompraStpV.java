package com.mng.robotest.test80.mango.test.stpv.shop.miscompras;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.Ticket;
import com.mng.robotest.test80.mango.test.pageobject.shop.miscompras.ModalDetalleCompra;

public class ModalDetalleCompraStpV {
	
	private final ModalDetalleCompra modalDetalleCompra;
    private final ModalDetalleArticuloStpV modalDetalleArticuloStpV;
	private final Channel channel;
	
	private ModalDetalleCompraStpV(ModalDetalleCompra section, Channel channel, WebDriver driver) {
		this.modalDetalleCompra = section;
		this.modalDetalleArticuloStpV = ModalDetalleArticuloStpV.getNew(modalDetalleCompra.getModalDetalleArticulo(), driver);
		this.channel = channel;
		
	}
	public static ModalDetalleCompraStpV getNew(ModalDetalleCompra section, Channel channel, WebDriver driver) {
		return new ModalDetalleCompraStpV(section, channel, driver);
	}
	public ModalDetalleArticuloStpV getModalDetalleArticulo() {
		return modalDetalleArticuloStpV;
	}
	
	public void validateIsOk(Ticket compraTienda) {
		checkData(compraTienda);
		if (channel==Channel.mobile) {
			checkIsVisibleImgCodigoBarrasMovil();
		}
	}
	
    @SuppressWarnings("static-access")
    @Validation
    private ChecksTM checkData(Ticket compra) {
        ChecksTM validations = ChecksTM.getNew();
        int maxSeconds = 1;
        validations.add(
        	"Es visible la capa correspondiente al detalle del tícket de compra (la esperamos hasta " + maxSeconds + " segundos)",
        	modalDetalleCompra.isVisibleSectionUntil(maxSeconds), State.Warn);
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
    
    @SuppressWarnings("static-access")
    @Validation (
    	description="Aparece la imagen correspondiente al código de barras de la compra",
    	level=State.Warn)
    private boolean checkIsVisibleImgCodigoBarrasMovil() {
        return (modalDetalleCompra.isVisibleCodigoBarrasImg());
    }
    
    @SuppressWarnings("static-access")
    @Step (
    	description="Seleccionar el #{posArticulo}o artículo de la Compra", 
        expected="Aparece la sección correspondiente al \"QuickView\" del artículo")
    public void selectArticulo(int posArticulo) {
        ArticuloScreen articulo = modalDetalleCompra.getDataArticulo(posArticulo, AppEcom.shop);
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
