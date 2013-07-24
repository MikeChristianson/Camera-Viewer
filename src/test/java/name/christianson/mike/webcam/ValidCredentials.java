package name.christianson.mike.webcam;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

class ValidCredentials extends Authenticator {

    private static final PasswordAuthentication PASSWORD_AUTHENTICATION = new PasswordAuthentication("testaccount", "password".toCharArray());

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return PASSWORD_AUTHENTICATION;
    }
}
