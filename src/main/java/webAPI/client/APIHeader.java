/**
 * encode: utf-8
 * 
 * @author Assis Henrique Oliveira Pacheco
 */
package webAPI.client;

public class APIHeader {
	private String value;
	private String name;
	
	public APIHeader(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "APIHeader{" +
				"value='" + value + '\'' +
				", name='" + name + '\'' +
				'}';
	}
}
