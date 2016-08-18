package cycle;

import com.comp.arch.InstFormat;

public class ExecutionCycle implements MipCycle {
	
	private String aluoutput;
	private int cond = 0;
	private Cycle cycle;
	private String instruction;
	private boolean wasProcessed = false;	
	
	public ExecutionCycle(String instruction) {
		this.instruction = instruction;
	}
	
	public String processALUOUTPUT() {
		int number1 = 0;
		int number2 = 0;
		
		switch(instruction) {
			// R type
			case "OR":
				int a = Integer.parseInt(cycle.getA());
				int b = Integer.parseInt(cycle.getImmediate());
				String str3 = Integer.toHexString(Integer.parseInt(Integer.toString(a | b, 2),2));
				
				aluoutput = ("0000000000000000" + str3).substring(str3.length());
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
			case "DSRLV":
				String toBeShifted = cycle.getB().replaceFirst("^0+(?!$)", "");
				String numberOfShifts = cycle.getA().replaceFirst("^0+(?!$)", "");
				numberOfShifts = numberOfShifts.substring(Math.max(numberOfShifts.length() - 5, 0));
				
				aluoutput = (Integer.parseInt(toBeShifted) << Integer.parseInt(numberOfShifts)) + "";
				break;
			// Immediate
			case "DADDIU":
				number1 = Integer.parseInt(cycle.getA(), 16);
				number2 = Integer.parseInt(cycle.getImmediate(), 16);
				
				aluoutput =  (number1 + number2) + "";
				
				aluoutput = InstFormat.toHex(aluoutput);
				break;
			case "LD":
				aluoutput = cycle.getImmediate();
				break;
			case "SD":
				aluoutput = cycle.getImmediate();
				break;
			case "J":
				aluoutput = Integer.toHexString((Integer.parseInt(cycle.getImmediate()) << 2));
				cond = 1;
				
				break;
			case "BNE":
				System.out.println("BNE");
				System.out.println(Integer.parseInt(Cycle.getCurrentNpc()));
				System.out.println((Integer.parseInt(cycle.getImmediate()) << 2));
				System.out.println((Integer.parseInt(Cycle.getCurrentNpc()) + (Integer.parseInt(cycle.getImmediate()) << 2)));
				aluoutput = Integer.toHexString((Integer.parseInt(Cycle.getCurrentNpc()) + (Integer.parseInt(cycle.getImmediate()) << 2)));
				// cond
				number1 = Integer.parseInt(cycle.getA(), 16);
				number2 = Integer.parseInt(cycle.getB(), 16);
				
				if (number1 != number2) {
					cond = 1;
				}
				
				break;
			default:
				System.out.println("ex wat");
				aluoutput = "";
				break;
		}
		if (true) {
						
			
		}
		
		aluoutput = ("0000000000000000" + aluoutput).substring(aluoutput.length());
		cycle.setAluoutput(aluoutput);
		
		System.out.println("\tALUOutput:" + aluoutput);
		System.out.println("\tCond:" + cond);
		
		cycle.setRegisterA(cycle.getRegisterA());
		cycle.setRegisterB(cycle.getRegisterB());
		
		return aluoutput;
	}
	
	public int getCond() {
		return this.cond;
	}

	@Override
	public void setCycle(Cycle cycle) {
		this.cycle = cycle;		
	}

	@Override
	public String toString() {
		return "ExecutionCycle [aluoutput=" + aluoutput + ", cond=" + cond
				+ ", cycle=" + cycle + ", instruction=" + instruction + "]";
	}

	@Override
	public String prettyPrint() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("Execution Cycle");
		sb.append("aluoutput:" + aluoutput);
		sb.append("cond:" + cond);
		
		return sb.toString();
	}
	
	@Override
	public String whichCycle() {
		return "ExecutionCycle";
	}

	public String getAluoutput() {
		return aluoutput;
	}

	public String getB() {
		return cycle.getB();
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
