package com.mng.robotest.testslegacy.data;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public enum Talla {

	T00("0" , "Undefined"),
	T01("01", "30x50cm"),
	T06("06", "50x90"),
	T09("09", "70x140"),
	T10("10", "90x150"),
	T18("18", "XXS", "XXS", "0"),
	T19("19", "XS", "ECH", "2"),
	T20("20", "S", "CH", "XS-S", "4"),
	T21("21", "M", "6"),
	T22("22", "L", "8"),
	T23("23", "XL", "EG", "10"),
	T24("24", "XXL", "EEG", "12"),
	T25("25", "1XL", "14"),
	T26("26", "2XL", "16"),	
	T27("27", "3XL", "18"),	
	T28("28", "4XL", "20"),	
	T32("32", "32", "4", "0"),
	T33("33", "33"),
	T34("34", "34", "6", "1"),
	T35("35", "35"),
	T36("36", "36", "8", "3"),
	T37("37", "37"),
	T38("38", "38", "10", "5"),
	T39("39", "39"),
	T40("40", "40", "12", "7"),
	T41("41", "41"),
	T42("42", "42", "14", "9"),
	T43("43", "43"),
	T44("44", "44", "16", "11"),
	T45("45", "45"),
	T46("46", "46", "18", "13"),
	T47("47", "47"),
	T48("48", "48"),
	T99("99", "U");

	private List<String> labels;
	private String value;
	private Talla(String value, String... labels) {
		this.labels = Arrays.asList(labels);
		this.value = value;
	}
	public List<String> getLabels(PaisShop pais) {
		if (pais==PaisShop.CHINA) {
			var labelsNextSizeOpt = getLabelsNextSize();
			if (labelsNextSizeOpt.isPresent()) {
				return labelsNextSizeOpt.get();
			}
		}
		return labels;
	}
	public String getValue() {
		return value;
	}
	
	private Optional<List<String>> getLabelsNextSize() {
		int nextNumber = Integer.valueOf(getValue()) + 1;
		String nextValue = String.format("%02d", nextNumber);
		var nextTallaOpt = getTalla(nextValue);
		if (nextTallaOpt.isPresent()) {
			var nextLabels = fromValue(nextValue).getLabels(PaisShop.ESPANA);
			return Optional.of(nextLabels);
		}
		return Optional.empty();
	}
	
	public static Talla fromLabel(String label, PaisShop pais) {
		Optional<Talla> talla = Arrays.asList(Talla.values()).stream()
			.filter(e -> e.getLabels(pais).contains(label))
			.findAny();
		
		if (talla.isPresent()) {
			return talla.get();
		}
		return T00;
	}
	public static Talla fromValue(String value) {
		var tallaOpt = getTalla(value);
		if (tallaOpt.isPresent()) {
			return tallaOpt.get();
		}
		return T00;
	}
	
	private static Optional<Talla> getTalla(String value) {
		return Arrays.asList(Talla.values()).stream()
				.filter(e -> e.getValue().compareTo(value)==0)
				.findAny();
	}
}
