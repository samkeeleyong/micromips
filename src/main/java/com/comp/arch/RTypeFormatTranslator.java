package com.comp.arch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RTypeFormatTranslator implements InstructionFormatTranslator {

	@Autowired
	private InstructionFormatRepository instructionFormatRepository; 
	
	@Override
	public String translate(String instruction, String instructionKeyword) {
		
		InstFormat instFormat = instructionFormatRepository.findByInstruction(instructionKeyword);
		
		String actualOpCode = instruction.split(" ")[0]; // OP
		String actualVariableStr = instruction.split(actualOpCode)[1]; // e.g. R1,R2,R3
		String[] actualVariables = actualVariableStr.split(","); // {R1, R2, R3}
		
		String formatOpCode = instFormat.getInstructionFormatString().split(" ")[0]; // OP
		String formatVariableStr = instFormat.getInstructionFormatString().split(formatOpCode)[1]; // e.g. rd,rs,rt
		String[] formatVariables = formatVariableStr.split(","); // {rd, rs, rt}

		for (int i = 0; i < formatVariables.length; i++) {
			instFormat.getActualVariablesMap().put(formatVariables[i].trim(), instFormat.translateRegisterToBinary(actualVariables[i].trim()));
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(instFormat.getOpCode());
		sb.append(instFormat.getVariable("rs"));
		sb.append(instFormat.getVariable("rt"));
		sb.append(instFormat.getVariable("rd"));
		sb.append(instFormat.getFifthParam());
		sb.append(instFormat.getFunc());
		
		System.out.println(sb.toString());
		return InstFormat.toHex(sb.toString());
	}
}
