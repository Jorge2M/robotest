package com.mng.robotest.tests.domains.votfconsole.utils;

import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;

public class ChecksResultWithStringData extends ChecksTM {
	String data;
	private ChecksResultWithStringData() {
		super();
	}
	public static ChecksResultWithStringData getNew() {
		return new ChecksResultWithStringData();
	}
	
	public String getData() {
		return data;
	}
	
	public void setData(String data) {
		this.data = data;
	}
}
