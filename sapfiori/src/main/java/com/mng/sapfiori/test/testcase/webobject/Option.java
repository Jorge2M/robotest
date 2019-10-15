package com.mng.sapfiori.test.testcase.webobject;

public enum Option {

	ReclasificaciónProductos ("Reclasificar productos", "Reclasificación de productos - Números estadísticos de mercancías");
	
	private final String titleIcon;
	private final String titlePage;
	private Option(String titleIcon, String titlePage) {
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
