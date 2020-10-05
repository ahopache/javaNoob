/**
 * encode: utf-8
 * 
 * @author Assis Henrique Oliveira Pacheco
 */
package webAPI;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;

public class API {
	protected APISecurity apiSecurity = null;
	protected List<APIHeader> listHeader = new ArrayList<APIHeader>();
	protected DefaultHttpClient client = new DefaultHttpClient();
	protected HttpRequestBase sendMethod;
	
	public API(APISecurity apiSecurity) {
		this.apiSecurity = apiSecurity;		
	}
	

	public String sendGet(String urlCommand, String command) {
		sendMethod = new HttpGet(urlCommand);
		return this.send(command);
	}


	public String sendPost(String urlCommand, String command) {
		sendMethod = new HttpPost(urlCommand);
		return this.send(command);
	}


	public String sendPath(String urlCommand, String command) {
		sendMethod = new HttpPatch(urlCommand);
		return this.send(command);
	}

	public String sendDelete(String urlCommand, String command) {
		sendMethod = new HttpDelete(urlCommand);
		return this.send(command);
	}

	protected String send(String command) {
		try {
			throw new Exception("This method need to be override!");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
