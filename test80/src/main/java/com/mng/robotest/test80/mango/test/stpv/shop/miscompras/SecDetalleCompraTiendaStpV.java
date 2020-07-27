package com.mng.robotest.test80.mango.test.stpv.shop.miscompras;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.CompraTienda;
import com.mng.robotest.test80.mango.test.pageobject.shop.miscompras.SecDetalleCompraTiendaDesktop;

public class SecDetalleCompraTiendaStpV {
	
	private final Channel channel;
	private final SecDetalleCompraTiendaDesktop secDetalleCompraTienda;
	
	private SecDetalleCompraTiendaStpV(SecDetalleCompraTiendaDesktop section, Channel channel) {
		this.secDetalleCompraTienda = section;
		this.channel = channel;
	}
	public static SecDetalleCompraTiendaStpV getNew(SecDetalleCompraTiendaDesktop section, Channel channel) {
		return new SecDetalleCompraTiendaStpV(section, channel);
	}
	
	public void validateIsOk(CompraTienda compraTienda) {
		checkData(compraTienda);
		if (channel==Channel.mobile) {
			checkIsVisibleImgCodigoBarrasMovil();
		}
	}
	
    @SuppressWarnings("static-access")
    @Validation
    private ChecksTM checkData(CompraTienda compraTienda) {
        ChecksTM validations = ChecksTM.getNew();
        int maxSeconds = 1;
        validations.add(
        	"Es visible la capa correspondiente al detalle del tícket de compra (la esperamos hasta " + maxSeconds + " segundos)",
        	secDetalleCompraTienda.isVisibleSectionUntil(maxSeconds), State.Warn);
        validations.add(
        	"Figura un número de tícket " + compraTienda.idCompra,
        	secDetalleCompraTienda.getNumTicket().compareTo(compraTienda.idCompra)==0, State.Warn);
        validations.add(
        	"Figura el importe " + compraTienda.importe,
        	secDetalleCompraTienda.getImporte().compareTo(compraTienda.importe)==0, State.Warn);
        validations.add(
        	"Figura la dirección " + compraTienda.direccion,
        	secDetalleCompraTienda.getDireccion().compareTo(compraTienda.direccion)==0, State.Warn);
        validations.add(
        	"Existen " + compraTienda.numPrendas + " prendas",
        	secDetalleCompraTienda.getNumPrendas()==compraTienda.numPrendas, State.Warn);
        return validations;
    }
    
    @SuppressWarnings("static-access")
    @Validation (
    	description="Aparece la imagen correspondiente al código de barras de la compra",
    	level=State.Warn)
    private boolean checkIsVisibleImgCodigoBarrasMovil() {
        return (secDetalleCompraTienda.isVisibleCodigoBarrasImg());
    }
    
    @SuppressWarnings("static-access")
    @Step (
    	description="Seleccionar el #{posArticulo}o artículo de la Compra", 
        expected="Aparece la sección correspondiente al \"QuickView\" del artículo")
    public void selectArticulo(int posArticulo, PageMisComprasStpV pageMisComprasStpV) {
        ArticuloScreen articulo = secDetalleCompraTienda.getDataArticulo(posArticulo, AppEcom.shop);
        secDetalleCompraTienda.selectArticulo(posArticulo);
        pageMisComprasStpV.getSecQuickViewArticulo().validateIsOk(articulo);
    }
}
