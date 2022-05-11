package com.mng.robotest.domains.apiproduct.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FamiliesRedis implements Serializable {
	
	private static final long serialVersionUID = -1144854884361085124L;
	
	@JsonProperty("erpId") String familyErpId;
    @JsonProperty("online") List<FamilyOnlineRedis> familiesOnline;
    List<String> subfamiliesOnline;
    
    public FamiliesRedis() { }
    
    public FamiliesRedis(String familyErpId, List<FamilyOnlineRedis> familiesOnline, List<String> subfamiliesOnline) {
		super();
		this.familyErpId = familyErpId;
		this.familiesOnline = familiesOnline;
		this.subfamiliesOnline = subfamiliesOnline;
	}
	public String getFamilyErpId() {
    	return familyErpId;
    }
    public void setFamilyErpId(String familyErpId) {
    	this.familyErpId = familyErpId;
    }
    public List<FamilyOnlineRedis> getFamiliesOnline() {
    	return familiesOnline;
    }
    public void setFamiliesOnline(List<FamilyOnlineRedis> familiesOnline) {
    	this.familiesOnline = familiesOnline;
    }
    public List<String> getSubfamiliesOnline() {
    	return subfamiliesOnline;
    }
    public void setSubfamiliesOnline(List<String> subfamiliesOnline) {
    	this.subfamiliesOnline = subfamiliesOnline;
    }
    
}

