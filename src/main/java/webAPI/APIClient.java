/*
 * encode: utf-8
 *
 * @author Assis Henrique Oliveira Pacheco
 *
 * @version: 1.0
 *
 * # PortuguesBR
 * Classe básica para comunicação entre APIs
 * O protocolo para comunicação fica em suas sub-classes
 *
 * # English
 * Basic class for API communication
 * The communication protocol is in its subclasses
 *
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
import webAPI.client.APIHeader;
import webAPI.client.security.None;

public class APIClient {
	protected APISecurity apiSecurity;
	protected List<APIHeader> listHeader = new ArrayList<>();
	protected DefaultHttpClient client;
	protected HttpRequestBase sendMethod;

	public APIClient() {
		this.apiSecurity = new None();
	}
	
	public APIClient(APISecurity apiSecurity) {
		this.apiSecurity = apiSecurity;
	}

	private void prepareForSend(APISecurity apiSecurity){
		this.apiSecurity = apiSecurity;
		try {
			client = new DefaultHttpClient();
		}catch (RuntimeException e){
			e.printStackTrace();
		}
	}

	public String sendGet(String urlCommand, String command) {
		prepareForSend(apiSecurity);
		sendMethod = new HttpGet(urlCommand);
		return this.send(command);
	}


	public String sendPost(String urlCommand, String command) {
		prepareForSend(apiSecurity);
		sendMethod = new HttpPost(urlCommand);
		return this.send(command);
	}


	public String sendPath(String urlCommand, String command) {
		prepareForSend(apiSecurity);
		sendMethod = new HttpPatch(urlCommand);
		return this.send(command);
	}

	public String sendDelete(String urlCommand, String command) {
		prepareForSend(apiSecurity);
		sendMethod = new HttpDelete(urlCommand);
		return send(command);
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
