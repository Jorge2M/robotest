package com.mng.robotest.test80.mango.test.getdata.usuarios;

import java.util.Calendar;


public class UserShop {
	public enum StateUser {free, busy}
	public String user;
	public String password;
	public StateUser stateUser = StateUser.free;
	public Calendar dateLastCheckout = Calendar.getInstance();
	
	public UserShop(String user, String password, StateUser stateUser) {
		this.user = user;
		this.password = password;
		this.stateUser = stateUser;
	}
	
	public UserShop(String user, String password) {
		this.user = user;
		this.password = password;
	}
	
	public UserShop(String user, String password, StateUser stateUser, Calendar calendar) {
		this.user = user;
		this.password = password;
		this.stateUser = stateUser;
		this.dateLastCheckout = calendar;
	}
}
