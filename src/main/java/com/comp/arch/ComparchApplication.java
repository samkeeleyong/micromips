package com.comp.arch;

import memory.MemoryHolder;
import memory.RegisterValuesHolder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ComparchApplication {

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
	}
}
