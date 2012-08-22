package ro.bnr.hooks;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.views.ViewBundle;
import ro.bnr.hooks.resources.HooksResource;

public class BnrHooksService extends Service<BnrHooksConfiguration> {

  public static void main(String[] args) throws Exception {
    new BnrHooksService().run(args);
  }

  public BnrHooksService() {
    addBundle(new ViewBundle());
  }

  @Override
  protected void initialize(BnrHooksConfiguration config, Environment environment)
      throws Exception {
    environment.addResource(new HooksResource());
  }
}
