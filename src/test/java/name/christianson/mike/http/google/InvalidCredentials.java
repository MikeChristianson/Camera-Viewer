package name.christianson.mike.http.google;

import com.google.api.client.http.BasicAuthentication;

public class InvalidCredentials {
    private static final BasicAuthentication invalidAuthentication = new BasicAuthentication("testaccount", "badpassword");

    public static BasicAuthentication getInstance() {
        return invalidAuthentication;
    }
}