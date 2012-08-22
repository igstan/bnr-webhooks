package ro.ieugen;

import com.google.common.base.Charsets;
import static com.google.common.base.Preconditions.checkNotNull;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import org.eclipse.jetty.util.ConcurrentHashSet;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.net.URI;
import java.util.Set;

@Path(HooksResource.HOOKS_URL)
public class HooksResource {

  public static final String HOOKS_URL = "/hooks";
  public final Set<URI> registeredUris;

  public HooksResource(ConcurrentHashSet<URI> registeredUris) {
    this.registeredUris = checkNotNull(registeredUris);
  }

  @POST
  @Produces(MediaType.TEXT_HTML)
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public Response registerHook(@FormParam("url") URI uri) {
    checkNotNull(uri);
    if (registeredUris.contains(uri)) {
      return Response.ok("Url already registered").build();
    } else {
      registeredUris.add(uri);
      return Response.created(uri).build();
    }
  }

  @GET
  @Produces(MediaType.TEXT_HTML)
  public String getRegistrationForm() throws Exception {
    return Files.toString(new File(Resources.getResource("hooks-form.html").toURI()), Charsets.UTF_8);
  }
}
