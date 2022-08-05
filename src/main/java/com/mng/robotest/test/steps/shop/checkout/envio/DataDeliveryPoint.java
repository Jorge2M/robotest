package com.mng.robotest.test.steps.shop.checkout.envio;

import com.mng.robotest.test.pageobject.shop.checkout.envio.SecSelectDPoint.TypeDeliveryPoint;


public class DataDeliveryPoint {
	TypeDeliveryPoint typeDeliveryPoint;
	String codigo;
	String name;
	String direccion;
	String cpAndPoblacion;
	String colecciones;
	String telefono;
	
	public TypeDeliveryPoint getTypeDeliveryPoint() {
		return this.typeDeliveryPoint;
	}
	public void setTypeDeliveryPoint(TypeDeliveryPoint typeDeliveryPoint) {
		this.typeDeliveryPoint = typeDeliveryPoint;
	}
	public String getCodigo() {
		return this.codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDireccion() {
		return this.direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getCPandPoblacion() {
		return this.cpAndPoblacion;
	}
	
	public String getCodPostal() {
		return (getCPandPoblacion().replaceAll("\\D+",""));
	}
	
	public void setCodPostal(String codPostal) {
		this.cpAndPoblacion = codPostal;
	}	
	public String getColecciones() {
		return this.colecciones;
	}
	public void setColecciones(String colecciones) {
		this.colecciones = colecciones;
	}
	public String getTelefono() {
		return this.telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
}
