package com.mng.robotest.test80.mango.test.stpv.shop.micuenta;

import com.mng.testmaker.utils.State;
import com.mng.testmaker.utils.otras.Channel;
import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.ChecksResult;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.CompraTienda;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.SecDetalleCompraTienda;

public class SecDetalleCompraTiendaStpV {
	
	private final Channel channel;
	private final SecDetalleCompraTienda secDetalleCompraTienda;
	
	private SecDetalleCompraTiendaStpV(SecDetalleCompraTienda section, Channel channel) {
		this.secDetalleCompraTienda = section;
		this.channel = channel;
	}
	public static SecDetalleCompraTiendaStpV getNew(SecDetalleCompraTienda section, Channel channel) {
		return new SecDetalleCompraTiendaStpV(section, channel);
	}
	
	public void validateIsOk(CompraTienda compraTienda) {
		checkData(compraTienda);
		if (channel==Channel.movil_web) {
			checkIsVisibleImgCodigoBarrasMovil();
		}
	}
	
    @SuppressWarnings("static-access")
    @Validation
    private ChecksResult checkData(CompraTienda compraTienda) {
        ChecksResult validations = ChecksResult.getNew();
        int maxSecondsWait = 1;
        validations.add(
        	"Es visible la capa correspondiente al detalle del tícket de compra (la esperamos hasta " + maxSecondsWait + " segundos)",
        	secDetalleCompraTienda.isVisibleSectionUntil(maxSecondsWait), State.Warn);
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
        ArticuloScreen articulo = secDetalleCompraTienda.getDataArticulo(posArticulo);
        secDetalleCompraTienda.selectArticulo(posArticulo);
        pageMisComprasStpV.getSecQuickViewArticulo().validateIsOk(articulo);
    }
}
