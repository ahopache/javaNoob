/**
 * encode: utf-8
 * 
 * @author Assis Henrique Oliveira Pacheco
 */
package webAPI.client.security;

import webAPI.APISecurity;

public class OAuth implements APISecurity{

	@Override
	public String getHeaderSecurityName() {
		return "Authorization";
	}

	@Override
	public String getHeaderSecurityValue() {
		return "Bearer ";
	}

}
