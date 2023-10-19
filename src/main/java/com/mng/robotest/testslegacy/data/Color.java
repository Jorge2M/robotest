package com.mng.robotest.testslegacy.data;

import java.util.ArrayList;
import java.util.List;

public enum Color {
	NEGRO("1", "Negros"),
	BLANCO("10", "Blancos"),
	ROJO("11", "Rojos"),
	GRIS("12", "Grises"),
	AZUL("13", "Azules"),
	BEIGE("14", "Beige"),
	VERDE("2", "Verdes"),
	MARRON("3", "Marrones"),
	CRUDO("4", "Crudos"),
	ROSA("5", "Rosas");
	
	String codigoColor;
	String nameFiltro;
	private Color(String codigoColor, String nameFiltro) {
		this.codigoColor = codigoColor;
		this.nameFiltro = nameFiltro;
	}
	
	public String getCodigoColor() {
		return codigoColor;
	}
	
	public String getNameFiltro() {
		return nameFiltro;
	}

	public static List<String> getListCodigosColor(List<Color> listColors) {
		List<String> listCodColors = new ArrayList<>();
		for (Color color : listColors) {
			listCodColors.add(color.getCodigoColor());
		}
		return listCodColors;
	}
	
	public static List<String> getListNamesFiltros(List<Color> listColors) {
		List<String> listCodColors = new ArrayList<>();
		for (Color color : listColors) {
			listCodColors.add(color.getNameFiltro());
		}
		return listCodColors;
	}
}
