package football.component;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "timezone"
})
public class Api {

	
	    @JsonProperty("timezone")
	    private List<String> timezone = null;
	     

	    @JsonProperty("timezone")
	    public List<String> getTimezone() {
	        return timezone;
	    }

	    @JsonProperty("timezone")
	    public void setTimezone(List<String> timezone) {
	        this.timezone = timezone;
	    }

	    public Api withTimezone(List<String> timezone) {
	        this.timezone = timezone;
	        return this;
	    }

	 
	
}
