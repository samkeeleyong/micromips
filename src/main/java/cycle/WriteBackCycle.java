package cycle;

import memory.RegisterValuesHolder;

public class WriteBackCycle implements MipCycle{

	private Cycle cycle;
	private String rn;
	private String instruction;
	private String instructionLine;
	private boolean wasProcessed = false;
	
	public WriteBackCycle(String instruction, String instructionLine) {
		this.instruction = instruction;
		this.instructionLine = instructionLine;
	}
	public String processRn() {
		switch(instruction) {
			case "OR":
				rn = cycle.getAluoutput();
				
				changeRegisterB();
				break;
			case "DSRLV":
				rn = cycle.getAluoutput();
				
				changeRegisterA();
				break;
			case "SLT":
				rn = cycle.getAluoutput();
				
				changeRegisterA();
				break;
			case "DADDIU":
			case "<other immediate instructions>":
				System.out.println("Cycle in WB:" + cycle);
				rn = cycle.getAluoutput();
				
				changeRegisterB();
				break;
			case "LD":
			case "<otherloadinstructions>":
				System.out.println("Writeback for Load instruction:");
				
				rn = cycle.getLmd();
				changeRegisterB();
				break;
			default:
				rn = "n/a";
				break;
			}
		
		return rn;
	}

	private void changeRegisterB() {
		String register = instructionLine.substring(instructionLine.indexOf(" R"), instructionLine.indexOf(",")).trim();
		System.out.println("In Writeback: changed " + register + " to " + rn);
		RegisterValuesHolder.addOrEdit(register, rn);
	}
	
	private void changeRegisterA() {
		String register = instructionLine.substring(instructionLine.indexOf(" "), instructionLine.indexOf(",")).trim();
		System.out.println("In Writeback: changed " + register + " to " + rn);
		RegisterValuesHolder.addOrEdit(register, rn);
	}
	
	@Override
	public void setCycle(Cycle cycle) {
		this.cycle = cycle;
	}

	@Override
	public String toString() {
		return "WriteBackCycle [cycle=" + cycle + "]";
	}

	@Override
	public String prettyPrint() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("WriteBack:");
		sb.append("rn:" + rn);
		
		return sb.toString();
	}
	
	@Override
	public String whichCycle() {
		return "WriteBackCycle";
	}
	public String getRn() {
		return rn;
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
