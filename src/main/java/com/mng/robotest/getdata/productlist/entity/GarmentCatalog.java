package com.mng.robotest.getdata.productlist.entity;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import com.mng.robotest.getdata.canonicalproduct.entity.EntityColor;
import com.mng.robotest.getdata.canonicalproduct.entity.EntityProduct;
import com.mng.robotest.getdata.canonicalproduct.entity.EntitySize;
import com.mng.robotest.test.generic.beans.ValeDiscount;


public class GarmentCatalog {

	private String garmentId;
	
	private String name;
	private String shortDescription;
	private int stock;
	private String genre;
	private List<Color> colors;
	private Labels labels;
	private AnalyticsEventsData analyticsEventsData;
	private EntityProduct canonicalProduct;
	
	private ValeDiscount valePais = null;
	private String urlFicha;

	public GarmentCatalog() {}
	public GarmentCatalog(String garmentId) {
		this.garmentId = garmentId;
	}

	
	private Article getArticleWithMoreStock() {
		Article articulo = new Article();
		articulo.setGarmentId(garmentId);
		articulo.setUrlFicha(urlFicha);

		Color colorResult = colors.get(0);
		Size sizeResult = colorResult.getSizes().get(0);
		for (Color color : colors) {
			Size size = color.getSizeWithMoreStock();
			if (size.getStock() > colorResult.getStock()) {
				colorResult = color;
				sizeResult = size; 
			}
		}
		
		articulo.setColor(colorResult);
		articulo.setSize(sizeResult);
		
		var articleCanonical = getArticleCanonical(colorResult, sizeResult);
		if (articleCanonical.isPresent()) {
			EntityColor colorCanonical = articleCanonical.get().getLeft();
			EntitySize sizeCanonical = articleCanonical.get().getRight();
			articulo.setColorCanonical(colorCanonical);
			articulo.setSizeCanonical(sizeCanonical);
			if (sizeCanonical.getStockDetails()!=null) {
				articulo.setWareHouse(sizeCanonical.getStockDetails().get(0).getWarehouse());
			} else {
				articulo.setWareHouse(null);
			}
		}
		
		return articulo;
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
	
	public void setValePais(ValeDiscount valePais) {
		this.valePais = valePais;
	}
	public ValeDiscount getValePais() {
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
		EntityColor colorCanonical;
		EntitySize sizeCanonical;
		String urlFicha;
		String wareHouse;
		
		public static Article getArticleCandidateForTest(GarmentCatalog garment) { 		
			return garment.getArticleWithMoreStock();
		}
		public static List<Article> getArticlesCandidateForTest(List<GarmentCatalog> garments) {
			return garments.stream()
					.map(g -> g.getArticleWithMoreStock())
					.toList();
		}
		
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
		public String getUrlFicha() {
			return urlFicha;
		}
		public void setUrlFicha(String urlFicha) {
			this.urlFicha = urlFicha;
		}
		public String getWareHouse() {
			return wareHouse;
		}
		public void setWareHouse(String wareHouse) {
			this.wareHouse = wareHouse;
		}
		public EntityColor getColorCanonical() {
			return colorCanonical;
		}
		public void setColorCanonical(EntityColor colorCanonical) {
			this.colorCanonical = colorCanonical;
		}
		public EntitySize getSizeCanonical() {
			return sizeCanonical;
		}
		public void setSizeCanonical(EntitySize sizeCanonical) {
			this.sizeCanonical = sizeCanonical;
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
	public EntityProduct getCanonicalProduct() {
		return canonicalProduct;
	}
	public void setCanonicalProduct(EntityProduct canonicalProduct) {
		this.canonicalProduct = canonicalProduct;
	}
	
	public void removeArticlesWithoutMaxStock() {
		removeOtherArticles(getArticleWithMoreStock());
	}
	
	private void removeOtherArticles(Article articleMaintain) {
		removeOtherArticlesFromMain(articleMaintain);
		if (canonicalProduct!=null) {
			removeOtherArticlesFromCanonical(articleMaintain);
		}
	}
	
	private void removeOtherArticlesFromMain(Article articleMaintain) {
		Color colorMaintain = articleMaintain.getColor();
		Size sizeMaintain = colorMaintain.getSizeWithMoreStock();
		
		colors = getColors().stream()
				.filter(c -> c.getId().compareTo(colorMaintain.getId())==0)
				.toList();
		
		Color color = colors.get(0);
		color.setSizes(color.getSizes().stream()
				.filter(s -> s.getId()==sizeMaintain.getId())
				.toList());
	}
	
	private void removeOtherArticlesFromCanonical(Article articleMaintain) {
		Color colorMaintain = articleMaintain.getColor();
		Size sizeMaintain = colorMaintain.getSizeWithMoreStock();

		List<EntityColor> listFiltered = canonicalProduct.getColors().stream()
				.filter(c -> c.getId().compareTo(colorMaintain.getId())==0)
				.toList();
		canonicalProduct.setColors(listFiltered);
		
		EntityColor color = canonicalProduct.getColors().get(0);
		color.setSizes(color.getSizes().stream()
				.filter(s -> s.getId().compareTo(String.valueOf(sizeMaintain.getId()))==0)
				.toList());
	}

	public String getAlmacenFirstArticle() {
		if (canonicalProduct!=null) {
			return canonicalProduct
						.getColors().get(0)
						.getSizes().get(0)
						.getStockDetails().get(0)
						.getWarehouse();
		}
		return null;
	}
	
	private Optional<Pair<EntityColor, EntitySize>> getArticleCanonical(Color color, Size size) {
		if (canonicalProduct==null) {
			return Optional.empty();
		}
		
		for (EntityColor colorCanonical : canonicalProduct.getColors()) {
			if (colorCanonical.getId().compareTo(color.getId())==0) {
				for (EntitySize sizeCanonical : colorCanonical.getSizes()) {
					if (sizeCanonical.getId().compareTo(String.valueOf(size.getId()))==0) {
						return Optional.of(Pair.of(colorCanonical, sizeCanonical));
					}
				}
			}
		}
		
		return Optional.empty();
	}
	
}
