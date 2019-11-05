package com.mng.sapfiori.test.testcase.webobject.iconsmenu;

public enum OptionMenu {

	ClasificarProductos ("Reclasificar productos", "Reclasificación de productos - Números estadísticos de mercancías"),
	ManagePRsBuyer ("Manage Purchase Requisitions", "Gestión de solicitudes de pedido: profesional");
	
	private final String titleIcon;
	private final String titlePage;
	private OptionMenu(String titleIcon, String titlePage) {
		this.titleIcon = titleIcon;
		this.titlePage = titlePage;
	}
	
	public String getTitleIcon() {
		return titleIcon;
	}
	public String getTitlePage() {
		return titlePage;
	}
}
