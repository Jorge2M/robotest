package com.mng.robotest.test80.mango.test.getdata.products.data;

import java.util.List;

import com.mng.robotest.test80.mango.test.generic.beans.ValePais;

public class Garment {

	private String garmentId;
	private int stock;
	private List<Color> colors;
	
	private ValePais valePais = null;

	public Garment() {}
	public Garment(String garmentId) {
		this.garmentId = garmentId;
	}
	
	public Article getArticleDefaultColorAndMoreStock() {
		Article articulo = new Article();
		articulo.setGarmentId(garmentId);
		Color colorDefault = getDefaultColor();
		articulo.setColor(colorDefault);
		articulo.setSize(colorDefault.getSizeWithMoreStock());
		return articulo;
	}
	
	public Article getArticleWithMoreStock() {
		Article articulo = new Article();
		articulo.setGarmentId(garmentId);
		Color colorMoreStock = getColorWithMoreStock();
		articulo.setColor(colorMoreStock);
		if (colorMoreStock!=null) {
			articulo.setSize(colorMoreStock.getSizeWithMoreStock());
		}
		return articulo;
	}
	
	private Color getDefaultColor() {
		if (colors==null) {
			return null;
		}
		for (Color color : colors) {
			if (color.isDefaultColor()) {
				return color;
			}
		}
		return colors.get(0);
	}
	
	private Color getColorWithMoreStock() {
		if (colors==null) {
			return null;
		}
		Color colorWithMoreStock = null;
		for (Color color : colors) {
			if (colorWithMoreStock == null ||
				color.getStock() > colorWithMoreStock.getStock()) {
				colorWithMoreStock = color;
			}
		}
		return colorWithMoreStock;
	}

	public String getGarmentId() {
		return garmentId;
	}
	public void setGarmentId(String garmentId) {
		this.garmentId = garmentId;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public List<Color> getColors() {
		return colors;
	}
	public void setColors(List<Color> colors) {
		this.colors = colors;
	}
	
	public void setValePais(ValePais valePais) {
		this.valePais = valePais;
	}
	public ValePais getValePais() {
		return valePais;
	}
	public boolean isVale() {
		return valePais!=null;
	}
	
	public static class Article {
		String garmentId;
		Color color;
		Size size;
		
		public String getGarmentId() {
			return garmentId;
		}
		public void setGarmentId(String garmentId) {
			this.garmentId = garmentId;
		}
		public Color getColor() {
			return color;
		}
		public void setColor(Color color) {
			this.color = color;
		}
		public Size getSize() {
			return size;
		}
		public void setSize(Size size) {
			this.size = size;
		}
	}
}
