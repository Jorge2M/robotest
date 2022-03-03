package com.mng.robotest.domains.apiproduct.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ColorRedis implements Serializable {

	private static final long serialVersionUID = -6753967195343511891L;
	
	String id; 
	String description;
    List<SizeRedis> sizes;
    List<ImageRedis> images;
    List<VideoRedis> videos;
    PriceRedis price;
    
    public ColorRedis() {}
    
	public ColorRedis(String id, String description, List<SizeRedis> sizes, List<ImageRedis> images,
			List<VideoRedis> videos, PriceRedis price) {
		super();
		this.id = id;
		this.description = description;
		this.sizes = sizes;
		this.images = images;
		this.videos = videos;
		this.price = price;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<SizeRedis> getSizes() {
		return sizes;
	}
	public void setSizes(List<SizeRedis> sizes) {
		this.sizes = sizes;
	}
	public List<ImageRedis> getImages() {
		return images;
	}
	public void setImages(List<ImageRedis> images) {
		this.images = images;
	}
	public List<VideoRedis> getVideos() {
		return videos;
	}
	public void setVideos(List<VideoRedis> videos) {
		this.videos = videos;
	}

	public PriceRedis getPrice() {
		return price;
	}

	public void setPrice(PriceRedis price) {
		this.price = price;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}

