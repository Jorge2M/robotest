package com.mng.robotest.testslegacy.data;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public enum Talla {

	T00("0" , Arrays.asList("Undefined")),
	T01("01", Arrays.asList("30x50cm")),
	T06("06", Arrays.asList("50x90")),
	T10("10", Arrays.asList("90x150")),
	T18("18", Arrays.asList("XXS", "4")),
	T19("19", Arrays.asList("XS", "6")),
	T20("20", Arrays.asList("S","XS-S", "8")),
	T21("21", Arrays.asList("M", "10")),
	T22("22", Arrays.asList("L", "12")),
	T23("23", Arrays.asList("XL", "14")),
	T24("24", Arrays.asList("XXL", "16")),
	T25("25", Arrays.asList("1XL", "18")),
	T26("26", Arrays.asList("2XL", "22")),	
	T27("27", Arrays.asList("3XL", "24")),	
	T28("28", Arrays.asList("4XL", "26")),	
	T32("32", Arrays.asList("32", "4", "0")),
	T33("33", Arrays.asList("33")),
	T34("34", Arrays.asList("34", "6", "1")),
	T35("35", Arrays.asList("35")),
	T36("36", Arrays.asList("36", "8", "3")),
	T37("37", Arrays.asList("37")),
	T38("38", Arrays.asList("38", "10", "5")),
	T39("39", Arrays.asList("39")),
	T40("40", Arrays.asList("40", "12", "7")),
	T41("41", Arrays.asList("41")),
	T42("42", Arrays.asList("42", "14", "9")),
	T43("43", Arrays.asList("43")),
	T44("44", Arrays.asList("44", "16", "11")),
	T45("45", Arrays.asList("45")),
	T46("46", Arrays.asList("46", "18", "13")),
	T47("47", Arrays.asList("47")),
	T48("48", Arrays.asList("48")),
	T99("99", Arrays.asList("U"));

	private List<String> labels;
	private String value;
	private Talla(String value, List<String> labels) {
		this.labels = labels;
		this.value = value;
	}
	public List<String> getLabels() {
		return labels;
	}
	public String getValue() {
		return value;
	}
	public static Talla fromLabel(String label) {
		Optional<Talla> talla = Arrays.asList(Talla.values()).stream()
			.filter(e -> e.getLabels().contains(label))
			.findAny();
		
		if (talla.isPresent()) {
			return talla.get();
		}
		return T00;
	}
	public static Talla fromValue(String value) {
		Optional<Talla> talla = Arrays.asList(Talla.values()).stream()
				.filter(e -> e.getValue().compareTo(value)==0)
				.findAny();
		
		if (talla.isPresent()) {
			return talla.get();
		}
		return T00;
	}
}
