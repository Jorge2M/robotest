package com.mng.robotest.test80;

import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.TreeSet;

import org.junit.Test;

import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;

@SuppressWarnings("javadoc")
public class TestTest80mng {

    @Test
    public void testListPagosEspanaShopDesktop() throws Exception {
        String listCountrysCommaSeparated = "001"; //Spain
        TreeSet<String> TreeSetPagoNames = Test80mng.getListPagoFilterNames(listCountrysCommaSeparated, Channel.desktop, AppEcom.shop, false/*isEmpl*/); //Code to test
        assertEquals(6, TreeSetPagoNames.size());
        TreeSet<String> TreeSetExpected = new TreeSet<>(Arrays.asList("VISA", "MASTERCARD", "VISA ELECTRON", "AMERICAN EXPRESS", "TARJETA MANGO", "PAYPAL"));
        assertTrue(TreeSetPagoNames.containsAll(TreeSetExpected));
    }
    
    @Test
    public void testListPagosManyCountrysOutletMobil() throws Exception {
        String listCountrysCommaSeparated = "019,064"; //United Kingdom, Hungary 
        TreeSet<String> TreeSetPagoNames = Test80mng.getListPagoFilterNames(listCountrysCommaSeparated, Channel.movil_web, AppEcom.outlet, false/*isEmpl*/); //Code to test
        assertEquals(6, TreeSetPagoNames.size());
        TreeSet<String> TreeSetExpected = new TreeSet<>(Arrays.asList("VISA", "MASTERCARD", "VISA ELECTRON", "PAYPAL", "Utánvételes Fizetés", "AMERICAN EXPRESS"));
        assertTrue(TreeSetPagoNames.containsAll(TreeSetExpected));
    }
}
