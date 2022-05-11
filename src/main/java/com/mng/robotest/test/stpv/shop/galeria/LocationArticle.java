package com.mng.robotest.test.stpv.shop.galeria;

public class LocationArticle {
	public enum AccessFrom {InitCatalog, InitPage}
	
	public AccessFrom accessFrom = AccessFrom.InitPage;
	public int numPage = 0;
	public int numArticle = 0;
	
	
	public static LocationArticle getInstanceInPage(int numPage, int numArticle) {
		LocationArticle location = new LocationArticle();
		location.accessFrom = AccessFrom.InitPage;
		location.numPage = numPage;
		location.numArticle = numArticle;
		return location;
	}
	
	public static LocationArticle getInstanceInCatalog(int numArticle) {
		LocationArticle location = new LocationArticle();
		location.accessFrom = AccessFrom.InitCatalog;
		location.numArticle = numArticle;
		return location;
	}
	
	@Override
	public String toString() {
		String init = numArticle + "º from " + accessFrom.name();
		switch (accessFrom) {
		case InitCatalog:
			return (init);
		case InitPage:
		default:
			return (init + " " + numPage + "ª");
		}
	}


	public boolean isFirstInGalery() {
		return (
			(accessFrom==AccessFrom.InitCatalog && numArticle==1) ||
			(accessFrom==AccessFrom.InitPage && numPage==1 && numArticle==1)
		);
	}
}
