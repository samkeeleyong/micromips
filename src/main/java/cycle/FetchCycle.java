package cycle;

public class FetchCycle implements MipCycle{

	private String ir;
	private String npc;
	private Cycle cycle;
	private String instructionAddress;
	private boolean wasProcessed = false;
	
	public FetchCycle(String opcode, String instructionAddress) {
		this.ir = opcode;
		this.instructionAddress = instructionAddress;
	}
	
	public String processIR() {
		
		cycle.setIr(ir);
		return ir;
	}
	
	public String processNPC() {
		this.npc = Cycle.getCurrentPc();
		this.npc = Integer.toHexString((Integer.parseInt(npc, 16) + 4));

		if (!("0000" + npc).substring(npc.length()).equals(instructionAddress)) {
			Cycle.SHOULD_SKIP = true;
			return "";
		} else {
			Cycle.SHOULD_SKIP = false;
		}
		Cycle.setCurrentNpc(npc);
		npc = ("0000000000000000" + npc).substring(npc.length());
		cycle.setNpc(npc);
		
		return npc;
	}

	@Override
	public void setCycle(Cycle cycle) {
		this.cycle = cycle;		
	}

	@Override
	public String toString() {
		return "FetchCycle [ir=" + ir + ", npc=" + npc + ", cycle=" + cycle
				+ "]";
	}

	@Override
	public String prettyPrint() {

		StringBuilder sb = new StringBuilder();
		
		sb.append("FetchCycle");
		sb.append("IR:" + ir);
		sb.append("NPC:" + npc);
		
		return sb.toString();
	}
	
	@Override
	public String whichCycle() {
		return "FetchCycle";
	}

	public String getIr() {
		return ir;
	}

	public String getNpc() {
		return npc;
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
