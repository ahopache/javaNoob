/**
 * encode: utf-8
 * 
 * @author Assis Henrique Oliveira Pacheco
 */
package webAPI.protocol;

import org.apache.http.util.EntityUtils;

import webAPI.API;
import webAPI.APIHeader;
import webAPI.APISecurity;

public class REST extends API{
		
	public REST(APISecurity apiSecurity) {
		super(apiSecurity);
		this.setHeaders();
	}

	public void setHeaders() {
		listHeader.add(new APIHeader("Accept","application/json"));
		listHeader.add(new APIHeader("Accept-Encoding","[gzip, deflate, br]"));
		listHeader.add(new APIHeader("Accept-Language","[pt-BR,pt;q=0.9,en-US;q=0.8,en;q=0.7]"));
		listHeader.add(new APIHeader(apiSecurity.getHeaderSecurityName(), apiSecurity.getHeaderSecurityValue()));
		listHeader.add(new APIHeader("Cache-Control","no-cache"));
		listHeader.add(new APIHeader("Connection","keep-alive"));
		listHeader.add(new APIHeader("Content-Type","application/json"));
		listHeader.add(new APIHeader("charset","UTF-8"));	
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