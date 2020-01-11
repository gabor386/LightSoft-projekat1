package football.component;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeZonePOJO {

	@Valid
	private ApiPOJO api;
	
	
	public TimeZonePOJO() {
	}

	@Valid
	public ApiPOJO getApi() {
		return api;
	}

	public void setApi(ApiPOJO api) {
		this.api = api;
	}



}
