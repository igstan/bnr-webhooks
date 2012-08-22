package ro.bnr.hooks.resources;

import static com.google.common.base.Preconditions.checkArgument;
import com.google.common.collect.Maps;
import java.net.URI;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import ro.bnr.hooks.views.HooksView;

@Path("/hooks")
public class HooksResource {

  private AtomicInteger id = new AtomicInteger();
  private ConcurrentMap<Integer, String> hooks = Maps.newConcurrentMap();

  @GET
  @Produces(MediaType.TEXT_HTML)
  public HooksView list() {
    return new HooksView(hooks);
  }

  @POST
  @Path("new")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public Response register(@FormParam("hook") String hook) {
    checkArgument(hook.startsWith("http://"));
    hooks.put(id.getAndIncrement(), hook);

    return Response.seeOther(URI.create("/hooks")).build();
  }
}
