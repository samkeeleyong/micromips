package cycle;

public class ExecutionCycle implements MipCycle {
	
	private String aluoutput;
	private int cond = 0;
	private Cycle cycle;
	private String instruction;
	
	public ExecutionCycle(String instruction) {
		this.instruction = instruction;
	}
	
	public String processALUOUTPUT() {
		// TODO memory reference
		
		// TODO register to register
		
		// TODO immediate
System.out.println(instruction);		
		switch(instruction) {
			case "DADDIU":
			case "<other immediate instructions>":
				int number1 = Integer.parseInt(cycle.getA(), 16);
				int number2 = Integer.parseInt(cycle.getImmediate(), 16);

				aluoutput =  (number1 + number2) + "";
				break;
			case "LD":
			case "<otherloadinstructions>":
				aluoutput = cycle.getImmediate();
				break;
			case "SLT":
				String left = cycle.getA();
				String right = cycle.getB();
				if (Integer.parseInt(left, 16) > Integer.parseInt(right, 16)) {
					aluoutput = "1";
				} else {
					aluoutput = "0";
				}
				break;
			case "J":
				cond = 1;
				cycle.setCond(cond);
				break;
			default:
				System.out.println("wat");
				break;
		}
		if (true) {
						
			
		}
		
		aluoutput = ("0000000000000000" + aluoutput).substring(aluoutput.length());
		cycle.setAluoutput(aluoutput);
		
		System.out.println("\tALUOutput:" + aluoutput);
		System.out.println("\tCond:" + cond);
		return aluoutput;
	}
	
	public int getCond() {
		return this.cond;
	}

	@Override
	public void setCycle(Cycle cycle) {
		this.cycle = cycle;		
	}
}
