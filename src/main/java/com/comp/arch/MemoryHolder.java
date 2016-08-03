package com.comp.arch;

import java.util.HashMap;
import java.util.Map;

public class MemoryHolder {
	private final static Map<Integer, String> memory = new HashMap<>();
	
	/**
	 * 
	 * @param key Integer address (e.g. 1000)
	 * @param value 2 digit hexadecimal
	 */
	public static void add(Integer key, String value) {
		memory.put(key, value);
	}
	
	/**
	 * 
	 * @return retrieve data starting from address
	 */
	public static String getDataFrom(Integer address) {
		StringBuilder dataBuilder = new StringBuilder();
		
		address += 7;
		for(int i = 7; i >= 1; i--) {
			dataBuilder.append(memory.get(address));
			address -= 1;
		}
		
		return dataBuilder.toString();
	}
}
