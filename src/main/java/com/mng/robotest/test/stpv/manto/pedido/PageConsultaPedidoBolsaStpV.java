package com.mng.robotest.test.stpv.manto.pedido;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.Pago;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.pageobject.manto.pedido.PageDetallePedido;
import com.mng.robotest.test.pageobject.manto.pedido.PagePedidos;
import com.mng.robotest.test.pageobject.manto.pedido.PageDetallePedido.RightButtons;
import com.mng.robotest.test.pageobject.manto.pedido.PagePedidos.TypeDetalle;
import com.mng.robotest.test.pageobject.shop.checkout.envio.TipoTransporteEnum.TipoTransporte;
import com.mng.robotest.test.utils.ImporteScreen;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;

/**
 * Clase que implementa los diferentes steps/validations asociados asociados a la página de Pedidos/Bolsas en Manto
 * @author jorge.munoz
 *
 */

public class PageConsultaPedidoBolsaStpV {

	/**
	 * Se accede al detalle de un pedido desde la lista de pedidos o bolsas
	 */
	@Step (
		description="Seleccionamos el código de pedido para acceder al Detalle", 
		expected="Aparece la página de detalle de #{typeDetalle} correcta",
		saveImagePage=SaveWhen.Always,
		saveErrorData=SaveWhen.Never)
	public static void detalleFromListaPedBol(DataPedido dataPedido, TypeDetalle typeDetalle, AppEcom appE, WebDriver driver) 
	throws Exception {
		PagePedidos.clickLinkPedidoInLineas(driver, dataPedido.getCodigoPedidoManto(), typeDetalle);
									
		//Validaciones
		validacionesTotalesPedido(dataPedido, typeDetalle, appE, driver);
	}
	
	public static void validacionesTotalesPedido(DataPedido dataPedido, TypeDetalle typeDetalle, AppEcom appE, WebDriver driver) {
		validaDatosGeneralesPedido(dataPedido, appE, driver);
		validaDatosEnvioPedido(dataPedido, typeDetalle, appE, driver);
	}
	
	@Validation
	public static ChecksTM validaDatosEnvioPedido(DataPedido dataPedido, TypeDetalle typeDetalle, AppEcom appE, WebDriver driver) {
		ChecksTM validations = ChecksTM.getNew();
		TipoTransporte tipoTransporte = dataPedido.getPago().getTipoEnvioType(appE);
		validations.add(
			"El campo \"tipo servicio\" contiene el valor <b>" + tipoTransporte.getCodigoIntercambio() + "</b> (asociado al tipo de envío " + tipoTransporte + ")",
			PageDetallePedido.getTipoServicio(driver).compareTo(tipoTransporte.getCodigoIntercambio())==0, State.Info);		
		
		if (typeDetalle==TypeDetalle.pedido && 
			dataPedido.getTypeEnvio()==TipoTransporte.TIENDA && 
			dataPedido.getDataDeliveryPoint()!=null) {
			String textEnvioTienda = dataPedido.getDataDeliveryPoint().getCodigo();
			validations.add(
				"En los datos de envío aparece el texto <b>ENVIO A TIENDA " + textEnvioTienda + "</b>",
				PageDetallePedido.get1rstLineDatosEnvioText(driver).contains(textEnvioTienda), State.Defect);			  
		}
		
		return validations;
	}
	
	@Validation
	public static ChecksTM validaDatosGeneralesPedido(DataPedido dataPedido, AppEcom appE, WebDriver driver) {
		ChecksTM validations = ChecksTM.getNew();
		validations.add(
			"Aparece la pantalla de detalle del pedido",
			PageDetallePedido.isPage(driver), State.Warn);
		validations.add(
			"Aparece un TOTAL de: " + dataPedido.getImporteTotalManto(),
			ImporteScreen.isPresentImporteInElements(dataPedido.getImporteTotalManto(), dataPedido.getCodigoPais(), PageDetallePedido.XPathImporteTotal, driver), State.Warn);
		validations.add(
			"Las 3 líneas de la dirección de envío figuran en la dirección del pedido (" + dataPedido.getDireccionEnvio() +")",
			PageDetallePedido.isDireccionPedido(driver, dataPedido.getDireccionEnvio()), State.Warn);
		validations.add(
			"Figura el código de país (" + dataPedido.getCodigoPais() + ")",
			PageDetallePedido.isCodPaisPedido(driver, dataPedido.getCodigoPais()), State.Warn);
		
		Pago pago = dataPedido.getPago();
		if (pago.getTpv().getEstado()!=null &&
			pago.getTpv().getEstado().compareTo("")!=0 &&
			appE!=AppEcom.votf) {
			boolean isPedidoInStateTpv = PageDetallePedido.isStateInTpvStates(driver, dataPedido);
			boolean pedidoInStateMenos1Null = PageDetallePedido.isPedidoInStateMenos1NULL(driver);
			validations.add(
				"Aparece uno de los resultados posibles según el TPV: " + pago.getTpv().getEstado(),
				isPedidoInStateTpv, State.Warn);
			if (!isPedidoInStateTpv) {
				validations.add(
					"El pedido no está en estado -1-NULL",
					!pedidoInStateMenos1Null, State.Defect);
			}
		}  
		
		return validations;
	}
	
	@Step (
		description="Seleccionamos el botón \"Ir A Generar\"", 
		expected="Aparece la página de generación del pedido",
		saveImagePage=SaveWhen.Always,
		saveErrorData=SaveWhen.Never)
	public static void clickButtonIrAGenerar(String idPedido, WebDriver driver) {
		click(RightButtons.IrAGenerar.getBy(), driver).exec();
		PageGenerarPedidoStpV.validateIsPage(idPedido, driver);
	}
}
