package cycle;

import com.comp.arch.web.MainController;

import memory.MemoryHolder;

public class MemCycle implements MipCycle {
	
	private String lmd;
	private Cycle cycle;
	private String instruction;
	private String aluoutput;
	private String memoryAffected;
	private boolean wasProcessed = false;
	private String instructionLine;
	
	public MemCycle(String instruction, String instructionLine) {
		this.instruction = instruction;
		this.instructionLine = instructionLine;
	}

	public String processLMD() {
		
		// processing stuff
		Cycle.setCurrentPc(Cycle.getCurrentNpc());
		switch(instruction) {
			case "LD":
			case "<otherloadinstructions>":
				String aluoutput = cycle.getAluoutput();
				
				Integer address = Integer.parseInt(aluoutput.substring(aluoutput.length() - 4));
				lmd = MemoryHolder.getDataFrom(address.toString());
				System.out.println(lmd);
				break;
			case "SD":
			case "<otherstoreinstructions>":
				aluoutput = cycle.getAluoutput();
				
				aluoutput = aluoutput.substring(Math.max(aluoutput.length() - 4, 0));
				memoryAffected = MemoryHolder.setDataTo(Integer.parseInt(aluoutput) + "", cycle.getB());
				break;
			case "J":
			case "BNE":
				aluoutput = cycle.getAluoutput();
				
				System.out.println(cycle.getCond());
				if (cycle.getCond() == 1) {
					
					String cheated = Integer.toHexString((Integer.parseInt(Cycle.getCurrentNpc(), 16) - 4) + (4 * Integer.parseInt(MainController.offsetMap.get(instructionLine))));
					aluoutput = ("0000000000000000" + cheated).substring(cheated.length());
					Cycle.setCurrentPc(aluoutput);
				}
				
				lmd = "n/a";
				memoryAffected = "n/a";
				
				break;
			default:
				lmd = "n/a";
				memoryAffected = "n/a";
				break;
		}
		
		cycle.setIr(lmd);
		this.aluoutput = cycle.getAluoutput();
		
		
		cycle.setRegisterA(cycle.getRegisterA());
		cycle.setRegisterB(cycle.getRegisterB());
		
		return lmd;
	}

	@Override
	public void setCycle(Cycle cycle) {
		this.cycle = cycle;
	}

	@Override
	public String toString() {
		return "MemCycle [lmd=" + lmd + ", cycle=" + cycle + ", instruction="
				+ instruction + "]";
	}

	@Override
	public String prettyPrint() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("LMD:" + lmd);
		
		return sb.toString();
	}

	public String getAluoutput() {
		return this.aluoutput;
	}
	
	@Override
	public String whichCycle() {
		return "MemCycle";
	}

	public String getLmd() {
		return lmd;
	}

	public String getMemoryAffected() {
		return memoryAffected;
	}

	public void setMemoryAffected(String memoryAffected) {
		this.memoryAffected = memoryAffected;
	}

	public String getPc() {
		return Cycle.getCurrentPc();
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
