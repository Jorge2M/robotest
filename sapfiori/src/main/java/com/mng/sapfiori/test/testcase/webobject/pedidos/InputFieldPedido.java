package com.mng.sapfiori.test.testcase.webobject.pedidos;

import com.mng.sapfiori.test.testcase.webobject.pedidos.PagePosSolicitudPedido.SectionPageSolPedido;

public enum InputFieldPedido {
	Material("Material", SectionPageSolPedido.InfGeneral, true, "Material", true),
	Centro("Centro", SectionPageSolPedido.InfGeneral, true, "Plant", true),
	Temporada("Temporada", SectionPageSolPedido.InfGeneral, true, "", false),
	AñoTemporada("Año de temporada", SectionPageSolPedido.InfGeneral, true, "", false),
	OrgCompras("Purchasing Organization", SectionPageSolPedido.InfContacto, true, "", false),
	GrupoCompras("Purchasing Group", SectionPageSolPedido.InfContacto, true, "", false),
	Customer("Customer", SectionPageSolPedido.InfContacto, false, "", false),
	Quantity("Quantity", SectionPageSolPedido.CantidadFecha, false, "RequestedQuantity", true),
	FechaEntrega("Delivery Date", SectionPageSolPedido.CantidadFecha, false, "DeliveryDate", true),
	IdCurvaDistrib("ID curva distrib.", SectionPageSolPedido.CantidadFecha, true, "ZZDPID", true),
	Precio("Prize", SectionPageSolPedido.CantidadFecha, false, "PurchaseRequisitionPrice", true);
	
	public String label;
	public SectionPageSolPedido section;
	public boolean isIcon;
	public boolean isInTable;
	public String idInTable;
	private InputFieldPedido(String label, SectionPageSolPedido section, boolean isIcon, String idInTable, boolean isInTable) {
		this.label = label;
		this.section = section;
		this.isIcon = isIcon;
		this.isInTable = isInTable;
		this.idInTable = idInTable;
	}
}
