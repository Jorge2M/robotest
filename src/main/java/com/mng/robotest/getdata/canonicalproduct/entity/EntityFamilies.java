package com.mng.robotest.getdata.canonicalproduct.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;


@JsonInclude(NON_EMPTY)
public class EntityFamilies implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String erpId;
	private List<EntityFamilyOnline> online;
	private List<String> subfamiliesOnline;
	
	public EntityFamilies() {
	}

	public String getErpId() {
		return erpId;
	}

	public void setErpId(String erpId) {
		this.erpId = erpId;
	}

	public List<EntityFamilyOnline> getOnline() {
		return online;
	}

	public void setOnline(List<EntityFamilyOnline> online) {
		this.online = online;
	}

	public List<String> getSubfamiliesOnline() {
		return subfamiliesOnline;
	}

	public void setSubfamiliesOnline(List<String> subfamiliesOnline) {
		this.subfamiliesOnline = subfamiliesOnline;
	}
}
