package com.mng.robotest.test.getdata.products.data;

import java.util.List;
import java.util.Optional;

import com.mng.robotest.test.generic.beans.ValePais;

public class GarmentCatalog {

	private String garmentId;
	
	private String name;
	private String shortDescription;
	private int stock;
	private String genre;
	private List<Color> colors;
	private Labels labels;
	private AnalyticsEventsData analyticsEventsData;
	
	private ValePais valePais = null;
	private String urlFicha;

	public GarmentCatalog() {}
	public GarmentCatalog(String garmentId) {
		this.garmentId = garmentId;
	}
	
	public Article getArticleDefaultColorAndMoreStock() {
		Article articulo = new Article();
		articulo.setGarmentId(garmentId);
		Optional<Color> colorDefaultOpt = getDefaultColor();
		if (colorDefaultOpt.isEmpty()) {
			return null;
		}
		articulo.setColor(colorDefaultOpt.get());
		articulo.setSize(colorDefaultOpt.get().getSizeWithMoreStock());
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
	
	private Optional<Color> getDefaultColor() {
		if (colors==null) {
			return Optional.empty();
		}
		for (Color color : colors) {
			if (color.isDefaultColor()) {
				return Optional.of(color);
			}
		}
		return Optional.of(colors.get(0));
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
	public Color getColor(String id) {
		for (Color color : getColors()) {
			if (id.compareTo(color.getId())==0) {
				return color;
			}
		}
		return null;
	}
	
	public void setColors(List<Color> colors) {
		this.colors = colors;
	}
	public Labels getLabels() {
		return labels;
	}
	public void setLabels(Labels labels) {
		this.labels = labels;
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
	
	public void setUrlFicha(String urlFicha) {
		this.urlFicha = urlFicha;
	}
	public String getUrlFicha() {
		return urlFicha;
	}
	
	public static class Article {
		String garmentId;
		Color color;
		Size size;
		
		public String getGarmentId() {
			return garmentId;
		}
		public String getArticleId() {
			return garmentId + size.getId2Digits() + color.getId();
		}
		public void setGarmentId(String garmentId) {
			this.garmentId = garmentId;
		}
		public Color getColor() {
			return color;
		}
		public String getColorLabel() {
			return color.getLabel();
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

	public AnalyticsEventsData getAnalyticsEventsData() {
		return analyticsEventsData;
	}
	public void setAnalyticsEventData(AnalyticsEventsData analyticsEventsData) {
		this.analyticsEventsData = analyticsEventsData;
	}
	
	@Override
	public String toString() {
		return "Garment [garmentId=" + garmentId + ", stock=" + stock + ", colors=" + colors + ", labels=" + labels
				+ ", analyticsEventsData=" + analyticsEventsData + ", valePais=" + valePais + ", urlFicha=" + urlFicha
				+ "]";
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
}
