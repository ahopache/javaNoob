package webAPI.security;

import webAPI.APISecurity;

public class Key implements APISecurity{
	private String[] secret;

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
