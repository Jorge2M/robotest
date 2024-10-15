package com.mng.robotest.tests.repository.usuarios;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;

import com.mng.robotest.tests.repository.secrets.GetterSecrets;
import com.mng.robotest.tests.repository.secrets.GetterSecrets.SecretType;
import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.data.PaisShop;
import com.mng.robotest.testslegacy.utils.PaisGetter;

import static com.mng.robotest.tests.repository.usuarios.UserShop.StateUser.*;
import static com.mng.robotest.testslegacy.data.PaisShop.ESPANA;

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
		return getUser(ESPANA);
	}
	
	public static UserShop getUser(PaisShop paisShop) {
		return new GestorUsersShop().getUserMaked(paisShop);
	}
	
	public UserShop getUserMaked(PaisShop paisShop) {
		if (paisShop==ESPANA) {
			return getTestPerformanceUser();
		} else {
			return getCountryMakedUser(paisShop);
		}
	}
	
	private UserShop getCountryMakedUser(PaisShop paisShop) {
		Pais pais = PaisGetter.from(paisShop);
		String user = String.format("e2e.%s.test@mango.com", pais.getCodigoAlf().toLowerCase());
		return new UserShop(user, "hsXPv7rUoYw3QnMKRhPT");
	}
	
	private UserShop getTestPerformanceUser() {
		UserShop userBusyOldest = null;
		Integer[] listRandomInts = getRandomListNotRepeated(listTestPerformanceUsers.size());
		for (Integer index : listRandomInts) {
			var user = listTestPerformanceUsers.get(index.intValue());
			if (user.getStateUser()==FREE) {
				user.setStateUser(BUSY);
				user.setDateLastCheckout(Calendar.getInstance());
				return user;
			}
			
			if (userBusyOldest==null || user.before(userBusyOldest)) {
				userBusyOldest = user;
			}
		}
		
		if (userBusyOldest!=null) {
			userBusyOldest.setDateLastCheckout(Calendar.getInstance());
		}
		
		return userBusyOldest;
	}
	
	private static synchronized void storeTestPerformanceUsers() {
		String passwordTestPerformance = 
				GetterSecrets.factory()
					.getCredentials(SecretType.SHOP_PERFORMANCE_USER)
					.getPassword();
		
		listTestPerformanceUsers = new CopyOnWriteArrayList<>();
		for (int i=1; i<=50; i++) {
			String number = String.valueOf(i);
			if (i<10) {
				number = "0" + number;
			}
			
			//TODO los usuarios test.performance01@mango.com y 10 han perdido la password mango457
			//así que mientras que no podamos recuperarla los excluimos
			if ("01".compareTo(number)!=0 && "10".compareTo(number)!=0) {
				listTestPerformanceUsers.add(new UserShop("test.performance" + number + "@mango.com", passwordTestPerformance));
			}
		}
	}
	
	private void releaseTestPerformanceUsedUsers() {
		var hoy = Calendar.getInstance();
		for (var user : listTestPerformanceUsers) {
			if (user.getStateUser()==BUSY) {
				var dateToLiberateUser = (Calendar)user.getDateLastCheckout().clone();
				dateToLiberateUser.add(Calendar.MINUTE, MINUTES_FOR_USER_LIBERATION);
				if (hoy.after(dateToLiberateUser)) {
					user.setStateUser(FREE);
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
	static synchronized void addUserShop(UserShop userShop) {
		if (listTestPerformanceUsers==null) {
			listTestPerformanceUsers = new CopyOnWriteArrayList<>();
		}
		listTestPerformanceUsers.add(userShop);
	}
	
	//For UnitTest purposes
	static synchronized void reset() {
		listTestPerformanceUsers = null;
	}
	
}
