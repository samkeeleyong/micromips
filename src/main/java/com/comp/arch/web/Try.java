package com.comp.arch.web;

import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;


public class Try {
//	public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
	void f() {
		/*String address = "1005";
		address = Integer.toHexString(Integer.parseInt(address, 16) + 7);
		for(int i = 7; i >= 0; i--) {
			System.out.println(address);
			address = Integer.toHexString(Integer.parseInt(address, 16) - 1);
		}*/
		String s = "2";
		System.out.println(Integer.toBinaryString(Integer.parseInt(s)));
		
		
//		String s = "0010";
//		System.out.println(Integer.parseInt(s, 2) << 2);
//		String str = "12345678";
//		String substring = str.substring(Math.max(str.length() - 5, 0));
//		System.out.println(substring);
		
//		System.out.println(Integer.parseInt("2"));
		/*String opcode = hexToBinary("014c102a");//"00000001010011000001000000101010";
		System.out.println("014c102a");
		System.out.println(opcode);
		System.out.println("00000001010011000001000000101010");
		String str = opcode.substring(6, 16);
		
		System.out.println(str);
		System.out.println(str.substring(0, 5));
		System.out.println(str.substring(5));
		
		System.out.println(Integer.parseInt(str.substring(0, 5), 2));
		System.out.println(Integer.parseInt(str.substring(5), 2));*/
		
		
//		String str = "OR R3, R2, R1";
//		String str2 = str.substring(str.indexOf(",") + 1);
//		System.out.println(str2.substring(str2.indexOf(",") + 1).trim());

		/*String instructionLine = "LD R10, 1000(R0)";
		int commaIndex = instructionLine.indexOf(",");
		String register = instructionLine.substring(instructionLine.indexOf("R"), commaIndex).trim();
		
		System.out.println(register);*/		
		
		/*String str = "DADDIU R1 , R2 ,#FF55";
		
		System.out.println(str.substring(str.indexOf("R"), str.indexOf(",")));
		String secondHalf = str.substring(str.indexOf(",") + 1);
		System.out.println(secondHalf);
		System.out.println(secondHalf.substring(0, secondHalf.indexOf(",")));*/
	}
	
	private static String hexToBinary(String hex) {
		String bin = "";
		for (char c: hex.toCharArray()) {
			if (c == '0') {
				bin = bin + "0000";
			} else {
				int i = Integer.parseInt(c + "", 16);
				String temp = Integer.toBinaryString(i);
				temp = ("0000" + temp).substring(temp.length());
			    bin = bin + temp;
			}
		}
	    
	    return bin;
	}
}
