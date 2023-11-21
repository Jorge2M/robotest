package com.mng.robotest.tests.repository.usuarios;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;

import static com.mng.robotest.tests.repository.usuarios.UserShop.StateUser.*;

public class GestorUsersShopIT {

    @Test
    public void testCheckoutFromGestorWithFreeUsers() {
        new GestorUsersShop().reset();
        UserShop userShop = GestorUsersShop.getUser(); //Code to test
        assertEquals("Checkout gets User in state " + BUSY, BUSY, userShop.getStateUser());
        
        int intervalSeconds = 5;
        Calendar beforeCurrentDate = Calendar.getInstance();
        beforeCurrentDate.add(Calendar.SECOND, intervalSeconds*-1);
        Calendar afterCurrentDate = Calendar.getInstance();
        afterCurrentDate.add(Calendar.SECOND, intervalSeconds);
        assertTrue("Checkout gets User with date checkout after currentdate - " + intervalSeconds + " seconds", userShop.getDateLastCheckout().after(beforeCurrentDate));
        assertTrue("Checkout gets User with date checkout before currentdate + " + intervalSeconds + " seconds", userShop.getDateLastCheckout().before(afterCurrentDate));
    }
    
    @Test
    public void testCheckoutFromGestorWithoutFreeUsers() {
    	GestorUsersShop gestorUsers = new GestorUsersShop();
    	gestorUsers.reset();
        Calendar beforeCurrentDate = Calendar.getInstance();
        beforeCurrentDate.add(Calendar.SECOND, -5);
        GestorUsersShop.addUserShop(new UserShop("user.oldest@mango.com", "", BUSY, beforeCurrentDate));
        GestorUsersShop.addUserShop(new UserShop("user.newest@mango.com", "", BUSY));
        
        UserShop userShop = GestorUsersShop.getUser(); //Code to test
        assertEquals("Checkout gets User in state " + BUSY, BUSY, userShop.getStateUser());
        assertEquals("Checkout gets Busy-User with oldest data checkout", "user.oldest@mango.com", userShop.getUser());
        assertTrue("The dateCheckout of the user is stablished to the current date", userShop.getDateLastCheckout().after(beforeCurrentDate));
    }
    
    @Test
    public void testCheckoutGetsDiferentUsers() {
    	GestorUsersShop gestorUsers = new GestorUsersShop();
    	gestorUsers.reset();
    	GestorUsersShop.addUserShop(new UserShop("user1.test@mango.com", "", FREE));
    	GestorUsersShop.addUserShop(new UserShop("user2.test@mango.com", "", FREE));
    	GestorUsersShop.addUserShop(new UserShop("user3.test@mango.com", "", FREE));

        Set<UserShop> setUsers = new HashSet<>(); //Set not adds duplicated elements
        setUsers.add(GestorUsersShop.getUser()); //Code to test
        setUsers.add(GestorUsersShop.getUser()); //Code to test
        setUsers.add(GestorUsersShop.getUser()); //Code to test
        
        assertEquals("Checkout gets 3 diferents Users", 3, setUsers.size());
    }
}
