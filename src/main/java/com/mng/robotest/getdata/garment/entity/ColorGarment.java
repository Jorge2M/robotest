package com.mng.robotest.getdata.garment.entity;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

public class ColorGarment {

	String id;
	String look;
	String label;
	String variant;
	String icon;
	@JsonProperty("default") boolean defaultColor;
	boolean visible;
	Price price;
	List<SizeGarment> sizes;
    @JsonInclude(NON_EMPTY)
    List<Object> images;
	String accessibilityText;
	DataLayerColor dataLayer;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLook() {
		return look;
	}
	public void setLook(String look) {
		this.look = look;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getVariant() {
		return variant;
	}
	public void setVariant(String variant) {
		this.variant = variant;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public boolean isDefaultColor() {
		return defaultColor;
	}
	public void setDefaultColor(boolean defaultColor) {
		this.defaultColor = defaultColor;
	}
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public Price getPrice() {
		return price;
	}
	public void setPrice(Price price) {
		this.price = price;
	}
	public List<SizeGarment> getSizes() {
		return sizes;
	}
	public Optional<SizeGarment> getSize(Integer id) {
		if (getSizes()==null) {
			return Optional.empty();
		}
		return getSizes().stream()
			.filter(s -> Integer.valueOf(s.getId()).equals(id))
			.findFirst();
	}
	
	public void setSizes(List<SizeGarment> sizes) {
		this.sizes = sizes;
		if (this.sizes.size()>1) {
			sizes.remove(0);
		}

	}
	public List<Object> getImages() {
		return images;
	}
	
	public void setImages(List<Object> images) {
		this.images = images;
	}
	public String getAccessibilityText() {
		return accessibilityText;
	}
	public void setAccessibilityText(String accessibilityText) {
		this.accessibilityText = accessibilityText;
	}
	public DataLayerColor getDataLayer() {
		return dataLayer;
	}
	public void setDataLayer(DataLayerColor dataLayer) {
		this.dataLayer = dataLayer;
	}
	
}
