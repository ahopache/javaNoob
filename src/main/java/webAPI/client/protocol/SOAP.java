/**
 * encode: utf-8
 * 
 * @author Assis Henrique Oliveira Pacheco
 */
package webAPI.client.protocol;

import org.apache.http.util.EntityUtils;

import webAPI.APIClient;
import webAPI.client.APIHeader;
import webAPI.APISecurity;

public class SOAP extends APIClient {
	
	public SOAP(APISecurity apiSecurity) {
		super(apiSecurity);
		this.setHeaders();		
	}

	public void setHeaders() {
		listHeader.add(new APIHeader("Content-Type","text/xml"));
		listHeader.add(new APIHeader("charset","UTF-8"));
		listHeader.add(new APIHeader("Accept-Encoding","[gzip, deflate, br]"));
		listHeader.add(new APIHeader("Accept-Language","[pt-BR,pt;q=0.9,en-US;q=0.8,en;q=0.7]"));
		listHeader.add(new APIHeader(apiSecurity.getHeaderSecurityName(), apiSecurity.getHeaderSecurityValue()));
	}
	
	@Override
	protected String send(String command) {
		try {
			for (APIHeader header: this.listHeader) {
				sendMethod.addHeader(header.getName(), header.getValue());
			}

			//sendMethod.setEntity(new StringEntity(command,"UTF-8"));
			return EntityUtils.toString(client.execute(sendMethod).getEntity() , "UTF-8").trim();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
}
