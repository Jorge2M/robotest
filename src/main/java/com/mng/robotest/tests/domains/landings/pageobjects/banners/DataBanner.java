package com.mng.robotest.tests.domains.landings.pageobjects.banners;

import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;

import com.mng.robotest.tests.domains.landings.pageobjects.banners.Banner.BannerType;

public class DataBanner {

	BannerType bannerType;
	int position;
	WebElement bannerWeb;
	Rectangle rectangle;
	String url;
	String urlResultant;
	String srcImage;
	String text;

	public BannerType getBannerType() {
		return this.bannerType;
	}
	
	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}	
	
	public WebElement getBannerWeb() {
		return bannerWeb;
	}
	
	public void setBannerWeb(WebElement bannerWeb) {
		this.bannerWeb = bannerWeb;
		this.rectangle = bannerWeb.getRect();
	}
	
	public void setBannerType(BannerType bannerType) {
		this.bannerType = bannerType;
	}
	
	public Rectangle getRectangle() {
		return rectangle;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getSrcImage() {
		return this.srcImage;
	}
	
	public void setSrcImage(String srcImage) {
		this.srcImage = srcImage;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void click() {
		bannerWeb.click();
	}

	public String getUrlResultant() {
		return urlResultant;
	}

	public void setUrlResultant(String urlResultant) {
		this.urlResultant = urlResultant;
	}

	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}


}
