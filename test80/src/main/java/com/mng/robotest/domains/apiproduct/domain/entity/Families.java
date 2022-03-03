package com.mng.robotest.domains.apiproduct.domain.entity;

import java.util.List;

public class Families {
	
	int familyErpId;
    List<FamilyOnline> familiesOnline;
    List<String> subfamiliesOnline;
    
	public int getFamilyErpId() {
		return familyErpId;
	}
	public void setFamilyErpId(int familyErpId) {
		this.familyErpId = familyErpId;
	}
	public List<FamilyOnline> getFamiliesOnline() {
		return familiesOnline;
	}
	public void setFamiliesOnline(List<FamilyOnline> familiesOnline) {
		this.familiesOnline = familiesOnline;
	}
	public List<String> getSubfamiliesOnline() {
		return subfamiliesOnline;
	}
	public void setSubfamiliesOnline(List<String> subfamiliesOnline) {
		this.subfamiliesOnline = subfamiliesOnline;
	}
	
}
