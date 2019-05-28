package com.mng.robotest.test80.mango.test.stpv.shop.micuenta;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.CompraTienda;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.PageMisCompras;

public class SecDetalleCompraTiendaStpV {
	
	public static void validateIsOk(CompraTienda compraTienda, Channel channel, WebDriver driver) {
		checkData(compraTienda, channel, driver);
		if (channel==Channel.movil_web) {
			checkIsVisibleImgCodigoBarrasMovil(driver);
		}
	}
	
    @SuppressWarnings("static-access")
    @Validation
    private static ChecksResult checkData(CompraTienda compraTienda, Channel channel, WebDriver driver) {
        ChecksResult validations = ChecksResult.getNew();
        int maxSecondsWait = 1;
        validations.add(
        	"Es visible la capa correspondiente al detalle del tícket de compra (la esperamos hasta " + maxSecondsWait + " segundos)",
        	PageMisCompras.SecDetalleCompraTienda.isVisibleSectionUntil(maxSecondsWait, driver), State.Warn);
        validations.add(
        	"Figura un número de tícket " + compraTienda.idCompra,
        	PageMisCompras.SecDetalleCompraTienda.getNumTicket(driver).compareTo(compraTienda.idCompra)==0, State.Warn);
        validations.add(
        	"Figura el importe " + compraTienda.importe,
        	PageMisCompras.SecDetalleCompraTienda.getImporte(driver).compareTo(compraTienda.importe)==0, State.Warn);
        validations.add(
        	"Figura la dirección " + compraTienda.direccion,
        	PageMisCompras.SecDetalleCompraTienda.getDireccion(driver).compareTo(compraTienda.direccion)==0, State.Warn);
        validations.add(
        	"Figura la fecha " + compraTienda.fecha,
        	PageMisCompras.SecDetalleCompraTienda.getFecha(channel, driver).compareTo(compraTienda.fecha)==0, State.Warn);
        validations.add(
        	"Existen " + compraTienda.numPrendas + " prendas",
        	PageMisCompras.SecDetalleCompraTienda.getNumPrendas(driver)==compraTienda.numPrendas, State.Warn);
        return validations;
    }
    
    @SuppressWarnings("static-access")
    @Validation (
    	description="Aparece la imagen correspondiente al código de barras de la compra",
    	level=State.Warn)
    private static boolean checkIsVisibleImgCodigoBarrasMovil(WebDriver driver) {
        return (PageMisCompras.SecDetalleCompraTienda.isVisibleCodigoBarrasImg(driver));
    }
    
    @SuppressWarnings("static-access")
    @Step (
    	description="Seleccionar el #{posArticulo}o artículo de la Compra", 
        expected="Aparece la sección correspondiente al \"QuickView\" del artículo")
    public static void selectArticulo(int posArticulo, WebDriver driver) {
        ArticuloScreen articulo = PageMisCompras.SecDetalleCompraTienda.getDataArticulo(posArticulo, driver);
        PageMisCompras.SecDetalleCompraTienda.selectArticulo(posArticulo, driver);
        
        //Validaciones
        PageMisComprasStpV.SecQuickViewArticulo.validateIsOk(articulo, driver);
    }
}
