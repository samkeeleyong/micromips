package com.comp.arch;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JTypeFormatTranslator implements InstructionFormatTranslator {

	@Autowired
	private InstructionFormatRepository instructionFormatRepository;
	
	@Override
	public String translate(String instruction, String instructionKeyword, Map<String, String> offsetMap) {
		
		InstFormat instFormat = instructionFormatRepository.findByInstruction(instructionKeyword);
		
		StringBuilder sb = new StringBuilder();
		sb.append(instFormat.getOpCode());
		System.out.println("MEOW");
		System.out.println(instFormat.getOpCode());
		String offset = offsetMap.get(instruction);
		offset = Integer.toBinaryString(Integer.parseInt(offset));
		offset = ("00000000000000000000000000" + offset).substring(offset.length());
		System.out.println(offset);
		sb.append(offset);
		System.out.println(sb.toString());
		return InstFormat.toHexByFour(sb.toString());
	}
}
