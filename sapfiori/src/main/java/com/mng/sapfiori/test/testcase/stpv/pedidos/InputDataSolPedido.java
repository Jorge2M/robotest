package com.mng.sapfiori.test.testcase.stpv.pedidos;

import com.mng.sapfiori.test.testcase.webobject.pedidos.InputFieldPedido;

public class InputDataSolPedido {
	public enum TypeOfDataInput {
		SendText,
		SelectByValue,
		SearchAndSelectValue,
		SelectByPosition;
	}

	private final InputFieldPedido inputPage;
	private final TypeOfDataInput typeDataInput;
	private String textToInput = null;
	private String valueToSearch = null;
	private String valueToSelectInTable = null;
	private Integer posElementToSelectInTable = null;
	
	public InputFieldPedido getInputPage() {
		return inputPage;
	}
	public TypeOfDataInput getTypeDataInput() {
		return typeDataInput;
	}
	public String getTextToInput() {
		return textToInput;
	}
	public String getValueToSearch() {
		return valueToSearch;
	}
	public String getValueToSelectInTable() {
		return valueToSelectInTable;
	}
	public Integer getPosElementToSelectInTable() {
		return posElementToSelectInTable;
	}
	
	private InputDataSolPedido(InputFieldPedido inputPage, TypeOfDataInput typeDataInput) {
		this.typeDataInput = typeDataInput;
		this.inputPage = inputPage;
	}
	public static InputDataSolPedido getForSendText(InputFieldPedido inputPage, String textToInput) {
		InputDataSolPedido inputData = new InputDataSolPedido(inputPage, TypeOfDataInput.SendText);
		inputData.textToInput = textToInput;
		return inputData;
	}
	public static InputDataSolPedido getForSelectValue(InputFieldPedido inputPage, String valueToSelectInTable) {
		InputDataSolPedido inputData = new InputDataSolPedido(inputPage, TypeOfDataInput.SelectByValue);
		inputData.valueToSelectInTable = valueToSelectInTable;
		inputData.textToInput = valueToSelectInTable;
		return inputData;
	}
	public static InputDataSolPedido getForSearchAndSelect(InputFieldPedido inputPage, String valueToSearch, String valueToSelect) {
		InputDataSolPedido inputData = new InputDataSolPedido(inputPage, TypeOfDataInput.SearchAndSelectValue);
		inputData.valueToSearch = valueToSearch;
		inputData.valueToSelectInTable = valueToSelect;
		return inputData;
	}
	public static InputDataSolPedido getForSearchAndSelect(InputFieldPedido inputPage, int posElementToSelectInTable) {
		InputDataSolPedido inputData = new InputDataSolPedido(inputPage, TypeOfDataInput.SelectByPosition);
		inputData.posElementToSelectInTable = posElementToSelectInTable;
		return inputData;
	}
}
