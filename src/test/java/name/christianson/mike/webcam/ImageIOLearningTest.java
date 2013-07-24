package name.christianson.mike.webcam;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import sun.net.www.protocol.http.AuthCacheImpl;
import sun.net.www.protocol.http.AuthCacheValue;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.URL;

import static junit.framework.Assert.assertNull;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.isA;
import static org.junit.Assert.assertEquals;

public class ImageIOLearningTest {

    private static URL testWebcamImageUrl;
    private static URL testWebcamVideoUrl;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() throws MalformedURLException {
        testWebcamImageUrl = new URL("http://webcam-nw/image.jpg");
        testWebcamVideoUrl = new URL("http://webcam-nw/video.cgi");
    }

    @Before
    public void setUp() throws Exception {
        AuthCacheValue.setAuthCache(new AuthCacheImpl()); //workaround for Java bug http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6626700
        Authenticator.setDefault(null);
    }

    @Test(expected = IIOException.class)
    public void resourceDoesNotExist() throws Exception {
        URL url = new URL("http://localhost/does/not/exist:8080");
        ImageIO.read(url);
    }

    @Test
    public void requiresAuthentication() throws IOException {
        thrown.expectCause(isA(IOException.class));
//      TODO figure out how to expect a cause's message, like so:  thrown.expectCauseMessage(CoreMatchers.containsString("response code: 401"));
        thrown.expectMessage(containsString("Can't get input stream from URL"));
        ImageIO.read(testWebcamImageUrl);
    }

    @Test
    public void invalidAuthentication() throws Exception {
        thrown.expectCause(isA(IOException.class));
//      TODO figure out how to expect a cause's message, like so:  thrown.expectCauseMessage(CoreMatchers.containsString("Server redirected too many  times"));
        thrown.expectMessage(containsString("Can't get input stream from URL"));
        Authenticator.setDefault(AuthenticatorFactory.invalidCredentials());
        ImageIO.read(testWebcamImageUrl);
    }

    @Test
    public void validAuthenticationYieldsUsableImage() throws Exception {
        Authenticator.setDefault(AuthenticatorFactory.validCredentials());
        BufferedImage image = ImageIO.read(testWebcamImageUrl);
        assertEquals(640, image.getWidth());
        assertEquals(480, image.getHeight());
    }

    @Test
    public void cannotUseImageIOForMJPEG() throws Exception {
        Authenticator.setDefault(AuthenticatorFactory.validCredentials());
        BufferedImage image = ImageIO.read(testWebcamVideoUrl);
        assertNull(image);
    }
}
