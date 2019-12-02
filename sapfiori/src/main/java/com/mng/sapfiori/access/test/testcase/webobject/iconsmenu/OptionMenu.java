package com.mng.sapfiori.access.test.testcase.webobject.iconsmenu;

import java.util.List;
import java.util.Arrays;

public enum OptionMenu {

	ClasificarProductos (Arrays.asList("Reclasificar productos"), "Reclasificación de productos - Números estadísticos de mercancías"),
	ManagePRsBuyer (Arrays.asList("Manage Purchase Requisitions", "Buyer"), "Gestión de solicitudes de pedido: profesional");
	
	private final List<String> textsInIcon;
	private final String titlePage;
	private OptionMenu(List<String> textsInIcon, String titlePage) {
		this.textsInIcon = textsInIcon;
		this.titlePage = titlePage;
	}
	
	public List<String> getTextsInIcon() {
		return textsInIcon;
	}
	public String getTitlePage() {
		return titlePage;
	}
}
