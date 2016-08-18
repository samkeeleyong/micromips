package cycle;

/**
 * Individual Cycle e.g. Instruction fetch
 * or Instruction decode
 */
public interface MipCycle {

	void setCycle(Cycle cycle);
	
	String prettyPrint();
	
	String whichCycle();
	
	boolean wasProcessed();

	void setWasProcessed(boolean wasProcessed);
}
