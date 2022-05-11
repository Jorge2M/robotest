package com.mng.robotest.test.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mng.robotest.test.utils.checkmenus.Label;

public class ListComparator {

	private final List<? extends Label> listExpected;
	private final List<? extends Label> listObtained;
	private final List<DuplaLabel> matchedLists;
	private final static String MarkSurpassedList = "###";
	private boolean listsMatch = true;
	
	public enum Case {
		match, 
		nomatch_and_labelexpected_notexists_in_listobtained, 
		nomatch_and_labelobtained_notexists_in_listexpected, 
		nomatch_but_labelexpected_exists_in_listobtained,
		nomatch_but_labelobtained_exists_in_listexpected}
	
	private ListComparator(List<? extends Label> listExpected, List<? extends Label> listObtained) {
		this.listExpected = listExpected;
		this.listObtained = listObtained;
		this.matchedLists = getMatchLists();
	}
	
	public static ListComparator getNew(List<? extends Label> listExpected, List<? extends Label> listObtained) {
		return (new ListComparator(listExpected, listObtained));
	}
	
	public boolean listsMatch() {
		return listsMatch;
	}
	
	public String getHtml() {
		String result = getHtmlHead();
		for (DuplaLabel dupla : matchedLists) {
			result += getHtmlDupla(dupla);
		}
		return (result);
	}
	
	private String getHtmlHead() {
		return (
			"<div style=\"display:table;margin:3px;\">" +
			"<div style=\"display:table-row;font-weight:bold;text-align:center;\">" +
			"<div style=\"display:table-cell;border:solid;border-width:thin;padding-left:3px;padding-right:3px;\"> EXPECTED </div>" +
			"<div style=\"display:table-cell;border:solid;border-width:thin;padding-left:3px;padding-right:3px;\"> OBTAINED </div>" +
			"</div>");
	}
		
	private String getHtmlDupla(DuplaLabel dupla) {
		String  divRow = "<div style=\"display:table-row;color:#000000;\">";
		if (dupla.label1==null || dupla.label2==null ||
			dupla.label1.compareTo(dupla.label2)!=0) {
			divRow = "<div style=\"display:table-row;color:#8000ff;\">";
		}
		String result = "";
		result += divRow;
		result += "<div style=\"display:table-cell;border:solid;border-width:thin;padding-left:3px;padding-right:3px;\">";
		result += (dupla.label1!=null) ? dupla.label1 : "";
		result += "</div>";
		result += "<div style=\"display:table-cell;border:solid;border-width:thin;padding-left:3px;padding-right:3px;\">";
		result += (dupla.label2!=null) ? dupla.label2 : "";
		result += "</div>";
		result += "</div>";
		return result;
	}
	
	List<DuplaLabel> getMatchedLists() {
		return matchedLists;
	}
	
	private List<DuplaLabel> getMatchLists() {
		List<DuplaLabel> listReturn = new ArrayList<>();
		Iterator<? extends Label> itExpected = listExpected.iterator();
		Iterator<? extends Label> itObtained = listObtained.iterator();
		String labelExpectedAct = getNextLabel(itExpected);
		String labelObtainedAct = getNextLabel(itObtained);
		do {
			Case caseObtained = getCase(labelExpectedAct, labelObtainedAct);
			switch (caseObtained) {
			case match: 
				listReturn.add(DuplaLabel.getNew(labelExpectedAct, labelObtainedAct));
				labelExpectedAct = getNextLabel(itExpected);
				labelObtainedAct = getNextLabel(itObtained);
				break;
			case nomatch_and_labelexpected_notexists_in_listobtained: 
				listsMatch = false;
				listReturn.add(DuplaLabel.getNew(labelExpectedAct, null));
				labelExpectedAct = getNextLabel(itExpected);
				break;
			case nomatch_and_labelobtained_notexists_in_listexpected:
				listsMatch = false;
				listReturn.add(DuplaLabel.getNew(null, labelObtainedAct));
				labelObtainedAct = getNextLabel(itObtained);
				break;
			case nomatch_but_labelexpected_exists_in_listobtained:
				if (!surpassedList(labelObtainedAct)) {
					labelObtainedAct = getNextLabel(itObtained);
				} else {
					labelExpectedAct = getNextLabel(itExpected);
				}
				break;
			case nomatch_but_labelobtained_exists_in_listexpected:
				if (!surpassedList(labelExpectedAct)) {
					labelExpectedAct = getNextLabel(itExpected);
				} else {
					labelObtainedAct = getNextLabel(itObtained);
				}
			}
		}
		while (!surpassedList(labelExpectedAct) || !surpassedList(labelObtainedAct));

		return listReturn;
	}
	
	private Case getCase(String labelExpectedAct, String labelObtainedAct) {
		if (labelExpectedAct.compareTo(labelObtainedAct)==0) {
			return Case.match;
		} 
		if (!surpassedList(labelExpectedAct) &&
			!listContainsLabel(listObtained, labelExpectedAct)) {
			return Case.nomatch_and_labelexpected_notexists_in_listobtained;
		} 
		if (!surpassedList(labelObtainedAct) &&
			!listContainsLabel(listExpected, labelObtainedAct)) {
			return Case.nomatch_and_labelobtained_notexists_in_listexpected;
		}
		if (listContainsLabel(listObtained, labelExpectedAct)) {
			return Case.nomatch_but_labelexpected_exists_in_listobtained;
		}
		if (listContainsLabel(listExpected, labelObtainedAct)) {
			return Case.nomatch_but_labelobtained_exists_in_listexpected;
		}
		return Case.match;
	}
	
	private String getNextLabel(Iterator<? extends Label> it) {
		if (it.hasNext()) {
			return (it.next().getLabel());
		}
		return MarkSurpassedList;
	}
	
	private boolean surpassedList(String label) {
		return (MarkSurpassedList.compareTo(label)==0);
	}
	
	private boolean listContainsLabel(List<? extends Label> listLabels, String labelExpected) {
		for (Label label : listLabels) {
			if (label.getLabel().compareTo(labelExpected)==0) {
				return true;
			}
		}
		return false;
	}
	  
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
