package com.comp.arch;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ITypeFormatTranslator implements InstructionFormatTranslator {

	@Autowired
	private InstructionFormatRepository instructionFormatRepository;
	
	@Override
	public String translate(String instruction, String instructionKeyword) {
		InstFormat instFormat = instructionFormatRepository.findByInstruction(instructionKeyword);
		String result = "";
		
		if (instFormat.getOffset()) {
			String actualOpCode = instruction.split(" ")[0]; // LD
			String actualVariableStr = instruction.split(actualOpCode)[1]; // e.g. R1,offset(base)
			String actualRt = actualVariableStr.split(",")[0]; // R1
			String actualSecondHalf = actualVariableStr.split(",")[1]; // offset(base)
			String actualOffset = actualSecondHalf.substring(0, actualSecondHalf.indexOf('(')); // offset
			String actualBase = actualSecondHalf.substring(actualSecondHalf.indexOf("(")+1, actualSecondHalf.indexOf(")")); // base
			
			String formatOpCode = instFormat.getInstructionFormatString().split(" ")[0]; // LD
			String formatVariableStr = instFormat.getInstructionFormatString().split(formatOpCode)[1]; // e.g. R1,offset(base)
			String formatRt = formatVariableStr.split(",")[0]; // R1
			String formatSecondHalf = formatVariableStr.split(",")[1]; // offset(base)
			String formatOffset = formatSecondHalf.substring(0, formatSecondHalf.indexOf('(')); // offset
			String formatBase = formatSecondHalf.substring(formatSecondHalf.indexOf("(")+1, formatSecondHalf.indexOf(")")); // base
			
			instFormat.getActualVariablesMap().put("rt", instFormat.translateRegisterToBinary(actualRt.trim()));
			instFormat.getActualVariablesMap().put("offset", actualOffset.trim());
			instFormat.getActualVariablesMap().put("base", instFormat.translateRegisterToBinary(actualBase.trim()));
			
			System.out.println(instFormat.getActualVariablesMap());
			
			StringBuilder sb = new StringBuilder();
			sb.append(instFormat.getOpCode());
			sb.append(instFormat.getVariable("base"));
			sb.append(instFormat.getVariable("rt"));
			
			result = InstFormat.toHex(sb.toString()) + instFormat.getVariable("offset");
			System.out.println(result);
		} else if (instFormat.getImmediate()) {
			String actualOpCode = instruction.split(" ")[0]; // OP
			String actualVariableStr = instruction.split(actualOpCode)[1]; // e.g. R1,R2,R3
			String[] actualVariables = actualVariableStr.split(","); // {R1, R2, R3}
			
			String formatOpCode = instFormat.getInstructionFormatString().split(" ")[0]; // OP
			String formatVariableStr = instFormat.getInstructionFormatString().split(formatOpCode)[1]; // e.g. rd,rs,rt
			String[] formatVariables = formatVariableStr.split(","); // {rd, rs, rt}

			for (int i = 0; i < formatVariables.length; i++) {
				if (i == formatVariables.length - 1) {
					instFormat.getActualVariablesMap().put(formatVariables[i].trim(), actualVariables[i].trim());
				} else {
					instFormat.getActualVariablesMap().put(formatVariables[i].trim(), instFormat.translateRegisterToBinary(actualVariables[i].trim()));
				}
			}
			StringBuilder sb = new StringBuilder();
			sb.append(instFormat.getOpCode());
			sb.append(instFormat.getVariable("rs"));
			sb.append(instFormat.getVariable("rt"));
			result = InstFormat.toHex(sb.toString()) + instFormat.getVariable("immediate").substring(1);
			System.out.println(result);
		}

		return result;
	}
}
