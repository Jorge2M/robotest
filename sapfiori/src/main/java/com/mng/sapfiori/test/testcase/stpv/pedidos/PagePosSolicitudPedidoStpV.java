package com.mng.sapfiori.test.testcase.stpv.pedidos;

import java.util.Arrays;
import java.util.List;

import com.mng.sapfiori.test.testcase.generic.stpv.modals.ModalSelectItemStpV;
import com.mng.sapfiori.test.testcase.generic.webobject.modals.ModalSelectMultiItem;
import com.mng.sapfiori.test.testcase.webobject.pedidos.PagePosSolicitudPedido;
import com.mng.sapfiori.test.testcase.webobject.pedidos.PagePosSolicitudPedido.InputPage;
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
		level=State.Defect)
	public boolean checkIsPage(int maxSeconds) throws Exception {
		return (pageObject.checkIsPage(maxSeconds));
	}
	
	@Step (
		description = "Seleccionar el icono asociado al input de <b>#{inputType}</b>",
		expected = "Aparece el modal para seleccionar el dato")
	public ModalSelectItemStpV clickIconInput(InputPage inputType) throws Exception {
		ModalSelectMultiItem modal = pageObject.getInputWithIcon(inputType).clickIconSetFilter();
		return (ModalSelectItemStpV.getNew(modal));
	}
	
	@Step (
		description = "Introducir el valor <b>#{valueToInput}</b> en el input <b>#{inputType}</b>")
	public void inputText(InputPage inputType, String valueToInput) {
		pageObject.getInput(inputType).sendKeys(valueToInput);
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
			pageObject.selectSection(inputData.inputPage.section);
			
			switch (inputData.typeDataInput) {
			case SendText:
				inputText(inputData.inputPage, inputData.textToInput);
				break;
			case SelectByValue:
				clickIconInput(inputData.inputPage)
					.clickEnterToShowInitialElements()
					.selectElementInTable(inputData.valueToSelectInTable);
				break;
			case SearchAndSelectValue:
        		clickIconInput(inputData.inputPage)
        			.searchAndSelectElement(inputData.valueToSearch, inputData.valueToSelectInTable);
				break;
			case SelectByPosition:
				clickIconInput(inputData.inputPage)
					.clickEnterToShowInitialElements()
					.selectElementsByPosition(Arrays.asList(inputData.posElementToSelectInTable));
				break;
			}

		}
	}
	
	public static class InputDataSolPedido {
		public enum TypeOfDataInput {
			SendText,
			SelectByValue,
			SearchAndSelectValue, 
			SelectByPosition}
		
		public final InputPage inputPage;
		public final TypeOfDataInput typeDataInput;
		public String textToInput = null;
		public String valueToSearch = null;
		public String valueToSelectInTable = null;
		public Integer posElementToSelectInTable = null;
		
		private InputDataSolPedido(InputPage inputPage, TypeOfDataInput typeDataInput) {
			this.typeDataInput = typeDataInput;
			this.inputPage = inputPage;
		}
		public static InputDataSolPedido getForSendText(InputPage inputPage, String textToInput) {
			InputDataSolPedido inputData = new InputDataSolPedido(inputPage, TypeOfDataInput.SendText);
			inputData.textToInput = textToInput;
			return inputData;
		}
		public static InputDataSolPedido getForSelectValue(InputPage inputPage, String valueToSelectInTable) {
			InputDataSolPedido inputData = new InputDataSolPedido(inputPage, TypeOfDataInput.SelectByValue);
			inputData.valueToSelectInTable = valueToSelectInTable;
			return inputData;
		}
		public static InputDataSolPedido getForSearchAndSelect(InputPage inputPage, String valueToSearch, String valueToSelect) {
			InputDataSolPedido inputData = new InputDataSolPedido(inputPage, TypeOfDataInput.SearchAndSelectValue);
			inputData.valueToSearch = valueToSearch;
			inputData.valueToSelectInTable = valueToSelect;
			return inputData;
		}
		public static InputDataSolPedido getForSearchAndSelect(InputPage inputPage, int posElementToSelectInTable) {
			InputDataSolPedido inputData = new InputDataSolPedido(inputPage, TypeOfDataInput.SelectByPosition);
			inputData.posElementToSelectInTable = posElementToSelectInTable;
			return inputData;
		}
	}
}
