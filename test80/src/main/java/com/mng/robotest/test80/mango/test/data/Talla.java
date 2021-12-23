package com.mng.robotest.test80.mango.test.data;

public enum Talla {
	XXS("XXS", "18"),
	XS("XS", "19"),
	S("S", "20"),
	M("M", "21"),
	L("L", "22"),
	XL("XL", "23"),
	XXL("XXL", "24"),
	U("U", "99"),
	t1("1", "32"),
	t2("2", "34"),
	t4("4", "36"),
	t6("6", "38"),
	t8("8", "40"),
	t10("10", "42"),
	t12("12", "44"),
	t14("14", "46"),
	t32("32", "32"),
	t33("33", "33"),
	t34("34", "34"),
	t35("35", "35"),
	t36("36", "36"),
	t37("37", "37"),
	t38("38", "38"),
	t39("39", "39"),
	t40("40", "40"),
	t41("41", "41"),
	t42("42", "42"),
	t43("43", "43"),
	t44("44", "44"),
	t45("45", "45"),
	t46("46", "46"),
	t47("47", "47"),
	t48("48", "48"),
	x30x50cm("30x50cm", "01"),
	x50x90("50x90", "06"),
	x90x150("90x150", "10"),
	Undefined("Undefined", "0");
	
	private String size;
	private String value;
	private Talla(String size, String value) {
		this.size = size;
		this.value = value;
	}
	public String getSize() {
		return size;
	}
	public String getValue() {
		return value;
	}
	public static Talla fromSize(String size) {
		for (Talla talla : Talla.values()) {
			if (talla.getSize().compareTo(size)==0) {
				return talla;
			}
		}
		return Undefined;
	}
	public static Talla fromValue(String value) {
		for (Talla talla : Talla.values()) {
			if (talla.getValue().compareTo(value)==0) {
				return talla;
			}
		}
		return null;
	}
}
