package com.mng.sapfiori.access.test.testcase.stpv.pedidos;

import java.util.Arrays;
import java.util.List;

import com.mng.sapfiori.access.test.testcase.generic.stpv.modals.ModalSelectItemStpV;
import com.mng.sapfiori.access.test.testcase.generic.webobject.inputs.withmodal.InputLabel;
import com.mng.sapfiori.access.test.testcase.generic.webobject.modals.ModalSelectMultiItem;
import com.mng.sapfiori.access.test.testcase.webobject.pedidos.InputFieldPedido;
import com.mng.sapfiori.access.test.testcase.webobject.pedidos.PagePosSolicitudPedido;
import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.conf.State;

public class PagePosSolicitudPedidoStpV {

	private final PagePosSolicitudPedido pageObject;

	private PagePosSolicitudPedidoStpV(PagePosSolicitudPedido pageObject) {
		this.pageObject = pageObject;
	}
	public static PagePosSolicitudPedidoStpV getNew(PagePosSolicitudPedido pageObject) {
		return new PagePosSolicitudPedidoStpV(pageObject);
	}
	
	@Validation (
		description=
			"Aparece la página con título <b>" + PagePosSolicitudPedido.TitlePage + "</b> " + 
			"(la esperamos hasta #{maxSeconds} segundos)",
		level=State.Warn)
	public boolean checkIsPage(int maxSeconds) throws Exception {
		return (pageObject.checkIsPage(maxSeconds));
	}
	
	@Step (
		description = "Seleccionar el icono asociado al input de <b>#{inputType}</b>",
		expected = "Aparece el modal para seleccionar el dato")
	public ModalSelectItemStpV clickIconInput(InputFieldPedido inputType) throws Exception {
		ModalSelectMultiItem modal = pageObject.getInputWithIcon(inputType).clickIconSetFilter();
		return (ModalSelectItemStpV.getNew(modal));
	}
	
	@Step (
		description = "Introducir el valor <b>#{valueToInput}</b> en el input <b>#{inputType}</b>")
	public void inputText(InputFieldPedido inputType, String valueToInput) throws Exception {
		InputLabel input = pageObject.getInput(inputType);
		input.clearAndSendText(valueToInput);
	}
	
	@Step (
		description = "Seleccionamos el botón <b>Aplicar</b>",
		expected = "Aparece la página de \"Solicitud de pedido\"")
	public PageSolicitudPedidoStpV clickAplicar() throws Exception {
		PageSolicitudPedidoStpV pageSolPedidoStpV = PageSolicitudPedidoStpV.getNew(
			pageObject.clickAplicar());
		pageSolPedidoStpV.checkIsPage(5);
		return pageSolPedidoStpV;
	}
	
	public void inputData(List<InputDataSolPedido> listInputData) throws Exception {
		for (InputDataSolPedido inputData : listInputData) {
			pageObject.selectSection(inputData.getInputPage().section);
			
			switch (inputData.getTypeDataInput()) {
			case SendText:
				inputText(inputData.getInputPage(), inputData.getTextToInput());
				break;
			case SelectByValue:
				clickIconInput(inputData.getInputPage())
					.clickEnterToShowInitialElements()
					.selectElementInTable(inputData.getValueToSelectInTable());
				break;
			case SearchAndSelectValue:
        		clickIconInput(inputData.getInputPage())
        			.searchAndSelectElement(inputData.getValueToSearch(), inputData.getValueToSelectInTable());
				break;
			case SelectByPosition:
				clickIconInput(inputData.getInputPage())
					.clickEnterToShowInitialElements()
					.selectElementsByPosition(Arrays.asList(inputData.getPosElementToSelectInTable()));
				break;
			}

		}
	}
}
