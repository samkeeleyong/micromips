package cycle;

import memory.RegisterValuesHolder;

public class DecodeCycle implements MipCycle {
	private String a;
	private String b;
	private String immediate;
	private boolean wasProcessed = false;
	private String opcode;
	private Cycle cycle;
	
	private String instructionLine;
	
	public DecodeCycle(String opcode, String instructionLine) {
		this.opcode = opcode;
		this.instructionLine = instructionLine;
	}
	
	public String processImmediate() {
		if (instructionLine.startsWith("OR") || instructionLine.startsWith("SLT")) {
			String str = instructionLine.substring(instructionLine.indexOf(",") + 1);
			String register = str.substring(str.indexOf(",") + 1).trim();
			immediate = RegisterValuesHolder.getRegistervalue(register, false);
		} else {
			immediate = opcode.substring(4);
			immediate = ("0000000000000000" + immediate).substring(immediate.length());
		}
		cycle.setImmediate(immediate);
		
		return immediate;
	}
	
	public String processA() {
		String a = hexToBinary(opcode);
		a = a.substring(6, 16);
		a = a.substring(0, 5);
		
		String register = "R" + Integer.parseInt(a, 2);
		cycle.setRegisterA(register.trim());
		a = RegisterValuesHolder.getRegistervalue(register, false);
		a = ("0000000000000000" + a).substring(a.length());
		cycle.setA(a);
		setA(a);
		return a;
	}
	
	public String processB() {
		
		String b = hexToBinary(opcode);
		b = b.substring(6, 16);
		b = b.substring(5);
		
		String register = "R" + Integer.parseInt(b, 2);
		
		cycle.setRegisterB(register.trim());
		b = RegisterValuesHolder.getRegistervalue(register, false);
		b = ("0000000000000000" + b).substring(b.length());
		cycle.setB(b);
		setB(b);
		return b;
	}

	private String hexToBinary(String hex) {
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
	
	@Override
	public void setCycle(Cycle cycle) {
		this.cycle = cycle;		
	}

	@Override
	public String toString() {
		return "DecodeCycle [a=" + a + ", b=" + b + ", immediate=" + immediate
				+ ", opcode=" + opcode + ", cycle=" + cycle + "]";
	}

	@Override
	public String prettyPrint() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("A:" + a);
		sb.append("B:" + b);
		sb.append("Immediate:" + immediate);
		
		return sb.toString();
	}
	
	@Override
	public String whichCycle() {
		return "DecodeCycle";
	}

	public String getImmediate() {
		return immediate;
	}

	public void setImmediate(String immediate) {
		this.immediate = immediate;
	}

	public String getA() {
		return a;
	}

	public String getB() {
		return b;
	}

	public void setA(String a) {
		this.a = a;
	}

	public void setB(String b) {
		this.b = b;
	}
	
	@Override
	public boolean wasProcessed() {
		return wasProcessed;
	}

	@Override
	public void setWasProcessed(boolean wasProcessed) {
		this.wasProcessed = wasProcessed;
	}
}
