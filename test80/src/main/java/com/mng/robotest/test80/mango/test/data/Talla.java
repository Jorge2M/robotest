package com.mng.robotest.test80.mango.test.data;

public enum Talla {
	XS(19),
	S(20),
	M(21),
	L(22),
	XL(23),
	XXL(24),
	U(99),
	Undefined(0);
	
	private int tallaNum;
	private Talla(int tallaNum) {
		this.tallaNum = tallaNum;
	}
	public int getTallaNum() {
		return tallaNum;
	}
	public static Talla from (String tallaStr) {
		try {
			return Talla.valueOf(tallaStr);
		}
		catch (IllegalArgumentException e) {
			return Undefined;
		}
	}
	public static Talla getTalla(int tallaNum) {
		for (Talla talla : Talla.values()) {
			if (talla.getTallaNum()==tallaNum) {
				return talla;
			}
		}
		return null;
	}
}
