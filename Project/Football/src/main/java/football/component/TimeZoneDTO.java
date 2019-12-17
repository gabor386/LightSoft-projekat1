package football.component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "api"
})
public class TimeZoneDTO {

	  @JsonProperty("api")
	    private Api api;

	    @JsonProperty("api")
	    public Api getApi() {
	        return api;
	    }

	    @JsonProperty("api")
	    public void setApi(Api api) {
	        this.api = api;
	    }

	    public TimeZoneDTO withApi(Api api) {
	        this.api = api;
	        return this;
	    }
	
}
