package ro.bnr.hooks.core;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.google.mockwebserver.MockResponse;
import com.google.mockwebserver.MockWebServer;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import ro.bnr.hooks.IntegrationTests;

public class FxRatesTest {

  public static final URL TEST_RESOURCE = Resources.getResource("nbrfxrates.xml");

  @Test(expected = IllegalArgumentException.class)
  public void failsForEmptyXmlBlob() {
    FxRates.checkXmlValidity("");
  }

  @Test
  public void noExceptionOnValidInput() throws IOException {
    FxRates.checkXmlValidity(Resources.toString(TEST_RESOURCE, Charsets.UTF_8));
  }

  @Test
  public void getRates() throws IOException, URISyntaxException {
    String expected = Resources.toString(Resources.getResource("nbrfxrates.xml"), Charsets.UTF_8);

    MockWebServer server = new MockWebServer();
    server.enqueue(new MockResponse().setBody(expected));
    server.play();

    String content = FxRates.get(server.getUrl("/hooks").toURI());
    assertThat(content, is(expected));
  }

  @Test
  @Category(IntegrationTests.class)
  public void getFromExternalService() throws IOException {
    String content = FxRates.get();

    DateTime now = new DateTime();
    String expectedDate = DateTimeFormat.forPattern("YYYY-MM-dd").print(now);
    assertThat(content, containsString(expectedDate));
  }
}
