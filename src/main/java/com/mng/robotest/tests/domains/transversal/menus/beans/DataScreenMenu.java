package com.mng.robotest.tests.domains.transversal.menus.beans;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.testslegacy.utils.checkmenus.Label;

public class DataScreenMenu implements Label, Comparable<DataScreenMenu> {

	private String label;
	private String id;
	private String dataTestId;
	private String href;
	
	private DataScreenMenu() {
	}
	
	public static DataScreenMenu getNew() {
		return new DataScreenMenu();
	}
	
	public static DataScreenMenu from(WebElement menu, Channel channel) {
		var dataMenu = new DataScreenMenu();
		dataMenu.setId(menu.getAttribute("id"));
		if (channel==Channel.desktop) {
			WebElement ancor = menu.findElement(By.xpath("./a"));
			dataMenu.setDataTestId(ancor.getAttribute("data-testid"));
			dataMenu.setHref(ancor.getAttribute("href"));
		} else {
			dataMenu.setDataTestId(menu.getAttribute("data-label"));
			if (dataMenu.isDataTestIdValid()) {
				dataMenu.setLabel(menu.getText().replace("New!", "").trim());
			}
			dataMenu.setHref(menu.getAttribute("href"));
		}
		if (dataMenu.isDataTestIdValid()) {
			dataMenu.setLabel(menu.getText().replace("New!", "").trim());
		}
		return dataMenu;
	}
	
	public static DataScreenMenu getNew(String dataGaLabel, String label) {
		var dataMenu = new DataScreenMenu();
		dataMenu.setDataTestId(dataGaLabel);
		dataMenu.setLabel(label);
		return dataMenu;
	}
	
	public static DataScreenMenu getNew(String label) {
		var dataMenu = new DataScreenMenu();
		dataMenu.setLabel(label);
		return dataMenu;
	}
	
	public String getDataTestId() {
		return this.dataTestId;
	}
	
	@Override
	public String getLabel() {
		return this.label;
	}
	
	public void setDataTestId(String dataTestId) {
		this.dataTestId = dataTestId;
	}
	
	public String getHref() {
		return this.href;
	}
	
	public void setHref(String href) {
		this.href = href;
	}
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setLabel(String label) {
		this.label = label;
		if (!isDataTestIdValid()) {
			//setDataGaLabel("-" + label);
			setDataTestId(label);
		}
	}
	
	public boolean isDataTestIdValid() {
		return (dataTestId!=null && dataTestId.compareTo("")!=0);
	}
	
	@Override
	public int compareTo(DataScreenMenu dataMenu) {
	  return (dataTestId.compareTo(dataMenu.dataTestId));
	}
	
	@Override
	public boolean equals(Object o) { 
		if (o == this) { 
			return true; 
		} 
		if (!(o instanceof DataScreenMenu)) { 
			return false; 
		} 
		DataScreenMenu c = (DataScreenMenu) o; 
		return (
			c.dataTestId.compareTo(this.dataTestId)==0 &&
			c.getLabel().toLowerCase().compareTo(this.getLabel().toLowerCase())==0 &&
			c.getDataTestId().compareTo(this.getDataTestId())==0);
	} 
	
	@Override
	public int hashCode() {
		return this.dataTestId.hashCode();
	}
	
	@Override
	public String toString() {
		String result = "";
		if (dataTestId.compareTo("")!=0) {
			result+=dataTestId;
		}
		if (getLabel().compareTo("")!=0) {
			result+=", " + getLabel();
		}
		return result;
	}
}
