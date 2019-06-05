package com.mng.robotest.test80.mango.test.data;

import com.mng.robotest.test80.mango.conftestmaker.AppEcom;

public class Descuento {
	
	final static int percDescEmpleadoShop = 25;
	final static int percDescEmpleadoOutlet = 10;
	
	public enum DiscountOver {
		OriginalPrice("descuento sobre el precio original"), 
		LastPriceOrSale("descuento adicional sobre el último precio/rebaja");
		
		String description;
		private DiscountOver(String description) {
			this.description = description;
		}
		
		public String getDescription() {
			return this.description;
		}
	};
	
	public enum DiscountType {Vale, Empleado};
	
	int percentageDesc = 0;
	DiscountOver discountOver;

	public Descuento(int porcentajeDesc) {
		this.percentageDesc = porcentajeDesc;
		this.discountOver = DiscountOver.OriginalPrice; //Default value
		
	}
	
	public Descuento(int porcentajeDesc, AppEcom app) {
		this.percentageDesc = porcentajeDesc;
		this.discountOver = getTypeDescuentoForApp(app);
	}
	
	public Descuento(AppEcom app, DiscountType discType) {
		this.discountOver = getTypeDescuentoForApp(app);
		if (discType==DiscountType.Empleado) {
			switch (app) {
			case outlet:
				this.percentageDesc = percDescEmpleadoOutlet;
				break;
			case shop:
			default:
				this.percentageDesc = percDescEmpleadoShop;
			}
		}
		else
			this.percentageDesc = 0;
	}

	public int getPercentageDesc() {
		return this.percentageDesc;
	}
	
	public DiscountOver getDiscountOver() {
		return this.discountOver;
	}
	
	public static DiscountOver getTypeDescuentoForApp(AppEcom app) {
		switch (app) {
		case outlet:
			return DiscountOver.LastPriceOrSale;
		case shop:
		default:
			return DiscountOver.OriginalPrice;
		}
	}
}
