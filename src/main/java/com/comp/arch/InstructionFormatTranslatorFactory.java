package com.comp.arch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InstructionFormatTranslatorFactory {

	@Autowired
	private RTypeFormatTranslator rType;
	
	@Autowired
	private ITypeFormatTranslator iType;
	
	public InstructionFormatTranslator getInstance(InstructionType instType) {
		switch(instType) {
			case R:
				return rType;
			case I:
				return iType;
			default:
				throw new IllegalArgumentException("No such Instruction Type");
		}
	}
}
