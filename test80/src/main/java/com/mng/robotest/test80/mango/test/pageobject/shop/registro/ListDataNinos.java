package com.mng.robotest.test80.mango.test.pageobject.shop.registro;

import java.util.ArrayList;

@SuppressWarnings("javadoc")
public class ListDataNinos {
    private ArrayList<DataNino> listNinos = new ArrayList<>();
    
    public void add(DataNino dataNino) {
        this.listNinos.add(dataNino);
    }
    
    public ArrayList<DataNino> getListNinos() {
        return this.listNinos;
    }
    
    public String getFormattedHTMLData() {
        String dataToReturn = "";
        for (DataNino dataNino : this.listNinos) {
            dataToReturn+=dataNino.getFormattedHTMLData() + "<br>";
        }
        
        return (replaceLast(dataToReturn, "<br>", ""));
    }
    
    private static String replaceLast(String text, String regex, String replacement) {
        return text.replaceFirst("(?s)"+regex+"(?!.*?"+regex+")", replacement);
    }
}

