package football.component;

import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiPOJO {

	
	@Valid
	  private Long results;
	    
	  	@JsonIdentityReference
	  	@Valid
	    private List<String> timezone;
	  	
	  	 
	    
	    
	    public ApiPOJO() {
		}


		public Long getResults() {
	    return results;
	    }
	     

	    public List<String> getTimezone() {
	        return timezone;
	    }

	    public void setTimezone(List<String> timezone) {
	        this.timezone = timezone;
	    }


		@Override
		public String toString() {
			return "Zone" + timezone;
		}

	 
	
}
