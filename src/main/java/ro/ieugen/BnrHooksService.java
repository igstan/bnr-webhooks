package ro.ieugen;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Environment;
import org.eclipse.jetty.util.ConcurrentHashSet;

import java.net.URI;

public class BnrHooksService extends Service<BnrHooksConfiguration> {

  public BnrHooksService() {
    super("bnr-hooks");
  }

  public static void main(String[] args) throws Exception {
    new BnrHooksService().run(args);
  }

  @Override
  protected void initialize(BnrHooksConfiguration configuration, Environment environment) throws Exception {
    environment.addResource(new HooksResource(new ConcurrentHashSet<URI>()));
  }
}
