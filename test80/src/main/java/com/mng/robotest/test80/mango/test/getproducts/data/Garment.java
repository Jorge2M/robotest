package com.mng.robotest.test80.mango.test.getproducts.data;

import java.util.List;

public class Garment {

	private String garmentId;
	private int stock;
	private List<Color> colors;

	public Color getDefaultColor() {
		for (Color color : colors) {
			if (color.isDefaultColor()) {
				return color;
			}
		}
		return colors.get(0);
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
}
