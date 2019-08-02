package com.mng.robotest.test80.mango.test.utils.checkmenus;

public class DataScreenMenu implements Label {

	private String dataGaLabel;
	private String label;
	
	private DataScreenMenu() {}
	public static DataScreenMenu getNew() {
		return new DataScreenMenu();
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
	
	public void setLabel(String label) {
		this.label = label;
		if (!isDataGaLabelValid()) {
			setDataGaLabel("-" + label);
		}
	}
	
	public boolean isDataGaLabelValid() {
		return (dataGaLabel!=null && dataGaLabel.compareTo("")!=0);
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
        	c.getLabel().compareTo(this.getLabel())==0);
    } 
}
