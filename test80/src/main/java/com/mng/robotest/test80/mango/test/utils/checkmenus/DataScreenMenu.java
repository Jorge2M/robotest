package com.mng.robotest.test80.mango.test.utils.checkmenus;

import org.openqa.selenium.WebElement;

public class DataScreenMenu implements Label, Comparable<DataScreenMenu> {

	private String dataGaLabel;
	private String label;
	private String id;
	
	private DataScreenMenu() {}
	public static DataScreenMenu getNew() {
		return new DataScreenMenu();
	}
	
	public static DataScreenMenu from(WebElement menu) {
		DataScreenMenu dataMenu = new DataScreenMenu();
		dataMenu.setId(menu.getAttribute("id"));
		dataMenu.setDataGaLabel(menu.getAttribute("data-label"));
		if (dataMenu.isDataGaLabelValid()) {
			dataMenu.setLabel(menu.getText().replace("New!", "").trim());
		}
		return dataMenu;
	}
	
	public static DataScreenMenu getNew(String dataGaLabel, String label) {
		DataScreenMenu dataMenu = new DataScreenMenu();
		dataMenu.setDataGaLabel(dataGaLabel);
		dataMenu.setLabel(label);
		return dataMenu;
	}
	
	public static DataScreenMenu getNew(String label) {
		DataScreenMenu dataMenu = new DataScreenMenu();
		dataMenu.setLabel(label);
		return dataMenu;
	}
	
	public String getDataGaLabel() {
		return this.dataGaLabel;
	}
	
	@Override
	public String getLabel() {
		return this.label;
	}
	
	public void setDataGaLabel(String dataGaLabel) {
		this.dataGaLabel = dataGaLabel;
	}
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setLabel(String label) {
		this.label = label;
		if (!isDataGaLabelValid()) {
			//setDataGaLabel("-" + label);
			setDataGaLabel(label);
		}
	}
	
	public boolean isDataGaLabelValid() {
		return (dataGaLabel!=null && dataGaLabel.compareTo("")!=0);
	}
	
	@Override
	public int compareTo(DataScreenMenu dataMenu) {
	  return (dataGaLabel.compareTo(dataMenu.dataGaLabel));
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
        	c.dataGaLabel.compareTo(this.dataGaLabel)==0 &&
        	c.getLabel().toLowerCase().compareTo(this.getLabel().toLowerCase())==0);
    } 
    
    @Override
    public int hashCode() {
        return this.dataGaLabel.hashCode();
    }
    
    @Override
    public String toString() {
    	String result = "";
    	if (dataGaLabel.compareTo("")!=0) {
    		result+=dataGaLabel;
    	}
    	if (getLabel().compareTo("")!=0) {
    		result+=", " + getLabel();
    	}
    	return result;
    }
}
