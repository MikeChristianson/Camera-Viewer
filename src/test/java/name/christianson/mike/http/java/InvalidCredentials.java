package name.christianson.mike.http.java;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

class InvalidCredentials extends Authenticator {

    private static final PasswordAuthentication PASSWORD_AUTHENTICATION = new PasswordAuthentication("testaccount", "badpassword".toCharArray());

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return PASSWORD_AUTHENTICATION;
    }
}
