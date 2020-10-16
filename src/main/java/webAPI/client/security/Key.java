/**
 * encode: utf-8
 * 
 * @author Assis Henrique Oliveira Pacheco
 */
package webAPI.client.security;

import webAPI.APISecurity;

import java.util.Arrays;

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


	@Override
	public String toString() {
		return "Key{" +
				"secret=" + Arrays.toString(secret) +
				'}';
	}
}
