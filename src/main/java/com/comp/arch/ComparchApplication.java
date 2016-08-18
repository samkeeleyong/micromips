package com.comp.arch;

import memory.MemoryHolder;
import memory.RegisterValuesHolder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ComparchApplication {

	/**
	 * 
	 * BNE R1, R2, L1
DADDIU R4, R3, #0006
L1:DADDIU R5, R4, #0007

	 * TODO
	 * 		1. Test cases
	 * 		2. UI fixes? 
	 * 				Return all opcode immediately
	 * 				Always update memory
	 */
	
	public static void main(String[] args) {
		SpringApplication.run(ComparchApplication.class, args);
		
		// initialization
		for (int i = 0; i <= 31; i++) {
			RegisterValuesHolder.addOrEdit("R" + i, "0000000000000000");
		}
		
		RegisterValuesHolder.addOrEdit("R1", "0000000000000001");
		RegisterValuesHolder.addOrEdit("R2", "0000000000000002");
		RegisterValuesHolder.addOrEdit("R8", "1234567891234567");
		
		String[] values = new String [] {"87","DC","FD","88","97","12","34","56","78","11","22","33","44","55","66","FF"};
		String[] hexValues = PipelineMap.getHexValues("3000", 17, true, 1);
		for (int i = 0; i < values.length; i++) {
			MemoryHolder.addOrEdit(hexValues[i], values[i]);
		}
		
		/*String[] values = new String [] {"87","DC","FD","88","97","12","34","56","78","11","22","33","44","55","66","FF"};
		for (int i = 0; i < values.length; i++) {
			MemoryHolder.add(i + 1000, values[i]);
		}
		ApplicationContext ctx =SpringApplication.run(ComparchApplication.class, args);
		InstructionFormatTranslator ift = (InstructionFormatTranslator) ctx.getBean("ITypeFormatTranslator");
		
		String str = "OR R2,R1,R0";
//		str = "DADDIU R1, R2, #FF55";
		str = "DADDIU R12, R0, #0006"; 
//		String str = "DSRLV R6,R1,R2"; // 00413016
//		String str = "LD R1, 3000(R2)"; // DC413000
//		String str = "DADDIU R1, R2, #FF55"; // 6441FF55
		String instFormatStr = ift.translate(str, "DADDIU");
		System.out.println("Instruction format str:" + instFormatStr);
		*/
		
		/*PipelineMap pipelineMap = new PipelineMap();
//		String[] instructions = new String[] {"DADDIU"};
//		String[] instructions = new String[] {"LD"};
		String[] instructions = new String[] {"SLT", "DADDIU"};
		List<String> opcodes = new ArrayList<>();
		opcodes.add("014C102A");
		opcodes.add("DC0A1000");
//		opcodes.add("640C0006");
//		opcodes.add("640A0004");
//		opcodes.add("6441FF55");
		
		pipelineMap.setUpMipCycles(opcodes, instructions);
		Cycle previousCycle = new Cycle();
		for(Cycle cycle: pipelineMap.getCycles()) {
			System.out.println("Cycle:" + cycle.getCycleName());
			previousCycle = cycle.processCycle(previousCycle);
		}*/
		
		
//DADDIU R1, R2, #FF55
//DADDIU R3, R0, #0006
//DSRLV R6,R1,R2
//LD R1, 3000(R2)
//DADDIU R1, R2, #FF55
	}
}
