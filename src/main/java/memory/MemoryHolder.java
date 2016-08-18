package memory;

import java.util.LinkedHashMap;
import java.util.Map;

public class MemoryHolder {
	private final static Map<String, String> memory = new LinkedHashMap<>();
	
	/**
	 * 
	 * @param key Integer address (e.g. 1000)
	 * @param value 2 digit hexadecimal
	 */
	public static void addOrEdit(String key, String value) {
		memory.put(key, value);
	}
	
	
	public static Map<String, String> getMemory() {
		return memory;
	}

	/**
	 * 
	 * @return retrieve data starting from address
	 */
	// 1005 - 100A
	public static String getDataFrom(String address) {
		StringBuilder dataBuilder = new StringBuilder();
		
		address = Integer.toHexString(Integer.parseInt(address, 16) + 7);
		for(int i = 7; i >= 0; i--) {
			dataBuilder.append(memory.get(address));
			address = Integer.toHexString(Integer.parseInt(address, 16) - 1);
		}
		
		return dataBuilder.toString();
	}
	
	public static String setDataTo(String address, String value) {

		String memoryAffected = address; 
		address = Integer.toHexString(Integer.parseInt(address, 16) + 7);
		
		memoryAffected = memoryAffected + " - " + address;
		
		for(int i = 7, j = 0; i >= 0; i--, j+=2) {
			memory.put(address, value.substring(j, j + 2));
			address = Integer.toHexString(Integer.parseInt(address, 16) - 1);
		}
		return memoryAffected;
	}
}
