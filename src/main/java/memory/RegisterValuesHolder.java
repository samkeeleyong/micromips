package memory;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

public class RegisterValuesHolder {

	private final static Map<String, String> registerValues = new LinkedHashMap<>();
	
	public static void addOrEdit(String register, String value) {
		registerValues.put(register, value);
	} 
	
	public static String getRegistervalue(String registerNumber) {
		return registerValues.get("R" + registerNumber);
	}
	
	public static String getRegistervalue(String registerNumber, boolean withoutR) {
		System.out.println("Getting " + registerNumber + ":" + registerValues.get(registerNumber));
		return registerValues.get(registerNumber);
	}
	
	public static Map<String, String> getRegistervalues() {
		return registerValues;
	}
	
	public static void writeRegisterValue(String registerNumber, String value) {
//		registerValues.put()
	}
}
