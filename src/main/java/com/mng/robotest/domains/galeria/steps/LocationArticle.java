package com.mng.robotest.domains.galeria.steps;

public class LocationArticle {
	public enum AccessFrom { INIT_CATALOG, INIT_PAGE }
	
	public AccessFrom accessFrom = AccessFrom.INIT_PAGE;
	public int numPage = 0;
	public int numArticle = 0;
	
	
	public static LocationArticle getInstanceInPage(int numPage, int numArticle) {
		var location = new LocationArticle();
		location.accessFrom = AccessFrom.INIT_PAGE;
		location.numPage = numPage;
		location.numArticle = numArticle;
		return location;
	}
	
	public static LocationArticle getInstanceInCatalog(int numArticle) {
		var location = new LocationArticle();
		location.accessFrom = AccessFrom.INIT_CATALOG;
		location.numArticle = numArticle;
		return location;
	}
	
	@Override
	public String toString() {
		String init = numArticle + "º from " + accessFrom.name();
		switch (accessFrom) {
		case INIT_CATALOG:
			return (init);
		case INIT_PAGE:
		default:
			return (init + " " + numPage + "ª");
		}
	}


	public boolean isFirstInGalery() {
		return (
			(accessFrom==AccessFrom.INIT_CATALOG && numArticle==1) ||
			(accessFrom==AccessFrom.INIT_PAGE && numPage==1 && numArticle==1)
		);
	}
}
