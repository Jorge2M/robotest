package com.mng.robotest.test80;

import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.TreeSet;
import org.junit.Test;

import com.mng.testmaker.utils.otras.Channel;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import static com.mng.robotest.test80.mango.test.data.PaisShop.*;

public class TestTest80mng {

    @Test
    public void testListPagosEspanaShopDesktop() throws Exception {
        String countrys = España.getCodigoPais();
        
        //Code to test
        TreeSet<String> TreeSetPagoNames = Test80mng.getListPagoFilterNames(countrys, Channel.desktop, AppEcom.shop, false);
        
        assertEquals(6, TreeSetPagoNames.size());
        TreeSet<String> TreeSetExpected = new TreeSet<>(Arrays.asList(
        	"VISA", 
        	"MASTERCARD", 
        	"VISA ELECTRON", 
        	"AMERICAN EXPRESS", 
        	"TARJETA MANGO", 
        	"PAYPAL"));
        assertTrue(TreeSetPagoNames.containsAll(TreeSetExpected));
    }
    
    @Test
    public void testListPagosManyCountrysOutletMobil() throws Exception {
        String countrys = 
        	ChannelIslands.getCodigoPais() + "," +
        	Hungary.getCodigoPais(); 
        
        //Code to test
        TreeSet<String> TreeSetPagoNames = Test80mng.getListPagoFilterNames(countrys, Channel.movil_web, AppEcom.outlet, false);
        
        assertEquals(6, TreeSetPagoNames.size());
        TreeSet<String> TreeSetExpected = new TreeSet<>(Arrays.asList(
        	"VISA", 
        	"MASTERCARD", 
        	"VISA ELECTRON", 
        	"PAYPAL", 
        	"Utánvételes Fizetés", 
        	"AMERICAN EXPRESS"));
        assertTrue(TreeSetPagoNames.containsAll(TreeSetExpected));
    }
}
