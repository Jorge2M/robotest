package com.mng.sapfiori.test.testcase.stpv.pedidos;

import com.mng.sapfiori.test.testcase.generic.webobject.modals.ModalMessages;
import com.mng.sapfiori.test.testcase.webobject.pedidos.InfoGeneralSolPedido;
import com.mng.sapfiori.test.testcase.webobject.pedidos.InputFieldPedido;
import com.mng.sapfiori.test.testcase.webobject.pedidos.PageSolicitudPedido;
import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.conf.State;

public class PageSolicitudPedidoStpV {

	private final PageSolicitudPedido pageObject;

	
	private PageSolicitudPedidoStpV(PageSolicitudPedido pageObject) {
		this.pageObject = pageObject;
	}
	public static PageSolicitudPedidoStpV getNew(PageSolicitudPedido pageObject) {
		return new PageSolicitudPedidoStpV(pageObject);
	}
	
	@Validation (
		description=
			"Aparece la página con título <b>" + PageSolicitudPedido.TitlePage + "</b> " + 
			"(la esperamos hasta #{maxSeconds} segundos)",
		level=State.Defect)
	public boolean checkIsPage(int maxSeconds) {
		return pageObject.checkIsPage(maxSeconds);
	}
	
	@Step (
		description="Introducimos la información general del pedido:<br>#{infoGeneral}")
	public void inputInfoGeneral(InfoGeneralSolPedido infoGeneral) {
		pageObject.inputInfoGeneral(infoGeneral);
	}
	
	@Step (
		description="Selecciona el icono de + para añadir articulos y seleccionar <b>#{concepto}</b>",
		expected="Aparece la página de \"Posición de solicitud de pedido\"")
	public PagePosSolicitudPedidoStpV añadirArticulo(String concepto) throws Exception {
		PagePosSolicitudPedidoStpV pagePosSolPedidoStpV = 
			PagePosSolicitudPedidoStpV.getNew(
				pageObject.añadirArticulo(concepto));
				
		pagePosSolPedidoStpV.checkIsPage(5);
		return pagePosSolPedidoStpV;
	}
	
	@Validation (
		description="En el 1er pedido aparece el input #{inputData.getInputPage()} con valor #{inputData.getTextToInput()}",
		level=State.Defect)
	public boolean checkFieldIn1rstLineaPedidos(InputDataSolPedido inputData) throws Exception {
		return (
			pageObject.checkFieldIn1rstLineaPedidos(inputData.getInputPage(), inputData.getTextToInput()));
	}
	
	@Step (
		description="Introducimos el valor #{value} en el input #{inputField}")
	public void inputFielValuedIn1rstLinePedidos(InputFieldPedido inputField, String value) throws Exception {
		pageObject.inputFielValuedIn1rstLinePedidos(inputField, value);
	}
	
	@Step (
		description="Seleccionamos el botón <b>Guardar</b>",
		expected="No aparece el modal de errores")
	public void clickButtonGuardar() throws Exception {
		pageObject.clickButtonGuardar();
		checkModalErroresNotAppears();
	}
	
	@Validation (
		description="No es visible el modal de mensajes",
		level=State.Defect)
	public boolean checkModalErroresNotAppears() {
		ModalMessages modalErrores = pageObject.getElementsMaker().getModalErrores();
		return (!modalErrores.isVisible());
	}
}
