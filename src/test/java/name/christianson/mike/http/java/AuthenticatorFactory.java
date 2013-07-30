package name.christianson.mike.http.java;

import java.net.Authenticator;

class AuthenticatorFactory {
    public static Authenticator validCredentials() {
        return new ValidCredentials();
    }

    public static Authenticator invalidCredentials() {
        return new InvalidCredentials();
    }
}
