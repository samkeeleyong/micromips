package cycle;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a single Cycle in 
 * the pipeline map.
 */
public class Cycle {
	
	private static String currentNpc;
	private static String currentPc;
	/**
	 * needs the previous cycle 
	 * to process
	 */
	private Cycle previousCycle;
	private String cycleName;

	private List<MipCycle> mipCycles = new ArrayList<>();
	/**
	 * Calling this constructor means 
	 * that this is the first cycle.
	 */
	public Cycle() {
		/** just to make it obvious */
		this.previousCycle = null;
	}
	
	public Cycle(String cycleName) {
		this.cycleName = cycleName;
	}
	/**
	 * @param previousCycle
	 */
	public Cycle(Cycle previousCycle) {
		this.previousCycle = previousCycle;
	}
	
	public void addMipCycle(MipCycle mipCycle) {
		mipCycles.add(mipCycle);
	}
	
	private String ir;
	private String npc;

	private String immediate;
	private String b;
	private String a;
	
	private String aluoutput;
	private int cond;
	private String lmd;
	private String rn;
	
	private String registerA;
	private String registerB;
	private Object memoryAffected;
	private String pc;
	
	public static boolean SHOULD_SKIP;
	/**
	 * call this to assign values
	 * 
	 * @return current cycle
	 * @return null if processing should end
	 */
	public Cycle processCycle(Cycle previousCycle) {
		
		for(MipCycle mipCycle: mipCycles) {
			mipCycle.setCycle(previousCycle);
			if (!(mipCycle instanceof FetchCycle) && Cycle.SHOULD_SKIP) {
				continue;
			}
			
			if (mipCycle instanceof FetchCycle) {
				FetchCycle IF = (FetchCycle)mipCycle;
				String ir = IF.processIR();
				String npc = IF.processNPC();
				
				this.ir = ir;
				this.npc = npc;
			} else if (mipCycle instanceof DecodeCycle) {
				
				DecodeCycle ID = (DecodeCycle)mipCycle;
				String a = ID.processA();
				String b = ID.processB();
				String immediate = ID.processImmediate();
				
				this.a = a;
				this.b = b;
				this.immediate = immediate;
			} else if (mipCycle instanceof ExecutionCycle) {
				ExecutionCycle EX = (ExecutionCycle) mipCycle;
				
				String aluoutput = EX.processALUOUTPUT();
				
				int cond = EX.getCond();
				
				this.aluoutput = aluoutput;
				this.cond = cond;
				this.b = EX.getB();
			} else if (mipCycle instanceof MemCycle) {
				MemCycle MEM = (MemCycle)mipCycle;
				String pc = MEM.getPc();
				this.pc = pc;
				
				String lmd = MEM.processLMD();
				this.lmd = lmd;
				this.aluoutput = MEM.getAluoutput();
				this.memoryAffected = MEM.getMemoryAffected(); 
			} else if (mipCycle instanceof WriteBackCycle) {
				WriteBackCycle WB = (WriteBackCycle)mipCycle;
				
				String rn = WB.processRn();
				this.rn = rn;
			}
			
			if (!Cycle.SHOULD_SKIP) {
				mipCycle.setWasProcessed(true);
			}
 		}
		return this;
	}

	@Override
	public String toString() {
		return "Cycle [previousCycle=" + previousCycle + " ir=" + ir + ", npc=" + npc + ", immediate="
				+ immediate + ", b=" + b + ", a=" + a + ", aluoutput="
				+ aluoutput + ", cond=" + cond + ", lmd=" + lmd + "]\n";
	}

	public String getCycleName() {
		return cycleName;
	}

	public void setCycleName(String cycleName) {
		this.cycleName = cycleName;
	}

	public Cycle getPreviousCycle() {
		return previousCycle;
	}

	public void setPreviousCycle(Cycle previousCycle) {
		this.previousCycle = previousCycle;
	}

	public String getIr() {
		return ir;
	}

	public void setIr(String ir) {
		this.ir = ir;
	}

	public String getNpc() {
		return npc;
	}

	public void setNpc(String npc) {
		this.npc = npc;
	}

	public List<MipCycle> getMipCycles() {
		return mipCycles;
	}

	public void setMipCycles(List<MipCycle> mipCycles) {
		this.mipCycles = mipCycles;
	}

	public String getImmediate() {
		return immediate;
	}

	public void setImmediate(String immediate) {
		this.immediate = immediate;
	}

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public String getAluoutput() {
		return aluoutput;
	}

	public void setAluoutput(String aluoutput) {
		this.aluoutput = aluoutput;
	}

	public int getCond() {
		return cond;
	}

	public void setCond(int cond) {
		this.cond = cond;
	}

	public String getLmd() {
		return lmd;
	}

	public void setLmd(String lmd) {
		this.lmd = lmd;
	}

	public String getRn() {
		return rn;
	}

	public void setRn(String rn) {
		this.rn = rn;
	}

	public String getRegisterA() {
		return registerA;
	}

	public void setRegisterA(String registerA) {
		this.registerA = registerA;
	}

	public String getRegisterB() {
		return registerB;
	}

	public void setRegisterB(String registerB) {
		this.registerB = registerB;
	}

	public Object getMemoryAffected() {
		return memoryAffected;
	}

	public void setMemoryAffected(Object memoryAffected) {
		this.memoryAffected = memoryAffected;
	}

	public static String getCurrentNpc() {
		
		if (currentNpc == null) {
			currentNpc = "0000";
		}
		
		return currentNpc;
	}
	
	public static void setCurrentNpc(String changedNpc) {
		currentNpc = changedNpc; 
	}
	
	public static String getCurrentPc() {
		
		if (currentPc == null) {
			currentPc = "0000";
		}
		
		return currentPc;
	}
	
	public static void setCurrentPc(String changedPc) {
		currentPc = changedPc;
	}
}
