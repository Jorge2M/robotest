package com.mng.robotest.tests.domains.transversal.banners.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import com.mng.robotest.tests.domains.menus.pageobjects.LineaWeb.LineaType;

public class DataBanner {

	BannerType bannerType;
	WebElement bannerWeb;
	Point location;
	Dimension size;
	int position;
	boolean isOverlayed=false;
	String urlBanner;
	String urlDestino;
	String srcImage;
	String directText;
	String floatingText;

	public BannerType getBannerType() {
		return this.bannerType;
	}
	
	public WebElement getBannerWeb() {
		return bannerWeb;
	}
	
	public void setBannerWeb(WebElement bannerWeb) {
		this.bannerWeb = bannerWeb;
	}
	
	public void setBannerType(BannerType bannerType) {
		this.bannerType = bannerType;
	}
	
	public Point getLocation() {
		return location;
	}
	
	public void setLocation(Point location) {
		this.location = location;
	}
	
	public Dimension getSize() {
		return size;
	}
	
	public void setSize(Dimension size) {
		this.size = size;
	}
	
	public int getPosition() {
		return position;
	}
	
	public void setPosition(int position) {
		this.position = position;
	}
	
	public String getUrlBanner() {
		return this.urlBanner;
	}
	
	public void setUrlBanner(String urlBanner) {
		this.urlBanner = urlBanner;
	}
	
	public String getUrlDestino() {
		return this.urlDestino;
	}
	
	public void setUrlDestino(String urlDestino) {
		this.urlDestino = urlDestino;
	}
	
	public String getSrcImage() {
		return this.srcImage;
	}
	
	public void setSrcImage(String srcImage) {
		this.srcImage = srcImage;
	}
	
	public String getDirectText() {
		return this.directText;
	}
	
	public void setDirectText(String directText) {
		this.directText = directText;
	}
	
	public String getFloatingText() {
		return this.floatingText;
	}
	
	public void setFloatingText(String floatingText) {
		this.floatingText = floatingText;
	}
	
	public String getText() {
		if ("".compareTo(getDirectText())!=0) {
			return getDirectText();
		}
		return (getFloatingText());
	}
	
	public DestinoType getDestinoType() {
		if (urlBanner!=null && urlBanner.contains("redirect.faces") && urlBanner.contains("producto=")) {
			return (DestinoType.FICHA);
		}
		return (DestinoType.OTROS);
	}
	
	public boolean isOverlayedWith(DataBanner dataBanner2) {
		return (
			dataBanner2!=null &&
			getLocation().x==dataBanner2.getLocation().x && 
			getLocation().y==dataBanner2.getLocation().y &&
			getSize().width==dataBanner2.getSize().width &&
			getSize().height==dataBanner2.getSize().height);
	}
	
	private static final String TAG_LINEA = "@TagLinea";
	private static final String XP_LINK_LINEA_WITH_TAG = 
		".//a[@class[contains(.,'link')] and @data-cta[contains(.,'tiendaid=" + TAG_LINEA + "')]]";
	
	private String getXPathLinkLinea(LineaType lineaType) {
		return (XP_LINK_LINEA_WITH_TAG.replace(TAG_LINEA, lineaType.toString()));
	}
	
	public String getUrlLinkLinea(LineaType lineaType) {
		String xpathLink = getXPathLinkLinea(lineaType);
		try {
			WebElement link = bannerWeb.findElement(By.xpath(xpathLink));
			return (link.getAttribute("data-cta"));
		}
		catch (NoSuchElementException e) {
			return "";
		}
	}
}
