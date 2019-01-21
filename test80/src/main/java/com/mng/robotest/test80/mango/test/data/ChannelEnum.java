package com.mng.robotest.test80.mango.test.data;

@SuppressWarnings("javadoc")
public class ChannelEnum {
    public enum Channel {
        desktop,
        movil_web
    }
    
    public static Channel getChannel(String channel) {
        switch (channel) {
        case "desktop":
            return Channel.desktop;
        case "movil_web":
            return Channel.movil_web;
        default:
            break;
        }
        
        return Channel.desktop;
    }
}
