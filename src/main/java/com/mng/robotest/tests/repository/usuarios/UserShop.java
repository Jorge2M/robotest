package com.mng.robotest.tests.repository.usuarios;

import java.util.Calendar;


public class UserShop {
	
	public enum StateUser { FREE, BUSY }
	
	private final String user;
	private final String password;
	private StateUser stateUser;
	private Calendar dateLastCheckout;
	
	public UserShop(String user, String password, StateUser stateUser) {
		this.user = user;
		this.password = password;
		this.stateUser = stateUser;
		dateLastCheckout = Calendar.getInstance();
	}
	
	public UserShop(String user, String password) {
		this.user = user;
		this.password = password;
		this.stateUser = StateUser.FREE;
		this.dateLastCheckout = Calendar.getInstance();
	}
	
	public UserShop(String user, String password, StateUser stateUser, Calendar calendar) {
		this.user = user;
		this.password = password;
		this.stateUser = stateUser;
		this.dateLastCheckout = calendar;
	}

	public boolean before(UserShop user) {
		return getDateLastCheckout().before(user.getDateLastCheckout());
	}
	
	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

	public StateUser getStateUser() {
		return stateUser;
	}
	public void setStateUser(StateUser stateUser) {
		this.stateUser = stateUser;
	}	

	public Calendar getDateLastCheckout() {
		return dateLastCheckout;
	}
	public void setDateLastCheckout(Calendar dateLastCheckout) {
		this.dateLastCheckout = dateLastCheckout;
	}	
	
}
