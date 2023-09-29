package com.mng.robotest.tests.repository.canonicalproduct.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;


@JsonInclude(NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EntityColor implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String id; 
	private String nrf; 
	private String description; 
	private String descriptionEn; 
	private List<EntitySize> sizes; 
	private List<EntityImage> images;
	private List<EntityVideo> videos;
	private EntityPrice price;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNrf() {
		return nrf;
	}
	public void setNrf(String nrf) {
		this.nrf = nrf;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescriptionEn() {
		return descriptionEn;
	}
	public void setDescriptionEn(String descriptionEn) {
		this.descriptionEn = descriptionEn;
	}
	public List<EntitySize> getSizes() {
		return sizes;
	}
	public void setSizes(List<EntitySize> sizes) {
		this.sizes = sizes;
	}
	public List<EntityImage> getImages() {
		return images;
	}
	public void setImages(List<EntityImage> images) {
		this.images = images;
	}
	public List<EntityVideo> getVideos() {
		return videos;
	}
	public void setVideos(List<EntityVideo> videos) {
		this.videos = videos;
	}

	public EntityPrice getPrice() {
		return price;
	}

	public void setPrice(EntityPrice price) {
		this.price = price;
	}

}
