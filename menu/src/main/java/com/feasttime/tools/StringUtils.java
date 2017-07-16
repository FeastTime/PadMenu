package com.feasttime.tools;

@SuppressWarnings("unused")
public class StringUtils {
	
	public static boolean isNotEmpty(String s) {
		return (s!=null && s.length()>0);
	}

	public static boolean isEmpty(String s) {
		return !isNotEmpty(s);
	}

	public static String getJsonValue(String jsonStr, String key){

		String keyFlog = "\"" + key + "\":\"" ;

		if (isNotEmpty(jsonStr)
				&& isNotEmpty(key)
				&& jsonStr.contains(keyFlog)){

			int startIndex = jsonStr.indexOf(keyFlog)+keyFlog.length();
			int endIndex = jsonStr.indexOf("\"", startIndex);

			if(endIndex >= startIndex)
				return jsonStr.substring(startIndex,endIndex);
		}

		return null;
	}
}
