package com.comp.arch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import cycle.Cycle;
import cycle.DecodeCycle;
import cycle.ExecutionCycle;
import cycle.FetchCycle;
import cycle.MemCycle;
import cycle.WriteBackCycle;

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

	public void setUpMipCycles(List<String> opcodes, String[] instructions, String[] instructionSplit, Map<String, String> offsetMap) {
		// fill up cycle objects
		String[] cycleCodes = new String[] {"IF", "ID", "EX", "MEM", "WB"};
		int cycleCounter = 0;
		
		String[] instructionAddresses = getHexValues("0004", opcodes.size(), true, 4);
		
		System.out.println("Number of opcodes: " + opcodes.size());
		for (int i = 0; i < opcodes.size(); i++) {
			
			for (int j = 0; j < cycleCodes.length; j++) {
				Cycle cycle = new Cycle(1 + cycleCounter + "");
				
				String cycleCode = cycleCodes[j];
				String opcode = opcodes.get(i);
				String instruction = instructions[i];
				String instructionLine = instructionSplit[i];
				
				switch(cycleCode) {
					case "IF":
						FetchCycle fetchCycle = new FetchCycle(opcode, instructionAddresses[i]);
						cycle.addMipCycle(fetchCycle);
						
						break;
					case "ID":
						DecodeCycle decodeCycle= new DecodeCycle(opcode, instructionLine);
						cycle.addMipCycle(decodeCycle);
						
						break;
					case "EX":
						ExecutionCycle executionCycle = new ExecutionCycle(instruction);
						cycle.addMipCycle(executionCycle);
						
						break;
					case "MEM":
						MemCycle memCycle = new MemCycle(instruction, instructionLine);
						cycle.addMipCycle(memCycle);
						break;
					case "WB":
						WriteBackCycle wmCycle = new WriteBackCycle(instruction, instructionLine);
						cycle.addMipCycle(wmCycle);
						break;
					default:
						break;
				}
				
				cycles.add(cycle);
				cycleCounter++;
			}
		}
		
		System.out.println("Cycles after setup:" + cycles);
	}

	public static String[] getHexValues(String startingPoint, int numberOfHexSeries, boolean includeStartingPoint, int increment) {
		String[] hexValues = new String[numberOfHexSeries];		
		String currentValue = startingPoint;
		
		if (includeStartingPoint) {
			currentValue = Integer.toHexString(Integer.parseInt(currentValue, 16) - increment);
		}
		for(int i = 0; i < numberOfHexSeries; i++) {
			int valueInInt = Integer.parseInt(currentValue, 16) + increment;
			currentValue = Integer.toHexString(valueInInt);
			
			hexValues[i] = ("0000" + currentValue).substring(currentValue.length());
		}
		
		return hexValues;
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
