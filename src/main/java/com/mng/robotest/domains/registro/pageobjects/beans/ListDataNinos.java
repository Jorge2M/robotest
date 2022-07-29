package com.mng.robotest.domains.registro.pageobjects.beans;

import java.util.ArrayList;


public class ListDataNinos {
	private ArrayList<DataNino> listNinos = new ArrayList<>();
	
	public void add(DataNino dataNino) {
		this.listNinos.add(dataNino);
	}
	
	public ArrayList<DataNino> getListNinos() {
		return this.listNinos;
	}
	
	public String getFormattedHTMLData() {
		String dataToReturn = "";
		for (DataNino dataNino : this.listNinos) {
			dataToReturn+=dataNino.getFormattedHTMLData() + "<br>";
		}
		
		return (replaceLast(dataToReturn, "<br>", ""));
	}
	
	private static String replaceLast(String text, String regex, String replacement) {
		return text.replaceFirst("(?s)"+regex+"(?!.*?"+regex+")", replacement);
	}
}

