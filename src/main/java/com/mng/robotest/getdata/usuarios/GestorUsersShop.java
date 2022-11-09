package com.mng.robotest.getdata.usuarios;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;

import com.mng.robotest.getdata.usuarios.UserShop.StateUser;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.utils.PaisGetter;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets.SecretType;


public class GestorUsersShop {

	private static CopyOnWriteArrayList<UserShop> listTestPerformanceUsers;
	private static final int MINUTES_FOR_USER_LIBERATION = 30;

	public GestorUsersShop() {
		if (listTestPerformanceUsers==null) {
			storeTestPerformanceUsers();
		}
		releaseTestPerformanceUsedUsers();
	}
	
	public static UserShop getUser() {
		return getUser(PaisShop.ESPANA);
	}
	
	public static UserShop getUser(PaisShop paisShop) {
		return new GestorUsersShop().getUserMaked(paisShop);
	}
	
	public UserShop getUserMaked(PaisShop paisShop) {
		if (paisShop==PaisShop.ESPANA) {
			return getTestPerformanceUser();
		} else {
			return getCountryMakedUser(paisShop);
		}
	}
	
	private UserShop getCountryMakedUser(PaisShop paisShop) {
		Pais pais = PaisGetter.from(paisShop);
		String user = String.format("e2e.%s.test@mango.com", pais.getCodigo_alf().toLowerCase());
		return new UserShop(user, "hsXPv7rUoYw3QnMKRhPT");
	}
	
	private UserShop getTestPerformanceUser() {
		UserShop userBusyOldest = null;
		Integer[] listRandomInts = getRandomListNotRepeated(listTestPerformanceUsers.size());
		for (Integer index : listRandomInts) {
			UserShop user = listTestPerformanceUsers.get(index.intValue());
			if (user.stateUser==StateUser.FREE) {
				user.stateUser = StateUser.BUSY;
				user.dateLastCheckout = Calendar.getInstance();
				return user;
			}
			
			if (userBusyOldest==null || user.dateLastCheckout.before(userBusyOldest.dateLastCheckout)) {
				userBusyOldest = user;
			}
		}
		
		if (userBusyOldest!=null) {
			userBusyOldest.dateLastCheckout = Calendar.getInstance();
		}
		
		return userBusyOldest;
	}
	
	private void storeTestPerformanceUsers() {
		String PASSWORD_TEST_PERFORMANCE = 
				GetterSecrets.factory()
					.getCredentials(SecretType.SHOP_PERFORMANCE_USER)
					.getPassword();
		
		listTestPerformanceUsers = new CopyOnWriteArrayList<>();
		for (int i=1; i<=50; i++) {
			String number = String.valueOf(i);
			if (i<10) {
				number = "0" + number;
			}
			listTestPerformanceUsers.add(new UserShop("test.performance" + number + "@mango.com", PASSWORD_TEST_PERFORMANCE));
		}
	}
	
	private void releaseTestPerformanceUsedUsers() {
		Calendar hoy = Calendar.getInstance();
		for (UserShop user : listTestPerformanceUsers) {
			if (user.stateUser==StateUser.BUSY) {
				Calendar dateToLiberateUser = (Calendar)user.dateLastCheckout.clone();
				dateToLiberateUser.add(Calendar.MINUTE, MINUTES_FOR_USER_LIBERATION);
				if (hoy.after(dateToLiberateUser)) {
					user.stateUser=StateUser.FREE;
				}
			}
		}
	}
	
	private Integer[] getRandomListNotRepeated(int size) {
		Integer[] arr = new Integer[size];
		for (int i = 0; i < arr.length; i++)
			arr[i] = Integer.valueOf(i);

		Collections.shuffle(Arrays.asList(arr));
		return arr;
	}
	
	//For UnitTest purposes
	void addUserShop(UserShop userShop) {
		if (listTestPerformanceUsers==null) {
			listTestPerformanceUsers = new CopyOnWriteArrayList<>();
		}
		listTestPerformanceUsers.add(userShop);
	}
	
	//For UnitTest purposes
	void reset() {
		listTestPerformanceUsers = null;
	}
}
