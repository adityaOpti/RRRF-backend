package com.probity.echawdi.utils;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;


public class StaticPropertyFetcher {

	private static HashMap<Object, Object> propertiesMap = new HashMap<Object, Object>();
	public static final String MISSING_BUNDLE_EXCEPTION = "MISSING_BUNDLE_EXCEPTION";
	public static final String MISSING_PROPERTY_EXCEPTION = "MISSING_PROPERTY_EXCEPTION";


	public static String getPropertyValue(String compName, String key) {
		Locale locale = new Locale("");
		compName+=locale.getLanguage();
		String fetchedValue = getPropertyValue(compName, key, locale);
		return fetchedValue;
	}
	
	public static String getPropertyValue(String compName, String key, String language) {
		Locale locale = new Locale("");
		compName+="_"+language;
		String fetchedValue = getPropertyValue(compName, key, locale);
		return fetchedValue;
	}

//	public static void main(String[] args) {
////		//System.out.println(getPropertyValue(FileName.PROPERTY_PATH+"BaseBidderLandingPage", "label.language.english", "mr"));
//		//System.out.println(getPropertyValue("/com/probity/common/page/BaseBidderLandingPage", "label.language.english", "mr"));
//	}
	
	public static String getPropertyValue(String compName, String key,	Locale locale) {
		Properties properties = (Properties) propertiesMap.get(compName);
		String fetchedValue="";
		if (properties != null) {
			fetchedValue = properties.getProperty(key);
		}
		else {
			try {
				Properties totalProperties = null;
				try {
					totalProperties = new Properties();
					final ResourceBundle resourceBundle = ResourceBundle.getBundle(compName, locale);
					final Enumeration<String> enumeration = resourceBundle.getKeys();
					while (enumeration.hasMoreElements()) {
						final String property = (String) enumeration.nextElement();
						totalProperties.setProperty(property, resourceBundle.getString(property));
					}
				} catch (final MissingResourceException mre) {
					throw new RuntimeException(MISSING_BUNDLE_EXCEPTION + " = "	+ compName + ".properties", mre);
				}
				propertiesMap.put(compName, totalProperties);
			} catch (final Exception e) {
				throw new RuntimeException("Failed to initialise properties for Component '"+ compName + "'", e);
			}
			properties = (Properties) propertiesMap.get(compName);
			fetchedValue = properties.getProperty(key);
		}

		if (fetchedValue != null) {
			fetchedValue = fetchedValue.trim();
		} else {
			throw new RuntimeException(MISSING_PROPERTY_EXCEPTION+ " for KEY '" + key + "'");
		}
		return fetchedValue;
	}
	
	public static void updatePropertiesMap() {
		propertiesMap = new HashMap<Object, Object>();
	}

	public static Set<Object> getPropertyKeys(String fileName) {
		try {
			Properties prop = new Properties();
			InputStream is = StaticPropertyFetcher.class.getResourceAsStream("/"+fileName+".properties");
			prop.load(is);
			Set<Object> keys = prop.keySet();
			return keys;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
