package ro.ieugen;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.yammer.dropwizard.testing.ResourceTest;
import org.eclipse.jetty.util.ConcurrentHashSet;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.io.File;
import java.net.URI;


public class HooksResourceTest extends ResourceTest {

  private final ConcurrentHashSet<URI> registeredUris = new ConcurrentHashSet<URI>();

  @Override
  protected void setUpResources() throws Exception {
    addResource(new HooksResource(registeredUris));
  }

  @Before
  public void setUp() throws Exception {
    registeredUris.clear();
  }

  @Test
  public void testRegisterHook() throws Exception {
    URI uri = URI.create("http://localhost:8081/test");
    MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
    formData.put("url", Lists.newArrayList(uri.toString()));

    ClientResponse response = client().resource("/hooks").type(MediaType.APPLICATION_FORM_URLENCODED)
        .post(ClientResponse.class, formData);

    assertThat("Registering a hook returns 201 ", response.getStatus(), equalTo(201));
    assertThat("We have the url in the store", registeredUris.contains(uri), equalTo(true));
  }

  @Test
  public void testGetRegistrationForm() throws Exception {
    String htmlForm = Files.toString(new File("src/main/resources/hooks-form.html"), Charsets.UTF_8);
    assertThat("Returned HTML is the one from the file",
        client().resource("/hooks").get(String.class), is(htmlForm));
  }
}
