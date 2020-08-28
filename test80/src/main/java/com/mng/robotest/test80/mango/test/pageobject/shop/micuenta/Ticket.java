package com.mng.robotest.test80.mango.test.pageobject.shop.micuenta;

import com.mng.robotest.test80.mango.test.pageobject.shop.miscompras.PageMisCompras.TypeTicket;

public class Ticket {
	
    private String id;
    private TypeTicket type;
    private String precio;
    private String fecha;
    private int numItems;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public TypeTicket getType() {
		return type;
	}
	public void setType(TypeTicket type) {
		this.type = type;
	}
	public String getPrecio() {
		return precio;
	}
	public void setPrecio(String precio) {
		this.precio = precio;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public int getNumItems() {
		return numItems;
	}
	public void setNumItems(int numItems) {
		this.numItems = numItems;
	}
    
}
