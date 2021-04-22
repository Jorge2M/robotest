package com.mng.robotest.test80.mango.test.data;

public enum Talla {
	XS("XS", "19"),
	S("S", "20"),
	M("M", "21"),
	L("L", "22"),
	XL("XL", "23"),
	XXL("XXL", "24"),
	U("U", "99"),
	x30x50cm("30x50cm", "01"),
	x50x90("50x90", "06"),
	x90x150("90x150", "10"),
	Undefined("Undefined", "0");
	
	private String name;
	private String tallaNum;
	private Talla(String name, String tallaNum) {
		this.name = name;
		this.tallaNum = tallaNum;
	}
	public String getName() {
		return name;
	}
	public String getTallaNum() {
		return tallaNum;
	}
	public static Talla from(String tallaStr) {
		for (Talla talla : Talla.values()) {
			if (talla.getName().compareTo(tallaStr)==0) {
				return talla;
			}
		}
		return Undefined;
//		try {
//			return Talla.valueOf(tallaStr);
//		}
//		catch (IllegalArgumentException e) {
//			return Undefined;
//		}
	}
	public static Talla getTalla(String tallaNum) {
		for (Talla talla : Talla.values()) {
			if (talla.getTallaNum().compareTo(tallaNum)==0) {
				return talla;
			}
		}
		return null;
	}
}
