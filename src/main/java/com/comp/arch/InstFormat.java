package com.comp.arch;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 *
 * Represents an instruction format
 * 
 * TODO convert to Hibernate object
 * @author samong	
 */
@Entity
public class InstFormat {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String id;
	
	@Enumerated(EnumType.STRING)
	private InstructionType instructionType;
	
	@Transient
	private String variables;
	
	private String opCode;
	
	@Transient
	private String rs;
	
	@Transient
	private String rt;
	
	@Transient
	private String rd;
	
	private String fifthParam;
	
	private String func;
	
	private String instruction;
	
	@Transient
	private Map<String, String> actualVariablesMap = new HashMap<>();
	
	private String instructionFormatString;

	private Boolean offset;
	
	private Boolean immediate;
	
	public InstFormat() {
		
	}
	
	public void setup(String instruction) {
		
	}
	
	public String translateRegisterToBinary(String register) {
		// TODO magulo!
		String binaryRepresentation = Integer.toString(Integer.parseInt(register.substring(1)), 2);
		String result = String.format("%05d", Integer.parseInt(binaryRepresentation)); 
		return result;
	}

	public static String toHexByFour(String binaryStr) {
		String[] split = binaryStr.split("(?<=\\G....)");
		String hexStr = "";
		for (String s: split) {
			if (s.length() == 4) {
				int decimal = Integer.parseInt(s, 2);
				hexStr += Integer.toString(decimal, 16);
			}
			
		}
		
	    return hexStr;
	}
	
	public static String toHex(String num) {
		return Integer.toHexString(Integer.parseInt(num));
	}
	
	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	public String getVariable(String key) {
		return actualVariablesMap.get(key);
	}

	public Boolean getOffset() {
		return offset;
	}

	public void setOffset(Boolean offset) {
		this.offset = offset;
	}

	public Boolean getImmediate() {
		return immediate;
	}

	public void setImmediate(Boolean immediate) {
		this.immediate = immediate;
	}

	public Map<String, String> getActualVariablesMap() {
		return actualVariablesMap;
	}

	public void setActualVariablesMap(Map<String, String> actualVariablesMap) {
		this.actualVariablesMap = actualVariablesMap;
	}

	public String getInstructionFormatString() {
		return instructionFormatString;
	}

	public void setInstructionFormatString(String instructionFormatString) {
		this.instructionFormatString = instructionFormatString;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOpCode() {
		return opCode;
	}

	public void setOpCode(String opCode) {
		this.opCode = opCode;
	}

	public String getRs() {
		return rs;
	}

	public void setRs(String rs) {
		this.rs = rs;
	}

	public String getRt() {
		return rt;
	}

	public void setRt(String rt) {
		this.rt = rt;
	}

	public String getRd() {
		return rd;
	}

	public void setRd(String rd) {
		this.rd = rd;
	}

	public String getFifthParam() {
		return fifthParam;
	}

	public void setFifthParam(String fifthParam) {
		this.fifthParam = fifthParam;
	}

	public String getFunc() {
		return func;
	}

	public void setFunc(String func) {
		this.func = func;
	}

	public InstructionType getInstructionType() {
		return instructionType;
	}

	public void setInstructionType(InstructionType instructionType) {
		this.instructionType = instructionType;
	}

	public String getVariables() {
		return variables;
	}

	public void setVariables(String variables) {
		this.variables = variables;
	}
}
