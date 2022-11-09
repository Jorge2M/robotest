package com.mng.robotest.test.data;

import com.mng.robotest.conftestmaker.AppEcom;

public class Descuento {
	
	private static final int PERC_DESC_EMPLEADO_SHOP = 25;
	private static final int PERC_DESC_EMPLEADO_OUTLET = 10;
	
	public enum DiscountOver {
		ORIGINAL_PRICE("descuento sobre el precio original"), 
		LAST_PRICE_OR_SALE("descuento adicional sobre el último precio/rebaja");
		
		String description;
		private DiscountOver(String description) {
			this.description = description;
		}
		
		public String getDescription() {
			return this.description;
		}
	};
	
	public enum DiscountType {VALE, EMPLEADO};
	
	int percentageDesc = 0;
	DiscountOver discountOver;

	public Descuento(int porcentajeDesc) {
		this.percentageDesc = porcentajeDesc;
		this.discountOver = DiscountOver.ORIGINAL_PRICE; //Default value
		
	}
	
	public Descuento(int porcentajeDesc, AppEcom app) {
		this.percentageDesc = porcentajeDesc;
		this.discountOver = getTypeDescuentoForApp(app);
	}
	
	public Descuento(AppEcom app, DiscountType discType) {
		this.discountOver = getTypeDescuentoForApp(app);
		if (discType==DiscountType.EMPLEADO) {
			switch (app) {
			case outlet:
				this.percentageDesc = PERC_DESC_EMPLEADO_OUTLET;
				break;
			case shop:
			default:
				this.percentageDesc = PERC_DESC_EMPLEADO_SHOP;
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
			return DiscountOver.LAST_PRICE_OR_SALE;
		case shop:
		default:
			return DiscountOver.ORIGINAL_PRICE;
		}
	}
}
