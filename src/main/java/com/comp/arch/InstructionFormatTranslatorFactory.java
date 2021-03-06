package com.comp.arch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InstructionFormatTranslatorFactory {

	@Autowired
	private RTypeFormatTranslator rType;
	
	@Autowired
	private ITypeFormatTranslator iType;
	
	@Autowired
	private JTypeFormatTranslator jType;
	
	public InstructionFormatTranslator getInstance(InstructionType instType) {
		switch(instType) {
			case R:
				return rType;
			case I:
				return iType;
			case J:
				return jType;
			default:
				throw new IllegalArgumentException("No such Instruction Type");
		}
	}
}
