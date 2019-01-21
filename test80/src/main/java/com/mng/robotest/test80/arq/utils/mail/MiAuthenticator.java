package com.mng.robotest.test80.arq.utils.mail;

import javax.mail.PasswordAuthentication;

@SuppressWarnings("javadoc")
public class MiAuthenticator extends javax.mail.Authenticator{	
    private PasswordAuthentication authentication;

    public MiAuthenticator(String user, String pasword) {
        String username = user;//user@domain.net
        String password = pasword;
        this.authentication = new PasswordAuthentication(username, password);
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return this.authentication;
    }
}
