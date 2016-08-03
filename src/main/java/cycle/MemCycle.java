package cycle;

import com.comp.arch.MemoryHolder;

public class MemCycle implements MipCycle {
	
	private String lmd;
	private Cycle cycle;
	private String instruction;
	
	public MemCycle(String instruction) {
		this.instruction = instruction;
	}

	public String processLMD() {
		
		// processing stuff
		
		switch(instruction) {
			case "LD":
			case "<otherloadinstructions>":
				String aluoutput = cycle.getAluoutput();
				Integer address = Integer.parseInt(aluoutput.substring(aluoutput.length() - 4));
				lmd = MemoryHolder.getDataFrom(address);
				break;
			default:
				System.out.println("wat");
				break;
		}
		
		System.out.println("\tLMD:" + lmd);
		cycle.setIr(lmd);
		
		return lmd;
	}

	@Override
	public void setCycle(Cycle cycle) {
		this.cycle = cycle;
	}
}
