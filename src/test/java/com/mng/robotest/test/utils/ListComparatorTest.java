package com.mng.robotest.test.utils;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.mng.robotest.test.utils.ListComparator.DuplaLabel;
import com.mng.robotest.test.utils.checkmenus.Label;

public class ListComparatorTest {
	
	public enum Item implements Label {
		Mario,
		Zelda,
		Link,
		Bowser,
		Yoshi,
		Kirby,
		Toad,
		Peach;
		
		@Override
		public String getLabel() {
			return this.name();
		}
		
		public static Item getItem(String value) {
			if (value!=null) {
				return valueOf(value);
			}
			return null;
		}
	}

	@Test
	public void testGetMatchedLists() {
		List<Label> list1 = Arrays.asList(Item.Mario, Item.Link, Item.Bowser, Item.Yoshi, Item.Toad);
		List<Label> list2 = Arrays.asList(Item.Zelda, Item.Bowser, Item.Yoshi, Item.Kirby, Item.Peach, Item.Toad);
		
		//Code to Test
		ListComparator comparator = ListComparator.getNew(list1, list2);
		
		List<DuplaLabel> result = comparator.getMatchedLists();
		assertTrue(getItem1(result.get(0))==Item.Mario && getItem2(result.get(0))==null); 
		assertTrue(getItem1(result.get(1))==Item.Link && getItem2(result.get(1))==null); 
		assertTrue(getItem1(result.get(2))==null && getItem2(result.get(2))==Item.Zelda); 
		assertTrue(getItem1(result.get(3))==Item.Bowser && getItem2(result.get(3))==Item.Bowser); 
		assertTrue(getItem1(result.get(4))==Item.Yoshi && getItem2(result.get(4))==Item.Yoshi); 
		assertTrue(getItem1(result.get(5))==null && getItem2(result.get(5))==Item.Kirby);
		assertTrue(getItem1(result.get(6))==null && getItem2(result.get(6))==Item.Peach); 
		assertTrue(getItem1(result.get(7))==Item.Toad && getItem2(result.get(7))==Item.Toad); 
	}
	
	private Item getItem1(DuplaLabel dupla) {
		return Item.getItem(dupla.label1);
	}
	
	private Item getItem2(DuplaLabel dupla) {
		return Item.getItem(dupla.label2);
	}
}
