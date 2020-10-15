/**
 * encode: utf-8
 * 
 * @author Assis Henrique Oliveira Pacheco
 */
package webAPI.client.security;

import webAPI.APISecurity;

public class None implements APISecurity{
	@Override
	public String getHeaderSecurityName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getHeaderSecurityValue() {
		// TODO Auto-generated method stub
		return null;
	}

}
