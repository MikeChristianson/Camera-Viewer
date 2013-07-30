package name.christianson.mike.http.google;

import com.google.api.client.http.BasicAuthentication;

public class ValidCredentials {
    private static final BasicAuthentication validAuthentication = new BasicAuthentication("testaccount", "password");

    public static BasicAuthentication getInstance() {
        return validAuthentication;
    }
}