package ro.bnr.hooks.views;

import static com.google.common.base.Preconditions.checkNotNull;
import com.yammer.dropwizard.views.View;
import java.util.Map;

public class HooksView extends View {

  private final Map<Integer, String> hooks;

  public HooksView(Map<Integer, String> hooks) {
    super("hooks.ftl");
    this.hooks = checkNotNull(hooks, "hooks");
  }

  public Map<Integer, String> getHooks() {
    return hooks;
  }
}
