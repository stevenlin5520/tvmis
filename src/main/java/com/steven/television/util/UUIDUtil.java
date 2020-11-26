package com.steven.television.util;

import java.util.UUID;

/**
 *
 * @desc
 * @author steven
 * @date 2020/11/9-22:27
 */
public class UUIDUtil {

	private static String str ;
	
	public static String getUUID(){
		return UUID.randomUUID().toString() ;
	}
	
	public static String getUUIDSTR(){
		str = getUUID();	
		return str.replace("-", "");
	}

	public static String getUUIDCaseStr(){
		return getUUIDSTR().toUpperCase();
	}
}
