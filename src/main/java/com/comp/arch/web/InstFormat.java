package com.comp.arch.web;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.comp.arch.InstructionType;

/**
 *
 * Represents an instruction format
 * 
 * TODO convert to Hibernate object
 * @author samong	
 */
@Entity(name="inst_format")
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
	
	@Column
	private String fifthParam;
	@Column
	private String func;
	@Column
	private String instruction;
	
	@Transient
	private Map<String, String> actualVariablesMap = new HashMap<>();
	@Column
	private String instructionFormatString;
	@Column
	private Boolean offsett;
	@Column
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
		return offsett;
	}

	public void setOffset(Boolean offset) {
		this.offsett = offset;
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

	@Override
	public String toString() {
		
		return "InstFormat instFormat" + id + " = new instFormat" + id + "();\n" +
				"instFormat" + id + ".setId(\"" + id + "\");\n" +
				"instFormat" + id + ".setInstructionType(InstructionType." + instructionType.name() + ");\n" +
				"instFormat" + id + ".setVariables(" + variables+ ");\n" +
				"instFormat" + id + ".setOpCode(\"" + opCode + "\");\n"  +
				"instFormat" + id + ".setRs(" + rs + ");\n" +
				"instFormat" + id + ".setRt(" + rt + ");\n" +
				"instFormat" + id + ".setRd(" + rd + ");\n" +
				"instFormat" + id + ".setFifthParam(\"" + fifthParam + "\");\n" +
				"instFormat" + id + ".setFunc(\"" + func + "\");\n" +
				"instFormat" + id + ".setInstruction(\"" + instruction + "\");\n" +
				"instFormat" + id + ".setActualVariablesMap(" + null + ");\n" +
				"instFormat" + id + ".setInstructionFormatString(\"" + instructionFormatString + "\");\n" +
				"instFormat" + id + ".setImmediate(" + immediate + ");\n" +
				"instFormat" + id + ".setOffset(" + offsett + ");\n" +
				"instFormat" + id + ".setImmediate(" + immediate + ");\n" + 
				"repository.save(instFormat" + id + ");";
	}
}
