package name.christianson.mike.http.google;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class GoogleHttpClientLearningTest {
    private static final HttpRequestFactory REQUEST_FACTORY = new NetHttpTransport().createRequestFactory();
    private static final GenericUrl VIDEO_URL = new GenericUrl("http://webcam-nw/video.cgi");
    private static final GenericUrl IMAGE_URL = new GenericUrl("http://webcam-nw/image.jpg");
    private HttpRequest videoRequest;
    private HttpRequest imageRequest;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws IOException {
        videoRequest = REQUEST_FACTORY.buildGetRequest(VIDEO_URL);
        imageRequest = REQUEST_FACTORY.buildGetRequest(IMAGE_URL);
    }

    @Test
    public void authenticationRequired() throws Exception {
        thrown.expect(HttpResponseException.class);
        thrown.expectMessage("401 Authorization Required");
        videoRequest.execute();
    }

    @Test
    public void testInvalidAuthentication() throws Exception {
        thrown.expect(HttpResponseException.class);
        thrown.expectMessage("401 Authorization Required");
        AuthenticatorFactory.invalidCredentials().initialize(videoRequest);
        videoRequest.execute();
    }

    @Test
    public void testValidVideoAuthentication() throws Exception {
        AuthenticatorFactory.validCredentials().initialize(videoRequest);
        HttpResponse response = videoRequest.execute();
        assertThat(response.getContentType(), containsString("multipart/x-mixed-replace;boundary="));
    }

    @Test
    public void testValidImageAuthentication() throws Exception {
        AuthenticatorFactory.validCredentials().initialize(imageRequest);
        HttpResponse response = imageRequest.execute();
        assertThat(response.getContentType(), containsString("image/jpeg"));
    }

}
