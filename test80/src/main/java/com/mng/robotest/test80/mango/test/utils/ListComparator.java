package com.mng.robotest.test80.mango.test.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.mng.robotest.test80.mango.test.utils.checkmenus.Label;

public class ListComparator {

	private final List<Label> listExpected;
	private final List<Label> listObtained;
	private final List<DuplaLabel> matchedLists;
	
	private ListComparator(List<Label> listExpected, List<Label> listObtained) {
		this.listExpected = listExpected;
		this.listObtained = listObtained;
		this.matchedLists = getMatchLists();
	}
	
	public static ListComparator getNew(List<Label> listExpected, List<Label> listObtained) {
		return (new ListComparator(listExpected, listObtained));
	}
	
	public List<DuplaLabel> getMatchedLists() {
		return matchedLists;
	}
	
	private List<DuplaLabel> getMatchLists() {
		List<DuplaLabel> listReturn = new ArrayList<>();
	    Iterator<Label> itExpected = listExpected.iterator();
	    Iterator<Label> itObtained = listObtained.iterator();
	    String labelExpectedAct = itExpected.next().getLabel();
	    String labelObtainedAct = itObtained.next().getLabel();
		do {
	    	if (labelExpectedAct.compareTo(labelObtainedAct)==0) {
	    		listReturn.add(DuplaLabel.getNew(labelExpectedAct, labelObtainedAct));
	    		if (itExpected.hasNext()) {
	    			labelExpectedAct = itExpected.next().getLabel();
	    		}
	    		if (itObtained.hasNext()) {
	    			labelObtainedAct = itObtained.next().getLabel();
	    		}
	    	} else {
	    		if (!listContainsLabel(listObtained, labelExpectedAct)) {
	    			listReturn.add(DuplaLabel.getNew(labelExpectedAct, null));
		    		if (itExpected.hasNext()) {
		    			labelExpectedAct = itExpected.next().getLabel();
		    		}
	    		} else {
		    		if (!listContainsLabel(listExpected, labelObtainedAct)) {
		    			listReturn.add(DuplaLabel.getNew(null, labelObtainedAct));
			    		if (itObtained.hasNext()) {
			    			labelObtainedAct = itObtained.next().getLabel();
			    		}
		    		} else {
			    		if (itExpected.hasNext()) {
			    			listReturn.add(DuplaLabel.getNew(labelExpectedAct, null));
			    			labelExpectedAct = itExpected.next().getLabel();
			    		}
			    		if (itObtained.hasNext()) {
			    			listReturn.add(DuplaLabel.getNew(null, labelObtainedAct));
			    			labelObtainedAct = itObtained.next().getLabel();
			    		}
			    		
			    		//,,,usar alguna función que retorne un elemento <Last> cuando se ejecuta un .next y no hay elementos
		    		}
	    		}
	    	}
	    }
		while (itExpected.hasNext() || itObtained.hasNext());

	    return listReturn;
	}
	
	private boolean listContainsLabel(List<Label> listLabels, String labelExpected) {
		for (Label label : listLabels) {
			if (label.getLabel().compareTo(labelExpected)==0) {
				return true;
			}
		}
		return false;
	}
	
//	public String makeHtmlComparative() {
//	    String result = "";
//	    result += "<div style=\"display:table;margin:3px;\">";
//	    result += "<div style=\"display:table-row;font-weight:bold;text-align:center;\">";
//	    result += "<div style=\"display:table-cell;border:solid;border-width:thin;padding-left:3px;padding-right:3px;\"> EXPECTED </div>";
//	    result += "<div style=\"display:table-cell;border:solid;border-width:thin;padding-left:3px;padding-right:3px;\"> OBTAINED </div>";
//	    result += "</div>";
//	    
//	    //Rows
//	    Iterator<Label> itExpected = listExpected.iterator();
//	    Iterator<Label> itObtained = listObtained.iterator();
//	    
//	    String labelExpectedAct = itExpected.next().getLabel();
//	    String labelObtainedAct = itObtained.next().getLabel();
//	    
//	    do {
//	    	result += "<div style=\"display:table-row;\">";
//	    	if (labelExpectedAct.compareTo(labelObtainedAct)==0) {
//	    		result += 
//	    			"<div style=\"display:table-cell;border:solid;border-width:thin;padding-left:3px;padding-right:3px;\">" +
//	    			labelExpectedAct + 
//	    			"</div>";
//	    		result+=
//	    			"<div style=\"display:table-cell;border:solid;border-width:thin;padding-left:3px;padding-right:3px;\">" +
//	    			labelObtainedAct + 
//	    			"</div>";
//	    	}
//	    	
//	    	result += "<div style=\"display:table-row;\">";
//	    	Label labelExpected = null;
//	    	Label labelObtained = null;
//	    	if (itExpected.hasNext()) {
//	    		labelExpected = itExpected.next();
//	    	}
//	    	if (itObtained.hasNext()) {
//	    		labelObtained = itObtained.next();
//	    	}
//	    	result += "<div style=\"display:table-cell;border:solid;border-width:thin;padding-left:3px;padding-right:3px;\">";
//	    }
//	    while (itExpected.hasNext() || itObtained.hasNext()) {
//	    
//	    while (it1.hasNext() || it2.hasNext()) {
//	        result += "<div style=\"display:table-row;\">";
//	        
//	        //Artículo obtained
//	        result += "<div style=\"display:table-cell;border:solid;border-width:thin;padding-left:3px;padding-right:3px;\">";
//	        if (itObtained.hasNext()) {
//	            result+=itObtained.next().toString();
//	        }
//	        result+="</div>";
//	        
//	        //Artículo expected
//	        result += "<div style=\"display:table-cell;border:solid;border-width:thin;padding-left:3px;padding-right:3px;\">";
//	        if (itExpected.hasNext()) {
//	            result+=itExpected.next().toString();
//	        }
//	        result+="</div>";
//	        result+="</div>";
//	    }
//	    
//	    result += "</div>";
//	    return (result);
//	}
	    
	static class DuplaLabel {
		public String label1;
		public String label2;
		
		private DuplaLabel(String label1, String label2) {
			this.label1 = label1;
			this.label2 = label2;
		}
		public static DuplaLabel getNew(String label1, String label2) {
			return (new DuplaLabel(label1, label2));
		}
	}
}
