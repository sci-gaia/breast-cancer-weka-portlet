package it.dfa.unict.pojo;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "href", "rel" })
public class Link {

	@JsonProperty("href")
	private String href;
	@JsonProperty("rel")
	private String rel;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return The href
	 */
	@JsonProperty("href")
	public String getHref() {
		return href;
	}

	/**
	 * 
	 * @param href
	 *            The href
	 */
	@JsonProperty("href")
	public void setHref(String href) {
		this.href = href;
	}

	/**
	 * 
	 * @return The rel
	 */
	@JsonProperty("rel")
	public String getRel() {
		return rel;
	}

	/**
	 * 
	 * @param rel
	 *            The rel
	 */
	@JsonProperty("rel")
	public void setRel(String rel) {
		this.rel = rel;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	@Override
	public String toString() {
		return "Link [href=" + href + ", rel=" + rel
				+ ", additionalProperties=" + additionalProperties + "]";
	}

}
