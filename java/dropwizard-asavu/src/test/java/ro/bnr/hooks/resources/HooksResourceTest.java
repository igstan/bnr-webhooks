package ro.bnr.hooks.resources;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.yammer.dropwizard.testing.ResourceTest;
import com.yammer.dropwizard.views.ViewMessageBodyWriter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;
import org.junit.Test;

public class HooksResourceTest extends ResourceTest {

  @Override
  protected void setUpResources() throws Exception {
    addProvider(ViewMessageBodyWriter.class);
    addResource(new HooksResource());
  }

  @Test
  public void pageContainsForm() {
    ClientResponse response = client().resource("/hooks").get(ClientResponse.class);
    assertThat(response.getStatus(), is(200));

    String content = response.getEntity(String.class);
    assertThat(content, containsString("action=\"/hooks/new\""));
  }

  @Test
  public void registerNewHook() {
    String expectedHook = "http://example.com";
    MultivaluedMap<String, String> form = new MultivaluedMapImpl();
    form.putSingle("hook", expectedHook);

    ClientResponse response = client().resource("/hooks/new")
        .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(ClientResponse.class, form);
    assertThat(response.getStatus(), is(303));
    assertThat(response.getLocation().toString(), endsWith("/hooks"));

    String content = client().resource(response.getLocation()).get(String.class);
    assertThat(content, containsString(expectedHook));
  }
}
