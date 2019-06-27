package com.mng.robotest.test80.mango.test.pageobject.shop.cabecera;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabeceraOutletMovil.Icono;

public interface SearchBarMobil {
	public boolean isVisibleUntil(int maxSecondsWait);
	public void search(String text);
	public void close() throws Exception;
	
	public static SearchBarMobil make(AppEcom app, WebDriver driver) throws Exception {
		if (app==AppEcom.outlet) {
			return (SearchBarMobilOld.make(driver));
		}
		
		int maxSecondsWait = 0;
		SearchBarMobil searchBar = getSearchBarVisible(maxSecondsWait, driver);
		if (searchBar==null) {
			makeSearchBarVisible(app, driver);
			maxSecondsWait = 2;
			searchBar = getSearchBarVisible(maxSecondsWait, driver);
			searchBar.close();
		}

		return searchBar;
	}
	
	static SearchBarMobil getSearchBarVisible(int maxSecondsWait, WebDriver driver) {
		SearchBarMobil searchBarNew = SearchBarMobilNew.make(driver);
		if (searchBarNew.isVisibleUntil(maxSecondsWait)) {
			return searchBarNew;
		}
		
		SearchBarMobil searchBarOld = SearchBarMobilOld.make(driver);
		if (searchBarOld.isVisibleUntil(maxSecondsWait)) {
			return searchBarOld;
		}
		
		return null;
	}
	
	static void makeSearchBarVisible(AppEcom app, WebDriver driver) throws Exception {
		SecCabeceraOutletMovil.click(Icono.Lupa, app, driver);
	}
}
