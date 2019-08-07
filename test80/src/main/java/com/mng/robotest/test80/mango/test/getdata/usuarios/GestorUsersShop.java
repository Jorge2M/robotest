package com.mng.robotest.test80.mango.test.getdata.usuarios;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;

import com.mng.robotest.test80.mango.test.getdata.usuarios.UserShop.StateUser;


public class GestorUsersShop {

    public static CopyOnWriteArrayList<UserShop> ListUsers;
    public static final int minutesForUserLiberation = 30;
    
    public static void setGestorUsersShopIfVoid() {
        if (ListUsers==null) {
            ListUsers = new CopyOnWriteArrayList<>();
            //ListUsers.add(new UserShop("listablanca003@mango.com", "sirjorge74"));

            //Tenemos 50 usuarios de este tipo 
            for (int i=1; i<=50; i++) {
                String number = String.valueOf(i);
                if (i<10) {
                    number = "0" + number;
                }
                ListUsers.add(new UserShop("test.performance" + number + "@mango.com", "Mango123"));
            }
        }
    }
    
    public static UserShop checkoutBestUserForNewTestCase() {
        setGestorUsersShopIfVoid();
        liberateUsersDependingDate();
        UserShop userBusyOldest = null;
        
        //Recorreremos la lista de usuarios mediante un índice aleatorio de modo que en ejecuciones paralelas de las suites de test
        //sea más difícil obtener el mismo usuario 
        Integer[] listRandomInts = getRandomListNotRepeated(ListUsers.size());
        for (Integer index : listRandomInts) {
            UserShop user = ListUsers.get(index.intValue());
            if (user.stateUser==StateUser.free) {
                user.stateUser = StateUser.busy;
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
    
    public static void liberateUsersDependingDate() {
        Calendar hoy = Calendar.getInstance();
        for (UserShop user : ListUsers) {
            if (user.stateUser==StateUser.busy) {
                Calendar dateToLiberateUser = (Calendar)user.dateLastCheckout.clone();
                dateToLiberateUser.add(Calendar.MINUTE, minutesForUserLiberation);
                if (hoy.after(dateToLiberateUser)) {
                    user.stateUser=StateUser.free;
                }
            }
        }
    }
    
    private static Integer[] getRandomListNotRepeated(int size) {
        Integer[] arr = new Integer[size];
        for (int i = 0; i < arr.length; i++)
            arr[i] = Integer.valueOf(i);

        Collections.shuffle(Arrays.asList(arr));
        return arr;
    }
    
    /**
     * Añadir usuario. Básicamete para dar soporte a los Tests
     */
    public static void addUserShop(UserShop userShop) {
        if (ListUsers==null) {
            ListUsers = new CopyOnWriteArrayList<>();
        }
        ListUsers.add(userShop);
    }
    
    public static void reset() {
        ListUsers = null;
    }
}
