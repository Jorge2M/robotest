package com.mng.robotest.test.data;

import java.util.ArrayList;
import java.util.List;

public enum Color {
	Negro("1", "Negros"),
	Blanco("10", "Blancos"),
	Rojo("11", "Rojos"),
	Gris("12", "Grises"),
	Azul("13", "Azules"),
	Beige("14", "Beige"),
	Verde("2", "Verdes"),
	Marron("3", "Marrones"),
	Crudo("4", "Crudos"),
	Rosa("5", "Rosas");
	
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
		for (Color color : listColors)
			listCodColors.add(color.getCodigoColor());
	
		return listCodColors;
	}
	
	public static List<String> getListNamesFiltros(List<Color> listColors) {
		List<String> listCodColors = new ArrayList<>();
		for (Color color : listColors)
			listCodColors.add(color.getNameFiltro());
	
		return listCodColors;
	}
}
