package cycle;

public class DecodeCycle implements MipCycle {
	private String a;
	private String b;
	private String immediate;
	
	private String opcode;
	private Cycle cycle;
	
	public DecodeCycle(String opcode) {
		this.opcode = opcode;
	}
	
	public String processImmediate() {
		immediate = opcode.substring(4);
		immediate = ("0000000000000000" + immediate).substring(immediate.length());
		cycle.setImmediate(immediate);
		System.out.println("\tImmediate:" + immediate);
		return immediate;
	}
	
	public String processA() {
		a = opcode.substring(2, 3);
		a = ("0000000000000000" + a).substring(a.length());
		cycle.setA(a);
		System.out.println("\tA:" + a);
		return a;
	}
	
	public String processB() {
		b = opcode.substring(3, 4);
		b = ("0000000000000000" + b).substring(b.length());
		cycle.setB(b);
		System.out.println("\tB:" + b);
		return b;
	}

	@Override
	public void setCycle(Cycle cycle) {
		this.cycle = cycle;		
	}
}
