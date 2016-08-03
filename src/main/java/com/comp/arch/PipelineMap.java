package com.comp.arch;

import java.util.ArrayList;
import java.util.List;

import cycle.Cycle;
import cycle.DecodeCycle;
import cycle.ExecutionCycle;
import cycle.FetchCycle;
import cycle.MemCycle;

/**
 * Represents a pipeline map with 
 * a list of {@link Cycle}
 * 
 * TODO convert to singelton
 */
public class PipelineMap {
	private List<Cycle> cycles = new ArrayList<>();

	public List<Cycle> getCycles() {
		return cycles;
	}

	public void setCycles(List<Cycle> cycles) {
		this.cycles = cycles;
	}

	public void setUpMipCycles(List<String> opcodes, int numberOfCycles, String[] instructions) {
		// fill up cycle objects
		String[] cycleCodes = new String[] {"IF", "ID", "EX", "MEM", "WEB"};
		
		int[] indexToStart = new int[] {0, 1, 2};
		int[] cycleIndex = new int[3];
		
		for (int i = 0; i < numberOfCycles; i++) {
			Cycle cycle = new Cycle(1 + i + "");
			
//			System.out.printf("Cycle %d\t", i + 1);
			for (int j = 0; j < instructions.length; j++) {
				if (i >= indexToStart[j]) {
					try {
						String cycleCode = cycleCodes[cycleIndex[j]];
						String opcode = opcodes.get(j);
						String instruction = instructions[j];
//						System.out.println(cycleCode);
						switch(cycleCode) {
							case "IF":
								FetchCycle fetchCycle = new FetchCycle(opcode, "0000");
								cycle.addMipCycle(fetchCycle);
								
								break;
							case "ID":
								DecodeCycle decodeCycle= new DecodeCycle(opcode);
								cycle.addMipCycle(decodeCycle);
								
								break;
							case "EX":
								ExecutionCycle executionCycle = new ExecutionCycle(instruction);
								cycle.addMipCycle(executionCycle);
								
								break;
							case "MEM":
								MemCycle memCycle = new MemCycle(instruction);
								cycle.addMipCycle(memCycle);
								break;
							default:
								break;
							
						}
						cycleIndex[j] = cycleIndex[j] + 1;
					
					} catch (ArrayIndexOutOfBoundsException e) {
						continue;
					}
				}
			}
			cycles.add(cycle);
		}
		
		System.out.println(cycles);
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		for(Cycle cycle: cycles) {
			str.append(cycle.getClass());
			str.append(",");
		}
		return "PipelineMap [cycles=" + cycles + "]";
	}
}
