package com.comp.arch;

import java.util.Map;

public interface InstructionFormatTranslator {

	String translate(String instruction, String instructionKeyword, Map<String, String> offsetMap);
}
