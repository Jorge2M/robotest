package com.mng.robotest.tests.domains.compra.payments.d3d.pageobjects;

public interface PageD3DLogin {

	public boolean isPage(int seconds);
	public boolean isImporteVisible(String importeTotal);
	public void inputCredentials(String user, String password);
	public void clickButtonSubmit();
	
	public static PageD3DLogin make() {
		var pageNew = new PageD3DLoginNew();
		if (pageNew.isPage(10)) {
			return pageNew;
		}
		return new PageD3DLoginOld();
	}
	
}
