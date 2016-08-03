package cycle;

public class FetchCycle implements MipCycle{

	private String ir;
	private String npc;
	private Cycle cycle;
	
	public FetchCycle(String opcode, String npc) {
		this.ir = opcode;
		this.npc = npc;
	}
	
	public String processIR() {
		System.out.println("\tIR:" + ir);
		cycle.setIr(ir);
		return ir;
	}
	
	public String processNPC() {
		
		this.npc = (Integer.parseInt(npc) + 4) + "";
		npc = ("0000000000000000" + npc).substring(npc.length());
		cycle.setNpc(npc);
		System.out.println("\tNPC:" + npc);
		
		return npc;
	}

	@Override
	public void setCycle(Cycle cycle) {
		this.cycle = cycle;		
	}
}
