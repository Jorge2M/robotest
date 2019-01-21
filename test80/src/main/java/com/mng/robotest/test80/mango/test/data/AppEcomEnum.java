package com.mng.robotest.test80.mango.test.data;

import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;

@SuppressWarnings({ "javadoc", "unused" })
public class AppEcomEnum {
    public enum AppEcom {
        shop,
        outlet,
        votf
    }
    
    public static AppEcom getAppEcom(String application) {
        return (AppEcom.valueOf(application));
    }
}
