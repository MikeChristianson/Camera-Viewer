package name.christianson.mike.http.google;

import com.google.api.client.http.BasicAuthentication;

public class AuthenticatorFactory {
    public static BasicAuthentication validCredentials() {
        return ValidCredentials.getInstance();
    }

    public static BasicAuthentication invalidCredentials() {
        return InvalidCredentials.getInstance();
    }
}
