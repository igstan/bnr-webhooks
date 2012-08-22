package ro.ieugen;

import com.yammer.dropwizard.config.Configuration;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

public class BnrHooksConfiguration extends Configuration {

  @JsonProperty
  private String baseURL;

  @NotEmpty
  @JsonProperty
  private String fetchURL;

  public String getBaseURL() {
    return baseURL;
  }

  public String getFetchURL() {
    return fetchURL;
  }
}
